package com.zy.qq.seriver;

import java.io.IOException;

/**
 * �����з�����
 * @author �������
 *
 */
public class OpenAll {

	public static void main(String[] args) {
		new Thread(){
			
			public void run() {
				
				System.out.println("�򿪷�����");
				LoginServer.openServer();
			};
			
		}.start();
		
		new Thread(){
				
				public void run() {
					
					System.out.println("��������");
					ChatServer.openChatServer();
				};
				
			}.start();
			
		new Thread(){
			public void run() {
				
				System.out.println("������ӷ�����");
				AddServer.openServer();
			};
		}.start();
		
		new Thread(){
			public void run() {
				
				System.out.println("����ע�������");
				try {
					RegServer.openServer();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
		
		new Thread(){
			public void run() {
				
				System.out.println("�����ļ�������");
				FileServer.openFileServer();
			};
		}.start();
	}
}
