package ManagerGUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class Managerpanel{

	//frame���� �гε�
	JFrame frame = new JFrame();
	CardLayout cardLayout = new CardLayout(0,0);
	JPanel mainpanel = new JPanel();
	JPanel buttonpanel = new JPanel();
	JLabel mangerID = new JLabel(); //������ �α��� ���̵� ǥ���� ��
	JLabel mangerID2 = new JLabel(); //������ �α��� ���̵� ǥ���� ��
	
	//������ UI
	JPanel stockPanel = new JPanel();
	JPanel Inner_stock1 = new JPanel();//������ Id�� ���ִ� �г�
	JPanel Inner_stock2 = new JPanel();// ǰ��,����,�뿩������ ���ִ� �󺧰�, ��ǰ�� �߰���ų �ؽ�Ʈ�ʵ尡 ���ִ� �� ũ�� ����
	JPanel Inner_stock3 = new JPanel();//��� �߰� ������ ���ִ� �г�
	JPanel Inner_stock4 = new JPanel();//������ ���̺��� ���ִ� �г�
	JTextField t1 = new JTextField();//ǰ���� �������ִ� JTextField
	JTextField t2 = new JTextField();//������ �������ִ� JTextField
	JTextField t3 = new JTextField();//�뿩���ɼ����� �������ִ� JTextField
	JLabel l1 = new JLabel("ǰ��");//������ ǰ�� ��
	JLabel l2 = new JLabel("����");//������ ���� ��
	JLabel l3 = new JLabel("�뿩 ����");//������ �뿩���� ��
	JButton stockbtn1 = new JButton("�߰�"); //��� �߰���ư
	JButton stockbtn2 = new JButton("����");//��� ������ư
	JTable stocktable;//������ ���̺�
	JScrollPane stock_sc;//�������� ������ JScrollPane
	DefaultTableModel model1;//���̺�  �߰� ,����,���� �� ���� �����ϰ� �ϱ����ؼ� ����ϴ� DefaultTableModel
    String header [] = {"ǰ��","����","�뿩����"};
    String contents[][];
	
    //������Ȳ ���� UI
	JPanel personal_day = new JPanel();
	JPanel Inner_day1 = new JPanel();//������Ȳ�� �����ִ� JTable�� ���ִ� �г�
	JPanel Inner_day2 = new JPanel();//������Ʈ��ư�� �����Ǿ��ִ� �г�
	JButton daybtn1 = new JButton("������Ʈ");//������Ȳ�� �ǽð����� ������Ʈ�Ҽ��ִ� ��ư
	JTable table;//������Ȳ ���̺�
	JScrollPane sc;
	DefaultTableModel model2;
	String header2 [] = {"��¥","�ð�","��ID"};
	String contents2 [][];
	
	
	
	//�޹��� ���� UI
	JPanel Inner_day3 = new JPanel();// h1�гΰ� h2�� �г��� ������ �г�
	JPanel h1 = new JPanel();//�߰� ���� ��ư�� �޹����� �������ִ� JTextField�� �ִ� �г�
	JPanel h2 = new JPanel();//�޹����� �����ִ� ���̺��� ������ �г�
	JTextField pdt;//�޹����� ���� �� �ִ� JTextField
	JTable table3;//�޹����� �����ִ� ���̺�
	JScrollPane sc3;
	DefaultTableModel model3;
	String header3 [] = {"�޹��� ��Ȳ"};
	String contents3 [][];
	JButton daybtn2 = new JButton("�޹��� ����");
	JButton daybtn3 = new JButton("����");

	
	//�����гι�ư
	JButton b1 = new JButton("��� ����");
	JButton b2 = new JButton("���� ��Ȳ");
	JButton b3 = new JButton("�޹���");

	
	public void holl() {//�޹��� �޼ҵ�
		
		
		pdt = new JTextField("0000-00-00");//�޹����� �������ִ� JTextField ex) 0000-00-00(�⵵ -��-��¥)

		//�߰� ���� ��ư�� �޹����� �������ִ� JTextField ����
		h1.add(pdt);
		h1.add(daybtn2);
		h1.add(daybtn3);
		
		//JTable ����
		contents3 = new String[0][0];
		model3 = new DefaultTableModel(contents3,header3);
		table3 = new JTable(model3);
		sc3 = new JScrollPane(table3);
		sc3.setPreferredSize(new Dimension(300,300));///��ũ�� ũ�� ����
		
		h2.add(sc3);
		
		Inner_day3.setLayout(new BorderLayout());
		Inner_day3.setBorder(new EmptyBorder(5, 5, 5, 5));

		Inner_day3.add(h1,BorderLayout.SOUTH);// �߰�������ư���ִ� �г� SOUTH�� ����
		Inner_day3.add(h2,BorderLayout.CENTER);//�޹����� �����ִ� JTable CENTER�μ���


	}
	
	public void stock() {//������ �żҵ�
		
		
		//JTable ����
	    contents= new String[0][0];
		model1 = new DefaultTableModel(contents,header);//DefaultTableModel�� JTable�� �� ������� �������� �־���
		stocktable = new JTable(model1);//JTable�� ������� �������� �� DefaultTableModel�� JTable��ü�� �־���
		stock_sc = new JScrollPane(stocktable);//JScrollPane�� JTable����
		Inner_stock4.add(stock_sc);//��ũ�ѿ� ���̺� ����

		Inner_stock1.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
		Inner_stock1.setBorder(new EmptyBorder(10, 10, 10, 10));
		Inner_stock1.add(mangerID);//������ ���̵� ǥ�� ��

		// ǰ��,����,�뿩������ ���ִ� �󺧰�, ��ǰ�� �߰���ų �ؽ�Ʈ�ʵ尡 ���ִ� �� ����
		Inner_stock2.setPreferredSize(new Dimension(300, 400));
		Inner_stock2.setLayout(new GridLayout(3,2,30,50));
		Inner_stock2.setBorder(new EmptyBorder(10, 10, 10, 10));
		Inner_stock2.add(l1);
		Inner_stock2.add(t1);
		Inner_stock2.add(l2);
		Inner_stock2.add(t2);
		Inner_stock2.add(l3);
		Inner_stock2.add(t3);

		//��ǰ �߰����� ��ư ����
		Inner_stock3.setBorder(new EmptyBorder(10, 10, 10, 10));
		Inner_stock3.add(stockbtn1);
		Inner_stock3.add(stockbtn2);


		stockPanel.setLayout(new BorderLayout());//stockPanel�� BorderLayout���� ����
		stockPanel.add(Inner_stock1,BorderLayout.NORTH);//������ ���̵� ǥ�� ���̵��ִ� �г� NORTH����
		stockPanel.add(Inner_stock2,BorderLayout.WEST);//ǰ��,����,�뿩������ ���ִ� �󺧰�, ��ǰ�� �߰���ų �ؽ�Ʈ�ʵ尡 ���ִ� �� WEST���� ����
		stockPanel.add(Inner_stock3,BorderLayout.SOUTH);//��ư�̵��ִ� �г� SOUTH ����
		stockPanel.add(Inner_stock4,BorderLayout.CENTER);//JTabl�� ���ִ� �г� CENTER ����



	}

	public void day() {//���� ��Ȳ �żҵ�

		//JTable ����
		contents2 = new String[0][0];
		model2 = new DefaultTableModel(contents2,header2);//DefaultTableModel�� JTable�� �� ������� �������� �־���
		table = new JTable(model2);//JTable�� ������� �������� �� DefaultTableModel�� JTable��ü�� �־���
		sc = new JScrollPane(table);//��ũ�ѿ� ���̺� ����
		
		Inner_day1.setBorder(new EmptyBorder(5, 5, 5, 5));//�����г� �ȿ� padding 5�� ����
		Inner_day1.add(mangerID2);//�α����� ���̵� �����ִ� ���� ����
		Inner_day1.add(daybtn1);//������Ʈ ��ư ����
		
		sc.setPreferredSize(new Dimension(400,300));///��ũ�� ũ�� ����
		Inner_day2.add(sc);
		
		
		personal_day.setLayout(new BorderLayout());//������Ȳ �г� ���̾ƿ� BorderLayout����
		personal_day.add(Inner_day1,BorderLayout.NORTH);//������Ʈ��ư���ִ� �г��� ���ʿ� ����
		personal_day.add(Inner_day2,BorderLayout.CENTER);//������Ȳ�������ִ� ���̺��� ���Ϳ� ����	
		
	}



	public Managerpanel(){//������
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout(0,0));//frame BorderLayout ���� 

		buttonpanel.add(b1);
		buttonpanel.add(b2);
		buttonpanel.add(b3);

		mainpanel.setBorder(new EmptyBorder(10, 10, 10, 10));//�����г� �ȿ� padding 10�� ����


		mainpanel.setBackground(Color.BLACK);
		frame.add(mainpanel,BorderLayout.CENTER);//mainpanel ���� ����
		mainpanel.setLayout(cardLayout);//mainpanel�� cardLayout����
		frame.add(buttonpanel,BorderLayout.NORTH);//buttonpanel ���� ����
		mainpanel.add(stockPanel,"stock");//mainpanel cardLayout�� ��� ���� �г�,������Ȳ �г�,�޹��� �г� ����
		mainpanel.add(personal_day,"personal_day");		
		mainpanel.add(Inner_day3,"hol");
		
		
		stock();//������ �żҵ�
		day();//������ȯ �޼ҵ�
		holl();//�޹��� �żҵ�

		frame.setTitle("������ ȭ��");
		frame.setSize(900, 500);
		frame.setVisible(true);

	}
	
		public void addButtonActionListener(ActionListener listener) {//�׼Ǹ����� �߰� �޼ҵ�
			b1.addActionListener(listener);
			b2.addActionListener(listener);
			b3.addActionListener(listener);
			stockbtn1.addActionListener(listener);
			stockbtn2.addActionListener(listener);
			daybtn1.addActionListener(listener);
			daybtn2.addActionListener(listener);
			daybtn3.addActionListener(listener);
			pdt.addActionListener(listener);
			
		}
		
		public void addTableModelListener(TableModelListener listener) {//���̺� ������ ����
			
			stocktable.getModel().addTableModelListener(listener); //������ ������
			table3.getModel().addTableModelListener(listener);//�޹��� ������

		}
		
	
}// Managerpanel close
