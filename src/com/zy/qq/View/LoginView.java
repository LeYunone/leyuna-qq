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

	JLabel bacgrangd, jan, bi, QQ, qq, tu;// gif,��С�����رգ�logo��QQ��ͷ��
	JLabel an1, an2, lie1, lie2;// ��ɫ��|��
	JTextField txtUserName;// �˺�
	JPasswordField txtPass;// ����
	JPanel bgcolor;// ��
	JLabel su1, mi1, ku1, ku2, gou1, gou2;// ����ͼ
	JLabel text1, text2, text3, btnNew, text5;// �Զ���¼����ס���룬�һ����룬ע���˺ţ���¼
	static Point origin = new Point();// ���������ڿ��϶�����
	int a = 0, b = 0, c = 0, d = 0;// ������
	int f = 0, g = 0, h = 0, j = 0;// ���ơ�
	JLabel btnOK, ma;// ����

	public LoginView() {

		// ʵ����
		bacgrangd = new JLabel(new ImageIcon("�ز�//1.gif"));
		jan = new JLabel(new ImageIcon("�ز�//��С��.png"));
		bi = new JLabel(new ImageIcon("�ز�//�ر�.png"));
		QQ = new JLabel(new ImageIcon("�ز�//qq.png"));
		qq = new JLabel("QQ");
		an1 = new JLabel();
		an2 = new JLabel();// ����
		tu = new JLabel(new ImageIcon("�ز�//ͷ��.png"));
		txtUserName = new JTextField();
		txtPass = new JPasswordField();
		su1 = new JLabel(new ImageIcon("�ز�//qq (1).png"));
		mi1 = new JLabel(new ImageIcon("�ز�//����.png"));
		lie1 = new JLabel(new ImageIcon("�ز�//ֱ��2.png"));
		lie2 = new JLabel(new ImageIcon("�ز�//ֱ��2.png"));
		bgcolor = new JPanel();
		ku1 = new JLabel(new ImageIcon("�ز�//���.png"));
		ku2 = new JLabel(new ImageIcon("�ز�//���.png"));
		gou1 = new JLabel(new ImageIcon("�ز�//�Թ�.png"));
		gou2 = new JLabel(new ImageIcon("�ز�//�Թ�.png"));
		text1 = new JLabel("�Զ���¼");
		text2 = new JLabel("��ס����");
		text3 = new JLabel("�һ�����");
		btnNew = new JLabel("ע���˺�");
		text5 = new JLabel("��¼");
		btnOK = new JLabel();
		ma = new JLabel(new ImageIcon("�ز�//��ά��.png"));

		// λ��
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
		// ����
		qq.setFont(new Font("΢���ź�", 1, 25));
		qq.setForeground(Color.white);
		an1.setBackground(new Color(0, 0, 0, 0.3f));
		an2.setBackground(new Color(0, 0, 0, 0.3f));
		bgcolor.setBackground(new Color(255, 255, 255));

		txtUserName.setForeground(Color.gray);
		txtUserName.setText("QQ����/�ֻ�/����");
		txtUserName.setOpaque(false);// ͸������
		txtUserName.setBorder(null);// ȥ���߿�
		txtUserName.setFont(new Font("΢���ź�", Font.PLAIN, 16));// ����������ʽ
		txtPass.setFont(new Font("΢���ź�", Font.PLAIN, 16));// ����������ʽ
		txtPass.setBorder(null);// ȥ���߿�

		txtPass.setOpaque(false);// ͸������
		txtPass.setForeground(Color.gray);
		txtPass.setText("����");
		txtPass.setEchoChar((char) 0);// ��������ʾ����

		text1.setFont(new Font("΢���ź�", 0, 12));
		text2.setFont(new Font("΢���ź�", 0, 12));
		text3.setFont(new Font("΢���ź�", 0, 12));
		btnNew.setFont(new Font("΢���ź�", 0, 12));
		text5.setFont(new Font("΢���ź�", 0, 15));
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

		// �¼�����
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

		this.addMouseMotionListener(new MouseMotionListener() {// �����϶��¼�
			public void mouseMoved(MouseEvent e) {
			}

			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
			}
		});

		txtUserName.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {// ʧȥ����
				su1.setIcon(new javax.swing.ImageIcon("�ز�//qq (1).png"));
				lie1.setIcon(new javax.swing.ImageIcon("�ز�//ֱ��2.png"));
				c = 0;
				if (txtUserName.getText().isEmpty()) {// �ж��Ƿ�Ϊ�գ�Ϊ������Ĭ����ʾ�
					txtUserName.setForeground(Color.gray);
					txtUserName.setText("QQ����/�ֻ�/����");
				}
			}

			public void focusGained(FocusEvent e) {// �õ�����
				txtUserName.setForeground(Color.black);
				lie1.setIcon(new javax.swing.ImageIcon("�ز�//ֱ��3.png"));
				a = 1;
				c = 1;
				b = 0;
				su1.setIcon(new javax.swing.ImageIcon("�ز�//qq (2).png"));
				if (txtUserName.getText().equals("QQ����/�ֻ�/����")) {
					txtUserName.setText("");
				} else {
					txtUserName.setText(txtUserName.getText());
					txtUserName.selectAll();
				}
			}
		});

		txtPass.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {// ʧȥ����
				lie2.setIcon(new javax.swing.ImageIcon("�ز�//ֱ��2.png"));// ʧȥ���㻻ͼƬ
				mi1.setIcon(new javax.swing.ImageIcon("�ز�//����.png"));
				d = 0;
				if (txtPass.getText().isEmpty()) {
					txtPass.setForeground(Color.gray);
					txtPass.setText("����");
					txtPass.setEchoChar((char) 0);// ��������ʾ����
				}
			}

			public void focusGained(FocusEvent e) {// �õ�����
				mi1.setIcon(new javax.swing.ImageIcon("�ز�//���� (1).png"));
				lie2.setIcon(new javax.swing.ImageIcon("�ز�//ֱ��3.png"));
				b = 1;
				a = 0;
				d = 1;
				txtPass.setForeground(Color.black);
				txtPass.setEchoChar('*');// ���û����뿴����
				if (txtPass.getText().equals("����")) {
					txtPass.setText("");
				} else {
					txtPass.setText(txtPass.getText());
				}
			}
		});

		getContentPane().setLayout(null);// ����

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
		this.setIconImage(Toolkit.getDefaultToolkit().createImage("�ز�\\͸����Ƭ.png"));// ����ͼ��
		this.setLocationRelativeTo(null);// ���־���
		this.setUndecorated(true);// ȥ����
		this.setFocusable(true);// ������Ȼ�ý���
		this.setBackground(new Color(255, 255, 255));// ������ɫ
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setAlwaysOnTop(true);// ���
		this.setVisible(true);// ��ʾ
	}

	public static void main(String[] args) {

		new  LoginView();

	}

	public void mouseClicked(MouseEvent e) {// ������ָ�
	}

	public void mousePressed(MouseEvent e) {// �����
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
				JOptionPane.showMessageDialog(this, "�û��������������д!");
				return;
			}
			JSONObject json=new ForServer().login();
			if(json.getInt("state")==0){
//				this.dispose(); //ȥ����¼����
				this.setVisible(false);
				Home home=new Home();   //��ȥ������
				home.setVisible(true);
				try {
					CL.datasocket=new DatagramSocket();
					new ChatForServer(CL.datasocket);  //����UDP������  ����
					new RegChatServer(CL.datasocket);  //����������
				} catch (SocketException e1) {
					e1.printStackTrace();
				}  //�ͻ��� ���͸�����˵�udp ���ֽ�  
//				CL.datasocket2=new DatagramSocket();
			}else{
				JOptionPane.showMessageDialog(this, json.getString("msg"));
			}
		}
	}

	public void mouseReleased(MouseEvent e) {// ���ʱ
		if (e.getSource() == btnOK || e.getSource() == text5) {
			text5.setFont(new Font("΢���ź�", 0, 15));
		}
	}

	public void mouseEntered(MouseEvent e) {// ��ͣ
		if (e.getSource() == jan) {
			an1.setOpaque(true);
		} else if (e.getSource() == bi) {
			an2.setOpaque(true);
		} else if (e.getSource() == txtUserName) {
			if (a == 0 && c == 0) {
				lie1.setIcon(new javax.swing.ImageIcon("�ز�//ֱ��4.png"));
			}
		} else if (e.getSource() == txtPass) {
			if (b == 0 && d == 0) {
				lie2.setIcon(new javax.swing.ImageIcon("�ز�//ֱ��4.png"));
			}
		} else if (e.getSource() == text3) {
			text3.setForeground(Color.GRAY);
		} else if (e.getSource() == btnNew) {
			btnNew.setForeground(Color.GRAY);
		} else if (e.getSource() == ma) {
			ma.setIcon(new javax.swing.ImageIcon("�ز�//��ά��2.png"));
		}
	}

	public void mouseExited(MouseEvent e) {// ��ͣ��
		if (e.getSource() == jan) {
			an1.setOpaque(false);
		} else if (e.getSource() == bi) {
			an2.setOpaque(false);
		} else if (e.getSource() == txtUserName) {
			if (a == 0) {
				lie1.setIcon(new javax.swing.ImageIcon("�ز�//ֱ��2.png"));
			}
		} else if (e.getSource() == txtPass) {
			if (b == 0) {
				lie2.setIcon(new javax.swing.ImageIcon("�ز�//ֱ��2.png"));
			}
		} else if (e.getSource() == text3) {
			text3.setForeground(new Color(170, 170, 170));
		} else if (e.getSource() == btnNew) {
			btnNew.setForeground(new Color(170, 170, 170));
		} else if (e.getSource() == ma) {
			ma.setIcon(new javax.swing.ImageIcon("�ز�//��ά��.png"));
		}

	}


}

