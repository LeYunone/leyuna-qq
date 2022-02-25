package com.zy.qq.View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.sf.json.JSONObject;

import com.zy.qq.clientServer.ChatForServer;
import com.zy.qq.clientServer.ForServer;
import com.zy.qq.clientServer.RegChatServer;
import com.zy.qq.uitility.CL;

public class LoginView extends JFrame implements MouseListener{

	JLabel bacgrangd, jan, bi, QQ, qq, tu;// gif,最小化，关闭，logo，QQ，头像
	JLabel an1, an2, lie1, lie2;// 暗色块|线
	JTextField txtUserName;// 账号
	JPasswordField txtPass;// 密码
	JPanel bgcolor;// 白
	JLabel su1, mi1, ku1, ku2, gou1, gou2;// 缩略图
	JLabel text1, text2, text3, btnNew, text5;// 自动登录，记住密码，找回密码，注册账号，登录
	static Point origin = new Point();// 变量，用于可拖动窗体
	int a = 0, b = 0, c = 0, d = 0;// 控制线
	int f = 0, g = 0, h = 0, j = 0;// 控制√
	JLabel btnOK, ma;// 背景

	public LoginView() {

		// 实例化
		bacgrangd = new JLabel(new ImageIcon("素材//1.gif"));
		jan = new JLabel(new ImageIcon("素材//最小化.png"));
		bi = new JLabel(new ImageIcon("素材//关闭.png"));
		QQ = new JLabel(new ImageIcon("素材//qq.png"));
		qq = new JLabel("QQ");
		an1 = new JLabel();
		an2 = new JLabel();// 暗调
		tu = new JLabel(new ImageIcon("素材//头像.png"));
		txtUserName = new JTextField();
		txtPass = new JPasswordField();
		su1 = new JLabel(new ImageIcon("素材//qq (1).png"));
		mi1 = new JLabel(new ImageIcon("素材//密码.png"));
		lie1 = new JLabel(new ImageIcon("素材//直线2.png"));
		lie2 = new JLabel(new ImageIcon("素材//直线2.png"));
		bgcolor = new JPanel();
		ku1 = new JLabel(new ImageIcon("素材//框框.png"));
		ku2 = new JLabel(new ImageIcon("素材//框框.png"));
		gou1 = new JLabel(new ImageIcon("素材//对勾.png"));
		gou2 = new JLabel(new ImageIcon("素材//对勾.png"));
		text1 = new JLabel("自动登录");
		text2 = new JLabel("记住密码");
		text3 = new JLabel("找回密码");
		btnNew = new JLabel("注册账号");
		text5 = new JLabel("登录");
		btnOK = new JLabel();
		ma = new JLabel(new ImageIcon("素材//二维码.png"));

		// 位置
		bacgrangd.setBounds(-35, -123, 500, 250);
		jan.setBounds(364, 2, 32, 32);
		bi.setBounds(396, 3, 32, 32);
		QQ.setBounds(10, 10, 32, 32);
		qq.setBounds(50, 5, 45, 45);
		an1.setBounds(361, 0, 35, 35);
		an2.setBounds(395, 0, 35, 35);
		tu.setBounds(170, 80, 90, 85);
		txtUserName.setBounds(130, 160, 180, 40);
		txtPass.setBounds(130, 200, 180, 40);
		su1.setBounds(100, 170, 20, 20);
		mi1.setBounds(100, 210, 20, 20);
		lie1.setBounds(100, 190, 240, 10);
		lie2.setBounds(100, 230, 240, 10);
		bgcolor.setBounds(0, 125, 500, 300);
		ku1.setBounds(100, 250, 20, 20);
		ku2.setBounds(190, 250, 20, 20);
		gou1.setBounds(106, 255, 10, 10);
		gou2.setBounds(196, 255, 10, 10);
		text1.setBounds(125, 250, 80, 20);
		text2.setBounds(215, 250, 80, 20);
		text3.setBounds(288, 250, 80, 20);
		btnNew.setBounds(15, 300, 80, 20);
		text5.setBounds(206, 285, 80, 20);
		btnOK.setBounds(100, 280, 242, 35);
		ma.setBounds(385, 290, 30, 30);
		// 属性
		qq.setFont(new Font("微软雅黑", 1, 25));
		qq.setForeground(Color.white);
		an1.setBackground(new Color(0, 0, 0, 0.3f));
		an2.setBackground(new Color(0, 0, 0, 0.3f));
		bgcolor.setBackground(new Color(255, 255, 255));

		txtUserName.setForeground(Color.gray);
		txtUserName.setText("QQ号码/手机/邮箱");
		txtUserName.setOpaque(false);// 透明背景
		txtUserName.setBorder(null);// 去掉边框
		txtUserName.setFont(new Font("微软雅黑", Font.PLAIN, 16));// 框内文字样式
		txtPass.setFont(new Font("微软雅黑", Font.PLAIN, 16));// 框内文字样式
		txtPass.setBorder(null);// 去掉边框

		txtPass.setOpaque(false);// 透明背景
		txtPass.setForeground(Color.gray);
		txtPass.setText("密码");
		txtPass.setEchoChar((char) 0);// 让密码显示出来

		text1.setFont(new Font("微软雅黑", 0, 12));
		text2.setFont(new Font("微软雅黑", 0, 12));
		text3.setFont(new Font("微软雅黑", 0, 12));
		btnNew.setFont(new Font("微软雅黑", 0, 12));
		text5.setFont(new Font("微软雅黑", 0, 15));
		text1.setForeground(new Color(170, 170, 170));
		text2.setForeground(new Color(170, 170, 170));
		text3.setForeground(new Color(170, 170, 170));
		btnNew.setForeground(new Color(170, 170, 170));
		text5.setForeground(Color.white);

		gou1.setVisible(false);
		gou2.setVisible(false);

		btnOK.setBackground(new Color(5, 186, 251));
		btnOK.setOpaque(true);

		text3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNew.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// 事件区域
		jan.addMouseListener(this);
		bi.addMouseListener(this);
		txtUserName.addMouseListener(this);
		txtPass.addMouseListener(this);
		text1.addMouseListener(this);
		text2.addMouseListener(this);
		text3.addMouseListener(this);
		btnNew.addMouseListener(this);
		ku1.addMouseListener(this);
		ku2.addMouseListener(this);
		btnOK.addMouseListener(this);
		ma.addMouseListener(this);
		this.addMouseListener(this);

		this.addMouseMotionListener(new MouseMotionListener() {// 窗体拖动事件
			public void mouseMoved(MouseEvent e) {
			}

			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
			}
		});

		txtUserName.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {// 失去焦点
				su1.setIcon(new javax.swing.ImageIcon("素材//qq (1).png"));
				lie1.setIcon(new javax.swing.ImageIcon("素材//直线2.png"));
				c = 0;
				if (txtUserName.getText().isEmpty()) {// 判断是否为空（为了设置默认提示语）
					txtUserName.setForeground(Color.gray);
					txtUserName.setText("QQ号码/手机/邮箱");
				}
			}

			public void focusGained(FocusEvent e) {// 得到焦点
				txtUserName.setForeground(Color.black);
				lie1.setIcon(new javax.swing.ImageIcon("素材//直线3.png"));
				a = 1;
				c = 1;
				b = 0;
				su1.setIcon(new javax.swing.ImageIcon("素材//qq (2).png"));
				if (txtUserName.getText().equals("QQ号码/手机/邮箱")) {
					txtUserName.setText("");
				} else {
					txtUserName.setText(txtUserName.getText());
					txtUserName.selectAll();
				}
			}
		});

		txtPass.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {// 失去焦点
				lie2.setIcon(new javax.swing.ImageIcon("素材//直线2.png"));// 失去焦点换图片
				mi1.setIcon(new javax.swing.ImageIcon("素材//密码.png"));
				d = 0;
				if (txtPass.getText().isEmpty()) {
					txtPass.setForeground(Color.gray);
					txtPass.setText("密码");
					txtPass.setEchoChar((char) 0);// 让密码显示出来
				}
			}

			public void focusGained(FocusEvent e) {// 得到焦点
				mi1.setIcon(new javax.swing.ImageIcon("素材//密码 (1).png"));
				lie2.setIcon(new javax.swing.ImageIcon("素材//直线3.png"));
				b = 1;
				a = 0;
				d = 1;
				txtPass.setForeground(Color.black);
				txtPass.setEchoChar('*');// 让用户输入看不见
				if (txtPass.getText().equals("密码")) {
					txtPass.setText("");
				} else {
					txtPass.setText(txtPass.getText());
				}
			}
		});

		getContentPane().setLayout(null);// 布局

		getContentPane().add(jan);
		getContentPane().add(bi);
		getContentPane().add(qq);
		getContentPane().add(QQ);
		getContentPane().add(an1);
		getContentPane().add(an2);
		getContentPane().add(tu);
		getContentPane().add(lie1);
		getContentPane().add(lie2);
		getContentPane().add(txtUserName);
		getContentPane().add(txtPass);
		getContentPane().add(su1);
		getContentPane().add(mi1);
		getContentPane().add(gou1);
		getContentPane().add(gou2);
		getContentPane().add(ku1);
		getContentPane().add(ku2);
		getContentPane().add(text1);
		getContentPane().add(text2);
		getContentPane().add(text3);
		getContentPane().add(btnNew);
		getContentPane().add(text5);
		getContentPane().add(btnOK);
		getContentPane().add(ma);
		getContentPane().add(bgcolor);
		getContentPane().add(bacgrangd);

		this.setSize(430, 330);
		this.setIconImage(Toolkit.getDefaultToolkit().createImage("素材\\透明照片.png"));// 窗体图标
		this.setLocationRelativeTo(null);// 保持居中
		this.setUndecorated(true);// 去顶部
		this.setFocusable(true);// 面板首先获得焦点
		this.setBackground(new Color(255, 255, 255));// 背景颜色
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setAlwaysOnTop(true);// 最顶层
		this.setVisible(true);// 显示
	}

	public static void main(String[] args) {

		new  LoginView();

	}

	public void mouseClicked(MouseEvent e) {// 点击不恢复
	}

	public void mousePressed(MouseEvent e) {// 点击后
		if (e.getSource() == jan) {
			setExtendedState(JFrame.ICONIFIED);
		} else if (e.getSource() == this) {
			origin.x = e.getX();
			origin.y = e.getY();
		} else if (e.getSource() == bi) {
			System.exit(0);
		} else if (e.getSource() == ku1 || e.getSource() == text1) {
			if (f == 0) {
				gou1.setVisible(true);
				g = 1;
				f = 1;
			} else if (g == 1) {
				gou1.setVisible(false);
				f = 0;
				g = 0;
			}
		} else if (e.getSource() == ku2 || e.getSource() == text2) {
			if (h == 0) {
				gou2.setVisible(true);
				j = 1;
				h = 1;
			} else if (j == 1) {
				gou2.setVisible(false);
				h = 0;
				j = 0;
			}
		} else if(e.getSource()==btnNew){
			this.setVisible(false);
			new RegView(this);
			 
		} else if(e.getSource()==btnOK){
			String username=txtUserName.getText();
			CL.username=username;
			char[]c=txtPass.getPassword();
			String password=new String(c);
			CL.password=password;
			if (username.trim().equals("") || password.trim().equals("")) {
				JOptionPane.showMessageDialog(this, "用户名和密码必须填写!");
				return;
			}
			JSONObject json=new ForServer().login();
			if(json.getInt("state")==0){
//				this.dispose(); //去掉登录界面
				this.setVisible(false);
				Home home=new Home();   //进去主界面
				home.setVisible(true);
				try {
					CL.datasocket=new DatagramSocket();
					new ChatForServer(CL.datasocket);  //启动UDP服务器  接受
					new RegChatServer(CL.datasocket);  //启动心跳包
				} catch (SocketException e1) {
					e1.printStackTrace();
				}  //客户端 发送给服务端的udp 套字节  
//				CL.datasocket2=new DatagramSocket();
			}else{
				JOptionPane.showMessageDialog(this, json.getString("msg"));
			}
		}
	}

	public void mouseReleased(MouseEvent e) {// 点击时
		if (e.getSource() == btnOK || e.getSource() == text5) {
			text5.setFont(new Font("微软雅黑", 0, 15));
		}
	}

	public void mouseEntered(MouseEvent e) {// 悬停
		if (e.getSource() == jan) {
			an1.setOpaque(true);
		} else if (e.getSource() == bi) {
			an2.setOpaque(true);
		} else if (e.getSource() == txtUserName) {
			if (a == 0 && c == 0) {
				lie1.setIcon(new javax.swing.ImageIcon("素材//直线4.png"));
			}
		} else if (e.getSource() == txtPass) {
			if (b == 0 && d == 0) {
				lie2.setIcon(new javax.swing.ImageIcon("素材//直线4.png"));
			}
		} else if (e.getSource() == text3) {
			text3.setForeground(Color.GRAY);
		} else if (e.getSource() == btnNew) {
			btnNew.setForeground(Color.GRAY);
		} else if (e.getSource() == ma) {
			ma.setIcon(new javax.swing.ImageIcon("素材//二维码2.png"));
		}
	}

	public void mouseExited(MouseEvent e) {// 悬停后
		if (e.getSource() == jan) {
			an1.setOpaque(false);
		} else if (e.getSource() == bi) {
			an2.setOpaque(false);
		} else if (e.getSource() == txtUserName) {
			if (a == 0) {
				lie1.setIcon(new javax.swing.ImageIcon("素材//直线2.png"));
			}
		} else if (e.getSource() == txtPass) {
			if (b == 0) {
				lie2.setIcon(new javax.swing.ImageIcon("素材//直线2.png"));
			}
		} else if (e.getSource() == text3) {
			text3.setForeground(new Color(170, 170, 170));
		} else if (e.getSource() == btnNew) {
			btnNew.setForeground(new Color(170, 170, 170));
		} else if (e.getSource() == ma) {
			ma.setIcon(new javax.swing.ImageIcon("素材//二维码.png"));
		}

	}


}

