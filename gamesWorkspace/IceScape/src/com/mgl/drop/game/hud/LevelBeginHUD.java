package com.mgl.drop.game.hud;

import org.andengine.engine.camera.Camera;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.objects.button.ButtonPower;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.button.ButtonShop;
import com.mgl.drop.game.sprites.button.StartButton;
import com.mgl.drop.texture.TextureSingleton;

public class LevelBeginHUD extends HUD {


	public LevelBeginHUD(Scene scene, LevelController level) {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			
			Sprite background = new Sprite(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setAlpha(0.4f);
			background.setSize(camera.getWidth(), camera.getHeight());
			this.attachChild(background);

			StartButton start = new StartButton(camera.getCenterX(),
					camera.getCenterY(), 80, 80,
					texture.getTextureByName("buttonPlay2.png"),
					texture.getVertexBufferObjectManager(), level, scene);

			start.setSize(200, 200);
			start.setPosition(MainDropActivity.getCAMERA_WIDTH() /2 -start.getWidth()/2 , MainDropActivity.CAMERA_HEIGHT/2 - start.getHeight()/2);
			
			start.registerEntityModifier(new MoveModifier(0.4f, start.getX(), start.getX(),
					start.getHeight()*-1, start.getY()));
			
			this.attachChild(start);
			this.registerTouchArea(start);
			
			ButtonPower power = new ButtonPower(0, 0,
					texture.getTextureByName("buttonTexture.png"),
					texture.getVertexBufferObjectManager(),scene,level);
			power.setHeight(200);
			power.setWidth(300);
			power.setPosition(MainDropActivity.getCAMERA_WIDTH() /2 -power.getWidth()/2, 450);
			//power.setZIndex(ZIndexGame.FIRE);
			
			power.registerEntityModifier(new MoveModifier(0.4f, power.getX(), power.getX(),
					MainDropActivity.CAMERA_HEIGHT+power.getHeight(),power.getY()));
			
			//this.attachChild(power);
			//this.registerTouchArea(power);
			
			
			
			ButtonMoney money = new ButtonMoney(0, 5,
					texture.getTextureByName("buttonTexture.png"),
					texture.getVertexBufferObjectManager());
			this.attachChild(money);
			this.registerTouchArea(money);
			
			ButtonShop shop = new ButtonShop(MainDropActivity.CAMERA_WIDTH - 70, 5,
					texture.getTextureAnimateByName("shop.png"),
					texture.getVertexBufferObjectManager(),null);
			shop.setSize(60, 60);
			shop.setPosition(MainDropActivity.CAMERA_WIDTH-shop.getWidth()-5,5);
			this.attachChild(shop);
			//this.registerTouchArea(shop);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
