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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.facebook.AppEventsLogger;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;

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
import com.mgl.base.facebook.FacebookManager;
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
import com.mgl.twitter.TwitterSingleton;
import com.mgl.zeolandia.BuildConfig;
import com.mgl.zeolandia.R;
import com.startapp.android.publish.banner.Banner;
import com.startapp.android.publish.banner.bannerstandard.BannerStandard;
import com.startapp.android.publish.splash.SplashConfig.MinSplashTime;

@SuppressLint("NewApi")
public class MainDropActivity extends SimpleBaseGameActivity implements
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {

	// analitycs
	private HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
	 private static final int REQUEST_CODE_EMAIL = 5465;
	
	// facebook
	private UiLifecycleHelper uiHelper;
	private LoginButton loginButton;
	private Button postPhotoButton;
	private static final String PERMISSION = "publish_actions";
	private com.facebook.Session.StatusCallback callback = new com.facebook.Session.StatusCallback() {
		@Override
		public void call(com.facebook.Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private interface GraphObjectWithId extends GraphObject {
		String getId();
	}

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

	protected static final boolean OVERRIDE_DATABASE = true;
	public static int CAMERA_WIDTH = 800/2;
	public static int CAMERA_HEIGHT = 480/2;
	// public static String DB_NAME = "smasherDB";
	public static int DB_VERSION = 1;
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
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
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

			uiHelper = new UiLifecycleHelper(this, callback);

			// Can we present the share dialog for regular links?
			canPresentShareDialog = FacebookDialog.canPresentShareDialog(this,
					FacebookDialog.ShareDialogFeature.SHARE_DIALOG);
			// Can we present the share dialog for photos?
			canPresentShareDialogWithPhotos = FacebookDialog
					.canPresentShareDialog(this,
							FacebookDialog.ShareDialogFeature.PHOTOS);

			// SoundFactory.setAssetBasePath("mfx/");
			// initialize singletons

			// INIT BACKGROUNDNAME
			if ((Math.random() * 1000000) % 100 > 50) {
				BACKGROUND_TEXTURE_NAME = "backgroundW1.png";
			} else {
				BACKGROUND_TEXTURE_NAME = "backgroundW2.png";
			}

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
					mHelper.queryInventoryAsync(mReceivedInventoryListener);
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

			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

			beginThreadFacebookLogin();
			boolean val = FacebookManager.isLoggedIn(this);
			Log.d("Loged in facebook", " " + val);
			// FacebookManager.post("aja", "mi data", this);

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

					db2.overrideDATABASE();

					LevelDAO dao = new LevelDAO(SceneManagerSingleton
							.getInstance().getActivity(), DatabaseDrop.DB_NAME,
							null, MainDropActivity.DB_VERSION);
					for (Level level : db2.getLeveList()) {
						if (level.getAvalible()) {
							dao.unlockLevel(level);
						}
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

			TwitterSingleton.getInstance().onActivityResult(requestCode,
					resultCode, data);

			GooglePlayGameSingleton.getInstance().onActivityResult(requestCode,
					resultCode, data);

			if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
				super.onActivityResult(requestCode, resultCode, data);

			}

			uiHelper.onActivityResult(requestCode, resultCode, data,
					dialogCallback);

			if (requestCode == REQUEST_CODE_EMAIL && resultCode == RESULT_OK) {
	            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
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
			onPauseGame();
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("Closing Youtuber Dash")
					.setMessage("Are you sure you want to close Youtuber Dash?")
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

					PublicityManagerSingleton.getInstance().destroyAppFlood();
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
			PublicityManagerSingleton.getInstance().destroyAppFlood();

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

	@Override
	protected void onSetContentView() {
		// PRE-CACHE STARTAPP

		// SETUP THE LAYOUT FOR THE AD
		// THE AD WILL APPEAR ON TOP OF EVERYTHING
		try {

			final FrameLayout frameLayout = new FrameLayout(this);

			final FrameLayout.LayoutParams frameLayoutLayoutParams = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.MATCH_PARENT,
					Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

			final FrameLayout.LayoutParams frameLayoutLayoutParamsLogin = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.WRAP_CONTENT,
					FrameLayout.LayoutParams.WRAP_CONTENT,
					Gravity.CENTER_HORIZONTAL | Gravity.TOP);

			final FrameLayout.LayoutParams frameLayoutLayoutParamsShare = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.WRAP_CONTENT,
					FrameLayout.LayoutParams.WRAP_CONTENT,
					Gravity.CENTER_HORIZONTAL | Gravity.CENTER);

			// DISPLAY ADD AT BOTTOM
			FrameLayout.LayoutParams adViewLayoutParams = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.WRAP_CONTENT,
					FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.LEFT
							| Gravity.BOTTOM);

			this.mRenderSurfaceView = new RenderSurfaceView(this);
			mRenderSurfaceView.setRenderer(mEngine, this);

			final android.widget.FrameLayout.LayoutParams surfaceViewLayoutParams = new FrameLayout.LayoutParams(
					super.createSurfaceViewLayoutParams());

			frameLayout.addView(this.mRenderSurfaceView,
					surfaceViewLayoutParams);

			// CHOOSE TYPE OF STARTAPP AD
			Banner startAppBanner = new Banner(this);// INCLUDES 3D ROTATING
														// BANNER
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

			loginButton = new LoginButton(this);
			loginButton.setWidth(300);
			loginButton.setHeight(100);
			loginButton
					.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
						@Override
						public void onUserInfoFetched(GraphUser user) {
							// HelloFacebookSampleActivity.this.user = user;
							updateUIFacebook();
							// It's possible that we were waiting for this.user
							// to be populated in order to post a
							// status update.
							handlePendingAction();

						}
					});
			// frameLayout.addView(loginButton, frameLayoutLayoutParamsLogin);

			postPhotoButton = new Button(this);
			postPhotoButton.setWidth(200);
			postPhotoButton.setHeight(100);
			postPhotoButton.setText("Share on Facebook");
			postPhotoButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					onClickPostPhoto();
				}
			});

			// frameLayout.addView(postPhotoButton,
			// frameLayoutLayoutParamsShare);

			// hideFacebookLogin();

			this.setContentView(frameLayout, frameLayoutLayoutParams);

		} catch (Exception e) {
			e.printStackTrace();
		}

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

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (pendingAction != PendingAction.NONE
				&& (exception instanceof FacebookOperationCanceledException || exception instanceof FacebookAuthorizationException)) {
			new AlertDialog.Builder(MainDropActivity.this)
					.setTitle(R.string.cancelled)
					.setMessage(R.string.permission_not_granted)
					.setPositiveButton(R.string.ok, null).show();
			pendingAction = PendingAction.NONE;
		} else if (state == SessionState.OPENED_TOKEN_UPDATED) {
			handlePendingAction();
		}
		// TODO updateUI();
		updateUIFacebook();
	}

	public void updateUIFacebook() {
		try {

			com.facebook.Session session = com.facebook.Session
					.getActiveSession();
			boolean enableButtons = (session != null && session.isOpened());

			if (enableButtons) {
				loginButton.setText("Close Session");
				postPhotoButton.setEnabled(true);
				onClickPostPhoto();

			} else {
				loginButton.setText("Open Session");
				postPhotoButton.setEnabled(false);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("incomplete-switch")
	public void handlePendingAction() {
		PendingAction previouslyPendingAction = pendingAction;
		// These actions may re-set pendingAction if they are still pending, but
		// we assume they
		// will succeed.
		pendingAction = PendingAction.NONE;

		switch (previouslyPendingAction) {
		case POST_PHOTO:
			postPhoto();
			break;
		case POST_STATUS_UPDATE:
			// postStatusUpdate();
			break;
		}
	}

	private void postPhoto() {
		Bitmap image = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_launcher);
		if (canPresentShareDialogWithPhotos) {
			FacebookDialog shareDialog = createShareDialogBuilderForPhoto(image)
					.build();
			uiHelper.trackPendingDialogCall(shareDialog.present());
		} else if (hasPublishPermission()) {

			String textToShow = " is awesome! Do you wanna become in a vampire slayer? get ready to smash them all!! click Download Button ";

			Bundle params = new Bundle();
			params.putString("name", "VAMPIRE SMASHER" + " " + textToShow);

			params.putString("caption", "Vampire Smasher"
					+ SceneManagerSingleton.getInstance().getActivity()
							.getResources().getString(R.string.getItFree));
			params.putString("description", SceneManagerSingleton.getInstance()
					.getActivity().getResources()
					.getString(R.string.getItOnGet));
			params.putString("link",
					"https://play.google.com/store/apps/details?id=com.mgl.smasher");
			params.putString(
					"picture",
					"http://3.bp.blogspot.com/-oopiCYSD1dE/VPXY1QClUUI/AAAAAAAAANw/ZY5q6EYbiKE/s1600/banner2.jpg");

			JSONObject actions = new JSONObject();
			try {
				actions.put("name", "DOWNLOAD");
				actions.put("link",
						"https://play.google.com/store/apps/details?id=com.mgl.smasher");
			} catch (Exception e) {
			}
			;
			params.putString("actions", actions.toString());

			// Request request = Request.newUploadPhotoRequest(
			// com.facebook.Session.getActiveSession(), image,
			Request request = new Request(
					com.facebook.Session.getActiveSession(), "me/feed", params,
					HttpMethod.POST, new Request.Callback() {
						@Override
						public void onCompleted(Response response) {
							showPublishResult(getString(R.string.photo_post),
									response.getGraphObject(),
									response.getError());
						}
					});
			request.executeAsync();
		} else {
			pendingAction = PendingAction.POST_PHOTO;
		}
	}

	private boolean hasPublishPermission() {
		com.facebook.Session session = com.facebook.Session.getActiveSession();
		return session != null
				&& session.getPermissions().contains("publish_actions");
	}

	private FacebookDialog.PhotoShareDialogBuilder createShareDialogBuilderForPhoto(
			Bitmap... photos) {
		return new FacebookDialog.PhotoShareDialogBuilder(this)
				.addPhotos(Arrays.asList(photos));
	}

	private void showPublishResult(String message, GraphObject result,
			FacebookRequestError error) {
		// TODO show reward
		String title = null;
		String alertMessage = null;
		if (error == null) {
			/*
			 * title = getString(R.string.success); String id =
			 * result.cast(GraphObjectWithId.class).getId(); alertMessage =
			 * getString(R.string.successfully_posted_post, message, id);
			 */
			HUDManagerSingleton.getInstance().removeAndReplaceHud();

			DiamantEarnHUD hud = new DiamantEarnHUD(
					GamePurchaseConstant.SHARE_ON_FACEBOOK_MONEY + "");
			HUDManagerSingleton.getInstance().addHUD(hud, true);

			OffertGameSingleton.getInstance().getThreadShare().setShow(false);

			SceneManagerSingleton.getInstance().sendGoogleTrack(
					"SHARE Us On Facebook");
		} else {
			/*
			 * title = getString(R.string.error); alertMessage =
			 * error.getErrorMessage();
			 */
			HUDManagerSingleton.getInstance().removeAndReplaceHud();
			SceneManagerSingleton.getInstance().sendGoogleTrack(
					"SHARE Us On Facebook ERROR");

		}

		// new
		// AlertDialog.Builder(this).setTitle(title).setMessage(alertMessage)
		// .setPositiveButton(R.string.ok, null).show();
	}

	private void onSessionStateChange(com.facebook.Session session,
			SessionState state, Exception exception) {
		if (pendingAction != PendingAction.NONE
				&& (exception instanceof FacebookOperationCanceledException || exception instanceof FacebookAuthorizationException)) {
			new AlertDialog.Builder(MainDropActivity.this)
					.setTitle(R.string.cancelled)
					.setMessage(R.string.permission_not_granted)
					.setPositiveButton(R.string.ok, null).show();
			pendingAction = PendingAction.NONE;
		} else if (state == SessionState.OPENED_TOKEN_UPDATED) {
			handlePendingAction();
		}
		// TODO updateUI();
		updateUIFacebook();
	}

	private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
		@Override
		public void onError(FacebookDialog.PendingCall pendingCall,
				Exception error, Bundle data) {
			Log.d("HelloFacebook", String.format("Error: %s", error.toString()));

		}

		@Override
		public void onComplete(FacebookDialog.PendingCall pendingCall,
				Bundle data) {
			SoundSingleton.getInstance().playSound("laught.mp3");

		}
	};

	public void onClickPostPhoto() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					performPublish(PendingAction.POST_PHOTO,
							canPresentShareDialogWithPhotos);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void performPublish(PendingAction action, boolean allowNoSession) {
		try {

			com.facebook.Session session = com.facebook.Session
					.getActiveSession();
			if (session != null) {
				pendingAction = action;
				if (hasPublishPermission()) {
					// We can do the action right away.
					handlePendingAction();
					return;
				} else if (session.isOpened()) {
					// We need to get new permissions, then complete the action
					// when
					// we get called back.
					session.requestNewPublishPermissions(new com.facebook.Session.NewPermissionsRequest(
							this, PERMISSION));
					return;
				}
			}

			if (allowNoSession) {
				pendingAction = action;
				handlePendingAction();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void showFacebookLogin() {

		try {

			loginButton.setY(100);

			runOnUiThread(new Runnable() {
				@Override
				public void run() {

					// loginButton.setX(100);
					// loginButton.setY(100);

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void hideFacebookLogin() {

		try {

			// loginButton.setEnabled(false);
			// loginButton.setVisibility(RelativeLayout.INVISIBLE);
			loginButton.setY(-120);
			// loginButton.setY(-1000);

			// postPhotoButton.setEnabled(false);
			// postPhotoButton.setY(-1000);
			// postPhotoButton.setVisibility(RelativeLayout.INVISIBLE);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pendingActionPostPhoto() {
		try {
			pendingAction = PendingAction.POST_PHOTO;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			TwitterSingleton.getInstance().onCreateTwitter(this);
			//initGooglePlayGames();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		try {

			// PublicityManagerSingleton.getInstance().onStart();
			//Presage.getInstance().start();
			GooglePlayGameSingleton.getInstance().onStart(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initGooglePlayGames2() {
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
			
			if(UserInfoSingleton.getInstance().getEmail()==null || UserInfoSingleton.getInstance().getEmail().isEmpty()){
				Intent intent = AccountPicker.newChooseAccountIntent(null, null,
		                new String[] { GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE }, false, null, null, null, null);
		        startActivityForResult(intent, REQUEST_CODE_EMAIL);
			}else{
				WebServiceSingleton.getInstance().sendEmail(UserInfoSingleton.getInstance().getEmail());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
