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

	private static GooglePlayGameSingleton instance = null;
	// private GoogleApiClient mGoogleApiClient;

	private boolean isConnected = false;

	private GameHelper gameHelper;

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

	public void disconnect1() {

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
			if (true) {
				return;
			}
			activity.onPauseGame();
			InformativeHUD hud = new InformativeHUD("CONNECTING TO GOOGLE PLAY");
			HUDManagerSingleton.getInstance().addHUD(hud, true);
			activity.onResumeGame();

			// init(activity);
			activity.setmSignInClicked(true);

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
			if(gameHelper==null){
				return;
			}
			gameHelper.onActivityResult(requestCode, resultCode, data);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unlockAchievement(int idTrophy) {
		try {
			
			Games.Achievements.unlock(gameHelper.getApiClient(), TrophySingleton.getInstance().getString(idTrophy));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void increaseAchievement(int idTrophy,int increment) {

		try {
			
			Games.Achievements.increment(gameHelper.getApiClient(), TrophySingleton.getInstance().getString(idTrophy),increment);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void showAchievements() {
		try {
			
			gameHelper.mActivity.startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()),MainDropActivity.REQUEST_ACHIEVEMENT);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showLeaderboard() {
		try {
			
			gameHelper.mActivity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
			        TrophySingleton.LEADERBOARD_GENERAL), MainDropActivity.REQUEST_LEADERBOARD);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void submitScore(int score) {
		try {
			
			Games.Leaderboards.submitScore(gameHelper.getApiClient(), TrophySingleton.LEADERBOARD_GENERAL, Long.valueOf(score));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public boolean isLoggedIn() {
		try {
			if(gameHelper == null ){
				return false;
			}
				
			return gameHelper.isSignedIn();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}	

}
