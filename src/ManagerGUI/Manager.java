package ManagerGUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import UserGUI.User.SubFrame;


public class Manager extends JFrame {//������ �α��� UI

	public JLabel Title;
	public JTextField ID,Pass;
	public JButton Log,Join,Find;
	EtchedBorder eborder;
	public SubFrame sub;
	
	//������UI ������
	public Manager(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		
		sub = new SubFrame();//���� �������� �־� ȸ������ ������ ����
		
		eborder=new EtchedBorder(EtchedBorder.RAISED);//����ȭ�� Ÿ��Ʋ �׵θ� ȿ������
		Title = new JLabel("ǲ�� ���� ����",SwingConstants.CENTER);
		Title.setBorder(eborder);
		Title.setFont(new Font("Serif",Font.BOLD,11));
		Title.setFont(Title.getFont().deriveFont(15f));
		Title.setBounds(375,50,250,70);
		Title.setBackground(Color.black);
		
		ID = new JTextField("���̵�");
		ID.setFont(ID.getFont().deriveFont(10f));
		ID.setBounds(350, 330, 150, 20);
		
		Pass = new JTextField("��й�ȣ");
		Pass.setFont(Pass.getFont().deriveFont(10f));
		Pass.setBounds(350, 360, 150, 20);
		
		Log = new JButton("LOGIN");//�α��� ��ư
		Log.setBounds(530,323,130,60);
		
		Join = new JButton("ȸ������");//ȸ������ ��ư
		Join.setBounds(380,423,130,30);
		

		
		getContentPane().add(Join);
		getContentPane().add(Log);
		getContentPane().add(ID);
		getContentPane().add(Pass);
		getContentPane().add(Title);
		

		
		pack();
		setSize(1000,600);
		super.setVisible(true);
}
	

	public class SubFrame extends Frame{//ȸ������ ������
		public JTextField IDin,Passin,Passrein,Name,Mail,Number;
		public JComboBox<String> Field;
		public JLabel LIDin,LPassin,LPassrein,LName,LMail,LNumber,Passre,LField;
		public JButton Check,Signup;
		boolean tatus=false;
		String pass1,pass2;
		public SubFrame() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(null);
			setTitle("ȸ������");
			this.setSize(500,600);
			IDin = new JTextField("");
			IDin.setFont(IDin.getFont().deriveFont(10f));
			IDin.setBounds(100, 60, 150, 40);
			LIDin = new JLabel("���̵�");
			LIDin.setFont(LIDin.getFont().deriveFont(10f));
			LIDin.setBounds(20, 60, 50, 40);
			
			
			Passin = new JTextField();
			Passin.setFont(Passin.getFont().deriveFont(10f));
			Passin.setBounds(100, 130, 150, 40);
			LPassin = new JLabel("��й�ȣ");
			LPassin.setFont(LPassin.getFont().deriveFont(10f));
			LPassin.setBounds(20, 130, 50, 40);
			
			Passrein = new JTextField();
			Passrein.setFont(Passrein.getFont().deriveFont(10f));
			Passrein.setBounds(100, 210, 150, 40);
			LPassrein = new JLabel("��й�ȣ Ȯ��");
			LPassrein.setFont(LPassrein.getFont().deriveFont(10f));
			LPassrein.setBounds(20, 210, 70, 40);
		
			
			Name = new JTextField();
			Name.setFont(Name.getFont().deriveFont(10f));
			Name.setBounds(100, 300, 150, 40);
			LName = new JLabel("�̸�");
			LName.setFont(LName.getFont().deriveFont(10f));
			LName.setBounds(20, 300, 50, 40);
		
			
			Mail = new JTextField();
			Mail.setFont(Mail.getFont().deriveFont(10f));
			Mail.setBounds(100, 390, 150, 40);
			LMail = new JLabel("�̸���");
			LMail.setFont(LMail.getFont().deriveFont(10f));
			LMail.setBounds(20, 390, 50, 40);
			
			Number = new JTextField();
			Number.setFont(Number.getFont().deriveFont(10f));
			Number.setBounds(100, 480, 150, 40);
			LNumber = new JLabel("��ȭ��ȣ");
			LNumber.setFont(LNumber.getFont().deriveFont(10f));
			LNumber.setBounds(20, 480, 50, 40);
			
			Field = new JComboBox<String>();
			Field.setFont(Field.getFont().deriveFont(10f));
			Field.setBounds(100, 510, 150, 40);
			LField = new JLabel("ǲ����");
			LField.setFont(LField.getFont().deriveFont(10f));
			LField.setBounds(20, 510, 50, 40);
			
			pass1 = Passin.getText();
			pass2 = Passrein.getText();
			
			Signup = new JButton("���");
			Signup.setSize(30, 30);
			Signup.setBounds(350,400,100,50);
			
			
			add(IDin);
			add(LIDin);
			add(Passin);
			add(LPassin);
			add(Passrein);
			add(LPassrein);
			add(Name);
			add(LName);
			add(Mail);
			add(LMail);
			add(Number);
			add(LNumber);
			add(Signup);
			add(Field);
			add(LField);
		
			
			addWindowListener(new WindowAdapter() {//ȸ������ �����Ӹ� ����
			
				public void windowClosing(WindowEvent e) {
					setVisible(false);
					dispose();
				}

			});
		}
		
	}
	
	public void setSub(SubFrame sb) {
		sb = sub;
	}
	public void addButtonActionListener(ActionListener listener) {
		sub.Signup.addActionListener(listener);
		Log.addActionListener(listener);
		Join.addActionListener(listener);
		ID.addActionListener(listener);
		Pass.addActionListener(listener);
		//���� ������ ��ư�� ���� ������ ��ư �̺�Ʈ
	}
}
