package com.zy.qq.clientServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zy.qq.View.Home;
import com.zy.qq.client.GroupUserList;
import com.zy.qq.uitility.CL;


/**
 * �ͻ�����������Դ�
 * @author �������
 *
 */
public class ForServer implements Runnable {

	private Socket socket=null;
	private InputStream is=null;
	private OutputStream os=null;
	private Thread thread;
	private boolean isrun=false;  //�����߳̿���
	
	public static int num=0;
	
	@Override
	public void run() {   //���������н����ķ���		
		try{
			while(isrun){
				Thread.sleep(5000);
				byte[]bf=new byte[2048*30];
				int len =0;
				os.write("03".getBytes());   //2  �õ�������Ϣ
				os.flush();
				len=is.read(bf);
				String my=new String(bf,0,len);
				CL.My_json_info=my;
//				System.out.println("���ˣ�"+my);
				
				os.write("01".getBytes());   //1�õ�������Ϣ
				os.flush();
				
				bf=new byte[10240];
				len  =is.read(bf);
				String msg=new String(bf,0,len);
				CL.json_friend=msg;    //���ݺ�����Ϣ
				
				os.write("02".getBytes());
				os.flush();
				is.read(); //��������������Ӧָ��
				String online=null;
				os.write(CL.friend_list.getBytes());
				os.flush();
				len=is.read(bf);  //�õ��������ߺ��ѵı��
				online=new String(bf,0,len);
				if(online.equals("040")){
					CL.friend_online="";
				}else{
					CL.friend_online=online;
//					System.out.println("�������߱�ţ�"+CL.friend_online);
					CL.friendlist.updateFriend();  //ʵʱ�����û��µ����к�����Ϣ
				}

				os.write("05".getBytes());
				os.flush();
				len=is.read(bf);
				String userinfoall=new String(bf,0,len);
				try {  
					if(!CL.json_All_userinfo.equals(userinfoall)){   //�����˵���Ϣ�޸ĵ�ʱ��
						CL.json_All_userinfo=userinfoall;
						CL.userinfolist.getAlluser();
						for(GroupUserList g:CL.groupUserviewlist){
							 g.updateGroup();
						}
					}
				} catch (Exception e) {
				}
				CL.json_All_userinfo=userinfoall;  //���е��û���Ϣ 	
				os.write(1);
				os.flush();
				len=is.read(bf);
				String userinfoallid=new String(bf,0,len);  //���ܵ�  xxx,xx,xx  ���
				if(!CL.All_userinfo_online.equals(userinfoallid)){  //�����������������
					try {
						Player play=new Player(new FileInputStream( "Music/����.mp3"));
						play.play();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (JavaLayerException e) {
						e.printStackTrace();
					}
					CL.All_userinfo_online=userinfoallid;
					CL.userinfolist.getAlluser();
				}
				CL.All_userinfo_online=userinfoallid;
				
				os.write("06".getBytes());  //���¼����Ⱥ���
				os.flush();
				len=is.read(bf);
				String myGroup=new String(bf,0,len);			
				
				if(!CL.stringbuffer_list_myGroup.equals(myGroup)){  //�������Ⱥ��ŷ����ı�  
					System.out.println("��Ⱥ�ɹ�");
				    CL.stringbuffer_list_myGroup=myGroup;
					os.write("07".getBytes());  //���¼����Ⱥ��Ϣ
					os.flush();
					len=is.read();
					os.write(CL.stringbuffer_list_myGroup.getBytes());
					os.flush();
					len=is.read(bf);
					String myGroupinfo=new String(bf,0,len); //�õ��Լ���Ⱥ��������Ϣ
					System.out.println("Ⱥ��"+myGroupinfo);
					CL.json_list_myGroupinfo=myGroupinfo;   //���д洢
					CL.grouplist.updateGroup();  //����Ⱥ��Ϣ
//					CL.grouplist.updateGroupList();//����Ⱥ�б�
				}				
				CL.stringbuffer_list_myGroup=myGroup;   //�õ����е�Ⱥ���
				
				os.write("08".getBytes()); //����Ⱥ�µ��û�           
				os.flush();
				is.read();
				os.write(CL.stringbuffer_list_myGroup.getBytes()); // �����ҵ�Ⱥ���
				os.flush();
				len=is.read(bf);
				String map_group_user=new String(bf,0,len);  //��Ⱥ�µ��û���Ⱥ�Ĺ�ϵ
				CL.Map_User_Group=map_group_user;  //�洢 Ⱥ���û���ŵĹ�ϵ
				os.write(1);  //����ִ��08  �õ�Ⱥ�������û�
				os.flush();
				len=is.read(bf);
				String map_group_user_online=new String(bf,0,len);
//				System.out.println("Ⱥ���߱��:"+map_group_user_online);
				try{
					if(!CL.Map_User_Group_Online.equals(map_group_user_online)){
//						System.out.println("����Ⱥ���߸��£�");
						CL.Map_User_Group_Online=map_group_user_online;  //�õ�json��ʽ��  Ⱥ��ţ�  Ⱥ�û����߱��
						CL.grouplist.updateGroupList(); //�����û��б����
						if(CL.groupUserviewlist.size()!=0){
							for(GroupUserList g:CL.groupUserviewlist){
								 g.updateGroup();
							}
						}
					}
				}catch (Exception e) {
//					System.out.println("������Ա���߳��ִ���");
				}
				CL.Map_User_Group_Online=map_group_user_online;  //�õ�json��ʽ��  Ⱥ��ţ�  Ⱥ�û����߱��
//				CL.groupuserlist.updateGroup();
//				json=JSONObject.fromObject(CL.Map_User_Group);  //Ⱥ���û���Ź�ϵ
//				String puid=json.getString("1");
//				JSONArray ja=JSONArray.fromObject(puid);
//				for(int i=0;i<ja.size();i++){
//					String s=(String) ja.get(i);
//					System.out.println(s);
//				}
				
			}
		}catch (Exception e) {
			System.out.println("��������Ͽ�����");
			e.printStackTrace();
			isrun=false;
		}finally{
			
		}
	}

	@SuppressWarnings("deprecation")
	public JSONObject login(){
		try {
			
			socket=new Socket(CL.ip,CL.Login_port);  //���ӷ�����
			is=socket.getInputStream();  //��ͨ�������� 
			os=socket.getOutputStream(); //���ͷ�������Ϣ
			String msg="{\"username\":\""+CL.username+"\",\"password\":\""+CL.password+"\"}";
			
			os.write(msg.getBytes()); //�����¼       ������Ϣ {"username":"xxxx","password":"xxxx"}
			os.flush();
			
			byte[] bf=new byte[2048];
			int len=is.read(bf);
			msg=new String(bf,0,len);
			
			JSONObject json=JSONObject.fromObject(msg);
			
			int state=json.getInt("state");
			if(state==0){ //����˺�û����
				if(thread!=null){  //�ж��Ƿ��Ѿ�����
					if(thread.getState()==Thread.State.RUNNABLE){
						
						thread.stop();
						isrun=false;
					}
				}
				
				////////////////////////////////////       ���е�½����    
				os.write("01".getBytes());   //1�õ�������Ϣ
				os.flush();
				
				bf=new byte[10240];
				len  =is.read(bf);
				msg=new String(bf,0,len);
				
				CL.json_friend=msg;    //���ݺ�����Ϣ
//				System.out.println(msg);  //json����ĺ�����Ϣ
				System.out.println("������Ϣ��"+msg);
				JSONArray jlist=JSONArray.fromObject(CL.json_friend);
				StringBuffer sb=new StringBuffer();
				for(int i=0;i<jlist.size();i++){
					JSONObject jsons=(JSONObject) jlist.get(i);
					String uid=jsons.getString("uid");
					sb.append(uid);
					sb.append(",");
				}
				if(sb.toString().equals("")){
					CL.friend_list="040";  //���û�к��ѵõ�040����
				}else{
					CL.friend_list=sb.toString();  //�洢���������ѱ�ŵ���Ϣ
				}
				
				os.write("03".getBytes());   //2  �õ�������Ϣ
				os.flush();
				len=is.read(bf);
				msg=new String(bf,0,len);
				
				CL.My_json_info=msg;
				System.out.println("���ˣ�"+msg);
				
				os.write("06".getBytes());  //���¼����Ⱥ���
				os.flush();
				len=is.read(bf);
				String myGroup=new String(bf,0,len);			
				CL.stringbuffer_list_myGroup=myGroup;   //�õ����е�Ⱥ���
//				System.out.println("Ⱥ���:"+CL.stringbuffer_list_myGroup);
				os.write("07".getBytes());  //���¼����Ⱥ��Ϣ
				os.flush();
				len=is.read();
				os.write(CL.stringbuffer_list_myGroup.getBytes());
				os.flush();
				len=is.read(bf);
				String myGroupinfo=new String(bf,0,len); //�õ��Լ���Ⱥ��������Ϣ
				CL.json_list_myGroupinfo=myGroupinfo;   //���д洢
				System.out.println("���Ⱥ��Ϣ:"+CL.json_list_myGroupinfo);
				
				os.write("05".getBytes());
				os.flush();
				len=is.read(bf);
				String userinfoall=new String(bf,0,len);
				CL.json_All_userinfo=userinfoall;  //���е��û���Ϣ 	
				os.write(1);
				os.flush();
				len=is.read(bf);
				String userinfoallid=new String(bf,0,len);  //���ܵ�  xxx,xx,xx  ���
				CL.All_userinfo_online=userinfoallid;
				os.write("08".getBytes()); //����Ⱥ�µ��û�           
				os.flush();
				is.read();
				os.write(CL.stringbuffer_list_myGroup.getBytes()); // �����ҵ�Ⱥ���
				os.flush();
				len=is.read(bf);
				String map_group_user=new String(bf,0,len);  //��Ⱥ�µ��û���Ⱥ�Ĺ�ϵ
				CL.Map_User_Group=map_group_user;  //�洢 Ⱥ���û���ŵĹ�ϵ
				JSONObject jsons=JSONObject.fromObject(CL.Map_User_Group);
				System.out.println(jsons);
				os.write(1);  //����ִ��08  ����Ⱥ�������û�
				os.flush();
				len=is.read(bf);
				String map_group_user_online=new String(bf,0,len);
				CL.Map_User_Group_Online=map_group_user_online;  //�õ�json��ʽ��  Ⱥ��ţ�  Ⱥ�û����߱��
				jsons=JSONObject.fromObject(CL.Map_User_Group);  //Ⱥ���û���Ź�ϵ
				
				//////////////////////////////////////////////
				
				///////////////////////////////////
				thread=new Thread(this);
				isrun=true;
				thread.start();
			}
			return json;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
