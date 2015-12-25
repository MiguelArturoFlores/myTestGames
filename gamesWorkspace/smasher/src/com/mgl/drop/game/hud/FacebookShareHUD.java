package com.mgl.drop.game.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.share.ButtonShareFacebook;
import com.mgl.drop.game.sprites.button.share.ButtonShareTwitter;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.smasher.R;

public class FacebookShareHUD  extends HUD {

	public FacebookShareHUD() {
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
			text.setPosition(MainDropActivity.CAMERA_WIDTH/2 - text.getWidth()/2, 150);
			this.attachChild(text);
			
			Text textEarn = ObjectFactorySingleton.getInstance().createText("Diamonts x 25", TextureSingleton.getInstance().getmFont1());
			textEarn.setPosition(MainDropActivity.CAMERA_WIDTH/2 - textEarn.getWidth()/2, text.getHeight()+text.getY()+50);
			this.attachChild(textEarn);
			
			ButtonShareFacebook login = new ButtonShareFacebook(0, 0, TextureSingleton.getInstance().getTextureByName("fb.png"), TextureSingleton.getInstance().getVertexBufferObjectManager());
			login.setSize(200, 200);
			login.setPosition(MainDropActivity.CAMERA_WIDTH/2 - login.getWidth()/2, MainDropActivity.CAMERA_HEIGHT/2 - login.getHeight()/2);
			
			this.attachChild(login);
			this.registerTouchArea(login);
			
			this.registerTouchArea(background);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}