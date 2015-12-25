package com.mgl.social.server.controller.mails;

import java.util.ArrayList;

public class ThreadSendMail extends Thread {

	private String mail;
	private String message;
	private ControllerMail controller;

	public ThreadSendMail(String mail, String message, String... params) {
		super();
		try {

			controller = new ControllerMail();
			this.mail = mail;
			this.message = message;
			//por ahora funciona para 10 parametros empezando de 0 a 9 {0} - {9} 
			int i=0;
			for (String param : params) {
				
				this.message = message.replace("{"+i+"}",param);
				i++;
			}
			
		} catch (Exception e) {

		}

	}

	public void run() {
		try {

			controller.sendMail(mail, message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
