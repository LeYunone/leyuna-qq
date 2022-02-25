package com.zy.qq.seriver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zy.qq.uitility.CL;
/**
 * ��Ӻ��ѷ�����
 * @author �������
 *
 */
public class AddServer extends Thread {

	@Override
	public void run() {  //��Ӻ�������
		String msg=new String(datapack.getData(),0,datapack.getLength());   //��ȡ�ÿͻ��˷����� json��ʽ����Ϣ Ȼ�������Ӧ���ж�
		JSONObject json=JSONObject.fromObject(msg);
		System.out.println("��Ӻ��ѷ��������յ���"+json);
			String type=json.getString("type");
			if(type.equals("Add")){  //�������Ӻ��ѵ�����     {"myuid":"","touid":"","type":"" }
				String touid=json.getString("touid");  //����ӷ�id
				if(!UserOnline.getUserOnline().ifOnline(touid)){  //����Է�û������
					UserOnline.getUserOnline().addMess(touid, datapack);  //����з���������Ϣ�洢
					return;
				}
				Userinfo userinfo=UserOnline.getUserOnline().getOnline(touid);
				try {
					datapack=new DatagramPacket(
							datapack.getData(),0,datapack.getLength(),   // json��ʽ�����ݰ� ������Ϣ����Ϣ��� ԭ���ݰ�
							InetAddress.getByName(userinfo.getUip())     //�û���ip��ַ
							,userinfo.getUport());                      //�û��Ķ˿�
					
					datasocket.send(datapack);  //  �����ͻ���

				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}else if(type.equals("Readd")){//�ظ���Ӻ��ѵ�����  {"myuid":"" ,"touid":" ","state":""}
				String touid=json.getString("touid");
				String myuid=json.getString("myuid");
				String state=json.getString("state");
				if(state.equals("0")){  //���״̬Ϊ0 ��Ϊ����  �������ݿ�
					int num=IFLogin.addFriend(myuid, touid);  
					System.out.println("��Ӻ�����ӡ������:"+num);
				}
				
				if(!UserOnline.getUserOnline().ifOnline(touid)){  //����Է�û������
					UserOnline.getUserOnline().addMess(touid, datapack);  //����з���������Ϣ�洢
					return;
				}
				
				Userinfo userinfo=UserOnline.getUserOnline().getOnline(touid);
				try {
					datapack=new DatagramPacket(
							datapack.getData(),0,datapack.getLength(),   // json��ʽ�����ݰ� ������Ϣ����Ϣ��� ԭ���ݰ�
							InetAddress.getByName(userinfo.getUip())     //�û���ip��ַ
							,userinfo.getUport());                      //�û��Ķ˿�
					
					datasocket.send(datapack);  //  �����ͻ���

				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}else if(type.equals("group")){  //{"type":"","uids":"","pgname"}				
				String pgname=json.getString("pgname");
				String uids=json.getString("uids");
				String pguser=json.getString("pguser");
				String [] puids=uids.split(",");
				System.out.println("���ܵ���"+json);
				boolean create=IFLogin.createGroup(pgname,pguser,puids); 
				if(!create){
					try {
						throw new SQLException(); //��������������ݿ�����
					} catch (SQLException e) {
						e.printStackTrace();
					}  
				}
			}
	}
	
	private DatagramPacket datapack;
	private static DatagramSocket datasocket=null;
	public AddServer(DatagramPacket datapack){
		this.datapack=datapack;
	}
	
	public static void openServer(){
		ExecutorService execu=Executors.newFixedThreadPool(1000);		
		try {
			datasocket=new DatagramSocket(CL.Add_port);
			while(true){
				byte[] bf=new byte[2048*5];
				int len=bf.length;
				DatagramPacket datapack=new DatagramPacket(bf,len);  //����һ���հ��� 
				datasocket.receive(datapack);  //���ܿͻ��˷�������Ϣ ���ϳ����ݰ�
				execu.execute(new AddServer(datapack));   //�������߳�			
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
