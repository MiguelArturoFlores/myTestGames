package com.google.example.games.basegameutils;

import android.content.Intent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class GooglePlayGameSingleton {

	private static final int REQUEST_ACHIEVEMENT = 9002;
	private static final int REQUEST_LEADERBOARD = 9003;

	private static GooglePlayGameSingleton instance = null;
	// private GoogleApiClient mGoogleApiClient;
	private boolean isConnected = false;
	private GameHelper gameHelper;
	private boolean hasInit = false;

	// constants
	public static final String LEADERBOARD_GENERAL = "CgkI5YTQj4cbEAIQDA";

	public static final String ACHIEVEMENT_BASIC_EATER = "CgkI5YTQj4cbEAIQAQ";
	public static final String ACHIEVEMENT_GOOD_EATER = "CgkI5YTQj4cbEAIQAg";
	public static final String ACHIEVEMENT_FANTASTIC_EATER = "CgkI5YTQj4cbEAIQAw";
	public static final String ACHIEVEMENT_SUPER_EATER = "CgkI5YTQj4cbEAIQBA";
	public static final String ACHIEVEMENT_ULTRA_EATER = "CgkI5YTQj4cbEAIQBQ";

	public static final String ACHIEVEMENT_BASIC_SURVIVOR = "CgkI5YTQj4cbEAIQBg";
	public static final String ACHIEVEMENT_GOOD_SURVIVOR = "CgkI5YTQj4cbEAIQBw";
	public static final String ACHIEVEMENT_AMAZING_SURVIVOR = "CgkI5YTQj4cbEAIQCA";
	public static final String ACHIEVEMENT_HARD_TO_KILL = "CgkI5YTQj4cbEAIQCQ";
	public static final String ACHIEVEMENT_INVINCIBLE = "CgkI5YTQj4cbEAIQCg";

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

			if (hasInit) {
				return;
			}

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
								hasInit = true;

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

	public void increaseAchievement(int idTrophy, int increment) {

		try {

			// Games.Achievements.increment(gameHelper.getApiClient(),
			// TrophySingleton.getInstance().getString(idTrophy),increment);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void showAchievements() {
		try {

			gameHelper.mActivity.startActivityForResult(Games.Achievements
					.getAchievementsIntent(gameHelper.getApiClient()),
					REQUEST_ACHIEVEMENT);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showLeaderboard() {
		try {

			gameHelper.mActivity.startActivityForResult(
					Games.Leaderboards.getLeaderboardIntent(
							gameHelper.getApiClient(), LEADERBOARD_GENERAL),
					REQUEST_LEADERBOARD);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void submitScore(int score) {
		try {

			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					LEADERBOARD_GENERAL, Long.valueOf(score));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void submitScore(int score, String leaderboardName) {
		try {

			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					leaderboardName, Long.valueOf(score));

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

			gameHelper.mActivity.startActivityForResult(
					Games.Leaderboards.getLeaderboardIntent(
							gameHelper.getApiClient(), leaderboarName),
					REQUEST_LEADERBOARD);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void validateAchievement(LevelController levelController) {
		try {

			int val = levelController.getScoreEntity().getScore();

			if (val > 50) {
				unlockAchievement(ACHIEVEMENT_BASIC_SURVIVOR);
			}
			if (val > 100) {
				unlockAchievement(ACHIEVEMENT_GOOD_SURVIVOR);
			}

			if (val > 1000) {
				unlockAchievement(ACHIEVEMENT_AMAZING_SURVIVOR);
			}

			if (val > 2500) {
				unlockAchievement(ACHIEVEMENT_HARD_TO_KILL);
				
			}
			if (val > 5000) {
				unlockAchievement(ACHIEVEMENT_INVINCIBLE);
				
			}
			
			val = UserInfoSingleton.getInstance().getMoney();

			if(val> 10){
				unlockAchievement(ACHIEVEMENT_BASIC_EATER);
			}
			if(val> 100){
				unlockAchievement(ACHIEVEMENT_GOOD_EATER);
			}
			if(val> 1000){
				unlockAchievement(ACHIEVEMENT_FANTASTIC_EATER);
			}
			if(val> 5000){
				unlockAchievement(ACHIEVEMENT_SUPER_EATER);
			}
			if(val> 10000){
				unlockAchievement(ACHIEVEMENT_ULTRA_EATER);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
