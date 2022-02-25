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
 * Ⱥ�û��б�
 * @author �������
 *
 */
public class GroupUserList extends JPanel {
 
	private ArrayList<GroupUser> userlist=null;  //��ÿ��Ⱥһ�����ǵ��û���
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
	
	public void getOnline(ArrayList<GroupUser> l){  //Ⱥ�µ��û����߸���
		String onlines=CL.Map_User_Group_Online;  //Ⱥ�µ������û���� 
		System.out.println("���и���"+onlines);
		JSONObject json=JSONObject.fromObject(onlines);
		String onlineuid=json.getString(pid); //�õ���Ⱥ�������û���
		JSONArray ja=JSONArray.fromObject(onlineuid);
		for(GroupUser gu:userlist){
			gu.setOnline(false);  //������Ϊ����
			for(int i=0;i<ja.size();i++){
				if(gu.getUid().equals(ja.get(i))){  //��������û������û�ƥ��
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

	public void updateGroup(){  //ʵʱ��������Ⱥ�û���Ϣ
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
					String puid=ja.getString(j);  //�õ���ӦȺ�µ��û����
					if(json2.getString("uid").equals(puid)){  //  �õ�Ⱥ���û��ĸ�����Ϣ
						String netname=json2.getString("netname");
						String head=json2.getString("head");
						String sign=json2.getString("sign");
						String uid=json2.getString("uid");
						GroupUser groupuser=new GroupUser(netname, head, sign, uid); //����Ⱥ�µ��û�
						groupuserlist.add(groupuser);
						CL.groupuserMap.put(uid, groupuser);
						CL.group_user_map.put(pid, groupuserlist); // ����ֱ�ӽ��串��
					} 
				}
			}
		}
		getOnline(CL.group_user_map.get(pid)); //���µ�������
		
	}
}
