package UserGUI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChoiceGUI extends JFrame {
	
	JLabel topLabel;
	JButton leftButton, rightButton;
	
	public ChoiceGUI() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Product Management Application V1.0");
		this.setLayout(null);
		
		//�� ���̱�
		topLabel = new JLabel("##(��  / ����) �����Ͽ� �ֽʽÿ�.##");
		topLabel.setBounds(0,0,500,50);
		this.add(topLabel);
		
		//�����ǳ�
		leftButton = new JButton("��");
		leftButton.setSize(200,280);
		leftButton.setLocation(240, 140);
		this.add(leftButton);
		
		//�������г�
		rightButton = new JButton("����");
		rightButton.setSize(200,280);
		rightButton.setLocation(540, 140);	
		this.add(rightButton);
		
		setVisible(true);
		setSize(1000,600);
		
	}
	
	
}
