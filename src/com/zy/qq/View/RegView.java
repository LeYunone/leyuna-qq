package com.zy.qq.View;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.sf.json.JSONObject;

import com.zy.qq.uitility.CL;

public class RegView extends JFrame implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4921581261136983784L;
	JLabel bacgrangd, jan, bi, QQ, qq;// gif,��С�����رգ�logo��QQ��ͷ��
	JLabel an1, an2;// ��ɫ��|��
	JTextField user;// �˺�
	JPasswordField pass;// ����
	JPanel bgcolor;// ��
	JLabel btnSend,text1,text2,text3,text4,text5;// �Զ���¼����ס���룬�һ����룬ע���˺ţ���¼
	static Point origin = new Point();// ���������ڿ��϶�����
	int a = 0, b = 0, c = 0, d = 0;// ������
	int f = 0, g = 0, h = 0, j = 0;// ���ơ�
	JLabel btnGetNew;// ����
	private JPasswordField passwordField;
	private JTextField txtEmail;
	private JTextField txtCode;
	private JTextField txtPassword;
	private JTextField txtPpassword;
	private LoginView loginview;
	private JLabel label_2;
	
	public RegView(LoginView loginview) {
		this.loginview=loginview;
		// ʵ����
		bacgrangd = new JLabel(new ImageIcon("�ز�//1.gif"));
		bacgrangd.setBounds(-35, -123, 500, 250);
		jan = new JLabel(new ImageIcon("�ز�//��С��.png"));
		jan.setBounds(364, 2, 32, 32);
		bi = new JLabel(new ImageIcon("�ز�//�ر�.png"));
		bi.setBounds(396, 3, 32, 32);
		QQ = new JLabel(new ImageIcon("�ز�//qq.png"));
		QQ.setBounds(10, 10, 32, 32);
		qq = new JLabel("QQ");
		qq.setBounds(50, 5, 45, 45);
		an1 = new JLabel();
		an1.setBounds(361, 0, 35, 35);
		an2 = new JLabel();// ����
		an2.setBounds(395, 0, 35, 35);
		bgcolor = new JPanel();
		bgcolor.setBounds(0, 125, 500, 380);
		text5 = new JLabel("\u6CE8\u518C");
		text5.setBounds(206, 285, 80, 20);
		btnGetNew = new JLabel();
		btnGetNew.setBounds(100, 280, 242, 35);
		// ����
		qq.setFont(new Font("΢���ź�", 1, 25));
		qq.setForeground(Color.white);
		an1.setBackground(new Color(0, 0, 0, 0.3f));
		an2.setBackground(new Color(0, 0, 0, 0.3f));
		bgcolor.setBackground(new Color(255, 255, 255));
		text5.setFont(new Font("΢���ź�", 0, 15));
		text5.setForeground(Color.white);

		btnGetNew.setBackground(new Color(5, 186, 251));
		btnGetNew.setOpaque(true);

		// �¼�����
		jan.addMouseListener(this);
		bi.addMouseListener(this);
		btnGetNew.addMouseListener(this);
		this.addMouseListener(this);

		this.addMouseMotionListener(new MouseMotionListener() {// �����϶��¼�
			public void mouseMoved(MouseEvent e) {
			}

			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
			}
		});
		getContentPane().setLayout(null);

		getContentPane().add(jan);
		getContentPane().add(bi);
		getContentPane().add(qq);
		getContentPane().add(QQ);
		getContentPane().add(an1);
		getContentPane().add(an2);
		getContentPane().add(text5);
		getContentPane().add(btnGetNew);
		getContentPane().add(bgcolor);
		bgcolor.setLayout(null);
		btnSend = new JLabel("\u83B7\u53D6\u9A8C\u8BC1\u7801");
		btnSend.setBounds(243, 41, 80, 20);
		bgcolor.add(btnSend);
		btnSend.setFont(new Font("΢���ź�", 0, 12));
		btnSend.setForeground(new Color(170, 170, 170));
		btnSend.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblNewLabel_2 = new JLabel("\u8BF7\u8F93\u5165\u60A8\u8981\u6CE8\u518C\u7684\u90AE\u7BB1\u5730\u5740");
		lblNewLabel_2.setBounds(37, 10, 156, 15);
		lblNewLabel_2.setFont(new Font("����", Font.PLAIN, 12));
		bgcolor.add(lblNewLabel_2);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(203, 7, 193, 21);
		bgcolor.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel label = new JLabel("\u9A8C\u8BC1\u7801");
		label.setBounds(37, 44, 54, 15);
		label.setFont(new Font("����", Font.PLAIN, 12));
		bgcolor.add(label);
		
		txtCode = new JTextField();
		txtCode.setBounds(102, 41, 101, 21);
		bgcolor.add(txtCode);
		txtCode.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\u5BC6\u7801");
		lblNewLabel.setBounds(37, 82, 54, 15);
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 12));
		bgcolor.add(lblNewLabel);
		
		JLabel label_1 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		label_1.setBounds(37, 119, 54, 15);
		label_1.setFont(new Font("����", Font.PLAIN, 12));
		bgcolor.add(label_1);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(102, 79, 193, 21);
		bgcolor.add(txtPassword);
		txtPassword.setColumns(10);
		
		txtPpassword = new JTextField();
		txtPpassword.setColumns(10);
		txtPpassword.setBounds(102, 116, 193, 21);
		bgcolor.add(txtPpassword);
		
		label_2 = new JLabel("\u53D6\u6D88");
		label_2.setForeground(new Color(170, 170, 170));
		label_2.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		label_2.setBounds(362, 168, 45, 20);
		label_2.addMouseListener(this);
		bgcolor.add(label_2);
		
		passwordField = new JPasswordField();
		passwordField.setText("\u786E\u8BA4\u5BC6\u7801");
		passwordField.setOpaque(false);
		passwordField.setForeground(Color.GRAY);
		passwordField.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		passwordField.setEchoChar(' ');
		btnSend.addMouseListener(this);
		getContentPane().add(bacgrangd);

		this.setSize(430, 330);
		this.setIconImage(Toolkit.getDefaultToolkit().createImage("�ز�\\͸����Ƭ.png"));// ����ͼ��
		this.setLocationRelativeTo(null);// ���־���
		this.setUndecorated(true);// ȥ����
		this.setFocusable(true);// ������Ȼ�ý���
		this.setBackground(new Color(255, 255, 255));// ������ɫ
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setVisible(true);// ��ʾ
	}

	int count=15;
	
	public void mousePressed(MouseEvent e) {// �����
		
		if (e.getSource() == jan) {
			setExtendedState(JFrame.ICONIFIED);
		} else if (e.getSource() == this) {
			origin.x = e.getX();
			origin.y = e.getY();
		}
		if(e.getSource()==btnSend){  //������֤��
			final Timer t=new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					btnSend.setText((""+count--).trim());
					if(count==-1){
						btnSend.setText("��ȡ��֤��");
						t.cancel();
					}
				}
			}, 0,1000);
			
			
			if (txtEmail.getText().trim().equals("")) {
				javax.swing.JOptionPane.showMessageDialog(RegView.this, "�û�������Ϊ��!");
				return;
			}		
			try {
				Socket socket=new Socket(CL.ip,CL.reg_port);
				InputStream is=socket.getInputStream();
				OutputStream os=socket.getOutputStream();
				
				os.write(("{\"type\":\"code\",\"username\":\"" + txtEmail.getText() + "\"}").getBytes());
				os.flush();
				
				byte[] bytes = new byte[1024];
				int len = is.read(bytes);
				String str = new String(bytes, 0, len);
				JSONObject json = JSONObject.fromObject(str);
				if (json.getInt("state") == 0) {
					javax.swing.JOptionPane.showMessageDialog(RegView.this, "���ͳɹ���");
				} else {
					javax.swing.JOptionPane.showMessageDialog(RegView.this, "����ʧ�ܣ�");
				}
				is.close();
				os.close();
				socket.close();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}	
		}
	if(e.getSource()==btnGetNew){  //ע��   ���Ƚ�������ж�
			System.out.println("1");
			if (txtEmail.getText().trim().equals("")) {
				System.out.println("2");
				javax.swing.JOptionPane.showMessageDialog(RegView.this, "�û�������Ϊ��!");
				return;
			}
			if (txtPassword.getText().trim().equals("")) {
				System.out.println("3");
				javax.swing.JOptionPane.showMessageDialog(RegView.this, "���벻��Ϊ��!");
				return;
			}
			if (txtPpassword.getText().trim().equals("")) {
				javax.swing.JOptionPane.showMessageDialog(RegView.this, "ȷ�����벻��Ϊ��!");
				return;
			}
			if (txtCode.getText().trim().equals("")) {
				javax.swing.JOptionPane.showMessageDialog(RegView.this, "��֤�벻��Ϊ��!");
				return;
			}
			if (!txtPassword.getText().trim().equals(txtPpassword.getText())) {
				javax.swing.JOptionPane.showMessageDialog(RegView.this, "�������벻���!");
				return;
			}
			try {
				Socket socket = new Socket(CL.ip, CL.reg_port);
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				os.write(("{\"type\":\"reg\",\"username\":\"" + txtEmail.getText() + "\",\"password\":\""
						+ txtPassword.getText() + "\",\"code\":\"" + txtCode.getText() + "\"}").getBytes());
				os.flush();
				byte[] bytes = new byte[1024];
				int len = is.read(bytes);
				String str = new String(bytes, 0, len);
				JSONObject json = JSONObject.fromObject(str);
				if (json.getInt("state") == 0) {
					javax.swing.JOptionPane.showMessageDialog(RegView.this, "��ϲ��!ע��ɹ������Ե�¼�ˣ�");
					this.setVisible(false);
					loginview.setVisible(true);
				} else if (json.getInt("state") == 1) {
					javax.swing.JOptionPane.showMessageDialog(RegView.this, "�û����Ѵ���!");
					txtEmail.setText("");
				} else if (json.getInt("state") == 2) {
					javax.swing.JOptionPane.showMessageDialog(RegView.this, "��֤����������»��!");
					txtCode.setText("");
				} else if (json.getInt("state") == 3) {
					javax.swing.JOptionPane.showMessageDialog(RegView.this, "δ֪����!");
				}
				is.close();
				os.close();
				socket.close();
			} catch (Exception e2) {
			}			
		}

	}

	public void mouseReleased(MouseEvent e) {// ���ʱ
		if (e.getSource() == btnGetNew || e.getSource() == text5) {
			text5.setFont(new Font("΢���ź�", 0, 15));
		}
	}

	public void mouseEntered(MouseEvent e) {// ��ͣ
		if (e.getSource() == jan) {
			an1.setOpaque(true);
		} else if (e.getSource() == bi) {
			an2.setOpaque(true);
		}  else if (e.getSource() == text3) {
			text3.setForeground(Color.GRAY);
		} else if (e.getSource() == btnSend) {
			btnSend.setForeground(Color.GRAY);
		} 
	}

	public void mouseExited(MouseEvent e) {// ��ͣ��
		if (e.getSource() == jan) {
			an1.setOpaque(false);
		} else if (e.getSource() == bi) {
			an2.setOpaque(false);
		} else if (e.getSource() == text3) {
			text3.setForeground(new Color(170, 170, 170));
		} else if (e.getSource() == btnSend) {
			btnSend.setForeground(new Color(170, 170, 170));
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource()==label_2){
			this.dispose();
			loginview.setVisible(true);
		}
	}
}
