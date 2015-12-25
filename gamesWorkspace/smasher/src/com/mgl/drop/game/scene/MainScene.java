package com.mgl.drop.game.scene;

import java.security.MessageDigest;


import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.common.SignInButton;
import com.google.example.games.basegameutils.ButtonAchievements;
import com.google.example.games.basegameutils.ButtonLeaderboard;
import com.google.example.games.basegameutils.ButtonLogInGoogle;
import com.google.example.games.basegameutils.GooglePlayButtonBar;
import com.google.example.games.basegameutils.GooglePlayGameSingleton;
import com.mgl.base.userinfo.OffertGameSingleton;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GamePurchaseConstant;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.MainSceneController;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.objects.button.ButtonPlay;
import com.mgl.drop.game.sprites.SpriteMoneyFree;
import com.mgl.drop.game.sprites.button.ButtonHelp;
import com.mgl.drop.game.sprites.button.ButtonHighScore;
import com.mgl.drop.game.sprites.button.ButtonMoreGame;
import com.mgl.drop.game.sprites.button.ButtonRate;
import com.mgl.drop.game.sprites.button.ButtonTrophy;
import com.mgl.drop.game.sprites.button.DisableMusicButton;
import com.mgl.drop.game.sprites.button.EnableMusicButton;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.ManageDate;

public class MainScene extends Scene {

	private Camera camera;
	private ObjectFactorySingleton objectFactorySingleton = ObjectFactorySingleton
			.getInstance();
	private TextureSingleton texture = TextureSingleton.getInstance();
	private float time = 0;

	private MainSceneController controller;

	public MainScene(Camera camera) {
		super();
		try {
			this.camera = camera;
			PoolObjectSingleton.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Scene createScene() {
		try {

			 printDebugHASHKey();

			Sprite background = new Sprite(
					0,
					0,
					texture.getTextureByName(MainDropActivity.BACKGROUND_TEXTURE_NAME),
					texture.getVertexBufferObjectManager());
			background.setZIndex(ZIndexGame.BACKGROUND);
			this.attachChild(background);

			Sprite fade = new Sprite(
					0,
					0,
					texture.getTextureByName(MainDropActivity.BACKGROUND_FADE_NAME),
					texture.getVertexBufferObjectManager());
			fade.setZIndex(ZIndexGame.FADE);
			this.attachChild(fade);

			Sprite title = new Sprite(0, 0,
					texture.getTextureByName("title.png"),
					texture.getVertexBufferObjectManager());
			title.setZIndex(ZIndexGame.FADE);
			title.setSize(MainDropActivity.CAMERA_WIDTH - 100,
					MainDropActivity.CAMERA_HEIGHT / 3 - 50);
			title.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - title.getWidth() / 2,
					100);

			title.registerEntityModifier(new MoveModifier(0.4f, title.getX(),
					title.getX(), title.getHeight() * -1, title.getY()));
			this.attachChild(title);

			ButtonMoreGame moreGame = new ButtonMoreGame(0, 0,
					texture.getTextureByName("buttonTextureRed.png"),
					texture.getVertexBufferObjectManager());

			moreGame.setZIndex(ZIndexGame.FIRE);
			this.attachChild(moreGame);
			this.registerTouchArea(moreGame);

			ButtonPlay btnPlay = new ButtonPlay(0, 0,
					texture.getTextureByName("buttonPlay2.png"),
					texture.getVertexBufferObjectManager());
			registerTouchArea(btnPlay);
			btnPlay.setSize(150, 150);
			this.attachChild(btnPlay);

			btnPlay.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - btnPlay.getWidth() / 2,
					title.getY() + title.getHeight() + 50);
			btnPlay.setZIndex(ZIndexGame.FIRE);

			btnPlay.registerEntityModifier(new MoveModifier(0.4f, btnPlay
					.getX(), btnPlay.getX(), MainDropActivity.CAMERA_HEIGHT
					+ btnPlay.getHeight(), btnPlay.getY()));

			ButtonHelp help = new ButtonHelp(0, 0,
					texture.getTextureByName("buttonTextureRed.png"),
					texture.getVertexBufferObjectManager());
			help.setZIndex(ZIndexGame.FIRE);
			help.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - btnPlay.getWidth() / 2,
					btnPlay.getY() + help.getHeight() + 100);
			help.registerEntityModifier(new MoveModifier(0.4f, help.getX(),
					help.getX(), MainDropActivity.CAMERA_HEIGHT
							+ help.getHeight(), help.getY()));
			this.attachChild(help);
			registerTouchArea(help);

			moreGame.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - moreGame.getWidth() / 2,
					help.getY() + help.getHeight() + 10);
			moreGame.registerEntityModifier(new MoveModifier(0.4f, moreGame
					.getX(), moreGame.getX(), MainDropActivity.CAMERA_HEIGHT
					+ moreGame.getHeight(), moreGame.getY()));

			ButtonHighScore score = new ButtonHighScore(0, 0,
					texture.getTextureByName("score.png"),
					texture.getVertexBufferObjectManager());
			score.setZIndex(ZIndexGame.FIRE);
			score.setSize(100, 100);
			score.setPosition(MainDropActivity.CAMERA_WIDTH - score.getWidth()
					- 5, help.getY() + help.getHeight() + 10);
			score.registerEntityModifier(new MoveModifier(0.4f, score.getX(),
					score.getX(), MainDropActivity.CAMERA_HEIGHT
							+ score.getHeight(), score.getY()));
			this.attachChild(score);
			registerTouchArea(score);

			ButtonTrophy trophy = new ButtonTrophy(0, 0,
					texture.getTextureByName("cadizT.png"),
					texture.getVertexBufferObjectManager());
			trophy.setZIndex(ZIndexGame.FIRE);
			trophy.setSize(100, 100);
			trophy.setPosition(MainDropActivity.CAMERA_WIDTH - score.getWidth()
					- 5, score.getY() - trophy.getHeight() - 10);
			trophy.registerEntityModifier(new MoveModifier(0.4f, trophy.getX(),
					trophy.getX(), MainDropActivity.CAMERA_HEIGHT
							+ trophy.getHeight(), trophy.getY()));
			this.attachChild(trophy);
			registerTouchArea(trophy);

			addButtonRate(
					MainDropActivity.CAMERA_WIDTH - trophy.getWidth() - 5,
					trophy.getY() - 10);

			DisableMusicButton disable = new DisableMusicButton(
					camera.getWidth() - 240, 20,
					texture.getTextureByName("offBegin.png"),
					texture.getVertexBufferObjectManager(), this);
			disable.setSize(100, 100);
			disable.setPosition(20, moreGame.getY());

			EnableMusicButton enable = new EnableMusicButton(
					camera.getWidth() - 240, 20,
					texture.getTextureByName("onBegin.png"),
					texture.getVertexBufferObjectManager(), this);
			enable.setSize(100, 100);
			enable.setPosition(20, moreGame.getY());

			enable.setButton(disable);
			disable.setButton(enable);

			enable.setZIndex(ZIndexGame.FIRE);
			disable.setZIndex(ZIndexGame.FIRE);

			// PublicityManagerSingleton.getInstance().showIntersitial();

			controller = new MainSceneController(this);
			updateScene();

			if (SoundSingleton.getInstance().isHasSound()) {
				this.attachChild(disable);
			} else {
				this.attachChild(enable);
			}
			
			

			SpriteMoneyFree moneyFree = new SpriteMoneyFree(0, 0, texture.getTextureByName("freeMoney.png"), texture.getVertexBufferObjectManager());
			moneyFree.setSize(100, 100);
			moneyFree.setZIndex(ZIndexGame.FADE);
			moneyFree.setPosition(MainDropActivity.CAMERA_WIDTH-moneyFree.getWidth(), 5);
			
			this.attachChild(moneyFree);
			this.registerTouchArea(moneyFree);
			
			if (SoundSingleton.getInstance().isHasSound()) {
				this.registerTouchArea(disable);
			} else {
				this.registerTouchArea(enable);
			}

			initGoogleButtons();
			
			
			
			return this;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	private void initGoogleButtons() {
		try {
			
			GooglePlayButtonBar buttonBar = new GooglePlayButtonBar(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager(), this);
			buttonBar.setPosition(10, 15);
			buttonBar.setZIndex(ZIndexGame.FADE);
			this.attachChild(buttonBar);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addButtonRate(float x, float y) {
		try {

			if(!OffertGameSingleton.getInstance().canIShowRateMe()){
				return;
			}
			
			if(UserInfoSingleton.getInstance().showRateme()){
				ButtonRate rate = new ButtonRate(0, 0,
						texture.getTextureByName("rate.png"),
						texture.getVertexBufferObjectManager());
				rate.setZIndex(ZIndexGame.FIRE);
				rate.setSize(100, 75);
				rate.setPosition(x, y- rate.getHeight());
				rate.registerEntityModifier(new MoveModifier(0.4f, rate.getX(),
						rate.getX(), MainDropActivity.CAMERA_HEIGHT
								+ rate.getHeight(), rate.getY()));
				this.attachChild(rate);
					registerTouchArea(rate);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateScene() {
		try {
			this.registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() {
				}

				@Override
				public void onUpdate(float pSecondsElapsed) {
					// HERE IS THE GAME LOOP
					time += pSecondsElapsed;
					// System.out.println("this is the time elapsed MAIN SCENE: "+time);
					if (time >= 0.1) {
						controller.update(pSecondsElapsed, getScene());
						sortChildren();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Scene getScene() {
		return this;
	}

	public static void printDebugHASHKey() {
		PackageInfo info;
		try {
			Log.i("hash key", "abc1");
			info = SceneManagerSingleton
					.getInstance()
					.getActivity()
					.getPackageManager()
					.getPackageInfo("com.mgl.smasher",
							PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md;
				md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String something = new String(Base64.encode(md.digest(), 0));
				// String something = new
				// String(Base64.encodeBytes(md.digest()));
				
					//Log.d("hash key", something);
					//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD(something),true);
				
				//FacebookManager.facebookLogin(SceneManagerSingleton.getInstance().getActivity(), FacebookManager.SHARE_VOLUNTARY, null);
				
				//ThY2laAVgVPyZToZo19mybtnuig=
	 //production FePXsB9Epgg1V4wfwx4zjhjjTME=
			}
		} catch (NameNotFoundException e1) {
			Log.e("name not found", e1.toString());
		} catch (NoSuchAlgorithmException e) {
			Log.e("no such an algorithm", e.toString());
		} catch (Exception e) {
			Log.e("exception", e.toString());
		}

	}

}
