package com.mgl.drop.game.scene;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.objects.button.ButtonPlay;
import com.mgl.drop.game.sprites.button.ButtonMoreGame;
import com.mgl.drop.game.sprites.button.ButtonShareFacebook;
import com.mgl.drop.object.type.ObjectType;
import com.mgl.drop.texture.TextureSingleton;

public class MainScene extends Scene {

	private Camera camera;
	private ObjectFactorySingleton objectFactorySingleton = ObjectFactorySingleton
			.getInstance();
	private TextureSingleton texture = TextureSingleton.getInstance();

	public MainScene(Camera camera) {
		super();
		try {
			this.camera = camera;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Scene createScene() {
		try {

			
			//printDebugHASHKey();

			Sprite background = new Sprite(0, 0,
					texture.getTextureByName("backgroundBegin.png"),
					texture.getVertexBufferObjectManager());
			this.attachChild(background);

			ButtonMoreGame moreGame = new ButtonMoreGame(0, 0,
					texture.getTextureByName("buttonMoreGamesRed.png"),
					texture.getVertexBufferObjectManager());
			moreGame.setPosition(3, MainDropActivity.getCAMERA_HEIGHT()
					- moreGame.getHeight());
			this.attachChild(moreGame);
			this.registerTouchArea(moreGame);

			ButtonPlay btnPlay = new ButtonPlay(0, 0,
					texture.getTextureByName("play.png"),
					texture.getVertexBufferObjectManager());
			registerTouchArea(btnPlay);
			this.attachChild(btnPlay);

			btnPlay.setPosition(250, 120);

//			ButtonShareFacebook bS = new ButtonShareFacebook(
//					btnPlay.getX() + 120, 120, btnPlay.getY(), 100,
//					texture.getTextureByName("shareOrange.png"),
//					texture.getVertexBufferObjectManager(), null);
//			
//			bS.setHeight(btnPlay.getHeight());
//			bS.setPosition(btnPlay.getX() + btnPlay.getWidth() + 10,
//					btnPlay.getY());
			//this.attachChild(bS);

			//this.registerTouchArea(bS);
			// PublicityManagerSingleton.getInstance().showIntersitial();

			return this;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void printDebugHASHKey() {
		PackageInfo info;
		try {
			Log.d("hash key", "abc1");
			info = SceneManagerSingleton
					.getInstance()
					.getActivity()
					.getPackageManager()
					.getPackageInfo("com.mgl.drop",
							PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md;
				md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String something = new String(Base64.encode(md.digest(), 0));
				// String something = new
				// String(Base64.encodeBytes(md.digest()));
				Log.d("hash key", something);
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
