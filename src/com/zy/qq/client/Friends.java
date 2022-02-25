package com.zy.qq.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import net.sf.json.JSONObject;

import org.w3c.dom.events.MouseEvent;

import com.zy.qq.View.ChatView;
import com.zy.qq.clientModel.Mess;
import com.zy.qq.uitility.CL;

/**
 * 封装好友信息
 * 方便图形界面显示
 * @author 清风理辛
 *
 */
public class Friends extends JPanel implements Comparable<Friends> ,Runnable,MouseListener {


	private JLabel lblHead;
	private JLabel lblName;
	private JLabel lblSign;

	private String head;
	private String netname;
	private String sign;
	private boolean online;
	private String uid;
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private Vector<Mess> Messlist=new Vector<Mess>();

	
	public Friends(final String head,final String netname,final String sign,final String uid){
		this.head=head;
		this.netname=netname;
		this.sign=sign;
		this.uid=uid;
		setBackground(new Color(255, 245, 238));
		this.setLayout(null);
		lblHead=new JLabel(new ImageIcon("上线/" + head + ".png"));
		lblHead.setBounds(10, 10, 54, 49);
		add(lblHead);
		
		lblName=new JLabel(netname);
		lblName.setBounds(74, 10, 354, 21);
		add(lblName);
		
		lblSign=new JLabel(sign);
		lblSign.setBounds(74, 41, 342, 18);
		add(lblSign);		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if(e.getClickCount()==2){
					run = false;// 终止线程
//						if(online){ //如果在线可以聊天
//							if(CL.Chatmap.get(uid)==null){
//								ChatView chatview=new ChatView(uid, netname, head, sign,Messlist);
//								CL.Chatmap.put(uid, chatview); //进行聊天框登记
//							}else{
//								CL.Chatmap.get(uid).setAlwaysOnTop(true);
//								CL.Chatmap.get(uid).setVisible(true);
//								CL.Chatmap.get(uid).addMess(Messlist);
//							}
//						}
					if(CL.Chatmap.get(uid)==null){
						ChatView chatview=new ChatView(uid, netname, head, sign,Messlist);
						CL.Chatmap.put(uid, chatview); //进行聊天框登记
					}else{
						CL.Chatmap.get(uid).setAlwaysOnTop(true);
						CL.Chatmap.get(uid).setVisible(true);
						CL.Chatmap.get(uid).addMess(Messlist);
					}
						Messlist.clear();
				}else if(e.getButton()==java.awt.event.MouseEvent.BUTTON3){
					
					JMenuItem itemAdd=new JMenuItem("删除好友");
					JPopupMenu menu=new JPopupMenu();
					menu.add(itemAdd);
					
					menu.show(e.getComponent(),e.getX(),e.getY());
					itemAdd.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							String myuid=JSONObject.fromObject(CL.My_json_info).getString("uid");
							// {"type":"delete","myuid":"","touid":""}
							
							String json="{\"touid\":\""+uid+"\",\"myuid\":\""+myuid+"\",\"type\":\"delete\"}";
							byte[] bf=json.toString().getBytes();
							int len=bf.length;
							try {
								DatagramPacket pack = new DatagramPacket(bf,len,InetAddress.getByName(CL.ip),CL.chat_port);
								CL.datasocket.send(pack);
								CL.map.remove(uid);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							
						}
					});
					
				}
			}
		});
		lblName.setFont(new Font("宋体",Font.PLAIN, 12));
		lblSign.setFont(new Font("宋体",Font.PLAIN, 10));		
		this.addMouseListener(this);
	}
	
	public Vector<Mess> getList(){
		return Messlist;
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
		lblName.setText(netname);
		this.netname = netname;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		lblSign.setText(sign);
		this.sign = sign;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public int compareTo(Friends f) {
		if(f.online){
			return 1;
		}else if(this.online){
			return -1;
		}else{
			return 0;
		}
	}
	
	public void wantChat(){  //聊天提示
		lblName.setFont(new Font("隶书",Font.BOLD,25));
	}
	
	public void NoChat(){
		lblName.setFont(new Font("隶书",Font.BOLD,10));
	}
	
	private boolean is =true;
	private Thread thread=null;
	
	public void addMess(Mess mess){
		Messlist.add(mess);
		
		if (thread == null) {			
			thread = new Thread(this);
			thread.start();
		} else if (thread.getState() == Thread.State.TERMINATED) {
			thread = new Thread(this);
			thread.start();
		} else if (run == false) {
			thread = new Thread(this);
			thread.start();
		}
	}
private boolean run=true;
	@Override
	public void run() {
		run=true;
		int x = this.getX();
		int y = this.getY();

		while (run) {  //头像跳动

			this.setLocation(x - 1, y - 1);
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {

			}
			this.setLocation(x + 2, y + 2);
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {

			}
			try {
				Player play=new Player(new FileInputStream( "Music/消息.mp3"));
				play.play();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
		}

		this.setLocation(x, y);
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent arg0) {
		
	}
	int xx = 0;
	int yy = 0;
	@Override
	public void mouseEntered(java.awt.event.MouseEvent arg0) {
		xx = this.getX();
		yy = this.getY();
		this.setLocation(xx - 3, yy - 3);
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent arg0) {
		this.setLocation(xx, yy);
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent arg0) {
		
	}
}
