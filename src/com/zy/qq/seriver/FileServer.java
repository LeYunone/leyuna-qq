package com.zy.qq.seriver;
/**
 * 文件接收的服务器
 * @author 清风理辛
 *
 */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;


import com.zy.qq.uitility.CL;

/**
 * 聊天服务器
 * @author 清风理辛
 *
 */
public class FileServer implements Runnable {

	private DatagramPacket datapack;
	private static DatagramSocket datasocket=null;
	private FileOutputStream fos=null;
	public FileServer(DatagramPacket datapack){
		this.datapack=datapack;
	}
	
	
	@Override
	public void run() {
		try {
			System.out.println("服务器:得到文件========");
			String num=(System.currentTimeMillis()+"").trim(); //文件编号
			fos=new FileOutputStream("E:/"+CL.filenameserver);
			fos.write(datapack.getData(),0,datapack.getLength());
			fos.flush();
			String path="E:/"+CL.filenameserver;
			CL.fileMap.put(num,path ); //存储文件
			String msg="{\"files\":\""+num+"\",\"type\":\"fileNum\"}"; //回执一个文件编号
			JSONObject json=JSONObject.fromObject(msg);
			byte[] b=json.toString().getBytes();
			int len=b.length;
			InetAddress ip=InetAddress.getByName(datapack.getAddress().getHostAddress()); //发送文件方的ip
			int port=datapack.getPort();  //发送文件方的端口
			datapack=new DatagramPacket(b, 0,len,ip,port); //
			datasocket.send(datapack);  //回执文件编号
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void openFileServer(){
		ExecutorService es=Executors.newFixedThreadPool(1000);
		
		try {
			datasocket=new DatagramSocket(CL.File_port);
			while(true){
				byte[] bf=new byte[2048*5];
				int len=bf.length;
				DatagramPacket datapack=new DatagramPacket(bf,len);  //创建一个空包裹 
				datasocket.receive(datapack);  //接受客户端发来的信息 整合成数据包
				es.execute(new FileServer(datapack));   //开启多线程			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
