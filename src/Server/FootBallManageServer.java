package Server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class FootBallManageServer {

	
	private ServerSocket ss = null;
//	private ArrayList<Socket> socketList;
//	private int sidx;
	FootBallDB fbdb;
	//����� Ŭ���̾�Ʈ ������ ���� AL
	ArrayList<ConnectThread> connectThreads = new ArrayList<ConnectThread>();
	
	//�ΰ� ��ü
	Logger logger;
	
//	public FootBallManageServer() {
//		for(int i=0;i<100;i++) {
//			socketList.add(new Socket());
//		}
//		sidx = 0;
//	}
	public void start() {
		
//		//�� ������ Ȯ��
//		try {
//			InetAddress ip = InetAddress.getLocalHost();
//			System.out.println("Host Name = [" + ip.getHostName() + "]");
//			System.out.println("Host Address = [" + ip.getHostAddress() + "]");
//		} catch (Exception e) {
//			System.out.println(e);
//		}
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
				Socket pSocket = new Socket();
				pSocket = ss.accept();
				//����� Ŭ���̾�Ʈ�� ���� ������ Ŭ���� ����
				ConnectThread connect = new ConnectThread();
				//Ŭ���̾�Ʈ ����Ʈ �߰�
				connectThreads.add(connect);
				connect.setSocekt(pSocket);
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
