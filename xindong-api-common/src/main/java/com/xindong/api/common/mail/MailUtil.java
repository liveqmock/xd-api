package com.xindong.api.common.mail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;


/**
 * 邮件发送工具实现类
 */
public class MailUtil {

	protected final Logger logger = Logger.getLogger(getClass());

	public boolean send(Mail mail) {
		// 发送email
		HtmlEmail email = new HtmlEmail();
		try {
			// 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
			email.setHostName(mail.getHost());
			// 字符编码集的设置
			email.setCharset(Mail.ENCODEING);
			// 收件人的邮箱
			email.addTo(mail.getReceiver());
			// 发送人的邮箱
			email.setFrom(mail.getSender(), mail.getName());
			// 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
			email.setAuthentication(mail.getUsername(), mail.getPassword());
			// 要发送的邮件主题
			email.setSubject(mail.getSubject());
			// 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
			email.setMsg(mail.getMessage());
			// 发送
			email.send();
			if (logger.isDebugEnabled()) {
				logger.debug(mail.getSender() + " 发送邮件到 " + mail.getReceiver());
			}
			return true;
		} catch (EmailException e) {
			e.printStackTrace();
			logger.error(mail.getSender() + " 发送邮件到 " + mail.getReceiver()
					+ " 失败");
			return false;
		}
	}

	public static void main(String[] args) {
		Mail mail = new Mail();
		mail.setHost("smtp.163.com");
		mail.setReceiver("460070867@qq.com"); // 接收人
		mail.setUsername("lichaoxiong6688@163.com");
		mail.setSender("lichaoxiong6688@163.com");
		mail.setPassword("628lichaoxiong");
		mail.setSubject("aaaaaaaaa");
		mail.setMessage("<!DOCTYPE><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head>" +
				"<body>一跑倾城<h6>恭喜您，您已报名成功！二维码已发送，请用微信购买账号登录，请点击一跑倾城官方微信“我的二维码”按钮查看！请妥善保管二维码，" +
				"活动当天凭二维码领取装备包参与比赛。客服电话：17051104437   客服邮箱：info@1pqc.cn。</h6><br/><br/><img alt=\"图片无法加载\" src=\"http://test.i.51wot.com/img/2016/3/23/prdlist1.jpg\"></body></html>");//内容
		new MailUtil().send(mail);
	}
}
