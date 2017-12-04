package com.xindong.api.common.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
//http://jiangzhengjun.iteye.com/blog/520830
/**
 * 发送邮件 带附件 
 * @author lichaoxiong
 *
 */
public class SimpleMail2 {
    public static void main(String[] args) throws Exception{
        System.out.println("Sending mail...");
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.163.com");//发送人的服务器地址
        props.setProperty("mail.user", "lichaoxiong6688@163.com");//发送人
        props.setProperty("mail.password", "628lichaoxiong");//发送人

        props.setProperty("mail.smtp.auth", "true");  
        
        MyAuthenticator myauth = new MyAuthenticator("lichaoxiong6688@163.com", "628lichaoxiong"); //发送人
        Session mailSession = Session.getDefaultInstance(props, myauth);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject("HTML  mail with images");
        message.setFrom(new InternetAddress("lichaoxiong6688@163.com"));//发送人
        message.addRecipient(Message.RecipientType.TO,
             new InternetAddress("460070867@qq.com"));//接收人

        //
        // This HTML mail have to 2 part, the BODY and the embedded image
        //
        MimeMultipart multipart = new MimeMultipart("related");

        // first part  (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
        messageBodyPart.setContent(htmlText, "text/html");

        // add it
        multipart.addBodyPart(messageBodyPart);

        // second part (the image)
        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource
          ("D:/test.i.51wot.com/640-454.jpg");//图片地址
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID","<image>");

        // add it
        multipart.addBodyPart(messageBodyPart);

        // put everything together
        message.setContent(multipart);

        transport.connect();
        transport.sendMessage(message,
            message.getRecipients(Message.RecipientType.TO));
        transport.close();
        }
    
    
}