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
 * ������
 * @author �������
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
			public void windowClosing(WindowEvent arg0) {  //�ص�����ʱ����д�������
				
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
		tabbedPane.addTab("������", null, panel_4, null);
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
		btnNewButton_1.setIcon(new ImageIcon("�ز�/����.png"));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addfriend.setVisible(true);
			}
		});
		btnNewButton_1.setFocusPainted(false);
		
		JButton btnGroup = new JButton("");
		btnGroup.setBounds(36, 569, 33, 35);
		contentPane.add(btnGroup);
		btnGroup.setIcon(new ImageIcon("�ز�/Ⱥ��.png"));
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
	
	public void updataMyinfo(){  //���¸�����Ϣ ��������Ҫʵʱ���� ��ֻ��Ҫ�������������ҵ��
		
		String str=CL.My_json_info;
		JSONObject json=JSONObject.fromObject(str);
		String head=json.getString("head");
		String netname=json.getString("netname");
		String sign=json.getString("sign");
		lblMyhead.setIcon(new ImageIcon("����/"+head+".png"));
		lblMynetname.setText(netname);
		lblMysign.setText(sign);
		this.repaint();
		
	}
	
	public static int showAddMsg(String name){  //���� ������Ӻ��ѵ���ʾ
		return JOptionPane.showConfirmDialog(null, name+"������Ӻ���","ϵͳ��ʾ",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
	}
	
	public static void showAddOKMsg(String name){  //���� ������Ӻ��ѳɹ�����ʾ
		JOptionPane.showMessageDialog(null, name+"��ӳɹ�");
		//ˢ�º����б�
	}
	
	public static void showAddONMsg(String name){  //���� ������Ӻ��ѳɹ�����ʾ
		JOptionPane.showMessageDialog(null, name+"�ܾ�����ĺ�������");
	}
	
	public static void getGroupUser(){  //�������ʱ�õ�Ⱥ�µ��û���
		System.out.println("��������и���");
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
	
	public void updateUser(String head,String netname,String sign){
		lblMyhead.setIcon(new ImageIcon("����/"+head+".png"));
		lblMynetname.setText(netname);
		lblMysign.setText(sign);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		String my=CL.My_json_info;
		JSONObject json=JSONObject.fromObject(my);
		MyUserinfo myuserinfo=new MyUserinfo();  //�ͻ��˵��û�����
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
