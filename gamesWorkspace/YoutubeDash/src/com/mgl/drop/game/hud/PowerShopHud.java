package com.mgl.drop.game.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.button.ButtonShop;
import com.mgl.drop.game.sprites.button.StartButton;
import com.mgl.drop.texture.TextureSingleton;

public class PowerShopHud extends HUD {

	public PowerShopHud( LevelController level,Scene scene) {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();

			float yOffset = -50;
			float alpha = 0.65f;

			Sprite backgroundBlack = new Sprite(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			backgroundBlack.setAlpha(0.8f);
			backgroundBlack.setSize(MainDropActivity.CAMERA_WIDTH,
					MainDropActivity.CAMERA_HEIGHT);
			this.attachChild(backgroundBlack);

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
			this.registerTouchArea(shop);

			
			if(level == null){
				return;
			}
			StartButton start = new StartButton(0, 725f, 150f, 120f,
					texture.getTextureByName("buttonPlay2.png"),
					texture.getVertexBufferObjectManager(), level, scene);
			start.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - start.getWidth() / 2,
					5);

			
			this.attachChild(start);
			this.registerTouchArea(start);
			
			SceneManagerSingleton.getInstance().sendGoogleTrack("Tienda de Poderes");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
