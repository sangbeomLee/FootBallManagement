package UserGUI;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class Userreserve extends Frame{
	private JPanel mainPanel,topPanel,MapPanel,bottomPanel;
	protected JTextField time,num;
	protected JComboBox<String> days;
	protected JLabel Ldays,Ltime,Lnum;
	protected JButton statebtn,reservebtn,inquirebtn;
	public State state;
	
	public Userreserve(State state) {
		this.setLayout(null);
		this.state = state;
		
		//�����ӿ� ��ĥ �гε�
	    topPanel = new JPanel();
		MapPanel =new JPanel();
		bottomPanel =new JPanel();
		mainPanel = new JPanel();
		
		//���гο� ��ĥ �󺧵�
		Ldays = new JLabel("��¥");
		Ltime = new JLabel("�ð�");
		Lnum= new JLabel("�ο���");
		days = new JComboBox<String>();
		time = new JTextField();
		num = new JTextField();
		statebtn = new JButton("�� �����Ȳ");
		reservebtn = new JButton("��ȸ");
		inquirebtn = new JButton("����");
		
		//��Ʈũ�ø� �־���.
		days.setFont(days.getFont().deriveFont(10f));
		time.setFont(time.getFont().deriveFont(10f));
		num.setFont(num.getFont().deriveFont(10f));
		Ldays.setFont(days.getFont().deriveFont(10f));
		Ltime.setFont(time.getFont().deriveFont(10f));
		Lnum.setFont(num.getFont().deriveFont(10f));
		
		//������ ��ġ,ũ�� ��������
		Ldays.setBounds(80, 20, 50, 50);
		days.setBounds(150, 20, 100, 50);
		Ltime.setBounds(380, 20, 50, 50);
		time.setBounds(450, 20, 100, 50);
		Lnum.setBounds(700, 20, 50, 50);
		num.setBounds(770, 20, 100, 50);
		
		topPanel.add(Ldays);
		topPanel.add(days);
		topPanel.add(Ltime);
		topPanel.add(time);
		topPanel.add(Lnum);
		topPanel.add(num);
		
		
		statebtn.setBounds(0, 0, 130, 50);
		reservebtn.setBounds(450, 0, 130, 50);
		inquirebtn.setBounds(770, 0, 130, 50);
		
		bottomPanel.add(statebtn);
		bottomPanel.add(reservebtn);
		bottomPanel.add(inquirebtn);
		
		//�г��� ������ ��ġ,ũ������
		topPanel.setLayout(null);
		topPanel.setBounds(0, 0, 1000, 100);
		MapPanel.setLayout(null);
		MapPanel.setBounds(0, 100, 1000, 400);
		bottomPanel.setLayout(null);
		bottomPanel.setBounds(0, 500, 1000, 100);
		MapPanel.setBackground(Color.BLACK);
		
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, 990, 580);
		this.add(mainPanel);
		//�����ӿ� �гκ���
		mainPanel.add(topPanel);
		mainPanel.add(MapPanel);
		mainPanel.add(bottomPanel);
		
		setSize(1000,600);
		setVisible(true);
		
		//������ ������ ���� �����Ӹ� ������.
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				dispose();
			}
		});

	}
	
	//��ư�����ʵ��� ����� ��Ʈ�� Ŭ������ ���.
	public void addButtonActionListener(ActionListener listener) {
		
		statebtn.addActionListener(listener);
		reservebtn.addActionListener(listener);
		inquirebtn.addActionListener(listener);

	}
	
}
