package com.zy.qq.seriver;

import java.util.ArrayList;

/**
 * Ⱥ��ϵ
 * @author �������
 *
 */
public class Group {

	private String pid;
	private ArrayList<String> list=new ArrayList<String>();  //�洢�û����
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}

	public void add(String uid){
		list.add(uid);
	}
	
	public ArrayList<String> getUser(){
		return list;
	}
	
}
