package com.mgl.twiitermanager.controller;

public class ThreadMessageTimeline extends Thread{

	
	
	private int messageConstant;
	private int quantity;
	private String hashtag;
	private int quantitySearch;
	
	public ThreadMessageTimeline(int messageConstant, int quantity,
			String hashtag, int quantitySearch) {
		super();
		this.messageConstant = messageConstant;
		this.quantity = quantity;
		this.hashtag = hashtag;
		this.quantitySearch = quantitySearch;
	}




	public void run(){
		try {
			
			TwitterController controller = new TwitterController();
			controller.loginMyAccount();
			controller.messagePeopleInHashTag(messageConstant, quantity, hashtag,quantitySearch);	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
