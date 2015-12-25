package com.google.example.games.basegameutils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.mgl.base.userinfo.TrophySingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.database.model.Trophy;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class GooglePlayGameSingleton {

	private static final int REQUEST_ACHIEVEMENT = 9002;
	
	private static final int REQUEST_LEADERBOARD = 9003;

	private static GooglePlayGameSingleton instance = null;
	// private GoogleApiClient mGoogleApiClient;
	private boolean isConnected = false;
	private GameHelper gameHelper;

	
	//constants
	public static final String LEADERBOARD_GENERAL = "CgkInbTf-9cbEAIQBA";
	
	public static final String LEADERBOARD_ALEXX = "CgkInbTf-9cbEAIQAQ";
	public static final String LEADERBOARD_DROSS = "CgkInbTf-9cbEAIQAg";
	public static final String LEADERBOARD_FERNANFLOO = "CgkInbTf-9cbEAIQAw";
	public static final String LEADERBOARD_LUZU="CgkInbTf-9cbEAIQFg";
	public static final String LEADERBOARD_RUBIUS="CgkInbTf-9cbEAIQEw";
	public static final String LEADERBOARD_STAXX="CgkInbTf-9cbEAIQFQ";
	public static final String LEADERBOARD_VEGETTA777="CgkInbTf-9cbEAIQFA";
	public static final String LEADERBOARD_WILLIREX="CgkInbTf-9cbEAIQGA";
	public static final String LEADERBOARD_ADAM="CgkInbTf-9cbEAIQEg";
	public static final String LEADERBOARD_MANGEL="CgkInbTf-9cbEAIQEQ";
	public static final String LEADERBOARD_GERMAN="CgkInbTf-9cbEAIQFw";
	
	private GooglePlayGameSingleton() {
		try {

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static GooglePlayGameSingleton getInstance() {
		try {

			if (instance == null) {
				instance = new GooglePlayGameSingleton();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	// public GoogleApiClient getmGoogleApiClient() {
	// return mGoogleApiClient;
	// }
	//
	// public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
	// this.mGoogleApiClient = mGoogleApiClient;
	// }

	public void init(MainDropActivity activity) {
		try {

			/*
			 * mGoogleApiClient = new GoogleApiClient.Builder(activity)
			 * .addConnectionCallbacks(activity)
			 * .addOnConnectionFailedListener(activity).addApi(Games.API)
			 * .addScope
			 * (Games.SCOPE_GAMES).setViewForPopups(activity.findViewById
			 * (android.R.id.content)) // add other APIs and scopes here as
			 * needed .build();
			 */

			gameHelper = new GameHelper(activity, GameHelper.CLIENT_GAMES);

			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					try {

						gameHelper.setup(new GameHelperListener() {

							@Override
							public void onSignInSucceeded() {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSignInFailed() {
								// TODO Auto-generated method stub

							}
						});

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			// gameHelper.

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void connect1() {
		try {

			// mGoogleApiClient.connect();
			gameHelper.connect();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void disconnect2() {

		try {

			// mGoogleApiClient.disconnect();

			gameHelper.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login(MainDropActivity activity) {
		try {

			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					try {
						// init(SceneManagerSingleton.getInstance().getActivity());
						gameHelper.beginUserInitiatedSignIn();

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});

			// activity.onActivityResult(MainDropActivity.RC_SIGN_IN,
			// resultCode, data);
	
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logout(MainDropActivity activity) {
		try {

			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					try {
						gameHelper.signOut();
						// Games.signOut(mGoogleApiClient);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});

			// activity.onContentChanged();
			activity.setmSignInClicked(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GameHelper getGameHelper() {
		return gameHelper;
	}

	public void setGameHelper(GameHelper gameHelper) {
		this.gameHelper = gameHelper;
	}

	public void onStop() {
		try {

			gameHelper.onStop();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onStart(MainDropActivity activity) {
		try {

			gameHelper.onStart(activity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onConnectionSuspended(int i) {
		try {
			
			gameHelper.onConnectionSuspended(i);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onConnectionFailed(ConnectionResult connectionResult) {
		try {
			
			gameHelper.onConnectionFailed(connectionResult);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			
			gameHelper.onActivityResult(requestCode, resultCode, data);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unlockAchievement(String idTrophy) {
		try {
			
			Games.Achievements.unlock(gameHelper.getApiClient(), (idTrophy));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void increaseAchievement(int idTrophy,int increment) {

		try {
			
			//Games.Achievements.increment(gameHelper.getApiClient(), TrophySingleton.getInstance().getString(idTrophy),increment);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void showAchievements() {
		try {
			
			gameHelper.mActivity.startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()),REQUEST_ACHIEVEMENT);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showLeaderboard() {
		try {
			
			gameHelper.mActivity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
			        LEADERBOARD_GENERAL), REQUEST_LEADERBOARD);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void submitScore(int score) {
		try {
			
			Games.Leaderboards.submitScore(gameHelper.getApiClient(), LEADERBOARD_GENERAL, Long.valueOf(score));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void submitScore(int score,String leaderboardName) {
		try {
			
			Games.Leaderboards.submitScore(gameHelper.getApiClient(), leaderboardName, Long.valueOf(score));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public boolean isLoggedIn() {
		try {
			
			return gameHelper.isSignedIn();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}

	public void showLeaderboard(String leaderboarName) {
		try {

			gameHelper.mActivity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
					leaderboarName), REQUEST_LEADERBOARD);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

}
