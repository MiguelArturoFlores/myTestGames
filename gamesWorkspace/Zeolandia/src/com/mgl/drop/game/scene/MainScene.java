package com.mgl.drop.game.scene;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.google.example.games.basegameutils.GooglePlayButtonBar;
import com.mgl.base.facebook.FacebookManager;
import com.mgl.base.server.ServerSingleton;
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
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteMoneyFree;
import com.mgl.drop.game.sprites.arunner.SpriteTitle;
import com.mgl.drop.game.sprites.azeoland.SpriteAnimator;
import com.mgl.drop.game.sprites.button.ButtonHelp;
import com.mgl.drop.game.sprites.button.ButtonHighScore;
import com.mgl.drop.game.sprites.button.ButtonMoreGame;
import com.mgl.drop.game.sprites.button.ButtonRate;
import com.mgl.drop.game.sprites.button.ButtonShareFacebook;
import com.mgl.drop.game.sprites.button.ButtonTrophy;
import com.mgl.drop.game.sprites.button.DisableMusicButton;
import com.mgl.drop.game.sprites.button.EnableMusicButton;
import com.mgl.drop.game.sprites.button.ayoutuberunner.ButtonLoginTwitter;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.ManageDate;
import com.mgl.twitter.TwitterSingleton;
import com.mgl.zeolandia.R;

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

			//printDebugHASHKey();

			
			int animationParam = 150;
			
			SpriteTitle title = new SpriteTitle(0, 0, texture.getTextureByName("title.png"),texture.getVertexBufferObjectManager(),null, new long[] {animationParam,animationParam,animationParam,animationParam,animationParam, animationParam});
//			title.setSize(500, 300);
			title.init(true);
			title.setPosition(MainDropActivity.CAMERA_WIDTH/2 - title.getWidth()/2,MainDropActivity.CAMERA_HEIGHT/2 - title.getHeight()/2 -20);
			title.setZIndex(ZIndexGame.FADE);
			this.attachChild(title);
			this.registerTouchArea(title);
			
			
//			title.setPosition(title.getX(), title.getHeight()*-1);
//			title.registerEntityModifier(new MoveModifier(0.2f,title.getX(),title.getX(),title.getY(),MainDropActivity.CAMERA_HEIGHT/2 - title.getHeight()/2));
			//controller = new MainSceneController(this);
			//updateScene();
			
//			Text text = ObjectFactorySingleton.getInstance().createText(
//					SceneManagerSingleton.getInstance().getActivity().getString(R.string.TAP_TO_CONTINUE), TextureSingleton.getInstance().getmFont1());
//			text.setZIndex(ZIndexGame.FADE);
//			text.setPosition(MainDropActivity.CAMERA_WIDTH/2 - text.getWidth()/2 , MainDropActivity.CAMERA_HEIGHT - 100);
//			this.attachChild(text);
//			
//			ButtonLoginTwitter loginTwitter = new ButtonLoginTwitter(0, 0, texture.getTextureByName("twttr.png"), texture.getVertexBufferObjectManager());
//			loginTwitter.setSize(100, 100);
//			this.attachChild(loginTwitter);
//			this.registerTouchArea(loginTwitter);
			
			
			SpriteAnimator textStar = new SpriteAnimator(0, 0, texture.getTextureByName("titleText.png"), texture.getVertexBufferObjectManager(), null, new long [] {animationParam,animationParam});
			textStar.setPosition(MainDropActivity.CAMERA_WIDTH/2 - textStar.getWidth()/2, title.getY()+title.getHeight() +10);
			textStar.init(true);
			
			this.attachChild(textStar);
			return this;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	private void initGoogleButtons() {
		try {
			
			GooglePlayButtonBar buttonBar = new GooglePlayButtonBar(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager(), this);
			buttonBar.setPosition(150, 15);
			buttonBar.setZIndex(ZIndexGame.FADE);
			this.attachChild(buttonBar);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void addButtonRate(float x, float y) {
		try {
			//TODO show hud instead of show this button;
			if(!OffertGameSingleton.getInstance().canIShowRateMe()){
				return;
			}
			
			if(UserInfoSingleton.getInstance().showRateme()){
				ButtonRate rate = new ButtonRate(0, 0,
						texture.getTextureByName("rate.png"),
						texture.getVertexBufferObjectManager(),3);
				rate.setZIndex(ZIndexGame.FADE);
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
