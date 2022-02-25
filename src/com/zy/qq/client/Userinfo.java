package com.zy.qq.client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.sf.json.JSONObject;


import com.zy.qq.clientModel.AddMess;
import com.zy.qq.uitility.CL;
import java.awt.Color;

public class Userinfo extends JPanel implements ActionListener,Comparable<Userinfo> {

	private static final long serialVersionUID = 1L;


	public String getUid() {
		return uid;
	}


	@Override
	public String toString() {
		return "Userinfo [uid=" + uid + ", head=" + head + ", netname="
				+ netname + ", sign=" + sign + ", online=" + online
				+ ", lblHead=" + lblHead + ", lblnetname=" + lblnetname
				+ ", lblsign=" + lblsign + ", btnAdd=" + btnAdd + "]";
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getHead() {
		return head;
	}


	public void setHead(String head) {
		if(online){
			lblHead.setIcon(new ImageIcon("上线/" + head + ".png")); //在线的头像
		}else{
			lblHead.setIcon(new ImageIcon("下线/" + head + ".png"));  //不在线的头像 黑色
		}
		this.head = head;
	}


	public String getNetname() {
		return netname;
	}


	public void setNetname(String netname) {
		this.netname = netname;
	}


	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}


	public boolean getOnline() {
		return online;
	}


	public void setOnline(boolean online) {  //设置在线 下线头像
		if(online){
			lblHead.setIcon(new ImageIcon("上线/" + head + ".png")); //在线的头像
		}else{
			lblHead.setIcon(new ImageIcon("下线/" + head + ".png"));  //不在线的头像 黑色
		}
		this.online = online;
	}


	private String uid;
	private String head;
	private String netname;
	private String sign;
	private boolean online;
	
	private JLabel lblHead;
	private JLabel lblnetname;
	private JLabel lblsign;
	private JButton btnAdd;
	
	
	public Userinfo(String uid,String head,String netname,String sign) {
		this.setBackground(new Color(255, 250, 240));
		this.uid=uid;
		this.head=head;
		this.netname=netname;
		this.sign=sign;
		setLayout(null);
		
		lblHead=new JLabel(new ImageIcon("上线/"+head+".png"));
		lblHead.setBounds(10, 10, 54, 53);
		add(lblHead);
		lblnetname=new JLabel(netname);
		lblnetname.setBounds(74, 10, 54, 15);
		add(lblnetname);
		lblsign=new JLabel(sign);
		lblsign.setBounds(74, 29, 54, 15);
		add(lblsign);
		btnAdd=new JButton("添加好友");
		btnAdd.setBounds(74, 50, 93, 23);
		btnAdd.addActionListener(this);
		add(btnAdd);
		
		lblnetname.setFont(new Font("宋体",Font.PLAIN, 12));
		lblsign.setFont(new Font("宋体",Font.PLAIN, 10));
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnAdd){
				
				if(uid.equals(JSONObject.fromObject(CL.My_json_info).getString("uid"))){  //如果是我自己
					JOptionPane.showMessageDialog(this, "是你自己!");
					return ;
				}
				String fs[]=CL.friend_list.split(",");
				for(String touid: fs){
					if(uid.equals(touid)){
						JOptionPane.showMessageDialog(this, "已经是好友!");
						return ;
					}
				}
			   //如果对方在线则可以发出添加操作  （架构，时间有限，没完成离线添加）
				int num=JOptionPane.showConfirmDialog(this, "是否添加"+netname+"为好友","系统提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(num==JOptionPane.YES_OPTION){ //如果确定添加 则向服务器发送一条添加好友的信息
				//{"myuid":"","touid":"","type":"" }
				AddMess addmess=new AddMess();
				addmess.setMyuid(JSONObject.fromObject(CL.My_json_info).getString("uid")); //我自己的id
				addmess.setTouid(uid);  //添加方的id
				addmess.setType("Add");  //添加主题
				JSONObject json=JSONObject.fromObject(addmess);
				byte[] bf=json.toString().getBytes();
				int len=bf.length;
				try {
					DatagramPacket datapack=new DatagramPacket(bf,len,InetAddress.getByName(CL.ip),CL.Add_port);
					System.out.println("发出"+json);
					CL.datasocket.send(datapack);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		
//			else{
//				JOptionPane.showMessageDialog(this, "对方没在线无法添加");
//			}
		}
	}


	@Override
	public int compareTo(Userinfo u) {
		if(u.online){
			return 1;
		}else if(this.online){
			return -1;
		}else{
			return 0;
		}
	
	}

}
