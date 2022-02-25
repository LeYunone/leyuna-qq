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
public class CreateUserList2 extends JPanel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreateUserList2() {
		setLayout(null);
		CL.createu2=this;
		showUser();
	}
	
	public void showUser(){ 
		Collection <CreateUser> c=CL.Create_Group_User_map2.values();
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
}
