package com.zy.qq.client;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import com.zy.qq.uitility.CL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 用户列表载体
 * @author 清风理辛
 *
 */
public class UserinfoList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserinfoList() {
		setLayout(null);
		getAlluser();
		CL.userinfolist=this;
		this.setBackground(new Color(255, 245, 238));
	}
	
	public void getAlluser(){  //得到所有用户信息
//		if(CL.json_All_userinfo.equals("")){
//			System.out.println("111");
//		}
//		if(CL.json_All_userinfo==null){
//			System.out.println("222");
//		}
		System.out.println(CL.json_All_userinfo);
		JSONArray ja=JSONArray.fromObject(CL.json_All_userinfo); //所有用户的信息
		for(int i=0;i<ja.size();i++){
			JSONObject json=(JSONObject) ja.get(i);
			String head=json.getString("head");
			String netname=json.getString("netname");
			String sign=json.getString("sign");
			String uid=json.getString("uid");
			Userinfo userinfo=new Userinfo(uid, head, netname, sign);
			CL.Allusermap.put(uid, userinfo);
		}
		getOnlineUserinfo();
	}
	
	public void getOnlineUserinfo(){  //得到所有在线用户
		String [] onlineuid=CL.All_userinfo_online.split(",");  //得到所有在线用户编号
		Set<String> set =CL.Allusermap.keySet(); //所有用户id
		for(String id:set){
			CL.Allusermap.get(id).setOnline(false); //先将所有的用户设置为下线状态
		}
		
		for(String uid:onlineuid){
			Userinfo userinfo=CL.Allusermap.get(uid);
			if(userinfo!=null){
				CL.Allusermap.get(uid).setOnline(true);  //将在线用户设置为在线
			}
		}
		Collection <Userinfo> c=CL.Allusermap.values();
		List<Userinfo> list =new ArrayList<Userinfo>(c);
		Collections.sort(list);
		this.removeAll();
		int count=0;
//		int x=217;
//		int y=83;
		int x=0;
		int y=0;
		for(int i=0;i<list.size();i++){
			Userinfo userinfo=list.get(i);
			userinfo.setBounds(10+x, 10+y, 180 ,90);
			x+=217;
			count++;
			if(count==3){
				y+=83;
				x=0;
				count=0;
			}
			this.add(userinfo);
		}
		this.setPreferredSize(new Dimension(0,80*list.size()));
		this.updateUI();
	}
}
