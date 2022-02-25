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
 * �����б�
 * @author �������
 *
 */
public class FriendList extends JPanel {


	public FriendList() {
		setBackground(new Color(255, 245, 238));
		setLayout(null);
		
		updateFriend();
		CL.friendlist=this;
	}
	
	public void getOnline(){  //�������߸���
		
		String[] onlines=CL.friend_online.split(",");
//		System.out.println("��ǰ����"+CL.friend_online);
		Set<String> set=CL.map.keySet();
		for(String uid: set){
			CL.map.get(uid).setOnline(false);   //��ȫ����Ϊ����
		}
		if(!CL.friend_list.equals("û�к�������") || CL.friend_list.trim().equals("")){
				
			for(String uid:onlines){
				Friends fs=CL.map.get(uid);
				if(fs!=null){
					CL.map.get(uid).setOnline(true);   //�ٰ������б��б�ŵ�����Ϊ����
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
	
	public void getFriend(){  //�õ����к���
		JSONArray jsonlist=JSONArray.fromObject(CL.json_friend);
		for(int i=0;i<jsonlist.size();i++){
			JSONObject json=(JSONObject) jsonlist.get(i);
			String head=json.getString("head");
			String netname=json.getString("netname");
			String sign=json.getString("sign");
			String uid=json.getString("uid");
			Friends fs=new Friends(head, netname, sign,uid);
			CL.map.put(uid, fs);  //��Ӻ����б�
		}
	}
	
	public void updateFriend(){  //ʵʱ�������к�����Ϣ
//		System.out.println("���º���");
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
					CL.map.put(uid, f);  //��Ӻ����б�
				}
			}
		}
		getOnline();
	}
}
