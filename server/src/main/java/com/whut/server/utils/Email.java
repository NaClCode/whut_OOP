package com.whut.server.utils;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;
import java.time.Duration;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.whut.server.service.DataProcessing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Email {
    
    private String randomCode;
    private String email;
    private String from = DataProcessing.config.get("email_name");
    private String host = DataProcessing.config.get("email_host");
    private String password = DataProcessing.config.get("email_password");
    private LocalDateTime now;
    private static final String sources = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXY";

    public Email() {
        setRandomCode();
    }

    public void setRandomCode() {
        Random rand = new Random();
        StringBuffer flag = new StringBuffer();
        for (int j = 0; j < 6; j++){
            flag.append(sources.charAt(rand.nextInt(sources.length() - 1)) + "");
        }
        randomCode = flag.toString();
        now = LocalDateTime.now();
    }

    public boolean sendEmail(String email){
        this.email = email;
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
            return new PasswordAuthentication(from, password);
            }
        });
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,
                                    new InternetAddress(email));
            message.setSubject("欢迎使用档案管理系统");
            message.setText("您的验证码是" + randomCode + "，请在5分钟内输入，请勿泄露给他人！");
            Transport.send(message);
            return true;
        }catch (MessagingException mex) {
            log.error("发送邮件失败", mex);
            return false;
        }
    }

    public boolean verifyCode(String email, String code){
        LocalDateTime target = LocalDateTime.now();
        Duration duration = Duration.between(now, target);
        System.out.println(duration.toMinutes());
        return code.equals(randomCode) && email.equals(this.email) && duration.toMinutes() <= 5;
    }

}
