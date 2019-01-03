package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;

public class FootBallDB {

	// DB������ ���� ����
	String jdbcDriver = "com.mysql.jdbc.Driver";
	String jdbcUrl = "jdbc:mysql://localhost/footballmanage";
	Connection conn;

	PreparedStatement pstmt;
	ResultSet rs;

	// db���� ����
	String dbUrl = "jdbc:mysql://localhost:3306/footballmanage";
	String dbUser = "root";
	String dbPw = "802406aa";

	// �ΰ� ����
	Logger logger;

	// sql
	String sql;

	public FootBallDB() {
		logger = Logger.getLogger(this.getClass().getName());
		connectionDB();
	}

	// DB����
	public void connectionDB() {

		try {
			// JDBC ����̹� �ε�
			Class.forName(jdbcDriver);

			// �����ͺ��̽� ����
			conn = (Connection) DriverManager.getConnection(dbUrl, dbUser, dbPw);
			logger.info("[footballmanage DB ���� ����!!]");

		} catch (Exception e) {
			e.printStackTrace();
			logger.warning("[footballmanage DB ���� ����(FootBallDB.java/connectionDB)!!]");
		}
	}

	// ǲ���� ������ȸ
	public String fieldCheck() {

		sql = "select fName from footballfield";

		String fname = "";
		// ��ü �˻� �����͸� �����ϴ� ArrayList
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ���δ�Ʈ�� ���� �ְ� ������ ��̸���Ʈ�� �ִ´�.
				if (fname.equals("")) {
					fname = fname + rs.getString("fName");
				} else {
					fname = fname + "#" + rs.getString("fName");
				}

			}
			logger.info("[fieldCheck] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[fieldCheck] ����!!");
		}
		return fname;
	}

	// id ���
	public boolean idRegistrationCustomer(Message m) {

		// id, passward �Űܳ��´�.
		String id = m.id;
		String pw = m.msg1;
		String msg2 = m.msg2;
		// �ߺ� �˻�.
		ArrayList<String> cID;
		cID = getAllCustomerID();
		for (String check : cID) {
			if (check.equals(id)) {
				return false;
			}
			;
		}
		// �ߺ��˻縦 ��ģ(���� id���� ����)�޽����� �����Ѵ�.
		// msg�� ������ (�̸�/�̸���/��ȭ��ȣ)

		String[] msgArray = msg2.split("#");
		
		try {

			sql = "INSERT INTO customer values(?, ?, ?, ?, ?)";
			// pstmt��ü ����, SQL ���� ����
			pstmt = conn.prepareStatement(sql);
			logger.info("[sql ���� �ִ�?!!]");

			// pstmt.set
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, msgArray[0]);
			pstmt.setString(4, msgArray[1]);
			pstmt.setString(5, msgArray[2]);

			// SQL�� ����
			pstmt.executeUpdate();

			logger.info("[idRegistrationCustomer �Ϸ�!!]");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[idRegistrationCustomer ����!!]");
			return false;
		}
	}

	public boolean idRegistrationAdminister(Message m) {

		// id, passward �Űܳ��´�.
		String id = m.id;
		String pw = m.msg1;
		String msg2 = m.msg2;
		// �ߺ� �˻�.
		ArrayList<String> aID;
		aID = getAllAdministerID();
		for (String check : aID) {
			if (check.equals(id)) {
				return false;
			}
			;
		}
		
		// �ߺ��˻縦 ��ģ(���� id���� ����)�޽����� �����Ѵ�.
		// msg�� ������ (�̸�/�̸���/��ȭ��ȣ)

		String[] msgArray = msg2.split("#");
		int fID = changeF(msgArray[3]);
		// int fID = Integer.parseInt(msgArray[3])''
		
		try {
			
			sql = "INSERT INTO administer values(?, ?, ?, ?, ?, ?)";
			// pstmt��ü ����, SQL ���� ����
			pstmt = conn.prepareStatement(sql);
			logger.info("[sql ���� �ִ�?!!]");

			// pstmt.set
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, msgArray[0]);
			pstmt.setString(4, msgArray[1]);
			pstmt.setString(5, msgArray[2]);
			pstmt.setInt(6, fID);
			// SQL�� ����
			pstmt.executeUpdate();

			logger.info("[idRegistrationAdminister �Ϸ�!!]");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[idRegistrationAdminister ����!!]");
			return false;
		}
	}

	// ǲ���� �̸��� ǲ���� id�� �ٲ��ִ� �Լ�
	public int changeF(String fName) {
		sql = "select fID,fName from footballfield";

		int fID = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ���δ�Ʈ�� ���� �ְ� ������ ��̸���Ʈ�� �ִ´�.

				if (fName.equals(rs.getString("fName"))) {
					fID = rs.getInt("fID");
					return fID;
				}
			}
			logger.info("[footballfieldchangeF] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[footballfieldchangeF] ����!!");
		}
		return fID;
	}

	// administer �� fIDã�� �Լ�
	public int findFID(String cid) {
		sql = "select fID,aID from administer";

		int fID = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ���δ�Ʈ�� ���� �ְ� ������ ��̸���Ʈ�� �ִ´�.

				if (cid.equals(rs.getString("aID"))) {
					fID = rs.getInt("fID");
					return fID;
				}
			}
			logger.info("[findFID] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[findFID] ����!!");
		}
		return fID;
	}

	// ��ü��ǰ�� �����´�.
	public ArrayList<String> getAllCustomerID() {
		sql = "select cID from customer";

		// ��ü �˻� �����͸� �����ϴ� ArrayList
		ArrayList<String> cID = new ArrayList<String>();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ���δ�Ʈ�� ���� �ְ� ������ ��̸���Ʈ�� �ִ´�.
				cID.add(rs.getString("cID"));
			}
			logger.info("[getAllCustomerID] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[getAllCustomerID] ����!!");
		}
		return cID;
	}

	// administer ���̵� �����´�.
	public ArrayList<String> getAllAdministerID() {
		sql = "select aID from administer";

		// ��ü �˻� �����͸� �����ϴ� ArrayList
		ArrayList<String> aID = new ArrayList<String>();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ���δ�Ʈ�� ���� �ְ� ������ ��̸���Ʈ�� �ִ´�.
				aID.add(rs.getString("aID"));
			}
			logger.info("[getAllAdministerID] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[getAllAdministerID] ����!!");
		}
		return aID;
	}

	// login check
	public int checkCustomerLogin(String id, String pw) {
		sql = "select cID,cPassword from customer";
		String cID;
		String cPassword;

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cID = rs.getString("cID");
				cPassword = rs.getString("cPassword");
				if (cID.equals(id)) {
					if (cPassword.equals(pw)) {
						logger.info("[checkID login] ����!!");
						return 1;
					} else {
						logger.info("[checkID login] ��й�ȣ����!!!");
						return 0;
					}
				}
			}
			logger.info("[checkID login] ����!!");
			return -1;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[checkID login] ����!!");
		}
		logger.info("[checkID login] ����!!");
		return -1;
	}

	public int checkAdministerLogin(String id, String pw) {
		sql = "select aID,aPassword from administer";
		String aID;
		String aPassword;

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				aID = rs.getString("aID");
				aPassword = rs.getString("aPassword");
				if (aID.equals(id)) {
					if (aPassword.equals(pw)) {
						logger.info("[checkID login] ����!!");
						return 1;
					} else {
						logger.info("[checkID login] ��й�ȣ����!!!");
						return 0;
					}
				}
			}
			logger.info("[checkID login] ����!!");
			return -1;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[checkID login] ����!!");
		}
		logger.info("[checkID login] ����!!");
		return -1;
	}

	public ArrayList<String> getProduct(Message m) {
		// fid �ٲ۴�.1�����ش�.
		int fID = findFID(m.id);

		sql = "select pName,pQuntity,pRepair from product where fID = " + fID;
		
		// ��ü �˻� �����͸� �����ϴ� ArrayList
		ArrayList<String> product = new ArrayList<String>();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ���δ�Ʈ�� ���� �ְ� ������ ��̸���Ʈ�� �ִ´�.
				String temp = "";
				temp = temp + rs.getString("pName");
				temp = temp + "#" + Integer.toString(rs.getInt("pQuntity"));
				temp = temp + "#" + Integer.toString(rs.getInt("pRepair"));
				
				product.add(temp);
			}
			logger.info("[getProduct] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[getProduct] ����!!");
		}
		return product;

	}

	// datemanage ������ ���̽��� �����ϱ����� �Լ�
	public void setDate(int id, String date, String time, int fID, String week) {
		try {
			sql = "INSERT INTO datemanage values(?, ?, ?, ?, ?)";
			// pstmt��ü ����, SQL ���� ����
			pstmt = conn.prepareStatement(sql);

			// pstmt.set
			pstmt.setInt(1, id);
			pstmt.setString(2, date);
			pstmt.setString(3, time);
			pstmt.setInt(4, fID);
			pstmt.setString(5, week);

			// SQL�� ����
			pstmt.executeUpdate();

			// logger.info("[setDate �Ϸ�!!]");
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[setDate ����!!]");
		}
	}

	// ��¥�� �˱����� �Լ�
	public ArrayList<String> getDate() {
		// ��-��-�� ������ ������ �ߴ�.
		sql = "select date_format(dDate,'%Y-%m-%d') as date from datemanage";
		ArrayList<String> date = new ArrayList<String>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// �ȿ��ִ� dDate �� �ִ´�.
				date.add(rs.getString("date"));
			}

			logger.info("[getDate] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[getDate] ����!!");
		}
		return date;
	}

	// ID���� 1�� �ִ����� �˱� ���� �Լ�.
	public int getMinimumDateID() {
		sql = "select dID from datemanage ORDER BY dID asc LIMIT 1;";

		int dID;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			dID = rs.getInt("dID");

			logger.info("[getMinimumDateID] ����!!");
			return dID;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[getMinimumDateID] ����!!");
		}
		logger.info("[getMinimumDateID] ����!!");
		return -1;
	}

	// ������ dateID���� �˱� ���� �Լ�
	public int getMaxmumDateID() {
		sql = "select dID from datemanage ORDER BY dID DESC LIMIT 1;";

		int dID;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			dID = rs.getInt("dID");

			logger.info("[getMaxmumDateID] ����!!");
			return dID;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[getMaxmumDateID] ����!!");
		}
		logger.info("[getMinimumDateID] ����!!");
		return -1;
	}

	// ���� ��¥�� DB data ����� �Լ�.
	public void deleteDateWeek(String today) {
		String day = today;
		
		try {
			// sql
			sql = "DELETE FROM datemanage where dID>0 and dDate <" + "'" + day + "'";
			// pstmt��ü ����, SQL ���� ����
			pstmt = conn.prepareStatement(sql);
			// SQL�� ����
			pstmt.executeUpdate();

			logger.info("[deleteDateWeek �Ϸ�!!]");
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[deleteDateWeek ����!!]");
		}
	}

	// ��¥�� ���������� �ڵ����� �־��ִ� �Լ�.
	public void addDate(String date) {
		int id = 0;
		// int minimum = getMinimumDateID();

		// �ð� �ֱ�.
		ArrayList<String> times = new ArrayList<String>();
		times.add("10��-12��");
		times.add("12��-14��");
		times.add("14��-16��");
		times.add("16��-18��");
		times.add("18��-20��");

		// id�� �ڵ�����!!
		for (int i = 1; i <= 4; i++) {
			for (int j = 0; j < 5; j++) {
				setDate(id, date, times.get(j), i, "");
			}
		}
		logger.info("[addDate ����!!]");

	}

	// 1���� �� ��¥�� �ִ��� Ȯ��.
	public boolean getWeekDdate(String after) {

		sql = "select dDate from datemanage where dDate>=" + "'" + after + "'";
		ArrayList<String> date = new ArrayList<String>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// �ȿ��ִ� dDate �� �ִ´�.
				date.add(rs.getString("dDate"));
			}

			logger.warning("[getDdate] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[getDdate] ����!!");
		}

		if (date.size() > 1) {
			
			return true;
		} else {
			return false;
		}
	}

	// dateinfo�� ������.
	public ArrayList<String> sendDateInfo() {
		// fid �ٲ۴�.1�����ش�.

		sql = "select DISTINCT dm.fID fid,dm.dDate dDate,dm.dTime from datemanage dm inner join reservation on dm.dID <> reservation.dID";
		// ��ü �˻� �����͸� �����ϴ� ArrayList
		ArrayList<String> dInfo = new ArrayList<String>();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// ù��°�� ����Ʈ ���� ���־ �����.
			rs.next();
			while (rs.next()) {
				// ���δ�Ʈ�� ���� �ְ� ������ ��̸���Ʈ�� �ִ´�.
				String temp = "";
				temp = temp + Integer.toString(rs.getInt("fid"));
				temp = temp + "#" + rs.getString("dDate");
				temp = temp + "#" + rs.getString("dTime");
				dInfo.add(temp);
			}
			logger.info("[sendDateInfo] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[sendDateInfo] ����!!");
		}
		return dInfo;

	}
	//date info�� ��¥ ����
	public String sendFieldDateInfo(Message m) {
		
		int fID = changeF(m.msg2);
        String dDate = "";
       
        try {
               sql = "select DISTINCT date_format(dm.dDate,'%Y-%m-%d') as dDate from datemanage dm where dm.fID = ? and dm.dID <> 1 and dm.dDayOfWeek = 0";
               pstmt = conn.prepareStatement(sql);
  
               // pstmt.set
               pstmt.setInt(1, fID);
               
               // SQL�� ����
               rs = pstmt.executeQuery();
               rs.next();
               dDate = dDate + rs.getString("dDate");
               while (rs.next()) {
                      // date#���̱�.
                      dDate = dDate + "#" + rs.getString("dDate");
               }
               logger.info("[sendFieldDateInfo] ����!!");
        } catch (Exception e) {
               // TODO: handle exception
               logger.warning("[sendFieldDateInfo] ����!!");
        }
        return dDate;

	}
	//date info�� �ð� ������.
	public String sendFieldTimeDateInfo(Message m) {

		String choicedate = m.msg1;
		int fID = changeF(m.msg2);
		String dDate = "";
		
		try {
			sql = "select dm.dTime from datemanage dm where dm.dID <> 1 and dm.fID = ? and dm.dDate = ? and dm.dID not in (select dID from reservation)";
			
			pstmt = conn.prepareStatement(sql);
			// pstmt.set
			pstmt.setInt(1, fID);
			pstmt.setString(2, choicedate);
			
			
			// SQL�� ����
			rs = pstmt.executeQuery();
			rs.next();
			dDate = dDate + rs.getString("dTime");
			
			while (rs.next()) {
				// date#���̱�.
				dDate = dDate + "#" + rs.getString("dTime");
			}
			logger.info("[sendFieldTimeDateInfo] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.warning("[sendFieldTimeDateInfo] ����!!");
		}
		return dDate;

	}

	//administer ��ǰ ����
	public synchronized boolean sendProductChange(Message m) {
		/// UPDATE tablename SET filedA='456' WHERE test='123'
		//�̻���� fid�� �˾ƾ��Ѵ�. �Լ��� ������
		
		String array[] = m.msg1.split("#");
		String pName = array[0];
		int pQ	 = Integer.parseInt(array[1]);
		int pR	 = Integer.parseInt(array[2]);
		int fID 	 = findFID(m.id);
		try {
			sql = "update product set pQuntity = ?, pRepair = ? where pName = ? and fID = ?";
			// pstmt��ü ����, SQL ���� ����
			pstmt = conn.prepareStatement(sql);

			// pstmt.set
			pstmt.setInt(1, pQ);
			pstmt.setInt(2, pR);
			pstmt.setString(3, pName);
			pstmt.setInt(4, fID);
			// SQL�� ����
			pstmt.executeUpdate();

			logger.info("[sendProductChange �Ϸ�!!]");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[sendProductChange ����!!]");
			return false;
		}
	}
	//��ǰ �߰�
	public synchronized boolean addProduct(Message m) {
		// id, passward �Űܳ��´�.
		
		String id = m.id;
		String msg1 = m.msg1;
		String array[] = msg1.split("#");
		String pName = array[0];
		int pQ	 = Integer.parseInt(array[1]);
		int pR	 = Integer.parseInt(array[2]);
		int fID 	 = findFID(m.id);
		
		try {
			sql = "INSERT INTO product VALUES (?, ?, ?, ?, ?);";
			// pstmt��ü ����, SQL ���� ����
			pstmt = conn.prepareStatement(sql);

			// pstmt.set
			pstmt.setInt(1, 0);
			pstmt.setInt(2, fID);
			pstmt.setInt(3, pQ);
			pstmt.setInt(4, pR);
			pstmt.setString(5, pName);
			// SQL�� ����
			pstmt.executeUpdate();

			logger.info("[addProduct �Ϸ�!!]");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[addProduct ����!!]");
			return false;
		}
	}
	//��ǰ �����
	public synchronized boolean deleteProduct(Message m) {	
		//����
		String pName = m.msg1;
		int fID 	 = findFID(m.id);
	
		try {
			// sql
			sql = "delete from product where pID>0 and fID = ? and pName = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fID);
			pstmt.setString(2, pName);

			// SQL�� ����
			pstmt.executeUpdate();

			logger.info("[deleteProduct �Ϸ�!!]");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[deleteProduct ����!!]");
			return false;
		}
	}

	//������ �����Ȳ
	public ArrayList<String> administerR(Message m) {
	
		int fID = findFID(m.id);

		sql = "select date_format(dm.dDate,'%Y-%m-%d') as dDate,dm.dTime as dTime,rv.cID as cID from datemanage dm,reservation rv where rv.dID = dm.dID and dm.dID <> 1 and rv.fid = " + fID + " ORDER BY dDate";
		
		// ��ü �˻� �����͸� �����ϴ� ArrayList
		ArrayList<String> reserveinfo = new ArrayList<String>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ���δ�Ʈ�� ���� �ְ� ������ ��̸���Ʈ�� �ִ´�.
				String temp = "";
				temp = temp + rs.getString("dDate");
				temp = temp + "#" + rs.getString("dTime");
				temp = temp + "#" + rs.getString("cID");
				reserveinfo.add(temp);
			}
			
			logger.info("[administerR] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[administerR] ����!!");
		}
		return reserveinfo;

	}
	//���� ���� �Ϸ�!
	public synchronized boolean reserveCustomer(Message m) {
		// id, passward �Űܳ��´�.
		String id = m.id;
		String msg1 = m.msg1;
		String msg2 = m.msg2;
		String array[] = msg2.split("#");
		
		int fID  = changeF(array[0]);
		String Date = array[1];
		String Time = array[2];
		int dID = findDID(Date,Time,fID);
		//����� ���� �����Ѱ�
		
		if(!compareDID(dID)) {
			return false;
		}
		try {
			sql = "INSERT INTO reservation VALUES (?, ?, ?, ?, ?);";
			// pstmt��ü ����, SQL ���� ����
			pstmt = conn.prepareStatement(sql);

			// pstmt.set
			pstmt.setInt(1, 0);
			pstmt.setString(2, id);
			pstmt.setInt(3, fID);
			pstmt.setInt(4, dID);
			pstmt.setString(5, msg1);
			// SQL�� ����
			
			pstmt.executeUpdate();

			logger.info("[reserveCustomer �Ϸ�!!]");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[reserveCustomer ����!!]");
			return false;
		}
	}
	//dIDã�´�.
	public int findDID(String dDate, String dTime, int fID) {
		int dID = -1;
		try {
			sql = "select dID from datemanage where dDate = ? and dTime = ? and fID = ? ";
			pstmt = conn.prepareStatement(sql);

			// pstmt.set
			pstmt.setString(1, dDate);
			pstmt.setString(2, dTime);
			pstmt.setInt(3, fID);

			// SQL�� ����
			rs = pstmt.executeQuery();
			rs.next();
			dID = rs.getInt("dID");
			logger.info("[findDID] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[findDID] ����!!");
		}
		return dID;

	}

	//dID reservation �� ���Ѵ�.
	public boolean compareDID(int dID) {
		int check = 0;
		try {
			sql = "select dID from reservation where dID = ?";
			// pstmt��ü ����, SQL ���� ����
			pstmt = conn.prepareStatement(sql);

			// pstmt.set
			pstmt.setInt(1, dID);

			// SQL�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				logger.info("[compareDID ����[�ߺ�]!!]");
				return false;
			}
			else {
				logger.info("[compareDID �Ϸ�!!]");
				return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[compareDID ����!!]");
			return false;
		}
	}
	//dDayOfWeek �߰��ϱ�.�޹���
	public boolean setDayOfWeek(Message m) {
		
		String dDate = m.msg1;
	
		int fID 	 = findFID(m.id);
		try {
			sql = "update datemanage set dDayOfWeek = 1 where dDate = ? and fID = ?";
			// pstmt��ü ����, SQL ���� ����
			pstmt = conn.prepareStatement(sql);
		
			// pstmt.set
			pstmt.setString(1, dDate);
			pstmt.setInt(2, fID);
			// SQL�� ����
			pstmt.executeUpdate();

			logger.info("[setDayOfWeek �Ϸ�!!]");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[setDayOfWeek ����!!]");
			return false;
		}
	}
	//���� �ѷ��ֱ�
	public ArrayList<String> checkcozyday(Message m){
		int fID = findFID(m.id);

		sql = "select distinct date_format(dm.dDate,'%Y-%m-%d') as dDate from datemanage dm where dm.dID <> 1 and dm.fid = ? and dm.dDayOfWeek = 1";
		
		// ��ü �˻� �����͸� �����ϴ� ArrayList
		ArrayList<String> cozyday = new ArrayList<String>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ���δ�Ʈ�� ���� �ְ� ������ ��̸���Ʈ�� �ִ´�.
				String temp = rs.getString("dDate");
				cozyday.add(temp);
			}
			logger.info("[checkcozyday] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[checkcozyday] ����!!");
		}
		return cozyday;

	}
	//���� ��Ȳ �����ֱ�
	public ArrayList<String> customerR(Message m) {
		
		String cID = m.id;

		sql = "select distinct rv.fID, date_format(dm.dDate,'%Y-%m-%d') dDate, dm.dTime dTime, rv.rHelper from reservation rv,datemanage dm where rv.cID = ? and rv.dID = dm.dID order by dDate";
		
		// ��ü �˻� �����͸� �����ϴ� ArrayList
		ArrayList<String> reserveinfo = new ArrayList<String>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cID);
			System.out.println(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// ���δ�Ʈ�� ���� �ְ� ������ ��̸���Ʈ�� �ִ´�.
				String temp = "";
				temp = temp + rs.getInt("fID");
				System.out.println("1" + temp);
				temp = temp + "#" + rs.getString("dDate");
				System.out.println("2" + temp);
				temp = temp + "#" + rs.getString("dTime");
				System.out.println("3" + temp);
				temp = temp + "#" + Integer.toString(rs.getInt("rHelper"));
				System.out.println("####2222#");
				reserveinfo.add(temp);
			}
			
			logger.info("[customerR] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[customerR] ����!!");
		}
		return reserveinfo;

	}
	//ǲ���� id�� �̸����� �ٲ��ִ� �Լ�
	public String changeFname(int fID) {
		sql = "select fID,fName from footballfield";

		String fName = "";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ���δ�Ʈ�� ���� �ְ� ������ ��̸���Ʈ�� �ִ´�.

				if (fID == (rs.getInt("fID"))) {
					fName = rs.getString("fName");
					logger.info("[changeFname] ����!!");
					return fName;
				}
			}
			logger.info("[changeFname] ����!!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[changeFname] ����!!");
		}
		return fName;
	}
}
