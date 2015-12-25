package com.mgl.drop.game.scene;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.Texture;

import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;

public class BattleScene extends Scene{

	float time = 0;
	private Long idLevel;
	private LevelController levelControllerExternal;
	private LevelController battleController;
	private TextureSingleton texture = TextureSingleton.getInstance();
	
	
	public BattleScene(Long idLevel, LevelController levelController){
		try {
			
			this.levelControllerExternal = levelController;
			this.idLevel = idLevel;
			time = 0;
			battleController = new LevelController(this);
			createBattleScene();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createBattleScene() {
		try {
			
			battleController.loadLevelBattle(idLevel,levelControllerExternal);
			updateScene();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
						
						battleController.update(pSecondsElapsed);
						time = 0;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
