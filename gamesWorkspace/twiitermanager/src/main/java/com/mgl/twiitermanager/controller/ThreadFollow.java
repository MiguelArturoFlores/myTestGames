package com.mgl.twiitermanager.controller;

public class ThreadFollow extends Thread {

	private String search;
	private int quantityToFollow;

	private int quantitySearch;
	private String accountName;
	
	public ThreadFollow(String search, int quantityToFollow, int quantitySearch) {
		try {
			this.search = search;
			this.quantityToFollow = quantityToFollow;
			this.quantitySearch = quantitySearch;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {

			TwitterController controller = new TwitterController();
			controller.loginMyAccount();
			controller.followAuto(search,quantityToFollow,quantitySearch);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
