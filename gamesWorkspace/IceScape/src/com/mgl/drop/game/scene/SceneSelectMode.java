package com.mgl.drop.game.scene;

import org.andengine.engine.handler.IUpdateHandler;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.base.server.ServerSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.objects.button.ButtonPlayNormal;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ButtonShop;
import com.mgl.drop.texture.TextureSingleton;

public class SceneSelectMode extends Scene {

	private TextureSingleton texture = TextureSingleton.getInstance();
	private float time = 0;

	public SceneSelectMode() {
		try {

			createScene();
			ServerSingleton.getInstance().testServer();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Scene createScene() {
		try {

			PublicityManagerSingleton.getInstance().showBanner();
			PublicityManagerSingleton.getInstance().showIntersitial();

			Sprite background = new Sprite(
					0,
					0,
					texture.getTextureByName(MainDropActivity.BACKGROUND_TEXTURE_NAME),
					texture.getVertexBufferObjectManager());
			background.setZIndex(ZIndexGame.BACKGROUND);
			this.attachChild(background);

//			Sprite fade = new Sprite(
//					0,
//					0,
//					texture.getTextureByName(MainDropActivity.BACKGROUND_FADE_NAME),
//					texture.getVertexBufferObjectManager());
//			fade.setZIndex(ZIndexGame.FADE);
//			this.attachChild(fade);

			Sprite backgroundBlack = new Sprite(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			backgroundBlack.setAlpha(0.4f);
			backgroundBlack.setSize(MainDropActivity.CAMERA_WIDTH,
					MainDropActivity.CAMERA_HEIGHT);
			backgroundBlack.setZIndex(ZIndexGame.FADE);
			this.attachChild(backgroundBlack);

			ButtonMoney money = UserInfoSingleton.getInstance().getButtonMoney();
			money.setZIndex(ZIndexGame.FADE);
			this.attachChild(money);
			this.registerTouchArea(money);

			ButtonShop shop = new ButtonShop(
					MainDropActivity.CAMERA_WIDTH - 70, 5,
					texture.getTextureAnimateByName("shop.png"),
					texture.getVertexBufferObjectManager(), null);

			shop.setZIndex(ZIndexGame.FADE);
			shop.setSize(60, 60);
			shop.setPosition(MainDropActivity.CAMERA_WIDTH - shop.getWidth()
					- 5, 5);
			this.attachChild(shop);
			this.registerTouchArea(shop);

			ButtonPlayNormal playNormal = new ButtonPlayNormal(100, 200,
					texture.getTextureByName("buttonTextureRed.png"),
					texture.getVertexBufferObjectManager());
			playNormal.setZIndex(ZIndexGame.FADE);
			this.attachChild(playNormal);
			this.registerTouchArea(playNormal);
			
			String textureName = "buttonTextureRed.png";
			if(!UserInfoSingleton.getInstance().isSurvivalActive()){
				textureName = "buttonTextureGrey.png";
			}

			playNormal.registerEntityModifier(new MoveModifier(0.4f, playNormal
					.getX(), playNormal.getX(), playNormal.getHeight() * -1,
					playNormal.getY()));



		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}


	private Scene getScene() {
		return this;
	}
}
