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
 * Ⱥ���б�
 * @author �������
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
	
	public void showGroup(){   //���б�����ʾ���е�Ⱥ
		
		Collection <Group> c=CL.groupMap.values();   
		List<Group> list =new ArrayList<Group>(c);// ת����list  ���� ֮����չȺ������
		this.removeAll();
		int i=0;
		for(Group g : list){
			g.setBounds(10, 10+i++*55, 453, 69);
			this.add(g);
		}
		this.setPreferredSize(new Dimension(0,50*list.size()));
		this.updateUI();
	}
	
	public void getGroup(){  //�õ�����Ⱥ
		JSONArray jsonlist=JSONArray.fromObject(CL.json_list_myGroupinfo);
		for(int i=0;i<jsonlist.size();i++){
			JSONObject json=(JSONObject) jsonlist.get(i);
			String head=json.getString("head");  //Ⱥͷ��
			String name=json.getString("name");  //Ⱥ��
			String pid=json.getString("pid");    //Ⱥ���
			String pguser=json.getString("pguser"); //Ⱥ��
			Group group=new Group(pid,head,name,null,pguser);
			CL.groupMap.put(pid, group);  //���Ⱥ�б�
		}
	}
	
	public void updateGroup(){  //ʵʱ��������Ⱥ��Ϣ
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
					System.out.println("�����Ⱥ"+pid);
					CL.groupMap.put(pid, group);  //���Ⱥ�б�
				}
			}
		}
		showGroup();
	}
	
	public void updateGroupList(){
		
		JSONObject json=JSONObject.fromObject(CL.Map_User_Group);  //Ⱥ���û���Ź�ϵ
		String myGroup=CL.stringbuffer_list_myGroup; //�ҵ�Ⱥ���
		String[] myGroups=myGroup.toString().split(",");
		for(String pid:myGroups){    //����ÿ��Ⱥ
			ArrayList<GroupUser> groupuserlist=new ArrayList<GroupUser>();  //�洢Ⱥ���û�
			String puids=json.getString(pid);  //�õ�Ⱥ�û�����
			JSONArray ja=JSONArray.fromObject(CL.json_All_userinfo);  //�����û��ĸ�����Ϣ 
			JSONArray jas=JSONArray.fromObject(puids);  //����Ⱥ�µ��û��������
			for(int i=0;i<ja.size();i++){
				JSONObject json2=(JSONObject) ja.get(i);
				for(int j=0;j<jas.size();j++){
					String puid=(String) jas.get(j);  //�õ���ӦȺ�µ��û����
					if(json2.getString("uid").equals(puid)){  //  �õ�Ⱥ���û��ĸ�����Ϣ
						String netname=json2.getString("netname");
						String head=json2.getString("head");
						String sign=json2.getString("sign");
						String uid=json2.getString("uid");
						GroupUser groupuser=new GroupUser(netname, head, sign, uid); //����Ⱥ�µ��û�
						groupuserlist.add(groupuser);
						String [] str=CL.friend_list.split(",");
						for(String fuid: str){
							if(!fuid.equals(uid)){
								CL.groupuserMap.put(uid, groupuser);
							}
						}
						CL.group_user_map.put(pid, groupuserlist); // �洢Ⱥ�µ��û� ��Ⱥ��ţ�  �û������顷 
					} 
					
				}
			}
		}
	}
}
