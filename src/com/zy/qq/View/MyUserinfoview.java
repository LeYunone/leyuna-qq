package com.zy.qq.View;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

import com.zy.qq.client.MyUserinfo;
import com.zy.qq.uitility.CL;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

import net.sf.json.JSONObject;
/**
 * 个人资料信息版   通过CL中的My_json_info  与服务器进行交互保存 
 * @author 清风理辛
 *
 */
public class MyUserinfoview extends JFrame implements ActionListener,MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblEdit;
	private JLabel lblEmail;
	private JLabel lblSex;
	private JTextField txtNetname;
	private JTextField txtSchool;
	private JTextField txtIphone;
	private JTextField txtEmail;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	private MyUserinfo myuser;
	private JTextField txtName;
	private JButton btnSave;
	private JTextArea txtSign;
	private JComboBox comSex;
	private JComboBox comBlood;
	private JLabel lblHead;
	private JLabel lblImgHead;
	
	private Home home;
	public MyUserinfoview(MyUserinfo myuser,Home home) {
		this.myuser=myuser;
		this.home=home;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 604, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(8, 10, 570, 166);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblImgHead = new JLabel(new ImageIcon("上线/"+myuser.getHead()+".png"));
		lblImgHead.addMouseListener(this);
		lblImgHead.setText("");
		lblImgHead.setBounds(20, 10, 111, 91);
		panel.add(lblImgHead);
		
		JLabel lblNetname = new JLabel(myuser.getNetname());
		lblNetname.setBounds(20, 116, 111, 15);
		panel.add(lblNetname);
		
		JLabel lblImg = new JLabel();
		lblImg.setBounds(211, 29, 37, 52);
		panel.add(lblImg);
		lblImg.setIcon(new ImageIcon("img/userinfo.png"));
		
		lblEmail = new JLabel(myuser.getEmail());
		lblEmail.setBounds(258, 29, 197, 26);
		panel.add(lblEmail);
		
		lblSex = new JLabel(myuser.getSex());
		lblSex.setBounds(258, 66, 54, 15);
		panel.add(lblSex);
		
		String year=myuser.getYear();
		int y=Integer.valueOf(year);  //用户的出生年
		Calendar date = Calendar.getInstance();
		String year2 = String.valueOf(date.get(Calendar.YEAR));
		int y2=Integer.valueOf(year2);  //当前的系统年
		int age=y2-y;
		
		JLabel lblYear = new JLabel((age+"").trim());
		lblYear.setBounds(336, 66, 54, 15);
		panel.add(lblYear);
		
		JLabel lblBrith = new JLabel(myuser.getMonth()+"月"+myuser.getDay()+"日");
		lblBrith.setBounds(411, 66, 54, 15);
		panel.add(lblBrith);
		
		JLabel lblBlood = new JLabel(myuser.getBlood());
		lblBlood.setBounds(475, 66, 54, 15);
		panel.add(lblBlood);
		
		lblEdit = new JLabel("\u7F16\u8F91\u8D44\u6599");
		lblEdit.setBounds(465, 35, 54, 15);
		panel.add(lblEdit);
		lblEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {  //如果点击了编辑资料 则进行编辑界面   425 ->  550
				setBounds(100, 100, 604, 550);
				txtEmail.setEditable(true);
				txtIphone.setEditable(true);
				txtName.setEditable(true);
				txtNetname.setEditable(true);
				txtSchool.setEditable(true);
				txtSign.setEditable(true);
			}
		});
		lblEdit.setForeground(Color.BLUE);
		
		JLabel lblSign = new JLabel(myuser.getSign());
		lblSign.setBounds(221, 97, 285, 52);
		panel.add(lblSign);
		
		lblHead = new JLabel(myuser.getHead());
		lblHead.setBounds(66, 50, 54, 15);
		panel.add(lblHead);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(8, 186, 570, 315);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("\u57FA\u672C\u4FE1\u606F");
		label.setBounds(32, 21, 54, 15);
		panel_1.add(label);
		
		JLabel lblNewLabel = new JLabel("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		lblNewLabel.setBounds(32, 46, 512, 15);
		panel_1.add(lblNewLabel);
		
		JLabel label_1 = new JLabel("\u6635\u79F0");
		label_1.setBounds(32, 71, 54, 15);
		panel_1.add(label_1);
		
		txtNetname = new JTextField(myuser.getNetname());
		txtNetname.setEditable(false);
		txtNetname.setBounds(92, 68, 106, 21);
		panel_1.add(txtNetname);
		txtNetname.setColumns(10);
		
		JLabel label_2 = new JLabel("\u6027\u522B");
		label_2.setBounds(261, 71, 54, 15);
		panel_1.add(label_2);
		
		comSex = new JComboBox();
		if(myuser.getSex().equals("男")){
			comSex.setModel(new DefaultComboBoxModel(new String[] {"\u7537", "\u5973"}));
		}else{
			comSex.setModel(new DefaultComboBoxModel(new String[] {"女", "男"}));
		}
		comSex.setBounds(310, 71, 69, 21);
		panel_1.add(comSex);
		
		JLabel label_3 = new JLabel("\u8840\u578B");
		label_3.setBounds(415, 71, 54, 15);
		panel_1.add(label_3);
		
		comBlood = new JComboBox();
		String b=myuser.getBlood();  //进行血型筛选
		String[] bloods={"O","A","B","AB"};
		for(int i=0;i<bloods.length;i++){
			if(bloods[i].equals(b)){  //将血型组中的元素进行排序 调换 ，  用户血型下标为0
				String j=bloods[0];
				bloods[0]=bloods[i];
				bloods[i]=j;
				break;   //找到后立马停止
			}
		}
		comBlood.setModel(new DefaultComboBoxModel(bloods));  //添加新的血型数组
		comBlood.setBounds(454, 71, 69, 21); 
		panel_1.add(comBlood);
		
		JLabel label_4 = new JLabel("\u751F\u65E5");
		label_4.setBounds(32, 107, 54, 15);
		panel_1.add(label_4);
		
		JLabel lblQ = new JLabel("\u7B7E\u540D");
		lblQ.setBounds(32, 137, 54, 15);
		panel_1.add(lblQ);
		
		txtSign = new JTextArea(myuser.getSign());
		txtSign.setEditable(false);
		txtSign.setBounds(92, 137, 434, 50);
		panel_1.add(txtSign);
		
		JLabel lblNewLabel_1 = new JLabel("\u66F4\u591A\u8D44\u6599");
		lblNewLabel_1.setBounds(32, 205, 54, 15);
		panel_1.add(lblNewLabel_1);
		
		JLabel label_5 = new JLabel("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		label_5.setBounds(32, 224, 512, 15);
		panel_1.add(label_5);
		
		JLabel label_6 = new JLabel("\u5B66\u6821");
		label_6.setBounds(32, 240, 54, 15);
		panel_1.add(label_6);
		
		txtSchool = new JTextField(myuser.getSchool());
		txtSchool.setEditable(false);
		txtSchool.setBounds(92, 237, 106, 21);
		panel_1.add(txtSchool);
		txtSchool.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("\u624B\u673A");
		lblNewLabel_2.setBounds(228, 240, 54, 15);
		panel_1.add(lblNewLabel_2);
		
		txtIphone = new JTextField(myuser.getIphone());
		txtIphone.setEditable(false);
		txtIphone.setBounds(292, 237, 140, 21);
		panel_1.add(txtIphone);
		txtIphone.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("\u90AE\u7BB1");
		lblNewLabel_3.setBounds(32, 275, 54, 15);
		panel_1.add(lblNewLabel_3);
		
		txtEmail = new JTextField(myuser.getEmail());
		txtEmail.setEditable(false);
		txtEmail.setBounds(92, 272, 235, 21);
		panel_1.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel label_7 = new JLabel("\u59D3\u540D");
		label_7.setBounds(261, 107, 54, 15);
		panel_1.add(label_7);
		
		txtName = new JTextField(myuser.getName());
		txtName.setEditable(false);
		txtName.setBounds(310, 104, 66, 21);
		panel_1.add(txtName);
		txtName.setColumns(10);
		
		btnSave = new JButton("\u4FDD\u5B58");
		btnSave.addActionListener(this);
		btnSave.setBounds(467, 282, 93, 23);
		panel_1.add(btnSave);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==btnSave){
			MyUserinfo myuser=new MyUserinfo();
			String netname=txtNetname.getText();
			String email=txtEmail.getText();
			String iphone=txtIphone.getText();
			String sign=txtSign.getText();
			String name=txtName.getText();
			String  school=txtSchool.getText();
			String sex=(String) comSex.getSelectedItem();
			String blood=(String) comBlood.getSelectedItem();
			String head=lblHead.getText();
			myuser.setName(name);
			myuser.setNetname(netname);
			myuser.setEmail(email);
			myuser.setIphone(iphone);
			myuser.setSign(sign);
			myuser.setSchool(school);
			myuser.setSex(sex);
			myuser.setBlood(blood);
			myuser.setHead(head);
			myuser.setYear("2019");
			myuser.setMonth("12");
			myuser.setDay("1");
			myuser.setUid(JSONObject.fromObject(CL.My_json_info).getString("uid"));  //自己的id
			
			JSONObject json=JSONObject.fromObject(myuser); //将json格式的个人用户 发送给服务器进行修改保存  使用udp进行交互  
			byte[]bf=json.toString().getBytes();  //将 包传过去
			int len=bf.length;
			try {
				DatagramPacket pack=new DatagramPacket(bf, len,InetAddress.getByName(CL.ip),CL.chat_port);
				CL.datasocket.send(pack);
			}catch (Exception e) {
			}
			home.updateUser(head, netname, sign); //更新主界面信息
			setBounds(100, 100, 604, 425);
		}
	}
	
	public void setHead(String i){
		lblHead.setText(i); //将头像重新设置
		lblImgHead.setIcon(new ImageIcon("上线/"+i+".png")); //设置信息板图片
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {  //点击头像
		new HeadList(this);
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
