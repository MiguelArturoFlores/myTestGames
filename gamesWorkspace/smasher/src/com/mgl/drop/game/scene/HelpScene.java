package com.mgl.drop.game.scene;

import java.util.ArrayList;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import com.mgl.base.MySprite;
import com.mgl.base.userinfo.HelpSingleton;
import com.mgl.base.userinfo.PurchaseSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ButtonNavigatePurchase;
import com.mgl.drop.texture.TextureSingleton;

public class HelpScene extends Scene {

	TextureSingleton texture = TextureSingleton.getInstance();
	private ArrayList<MySprite> spriteList;

	public HelpScene() {
		try {

			spriteList = new ArrayList<MySprite>();
			
			createScene();
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void createScene() {
		try {

			Sprite background = new Sprite(
					0,
					0,
					texture.getTextureByName(MainDropActivity.BACKGROUND_TEXTURE_NAME),
					texture.getVertexBufferObjectManager());
			background.setZIndex(ZIndexGame.BACKGROUND);
			this.attachChild(background);

			Sprite fade = new Sprite(
					0,
					0,
					texture.getTextureByName(MainDropActivity.BACKGROUND_FADE_NAME),
					texture.getVertexBufferObjectManager());
			fade.setZIndex(ZIndexGame.FADE);
			this.attachChild(fade);

			//
			
			ButtonMoney money = UserInfoSingleton.getInstance().getButtonMoney();
			this.attachChild(money);
			this.registerTouchArea(money);

			// TODO add shop

			spriteList = HelpSingleton.getInstance().getSpriteList();
			
			SpriteBackground backAux = new SpriteBackground(0, 0, texture.getTextureByName("blackBackground.png"), texture.getVertexBufferObjectManager());
			this.attachChild(backAux);

			navigate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void navigate() {
		try {

			int screenCont = 0;
			
			for(MySprite spr : spriteList ){
				
				spr.setPosition((MainDropActivity.CAMERA_WIDTH/2 - spr.getWidth()/2)+(MainDropActivity.CAMERA_WIDTH*screenCont),MainDropActivity.CAMERA_HEIGHT/2 - spr.getHeight()/2);
				if(spr.hasParent()){
					spr.detachSelf();
				}
				
				//spr.setWidth(MainDropActivity.CAMERA_WIDTH/2);
				//spr.setHeight(200);
				spr.setX((MainDropActivity.CAMERA_WIDTH/2 - spr.getWidth()/2)+(MainDropActivity.CAMERA_WIDTH*screenCont));
				this.attachChild(spr);
				this.registerTouchArea(spr);
				screenCont ++;
			}
			
			ButtonNavigatePurchase navigateNext = new ButtonNavigatePurchase(MainDropActivity.CAMERA_WIDTH-60, MainDropActivity.CAMERA_HEIGHT/2 -60, texture.getTextureByName("next.png"), texture.getVertexBufferObjectManager(), spriteList, true);
			this.attachChild(navigateNext);
			this.registerTouchArea(navigateNext);
			
			ButtonNavigatePurchase navigatePrev = new ButtonNavigatePurchase(0, MainDropActivity.CAMERA_HEIGHT/2 -60, texture.getTextureByName("prev.png"), texture.getVertexBufferObjectManager(), spriteList, false);
			this.attachChild(navigatePrev);
			this.registerTouchArea(navigatePrev);
			
			navigateNext.setSize(60, 60);
			navigatePrev.setSize(60, 60);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
