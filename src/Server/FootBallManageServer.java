package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
		
//		//�� ������ Ȯ��
//		try {
//			InetAddress ip = InetAddress.getLocalHost();
//			System.out.println("Host Name = [" + ip.getHostName() + "]");
//			System.out.println("Host Address = [" + ip.getHostAddress() + "]");
//		} catch (Exception e) {
//			System.out.println(e);
//		}
		
		//���ó�¥ Ȯ��.
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
		Date currentTime = new Date();
		String mTime = mSimpleDateFormat.format ( currentTime );
		System.out.println ("���ó�¥ Ȯ��" + mTime );
		//��¥ �����ֱ�
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentTime);
		cal.add(Calendar.DATE, 6); // ��¥ ���ϱ�
		String addTime = mSimpleDateFormat.format(cal.getTime());
		System.out.println("���ѳ�¥ Ȯ�� : " + addTime);
		//fbdb.setDate(1, "2019/01/02", "time1", 1, null);
		//db����
		dbConnect();
		
		//System.out.println(fbdb.getMinimumDateID());
		//System.out.println(fbdb.getMaxmumDateID());
		fbdb.deleteDateWeek(mTime);
		if(fbdb.getWeekDdate(addTime)) {
			System.out.println("�̹��ְ� �ִ�.");
		}
		else {
			System.out.println("�̹��ָ������� ���� �߰��ض�");
			//fbdb.addDate(addTime);
		}
		
		

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
