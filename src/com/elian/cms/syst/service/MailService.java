package com.elian.cms.syst.service;

import java.io.Serializable;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件服务接口
 * 
 * @author Joe
 * 
 */
public interface MailService extends Serializable {

	/**
	 * 发送邮件
	 */
	public void send(MimeMessage mimeMessage);

	/**
	 * 发送文本邮件
	 * 
	 * @param from
	 *            邮件发送人，如果为空则采用默认发送人
	 * @param mailTo
	 *            邮件收件人
	 * @param mailCc
	 *            邮件抄送人
	 * @param mailBcc
	 *            邮件密送人
	 * @param subject
	 *            邮件发送主题
	 * @param encoding
	 *            邮件内容的编码方式 比如是UTF-8、Big5等
	 * @param content
	 *            发送邮件的內容
	 * @param attachmentFilenames
	 *            附件路徑,包含附件名称
	 */
	public void sendText(String from, String[] mailTo, String[] mailCc,
			String[] mailBcc, String subject, String encoding, String content,
			Date sentDate, String[] attachmentFilenames)
			throws MessagingException;
			
	/**
	 * 
	 * 发送网页邮件
	 * 
	 * @param from
	 *            邮件发送人，如果为空则采用默认发送人
	 * @param mailTo
	 *            邮件收件人
	 * @param mailCc
	 *            邮件抄送人
	 * @param mailBcc
	 *            邮件密送人
	 * @param subject
	 *            邮件发送主题
	 * @param encoding
	 *            邮件内容的编码方式 比如是UTF-8、Big5等
	 * @param content
	 *            发送邮件的內容
	 * @param attachmentFilenames
	 *            附件路徑,包含附件名称
	 */
	public void sendHtml(String from, String[] mailTo, String[] mailCc,
			String[] mailBcc, String subject, String encoding, String content,
			Date sentDate, String[] attachmentFilenames)
			throws MessagingException;
}