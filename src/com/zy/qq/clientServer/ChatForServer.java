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
 * 连接聊天服务器 进行交互  接受消息   添加好友
 * @author 清风理辛
 *
 */
public class ChatForServer extends Thread {

	private DatagramSocket clientSocket=null;
	@Override
	public void run() {		
		while(true){  
			byte[]bf=new byte[2048*5];
			int len=bf.length;
			
			DatagramPacket pack=new DatagramPacket(bf, len); //空包
			try {
				
				clientSocket.receive(pack); //接受服务器上的消息
				String msg=new String(pack.getData(),0,pack.getLength());  //处理接受到的消息
				System.out.println("交互服务器："+msg);
				JSONObject json=JSONObject.fromObject(msg);
				String type=json.getString("type");
				if(type.equals("ok")){   //消息回执指令
					String ok="isok";
					bf=ok.getBytes();
					len=bf.length;
					pack=new DatagramPacket(bf, len,InetAddress.getByName(CL.ip),CL.chat_port);  //发送给服务器的包裹
					clientSocket.send(pack);
					
				}else if(type.equals("chat")){  //聊天指令         通过调用消息池中的 各自的聊天窗口的添加消息方法添加消息
					msg=new String(pack.getData(),0,pack.getLength());
					json=JSONObject.fromObject(msg);  //{"code":"","msg":"213123","myuid":"1","touid":"2","type":"chat"}
					MessPool.getMessPool().addMess(json);
					
				}else if(type.equals("Add")){  //如果接到添加好友的指令则 弹窗回复
					String myuid=json.getString("myuid");  //请求添加好友方的id
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
						System.out.println("添加好友");
						CL.json_friend=jsa.toString(); //更新好友列表  本地方
						if(CL.friend_list.equals("040")){
							StringBuffer sb=new StringBuffer();  //更新好友编号列表
							sb.append(myuid); 
							sb.append(",");
							CL.friend_list=sb.toString();
						}else{
							StringBuffer sb=new StringBuffer(CL.friend_list);  //更新好友编号列表
							sb.append(myuid); 
							sb.append(",");
							CL.friend_list=sb.toString();
						}
//						CL.friendlist.updateFriend();
					}
					String mymyuid=JSONObject.fromObject(CL.My_json_info).getString("uid"); //我自己的id
					String readd="{\"myuid\":\""+mymyuid+"\",\"touid\":\""+myuid+"\",\"state\":\""+state+"\",\"type\":\"Readd\"}";
					bf=readd.getBytes();
					len=bf.length;
					pack=new DatagramPacket(bf, len,InetAddress.getByName(CL.ip),CL.Add_port);
					clientSocket.send(pack); //发送回执信息
					
				}else if(type.equals("Readd")){  //  {"myuid":"" ,"touid":" ","state":""}
					String myuid=json.getString("myuid");  //回执方的id
					JSONArray ja=JSONArray.fromObject(CL.json_All_userinfo);
					String netname=null;
					String head=null;
					String sign=null;
					String email=null;
					for(int i=0;i<ja.size();i++){
						JSONObject j=(JSONObject) ja.get(i);
						if(j.getString("uid").equals(myuid)){
							netname=j.getString("netname");  //得到对方的网名
							head=j.getString("head");
							sign=j.getString("sign");
							email=j.getString("email");
						}
					}
					String state=json.getString("state");
					if(state.equals("0")){   //0为接受状态

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
						CL.json_friend=jsa.toString(); //更新好友列表
						System.out.println("新的好友："+CL.json_friend);
						if(CL.friend_list.equals("040")){
							StringBuffer sb=new StringBuffer();  //更新好友编号列表
							sb.append(myuid); 
							sb.append(",");
							CL.friend_list=sb.toString();
						}else{
							StringBuffer sb=new StringBuffer(CL.friend_list);  //更新好友编号列表
							sb.append(myuid); 
							sb.append(",");
							CL.friend_list=sb.toString();
						}
//						CL.friendlist.updateFriend();

					}else{
						Home.showAddONMsg(netname);
					}
				}else if(type.equals("file")){
					
				}else if(type.equals("chats")){  //如果接受到的是群聊指令
					
					MessPool.getMessPool().addMess(json);
				}else if(type.equals("fileNum")){  //如果接受到的是一个文件编号
					System.out.println("客户端1:得到文件编码========");
					String filenum=json.getString("files");  //得到这个文件编号
					if(!CL.fileTouid.equals("")){  //如果发现有需求向用户发送文件
						String touid=CL.fileTouid;
						String myuid=JSONObject.fromObject(CL.My_json_info).getString("uid");
						msg="{\"myuid\":\""+myuid+"\",\"touid\":\""+touid+"\",\"files\":\""+filenum+"\",\"type\":\"SendFile\"}";  //创建向好友发送文件的请求
						byte[] b=msg.getBytes();
						len=b.length;
						pack=new DatagramPacket(b,0,len,InetAddress.getByName(CL.ip),CL.chat_port); //向聊天服务器发送编号
						CL.datasocket.send(pack);
					}
				}else if(type.equals("SendFile")){  //如果接受到的是客户端发来的文件编号
					System.out.println("客户端2:接受到文件编码========");
					String filenum=json.getString("files");  //得到文件编号
					String myuid=json.getString("myuid"); //发送方的id  用于显示
					JSONArray ja=JSONArray.fromObject(CL.json_All_userinfo);
					String netname="";
					for(int i=0;i<ja.size();i++){
						JSONObject j=(JSONObject) ja.get(i);
						if(j.getString("uid").equals(myuid)){
							netname=j.getString("netname");
						}
					}
					String mymyuid=json.getString("touid");
					int num=JOptionPane.showConfirmDialog(null,netname+"向你发送一个文件，是否接收","系统提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(num==JOptionPane.YES_OPTION){
						msg="{\"myuid\":\""+mymyuid+"\",\"files\":\""+filenum+"\",\"type\":\"getFile\"}"; //向服务器请求下载
						byte[] b=msg.getBytes();
						len=b.length;
						pack=new DatagramPacket(b,0,len,InetAddress.getByName(CL.ip),CL.chat_port); //向聊天服务器发送编号
						CL.datasocket.send(pack);
					}
				}else if(type.equals("filename")){ //如果传过来的是文件名
					String filename=json.getString("filesname");
					CL.filename=filename;  //存储文件名
				}else if(type.equals("droupGroup")){
					String pid=json.getString("pid");
					System.out.println(pid);
					if(CL.groupMap.get(pid)!=null){
						CL.groupMap.remove(json.getString("pid"));  //将面板删除
						CL.grouplist.showGroup();
					}
				}else{ //如果没有主题 则为下载文件
					System.out.println("客户端2:得到文件========");
					FileOutputStream fos=null;
					try {
//						String path=(System.currentTimeMillis()+"").trim();
						System.out.println("文件名"+CL.filename);
						fos=new FileOutputStream(new File("D:/"+CL.filename));
						byte[] b=new byte[1024*10];
						 len = 0;   //数据长度
						    while (len == 0) {  //无数据则开始循环接收数据
						        //接收数据包		               
						        len = pack.getLength();
						        if (len > 0) {
						            //指定接收到数据的长度，可使程序正常接收数据
						            fos.write(b,0,len);
						            fos.flush();
						            len = 0;//循环接收
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
