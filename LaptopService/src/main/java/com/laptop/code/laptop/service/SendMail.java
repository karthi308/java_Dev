package com.laptop.code.laptop.service;

import org.springframework.stereotype.Service;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;
import javax.mail.Transport;
@Service
public class SendMail {
    public String mail(String mailId,String userId,String subject,String text) {

        final String username = "iotech22@gmail.com";
        final String password = "gzthkbzikqvhwzox";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("iotech22@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(mailId)
            );
            message.setSubject(subject);
            message.setText("Dear "+userId+","+ "\n\n"+text);
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
        return "Successfully OTP Sent to your "+mailId;
    }
    }