package com.zy.qq.clientServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.zy.qq.uitility.CL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * ������Ӻ��ѷ������Ľӿ�
 * @author �������     (�ķϣ�)
 *
 */
public class AddForServer extends Thread {

	@Override
	public void run() {
		while(true){
			byte[]bf=new byte[2048*5];
			int len=bf.length;
			
			DatagramPacket datapack=new DatagramPacket(bf, len); //�հ�
			
			try {
				socket.receive(datapack);
				String msg=new String(datapack.getData(),0,datapack.getLength());
				
				JSONObject json=JSONObject.fromObject(msg);  //����
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
