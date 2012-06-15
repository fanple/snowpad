package com.xiboliya.snowpad;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * "����"�Ի���
 * 
 * @author chen
 * 
 */
public class FontChooser extends BaseDialog implements ActionListener,
    ListSelectionListener, CaretListener {
  private static final long serialVersionUID = 1L;
  private JLabel lblFont = new JLabel("���壺");
  private JLabel lblStyle = new JLabel("���Σ�");
  private JLabel lblSize = new JLabel("��С��");
  private JLabel lblView = new JLabel("A����");
  private BaseTextField txtFont = new BaseTextField();
  private BaseTextField txtStyle = new BaseTextField();
  private BaseTextField txtSize = new BaseTextField();
  private JList listFont = new JList();
  private JList listStyle = new JList();
  private JList listSize = new JList();
  private JScrollPane srpFont = new JScrollPane(this.listFont);
  private JScrollPane srpStyle = new JScrollPane(this.listStyle);
  private JScrollPane srpSize = new JScrollPane(this.listSize);
  private JButton btnOk = new JButton("ȷ��");
  private JButton btnCancel = new JButton("ȡ��");
  private JPanel pnlMain = (JPanel) this.getContentPane();
  private BaseKeyAdapter keyAdapter = new BaseKeyAdapter(this);
  private BaseKeyAdapter buttonKeyAdapter = new BaseKeyAdapter(this, false);
  private JTextArea txaSource = null;
  private BaseMouseAdapter baseMouseAdapter = new BaseMouseAdapter();

  /**
   * ���췽��
   * 
   * @param owner
   *          ������
   * @param modal
   *          �Ƿ�Ϊģʽ����
   */
  public FontChooser(JFrame owner, boolean modal, JTextArea txaSource) {
    super(owner, modal);
    if (txaSource == null) {
      return;
    }
    this.txaSource = txaSource;
    this.setTitle("����");
    this.init();
    this.fillFontList();
    this.fillStyleList();
    this.fillSizeList();
    this.setView(false);
    this.addListeners();
    this.setSize(365, 315);
    this.setVisible(true);
  }

  /**
   * ��ʼ������
   */
  private void init() {
    this.lblView.setBorder(new TitledBorder("ʾ��"));
    this.lblView.setHorizontalAlignment(JLabel.CENTER);
    this.pnlMain.setLayout(null);
    this.lblFont.setBounds(10, 10, 140, Util.VIEW_HEIGHT);
    this.txtFont.setBounds(10, 30, 140, Util.INPUT_HEIGHT);
    this.srpFont.setBounds(10, 53, 140, 120);
    this.pnlMain.add(this.lblFont);
    this.pnlMain.add(this.txtFont);
    this.pnlMain.add(this.srpFont);
    this.lblStyle.setBounds(160, 10, 100, Util.VIEW_HEIGHT);
    this.txtStyle.setBounds(160, 30, 100, Util.INPUT_HEIGHT);
    this.srpStyle.setBounds(160, 53, 100, 120);
    this.pnlMain.add(this.lblStyle);
    this.pnlMain.add(this.txtStyle);
    this.pnlMain.add(this.srpStyle);
    this.lblSize.setBounds(270, 10, 80, Util.VIEW_HEIGHT);
    this.txtSize.setBounds(270, 30, 80, Util.INPUT_HEIGHT);
    this.srpSize.setBounds(270, 53, 80, 120);
    this.pnlMain.add(this.lblSize);
    this.pnlMain.add(this.txtSize);
    this.pnlMain.add(this.srpSize);
    this.lblView.setBounds(10, 180, 245, 100);
    this.pnlMain.add(this.lblView);
    this.btnOk.setBounds(265, 200, 80, Util.BUTTON_HEIGHT);
    this.btnCancel.setBounds(265, 240, 80, Util.BUTTON_HEIGHT);
    this.pnlMain.add(this.btnOk);
    this.pnlMain.add(this.btnCancel);
  }

  /**
   * ���"����"�б�
   */
  private void fillFontList() {
    String[] fontFamilys = Util.FONT_FAMILY_NAMES; // ��ȡϵͳ��������������б�
    this.listFont.setListData(fontFamilys);
    Font font = this.txaSource.getFont();
    this.listFont.setSelectedValue(font.getFamily(), true);
    this.listFont.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.setFontView();
  }

  /**
   * ���"����"�б�
   */
  private void fillStyleList() {
    String[] fontStyles = new String[] { "����", "����", "б��", "��б��" };
    this.listStyle.setListData(fontStyles);
    Font font = this.txaSource.getFont();
    this.listStyle.setSelectedIndex(font.getStyle());
    this.listStyle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.setStyleView();
  }

  /**
   * ���"��С"�б�
   */
  private void fillSizeList() {
    Integer[] fontSizes = new Integer[Util.MAX_FONT_SIZE - Util.MIN_FONT_SIZE
        + 1];
    for (int i = 0; i < fontSizes.length; i++) {
      fontSizes[i] = Util.MIN_FONT_SIZE + i; // Integer����Զ�װ��
    }
    this.listSize.setListData(fontSizes);
    Font font = this.txaSource.getFont();
    this.listSize.setSelectedValue(font.getSize(), true);
    this.listSize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.setSizeView();
  }

  /**
   * �����б�����ʾ
   */
  public void updateListView() {
    Font font = this.txaSource.getFont();
    this.listFont.setSelectedValue(font.getFamily(), true);
    this.listStyle.setSelectedIndex(font.getStyle());
    this.listSize.setSelectedValue(font.getSize(), true);
  }

  /**
   * ���ݵ�ǰ"����"�б���ѡ�񣬸��¶����ı������ʾ
   */
  public void setFontView() {
    String strFont = this.listFont.getSelectedValue().toString();
    if (null != strFont) {
      this.txtFont.setText(strFont);
    }
  }

  /**
   * ���ݵ�ǰ"����"�б���ѡ�񣬸��¶����ı������ʾ
   */
  public void setStyleView() {
    String strStyle = this.listStyle.getSelectedValue().toString();
    if (null != strStyle) {
      this.txtStyle.setText(strStyle);
    }
  }

  /**
   * ���ݵ�ǰ"��С"�б���ѡ�񣬸��¶����ı������ʾ
   */
  public void setSizeView() {
    String strSize = this.listSize.getSelectedValue().toString();
    if (null != strSize) {
      this.txtSize.setText(strSize);
    }
  }

  /**
   * ����ʾ�����ֺ��ı��������
   * 
   * @param isOk
   *          �û��Ƿ�����"ȷ��"��ť
   */
  private void setView(boolean isOk) {
    if (isOk) {
      this.txaSource.setFont(this.lblView.getFont());
      return;
    }
    Font font = new Font(this.listFont.getSelectedValue().toString(),
        this.listStyle.getSelectedIndex(), (Integer) this.listSize
            .getSelectedValue());
    this.lblView.setFont(font);
  }

  /**
   * ��������¼��ĳ������������Զ�����
   * 
   * @author chen
   * 
   */
  private class BaseMouseAdapter extends MouseAdapter {
    public void mouseClicked(MouseEvent e) {
      if (listFont.equals(e.getSource())) {
        txtFont.setText(listFont.getSelectedValue().toString());
      } else if (listStyle.equals(e.getSource())) {
        txtStyle.setText(listStyle.getSelectedValue().toString());
      } else if (listSize.equals(e.getSource())) {
        txtSize.setText(listSize.getSelectedValue().toString());
      }
      if (e.getClickCount() == 2) { // ���˫��ʱ��ִ�в���
        onEnter();
      }
    }
  }

  /**
   * ���Ӹ�������¼�������
   */
  private void addListeners() {
    this.btnCancel.addActionListener(this);
    this.btnOk.addActionListener(this);
    this.listFont.addListSelectionListener(this);
    this.listStyle.addListSelectionListener(this);
    this.listSize.addListSelectionListener(this);
    this.listFont.addMouseListener(this.baseMouseAdapter);
    this.listStyle.addMouseListener(this.baseMouseAdapter);
    this.listSize.addMouseListener(this.baseMouseAdapter);
    this.txtFont.addCaretListener(this);
    this.txtStyle.addCaretListener(this);
    this.txtSize.addCaretListener(this);
    // ����Ϊ���ɻ�ý����������Ӽ����¼��������û�����Esc��ʱ�ر�"����"�Ի���
    this.txtFont.addKeyListener(this.keyAdapter);
    this.txtStyle.addKeyListener(this.keyAdapter);
    this.txtSize.addKeyListener(this.keyAdapter);
    this.listFont.addKeyListener(this.keyAdapter);
    this.listStyle.addKeyListener(this.keyAdapter);
    this.listSize.addKeyListener(this.keyAdapter);
    this.btnCancel.addKeyListener(this.buttonKeyAdapter);
    this.btnOk.addKeyListener(this.buttonKeyAdapter);
  }

  /**
   * "ȡ��"��ť�Ĵ�������
   */
  public void onCancel() {
    this.dispose();
  }

  /**
   * "ȷ��"��ť�Ĵ�������
   */
  public void onEnter() {
    this.setView(true);
    this.onCancel();
  }

  /**
   * Ϊ����������¼��Ĵ�������
   */
  public void actionPerformed(ActionEvent e) {
    if (this.btnCancel.equals(e.getSource())) {
      this.onCancel();
    } else if (this.btnOk.equals(e.getSource())) {
      this.onEnter();
    }
  }

  /**
   * ���б���ı�ѡ��ʱ���������¼�
   */
  public void valueChanged(ListSelectionEvent e) {
    if (this.listFont.equals(e.getSource())) {
      if (!this.txtFont.getText().equals(this.listFont.getSelectedValue())) {
        this.txtFont.setText(this.listFont.getSelectedValue().toString());
      }
    } else if (this.listStyle.equals(e.getSource())) {
      if (!this.txtStyle.getText().equals(this.listStyle.getSelectedValue())) {
        this.txtStyle.setText(this.listStyle.getSelectedValue().toString());
      }
    } else if (this.listSize.equals(e.getSource())) {
      try {
        String strSize = this.txtSize.getText();
        if (strSize.length() > 0) {
          if (!new Integer(strSize).equals(this.listSize.getSelectedValue())) {
            this.txtSize.setText(this.listSize.getSelectedValue().toString());
          }
        }
      } catch (NumberFormatException x) {
        // ��ת�����ַ�����������
        x.printStackTrace();
      }
    }
    this.setView(false);
  }

  /**
   * ���ı���Ĺ�귢���仯ʱ���������¼�
   */
  public void caretUpdate(CaretEvent e) {
    if (this.txtFont.equals(e.getSource())) {
      this.listFont.setSelectedValue(this.txtFont.getText(), true);
    } else if (this.txtStyle.equals(e.getSource())) {
      this.listStyle.setSelectedValue(this.txtStyle.getText(), true);
    } else if (this.txtSize.equals(e.getSource())) {
      try {
        String strSize = this.txtSize.getText();
        if (strSize.length() > 0) {
          this.listSize.setSelectedValue(new Integer(strSize), true);
        }
      } catch (NumberFormatException x) {
        // ��ת�����ַ�����������
        x.printStackTrace();
      }
    }
  }
}