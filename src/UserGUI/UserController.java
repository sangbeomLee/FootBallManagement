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

public class UserController implements Runnable {
	public User user;
	private BufferedReader inMsg = null;
	private PrintWriter outMsg = null;
	
	Socket socket = null;
	Message m;
	Logger logger;
	Thread thread;
	Gson gson;
	boolean status;
	
	public UserController(User user) {
		this.user = user;
		logger = Logger.getLogger(this.getClass().getName());
		gson = new Gson();
		connectServer();
		
	}
	public void connectServer() {
		try {
			socket = new Socket("172.30.1.24",8888);
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
	public void appMain() {
			user.addButtonActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Object obj = e.getSource();
					if(obj == user.sub.Signup &&m. )
					{
					
						String id = user.sub.IDin.getText();
						String pw = user.sub.Passin.getText();
						String msg = user.sub.Name.getText()+"#"+user.sub.Mail.getText()+"#"+user.sub.Number.getText();
						String type1 ="customer";
						String type2 = "register";
						m = new Message(id, pw, msg, type1, type2);
						outMsg.println(gson.toJson(m));
						JOptionPane.showMessageDialog(null, "ȸ�������̵Ǿ����ϴ�");
					}
					else if(user.sub.Passin.getText()!=user.sub.Passre.getText()) {
						JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٸ��ϴ�.");
					}
				}
			});
			
		
	}
	@Override
	public void run() {
		status = true;
		String msg;
		String outm;
		while(status) {
			try {
				msg = inMsg.readLine();
				m = gson.fromJson(msg, Message.class);//Message Ŭ���� �������� ��ȯ���ش�.
				
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.log(WARNING,"[User]�޽��� ��Ʈ�� ����!!");

				e.printStackTrace();
			}
			logger.info("[MultiChatUI]"+thread.getName() + "�޽��� ���� ������ �����!!");
		}
	}
	
}
