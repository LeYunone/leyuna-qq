package com.zy.qq.seriver;

import java.net.Socket;
/**
 *聊天服务器用
 * @author 清风理辛
 *
 */
public class Userinfo {

	@Override
	public String toString() {
		return "Userinfo [socket=" + socket + ", uip=" + uip + ", uport="
				+ uport + ", uid=" + uid + ", uemail=" + uemail + ", uiphone="
				+ uiphone + "]";
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public String getUip() {
		return uip;
	}
	public void setUip(String uip) {
		this.uip = uip;
	}
	public int getUport() {
		return uport;
	}
	public void setUport(int uport) {
		this.uport = uport;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUemail() {
		return uemail;
	}
	public void setUemail(String uemail) {
		this.uemail = uemail;
	}
	public String getUiphone() {
		return uiphone;
	}
	public void setUiphone(String uiphone) {
		this.uiphone = uiphone;
	}
	private Socket socket;
	private String uip;
	private int uport;
	private String uid;
	private String uemail;
	private String uiphone;
}
