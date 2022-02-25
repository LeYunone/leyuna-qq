package com.zy.qq.View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import com.zy.qq.client.ImgList;
/**
 * 表情悬浮框
 * @author 清风理辛
 *
 */
public class ImgListView extends JDialog {

	/**
	 * Launch the application.
	 */
	/**
	 * Create the dialog.
	 */
	private ChatView chatview;
	
	public ImgListView(ChatView chatview) {
		this.chatview=chatview;
		setBounds(100, 100, 446, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			scrollPane.setViewportView(new ImgList(chatview,this));
		}
	}

}
