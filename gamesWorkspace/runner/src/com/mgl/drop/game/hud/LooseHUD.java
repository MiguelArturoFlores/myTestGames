package com.mgl.drop.game.hud;

import org.andengine.engine.camera.Camera;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.LevelManager;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteRegresiveCount;
import com.mgl.drop.game.sprites.button.ResetButton;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.runner.R;

public class LooseHUD extends HUD {

	public LooseHUD(Scene scene, LevelManager levelManager,
			LevelController controller) {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();

			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());

			background.setSize(MainDropActivity.CAMERA_WIDTH,
					MainDropActivity.CAMERA_HEIGHT);
			background.setAlpha(0.5f);

			Camera camera = SceneManagerSingleton.getInstance().getCamera();

			Sprite backgroundBlack = new Sprite(0, 0,
					texture.getTextureByName("looseImage.png"),
					texture.getVertexBufferObjectManager());
			// backgroundBlack.setSize(camera.getWidth()/2f,
			// camera.getHeight());
			backgroundBlack.setPosition(0, 0);

			this.attachChild(background);

			this.attachChild(backgroundBlack);

			Text text = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity()
							.getResources().getString(R.string.looseRetry),
					texture.getmFont2());
			text.setPosition(camera.getWidth() / 2 - text.getWidth() / 2,
					camera.getHeight() - 200);
			text.setColor(Color.WHITE);
			this.attachChild(text);

			ResetButton resetButton = new ResetButton(120, 10,
					texture.getTextureByName("retry.png"),
					texture.getVertexBufferObjectManager(), scene, levelManager);
			resetButton.setSize(90, 90);
			resetButton.setPosition(
					camera.getWidth() / 2 - resetButton.getWidth() / 2,
					text.getY() + text.getHeight() + 10);

			this.registerTouchArea(resetButton);
			this.attachChild(resetButton);

			if (controller.getGameObjectController().getGameType() != GameConstants.PLAY_SURVIVAL) {
				SpriteRegresiveCount regresive = new SpriteRegresiveCount(0, 0,
						MainDropActivity.CAMERA_WIDTH, 220,
						texture.getTextureByName("black.jpg"),
						texture.getVertexBufferObjectManager(), this,
						controller);
				controller.addSpriteToUpdate(regresive);
				regresive.setAlpha(0.4f);
				this.attachChild(regresive);
			}

			this.registerTouchArea(background);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
