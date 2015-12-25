package com.mgl.drop.game.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ayoutuberunner.ButtonShareFacebook;
import com.mgl.drop.game.sprites.button.ayoutuberunner.ButtonShareWhatsapp;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.youtuberunner.R;

public class FacebookShareHUD extends HUD{

	private int quantity ;
	private int cont = 0;
	
	private Text quantityText;
	
	public FacebookShareHUD(){
		try {
			this.quantity = quantity;
			cont = 0;
			TextureSingleton texture = TextureSingleton.getInstance();
			
			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			background.setAlpha(0.9f);
			
			this.attachChild(background);
			
			Text text = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.SHARE_FACEBOOK_TO_EARN), TextureSingleton.getInstance().getmFont1());
			text.setPosition(MainDropActivity.CAMERA_WIDTH/2 - text.getWidth()/2, 100);
			this.attachChild(text);
			
			
			ButtonShareFacebook facebook =  new ButtonShareFacebook(0, 0, texture.getTextureByName("fb.png"), texture.getVertexBufferObjectManager());
			facebook.setPosition(MainDropActivity.CAMERA_WIDTH/2 - facebook.getWidth()/2,250);
			this.attachChild(facebook);
			this.registerTouchArea(facebook);
			
			
			quantityText= ObjectFactorySingleton.getInstance().createText(
					cont+"/"+quantity, TextureSingleton.getInstance().getmFont1());
			quantityText.setPosition(MainDropActivity.CAMERA_WIDTH/2 - quantityText.getWidth()/2, 160);
			//this.attachChild(quantityText);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void increaseCont() {
		try {
			cont++;
			if(cont>=quantity){
				HUDManagerSingleton.getInstance().removeAndReplaceHud();
				return;
			}
			
		//	quantityText.detachSelf();
			quantityText = MainDropActivity.changeText(cont+"/"+quantity, quantityText, TextureSingleton.getInstance().getmFont1());
			this.attachChild(quantityText);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
