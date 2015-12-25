package com.mgl.twiitermanager.controller;

public class ThreadFollowWhoFollowMe extends Thread{

	int quantityToFollow;

	public ThreadFollowWhoFollowMe(int quantityToFollow) {
		super();
		this.quantityToFollow = quantityToFollow;
	}

	
	public void run(){
		
		try {
			
			TwitterController controller = new TwitterController();
			controller.loginMyAccount();
			controller.followWhoFollowMe(quantityToFollow);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
