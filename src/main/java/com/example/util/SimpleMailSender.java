package com.example.util;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 简单邮件发送器
 */
public class SimpleMailSender {

	public boolean sendEmail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// //不要用getDefaultInstance,这样只能使用一种邮箱，再想改成别的邮箱就不行了
		Session sendMailSession = Session.getInstance(pro, authenticator); // 用这个，程序里可以切换其他邮箱，前提是这些邮箱要开通smtp协议，这是MailSenderInfo里写死的。
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());

			MimeBodyPart content = null;
			if (mailInfo.isHtml()) {
				content = this.sendHtmlMail(mailInfo);
			} else {
				content = new MimeBodyPart();
				content.setText(mailInfo.getContent());
			}

			MimeMultipart msgMultipart = new MimeMultipart();
			if (mailInfo.getAttachFiles() != null && mailInfo.getAttachFiles().length > 0) {
				msgMultipart = this.getAttach(mailInfo);
			}
			if(content!=null){
				msgMultipart.addBodyPart(content);
			}
			mailMessage.setContent(msgMultipart);

			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 * @throws MessagingException
	 */
	private MimeMultipart getAttach(MailSenderInfo mailInfo) throws MessagingException {

		// 整封邮件的MINE消息体
		MimeMultipart msgMultipart = new MimeMultipart("mixed");// 混合的组合关系
		if (mailInfo.getAttachFiles() != null) {
			for (File file : mailInfo.getAttachFiles()) {
				// 附件1
				MimeBodyPart attch = new MimeBodyPart();
				// 把文件，添加到附件1中
				// 数据源
				DataSource ds = new FileDataSource(file);
				// 数据处理器
				DataHandler dh = new DataHandler(ds);
				// 设置第一个附件的数据
				attch.setDataHandler(dh);
				// 设置第一个附件的文件名
				attch.setFileName(file.getName());
				msgMultipart.addBodyPart(attch);
			}
		}
		return msgMultipart;
	}

	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 */
	private String sendTextMail(MailSenderInfo mailInfo) {
		// 设置邮件消息的主要内容
		String mailContent = mailInfo.getContent();
		return mailContent;
	}

	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 * @throws MessagingException
	 */
	private MimeBodyPart sendHtmlMail(MailSenderInfo mailInfo) throws MessagingException {
		// 正文内容
		MimeBodyPart content = new MimeBodyPart();

		// 正文（图片和文字部分）
		MimeMultipart bodyMultipart = new MimeMultipart("related");
		// html代码部分
		MimeBodyPart htmlPart = new MimeBodyPart();
		// html代码
		htmlPart.setContent(mailInfo.getContent(), "text/html;charset=utf-8");
		bodyMultipart.addBodyPart(htmlPart);
		// 设置内容为正文
		content.setContent(bodyMultipart);
		return content;
	}
}