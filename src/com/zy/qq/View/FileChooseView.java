package com.zy.qq.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;

public class FileChooseView extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	private ChatView chatview;
	public FileChooseView(ChatView chatview) {
		this.chatview=chatview;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 539, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JFileChooser fileChooser = new JFileChooser();
		contentPane.add(fileChooser, BorderLayout.CENTER);
		
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int nRetVal = fileChooser.showOpenDialog(fileChooser);
		if (nRetVal == JFileChooser.APPROVE_OPTION){
			System.out.println(fileChooser.getSelectedFile().getPath());
			System.out.println(fileChooser.getSelectedFile().getName());
		}
	}
}
