package com.example.email;

import com.example.util.MailSenderInfo;
import com.example.util.SimpleMailSender;

public class EmailTest {

	public static void main(String[] args) {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.****.com");
		mailInfo.setMailServerPort("**");
		mailInfo.setValidate(true);
		mailInfo.setUserName("abc@163.com");
		mailInfo.setPassword("");// 您的邮箱密码
		mailInfo.setFromAddress("abc@163.com");
		mailInfo.setToAddress("abc@163.com");
		mailInfo.setSubject("设置邮箱标题");
//		mailInfo.setContent("<font color='red'>设置邮箱内容</font>");
		mailInfo.setContent("设置邮箱内容");
		
//		File[] attachFiles = new File[]{new File("C:\\Users\\abc\\Desktop\\1.txt"),new File("C:\\Users\\abc\\Desktop\\2.txt")};
//		mailInfo.setAttachFiles(attachFiles);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendEmail(mailInfo);
		
	}
}
