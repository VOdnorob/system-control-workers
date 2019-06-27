package com.valentyn.odnorob.diplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String text, String subject) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(to);
        msg.setText(text);
        msg.setSubject(subject);
        try {
            mailSender.send(msg);
        } catch (MailException ex) {
            ex.printStackTrace();
        }

    }
}
