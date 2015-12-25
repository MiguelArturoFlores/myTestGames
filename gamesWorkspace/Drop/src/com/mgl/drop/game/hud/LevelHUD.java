package com.mgl.drop.game.hud;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import com.mgl.drop.game.LevelManager;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.button.PauseButton;
import com.mgl.drop.game.sprites.button.ResetButton;
import com.mgl.drop.texture.TextureSingleton;

public class LevelHUD extends HUD {

	public LevelHUD(Scene scene, LevelManager levelManager) {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();
			Camera camera = SceneManagerSingleton.getInstance().getCamera();

			
			PauseButton pasuseButton = new PauseButton(15,
					0 , texture.getTextureByName("orangePause.png"),
					texture.getVertexBufferObjectManager(), scene,levelManager);
			pasuseButton.setAlpha(0.8f);
			pasuseButton.setSize(50, 60);

			ResetButton resetButton = new ResetButton(camera.getWidth() - 40,
					160, texture.getTextureByName("tryAgainOrange.png"),
					texture.getVertexBufferObjectManager(), scene, levelManager);
			resetButton.setSize(50, 60);
			resetButton.setPosition(75,0);
			resetButton.setAlpha(0.8f);
			
			Sprite background = new Sprite(0,0,
					texture.getTextureByName("gray.jpg"),
					texture.getVertexBufferObjectManager());
			background.setAlpha(0f);
			background.setSize(camera.getWidth(), 70);

			this.attachChild(background);
			this.attachChild(pasuseButton);
			
			this.attachChild(resetButton);

			this.registerTouchArea(pasuseButton);
			this.registerTouchArea(resetButton);



		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
