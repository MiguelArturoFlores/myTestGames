package com.mgl.base.publicity;

import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.appflood.AFBannerView;
import com.appflood.AppFlood;
import com.appflood.AppFlood.AFRequestDelegate;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.ChartboostDelegate;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.MobileCore.AD_UNITS;
import com.ironsource.mobilcore.MobileCore.LOG_TYPE;
import com.jirbo.adcolony.AdColony;
import com.jirbo.adcolony.AdColonyV4VCAd;
import com.jirbo.adcolony.AdColonyV4VCListener;
import com.jirbo.adcolony.AdColonyV4VCReward;
import com.jirbo.adcolony.AdColonyVideoAd;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GamePurchaseConstant;
import com.mgl.drop.game.hud.DiamantEarnHUD;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.RewardVideoHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;
import com.startapp.android.publish.banner.Banner;
import com.startapp.android.publish.banner.bannerstandard.BannerStandard;
import com.startapp.android.publish.nativead.StartAppNativeAd;
import com.startapp.android.publish.splash.SplashConfig;

public class PublicityManagerSingleton {

	private static PublicityManagerSingleton instance = null;

	private Activity activity;

	// admob
	private String MY_ADMOB_UNIT_ID = "ca-app-pub-9909317453090800/2099318976";
	private String MY_ADMOB_VIDEO = "ca-app-pub-9909317453090800/3754303772";
	private InterstitialAd mInterstitialAd;
	private InterstitialAd admobVideoAdd;

	// adColony
	private String AdColonyAppId = "app12f59c941daf45629b";
	private String AdColonyZoneId = "vzd7df907c35544e83a5";
	private String AdColonyRewardId = "vz8f882323b80f455aaf";
	
	private String AdColonyClientOptions = "version:1.6,store:google";
	private AdColonyV4VCAd adRewardColony;
	private AdColonyVideoAd adVideoColony;

	// add networks
	private Chartboost cb;
	private StartAppAd startAppAd = null;
	private StartAppNativeAd startAppAdNative = null;

	private boolean activeChartboost = true;
	private boolean activeAppFlood = true;
	private boolean activeMobileCore = true;
	private boolean activeStartApp = true;
	private boolean activeadMob = true;
	private boolean activeAdColony = true;

	private static FrameLayout frameLayout;
	private static LayoutParams frameLayoutLayoutParams;

	private ThreadCountActivate thread;
	private ThreadCountActivate threadReward;

	private ChartboostDelegate delegateChartboost;

	private PublicityManagerSingleton() {

		try {

			Log.d("Inicializo el singelton de publicidad",
					"Inicializo el singelton de publicidad");
			// 145
			thread = new ThreadCountActivate(12000l, false);
			thread.start();

			threadReward = new ThreadCountActivate(110l, true);
			threadReward.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initPublicityData() {
		try {

			if (activeChartboost) {
				initChartbostData();
			}
			if (activeAppFlood) {
				initAppFloodData();
			}
			if (activeMobileCore) {
				initMobileCoreData();
			}
			if (activeStartApp) {
				initStartAppData();
			}
			if (activeadMob) {
				initAdMobData();
			}
			if (activeAdColony) {
				initAdColonyData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initAdColonyData() {
		try {

			AdColony.configure(activity, AdColonyClientOptions, AdColonyAppId,
					AdColonyRewardId, AdColonyZoneId);
			adVideoColony = new AdColonyVideoAd();
			adRewardColony = new AdColonyV4VCAd(AdColonyRewardId);

			AdColony.addV4VCListener(new AdColonyV4VCListener() {

				@Override
				public void onAdColonyV4VCReward(AdColonyV4VCReward reward) {

					try {
						UserInfoSingleton
								.getInstance()
								.increaseMoney(
										GamePurchaseConstant.SHOW_REWARDED_VIDEO_COLONY);
						// UserInfoSingleton.getInstance().setUserLikeUs();
						DiamantEarnHUD hud = new DiamantEarnHUD(
								GamePurchaseConstant.SHOW_REWARDED_VIDEO_COLONY
										+ "");
						HUDManagerSingleton.getInstance().addHUD(hud, true);
						SceneManagerSingleton.getInstance().sendGoogleTrack(
								"View Video Currency adColony");

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initAdMobData() {
		try {

			// Crear adView.
			mInterstitialAd = new InterstitialAd(activity);
			mInterstitialAd.setAdUnitId(MY_ADMOB_UNIT_ID);
			requestNewInterstitialAdmob();
			
			admobVideoAdd = new InterstitialAd(activity);
			admobVideoAdd.setAdUnitId(MY_ADMOB_VIDEO);
			requestNewAdmobVideo();

			admobVideoAdd.setAdListener(new AdListener() {

				@Override
				public void onAdClosed() {
					// TODO Auto-generated method stub
					super.onAdClosed();
					try {

						UserInfoSingleton.getInstance().increaseMoney(
								GamePurchaseConstant.SHOW_REWARDED_VIDEO_ADMOB);
						// UserInfoSingleton.getInstance().setUserLikeUs();
						DiamantEarnHUD hud = new DiamantEarnHUD(
								GamePurchaseConstant.SHOW_REWARDED_VIDEO_ADMOB
										+ "");
						HUDManagerSingleton.getInstance().addHUD(hud, true);
						SceneManagerSingleton.getInstance().sendGoogleTrack(
								"View Video Currency ADMOB");

					} catch (Exception e) {

						e.printStackTrace();
					}
				}

			});

			// MainDropActivity act = (MainDropActivity) activity;
			// frameLayout.addView(mInterstitialAd);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void requestNewInterstitialAdmob() {
		try {

			AdRequest adRequest = new AdRequest.Builder().build();

			mInterstitialAd.loadAd(adRequest);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void requestNewAdmobVideo() {
		try {

			AdRequest adRequest = new AdRequest.Builder().build();

			admobVideoAdd.loadAd(adRequest);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initStartAppData() {
		try {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					StartAppSDK.init(activity, "108072614", "204720947", true);
					SplashConfig.getDefaultSplashConfig().setOrientation(
							SplashConfig.Orientation.LANDSCAPE);
					StartAppNativeAd nat = new StartAppNativeAd(activity);

					startAppAd = new StartAppAd(activity);
					startAppAdNative = new StartAppNativeAd(activity);

					startAppAd.loadAd();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initMobileCoreData() {
		try {

			// activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			if (!activeMobileCore) {
				return;
			}

			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {

					String code = "7D9BLHT91TL76K8BKVIPU7NGAEC4J";
					MobileCore.init(activity, code, LOG_TYPE.PRODUCTION,
							AD_UNITS.OFFERWALL);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initAppFloodData() {
		try {

			if (!activeAppFlood) {
				return;
			}
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					AppFlood.initialize(activity, "keBvbLKUEZFWk5kn",
							"157wFHJh609cL54efb9ef", AppFlood.AD_ALL);
				}
			});

			// preloadAppFlood();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void preloadAppFlood() {
		try {

			AppFlood.preload(AppFlood.BANNER_INTERSTITIAL
					| AppFlood.AD_INTERSTITIAL | AppFlood.AD_FULLSCREEN
					| AppFlood.BANNER_SMALL | AppFlood.AD_BANNER,
					new AFRequestDelegate() {
						@Override
						public void onFinish(JSONObject arg0) {

							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {

								}
							});
						}
					});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initChartbostData() {
		try {
			if (!activeChartboost) {
				return;
			}

			delegateChartboost = new ChartboostDelegate() {

				@Override
				public void didCompleteRewardedVideo(String location, int reward) {
					// TODO Auto-generated method stub
					super.didCompleteRewardedVideo(location, reward);

					try {

						UserInfoSingleton.getInstance().increaseMoney(
								GamePurchaseConstant.SHOW_REWARDED_VIDEO);
						// UserInfoSingleton.getInstance().setUserLikeUs();
						DiamantEarnHUD hud = new DiamantEarnHUD(
								GamePurchaseConstant.SHOW_REWARDED_VIDEO + "");
						HUDManagerSingleton.getInstance().addHUD(hud, true);
						SceneManagerSingleton.getInstance().sendGoogleTrack(
								"View Video Currency chartboost");

					} catch (Exception e) {

						e.printStackTrace();
					}

				}

			};

			String appId = "555bba7cc909a60f1b210f01";
			String appSignature = "803b8817ca6218a560fd112db6f1418c0f27cb41";
			Chartboost.startWithAppId(activity, appId, appSignature);

			Chartboost.setImpressionsUseActivities(true);

			Chartboost.onCreate(activity);
			Chartboost.onStart(activity);

			Chartboost.setDelegate(delegateChartboost);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static PublicityManagerSingleton getInstance() {
		if (instance == null) {
			instance = new PublicityManagerSingleton();
		}
		return instance;
	}

	public void setInstance(PublicityManagerSingleton instance) {
		PublicityManagerSingleton.instance = instance;
	}

	public void initPublicityThread() {
		try {
			if (!activeChartboost) {
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void showIntersitialAux() {
		try {

			Log.d("ADMOBBBBB ", "INTERSITIAL ADMOOOOOOBBBBBB");
			mInterstitialAd.show();
			requestNewInterstitialAdmob();

			if (UserInfoSingleton.getInstance().getPublicity() == 0) {
				return;
			}

			Double val = (Math.random() * 19843041) % 100;

			if (thread == null) {
				Log.d("NULL THREAD", "Thread pubicity was null");
				return;
			}
			if (!thread.isShow()) {
				return;
			}

			// showMobileCoreIntersitial();
			// showChartboostIntersitial();
			// showStartAppIntersitial();
			// showAppFloodIntersitial();

			if (val.intValue() <= 30) {
				showChartboostIntersitial();
			} else if (val.intValue() >= 30 && val.intValue() < 45) {
				showAppFloodIntersitial();
			} else if (val.intValue() >= 45 && val.intValue() < 60) {
				showMobileCoreIntersitial();
			} else if (val.intValue() >= 60 && val.intValue() <= 100) {
				showStartAppIntersitial();
			}

			thread.setShow(false);
			//
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showAdColonyVideo() {
		try {

			boolean ret = adVideoColony.shown();
			adVideoColony = new AdColonyVideoAd();
			adVideoColony.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showAdColonyRewardVideo() {
		try {

			adRewardColony = new AdColonyV4VCAd(AdColonyRewardId);

			adRewardColony.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showAdMobIntersitial() {
		try {

			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					try {
						Log.d("ADMOBBBBB ", "INTERSITIAL ADMOOOOOOBBBBBB");
						mInterstitialAd.show();
						requestNewInterstitialAdmob();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showIntersitial() {
		try {

			if (UserInfoSingleton.getInstance().getPublicity() == 0) {
				return;
			}

			Double val = (Math.random() * 1984304154) % 100;

			if (thread == null) {
				Log.d("NULL THREAD", "Thread pubicity was null");
				return;
			}
			if (!thread.isShow()) {
				return;
			}

			if (val.intValue() < 10) {
				if (showChartboostIntersitial()) {
				} else {
					showIntersitial();
				}
			} else if (val >= 10 && val <= 30) {
				showAdColonyVideo();
			} else if (val >= 30 && val < 80) {
				showStartAppIntersitial();
			} else {
				showAdMobIntersitial();
			}

			thread.setShow(false);
			//
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean showStartAppIntersitial() {
		try {
			if (startAppAdNative == null) {

				return false;
			}

			activity.runOnUiThread(new Runnable() {
				public void run() {
					startAppAd.loadAd();
					startAppAd.showSplash(activity, null);

				}
			});

			return startAppAd.showAd();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	private void showMobileCoreIntersitial() {
		try {
			if (!activeMobileCore) {
				return;
			}
			activity.runOnUiThread(new Runnable() {
				public void run() {

					if (MobileCore.isOfferwallReady()) {

						MobileCore.showOfferWall(activity, null);
						// MobileCore.showStickee(activity);

						MobileCore.refreshOffers();

					} else {
						Log.d("Mobile Core", "No estaba listo");
					}

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showAppFloodIntersitial() {
		try {

			if (!activeAppFlood) {
				return;
			}
			activity.runOnUiThread(new Runnable() {
				public void run() {
					// your code here
					// AppFlood.showInterstitial(activity);
					// AppFlood.showFullScreen(activity);
					// AppFlood.showList(activity, 1);
					AppFlood.showPanel(activity, 1);// puede ser
					// AppFlood.showVideo(activity, null);
					// AppFlood.showBanner(activity, 1, 1);
					preloadAppFlood();
				}
			});

			// AppFlood.showFullScreen(activity);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean showChartboostIntersitial() {
		try {
			if (!activeChartboost) {
				return false;
			}

			if (Chartboost.hasInterstitial(CBLocation.LOCATION_DEFAULT)) {

				Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
				Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
				return true;
			} else {
				Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Chartboost getCb() {
		return cb;
	}

	public void setCb(Chartboost cb) {
		this.cb = cb;
	}


	public StartAppAd getStartAppAd() {
		return startAppAd;
	}

	public void setStartAppAd(StartAppAd startAppAd) {
		this.startAppAd = startAppAd;
	}

	public boolean isActiveChartboost() {
		return activeChartboost;
	}

	public void setActiveChartboost(boolean activeChartboost) {
		this.activeChartboost = activeChartboost;
	}

	public boolean isActiveAppFlood() {
		return activeAppFlood;
	}

	public void setActiveAppFlood(boolean activeAppFlood) {
		this.activeAppFlood = activeAppFlood;
	}

	public boolean isActiveMobileCore() {
		return activeMobileCore;
	}

	public void setActiveMobileCore(boolean activeMobileCore) {
		this.activeMobileCore = activeMobileCore;
	}

	public boolean isActiveStartApp() {
		return activeStartApp;
	}

	public void setActiveStartApp(boolean activeStartApp) {
		this.activeStartApp = activeStartApp;
	}


	public StartAppNativeAd getStartAppAdNative() {
		return startAppAdNative;
	}

	public void setStartAppAdNative(StartAppNativeAd startAppAdNative) {
		this.startAppAdNative = startAppAdNative;
	}

	public void destroy() {
		try {

			instance = null;

			activity = null;

			cb = null;
			startAppAd = null;
			startAppAdNative = null;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void showMoreGames() {
		try {

			// showChartboostMoreGames();

			showAppFloodMoreGames();

			// showMobileCoreIntersitial();

			// showStartAppIntersitial();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showAppFloodMoreGames() {
		try {

			if (!activeAppFlood) {
				return;
			}

			Log.d("Showing", "More games");
			AppFlood.showList(getActivity(), 1);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void destroyAdds() {
		try {


		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static FrameLayout getFrameLayout() {
		return frameLayout;
	}

	public static void setFrameLayout(FrameLayout frameLayout) {
		PublicityManagerSingleton.frameLayout = frameLayout;
	}

	public static LayoutParams getFrameLayoutLayoutParams() {
		return frameLayoutLayoutParams;
	}

	public static void setFrameLayoutLayoutParams(
			LayoutParams frameLayoutLayoutParams) {
		PublicityManagerSingleton.frameLayoutLayoutParams = frameLayoutLayoutParams;
	}

	public ThreadCountActivate getThread() {
		return thread;
	}

	public void setThread(ThreadCountActivate thread) {
		this.thread = thread;
	}

	public void destroyAppFlood() {
		try {
			AppFlood.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void showVideo() {
		try {

			if (!activeChartboost && !activeAdColony) {
				return;
			}

			if (Chartboost.hasRewardedVideo(CBLocation.LOCATION_DEFAULT)) {

				Chartboost.showRewardedVideo(CBLocation.LOCATION_DEFAULT);
				SceneManagerSingleton.getInstance().sendGoogleTrack(
						"View VIDEO chartboost");
				Chartboost.cacheRewardedVideo(CBLocation.LOCATION_DEFAULT);

			} else {

				SceneManagerSingleton.getInstance().sendGoogleTrack(
						"View VIDEO adColony");
				showAdColonyRewardVideo();
				// showAdColonyVideo();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean hasVideo() {
		try {

			if (!activeChartboost && !activeadMob && !activeAdColony) {
				return false;
			}

			// showAdColonyRewardVideo();
			if (true)
				return true;
			boolean retType = Chartboost
					.hasRewardedVideo(CBLocation.LOCATION_DEFAULT);

			return retType;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void loadVideoCache() {

		try {

			if (!activeChartboost) {
				return;
			}

			if (Chartboost.hasRewardedVideo(CBLocation.LOCATION_DEFAULT)) {
				return;
			}

			Chartboost.cacheRewardedVideo(CBLocation.LOCATION_DEFAULT);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void showViewVideoToEarn() {
		try {

			if (threadReward == null) {
				Log.d("NULL THREAD", "Thread pubicity was null");
				return;
			}
			if (!threadReward.isShow()) {
				return;
			}

			if (!activeChartboost) {
				return;
			}

			if (!Chartboost.hasRewardedVideo(CBLocation.LOCATION_DEFAULT)) {
				Chartboost.cacheRewardedVideo(CBLocation.LOCATION_DEFAULT);
				SceneManagerSingleton.getInstance().sendGoogleTrack(
						"Chartboost no tenia Videos");
				// showAdColonyRewardVideo();
				// return;
			}

			HUDManagerSingleton.getInstance()
					.addHUD(new RewardVideoHUD(), true);

			threadReward.setShow(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onPause() {

		try {

			if (UserInfoSingleton.getInstance().getPublicity() == 0) {
				return;
			}

			AdColony.pause();
			// startAppAd.onPause();
			// adView.pause();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onResume() {

		try {

			// adView.resume();
			AdColony.resume(activity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onDestroy() {

		try {

			// adView.destroy();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}