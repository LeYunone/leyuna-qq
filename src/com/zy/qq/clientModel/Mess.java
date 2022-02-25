package com.zy.qq.clientModel;

/**
 * 消息
 * @author 清风理辛
 *
 */
public class Mess {

	private String msg;
	private String touid;
	private String myuid;
	private String code;
	private String type;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTouid() {
		return touid;
	}
	public void setTouid(String touid) {
		this.touid = touid;
	}
	public String getMyuid() {
		return myuid;
	}
	public void setMyuid(String myuid) {
		this.myuid = myuid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Mess [msg=" + msg + ", touid=" + touid + ", myuid=" + myuid
				+ ", code=" + code + ", type=" + type + "]";
	}
	
	
	
	
}
