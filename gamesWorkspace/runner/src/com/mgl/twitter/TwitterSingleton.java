package com.mgl.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import io.fabric.sdk.android.Fabric;

import com.mgl.base.MyFactory;
import com.mgl.base.publicity.ThreadCountActivate;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.base.userinfo.WebServiceSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.hud.DiamantEarnHUD;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.TwitterLoginHUD;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.runner.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

public class TwitterSingleton {

	// USE this
	// http://javatechig.com/android/how-to-integrate-twitter-in-android-application
	// http://javatechig.com/android/using-facebook-sdk-in-android-example

	private static TwitterSingleton instance = null;

	public static String TWITTER_CONSUMER_KEY = "aOexfP13jYf5KllwbZXzWqXpG";
	public static String TWITTER_CONSUMER_SECRET = "D6xFJvVQ4fH26O8A8tXJ8DDUxWYzGmTuUG1tgg12hNAFvWrPJZ";
	
	//public static String TWITTER_CONSUMER_KEY = "Hv8LaCFpfCbDUwmpAapnY3LhF";
	//public static String TWITTER_CONSUMER_SECRET = "mlTFVU2iedFw99nU5OIpLNeTWgMIw5A37WBq7Qya49Iw9Hpbam";
	
	public static String TWITTER_CALLBACK = "https://api.twitter.com/oauth2/token";
	public static String TWITTER_OAUTH_VERIFIER = "oauth_verifier";

	// preferences

	private static final String PREF_NAME = "sample_twitter_pref";
	private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	private static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";
	private static final String PREF_USER_NAME = "twitter_user_name";

	public static final int WEBVIEW_REQUEST_CODE = 111;

	// Shared Preferences
	private static SharedPreferences mSharedPreferences;

	// Internet Connection detector
	private ConnectionDetector cd;

	private ThreadCountActivate threadCountLogin;
	
	//twitter4j
	private Twitter twitter = TwitterFactory.getSingleton();
	
	//SSO
	private TwitterAuthConfig twitterAuthConfig;
	private TwitterAuthClient twitterAuthClient;

	
	
	private TwitterSingleton() {
		try {

			threadCountLogin = new ThreadCountActivate(180L, true);

			
			// mSharedPreferences =
			// SceneManagerSingleton.getInstance().getActivity().getSharedPreferences(
			// TwitterSingleton.PREF_NAME, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static TwitterSingleton getInstance() {
		try {

			if (instance == null) {
				instance = new TwitterSingleton();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return instance;
	}

	public static void setInstance(TwitterSingleton instance) {
		TwitterSingleton.instance = instance;
	}

	public void loginToTwitter() {
		try {

			boolean isLoggedIn = mSharedPreferences.getBoolean(
					PREF_KEY_TWITTER_LOGIN, false);

			if(isLoggedIn){
				HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Already Logged"), true);
				return;
			}
			
			twitterAuthConfig = new TwitterAuthConfig(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
			Fabric.with(SceneManagerSingleton.getInstance().getActivity(),new com.twitter.sdk.android.Twitter(twitterAuthConfig));
			twitterAuthClient = new TwitterAuthClient();
			
			twitterAuthClient.authorize(SceneManagerSingleton.getInstance().getActivity(), new com.twitter.sdk.android.core.Callback<TwitterSession>() {

                @Override
                public void success(Result<TwitterSession> twitterSessionResult) {
                
                	try {
						
                	TwitterSession session = com.twitter.sdk.android.Twitter.getSessionManager().getActiveSession();
                	TwitterAuthToken authToken = session.getAuthToken();
                	
                	String token = authToken.token;
                	String secret = authToken.secret;
                	String name = session.getUserName();
                	
                	saveTwitterInfoFabric(session.getUserName(), session.getUserId(), token, secret);
    
                	UserInfoSingleton.getInstance().setPlayerSelect(GameConstants.PLAYER_C);
    				SceneManagerSingleton.getInstance().setCurrentScene(AllScenes.POWER_UP);
                	
                	} catch (Exception e) {

					}
    
                }

                @Override
                public void failure(com.twitter.sdk.android.core.TwitterException e) {
                	HUDManagerSingleton.getInstance().removeAndReplaceHud();
                    HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Fail Login Twitter"), true);
                    e.printStackTrace();
                }
     });
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveTwitterInfoFabric(String username,Long userID,String accessToken, String accessSecret) {

		try {
	
			/* Storing oAuth tokens to shared preferences */
			Editor e = mSharedPreferences.edit();
			e.putString(PREF_KEY_OAUTH_TOKEN, accessToken);
			e.putString(PREF_KEY_OAUTH_SECRET, accessSecret);
			e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
			e.putString(PREF_USER_NAME, username);
			e.commit();

			//UserInfoSingleton.getInstance().increasePowerD(10);
			HUDManagerSingleton.getInstance().removeAndReplaceHud();
			

			SceneManagerSingleton.getInstance().sendGoogleTrack(
					"Login with twitter");
			WebServiceSingleton.getInstance().sendDataTwitterYoutube();
			
			
			twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
			AccessToken accessToken4j = new AccessToken(mSharedPreferences.getString(
					TwitterSingleton.PREF_KEY_OAUTH_TOKEN, ""),
					mSharedPreferences.getString(
							TwitterSingleton.PREF_KEY_OAUTH_SECRET, ""));
			twitter.setOAuthAccessToken(accessToken4j);

		} catch (com.twitter.sdk.android.core.TwitterException e1) {
			e1.printStackTrace();
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {

			twitterAuthClient.onActivityResult(requestCode, resultCode, data);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isLoggedIn() {
		try {

			return mSharedPreferences.getBoolean(
					TwitterSingleton.PREF_KEY_TWITTER_LOGIN, false);

		} catch (Exception e) {

		}
		return false;
	}

	public String getAccessToken() {
		try {

			//return twitter.getOAuthAccessToken().getToken();
			TwitterSession session = com.twitter.sdk.android.Twitter.getSessionManager().getActiveSession();
			return session.getAuthToken().token;

		} catch (Exception e) {

		}
		return "";
	}

	public String getAccessSecret() {
		try {

			//return twitter.getOAuthAccessToken().getTokenSecret();
			TwitterSession session = com.twitter.sdk.android.Twitter.getSessionManager().getActiveSession();
			return session.getAuthToken().secret;
			
		} catch (Exception e) {

		}
		return "";
	}

	public String getUserName() {
		try {

			//return twitter.getOAuthAccessToken().getScreenName();
			TwitterSession session = com.twitter.sdk.android.Twitter.getSessionManager().getActiveSession();
			return session.getUserName();

		} catch (Exception e) {

		}
		return "";
	}

	public void postYoutuberTweet(float score) {
		try {

			SceneManagerSingleton.getInstance().sendGoogleTrack(
					"Posting message on twitter");
			String message = new String();
			message = getTwitterShareMessage(Float.valueOf(score).intValue())
					+ " "
					+ SceneManagerSingleton.getInstance().getActivity()
							.getString(R.string.GAME_URL);

			// message = "He Ganado "+score
			// +" puntos jugando con "+getYoutuberTwitter(UserInfoSingleton.getInstance().getPlayerSelected())+
			// " al Youtuber Dash "+"https://play.google.com/store/apps/details?id=com.mgl.smasher";
			
			if(true){
				twitter.updateStatus(message);
			}
			
			TwitterSession session = com.twitter.sdk.android.Twitter.getSessionManager().getActiveSession();
			TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
			StatusesService statusesService = twitterApiClient.getStatusesService();
			statusesService.update(message, null, false, null, null, null, null,null , new Callback<Tweet>() {

				@Override
				public void failure(TwitterException arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void success(Result<Tweet> arg0) {
					// TODO Auto-generated method stub
					
				}
			
			});
			
			//twitter.updateStatus(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void showTwitterRewardLogin() {
		try {

			if (!threadCountLogin.isShow() || isLoggedIn()) {
				return;
			}

			HUDManagerSingleton.getInstance().addHUD(new TwitterLoginHUD(),
					true);

			threadCountLogin.setShow(false);
			threadCountLogin.setTimeToSleep(60 * 5L);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showTwitterRewardFree() {
		try {

			HUDManagerSingleton.getInstance().addHUD(new TwitterLoginHUD(),
					true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getTwitterShareMessage(int score) {
		try {
			String message = new String();
			MainDropActivity activity = SceneManagerSingleton.getInstance()
					.getActivity();

			message = "Share on twitter";

			return message;

		} catch (Exception e) {

		}
		return "";
	}

	public void onCreateTwitter(Activity activity) {
		try {
			
			mSharedPreferences = activity.getSharedPreferences(
					TwitterSingleton.PREF_NAME, 0);
			boolean isLoggedIn = mSharedPreferences.getBoolean(
					TwitterSingleton.PREF_KEY_TWITTER_LOGIN, false);
			
			if (isLoggedIn) {
				String username = mSharedPreferences.getString(
						TwitterSingleton.PREF_USER_NAME, "");
				
				twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
				AccessToken accessToken = new AccessToken(mSharedPreferences.getString(
						TwitterSingleton.PREF_KEY_OAUTH_TOKEN, ""),
						mSharedPreferences.getString(
								TwitterSingleton.PREF_KEY_OAUTH_SECRET, ""));
				twitter.setOAuthAccessToken(accessToken);
				

			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void postTweet() {
		try {
			
			SceneManagerSingleton.getInstance().sendGoogleTrack(
					"Posting message on twitter");
			String message = new String();
			message = "Hey Friends Try this #vampire #game its Awsome!" + SceneManagerSingleton.getInstance().getActivity()
							.getString(R.string.GAME_URL);

			// message = "He Ganado "+score
			// +" puntos jugando con "+getYoutuberTwitter(UserInfoSingleton.getInstance().getPlayerSelected())+
			// " al Youtuber Dash "+"https://play.google.com/store/apps/details?id=com.mgl.smasher";
			
			if(true){
				twitter.updateStatus(message);
			}
			
			TwitterSession session = com.twitter.sdk.android.Twitter.getSessionManager().getActiveSession();
			TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
			StatusesService statusesService = twitterApiClient.getStatusesService();
			statusesService.update(message, null, false, null, null, null, null,null , new Callback<Tweet>() {

				@Override
				public void failure(TwitterException arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void success(Result<Tweet> arg0) {
					// TODO Auto-generated method stub
					
				}
			
			});
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
