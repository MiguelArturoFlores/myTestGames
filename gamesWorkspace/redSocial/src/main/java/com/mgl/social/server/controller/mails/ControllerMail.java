package com.mgl.social.server.controller.mails;

public class ControllerMail {

	public void sendMail(String mail, String message) {
		try {
			
			MailTLS email = new MailTLS();
			email.sendMail(mail,message);
			
		} catch (Exception e) {

		}		
	}

	
	
}
