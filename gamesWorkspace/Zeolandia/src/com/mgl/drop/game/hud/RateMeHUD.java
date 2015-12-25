package com.mgl.drop.game.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;

import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ButtonRate;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.zeolandia.R;

public class RateMeHUD extends MyHud {

	public RateMeHUD() {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();
			
			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			background.setAlpha(0.9f);
			
			this.attachChild(background);
			
			
			Text text = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.RATE_MESSAGE), TextureSingleton.getInstance().getmFont1());
			text.setPosition(MainDropActivity.CAMERA_WIDTH/2 - text.getWidth()/2, 75);
			Text text1 = ObjectFactorySingleton.getInstance().createText(
					"Please Rate", TextureSingleton.getInstance().getmFont1());
			text1.setPosition(MainDropActivity.CAMERA_WIDTH/2 - text1.getWidth()/2, text.getY()+ text.getHeight()+5);
			
			
			this.attachChild(text);
			this.attachChild(text1);
			
			
			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0,
					texture.getTextureByName("closeHud.png"),
					texture.getVertexBufferObjectManager(), this);
			remove.setSize(70, 70);
			remove.setPosition(MainDropActivity.CAMERA_WIDTH - remove.getWidth() - 20, 20);
			this.attachChild(remove);
			this.registerTouchArea(remove);
			
			addFiveStars();
			
			this.registerTouchArea(background);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void addFiveStars() {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			
			ButtonRate rate1 = new ButtonRate(0, 0,texture.getTextureByName("fillStar.png"), texture.getVertexBufferObjectManager(),1);
			rate1.setSize(60, 60);
			rate1.setRealRate(false);
			rate1.setPosition(150,  MainDropActivity.CAMERA_HEIGHT/2 - rate1.getHeight()/2);
			this.attachChild(rate1);
			this.registerTouchArea(rate1);
			
			ButtonRate rate2 = new ButtonRate(0, 0,texture.getTextureByName("fillStar.png"), texture.getVertexBufferObjectManager(),2);
			rate2.setSize(70, 70);
			rate2.setRealRate(false);
			rate2.setPosition(rate1.getX()+rate1.getWidth()+5,  MainDropActivity.CAMERA_HEIGHT/2 - rate1.getHeight()/2);
			this.attachChild(rate2);
			this.registerTouchArea(rate2);
			
			ButtonRate rate3 = new ButtonRate(0, 0,texture.getTextureByName("fillStar.png"), texture.getVertexBufferObjectManager(),3);
			rate3.setSize(80, 80);
			rate3.setRealRate(false);
			rate3.setPosition(rate2.getX()+rate2.getWidth()+5,  MainDropActivity.CAMERA_HEIGHT/2 - rate1.getHeight()/2);
			this.attachChild(rate3);
			this.registerTouchArea(rate3);
			
			ButtonRate rate4 = new ButtonRate(0, 0,texture.getTextureByName("fillStar.png"), texture.getVertexBufferObjectManager(),4);
			rate4.setSize(90, 90);
			rate4.setRealRate(false);
			rate4.setPosition(rate3.getX()+rate3.getWidth()+5,  MainDropActivity.CAMERA_HEIGHT/2 - rate1.getHeight()/2);
			this.attachChild(rate4);
			this.registerTouchArea(rate4);
			
			ButtonRate rate5 = new ButtonRate(0, 0,texture.getTextureByName("fillStar.png"), texture.getVertexBufferObjectManager(),5);
			rate5.setSize(120, 120);
			rate5.setRealRate(true);
			rate5.setPosition(rate4.getX()+rate4.getWidth()+5,  MainDropActivity.CAMERA_HEIGHT/2 - rate1.getHeight()/2);
			this.attachChild(rate5);
			this.registerTouchArea(rate5);
			
			
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
