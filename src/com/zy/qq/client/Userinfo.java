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
			lblHead.setIcon(new ImageIcon("����/" + head + ".png")); //���ߵ�ͷ��
		}else{
			lblHead.setIcon(new ImageIcon("����/" + head + ".png"));  //�����ߵ�ͷ�� ��ɫ
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


	public void setOnline(boolean online) {  //�������� ����ͷ��
		if(online){
			lblHead.setIcon(new ImageIcon("����/" + head + ".png")); //���ߵ�ͷ��
		}else{
			lblHead.setIcon(new ImageIcon("����/" + head + ".png"));  //�����ߵ�ͷ�� ��ɫ
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
		
		lblHead=new JLabel(new ImageIcon("����/"+head+".png"));
		lblHead.setBounds(10, 10, 54, 53);
		add(lblHead);
		lblnetname=new JLabel(netname);
		lblnetname.setBounds(74, 10, 54, 15);
		add(lblnetname);
		lblsign=new JLabel(sign);
		lblsign.setBounds(74, 29, 54, 15);
		add(lblsign);
		btnAdd=new JButton("��Ӻ���");
		btnAdd.setBounds(74, 50, 93, 23);
		btnAdd.addActionListener(this);
		add(btnAdd);
		
		lblnetname.setFont(new Font("����",Font.PLAIN, 12));
		lblsign.setFont(new Font("����",Font.PLAIN, 10));
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnAdd){
				
				if(uid.equals(JSONObject.fromObject(CL.My_json_info).getString("uid"))){  //��������Լ�
					JOptionPane.showMessageDialog(this, "�����Լ�!");
					return ;
				}
				String fs[]=CL.friend_list.split(",");
				for(String touid: fs){
					if(uid.equals(touid)){
						JOptionPane.showMessageDialog(this, "�Ѿ��Ǻ���!");
						return ;
					}
				}
			   //����Է���������Է�����Ӳ���  ���ܹ���ʱ�����ޣ�û���������ӣ�
				int num=JOptionPane.showConfirmDialog(this, "�Ƿ����"+netname+"Ϊ����","ϵͳ��ʾ",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(num==JOptionPane.YES_OPTION){ //���ȷ����� �������������һ����Ӻ��ѵ���Ϣ
				//{"myuid":"","touid":"","type":"" }
				AddMess addmess=new AddMess();
				addmess.setMyuid(JSONObject.fromObject(CL.My_json_info).getString("uid")); //���Լ���id
				addmess.setTouid(uid);  //��ӷ���id
				addmess.setType("Add");  //�������
				JSONObject json=JSONObject.fromObject(addmess);
				byte[] bf=json.toString().getBytes();
				int len=bf.length;
				try {
					DatagramPacket datapack=new DatagramPacket(bf,len,InetAddress.getByName(CL.ip),CL.Add_port);
					System.out.println("����"+json);
					CL.datasocket.send(datapack);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		
//			else{
//				JOptionPane.showMessageDialog(this, "�Է�û�����޷����");
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
