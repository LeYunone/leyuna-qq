package com.zy.qq.clientServer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JOptionPane;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zy.qq.View.Home;
import com.zy.qq.client.Friends2;
import com.zy.qq.clientModel.MessPool;
import com.zy.qq.uitility.CL;

/**
 * ������������� ���н���  ������Ϣ   ��Ӻ���
 * @author �������
 *
 */
public class ChatForServer extends Thread {

	private DatagramSocket clientSocket=null;
	@Override
	public void run() {		
		while(true){  
			byte[]bf=new byte[2048*5];
			int len=bf.length;
			
			DatagramPacket pack=new DatagramPacket(bf, len); //�հ�
			try {
				
				clientSocket.receive(pack); //���ܷ������ϵ���Ϣ
				String msg=new String(pack.getData(),0,pack.getLength());  //������ܵ�����Ϣ
				System.out.println("������������"+msg);
				JSONObject json=JSONObject.fromObject(msg);
				String type=json.getString("type");
				if(type.equals("ok")){   //��Ϣ��ִָ��
					String ok="isok";
					bf=ok.getBytes();
					len=bf.length;
					pack=new DatagramPacket(bf, len,InetAddress.getByName(CL.ip),CL.chat_port);  //���͸��������İ���
					clientSocket.send(pack);
					
				}else if(type.equals("chat")){  //����ָ��         ͨ��������Ϣ���е� ���Ե����촰�ڵ������Ϣ���������Ϣ
					msg=new String(pack.getData(),0,pack.getLength());
					json=JSONObject.fromObject(msg);  //{"code":"","msg":"213123","myuid":"1","touid":"2","type":"chat"}
					MessPool.getMessPool().addMess(json);
					
				}else if(type.equals("Add")){  //����ӵ���Ӻ��ѵ�ָ���� �����ظ�
					String myuid=json.getString("myuid");  //������Ӻ��ѷ���id
					JSONArray ja=JSONArray.fromObject(CL.json_All_userinfo);
					String netname=null;
					String head=null;
					String sign=null;
					for(int i=0;i<ja.size();i++){
						JSONObject j=(JSONObject) ja.get(i);
						if(j.getString("uid").equals(myuid)){
							netname=j.getString("netname");
							head=j.getString("head");
							sign=j.getString("sign");
						}
					}
					String state=(Home.showAddMsg(netname)+"").trim();
					//{"myuid":"" ,"touid":" ","state":""}
					if(state.equals("0")){
						Friends2 f=new Friends2();
						f.setHead(head);
						f.setNetname(netname);
						f.setSign(sign);
						f.setUid(myuid);
						
						JSONArray jsa=JSONArray.fromObject(CL.json_friend);						
						JSONObject jso=JSONObject.fromObject(f);
						jsa.add(jso);
						System.out.println("��Ӻ���");
						CL.json_friend=jsa.toString(); //���º����б�  ���ط�
						if(CL.friend_list.equals("040")){
							StringBuffer sb=new StringBuffer();  //���º��ѱ���б�
							sb.append(myuid); 
							sb.append(",");
							CL.friend_list=sb.toString();
						}else{
							StringBuffer sb=new StringBuffer(CL.friend_list);  //���º��ѱ���б�
							sb.append(myuid); 
							sb.append(",");
							CL.friend_list=sb.toString();
						}
//						CL.friendlist.updateFriend();
					}
					String mymyuid=JSONObject.fromObject(CL.My_json_info).getString("uid"); //���Լ���id
					String readd="{\"myuid\":\""+mymyuid+"\",\"touid\":\""+myuid+"\",\"state\":\""+state+"\",\"type\":\"Readd\"}";
					bf=readd.getBytes();
					len=bf.length;
					pack=new DatagramPacket(bf, len,InetAddress.getByName(CL.ip),CL.Add_port);
					clientSocket.send(pack); //���ͻ�ִ��Ϣ
					
				}else if(type.equals("Readd")){  //  {"myuid":"" ,"touid":" ","state":""}
					String myuid=json.getString("myuid");  //��ִ����id
					JSONArray ja=JSONArray.fromObject(CL.json_All_userinfo);
					String netname=null;
					String head=null;
					String sign=null;
					String email=null;
					for(int i=0;i<ja.size();i++){
						JSONObject j=(JSONObject) ja.get(i);
						if(j.getString("uid").equals(myuid)){
							netname=j.getString("netname");  //�õ��Է�������
							head=j.getString("head");
							sign=j.getString("sign");
							email=j.getString("email");
						}
					}
					String state=json.getString("state");
					if(state.equals("0")){   //0Ϊ����״̬

						Home.showAddOKMsg(netname);
						Friends2 f=new Friends2();
						f.setHead(head);
						f.setNetname(netname);
						f.setSign(sign);
						f.setUid(myuid);
						f.setEmail(email);
						JSONArray jsa=JSONArray.fromObject(CL.json_friend);
						
						JSONObject jso=JSONObject.fromObject(f);
						jsa.add(jso);
						CL.json_friend=jsa.toString(); //���º����б�
						System.out.println("�µĺ��ѣ�"+CL.json_friend);
						if(CL.friend_list.equals("040")){
							StringBuffer sb=new StringBuffer();  //���º��ѱ���б�
							sb.append(myuid); 
							sb.append(",");
							CL.friend_list=sb.toString();
						}else{
							StringBuffer sb=new StringBuffer(CL.friend_list);  //���º��ѱ���б�
							sb.append(myuid); 
							sb.append(",");
							CL.friend_list=sb.toString();
						}
//						CL.friendlist.updateFriend();

					}else{
						Home.showAddONMsg(netname);
					}
				}else if(type.equals("file")){
					
				}else if(type.equals("chats")){  //������ܵ�����Ⱥ��ָ��
					
					MessPool.getMessPool().addMess(json);
				}else if(type.equals("fileNum")){  //������ܵ�����һ���ļ����
					System.out.println("�ͻ���1:�õ��ļ�����========");
					String filenum=json.getString("files");  //�õ�����ļ����
					if(!CL.fileTouid.equals("")){  //����������������û������ļ�
						String touid=CL.fileTouid;
						String myuid=JSONObject.fromObject(CL.My_json_info).getString("uid");
						msg="{\"myuid\":\""+myuid+"\",\"touid\":\""+touid+"\",\"files\":\""+filenum+"\",\"type\":\"SendFile\"}";  //��������ѷ����ļ�������
						byte[] b=msg.getBytes();
						len=b.length;
						pack=new DatagramPacket(b,0,len,InetAddress.getByName(CL.ip),CL.chat_port); //��������������ͱ��
						CL.datasocket.send(pack);
					}
				}else if(type.equals("SendFile")){  //������ܵ����ǿͻ��˷������ļ����
					System.out.println("�ͻ���2:���ܵ��ļ�����========");
					String filenum=json.getString("files");  //�õ��ļ����
					String myuid=json.getString("myuid"); //���ͷ���id  ������ʾ
					JSONArray ja=JSONArray.fromObject(CL.json_All_userinfo);
					String netname="";
					for(int i=0;i<ja.size();i++){
						JSONObject j=(JSONObject) ja.get(i);
						if(j.getString("uid").equals(myuid)){
							netname=j.getString("netname");
						}
					}
					String mymyuid=json.getString("touid");
					int num=JOptionPane.showConfirmDialog(null,netname+"���㷢��һ���ļ����Ƿ����","ϵͳ��ʾ",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(num==JOptionPane.YES_OPTION){
						msg="{\"myuid\":\""+mymyuid+"\",\"files\":\""+filenum+"\",\"type\":\"getFile\"}"; //���������������
						byte[] b=msg.getBytes();
						len=b.length;
						pack=new DatagramPacket(b,0,len,InetAddress.getByName(CL.ip),CL.chat_port); //��������������ͱ��
						CL.datasocket.send(pack);
					}
				}else if(type.equals("filename")){ //��������������ļ���
					String filename=json.getString("filesname");
					CL.filename=filename;  //�洢�ļ���
				}else if(type.equals("droupGroup")){
					String pid=json.getString("pid");
					System.out.println(pid);
					if(CL.groupMap.get(pid)!=null){
						CL.groupMap.remove(json.getString("pid"));  //�����ɾ��
						CL.grouplist.showGroup();
					}
				}else{ //���û������ ��Ϊ�����ļ�
					System.out.println("�ͻ���2:�õ��ļ�========");
					FileOutputStream fos=null;
					try {
//						String path=(System.currentTimeMillis()+"").trim();
						System.out.println("�ļ���"+CL.filename);
						fos=new FileOutputStream(new File("D:/"+CL.filename));
						byte[] b=new byte[1024*10];
						 len = 0;   //���ݳ���
						    while (len == 0) {  //��������ʼѭ����������
						        //�������ݰ�		               
						        len = pack.getLength();
						        if (len > 0) {
						            //ָ�����յ����ݵĳ��ȣ���ʹ����������������
						            fos.write(b,0,len);
						            fos.flush();
						            len = 0;//ѭ������
						        }
						}
					} catch (Exception e) {
						fos.close();
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
	}

	public ChatForServer(DatagramSocket clientSocket){
		this.clientSocket=clientSocket;
		this.start();
	}
}
