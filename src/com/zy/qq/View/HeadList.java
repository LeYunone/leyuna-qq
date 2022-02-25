package com.zy.qq.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
/**
 * 头像列表
 * @author 清风理辛
 *
 */
public class HeadList extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	/**
	 * Create the frame.
	 */
	private MyUserinfoview myuserinfoview;
	public HeadList(MyUserinfoview myuserinfoview) {
		this.myuserinfoview=myuserinfoview;
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 440, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		getHead();
		this.setVisible(true);
	}
	
	public void getHead(){
		int x=0;  //59
		int y=0;  //58
		int count=0;
		for(int i=0;i<=39;i++){  //39张头像
			JLabel lblhead=new JLabel();
			String path="上线/"+i+".png"; //头像路径
			lblhead.setIcon(new ImageIcon(path)); //加载图片
			lblhead.setToolTipText(i+"");
			lblhead.setBounds(10+x, 10+y, 49 ,48);
			final String str= i+"";
			lblhead.addMouseListener(new MouseAdapter() {  //点击图片得到图片
				@Override
				public void mouseClicked(MouseEvent e) {
					myuserinfoview.setHead((str+"").trim());  //得到头像选择
				}
			});
			
			x+=59;
			count++;
			if(count==7){
				y+=58;
				x=0;
				count=0;
			}
			panel.add(lblhead);
		}
		this.setPreferredSize(new Dimension(0,50*6));
	}
}
