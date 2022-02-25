package com.zy.qq.clientModel;

import java.util.ArrayList;

import com.zy.qq.uitility.CL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * ��Ϣ��
 * @author �������
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
		mess.setTouid(touid);  //�����˽�� ���� ���ҡ� ����  �ҵı��      ����� Ⱥ��  ���� Ⱥ���
		mess.setMyuid(myuid);  //�����˽�� ���Ƿ��ͷ��ı��id     �����Ⱥ�����Ƿ��ͷ���id
		mess.setType(type);   //Ⱥ��chats  ˽��chat
		mess.setMsg(msg);
		if(type.equals("chat")){  //�����˽�� ��Ե�
			
			try{
				if(CL.Chatmap.get(myuid).isVisible()){
					CL.Chatmap.get(myuid).addTomess(msg);
				}else {
					throw new Exception(); 
				}
			}catch (Exception e) {

				if(CL.map.get(myuid)!=null ){
					System.out.println("���ú�������");
					CL.map.get(myuid).addMess(mess);  //��������¼
				}else{
					System.out.println("����İ��������");
					CL.groupuserMap.get(myuid).addMess(mess);
				}
			}
		}else if(type.equals("chats")){ //�����Ⱥ�ķ��͹�������Ϣ
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
