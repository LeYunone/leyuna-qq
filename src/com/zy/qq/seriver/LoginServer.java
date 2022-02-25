package com.zy.qq.seriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zy.qq.Exception.AccountException;
import com.zy.qq.Exception.NotUserException;
import com.zy.qq.Exception.PasswordException;
import com.zy.qq.uitility.CL;
/**
 * 登录服务器
 * @author 清风理辛
 *
 */
public class LoginServer implements Runnable {
	
	private Socket socket;
	
	public LoginServer(Socket socket){
		this.socket=socket;
	}

	public static void openServer(){  //打开服务器
		ExecutorService es=Executors.newFixedThreadPool(1000);  //创建1000个线程池
		
		try {
			ServerSocket serversocket=new ServerSocket(CL.Login_port);
			while(true){
				Socket socket=serversocket.accept();  //等待用户端连接
				//如果连上
				es.execute(new LoginServer(socket));  //创建一个新用户至服务器线程池
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {   //服务器线程事务
		InputStream is=null;
		OutputStream os=null;
		String uid=null;
		try {
			is=socket.getInputStream();
			os=socket.getOutputStream();
			byte[] bf=new byte[2048];
			int len=is.read(bf);  //String msg="{\"iphone\":\""+iphone+"\",\"email\":\""+email+"\",\"name\":\""+name+"\",\"passwod\":\""+password+"\"}";

			String msg=new String(bf,0,len);  //接受客户端的登录请求
			
			JSONObject json=JSONObject.fromObject(msg); //解析登录信息
			String username=json.getString("username");
			String password=json.getString("password");
			 ///判断是否是手机号码登录
			String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		    Pattern p = Pattern.compile(regExp);
		    Matcher m = p.matcher(username);
		    boolean lis=m.matches();
		    
			try{   //登录时进行检测 服务器回复登录问题   登录成功后注册在线
				if(lis){ //如果是手机号码   
					uid=IFLogin.loginiphone(username, password);  //手机登录
					UserOnline.getUserOnline().regOnline(socket, uid, null, username);
				}else{ 
					uid=IFLogin.loginemail(username, password);   //邮箱登录
					UserOnline.getUserOnline().regOnline(socket, uid, username, null);
					
 				}
				
				//如果没有抛出异常 ，则 登录成功服务器回复0
				os.write("{\"state\":0,\"msg\":\"登录成功!\"}".getBytes());
				os.flush();
				
				while(true){  //登录后成功服务器相连
					bf=new byte[2048*5];
					len=is.read(bf);
//					HashMap<String,Group> map=IFLogin.getGroup();  //得到所有群的信息   （群的编号   群下的用户编号   Group.getUser）
					msg=new String(bf,0,len);
//					System.out.println("得到执行"+msg);
					if(msg.equals("01")){   //传送好友列表
						 Vector<Friend> vc=IFLogin.getFriend(uid);  //得到好友
						 os.write(JSONArray.fromObject(vc).toString().getBytes());
						 os.flush();
					}else if(msg.equals("02")){  //传送好友在线列表
						//更新前，客服端向服务器发送一个自己的好友列表
						os.write(1);// "给我列表"
						os.flush();
						len=is.read(bf);  // 读到客户端发来的列表  XXXX,XXXX,XXX 好友编号
						msg=new String(bf,0,len);
						if(msg.equals("040")){  //如果接受到的是没有好友的列表信息
							os.write("040".getBytes());
						}else{
							String fs[]=msg.split(",");
							StringBuffer sb=new StringBuffer();
							for(String f:fs){
								System.out.println(f);
								if(UserOnline.getUserOnline().ifOnline(f)){
									sb.append(f);
									sb.append(",");
								}
							}
							if(sb.length()==0){
								System.out.println("没有好友在线");
								os.write(0);
								os.flush();
							}else{
								os.write(sb.toString().getBytes());
								os.flush();
							}
						}
						
					}else if(msg.equals("03")){  //传送个人信息
						Myuserinfo user=IFLogin.reMyuser(uid);
						json=JSONObject.fromObject(user);
						os.write(json.toString().getBytes());
						os.flush();
					}else if(msg.equals("04")){  //保存个人信息   通过客户端传送过来的My_json_info  json格式的个人资料进行数据库保存更新
						
						
						
					}else if(msg.equals("05")){  //传输所有的用户信息 以备使用   两部操作
						Vector<Friend> userinfoall=IFLogin.queryAll();
						JSONArray ja=JSONArray.fromObject(userinfoall);
						os.write(ja.toString().getBytes());  //所有的用户信息  
						os.flush();
						is.read(); //读到继续指令
						//将所有在线用户的编号发给客户端
						Set<String> setonline=UserOnline.getUserOnline().getOnlineList();  //得到所有在线用户的编号
						//得到[x,x]
						Iterator<String> it=setonline.iterator();
						StringBuffer sb=new StringBuffer();
						while(it.hasNext()){
							sb.append(it.next());
							sb.append(",");
						}
						os.write(sb.toString().getBytes());
						os.flush();
					}else if(msg.equals("06")){  //传输我加入的群编号
						StringBuffer sb=IFLogin.getMyGroup(uid);
						os.write(sb.toString().getBytes());  //传输群编号
						os.flush();
					}else if(msg.equals("07")){  //传输客户端传输过来的群编号的群信息
						os.write(22);  //给我你的群的编号
						os.flush();
						len=is.read(bf); //读取发送过来的群编号
						msg=new String(bf,0,len);
						String[] pids=msg.split(",");
						Vector<Groupinfo> list=IFLogin.getMyGroupinfo(pids);
						JSONArray jarry=JSONArray.fromObject(list);
						os.write(jarry.toString().getBytes());  //向客户端发送你加入的群的信息
						os.flush();
					}else if(msg.equals("08")){  //查询我的群的所有用户
						os.write(1); //给我你的群号
						os.flush();
						len=is.read(bf);   //接受到你给我的你的群编号
						msg=new String(bf,0,len);
						String [] str=msg.split(",");
						HashMap<String, Group> map=IFLogin.getGroup(str); //每个群里所有的用户编号
						HashMap<String,ArrayList<String>> umap=new HashMap<String, ArrayList<String>>();  //用户与群的关系
						HashMap<String,ArrayList<String>> onlineMap=new HashMap<String, ArrayList<String>>();  //群下用户的在线编号
						for(String s:str){
							onlineMap.put(s, new ArrayList<String>());
							ArrayList<String> list=map.get(s).getUser();
							for(int i=0;i<list.size();i++){
								if(UserOnline.getUserOnline().ifOnline(list.get(i))){
									onlineMap.get(s).add(list.get(i));
								}
							}
							umap.put(s, list);
							
						}
						JSONObject ja=JSONObject.fromObject(umap);
						os.write(ja.toString().getBytes());
						os.flush();
						
						is.read(); //得到需要执行 传给客户端群中在线用户编号
						JSONObject jaonline=JSONObject.fromObject(onlineMap);
						os.write(jaonline.toString().getBytes());  //传送用户在线编号
						os.flush();    
					}else if(msg.equals("09")){   // 得到账号下的离线信息
					}
				}
			}catch (NotUserException e) {
				os.write("{\"state\":1,\"msg\":\"没用用户名!\"}".getBytes());  
				os.flush();
			} catch (PasswordException e) {
				os.write("{\"state\":2,\"msg\":\"密码错误!\"}".getBytes());
				os.flush();
			} catch (AccountException e) {
				os.write("{\"state\":3,\"msg\":\"账号被封!\"}".getBytes());
				os.flush();
			} catch (SQLException e) {
				os.write("{\"state\":4,\"msg\":\"数据库出现差错!\"}".getBytes());
				os.flush();
			}
			
		} catch (IOException e) {
			System.out.println("发送错误");
		}finally{
			try {
				UserOnline.getUserOnline().outOnline(uid);
				is.close();
				os.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
