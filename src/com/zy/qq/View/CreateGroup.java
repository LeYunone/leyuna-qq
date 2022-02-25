package com.zy.qq.View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.swing.JFormattedTextField;

import net.sf.json.JSONObject;

import com.zy.qq.client.CreateUser;
import com.zy.qq.client.CreateUserList;
import com.zy.qq.client.CreateUserList2;
import com.zy.qq.uitility.CL;
/**
 * 创建讨论组
 * @author 清风理辛
 *
 */
public class CreateGroup extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JButton btnOK;
	private JTextField txtName;


	/**
	 * Create the dialog.
	 */
	public CreateGroup() {

		setBounds(100, 100, 487, 408);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				System.out.println(arg0.getKeyChar());
			}
		});
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 219, 332);
		contentPanel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) { //回车10  	退格8
				CL.createu.showUesrOne(textField.getText());
				if(e.getKeyCode()==8){
					if(textField.getText().trim().equals("")){
						CL.createu.getFriend();
					}
				}
			}
		});
		scrollPane.setColumnHeaderView(textField);
		textField.setColumns(10);
		scrollPane.setViewportView(new CreateUserList());
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(239, 10, 230, 332);
		contentPanel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportView(new CreateUserList2());
		panel_1.add(scrollPane_1, BorderLayout.CENTER);
		
		btnOK = new JButton("\u521B\u5EFA");
		btnOK.setBounds(376, 346, 93, 23);
		btnOK.addActionListener(this);
		contentPanel.add(btnOK);
		
		txtName = new JTextField();
		txtName.setBounds(300, 346, 66, 21);
		contentPanel.add(txtName);
		txtName.setColumns(10);
	}


	public void way(){
		Collection <CreateUser> c=CL.Create_Group_User_map2.values();
		List<CreateUser> list =new ArrayList<CreateUser>(c);  //得到所有对象组
		StringBuffer sb=new StringBuffer(); //创建组员表
		for(CreateUser cu:list){
			String uid=cu.getUid();
			sb.append(uid); //添加组员  只有组员编号
			sb.append(",");
		}
		
		String msg=sb.toString();
		String pgname=txtName.getText().trim();
		if(pgname.trim().equals("")){
			pgname=msg;
		}
		String i="{\"type\":\"group\",\"uids\":\""+msg+"\",\"pgname\":\""+pgname+"\"}";
		JSONObject json=JSONObject.fromObject(i);
		byte[]bf=json.toString().getBytes();
		int len=bf.length;
		try {
			DatagramPacket pack=new DatagramPacket(bf, len,InetAddress.getByName(CL.ip),CL.Add_port);
			CL.datasocket.send(pack);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.dispose();
		CL.Create_Group_User_map.clear();
		CL.Create_Group_User_map2.clear();
		CL.createu.getFriend();
		CL.createu2.showUser();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnOK){
			Collection <CreateUser> c=CL.Create_Group_User_map2.values();
			List<CreateUser> list =new ArrayList<CreateUser>(c);  //得到所有对象组
			StringBuffer sb=new StringBuffer(); //创建组员表
			for(CreateUser cu:list){
				String uid=cu.getUid();
				sb.append(uid); //添加组员  只有组员编号
				sb.append(",");
			}
			
			String msg=sb.toString();
			String pgname=txtName.getText().trim();
			if(pgname.trim().equals("")){
				pgname=msg;
			}
			String pguser=JSONObject.fromObject(CL.My_json_info).getString("uid");
			String i="{\"type\":\"group\",\"uids\":\""+msg+"\",\"pgname\":\""+pgname+"\",\"pguser\":\""+pguser+"\"}";
			System.out.println("创建群发送"+i);
			JSONObject json=JSONObject.fromObject(i);
			byte[]bf=json.toString().getBytes();
			int len=bf.length;
			try {
				DatagramPacket pack=new DatagramPacket(bf, len,InetAddress.getByName(CL.ip),CL.Add_port);
				CL.datasocket.send(pack);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			this.dispose();
			CL.Create_Group_User_map.clear();
			CL.Create_Group_User_map2.clear();
			CL.createu.getFriend();
			CL.createu2.showUser();
		}
	}
}
