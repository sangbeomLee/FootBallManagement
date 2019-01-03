package ManagerGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.google.gson.Gson;

import ManagerGUI.Manager.SubFrame;
import Server.Message;

public class ManagerController {
	public Manager administer;
	private BufferedReader inMsg = null;
	private PrintWriter outMsg = null;
	public SubFrame sub;
	
	int num=0;//JTable �� ����
	boolean state1 = false;
	boolean state2 = false;
	
	String id;
	String outm;
	Socket socket = null;
	Message m;
	Logger logger;
	Thread thread;
	Gson gson;
	ConnectManagerServer connectS;
	Managerpanel Manager; //������ UI ����
	
	public ManagerController() {
		

		administer= new Manager(); //�α��� ȭ��
		appMain();
		

	      logger = Logger.getLogger(this.getClass().getName());
	      
	      connectS = new ConnectManagerServer(this);
	      connectS.connectServer(administer);
	      
	      
	      socket = connectS.setSocket();
	      inMsg = connectS.setInMsg();
	      outMsg = connectS.setOutMsg();
	      thread = connectS.setThread();
	      gson = connectS.setGson();

			
		
	}
	public void appMain() {
	
			administer.addButtonActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Object obj = e.getSource();
					
					if(obj ==administer.Log) { //�α��� ������ �޽��� �����°�
						outMsg.println(gson.toJson(new Message(administer.ID.getText(),administer.Pass.getText(),"","administer","login")));
						logger.info("[�α��� ����]!!");
					}
					
					if(obj == administer.sub.Signup)
					{
						if(!administer.sub.Passin.getText().equals(administer.sub.Passrein.getText())) {
							JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٸ��ϴ�.");
							return;
						}
						String id = administer.sub.IDin.getText();
						String pw = administer.sub.Passin.getText();
						String msg = administer.sub.Name.getText()+"#"+administer.sub.Mail.getText()+"#"+administer.sub.Number.getText() + "#" + administer.sub.Field.getSelectedItem();
						String type1 ="administer";
						String type2 = "register";
						m = new Message(id, pw, msg, type1, type2);
						outMsg.println(gson.toJson(m));
						
					}
					if(obj ==administer.Join )
					{
						m = new Message("","","","administer","field");
						outMsg.println(gson.toJson(m));
						
						
					}
					
					
				}
			});
			

	}//appmain close
	
	
	public void appMain2() {// ������UI �׼� ������
		
		Manager.addButtonActionListener(new ActionListener() { //������UI �׼� ������

				
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(e.getSource() == Manager.b1)//��ǰ���� �г� ��ȯ
				{
					Manager.cardLayout.show(Manager.mainpanel, "stock");
				}
				else if(e.getSource() == Manager.b2)//���� ��Ȳ �г� ��ȯ
				{
					Manager.cardLayout.show(Manager.mainpanel, "personal_day");
				}
				else if(e.getSource() == Manager.stockbtn2)//�� ����
				{
					
					if(Manager.stocktable.getSelectedRow() == -1)
					{
						return;
					}
					else
					{
						
						//Manager.stocktable.getValueAt(row,column);
						//System.out.println(Manager.stocktable.getSelectedRow());
						
						System.out.println(Manager.stocktable.getValueAt(Manager.stocktable.getSelectedRow(),0) );
						Manager.model1.removeRow(Manager.stocktable.getSelectedRow());
						
						//outMsg.println(gson.toJson(new Message(id,"","","administer","changeproduct"))); //�����Ǿ��ٰ� ������ �޼��� ������
						
						num--;
					}

				}
				else if(e.getSource() == Manager.stockbtn1)//�� �߰�
				{
					
					num++;//�� ����
					
					String input[] = new String[4];

					input[0] = Manager.t1.getText();
					input[1] = Manager.t2.getText();
					input[2] = Manager.t3.getText();
					Manager.model1.addRow(input);

					Manager.t1.setText("");
					Manager.t2.setText("");
					Manager.t3.setText("");
					
					String send = input[0] + "#" + input[1] + "#" + input[2];
					outMsg.println(gson.toJson(new Message(id,send,"","administer","addproduct"))); //�߰��Ǿ��ٰ� ������ �޼��� ������
								
					
				}
				
			}
			
		});//addButtonActionListener close
		

			Manager.addTableModelListener(new TableModelListener() {//���̺� ������ 

				// ������ �� ��ǥ�� �˾ƿ��� ����
				//int row = Manager.stocktable.getEditingRow();
				//int column = Manager.stocktable.getEditingColumn(); 
		
				@Override
				public void tableChanged(TableModelEvent e) {
					//e.getColumn();//�̺�Ʈ�� �߻��� ���� Į��
					//e.getFirstRow();//�̺�Ʈ�� �߻��� ���� ��

					String input[] = new String[4];//������ ���� string �迭
					int eventType = e.getType();
					if(eventType == 0) //eventType( ���� �߰��Ǹ� 1 , ���� �����Ǹ� -1 , �����Ǹ� 0)
					{
						Object va = Manager.model1.getValueAt(e.getFirstRow(), e.getColumn());//�̺�Ʈ�� �߻��� ���� ��
						
						input[0] = (String) Manager.stocktable.getValueAt(Manager.stocktable.getSelectedRow(),0);
						input[1] = (String) Manager.stocktable.getValueAt(Manager.stocktable.getSelectedRow(),1);
						input[2] = (String) Manager.stocktable.getValueAt(Manager.stocktable.getSelectedRow(),2);
						
						String send = input[0] + "#" + input[1] + "#" + input[2];
						
						outMsg.println(gson.toJson(new Message(id,send,"","administer","changeproduct"))); //���� �Ǿ��ٰ� ������ �޼��� ������
						System.out.println(send);

					}

					//System.out.println(e.getColumn());
					//System.out.println(e.getFirstRow());
					//System.out.println(va);
					
					//Manager.stocktable.getValueAt(row,column);
					//System.out.println(Manager.stocktable.getValueAt(row,column));
					
				}

			}	
		); // addTableModelListener close
			


		
	}//appmain2 close
	
	
	public void setSub(SubFrame sub) {
		   this.sub = sub;
	   }
	
	public void part2() {//������ ȭ�� �޼ҵ�
		
		
		administer.setVisible(false);//�α��� ȭ�� ����
		
		id = connectS.setID();
		
		outMsg.println(gson.toJson(new Message(id, "", "product", "administer", "setproduct"))); // ������ �޼��� ������
		Manager = new Managerpanel(); // ������ ȭ�� ��ü ����
		Manager.mangerID.setText(id + "���� �α��� �Ͽ����ϴ�");//ȭ�鿡 ������ ���̵� ���� 
		Manager.mangerID2.setText(id + "���� �α��� �Ͽ����ϴ�");//ȭ�鿡 ������ ���̵� ����
		
	}
	
	public void show_stock(String a) {//���̵𰪿� ���߾� �����Ͱ� �ѷ��ִ� �żҵ�
		
		
		Manager.contents= new String[num++][0];

		String arr [] = a.split("#");
		
		System.out.println(arr[0]);
		Vector<String> row = new Vector<String>();
		row.add(arr[0]); // 0���� ǰ�� 1���¼��� 2���� ���� ����
		row.add(arr[1]);
		row.add(arr[2]);
		Manager.model1.addRow(row);//���̺� ���� ����
		//appMain2();
		
	}
	
}//managerController close













