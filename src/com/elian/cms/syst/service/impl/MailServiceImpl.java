package com.elian.cms.syst.service.impl;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.elian.cms.syst.service.MailService;
import com.elian.cms.syst.util.StringUtils;

/**
 * 邮件服务实现类
 * 
 * @author Joe
 * 
 */
@Component("mailService")
public class MailServiceImpl implements MailService, Serializable {
	private static final long serialVersionUID = 2477568053736959477L;

	private final static Log logger = LogFactory.getLog(MailServiceImpl.class);
	private JavaMailSender mailSender;

	@Resource
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 发送邮件
	 */
	public void send(MimeMessage mimeMessage) {
		mailSender.send(mimeMessage);
	}

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
			throws MessagingException {
		send(createMimeMessage(from, mailTo, mailCc, mailBcc, subject,
				encoding, false, content, sentDate, attachmentFilenames));
	}

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
			throws MessagingException {
		send(createMimeMessage(from, mailTo, mailCc, mailBcc, subject,
				encoding, true, content, sentDate, attachmentFilenames));
	}

	/**
	 * 创建邮件发送对象
	 */
	private MimeMessage createMimeMessage(String from, String[] mailTo,
			String[] mailCc, String[] mailBcc, String subject, String encoding,
			boolean html, String content, Date sentDate,
			String[] attachmentFilenames) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, true,
				encoding);
		if (StringUtils.isBlank(from))
			mail.setFrom(((JavaMailSenderImpl) mailSender)
					.getJavaMailProperties().getProperty("mail.defaultFrom"));
		else
			mail.setFrom(from);

		if (mailTo != null)
			mail.setTo(mailTo);
		if (mailCc != null)
			mail.setCc(mailCc);
		if (mailBcc != null)
			mail.setBcc(mailBcc);

		if (subject != null)
			mail.setSubject(subject);
		else
			mail.setSubject("");

		if (content != null)
			mail.setText(content, html);
		else
			mail.setText("");

		if (sentDate != null)
			mail.setSentDate(sentDate);
		else
			mail.setSentDate(new Date());

		if (attachmentFilenames != null) {
			addAttachmentFile(mail, attachmentFilenames, subject);
		}
		return mimeMessage;
	}

	/**
	 * 添加附件
	 * 
	 * @param helper
	 * @param attachmentFilenames
	 *            附件文件名，包括附件对应路径
	 */
	private void addAttachmentFile(MimeMessageHelper helper,
			String[] attachmentFilenames, String subject)
			throws MessagingException {
		Assert.notNull(attachmentFilenames,
				"Attachment file names must not be null");
		for (int i = 0, length = attachmentFilenames.length; i < length; i++) {
			File file = new File(attachmentFilenames[i]);
			if (!file.exists() || !file.isFile()) {
				logger.warn("The file is not found![subject=" + subject
						+ ", file=" + attachmentFilenames[i] + "]");
				continue;
			}
			// 附件名需要进行编码，不然如果是中文有可能会乱码
			String filename = file.getName();
			try {
				filename = MimeUtility.encodeText(filename);
			}
			catch (UnsupportedEncodingException e) {
				logger.info("Attachment file name can't be encoded " + "[file="
						+ attachmentFilenames[i] + "]!", e);
			}
			helper.addAttachment(filename, file);
		}
	}
}