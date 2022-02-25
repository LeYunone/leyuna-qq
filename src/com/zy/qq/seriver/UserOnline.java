package com.zy.qq.seriver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

/**
 * �û����߲�ѯ ��������
 * @author �������
 *
 */
public class UserOnline {

	private UserOnline(){}
	
	private static UserOnline useronline=new UserOnline();
	
	public static UserOnline getUserOnline(){
		return useronline;
	}
	
	private HashMap<String, Userinfo> map=new HashMap<String, Userinfo>();
	
	public void regOnline(Socket socket,String uid,String uemail,String uiphone){  //�������� �û��б�һ���û���Ž������ߵǼ�
		
		Userinfo userinfo=map.get(uid);
		if(userinfo!=null){  //�ж��˺��Ƿ�����    ���м����ж�
			try {
				userinfo.getSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		userinfo=new Userinfo();
		userinfo.setSocket(socket);
		userinfo.setUemail(uemail);
		userinfo.setUid(uid);
		userinfo.setUiphone(uiphone);
		map.put(uid, userinfo);  //���ߵǼ�
	}
	
	public boolean ifOnline(String uid){  //�鿴�û��Ƿ�����
		Userinfo userinfo=map.get(uid);
		if(userinfo==null){
			return false;
		}else{
			return true;
		}
	}
	
	public Set<String> getOnlineList(){  //�õ������û��б�  
		return map.keySet();
	}
	
	public void updateUserUDP(String uid,String ip,int port){
		map.get(uid).setUip(ip);
		map.get(uid).setUport(port);
	}
	
	public Userinfo getOnline(String uid){  //�õ������û�
		return map.get(uid);
	}
	
	public void outOnline(String uid){
		map.remove(uid);
	}
	
	
	private Hashtable<String, ArrayList<DatagramPacket>> messmap=new Hashtable<String, ArrayList<DatagramPacket>>();  //�洢������Ϣ
	private ArrayList<DatagramPacket> dataList=new ArrayList<DatagramPacket>();  //���߰�����
	
	
	public void addMess(String uid,DatagramPacket pack){ //���������Ϣ
		dataList.add(pack);
		messmap.put(uid, dataList);
	}
	
	public  ArrayList<DatagramPacket> getMess(String uid){  //ȡ��������Ϣ
		return messmap.get(uid);
	}
	
	public void clearMess(String uid){ //��յ�ǰ�û��µ�������Ϣ
		messmap.remove(uid);
	}
}
