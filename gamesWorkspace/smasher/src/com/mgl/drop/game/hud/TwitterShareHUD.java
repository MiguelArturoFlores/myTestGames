package com.mgl.drop.game.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ButtonLoginTwitter;
import com.mgl.drop.game.sprites.button.share.ButtonShareTwitter;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.smasher.R;

public class TwitterShareHUD  extends HUD {

	public TwitterShareHUD() {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();
			
			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			background.setAlpha(0.9f);
			
			this.attachChild(background);
		
			Text text = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.SHARE), TextureSingleton.getInstance().getmFont1());
			text.setPosition(MainDropActivity.CAMERA_WIDTH/2 - text.getWidth()/2, 100);
			this.attachChild(text);
			
			Text textShare = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.SHARE_TWITTER), TextureSingleton.getInstance().getmFont1());
			textShare.setPosition(MainDropActivity.CAMERA_WIDTH/2 - textShare.getWidth()/2, MainDropActivity.CAMERA_HEIGHT/2 - textShare.getHeight()/2);
			this.attachChild(textShare);
			
			ButtonShareTwitter login = new ButtonShareTwitter(0	, 0, TextureSingleton.getInstance().getTextureByName("twttr.png"), TextureSingleton.getInstance().getVertexBufferObjectManager());
			login.setSize(200, 200);
			login.setPosition(MainDropActivity.CAMERA_WIDTH/2 - login.getWidth()/2, textShare.getY()+textShare.getHeight()+25);
			
			this.attachChild(login);
			this.registerTouchArea(login);
			
			this.registerTouchArea(background);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
