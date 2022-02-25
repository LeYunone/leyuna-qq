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
 * 添加好友服务器
 * @author 清风理辛
 *
 */
public class AddServer extends Thread {

	@Override
	public void run() {  //添加好友事务
		String msg=new String(datapack.getData(),0,datapack.getLength());   //先取得客户端发来的 json格式的信息 然后进行相应的判断
		JSONObject json=JSONObject.fromObject(msg);
		System.out.println("添加好友服务器接收到："+json);
			String type=json.getString("type");
			if(type.equals("Add")){  //如果是添加好友的命令     {"myuid":"","touid":"","type":"" }
				String touid=json.getString("touid");  //被添加方id
				if(!UserOnline.getUserOnline().ifOnline(touid)){  //如果对方没有在线
					UserOnline.getUserOnline().addMess(touid, datapack);  //则进行服务器的信息存储
					return;
				}
				Userinfo userinfo=UserOnline.getUserOnline().getOnline(touid);
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
			}else if(type.equals("Readd")){//回复添加好友的命令  {"myuid":"" ,"touid":" ","state":""}
				String touid=json.getString("touid");
				String myuid=json.getString("myuid");
				String state=json.getString("state");
				if(state.equals("0")){  //如果状态为0 则为接受  调动数据库
					int num=IFLogin.addFriend(myuid, touid);  
					System.out.println("添加好友受印象行数:"+num);
				}
				
				if(!UserOnline.getUserOnline().ifOnline(touid)){  //如果对方没有在线
					UserOnline.getUserOnline().addMess(touid, datapack);  //则进行服务器的信息存储
					return;
				}
				
				Userinfo userinfo=UserOnline.getUserOnline().getOnline(touid);
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
			}else if(type.equals("group")){  //{"type":"","uids":"","pgname"}				
				String pgname=json.getString("pgname");
				String uids=json.getString("uids");
				String pguser=json.getString("pguser");
				String [] puids=uids.split(",");
				System.out.println("接受到："+json);
				boolean create=IFLogin.createGroup(pgname,pguser,puids); 
				if(!create){
					try {
						throw new SQLException(); //如果报错则是数据库问题
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
				DatagramPacket datapack=new DatagramPacket(bf,len);  //创建一个空包裹 
				datasocket.receive(datapack);  //接受客户端发来的信息 整合成数据包
				execu.execute(new AddServer(datapack));   //开启多线程			
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
