package com.zy.qq.seriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zy.qq.Exception.AccountException;
import com.zy.qq.Exception.NotUserException;
import com.zy.qq.Exception.PasswordException;
import com.zy.qq.uitility.CL;
/**
 * ��¼������
 * @author �������
 *
 */
public class LoginServer implements Runnable {
	
	private Socket socket;
	
	public LoginServer(Socket socket){
		this.socket=socket;
	}

	public static void openServer(){  //�򿪷�����
		ExecutorService es=Executors.newFixedThreadPool(1000);  //����1000���̳߳�
		
		try {
			ServerSocket serversocket=new ServerSocket(CL.Login_port);
			while(true){
				Socket socket=serversocket.accept();  //�ȴ��û�������
				//�������
				es.execute(new LoginServer(socket));  //����һ�����û����������̳߳�
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {   //�������߳�����
		InputStream is=null;
		OutputStream os=null;
		String uid=null;
		try {
			is=socket.getInputStream();
			os=socket.getOutputStream();
			byte[] bf=new byte[2048];
			int len=is.read(bf);  //String msg="{\"iphone\":\""+iphone+"\",\"email\":\""+email+"\",\"name\":\""+name+"\",\"passwod\":\""+password+"\"}";

			String msg=new String(bf,0,len);  //���ܿͻ��˵ĵ�¼����
			
			JSONObject json=JSONObject.fromObject(msg); //������¼��Ϣ
			String username=json.getString("username");
			String password=json.getString("password");
			 ///�ж��Ƿ����ֻ������¼
			String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		    Pattern p = Pattern.compile(regExp);
		    Matcher m = p.matcher(username);
		    boolean lis=m.matches();
		    
			try{   //��¼ʱ���м�� �������ظ���¼����   ��¼�ɹ���ע������
				if(lis){ //������ֻ�����   
					uid=IFLogin.loginiphone(username, password);  //�ֻ���¼
					UserOnline.getUserOnline().regOnline(socket, uid, null, username);
				}else{ 
					uid=IFLogin.loginemail(username, password);   //�����¼
					UserOnline.getUserOnline().regOnline(socket, uid, username, null);
					
 				}
				
				//���û���׳��쳣 ���� ��¼�ɹ��������ظ�0
				os.write("{\"state\":0,\"msg\":\"��¼�ɹ�!\"}".getBytes());
				os.flush();
				
				while(true){  //��¼��ɹ�����������
					bf=new byte[2048*5];
					len=is.read(bf);
//					HashMap<String,Group> map=IFLogin.getGroup();  //�õ�����Ⱥ����Ϣ   ��Ⱥ�ı��   Ⱥ�µ��û����   Group.getUser��
					msg=new String(bf,0,len);
//					System.out.println("�õ�ִ��"+msg);
					if(msg.equals("01")){   //���ͺ����б�
						 Vector<Friend> vc=IFLogin.getFriend(uid);  //�õ�����
						 os.write(JSONArray.fromObject(vc).toString().getBytes());
						 os.flush();
					}else if(msg.equals("02")){  //���ͺ��������б�
						//����ǰ���ͷ��������������һ���Լ��ĺ����б�
						os.write(1);// "�����б�"
						os.flush();
						len=is.read(bf);  // �����ͻ��˷������б�  XXXX,XXXX,XXX ���ѱ��
						msg=new String(bf,0,len);
						if(msg.equals("040")){  //������ܵ�����û�к��ѵ��б���Ϣ
							os.write("040".getBytes());
						}else{
							String fs[]=msg.split(",");
							StringBuffer sb=new StringBuffer();
							for(String f:fs){
								System.out.println(f);
								if(UserOnline.getUserOnline().ifOnline(f)){
									sb.append(f);
									sb.append(",");
								}
							}
							if(sb.length()==0){
								System.out.println("û�к�������");
								os.write(0);
								os.flush();
							}else{
								os.write(sb.toString().getBytes());
								os.flush();
							}
						}
						
					}else if(msg.equals("03")){  //���͸�����Ϣ
						Myuserinfo user=IFLogin.reMyuser(uid);
						json=JSONObject.fromObject(user);
						os.write(json.toString().getBytes());
						os.flush();
					}else if(msg.equals("04")){  //���������Ϣ   ͨ���ͻ��˴��͹�����My_json_info  json��ʽ�ĸ������Ͻ������ݿⱣ�����
						
						
						
					}else if(msg.equals("05")){  //�������е��û���Ϣ �Ա�ʹ��   ��������
						Vector<Friend> userinfoall=IFLogin.queryAll();
						JSONArray ja=JSONArray.fromObject(userinfoall);
						os.write(ja.toString().getBytes());  //���е��û���Ϣ  
						os.flush();
						is.read(); //��������ָ��
						//�����������û��ı�ŷ����ͻ���
						Set<String> setonline=UserOnline.getUserOnline().getOnlineList();  //�õ����������û��ı��
						//�õ�[x,x]
						Iterator<String> it=setonline.iterator();
						StringBuffer sb=new StringBuffer();
						while(it.hasNext()){
							sb.append(it.next());
							sb.append(",");
						}
						os.write(sb.toString().getBytes());
						os.flush();
					}else if(msg.equals("06")){  //�����Ҽ����Ⱥ���
						StringBuffer sb=IFLogin.getMyGroup(uid);
						os.write(sb.toString().getBytes());  //����Ⱥ���
						os.flush();
					}else if(msg.equals("07")){  //����ͻ��˴��������Ⱥ��ŵ�Ⱥ��Ϣ
						os.write(22);  //�������Ⱥ�ı��
						os.flush();
						len=is.read(bf); //��ȡ���͹�����Ⱥ���
						msg=new String(bf,0,len);
						String[] pids=msg.split(",");
						Vector<Groupinfo> list=IFLogin.getMyGroupinfo(pids);
						JSONArray jarry=JSONArray.fromObject(list);
						os.write(jarry.toString().getBytes());  //��ͻ��˷���������Ⱥ����Ϣ
						os.flush();
					}else if(msg.equals("08")){  //��ѯ�ҵ�Ⱥ�������û�
						os.write(1); //�������Ⱥ��
						os.flush();
						len=is.read(bf);   //���ܵ�����ҵ����Ⱥ���
						msg=new String(bf,0,len);
						String [] str=msg.split(",");
						HashMap<String, Group> map=IFLogin.getGroup(str); //ÿ��Ⱥ�����е��û����
						HashMap<String,ArrayList<String>> umap=new HashMap<String, ArrayList<String>>();  //�û���Ⱥ�Ĺ�ϵ
						HashMap<String,ArrayList<String>> onlineMap=new HashMap<String, ArrayList<String>>();  //Ⱥ���û������߱��
						for(String s:str){
							onlineMap.put(s, new ArrayList<String>());
							ArrayList<String> list=map.get(s).getUser();
							for(int i=0;i<list.size();i++){
								if(UserOnline.getUserOnline().ifOnline(list.get(i))){
									onlineMap.get(s).add(list.get(i));
								}
							}
							umap.put(s, list);
							
						}
						JSONObject ja=JSONObject.fromObject(umap);
						os.write(ja.toString().getBytes());
						os.flush();
						
						is.read(); //�õ���Ҫִ�� �����ͻ���Ⱥ�������û����
						JSONObject jaonline=JSONObject.fromObject(onlineMap);
						os.write(jaonline.toString().getBytes());  //�����û����߱��
						os.flush();    
					}else if(msg.equals("09")){   // �õ��˺��µ�������Ϣ
					}
				}
			}catch (NotUserException e) {
				os.write("{\"state\":1,\"msg\":\"û���û���!\"}".getBytes());  
				os.flush();
			} catch (PasswordException e) {
				os.write("{\"state\":2,\"msg\":\"�������!\"}".getBytes());
				os.flush();
			} catch (AccountException e) {
				os.write("{\"state\":3,\"msg\":\"�˺ű���!\"}".getBytes());
				os.flush();
			} catch (SQLException e) {
				os.write("{\"state\":4,\"msg\":\"���ݿ���ֲ��!\"}".getBytes());
				os.flush();
			}
			
		} catch (IOException e) {
			System.out.println("���ʹ���");
		}finally{
			try {
				UserOnline.getUserOnline().outOnline(uid);
				is.close();
				os.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
