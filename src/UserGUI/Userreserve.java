package UserGUI;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Userreserve extends JFrame{
	public JPanel mainPanel,topPanel,MapPanel,bottomPanel;
	protected JTextField num;
	protected JComboBox<String> ground,days,time;
	protected JLabel Lground,Ldays,Ltime,Lnum;
	protected JButton statebtn,reservebtn,inquirebtn, solbtn, teambtn;
	public State state;
	private JPanel btnpanel;
	public JPanel p;
	public JPanel solpanel;
	protected JButton togeterbtn;
	
	CardLayout cardLayout = new CardLayout(0,0);	
	
	//���̺� ����2
	JTable table2;
	JScrollPane sc2;
	DefaultTableModel model2;
    String header2 [] = {"ǲ����","��¥","�ð�","�뺴��"};
	String contents2 [][];
	
	public Userreserve(State state) {
		this.setLayout(null);
		this.state = state;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		p = new JPanel();
		
		//�����ӿ� ��ĥ �гε�
	    topPanel = new JPanel();
		MapPanel =new JPanel();
		bottomPanel =new JPanel();
		mainPanel = new JPanel();
		
		//���гο� ��ĥ �󺧵�
		Ldays = new JLabel("��¥");
		Ltime = new JLabel("�ð�");
		Lnum= new JLabel("�뺴 �ο���");
		Lground = new JLabel("ǲ����");
		
		days = new JComboBox<String>();
		time = new JComboBox<String>();
		ground = new JComboBox<String>();
		
		num = new JTextField("0");
		statebtn = new JButton("�� �����Ȳ");
		reservebtn = new JButton("����");
		
		//��Ʈũ�ø� �־���.
		days.setFont(days.getFont().deriveFont(10f));
		time.setFont(time.getFont().deriveFont(10f));
		num.setFont(num.getFont().deriveFont(10f));
		Ldays.setFont(days.getFont().deriveFont(10f));
		Ltime.setFont(time.getFont().deriveFont(10f));
		Lnum.setFont(num.getFont().deriveFont(10f));
		Lground.setFont(num.getFont().deriveFont(10f));
		//������ ��ġ,ũ�� ��������
		Lground.setBounds(10, 20, 50, 30);
		ground.setBounds(100, 20, 100, 30);
		Ldays.setBounds(250, 20, 50, 30);
		days.setBounds(300, 20, 100, 30);
		Ltime.setBounds(450, 20, 50, 30);
		time.setBounds(500, 20, 100, 30);
		Lnum.setBounds(700, 20, 50, 30);
		num.setBounds(770, 20, 100, 30);
		
		topPanel.add(Ldays);
		topPanel.add(days);
		topPanel.add(Ltime);
		topPanel.add(time);
		topPanel.add(Lnum);
		topPanel.add(num);
		topPanel.add(ground);
		topPanel.add(Lground);
		
		statebtn.setBounds(0, 0, 130, 30);
		reservebtn.setBounds(450, 0, 130, 30);
		
		bottomPanel.add(statebtn);
		bottomPanel.add(reservebtn);
		
		//�г��� ������ ��ġ,ũ������
		topPanel.setLayout(null);
		topPanel.setBounds(0, 0, 1000, 50);
		
		MapPanel.setBounds(0, 0, 1000, 400);
		bottomPanel.setLayout(null);
		bottomPanel.setBounds(0, 400, 1000, 100);
		MapPanel.setBackground(Color.BLACK);
		
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 50, 900, 600);
		//�����ӿ� �гκ���
		mainPanel.add(topPanel);
		mainPanel.add(MapPanel);
		mainPanel.add(bottomPanel);
		
		
		btnpanel = new JPanel();
		solbtn = new JButton("�뺴");
		teambtn = new JButton("��");
		solbtn.setBounds(380, 0, 70, 30);
		teambtn.setBounds(450, 0, 70, 30);
		btnpanel.add(solbtn);
		btnpanel.add(teambtn);
		btnpanel.setLayout(null);
		btnpanel.setBounds(0, 0, 900, 50);
		
		
		contents2 = new String[0][0];
	    model2 = new DefaultTableModel(contents2,header2);
	    table2 = new JTable(model2);
	    sc2 = new JScrollPane(table2);
	    //sc2.setLayout(null);
	    sc2.setBounds(100, 100, 700, 300);
		solpanel = new JPanel();
		togeterbtn =new JButton("����");
		
		togeterbtn.setBounds(700, 400, 70, 30);
		solpanel.setLayout(null);
		solpanel.setBounds(0, 50, 900, 600);
		solpanel.add(sc2);
		solpanel.add(togeterbtn);
		
		
		
		
		
		
		
		
		//p.add(mainPanel);
		this.add(btnpanel);
		
		
		
		p.setLayout(cardLayout);
		p.add(mainPanel,"main");
		p.add(solpanel,"sol");
		

		
		p.setBounds(0, 50, 900, 600);
		//p.setLayout(null);
		setSize(900,600);
		setVisible(false);
		this.add(p);
		//������ ������ ���� �����Ӹ� ������.
	
		

	}

	
	//��ư�����ʵ��� ����� ��Ʈ�� Ŭ������ ���.
	public void addButtonActionListener(ActionListener listener) {
		
		statebtn.addActionListener(listener);
		reservebtn.addActionListener(listener);
		ground.addActionListener(listener);
		days.addActionListener(listener);
		time.addActionListener(listener);
		solbtn.addActionListener(listener);
		teambtn.addActionListener(listener);
		togeterbtn.addActionListener(listener);
	}
	
}
