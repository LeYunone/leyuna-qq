package com.zy.qq.client;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;

import com.zy.qq.View.ChatView;
import com.zy.qq.View.ImgListView;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
 * 头像列表
 * @author 清风理辛
 *
 */
public class ImgList extends JPanel implements MouseListener {

	/**
	 * Create the panel.
	 */
	private ChatView chatview;
	private JLabel lblImg;
	private ImgListView imglistview;
	
	public ImgList(ChatView chatview,ImgListView imglistview) {
		this.chatview=chatview;
		this.imglistview=imglistview;
		setLayout(null);
		getImg();
		setVisible(true);
	}
	
	public void getImg(){
		int x=0;
		int y=0;
		int count=0;
		String path=null;
		String p=null;
		for(int i=1;i<=50;i++){
			if(i<10){
				p=("#0"+i).trim();
				path="img/"+p+".png";
			}else{
				p=("#"+i).trim();
				path="img/"+p+".png";
			}
			lblImg=new JLabel(new ImageIcon(path));
			lblImg.setToolTipText((i+"").trim());
			final String mp=p;
			lblImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {  //如果点击了这个表情
					try {
						chatview.manageMyInfo(mp);
						 imglistview.dispose();
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
				
			});
			lblImg.setBounds(10+x, 10+y,28,28);
			x+=38;
			count++;
			if(count==11){
				y+=35;
				x=0;
				count=0;
			}
			this.add(lblImg);
			this.updateUI();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
