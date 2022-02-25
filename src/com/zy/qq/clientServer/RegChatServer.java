package com.zy.qq.clientServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import net.sf.json.JSONObject;

import net.sf.json.JSON;

import com.zy.qq.uitility.CL;

public class RegChatServer extends Thread {

	private DatagramSocket datasocket;
	
	@Override
	public void run() {
		String uid=JSONObject.fromObject(CL.My_json_info).getString("uid");
		String msg = "{\"type\":\"f5\",\"myuid\":\"" + uid + "\"}";
		while(true){
			try {
				DatagramPacket pack=new DatagramPacket(msg.getBytes(), msg.getBytes().length,InetAddress.getByName(CL.ip),CL.chat_port);
				datasocket.send(pack);
				Thread.sleep(10000);
			}catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public RegChatServer(DatagramSocket datasocket){
		this.datasocket=datasocket;
		this.start();
	}
}
