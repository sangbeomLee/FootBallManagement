package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;
public class FootBallDB {

	//DB������ ���� ����
	String jdbcDriver = "com.mysql.jdbc.Driver";
	String jdbcUrl = "jdbc:mysql://localhost/footballmanage";
	Connection conn;
	
	PreparedStatement pstmt;
	ResultSet rs;
	
	//db���� ����
	String dbUrl = "jdbc:mysql://localhost:3306/footballmanage";
	String dbUser = "root";
	String dbPw = "802406aa";
	
	//�ΰ� ����
	Logger logger;
	
	//sql
	String sql;
	
	public FootBallDB() {
		logger = Logger.getLogger(this.getClass().getName());
		connectionDB();
	}
	
	//DB����
	public void connectionDB() {
		
		try {
			// JDBC ����̹� �ε�
			Class.forName(jdbcDriver);
			
			// �����ͺ��̽� ����
			conn = (Connection) DriverManager.getConnection(dbUrl,dbUser, dbPw);
			logger.info("[footballmanage DB ���� ����!!]");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning("[footballmanage DB ���� ����(FootBallDB.java/connectionDB)!!]");
		}
	}

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
					fname = fname +rs.getString("fName");
				}
				else {
					fname = fname + "#" +rs.getString("fName");
				}
				
			}
			logger.info("[fieldCheck] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[fieldCheck] ����!!");
		}
		return fname;
	}
	//id ���
	public boolean idRegistrationCustomer(Message m) {
		
		//id, passward �Űܳ��´�.
		String id = m.id;
		String pw = m.msg1;
		String msg2 = m.msg2;
		//�ߺ� �˻�.
		ArrayList<String> cID;
		cID = getAllCustomerID();
		for(String check : cID) {
			if(check.equals(id)) {
				return false;
			};
		}
		System.out.println(msg2);
		//�ߺ��˻縦 ��ģ(���� id���� ����)�޽����� �����Ѵ�.
		//msg�� ������ (�̸�/�̸���/��ȭ��ȣ)
		
		String[] msgArray = msg2.split("#");
		System.out.println(id+pw+msgArray[0]+msgArray[1]+msgArray[2]);
		try {
			
			sql = "INSERT INTO customer values(?, ?, ?, ?, ?)";
			//pstmt��ü ����, SQL ���� ����
			pstmt = conn.prepareStatement(sql);
			logger.info("[sql ���� �ִ�?!!]");
			
			//pstmt.set
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, msgArray[0]);
			pstmt.setString(4, msgArray[1]);
			pstmt.setString(5, msgArray[2]);
			
			//SQL�� ����
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
		
		//id, passward �Űܳ��´�.
		String id = m.id;
		String pw = m.msg1;
		String msg2 = m.msg2;
		//�ߺ� �˻�.
		ArrayList<String> aID;
		aID = getAllAdministerID();
		for(String check : aID) {
			if(check.equals(id)) {
				return false;
			};
		}
		System.out.println(msg2);
		//�ߺ��˻縦 ��ģ(���� id���� ����)�޽����� �����Ѵ�.
		//msg�� ������ (�̸�/�̸���/��ȭ��ȣ)
		
		String[] msgArray = msg2.split("#");
		int fID = changeF(msgArray[3]);
		//int fID = Integer.parseInt(msgArray[3])''
		System.out.println(id+"#"+pw+"#"+msgArray[0]+"#"+msgArray[1]+"#"+msgArray[2]+"#" + fID);
		try {
			System.out.println();
			sql = "INSERT INTO administer values(?, ?, ?, ?, ?, ?)";
			//pstmt��ü ����, SQL ���� ����
			pstmt = conn.prepareStatement(sql);
			logger.info("[sql ���� �ִ�?!!]");
			
			//pstmt.set
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, msgArray[0]);
			pstmt.setString(4, msgArray[1]);
			pstmt.setString(5, msgArray[2]);
			pstmt.setInt(6, fID);
			//SQL�� ����
			pstmt.executeUpdate();
			
			logger.info("[idRegistrationAdminister �Ϸ�!!]");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("[idRegistrationAdminister ����!!]");	
			return false;
		}
	}
	
	public int changeF(String fName) {
		sql = "select fID,fName from footballfield";
		
		int fID = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ���δ�Ʈ�� ���� �ְ� ������ ��̸���Ʈ�� �ִ´�.
				if(fName.equals(rs.getString("fName"))) {
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

	//��ü��ǰ�� �����´�.
	public ArrayList<String> getAllCustomerID(){
		sql = "select cID from customer";
		
		//��ü �˻� �����͸� �����ϴ� ArrayList
		ArrayList<String> cID = new ArrayList<String>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//���δ�Ʈ�� ���� �ְ� ������ ��̸���Ʈ�� �ִ´�.
				cID.add(rs.getString("cID"));
			}
			logger.info("[getAllCustomerID] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[getAllCustomerID] ����!!");
		}
		return cID;
	}

	// ��ü��ǰ�� �����´�.
	public ArrayList<String> getAllAdministerID() {
		sql = "select cID from administer";

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

	//login check
	public int checkCustomerLogin(String id, String pw){
		sql = "select cID,cPassword from customer";
		String cID;
		String cPassword;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cID 		= rs.getString("cID");
				cPassword 	= rs.getString("cPassword");
				if(cID.equals(id)) {
					if(cPassword.equals(pw)) {
						logger.info("[checkID login] ����!!");
						return 1;
					}
					else {
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
	public int checkAdministerLogin(String id, String pw){
		sql = "select aID,aPassword from administer";
		String aID;
		String aPassword;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				aID 		= rs.getString("aID");
				aPassword 	= rs.getString("aPassword");
				if(aID.equals(id)) {
					if(aPassword.equals(pw)) {
						logger.info("[checkID login] ����!!");
						return 1;
					}
					else {
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
		// fid �ٲ۴�.
		int fID = changeF(m.type2);
		
		sql = "select pName,pQuntity,pRepair from product where " + fID;

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
				System.out.println(temp);
				product.add(temp);
			}
			logger.info("[getProduct] ����!!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.warning("[getProduct] ����!!");
		}
		return product;

	}
	
	
	
	
	
	
	
	
	
	
}
