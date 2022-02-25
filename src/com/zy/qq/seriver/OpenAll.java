package com.zy.qq.seriver;

import java.io.IOException;

/**
 * 打开所有服务器
 * @author 清风理辛
 *
 */
public class OpenAll {

	public static void main(String[] args) {
		new Thread(){
			
			public void run() {
				
				System.out.println("打开服务器");
				LoginServer.openServer();
			};
			
		}.start();
		
		new Thread(){
				
				public void run() {
					
					System.out.println("启动聊天");
					ChatServer.openChatServer();
				};
				
			}.start();
			
		new Thread(){
			public void run() {
				
				System.out.println("启动添加服务器");
				AddServer.openServer();
			};
		}.start();
		
		new Thread(){
			public void run() {
				
				System.out.println("启动注册服务器");
				try {
					RegServer.openServer();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
		
		new Thread(){
			public void run() {
				
				System.out.println("启动文件服务器");
				FileServer.openFileServer();
			};
		}.start();
	}
}
