package com.zy.qq.seriver;

import org.apache.commons.mail.HtmlEmail;


/**
 * ������֤��   
 * @author �������
 *
 */
public class SendCode {

	public static boolean sendEmail(String emailaddress, String code) {

		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.163.com");
			email.setCharset("UTF-8");
			email.addTo(emailaddress);// �ռ���ַ
			email.setFrom("pengli617@163.com", "���װ��İְ�");
			email.setAuthentication("pengli617@163.com", "Aa3201360");
			email.setSubject("ʥ�����˵�����");
			email.setMsg("��֤����:" + code);
			email.send();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
