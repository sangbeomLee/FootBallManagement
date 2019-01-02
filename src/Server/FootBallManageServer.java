package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class FootBallManageServer {

	
	private ServerSocket ss = null;
	private Socket s 		= null;
	FootBallDB fbdb;
	//����� Ŭ���̾�Ʈ ������ ���� AL
	ArrayList<ConnectThread> connectThreads = new ArrayList<ConnectThread>();
	
	//�ΰ� ��ü
	Logger logger;
	
	public void start() {
		
		//db����
		dbConnect();
		//�ΰ� ��ü ����
		logger = Logger.getLogger(this.getClass().getName());
		
		try {
			//���� ���� ����
			ss = new ServerSocket(8888);
			logger.info("FootBallManageServer Start!!");
			
			//���� ���� ���鼭 Ŭ���̾�Ʈ ���� ��ٸ���.
			while(true) {
				s = ss.accept();
				//����� Ŭ���̾�Ʈ�� ���� ������ Ŭ���� ����
				ConnectThread connect = new ConnectThread();
				//Ŭ���̾�Ʈ ����Ʈ �߰�
				connectThreads.add(connect);
				connect.setSocekt(s);
				connect.setDB(fbdb);
				//������ ����
				connect.start();
			}
		} catch(Exception e) {
			logger.info("[FootBallManageServer]start(); Exception �߻�!!");
			e.printStackTrace();
		}
	
	
	}
	
	public void dbConnect() {
		fbdb = new FootBallDB();
		
	}
	
	public static void main(String[] args) {
		FootBallManageServer fbms = new FootBallManageServer();
		fbms.start();
	}

	
}
