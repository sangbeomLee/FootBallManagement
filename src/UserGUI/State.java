package UserGUI;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class State extends Frame{
	protected JPanel mainPanel;
	protected JButton statecancelbtn;
	protected JLabel Lstate;
	
	//���̺� ����
	JTable table1;
	JScrollPane sc1;
	DefaultTableModel model1;
    String header1 [] = {"ǲ����","��¥","�ð�","�뺴 �ο���"};
	String contents1 [][];
	
	public State(){
		
		  contents1 = new String[0][0];
	      model1 = new DefaultTableModel(contents1,header1);
	      table1 = new JTable(model1);
	      sc1 = new JScrollPane(table1);
	      sc1.setPreferredSize(new Dimension(300,300));///��ũ�� ũ�� ����

		
		
		
		this.setLayout(null);
		mainPanel = new JPanel();
		statecancelbtn = new JButton("�������");
		Lstate = new JLabel("������Ȳ");
		Lstate.setFont(Lstate.getFont().deriveFont(12f));
		Lstate.setBounds(100, 5, 100, 15);
		statecancelbtn.setBounds(700, 460, 100, 70);
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, 1000, 600);
		
		sc1.setBounds(100, 50, 800, 400);
		mainPanel.add(Lstate);
		mainPanel.add(statecancelbtn);
		mainPanel.add(sc1);
		this.add(mainPanel);
		setSize(1000,600);
		setTitle("������Ȳ");
		setVisible(false);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		//������ȯ UI
		
	}
	public void addButtonActionListener(ActionListener listener) {
		statecancelbtn.addActionListener(listener);
	}
	
}
