package com.zy.qq.client;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.JLabel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zy.qq.uitility.CL;
/**
 * 群用户列表
 * @author 清风理辛
 *
 */
public class GroupUserList extends JPanel {
 
	private ArrayList<GroupUser> userlist=null;  //给每个群一个他们的用户组
	private String pid;
	
	public GroupUserList(ArrayList<GroupUser> userlist,String pid) {
		this.setBackground(new Color(255, 245, 238));
		this.userlist=userlist;
		this.pid=pid;
		setLayout(null);
		getOnline(this.userlist);
		CL.groupUserviewlist.add(this);
//		Timer t=new Timer();
//		t.schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				updateGroup();
//			}
//		}, 0, 5000);
		
	}
	
	public void getOnline(ArrayList<GroupUser> l){  //群下的用户在线更新
		String onlines=CL.Map_User_Group_Online;  //群下的在线用户编号 
		System.out.println("进行更新"+onlines);
		JSONObject json=JSONObject.fromObject(onlines);
		String onlineuid=json.getString(pid); //得到改群的在线用户表
		JSONArray ja=JSONArray.fromObject(onlineuid);
		for(GroupUser gu:userlist){
			gu.setOnline(false);  //先设置为下线
			for(int i=0;i<ja.size();i++){
				if(gu.getUid().equals(ja.get(i))){  //如果在线用户表与用户匹配
					gu.setOnline(true);
				}
			}
		}
		Collections.sort(userlist);
		
		this.removeAll();
		int i=0;
		for(GroupUser g : userlist){
			g.setBounds(10, 10+i++*40, 306, 59);
			this.add(g);
		}
		this.setPreferredSize(new Dimension(0,45*userlist.size()));
		this.updateUI();
	}

	public void updateGroup(){  //实时更新所有群用户信息
		JSONObject json=JSONObject.fromObject(CL.Map_User_Group);  //群与用户编号关系
		String myGroup=CL.stringbuffer_list_myGroup; //我的群编号
		String[] myGroups=myGroup.toString().split(",");
		for(String pid:myGroups){    //遍历每个群
			ArrayList<GroupUser> groupuserlist=new ArrayList<GroupUser>();  //存储群下用户
			String puids=json.getString(pid);  //得到群用户数组
			JSONArray ja=JSONArray.fromObject(CL.json_All_userinfo);  //所有用户的个人信息 
			JSONArray jas=JSONArray.fromObject(puids);  //创建群下的用户编号数组
			for(int i=0;i<ja.size();i++){
				JSONObject json2=(JSONObject) ja.get(i);
				for(int j=0;j<jas.size();j++){
					String puid=ja.getString(j);  //得到相应群下的用户编号
					if(json2.getString("uid").equals(puid)){  //  拿到群下用户的个人信息
						String netname=json2.getString("netname");
						String head=json2.getString("head");
						String sign=json2.getString("sign");
						String uid=json2.getString("uid");
						GroupUser groupuser=new GroupUser(netname, head, sign, uid); //创建群下的用户
						groupuserlist.add(groupuser);
						CL.groupuserMap.put(uid, groupuser);
						CL.group_user_map.put(pid, groupuserlist); // 更新直接将其覆盖
					} 
				}
			}
		}
		getOnline(CL.group_user_map.get(pid)); //重新调用在线
		
	}
}
