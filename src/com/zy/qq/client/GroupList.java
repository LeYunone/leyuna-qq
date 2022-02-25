package com.zy.qq.client;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JLabel;

import com.zy.qq.View.Home;
import com.zy.qq.uitility.CL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 群组列表
 * @author 清风理辛
 *
 */
public class GroupList extends JPanel {

	/**
	 * Create the panel.
	 */
	public GroupList() {
		setLayout(null);
		this.setBackground(new Color(255, 245, 238));
		updateGroup();
		CL.grouplist=this;
	}
	
	public void showGroup(){   //在列表上显示所有的群
		
		Collection <Group> c=CL.groupMap.values();   
		List<Group> list =new ArrayList<Group>(c);// 转换成list  方便 之后扩展群组排序
		this.removeAll();
		int i=0;
		for(Group g : list){
			g.setBounds(10, 10+i++*55, 453, 69);
			this.add(g);
		}
		this.setPreferredSize(new Dimension(0,50*list.size()));
		this.updateUI();
	}
	
	public void getGroup(){  //得到所有群
		JSONArray jsonlist=JSONArray.fromObject(CL.json_list_myGroupinfo);
		for(int i=0;i<jsonlist.size();i++){
			JSONObject json=(JSONObject) jsonlist.get(i);
			String head=json.getString("head");  //群头像
			String name=json.getString("name");  //群名
			String pid=json.getString("pid");    //群编号
			String pguser=json.getString("pguser"); //群主
			Group group=new Group(pid,head,name,null,pguser);
			CL.groupMap.put(pid, group);  //添加群列表
		}
	}
	
	public void updateGroup(){  //实时更新所有群信息
		if(CL.groupMap.size()==0){
			getGroup();
		}else{
			JSONArray jsonlist=JSONArray.fromObject(CL.json_list_myGroupinfo);
			for(int i=0;i<jsonlist.size();i++){
				JSONObject json=(JSONObject) jsonlist.get(i);
				String pid=json.getString("pid");
				String head=json.getString("head");
				String name=json.getString("name");
				String pguser=json.getString("pguser");
				Group g=CL.groupMap.get(pid);
				if(g!=null){
					g.setHead(head);
					g.setName(name);
				}else{
					Group group =new Group(pid,head,name,null,pguser);
					System.out.println("新添加群"+pid);
					CL.groupMap.put(pid, group);  //添加群列表
				}
			}
		}
		showGroup();
	}
	
	public void updateGroupList(){
		
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
					String puid=(String) jas.get(j);  //得到相应群下的用户编号
					if(json2.getString("uid").equals(puid)){  //  拿到群下用户的个人信息
						String netname=json2.getString("netname");
						String head=json2.getString("head");
						String sign=json2.getString("sign");
						String uid=json2.getString("uid");
						GroupUser groupuser=new GroupUser(netname, head, sign, uid); //创建群下的用户
						groupuserlist.add(groupuser);
						String [] str=CL.friend_list.split(",");
						for(String fuid: str){
							if(!fuid.equals(uid)){
								CL.groupuserMap.put(uid, groupuser);
							}
						}
						CL.group_user_map.put(pid, groupuserlist); // 存储群下的用户 《群编号，  用户对象组》 
					} 
					
				}
			}
		}
	}
}
