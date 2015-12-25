package com.mgl.social.server.controller.mails;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailTLS {

	final String username = "mysocial.all@gmail.com";
	final String password = "yyyymmdd";

	private Properties props ;
	private Session session;
	
	public MailTLS() {

		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username,
								password);
					}
				});
		
	}

	public void sendMail(String email, String messageToSend) {

		try {

			

			String 	HTMLBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">	<head><title>DEFAULT TITLE</title>	</head><body><p>hi this is the mail for like do you like it?</p></body></html>";
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mysocial.all@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			message.setSubject("Testing Subject");
			BodyPart bPart = new MimeBodyPart();
			
			//here set the htmlButy
			bPart.setContent(messageToSend, "text/html; charset=us-ascii");
			
			Multipart multipart = new MimeMultipart("alternative");
			multipart.addBodyPart(bPart);
			message.setContent(multipart);
			
			Transport.send(message);
			
			System.out.println("Done to "+email);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
