package com.zy.qq.seriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import com.zy.qq.uitility.CL;

/**
 * ���������
 * @author �������
 *
 */
public class ChatServer implements Runnable {

	private DatagramPacket datapack;
	private static DatagramSocket datasocket=null;
	
	public ChatServer(DatagramPacket datapack){
		this.datapack=datapack;
	}
	
	@Override
	public void run() {
		String msg=new String(datapack.getData(),0,datapack.getLength());   //��ȡ�ÿͻ��˷����� json��ʽ����Ϣ Ȼ�������Ӧ���ж�
//		System.out.println(msg);
		JSONObject json=JSONObject.fromObject(msg);
			
			String type=json.getString("type");
//			System.out.println("����������յ�type:"+type);
			if(type.equals("chat") ){  //������ܵ�����Ϣʱ����ҵ��
				String touid=json.getString("touid");
				String myuid=json.getString("myuid");
				if(!UserOnline.getUserOnline().ifOnline(touid)){  //����Է�û������
					UserOnline.getUserOnline().addMess(touid, datapack);  //����з���������Ϣ�洢
					return;
				}
				UserOnline.getUserOnline().updateUserUDP(myuid, datapack.getAddress().getHostAddress(), datapack.getPort());
				Userinfo userinfo=UserOnline.getUserOnline().getOnline(touid);  //�õ��Է���ip�û�
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
				
				
			}else if(type.equals("ok")){  //��Ϣȷ���յ����
								
			}else if(type.equals("f5")){
				String myuid=json.getString("myuid");
				UserOnline.getUserOnline().updateUserUDP(myuid, datapack.getAddress().getHostAddress(), datapack.getPort());
				Userinfo userinfo=UserOnline.getUserOnline().getOnline(myuid);  //�õ��ҵ�ip�û�
				if(UserOnline.getUserOnline().getMess(myuid)!=null){  //����ҵ��˺�����������Ϣ
					ArrayList<DatagramPacket> datalist=UserOnline.getUserOnline().getMess(myuid);
					for(DatagramPacket datagramPacket:datalist){   //�������е���Ϣ ���͵��ҵĿͻ��˶˿���
						try {
							datagramPacket=new DatagramPacket(
									datagramPacket.getData(),0,datagramPacket.getLength(),   // json��ʽ�����ݰ� ������Ϣ����Ϣ��� ԭ���ݰ�
									InetAddress.getByName(userinfo.getUip())     //�û���ip��ַ
									,userinfo.getUport()); 	//�û��Ķ˿�
							datasocket.send(datagramPacket);  //  �����ͻ���
						} catch (Exception e) {
							e.printStackTrace();
						}                      						
					}
					UserOnline.getUserOnline().clearMess(myuid);
					
				}
			}else if(type.equals("chats")){  //������ܵ�����Ⱥ�ĵ���Ϣ
				String pid=json.getString("touid");  //�õ�Ⱥ��
				HashMap<String,Group> map=IFLogin.getGroup(pid);  //�õ�group  ������list�������û��ı�ţ�
				ArrayList<String> list=map.get(pid).getUser(); //�����û����
				for(int i=0;i<list.size();i++){  	//����Ⱥ�ѱ��
					Userinfo userinfo=UserOnline.getUserOnline().getOnline(list.get(i));
					if(!UserOnline.getUserOnline().ifOnline(list.get(i))){  	//����Է�û������
						UserOnline.getUserOnline().addMess(list.get(i), datapack);  	//����з���������Ϣ�洢
					}else {  //�������
						try{
							datapack=new DatagramPacket(
									datapack.getData(),0,datapack.getLength(),   // json��ʽ�����ݰ� ������Ϣ����Ϣ��� ԭ���ݰ�
									InetAddress.getByName(userinfo.getUip())     //�û���ip��ַ
									,userinfo.getUport());                      //�û��Ķ˿�
							datasocket.send(datapack);  //  �����ͻ���
						}catch (Exception e) {
						}
					}
				}
			}else if(type.equals("save")){  //������ܵ����Ǳ�����Ϣ
				IFLogin.saveUserinfo(json);
			}else if(type.equals("delete")){  //ɾ������
				
				String touid=json.getString("touid");
				String myuid=json.getString("myuid");
				IFLogin.deleteFriends(myuid, touid);
				
			}else if(type.equals("deleteGroup")){  //��Ⱥ
				
				String uid=json.getString("myuid");
				String pid=json.getString("pid");
				IFLogin.deleteGroup(pid,uid);  //����ɾ������
				
			}else if(type.equals("SendFile")){  //����Ƿ����ļ�
				System.out.println("������:�õ������ļ�������ͻ���========");
				String touid=json.getString("touid");  //������
//				String myuid=json.getString("myuid");  //���ͷ���id
				if(!UserOnline.getUserOnline().ifOnline(touid)){  //����Է�û������
					UserOnline.getUserOnline().addMess(touid, datapack);  //����з���������Ϣ�洢
					return;
				}
				Userinfo userinfo=UserOnline.getUserOnline().getOnline(touid);  //�õ��Է���ip�û�
				System.out.println("��������"+touid +"����:"+json.getString("files"));
				try {
					datapack=new DatagramPacket(
							datapack.getData(),0,datapack.getLength(),   // json��ʽ�����ݰ� ������Ϣ����Ϣ��� ԭ���ݰ�
							InetAddress.getByName(userinfo.getUip())     //�û���ip��ַ
							,userinfo.getUport());                      //�û��Ķ˿�
					System.out.println("�����������");
					datasocket.send(datapack);  //  �����ͻ���
					System.out.println("���ͳɹ�");
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(type.equals("getFile")){  //������ܵ�����Ҫ���������ļ�������
				System.out.println("������:�ͻ�������õ��ļ�========");
				String filenum=json.getString("files");
				String path=CL.fileMap.get(filenum);  //�õ���Ҫ���͵��ļ�·��
				String myuid=json.getString("myuid");  //��Ҫ�� myuid�����ļ�
				FileInputStream fis=null;
				try {
					fis=new FileInputStream(new File(path));
					byte[] b=new byte[fis.available()];
					fis.read(b);
					
					Userinfo userinfo=UserOnline.getUserOnline().getOnline(myuid);  //�õ��Է���ip�û�
					datapack=new DatagramPacket(
							datapack.getData(),0,datapack.getLength(),   // json��ʽ�����ݰ� ������Ϣ����Ϣ��� ԭ���ݰ�
							InetAddress.getByName(userinfo.getUip())     //�û���ip��ַ
							,userinfo.getUport());                      //�û��Ķ˿�
					datasocket.send(datapack);  //  �����ͻ���
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(type.equals("filename")){ //������ļ����洢
				String filename=json.getString("filesname");
				CL.filenameserver=filename;
				System.out.println(CL.filenameserver);
				String touid=json.getString("touid");
				if(!UserOnline.getUserOnline().ifOnline(touid)){  //����Է�û������
					UserOnline.getUserOnline().addMess(touid, datapack);  //����з���������Ϣ�洢
					return;
				}
				Userinfo userinfo=UserOnline.getUserOnline().getOnline(touid);  //�õ��Է���ip�û�
				try {
					datapack=new DatagramPacket(
							datapack.getData(),0,datapack.getLength(),   // json��ʽ�����ݰ� ������Ϣ����Ϣ��� ԭ���ݰ�
							InetAddress.getByName(userinfo.getUip())     //�û���ip��ַ
							,userinfo.getUport());                      //�û��Ķ˿�
					datasocket.send(datapack);  //  �����ͻ���
				}catch (Exception e) {
				}
			}else if(type.equals("droupGroup")){  //����õ����ǽ�ɢȺ�������
				
				String pid=json.getString("pid");
				HashMap<String, Group> map=IFLogin.getGroup(pid); //�õ����е��û����
				ArrayList<String> list=map.get(pid).getUser(); //�����û����
				for(String uid : list){
					if(!UserOnline.getUserOnline().ifOnline(uid)){  //����Է�û������ �򲻽��д���
					}else {
						Userinfo userinfo=UserOnline.getUserOnline().getOnline(uid);  //�õ��Է���ip�û�
						try {
							datapack=new DatagramPacket(
									datapack.getData(),0,datapack.getLength(),   // json��ʽ�����ݰ� ������Ϣ����Ϣ��� ԭ���ݰ�
									InetAddress.getByName(userinfo.getUip())     //�û���ip��ַ
									,userinfo.getUport());                      //�û��Ķ˿�
							datasocket.send(datapack);  //  �����ͻ���
						}catch (Exception e) {
						}
					}
				}
				IFLogin.droupGroup(pid);
				
			}
	}
	
	public static void openChatServer(){
		ExecutorService es=Executors.newFixedThreadPool(1000);
		
		try {
			datasocket=new DatagramSocket(CL.chat_port);
			while(true){
				byte[] bf=new byte[2048*5];
				int len=bf.length;
				DatagramPacket datapack=new DatagramPacket(bf,len);  //����һ���հ��� 
				datasocket.receive(datapack);  //���ܿͻ��˷�������Ϣ ���ϳ����ݰ�
				es.execute(new ChatServer(datapack));   //�������߳�			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
