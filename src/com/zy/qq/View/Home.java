package com.zy.qq.View;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zy.qq.client.FriendList;
import com.zy.qq.client.GroupList;
import com.zy.qq.client.GroupUser;
import com.zy.qq.client.MyUserinfo;
import com.zy.qq.uitility.CL;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 主界面
 * @author 清风理辛
 *
 */
public class Home extends JFrame implements MouseListener {

	private JPanel contentPane;
	private JLabel lblMyhead;
	private JLabel lblMynetname;
	private JLabel lblMysign;

	/**
	 * Create the frame.
	 */
	private AddFriend addfriend;
	private FriendList friendList;
	public Home() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {  //关掉窗口时，读写所有面板
				
			}
		});
		addfriend=new AddFriend();
		friendList=new FriendList();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 244, 656);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
//		contentPane.setb
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(255, 250, 205));
		panel.setBounds(5, 5, 218, 40);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(10, 10));
		
		lblMyhead = new JLabel("");
		lblMyhead.addMouseListener(this);
		lblMyhead.setIcon(new ImageIcon("C:\\Users\\\u6E05\u98CE\u7406\u8F9B\\Desktop\\QQ\u622A\u56FE20191106124041.png"));
		panel.add(lblMyhead, BorderLayout.WEST);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(10, 10));
		
		lblMynetname = new JLabel("\u6C64\u9701\u5B87\u7684\u7238\u7238");
		panel_1.add(lblMynetname, BorderLayout.CENTER);
		
		lblMysign = new JLabel("107\u4E4B\u7236");
		panel_1.add(lblMysign, BorderLayout.SOUTH);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(5, 55, 218, 504);
		contentPane.add(tabbedPane);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("\u597D\u53CB", null, panel_2, null);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(friendList);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("讨论组", null, panel_4, null);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_4.add(scrollPane_1, BorderLayout.CENTER);
		scrollPane_1.setViewportView(new GroupList());
		updataMyinfo();
		getGroupUser();
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\\u6E05\u98CE\u7406\u8F9B\\Desktop\\\u76AE\u80A4.jpg"));
		lblNewLabel.setBounds(0, 0, 223, 619);
		contentPane.add(lblNewLabel);
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBounds(5, 569, 33, 35);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.setIcon(new ImageIcon("素材/好友.png"));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addfriend.setVisible(true);
			}
		});
		btnNewButton_1.setFocusPainted(false);
		
		JButton btnGroup = new JButton("");
		btnGroup.setBounds(36, 569, 33, 35);
		contentPane.add(btnGroup);
		btnGroup.setIcon(new ImageIcon("素材/群聊.png"));
		btnGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateGroup cg=new CreateGroup();
				cg.setVisible(true);
			}
		});
		btnGroup.setFocusPainted(false);
		contentPane.setComponentZOrder(btnGroup, 0);
		contentPane.setComponentZOrder(btnNewButton_1, 0);
		setLocationRelativeTo(null);
//		this.getContetPane().setBackground(Color.red);//
	}
	
	public void updataMyinfo(){  //更新个人信息 ，但不需要实时更新 ，只需要处理保存个人资料业务
		
		String str=CL.My_json_info;
		JSONObject json=JSONObject.fromObject(str);
		String head=json.getString("head");
		String netname=json.getString("netname");
		String sign=json.getString("sign");
		lblMyhead.setIcon(new ImageIcon("上线/"+head+".png"));
		lblMynetname.setText(netname);
		lblMysign.setText(sign);
		this.repaint();
		
	}
	
	public static int showAddMsg(String name){  //弹窗 弹出添加好友的提示
		return JOptionPane.showConfirmDialog(null, name+"请求添加好友","系统提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
	}
	
	public static void showAddOKMsg(String name){  //弹窗 弹出添加好友成功的提示
		JOptionPane.showMessageDialog(null, name+"添加成功");
		//刷新好友列表
	}
	
	public static void showAddONMsg(String name){  //弹窗 弹出添加好友成功的提示
		JOptionPane.showMessageDialog(null, name+"拒绝了你的好友请求");
	}
	
	public static void getGroupUser(){  //界面加载时得到群下的用户组
		System.out.println("主界面进行更新");
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
	
	public void updateUser(String head,String netname,String sign){
		lblMyhead.setIcon(new ImageIcon("上线/"+head+".png"));
		lblMynetname.setText(netname);
		lblMysign.setText(sign);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		String my=CL.My_json_info;
		JSONObject json=JSONObject.fromObject(my);
		MyUserinfo myuserinfo=new MyUserinfo();  //客户端的用户对象
		myuserinfo.setBlood(json.getString("blood"));
		myuserinfo.setDay(json.getString("day"));
		myuserinfo.setEmail(json.getString("email"));
		myuserinfo.setHead(json.getString("head"));
		myuserinfo.setIphone(json.getString("iphone"));
		myuserinfo.setMonth(json.getString("month"));
		myuserinfo.setName(json.getString("name"));
		myuserinfo.setNetname(json.getString("netname"));
		myuserinfo.setSchool(json.getString("school"));
		myuserinfo.setSex(json.getString("sex"));
		myuserinfo.setSign(json.getString("sign"));
		myuserinfo.setUid(json.getString("uid"));
		myuserinfo.setYear(json.getString("year"));
		new MyUserinfoview(myuserinfo,this);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
}
