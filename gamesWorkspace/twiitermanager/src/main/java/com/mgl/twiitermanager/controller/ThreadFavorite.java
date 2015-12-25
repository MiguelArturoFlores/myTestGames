package com.mgl.twiitermanager.controller;

public class ThreadFavorite extends Thread{
	
	private String search;
	private int quantitySearch;
	private String accountName;
	
	public ThreadFavorite(String search, int quantitySearch,String accountName){
		this.search = search;
		this.quantitySearch = quantitySearch;
		this.accountName = accountName;
	}
	
	public void run(){
		try {
			
			TwitterController controller = new TwitterController();
			controller.loginMyAccount();
			controller.favoriteTweets(search,quantitySearch);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
