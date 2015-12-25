package com.mgl.drop.game.hud;

import java.util.ArrayList;

import org.andengine.engine.camera.hud.HUD;
import com.mgl.base.MySprite;
import com.mgl.base.userinfo.PurchaseSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ButtonNavigatePurchase;
import com.mgl.drop.texture.TextureSingleton;

public class OffertHUD extends HUD{

	private ArrayList<MySprite> offertList;

	private int screenCont = 0;
	private TextureSingleton texture = TextureSingleton.getInstance();
	
	public OffertHUD(){
		try {
			
			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			 background.setAlpha(0.7f);
			background.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);

			this.attachChild(background);
			
			
			SpriteBackground offertBackground = new SpriteBackground(0, 0,
					texture.getTextureByName("blackBackground.png"),
					texture.getVertexBufferObjectManager());
//			offertBackground.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT/2);
//			offertBackground.setPosition(0,MainDropActivity.CAMERA_HEIGHT/4);
			this.attachChild(offertBackground);
			
			initOffertNavigate();
			
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

	private void initOffertNavigate() {
		try {
			
			offertList = new ArrayList<MySprite>();
			offertList = PurchaseSingleton.getInstance().getSpriteOffertList();
					
			int i = 0;
			int j = 0;

			for(MySprite spr : offertList ){
				
				spr.setPosition((MainDropActivity.CAMERA_WIDTH/2 - spr.getWidth()/2)+(MainDropActivity.CAMERA_WIDTH*screenCont),MainDropActivity.CAMERA_HEIGHT/2 - spr.getHeight()/2);
				if(spr.hasParent()){
					spr.detachSelf();
				}
				this.attachChild(spr);
				this.registerTouchArea(spr);
				i++;
				screenCont++;
			}
			
			ButtonNavigatePurchase navigateNext = new ButtonNavigatePurchase(MainDropActivity.CAMERA_WIDTH-60, MainDropActivity.CAMERA_HEIGHT/2 -60, texture.getTextureByName("next.png"), texture.getVertexBufferObjectManager(), offertList, true);
			this.attachChild(navigateNext);
			this.registerTouchArea(navigateNext);
			
			ButtonNavigatePurchase navigatePrev = new ButtonNavigatePurchase(0, MainDropActivity.CAMERA_HEIGHT/2 -60, texture.getTextureByName("prev.png"), texture.getVertexBufferObjectManager(), offertList, false);
			this.attachChild(navigatePrev);
			this.registerTouchArea(navigatePrev);
			
			navigateNext.setSize(60, 60);
			navigatePrev.setSize(60, 60);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
