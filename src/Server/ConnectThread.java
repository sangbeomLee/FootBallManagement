package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
		setDdata();
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
						
						if(fbdb.checkCustomerLogin(m.id, m.msg1) == 1) {
							outMsg.println(gson.toJson(new Message(m.id, "���� �α��ο� �����߽��ϴ�.", "�α��μ���", "customer", "server")));
							logger.info("[�α��� ����]!!");

							String fname = fbdb.fieldCheck();
							outMsg.println(gson.toJson(new Message("", fname, "ǲ�����̸�", "fname", "customer")));
							logger.info("[�� ǲ���� ���� �Ϸ�!]");
//							// ������ �κ� ����
//							ArrayList<String> items = fbdb.sendDateInfo();
//							for (String send : items) {
//								outMsg.println(gson.toJson(new Message(m.id, send, "dateinfo", "customer", "server")));
//							}
//							outMsg.println(gson.toJson(new Message(m.id, "finishSetproduct", "dfinish", "customer", "server")));
//							logger.info("[Date ���� �Ϸ�]!!");

						}
						else if(fbdb.checkCustomerLogin(m.id, m.msg1) == 0) {
							outMsg.println(gson.toJson(new Message(m.id, "���� �α��ο� �����߽��ϴ�.", "��й�ȣ�ٸ�", "customer", "server")));
							logger.info("[�α��� ����(�н�����)]!!");
						}
						else {
							outMsg.println(gson.toJson(new Message(m.id, "���� �α��ο� �����߽��ϴ�.", "�α��ν���", "customer", "server")));
							logger.info("[�α��� ����!!");
						}
					}else if(m.type2.equals("finddate")) {
						String send = fbdb.sendFieldDateInfo(m);
						outMsg.println(gson.toJson(new Message("", send, "fdate", "customer", "server")));
						logger.info("[�� ǲ���� date ���� �Ϸ�!]");
					}
					else if(m.type2.equals("findtime")) {
						String send = fbdb.sendFieldTimeDateInfo(m);
						outMsg.println(gson.toJson(new Message("", send, "ftime", "customer", "server")));
						logger.info("[�� ǲ���� time ���� �Ϸ�!]");
					}
					else if(m.type2.equals("reserve")) {
						if(fbdb.reserveCustomer(m)) {
							outMsg.println(gson.toJson(new Message("m.id", "", "reserve", "customer", "server")));
							logger.info("[�� ǲ���� reserve ���� �Ϸ�!]");
						}
						else {
							outMsg.println(gson.toJson(new Message("m.id", "", "notreserve", "customer", "server")));
							logger.info("[�� ǲ���� reserve ���� ����!!]");
						}
						
					}
					//�����̰� �����ϴ� ������Ʈ
					else if(m.type2.equals("findstate")) {
						System.out.println(m);
						ArrayList<String>reserve = fbdb.customerR(m);
						for(String send : reserve) {
							outMsg.println(gson.toJson(new Message("", send, "state", "customer", "server")));
						}
						logger.info("[�� ǲ���� state ���� �Ϸ�!]");
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
						outMsg.println(gson.toJson(new Message(m.id, "finishSetproduct", "finish", "administer", "server")));
						logger.info("[���δ�Ʈ ���� �Ϸ�]!!");
					}
					//�����ϱ�
					else if(m.type2.equals("changeproduct")) {
						//db���� ����
						if(fbdb.sendProductChange(m)) {
							outMsg.println(gson.toJson(new Message(m.id, "", "changed","administer","server")));
							logger.info("[���� �Ϸ�]!!");
						}
						else {
							outMsg.println(gson.toJson(new Message(m.id, "", "notchanged","administer","server")));
							logger.info("[���� ����]!!");
						}
					}
					//��ǰ �߰�
					else if(m.type2.equals("addproduct")) {
						//db���� ����
						if(fbdb.addProduct(m)) {
							outMsg.println(gson.toJson(new Message(m.id, "", "addproduct","administer","server")));
							logger.info("[�߰� �Ϸ�]!!");
						}
						else {
							outMsg.println(gson.toJson(new Message(m.id, "", "notaddproduct","administer","server")));
							logger.info("[�߰� ����]!!");
						}
					}
					else if(m.type2.equals("deleteproduct")) {
						if(fbdb.deleteProduct(m)) {
							outMsg.println(gson.toJson(new Message(m.id, "", "deleteproduct","administer","server")));
							logger.info("[���� �Ϸ�]!!");
						}
						else {
							outMsg.println(gson.toJson(new Message(m.id, "", "notdeleteproduct","administer","server")));
							logger.info("[���� ����]!!");
						}
					}
					//������Ȳ
					else if(m.type2.equals("checkreservation")) {
						ArrayList<String> send = fbdb.administerR(m); 
						for(String temp : send) {
							outMsg.println(gson.toJson(new Message(m.id, temp, "checkreservation","administer","server")));
						}
						logger.info("[������Ȳ �Ϸ�]!!");
					}
					//�޹��� ����.
					else if(m.type2.equals("addcozyday")) {
						if(fbdb.setDayOfWeek(m)) {
							outMsg.println(gson.toJson(new Message(m.id, "", "cozyday","administer","server")));
							logger.info("[cozyday �Ϸ�]!!");
						}
						else {
							outMsg.println(gson.toJson(new Message(m.id, "", "notcozyday","administer","server")));
							logger.info("[cozyday ����]!!");
						}
					}
					else if(m.type2.equals("checkcozyday")) {
						ArrayList<String> send = fbdb.checkcozyday(m); 
						for(String temp : send) {
							outMsg.println(gson.toJson(new Message(m.id, temp, "checkcozyday","administer","server")));
						}
						logger.info("[checkcozyday �Ϸ�]!!");
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

	//��¥ �ڵ��߰�
	void setDdata() {
		//���� ������ Ȯ���Ѵ�.
		// ���ó�¥ Ȯ��.
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		Date currentTime = new Date();
		// ���ó�¥
		String mTime = mSimpleDateFormat.format(currentTime);
		// ��¥ �����ֱ�
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentTime);
		cal.add(Calendar.DATE, 6); // ��¥ ���ϱ�
		String addTime = mSimpleDateFormat.format(cal.getTime());
		// System.out.println("���ѳ�¥ Ȯ�� : " + addTime);

		if (fbdb.getWeekDdate(addTime)) {
			//System.out.println("�̹��ְ� �ִ�.");
			//fbdb.deleteDateWeek(mTime);
		} else {
			//System.out.println("�̹��ָ������� ���� �߰��ض�");
			fbdb.deleteDateWeek(mTime);
			fbdb.addDate(addTime);
		}
	}
}
