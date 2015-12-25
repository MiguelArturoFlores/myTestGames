package com.mgl.twiitermanager.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import javax.sound.midi.ControllerEventListener;

import com.mgl.twiitermanager.message.MessageSingleton;
import com.mgl.twiitermanager.model.Follower;
import com.mgl.twiitermanager.model.UserInfo;
import com.mgl.twiitermanager.model.UserMessaged;
import com.mgl.twiitermanager.util.ManageDate;

import twitter4j.Friendship;
import twitter4j.GeoLocation;
import twitter4j.IDs;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterController {

	public static String CONSUMER_KEY = "qhC5Fq51pwY1VoMLqUX37yYvF";
	public static String CONSUMER_SECRET = "7EKRt01IrrCZLQLOulrIFEBuFdovIr2F0e4XNjVaiFQKtinTO2";

	
	public static String CONSUMER_KEY_YOUTUBER = "6ab0MEdH8kEtsQsSjpDTpx1cv";
	public static String CONSUMER_SECRET_YOUTUBER = "0hy0kJMtW4ExP7HbeJOmCqAclYQsJVwinDbtqSEhkyk8lSMdSf";

	
	
	public static String ACCESS_TOKEN = "2279402005-due5OpNJJYHo2YLXtGE0cTKT03MXEzXpVBuZGaT";
	public static String ACCESS_TOKEN_SECRET = "RhtuTpQyoY85SyCowHtK7miUBKJNOwG0508129uWyAHXh";
	// public static long OWNER_ID = 2279402005;

	private Twitter twitter;
	private DBController controller;
	

	public TwitterController() {
		try {
			controller = new DBController();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertUser() {

	}

	public void postTweet(String string) {
		try {

			Status status = twitter.updateStatus("test update 1");
			System.out.println("Successfully updated the status to ["
					+ status.getText() + "].");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loginMyAccount() {
		try {

			twitter = TwitterFactory.getSingleton();
			twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

			RequestToken requestToken = twitter.getOAuthRequestToken();
			AccessToken accessToken = new AccessToken(ACCESS_TOKEN,
					ACCESS_TOKEN_SECRET);
			twitter.setOAuthAccessToken(accessToken);
			
			//verifyMainUserInDatabase();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void authenthicateNewUser() {
		try {

			twitter = TwitterFactory.getSingleton();
			twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

			RequestToken requestToken = twitter.getOAuthRequestToken();

			AccessToken accessToken = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			while (null == accessToken) {
				System.out
						.println("Open the following URL and grant access to your account:");
				System.out.println(requestToken.getAuthorizationURL());
				System.out
						.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
				String pin = br.readLine();
				try {
					if (pin.length() > 0) {
						accessToken = twitter.getOAuthAccessToken(requestToken,
								pin);
					} else {
						accessToken = twitter.getOAuthAccessToken();
					}
				} catch (TwitterException te) {
					if (401 == te.getStatusCode()) {
						System.out.println("Unable to get the access token.");
					} else {
						te.printStackTrace();
					}
				}
			}
			
			//persist to the accessToken for future reference.
		    storeAccessToken(twitter.verifyCredentials().getId() , accessToken);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private  void storeAccessToken(long useId, AccessToken accessToken){
		try {
			System.out.println("Finally accesstoken"+accessToken.getToken());
		    System.out.println("Finally accesstokenSecret"+accessToken.getTokenSecret());
			
			controller.insertUser(new com.mgl.twiitermanager.model.User(twitter.getId(),twitter.getScreenName(),accessToken.getToken(),accessToken.getTokenSecret()));
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    
	  }

	private void verifyMainUserInDatabase(AccessToken accessToken) {
		try {

			com.mgl.twiitermanager.model.User user = controller
					.loadUser(twitter.getId());
			if (user != null) {
				return;
			}

			controller.insertUser(new com.mgl.twiitermanager.model.User(twitter.getId(),twitter.getScreenName(),accessToken.getToken(),accessToken.getTokenSecret()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Status> searchTweet(String wordToSearch, int quantity) {
		try {

			Query query = new Query(wordToSearch);
			int i = 1;
			int numberOfTweets = quantity;
			long lastID = Long.MAX_VALUE;
			ArrayList<Status> tweets = new ArrayList<Status>();

			while (tweets.size() < numberOfTweets) {
				if (numberOfTweets - tweets.size() > 100)
					query.setCount(100);
				else
					query.setCount(numberOfTweets - tweets.size());
				try {
					QueryResult result = twitter.search(query);
					tweets.addAll(result.getTweets());
					System.out.println("Gathered " + tweets.size() + " tweets");
					for (Status t : tweets)
						if (t.getId() < lastID)
							lastID = t.getId();

				}

				catch (TwitterException te) {
					System.out.println("Couldn't connect: " + te);
				}
				;
				query.setMaxId(lastID - 1);
			}

			/*
			 * for (int j = 0; j < tweets.size(); j++) { Status t = (Status)
			 * tweets.get(j);
			 * 
			 * GeoLocation loc = t.getGeoLocation();
			 * 
			 * String user = t.getUser().getScreenName(); String msg =
			 * t.getText(); String time = ""; if (loc != null) { Double lat =
			 * t.getGeoLocation().getLatitude(); Double lon =
			 * t.getGeoLocation().getLongitude(); System.out.println(j +
			 * " USER: " + user + " wrote: " + msg + " located at " + lat + ", "
			 * + lon); } else System.out.println(j + " USER: " + user +
			 * " wrote: " + msg); }
			 */

			return tweets;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Status>();

	}

	public ArrayList<User> getUserListToFollow(ArrayList<Status> statusList) {
		ArrayList<User> userRet = new ArrayList<>();
		try {

			for (Status status : statusList) {

				User user = status.getUser();

				ArrayList<Follower> list = controller.loadUserFollow(user.getId(), twitter.getId());
				
				if(list !=null && !list.isEmpty()){
					continue;
				}
				
				Relationship rs = twitter.showFriendship(twitter.getId(),
						user.getId());
				if (rs.isSourceFollowingTarget()) {
					// System.out.println("ya sigo a "+user.getName());
					continue;
				}

				// System.out.println("NO sigo a "+user.getName());
				int followersCount = user.getFollowersCount();
				int friendsCount = user.getFriendsCount();

				int val = followersCount - friendsCount;
				if (val < 0) {
					val = val * -1;
				}

				if (val > followersCount*1.2) {
					continue;
				}

				userRet.add(user);
				// twitter.createFriendship(user.getId());

			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				Thread.sleep(60*8*1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return userRet;
	}

	public void followAuto(String search, int quantityToFollow,
			int quantitySearch) {
		try {

			int follow = 0;
			int timeToSearch = 20;// 20 segundos
			int timeToFollow = 25;// 30 segundos
			int timeExtra = 5;

			while (follow < quantityToFollow) {

				ArrayList<User> userToFollow = getUserListToFollow(searchTweet(
						search, quantitySearch));

				for (User user : userToFollow) {
					try {

						if(follow>quantityToFollow){
							return;
						}
						
						ArrayList<Follower> followerList = controller
								.loadUserFollow(user.getId(),twitter.getId());

						if (followerList == null || followerList.isEmpty()) {

							twitter.createFriendship(user.getId());
							controller
									.insertFollower(new Follower(
											twitter.getId(),
											user.getId(),
											user.getScreenName(),
											ManageDate
													.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS)));
							follow++;
							System.out.println(follow + " Follow "
									+ user.getName());
							Thread.sleep((timeToFollow + getRandomMax(1,
									timeExtra)) * 1000);
						}
					} catch (Exception e) {

					}

				}

				Thread.sleep(timeToSearch * 1000);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void favoriteTweets(String search, int quantitySearch) {
		try {
			int timeToFavorite = 15;
			int favoriteCount = 0;
			int FAVORITE_QUEANTITY = quantitySearch;
			while (true) {
				ArrayList<Status> statusList = searchTweet(search,
						quantitySearch+15);

				for (Status status : statusList) {
					try {

						twitter.createFavorite(status.getId());
						favoriteCount++;
						System.out.println(favoriteCount + " Favorited "
								+ status.getText());
						if (favoriteCount > FAVORITE_QUEANTITY) {
							return;
						}
						Thread.sleep(timeToFavorite * 1000);

					} catch (Exception e) {
						e.printStackTrace();
						//Thread.sleep(60* 8 * 1000);
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void followWhoFollowMe(int maxToFollow) {
		try {

			int timeToFollow = 2;
			int timeExtra = 3;
			int follow = 0;
			IDs idList = twitter.getFollowersIDs(-1);

			ArrayList<Long> followerList = new ArrayList<>();

			do {
				// System.out.println("aca");
				for (long id : idList.getIDs()) {
					// System.out.println("id : "+id);
					followerList.add(id);
				}
			} while (idList.hasNext());
			System.out.println("Users who follow me " + followerList.size());

			ArrayList<Long> userIFollowList = getUserIFollowList();

			for (Long id : userIFollowList) {

				if (followerList.remove(id)) {

				}
			}
			System.out.println("Users who follow me but I dont Follow back"
					+ followerList.size());

			// follow
			for (Long id : followerList) {
				try {

					// Relationship rs = twitter.showFriendship(twitter.getId(),
					// id);

					follow++;
					if (follow > maxToFollow) {
						System.out.println("Followed " + follow);
						return;
					}
					System.out.println("number user cheked " + follow);

					// if (rs.isSourceFollowingTarget()) {
					// // System.out.println("ya sigo a "+user.getName());
					// continue;
					// }

					twitter.createFriendship(id.longValue());
					controller.insertFollower(new Follower(twitter.getId(), id
							.longValue(), "unknow", ManageDate
							.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS)));

					System.out.println(follow + " Follow " + id);
					Thread.sleep((timeToFollow + getRandomMax(1, timeExtra)) * 1000);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Long> getUserIFollowList() {
		try {

			IDs idList = twitter.getFriendsIDs(-1);
			ArrayList<Long> followerList = new ArrayList<>();

			do {
				// System.out.println("aca");
				for (long id : idList.getIDs()) {
					// System.out.println("id : "+id);
					followerList.add(id);
				}
			} while (idList.hasNext());

			return followerList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Long> getFollowerList() {
		try {

			IDs idList = twitter.getFollowersIDs(-1);
			ArrayList<Long> followerList = new ArrayList<>();

			do {
				// System.out.println("aca");
				for (long id : idList.getIDs()) {
					// System.out.println("id : "+id);
					followerList.add(id);
				}
			} while (idList.hasNext());

			return followerList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void unfollowWhoDontFollowMeInDB() {
		try {

			int timeToUnFollow = 12;
			int follow = 0;

			ArrayList<Long> userIFollowList = getUserIFollowList();
			ArrayList<Long> followerList = getFollowerList();

			System.out.println("Users who I follow " + userIFollowList.size());
			System.out.println("Users who follow me " + followerList.size());

			for (Long id : followerList) {
				userIFollowList.remove(id);
			}

			System.out.println("Total Users who doesnt follow me back "
					+ userIFollowList.size());

			ArrayList<com.mgl.twiitermanager.model.Follower> list = controller
					.loadUserIfollow();
			for (com.mgl.twiitermanager.model.Follower follower : list) {
				userIFollowList.remove(follower.getIdFollower());
			}

			System.out
					.println("New List of user to unfollow because they dont follow me "
							+ userIFollowList.size());
			// unfollow
			int i = 0;
			for (Long id : userIFollowList) {
				try {

					if (i >= 200) {
						return;
					}
					follow++;
					twitter.destroyFriendship(id.longValue());

					System.out.println(follow + " UnFollow " + id);
					Thread.sleep(timeToUnFollow * 1000);
					i++;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getRandomMax(int min, int max) {

		try {

			Double val = min + ((Math.random() * 123456323) % (1 + max - min));
			return val.intValue();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return 1;
	}

	public ArrayList<Long> getLastIdInDays(int number) {
		ArrayList<Long> idUser = new ArrayList<>();
		try {

			ArrayList<Follower> followerList = controller
					.loadIdUserIFollowWithinDaysInDB(number);
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_YEAR, number * -1);

			System.out
					.println("Users in db that I follow "
							+ followerList.size()
							+ " on "
							+ ManageDate.formatDate(c.getTime(),
									ManageDate.YYYY_MM_DD));
			for (Follower f : followerList) {
				if (f.getFollowMe() == null) {
					f.setFollowMe(0L);
				}
				System.out.println("f " + f.getFollowMe());
				if (f.getFollowMe() == 1) {
					continue;
				}
				idUser.add(f.getIdFollower());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return idUser;
	}

	public void unfollowWithInDays(int number, int maxQuantity) {
		try {

			ArrayList<Long> userIfollowList = getLastIdInDays(number);
			ArrayList<Long> followerList = getFollowerList();

			ArrayList<Long> updateUser = new ArrayList<>();
			for (Long id : followerList) {

				if (userIfollowList.remove(id)) {
					updateUser.add(id);
				}
			}

			System.out.println("Total To unfollow And add to block "
					+ userIfollowList.size());
			System.out.println("USER TO UPDATE " + updateUser.size());

			controller.updateFollowers(updateUser);
			int i = 0;
			int timeToUnFollow = 12;// 30 segundos
			int timeExtra = 5;

			for (Long id : userIfollowList) {
				if (i >= maxQuantity) {
					return;
				}
				try {

					try {
						twitter.destroyFriendship(id.longValue());
						controller.updateFollowerBlock(id);
						System.out.println(i + " UnFollow " + id);
					} catch (Exception e) {
						e.printStackTrace();
					}

					

				} catch (Exception e) {
					e.printStackTrace();
				}

				Thread.sleep((timeToUnFollow + getRandomMax(1, timeExtra)) * 1000);
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void messagePeopleInHashTag(int messageConstant, int quantity,
			String hashtag, int quantitySearch) {
		try {

			int messages = 0;
			int timeToSearch = 20;// 20 segundos
			int timeToFollow = 25;// 30 segundos
			int timeExtra = 2;

			while (messages < quantity) {
				try {

					ArrayList<User> userToFollow = getUserListToMessage(
							searchTweet(hashtag, quantitySearch),
							messageConstant);

					for (User user : userToFollow) {
						try {

							if (messages > quantity) {
								return;
							}

							ArrayList<UserMessaged> um = controller
									.loadUserMessaged(
											Long.valueOf(user.getId()),
											Long.valueOf(messageConstant));
							if (um == null || um.isEmpty()) {

								String messageToPost = new String();
								messageToPost = MessageSingleton.getInstance()
										.getMessage(messageConstant);

								if (messageToPost == null
										|| messageToPost.isEmpty()) {
									return;
								}

								messageToPost = "@" + user.getScreenName()
										+ " " + messageToPost;

								twitter.updateStatus(messageToPost);

								controller
										.insertUserMessaged(new UserMessaged(
												user.getId(),
												twitter.getId(),
												Long.valueOf(messageConstant),
												ManageDate
														.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS)));

								messages++;
								System.out.println(messages + " message "
										+ messageToPost);
								Thread.sleep((timeToFollow + getRandomMax(3,
										timeExtra)) * 1000);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void messagePeopleYoutuber(int messageConstant, int quantity,
			String hashtag, int quantitySearch ) {
		try {
			String youtuberName = hashtag.substring(1,hashtag.length());
			int messages = 0;
			int timeToSearch = 20;// 20 segundos
			int timeToFollow = 55;// 30 segundos
			int timeExtra = 20;
			
			int timeToMessage = 0;
			int timeToMessageAux = timeToMessage;

			while (messages < quantity) {
				try {

					ArrayList<User> userToFollow = getUserListToMessage(
							searchTweet(hashtag, quantitySearch),
							messageConstant);
					
					String messageToPost = new String();
					
					for (User user : userToFollow) {
						try {

							if (messages > quantity) {
								return;
							}

							ArrayList<UserMessaged> um = controller
									.loadUserMessaged(
											Long.valueOf(user.getId()),
											Long.valueOf(messageConstant));
							if (um == null || um.isEmpty()) {

								
								messageToPost = MessageSingleton.getInstance()
										.getMessageYoutuber(youtuberName, "@" + user.getScreenName());

								if (messageToPost == null
										|| messageToPost.isEmpty()) {
									return;
								}

								twitter.updateStatus(messageToPost);
								Thread.sleep((timeToFollow + getRandomMax(timeToMessageAux,
										timeExtra)) * 1000);
								/*messageToPost = MessageSingleton.getInstance()
										.getMessageHelpMeShare("@" + user.getScreenName());
								twitter.updateStatus(messageToPost);
								*/
								controller
										.insertUserMessaged(new UserMessaged(
												user.getId(),
												twitter.getId(),
												Long.valueOf(messageConstant),
												ManageDate
														.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS)));

								messages++;
								System.out.println(messages + " message "
										+ messageToPost);
								Thread.sleep((timeToFollow + getRandomMax(timeToMessage,
										timeExtra)) * 1000);
							}

						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("ERROR HERE :"+messageToPost);
							Thread.sleep((timeToFollow + getRandomMax(timeToMessage,
									timeExtra)) * 1000);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<User> getUserListToMessage(ArrayList<Status> searchTweet,
			int messageConstant) {
		ArrayList<User> userRet = new ArrayList<>();
		try {

			for (Status status : searchTweet) {

				User user = status.getUser();

				// Relationship rs = twitter.showFriendship(twitter.getId(),
				// user.getId());

				ArrayList<UserMessaged> userMessagedList = controller
						.loadUserIMessageFollow(Long.valueOf(user.getId()),
								Long.valueOf(messageConstant));

				if (userMessagedList != null && !userMessagedList.isEmpty()) {
					continue;
				}

				// System.out.println("NO sigo a "+user.getName());

				userRet.add(user);
				// twitter.createFriendship(user.getId());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userRet;
	}

	public boolean loginMyAccount(String accountName) {
		try {
			if(twitter == null){
				twitter = TwitterFactory.getSingleton();
				twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
			}
			com.mgl.twiitermanager.model.User user = controller.loadUser(accountName);
			
			if(user == null){
				return false;
			}
			AccessToken accessToken = new AccessToken(user.getAccessToken(),
					user.getTokenSecret());
			
			twitter.setOAuthAccessToken(accessToken);

			return true;
			//verifyMainUserInDatabase();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<UserInfo> loadYoutuberDashList() {
		
		try {
			
			return controller.loadYoutuberDashList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean loginMyAccountYoutuberDash(UserInfo user) {
		try {
			
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(CONSUMER_KEY_YOUTUBER);
			builder.setOAuthConsumerSecret(CONSUMER_SECRET_YOUTUBER);
			Configuration configuration = builder.build();
			TwitterFactory factory = new TwitterFactory(configuration);
			twitter = factory.getInstance();
			
			if(twitter == null){
				//twitter = TwitterFactory.getSingleton();
				//twitter.setOAuthConsumer(CONSUMER_KEY_YOUTUBER, CONSUMER_SECRET_YOUTUBER);
			}else{
				twitter.setOAuthAccessToken(null);
			}

			AccessToken accessToken = new AccessToken(user.getTwitterAccessToken(),
					user.getTwitterSecretToken());
			
			twitter.setOAuthAccessToken(accessToken);

			return true;
			//verifyMainUserInDatabase();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void tweetYoutuberDash(UserInfo u) {
		try {
			
			
			//String message = MessageSingleton.getInstance().getMessageYoutuberDash();
			String message = MessageSingleton.getInstance().getMessageVampireSmasher();
			try {
				twitter.updateStatus(message);
				System.out.println(" tweeting with "+u.getTwitterUserName()+" message "+message);
			} catch (Exception e) {
				System.out.println("Error posting this message "+ message +" USER "+u.getEmail());
				e.printStackTrace();
			}
			
			
			Thread.sleep(5000 + TwitterController.getRandomMax(0, 2)*1000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
