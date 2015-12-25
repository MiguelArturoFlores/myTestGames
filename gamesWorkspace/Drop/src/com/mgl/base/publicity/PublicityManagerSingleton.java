package com.mgl.base.publicity;

import io.presage.Presage;
import io.presage.utils.IADHandler;

import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.appflood.AFBannerView;
import com.appflood.AFVideoActivity;
import com.appflood.AppFlood;
import com.appflood.AppFlood.AFEventDelegate;
import com.appflood.AppFlood.AFRequestDelegate;
import com.appflood.AppFlood.AFVideoDelegate;
import com.chartboost.sdk.CBPreferences;
import com.chartboost.sdk.Chartboost;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.ironsource.mobilcore.MobileCore;
import com.ironsource.mobilcore.MobileCore.AD_UNITS;
import com.ironsource.mobilcore.MobileCore.LOG_TYPE;
import com.jirbo.adcolony.AdColony;
import com.jirbo.adcolony.AdColonyV4VCAd;
import com.jirbo.adcolony.AdColonyV4VCListener;
import com.jirbo.adcolony.AdColonyV4VCReward;
import com.jirbo.adcolony.AdColonyVideoAd;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.hud.InHouseAddHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;
import com.startapp.android.publish.banner.Banner;
import com.startapp.android.publish.banner.bannerstandard.BannerStandard;
import com.startapp.android.publish.model.AdPreferences;
import com.startapp.android.publish.nativead.StartAppNativeAd;
import com.startapp.android.publish.splash.SplashConfig;

public class PublicityManagerSingleton {

	private static PublicityManagerSingleton instance = null;

	private Activity activity;

	// add networks
	private Chartboost cb;
	private StartAppAd startAppAd = null;
	private StartAppNativeAd startAppAdNative = null;

	// addColony
	// adColony
	private String AdColonyAppId = "app81ed9bd4fce746b5b0";
	private String AdColonyZoneId = "vzad6ab7ff0e33439e8d";
	private String AdColonyClientOptions = "version:1.25,store:google";
	private AdColonyVideoAd adVideoColony;

	// admob
	private String MY_ADMOB_UNIT_ID = "ca-app-pub-9909317453090800/6695717378";
	private InterstitialAd mInterstitialAd;

	private boolean activeChartboost = true;
	private boolean activeAppFlood = true;
	private boolean activeMobileCore = true;
	private boolean activeStartApp = true;
	private boolean activeAdColony = true;
	private boolean activeAdMob = true;
	private boolean activeOgury = true;

	private static AFBannerView afbannerView;
	private static Banner startAppBanner;
	private static BannerStandard startAppBannersStandard;

	private static FrameLayout frameLayout;
	private static LayoutParams frameLayoutLayoutParams;

	private ThreadCountPublicity thread;
	private ThreadCountPublicity threadHouse;

	// ogury handler
	private IADHandler oguryHandler;

	
	private PublicityManagerSingleton() {

		try {

			Log.d("Inicializo el singelton de publicidad",
					"Inicializo el singelton de publicidad");

			thread = new ThreadCountPublicity(40L, false);
			thread.start();

			threadHouse = new ThreadCountPublicity(80L, false);
			threadHouse.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initPublicityData() {
		try {

			if(activeOgury){
				initOguryData();
			}
			
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
			if (activeAdColony) {
				initAdColonyData();
			}
			if (activeAdMob) {
				initAdMobData();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initOguryData() {
		try {
			
			Presage.getInstance().setContext(activity);
			oguryHandler =  new IADHandler() {
				
				@Override
				public void onAdNotFound() {
					showIntersitialNormal();
				}
				

				@Override
				public void onAdFound() {
					SceneManagerSingleton.getInstance().getActivity().onPauseGame();
					thread.setShowIntersitial(false);
				}
				
				@Override
				public void onAdClosed() {
					SceneManagerSingleton.getInstance().getActivity().onResumeGame();
					thread.setShowIntersitial(false);
				}
			};
			
			
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
	
	private void initAdColonyData() {
		try {

			AdColony.configure(activity, AdColonyClientOptions, AdColonyAppId,
					AdColonyZoneId);
			adVideoColony = new AdColonyVideoAd();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initStartAppData() {
		try {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {

					StartAppSDK.init(activity, "108072614", "208880014", true);
					SplashConfig.getDefaultSplashConfig().setOrientation(
							SplashConfig.Orientation.LANDSCAPE);
					StartAppNativeAd nat = new StartAppNativeAd(activity);

					startAppAd = new StartAppAd(activity);
					startAppAdNative = new StartAppNativeAd(activity);
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
					AppFlood.initialize(activity, "JXcpOHVhPoH82GP1",
							"N8HWdOv0537aL541f5a12", AppFlood.AD_ALL);
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

			this.cb = Chartboost.sharedChartboost();
			String appId = "53e2b9661873da20d86cf29f";
			String appSignature = "5dc9bd853a2d3d650fad83fd1e3941c0f0ef55df";
			this.cb.onCreate(activity, appId, appSignature, null);
			CBPreferences.getInstance().setImpressionsUseActivities(true);

			// CBPreferences.getInstance().setOrientation(CBOrientation.LANDSCAPE);
			// cb.getPreferences().setOrientation(CBOrientation.LANDSCAPE_RIGHT);

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
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {

					cb.onStart(activity);
					cb.cacheInterstitial();
					cb.cacheMoreApps();
					// cb.showInterstitial();

				}
			});

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
	
	public void showIntersitialHouseAdd() {
		try {
			if (true) {
				return;
			}
			if (threadHouse == null) {
				Log.d("NULL THREAD", "Thread pubicity was null");
				return;
			}
			if (!threadHouse.isShowIntersitial()) {
				return;
			}

			HUDManagerSingleton.getInstance().addHUD(new InHouseAddHUD());

			threadHouse.setShowIntersitial(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showOguryInterstitial() {
		try {
			
			activity.runOnUiThread(new Runnable() {
				public void run() {
					Presage.getInstance().adToServe("interstitial",oguryHandler);
				}
			});

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showIntersitialNormal() {
		try {
			
			Double val = (Math.random() * 19843041) % 100;

			if (thread == null) {
				Log.d("NULL THREAD", "Thread pubicity was null");
				return;
			}
//			if (!thread.isShowIntersitial()) {
//				return;
//			}

			// Uncomment this to make the distribution bettwen add networks
			if (val.intValue() <= 15) {
				HUDManagerSingleton.getInstance().addHUD(new InHouseAddHUD());
			} else if (val.intValue() <= 40) {
				showAdColonyVideo();
			} else if (val.intValue() <= 70) {
				showStartAppIntersitial();
			} else if (val.intValue() <= 100) {
				showAdMobIntersitial();
			}

			thread.setShowIntersitial(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showIntersitial() {
		try {


			if (thread == null) {
				Log.d("NULL THREAD", "Thread pubicity was null");
				return;
			}
			if (!thread.isShowIntersitial()) {
				return;
			}
			
			showOguryInterstitial();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showStartAppIntersitial() {
		try {
			if (startAppAdNative == null) {

				return;
			}
			activity.runOnUiThread(new Runnable() {
				public void run() {

					// startAppAd.showAd(); // show the ad
					startAppAd.loadAd(); // load the next ad
					// startAppAd.showSplash(activity, null);
					// startAppAd.showSlider(activity); //USEfull
					startAppAd.showSplash(activity, null);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

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

	private void showChartboostIntersitial() {
		try {
			if (!activeChartboost) {
				return;
			}

			activity.runOnUiThread(new Runnable() {
				public void run() {
					if (cb.hasCachedInterstitial()) {

						cb.showInterstitial();
						// cb.showMoreApps();
						cb.cacheInterstitial();
					} else {
						Log.d("Chartboost", "No tenia anuncios");
						showIntersitial();
					}

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

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

	public void showBanner() {
		try {

			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {

					// stuff that updates ui

					// AppFlood.showBanner(activity, AppFlood.BANNER_SMALL,
					// AppFlood.BANNER_POSITION_BOTTOM);
					// startAppBanner = new Banner(activity);

					// startAppBanner.showBanner();
					if (!activeStartApp) {
						return;
					}
					startAppBannersStandard.showBanner();

					// startAppBanner.hideBanner();

					// AppFlood.showBanner(activity,AppFlood.BANNER_POSITION_BOTTOM,AppFlood.BANNER_SMALL);

				}
			});

			// afbannerView.setVisibility(View.VISIBLE);
			// AppFlood.showBanner(activity, AppFlood.BANNER_SMALL,
			// AppFlood.BANNER_POSITION_BOTTOM);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void setAppFloodBannerView(AFBannerView bannerView) {
		try {

			afbannerView = bannerView;

		} catch (Exception e) {
			e.printStackTrace();
		}

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

	public static AFBannerView getAfbannerView() {
		return afbannerView;
	}

	public static void setAfbannerView(AFBannerView afbannerView) {
		PublicityManagerSingleton.afbannerView = afbannerView;
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

	public static Banner getStartAppBanner() {
		return startAppBanner;
	}

	public static void setStartAppBanner(Banner startAppBanner) {
		PublicityManagerSingleton.startAppBanner = startAppBanner;
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

			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {

					if (!activeStartApp) {
						return;
					}

					startAppBannersStandard.hideBanner();
					startAppBanner.hideBanner();

					startAppBannersStandard = new BannerStandard(activity);
					frameLayout.addView(startAppBannersStandard,
							frameLayoutLayoutParams);

					startAppBannersStandard.hideBanner();
					startAppBanner.hideBanner();

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void setStartAppBanneStandardr(
			BannerStandard startAppBannerStandard, FrameLayout frameLayout,
			LayoutParams frameLayoutLayoutParams) {
		try {

			PublicityManagerSingleton.startAppBannersStandard = startAppBannerStandard;
			PublicityManagerSingleton.frameLayout = frameLayout;
			PublicityManagerSingleton.frameLayoutLayoutParams = frameLayoutLayoutParams;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static BannerStandard getStartAppBannersStandard() {
		return startAppBannersStandard;
	}

	public static void setStartAppBannersStandard(
			BannerStandard startAppBannersStandard) {
		PublicityManagerSingleton.startAppBannersStandard = startAppBannersStandard;
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

	public ThreadCountPublicity getThread() {
		return thread;
	}

	public void setThread(ThreadCountPublicity thread) {
		this.thread = thread;
	}

	public void destroyAppFlood() {
		try {
			AppFlood.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
