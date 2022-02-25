package com.zy.qq.clientServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zy.qq.View.Home;
import com.zy.qq.client.GroupUserList;
import com.zy.qq.uitility.CL;


/**
 * 客户端与服务器对答
 * @author 清风理辛
 *
 */
public class ForServer implements Runnable {

	private Socket socket=null;
	private InputStream is=null;
	private OutputStream os=null;
	private Thread thread;
	private boolean isrun=false;  //控制线程开关
	
	public static int num=0;
	
	@Override
	public void run() {   //与服务进进行交互的方法		
		try{
			while(isrun){
				Thread.sleep(5000);
				byte[]bf=new byte[2048*30];
				int len =0;
				os.write("03".getBytes());   //2  得到个人信息
				os.flush();
				len=is.read(bf);
				String my=new String(bf,0,len);
				CL.My_json_info=my;
//				System.out.println("个人："+my);
				
				os.write("01".getBytes());   //1得到好友信息
				os.flush();
				
				bf=new byte[10240];
				len  =is.read(bf);
				String msg=new String(bf,0,len);
				CL.json_friend=msg;    //传递好友信息
				
				os.write("02".getBytes());
				os.flush();
				is.read(); //读到服务器已响应指令
				String online=null;
				os.write(CL.friend_list.getBytes());
				os.flush();
				len=is.read(bf);  //得到所有在线好友的编号
				online=new String(bf,0,len);
				if(online.equals("040")){
					CL.friend_online="";
				}else{
					CL.friend_online=online;
//					System.out.println("好友在线编号："+CL.friend_online);
					CL.friendlist.updateFriend();  //实时更新用户下的所有好友信息
				}

				os.write("05".getBytes());
				os.flush();
				len=is.read(bf);
				String userinfoall=new String(bf,0,len);
				try {  
					if(!CL.json_All_userinfo.equals(userinfoall)){   //当有人的信息修改的时候
						CL.json_All_userinfo=userinfoall;
						CL.userinfolist.getAlluser();
						for(GroupUserList g:CL.groupUserviewlist){
							 g.updateGroup();
						}
					}
				} catch (Exception e) {
				}
				CL.json_All_userinfo=userinfoall;  //所有的用户信息 	
				os.write(1);
				os.flush();
				len=is.read(bf);
				String userinfoallid=new String(bf,0,len);  //接受到  xxx,xx,xx  编号
				if(!CL.All_userinfo_online.equals(userinfoallid)){  //如果发现有人上线了
					try {
						Player play=new Player(new FileInputStream( "Music/上线.mp3"));
						play.play();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (JavaLayerException e) {
						e.printStackTrace();
					}
					CL.All_userinfo_online=userinfoallid;
					CL.userinfolist.getAlluser();
				}
				CL.All_userinfo_online=userinfoallid;
				
				os.write("06".getBytes());  //更新加入的群编号
				os.flush();
				len=is.read(bf);
				String myGroup=new String(bf,0,len);			
				
				if(!CL.stringbuffer_list_myGroup.equals(myGroup)){  //如果发现群编号发生改变  
					System.out.println("退群成功");
				    CL.stringbuffer_list_myGroup=myGroup;
					os.write("07".getBytes());  //更新加入的群信息
					os.flush();
					len=is.read();
					os.write(CL.stringbuffer_list_myGroup.getBytes());
					os.flush();
					len=is.read(bf);
					String myGroupinfo=new String(bf,0,len); //得到自己的群的所有信息
					System.out.println("群："+myGroupinfo);
					CL.json_list_myGroupinfo=myGroupinfo;   //进行存储
					CL.grouplist.updateGroup();  //进行群信息
//					CL.grouplist.updateGroupList();//进行群列表
				}				
				CL.stringbuffer_list_myGroup=myGroup;   //得到所有的群编号
				
				os.write("08".getBytes()); //更新群下的用户           
				os.flush();
				is.read();
				os.write(CL.stringbuffer_list_myGroup.getBytes()); // 发送我的群编号
				os.flush();
				len=is.read(bf);
				String map_group_user=new String(bf,0,len);  //你群下的用户与群的关系
				CL.Map_User_Group=map_group_user;  //存储 群与用户编号的关系
				os.write(1);  //继续执行08  得到群中在线用户
				os.flush();
				len=is.read(bf);
				String map_group_user_online=new String(bf,0,len);
//				System.out.println("群在线编号:"+map_group_user_online);
				try{
					if(!CL.Map_User_Group_Online.equals(map_group_user_online)){
//						System.out.println("进行群在线更新！");
						CL.Map_User_Group_Online=map_group_user_online;  //得到json格式的  群编号：  群用户在线编号
						CL.grouplist.updateGroupList(); //进行用户列表更新
						if(CL.groupUserviewlist.size()!=0){
							for(GroupUserList g:CL.groupUserviewlist){
								 g.updateGroup();
							}
						}
					}
				}catch (Exception e) {
//					System.out.println("更新组员在线出现错误");
				}
				CL.Map_User_Group_Online=map_group_user_online;  //得到json格式的  群编号：  群用户在线编号
//				CL.groupuserlist.updateGroup();
//				json=JSONObject.fromObject(CL.Map_User_Group);  //群与用户编号关系
//				String puid=json.getString("1");
//				JSONArray ja=JSONArray.fromObject(puid);
//				for(int i=0;i<ja.size();i++){
//					String s=(String) ja.get(i);
//					System.out.println(s);
//				}
				
			}
		}catch (Exception e) {
			System.out.println("与服务器断开连接");
			e.printStackTrace();
			isrun=false;
		}finally{
			
		}
	}

	@SuppressWarnings("deprecation")
	public JSONObject login(){
		try {
			
			socket=new Socket(CL.ip,CL.Login_port);  //连接服务器
			is=socket.getInputStream();  //接通服务器端 
			os=socket.getOutputStream(); //发送服务器信息
			String msg="{\"username\":\""+CL.username+"\",\"password\":\""+CL.password+"\"}";
			
			os.write(msg.getBytes()); //请求登录       发送信息 {"username":"xxxx","password":"xxxx"}
			os.flush();
			
			byte[] bf=new byte[2048];
			int len=is.read(bf);
			msg=new String(bf,0,len);
			
			JSONObject json=JSONObject.fromObject(msg);
			
			int state=json.getInt("state");
			if(state==0){ //如果账号没被封
				if(thread!=null){  //判断是否已经连上
					if(thread.getState()==Thread.State.RUNNABLE){
						
						thread.stop();
						isrun=false;
					}
				}
				
				////////////////////////////////////       进行登陆服务    
				os.write("01".getBytes());   //1得到好友信息
				os.flush();
				
				bf=new byte[10240];
				len  =is.read(bf);
				msg=new String(bf,0,len);
				
				CL.json_friend=msg;    //传递好友信息
//				System.out.println(msg);  //json数组的好友信息
				System.out.println("好友信息："+msg);
				JSONArray jlist=JSONArray.fromObject(CL.json_friend);
				StringBuffer sb=new StringBuffer();
				for(int i=0;i<jlist.size();i++){
					JSONObject jsons=(JSONObject) jlist.get(i);
					String uid=jsons.getString("uid");
					sb.append(uid);
					sb.append(",");
				}
				if(sb.toString().equals("")){
					CL.friend_list="040";  //如果没有好友得到040错误
				}else{
					CL.friend_list=sb.toString();  //存储仅包含好友编号的信息
				}
				
				os.write("03".getBytes());   //2  得到个人信息
				os.flush();
				len=is.read(bf);
				msg=new String(bf,0,len);
				
				CL.My_json_info=msg;
				System.out.println("个人："+msg);
				
				os.write("06".getBytes());  //更新加入的群编号
				os.flush();
				len=is.read(bf);
				String myGroup=new String(bf,0,len);			
				CL.stringbuffer_list_myGroup=myGroup;   //得到所有的群编号
//				System.out.println("群编号:"+CL.stringbuffer_list_myGroup);
				os.write("07".getBytes());  //更新加入的群信息
				os.flush();
				len=is.read();
				os.write(CL.stringbuffer_list_myGroup.getBytes());
				os.flush();
				len=is.read(bf);
				String myGroupinfo=new String(bf,0,len); //得到自己的群的所有信息
				CL.json_list_myGroupinfo=myGroupinfo;   //进行存储
				System.out.println("你的群信息:"+CL.json_list_myGroupinfo);
				
				os.write("05".getBytes());
				os.flush();
				len=is.read(bf);
				String userinfoall=new String(bf,0,len);
				CL.json_All_userinfo=userinfoall;  //所有的用户信息 	
				os.write(1);
				os.flush();
				len=is.read(bf);
				String userinfoallid=new String(bf,0,len);  //接受到  xxx,xx,xx  编号
				CL.All_userinfo_online=userinfoallid;
				os.write("08".getBytes()); //更新群下的用户           
				os.flush();
				is.read();
				os.write(CL.stringbuffer_list_myGroup.getBytes()); // 发送我的群编号
				os.flush();
				len=is.read(bf);
				String map_group_user=new String(bf,0,len);  //你群下的用户与群的关系
				CL.Map_User_Group=map_group_user;  //存储 群与用户编号的关系
				JSONObject jsons=JSONObject.fromObject(CL.Map_User_Group);
				System.out.println(jsons);
				os.write(1);  //继续执行08  更新群中在线用户
				os.flush();
				len=is.read(bf);
				String map_group_user_online=new String(bf,0,len);
				CL.Map_User_Group_Online=map_group_user_online;  //得到json格式的  群编号：  群用户在线编号
				jsons=JSONObject.fromObject(CL.Map_User_Group);  //群与用户编号关系
				
				//////////////////////////////////////////////
				
				///////////////////////////////////
				thread=new Thread(this);
				isrun=true;
				thread.start();
			}
			return json;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
