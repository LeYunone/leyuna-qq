package com.zy.qq.seriver;

import org.apache.commons.mail.HtmlEmail;


/**
 * 发送验证码   
 * @author 清风理辛
 *
 */
public class SendCode {

	public static boolean sendEmail(String emailaddress, String code) {

		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.163.com");
			email.setCharset("UTF-8");
			email.addTo(emailaddress);// 收件地址
			email.setFrom("pengli617@163.com", "你亲爱的爸爸");
			email.setAuthentication("pengli617@163.com", "Aa3201360");
			email.setSubject("圣诞老人的礼物");
			email.setMsg("验证码是:" + code);
			email.send();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
