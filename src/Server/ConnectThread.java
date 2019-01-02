package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
				}
				else if (m.type1.equals("administer")) {
					//msgSendAll(gson.toJson(new Message(m.id, "", "���� �α��� �߽��ϴ�.", "server")));
					
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
