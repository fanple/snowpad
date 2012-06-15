package com.xiboliya.snowpad;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * "����"��"�滻"�Ի���
 * 
 * @author chen
 * 
 */
public class FindReplaceDialog extends BaseDialog implements ActionListener,
    CaretListener, ChangeListener {
  private static final long serialVersionUID = 1L;
  private JPanel pnlMain = (JPanel) this.getContentPane();
  private JTabbedPane tpnMain = new JTabbedPane();
  private JTextArea txaSource = null; // ���ڱ༭���ı���
  private boolean isFindDown = true; // ���²���
  private boolean isIgnoreCase = true; // ���Դ�Сд
  private BaseKeyAdapter keyAdapter = new BaseKeyAdapter(this);
  private BaseKeyAdapter buttonKeyAdapter = new BaseKeyAdapter(this, false);
  private String strFind = ""; // ���ҵ��ַ���
  // ����
  private JPanel pnlFind = new JPanel();
  private JLabel lblFindTextF = new JLabel("�������ݣ�");
  private BaseTextField txtFindTextF = new BaseTextField();
  private JCheckBox chkNotIgnoreCaseF = new JCheckBox("���ִ�Сд(C)", false);
  private JRadioButton radFindUpF = new JRadioButton("����(U)", false);
  private JRadioButton radFindDownF = new JRadioButton("����(D)", true);
  private JButton btnFindF = new JButton("����(F)");
  private JButton btnCountF = new JButton("ͳ�ƴ���(T)");
  private JButton btnCancelF = new JButton("ȡ��");
  private ButtonGroup bgpFindUpDownF = new ButtonGroup();
  private JPanel pnlFindUpDownF = new JPanel(new GridLayout(2, 1));
  // �滻
  private JPanel pnlReplace = new JPanel();
  private JLabel lblFindTextR = new JLabel("�������ݣ�");
  private JLabel lblReplaceTextR = new JLabel("�滻Ϊ��");
  private BaseTextField txtFindTextR = new BaseTextField();
  private BaseTextField txtReplaceTextR = new BaseTextField();
  private JCheckBox chkNotIgnoreCaseR = new JCheckBox("���ִ�Сд(C)", false);
  private JRadioButton radFindUpR = new JRadioButton("����(U)", false);
  private JRadioButton radFindDownR = new JRadioButton("����(D)", true);
  private JButton btnFindR = new JButton("����(F)");
  private JButton btnReplaceR = new JButton("�滻(R)");
  private JButton btnReplaceAllR = new JButton("ȫ���滻(A)");
  private JButton btnCancelR = new JButton("ȡ��");
  private ButtonGroup bgpFindUpDownR = new ButtonGroup();
  private JPanel pnlFindUpDownR = new JPanel(new GridLayout(2, 1));

  public FindReplaceDialog(JFrame owner, boolean modal, JTextArea txaSource) {
    super(owner, modal);
    if (txaSource == null) {
      return;
    }
    this.txaSource = txaSource;
    this.setTitle("����");
    this.init();
    this.setMnemonic();
    this.addListeners();
    this.setSize(390, 205);
    this.setVisible(true);
  }

  /**
   * �����ʼ��
   */
  private void init() {
    // ����
    this.pnlFind.setLayout(null);
    this.lblFindTextF.setBounds(10, 10, 70, Util.VIEW_HEIGHT);
    this.txtFindTextF.setBounds(80, 9, 180, Util.INPUT_HEIGHT);
    this.pnlFind.add(this.lblFindTextF);
    this.pnlFind.add(this.txtFindTextF);
    this.chkNotIgnoreCaseF.setBounds(10, 60, 110, Util.VIEW_HEIGHT);
    this.pnlFind.add(this.chkNotIgnoreCaseF);
    this.pnlFindUpDownF.setBounds(145, 40, 95, 70);
    this.pnlFindUpDownF.setBorder(new TitledBorder("����"));
    this.pnlFindUpDownF.add(this.radFindUpF);
    this.pnlFindUpDownF.add(this.radFindDownF);
    this.pnlFind.add(this.pnlFindUpDownF);
    this.btnFindF.setEnabled(false);
    this.btnFindF.setBounds(270, 10, 100, Util.BUTTON_HEIGHT);
    this.btnCountF.setEnabled(false);
    this.btnCountF.setBounds(270, 48, 100, Util.BUTTON_HEIGHT);
    this.btnCancelF.setBounds(270, 86, 100, Util.BUTTON_HEIGHT);
    this.pnlFind.add(this.btnFindF);
    this.pnlFind.add(this.btnCountF);
    this.pnlFind.add(this.btnCancelF);
    this.bgpFindUpDownF.add(this.radFindDownF);
    this.bgpFindUpDownF.add(this.radFindUpF);
    // �滻
    this.pnlReplace.setLayout(null);
    this.lblFindTextR.setBounds(10, 10, 70, Util.VIEW_HEIGHT);
    this.txtFindTextR.setBounds(80, 9, 180, Util.INPUT_HEIGHT);
    this.pnlReplace.add(this.lblFindTextR);
    this.pnlReplace.add(this.txtFindTextR);
    this.lblReplaceTextR.setBounds(10, 38, 70, Util.VIEW_HEIGHT);
    this.txtReplaceTextR.setBounds(80, 37, 180, Util.INPUT_HEIGHT);
    this.pnlReplace.add(this.lblReplaceTextR);
    this.pnlReplace.add(this.txtReplaceTextR);
    this.chkNotIgnoreCaseR.setBounds(10, 88, 110, Util.VIEW_HEIGHT);
    this.pnlReplace.add(this.chkNotIgnoreCaseR);
    this.pnlFindUpDownR.setBounds(145, 60, 95, 70);
    this.pnlFindUpDownR.setBorder(new TitledBorder("����"));
    this.pnlFindUpDownR.add(this.radFindUpR);
    this.pnlFindUpDownR.add(this.radFindDownR);
    this.pnlReplace.add(this.pnlFindUpDownR);
    this.btnFindR.setEnabled(false);
    this.btnReplaceR.setEnabled(false);
    this.btnReplaceAllR.setEnabled(false);
    this.btnFindR.setBounds(270, 10, 100, Util.BUTTON_HEIGHT);
    this.btnReplaceR.setBounds(270, 40, 100, Util.BUTTON_HEIGHT);
    this.btnReplaceAllR.setBounds(270, 70, 100, Util.BUTTON_HEIGHT);
    this.btnCancelR.setBounds(270, 100, 100, Util.BUTTON_HEIGHT);
    this.pnlReplace.add(this.btnFindR);
    this.pnlReplace.add(this.btnReplaceR);
    this.pnlReplace.add(this.btnReplaceAllR);
    this.pnlReplace.add(this.btnCancelR);
    this.bgpFindUpDownR.add(this.radFindDownR);
    this.bgpFindUpDownR.add(this.radFindUpR);
    // ������
    this.tpnMain.add(this.pnlFind, "����");
    this.tpnMain.add(this.pnlReplace, "�滻");
    this.pnlMain.add(this.tpnMain, BorderLayout.CENTER);
    this.setTabbedIndex(0);
    this.tpnMain.setFocusable(false);
  }

  /**
   * ����ѡ��ĵ�ǰ��ͼ
   * 
   * @param index
   *          ��ͼ��������
   */
  public void setTabbedIndex(int index) {
    this.tpnMain.setSelectedIndex(index);
  }

  /**
   * ��ȡѡ���ǰ��ͼ��������
   * 
   * @return ��ǰ��ͼ��������
   */
  public int getTabbedIndex() {
    return this.tpnMain.getSelectedIndex();
  }

  /**
   * ѡ�С��������ݡ��е��ı�
   */
  public void setFindTextSelect() {
    int tabbedIndex = this.getTabbedIndex();
    if (tabbedIndex == 0) {
      this.txtFindTextF.selectAll();
    } else {
      this.txtFindTextR.selectAll();
    }
  }

  /**
   * ��ȡ���ҵ��ַ���
   * 
   * @return ���ҵ��ַ���
   */
  public String getFindText() {
    return this.strFind;
  }

  /**
   * ���ò��ҵ��ַ���
   * 
   * @param strFind
   *          ���ҵ��ַ���
   * @param isUpdate
   *          �Ƿ�ͬ�������ı������ʾ������Ϊtrue��������Ϊfalse
   */
  public void setFindText(String strFind, boolean isUpdate) {
    this.strFind = strFind;
    if (isUpdate) {
      this.txtFindTextF.setText(strFind);
      this.txtFindTextR.setText(strFind);
      if (this.getTabbedIndex() == 0) {
        this.txtFindTextF.selectAll();
      } else if (this.getTabbedIndex() == 1) {
        this.txtFindTextR.selectAll();
      }
    }
  }

  /**
   * ��ȡ�����滻���ַ���
   * 
   * @return �����滻���ַ���
   */
  public String getReplaceText() {
    return this.txtReplaceTextR.getText();
  }

  /**
   * ���������滻���ַ���
   * 
   * @param strReplace
   *          �����滻���ַ���
   */
  public void setReplaceText(String strReplace) {
    this.txtReplaceTextR.setText(strReplace);
  }

  /**
   * Ϊ��������ÿ�ݼ�
   */
  private void setMnemonic() {
    // ����
    this.chkNotIgnoreCaseF.setMnemonic('C');
    this.btnFindF.setMnemonic('F');
    this.btnCountF.setMnemonic('T');
    this.radFindUpF.setMnemonic('U');
    this.radFindDownF.setMnemonic('D');
    // �滻
    this.chkNotIgnoreCaseR.setMnemonic('C');
    this.btnFindR.setMnemonic('F');
    this.btnReplaceR.setMnemonic('R');
    this.btnReplaceAllR.setMnemonic('A');
    this.radFindUpR.setMnemonic('U');
    this.radFindDownR.setMnemonic('D');
  }

  /**
   * Ϊ��������Ӽ�����
   */
  private void addListeners() {
    this.tpnMain.addChangeListener(this);
    // ����
    this.txtFindTextF.addCaretListener(this);
    this.btnFindF.addActionListener(this);
    this.btnCountF.addActionListener(this);
    this.btnCancelF.addActionListener(this);
    this.radFindDownF.addActionListener(this);
    this.radFindUpF.addActionListener(this);
    this.chkNotIgnoreCaseF.addActionListener(this);
    this.txtFindTextF.addKeyListener(this.keyAdapter);
    this.chkNotIgnoreCaseF.addKeyListener(this.keyAdapter);
    this.radFindDownF.addKeyListener(this.keyAdapter);
    this.radFindUpF.addKeyListener(this.keyAdapter);
    this.btnCancelF.addKeyListener(this.buttonKeyAdapter);
    this.btnFindF.addKeyListener(this.buttonKeyAdapter);
    this.btnCountF.addKeyListener(this.buttonKeyAdapter);
    // �滻
    this.txtFindTextR.addCaretListener(this);
    this.btnFindR.addActionListener(this);
    this.btnReplaceR.addActionListener(this);
    this.btnReplaceAllR.addActionListener(this);
    this.btnCancelR.addActionListener(this);
    this.radFindDownR.addActionListener(this);
    this.radFindUpR.addActionListener(this);
    this.chkNotIgnoreCaseR.addActionListener(this);
    this.txtFindTextR.addKeyListener(this.keyAdapter);
    this.txtReplaceTextR.addKeyListener(this.keyAdapter);
    this.chkNotIgnoreCaseR.addKeyListener(this.keyAdapter);
    this.radFindDownR.addKeyListener(this.keyAdapter);
    this.radFindUpR.addKeyListener(this.keyAdapter);
    this.btnCancelR.addKeyListener(this.buttonKeyAdapter);
    this.btnReplaceR.addKeyListener(this.buttonKeyAdapter);
    this.btnFindR.addKeyListener(this.buttonKeyAdapter);
    this.btnReplaceAllR.addKeyListener(this.buttonKeyAdapter);
  }

  /**
   * Ϊ����������¼��Ĵ�������
   */
  public void actionPerformed(ActionEvent e) {
    // ����
    if (this.btnCancelF.equals(e.getSource())) {
      this.onCancel();
    } else if (this.btnFindF.equals(e.getSource())) {
      this.onEnter();
    } else if (this.btnCountF.equals(e.getSource())) {
      this.getTextCount();
    } else if (this.chkNotIgnoreCaseF.equals(e.getSource())) {
      boolean selected = this.chkNotIgnoreCaseF.isSelected();
      this.isIgnoreCase = !selected;
      this.chkNotIgnoreCaseR.setSelected(selected);
    } else if (this.radFindDownF.equals(e.getSource())) {
      this.isFindDown = true;
      this.radFindDownR.setSelected(true);
    } else if (this.radFindUpF.equals(e.getSource())) {
      this.isFindDown = false;
      this.radFindUpR.setSelected(true);
    }
    // �滻
    else if (this.btnCancelR.equals(e.getSource())) {
      this.onCancel();
    } else if (this.btnFindR.equals(e.getSource())) {
      this.findText(this.isFindDown);
    } else if (this.btnReplaceR.equals(e.getSource())) {
      this.onEnter();
    } else if (this.btnReplaceAllR.equals(e.getSource())) {
      this.replaceAllText();
    } else if (this.chkNotIgnoreCaseR.equals(e.getSource())) {
      boolean selected = this.chkNotIgnoreCaseR.isSelected();
      this.isIgnoreCase = !selected;
      this.chkNotIgnoreCaseF.setSelected(selected);
    } else if (this.radFindDownR.equals(e.getSource())) {
      this.isFindDown = true;
      this.radFindDownF.setSelected(true);
    } else if (this.radFindUpR.equals(e.getSource())) {
      this.isFindDown = false;
      this.radFindUpF.setSelected(true);
    }
  }

  /**
   * Ĭ�ϵġ�ȡ������������
   */
  public void onCancel() {
    this.dispose();
  }

  /**
   * Ĭ�ϵġ�ȷ������������
   */
  public void onEnter() {
    if (this.tpnMain.getSelectedIndex() == 0) {
      this.findText(this.isFindDown);
    } else {
      this.replaceText();
    }
  }

  /**
   * �����ַ���
   * 
   * @param isFindDown
   *          ���ҵķ���������²�����Ϊtrue����֮��Ϊfalse
   * @return ���ҽ����������ҳɹ�����true����֮��Ϊfalse
   */
  public boolean findText(boolean isFindDown) {
    if (this.strFind != null && !this.strFind.isEmpty()) {
      int index = Util.findText(this.strFind, this.txaSource, isFindDown,
          this.isIgnoreCase);
      if (index >= 0) {
        this.txaSource.select(index, index + this.strFind.length());
        return true;
      } else {
        JOptionPane.showMessageDialog(this, "�Ҳ���\"" + this.strFind + "\"",
            Util.SOFTWARE, JOptionPane.NO_OPTION);
      }
    }
    return false;
  }

  /**
   * �滻�ַ���
   */
  private void replaceText() {
    boolean isEquals = false;
    String strSel = this.txaSource.getSelectedText();
    if (strSel != null) {
      if (this.isIgnoreCase) {
        if (strSel.equalsIgnoreCase(this.txtFindTextR.getText())) {
          isEquals = true;
        }
      } else {
        if (strSel.equals(this.txtFindTextR.getText())) {
          isEquals = true;
        }
      }
    }
    if (isEquals) {
      this.txaSource.replaceSelection(this.txtReplaceTextR.getText());
    }
    this.findText(this.isFindDown);
  }

  /**
   * �滻�����ַ���
   */
  private void replaceAllText() {
    String strFindText = this.txtFindTextR.getText();
    StringBuilder stbFindTextTemp = new StringBuilder(strFindText);
    String strReplaceText = this.txtReplaceTextR.getText();
    StringBuilder stbTextAll = new StringBuilder(this.txaSource.getText()); //
    StringBuilder stbTextAllTemp = new StringBuilder(stbTextAll); //
    if (strFindText != null && strFindText.length() > 0) {
      int caretPos = 0;
      int index = 0;
      int times = 0; // ѭ������
      if (this.isIgnoreCase) {
        stbFindTextTemp = new StringBuilder(stbFindTextTemp.toString()
            .toLowerCase());
        stbTextAllTemp = new StringBuilder(stbTextAllTemp.toString()
            .toLowerCase());
      }
      while (caretPos >= 0) {
        index = stbTextAllTemp.indexOf(stbFindTextTemp.toString(), caretPos);
        if (index >= 0) {
          stbTextAll.replace(index, index + stbFindTextTemp.length(),
              strReplaceText);
          caretPos = index + strReplaceText.length();
          stbTextAllTemp = new StringBuilder(stbTextAll); //
          if (this.isIgnoreCase) {
            stbTextAllTemp = new StringBuilder(stbTextAllTemp.toString()
                .toLowerCase());
          }
        } else {
          break;
        }
        times++;
      }
      if (times > 0) {
        this.txaSource.setText(stbTextAll.toString());
        this.txaSource.setCaretPosition(0);
        JOptionPane.showMessageDialog(this, "���滻 " + times + " ����",
            Util.SOFTWARE, JOptionPane.NO_OPTION);
      } else {
        JOptionPane.showMessageDialog(this, "�Ҳ���\"" + this.strFind + "\"",
            Util.SOFTWARE, JOptionPane.NO_OPTION);
      }
    }
  }

  /**
   * "ͳ�ƴ���"�Ĵ�������
   */
  private void getTextCount() {
    if (this.strFind == null || this.strFind.isEmpty()) {
      return;
    }
    String strText = this.txaSource.getText();
    String strFindTemp = this.strFind;
    if (this.isIgnoreCase) {
      strFindTemp = strFindTemp.toLowerCase();
      strText = strText.toLowerCase();
    }
    int index = strText.indexOf(strFindTemp);
    int times = 0; // �ַ������ִ���
    while (index >= 0) {
      index = strText.indexOf(strFindTemp, index + strFindTemp.length());
      times++;
    }
    JOptionPane.showMessageDialog(this, "���ҵ� " + times + " ����", Util.SOFTWARE,
        JOptionPane.NO_OPTION);
  }

  /**
   * ���ı���Ĺ�귢���仯ʱ���������¼�
   */
  public void caretUpdate(CaretEvent e) {
    // ����
    if (this.txtFindTextF.equals(e.getSource())) {
      this.strFind = this.txtFindTextF.getText();
      if (this.strFind.isEmpty()) {
        this.btnFindF.setEnabled(false);
        this.btnCountF.setEnabled(false);
      } else {
        this.btnFindF.setEnabled(true);
        this.btnCountF.setEnabled(true);
      }
    }
    // �滻
    else if (this.txtFindTextR.equals(e.getSource())) {
      this.strFind = this.txtFindTextR.getText();
      if (this.strFind.isEmpty()) {
        this.btnFindR.setEnabled(false);
        this.btnReplaceR.setEnabled(false);
        this.btnReplaceAllR.setEnabled(false);
      } else {
        this.btnFindR.setEnabled(true);
        this.btnReplaceR.setEnabled(true);
        this.btnReplaceAllR.setEnabled(true);
      }
    }
  }

  /**
   * ��ѡ��ı䵱ǰ��ͼʱ����
   */
  public void stateChanged(ChangeEvent e) {
    if (this.tpnMain.getSelectedIndex() == 0) {
      this.setTitle(this.tpnMain.getTitleAt(0));
      this.txtFindTextF.setText(this.strFind);
      this.txtFindTextF.selectAll();
    } else if (this.tpnMain.getSelectedIndex() == 1) {
      this.setTitle(this.tpnMain.getTitleAt(1));
      this.txtFindTextR.setText(this.strFind);
      this.txtFindTextR.selectAll();
    }
  }
}