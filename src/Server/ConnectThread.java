package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gson.Gson;

public class ConnectThread extends Thread {
	
	//db ����
	FootBallDB fbdb;
	private Socket s 		= null;
	
	//logger����
	Logger logger;

	// ���� �޽��� �� �Ľ� �޽��� ó��
	String msg;

	// �޽��� ��ü ����
	Message m = new Message();

	// JSON �ļ� �ʱ�ȭ
	Gson gson = new Gson();

	// ����� ��Ʈ��
	private BufferedReader inMsg = null;
	private PrintWriter outMsg = null;
	boolean status = true;

	public ConnectThread() {
		logger = Logger.getLogger(this.getClass().getName());
		logger.info(this.getName() + "������!!");
	}

	public void run() {
		status = true;
		//logger.info("[������ ����]!!");
		try {
			//logger.info("[try������ ����]!!");
			inMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
			outMsg = new PrintWriter(s.getOutputStream(), true);

			while (status) {
				logger.info("[while������ ����]!!");
				// ���ŵ� �޽����� msg ������ ����
				msg = inMsg.readLine();
				//logger.info("[read ����]!!");
				m = gson.fromJson(msg, Message.class);

				// �Ľ̵� ���ڿ� �迭�� �� ��° ��Ұ�
				// �α׾ƿ� �޽���
				if (m.type1.equals("customer")) {
					logger.info("[Ŀ�����  ����]!!");
					if (m.type2.equals("register")) {
						//������ ���̽����ִ� �Ŷ� Ȯ���ؾ���.
						//�����ͺ��̽��� ���̵�� �н����� ��������Ѵ�.
						if(fbdb.idRegistrationCustomer(m)) {
							outMsg.println(gson.toJson(new Message(m.id, "���� ȸ�����Կ� �����߽��ϴ�.", "ȸ�����Լ���", "customer", "server")));
							logger.info("[db��� ����]!!");
						}
						else {
							outMsg.println(gson.toJson(new Message(m.id, "���� ȸ�����Կ� �����߽��ϴ�.", "ȸ�����Խ���", "customer", "server")));
							logger.info("[db��� ����]!!");
						}
						
						// �ش� Ŭ���̾�Ʈ ������ ���� status false
					}
					else if(m.type2.equals("login")) {
						System.out.println(m.id + "#" + m.msg1);
						if(fbdb.checkCustomerLogin(m.id, m.msg1) == 1) {
							outMsg.println(gson.toJson(new Message(m.id, "���� �α��ο� �����߽��ϴ�.", "�α��μ���", "customer", "server")));
							logger.info("[�α��� ����]!!");
						}
						else if(fbdb.checkCustomerLogin(m.id, m.msg1) == 0) {
							outMsg.println(gson.toJson(new Message(m.id, "���� �α��ο� �����߽��ϴ�.", "��й�ȣ�ٸ�", "customer", "server")));
							logger.info("[�α��� ����(�н�����)]!!");
						}
						else {
							outMsg.println(gson.toJson(new Message(m.id, "���� �α��ο� �����߽��ϴ�.", "�α��ν���", "customer", "server")));
							logger.info("[�α��� ����!!");
						}
					}
				}
				else if (m.type1.equals("administer")) {
					//msgSendAll(gson.toJson(new Message(m.id, "", "���� �α��� �߽��ϴ�.", "server")));
					logger.info("[administer  ����]!!");
					if (m.type2.equals("register")) {
						//������ ���̽����ִ� �Ŷ� Ȯ���ؾ���.
						//�����ͺ��̽��� ���̵�� �н����� ��������Ѵ�.
						if(fbdb.idRegistrationAdminister(m)) {
							outMsg.println(gson.toJson(new Message(m.id, "���� ȸ�����Կ� �����߽��ϴ�.", "ȸ�����Լ���", "administer", "server")));
							logger.info("[db��� ����]!!");
						}
						else {
							outMsg.println(gson.toJson(new Message(m.id, "���� ȸ�����Կ� �����߽��ϴ�.", "ȸ�����Խ���", "administer", "server")));
							logger.info("[db��� ����]!!");
						}
						
						// �ش� Ŭ���̾�Ʈ ������ ���� status false
					}
					else if(m.type2.equals("field")) {
						String fname = fbdb.fieldCheck();
						outMsg.println(gson.toJson(new Message("", fname, "ǲ�����̸�", "fname", "administer")));
						logger.info("[ǲ���� ���� �Ϸ�!]");
					}
					else if(m.type2.equals("login")) {
						System.out.println(m.id + "#" + m.msg1);
						if(fbdb.checkAdministerLogin(m.id, m.msg1) == 1) {
							outMsg.println(gson.toJson(new Message(m.id, "���� �α��ο� �����߽��ϴ�.", "�α��μ���", "administer", "server")));
							logger.info("[�α��� ����]!!");
						}
						else if(fbdb.checkAdministerLogin(m.id, m.msg1) == 0) {
							outMsg.println(gson.toJson(new Message(m.id, "���� �α��ο� �����߽��ϴ�.", "��й�ȣ�ٸ�", "administer", "server")));
							logger.info("[�α��� ����(�н�����)]!!");
						}
						else {
							outMsg.println(gson.toJson(new Message(m.id, "���� �α��ο� �����߽��ϴ�.", "�α��ν���", "administer", "server")));
							logger.info("[�α��� ����]!!");
						}
					}
					else if(m.type2.equals("setproduct")) {
						ArrayList<String> items = fbdb.getProduct(m);
						
						for(String send : items) {
							outMsg.println(gson.toJson(new Message(m.id, send, "productinfo", "administer", "server")));
							logger.info("[���δ�Ʈ ���� ������..]!!");
						}
						logger.info("[���δ�Ʈ ���� �Ϸ�]!!");
					}
				}
				else{
					//�߸��� �����Դϴ� ����
					//status = false;
					//logger.info("������ ����");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
			//logger.info("[e ����]!!");
		}
		// ������ ����� Ŭ���̾�Ʈ ���� ����
		this.interrupt();
		logger.info(this.getName() + "�����!!");
	}

	void setSocekt(Socket s) {
		this.s	= s;
	}
	void setDB(FootBallDB db) {
		this.fbdb = db;
	}

}
