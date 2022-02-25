package com.zy.qq.clientServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.zy.qq.uitility.CL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 连接添加好友服务器的接口
 * @author 清风理辛     (荒废！)
 *
 */
public class AddForServer extends Thread {

	@Override
	public void run() {
		while(true){
			byte[]bf=new byte[2048*5];
			int len=bf.length;
			
			DatagramPacket datapack=new DatagramPacket(bf, len); //空包
			
			try {
				socket.receive(datapack);
				String msg=new String(datapack.getData(),0,datapack.getLength());
				
				JSONObject json=JSONObject.fromObject(msg);  //解析
				String type=json.getString("type");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private DatagramSocket socket=null;
	
	public AddForServer(DatagramSocket socket){
		this.socket=socket;
		this.start();
	}
}
