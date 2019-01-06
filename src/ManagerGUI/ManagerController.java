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
import Server.Message; //������ �ִ� MessageŬ���� import

public class ManagerController {
	
	public Manager administer;//�α��� UI����
	private BufferedReader inMsg = null;
	private PrintWriter outMsg = null;
	public SubFrame sub;
	
	int num=0;//������ JTable �� ����
	int num2=0;//������Ȳ  JTable �� ����
	int num3=0;//�޹�����Ȳ  JTable �� ����
	String input1[];
	
	String id;
	Socket socket = null;//���� ����
	Message m;
	Logger logger;
	//Thread thread;
	Gson gson;//������ ���� �޼����� ����� gson
	ConnectManagerServer connectS;//������ ������ ����
	Managerpanel Manager; //������ UI ����
	
	public ManagerController() {//������
	

		administer= new Manager(); //�α��� ȭ��
		appMain();//�α���UI �׼Ǹ����ʰ� ����ִ� �޼ҵ� 
		

	      logger = Logger.getLogger(this.getClass().getName());
	      
	      connectS = new ConnectManagerServer(this);//�α��� UI ����
	      connectS.connectServer(administer);
	      
	      
	      socket = connectS.setSocket();//���� ����
	      inMsg = connectS.setInMsg();//�������� ���� �޼��� ����
	      outMsg = connectS.setOutMsg();//������ ���� �ƿ� �޼��� ����
	     //thread = connectS.setThread();
	      gson = connectS.setGson();//������ ���� �޼����� ����� gson

			
		
	}
	public void appMain() {//�α���UI�׼� ������
	
			administer.addButtonActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Object obj = e.getSource();
					
					if(obj ==administer.Log) { //�α��� ������ �޽��� �����°�
						outMsg.println(gson.toJson(new Message(administer.ID.getText(),administer.Pass.getText(),"","administer","login")));
						logger.info("[�α��� ����]!!");
					}//�α��� ��ư�� �޼��� ������ �־� ������ ��ȯ
					
					if(obj == administer.sub.Signup)//ȸ������ ��ư�� ������ �޼��� ����
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
					if(obj ==administer.Join )//ȸ������ �̺�Ʈ ������ �޼����� �־� ȸ������ �ǽ�
					{
						m = new Message("","","","administer","field");
						outMsg.println(gson.toJson(m));
					}
					if(obj==administer.ID)
					{
						administer.ID.setText("");
					}
					if(obj==administer.Pass)
					{
						administer.Pass.setText("");
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
					Manager.frame.setSize(900,500);
					Manager.cardLayout.show(Manager.mainpanel, "stock");
				}
				else if(e.getSource() == Manager.b2)//���� ��Ȳ �г� ��ȯ
				{
					Manager.frame.setSize(900,500);
					Manager.cardLayout.show(Manager.mainpanel, "personal_day");
				}
				else if(e.getSource() == Manager.b3)//�޹��� �г� ��ȯ
				{
					Manager.model3.setRowCount(0);//jtable �ʱ�ȭ

					
					outMsg.println(gson.toJson(new Message(id,"","","administer","checkcozyday"))); //������Ȳ ������ ��û
					
					
					Manager.frame.setSize(400,400);
					Manager.cardLayout.show(Manager.mainpanel, "hol");
				}
				else if(e.getSource() == Manager.stockbtn2)//������ �� ����
				{
					
					if(Manager.stocktable.getSelectedRow() == -1)//������ ���� �ƹ��͵� ������� ����
					{
						return;
					}
					else
					{
						String send = (String) Manager.stocktable.getValueAt(Manager.stocktable.getSelectedRow(),0);//�������� ���õ� ���� ù��° ������ ������	
						Manager.model1.removeRow(Manager.stocktable.getSelectedRow());//�� ����
						outMsg.println(gson.toJson(new Message(id,send,"","administer","deleteproduct"))); //�����Ǿ��ٰ� ������ �޼��� ������		
						num--;//������� Jtable �హ�� ����
					}

				}
				else if(e.getSource() == Manager.stockbtn1)//������ �� �߰�
				{
					
					num++;//�� ����
					
					String input[] = new String[4];

					//������ �ؽ�Ʈ�ʵ忡 ���� ���ڵ� �迭�� ����
					input[0] = Manager.t1.getText();
					input[1] = Manager.t2.getText();
					input[2] = Manager.t3.getText();
					Manager.model1.addRow(input);//�� �߰�

					//�ؽ�Ʈ�ʵ� �ʱ�ȭ
					Manager.t1.setText("");
					Manager.t2.setText("");
					Manager.t3.setText("");
					
					String send = input[0] + "#" + input[1] + "#" + input[2];//���������� �����͸�   ǰ��#����#��������# �̷������� �޾Ƽ�  #�ִ� �κ��� �ɰ��� ����ϱ� ������ #�� ���༭ ������
					outMsg.println(gson.toJson(new Message(id,send,"","administer","addproduct"))); //�߰��Ǿ��ٰ� ������ �޼��� ������
								
					
				}
				else if(e.getSource() == Manager.daybtn1)//������Ȳ  ������Ʈ
				{
					Manager.model2.setRowCount(0); //jtable �ʱ�ȭ
					num2=0;//������Ȳ �迭 �ʱ�ȭ
					outMsg.println(gson.toJson(new Message(id,"","","administer","checkreservation"))); //������Ȳ ������ ��û
				}
				else if(e.getSource() == Manager.daybtn2)//�޹��� �߰� ��ư
				{
					num3++;
					input1= new String[1];
					input1[0] = Manager.pdt.getText();
					
					
					String send = Manager.pdt.getText();//���� 2019-09-03
					//Manager.model3.addRow(input);//JTable�� �޹��� �����Ͱ� �� �� �߰�
					
					//System.out.println(send +"**");					
					outMsg.println(gson.toJson(new Message(id,"",send,"administer","addcozyday"))); //������Ȳ ������ ��û
					
					
					Manager.pdt.setText("0000-00-00");
					
				}
				else if(e.getSource() == Manager.pdt)//�޹��� ���� �ؽ�Ʈ �ʵ�
				{
					Manager.pdt.setText("");
				}
				else if(e.getSource() == Manager.daybtn3)//�޹����� ����
				{
					
					if(Manager.table3.getSelectedRow() == -1)//�޹��� ���̺� �ƹ��͵� ������� Ŭ������ �� ����
					{
						return;
					}
					else
					{
						String send = (String) Manager.table3.getValueAt(Manager.table3.getSelectedRow(),0);//������ �޹����� ��
						
						//System.out.println(Manager.table3.getValueAt(Manager.table3.getSelectedRow(),0) );
						Manager.model3.removeRow(Manager.table3.getSelectedRow());//�� ����
						
						outMsg.println(gson.toJson(new Message(id,send,"","administer","deleteproduct"))); //�����Ǿ��ٰ� ������ �޼��� ������
						
						num3--;//�ຯ�� ����
					}
				}
				
				
			}
			
		});//addButtonActionListener close
		

			Manager.addTableModelListener(new TableModelListener() {//������ ���̺� ������ 

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
						//Object va = Manager.model1.getValueAt(e.getFirstRow(), e.getColumn());//�̺�Ʈ�� �߻��� ���� ��
						
						//������ ���� ���ȿ� �ִ� �����͵��� ������ �迭�� ������ �� ������ ������
						input[0] = (String) Manager.stocktable.getValueAt(Manager.stocktable.getSelectedRow(),0);
						input[1] = (String) Manager.stocktable.getValueAt(Manager.stocktable.getSelectedRow(),1);
						input[2] = (String) Manager.stocktable.getValueAt(Manager.stocktable.getSelectedRow(),2);
						
						String send = input[0] + "#" + input[1] + "#" + input[2];
						
						outMsg.println(gson.toJson(new Message(id,send,"","administer","changeproduct"))); //���� �Ǿ��ٰ� ������ �޼��� ������
						System.out.println(send);

					}
					
				}

			}	
		); // addTableModelListener close
			


		
	}//appmain2 close
	
	
	public void setSub(SubFrame sub) {
		   this.sub = sub;
	   }	
	
	public void part2() {//������ ȭ�� �޼ҵ�
		
		
		administer.setVisible(false);//�α��� ȭ�� ����
		
		id = connectS.setID();//�α����� id ��������
		
		outMsg.println(gson.toJson(new Message(id, "", "product", "administer", "setproduct"))); // ������ �޼��� ������
		Manager = new Managerpanel(); // ������ ȭ�� ��ü ����
		Manager.mangerID.setText(id + "���� �α��� �Ͽ����ϴ�");//ȭ�鿡 ������ ���̵� ���� 
		Manager.mangerID2.setText(id + "���� �α��� �Ͽ����ϴ�                                                  ");//ȭ�鿡 ������ ���̵� ����

	}
	
	public void show_stock(String a) {//���̵𰪿� ���߾� �����Ͱ� �ѷ��ִ� �żҵ�
		
		
		Manager.contents= new String[num++][0];//�޼����� �ѹ������°��̾ƴ϶� ���ึ�� ���⶧����  ������ �ô븶�� �ప�� ����������
		
		
		String arr [] = a.split("#");//�������� �޾ƿ��� �޼�����  ǰ��#����#��������# �̷������� ���⋚���� #�ִ� �κ��� �ɰ��� �����
		
		Vector<String> row = new Vector<String>();
		row.add(arr[0]); // 0���� ǰ�� 1���¼��� 2���� ���� ����
		row.add(arr[1]);
		row.add(arr[2]);
		Manager.model1.addRow(row);//���̺� ���� ����		
	}
	
	public void show_reservation(String a){//���� ��Ȳ�����͸� �޾ƿ��� �޼ҵ�
		
		Manager.contents2 = new String[num2++][0];//�޼����� �ѹ������°��̾ƴ϶� ���ึ�� ���⶧����  ������ �ô븶�� �ప�� ����������
		
		String arr [] = a.split("#");//�������� �޾ƿ��� �޼�����  ��¥#�ð�#����# �̷������� ���⋚���� #�ִ� �κ��� �ɰ��� �����
		
		Vector<String> row = new Vector<String>();
		row.add(arr[0]); // 0���� ��¥ 1���½ð� 2���� ����id
		row.add(arr[1]);
		row.add(arr[2]);
		Manager.model2.addRow(row);//���̺� ���� ����	
	}
	
	public void hol_sh(String a,String b){//�޹��� �����͸� �޾ƿ��� �żҵ�
		
		if(b.equals("not"))
		{
			return;
		}
		else if(b.equals("update"))
		{
			Manager.contents3 = new String[num3++][0];//�޼����� �ѹ������°��̾ƴ϶� ���ึ�� ���⶧����  ������ �ô븶�� �ప�� ����������
			
			String arr = a;
			
			Vector<String> row = new Vector<String>();
			row.add(arr); // �޹��� ������
			Manager.model3.addRow(row);//JTable�� ���� ����	
		}
		else if(b.equals("add"))
		{
			//System.out.println("�Ƕ�");
			Manager.model3.addRow(input1);//JTable�� �޹��� �����Ͱ� �� �� �߰�

		}

	}
	
	
	
	
}//managerController close













