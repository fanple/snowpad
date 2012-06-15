package com.xiboliya.snowpad;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.undo.UndoManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * ��ѩ���±� ����һ����Windows�ġ����±���������ͬ��java�汾��
 * ��Ȼ��ֻ�����Ҫ������Ŀ����ʵ��һ������ͬʱ��Windows��Linux�����е���ǿ�ͼ��±���
 * �ٶȿռ䣺http://hi.baidu.com/xiboliya
 * 
 * @author chen
 * 
 */
public class SnowPadFrame extends JFrame implements ActionListener,
    CaretListener, UndoableEditListener, WindowFocusListener {
  private static final long serialVersionUID = 1L; // ���л�����ʱʹ�õ�һ���汾�ţ����뵱ǰ�����л��������
  private JTextArea txaMain = new JTextArea(); // ���ڱ༭���ı���
  private JMenuBar menuBar = new JMenuBar();
  private JMenu menuFile = new JMenu("�ļ�(F)");
  private JMenuItem itemNew = new JMenuItem("�½�(N)", 'N');
  private JMenuItem itemOpen = new JMenuItem("��(O)...", 'O');
  private JMenuItem itemOpenByEncoding = new JMenuItem("��ָ�������(E)...", 'E');
  private JMenuItem itemReOpen = new JMenuItem("���������ļ�(L)", 'L');
  private JMenuItem itemReName = new JMenuItem("������(R)...", 'R');
  private JMenuItem itemSave = new JMenuItem("����(S)", 'S');
  private JMenuItem itemSaveAs = new JMenuItem("����Ϊ(A)...", 'A');
  private JMenuItem itemDelFile = new JMenuItem("ɾ����ǰ�ļ�(D)", 'D');
  private JMenu menuFileHistory = new JMenu("����༭");
  private JMenuItem itemClearFileHistory = new JMenuItem("�������༭�б�");
  private JMenuItem itemExit = new JMenuItem("�˳�(X)", 'X');
  private JMenu menuEdit = new JMenu("�༭(E)");
  private JMenuItem itemUnDo = new JMenuItem("����(U)", 'U');
  private JMenuItem itemReDo = new JMenuItem("����(Y)", 'Y');
  private JMenuItem itemCut = new JMenuItem("����(T)", 'T');
  private JMenuItem itemCopy = new JMenuItem("����(C)", 'C');
  private JMenuItem itemPaste = new JMenuItem("ճ��(P)", 'P');
  private JMenuItem itemDel = new JMenuItem("ɾ��(L)", 'L');
  private JMenu menuCase = new JMenu("�л���Сд");
  private JMenuItem itemCaseUp = new JMenuItem("�л�Ϊ��д");
  private JMenuItem itemCaseLow = new JMenuItem("�л�ΪСд");
  private JMenu menuToClip = new JMenu("���Ƶ�������");
  private JMenuItem itemToClipFileName = new JMenuItem("��ǰ�ļ���");
  private JMenuItem itemToClipFilePath = new JMenuItem("��ǰ�ļ�·��");
  private JMenuItem itemToClipDirPath = new JMenuItem("��ǰĿ¼·��");
  private JMenuItem itemToClipCurLine = new JMenuItem("��ǰ��");
  private JMenuItem itemToClipAllText = new JMenuItem("�����ı�");
  private JMenu menuLine = new JMenu("������");
  private JMenuItem itemLineCopy = new JMenuItem("��д��ǰ��");
  private JMenuItem itemLineDel = new JMenuItem("ɾ����ǰ��");
  private JMenuItem itemLineDelToStart = new JMenuItem("ɾ��������");
  private JMenuItem itemLineDelToEnd = new JMenuItem("ɾ������β");
  private JMenuItem itemLineToUp = new JMenuItem("���Ƶ�ǰ��");
  private JMenuItem itemLineToDown = new JMenuItem("���Ƶ�ǰ��");
  private JMenu menuSort = new JMenu("����");
  private JMenuItem itemSortUp = new JMenuItem("����");
  private JMenuItem itemSortDown = new JMenuItem("����");
  private JMenuItem itemSelCopy = new JMenuItem("��д��ǰѡ��(W)", 'W');
  private JMenu menuTrim = new JMenu("����հ�");
  private JMenuItem itemTrimStart = new JMenuItem("����");
  private JMenuItem itemTrimEnd = new JMenuItem("��β");
  private JMenuItem itemTrimAll = new JMenuItem("����+��β");
  private JMenuItem itemTrimSelected = new JMenuItem("ѡ����");
  private JMenu menuDelNullLine = new JMenu("ɾ������");
  private JMenuItem itemDelNullLineAll = new JMenuItem("ȫ�ķ�Χ");
  private JMenuItem itemDelNullLineSelected = new JMenuItem("ѡ����Χ");
  private JMenuItem itemSelAll = new JMenuItem("ȫѡ(A)", 'A');
  private JMenu menuInsert = new JMenu("����(I)");
  private JMenuItem itemInsertDateTime = new JMenuItem("ʱ��/����(D)...", 'D');
  private JMenuItem itemInsertChar = new JMenuItem("�����ַ�(S)...", 'S');
  private JMenu menuSearch = new JMenu("����(S)");
  private JMenuItem itemFind = new JMenuItem("����(F)...", 'F');
  private JMenuItem itemFindNext = new JMenuItem("������һ��(N)", 'N');
  private JMenuItem itemFindPrevious = new JMenuItem("������һ��(P)", 'P');
  private JMenu menuQuickFind = new JMenu("���ٲ���");
  private JMenuItem itemQuickFindDown = new JMenuItem("�������²���");
  private JMenuItem itemQuickFindUp = new JMenuItem("�������ϲ���");
  private JMenuItem itemReplace = new JMenuItem("�滻(R)...", 'R');
  private JMenuItem itemGoto = new JMenuItem("ת��(G)...", 'G');
  private JMenu menuStyle = new JMenu("��ʽ(O)");
  private JMenu menuLineWrapSet = new JMenu("��������(L)");
  private JCheckBoxMenuItem itemLineWrap = new JCheckBoxMenuItem("�Զ�����(W)");
  private JMenu menuLineWrapStyle = new JMenu("���з�ʽ(S)");
  private JRadioButtonMenuItem itemLineWrapByWord = new JRadioButtonMenuItem(
      "���ʱ߽绻��(W)");
  private JRadioButtonMenuItem itemLineWrapByChar = new JRadioButtonMenuItem(
      "�ַ��߽绻��(C)");
  private JMenuItem itemFont = new JMenuItem("����(F)...", 'F');
  private JCheckBoxMenuItem itemTextDrag = new JCheckBoxMenuItem("�ı���ק(D)");
  private JCheckBoxMenuItem itemAutoIndent = new JCheckBoxMenuItem("�Զ�����(I)");
  private JMenu menuLineStyle = new JMenu("���з���ʽ(S)");
  private JRadioButtonMenuItem itemLineStyleWin = new JRadioButtonMenuItem(
      LineSeparator.WINDOWS.getName() + "��ʽ");
  private JRadioButtonMenuItem itemLineStyleUnix = new JRadioButtonMenuItem(
      LineSeparator.UNIX.getName() + "��ʽ");
  private JRadioButtonMenuItem itemLineStyleMac = new JRadioButtonMenuItem(
      LineSeparator.MACINTOSH.getName() + "��ʽ");
  private JMenu menuCharset = new JMenu("�ַ������ʽ(C)");
  private JRadioButtonMenuItem itemCharsetBASE = new JRadioButtonMenuItem(
      "Ĭ�ϸ�ʽ(" + CharEncoding.BASE.toString() + ")");
  private JRadioButtonMenuItem itemCharsetANSI = new JRadioButtonMenuItem(
      "ANSI��ʽ");
  private JRadioButtonMenuItem itemCharsetUTF8 = new JRadioButtonMenuItem(
      "UTF-8��ʽ");
  private JRadioButtonMenuItem itemCharsetUTF8_NO_BOM = new JRadioButtonMenuItem(
      "UTF-8 No BOM��ʽ");
  private JRadioButtonMenuItem itemCharsetULE = new JRadioButtonMenuItem(
      "Unicode Little Endian��ʽ");
  private JRadioButtonMenuItem itemCharsetUBE = new JRadioButtonMenuItem(
      "Unicode Big Endian��ʽ");
  private JMenu menuView = new JMenu("�鿴(V)");
  private JCheckBoxMenuItem itemStateBar = new JCheckBoxMenuItem("״̬��(S)");
  private JCheckBoxMenuItem itemAlwaysOnTop = new JCheckBoxMenuItem("ǰ����ʾ(A)");
  private JCheckBoxMenuItem itemResizable = new JCheckBoxMenuItem("��������(R)");
  private JMenu menuFontSize = new JMenu("��������(F)");
  private JMenuItem itemFontSizePlus = new JMenuItem("�Ŵ�(B)", 'B');
  private JMenuItem itemFontSizeMinus = new JMenuItem("��С(S)", 'S');
  private JMenuItem itemFontSizeReset = new JMenuItem("�ָ���ʼ(D)", 'D');
  private JMenu menuColor = new JMenu("��ɫ����(C)");
  private JMenuItem itemColorFont = new JMenuItem("������ɫ(F)...", 'F');
  private JMenuItem itemColorBack = new JMenuItem("������ɫ(B)...", 'B');
  private JMenuItem itemColorCaret = new JMenuItem("�����ɫ(C)...", 'C');
  private JMenuItem itemColorSelFont = new JMenuItem("ѡ��������ɫ(T)...", 'T');
  private JMenuItem itemColorSelBack = new JMenuItem("ѡ��������ɫ(K)...", 'K');
  private JMenuItem itemColorAnti = new JMenuItem("ȫ����ɫ(A)", 'A');
  private JMenuItem itemColorComplementary = new JMenuItem("ȫ����ɫ(R)", 'R');
  private JMenu menuColorStyle = new JMenu("��ɫ����(Y)");
  private JMenuItem itemColorStyle1 = new JMenuItem("��ɫ����(1)", '1');
  private JMenuItem itemColorStyle2 = new JMenuItem("��ɫ����(2)", '2');
  private JMenuItem itemColorStyle3 = new JMenuItem("��ɫ����(3)", '3');
  private JMenuItem itemColorStyle4 = new JMenuItem("��ɫ����(4)", '4');
  private JMenuItem itemColorStyle5 = new JMenuItem("��ɫ����(5)", '5');
  private JMenuItem itemColorStyleDefault = new JMenuItem("�ָ�Ĭ����ɫ(0)", '0');
  private JMenu menuHighlight = new JMenu("������ʾ(H)");
  private JMenuItem itemHighlight1 = new JMenuItem("��ʽ(1)", '1');
  private JMenuItem itemHighlight2 = new JMenuItem("��ʽ(2)", '2');
  private JMenuItem itemHighlight3 = new JMenuItem("��ʽ(3)", '3');
  private JMenuItem itemHighlight4 = new JMenuItem("��ʽ(4)", '4');
  private JMenuItem itemHighlight5 = new JMenuItem("��ʽ(5)", '5');
  private JMenu menuRmHighlight = new JMenu("�������(M)");
  private JMenuItem itemRmHighlight1 = new JMenuItem("��ʽ(1)", '1');
  private JMenuItem itemRmHighlight2 = new JMenuItem("��ʽ(2)", '2');
  private JMenuItem itemRmHighlight3 = new JMenuItem("��ʽ(3)", '3');
  private JMenuItem itemRmHighlight4 = new JMenuItem("��ʽ(4)", '4');
  private JMenuItem itemRmHighlight5 = new JMenuItem("��ʽ(5)", '5');
  private JMenuItem itemRmHighlightAll = new JMenuItem("���и�ʽ(0)", '0');
  private JMenuItem itemTabSet = new JMenuItem("Tab������...", 'T');
  private JMenu menuHelp = new JMenu("����(H)");
  private JMenuItem itemHelp = new JMenuItem("��������(H)", 'H');
  private JMenuItem itemAbout = new JMenuItem("���ڼ��±�(A)", 'A');
  private JScrollPane srp = new JScrollPane(this.txaMain);
  private JPopupMenu popMenu = new JPopupMenu();
  private JMenuItem itemPopUnDo = new JMenuItem("����(U)", 'U');
  private JMenuItem itemPopReDo = new JMenuItem("����(Y)", 'Y');
  private JMenuItem itemPopCut = new JMenuItem("����(T)", 'T');
  private JMenuItem itemPopCopy = new JMenuItem("����(C)", 'C');
  private JMenuItem itemPopPaste = new JMenuItem("ճ��(P)", 'P');
  private JMenuItem itemPopDel = new JMenuItem("ɾ��(D)", 'D');
  private JMenuItem itemPopSelAll = new JMenuItem("ȫѡ(A)", 'A');
  private JMenu menuPopHighlight = new JMenu("������ʾ(H)");
  private JMenuItem itemPopHighlight1 = new JMenuItem("��ʽ(1)", '1');
  private JMenuItem itemPopHighlight2 = new JMenuItem("��ʽ(2)", '2');
  private JMenuItem itemPopHighlight3 = new JMenuItem("��ʽ(3)", '3');
  private JMenuItem itemPopHighlight4 = new JMenuItem("��ʽ(4)", '4');
  private JMenuItem itemPopHighlight5 = new JMenuItem("��ʽ(5)", '5');
  private JMenu menuPopRmHighlight = new JMenu("�������(M)");
  private JMenuItem itemPopRmHighlight1 = new JMenuItem("��ʽ(1)", '1');
  private JMenuItem itemPopRmHighlight2 = new JMenuItem("��ʽ(2)", '2');
  private JMenuItem itemPopRmHighlight3 = new JMenuItem("��ʽ(3)", '3');
  private JMenuItem itemPopRmHighlight4 = new JMenuItem("��ʽ(4)", '4');
  private JMenuItem itemPopRmHighlight5 = new JMenuItem("��ʽ(5)", '5');
  private JMenuItem itemPopRmHighlightAll = new JMenuItem("���и�ʽ(0)", '0');

  private JFileChooser fcrOpen = new OpenFileChooser(); // "��"�ļ�ѡ����
  private JFileChooser fcrSave = new SaveFileChooser(); // "����"�ļ�ѡ����
  private StringBuilder stbTitle = new StringBuilder(Util.SOFTWARE); // �������ַ���
  private StatePanel pnlState = new StatePanel(4); // ״̬�����
  private boolean isNew = true; // �ļ��Ƿ��ѱ��棬���δ������Ϊtrue
  private boolean isTextChanged = false; // �ı������Ƿ����޸ģ�������޸���Ϊtrue
  private boolean isStyleChanged = false; // �ı���ʽ�Ƿ����޸ģ�������޸���Ϊtrue
  private boolean fileExistsLabel = false; // ���ļ�ɾ�����ƶ������ڱ�ʶ�Ƿ��ѵ�������ʾ��
  private LineSeparator lineSeparator = LineSeparator.DEFAULT; // ��ǰ�Ļ��з���ʽ
  private CharEncoding encoding = CharEncoding.BASE; // ��ǰ���ַ�����
  private UndoManager undoManager = new UndoManager(); // ����������
  private int undoIndex = Util.DEFAULT_UNDO_INDEX; // ������ʶ������ʼ��ΪĬ��ֵ����ֵ���ı��ʾ�ı����޸�
  private Clipboard clip = this.getToolkit().getSystemClipboard(); // ������
  private FontChooser fontChooser = null; // ����Ի���
  private FindReplaceDialog findReplaceDialog = null; // ���ҡ��滻�Ի���
  private GotoDialog gotoDialog = null; // ת���Ի���
  private AboutDialog aboutDialog = null; // ���ڶԻ���
  private TabSetDialog tabSetDialog = null; // Tab�ַ����öԻ���
  private InsertCharDialog insertCharDialog = null; // �����ַ��Ի���
  private InsertDateDialog insertDateDialog = null; // ����ʱ��/���ڶԻ���
  private FileEncodingDialog fileEncodingDialog = null; // �ļ������ʽ�Ի���
  private LinkedList<String> fileHistoryList = new LinkedList<String>(); // �������༭���ļ���������
  private ButtonGroup bgpLineWrapStyle = new ButtonGroup(); // ���ڴ�Ż��з�ʽ�İ�ť��
  private ButtonGroup bgpLineStyle = new ButtonGroup(); // ���ڴ�Ż��з���ʽ�İ�ť��
  private ButtonGroup bgpCharset = new ButtonGroup(); // ���ڴ���ַ������ʽ�İ�ť��
  private KeyAdapter autoIndentKeyAdapter = null; // �����Զ������ļ���������
  private KeyAdapter tabReplaceKeyAdapter = null; // ���������Կո����Tab���ļ���������
  private boolean isReplaceBySpace = true; // �Կո����Tab��
  private File file = null; // ��ǰ�༭���ļ�
  private ImageIcon icon = null; // ������ͼ��
  private final Color colorFont = this.txaMain.getForeground(); // �ı���Ĭ��������ɫ
  private final Color colorBack = this.txaMain.getBackground(); // �ı���Ĭ�ϱ�����ɫ
  private final Color colorCaret = this.txaMain.getCaretColor(); // �ı���Ĭ�Ϲ����ɫ
  private final Color colorSelFont = this.txaMain.getSelectedTextColor(); // �ı���Ĭ��ѡ��������ɫ
  private final Color colorSelBack = this.txaMain.getSelectionColor(); // �ı���Ĭ��ѡ��������ɫ
  private Highlighter highlighter = this.txaMain.getHighlighter(); // �ı���ĸ�����ʾ����
  private LinkedList<PartnerBean> highlighterList = new LinkedList<PartnerBean>(); // ����ı��������и������������

  /**
   * ���췽�� ���ڳ�ʼ�����������
   */
  public SnowPadFrame() {
    this.setTitle(this.stbTitle.toString());
    this.setSize(600, 500);
    this.setLocationRelativeTo(null); // ʹ���ھ�����ʾ
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // ����Ĭ�Ϲرղ���Ϊ�գ��Ա����Ӵ��ڼ����¼�
    this.init();
    this.setIcon();
    this.setVisible(true);
  }

  /**
   * �������Ĺ��췽���������ڳ���������ͬʱ���ļ�
   * 
   * @param strFile
   *          ��ʾ�ļ�·�����ַ����������Ǿ���·�����磺E:\file\test.txt��Ҳ���������·�����磺../test.txt��
   */
  public SnowPadFrame(String strFile) {
    this();
    if (strFile != null && !strFile.isEmpty()) {
      File file = new File(strFile);
      if (file.exists()) {
        this.toOpenFile(file, true);
        this.setAfterOpenFile();
        this.setFileNameAndPath(file);
      }
    }
  }

  /**
   * �����Զ���Ĵ���ͼ��
   */
  private void setIcon() {
    try {
      this.icon = new ImageIcon(ClassLoader.getSystemResource("res/icon.gif"));
      this.setIconImage(icon.getImage());
    } catch (Exception x) {
      x.printStackTrace();
    }
  }

  /**
   * ��ʼ����������Ӽ�����
   */
  private void init() {
    this.addMenuItem();
    this.addTextArea();
    this.addStatePanel();
    this.addPopMenu();
    this.setMenuMnemonic();
    this.setMenuDefault();
    this.setMenuDefaultInit();
    this.addListeners();
    this.addFileFilter();
    this.undoManager.setLimit(-1); // ���ô˳������������ֵ����༭����С��0��ֵ��ʾ�༭���������ƣ�Ĭ��ֵΪ100��
  }

  /**
   * ���Ӹ�������¼�������
   */
  private void addListeners() {
    this.txaMain.addCaretListener(this);
    this.txaMain.getDocument().addUndoableEditListener(this);
    this.itemAbout.addActionListener(this);
    this.itemCopy.addActionListener(this);
    this.itemCut.addActionListener(this);
    this.itemInsertChar.addActionListener(this);
    this.itemInsertDateTime.addActionListener(this);
    this.itemDel.addActionListener(this);
    this.itemCaseUp.addActionListener(this);
    this.itemCaseLow.addActionListener(this);
    this.itemQuickFindDown.addActionListener(this);
    this.itemQuickFindUp.addActionListener(this);
    this.itemExit.addActionListener(this);
    this.itemFind.addActionListener(this);
    this.itemFindNext.addActionListener(this);
    this.itemFindPrevious.addActionListener(this);
    this.itemFont.addActionListener(this);
    this.itemGoto.addActionListener(this);
    this.itemToClipFileName.addActionListener(this);
    this.itemToClipFilePath.addActionListener(this);
    this.itemToClipDirPath.addActionListener(this);
    this.itemToClipCurLine.addActionListener(this);
    this.itemToClipAllText.addActionListener(this);
    this.itemLineCopy.addActionListener(this);
    this.itemLineDel.addActionListener(this);
    this.itemLineDelToStart.addActionListener(this);
    this.itemLineDelToEnd.addActionListener(this);
    this.itemLineToUp.addActionListener(this);
    this.itemLineToDown.addActionListener(this);
    this.itemSortUp.addActionListener(this);
    this.itemSortDown.addActionListener(this);
    this.itemSelCopy.addActionListener(this);
    this.itemTrimStart.addActionListener(this);
    this.itemTrimEnd.addActionListener(this);
    this.itemTrimAll.addActionListener(this);
    this.itemTrimSelected.addActionListener(this);
    this.itemDelNullLineAll.addActionListener(this);
    this.itemDelNullLineSelected.addActionListener(this);
    this.itemHelp.addActionListener(this);
    this.itemLineWrap.addActionListener(this);
    this.itemLineWrapByWord.addActionListener(this);
    this.itemLineWrapByChar.addActionListener(this);
    this.itemLineStyleWin.addActionListener(this);
    this.itemLineStyleUnix.addActionListener(this);
    this.itemLineStyleMac.addActionListener(this);
    this.itemCharsetBASE.addActionListener(this);
    this.itemCharsetANSI.addActionListener(this);
    this.itemCharsetUTF8.addActionListener(this);
    this.itemCharsetUTF8_NO_BOM.addActionListener(this);
    this.itemCharsetULE.addActionListener(this);
    this.itemCharsetUBE.addActionListener(this);
    this.itemTextDrag.addActionListener(this);
    this.itemAutoIndent.addActionListener(this);
    this.itemAlwaysOnTop.addActionListener(this);
    this.itemResizable.addActionListener(this);
    this.itemFontSizePlus.addActionListener(this);
    this.itemFontSizeMinus.addActionListener(this);
    this.itemFontSizeReset.addActionListener(this);
    this.itemColorFont.addActionListener(this);
    this.itemColorBack.addActionListener(this);
    this.itemColorCaret.addActionListener(this);
    this.itemColorSelFont.addActionListener(this);
    this.itemColorSelBack.addActionListener(this);
    this.itemColorAnti.addActionListener(this);
    this.itemColorComplementary.addActionListener(this);
    this.itemColorStyle1.addActionListener(this);
    this.itemColorStyle2.addActionListener(this);
    this.itemColorStyle3.addActionListener(this);
    this.itemColorStyle4.addActionListener(this);
    this.itemColorStyle5.addActionListener(this);
    this.itemColorStyleDefault.addActionListener(this);
    this.itemHighlight1.addActionListener(this);
    this.itemHighlight2.addActionListener(this);
    this.itemHighlight3.addActionListener(this);
    this.itemHighlight4.addActionListener(this);
    this.itemHighlight5.addActionListener(this);
    this.itemRmHighlight1.addActionListener(this);
    this.itemRmHighlight2.addActionListener(this);
    this.itemRmHighlight3.addActionListener(this);
    this.itemRmHighlight4.addActionListener(this);
    this.itemRmHighlight5.addActionListener(this);
    this.itemRmHighlightAll.addActionListener(this);
    this.itemTabSet.addActionListener(this);
    this.itemNew.addActionListener(this);
    this.itemOpen.addActionListener(this);
    this.itemOpenByEncoding.addActionListener(this);
    this.itemReOpen.addActionListener(this);
    this.itemReName.addActionListener(this);
    this.itemPaste.addActionListener(this);
    this.itemPopCopy.addActionListener(this);
    this.itemPopCut.addActionListener(this);
    this.itemPopDel.addActionListener(this);
    this.itemPopPaste.addActionListener(this);
    this.itemPopSelAll.addActionListener(this);
    this.itemPopUnDo.addActionListener(this);
    this.itemPopReDo.addActionListener(this);
    this.itemPopHighlight1.addActionListener(this);
    this.itemPopHighlight2.addActionListener(this);
    this.itemPopHighlight3.addActionListener(this);
    this.itemPopHighlight4.addActionListener(this);
    this.itemPopHighlight5.addActionListener(this);
    this.itemPopRmHighlight1.addActionListener(this);
    this.itemPopRmHighlight2.addActionListener(this);
    this.itemPopRmHighlight3.addActionListener(this);
    this.itemPopRmHighlight4.addActionListener(this);
    this.itemPopRmHighlight5.addActionListener(this);
    this.itemPopRmHighlightAll.addActionListener(this);
    this.itemReplace.addActionListener(this);
    this.itemSave.addActionListener(this);
    this.itemSaveAs.addActionListener(this);
    this.itemDelFile.addActionListener(this);
    this.itemClearFileHistory.addActionListener(this);
    this.itemSelAll.addActionListener(this);
    this.itemStateBar.addActionListener(this);
    this.itemReDo.addActionListener(this);
    this.itemUnDo.addActionListener(this);
    // Ϊ���������¼�������
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        exit();
      }
    });
    // Ϊ�������ӽ��������
    this.addWindowFocusListener(this);
    // Ϊ�ı�����������¼�������
    this.txaMain.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) { // ����Ҽ�ʱ����ʾ��ݲ˵�
          popMenu.show(txaMain, e.getX(), e.getY());
        }
      }
    });
    // ����JTextArea�����Ĭ���ȼ���Ctrl+C��Ctrl+H��Ctrl+V��Ctrl+X
    InputMap inputMap = this.txaMain.getInputMap();
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
    // ��ʼ�������Զ������ļ���������
    this.autoIndentKeyAdapter = new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          toAutoIndent();
        }
      }
    };
    // ��ʼ�����������Կո����Tab���ļ���������
    this.tabReplaceKeyAdapter = new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_TAB) {
          toTabReplace();
        }
      }
    };
  }

  /**
   * ������������ı���
   */
  private void addTextArea() {
    this.getContentPane().add(this.srp, BorderLayout.CENTER); // Ϊʹ�ı����й������������������������
    this.txaMain.setTabSize(Util.DEFAULT_TABSIZE); // Ϊ�����ۣ��ʵ�����"Tab"�ַ�ռ�õĿո�Ĭ��ռ��8���ո�
    this.txaMain.setFont(Util.TEXT_FONT);
  }

  /**
   * �����������״̬��
   */
  private void addStatePanel() {
    String strStateChars = Util.STATE_CHARS + "0";
    String strStateLines = Util.STATE_LINES + "1";
    String strStateCurLn = Util.STATE_CUR_LINE + "1";
    String strStateCurCol = Util.STATE_CUR_COLUMN + "1";
    String strStateCurSel = Util.STATE_CUR_SELECT + "0";
    String strStateLineStyle = Util.STATE_LINE_STYLE;
    String strStateEncoding = Util.STATE_ENCODING;
    this.pnlState.setStringByIndex(0, strStateChars + ", " + strStateLines,
        StatePanelAlignment.X_CENTER);
    this.pnlState.setStringByIndex(1, strStateCurLn + ", " + strStateCurCol
        + ", " + strStateCurSel);
    this.pnlState.setStringByIndex(2, strStateLineStyle);
    this.pnlState.setStringByIndex(3, strStateEncoding);
    this.getContentPane().add(this.pnlState, BorderLayout.SOUTH);
  }

  /**
   * ����������Ӳ˵���
   */
  private void addMenuItem() {
    this.setJMenuBar(this.menuBar);
    this.menuBar.add(this.menuFile);
    this.menuFile.add(this.itemNew);
    this.menuFile.add(this.itemOpen);
    this.menuFile.add(this.itemOpenByEncoding);
    this.menuFile.add(this.itemReOpen);
    this.menuFile.add(this.itemReName);
    this.menuFile.add(this.itemSave);
    this.menuFile.add(this.itemSaveAs);
    this.menuFile.addSeparator();
    this.menuFile.add(this.itemDelFile);
    this.menuFile.add(this.menuFileHistory);
    this.menuFile.add(this.itemClearFileHistory);
    this.menuFile.addSeparator();
    this.menuFile.add(this.itemExit);
    this.menuBar.add(this.menuEdit);
    this.menuEdit.add(this.itemUnDo);
    this.menuEdit.add(this.itemReDo);
    this.menuEdit.addSeparator();
    this.menuEdit.add(this.itemCut);
    this.menuEdit.add(this.itemCopy);
    this.menuEdit.add(this.itemPaste);
    this.menuEdit.add(this.itemSelAll);
    this.menuEdit.add(this.itemDel);
    this.menuEdit.addSeparator();
    this.menuEdit.add(this.menuCase);
    this.menuCase.add(this.itemCaseUp);
    this.menuCase.add(this.itemCaseLow);
    this.menuEdit.add(this.menuToClip);
    this.menuToClip.add(this.itemToClipFileName);
    this.menuToClip.add(this.itemToClipFilePath);
    this.menuToClip.add(this.itemToClipDirPath);
    this.menuToClip.addSeparator();
    this.menuToClip.add(this.itemToClipCurLine);
    this.menuToClip.add(this.itemToClipAllText);
    this.menuEdit.add(this.menuLine);
    this.menuLine.add(this.itemLineCopy);
    this.menuLine.add(this.itemLineDel);
    this.menuLine.addSeparator();
    this.menuLine.add(this.itemLineDelToStart);
    this.menuLine.add(this.itemLineDelToEnd);
    this.menuLine.addSeparator();
    this.menuLine.add(this.itemLineToUp);
    this.menuLine.add(this.itemLineToDown);
    this.menuEdit.add(this.menuSort);
    this.menuSort.add(this.itemSortUp);
    this.menuSort.add(this.itemSortDown);
    this.menuEdit.add(this.itemSelCopy);
    this.menuEdit.add(this.menuTrim);
    this.menuTrim.add(this.itemTrimStart);
    this.menuTrim.add(this.itemTrimEnd);
    this.menuTrim.add(this.itemTrimAll);
    this.menuTrim.addSeparator();
    this.menuTrim.add(this.itemTrimSelected);
    this.menuEdit.add(this.menuDelNullLine);
    this.menuDelNullLine.add(this.itemDelNullLineAll);
    this.menuDelNullLine.add(this.itemDelNullLineSelected);
    this.menuEdit.addSeparator();
    this.menuEdit.add(this.menuInsert);
    this.menuInsert.add(this.itemInsertChar);
    this.menuInsert.add(this.itemInsertDateTime);
    this.menuBar.add(this.menuSearch);
    this.menuSearch.add(this.itemFind);
    this.menuSearch.add(this.itemFindNext);
    this.menuSearch.add(this.itemFindPrevious);
    this.menuSearch.add(this.menuQuickFind);
    this.menuQuickFind.add(this.itemQuickFindDown);
    this.menuQuickFind.add(this.itemQuickFindUp);
    this.menuSearch.add(this.itemReplace);
    this.menuSearch.add(this.itemGoto);
    this.menuBar.add(this.menuStyle);
    this.menuStyle.add(this.menuLineWrapSet);
    this.menuLineWrapSet.add(this.itemLineWrap);
    this.menuLineWrapSet.add(this.menuLineWrapStyle);
    this.menuLineWrapStyle.add(this.itemLineWrapByWord);
    this.menuLineWrapStyle.add(this.itemLineWrapByChar);
    this.menuStyle.add(this.menuLineStyle);
    this.menuLineStyle.add(this.itemLineStyleWin);
    this.menuLineStyle.add(this.itemLineStyleUnix);
    this.menuLineStyle.add(this.itemLineStyleMac);
    this.menuStyle.add(this.menuCharset);
    this.menuCharset.add(this.itemCharsetBASE);
    this.menuCharset.addSeparator();
    this.menuCharset.add(this.itemCharsetANSI);
    this.menuCharset.add(this.itemCharsetUTF8);
    this.menuCharset.add(this.itemCharsetUTF8_NO_BOM);
    this.menuCharset.add(this.itemCharsetULE);
    this.menuCharset.add(this.itemCharsetUBE);
    this.menuStyle.add(this.itemFont);
    this.menuStyle.add(this.itemTextDrag);
    this.menuStyle.add(this.itemAutoIndent);
    this.menuBar.add(this.menuView);
    this.menuView.add(this.itemStateBar);
    this.menuView.add(this.itemAlwaysOnTop);
    this.menuView.add(this.itemResizable);
    this.menuView.addSeparator();
    this.menuView.add(this.menuFontSize);
    this.menuFontSize.add(this.itemFontSizePlus);
    this.menuFontSize.add(this.itemFontSizeMinus);
    this.menuFontSize.add(this.itemFontSizeReset);
    this.menuView.add(this.menuColor);
    this.menuColor.add(this.itemColorFont);
    this.menuColor.add(this.itemColorBack);
    this.menuColor.add(this.itemColorCaret);
    this.menuColor.add(this.itemColorSelFont);
    this.menuColor.add(this.itemColorSelBack);
    this.menuColor.addSeparator();
    this.menuColor.add(this.itemColorAnti);
    this.menuColor.add(this.itemColorComplementary);
    this.menuView.add(this.menuColorStyle);
    this.menuColorStyle.add(this.itemColorStyle1);
    this.menuColorStyle.add(this.itemColorStyle2);
    this.menuColorStyle.add(this.itemColorStyle3);
    this.menuColorStyle.add(this.itemColorStyle4);
    this.menuColorStyle.add(this.itemColorStyle5);
    this.menuColorStyle.addSeparator();
    this.menuColorStyle.add(this.itemColorStyleDefault);
    this.menuView.add(this.menuHighlight);
    this.menuHighlight.add(this.itemHighlight1);
    this.menuHighlight.add(this.itemHighlight2);
    this.menuHighlight.add(this.itemHighlight3);
    this.menuHighlight.add(this.itemHighlight4);
    this.menuHighlight.add(this.itemHighlight5);
    this.menuView.add(this.menuRmHighlight);
    this.menuRmHighlight.add(this.itemRmHighlight1);
    this.menuRmHighlight.add(this.itemRmHighlight2);
    this.menuRmHighlight.add(this.itemRmHighlight3);
    this.menuRmHighlight.add(this.itemRmHighlight4);
    this.menuRmHighlight.add(this.itemRmHighlight5);
    this.menuRmHighlight.addSeparator();
    this.menuRmHighlight.add(this.itemRmHighlightAll);
    this.menuView.add(this.itemTabSet);
    this.menuBar.add(this.menuHelp);
    this.menuHelp.add(this.itemHelp);
    this.menuHelp.addSeparator();
    this.menuHelp.add(this.itemAbout);
    this.bgpLineWrapStyle.add(this.itemLineWrapByWord);
    this.bgpLineWrapStyle.add(this.itemLineWrapByChar);
    this.bgpLineStyle.add(this.itemLineStyleWin);
    this.bgpLineStyle.add(this.itemLineStyleUnix);
    this.bgpLineStyle.add(this.itemLineStyleMac);
    this.bgpCharset.add(this.itemCharsetBASE);
    this.bgpCharset.add(this.itemCharsetANSI);
    this.bgpCharset.add(this.itemCharsetUTF8);
    this.bgpCharset.add(this.itemCharsetUTF8_NO_BOM);
    this.bgpCharset.add(this.itemCharsetULE);
    this.bgpCharset.add(this.itemCharsetUBE);
  }

  /**
   * ��ʼ����ݲ˵�
   */
  private void addPopMenu() {
    this.popMenu.add(this.itemPopUnDo);
    this.popMenu.add(this.itemPopReDo);
    this.popMenu.addSeparator();
    this.popMenu.add(this.itemPopCut);
    this.popMenu.add(this.itemPopCopy);
    this.popMenu.add(this.itemPopPaste);
    this.popMenu.add(this.itemPopDel);
    this.popMenu.add(this.itemPopSelAll);
    this.popMenu.addSeparator();
    this.popMenu.add(this.menuPopHighlight);
    this.menuPopHighlight.add(itemPopHighlight1);
    this.menuPopHighlight.add(itemPopHighlight2);
    this.menuPopHighlight.add(itemPopHighlight3);
    this.menuPopHighlight.add(itemPopHighlight4);
    this.menuPopHighlight.add(itemPopHighlight5);
    this.popMenu.add(this.menuPopRmHighlight);
    this.menuPopRmHighlight.add(itemPopRmHighlight1);
    this.menuPopRmHighlight.add(itemPopRmHighlight2);
    this.menuPopRmHighlight.add(itemPopRmHighlight3);
    this.menuPopRmHighlight.add(itemPopRmHighlight4);
    this.menuPopRmHighlight.add(itemPopRmHighlight5);
    this.menuPopRmHighlight.addSeparator();
    this.menuPopRmHighlight.add(itemPopRmHighlightAll);
    Dimension popSize = this.popMenu.getPreferredSize();
    popSize.width += popSize.width / 5; // Ϊ�����ۣ��ʵ��ӿ��˵�����ʾ
    this.popMenu.setPopupSize(popSize);
  }

  /**
   * ���ø��˵���ĳ�ʼ״̬�����Ƿ����
   */
  private void setMenuDefault() {
    this.menuSort.setEnabled(false);
    this.itemReOpen.setEnabled(false);
    this.itemReName.setEnabled(false);
    this.itemDelFile.setEnabled(false);
    this.itemUnDo.setEnabled(false);
    this.itemReDo.setEnabled(false);
    this.itemCut.setEnabled(false);
    this.itemCopy.setEnabled(false);
    this.itemDel.setEnabled(false);
    this.menuCase.setEnabled(false);
    this.itemFind.setEnabled(false);
    this.itemFindNext.setEnabled(false);
    this.itemFindPrevious.setEnabled(false);
    this.menuQuickFind.setEnabled(false);
    this.itemSelCopy.setEnabled(false);
    this.itemReplace.setEnabled(false);
    this.itemGoto.setEnabled(false);
    this.itemToClipFileName.setEnabled(false);
    this.itemToClipFilePath.setEnabled(false);
    this.itemToClipDirPath.setEnabled(false);
    this.itemTrimSelected.setEnabled(false);
    this.itemDelNullLineSelected.setEnabled(false);
    this.itemPopCopy.setEnabled(false);
    this.itemPopCut.setEnabled(false);
    this.itemPopDel.setEnabled(false);
    this.itemPopUnDo.setEnabled(false);
    this.itemPopReDo.setEnabled(false);
    this.setFileHistoryMenuEnabled();
  }

  /**
   * �����ʼ��ʱ�������йز˵��ĳ�ʼ״̬�빦��
   */
  private void setMenuDefaultInit() {
    this.itemLineWrap.setSelected(true);
    this.itemLineWrapByWord.setSelected(true);
    this.itemTextDrag.setSelected(false);
    this.itemAutoIndent.setSelected(false);
    this.itemStateBar.setSelected(true);
    this.itemAlwaysOnTop.setSelected(false);
    this.itemResizable.setSelected(false);
    this.setLineWarp();
    this.setLineWrapStyle(true);
    this.setTextDrag();
    this.setStateBar();
    this.setAlwaysOnTop();
    this.setResizable();
    this.setLineStyleString(LineSeparator.DEFAULT, true);
    this.setCharEncoding(CharEncoding.BASE, true);
  }

  /**
   * Ϊ�ļ�ѡ���������ļ�������
   */
  private void addFileFilter() {
    BaseFileFilter txtFileFilter = new BaseFileFilter(Util.TXT_EXT, "�ı��ĵ�(*"
        + Util.TXT_EXT + ")");
    this.fcrOpen.addChoosableFileFilter(txtFileFilter);
    this.fcrSave.addChoosableFileFilter(txtFileFilter);
  }

  /**
   * ����"����༭"�˵��Ƿ����
   */
  private void setFileHistoryMenuEnabled() {
    if (this.menuFileHistory.getItemCount() == 0) {
      this.menuFileHistory.setEnabled(false);
      this.itemClearFileHistory.setEnabled(false);
    } else {
      this.menuFileHistory.setEnabled(true);
      this.itemClearFileHistory.setEnabled(true);
    }
  }

  /**
   * �����ı����е��ַ��Ƿ�Ϊ�գ�������ز˵���״̬
   * 
   * @param isExist
   *          �ı������Ƿ����ַ�
   */
  private void setMenuStateByTextArea(boolean isExist) {
    this.menuSort.setEnabled(isExist);
    this.itemFind.setEnabled(isExist);
    this.itemFindNext.setEnabled(isExist);
    this.itemFindPrevious.setEnabled(isExist);
    this.itemReplace.setEnabled(isExist);
    this.itemGoto.setEnabled(isExist);
  }

  /**
   * �����ı�����ѡ����ַ����Ƿ�Ϊ�գ�������ز˵���״̬
   * 
   * @param isNull
   *          ѡ���Ƿ�Ϊ��
   */
  private void setMenuStateBySelectedText(boolean isNull) {
    this.itemCopy.setEnabled(isNull);
    this.itemCut.setEnabled(isNull);
    this.itemDel.setEnabled(isNull);
    this.menuCase.setEnabled(isNull);
    this.menuQuickFind.setEnabled(isNull);
    this.itemSelCopy.setEnabled(isNull);
    this.itemPopCopy.setEnabled(isNull);
    this.itemPopCut.setEnabled(isNull);
    this.itemPopDel.setEnabled(isNull);
    this.itemTrimSelected.setEnabled(isNull);
    this.itemDelNullLineSelected.setEnabled(isNull);
  }

  /**
   * ���ó����������˵����״̬
   */
  private void setMenuStateUndoRedo() {
    boolean canRedo = this.undoManager.canRedo();
    boolean canUndo = this.undoManager.canUndo();
    this.itemReDo.setEnabled(canRedo);
    this.itemUnDo.setEnabled(canUndo);
    this.itemPopUnDo.setEnabled(canUndo);
    this.itemPopReDo.setEnabled(canRedo);
  }

  /**
   * Ϊ���˵����������Ƿ��Ϳ�ݼ�
   */
  private void setMenuMnemonic() {
    this.menuFile.setMnemonic('F');
    this.menuHelp.setMnemonic('H');
    this.menuEdit.setMnemonic('E');
    this.menuSearch.setMnemonic('S');
    this.menuStyle.setMnemonic('O');
    this.menuView.setMnemonic('V');
    this.menuLineWrapSet.setMnemonic('L');
    this.menuLineStyle.setMnemonic('S');
    this.itemLineStyleWin.setMnemonic('W');
    this.itemLineStyleUnix.setMnemonic('U');
    this.itemLineStyleMac.setMnemonic('M');
    this.menuCharset.setMnemonic('C');
    this.itemCharsetANSI.setMnemonic('A');
    this.itemCharsetUTF8.setMnemonic('U');
    this.itemCharsetUTF8_NO_BOM.setMnemonic('N');
    this.itemCharsetULE.setMnemonic('L');
    this.itemCharsetUBE.setMnemonic('B');
    this.menuInsert.setMnemonic('I');
    this.itemLineWrap.setMnemonic('W');
    this.menuLineWrapStyle.setMnemonic('S');
    this.itemLineWrapByWord.setMnemonic('W');
    this.itemLineWrapByChar.setMnemonic('C');
    this.itemTextDrag.setMnemonic('D');
    this.itemAutoIndent.setMnemonic('I');
    this.itemStateBar.setMnemonic('S');
    this.itemAlwaysOnTop.setMnemonic('A');
    this.itemResizable.setMnemonic('R');
    this.menuColor.setMnemonic('C');
    this.menuFontSize.setMnemonic('F');
    this.menuColorStyle.setMnemonic('Y');
    this.menuHighlight.setMnemonic('H');
    this.menuRmHighlight.setMnemonic('M');
    this.menuPopHighlight.setMnemonic('H');
    this.menuPopRmHighlight.setMnemonic('M');
    this.itemNew.setAccelerator(KeyStroke.getKeyStroke('N',
        InputEvent.CTRL_DOWN_MASK));
    this.itemOpen.setAccelerator(KeyStroke.getKeyStroke('O',
        InputEvent.CTRL_DOWN_MASK));
    this.itemSave.setAccelerator(KeyStroke.getKeyStroke('S',
        InputEvent.CTRL_DOWN_MASK));
    this.itemExit.setAccelerator(KeyStroke.getKeyStroke('Q',
        InputEvent.CTRL_DOWN_MASK));
    this.itemAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0)); // ��ݼ���F1
    this.itemUnDo.setAccelerator(KeyStroke.getKeyStroke('Z',
        InputEvent.CTRL_DOWN_MASK));
    this.itemReDo.setAccelerator(KeyStroke.getKeyStroke('Y',
        InputEvent.CTRL_DOWN_MASK));
    this.itemCut.setAccelerator(KeyStroke.getKeyStroke('X',
        InputEvent.CTRL_DOWN_MASK));
    this.itemCopy.setAccelerator(KeyStroke.getKeyStroke('C',
        InputEvent.CTRL_DOWN_MASK));
    this.itemPaste.setAccelerator(KeyStroke.getKeyStroke('V',
        InputEvent.CTRL_DOWN_MASK));
    this.itemDel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)); // ��ݼ���Delete
    this.itemCaseUp.setAccelerator(KeyStroke.getKeyStroke('U',
        InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
    this.itemCaseLow.setAccelerator(KeyStroke.getKeyStroke('U',
        InputEvent.CTRL_DOWN_MASK));
    this.itemFind.setAccelerator(KeyStroke.getKeyStroke('F',
        InputEvent.CTRL_DOWN_MASK));
    this.itemFindNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0)); // ��ݼ���F3
    this.itemFindPrevious.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,
        InputEvent.SHIFT_DOWN_MASK)); // ��ݼ���Shift+F3
    this.itemQuickFindDown.setAccelerator(KeyStroke.getKeyStroke('K',
        InputEvent.CTRL_DOWN_MASK));
    this.itemQuickFindUp.setAccelerator(KeyStroke.getKeyStroke('K',
        InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
    this.itemReplace.setAccelerator(KeyStroke.getKeyStroke('H',
        InputEvent.CTRL_DOWN_MASK));
    this.itemGoto.setAccelerator(KeyStroke.getKeyStroke('G',
        InputEvent.CTRL_DOWN_MASK));
    this.itemToClipCurLine.setAccelerator(KeyStroke.getKeyStroke('C',
        InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
    this.itemToClipAllText.setAccelerator(KeyStroke.getKeyStroke('A',
        InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
    this.itemLineCopy.setAccelerator(KeyStroke.getKeyStroke('D',
        InputEvent.CTRL_DOWN_MASK));
    this.itemLineDel.setAccelerator(KeyStroke.getKeyStroke('D',
        InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
    this.itemSortUp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP,
        InputEvent.ALT_DOWN_MASK)); // ��ݼ���Alt+���Ϸ����
    this.itemSortDown.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,
        InputEvent.ALT_DOWN_MASK)); // ��ݼ���Alt+���·����
    this.itemSelCopy.setAccelerator(KeyStroke.getKeyStroke('R',
        InputEvent.CTRL_DOWN_MASK));
    this.itemTrimStart.setAccelerator(KeyStroke.getKeyStroke('S',
        InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
    this.itemTrimEnd.setAccelerator(KeyStroke.getKeyStroke('E',
        InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
    this.itemTrimAll.setAccelerator(KeyStroke.getKeyStroke('L',
        InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
    this.itemTrimSelected.setAccelerator(KeyStroke.getKeyStroke('T',
        InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
    this.itemDelNullLineAll.setAccelerator(KeyStroke.getKeyStroke('A',
        InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK));
    this.itemDelNullLineSelected.setAccelerator(KeyStroke.getKeyStroke('S',
        InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK));
    this.itemSelAll.setAccelerator(KeyStroke.getKeyStroke('A',
        InputEvent.CTRL_DOWN_MASK));
    this.itemInsertDateTime.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_F5, 0)); // ��ݼ���F5
    this.itemFontSizePlus.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP,
        InputEvent.CTRL_DOWN_MASK)); // ��ݼ���Ctrl+���Ϸ����
    this.itemFontSizeMinus.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_DOWN, InputEvent.CTRL_DOWN_MASK)); // ��ݼ���Ctrl+���·����
    this.itemFontSizeReset.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_SLASH, InputEvent.CTRL_DOWN_MASK)); // ��ݼ���Ctrl+'/'��
    this.itemLineDelToStart
        .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,
            InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK)); // ��ݼ���Ctrl+Alt+�������
    this.itemLineDelToEnd.setAccelerator(KeyStroke
        .getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.CTRL_DOWN_MASK
            + InputEvent.ALT_DOWN_MASK)); // ��ݼ���Ctrl+Alt+���ҷ����
    this.itemLineToUp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP,
        InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK)); // ��ݼ���Ctrl+Shift+���Ϸ����
    this.itemLineToDown.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,
        InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK)); // ��ݼ���Ctrl+Shift+���·����
  }

  /**
   * Ϊ���˵��������¼��Ĵ�������
   */
  public void actionPerformed(ActionEvent e) {
    if (this.itemAbout.equals(e.getSource())) {
      this.showAbout();
    } else if (this.itemCopy.equals(e.getSource())) {
      this.copyText();
    } else if (this.itemCut.equals(e.getSource())) {
      this.cutText();
    } else if (this.itemInsertChar.equals(e.getSource())) {
      this.openInsertCharDialog();
    } else if (this.itemInsertDateTime.equals(e.getSource())) {
      this.openInsertDateDialog();
    } else if (this.itemDel.equals(e.getSource())) {
      this.deleteText();
    } else if (this.itemCaseUp.equals(e.getSource())) {
      this.switchCase(true);
    } else if (this.itemCaseLow.equals(e.getSource())) {
      this.switchCase(false);
    } else if (this.itemExit.equals(e.getSource())) {
      this.exit();
    } else if (this.itemFind.equals(e.getSource())) {
      this.openFindDialog();
    } else if (this.itemFindNext.equals(e.getSource())) {
      this.findNextText(true);
    } else if (this.itemFindPrevious.equals(e.getSource())) {
      this.findNextText(false);
    } else if (this.itemQuickFindDown.equals(e.getSource())) {
      this.quickFindText(true);
    } else if (this.itemQuickFindUp.equals(e.getSource())) {
      this.quickFindText(false);
    } else if (this.itemFont.equals(e.getSource())) {
      this.openFontChooser();
    } else if (this.itemGoto.equals(e.getSource())) {
      this.openGotoDialog();
    } else if (this.itemToClipFileName.equals(e.getSource())) {
      this.toClipFileName();
    } else if (this.itemToClipFilePath.equals(e.getSource())) {
      this.toClipFilePath();
    } else if (this.itemToClipDirPath.equals(e.getSource())) {
      this.toClipDirPath();
    } else if (this.itemToClipCurLine.equals(e.getSource())) {
      this.toClipCurLine();
    } else if (this.itemToClipAllText.equals(e.getSource())) {
      this.toClipAllText();
    } else if (this.itemLineCopy.equals(e.getSource())) {
      this.copyLines();
    } else if (this.itemLineDel.equals(e.getSource())) {
      this.deleteLines();
    } else if (this.itemLineDelToStart.equals(e.getSource())) {
      this.deleteLineToStart();
    } else if (this.itemLineDelToEnd.equals(e.getSource())) {
      this.deleteLineToEnd();
    } else if (this.itemLineToUp.equals(e.getSource())) {
      this.moveLineToUp();
    } else if (this.itemLineToDown.equals(e.getSource())) {
      this.moveLineToDown();
    } else if (this.itemSortUp.equals(e.getSource())) {
      this.sortLines(true);
    } else if (this.itemSortDown.equals(e.getSource())) {
      this.sortLines(false);
    } else if (this.itemSelCopy.equals(e.getSource())) {
      this.copySelectedText();
    } else if (this.itemTrimStart.equals(e.getSource())) {
      this.trimLines(0);
    } else if (this.itemTrimEnd.equals(e.getSource())) {
      this.trimLines(1);
    } else if (this.itemTrimAll.equals(e.getSource())) {
      this.trimLines(2);
    } else if (this.itemTrimSelected.equals(e.getSource())) {
      this.trimSelected();
    } else if (this.itemDelNullLineAll.equals(e.getSource())) {
      this.delAllNullLines();
    } else if (this.itemDelNullLineSelected.equals(e.getSource())) {
      this.delSelectedNullLines();
    } else if (this.itemHelp.equals(e.getSource())) {

    } else if (this.itemLineWrap.equals(e.getSource())) {
      this.setLineWarp();
    } else if (this.itemLineWrapByWord.equals(e.getSource())) {
      this.setLineWrapStyle(true);
    } else if (this.itemLineWrapByChar.equals(e.getSource())) {
      this.setLineWrapStyle(false);
    } else if (this.itemLineStyleWin.equals(e.getSource())) {
      this.setLineStyleString(LineSeparator.WINDOWS, false);
    } else if (this.itemLineStyleUnix.equals(e.getSource())) {
      this.setLineStyleString(LineSeparator.UNIX, false);
    } else if (this.itemLineStyleMac.equals(e.getSource())) {
      this.setLineStyleString(LineSeparator.MACINTOSH, false);
    } else if (this.itemCharsetBASE.equals(e.getSource())) {
      this.setCharEncoding(CharEncoding.BASE, false);
    } else if (this.itemCharsetANSI.equals(e.getSource())) {
      this.setCharEncoding(CharEncoding.ANSI, false);
    } else if (this.itemCharsetUTF8.equals(e.getSource())) {
      this.setCharEncoding(CharEncoding.UTF8, false);
    } else if (this.itemCharsetUTF8_NO_BOM.equals(e.getSource())) {
      this.setCharEncoding(CharEncoding.UTF8_NO_BOM, false);
    } else if (this.itemCharsetULE.equals(e.getSource())) {
      this.setCharEncoding(CharEncoding.ULE, false);
    } else if (this.itemCharsetUBE.equals(e.getSource())) {
      this.setCharEncoding(CharEncoding.UBE, false);
    } else if (this.itemTextDrag.equals(e.getSource())) {
      this.setTextDrag();
    } else if (this.itemAutoIndent.equals(e.getSource())) {
      this.setAutoIndent();
    } else if (this.itemNew.equals(e.getSource())) {
      this.createNew(true);
    } else if (this.itemOpen.equals(e.getSource())) {
      this.openFile();
    } else if (this.itemOpenByEncoding.equals(e.getSource())) {
      this.openFileByEncoding();
    } else if (this.itemReOpen.equals(e.getSource())) {
      this.reOpenFile();
    } else if (this.itemReName.equals(e.getSource())) {
      this.reNameFile();
    } else if (this.itemPaste.equals(e.getSource())) {
      this.pasteText();
    } else if (this.itemPopCopy.equals(e.getSource())) {
      this.copyText();
    } else if (this.itemPopCut.equals(e.getSource())) {
      this.cutText();
    } else if (this.itemPopDel.equals(e.getSource())) {
      this.deleteText();
    } else if (this.itemPopPaste.equals(e.getSource())) {
      this.pasteText();
    } else if (this.itemPopSelAll.equals(e.getSource())) {
      this.selectAll();
    } else if (this.itemPopUnDo.equals(e.getSource())) {
      this.undoAction();
    } else if (this.itemPopReDo.equals(e.getSource())) {
      this.redoAction();
    } else if (this.itemReDo.equals(e.getSource())) {
      this.redoAction();
    } else if (this.itemReplace.equals(e.getSource())) {
      this.openReplaceDialog();
    } else if (this.itemSave.equals(e.getSource())) {
      this.saveFile(false);
    } else if (this.itemSaveAs.equals(e.getSource())) {
      this.saveAsFile();
    } else if (this.itemDelFile.equals(e.getSource())) {
      this.deleteFile();
    } else if (this.itemSelAll.equals(e.getSource())) {
      this.selectAll();
    } else if (this.itemStateBar.equals(e.getSource())) {
      this.setStateBar();
    } else if (this.itemAlwaysOnTop.equals(e.getSource())) {
      this.setAlwaysOnTop();
    } else if (this.itemResizable.equals(e.getSource())) {
      this.setResizable();
    } else if (this.itemFontSizePlus.equals(e.getSource())) {
      this.setFontSizePlus();
    } else if (this.itemFontSizeMinus.equals(e.getSource())) {
      this.setFontSizeMinus();
    } else if (this.itemFontSizeReset.equals(e.getSource())) {
      this.setFontSizeReset();
    } else if (this.itemColorFont.equals(e.getSource())) {
      this.setFontColor();
    } else if (this.itemColorBack.equals(e.getSource())) {
      this.setBackColor();
    } else if (this.itemColorCaret.equals(e.getSource())) {
      this.setCaretColor();
    } else if (this.itemColorSelFont.equals(e.getSource())) {
      this.setSelFontColor();
    } else if (this.itemColorSelBack.equals(e.getSource())) {
      this.setSelBackColor();
    } else if (this.itemColorAnti.equals(e.getSource())) {
      this.setColorTransform(true);
    } else if (this.itemColorComplementary.equals(e.getSource())) {
      this.setColorTransform(false);
    } else if (this.itemColorStyle1.equals(e.getSource())) {
      this.setColorStyle(1);
    } else if (this.itemColorStyle2.equals(e.getSource())) {
      this.setColorStyle(2);
    } else if (this.itemColorStyle3.equals(e.getSource())) {
      this.setColorStyle(3);
    } else if (this.itemColorStyle4.equals(e.getSource())) {
      this.setColorStyle(4);
    } else if (this.itemColorStyle5.equals(e.getSource())) {
      this.setColorStyle(5);
    } else if (this.itemColorStyleDefault.equals(e.getSource())) {
      this.setColorStyle(0);
    } else if (this.itemHighlight1.equals(e.getSource())) {
      this.setHighlight(1);
    } else if (this.itemHighlight2.equals(e.getSource())) {
      this.setHighlight(2);
    } else if (this.itemHighlight3.equals(e.getSource())) {
      this.setHighlight(3);
    } else if (this.itemHighlight4.equals(e.getSource())) {
      this.setHighlight(4);
    } else if (this.itemHighlight5.equals(e.getSource())) {
      this.setHighlight(5);
    } else if (this.itemRmHighlight1.equals(e.getSource())) {
      this.rmHighlight(1);
    } else if (this.itemRmHighlight2.equals(e.getSource())) {
      this.rmHighlight(2);
    } else if (this.itemRmHighlight3.equals(e.getSource())) {
      this.rmHighlight(3);
    } else if (this.itemRmHighlight4.equals(e.getSource())) {
      this.rmHighlight(4);
    } else if (this.itemRmHighlight5.equals(e.getSource())) {
      this.rmHighlight(5);
    } else if (this.itemRmHighlightAll.equals(e.getSource())) {
      this.rmHighlight(0);
    } else if (this.itemPopHighlight1.equals(e.getSource())) {
      this.setHighlight(1);
    } else if (this.itemPopHighlight2.equals(e.getSource())) {
      this.setHighlight(2);
    } else if (this.itemPopHighlight3.equals(e.getSource())) {
      this.setHighlight(3);
    } else if (this.itemPopHighlight4.equals(e.getSource())) {
      this.setHighlight(4);
    } else if (this.itemPopHighlight5.equals(e.getSource())) {
      this.setHighlight(5);
    } else if (this.itemPopRmHighlight1.equals(e.getSource())) {
      this.rmHighlight(1);
    } else if (this.itemPopRmHighlight2.equals(e.getSource())) {
      this.rmHighlight(2);
    } else if (this.itemPopRmHighlight3.equals(e.getSource())) {
      this.rmHighlight(3);
    } else if (this.itemPopRmHighlight4.equals(e.getSource())) {
      this.rmHighlight(4);
    } else if (this.itemPopRmHighlight5.equals(e.getSource())) {
      this.rmHighlight(5);
    } else if (this.itemPopRmHighlightAll.equals(e.getSource())) {
      this.rmHighlight(0);
    } else if (this.itemTabSet.equals(e.getSource())) {
      this.openTabSetDialog();
    } else if (this.itemUnDo.equals(e.getSource())) {
      this.undoAction();
    } else if (this.itemClearFileHistory.equals(e.getSource())) {
      this.clearFileHistory();
    } else if (Util.FILE_HISTORY.equals(e.getActionCommand())) { // ����༭���ļ��˵�
      JMenuItem itemFile = (JMenuItem) e.getSource();
      this.openFileHistory(itemFile.getText());
    }
  }

  /**
   * "������ʾ"�и���ʽ�Ĵ�������
   * 
   * @param style
   *          ����ĳ����ɫ���и�����ʾ
   */
  private void setHighlight(int style) {
    String strSelText = this.txaMain.getSelectedText();
    if (strSelText == null || strSelText.isEmpty()) {
      return;
    }
    String strText = this.txaMain.getText();
    LinkedList<Integer> linkedList = new LinkedList<Integer>();
    int index = -1;
    do {
      int start = 0;
      if (index >= 0) {
        start += index + strSelText.length();
      }
      index = strText.indexOf(strSelText, start);
      if (index >= 0) {
        linkedList.add(index);
      }
    } while (index >= 0);
    Color color = null;
    switch (style) {
    case 1:
      color = Util.COLOR_HIGHLIGHT_1;
      break;
    case 2:
      color = Util.COLOR_HIGHLIGHT_2;
      break;
    case 3:
      color = Util.COLOR_HIGHLIGHT_3;
      break;
    case 4:
      color = Util.COLOR_HIGHLIGHT_4;
      break;
    case 5:
      color = Util.COLOR_HIGHLIGHT_5;
      break;
    }
    for (Integer startIndex : linkedList) {
      try {
        this.highlighter.addHighlight(startIndex, startIndex
            + strSelText.length(),
            new DefaultHighlighter.DefaultHighlightPainter(color));
        Highlighter.Highlight[] arrHighlight = highlighter.getHighlights();
        this.highlighterList.add(new PartnerBean(
            arrHighlight[arrHighlight.length - 1], style));
      } catch (BadLocationException x) {
        x.printStackTrace();
      }
    }
  }

  /**
   * "�������"�и���ʽ�Ĵ�������
   * 
   * @param style
   *          ���ĳ����ɫ�ĸ�����ʾ
   */
  private void rmHighlight(int style) {
    if (style == 0) {
      this.highlighter.removeAllHighlights();
      this.highlighterList.clear();
      return;
    }
    for (int n = 0; n < this.highlighterList.size(); n++) {
      PartnerBean partnerBean = this.highlighterList.get(n);
      if (partnerBean.getIndex() == style) {
        this.highlighter.removeHighlight((Highlighter.Highlight) partnerBean
            .getObject());
        this.highlighterList.remove(n);
        n--;
      }
    }
  }

  /**
   * "��ɫ����"��"ȫ����ɫ/ȫ����ɫ"�Ĵ�������
   * 
   * @param mode
   *          ���ڱ�ʶ��ɫ/��ɫ�����Ϊtrue��ʾ��ɫ����֮��Ϊ��ɫ
   */
  private void setColorTransform(boolean mode) {
    Color colorFont = this.getConvertColor(this.txaMain.getForeground(), mode);
    Color colorBack = this.getConvertColor(this.txaMain.getBackground(), mode);
    Color colorCaret = this.getConvertColor(this.txaMain.getCaretColor(), mode);
    Color colorSelFont = this.getConvertColor(this.txaMain
        .getSelectedTextColor(), mode);
    Color colorSelBack = this.getConvertColor(this.txaMain.getSelectionColor(),
        mode);
    this.txaMain.setForeground(colorFont);
    this.txaMain.setBackground(colorBack);
    this.txaMain.setCaretColor(colorCaret);
    this.txaMain.setSelectedTextColor(colorSelFont);
    this.txaMain.setSelectionColor(colorSelBack);
  }

  /**
   * ��ָ����rgbģʽ��ɫת��Ϊ��ɫ/��ɫ
   * 
   * @param color
   *          ��������rgbģʽ��ɫ
   * @param mode
   *          ���ڱ�ʶ��ɫ/��ɫ�����Ϊtrue��ʾ��ɫ����֮��Ϊ��ɫ
   * @return ���������ɫ
   */
  private Color getConvertColor(Color color, boolean mode) {
    if (color != null) {
      int red = color.getRed();
      int green = color.getGreen();
      int blue = color.getBlue();
      if (mode) { // ��ɫ
        red = 255 - red;
        green = 255 - green;
        blue = 255 - blue;
      } else { // ��ɫ
        int min = red;
        int max = red;
        if (min > green) {
          min = green;
        }
        if (min > blue) {
          min = blue;
        }
        if (max < green) {
          max = green;
        }
        if (max < blue) {
          max = blue;
        }
        int total = max + min;
        red = total - red;
        green = total - green;
        blue = total - blue;
      }
      color = new Color(red, green, blue);
    }
    return color;
  }

  /**
   * ����"��ɫ����"�Ĵ�������
   * 
   * @param style
   *          ��ɫ������ţ���1��2��3��4��5�Լ�Ĭ����ɫ��0���ȹ�6��
   */
  private void setColorStyle(int style) {
    Color colorFont = null;
    Color colorBack = null;
    Color colorCaret = null;
    Color colorSelFont = null;
    Color colorSelBack = null;
    switch (style) {
    case 1:
      colorFont = new Color(211, 215, 207);
      colorBack = new Color(46, 52, 54);
      colorCaret = new Color(211, 215, 207);
      colorSelFont = new Color(238, 238, 236);
      colorSelBack = new Color(136, 138, 133);
      break;
    case 2:
      colorFont = new Color(240, 240, 240);
      colorBack = new Color(0, 128, 128);
      colorCaret = new Color(240, 240, 240);
      colorSelFont = new Color(22, 99, 88);
      colorSelBack = new Color(240, 240, 240);
      break;
    case 3:
      colorFont = new Color(46, 52, 54);
      colorBack = new Color(255, 251, 240);
      colorCaret = new Color(46, 52, 54);
      colorSelFont = new Color(255, 251, 240);
      colorSelBack = new Color(46, 52, 54);
      break;
    case 4:
      colorFont = new Color(51, 53, 49);
      colorBack = new Color(204, 232, 207);
      colorCaret = new Color(51, 53, 49);
      colorSelFont = new Color(204, 232, 207);
      colorSelBack = new Color(51, 53, 49);
      break;
    case 5:
      colorFont = new Color(58, 57, 53);
      colorBack = new Color(221, 212, 195);
      colorCaret = new Color(58, 57, 53);
      colorSelFont = new Color(221, 212, 195);
      colorSelBack = new Color(58, 57, 53);
      break;
    case 0:
    default:
      colorFont = this.colorFont;
      colorBack = this.colorBack;
      colorCaret = this.colorCaret;
      colorSelFont = this.colorSelFont;
      colorSelBack = this.colorSelBack;
      break;
    }
    this.txaMain.setForeground(colorFont);
    this.txaMain.setBackground(colorBack);
    this.txaMain.setCaretColor(colorCaret);
    this.txaMain.setSelectedTextColor(colorSelFont);
    this.txaMain.setSelectionColor(colorSelBack);
  }

  /**
   * "����"�Ĵ�������
   * 
   * @param order
   *          �����˳������Ϊtrue������Ϊfalse
   */
  private void sortLines(boolean order) {
    if (this.txaMain.getText().isEmpty()) {
      return;
    }
    CurrentLines currentLines = new CurrentLines(this.txaMain);
    int lineCount = currentLines.getLineCount();
    if (lineCount < 2) {
      this.txaMain.selectAll();
    } else {
      this.txaMain.select(currentLines.getStartIndex(), currentLines
          .getEndIndex());
    }
    String strSelText = this.txaMain.getSelectedText();
    String[] arrText = strSelText.split("\n", -1); // ����ǰѡ�����ı����д���������ĩβ�Ķദ����
    if (arrText.length <= 1) {
      return;
    }
    for (int i = 0; i < arrText.length; i++) { // ð������
      for (int j = 0; j < i; j++) {
        if (arrText[i].compareTo(arrText[j]) < 0) {
          String str = arrText[i];
          arrText[i] = arrText[j];
          arrText[j] = str;
        }
      }
    }
    StringBuilder stbSorted = new StringBuilder();
    if (order) { // ����
      for (String str : arrText) {
        stbSorted.append(str + "\n");
      }
    } else { // ����
      for (String str : arrText) {
        stbSorted.insert(0, str + "\n");
      }
    }
    this.txaMain.replaceSelection(stbSorted
        .deleteCharAt(stbSorted.length() - 1).toString()); // ɾ���ַ���ĩβ����Ļ��з�
  }

  /**
   * "�����Կո����Tab��"�Ĵ�������
   */
  private void setTabReplace() {
    if (this.isReplaceBySpace) {
      this.txaMain.addKeyListener(this.tabReplaceKeyAdapter);
    } else {
      this.txaMain.removeKeyListener(this.tabReplaceKeyAdapter);
    }
  }

  /**
   * ��Tab��ʱ�����滻Ϊ�����Ŀո�
   */
  private void toTabReplace() {
    int currentIndex = this.txaMain.getCaretPosition();
    if (this.txaMain.getText().charAt(currentIndex - 1) != '\t') { // �ı����������ǰһ���ַ��Ƿ�ΪTab
      return;
    }
    int tabSize = this.txaMain.getTabSize();
    String strSpace = "";
    for (int i = 0; i < tabSize; i++) {
      strSpace += " ";
    }
    this.txaMain.replaceRange(strSpace, currentIndex - 1, currentIndex);
  }

  /**
   * "�Զ�����"�Ĵ�������
   */
  private void setAutoIndent() {
    if (this.itemAutoIndent.isSelected()) {
      this.txaMain.addKeyListener(this.autoIndentKeyAdapter);
    } else {
      this.txaMain.removeKeyListener(this.autoIndentKeyAdapter);
    }
  }

  /**
   * ���س���ʱ��������
   */
  private void toAutoIndent() {
    CurrentLines currentLines = new CurrentLines(this.txaMain,
        CurrentLines.LineExtend.EXTEND_UP);
    String strContentExtend = currentLines.getStrContentExtend();
    if (strContentExtend == null) {
      return;
    }
    int currentIndex = currentLines.getCurrentIndex();
    if (this.txaMain.getText().charAt(currentIndex - 1) != '\n') { // ������ǻ��в���������������
      return;
    }
    String strSpace = "";
    for (int i = 0; i < strContentExtend.length(); i++) {
      switch (strContentExtend.charAt(i)) {
      case ' ':
      case '\t':
      case '��':
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
      this.txaMain.insert(strSpace, this.txaMain.getCaretPosition());
    }
  }

  /**
   * "���Ƶ�ǰ��"�Ĵ�������
   */
  private void moveLineToUp() {
    CurrentLines currentLines = new CurrentLines(this.txaMain,
        CurrentLines.LineExtend.EXTEND_UP);
    String strContentExtend = currentLines.getStrContentExtend();
    if (strContentExtend == null) {
      return;
    }
    int startIndex = currentLines.getStartIndex();
    int endIndex = currentLines.getEndIndex();
    int endLineNum = currentLines.getEndLineNum();
    String strContentCurrent = currentLines.getStrContentCurrent();
    if (endLineNum == this.txaMain.getLineCount() - 1) {
      // �����ǰʵ��ѡ����ĩ��Ϊ�ı���ĩ�У�Ӧ��һ������
      strContentCurrent += "\n";
      strContentExtend = strContentExtend.substring(0, strContentExtend
          .length() - 1);
    }
    String strMoved = strContentCurrent + strContentExtend;
    this.txaMain.replaceRange(strMoved, startIndex, endIndex);
    this.txaMain
        .select(startIndex, startIndex + strContentCurrent.length() - 1);
  }

  /**
   * "���Ƶ�ǰ��"�Ĵ�������
   */
  private void moveLineToDown() {
    CurrentLines currentLines = new CurrentLines(this.txaMain,
        CurrentLines.LineExtend.EXTEND_DOWN);
    String strContentExtend = currentLines.getStrContentExtend();
    if (strContentExtend == null) {
      return;
    }
    int startIndex = currentLines.getStartIndex();
    int endIndex = currentLines.getEndIndex();
    int endLineNum = currentLines.getEndLineNum();
    String strContent = currentLines.getStrContent();
    String strContentCurrent = currentLines.getStrContentCurrent();
    boolean isReachEnd = false;
    if (endLineNum == this.txaMain.getLineCount() - 1) {
      // �����չ��Ϊ�ı���ĩ�У�Ӧ��һ������
      strContentCurrent = strContentCurrent.substring(0, strContentCurrent
          .length() - 1);
      strContentExtend += "\n";
      isReachEnd = true;
    }
    String strMoved = strContentExtend + strContentCurrent;
    this.txaMain.replaceRange(strMoved, startIndex, endIndex);
    int selectEndIndex = startIndex + strContent.length() - 1; // �ƶ���֮������ѡ���ƶ����е���ĩƫ����
    if (isReachEnd) {
      selectEndIndex++;
    }
    this.txaMain.select(startIndex + strContentExtend.length(), selectEndIndex);
  }

  /**
   * "�������༭�б�"�Ĵ�������
   */
  private void clearFileHistory() {
    if (!this.fileHistoryList.isEmpty()) {
      this.fileHistoryList.clear();
      this.menuFileHistory.removeAll();
      this.setFileHistoryMenuEnabled();
    }
  }

  /**
   * ���"ѡ����"�Ĵ�������
   */
  private void trimSelected() {
    StringBuilder stbSelected = new StringBuilder(this.txaMain
        .getSelectedText());
    if (stbSelected.toString().isEmpty()) {
      return;
    }
    boolean label = false; // �Ƿ���ڿհ��ַ��ı�ʶ��
    for (int i = 0; i < stbSelected.length(); i++) {
      switch (stbSelected.charAt(i)) {
      case ' ':
      case '\t':
      case '��':
        stbSelected.deleteCharAt(i);
        label = true; // ���ҵ��հ��ַ�
        i--; // ����ɾ���˲��ҵ��Ŀհ��ַ�����Ҫ��������ֵ
        break;
      }
    }
    if (label) {
      this.txaMain.replaceSelection(stbSelected.toString());
    }
  }

  /**
   * ����"�ַ������ʽ"�Ĵ�������
   * 
   * @param encoding
   *          �ַ������ʽ��ö��ֵ
   * @param isUpdateMenu
   *          �Ƿ���²˵���ѡ��
   */
  private void setCharEncoding(CharEncoding encoding, boolean isUpdateMenu) {
    if (isUpdateMenu) {
      this.encoding = encoding;
      this.setCharEncodingSelected();
    } else if (!this.encoding.equals(encoding)) {
      this.encoding = encoding;
      this.isStyleChanged = true;
      this.setStylePrefix();
    }
    this.updateStateEncoding();
  }

  /**
   * ���ݲ��õ��ַ������ʽ�����²˵���ѡ��
   */
  private void setCharEncodingSelected() {
    switch (this.encoding) {
    case UTF8:
      this.itemCharsetUTF8.setSelected(true);
      break;
    case UTF8_NO_BOM:
      this.itemCharsetUTF8_NO_BOM.setSelected(true);
      break;
    case ULE:
      this.itemCharsetULE.setSelected(true);
      break;
    case UBE:
      this.itemCharsetUBE.setSelected(true);
      break;
    case ANSI:
      this.itemCharsetANSI.setSelected(true);
      break;
    default:
      this.itemCharsetBASE.setSelected(true);
      break;
    }
  }

  /**
   * ����"���з���ʽ"�Ĵ�������
   * 
   * @param lineSeparator
   *          �����õĻ��з�
   * @param isUpdateMenu
   *          �Ƿ���²˵���ѡ��
   */
  private void setLineStyleString(LineSeparator lineSeparator,
      boolean isUpdateMenu) {
    if (isUpdateMenu) {
      this.lineSeparator = lineSeparator;
      this.setLineStyleSelected();
    } else if (!this.lineSeparator.equals(lineSeparator)) {
      this.lineSeparator = lineSeparator;
      this.isStyleChanged = true;
      this.setStylePrefix();
    }
    this.updateStateLineStyle();
  }

  /**
   * ���ݲ��õĻ��з������²˵���ѡ��
   */
  private void setLineStyleSelected() {
    switch (this.lineSeparator) {
    case UNIX:
      this.itemLineStyleUnix.setSelected(true);
      break;
    case MACINTOSH:
      this.itemLineStyleMac.setSelected(true);
      break;
    case WINDOWS:
      this.itemLineStyleWin.setSelected(true);
      break;
    case DEFAULT:
      if (LineSeparator.DEFAULT.toString().equals(
          LineSeparator.WINDOWS.toString())) {
        this.itemLineStyleWin.setSelected(true);
      } else if (LineSeparator.DEFAULT.toString().equals(
          LineSeparator.UNIX.toString())) {
        this.itemLineStyleUnix.setSelected(true);
      } else if (LineSeparator.DEFAULT.toString().equals(
          LineSeparator.MACINTOSH.toString())) {
        this.itemLineStyleMac.setSelected(true);
      }
    }
  }

  /**
   * ����"�����ַ�"�Ĵ�������
   */
  private void openInsertCharDialog() {
    if (this.insertCharDialog == null) {
      Hashtable<String, String> hashtable = new Hashtable<String, String>();
      hashtable.put("�������", Util.INSERT_SPECIAL);
      hashtable.put("������", Util.INSERT_PUNCTUATION);
      hashtable.put("��ѧ����", Util.INSERT_MATH);
      hashtable.put("��λ����", Util.INSERT_UNIT);
      hashtable.put("���ַ���", Util.INSERT_DIGIT);
      hashtable.put("ƴ������", Util.INSERT_PINYIN);
      this.insertCharDialog = new InsertCharDialog(this, false, this.txaMain,
          hashtable);
    } else if (!this.insertCharDialog.isVisible()) {
      this.insertCharDialog.setVisible(true);
    }
  }

  /**
   * "���з�ʽ"�Ĵ�������
   * 
   * @param isByWord
   *          ���з�ʽ��true��ʾ�Ե��ʱ߽绻�У�false��ʾ���ַ��߽绻��
   */
  private void setLineWrapStyle(boolean isByWord) {
    this.txaMain.setWrapStyleWord(isByWord);
  }

  /**
   * "ɾ��������"�Ĵ�������
   */
  private void deleteLineToStart() {
    CurrentLine currentLine = new CurrentLine(this.txaMain);
    int startIndex = currentLine.getStartIndex();
    int currentIndex = currentLine.getCurrentIndex();
    if (currentIndex != startIndex) {
      this.txaMain.replaceRange("", startIndex, currentIndex);
    }
  }

  /**
   * "ɾ������β"�Ĵ�������
   */
  private void deleteLineToEnd() {
    CurrentLine currentLine = new CurrentLine(this.txaMain);
    int endIndex = currentLine.getEndIndex();
    int currentIndex = currentLine.getCurrentIndex();
    int lineNum = currentLine.getLineNum();
    int lineCount = this.txaMain.getLineCount();
    if (currentIndex != endIndex) {
      if (lineCount > lineNum + 1) {
        endIndex--;
      }
      this.txaMain.replaceRange("", currentIndex, endIndex);
    }
  }

  /**
   * ��������༭���ļ��Ӳ˵�
   * 
   * @param strFile
   *          �������ļ�·��
   */
  private void addFileHistoryItem(String strFile) {
    if (strFile == null || strFile.isEmpty()) {
      return;
    }
    int index = this.checkFileInHistory(strFile);
    if (index >= 0) {
      JMenuItem itemFile = new JMenuItem(strFile);
      itemFile.setActionCommand(Util.FILE_HISTORY);
      itemFile.addActionListener(this);
      if (this.fileHistoryList.size() > index) {
        this.fileHistoryList.remove(index);
        this.menuFileHistory.remove(index);
      }
      this.fileHistoryList.add(strFile);
      this.menuFileHistory.add(itemFile);
      this.setFileHistoryMenuEnabled();
    }
  }

  /**
   * ����ļ����Ƿ��Ѵ���
   * 
   * @param strFile
   *          �������ļ�·��
   * @return ��Ҫ���ӵ�����༭��������-1��ʾ�����쳣
   */
  private int checkFileInHistory(String strFile) {
    if (strFile == null || strFile.isEmpty()) {
      return -1;
    }
    int index = -1;
    int listSize = this.fileHistoryList.size();
    if (listSize == 0) {
      index = 0;
    } else {
      index = this.fileHistoryList.indexOf(strFile);
      if (index < 0) {
        if (listSize >= Util.FILE_HISTORY_MAX) {
          index = 0;
        } else {
          index = listSize;
        }
      }
    }
    return index;
  }

  /**
   * ������༭���ļ�
   * 
   * @param strFile
   *          ����༭�������ļ�·��
   */
  private void openFileHistory(String strFile) {
    if (!this.saveFileBeforeAct()) {
      return;
    }
    if (strFile == null || strFile.isEmpty()) {
      return;
    }
    File file = new File(strFile);
    if (file != null && file.exists()) {
      this.toOpenFile(file, true);
      this.setAfterOpenFile();
      this.setFileNameAndPath(file);
    } else {
      JOptionPane.showMessageDialog(this, "�ļ���" + file + " �����ڣ�",
          Util.SOFTWARE, JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * "ɾ��ȫ�Ŀ���"�Ĵ�������
   */
  private void delAllNullLines() {
    String strTextAll = this.txaMain.getText();
    String strText = this.delNullLines(strTextAll);
    if (!strTextAll.equals(strText)) {
      this.txaMain.setText(strText);
    }
  }

  /**
   * "ɾ��ѡ���ڿ���"�Ĵ�������
   */
  private void delSelectedNullLines() {
    String strTextSel = this.txaMain.getSelectedText();
    String strText = this.delNullLines(strTextSel);
    if (!strTextSel.equals(strText)) {
      this.txaMain.replaceSelection(strText);
    }
  }

  /**
   * ɾ���ı��ڿ���
   * 
   * @param strText
   *          ���������ı�
   * @return ɾ�����к���ı�
   */
  private String delNullLines(String strText) {
    if (strText == null) {
      return strText;
    }
    String strDouble = "\n\n";
    int index = strText.indexOf(strDouble);
    boolean hasEnter = false;
    if (strText.startsWith("\n") || strText.endsWith("\n")) {
      hasEnter = true;
    }
    if (index < 0 && !hasEnter) {
      return strText;
    }
    while (index >= 0) {
      strText = strText.replaceAll(strDouble, "\n");
      index = strText.indexOf(strDouble);
    }
    if (strText.startsWith("\n")) {
      strText = strText.substring(1, strText.length());
    }
    if (strText.endsWith("\n")) {
      strText = strText.substring(0, strText.length() - 1);
    }
    return strText;
  }

  /**
   * "������ɫ"�Ĵ�������
   */
  private void setFontColor() {
    Color color = JColorChooser.showDialog(this, "������ɫ", this.txaMain
        .getForeground());
    if (color != null) {
      this.txaMain.setForeground(color);
    }
  }

  /**
   * "������ɫ"�Ĵ�������
   */
  private void setBackColor() {
    Color color = JColorChooser.showDialog(this, "������ɫ", this.txaMain
        .getBackground());
    if (color != null) {
      this.txaMain.setBackground(color);
    }
  }

  /**
   * "�����ɫ"�Ĵ�������
   */
  private void setCaretColor() {
    Color color = JColorChooser.showDialog(this, "�����ɫ", this.txaMain
        .getCaretColor());
    if (color != null) {
      this.txaMain.setCaretColor(color);
    }
  }

  /**
   * "ѡ��������ɫ"�Ĵ�������
   */
  private void setSelFontColor() {
    Color color = JColorChooser.showDialog(this, "ѡ��������ɫ", this.txaMain
        .getSelectedTextColor());
    if (color != null) {
      this.txaMain.setSelectedTextColor(color);
    }
  }

  /**
   * "ѡ��������ɫ"�Ĵ�������
   */
  private void setSelBackColor() {
    Color color = JColorChooser.showDialog(this, "ѡ��������ɫ", this.txaMain
        .getSelectionColor());
    if (color != null) {
      this.txaMain.setSelectionColor(color);
    }
  }

  /**
   * "Tab������"�Ĵ�������
   */
  private void openTabSetDialog() {
    if (this.tabSetDialog == null) {
      this.tabSetDialog = new TabSetDialog(this, true, this.txaMain);
    } else {
      this.tabSetDialog.setVisible(true);
    }
    this.isReplaceBySpace = this.tabSetDialog.getReplaceBySpace();
    this.setTabReplace();
  }

  /**
   * "�ı���ק"�Ĵ�������
   */
  private void setTextDrag() {
    this.txaMain.setDragEnabled(this.itemTextDrag.isSelected());
  }

  /**
   * �������ţ�"�Ŵ�"�Ĵ�������
   */
  private void setFontSizePlus() {
    Font font = this.txaMain.getFont();
    if (font.getSize() >= Util.MAX_FONT_SIZE) {
      return;
    }
    this.txaMain.setFont(new Font(font.getFamily(), font.getStyle(), font
        .getSize() + 1));
  }

  /**
   * �������ţ�"��С"�Ĵ�������
   */
  private void setFontSizeMinus() {
    Font font = this.txaMain.getFont();
    if (font.getSize() <= Util.MIN_FONT_SIZE) {
      return;
    }
    this.txaMain.setFont(new Font(font.getFamily(), font.getStyle(), font
        .getSize() - 1));
  }

  /**
   * �������ţ�"�ָ���ʼ"�Ĵ�������
   */
  private void setFontSizeReset() {
    Font font = this.txaMain.getFont();
    this.txaMain.setFont(new Font(font.getFamily(), font.getStyle(),
        Util.TEXT_FONT.getSize()));
  }

  /**
   * ����հ׵Ĵ�������
   * 
   * @param position
   *          ����հ׵�λ�ã�0Ϊ�����ס��հף�1Ϊ����β���հף�2Ϊ������+��β���հ�
   */
  private void trimLines(int position) {
    CurrentLines currentLines = new CurrentLines(this.txaMain);
    String strContent = currentLines.getStrContent();
    if (strContent == null || strContent.isEmpty()) {
      return;
    }
    String arrContents[] = strContent.split("\n", -1); // ����ǰѡ�����ı����д���������ĩβ�Ķദ����
    StringBuilder stbContent = new StringBuilder(); // ���ڴ�Ŵ�������ı�
    for (int n = 0; n < arrContents.length; n++) {
      String strLine = arrContents[n];
      if (strLine.isEmpty()) {
        stbContent.append("\n");
        continue;
      }
      if (position == 0) { // ���"����"�հ�
        stbContent.append(this.trimLine(strLine, true) + "\n");
      } else if (position == 1) { // ���"��β"�հ�
        stbContent.append(this.trimLine(strLine, false) + "\n");
      } else if (position == 2) { // ���"����+��β"�հ�
        strLine = this.trimLine(strLine, true);
        stbContent.append(this.trimLine(strLine, false) + "\n");
      }
    }
    if (stbContent != null) {
      int startIndex = currentLines.getStartIndex();
      int endIndex = currentLines.getEndIndex();
      this.txaMain.replaceRange(stbContent
          .deleteCharAt(stbContent.length() - 1).toString(), startIndex,
          endIndex);
    }
  }

  /**
   * ���һ���ı��Ŀհ�
   * 
   * @param position
   *          ����հ׵�λ�ã�trueΪ�����ס��հף�falseΪ����β���հ�
   * @return ���ָ���հ׵��ı�
   */
  private String trimLine(String strLine, boolean position) {
    if (strLine == null || strLine.isEmpty()) {
      return strLine;
    }
    int blank = 0; // �հ��ַ��ĸ���
    boolean label = false; // ���ڱ�ʶ�հ��ַ��Ľ���
    if (position) { // ���"����"�հ�
      for (int i = 0; i < strLine.length(); i++) {
        switch (strLine.charAt(i)) {
        case ' ':
        case '\t':
        case '��':
          blank++;
          break;
        default:
          label = true;
          break;
        }
        if (label) {
          break;
        }
      }
      strLine = strLine.substring(blank);
    } else { // ���"��β"�հ�
      for (int i = strLine.length() - 1; i >= 0; i--) {
        switch (strLine.charAt(i)) {
        case ' ':
        case '\t':
        case '��':
          blank++;
          break;
        default:
          label = true;
          break;
        }
        if (label) {
          break;
        }
      }
      strLine = strLine.substring(0, strLine.length() - blank);
    }
    return strLine;
  }

  /**
   * ���ñ�������ͷ��"*"�ű�ʶ���Ա�ʾ�ı��Ƿ����޸�
   */
  private void setTextPrefix() {
    if (this.isTextChanged) {
      if (!this.stbTitle.toString().startsWith(Util.TEXT_PREFIX)) {
        this.stbTitle.insert(0, Util.TEXT_PREFIX); // �ڱ������Ŀ�ͷ����"*"
        this.setTitle(this.stbTitle.toString());
      }
    } else {
      if (this.stbTitle.toString().startsWith(Util.TEXT_PREFIX)) {
        this.stbTitle.deleteCharAt(0); // ɾ����������ͷ��"*"
        this.setTitle(this.stbTitle.toString());
      }
    }
  }

  /**
   * ���ñ�������ͷ��"��"�ű�ʶ���Ա�ʾ�ı���ʽ�Ƿ����޸�
   */
  private void setStylePrefix() {
    if (this.isStyleChanged) {
      if (this.stbTitle.toString().startsWith(
          Util.TEXT_PREFIX + Util.STYLE_PREFIX)) {
      } else if (this.stbTitle.toString().startsWith(Util.TEXT_PREFIX)) {
        this.stbTitle.insert(1, Util.STYLE_PREFIX);
      } else if (this.stbTitle.toString().startsWith(Util.STYLE_PREFIX)) {
      } else {
        this.stbTitle.insert(0, Util.STYLE_PREFIX);
      }
    } else {
      if (this.stbTitle.toString().startsWith(
          Util.TEXT_PREFIX + Util.STYLE_PREFIX)) {
        this.stbTitle.deleteCharAt(1);
      } else if (this.stbTitle.toString().startsWith(Util.TEXT_PREFIX)) {
      } else if (this.stbTitle.toString().startsWith(Util.STYLE_PREFIX)) {
        this.stbTitle.deleteCharAt(0);
      } else {
      }
    }
    this.setTitle(this.stbTitle.toString());
  }

  /**
   * ����ϵͳ�����������
   * 
   * @param strText
   *          Ҫ�����������ı�
   */
  private void setClipboardContents(String strText) {
    if (strText == null || strText.isEmpty()) {
      return;
    }
    StringSelection ss = new StringSelection(strText);
    this.clip.setContents(ss, ss);
    this.itemPaste.setEnabled(true);
    this.itemPopPaste.setEnabled(true);
  }

  /**
   * ����"��ǰ�ļ���"��������Ĵ�������
   */
  private void toClipFileName() {
    this.setClipboardContents(this.file.getName());
  }

  /**
   * ����"��ǰ�ļ�·��"��������Ĵ�������
   */
  private void toClipFilePath() {
    this.setClipboardContents(this.file.getAbsolutePath());
  }

  /**
   * ����"��ǰĿ¼·��"��������Ĵ�������
   */
  private void toClipDirPath() {
    this.setClipboardContents(this.file.getParent());
  }

  /**
   * ����"��ǰ��"��������Ĵ�������
   */
  private void toClipCurLine() {
    CurrentLine currentLine = new CurrentLine(this.txaMain);
    String strLine = currentLine.getStrLine();
    if (!strLine.endsWith("\n")) {
      strLine += "\n";
    }
    this.setClipboardContents(strLine);
  }

  /**
   * ����"�����ı�"��������Ĵ�������
   */
  private void toClipAllText() {
    this.setClipboardContents(this.txaMain.getText());
  }

  /**
   * "��������"�Ĵ�������
   */
  private void setResizable() {
    this.setResizable(!this.itemResizable.isSelected());
  }

  /**
   * "ǰ����ʾ"�Ĵ�������
   */
  private void setAlwaysOnTop() {
    this.setAlwaysOnTop(this.itemAlwaysOnTop.isSelected());
  }

  /**
   * "��д��ǰѡ��"�Ĵ�������
   */
  private void copySelectedText() {
    int start = this.txaMain.getSelectionStart();
    int end = this.txaMain.getSelectionEnd();
    if (start != end) {
      this.txaMain.insert(this.txaMain.getSelectedText(), end);
      this.txaMain.select(start, end);
    }
  }

  /**
   * "��д��ǰ��"�Ĵ�������
   */
  private void copyLines() {
    CurrentLines currentLines = new CurrentLines(this.txaMain);
    String strContent = currentLines.getStrContent();
    int endIndex = currentLines.getEndIndex();
    int currentIndex = currentLines.getCurrentIndex();
    if (currentIndex == this.txaMain.getText().length()) {
      strContent = "\n" + strContent;
    }
    this.txaMain.insert(strContent, endIndex);
  }

  /**
   * "ɾ����ǰ��"�Ĵ�������
   */
  private void deleteLines() {
    CurrentLines currentLines = new CurrentLines(this.txaMain);
    int startIndex = currentLines.getStartIndex();
    int endIndex = currentLines.getEndIndex();
    int length = this.txaMain.getText().length();
    if (length > 0) {
      if (startIndex > 0 && endIndex == length) {
        startIndex--; // λ���ı����зǿյ����һ��ʱ��ȷ��ɾ�����з�
      }
      this.txaMain.replaceRange("", startIndex, endIndex);
    }
  }

  /**
   * "���ٲ���"�Ĵ�������
   * 
   * @param isFindDown
   *          ���ҷ������²���Ϊtrue�����ϲ���Ϊfalse
   */
  private void quickFindText(boolean isFindDown) {
    String strFindText = this.txaMain.getSelectedText();
    if (strFindText != null && strFindText.length() > 0) {
      int index = Util.findText(strFindText, this.txaMain, isFindDown, false);
      if (index >= 0) {
        this.txaMain.select(index, index + strFindText.length());
      }
    }
  }

  /**
   * "�л���Сд"�Ĵ�������
   * 
   * @param isCaseUp
   *          �л�Ϊ��д(true)���л�ΪСд(false)
   */
  private void switchCase(boolean isCaseUp) {
    String strSel = this.txaMain.getSelectedText();
    if (strSel.isEmpty()) {
      return;
    }
    int start = this.txaMain.getSelectionStart();
    int end = this.txaMain.getSelectionEnd();
    if (isCaseUp) {
      this.txaMain.replaceSelection(strSel.toUpperCase());
    } else {
      this.txaMain.replaceSelection(strSel.toLowerCase());
    }
    this.txaMain.select(start, end);
  }

  /**
   * "�Ӵ���ɾ���ļ�"�Ĵ�������
   */
  private void deleteFile() {
    int result = JOptionPane.showConfirmDialog(this, "�˲�����ɾ�������ļ���" + this.file
        + "\n�Ƿ������", Util.SOFTWARE, JOptionPane.YES_NO_CANCEL_OPTION);
    if (result != JOptionPane.YES_OPTION) {
      return;
    }
    if (this.file.delete()) {
      this.createNew(false);
    } else {
      JOptionPane.showMessageDialog(this, "�ļ���" + this.file + "ɾ��ʧ�ܣ�",
          Util.SOFTWARE, JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * "������"�Ĵ�������
   */
  private void reNameFile() {
    this.fcrSave.setSelectedFile(this.file);
    this.fcrSave.setDialogTitle("������");
    if (JFileChooser.APPROVE_OPTION != this.fcrSave.showSaveDialog(this)) {
      return;
    }
    File fileReName = this.fcrSave.getSelectedFile();
    if (this.file.equals(fileReName)) { // �ļ���δ�޸�ʱ����������
      return;
    }
    this.toSaveFile(fileReName);
    this.file.delete(); // ɾ��ԭ�ļ�
    this.setFileNameAndPath(fileReName);
  }

  /**
   * "����"�Ĵ�������
   */
  private void undoAction() {
    if (this.undoManager.canUndo()) { // �ж��Ƿ���Գ���
      this.undoManager.undo(); // ִ�г�������
      this.undoIndex--; // ������ʶ���ݼ�
      this.updateStateAll();
    }
    this.setAfterUndoRedo();
  }

  /**
   * "����"�Ĵ�������
   */
  private void redoAction() {
    if (this.undoManager.canRedo()) { // �ж��Ƿ��������
      this.undoManager.redo(); // ִ����������
      this.undoIndex++; // ������ʶ������
      this.updateStateAll();
    }
    this.setAfterUndoRedo();
  }

  /**
   * ִ��"����"��"����"����������
   */
  private void setAfterUndoRedo() {
    if (this.undoIndex == Util.DEFAULT_UNDO_INDEX) { // ������ʶ����Ĭ��ֵ��ȣ���ʾ�ı�δ�޸�
      this.isTextChanged = false;
    } else {
      this.isTextChanged = true;
    }
    this.setTextPrefix();
    this.setMenuStateUndoRedo(); // ���ó����������˵���״̬
  }

  /**
   * "ʱ��/����"�Ĵ�������
   */
  private void openInsertDateDialog() {
    if (this.insertDateDialog == null) {
      this.insertDateDialog = new InsertDateDialog(this, false, this.txaMain);
    } else if (!this.insertDateDialog.isVisible()) {
      this.insertDateDialog.setVisible(true);
    }
  }

  /**
   * "�˳�"�Ĵ�������
   */
  private void exit() {
    if (saveFileBeforeAct()) { // �رճ���ǰ����ļ��Ƿ����޸�
      System.exit(0);
    }
  }

  /**
   * "����"�Ĵ�������
   */
  private void showAbout() {
    if (this.aboutDialog == null) {
      final String strBlog = "http://hi.baidu.com/xiboliya";
      String[] arrStrLabel = new String[] { "������" + Util.SOFTWARE,
          "�汾��" + Util.VERSION, "���ߣ���ԭ",
          "<html>���ͣ�<a href='" + strBlog + "'>" + strBlog + "</a></html>",
          "��Ȩ����Ϊ�������������������û��޸�" };
      this.aboutDialog = new AboutDialog(this, true, arrStrLabel, this.icon);
      this.aboutDialog.addLinkByIndex(3, strBlog);
      this.aboutDialog.pack(); // �Զ��������ڴ�С������Ӧ�����
    }
    this.aboutDialog.setVisible(true);
  }

  /**
   * "�Զ�����"�Ĵ�������
   */
  private void setLineWarp() {
    boolean isLineWrap = this.itemLineWrap.isSelected();
    this.txaMain.setLineWrap(isLineWrap);
    this.menuLineWrapStyle.setEnabled(isLineWrap);
  }

  /**
   * �򿪵��ļ��������޸ģ���ִ���½����رղ���ʱ�������Ի������û�ѡ����Ӧ�Ĳ���
   * 
   * @return �û�ѡ�����ǻ��ʱ����true��ѡ��ȡ����ر�ʱ����false
   */
  private boolean saveFileBeforeAct() {
    if (this.file != null && (this.isTextChanged || this.isStyleChanged)) {
      String strTemp = "����";
      if (!this.isTextChanged && this.isStyleChanged) {
        strTemp = "��ʽ";
      }
      int result = JOptionPane.showConfirmDialog(this, "�ļ���" + this.file + "��"
          + strTemp + "�Ѿ��޸ġ�\n�뱣���ļ���", Util.SOFTWARE,
          JOptionPane.YES_NO_CANCEL_OPTION);
      if (result == JOptionPane.YES_OPTION) {
        this.saveFile(false);
      } else if (result == JOptionPane.CANCEL_OPTION
          || result == JOptionPane.CLOSED_OPTION) {
        return false;
      }
    }
    return true;
  }

  /**
   * "�½�"�Ĵ�������
   * 
   * @param needSave
   *          �Ƿ���Ҫ�����޸�
   */
  private void createNew(boolean needSave) {
    if (needSave && !this.saveFileBeforeAct()) {
      return;
    }
    this.txaMain.setText("");
    this.setFileNameAndPath(null);
    this.setLineStyleString(LineSeparator.DEFAULT, true);
    this.setCharEncoding(CharEncoding.BASE, true);
    this.isNew = true;
    this.isTextChanged = false;
    this.isStyleChanged = false;
    this.fileExistsLabel = false;
    this.setMenuDefault(); // �ָ��˵��ĳ�ʼ״̬
    this.undoManager.discardAllEdits(); // ��ճ���������
    this.undoIndex = Util.DEFAULT_UNDO_INDEX; // ������ʶ���ָ�Ĭ��ֵ
    this.setTextPrefix();
    this.setStylePrefix();
  }

  /**
   * "ɾ��"�Ĵ�������
   */
  private void deleteText() {
    this.txaMain.replaceSelection("");
  }

  /**
   * "����"�Ĵ�������
   */
  private void copyText() {
    this.setClipboardContents(this.txaMain.getSelectedText());
  }

  /**
   * "����"�Ĵ�������
   */
  private void cutText() {
    this.copyText(); // ����ѡ���ı�
    this.deleteText(); // ɾ��ѡ���ı�
  }

  /**
   * "ճ��"�Ĵ�������
   */
  private void pasteText() {
    try {
      Transferable tf = this.clip.getContents(this);
      if (tf != null) {
        String str = tf.getTransferData(DataFlavor.stringFlavor).toString(); // ����������ڵ����ݲ����ı������׳��쳣
        if (str != null) {
          str = str.replaceAll(LineSeparator.WINDOWS.toString(),
              LineSeparator.UNIX.toString()); // ��Windows��ʽ�Ļ��з�\n\r��ת��ΪUNIX/Linux��ʽ
          str = str.replaceAll(LineSeparator.MACINTOSH.toString(),
              LineSeparator.UNIX.toString()); // Ϊ���ݴ��������ܲ����\r�ַ��滻Ϊ\n
          this.txaMain.replaceSelection(str);
        }
      }
    } catch (Exception x) {
      // �������쳣
      x.printStackTrace();
    }
  }

  /**
   * "ȫѡ"�Ĵ�������
   */
  private void selectAll() {
    this.txaMain.selectAll();
  }

  /**
   * "����"�Ĵ�������
   */
  private void openFontChooser() {
    if (this.fontChooser == null) {
      this.fontChooser = new FontChooser(this, true, this.txaMain);
    } else {
      this.fontChooser.updateListView();
      this.fontChooser.setFontView();
      this.fontChooser.setStyleView();
      this.fontChooser.setSizeView();
      this.fontChooser.setVisible(true);
    }
  }

  /**
   * �Ƿ��Ѿ����˲��һ��滻�Ի���
   * 
   * @return ����Ѿ����˲��һ��滻�Ի����򷵻�false
   */
  private boolean canOpenDialog() {
    if (this.findReplaceDialog != null && this.findReplaceDialog.isVisible()) {
      return false;
    }
    return true;
  }

  /**
   * Ԥ������ǰѡ�е��ı����Ա����ڲ��ҶԻ������ʾ
   * 
   * @return ������֮����ı�
   */
  private String checkSelText() {
    String strSel = this.txaMain.getSelectedText();
    if (strSel != null) {
      int index = strSel.indexOf("\n");
      if (index >= 0) {
        strSel = strSel.substring(0, index);
      }
    }
    return strSel;
  }

  /**
   * "����"�Ĵ�������
   */
  private void openFindDialog() {
    if (!this.canOpenDialog()) {
      this.findReplaceDialog.setTabbedIndex(0); // �򿪲���ѡ�
      return;
    }
    if (this.findReplaceDialog == null) {
      this.findReplaceDialog = new FindReplaceDialog(this, false, this.txaMain);
    } else {
      this.findReplaceDialog.setVisible(true);
    }
    this.findReplaceDialog.setTabbedIndex(0); // �򿪲���ѡ�
    String strSel = this.checkSelText();
    if (strSel != null && !strSel.isEmpty()) {
      this.findReplaceDialog.setFindText(strSel, true);
    } else {
      this.findReplaceDialog.setFindTextSelect();
    }
  }

  /**
   * "������һ��"�Ĵ�������
   * 
   * @param isFindDown
   *          ���ҵķ���������²�����Ϊtrue����֮��Ϊfalse
   */
  private void findNextText(boolean isFindDown) {
    if (!this.canOpenDialog()) {
      this.findReplaceDialog.setTabbedIndex(0); // �򿪲���ѡ�
      return;
    }
    String strSel = this.checkSelText();
    if (this.findReplaceDialog == null) {
      this.findReplaceDialog = new FindReplaceDialog(this, false, this.txaMain);
      this.findReplaceDialog.setTabbedIndex(0); // �򿪲���ѡ�
      if (strSel != null && !strSel.isEmpty()) {
        this.findReplaceDialog.setFindText(strSel, true);
      }
    } else if (this.findReplaceDialog.getFindText().isEmpty()) {
      if (strSel != null && !strSel.isEmpty()) {
        this.findReplaceDialog.setFindText(strSel, true);
      }
      this.findReplaceDialog.setTabbedIndex(0); // �򿪲���ѡ�
      this.findReplaceDialog.setVisible(true);
    } else {
      this.findReplaceDialog.findText(isFindDown);
    }
  }

  /**
   * "�滻"�Ĵ�������
   */
  private void openReplaceDialog() {
    if (!this.canOpenDialog()) {
      this.findReplaceDialog.setTabbedIndex(1); // ���滻ѡ�
      return;
    }
    if (this.findReplaceDialog == null) {
      this.findReplaceDialog = new FindReplaceDialog(this, false, this.txaMain);
    } else {
      this.findReplaceDialog.setVisible(true);
    }
    this.findReplaceDialog.setTabbedIndex(1); // ���滻ѡ�
    String strSel = this.checkSelText();
    if (strSel != null && !strSel.isEmpty()) {
      this.findReplaceDialog.setFindText(strSel, true);
    } else {
      this.findReplaceDialog.setFindTextSelect();
    }
    this.findReplaceDialog.setReplaceText("");
  }

  /**
   * "ת��"�Ĵ�������
   */
  private void openGotoDialog() {
    if (this.gotoDialog == null) {
      this.gotoDialog = new GotoDialog(this, true, this.txaMain);
    } else {
      this.gotoDialog.setVisible(true);
    }
  }

  /**
   * "״̬��"�Ĵ�������
   */
  private void setStateBar() {
    this.pnlState.setVisible(this.itemStateBar.isSelected());
  }

  /**
   * "���������ļ�"�Ĵ�������
   */
  private void reOpenFile() {
    if (this.file == null) {
      return;
    }
    if (this.isTextChanged || this.isStyleChanged) {
      String strTemp = "����";
      if (!this.isTextChanged && this.isStyleChanged) {
        strTemp = "��ʽ";
      }
      int result = JOptionPane.showConfirmDialog(this, "�ļ���" + this.file + "��"
          + strTemp + "�Ѿ��޸ġ�\n������޸�����������", Util.SOFTWARE,
          JOptionPane.YES_NO_CANCEL_OPTION);
      if (result != JOptionPane.YES_OPTION) {
        return;
      }
    }
    if (this.file.exists()) {
      this.toOpenFile(this.file, true);
      this.setAfterOpenFile();
      this.setTextPrefix();
      this.setStylePrefix();
    } else {
      int result = JOptionPane.showConfirmDialog(this, "�ļ���" + this.file
          + "�����ڡ�\nҪ���´�����", Util.SOFTWARE, JOptionPane.YES_NO_CANCEL_OPTION);
      if (result == JOptionPane.YES_OPTION) {
        this.checkFile(this.file);
        this.toSaveFile(this.file);
        this.setAfterSaveFile();
        this.setTextPrefix();
        this.setStylePrefix();
      }
    }
  }

  /**
   * ���ļ��������
   */
  private void setAfterOpenFile() {
    this.txaMain.setCaretPosition(0); // �����������Ϊ�ı���ͷ
    this.isTextChanged = false;
    this.isStyleChanged = false;
    this.isNew = false;
    this.undoManager.discardAllEdits();
    this.undoIndex = Util.DEFAULT_UNDO_INDEX; // ������ʶ���ָ�Ĭ��ֵ
    this.setMenuStateUndoRedo(); // ���ó����������˵���״̬
    this.itemReOpen.setEnabled(true);
    this.itemReName.setEnabled(true);
    this.itemDelFile.setEnabled(true);
    this.itemToClipFileName.setEnabled(true);
    this.itemToClipFilePath.setEnabled(true);
    this.itemToClipDirPath.setEnabled(true);
  }

  /**
   * "��"�Ĵ�������
   */
  private void openFile() {
    if (!this.saveFileBeforeAct()) {
      return;
    }
    this.fcrOpen.setSelectedFile(null);
    if (JFileChooser.APPROVE_OPTION != this.fcrOpen.showOpenDialog(this)) {
      return;
    }
    File file = this.fcrOpen.getSelectedFile();
    if (file != null && file.exists()) {
      this.toOpenFile(file, true);
      this.setAfterOpenFile();
      this.setFileNameAndPath(file);
    }
  }

  /**
   * "��ָ�������"�Ĵ�������
   */
  private void openFileByEncoding() {
    if (!this.saveFileBeforeAct()) {
      return;
    }
    if (this.fileEncodingDialog == null) {
      this.fileEncodingDialog = new FileEncodingDialog(this, true);
    } else {
      this.fileEncodingDialog.setVisible(true);
    }
    if (!this.fileEncodingDialog.getOk()) {
      return;
    }
    CharEncoding charEncoding = this.fileEncodingDialog.getCharEncoding();
    this.fcrOpen.setSelectedFile(null);
    if (JFileChooser.APPROVE_OPTION != this.fcrOpen.showOpenDialog(this)) {
      return;
    }
    File file = this.fcrOpen.getSelectedFile();
    if (file != null && file.exists()) {
      if (charEncoding != null) {
        this.setCharEncoding(charEncoding, true);
        this.toOpenFile(file, false);
      } else {
        this.toOpenFile(file, true);
      }
      this.setAfterOpenFile();
      this.setFileNameAndPath(file);
    }

  }

  /**
   * �����ļ���ͷ��BOM��������ڵĻ������ж��ļ��ı����ʽ�� �ı��ļ��и��ֲ�ͬ�ı����ʽ������ж�������ᵼ����ʾ�򱣴����
   * Ϊ�˱�ʶ�ļ��ı����ʽ�����ڱ༭�ͱ��棬�����ļ���ͷ������BOM�����Ա�ʶ�����ʽ�� UTF-8��ʽ��0xef 0xbb 0xbf�� Unicode
   * Little Endian��ʽ��0xff 0xfe�� Unicode Big Endian��ʽ��0xfe
   * 0xff����ANSI��ʽ��û��BOM�ġ�����һ�ֲ���BOM��UTF-8��ʽ���ļ���������ANSI�����֣����δ��ʶ������ʽ��
   * 
   * @param file
   *          ���жϵ��ļ�
   */
  private void checkFileEncoding(File file) {
    FileInputStream fileInputStream = null;
    int charArr[] = new int[3];
    try {
      fileInputStream = new FileInputStream(file);
      for (int i = 0; i < charArr.length; i++) {
        charArr[i] = fileInputStream.read();
      }
    } catch (Exception x) {
      x.printStackTrace();
    } finally {
      try {
        fileInputStream.close();
      } catch (IOException x) {
        x.printStackTrace();
      }
    }
    if (charArr[0] == 0xff && charArr[1] == 0xfe) {
      this.setCharEncoding(CharEncoding.ULE, true);
    } else if (charArr[0] == 0xfe && charArr[1] == 0xff) {
      this.setCharEncoding(CharEncoding.UBE, true);
    } else if (charArr[0] == 0xef && charArr[1] == 0xbb && charArr[2] == 0xbf) {
      this.setCharEncoding(CharEncoding.UTF8, true);
    } else {
      this.setCharEncoding(CharEncoding.BASE, true);
    }
  }

  /**
   * ���ı��ļ�����������ʾ���ı�����
   * 
   * @param file
   *          �򿪵��ļ�
   * @param isAutoCheckEncoding
   *          �Ƿ��Զ��������ʽ
   */
  private void toOpenFile(File file, boolean isAutoCheckEncoding) {
    if (isAutoCheckEncoding) {
      this.checkFileEncoding(file);
    }
    InputStreamReader inputStreamReader = null;
    try {
      String strCharset = this.encoding.toString();
      inputStreamReader = new InputStreamReader(new FileInputStream(file),
          strCharset);
      char chrBuf[] = new char[Util.BUFFER_LENGTH];
      int len = 0;
      StringBuilder stbTemp = new StringBuilder();
      switch (this.encoding) {
      case UTF8:
      case ULE:
      case UBE:
        inputStreamReader.read(); // ȥ���ļ���ͷ��BOM
        break;
      }
      while ((len = inputStreamReader.read(chrBuf)) != -1) {
        stbTemp.append(chrBuf, 0, len);
      }
      String strTemp = stbTemp.toString();
      if (strTemp.indexOf(LineSeparator.WINDOWS.toString()) >= 0) {
        strTemp = strTemp.replaceAll(LineSeparator.WINDOWS.toString(),
            LineSeparator.UNIX.toString());
        this.setLineStyleString(LineSeparator.WINDOWS, true);
      } else if (strTemp.indexOf(LineSeparator.MACINTOSH.toString()) >= 0) {
        strTemp = strTemp.replaceAll(LineSeparator.MACINTOSH.toString(),
            LineSeparator.UNIX.toString());
        this.setLineStyleString(LineSeparator.MACINTOSH, true);
      } else if (strTemp.indexOf(LineSeparator.UNIX.toString()) >= 0) {
        this.setLineStyleString(LineSeparator.UNIX, true);
      } else { // ���ļ����ݲ���1��ʱ��������ΪϵͳĬ�ϵĻ��з�
        this.setLineStyleString(LineSeparator.DEFAULT, true);
      }
      this.txaMain.setText(strTemp);
      this.addFileHistoryItem(file.getCanonicalPath()); // ��������༭���ļ��б�
      this.fileExistsLabel = true;
    } catch (Exception x) {
      x.printStackTrace();
    } finally {
      try {
        inputStreamReader.close();
      } catch (IOException x) {
        x.printStackTrace();
      }
    }
  }

  /**
   * "����"�Ĵ�������
   * 
   * @param isSaveAs
   *          �Ƿ�Ϊ"����Ϊ"
   */
  private void saveFile(boolean isSaveAs) {
    boolean isFileExist = true; // ��ǰ�ļ��Ƿ����
    if (isSaveAs || this.isNew) {
      if (isSaveAs) {
        this.fcrSave.setDialogTitle("����Ϊ");
      } else {
        this.fcrSave.setDialogTitle("����");
      }
      this.fcrSave.setSelectedFile(null);
      if (JFileChooser.APPROVE_OPTION != this.fcrSave.showSaveDialog(this)) {
        return;
      }
      File file = this.fcrSave.getSelectedFile();
      if (file != null) {
        this.toSaveFile(file);
        this.setFileNameAndPath(file);
      } else {
        return;
      }
    } else {
      if (this.file != null) {
        isFileExist = this.checkFile(this.file);
        this.toSaveFile(this.file);
      } else {
        return;
      }
    }
    this.setAfterSaveFile();
    this.setTextPrefix();
    this.setStylePrefix();
    if (!isFileExist) {
      JOptionPane.showMessageDialog(this, "��ʧ���ļ���"
          + this.file.getAbsolutePath() + "\n�����´�����", Util.SOFTWARE,
          JOptionPane.CANCEL_OPTION);
    }
  }

  /**
   * ����ļ��Լ����ڵ�Ŀ¼�Ƿ����
   * 
   * @param file
   *          �������ļ�
   * @return ������ļ��Ƿ���ڣ�������ڷ���true����֮��Ϊfalse
   */
  private boolean checkFile(File file) {
    File fileParent = new File(file.getParent()); // ��ȡ�ļ��ĸ�Ŀ¼
    if (!fileParent.exists()) {
      fileParent.mkdirs(); // �����Ŀ¼�����ڣ��򴴽�֮
    }
    return file.exists();
  }

  /**
   * "����Ϊ"�Ĵ�������
   */
  private void saveAsFile() {
    this.saveFile(true);
  }

  /**
   * ���ı����е��ı����浽�ļ�
   * 
   * @param file
   *          ������ļ�
   */
  private void toSaveFile(File file) {
    FileOutputStream fileOutputStream = null;
    try {
      fileOutputStream = new FileOutputStream(file);
      String strText = this.txaMain.getText();
      strText = strText.replaceAll(LineSeparator.UNIX.toString(),
          this.lineSeparator.toString());
      byte byteStr[];
      int charBOM[] = new int[] { -1, -1, -1 }; // ���ݵ�ǰ���ַ����룬���BOM������
      switch (this.encoding) {
      case UTF8:
        charBOM[0] = 0xef;
        charBOM[1] = 0xbb;
        charBOM[2] = 0xbf;
        break;
      case ULE:
        charBOM[0] = 0xff;
        charBOM[1] = 0xfe;
        break;
      case UBE:
        charBOM[0] = 0xfe;
        charBOM[1] = 0xff;
        break;
      }
      byteStr = strText.getBytes(this.encoding.toString());
      for (int i = 0; i < charBOM.length; i++) {
        if (charBOM[i] == -1) {
          break;
        }
        fileOutputStream.write(charBOM[i]);
      }
      fileOutputStream.write(byteStr);
      this.addFileHistoryItem(file.getCanonicalPath()); // ��������༭���ļ��б�
      this.fileExistsLabel = true;
    } catch (Exception x) {
      x.printStackTrace();
    } finally {
      try {
        fileOutputStream.flush();
        fileOutputStream.close();
      } catch (IOException x) {
        x.printStackTrace();
      }
    }
  }

  /**
   * �����ļ��������
   */
  private void setAfterSaveFile() {
    this.itemReOpen.setEnabled(true);
    this.itemReName.setEnabled(true);
    this.itemDelFile.setEnabled(true);
    this.itemToClipFileName.setEnabled(true);
    this.itemToClipFilePath.setEnabled(true);
    this.itemToClipDirPath.setEnabled(true);
    this.isTextChanged = false;
    this.isStyleChanged = false;
    this.isNew = false;
    this.undoIndex = Util.DEFAULT_UNDO_INDEX; // ������ʶ���ָ�Ĭ��ֵ
  }

  /**
   * �����ļ������ƺ�·��
   * 
   * @param file
   *          ��ǰ�༭���ļ�
   */
  private void setFileNameAndPath(File file) {
    this.file = file;
    this.stbTitle = new StringBuilder(Util.SOFTWARE);
    if (file != null && file.exists()) {
      try {
        this.file = this.file.getCanonicalFile(); // ��ȡ�˳���·�����Ĺ淶��ʽ
        this.stbTitle.insert(0, this.file.getAbsolutePath() + " - ");
      } catch (IOException x) {
        x.printStackTrace();
      }
    }
    this.setTitle(this.stbTitle.toString());
  }

  /**
   * ����״̬�����ı�������
   */
  private void updateStateAll() {
    String strStateChars = Util.STATE_CHARS + this.txaMain.getText().length();
    String strStateLines = Util.STATE_LINES + this.txaMain.getLineCount();
    this.pnlState.setStringByIndex(0, strStateChars + ", " + strStateLines);
  }

  /**
   * ����״̬����ǰ������ڵ������������뵱ǰѡ����ַ���
   */
  private void updateStateCur() {
    int curLn = 1;
    int curCol = 1;
    int curSel = 0;
    CurrentLine currentLine = new CurrentLine(this.txaMain);
    int currentIndex = currentLine.getCurrentIndex();
    int startIndex = currentLine.getStartIndex();
    curLn = currentLine.getLineNum() + 1;
    curCol += currentIndex - startIndex;
    String strSel = this.txaMain.getSelectedText();
    if (strSel != null) {
      curSel = strSel.length();
    }
    String strStateCurLn = Util.STATE_CUR_LINE + curLn;
    String strStateCurCol = Util.STATE_CUR_COLUMN + curCol;
    String strStateCurSel = Util.STATE_CUR_SELECT + curSel;
    this.pnlState.setStringByIndex(1, strStateCurLn + ", " + strStateCurCol
        + ", " + strStateCurSel);
  }

  /**
   * ����״̬����ǰ�Ļ��з���ʽ
   */
  private void updateStateLineStyle() {
    this.pnlState.setStringByIndex(2, Util.STATE_LINE_STYLE
        + this.lineSeparator.getName());
  }

  /**
   * ����״̬����ǰ���ַ������ʽ
   */
  private void updateStateEncoding() {
    this.pnlState.setStringByIndex(3, Util.STATE_ENCODING
        + this.encoding.getName());
  }

  /**
   * ���ı����еĹ��仯ʱ�����������¼�
   */
  public void caretUpdate(CaretEvent e) {
    this.updateStateCur();
    String selText = this.txaMain.getSelectedText();
    if (selText != null && selText.length() > 0) {
      this.setMenuStateBySelectedText(true);
    } else {
      this.setMenuStateBySelectedText(false);
    }
  }

  /**
   * ���ı����е��ı������仯ʱ�����������¼�
   */

  public void undoableEditHappened(UndoableEditEvent e) {
    this.undoManager.addEdit(e.getEdit());
    this.undoIndex++; // ������ʶ������
    this.setMenuStateUndoRedo(); // ���ó����������˵���״̬
    if (this.txaMain == null || this.txaMain.getText().isEmpty()) {
      this.setMenuStateByTextArea(false);
    } else {
      this.setMenuStateByTextArea(true);
    }
    this.isTextChanged = true;
    this.updateStateAll();
    this.setTextPrefix();
  }

  /**
   * �������ڻ�ý���ʱ�����������¼�
   */
  public void windowGainedFocus(WindowEvent e) {
    try {
      Transferable tf = this.clip.getContents(this);
      if (tf == null) {
        this.itemPaste.setEnabled(false);
        this.itemPopPaste.setEnabled(false);
      } else {
        String str = tf.getTransferData(DataFlavor.stringFlavor).toString(); // ����������ڵ����ݲ����ı������׳��쳣
        if (str != null && str.length() > 0) {
          this.itemPaste.setEnabled(true);
          this.itemPopPaste.setEnabled(true);
        }
      }
    } catch (Exception x) {
      // �������쳣
      // x.printStackTrace();
      this.itemPaste.setEnabled(false);
      this.itemPopPaste.setEnabled(false);
    }
    if (this.file != null && !this.file.exists()) {
      if (this.fileExistsLabel) {
        int result = JOptionPane.showConfirmDialog(this, "�ļ���" + this.file
            + "�����ڡ�\nҪ���´�����", Util.SOFTWARE, JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
          this.checkFile(this.file);
          this.toSaveFile(this.file);
          this.setAfterSaveFile();
          this.setTextPrefix();
          this.setStylePrefix();
        } else {
          this.fileExistsLabel = false;
        }
      }
    }
  }

  /**
   * ��������ʧȥ����ʱ�����������¼�
   */
  public void windowLostFocus(WindowEvent e) {

  }
}