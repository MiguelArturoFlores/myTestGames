package com.mgl.drop.game.scene;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.base.server.ServerSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.MainSceneController;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.objects.button.ButtonPlayNormal;
import com.mgl.drop.game.objects.button.ButtonPlaySurvival;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ButtonShop;
import com.mgl.drop.texture.TextureSingleton;

public class SceneSelectMode extends Scene {

	private TextureSingleton texture = TextureSingleton.getInstance();
	private float time = 0;
	private MainSceneController controller;

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
			ButtonPlaySurvival playSurvival = new ButtonPlaySurvival(100, 400,
					texture.getTextureByName(textureName),
					texture.getVertexBufferObjectManager());
			playSurvival.setZIndex(ZIndexGame.FADE);
			this.attachChild(playSurvival);

			// validat if is unlocked
			if(!UserInfoSingleton.getInstance().isSurvivalActive()){
				SpriteBackground lock = new SpriteBackground(0, 0, texture.getTextureByName("lockWand.png"), texture.getVertexBufferObjectManager());
				lock.setPosition(playSurvival.getWidth()/2 - lock.getWidth()/2, playSurvival.getHeight()/2-lock.getHeight()/2);
				playSurvival.attachChild(lock);
			}
			
			this.registerTouchArea(playSurvival);

			playNormal.registerEntityModifier(new MoveModifier(0.4f, playNormal
					.getX(), playNormal.getX(), playNormal.getHeight() * -1,
					playNormal.getY()));

			playSurvival.registerEntityModifier(new MoveModifier(0.4f,
					playSurvival.getX(), playSurvival.getX(),
					MainDropActivity.CAMERA_HEIGHT + playSurvival.getHeight(),
					playSurvival.getY()));

			controller = new MainSceneController(this);
			updateScene();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	private void updateScene() {
		try {

			this.registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() {
				}

				@Override
				public void onUpdate(float pSecondsElapsed) {
					// HERE IS THE GAME LOOP
					time += pSecondsElapsed;
					// System.out.println("this is the time elapsed MAIN SCENE: "+time);
					if (time >= 0.1) {
						controller.update(pSecondsElapsed, getScene());
						sortChildren();
					}

					// level.update(pSecondsElapsed, level);
					// game.update(pSecondsElapsed);
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Scene getScene() {
		return this;
	}
}
