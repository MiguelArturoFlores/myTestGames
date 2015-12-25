package com.mgl.drop.game.hud;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.LevelManager;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ContinueButton;
import com.mgl.drop.game.sprites.button.DisableMusicButton;
import com.mgl.drop.game.sprites.button.EnableMusicButton;
import com.mgl.drop.game.sprites.button.ResetButton;
import com.mgl.drop.game.sprites.button.SelectLevelButton;
import com.mgl.drop.texture.TextureSingleton;

public class PauseHUD extends HUD {

	public PauseHUD(Scene scene, LevelManager levelManager) {
		try {

			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			TextureSingleton texture = TextureSingleton.getInstance();

			Sprite spriteBack = new Sprite(-100, 0,
					texture.getTextureByName("pauseMenu.png"),
					texture.getVertexBufferObjectManager());
			spriteBack.setSize(150, camera.getHeight());

			spriteBack.setAlpha(0.9f);
			spriteBack.registerEntityModifier(new MoveModifier(0.2f, -150, 0,
					0, 0));

			SpriteBackground background = new SpriteBackground(80, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setAlpha(0.4f);
			background.setSize(camera.getWidth()+150, camera.getHeight() * 2);

			
			spriteBack.attachChild(background);

			int offset = 90;

			ContinueButton continueButton = new ContinueButton(0, 0,
					texture.getTextureByName("buttonPlay.png"),
					texture.getVertexBufferObjectManager(), scene, spriteBack);
			continueButton.setSize(60, 60);
			continueButton.setPosition(20, 50);

			ResetButton resetButton = new ResetButton(camera.getWidth() - 40,
					160, texture.getTextureByName("retry.png"),
					texture.getVertexBufferObjectManager(), scene, levelManager);
			resetButton.setSize(60, 60);
			resetButton.setPosition(20, continueButton.getY() + 60 + 40);

			SelectLevelButton selectLevelButton = new SelectLevelButton(
					camera.getWidth() - 40, 220,
					texture.getTextureByName("selectLevel.png"),
					texture.getVertexBufferObjectManager(), scene);
			selectLevelButton.setSize(60, 60);
			selectLevelButton.setPosition(20, resetButton.getY() + 60 + 40);

			DisableMusicButton disable = new DisableMusicButton(
					camera.getWidth() - 240, 20,
					texture.getTextureByName("soundOff.png"),
					texture.getVertexBufferObjectManager(),  this);
			disable.setSize(60, 60);
			disable.setPosition(20, selectLevelButton.getY() + 60 + 40);

			EnableMusicButton enable = new EnableMusicButton(
					camera.getWidth() - 240, 20,
					texture.getTextureByName("soundOn.png"),
					texture.getVertexBufferObjectManager(),  this);
			enable.setSize(60, 60);
			enable.setPosition(20, selectLevelButton.getY() + 60 + 40);
			

			enable.setButton(disable);
			disable.setButton(enable);

			spriteBack.attachChild(continueButton);
			spriteBack.attachChild(selectLevelButton);
			spriteBack.attachChild(resetButton);
			if (SoundSingleton.getInstance().isHasSound()) {
				spriteBack.attachChild(disable);
			} else {
				spriteBack.attachChild(enable);
			}

			
			this.registerTouchArea(continueButton);
			this.registerTouchArea(selectLevelButton);
			this.registerTouchArea(resetButton);
			if (SoundSingleton.getInstance().isHasSound()) {
				this.registerTouchArea(disable);
			} else {
				this.registerTouchArea(enable);
			}

			this.attachChild(spriteBack);
			this.registerTouchArea(background);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
