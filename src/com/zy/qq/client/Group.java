package com.zy.qq.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import net.sf.json.JSONObject;

import com.zy.qq.View.GroupChatView;
import com.zy.qq.clientModel.Mess;
import com.zy.qq.uitility.CL;
/**
 * 群对象
 * @author 清风理辛
 *
 */
public class Group extends JPanel implements MouseListener,Runnable {


	private String pid;
	private String head;
	private String name;
	private Date date;
	private String pguser;
	
	private JLabel lblHead;
	private JLabel lblName;
	private JLabel lbltime;
	
	public Group(final String pid,final String head,final String name,Date date,final String pguser) {
		this.head=head;
		this.name=name;
		this.pid=pid;
		this.setPguser(pguser);
		this.setLayout(null);
		this.setBackground(new Color(255, 245, 238));
		lblHead=new JLabel(new ImageIcon("上线/" + head + ".png"));
		lblHead.setBounds(10, 10, 54, 49);
		add(lblHead);
		
		lblName=new JLabel(name);
		lblName.setBounds(74, 10, 354, 30);
		add(lblName);
		
		lbltime=new JLabel();
		lbltime.setBounds(74, 41, 342, 18);
		add(lbltime);		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if(e.getClickCount()==2){
						run=false;
						if(CL.chatsmap.get(pid)==null){
							GroupChatView gcv=new GroupChatView(pid,head,name,list);
							CL.chatsmap.put(pid, gcv);
						}else{
							CL.chatsmap.get(pid).setAlwaysOnTop(true);
							CL.chatsmap.get(pid).setVisible(true);
							CL.chatsmap.get(pid).addMess(list);
						}
						list.clear();
				}
				if(e.getButton()==MouseEvent.BUTTON3){
					JMenuItem itemDel=new JMenuItem("退出该群");
					JPopupMenu menu=new JPopupMenu();
					final String myuid=JSONObject.fromObject(CL.My_json_info).getString("uid"); //自己的id
					if(myuid.equals(pguser)){
						JMenuItem itemDrop=new JMenuItem("解散该群");
						menu.add(itemDrop);
						
						itemDrop.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								String json="{\"pid\":\""+pid+"\",\"type\":\"droupGroup\"}";
								byte[]b=json.toString().getBytes();
								int len=b.length;
								try {
									DatagramPacket pack=new DatagramPacket(b, 0,len,InetAddress.getByName(CL.ip),CL.chat_port);
									CL.datasocket.send(pack);  //发送解散群组的功能
									CL.groupMap.remove(pid);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						});
					}
					menu.add(itemDel);
					menu.show(e.getComponent(),e.getX(),e.getY());
					itemDel.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if(pid.equals("3")){
								JOptionPane.showMessageDialog(null,"? 你有资格退这个群?");
								return;
							}
							String json="{\"myuid\":\""+myuid+"\",\"pid\":\""+pid+"\",\"type\":\"deleteGroup\"}";
							byte[]b=json.toString().getBytes();
							int len=b.length;
							try {
								DatagramPacket pack=new DatagramPacket(b, 0,len,InetAddress.getByName(CL.ip),CL.chat_port);
								CL.datasocket.send(pack);  //发送退出群组的功能
								CL.groupMap.remove(pid);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
		this.addMouseListener(this);
		lblName.setFont(new Font("宋体",Font.PLAIN, 12));
	}
	
	private ArrayList<Mess> list=new ArrayList<Mess>();  //未开窗信息存储
	private Thread thread=null;
	public void addMess(Mess mess){
		list.add(mess);
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
	
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	private boolean run=true;
	@Override
	public void run() {
		run=true;
		int x = this.getX();
		int y = this.getY();

		while (run) {

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
		}

		this.setLocation(x, y);
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		
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
	public void mousePressed(MouseEvent arg0) {
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}


	public String getPguser() {
		return pguser;
	}


	public void setPguser(String pguser) {
		this.pguser = pguser;
	}


}
