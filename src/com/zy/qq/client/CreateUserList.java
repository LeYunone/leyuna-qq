package com.zy.qq.client;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;

import com.zy.qq.uitility.CL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 创建群组下的用户列表
 * @author 清风理辛
 *
 */
public class CreateUserList extends JPanel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreateUserList() {
		setLayout(null);
			
		getFriend();
		CL.createu=this;
	}
	
	public void getFriend(){  //得到所有好友
		JSONArray jsonlist=JSONArray.fromObject(CL.json_friend);
		for(int i=0;i<jsonlist.size();i++){
			JSONObject json=(JSONObject) jsonlist.get(i);
			
			String uid=json.getString("uid");
			
			String netname=json.getString("netname");  //得到名字和邮箱
			String email=json.getString("email");   
			CreateUser cu=new CreateUser(email, netname,uid);
			CL.Create_Group_User_map.put(uid, cu);
		}
		String myuid=JSONObject.fromObject(CL.My_json_info).getString("uid");
		String netname=JSONObject.fromObject(CL.My_json_info).getString("netname");  //得到名字和邮箱
		String email=JSONObject.fromObject(CL.My_json_info).getString("email");   
		CreateUser cu=new CreateUser(email, netname,myuid);
		CL.Create_Group_User_map2.put(myuid, cu);	
		CL.Create_Group_User_map.remove(myuid);
		showUser();
	}
	
	public void showUser(){
		Collection <CreateUser> c=CL.Create_Group_User_map.values();
		List<CreateUser> list =new ArrayList<CreateUser>(c);
		this.removeAll();
		int i=0;
		for(CreateUser cu : list){
			cu.setBounds(10, 10+i++*25, 199, 23);
			this.add(cu);
		}
		this.setPreferredSize(new Dimension(0,45*list.size()));
		this.updateUI();
	}
	
	public void showUesrOne(String m){  //模糊匹配
		Collection <CreateUser> c=CL.Create_Group_User_map.values();
		List<CreateUser> list =new ArrayList<CreateUser>(c);
		this.removeAll();
		for(CreateUser cu : list){
			if(cu.getEmail().contains(m) || cu.getName().contains(m)){
				cu.setBounds(10, 10, 199, 23);
				this.add(cu);
			}
		}
		this.setPreferredSize(new Dimension(0,45*list.size()));
		this.updateUI();
	}
	
	
}
