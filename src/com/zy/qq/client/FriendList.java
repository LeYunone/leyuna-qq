package com.zy.qq.client;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zy.qq.uitility.CL;
/**
 * 好友列表
 * @author 清风理辛
 *
 */
public class FriendList extends JPanel {


	public FriendList() {
		setBackground(new Color(255, 245, 238));
		setLayout(null);
		
		updateFriend();
		CL.friendlist=this;
	}
	
	public void getOnline(){  //好友在线更新
		
		String[] onlines=CL.friend_online.split(",");
//		System.out.println("当前在线"+CL.friend_online);
		Set<String> set=CL.map.keySet();
		for(String uid: set){
			CL.map.get(uid).setOnline(false);   //先全部设为下线
		}
		if(!CL.friend_list.equals("没有好友在线") || CL.friend_list.trim().equals("")){
				
			for(String uid:onlines){
				Friends fs=CL.map.get(uid);
				if(fs!=null){
					CL.map.get(uid).setOnline(true);   //再把在线列表有编号的设置为在线
				}
			}
		}
		
		Collection <Friends> c=CL.map.values();
		List<Friends> list =new ArrayList<Friends>(c);
		Collections.sort(list);
		this.removeAll();
		int i=0;
		for(Friends f : list){
			f.setBounds(10, 10+i++*55, 453, 69);
			this.add(f);
		}
		this.setPreferredSize(new Dimension(0,50*list.size()));
		this.updateUI();
	}
	
	public void getFriend(){  //得到所有好友
		JSONArray jsonlist=JSONArray.fromObject(CL.json_friend);
		for(int i=0;i<jsonlist.size();i++){
			JSONObject json=(JSONObject) jsonlist.get(i);
			String head=json.getString("head");
			String netname=json.getString("netname");
			String sign=json.getString("sign");
			String uid=json.getString("uid");
			Friends fs=new Friends(head, netname, sign,uid);
			CL.map.put(uid, fs);  //添加好友列表
		}
	}
	
	public void updateFriend(){  //实时更新所有好友信息
//		System.out.println("更新好友");
		if(CL.map.size()==0){
			getFriend();
		}else{
			JSONArray jsonlist=JSONArray.fromObject(CL.json_friend);
			for(int i=0;i<jsonlist.size();i++){
				JSONObject json=(JSONObject) jsonlist.get(i);
				String uid=json.getString("uid");
				String head=json.getString("head");
				String netname=json.getString("netname");
				String sign=json.getString("sign");
				String email=json.getString("email");
				
				Friends fs=CL.map.get(uid);
				if(fs!=null){
					fs.setHead(head);
					fs.setNetname(netname);
					fs.setSign(sign);
					fs.setEmail(email);
				}else{
					Friends f =new Friends(head, netname, sign,uid);
					f.setEmail(email);
					CL.map.put(uid, f);  //添加好友列表
				}
			}
		}
		getOnline();
	}
}
