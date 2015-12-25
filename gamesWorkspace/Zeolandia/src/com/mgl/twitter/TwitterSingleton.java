package com.mgl.twitter;

import org.andengine.entity.text.Text;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import com.google.example.games.basegameutils.GooglePlayGameSingleton;
import com.mgl.base.publicity.ThreadCountActivate;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.base.userinfo.WebServiceSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.hud.DiamantEarnHUD;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.TwitterLoginHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;



import com.mgl.drop.texture.TextureSingleton;
import com.mgl.zeolandia.R;

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

	public static String TWITTER_CONSUMER_KEY = "6ab0MEdH8kEtsQsSjpDTpx1cv";
	public static String TWITTER_CONSUMER_SECRET = "0hy0kJMtW4ExP7HbeJOmCqAclYQsJVwinDbtqSEhkyk8lSMdSf";

	public static String TWITTER_CALLBACK = "https://api.twitter.com/oauth2/token";
	public static String TWITTER_OAUTH_VERIFIER = "oauth_verifier";

	// preferences
	
	private static final String PREF_NAME = "sample_twitter_pref";
	private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	private static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";
	private static final String PREF_USER_NAME = "twitter_user_name";
	

	public static final int WEBVIEW_REQUEST_CODE = 111;

	private Twitter twitter = TwitterFactory.getSingleton();
	private RequestToken requestToken;

	// Shared Preferences
	private static SharedPreferences mSharedPreferences;
	
	// Internet Connection detector
	private ConnectionDetector cd;

	private ThreadCountActivate threadCountLogin;
	
	private TwitterSingleton() {
		try {

			threadCountLogin = new ThreadCountActivate(180L, true);
			
			//mSharedPreferences = SceneManagerSingleton.getInstance().getActivity().getSharedPreferences(
			//		TwitterSingleton.PREF_NAME, 0);
			
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

	public void onCreateTwitter(Activity activity) {
		try {

			/* Enabling strict mode */
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			
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
				

			} else {

				Uri uri = activity.getIntent().getData();
				if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK)) {

					String verifier = uri
							.getQueryParameter(TWITTER_OAUTH_VERIFIER);
					try {

						/* Getting oAuth authentication token */
						AccessToken accessToken = twitter.getOAuthAccessToken(
								requestToken, verifier);

						/* Getting user id form access token */
						long userID = accessToken.getUserId();
						final User user = twitter.showUser(userID);
						final String username = user.getName();

						/* save updated token */
						saveTwitterInfo(accessToken);

					} catch (Exception e) {
						Log.e("Failed to login Twitter!!", e.getMessage());
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saving user information, after user is authenticated for the first time.
	 * You don't need to show user to login, until user has a valid access toen
	 */
	private void saveTwitterInfo(AccessToken accessToken) {

		long userID = accessToken.getUserId();

		User user;
		try {
			user = twitter.showUser(userID);

			String username = user.getName();
			

			/* Storing oAuth tokens to shared preferences */
			Editor e = mSharedPreferences.edit();
			e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
			e.putString(PREF_KEY_OAUTH_SECRET, accessToken.getTokenSecret());
			e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
			e.putString(PREF_USER_NAME, username);
			e.commit();
			
			UserInfoSingleton.getInstance().increaseMoney(5000);
			DiamantEarnHUD hud = new DiamantEarnHUD(5000+"");
			HUDManagerSingleton.getInstance().removeAndReplaceHud();
			HUDManagerSingleton.getInstance().addHUD(hud, true);
			
			SceneManagerSingleton.getInstance().sendGoogleTrack("Login with twitter");
			WebServiceSingleton.getInstance().sendDataTwitterYoutube();
			
		} catch (TwitterException e1) {
			e1.printStackTrace();
		}
	}

	public void loginToTwitter() {
		try {

			boolean isLoggedIn = mSharedPreferences.getBoolean(
					PREF_KEY_TWITTER_LOGIN, false);

			if (!isLoggedIn) {
				final ConfigurationBuilder builder = new ConfigurationBuilder();
				builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
				builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);

				final Configuration configuration = builder.build();
				final TwitterFactory factory = new TwitterFactory(configuration);
				twitter = factory.getInstance();

				try {
					requestToken = twitter
							.getOAuthRequestToken(TWITTER_CALLBACK);

					/**
					 * Loading twitter login page on webview for authorization
					 * Once authorized, results are received at onActivityResult
					 * */
					final Intent intent = new Intent(SceneManagerSingleton
							.getInstance().getActivity(), WebViewActivity.class);
					intent.putExtra(WebViewActivity.EXTRA_URL,
							requestToken.getAuthenticationURL());
					SceneManagerSingleton
							.getInstance()
							.getActivity()
							.startActivityForResult(intent,
									WEBVIEW_REQUEST_CODE);

				} catch (TwitterException e) {
					e.printStackTrace();
				}
			} else {
				HUDManagerSingleton.getInstance().addHUD(
						new InformativeHUD("Already login to twitter"),
						true);
				SceneManagerSingleton.getInstance().sendGoogleTrack("Already Login twitter");
				// already login

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {

			if (requestCode == WEBVIEW_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
				String verifier = data.getExtras().getString(
						TWITTER_OAUTH_VERIFIER);
				try {
					
					
					AccessToken accessToken = twitter.getOAuthAccessToken(
							requestToken, verifier);

					
					long userID = accessToken.getUserId();
					final User user = twitter.showUser(userID);
					String username = user.getName();

					saveTwitterInfo(accessToken);


				} catch (Exception e) {
					Log.e("Twitter Login Failed", e.getMessage());
					HUDManagerSingleton.getInstance().addHUD(
							new InformativeHUD("Problem login with twitter"),
							true);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isLoggedIn(){
		try {
			
			return mSharedPreferences.getBoolean(
					TwitterSingleton.PREF_KEY_TWITTER_LOGIN, false);
			
		} catch (Exception e) {

		}
		return false;
	}
	
	public String getAccessToken(){
		try {
			
			return twitter.getOAuthAccessToken().getToken();
			
			
		} catch (Exception e) {

		}
		return "";
	}

	public String getAccessSecret(){
		try {
			
			return twitter.getOAuthAccessToken().getTokenSecret();
			
			
		} catch (Exception e) {

		}
		return "";
	}
	
	public String getUserName(){
		try {
			
			return twitter.getOAuthAccessToken().getScreenName();
			
			
		} catch (Exception e) {

		}
		return "";
	}

	
	public void postYoutuberTweet(float score) {
		try {
			
			
			SceneManagerSingleton.getInstance().sendGoogleTrack("Posting message on twitter");
			String message = new String();
			message = getTwitterShareMessage(Float.valueOf(score).intValue())+" "+SceneManagerSingleton.getInstance().getActivity().getString(R.string.YOUTUBER_DASH_URL);
			
			//message = "He Ganado "+score +" puntos jugando con "+getYoutuberTwitter(UserInfoSingleton.getInstance().getPlayerSelected())+
			//		" al Youtuber Dash "+"https://play.google.com/store/apps/details?id=com.mgl.smasher";
			
			twitter.updateStatus(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getYoutuberTwitter(int playerSelected) {
		try {

			switch (playerSelected) {
			case GameConstants.PLAYER_A:

				return "@aLexBY11";
			case GameConstants.PLAYER_B:

				return "@eldiariodedross";
			case GameConstants.PLAYER_C:

				return "@fernanfloo";

			case GameConstants.PLAYER_D:
				return "@LuzuGames";
			
			case GameConstants.PLAYER_E:

				return "@Rubiu5";

			case GameConstants.PLAYER_F:

				return "@bysTaXx";
			case GameConstants.PLAYER_G:

				return "@vegetta777";
			case GameConstants.PLAYER_H:

				return "@WillyrexYT";
			case GameConstants.PLAYER_I:

				return "@_OtraVezLunes";
			case GameConstants.PLAYER_J:

				return "@mangelrogel";
			case GameConstants.PLAYER_K:

				return "@GermanGarmendia";


			default:
				break;
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return "@elrubiusomg";
	}


	public void showTwitterRewardLogin(){
		try {
			
			if (!threadCountLogin.isShow() || isLoggedIn()) {
				return;
			}
			
			
			HUDManagerSingleton.getInstance().addHUD(new TwitterLoginHUD(), true);
			
			
			threadCountLogin.setShow(false);
			threadCountLogin.setTimeToSleep(60*5L);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void showTwitterRewardFree(){
		try {
			
			
			HUDManagerSingleton.getInstance().addHUD(new TwitterLoginHUD(), true);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getTwitterShareMessage(int score) {
		try {
			String message = new String();
			MainDropActivity activity = SceneManagerSingleton.getInstance().getActivity();
			
			message = "\""+activity.getString(R.string.IHAVE_WIN) +" "+score+" "+activity.getString(R.string.POINTS_PLAYING)+" "+ TwitterSingleton.getYoutuberTwitter(UserInfoSingleton.getInstance().getPlayerSelected()) +" "+activity.getString(R.string.TO_YOUTUBER_DASH)+"\""; 
			
			return message;
			
		} catch (Exception e) {
			
		}
		return "";
	}
	
}
