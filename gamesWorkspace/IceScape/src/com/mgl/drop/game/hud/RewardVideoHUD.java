package com.mgl.drop.game.hud;

import org.andengine.engine.camera.hud.HUD;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ButtonViewVideo;
import com.mgl.drop.texture.TextureSingleton;

public class RewardVideoHUD extends HUD {

	public RewardVideoHUD() {

		try {

			TextureSingleton texture = TextureSingleton.getInstance();

			SpriteBackground backgroundAux = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			backgroundAux.setSize(MainDropActivity.CAMERA_WIDTH,
					MainDropActivity.CAMERA_HEIGHT);

			this.attachChild(backgroundAux);
			backgroundAux.setAlpha(0.7f);
			
			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("videoReward.png"),
					texture.getVertexBufferObjectManager());
			background.setSize(MainDropActivity.CAMERA_WIDTH,
					MainDropActivity.CAMERA_HEIGHT);

			this.attachChild(background);

		/*	Text text = ObjectFactorySingleton.getInstance().createText(
					"We have some free diamants for you",
					TextureSingleton.getInstance().getmFont1());
			text.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - text.getWidth() / 2,
					250);
			Text text1 = ObjectFactorySingleton.getInstance().createText(
					"Watch this video to get it!",
					TextureSingleton.getInstance().getmFont1());
			text1.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - text1.getWidth() / 2,
					text.getY() + text.getHeight() + 5);
*/
			ButtonViewVideo rate = new ButtonViewVideo(0, 0,
					texture.getTextureByName("video.png"),
					texture.getVertexBufferObjectManager());
			rate.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - rate.getWidth() / 2,
					305);

			//this.attachChild(text);
			//this.attachChild(text1);

			this.attachChild(rate);
			this.registerTouchArea(rate);

			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0,
					texture.getTextureByName("closeHud.png"),
					texture.getVertexBufferObjectManager(), this);
			remove.setSize(70, 70);
			remove.setPosition(
					MainDropActivity.CAMERA_WIDTH - remove.getWidth() - 20, 20);
			this.attachChild(remove);
			this.registerTouchArea(remove);

			this.registerTouchArea(background);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
