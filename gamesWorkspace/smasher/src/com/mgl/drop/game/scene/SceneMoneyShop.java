package com.mgl.drop.game.scene;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import com.mgl.base.MySprite;
import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.base.userinfo.PurchaseSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.GamePurchaseConstant;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.database.model.MyPurchase;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.objects.button.ButtonPlayNormal;
import com.mgl.drop.game.objects.button.ButtonPlaySurvival;
import com.mgl.drop.game.sprites.SpriteSelectLevel;
import com.mgl.drop.game.sprites.button.ButtonBuyMoney;
import com.mgl.drop.game.sprites.button.ButtonNavigatePurchase;
import com.mgl.drop.game.sprites.button.ButtonOffert;
import com.mgl.drop.texture.TextureSingleton;

public class SceneMoneyShop extends Scene {

	private TextureSingleton texture = TextureSingleton.getInstance();
	private ArrayList<MySprite> spriteList;

	private int screenCont = 0;

	public SceneMoneyShop() {
		try {

			screenCont = 0;
			createScene();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Scene createScene() {
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
			
			this.attachChild(money);
			this.registerTouchArea(money);

			ButtonOffert offert = new ButtonOffert(
					MainDropActivity.CAMERA_WIDTH - 100,5,
					texture.getTextureAnimateByName("offert.png"),
					texture.getVertexBufferObjectManager(), null);
			offert.setPosition(
					MainDropActivity.CAMERA_WIDTH - 10 - offert.getWidth(), 5);
			this.attachChild(offert);
			this.registerTouchArea(offert);
			offert.setSize(offert.getWidth(), offert.getHeight()-20);
			spriteList = PurchaseSingleton.getInstance().getSpriteList();

			navigate();
			// TODO https://www.youtube.com/watch?v=tICD7YqssX8

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public void navigate() {
		try {

			int i = 0;
			int j = 0;

			for (MySprite spr : spriteList) {

				if(spr.hasParent()){
					spr.detachSelf();
				}
				spr.setPosition((220 * i) + 25
						+ (MainDropActivity.CAMERA_WIDTH * screenCont),
						(215 * j) + 90);
				this.attachChild(spr);
				this.registerTouchArea(spr);
				i++;

				if (i >= 2) {
					i = 0;
					j++;
				}

				if (j >= 3) {
					i = 0;
					j = 0;
					screenCont++;
				}
			}

			ButtonNavigatePurchase navigateNext = new ButtonNavigatePurchase(
					MainDropActivity.CAMERA_WIDTH - 60,
					MainDropActivity.CAMERA_HEIGHT - 60,
					texture.getTextureByName("next.png"),
					texture.getVertexBufferObjectManager(), spriteList, true);
			this.attachChild(navigateNext);
			this.registerTouchArea(navigateNext);

			ButtonNavigatePurchase navigatePrev = new ButtonNavigatePurchase(0,
					MainDropActivity.CAMERA_HEIGHT - 60,
					texture.getTextureByName("prev.png"),
					texture.getVertexBufferObjectManager(), spriteList, false);
			this.attachChild(navigatePrev);
			this.registerTouchArea(navigatePrev);

			navigateNext.setSize(60, 60);
			navigatePrev.setSize(60, 60);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
