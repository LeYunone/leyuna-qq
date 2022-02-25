package com.zy.qq.seriver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

/**
 * 用户在线查询 服务器用
 * @author 清风理辛
 *
 */
public class UserOnline {

	private UserOnline(){}
	
	private static UserOnline useronline=new UserOnline();
	
	public static UserOnline getUserOnline(){
		return useronline;
	}
	
	private HashMap<String, Userinfo> map=new HashMap<String, Userinfo>();
	
	public void regOnline(Socket socket,String uid,String uemail,String uiphone){  //给服务器 用户列表一个用户编号进行在线登记
		
		Userinfo userinfo=map.get(uid);
		if(userinfo!=null){  //判断账号是否在线    进行挤号判断
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
		map.put(uid, userinfo);  //在线登记
	}
	
	public boolean ifOnline(String uid){  //查看用户是否在线
		Userinfo userinfo=map.get(uid);
		if(userinfo==null){
			return false;
		}else{
			return true;
		}
	}
	
	public Set<String> getOnlineList(){  //得到在线用户列表  
		return map.keySet();
	}
	
	public void updateUserUDP(String uid,String ip,int port){
		map.get(uid).setUip(ip);
		map.get(uid).setUport(port);
	}
	
	public Userinfo getOnline(String uid){  //得到在线用户
		return map.get(uid);
	}
	
	public void outOnline(String uid){
		map.remove(uid);
	}
	
	
	private Hashtable<String, ArrayList<DatagramPacket>> messmap=new Hashtable<String, ArrayList<DatagramPacket>>();  //存储离线信息
	private ArrayList<DatagramPacket> dataList=new ArrayList<DatagramPacket>();  //离线包载体
	
	
	public void addMess(String uid,DatagramPacket pack){ //添加离线信息
		dataList.add(pack);
		messmap.put(uid, dataList);
	}
	
	public  ArrayList<DatagramPacket> getMess(String uid){  //取得离线信息
		return messmap.get(uid);
	}
	
	public void clearMess(String uid){ //清空当前用户下的离线信息
		messmap.remove(uid);
	}
}
