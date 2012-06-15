package com.xiboliya.snowpad;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * "����"�Ի���
 * 
 * @author chen
 * 
 */
public class AboutDialog extends BaseDialog implements ActionListener {
  private static final long serialVersionUID = 1L;
  private int lines = 1; // ��ʾ��ǩ������
  private GridLayout layout = null;
  private JPanel pnlMain = (JPanel) this.getContentPane();
  private JPanel pnlCenter = new JPanel();
  private JPanel pnlEast = new JPanel();
  private JPanel pnlWest = new JPanel();
  private JPanel pnlSouth = new JPanel();
  private JPanel pnlNorth = new JPanel();
  private JLabel lblWest = new JLabel(" ");
  private JLabel lblNorth = new JLabel();
  private JLabel lblEast = new JLabel(" ");
  private JButton btnOk = new JButton("ȷ��");
  private BaseKeyAdapter buttonKeyAdapter = new BaseKeyAdapter(this, false);
  private LinkedList<JLabel> labelList = new LinkedList<JLabel>(); // �����ʾ��ǩ������

  public AboutDialog(JFrame owner) {
    this(owner, true, 1);
  }

  public AboutDialog(JFrame owner, boolean modal) {
    this(owner, modal, 1);
  }

  public AboutDialog(JFrame owner, boolean modal, int lines) {
    super(owner, modal);
    this.checkLines(lines);
    this.init();
    this.addListeners();
  }

  public AboutDialog(JFrame owner, boolean modal, String[] arrStrLabel) {
    this(owner, modal, arrStrLabel.length);
    if (arrStrLabel.length > 0) {
      for (int i = 0; i < this.lines; i++) {
        this.labelList.get(i).setText(arrStrLabel[i]);
      }
    }
  }

  public AboutDialog(JFrame owner, boolean modal, String[] arrStrLabel,
      ImageIcon icon) {
    this(owner, modal, arrStrLabel);
    if (icon != null) {
      this.lblNorth.setIcon(icon);
    }
  }

  /**
   * ��ʼ������
   */
  private void init() {
    this.setTitle("����");
    this.pnlWest.add(this.lblWest);
    this.pnlNorth.add(this.lblNorth);
    this.pnlEast.add(this.lblEast);
    this.pnlSouth.add(this.btnOk);
    this.pnlMain.add(this.pnlWest, BorderLayout.WEST);
    this.pnlMain.add(this.pnlNorth, BorderLayout.NORTH);
    this.pnlMain.add(this.pnlEast, BorderLayout.EAST);
    this.pnlMain.add(this.pnlSouth, BorderLayout.SOUTH);
    this.layout = new GridLayout(this.lines, 1);
    this.pnlCenter.setLayout(this.layout);
    this.initLabelList();
    this.pnlMain.add(this.pnlCenter, BorderLayout.CENTER);
  }

  /**
   * ��ʼ������
   */
  private void initLabelList() {
    for (int i = 0; i < this.lines; i++) {
      this.appendLabelList();
    }
  }

  /**
   * ׷��һ���յ���ʾ��ǩ
   * 
   * @return ���׷�ӳɹ��򷵻�true�����򷵻�false
   */
  private boolean appendLabelList() {
    if (this.labelList.size() < this.lines) {
      JLabel lblTemp = new JLabel(" ");
      this.labelList.add(lblTemp);
      this.pnlCenter.add(lblTemp);
      return true;
    }
    return false;
  }

  /**
   * ��ʽ����ʾ��ǩ����
   * 
   * @param lines
   *          ��ʾ��ǩ������
   * @return ������ʽ�������ʾ��ǩ����
   */
  private int checkLines(int lines) {
    if (lines <= 0) {
      this.lines = 1;
    } else {
      this.lines = lines;
    }
    return this.lines;
  }

  /**
   * ��ʽ���±�
   * 
   * @param index
   *          �±�
   * @return ������ʽ������±�
   */
  private int checkIndex(int index) {
    if (index < 0) {
      index = 0;
    } else if (index >= this.labelList.size()) {
      index = this.labelList.size() - 1;
    }
    return index;
  }

  /**
   * �����¼�������
   */
  private void addListeners() {
    this.btnOk.addActionListener(this);
    this.btnOk.addKeyListener(this.buttonKeyAdapter);
  }

  /**
   * Ϊָ���±�ı�ǩ��������
   * 
   * @param index
   *          ��ǩ���±�
   * @param strLink
   *          �����ַ���
   */
  public void addLinkByIndex(int index, final String strLink) {
    if (index < 0 || index >= lines || strLink == null || strLink.isEmpty()) {
      return;
    }
    JLabel lblTemp = this.labelList.get(index);
    lblTemp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lblTemp.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        try {
          // ����������˱�ǩʱ��������ϵͳ�������ҳ
          Runtime.getRuntime().exec("cmd /c start " + strLink);
        } catch (Exception x) {
          // �������ϵͳ��֧�ִ�������׳��쳣
          x.printStackTrace();
        }
      }
    });
  }

  /**
   * Ϊָ���±�ı�ǩ�����ַ���
   * 
   * @param index
   *          ��ǩ���±�
   * @param strLabel
   *          ���õ��ַ���
   */
  public void setStringByIndex(int index, String strLabel) {
    this.labelList.get(this.checkIndex(index)).setText(strLabel);
  }

  /**
   * ��ȡָ���±�ı�ǩ���ַ���
   * 
   * @param index
   *          ��ǩ���±�
   * @return ��ǩ���ַ���
   */
  public String getStringByIndex(int index) {
    return this.labelList.get(this.checkIndex(index)).getText();
  }

  /**
   * Ϊ����������¼��Ĵ�������
   */
  public void actionPerformed(ActionEvent e) {
    if (this.btnOk.equals(e.getSource())) {
      this.onCancel();
    }
  }

  /**
   * Ĭ�ϵ�"ȷ��"��������
   */
  public void onEnter() {
    this.onCancel();
  }

  /**
   * Ĭ�ϵ�"ȡ��"��������
   */
  public void onCancel() {
    this.dispose();
  }
}