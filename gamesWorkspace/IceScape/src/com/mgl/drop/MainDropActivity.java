package com.mgl.drop;

//import io.presage.Presage;

import java.io.IOException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.json.JSONObject;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.service.textservice.SpellCheckerService.Session;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.example.games.basegameutils.GooglePlayGameSingleton;
import com.mgl.base.MySprite;
import com.mgl.base.analitycs.ThreadAnalitycs;
import com.mgl.base.analitycs.TrackerName;
import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.base.userinfo.OffertGameSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.base.userinfo.WebServiceSingleton;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.GamePurchaseConstant;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.database.model.PlayerDB;
import com.mgl.drop.game.hud.DiamantEarnHUD;
import com.mgl.drop.game.hud.FreeDailyRewardHUD;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.InformativeSpriteHUD;
import com.mgl.drop.game.hud.RewardVideoHUD;
import com.mgl.drop.game.resolutionpolicy.CroppedResolutionPolicy;
import com.mgl.drop.game.scene.MainScene;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.ManageDate;
import com.mgl.inappbilling.util.IabHelper;
import com.mgl.inappbilling.util.IabResult;
import com.mgl.inappbilling.util.Inventory;
import com.mgl.inappbilling.util.Purchase;
import com.mgl.inappbilling.util.Security;
import com.mgl.penguinsnow.BuildConfig;
import com.mgl.penguinsnow.R;
import com.mgl.twitter.TwitterSingleton;
import com.startapp.android.publish.banner.Banner;
import com.startapp.android.publish.banner.bannerstandard.BannerStandard;
import com.startapp.android.publish.splash.SplashConfig.MinSplashTime;

@SuppressLint("NewApi")
public class MainDropActivity extends SimpleBaseGameActivity implements
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {

	// Note: Your consumer key and secret should be obfuscated in your source code before shipping.
	//private static final String TWITTER_KEY = "Hv8LaCFpfCbDUwmpAapnY3LhF";
	//private static final String TWITTER_SECRET = "mlTFVU2iedFw99nU5OIpLNeTWgMIw5A37WBq7Qya49Iw9Hpbam";
	
	// analitycs
	private HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
	private static final int REQUEST_CODE_EMAIL = 5465;

	private PendingAction pendingAction = PendingAction.NONE;

	private boolean canPresentShareDialog;
	private boolean canPresentShareDialogWithPhotos;

	private enum PendingAction {
		NONE, POST_PHOTO, POST_STATUS_UPDATE
	}

	// adds
	// chartbost
	// constants
	public static final int HELP_LEVEL_RETRY = 1000; // IS USED TO SHOW HEL TO
														// PASS AWAY ONE LEVEL
														// BY SHARING

	protected static final boolean OVERRIDE_DATABASE = false;
	public static int CAMERA_WIDTH = 480;
	public static int CAMERA_HEIGHT = 800;
	// public static String DB_NAME = "smasherDB";
	public static int DB_VERSION = 4;
	public static String BACKGROUND_TEXTURE_NAME;
	// public static String BACKGROUND_FADE_NAME;
	public static int MIN_LEVEL_TO_RATE = 8;

	
	// fields
	private SmoothCamera camera;

	// singletns
	private TextureSingleton texture;
	private ObjectFactorySingleton objectFactory;
	private SceneManagerSingleton sceneManager;

	// in app purchase
	private static final String TAG = "com.mgl.inappbilling";
	private IabHelper mHelper;

	private String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoWQbFr7vKKcU0gEXqJjnOfT30jqXWlSTd2HgqOqWetoMStNhvni7gynt0e2OXyAq7GROnrtOZvBLTWng6I08Mbn6Fqgx2GCApv+f+8tLLkfh6JYiLUlGcmRiEf9UO94nkNUPrjaXYgwCjbQhEAj0SU2U5EsTt/Be1qOrj3Hv+f3jSDyJj0Zm9YWHKHM/D29POVm0auHjkZePHXRvFoOtxkTKoQnXp6lce+ludRyjbKYixsX1srGxsBVENfb0kVFSk744mDB2YIvnQObc6BJCHmmRBslr1f66LGIB4Cq1KUIOZQQlT9kblIOyRGdY36ImlXqigqh03cDWPfQ+Gl56AwIDAQAB";

	private IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener;
	private IabHelper.OnConsumeFinishedListener mConsumeFinishedListener;
	private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;

	private boolean avaliblePurchase = false;

	private boolean mSignInClicked = false;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		camera = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT,
				CAMERA_HEIGHT / 2 + 275, CAMERA_HEIGHT / 2, 1);

		final CroppedResolutionPolicy canvasSurface = new CroppedResolutionPolicy(
				CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions eo = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(),
				camera);
		eo.getAudioOptions().setNeedsSound(true);
		eo.getAudioOptions().setNeedsMusic(true);

		eo.getTouchOptions().setNeedsMultiTouch(true);

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

			// INIT BACKGROUNDNAME
			BACKGROUND_TEXTURE_NAME = "backgroundW1.png";

			texture = TextureSingleton.getInstance(getTextureManager(),
					getAssets(), getVertexBufferObjectManager(),
					getFontManager());

			sceneManager = SceneManagerSingleton.getInstance(this, mEngine,
					camera);

			getTracker(TrackerName.APP_TRACKER);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initInAppPurchase() {
		try {

			mHelper = new IabHelper(this, base64EncodedPublicKey);

			avaliblePurchase = false;

			mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
				public void onIabSetupFinished(IabResult result) {
					if (!result.isSuccess()) {
						Log.d(TAG, "In-app Billing setup failed: " + result);
					} else {
						Log.d(TAG, "In-app Billing is set up OK");
						initInAppPurchaseListeners();

					}
				}

				private void consumeTestPurchase() {
					try {

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void requestInventoryItems() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if(mHelper.ismSetupDone()){
						mHelper.queryInventoryAsync(mReceivedInventoryListener);
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initInAppPurchaseListeners() {
		try {
			avaliblePurchase = true;
			mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {

				public void onIabPurchaseFinished(IabResult result,
						Purchase purchase) {
					if (mHelper == null) {

						return;
					}
					if (result.isFailure()) {
						// Handle error

						Log.d("PurchaseState", result.getResponse() + ""
								+ result.getMessage());
						InformativeHUD hud = new InformativeHUD(
								"La compra no se pudo efectuar, no se cobrara nada ");
						HUDManagerSingleton.getInstance().addHUD(hud, true);
						return;
					} else {
						Log.d("Comprando", "comprando - > consumuiendo");
						consumeItemSuccessfull(purchase);

					}

				}

			};
			// Consuming items and showing consumtion message to the user
			mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
				public void onConsumeFinished(Purchase purchase,
						IabResult result) {
					if (mHelper == null) {

						return;
					}

					if (result.isSuccess()) {
						// clickButton.setEnabled(true);
						// TODO set eneable the button to purchase again!
						if (purchase == null) {
							return;
						}

						showPurchaseSuccesfullResult(purchase);

					} else {
						// handle error
					}
				}
			};

			mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
				public void onQueryInventoryFinished(IabResult result,
						Inventory inventory) {
					if (mHelper == null) {
						return;
					}
					if (result.isFailure()) {
						// Handle failure
						Log.d("Failure", "failure inventory");
					} else {
						assignItemPurchased(inventory, result);
					}
				}

			};

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showPurchaseSuccesfullResult(Purchase purchase) {
		try {

			if (purchase.getSku().equals(GamePurchaseConstant.BUY_A_STRING)) {

				InformativeSpriteHUD hud = new InformativeSpriteHUD(
						createSprite(GamePurchaseConstant.BUY_A_TEXTURE),
						"You've Succesfull Purchased");
				HUDManagerSingleton.getInstance().addHUD(hud, true);

				mHelper.consumeAsync(purchase, mConsumeFinishedListener);

			} else if (purchase.getSku().equals(
					GamePurchaseConstant.BUY_B_STRING)) {

				InformativeSpriteHUD hud = new InformativeSpriteHUD(
						createSprite(GamePurchaseConstant.BUY_B_TEXTURE),
						"You've Succesfull Purchased");
				HUDManagerSingleton.getInstance().addHUD(hud, true);

				mHelper.consumeAsync(purchase, mConsumeFinishedListener);

			} else if (purchase.getSku().equals(
					GamePurchaseConstant.BUY_C_STRING)) {

				InformativeSpriteHUD hud = new InformativeSpriteHUD(
						createSprite(GamePurchaseConstant.BUY_C_TEXTURE),
						"You've Succesfull Purchased");
				HUDManagerSingleton.getInstance().addHUD(hud, true);

				mHelper.consumeAsync(purchase, mConsumeFinishedListener);

			} else if (purchase.getSku().equals(
					GamePurchaseConstant.BUY_D_STRING)
					|| purchase.getSku().equals(
							GamePurchaseConstant.BUY_D_OFFERT_STRING)) {

				InformativeSpriteHUD hud = new InformativeSpriteHUD(
						createSprite(GamePurchaseConstant.BUY_D_TEXTURE),
						"You've Succesfull Purchased");
				HUDManagerSingleton.getInstance().addHUD(hud, true);

				mHelper.consumeAsync(purchase, mConsumeFinishedListener);

			} else if (purchase.getSku().equals(
					GamePurchaseConstant.BUY_E_STRING)
					|| purchase.getSku().equals(
							GamePurchaseConstant.BUY_E_OFFERT_STRING)) {

				InformativeSpriteHUD hud = new InformativeSpriteHUD(
						createSprite(GamePurchaseConstant.BUY_E_TEXTURE),
						"You've Succesfull Purchased");
				HUDManagerSingleton.getInstance().addHUD(hud, true);

				mHelper.consumeAsync(purchase, mConsumeFinishedListener);

			} else if (purchase.getSku().equals(
					GamePurchaseConstant.BUY_F_STRING)
					|| purchase.getSku().equals(
							GamePurchaseConstant.BUY_F_OFFERT_STRING)) {

				InformativeSpriteHUD hud = new InformativeSpriteHUD(
						createSprite(GamePurchaseConstant.BUY_F_TEXTURE),
						"You've Succesfull Purchased");
				HUDManagerSingleton.getInstance().addHUD(hud, true);

				mHelper.consumeAsync(purchase, mConsumeFinishedListener);

			} else if (purchase.getSku().equals(
					GamePurchaseConstant.BUY_G_STRING)
					|| purchase.getSku().equals(
							GamePurchaseConstant.BUY_G_OFFERT_STRING)) {

				InformativeSpriteHUD hud = new InformativeSpriteHUD(
						createSprite(GamePurchaseConstant.BUY_G_TEXTURE),
						"You've Succesfull Purchased");
				HUDManagerSingleton.getInstance().addHUD(hud, true);

				mHelper.consumeAsync(purchase, mConsumeFinishedListener);

			} else if (purchase.getSku().equals(
					GamePurchaseConstant.BUY_H_STRING)
					|| purchase.getSku().equals(
							GamePurchaseConstant.BUY_H_OFFERT_STRING)) {

				InformativeSpriteHUD hud = new InformativeSpriteHUD(
						createSprite(GamePurchaseConstant.BUY_H_TEXTURE),
						"You've Succesfull Purchased");
				HUDManagerSingleton.getInstance().addHUD(hud, true);

				mHelper.consumeAsync(purchase, mConsumeFinishedListener);

			} else if (purchase.getSku().equals(
					GamePurchaseConstant.BUY_I_STRING)
					|| purchase.getSku().equals(
							GamePurchaseConstant.BUY_I_OFFERT_STRING)) {

				InformativeSpriteHUD hud = new InformativeSpriteHUD(
						createSprite(GamePurchaseConstant.BUY_I_TEXTURE),
						"You've Succesfull Purchased");
				HUDManagerSingleton.getInstance().addHUD(hud, true);

				mHelper.consumeAsync(purchase, mConsumeFinishedListener);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private MySprite createSprite(String textureName) {
		try {

			SpriteBackground img = new SpriteBackground(0, 0,
					texture.getTextureByName(textureName),
					texture.getVertexBufferObjectManager());
			return img;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void assignItemPurchased(Inventory inventory, IabResult result) {
		try {

			if (inventory.getPurchase(GamePurchaseConstant.BUY_A_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_A_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_A);

			} else if (inventory.getPurchase(GamePurchaseConstant.BUY_B_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_B_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_B);

			} else if (inventory.getPurchase(GamePurchaseConstant.BUY_C_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_C_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_C);

			} else if (inventory.getPurchase(GamePurchaseConstant.BUY_D_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_D_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_D);

			} else if (inventory.getPurchase(GamePurchaseConstant.BUY_E_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_E_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_E);

			} else if (inventory.getPurchase(GamePurchaseConstant.BUY_F_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_F_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_F);

			} else if (inventory.getPurchase(GamePurchaseConstant.BUY_G_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_G_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_G);

			} else if (inventory.getPurchase(GamePurchaseConstant.BUY_H_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_H_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_H);

			} else if (inventory.getPurchase(GamePurchaseConstant.BUY_I_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_I_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_I);

			} else if (inventory
					.getPurchase(GamePurchaseConstant.BUY_D_OFFERT_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_D_OFFERT_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_D);

			} else if (inventory
					.getPurchase(GamePurchaseConstant.BUY_E_OFFERT_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_E_OFFERT_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_E);

			} else if (inventory
					.getPurchase(GamePurchaseConstant.BUY_F_OFFERT_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_F_OFFERT_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_F);

			} else if (inventory
					.getPurchase(GamePurchaseConstant.BUY_G_OFFERT_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_G_OFFERT_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_G);

			} else if (inventory
					.getPurchase(GamePurchaseConstant.BUY_H_OFFERT_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_H_OFFERT_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_H);

			} else if (inventory
					.getPurchase(GamePurchaseConstant.BUY_I_OFFERT_STRING) != null) {
				mHelper.consumeAsync(inventory
						.getPurchase(GamePurchaseConstant.BUY_I_OFFERT_STRING),
						mConsumeFinishedListener);
				UserInfoSingleton.getInstance().buyItem(
						GamePurchaseConstant.BUY_I);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void consumeItemSuccessfull(Purchase purchase) {
		try {
			// TODO validate purchase.sku if its no a consumbale item if I have
			// no consumable items
			consumeItem();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// idbuytype mean gameConstants.buyA buyB
	public void generatePurchaseFlow(int idBuyType) {
		try {
			if (mHelper == null) {
				return;
			}

			if (idBuyType == GamePurchaseConstant.BUY_A) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_A_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_A_STRING);
			} else if (idBuyType == GamePurchaseConstant.BUY_B) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_B_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_B_STRING);

			} else if (idBuyType == GamePurchaseConstant.BUY_C) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_C_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_C_STRING);
			} else if (idBuyType == GamePurchaseConstant.BUY_D) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_D_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_D_STRING);
			} else if (idBuyType == GamePurchaseConstant.BUY_E) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_E_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_E_STRING);
			} else if (idBuyType == GamePurchaseConstant.BUY_F) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_F_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_F_STRING);
			} else if (idBuyType == GamePurchaseConstant.BUY_G) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_G_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_G_STRING);
			} else if (idBuyType == GamePurchaseConstant.BUY_H) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_H_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_H_STRING);
			} else if (idBuyType == GamePurchaseConstant.BUY_I) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_I_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_I_STRING);
			}

			else if (idBuyType == GamePurchaseConstant.BUY_D_OFFERT) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_D_OFFERT_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_D_OFFERT_STRING);
			} else if (idBuyType == GamePurchaseConstant.BUY_E_OFFERT) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_E_OFFERT_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_E_OFFERT_STRING);
			} else if (idBuyType == GamePurchaseConstant.BUY_F_OFFERT) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_F_OFFERT_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_F_OFFERT_STRING);
			} else if (idBuyType == GamePurchaseConstant.BUY_G_OFFERT) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_G_OFFERT_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_G_OFFERT_STRING);
			} else if (idBuyType == GamePurchaseConstant.BUY_H_OFFERT) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_H_OFFERT_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_H_OFFERT_STRING);
			} else if (idBuyType == GamePurchaseConstant.BUY_I_OFFERT) {
				mHelper.launchPurchaseFlow(this,
						GamePurchaseConstant.BUY_I_OFFERT_STRING, 10001,
						mPurchaseFinishedListener,
						GamePurchaseConstant.BUY_I_OFFERT_STRING);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void consumeItem() {
		try {

			mHelper.queryInventoryAsync(mReceivedInventoryListener);

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

			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

			beginThreadFacebookLogin();

			this.runOnUiThread(new Runnable() {

				@Override
				public void run() {

				}

			});

			DatabaseDrop db2 = new DatabaseDrop(getActivity(),
					DatabaseDrop.DB_NAME, null, DB_VERSION);
			try {

				db2.openDataBase();
				db2.createDataBase();// TODO
				Log.d("getting redable", "rdabe1");
				db2.getReadableDatabase();

				Log.d("getting redable", "rdabe2");

				db2.close();

				db2.openDataBase();

				if (db2.isUpgrade()) {

					//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("UPGRADING"), true);
					
					db2.overrideDATABASE();

					LevelDAO dao = new LevelDAO(SceneManagerSingleton
							.getInstance().getActivity(), DatabaseDrop.DB_NAME,
							null, MainDropActivity.DB_VERSION);
					
					dao.updateDatabaseUserInfo(db2.getUserInfo());
					
					for (Level level : db2.getLeveList()) {
						if (level.getAvalible()) {
							dao.changePercentage(level);
							
						}
					}
					try {
						for(PlayerDB player : db2.getPlayerList()){
							dao.updateFromUpgrade(player);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
				}

				if (OVERRIDE_DATABASE) {
					db2.overrideDATABASE();
				}

				db2.close();
				//

			} catch (IOException e) {
				e.printStackTrace();
			}

			Scene scene = sceneManager.createSplashScene(this);

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

			initInAppPurchase();

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

		try {

			GooglePlayGameSingleton.getInstance().onActivityResult(requestCode,
					resultCode, data);

			TwitterSingleton.getInstance().onActivityResult(requestCode,
					resultCode, data);
			
			if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
				super.onActivityResult(requestCode, resultCode, data);

			}

			if (requestCode == REQUEST_CODE_EMAIL && resultCode == RESULT_OK) {
				String accountName = data
						.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
				UserInfoSingleton.getInstance().setEmail(accountName);
				WebServiceSingleton.getInstance().sendEmail(accountName);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// super.onActivityResult(requestCode, resultCode, data);

		try {
			// super.onActivityResult(requestCode, resultCode, data);

			// if(requestCode == 10001){
			// if (!mHelper.handleActivityResult(requestCode, resultCode, data))
			// {
			// super.onActivityResult(requestCode, resultCode, data);

			// }
			// }else{
			// super.onActivityResult(requestCode, resultCode, data);
			// uiHelper.onActivityResult(requestCode, resultCode, data,
			// dialogCallback);
			// }

			// com.facebook.Session.getActiveSession().onActivityResult(this,
			// requestCode, resultCode, data);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {

			if (PublicityManagerSingleton.getInstance().getStartAppAd() != null) {
				if (UserInfoSingleton.getInstance().getPublicity() != 0) {
					PublicityManagerSingleton.getInstance().getStartAppAd()
							.onPause();
				}

			}

			SoundSingleton.getInstance().setPlaySoundMusic(false);

			if (this.isGameLoaded()) {

				// texture.unloadTextures();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// AppEventsLogger.deactivateApp(this);

		// music.pause();
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		try {

			if (!sceneManager.getCurrentScene().equals(AllScenes.MAIN)) {

				if (!HUDManagerSingleton.getInstance().canGoBack()) {
					return;
				}

				SceneManagerSingleton.getInstance().goBack();

				/*
				 * System.out.println("going to main");
				 * SceneManagerSingleton.getInstance().setCurrentScene(
				 * AllScenes.MAIN); System.out.println("in main");
				 */
				return;
			}

			
			if (!HUDManagerSingleton.getInstance().canGoBack()) {
				return;
			}
			onPauseGame();
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle( getString(R.string.CLOSING_GAME))
					.setMessage(getString(R.string.CLOSING_GAME2))
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									exitGame();
								}

							}).setNegativeButton("No", null).show();
			MainScene.printDebugHASHKey();

			// exitGame();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void exitGame() {
		try {
			if (PublicityManagerSingleton.getInstance().getStartAppAd() != null) {
				if (UserInfoSingleton.getInstance().getPublicity() != 0) {
					PublicityManagerSingleton.getInstance().getStartAppAd()
							.onBackPressed();
				}
			}

			ThreadAnalitycs thread = new ThreadAnalitycs(this, 2);
			thread.start();

			if (this.isGameLoaded()) {
				if (this.isGameLoaded()) {
					texture.destroy();
					// SceneManagerSingleton.getInstance().destroy();
					// PublicityManagerSingleton.getInstance().destroy();

					System.exit(0);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected synchronized void onResume() {
		try {

			super.onResume();

			SoundSingleton.getInstance().setPlaySoundMusic(true);

			if (PublicityManagerSingleton.getInstance().getStartAppAd() != null) {
				PublicityManagerSingleton.getInstance().getStartAppAd()
						.onResume();
			}

			// System.gc();
			if (this.isGameLoaded()) {

				// texture.loadTextures();

			}

			// AppEventsLogger.activateApp(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onDestroy() {
		try {

			super.onDestroy();

			// this.mRenderSurfaceView.destroyDrawingCache();

			ThreadAnalitycs thread = new ThreadAnalitycs(this, 2);
			thread.start();

			if (this.isGameLoaded()) {
				texture.destroy();
				// SceneManagerSingleton.getInstance().destroy();
				// PublicityManagerSingleton.getInstance().destroy();

				if (mHelper != null)
					mHelper.dispose();
				mHelper = null;

				System.exit(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);

		MultiDex.install(this);
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

	// @Override
	// protected void onSetContentView() {
	//
	// // PRE-CACHE STARTAPP
	//
	// // SETUP THE LAYOUT FOR THE AD
	// // THE AD WILL APPEAR ON TOP OF EVERYTHING
	// if(true){
	// return;
	// }
	// try {
	//
	// final FrameLayout frameLayout = new FrameLayout(this);
	//
	// final FrameLayout.LayoutParams frameLayoutLayoutParams = new
	// FrameLayout.LayoutParams(
	// FrameLayout.LayoutParams.MATCH_PARENT,
	// FrameLayout.LayoutParams.MATCH_PARENT,
	// Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
	//
	// final FrameLayout.LayoutParams frameLayoutLayoutParamsLogin = new
	// FrameLayout.LayoutParams(
	// FrameLayout.LayoutParams.WRAP_CONTENT,
	// FrameLayout.LayoutParams.WRAP_CONTENT,
	// Gravity.CENTER_HORIZONTAL | Gravity.TOP);
	//
	// final FrameLayout.LayoutParams frameLayoutLayoutParamsShare = new
	// FrameLayout.LayoutParams(
	// FrameLayout.LayoutParams.WRAP_CONTENT,
	// FrameLayout.LayoutParams.WRAP_CONTENT,
	// Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
	//
	// // DISPLAY ADD AT BOTTOM
	// FrameLayout.LayoutParams adViewLayoutParams = new
	// FrameLayout.LayoutParams(
	// FrameLayout.LayoutParams.WRAP_CONTENT,
	// FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.LEFT
	// | Gravity.BOTTOM);
	//
	// this.mRenderSurfaceView = new RenderSurfaceView(this);
	// mRenderSurfaceView.setRenderer(mEngine, this);
	//
	// final android.widget.FrameLayout.LayoutParams surfaceViewLayoutParams =
	// new FrameLayout.LayoutParams(
	// super.createSurfaceViewLayoutParams());
	//
	// frameLayout.addView(this.mRenderSurfaceView,
	// surfaceViewLayoutParams);
	//
	// // CHOOSE TYPE OF STARTAPP AD
	// Banner startAppBanner = new Banner(this);// INCLUDES 3D ROTATING
	// // BANNER
	// startAppBanner.hideBanner();
	// PublicityManagerSingleton.setStartAppBanner(startAppBanner);
	//
	// BannerStandard startAppBannerStandard = new BannerStandard(this);//
	// INCLUDES
	// // 3D
	// // ROTATING
	// // BANNER
	// startAppBannerStandard.hideBanner();
	// PublicityManagerSingleton.setStartAppBanneStandardr(
	// startAppBannerStandard, frameLayout, adViewLayoutParams);
	// // startAppBanner.setScaleX((float) 0.8);
	// // startAppBanner.setScaleY((float) 0.5);
	//
	// // BannerStandard startAppBanner = new
	// // BannerStandard(this);//TRADITIONAL BANNER ONLY
	//
	// frameLayout.addView(startAppBanner, adViewLayoutParams);
	// frameLayout.addView(startAppBannerStandard, adViewLayoutParams);
	//
	// loginButton = new LoginButton(this);
	// loginButton.setWidth(300);
	// loginButton.setHeight(100);
	// loginButton
	// .setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
	// @Override
	// public void onUserInfoFetched(GraphUser user) {
	// // HelloFacebookSampleActivity.this.user = user;
	// updateUIFacebook();
	// // It's possible that we were waiting for this.user
	// // to be populated in order to post a
	// // status update.
	// handlePendingAction();
	//
	// }
	// });
	// // frameLayout.addView(loginButton, frameLayoutLayoutParamsLogin);
	//
	// postPhotoButton = new Button(this);
	// postPhotoButton.setWidth(200);
	// postPhotoButton.setHeight(100);
	// postPhotoButton.setText("Share on Facebook");
	// postPhotoButton.setOnClickListener(new View.OnClickListener() {
	// public void onClick(View view) {
	// onClickPostPhoto();
	// }
	// });
	//
	// // frameLayout.addView(postPhotoButton,
	// // frameLayoutLayoutParamsShare);
	//
	// // hideFacebookLogin();
	//
	// this.setContentView(frameLayout, frameLayoutLayoutParams);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

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
		try {

			if (!mTrackers.containsKey(trackerId)) {

				GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
				Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics
						.newTracker(R.xml.app_tracker)
						: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics
								.newTracker(R.xml.global_tracker) : analytics
								.newTracker(R.xml.global_tracker);
				mTrackers.put(trackerId, t);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mTrackers.get(trackerId);
	}

	public static boolean isConnectingToInternet() {
		try {

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Text changeText(String text, Text textT, Font font) {
		try {

			float x = textT.getX();
			float y = textT.getY();

			textT.detachSelf();
			textT = ObjectFactorySingleton.getInstance().createText(text, font);
			textT.setPosition(x, y);

			return textT;

		} catch (Exception e) {
			e.printStackTrace();
			return textT;
		}

	}

	public static void verifyFreeGift() {
		try {

			if (UserInfoSingleton.getInstance().getDate() == null
					|| UserInfoSingleton.getInstance().getDate().isEmpty()) {

				UserInfoSingleton.getInstance().setLastLogin(
						ManageDate
								.formatDate(new Date(), ManageDate.YYYY_MM_DD));
				UserInfoSingleton.getInstance().increaseMoney(
						GamePurchaseConstant.FREE_DAILY_MONEY);

				InformativeHUD hud = new InformativeHUD("You have earned"
						+ GamePurchaseConstant.FREE_DAILY_MONEY
						+ " diamont, come back tomorrow!  ");
				HUDManagerSingleton.getInstance().addHUD(hud, true);
			}

			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			Date lastLogin = sdf.parse(UserInfoSingleton.getInstance()
					.getDate());

			if (lastLogin == null) {
				return;
			}

			String todayStr = ManageDate.formatDate(new Date(),
					ManageDate.YYYY_MM_DD);

			if (lastLogin.before(today)) {
				UserInfoSingleton.getInstance().increaseMoney(
						GamePurchaseConstant.FREE_DAILY_MONEY);
				Calendar c = Calendar.getInstance();
				c.setTime(today);
				c.add(Calendar.DAY_OF_YEAR, 1);
				today = c.getTime();

				UserInfoSingleton.getInstance().setLastLogin(
						ManageDate.formatDate(today, ManageDate.YYYY_MM_DD));

				FreeDailyRewardHUD dailyReaward = new FreeDailyRewardHUD(
						GamePurchaseConstant.FREE_DAILY_MONEY);
				HUDManagerSingleton.getInstance().addHUD(dailyReaward, true);
				/*
				 * lastLogin = sdf.parse(UserInfoSingleton.getInstance()
				 * .getDate());
				 * 
				 * 
				 * InformativeHUD hudInf = new
				 * InformativeHUD(ManageDate.formatDate(today,
				 * ManageDate.YYYY_MM_DD)+" : "+ManageDate.formatDate(lastLogin,
				 * ManageDate.YYYY_MM_DD) );
				 * HUDManagerSingleton.getInstance().addHUD(hudInf, true);
				 */
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			TwitterSingleton.getInstance().onCreateTwitter(this);
			initGooglePlayGames();

			createAddLayout(savedInstanceState);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createAddLayout(Bundle savedInstanceState) {
		try {
			
			setContentView(R.layout.activity_main);
			final DisplayMetrics displayMetrics = new DisplayMetrics();
			this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			
			AdView adview =  PublicityManagerSingleton.getInstance().createAdmobAdd(this);
			if(adview==null){
				System.out.println("MAIN DROP ACTIVITY  adview was null line 1440 create add Layout");
			}
			
			RelativeLayout layout = (RelativeLayout)findViewById(R.id.relativeLayout);
			
			//LayoutParams paramsRelative = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			//layout.setLayoutParams(paramsRelative);
			
			LayoutParams paramsLa = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(paramsLa);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			
			layout.addView(this.mRenderSurfaceView);
			layout.addView(adview, params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		try {

			// PublicityManagerSingleton.getInstance().onStart();
			// Presage.getInstance().start();
			GooglePlayGameSingleton.getInstance().onStart(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initGooglePlayGames() {
		try {

			GooglePlayGameSingleton.getInstance().init(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

		GooglePlayGameSingleton.getInstance().onConnectionFailed(
				connectionResult);
	}

	@Override
	public void onConnectionSuspended(int i) {
		// Attempt to reconnect
		GooglePlayGameSingleton.getInstance().onConnectionSuspended(i);
	}

	@Override
	protected void onStop() {
		super.onStop();
		GooglePlayGameSingleton.getInstance().onStop();
	}

	@Override
	public void onConnected(Bundle arg0) {
		try {

			InformativeHUD hud = new InformativeHUD("CONNECTED TO GOOGLE PLAY");
			HUDManagerSingleton.getInstance().addHUD(hud, true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean ismSignInClicked() {
		return mSignInClicked;
	}

	public void setmSignInClicked(boolean mSignInClicked) {
		this.mSignInClicked = mSignInClicked;
	}

	public void requestEmailAccountToSend() {
		try {

			if (UserInfoSingleton.getInstance().getEmail() == null
					|| UserInfoSingleton.getInstance().getEmail().isEmpty() || UserInfoSingleton.getInstance().getEmail().equals("null")) {
				Intent intent = AccountPicker.newChooseAccountIntent(null,
						null,
						new String[] { GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE },
						false, null, null, null, null);
				startActivityForResult(intent, REQUEST_CODE_EMAIL);
			} else {
				WebServiceSingleton.getInstance().sendEmail(
						UserInfoSingleton.getInstance().getEmail());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	
}
