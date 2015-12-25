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

			//printDebugHASHKey();

			Sprite background = new Sprite(
					0,
					0,
					texture.getTextureByName(MainDropActivity.BACKGROUND_TEXTURE_NAME),
					texture.getVertexBufferObjectManager());
			background.setZIndex(ZIndexGame.BACKGROUND);
			this.attachChild(background);

			SpriteTitle title = new SpriteTitle(0, 0, texture.getTextureByName("title.png"),texture.getVertexBufferObjectManager(),null);
			//title.setSize(480, 550);
			title.setPosition(MainDropActivity.CAMERA_WIDTH/2 - title.getWidth()/2,MainDropActivity.CAMERA_HEIGHT/2 - title.getHeight()/2);
			title.setZIndex(ZIndexGame.FADE);
			this.attachChild(title);
			this.registerTouchArea(title);
			
			title.setPosition(title.getX(), title.getHeight()*-1);
			
			title.registerEntityModifier(new MoveModifier(0.2f,title.getX(),title.getX(),title.getY(),MainDropActivity.CAMERA_HEIGHT/2 - title.getHeight()/2));
			
			controller = new MainSceneController(this);
			updateScene();

			Text text = ObjectFactorySingleton.getInstance().createText(
					"Tap to Continue", TextureSingleton.getInstance().getmFont1());
			text.setZIndex(ZIndexGame.FADE);
			text.setPosition(MainDropActivity.CAMERA_WIDTH/2 - text.getWidth()/2 , MainDropActivity.CAMERA_HEIGHT - 100);
			this.attachChild(text);
			
			return this;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	private void addButtonRate(float x, float y) {
		try {


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
