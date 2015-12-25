package com.mgl.drop.game.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ayoutuberunner.ButtonLoginTwitter;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.zeolandia.R;

public class TwitterLoginHUD extends HUD {

	public TwitterLoginHUD() {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();
			
			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			background.setAlpha(0.9f);
			
			this.attachChild(background);
		
			Text text = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.LOGIN_WITH_TWITTER_TO_EARN), TextureSingleton.getInstance().getmFont1());
			text.setPosition(MainDropActivity.CAMERA_WIDTH/2 - text.getWidth()/2, 100);
			this.attachChild(text);
			
			SpriteBackground diamant = new SpriteBackground(0, 0,
					texture.getTextureByName("moreMoney.png"),
					texture.getVertexBufferObjectManager());
			
			diamant.setPosition(MainDropActivity.CAMERA_WIDTH/2 - diamant.getWidth()/2, MainDropActivity.CAMERA_HEIGHT/2 - diamant.getHeight()/2);
			this.attachChild(diamant);
			
			ButtonLoginTwitter login = new ButtonLoginTwitter(0	, 0, TextureSingleton.getInstance().getTextureByName("twttr.png"), TextureSingleton.getInstance().getVertexBufferObjectManager());
			login.setSize(150, 150);
			login.setPosition(MainDropActivity.CAMERA_WIDTH/2 - login.getWidth()/2, diamant.getY()+diamant.getHeight()+25);
			
			this.attachChild(login);
			this.registerTouchArea(login);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
