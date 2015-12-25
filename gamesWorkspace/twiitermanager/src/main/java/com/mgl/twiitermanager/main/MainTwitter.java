package com.mgl.twiitermanager.main;

import java.util.ArrayList;



import com.mgl.twiitermanager.controller.DBController;
import com.mgl.twiitermanager.controller.ThreadFavorite;
import com.mgl.twiitermanager.controller.ThreadFollow;
import com.mgl.twiitermanager.controller.ThreadFollowWhoFollowMe;
import com.mgl.twiitermanager.controller.ThreadMessageTimeline;
import com.mgl.twiitermanager.controller.TwitterController;
import com.mgl.twiitermanager.model.MessageConstant;
import com.mgl.twiitermanager.model.UserInfo;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class MainTwitter {

	//data to login IDR 
	//just135.justhost.com
	//idontrem_miguelf
	//1Miguel_Arturo
	
	
	public static void main(String[] args) {

		try {
			
			
			TwitterController controller = new TwitterController();
			//controller.authenthicateNewUser();
			
			//publishTweetYoutuberDash();
			
			updateMiguelAccount();
			//updateIDRAccount();
			//updateGoyoAccount();
			//updateVaneAccount();
			//updateAbilioAccount();
			
			//sendMessagesAllAccounts();
			
			//ThreadFollow threadFollow = new ThreadFollow("#screenshotsaturday",97);
			//threadFollow.start(); 
			
			//ThreadFavorite threadFavorite = new ThreadFavorite("#HappyBirthdayJazmynBieber",99);
			//threadFavorite.start(); 
			
			//ThreadMessageTimeline threadMessage = new ThreadMessageTimeline(MessageConstant.CRAPPY_PIGEON_MESSAGE, 25, "#funny",30);
			//threadMessage.start();
			
			//ThreadMessageTimeline threadMessage = new ThreadMessageTimeline(MessageConstant.VAMPIRE_SMASHER_MESSAGE, 25, "#vampire");
			//threadMessage.start();
			
			//ThreadFollowWhoFollowMe threadFollowWhoFollow = new ThreadFollowWhoFollowMe(50);
			//threadFollowWhoFollow.start();
			
			//TwitterController controller = new TwitterController();
			//controller.loginMyAccount();
			//controller.unfollowWithInDays(5);
			
			//controller.unfollowWhoDontFollowMeInDB();
			
			/*DBController controllerDB = new DBController();
			ArrayList<com.mgl.twiitermanager.model.Follower> list = controllerDB.loadUserIfollow();
			for(com.mgl.twiitermanager.model.Follower follower: list){
				System.out.println("User id "+follower.getIdFollower());
			}*/

			//System.out.println("lost size "+list.size() );
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void sendMessagesAllAccounts() {
		try {
			
			
			
			ArrayList<String> nameList = new ArrayList<>();
			nameList.add(new String("miguelarturof"));
			nameList.add(new String("vanesamg20"));
			nameList.add(new String("idrGames"));
			nameList.add(new String("josegoyo1990"));
			nameList.add(new String("AJasymmetry"));
			
			TwitterController controller = new TwitterController();
			for(String name : nameList){
				
				boolean b = controller.loginMyAccount(name);
				if(!b){
					System.out.println("Problem with login");
					continue;
				}
				
				controller.messagePeopleInHashTag(MessageConstant.VAMPIRE_SMASHER_MESSAGE, 13, "#internetbestfriendday", 35);
				
				controller.messagePeopleInHashTag(MessageConstant.CRAPPY_PIGEON_MESSAGE, 12, "#SundayFunday", 35);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void publishTweetYoutuberDash() {
		try {
			
			TwitterController controller = new TwitterController();
			ArrayList<UserInfo> userList = controller.loadYoutuberDashList();
			
			for(UserInfo u : userList){
				
				TwitterController controllerAux = new TwitterController();
				controllerAux.loginMyAccountYoutuberDash(u);
				
				controllerAux.tweetYoutuberDash(u);
				
				
			}
			System.out.println("size "+userList.size());
			System.out.println("------------------------------------------------------------------------------------");
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void updateMiguelAccount() {
		try {
			
			//login
			TwitterController controller = new TwitterController();
			boolean b = controller.loginMyAccount("miguelarturof");
			if(!b){
				System.out.println("Problem with login");
				return;
			}
			
			
			int quantity = 7;
			int quantitySearch = 20;
			
			//german
			controller.messagePeopleYoutuber(MessageConstant.YOUTUBER_DASH_MESSAGE, quantity, "@GermanGarmendia", quantitySearch);
			
			//fernan
			//controller.messagePeopleYoutuber(MessageConstant.YOUTUBER_DASH_MESSAGE, quantity, "@fernanfloo", quantitySearch);
			
			//mangel
			//controller.messagePeopleYoutuber(MessageConstant.YOUTUBER_DASH_MESSAGE, quantity, "@mangelrogel", quantitySearch);
			
			//rubius
			//controller.messagePeopleYoutuber(MessageConstant.YOUTUBER_DASH_MESSAGE, quantity, "@Rubiu5", quantitySearch);
			
			//@aLexBY11
			//controller.messagePeopleYoutuber(MessageConstant.YOUTUBER_DASH_MESSAGE, quantity, "@aLexBY11", quantitySearch);
			
			//@eldiariodedross
			//controller.messagePeopleYoutuber(MessageConstant.YOUTUBER_DASH_MESSAGE, quantity, "@eldiariodedross", quantitySearch);
			
			//@LuzuGames
			//controller.messagePeopleYoutuber(MessageConstant.YOUTUBER_DASH_MESSAGE, quantity, "@LuzuGames", quantitySearch);
			
			//@bysTaXx
			//controller.messagePeopleYoutuber(MessageConstant.YOUTUBER_DASH_MESSAGE, quantity, "@bysTaXx", quantitySearch);
			
			//@vegetta777
			//controller.messagePeopleYoutuber(MessageConstant.YOUTUBER_DASH_MESSAGE, quantity, "@vegetta777", quantitySearch);
			
			//@WillyrexYT
			//controller.messagePeopleYoutuber(MessageConstant.YOUTUBER_DASH_MESSAGE, quantity, "@WillyrexYT", quantitySearch);
			
			//@_OtraVezLunes
			//controller.messagePeopleYoutuber(MessageConstant.YOUTUBER_DASH_MESSAGE, quantity, "@_OtraVezLunes", quantitySearch);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private static void updateVaneAccount() {
		try {
			
			//login
			TwitterController controller = new TwitterController();
			boolean b = controller.loginMyAccount("vanesamg20");
			if(!b){
				System.out.println("Problem with login");
				return;
			}
			
			//unfollow
			controller.unfollowWithInDays(5, 95);
			
			//favorite
			controller.favoriteTweets("cute", 63);
			
			//follow 
			controller.followAuto("android", 51, 55);
			
			//messageu
			controller.messagePeopleInHashTag(MessageConstant.CRAPPY_PIGEON_MESSAGE, 20, "funny", 35);
			
			controller.messagePeopleInHashTag(MessageConstant.VAMPIRE_SMASHER_MESSAGE, 25, "vampire", 35);
			
			//controller.messagePeopleInHashTag(MessageConstant.FACEBOOK_FANPAGE_MESSAGE, 15, "#facebook", 35);
			
			//follow who follow me
			controller.followWhoFollowMe(35);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void updateIDRAccount() {
		try {
			
			//login
			TwitterController controller = new TwitterController();
			boolean b = controller.loginMyAccount("idrGames");
			if(!b){
				System.out.println("Problem with login");
				return;
			}
			
			//message
			controller.messagePeopleInHashTag(MessageConstant.VAMPIRE_SMASHER_MESSAGE, 15, "vampires", 20);
			
			controller.messagePeopleInHashTag(MessageConstant.CRAPPY_PIGEON_MESSAGE, 10, "funny", 20);
			
			//unfollow
			controller.unfollowWithInDays(5, 95);

			//favorite
			controller.favoriteTweets("indie", 63);
			
			//follow 
			controller.followAuto("game", 97, 50);
			
		
			
			
			
			//controller.messagePeopleInHashTag(MessageConstant.FACEBOOK_FANPAGE_MESSAGE, 5, "#facebook", 35);
			
			//follow who follow me
			controller.followWhoFollowMe(45);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private static void updateGoyoAccount() {
		try {
			
			//login
			TwitterController controller = new TwitterController();
			boolean b = controller.loginMyAccount("josegoyo1990");
			if(!b){
				System.out.println("Problem with login");
				return;
			}
			
			//message
			controller.messagePeopleInHashTag(MessageConstant.VAMPIRE_SMASHER_MESSAGE, 25, "monster", 25);
			
			controller.messagePeopleInHashTag(MessageConstant.CRAPPY_PIGEON_MESSAGE, 25, "funny", 25);
			
			//unfollow
			controller.unfollowWithInDays(5, 150);
			
			//favorite
			controller.favoriteTweets("game", 70);
			
			//follow 
			controller.followAuto("art", 45, 60);
			
			
			
			//controller.messagePeopleInHashTag(MessageConstant.FACEBOOK_FANPAGE_MESSAGE, 5, "#ilustration", 25);
			
			//follow who follow me
			controller.followWhoFollowMe(45);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private static void updateAbilioAccount() {
		try {
			
			//login
			TwitterController controller = new TwitterController();
			boolean b = controller.loginMyAccount("AJasymmetry");
			if(!b){
				System.out.println("Problem with login");
				return;
			}
			
			//message
			controller.messagePeopleInHashTag(MessageConstant.CRAPPY_PIGEON_MESSAGE, 25, "humor", 25);
			
			controller.messagePeopleInHashTag(MessageConstant.VAMPIRE_SMASHER_MESSAGE, 25, "rock", 25);
			
			//unfollow
			controller.unfollowWithInDays(5, 40);
			
			//favorite
			//controller.favoriteTweets("game", 60);
			
			//follow 
			//sfx
			controller.followAuto("#HugYourCatDay", 97, 50);
			
			
			
			//controller.messagePeopleInHashTag(MessageConstant.FACEBOOK_FANPAGE_MESSAGE, 25, "#gaming", 25);
			
			//follow who follow me
			controller.followWhoFollowMe(45);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
