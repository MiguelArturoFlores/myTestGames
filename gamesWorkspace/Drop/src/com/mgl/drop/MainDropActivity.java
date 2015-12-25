package com.mgl.drop;

import io.presage.Presage;


import java.io.IOException;
import java.util.HashMap;

import com.mgl.crappypigeon.R;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.facebook.Session;
import com.facebook.widget.LoginButton;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mgl.base.analitycs.ThreadAnalitycs;
import com.mgl.base.analitycs.TrackerName;
import com.mgl.base.facebook.FacebookManager;
import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.resolutionpolicy.CroppedResolutionPolicy;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.texture.TextureSingleton;
import com.startapp.android.publish.banner.Banner;
import com.startapp.android.publish.banner.bannerstandard.BannerStandard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;

@SuppressLint("NewApi")
public class MainDropActivity extends SimpleBaseGameActivity {

	// analitycs
	private HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	// adds
	// chartbost

	// constants
	public static final int HELP_LEVEL_RETRY = 3; // IS USED TO SHOW HEL TO
														// PASS AWAY ONE LEVEL
														// BY SHARING
	public static int CAMERA_WIDTH = 800;
	public static int CAMERA_HEIGHT = 480;
	public static String DB_NAME = "palomaDB";
	public static int DB_VERSION = 1;

	// fields
	private SmoothCamera camera;

	// singletns
	private TextureSingleton texture;
	private ObjectFactorySingleton objectFactory;
	private SceneManagerSingleton sceneManager;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		camera = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, 480, 800,
				1);

		final CroppedResolutionPolicy canvasSurface = new CroppedResolutionPolicy(
				CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions eo = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(),
				camera);
		eo.getAudioOptions().setNeedsSound(true);
		eo.getAudioOptions().setNeedsMusic(true);

		PublicityManagerSingleton publicityManager = PublicityManagerSingleton
				.getInstance();
		publicityManager.setActivity(this);
		publicityManager.initPublicityData();

		
		Log.d("Orientation ",
				this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? "landscape"
						: "otro");

		return eo;

	}

	@Override
	protected void onCreateResources() {
		try {

			// SoundFactory.setAssetBasePath("mfx/");
			// initialize singletons

			texture = TextureSingleton.getInstance(getTextureManager(),
					getAssets(), getVertexBufferObjectManager(),
					getFontManager());

			texture.loadTextureSplash();

			sceneManager = SceneManagerSingleton.getInstance(this, mEngine,
					camera);

			getTracker(TrackerName.APP_TRACKER);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected Scene onCreateScene() {
		try {

			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
				mRenderSurfaceView.setPreserveEGLContextOnPause(true);
			}

			/*
			 * final Scene scene = new Scene();
			 * 
			 * final Game game = new Game(scene, camera); game.init();
			 * scene.registerUpdateHandler(new IUpdateHandler() { public void
			 * reset() { }
			 * 
			 * public void onUpdate(float pSecondsElapsed) { // HERE IS THE GAME
			 * LOOP //
			 * System.out.println("this is the time elapsed : "+pSecondsElapsed
			 * ); // level.update(pSecondsElapsed, level); //
			 * game.update(pSecondsElapsed); } });
			 */

			// boolean val = FacebookManager.isLoggedIn(this);

			// init data chartboost
			// this.cb.cacheInterstitial();

			ThreadAnalitycs thread = new ThreadAnalitycs(this, 1);
			thread.start();

			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

			beginThreadFacebookLogin();
			boolean val = FacebookManager.isLoggedIn(this);
			Log.d("Loged in facebook", " " + val);
			// FacebookManager.post("aja", "mi data", this);

			Scene scene = sceneManager.createSplashScene(this);

			this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					DatabaseDrop db2 = new DatabaseDrop(getActivity(), DB_NAME,
							null, DB_VERSION);
					try {

						db2.openDataBase();
						db2.createDataBase();// TODO
						Log.d("getting redable", "rdabe1");
						db2.getReadableDatabase();

						Log.d("getting redable", "rdabe2");

						db2.close();

						db2.openDataBase();

						if (db2.isUpgrade()) {

							db2.overrideDATABASE();

							LevelDAO dao = new LevelDAO(SceneManagerSingleton
									.getInstance().getActivity(),
									MainDropActivity.DB_NAME, null,
									MainDropActivity.DB_VERSION);
							for (Level level : db2.getLeveList()) {
								if (level.getAvalible()) {
									dao.unlockLevel(level);
								}
							}
						}

						// db2.overrideDATABASE();
						db2.close();
						//

					} catch (IOException e) {
						e.printStackTrace();
					}

				}

			});

			return scene;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Context getActivity() {

		return this;
	}

	public void loadPublicitySound() {
		try {

			/*
			 * PublicityManagerSingleton publicityManager =
			 * PublicityManagerSingleton .getInstance();
			 * publicityManager.setActivity(this);
			 * publicityManager.initPublicityThread();
			 */

			SoundSingleton.getInstance(getSoundManager(), this,
					getMusicManager());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void beginThreadFacebookLogin() {
		try {
			// FacebookManager.facebookLogin(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public synchronized void onGameDestroyed() {

		super.onGameDestroyed();

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);

	}

	@Override
	protected void onPause() {
		super.onPause();

		if (PublicityManagerSingleton.getInstance().getStartAppAd() != null) {
			PublicityManagerSingleton.getInstance().getStartAppAd().onPause();
		}

		if (this.isGameLoaded()) {

			// texture.unloadTextures();

		}
		// music.pause();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		if (PublicityManagerSingleton.getInstance().getStartAppAd() != null) {
			PublicityManagerSingleton.getInstance().getStartAppAd()
					.onBackPressed();
		}

		ThreadAnalitycs thread = new ThreadAnalitycs(this, 2);
		thread.start();

		if (this.isGameLoaded()) {
			if (this.isGameLoaded()) {
				texture.destroy();
				// SceneManagerSingleton.getInstance().destroy();
				// PublicityManagerSingleton.getInstance().destroy();

				PublicityManagerSingleton.getInstance().destroyAppFlood();
				System.exit(0);
			}

		}

	}

	@Override
	protected synchronized void onResume() {
		super.onResume();

		if (PublicityManagerSingleton.getInstance().getStartAppAd() != null) {
			PublicityManagerSingleton.getInstance().getStartAppAd().onResume();
		}

		// System.gc();
		if (this.isGameLoaded()) {

			// texture.loadTextures();

		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// this.mRenderSurfaceView.destroyDrawingCache();

		ThreadAnalitycs thread = new ThreadAnalitycs(this, 2);
		thread.start();
		PublicityManagerSingleton.getInstance().destroyAppFlood();

		if (this.isGameLoaded()) {
			texture.destroy();
			// SceneManagerSingleton.getInstance().destroy();
			// PublicityManagerSingleton.getInstance().destroy();

			System.exit(0);
		}
	}

	// In onStop()

	// In onDestroy()

	// In onBackPressed()

	public static int getCAMERA_WIDTH() {
		return CAMERA_WIDTH;
	}

	public static void setCAMERA_WIDTH(int cAMERA_WIDTH) {
		CAMERA_WIDTH = cAMERA_WIDTH;
	}

	public static int getCAMERA_HEIGHT() {
		return CAMERA_HEIGHT;
	}

	public static void setCAMERA_HEIGHT(int cAMERA_HEIGHT) {
		CAMERA_HEIGHT = cAMERA_HEIGHT;
	}

	public static String getDB_NAME() {
		return DB_NAME;
	}

	public static void setDB_NAME(String dB_NAME) {
		DB_NAME = dB_NAME;
	}

	public static int getDB_VERSION() {
		return DB_VERSION;
	}

	public static void setDB_VERSION(int dB_VERSION) {
		DB_VERSION = dB_VERSION;
	}

	public SmoothCamera getCamera() {
		return camera;
	}

	public void setCamera(SmoothCamera camera) {
		this.camera = camera;
	}

	public TextureSingleton getTexture() {
		return texture;
	}

	public void setTexture(TextureSingleton texture) {
		this.texture = texture;
	}

	public ObjectFactorySingleton getObjectFactory() {
		return objectFactory;
	}

	public void setObjectFactory(ObjectFactorySingleton objectFactory) {
		this.objectFactory = objectFactory;
	}

	public SceneManagerSingleton getSceneManager() {
		return sceneManager;
	}

	public void setSceneManager(SceneManagerSingleton sceneManager) {
		this.sceneManager = sceneManager;
	}

	@Override
	protected void onSetContentView() {
		// PRE-CACHE STARTAPP

		// SETUP THE LAYOUT FOR THE AD
		// THE AD WILL APPEAR ON TOP OF EVERYTHING
		
		
		if(true){
			return;
		}
		final FrameLayout frameLayout = new FrameLayout(this);

		final FrameLayout.LayoutParams frameLayoutLayoutParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT,
				Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

		// DISPLAY ADD AT BOTTOM
		FrameLayout.LayoutParams adViewLayoutParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.LEFT
						| Gravity.BOTTOM);

		this.mRenderSurfaceView = new RenderSurfaceView(this);
		mRenderSurfaceView.setRenderer(mEngine, this);

		final android.widget.FrameLayout.LayoutParams surfaceViewLayoutParams = new FrameLayout.LayoutParams(
				super.createSurfaceViewLayoutParams());

		frameLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);

		// CHOOSE TYPE OF STARTAPP AD
		Banner startAppBanner = new Banner(this);// INCLUDES 3D ROTATING BANNER
		startAppBanner.hideBanner();
		PublicityManagerSingleton.setStartAppBanner(startAppBanner);

		BannerStandard startAppBannerStandard = new BannerStandard(this);// INCLUDES
																			// 3D
																			// ROTATING
																			// BANNER
		startAppBannerStandard.hideBanner();
		PublicityManagerSingleton.setStartAppBanneStandardr(
				startAppBannerStandard, frameLayout, adViewLayoutParams);
		// startAppBanner.setScaleX((float) 0.8);
		// startAppBanner.setScaleY((float) 0.5);

		// BannerStandard startAppBanner = new
		// BannerStandard(this);//TRADITIONAL BANNER ONLY

		frameLayout.addView(startAppBanner, adViewLayoutParams);
		frameLayout.addView(startAppBannerStandard, adViewLayoutParams);

		
		
		this.setContentView(frameLayout, frameLayoutLayoutParams);

	}

	public boolean hasInternetConnection() {
		try {

			ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo i = conMgr.getActiveNetworkInfo();
			if (i == null)
				return false;
			if (!i.isConnected()) {
				return false;
			}
			if (!i.isAvailable()) {
				return false;
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public synchronized Tracker getTracker(TrackerName trackerId) {
		if (!mTrackers.containsKey(trackerId)) {

			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics
					.newTracker(R.xml.app_tracker)
					: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics
							.newTracker(R.xml.global_tracker) : analytics
							.newTracker(R.xml.global_tracker);
			mTrackers.put(trackerId, t);

		}
		return mTrackers.get(trackerId);
	}

	public static boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) SceneManagerSingleton
				.getInstance().getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		try {

			//PublicityManagerSingleton.getInstance().onStart();
			Presage.getInstance().setContext(this.getBaseContext());
			Presage.getInstance().start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/*
	 * @SuppressLint("NewApi")
	 * 
	 * @Override protected void onSetContentView() { //Creating the parent frame
	 * layout: final FrameLayout frameLayout = new FrameLayout(this); //Creating
	 * its layout params, making it fill the screen. final
	 * FrameLayout.LayoutParams frameLayoutLayoutParams = new
	 * FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
	 * FrameLayout.LayoutParams.FILL_PARENT);
	 * 
	 * //Creating the banner view. AFBannerView bannerView = new
	 * AFBannerView(this);
	 * PublicityManagerSingleton.setAppFloodBannerView(bannerView);
	 * bannerView.setVisibility(LinearLayout.VISIBLE);
	 * bannerView.setActivated(false);
	 * 
	 * //.... //Do any initiallizations on the banner view here. //....
	 * 
	 * //Creating the banner layout params. With this params, the ad will be
	 * placed in the top of the screen, middle horizontally. final
	 * FrameLayout.LayoutParams bannerViewLayoutParams = new
	 * FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
	 * FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.END);
	 * 
	 * //Creating AndEngine's view. this.mRenderSurfaceView = new
	 * RenderSurfaceView(this); //TODO mRenderSurfaceView.setRenderer(mEngine,
	 * this);
	 * 
	 * 
	 * 
	 * //createSurfaceViewLayoutParams is an AndEngine method for creating the
	 * params for its view. final android.widget.FrameLayout.LayoutParams
	 * surfaceViewLayoutParams = new
	 * FrameLayout.LayoutParams(super.createSurfaceViewLayoutParams());
	 * 
	 * //Adding the views to the frame layout.
	 * frameLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);
	 * frameLayout.addView(bannerView, bannerViewLayoutParams);
	 * 
	 * //Setting content view this.setContentView(frameLayout,
	 * frameLayoutLayoutParams); }
	 */
}
