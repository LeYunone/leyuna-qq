package com.zy.qq.seriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zy.qq.Exception.UsernameException;

import net.sf.json.JSONObject;
import net.sf.json.processors.JsonBeanProcessor;

/**
 * ע�������
 * @author �������
 *
 */
public class RegServer implements Runnable {

	private static HashMap<String, String> map=new HashMap<String, String>();
	
	@Override
	public void run() {
		InputStream is=null;
		OutputStream os=null;
		try{		
			is=socket.getInputStream();
			os=socket.getOutputStream();
			byte[] bf=new byte[2048];
			int len=is.read(bf);
			String msg=new String(bf,0,len);
			JSONObject json=JSONObject.fromObject(msg);
			String type=json.getString("type");
			if(type.equals("reg")){   //{"type":"","username":"",}
				String username = json.getString("username");
				String password = json.getString("password");
				String code = json.getString("code");
				String code1 = map.get(username);
				if (code1 != null) {
					map.remove(username);// �õ�������ɾ��
				}
				
				if (code1.equals(code)) {  //�����֤����ȷ
					try {
						new SQLSendemail().regUser(username, password);
					} catch (UsernameException e) {  //�û��쳣
						os.write("{\"state\":1,\"msg\":\"�û����Ѵ���!\"}".getBytes());
						os.flush();
						return;
					} catch (SQLException e) { //���ݿ��쳣
						os.write("{\"state\":3,\"msg\":\"������\"}".getBytes());
						os.flush();
						return;
					}
					os.write("{\"state\":0,\"msg\":\"ע��ɹ���\"}".getBytes());
					os.flush();
				} else {
					os.write("{\"state\":2,\"msg\":\"��֤����������»��!\"}".getBytes());
					os.flush();
				}
				
			}else if(type.equals("code")){   //��֤������
				String username=json.getString("username");  //�û���
				Random random = new Random();
				StringBuffer code = new StringBuffer();
				for (int i = 0; i < 6; i++) {
					code.append(random.nextInt(10));
				}
				if (username.indexOf("@") >= 0) {
					map.put(username, code.toString());
					SendCode.sendEmail(username, code.toString());
					os.write("{\"state\":0,\"msg\":\"��֤�뷢�ͳɹ���\"}".getBytes());
					os.flush();
				} else {
					os.write("{\"state\":1,\"msg\":\"��֤�뷢��ʧ�ܣ�\"}".getBytes());
					os.flush();
				}
//				if(username.trim().length()==11){     �ֻ�����  ����
//				try{
//					Long.parseLong(username); //�����ַ�
//				}catch (Exception e) {
//					os.write("{\"state\":1,\"msg\":\"�û����Ѵ���!\"}".getBytes());
//					os.flush();
//					return;
//				}
//			}
			}
			
		}catch (Exception e) {
			try {
				is.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				os.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	 private Socket socket;
	
	public RegServer(Socket socket){
		this.socket=socket;
	}
	
	public static void openServer() throws IOException{
		ExecutorService service = Executors.newFixedThreadPool(1000);
		ServerSocket server = new ServerSocket(4003);
		while (true) {
			Socket socket = server.accept();
			service.execute(new RegServer(socket));
		}
	}
}
