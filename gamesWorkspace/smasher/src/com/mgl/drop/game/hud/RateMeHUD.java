package com.mgl.drop.game.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;

import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ButtonRate;
import com.mgl.drop.texture.TextureSingleton;

public class RateMeHUD extends MyHud {

	public RateMeHUD() {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();
			
			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			background.setAlpha(0.6f);
			
			this.attachChild(background);
			
			SpriteBackground backAux = new SpriteBackground(0, 0,
					texture.getTextureByName("verticalBackgroundGeneral.png"),
					texture.getVertexBufferObjectManager());
			backAux.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);

			this.attachChild(backAux);
			
			
			Text text = ObjectFactorySingleton.getInstance().createText(
					"Do you like Vampire Smasher?", TextureSingleton.getInstance().getmFont1());
			text.setPosition(MainDropActivity.CAMERA_WIDTH/2 - text.getWidth()/2, 250);
			Text text1 = ObjectFactorySingleton.getInstance().createText(
					"Please Rate", TextureSingleton.getInstance().getmFont1());
			text1.setPosition(MainDropActivity.CAMERA_WIDTH/2 - text1.getWidth()/2, text.getY()+ text.getHeight()+5);
			
			ButtonRate rate = new ButtonRate(0, 0,texture.getTextureByName("rate.png"), texture.getVertexBufferObjectManager());
			rate.setPosition(MainDropActivity.CAMERA_WIDTH/2 - rate.getWidth()/2,  text1.getY()+ text1.getHeight()+25);
			
			
			this.attachChild(text);
			this.attachChild(text1);
			
			this.attachChild(rate);
			this.registerTouchArea(rate);
			
			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0,
					texture.getTextureByName("closeHud.png"),
					texture.getVertexBufferObjectManager(), this);
			remove.setSize(70, 70);
			remove.setPosition(MainDropActivity.CAMERA_WIDTH - remove.getWidth() - 20, 20);
			this.attachChild(remove);
			this.registerTouchArea(remove);
			
			
			this.registerTouchArea(background);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onCloseAction() {
		
		try {
			
			UserInfoSingleton.getInstance().noShowRate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
