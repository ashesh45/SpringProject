package com.bway.springdemo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailUtils {
	
	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(String ToEmail, String subject, String message) {
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(ToEmail);
		email.setSubject(subject);
		email.setText(message);
		
		mailSender.send(email);
		
		
	}
	
}
