package com.xiboliya.snowpad;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Enumeration;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.JTextComponent;

/**
 * ʵ�ù����࣬���������õĸ������Ժͷ��������Ϊfinal���ͣ�ʹ���಻�ɱ��̳�
 * 
 * @author chen
 * 
 */
public final class Util {
  public static final String SOFTWARE = "��ѩ���±�"; // ��������
  public static final String VERSION = "V2.2"; // �����汾��
  public static final String TXT_EXT = ".txt"; // txt�ļ�����չ��
  public static final String FILE_SEPARATOR = System.getProperty(
      "file.separator", "/"); // ��ǰ����ϵͳ���ļ��ָ���
  public static final String LINE_SEPARATOR = System.getProperty(
      "line.separator", "\n"); // ��ǰ����ϵͳ���зָ���
  public static final int BUFFER_LENGTH = 1024; // �������Ĵ�С
  public static final Font GLOBAL_FONT = new Font("����", Font.PLAIN, 12); // ȫ�ֵ�Ĭ������
  public static final Font TEXT_FONT = new Font("����", Font.PLAIN, 14); // �ı����Ĭ������
  public static final int MIN_FONT_SIZE = 8; // ������Сֵ
  public static final int MAX_FONT_SIZE = 100; // �������ֵ
  public static final int MIN_TABSIZE = 1; // Tab�ַ���Сֵ
  public static final int MAX_TABSIZE = 99; // Tab�ַ����ֵ
  public static final int DEFAULT_TABSIZE = 4; // Tab�ַ�Ĭ��ֵ
  public static final String FILE_HISTORY = "FileHistory"; // ���ڱ�ʶ����༭���ļ�
  public static final int FILE_HISTORY_MAX = 10; // ����༭�ļ������洢����
  public static final int DEFAULT_UNDO_INDEX = 0; // ������ʶ����Ĭ��ֵ
  public static final String[] FONT_FAMILY_NAMES = java.awt.GraphicsEnvironment
      .getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); // ��ȡϵͳ��������������б�
  public static final int INSERT_MAX_COLUMN = 10; // �����ַ�������������
  public static final int INSERT_MAX_ROW = 10; // �����ַ�������������
  public static final int INSERT_MAX_ELEMENT = INSERT_MAX_COLUMN
      * INSERT_MAX_ROW; // �����ַ���������Ԫ����
  public static final int INPUT_HEIGHT = 22; // ���������ĸ߶�
  public static final int VIEW_HEIGHT = 18; // ��ǩ����ѡ��ť����ѡ��ĸ߶�
  public static final int BUTTON_HEIGHT = 23; // ��ť�ĸ߶�
  public static final Font VIEW_FONT = new Font("����", Font.PLAIN, 80); // �����ַ�������Ԥ����ǩ������
  public static final String INSERT_SPECIAL = "�����������졨�������������������������I�G�����ߩh�i�l�m�j�k�|�}�~���ѡ��������I�J�L�K�ΨO���ܨM��"; // �������
  public static final String INSERT_PUNCTUATION = "�����������������U���E��F���������o�p�q?�r�s�t�u���C�򡪦����n������������񡲡���㡾��������硴����塸����顺�����v�w�x�y�z�{�������������A�@��"; // ������
  public static final String INSERT_MATH = "�֡ԡ٣��ܡݣ����ڡۡˡ��������£��ҡӡءޡġšơǡȡɡʡߡ�͡ΡϡСѡաס̨Q�R�P�ԩ������������N�S�S�R"; // ��ѧ����
  public static final String INSERT_UNIT = "����磤����꣥����H�����멈�T�L�M�N�Q�O�J�K�P���"; // ��λ����
  public static final String INSERT_DIGIT = "�����������������������������������¢âĢŢƢǢȢɢʢˢ̢͢΢ϢТѢҢӢԢբ֢עآ٢ڢۢܢݢޢߢ����������������������������������������������������"; // ���ַ���
  public static final String INSERT_PINYIN = "����������������������������������������������������������������"; // ƴ������
  public static final String CTRL_Z = "Ctrl+Z"; // ��ϼ�Ctrl+Z���ַ���
  public static final String CTRL_Y = "Ctrl+Y"; // ��ϼ�Ctrl+Y���ַ���
  public static final String TEXT_PREFIX = "*"; // �ļ��ı��޸ĵı�������ʶ��
  public static final String STYLE_PREFIX = "��"; // �ļ���ʽ�޸ĵı�������ʶ��
  public static final String STATE_CHARS = "Chars:"; // ״̬����ʾ��Ϣ-�ı����ַ���
  public static final String STATE_LINES = "Lines:"; // ״̬����ʾ��Ϣ-�ı�������
  public static final String STATE_CUR_LINE = "Ln:"; // ״̬����ʾ��Ϣ-��굱ǰ�к�
  public static final String STATE_CUR_COLUMN = "Col:"; // ״̬����ʾ��Ϣ-��굱ǰ�к�
  public static final String STATE_CUR_SELECT = "Sel:"; // ״̬����ʾ��Ϣ-��ǰѡ����ַ���
  public static final String STATE_LINE_STYLE = "LineStyle:"; // ״̬����ʾ��Ϣ-��ǰ���з���ʽ
  public static final String STATE_ENCODING = "Encoding:"; // ״̬����ʾ��Ϣ-��ǰ�����ʽ
  public static final String[] FILE_ENCODINGS = new String[] { "�Զ����",
      CharEncoding.ANSI.getName(), CharEncoding.UBE.getName(),
      CharEncoding.ULE.getName(), CharEncoding.UTF8.getName(),
      CharEncoding.UTF8_NO_BOM.getName(), CharEncoding.BASE.getName() }; // ѡ������ʽ������
  public static final String[] DATE_STYLES = new String[] {
      "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss:SSS", "yyyy-MM-dd KK:mm:ss",
      "yyyy-MM-dd KK:mm:ss a", "yyyy-MM-dd HH:mm:ss E",
      "yyyy-MM-dd HH:mm:ss zZ", "yyyy��MM��dd�� HHʱmm��ss��",
      "G yyyy-MM-dd HH:mm:ss E zZ", "yy-M-d H:m:s", "yyyy/MM/dd HH:mm:ss",
      "yyyy.MM.dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd", "yyyy.MM.dd",
      "yy/MM/dd", "HH:mm:ss", "KK:mm:ss a", "HH:mm:ss:SSS" }; // ʱ��/���ڸ�ʽ�ַ���
  public static final Color COLOR_HIGHLIGHT_1 = new Color(255, 0, 0, 40); // ���ڸ�����ʾ����ɫ1,���е�4��������ʾ͸���ȣ���ֵԽСԽ͸��
  public static final Color COLOR_HIGHLIGHT_2 = new Color(0, 255, 0, 40); // ���ڸ�����ʾ����ɫ2
  public static final Color COLOR_HIGHLIGHT_3 = new Color(0, 0, 255, 40); // ���ڸ�����ʾ����ɫ3
  public static final Color COLOR_HIGHLIGHT_4 = new Color(0, 255, 255, 40); // ���ڸ�����ʾ����ɫ4
  public static final Color COLOR_HIGHLIGHT_5 = new Color(255, 0, 255, 40); // ���ڸ�����ʾ����ɫ5

  /**
   * ���ڴ���Ϊ�����࣬�ʽ����췽��˽�л�
   */
  private Util() {
  }

  /**
   * ��ʽ����������ļ���
   * 
   * @param strFileName
   *          �ļ�������·��
   * @param fileFilter
   *          ��ǰ���ļ����͹�����
   * @param strExt
   *          �������ļ�����չ��
   * @return ��ʽ������ļ�
   */
  public static File checkFileName(String strFileName,
      BaseFileFilter fileFilter, String strExt) {
    if (strFileName == null || strFileName.isEmpty() || fileFilter == null
        || strExt == null || strExt.isEmpty()) {
      return null;
    }
    if (fileFilter.getExt().equalsIgnoreCase(strExt)) {
      if (!strFileName.toLowerCase().endsWith(strExt.toLowerCase())) {
        strFileName += strExt;
      }
    }
    return new File(strFileName);
  }

  /**
   * �޸����������Ĭ������
   */
  public static void setDefaultFont() {
    FontUIResource fontRes = new FontUIResource(GLOBAL_FONT);
    Enumeration<Object> keys = UIManager.getDefaults().keys();
    while (keys.hasMoreElements()) {
      Object key = keys.nextElement();
      Object value = UIManager.get(key);
      if (value instanceof FontUIResource) {
        UIManager.put(key, fontRes);
      }
    }
  }

  /**
   * ���ı�����в����ַ���
   * 
   * @param strFindText
   *          ���ҵ��ַ���
   * @param txcSource
   *          �ı����
   * @param isFindDown
   *          �Ƿ����²���
   * @param isIgnoreCase
   *          �Ƿ����ִ�Сд
   * @return ���ҵ��ַ���λ���ı�����е�����
   */
  public static int findText(String strFindText, JTextComponent txcSource,
      boolean isFindDown, boolean isIgnoreCase) {
    if (isFindDown) {
      return findDownText(strFindText, txcSource, isIgnoreCase);
    } else {
      return findUpText(strFindText, txcSource, isIgnoreCase);
    }
  }

  /**
   * ���ı���������²����ַ���
   * 
   * @param strFindText
   *          ���ҵ��ַ���
   * @param txcSource
   *          �ı����
   * @param isIgnoreCase
   *          �Ƿ����ִ�Сд
   * @return ���ҵ��ַ���λ���ı�����е�����
   */
  private static int findDownText(String strFindText, JTextComponent txcSource,
      boolean isIgnoreCase) {
    if (strFindText == null || txcSource == null || strFindText.isEmpty()
        || txcSource.getText().isEmpty()) {
      return -1;
    }
    int caretPos = txcSource.getCaretPosition();
    String strSource = txcSource.getText().substring(caretPos);
    if (isIgnoreCase) {
      strFindText = strFindText.toLowerCase();
      strSource = strSource.toLowerCase();
    }
    int index = strSource.indexOf(strFindText);
    if (index >= 0) {
      return caretPos + index;
    } else {
      return -1;
    }
  }

  /**
   * ���ı���������ϲ����ַ���
   * 
   * @param strFindText
   *          ���ҵ��ַ���
   * @param txcSource
   *          �ı����
   * @param isIgnoreCase
   *          �Ƿ����ִ�Сд
   * @return ���ҵ��ַ���λ���ı�����е�����
   */
  private static int findUpText(String strFindText, JTextComponent txcSource,
      boolean isIgnoreCase) {
    if (strFindText == null || txcSource == null || strFindText.isEmpty()
        || txcSource.getText().isEmpty()) {
      return -1;
    }
    int caretPos = txcSource.getCaretPosition();
    if (txcSource.getSelectedText() != null && isIgnoreCase) {
      if (txcSource.getSelectedText().equalsIgnoreCase(strFindText)) {
        caretPos -= strFindText.length();
      }
    } else if (txcSource.getSelectedText() != null && !isIgnoreCase) {
      if (txcSource.getSelectedText().equals(strFindText)) {
        caretPos -= strFindText.length();
      }
    }
    String strSource = txcSource.getText().substring(0, caretPos);
    if (isIgnoreCase) {
      strFindText = strFindText.toLowerCase();
      strSource = strSource.toLowerCase();
    }
    return strSource.lastIndexOf(strFindText);
  }
}