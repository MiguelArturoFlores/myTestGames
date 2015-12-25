package com.mgl.drop.game.scene;

import java.util.ArrayList;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import com.mgl.base.MySprite;
import com.mgl.base.userinfo.HelpSingleton;
import com.mgl.base.userinfo.ScoreSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ButtonNavigatePurchase;
import com.mgl.drop.game.sprites.button.ButtonShop;
import com.mgl.drop.texture.TextureSingleton;

public class SceneScore extends Scene {

	TextureSingleton texture = TextureSingleton.getInstance();
	private ArrayList<MySprite> spriteList;

	public SceneScore() {
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

			ButtonMoney money = UserInfoSingleton.getInstance().getButtonMoney();
			money.setZIndex(ZIndexGame.FADE);
			

			// TODO add shop

			spriteList = ScoreSingleton.getInstance().getSpriteList();
			
			SpriteBackground backAux = new SpriteBackground(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager());
			backAux.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			backAux.setAlpha(0.6f);
			this.attachChild(backAux);

			ButtonShop shop = new ButtonShop(MainDropActivity.CAMERA_WIDTH - 70, 5,
					texture.getTextureAnimateByName("shop.png"),
					texture.getVertexBufferObjectManager(),null);
			
			shop.setZIndex(ZIndexGame.FADE);
			shop.setSize(60, 60);
			shop.setPosition(MainDropActivity.CAMERA_WIDTH-shop.getWidth()-5,5);
			this.attachChild(shop);
			this.registerTouchArea(shop);
			
			this.attachChild(money);
			this.registerTouchArea(money);
			
			navigate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void navigate() {
		try {

			int screenCont = 0;
			
			for(MySprite spr : spriteList ){
				
				spr.setPosition((MainDropActivity.CAMERA_WIDTH/2 - spr.getWidth()/2)+(MainDropActivity.CAMERA_WIDTH*screenCont),100);
				if(spr.hasParent()){
					spr.detachSelf();
				}
				
				spr.setX((MainDropActivity.CAMERA_WIDTH/2 - spr.getWidth()/2));
				spr.setY(screenCont *spr.getHeight()+5+100);
				this.attachChild(spr);
				this.registerTouchArea(spr);
				screenCont ++;
			}
			
//			ButtonNavigatePurchase navigateNext = new ButtonNavigatePurchase(MainDropActivity.CAMERA_WIDTH-60, MainDropActivity.CAMERA_HEIGHT/2 -60, texture.getTextureByName("next.png"), texture.getVertexBufferObjectManager(), spriteList, true);
//			this.attachChild(navigateNext);
//			this.registerTouchArea(navigateNext);
//			
//			ButtonNavigatePurchase navigatePrev = new ButtonNavigatePurchase(0, MainDropActivity.CAMERA_HEIGHT/2 -60, texture.getTextureByName("next.png"), texture.getVertexBufferObjectManager(), spriteList, false);
//			this.attachChild(navigatePrev);
//			this.registerTouchArea(navigatePrev);
//			
//			navigateNext.setSize(60, 60);
//			navigatePrev.setSize(60, 60);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}