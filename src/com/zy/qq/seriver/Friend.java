package com.zy.qq.seriver;
/**
 * 好友信息  （待开发全）
 * @author 清风理辛
 *
 */
public class Friend {

	public String getNetname() {
		return netname;
	}
	public void setNetname(String netname) {
		this.netname = netname;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	private String email;
	private String netname;   //昵称
	private String head;     //头像
	private String uid;     //编号
	private String sign;   //签名
}
