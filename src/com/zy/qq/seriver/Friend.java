package com.zy.qq.seriver;
/**
 * ������Ϣ  ��������ȫ��
 * @author �������
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
	private String netname;   //�ǳ�
	private String head;     //ͷ��
	private String uid;     //���
	private String sign;   //ǩ��
}
