package com.zy.qq.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.zy.qq.client.UserinfoList;
import java.awt.Color;

public class AddFriend extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public AddFriend() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 710, 642);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 245, 238));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(147, 49, 54, 15);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(230, 46, 214, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(493, 45, 93, 23);
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(30, 90, 644, 442);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		UserinfoList userinfolist=new UserinfoList();
		scrollPane.setViewportView(userinfolist);  //添加用户列表
		
	}
}
