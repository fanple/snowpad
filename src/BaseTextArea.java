package com.xiboliya.snowpad;

import javax.swing.InputMap;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * 实现撤销管理器和右键快捷菜单的JTextArea控件
 * 
 * @author 冰原
 * 
 */
public class BaseTextArea extends JTextArea {
  private static final long serialVersionUID = 1L;
  private int newFileIndex = 1; // 新建文件的序号
  private File file = null;
  private String title = Util.NEW_FILE_NAME;
  private LinkedList<PartnerBean> highlighterList = new LinkedList<PartnerBean>(); // 存放文本域中所有高亮对象的链表
  private LineSeparator lineSeparator = LineSeparator.DEFAULT; // 换行符格式
  private CharEncoding charEncoding = CharEncoding.BASE; // 字符编码格式
  private KeyAdapter autoIndentKeyAdapter = null; // 用于自动缩进的键盘适配器
  private KeyAdapter tabReplaceKeyAdapter = null; // 用于设置以空格代替Tab键的键盘适配器
  private Color[] colorStyle = null; // 配色方案
  private boolean isSaved = false; // 文件是否已保存，如果已保存则为true
  private boolean isTextChanged = false; // 文本内容是否已修改，如果已修改则为true
  private boolean isStyleChanged = false; // 文本格式是否已修改，如果已修改则为true
  private boolean fileExistsLabel = false; // 当文件删除或移动后，用于标识是否已弹出过提示框
  private boolean tabReplaceBySpace = false; // 是否以空格代替Tab键
  private boolean autoIndent = false; // 是否可自动缩进
  private boolean isLineNumberView = false; // 是否显示行号栏
  private UndoManager undoManager = new UndoManager(); // 撤销管理器
  private int undoIndex = Util.DEFAULT_UNDO_INDEX; // 撤销标识符，初始化为默认值，此值若改变表示文本已修改

  /**
   * 默认的构造方法
   */
  public BaseTextArea() {
    super();
    this.init();
  }

  public BaseTextArea(Setting setting) {
    this();
    this.loadSetting(setting);
  }

  private void init() {
    this.undoManager.setLimit(-1); // 设置此撤销管理器保持的最大编辑数，小于0的值表示编辑数不受限制，默认值为100。
    this.disableShortcut();
    // 初始化用于自动缩进的键盘适配器
    this.autoIndentKeyAdapter = new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          toAutoIndent();
        }
      }
    };
    // 初始化用于设置以空格代替Tab键的键盘适配器
    this.tabReplaceKeyAdapter = new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_TAB) {
          toTabReplace();
        }
      }
    };
  }

  public void loadSetting(Setting setting) {
    if (setting == null) {
      return;
    }
    this.setLineWrap(setting.isLineWrap);
    this.setWrapStyleWord(setting.isWrapStyleWord);
    this.setLineSeparator(setting.lineSeparator);
    this.setCharEncoding(setting.charEncoding);
    this.setFont(setting.font);
    this.setDragEnabled(setting.textDrag);
    this.setAutoIndent(setting.autoIndent);
    this.setTabReplaceBySpace(setting.tabReplaceBySpace);
    this.setColorStyle(setting.colorStyle);
    this.setTabSize(setting.tabSize);
    this.setSaved(setting.isSaved);
    this.setTextChanged(setting.isTextChanged);
    this.setStyleChanged(setting.isStyleChanged);
    this.setFileExistsLabel(setting.fileExistsLabel);
    this.setLineNumberView(setting.isLineNumberView);
  }

  private void disableShortcut() {
    // 屏蔽JTextArea组件的默认热键：Ctrl+C、Ctrl+H、Ctrl+V、Ctrl+X
    InputMap inputMap = this.getInputMap();
    inputMap.put(
        KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK),
        "CTRL_C");
    inputMap.put(
        KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK),
        "CTRL_H");
    inputMap.put(
        KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK),
        "CTRL_V");
    inputMap.put(
        KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK),
        "CTRL_X");
  }

  /**
   * 按回车键时进行缩进
   */
  private void toAutoIndent() {
    CurrentLines currentLines = new CurrentLines(this,
        CurrentLines.LineExtend.EXTEND_UP);
    String strContentExtend = currentLines.getStrContentExtend();
    if (strContentExtend == null) {
      return;
    }
    int currentIndex = currentLines.getCurrentIndex();
    if (this.getText().charAt(currentIndex - 1) != '\n') { // 如果不是换行操作，将不作处理
      return;
    }
    String strSpace = "";
    for (int i = 0; i < strContentExtend.length(); i++) {
      switch (strContentExtend.charAt(i)) {
      case ' ':
      case '\t':
      case '　':
        strSpace += strContentExtend.charAt(i);
        break;
      default:
        break;
      }
      if (strSpace.length() == i) {
        break;
      }
    }
    if (!strSpace.isEmpty()) {
      this.insert(strSpace, this.getCaretPosition());
    }
  }

  /**
   * 按Tab键时将其替换为等量的空格
   */
  private void toTabReplace() {
    int currentIndex = this.getCaretPosition();
    if (this.getText().charAt(currentIndex - 1) != '\t') { // 文本域中输入的前一个字符是否为Tab
      return;
    }
    int tabSize = this.getTabSize();
    String strSpace = "";
    for (int i = 0; i < tabSize; i++) {
      strSpace += " ";
    }
    this.replaceRange(strSpace, currentIndex - 1, currentIndex);
  }

  public void setFile(File file) {
    if (file != null) {
      try {
        this.file = file.getCanonicalFile(); // 获取此抽象路径名的规范形式
        this.setTitle(this.file.getName());
      } catch (IOException x) {
        x.printStackTrace();
      }
    } else {
      this.file = file;
    }
  }

  public File getFile() {
    return this.file;
  }

  public String getFileName() {
    if (this.file != null) {
      return file.getAbsolutePath();
    }
    return null;
  }

  public void setTitle(String title) {
    if (title != null) {
      this.title = title;
    }
  }

  public String getTitle() {
    return this.title;
  }

  public LinkedList<PartnerBean> getHighlighterList() {
    return this.highlighterList;
  }

  public void setLineSeparator(LineSeparator lineSeparator) {
    this.lineSeparator = lineSeparator;
  }

  public LineSeparator getLineSeparator() {
    return this.lineSeparator;
  }

  public void setCharEncoding(CharEncoding charEncoding) {
    this.charEncoding = charEncoding;
  }

  public CharEncoding getCharEncoding() {
    return this.charEncoding;
  }

  public void setSaved(boolean isSaved) {
    this.isSaved = isSaved;
  }

  public boolean getSaved() {
    return this.isSaved;
  }

  public void setTextChanged(boolean isTextChanged) {
    this.isTextChanged = isTextChanged;
  }

  public boolean getTextChanged() {
    return this.isTextChanged;
  }

  public void setStyleChanged(boolean isStyleChanged) {
    this.isStyleChanged = isStyleChanged;
  }

  public boolean getStyleChanged() {
    return this.isStyleChanged;
  }

  public void setFileExistsLabel(boolean fileExistsLabel) {
    this.fileExistsLabel = fileExistsLabel;
  }

  public boolean getFileExistsLabel() {
    return this.fileExistsLabel;
  }

  public void setColorStyle(Color[] colorStyle) {
    if (colorStyle == null) {
      return;
    }
    this.colorStyle = colorStyle;
    this.setForeground(colorStyle[0]);
    this.setBackground(colorStyle[1]);
    this.setCaretColor(colorStyle[2]);
    this.setSelectedTextColor(colorStyle[3]);
    this.setSelectionColor(colorStyle[4]);
  }

  public Color[] getColorStyle() {
    return this.colorStyle;
  }

  public void setAutoIndent(boolean enable) {
    if (enable) {
      this.addKeyListener(this.autoIndentKeyAdapter);
    } else {
      this.removeKeyListener(this.autoIndentKeyAdapter);
    }
    this.autoIndent = enable;
  }

  public boolean getAutoIndent() {
    return this.autoIndent;
  }

  public void setTabReplaceBySpace(boolean enable) {
    if (enable) {
      this.addKeyListener(this.tabReplaceKeyAdapter);
    } else {
      this.removeKeyListener(this.tabReplaceKeyAdapter);
    }
    this.tabReplaceBySpace = enable;
  }

  public boolean getTabReplaceBySpace() {
    return this.tabReplaceBySpace;
  }

  public UndoManager getUndoManager() {
    return this.undoManager;
  }

  public int getUndoIndex() {
    return this.undoIndex;
  }

  public void setUndoIndex(int undoIndex) {
    this.undoIndex = undoIndex;
  }

  public void setNewFileIndex(int newFileIndex) {
    this.newFileIndex = newFileIndex;
  }

  public int getNewFileIndex() {
    return this.newFileIndex;
  }

  public boolean getLineNumberView() {
    return this.isLineNumberView;
  }

  public void setLineNumberView(boolean isLineNumberView) {
    this.isLineNumberView = isLineNumberView;
  }
}
