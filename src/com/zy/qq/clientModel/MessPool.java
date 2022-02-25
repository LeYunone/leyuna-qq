package com.zy.qq.clientModel;

import java.util.ArrayList;

import com.zy.qq.uitility.CL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 消息池
 * @author 清风理辛
 *
 */
public class MessPool {
	
	
	private MessPool(){}
	
	private static MessPool messpool=new MessPool();
	
	public static MessPool getMessPool(){
		return messpool;
	}

	public void addMess(JSONObject json){
		String myuid=json.getString("myuid"); 

//		System.out.println(CL.Chatmap); 
		Mess mess=new Mess();
		String msg=json.getString("msg");
		String code=json.getString("code");
		String touid=json.getString("touid");
		String type=json.getString("type");
		mess.setCode(code);
		mess.setTouid(touid);  //如果是私聊 则是 向“我” 发送  我的编号      如果是 群聊  则是 群编号
		mess.setMyuid(myuid);  //如果是私聊 则是发送方的编号id     如果是群聊则是发送方的id
		mess.setType(type);   //群聊chats  私聊chat
		mess.setMsg(msg);
		if(type.equals("chat")){  //如果是私聊 点对点
			
			try{
				if(CL.Chatmap.get(myuid).isVisible()){
					CL.Chatmap.get(myuid).addTomess(msg);
				}else {
					throw new Exception(); 
				}
			}catch (Exception e) {

				if(CL.map.get(myuid)!=null ){
					System.out.println("调用好友聊天");
					CL.map.get(myuid).addMess(mess);  //添加聊天记录
				}else{
					System.out.println("调用陌生人聊天");
					CL.groupuserMap.get(myuid).addMess(mess);
				}
			}
		}else if(type.equals("chats")){ //如果是群聊发送过来的信息
			try{
				if(CL.chatsmap.get(touid).isVisible()){
					JSONArray a=JSONArray.fromObject(CL.json_All_userinfo);
					String name=null;
					for(int i=0;i<a.size();i++){
						json=(JSONObject) a.get(i);
						if(myuid.equals(json.getString("uid"))){
							name=json.getString("netname");
						}
					}
					CL.chatsmap.get(touid).addTomess(msg, name);
				}else{
					throw new Exception();
				}
			}catch (Exception e) {
				CL.groupMap.get(touid).addMess(mess);
			}
		}

	}
}
