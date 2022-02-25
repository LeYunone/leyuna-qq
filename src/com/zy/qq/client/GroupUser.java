package com.zy.qq.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import net.sf.json.JSONObject;

import com.zy.qq.View.ChatView;
import com.zy.qq.clientModel.AddMess;
import com.zy.qq.clientModel.Mess;
import com.zy.qq.uitility.CL;
/**
 * 群用户对象
 * @author 清风理辛
 *
 */
public class GroupUser extends JPanel implements Comparable<GroupUser> {

	private String netname;
	private String head;
	private String sign;
	private String uid;
	private boolean online;
	
	private JLabel lblName;
	private JLabel lblHead;
	private JLabel lblSign;
	
	public GroupUser(final String netname,final String head,final String sign,final String uid) {
		this.setBackground(new Color(255, 245, 238));
		this.head=head;
		this.netname=netname;
		this.sign=sign;
		this.uid=uid;
		this.setLayout(null);
		lblHead=new JLabel(new ImageIcon("上线/" + head + ".png"));
		lblHead.setBounds(10, 10, 54, 39);
		add(lblHead);
		
		lblName=new JLabel(netname);
		lblName.setBounds(74, 10, 178, 15);
		add(lblName);
		
		lblSign=new JLabel(sign);
		lblSign.setBounds(74, 34, 178, 15);
		add(lblSign);		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if(e.getClickCount()==2){
//						if(online){ //如果在线可以聊天
							if(CL.Chatmap.get(uid)==null){
								ChatView chatview=new ChatView(uid, netname, head,sign,Messlist);
								CL.Chatmap.put(uid, chatview); //进行聊天框登记  如果是陌生人也登记
							}else{
								CL.Chatmap.get(uid).setAlwaysOnTop(true);
								CL.Chatmap.get(uid).setVisible(true);
								String [] str=CL.friend_list.split(",");
								for(String fuid: str){
									if(fuid.equals(uid)){
										CL.Chatmap.get(uid).addMess(CL.map.get(uid).getList());
										return;
									}
								}
								CL.Chatmap.get(uid).addMess(Messlist);
							}
						
						Messlist.clear();
				}
				if(e.getButton()==MouseEvent.BUTTON3){
					JMenuItem itemAdd=new JMenuItem("添加好友");
					JPopupMenu menu=new JPopupMenu();
					menu.add(itemAdd);
					
					menu.show(e.getComponent(),e.getX(),e.getY());
					itemAdd.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							if(uid.equals(JSONObject.fromObject(CL.My_json_info).getString("uid"))){  //如果是我自己
								JOptionPane.showMessageDialog(null, "是你自己!");
								return ;
							}
							int num=JOptionPane.showConfirmDialog(null, "是否添加"+netname+"为好友","系统提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
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
						}
					});
					
				}
			}
		});	
		lblName.setFont(new Font("宋体",Font.PLAIN, 12));
		lblSign.setFont(new Font("宋体",Font.PLAIN, 10));		
		
	}
	
	private Vector<Mess> Messlist =new Vector<Mess>();
	
	public void addMess(Mess mess){
		Messlist.add(mess);
	}

	public String getNetname() {
		return netname;
	}

	public void setNetname(String netname) {
		lblName.setText(netname);
		this.netname = netname;
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		lblSign.setText(sign);
		this.sign = sign;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public boolean getOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		if(online){
			lblHead.setIcon(new ImageIcon("上线/" + head + ".png")); //在线的头像
		}else{
			lblHead.setIcon(new ImageIcon("下线/" + head + ".png"));  //不在线的头像 黑色
		}
		this.online = online;
	}
	
	@Override
	public int compareTo(GroupUser f) {
		if(f.online){
			return 1;
		}else if(this.online){
			return -1;
		}else{
			return 0;
		}
	}


	
}
