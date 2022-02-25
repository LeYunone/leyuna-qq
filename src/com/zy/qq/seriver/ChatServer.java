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
 * 聊天服务器
 * @author 清风理辛
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
		String msg=new String(datapack.getData(),0,datapack.getLength());   //先取得客户端发来的 json格式的信息 然后进行相应的判断
//		System.out.println(msg);
		JSONObject json=JSONObject.fromObject(msg);
			
			String type=json.getString("type");
//			System.out.println("聊天服务器收到type:"+type);
			if(type.equals("chat") ){  //如果接受到的信息时聊天业务
				String touid=json.getString("touid");
				String myuid=json.getString("myuid");
				if(!UserOnline.getUserOnline().ifOnline(touid)){  //如果对方没有在线
					UserOnline.getUserOnline().addMess(touid, datapack);  //则进行服务器的信息存储
					return;
				}
				UserOnline.getUserOnline().updateUserUDP(myuid, datapack.getAddress().getHostAddress(), datapack.getPort());
				Userinfo userinfo=UserOnline.getUserOnline().getOnline(touid);  //得到对方的ip用户
				try {
					datapack=new DatagramPacket(
							datapack.getData(),0,datapack.getLength(),   // json格式的数据包 包含信息和信息编号 原数据包
							InetAddress.getByName(userinfo.getUip())     //用户的ip地址
							,userinfo.getUport());                      //用户的端口
					
					datasocket.send(datapack);  //  发给客户端

				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}else if(type.equals("ok")){  //消息确认收到与否
								
			}else if(type.equals("f5")){
				String myuid=json.getString("myuid");
				UserOnline.getUserOnline().updateUserUDP(myuid, datapack.getAddress().getHostAddress(), datapack.getPort());
				Userinfo userinfo=UserOnline.getUserOnline().getOnline(myuid);  //得到我的ip用户
				if(UserOnline.getUserOnline().getMess(myuid)!=null){  //如果我的账号下有离线信息
					ArrayList<DatagramPacket> datalist=UserOnline.getUserOnline().getMess(myuid);
					for(DatagramPacket datagramPacket:datalist){   //遍历所有的信息 发送到我的客户端端口上
						try {
							datagramPacket=new DatagramPacket(
									datagramPacket.getData(),0,datagramPacket.getLength(),   // json格式的数据包 包含信息和信息编号 原数据包
									InetAddress.getByName(userinfo.getUip())     //用户的ip地址
									,userinfo.getUport()); 	//用户的端口
							datasocket.send(datagramPacket);  //  发给客户端
						} catch (Exception e) {
							e.printStackTrace();
						}                      						
					}
					UserOnline.getUserOnline().clearMess(myuid);
					
				}
			}else if(type.equals("chats")){  //如果接受到的是群聊的信息
				String pid=json.getString("touid");  //得到群号
				HashMap<String,Group> map=IFLogin.getGroup(pid);  //得到group  里面有list（所有用户的编号）
				ArrayList<String> list=map.get(pid).getUser(); //所有用户编号
				for(int i=0;i<list.size();i++){  	//遍历群友编号
					Userinfo userinfo=UserOnline.getUserOnline().getOnline(list.get(i));
					if(!UserOnline.getUserOnline().ifOnline(list.get(i))){  	//如果对方没有在线
						UserOnline.getUserOnline().addMess(list.get(i), datapack);  	//则进行服务器的信息存储
					}else {  //如果在线
						try{
							datapack=new DatagramPacket(
									datapack.getData(),0,datapack.getLength(),   // json格式的数据包 包含信息和信息编号 原数据包
									InetAddress.getByName(userinfo.getUip())     //用户的ip地址
									,userinfo.getUport());                      //用户的端口
							datasocket.send(datapack);  //  发给客户端
						}catch (Exception e) {
						}
					}
				}
			}else if(type.equals("save")){  //如果接受到的是保存信息
				IFLogin.saveUserinfo(json);
			}else if(type.equals("delete")){  //删除好友
				
				String touid=json.getString("touid");
				String myuid=json.getString("myuid");
				IFLogin.deleteFriends(myuid, touid);
				
			}else if(type.equals("deleteGroup")){  //退群
				
				String uid=json.getString("myuid");
				String pid=json.getString("pid");
				IFLogin.deleteGroup(pid,uid);  //进行删除操作
				
			}else if(type.equals("SendFile")){  //如果是发送文件
				System.out.println("服务器:得到发送文件编码给客户端========");
				String touid=json.getString("touid");  //向他发
//				String myuid=json.getString("myuid");  //发送方的id
				if(!UserOnline.getUserOnline().ifOnline(touid)){  //如果对方没有在线
					UserOnline.getUserOnline().addMess(touid, datapack);  //则进行服务器的信息存储
					return;
				}
				Userinfo userinfo=UserOnline.getUserOnline().getOnline(touid);  //得到对方的ip用户
				System.out.println("向他发送"+touid +"编码:"+json.getString("files"));
				try {
					datapack=new DatagramPacket(
							datapack.getData(),0,datapack.getLength(),   // json格式的数据包 包含信息和信息编号 原数据包
							InetAddress.getByName(userinfo.getUip())     //用户的ip地址
							,userinfo.getUport());                      //用户的端口
					System.out.println("发送这个东西");
					datasocket.send(datapack);  //  发给客户端
					System.out.println("发送成功");
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(type.equals("getFile")){  //如果接受到的是要向他发送文件的请求
				System.out.println("服务器:客户端请求得到文件========");
				String filenum=json.getString("files");
				String path=CL.fileMap.get(filenum);  //得到需要发送的文件路径
				String myuid=json.getString("myuid");  //需要向 myuid发送文件
				FileInputStream fis=null;
				try {
					fis=new FileInputStream(new File(path));
					byte[] b=new byte[fis.available()];
					fis.read(b);
					
					Userinfo userinfo=UserOnline.getUserOnline().getOnline(myuid);  //得到对方的ip用户
					datapack=new DatagramPacket(
							datapack.getData(),0,datapack.getLength(),   // json格式的数据包 包含信息和信息编号 原数据包
							InetAddress.getByName(userinfo.getUip())     //用户的ip地址
							,userinfo.getUport());                      //用户的端口
					datasocket.send(datapack);  //  发给客户端
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(type.equals("filename")){ //如果是文件名存储
				String filename=json.getString("filesname");
				CL.filenameserver=filename;
				System.out.println(CL.filenameserver);
				String touid=json.getString("touid");
				if(!UserOnline.getUserOnline().ifOnline(touid)){  //如果对方没有在线
					UserOnline.getUserOnline().addMess(touid, datapack);  //则进行服务器的信息存储
					return;
				}
				Userinfo userinfo=UserOnline.getUserOnline().getOnline(touid);  //得到对方的ip用户
				try {
					datapack=new DatagramPacket(
							datapack.getData(),0,datapack.getLength(),   // json格式的数据包 包含信息和信息编号 原数据包
							InetAddress.getByName(userinfo.getUip())     //用户的ip地址
							,userinfo.getUport());                      //用户的端口
					datasocket.send(datapack);  //  发给客户端
				}catch (Exception e) {
				}
			}else if(type.equals("droupGroup")){  //如果得到的是解散群组的命令
				
				String pid=json.getString("pid");
				HashMap<String, Group> map=IFLogin.getGroup(pid); //得到所有的用户编号
				ArrayList<String> list=map.get(pid).getUser(); //所有用户编号
				for(String uid : list){
					if(!UserOnline.getUserOnline().ifOnline(uid)){  //如果对方没有在线 则不进行处理
					}else {
						Userinfo userinfo=UserOnline.getUserOnline().getOnline(uid);  //得到对方的ip用户
						try {
							datapack=new DatagramPacket(
									datapack.getData(),0,datapack.getLength(),   // json格式的数据包 包含信息和信息编号 原数据包
									InetAddress.getByName(userinfo.getUip())     //用户的ip地址
									,userinfo.getUport());                      //用户的端口
							datasocket.send(datapack);  //  发给客户端
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
				DatagramPacket datapack=new DatagramPacket(bf,len);  //创建一个空包裹 
				datasocket.receive(datapack);  //接受客户端发来的信息 整合成数据包
				es.execute(new ChatServer(datapack));   //开启多线程			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
