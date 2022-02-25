package com.zy.qq.View;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.zy.qq.client.GroupUserList;
import com.zy.qq.clientModel.Mess;
import com.zy.qq.uitility.CL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GroupChatView extends JFrame  {

	private JPanel contentPane;
	private String pid;
	private String head;
	private String name;
	
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

	public ArrayList<Mess> getList() {
		return list;
	}

	public void setList(ArrayList<Mess> list) {
		this.list = list;
	}

	private JButton btnSend;
	private JTextArea txtRe;
	private JTextArea txtSend;
	private ArrayList<Mess> list;

	/**
	 * Create the frame.
	 */
	public GroupChatView(final String pid,String head,String name,ArrayList<Mess> list) {
		
		this.setPid(pid);
		this.setName(name);
		this.setHead(head);
		this.setList(list);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 831, 538);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(5, 20, 487, 442);
		splitPane.setDividerSize(15);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		txtRe = new JTextArea();
		txtRe.setEditable(false);
		scrollPane.setViewportView(txtRe);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		txtSend = new JTextArea();
		scrollPane_1.setViewportView(txtSend);
		
		JPanel panel_2 = new JPanel();
		scrollPane_1.setColumnHeaderView(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton_1 = new JButton("\u8868\u60C5");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel_2.add(btnNewButton_1, BorderLayout.WEST);
		splitPane.setDividerLocation(250);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 487, 15);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel(new ImageIcon("上线/"+head+".png"));
		panel.add(lblNewLabel_1, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel(name);
		panel_3.add(lblNewLabel, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 462, 487, 23);
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		btnSend = new JButton("发   送");
		btnSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(pid);
				String msg=txtSend.getText(); 
				
				Mess mess=new Mess();
				mess.setMsg(msg);
				mess.setMyuid(JSONObject.fromObject(CL.My_json_info).getString("uid"));
				mess.setTouid(pid);    //向编号为pid 的群发送
				mess.setType("chats");
				mess.setCode((System.currentTimeMillis()+"").trim());
				//{"myuid":"","toid":"","msg":"","type":"chats"}
//				System.out.println(mess.toString());
				JSONObject json=JSONObject.fromObject(mess);
				byte[]bf=json.toString().getBytes();
				int len=bf.length;
				System.out.println("发送"+mess.toString());
				try {
					DatagramPacket pack=new DatagramPacket(bf, len,InetAddress.getByName(CL.ip),CL.chat_port);
					CL.datasocket.send(pack);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				txtSend.setText("");			
			}
		});
		panel_1.add(btnSend, BorderLayout.EAST);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(492, 5, 326, 484);
		contentPane.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setViewportView(new GroupUserList(CL.group_user_map.get(pid), pid));
		panel_4.add(scrollPane_2, BorderLayout.CENTER);
		for(Mess mess: list){  
			String myuid=mess.getMyuid();
			JSONArray a=JSONArray.fromObject(CL.json_All_userinfo);
			String nname=null;
			for(int i=0;i<a.size();i++){
				JSONObject json=(JSONObject) a.get(i);
				if(myuid.equals(json.getString("uid"))){
					nname=json.getString("netname");
				}
			}
			addTomess(mess.getMsg(),nname);
		}
		this.setVisible(true);
//		this.addWindowListener(this);
		list.clear();
//		System.out.println("这个群是"+pid);
	}

	public void addMymess(Mess mess){
		String myname=JSONObject.fromObject(CL.My_json_info).getString("netname");
		String msg=mess.getMsg();
		txtRe.append(myname+"\t"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\n"+msg+"\n");	
	}
	
	public void addTomess(String msg,String name){
		txtRe.append(name+"\t"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\n"+msg+"\n");
	}
	
	public void addMess(ArrayList<Mess> list){
		for(Mess mess: list){
			String myuid=mess.getMyuid();
			JSONArray a=JSONArray.fromObject(CL.json_All_userinfo);
			String name=null;
			for(int i=0;i<a.size();i++){
				JSONObject json=(JSONObject) a.get(i);
				if(myuid.equals(json.getString("uid"))){
					name=json.getString("netname");
				}
			}
			addTomess(mess.getMsg(),name);
		}
	}
}
