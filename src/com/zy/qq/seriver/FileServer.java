package com.zy.qq.seriver;
/**
 * �ļ����յķ�����
 * @author �������
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
 * ���������
 * @author �������
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
			System.out.println("������:�õ��ļ�========");
			String num=(System.currentTimeMillis()+"").trim(); //�ļ����
			fos=new FileOutputStream("E:/"+CL.filenameserver);
			fos.write(datapack.getData(),0,datapack.getLength());
			fos.flush();
			String path="E:/"+CL.filenameserver;
			CL.fileMap.put(num,path ); //�洢�ļ�
			String msg="{\"files\":\""+num+"\",\"type\":\"fileNum\"}"; //��ִһ���ļ����
			JSONObject json=JSONObject.fromObject(msg);
			byte[] b=json.toString().getBytes();
			int len=b.length;
			InetAddress ip=InetAddress.getByName(datapack.getAddress().getHostAddress()); //�����ļ�����ip
			int port=datapack.getPort();  //�����ļ����Ķ˿�
			datapack=new DatagramPacket(b, 0,len,ip,port); //
			datasocket.send(datapack);  //��ִ�ļ����
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
				DatagramPacket datapack=new DatagramPacket(bf,len);  //����һ���հ��� 
				datasocket.receive(datapack);  //���ܿͻ��˷�������Ϣ ���ϳ����ݰ�
				es.execute(new FileServer(datapack));   //�������߳�			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
