package com.learn.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pepjaggu@gmail.com"); // Sender's email
        message.setTo(to);   // Recipient's email
        message.setSubject(subject);           // Email subject
        message.setText(body);                 // Email body
        mailSender.send(message);
        log.info("Email sent Successfully {}",message);
    }
}

