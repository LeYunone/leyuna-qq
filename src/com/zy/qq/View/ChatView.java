package com.zy.qq.View;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JSplitPane;
import java.awt.Cursor;

import javax.swing.Icon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import net.sf.json.JSONObject;

import com.zy.qq.clientModel.Mess;
import com.zy.qq.uitility.CL;

import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;
import javax.swing.JFileChooser;

/**
 * 鑱婂ぉ妗�鏆備笖鏃犺。
 * 
 * @author 娓呴鐞嗚緵
 * 
 */
@SuppressWarnings("serial")
public class ChatView extends JFrame implements MouseListener,ActionListener {

	private JPanel contentPane;

	private String uid;
	private String netname;
	private String head;
	private String sign;
	private JLabel lblhead;
	private JLabel lblnetname;
	private JLabel lblsign;
	private Vector<Mess> list;

	private ImgListView imglistview;
	private JTextPane MsgArea;
	private JTextPane MsgMy;
	private JFileChooser fileChooser;
	/**
	 * Create the frame.
	 */
	public ChatView(final String uid, String netname, String head, String sign,
			Vector<Mess> list) {

		this.setUid(uid);
		this.netname = netname;
		this.setHead(head);
		this.setSign(sign);
		this.setList(list);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 556, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		lblhead = new JLabel("");
		panel.add(lblhead, BorderLayout.WEST);

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		lblnetname = new JLabel("New label");
		panel_1.add(lblnetname, BorderLayout.CENTER);

		lblsign = new JLabel("New label");
		panel_1.add(lblsign, BorderLayout.SOUTH);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.EAST);

		JButton btnNewButton_1 = new JButton("New button");
		panel_4.add(btnNewButton_1);

		JButton btnSend = new JButton("  \u53D1   \u9001");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String f="";
				if(chat.equals("")){  //濡傛灉娌℃湁璋冪敤杩囪〃鎯� 鍒欒繘琛岀畝鍗曞鐞�
					f=MsgMy.getText();
				}else{
					String m=getChat();
					char[]c=m.toCharArray();
					int count=0;
					for(int i=0;i<c.length;i++){
						if(c[i]=='#'){
							count++;
						}
					}
					String s=MsgMy.getText();
					String d=MsgMy.getText().substring(m.length()-(2*count),MsgMy.getText().length());
					f=m+d;
				}
				//
//				sb.append(MsgMy.getText());
				setIs(true);
				String msg = f;  
				clearChat("");
				setChat2("");
				Mess mess = new Mess();
				mess.setMsg(msg);
				mess.setMyuid(JSONObject.fromObject(CL.My_json_info).getString(
						"uid"));
				mess.setTouid(uid);
				mess.setType("chat");
				mess.setCode((System.currentTimeMillis() + "").trim());
				JSONObject json = JSONObject.fromObject(mess);
				
				byte[] bf = json.toString().getBytes();
				int len = bf.length;
				try {
					DatagramPacket pack = new DatagramPacket(bf, len,
							InetAddress.getByName(CL.ip), CL.chat_port);
					CL.datasocket.send(pack);
					if(!path.equals("")){   //点击发送文件的时候，记录发送对方的id
						
						String str="{\"touid\":\""+uid+"\",\"filesname\":\""+CL.filename+"\",\"type\":\"filename\"}";
						bf = str.toString().getBytes();
						len = bf.length;					
						pack = new DatagramPacket(bf, len,
						InetAddress.getByName(CL.ip), CL.chat_port);
						CL.datasocket.send(pack);  //先将文件名发送给服务器
						Thread.sleep(1000);
							
						CL.fileTouid=uid;
						fis=new FileInputStream(new File(path));
						byte[] b = new byte[fis.available()];
						fis.read(b);
						
						pack=new DatagramPacket(b, 0,b.length,InetAddress.getByName(CL.ip), CL.File_port);
						System.out.println("客户端1:发送文件========");
						CL.datasocket.send(pack);  //发送文件给服务器				
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException s) {
					s.printStackTrace();
				}
				MsgMy.setText("");
				addMymess(mess);
			}
		});
		panel_4.add(btnSend);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(15);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		contentPane.add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);

		JPanel panel_3 = new JPanel();
		scrollPane.setColumnHeaderView(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

		JButton btnImg = new JButton("\u8868\u60C5");
		btnImg.addMouseListener(this);
		panel_3.add(btnImg);

		btnFile = new JButton("\u53D1\u9001\u6587\u4EF6");
		btnFile.addActionListener(this);
		panel_3.add(btnFile);
		
		MsgMy = new JTextPane();
		scrollPane.setViewportView(MsgMy);

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);
		
		MsgArea = new JTextPane();
		MsgArea.setEditable(false);
		scrollPane_1.setViewportView(MsgArea);
		splitPane.setDividerLocation(250);

		lblsign.setText(sign);
		lblnetname.setText(netname);
		lblhead.setIcon(new ImageIcon("上线/" + head + ".png"));
		for (Mess mess : list) { // 棣栨鎵撳紑鏃�閬嶅巻绂荤嚎淇℃伅
			System.out.println(mess.getMsg());
			addTomess(mess.getMsg());
		}
		this.setVisible(true);
		list.clear();
		ImgListView imglistview = new ImgListView(this);
		this.imglistview = imglistview;
		imglistview.setVisible(false);
	}

	public void imgList() {

	}
	
	public void setPath(String i){
		this.path=i;
	}
	
	public void addMymess(Mess mess) { // 闈㈡澘娣诲姞鎴戠殑淇℃伅
		String myname = JSONObject.fromObject(CL.My_json_info).getString(
				"netname");
		String msg = mess.getMsg();
		try {
			manageInfo(myname, msg);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void addTomess(String msg) { // 闈㈡澘娣诲姞鍏朵粬浜虹殑淇℃伅
		try {
			manageInfo(netname, msg);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void addMess(Vector<Mess> list) {
		for (Mess mess : list) {
			addTomess(mess.getMsg());
		}
	}

	public void manageInfo(String name,String info) throws BadLocationException {
		int length = info.length();// 鑾峰彇String 闀垮害
		char[] every = new char[length];
		boolean is=false;
		int count = 0;// 鍒濆瀛楃鐨勪綅缃紝鍙樺寲
		String path = "img/"; // 瀹炵幇insertString()鐨勫繀瑕佸墠鎻�
		Document doc = MsgArea.getStyledDocument();// AAAAA 鍚庨潰insertStirn
													// 鐢ㄥ埌doc,insertIcon鐢ㄥ埌MsgArea
		SimpleAttributeSet attr = new SimpleAttributeSet();// AAAAA
		for (int i = 0; i < every.length; i++) {
			every[i] = info.charAt(i);
			if (every[i] == '#') // 璇嗗埆淇℃伅涓槸鍚︽湁#鍙�
				is = true;
		}
		// 寮�鐜╂媶瀛楁父鎴�
		String msg = name+"\t"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n";
		doc.insertString(doc.getLength(), msg, attr);
		if (is == false) {
			msg = info + "\n";
			doc.insertString(doc.getLength(), msg, attr);
			return;
		}
		for (int i = 0; i <length; i++) {
			if (every[i] == '#') {
				String str = null;
				str = info.substring(count, i); // 寰楀埌琛ㄦ儏鍓嶇殑鏂囧瓧
				try {
					if (str != null)
						doc.insertString(doc.getLength(), str, attr);// AAAA娣诲姞琛ㄦ儏鍓嶇殑鏂囧瓧
				} catch (Exception e) {
					System.out.println("a big error here");
				}
				String icName;
				icName = info.substring(i, i + 3); // 寰楀埌琛ㄦ儏鐨勫悕瀛�#01
				Icon ic = new ImageIcon(path + icName + ".png");// 灏嗚〃鎯呰浆鍖栦负icon
				System.out.println(path + icName + ".png");
				MsgArea.setCaretPosition(doc.getLength());
				MsgArea.insertIcon(ic); // 鍔犲叆琛ㄦ儏
				count = i + 3;// 灏嗗瓧绗﹁捣濮嬩綅缃烦鍒拌〃鎯呭悗绗竴浣嶇疆
			}
		}
		if (count < length) {
			String theLast = null;
			theLast = info.substring(count, length);
			theLast=theLast+"\n";
			try {
				doc.insertString(doc.getLength(), theLast, attr);
			} catch (Exception e) {
				System.out.println("a big error here");
			}
		}else{
			String j="\n";
			doc.insertString(doc.getLength(), j, attr);
		}
	}
	
	private String chat=""; //瀛樺偍鑱婂ぉ妗嗙殑瀵嗚淇℃伅
	private String chat2="";  //宸ュ叿
	private JButton btnFile;
	private boolean is=true;
	
	public void setIs(boolean is){
		this.is=is;
	}
	
	public void manageMyInfo(String info) throws BadLocationException {  
		if(is){
			String a=MsgMy.getText();
			setChat2(a+info);
			setChat(getChat2());
			setIs(false);
		}else{
			char[]c=getChat2().toCharArray();
			int coun=0;
			for(int i=0;i<c.length;i++){
				if(c[i]=='#'){
					coun++;
				}
			}
			//绗簩娆�
			String b=MsgMy.getText().substring(getChat2().length()-(2*coun),MsgMy.getText().length());  
			setChat(getChat2()+b+info);  //寰楀埌淇℃伅
			setChat2(getChat());
		}
		
//		sb.append(MsgMy.getText()+info); //瀛樺偍淇℃伅
//		info=MsgMy.getText()+info;  //寰楀埌鎴戠殑淇℃伅鍓嶈█
		int length = info.length();// 鑾峰彇String 闀垮害
		char[] every = new char[length];
		boolean is=false;
		int count = 0;// 鍒濆瀛楃鐨勪綅缃紝鍙樺寲
		String path = "img/"; // 瀹炵幇insertString()鐨勫繀瑕佸墠鎻�
		Document doc = MsgMy.getStyledDocument();// AAAAA 鍚庨潰insertStirn
													// 鐢ㄥ埌doc,insertIcon鐢ㄥ埌MsgArea
		SimpleAttributeSet attr = new SimpleAttributeSet();// AAAAA
		for (int i = 0; i < every.length; i++) {
			every[i] = info.charAt(i);
			if (every[i] == '#') // 璇嗗埆淇℃伅涓槸鍚︽湁#鍙�
				is = true;
		}
		// 寮�鐜╂媶瀛楁父鎴�
		for (int i = 0; i <length; i++) {
			if (is == false) {
				String msg =info;
				doc.insertString(doc.getLength(), msg, attr);
				break;
			}
			if (every[i] == '#') {
				String str = null;
				str = info.substring(count, i); // 寰楀埌琛ㄦ儏鍓嶇殑鏂囧瓧
				try {
					if (str != null)
						doc.insertString(doc.getLength(), str, attr);// AAAA娣诲姞琛ㄦ儏鍓嶇殑鏂囧瓧
				} catch (Exception e) {
				}
				String icName;
				icName = info.substring(i, i + 3); // 寰楀埌琛ㄦ儏鐨勫悕瀛�#01
				Icon ic = new ImageIcon(path + icName + ".png");// 灏嗚〃鎯呰浆鍖栦负icon
				System.out.println(path + icName + ".png");
				MsgMy.setCaretPosition(doc.getLength());
				MsgMy.insertIcon(ic); // 鍔犲叆琛ㄦ儏
				count = i + 3;// 灏嗗瓧绗﹁捣濮嬩綅缃烦鍒拌〃鎯呭悗绗竴浣嶇疆
			}
		}
		if (count != length) {
			String theLast = null;
			theLast = info.substring(count, length);
			try {
				doc.insertString(doc.getLength(), theLast, attr);
			} catch (Exception e) {
			}
		}
	}
	public String getNetname() {
		return netname;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public Vector<Mess> getList() {
		return list;
	}

	public void setList(Vector<Mess> list) {
		this.list = list;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		imglistview.setVisible(true);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		imglistview.setVisible(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
	FileInputStream fis=null;
	private String path=""; //路径名
	private String name=""; //文件名
	@Override
	public void actionPerformed(ActionEvent arg0) {   //鐐瑰嚮鎸夐挳浜嬩欢
		if(arg0.getSource()==btnFile){
			fileChooser = new JFileChooser();
			fileChooser.setBounds(128, 10, 459, 363);
			this.add(fileChooser);
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int nRetVal = fileChooser.showOpenDialog(fileChooser);
			if (nRetVal == JFileChooser.APPROVE_OPTION){   //瀵规枃浠堕�鎷╄繘琛屼簨浠剁洃鍚�
				String path=fileChooser.getSelectedFile().getPath(); //鏂囦欢璺緞
				CL.filename=fileChooser.getSelectedFile().getName();  //文件名
				this.path=path;
				String name=fileChooser.getSelectedFile().getName(); //鏂囦欢鍚�

				String i=name.substring(name.indexOf(".")+1,name.length()); //鏂囦欢鏍煎紡  绮楃硻
				if(i.equalsIgnoreCase("png") || i.equalsIgnoreCase("gif") || i.equalsIgnoreCase("jpg") ){  //濡傛灉鏄浘鐗囨牸寮�
					try {
						manageMyInfo("#51"+name);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}else if(i.equalsIgnoreCase("doc") || i.equalsIgnoreCase("docx")){  //濡傛灉鏄枃妗�
					try {
						manageMyInfo("#52"+name);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}else if(i.equalsIgnoreCase("txt")){      //濡傛灉鏄痶xt
					try {
						manageMyInfo("#53"+name);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}else {
					try {
						manageMyInfo("#54"+name);   //濡傛灉鏄叾浠栨枃浠�
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	public void clearChat(String i){
		this.chat=i;
	}
	
	public void setChat(String i){
		this.chat=i;
	}
	
	public String getChat(){
		return chat;
	}

	public String getChat2() {
		return chat2;
	}

	public void setChat2(String chat2) {
		this.chat2 = chat2;
	}
}
