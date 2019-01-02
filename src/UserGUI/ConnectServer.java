package UserGUI;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.*;
import com.google.gson.Gson;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Server.Message;
import java.io.IOException;
import java.io.InputStreamReader;


public class ConnectServer implements Runnable{
	public User user;
	private BufferedReader inMsg = null;
	private PrintWriter outMsg = null;
	
	String outm;
	Socket socket = null;
	Message m;
	Logger logger;
	Thread thread;
	Gson gson;
	boolean status;
	
	public void connectServer() {
		try {
			socket = new Socket("172.16.30.242",8888);
			logger.log(INFO,"[Client]Server ���� ����!!");
			
			inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outMsg = new PrintWriter(socket.getOutputStream(),true);
			thread = new Thread(this);
			thread.start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			logger.log(WARNING,"[MultiChatUI]connectServer() Exception �߻�");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run() {
		status = true;
		String msg;
		
		m = new Message();
		
		while(status) {
			try {
				msg = inMsg.readLine();
				m = gson.fromJson(msg, Message.class);//Message Ŭ���� �������� ��ȯ���ش�.
				if(m.msg2.equals("ȸ�����Խ���"))
				{
					JOptionPane.showMessageDialog(null,"���̵� �ߺ��˴ϴ�.","", JOptionPane.WARNING_MESSAGE);
				}
				else if(m.msg2.equals("ȸ�����Լ���")) {
					JOptionPane.showMessageDialog(null, "ȸ�������̵Ǿ����ϴ�");
				}
				
				else if(m.msg2.equals("�α��μ���"))
				{
					JOptionPane.showMessageDialog(null, "�α��μ���");
					user.setVisible(false);
				}
				else if(m.msg2.equals("��й�ȣ�ٸ�"))
				{
					JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٸ��ϴ�.","" ,JOptionPane.WARNING_MESSAGE);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.log(WARNING,"[User]�޽��� ��Ʈ�� ����!!");

				e.printStackTrace();
			}
			logger.info("[MultiChatUI]"+thread.getName() + "�޽��� ���� ������ �����!!");
		}
	}
}
