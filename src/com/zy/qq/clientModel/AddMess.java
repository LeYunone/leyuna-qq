package com.zy.qq.clientModel;
/**
 * 添加好友的消息
 * @author 清风理辛
 *
 *///{"myuid":"","touid":"","type":"" }
public class AddMess {

	public String getMyuid() {
		return myuid;
	}
	public void setMyuid(String myuid) {
		this.myuid = myuid;
	}
	public String getTouid() {
		return touid;
	}
	public void setTouid(String touid) {
		this.touid = touid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private String myuid;
	private String touid;
	private String type;
	
	
}
