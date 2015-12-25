package com.mgl.drop.game.hud;

import java.util.ArrayList;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.text.Text;

import com.mgl.base.MySprite;
import com.mgl.base.userinfo.PurchaseSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;

public class OffertTimeHUD extends HUD{

	
	private TextureSingleton texture  = TextureSingleton.getInstance();

	private int time = 10;
	private float contTime = 0 ;
	
	
	private Text timeText;
	private MySprite sprite;
	
	public OffertTimeHUD(){
		try {
			
			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			 background.setAlpha(0.7f);
			background.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);

			this.attachChild(background);
			
			time = 10;
			
			SpriteBackground offertBackground = new SpriteBackground(0, 0,
					texture.getTextureByName("blackBackground.png"),
					texture.getVertexBufferObjectManager());
//			offertBackground.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT/2);
//			offertBackground.setPosition(0,MainDropActivity.CAMERA_HEIGHT/4);
			this.attachChild(offertBackground);
			
			
			timeText = ObjectFactorySingleton.getInstance().createText(" "+time+"s",
					texture.getmFont1());
			timeText.setPosition(MainDropActivity.CAMERA_WIDTH/2 - timeText.getWidth()/2, MainDropActivity.CAMERA_HEIGHT/4 +10);
			this.attachChild(timeText);
			
			loadOffert();
			
			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0,
					texture.getTextureByName("closeHud.png"),
					texture.getVertexBufferObjectManager(), this);
			remove.setSize(70, 70);
			remove.setPosition(MainDropActivity.CAMERA_WIDTH - remove.getWidth() - 20, 20);

			this.attachChild(remove);

			this.registerTouchArea(remove);
			
			this.registerTouchArea(background);
			
			update();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private void loadOffert() {
		try {
			
			ArrayList<MySprite> sprList = PurchaseSingleton.getInstance().getSpriteOffertList();
			Double val = (Math.random()*10000000)%(sprList.size());
			sprite = sprList.get(val.intValue());
			
			if(sprite==null){
				return;
			}
			if(sprite.hasParent()){
				sprite.detachSelf();
			}
			sprite.setPosition(MainDropActivity.CAMERA_WIDTH/2 - sprite.getWidth()/2, MainDropActivity.CAMERA_HEIGHT/2 +50 - sprite.getHeight()/2);
			this.attachChild(sprite);
			this.registerTouchArea(sprite);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update() {
		try {
			
			this.registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() {
				}

				
				@Override
				public void onUpdate(float pSecondsElapsed) {
					
					try {
						
						if(time<0){
							HUDManagerSingleton.getInstance().removeAndReplaceHud();
							
							
						}
						
						contTime = contTime + pSecondsElapsed;
						if(contTime>1){
							time --;
							contTime = 0;
							if(timeText != null && timeText.hasParent()){
								timeText.detachSelf();
							}
							
							timeText = ObjectFactorySingleton.getInstance().createText("Seconds "+time,
									texture.getmFont1());
							timeText.setPosition(MainDropActivity.CAMERA_WIDTH/2 - timeText.getWidth()/2, MainDropActivity.CAMERA_HEIGHT/4 +10);
							if(timeText == null){
								return;
							}
							getHUD().attachChild(timeText);
							
							contTime = 0;
						}
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
	}
	
	public HUD getHUD(){
		return this;
	}
	
	
}
