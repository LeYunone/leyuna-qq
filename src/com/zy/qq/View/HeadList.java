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
 * ͷ���б�
 * @author �������
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
		for(int i=0;i<=39;i++){  //39��ͷ��
			JLabel lblhead=new JLabel();
			String path="����/"+i+".png"; //ͷ��·��
			lblhead.setIcon(new ImageIcon(path)); //����ͼƬ
			lblhead.setToolTipText(i+"");
			lblhead.setBounds(10+x, 10+y, 49 ,48);
			final String str= i+"";
			lblhead.addMouseListener(new MouseAdapter() {  //���ͼƬ�õ�ͼƬ
				@Override
				public void mouseClicked(MouseEvent e) {
					myuserinfoview.setHead((str+"").trim());  //�õ�ͷ��ѡ��
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
