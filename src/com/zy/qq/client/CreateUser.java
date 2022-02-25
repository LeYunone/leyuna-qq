package com.zy.qq.client;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sf.json.JSONObject;

import com.zy.qq.uitility.CL;
/**
 * 创建群聊时的用户对象
 * @author 清风理辛
 *
 */
public class CreateUser extends JPanel implements MouseListener {

	private String email;
	private String name;
	private String uid;
	
	private JLabel lblInfo;
	
	public CreateUser(String email,String name,String uid) {
		
		this.setEmail(email);
		this.name=name;
		this.uid=uid;
		
		lblInfo=new JLabel(name+"   ("+email+")");
		lblInfo.setBounds(156, 5, 138, 15);
		add(lblInfo);
		this.addMouseListener(this);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1){
			CL.Create_Group_User_map.remove(uid);
			CL.Create_Group_User_map2.put(uid, this);
			CL.createu2.showUser();
			CL.createu.showUser();
		}
		if(e.getButton()==MouseEvent.BUTTON3){ 
			String myuid=JSONObject.fromObject(CL.My_json_info).getString("uid");
			if(uid.equals(myuid)){  //如果点的是我自己
				return;
			}
			CL.Create_Group_User_map.put(uid, this);
			CL.Create_Group_User_map2.remove(uid);
			CL.createu2.showUser();
			CL.createu.showUser();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		lblInfo.setForeground(Color.RED);
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		lblInfo.setForeground(Color.black);		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
	

}
