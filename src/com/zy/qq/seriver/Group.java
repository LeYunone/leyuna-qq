package com.zy.qq.seriver;

import java.util.ArrayList;

/**
 * 群关系
 * @author 清风理辛
 *
 */
public class Group {

	private String pid;
	private ArrayList<String> list=new ArrayList<String>();  //存储用户编号
	
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
