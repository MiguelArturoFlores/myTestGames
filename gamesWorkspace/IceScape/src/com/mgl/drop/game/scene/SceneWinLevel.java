package com.mgl.drop.game.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.texture.TextureSingleton;

public class SceneWinLevel extends Scene{

	
	private Camera camera;
	private ObjectFactorySingleton objectFactorySingleton = ObjectFactorySingleton
			.getInstance();
	private TextureSingleton texture = TextureSingleton.getInstance();
	private SceneManagerSingleton sceneManager = SceneManagerSingleton.getInstance();
	
	private LevelController level;
	
	private float time = 0;
	
	public SceneWinLevel(LevelController level){
		try {
			this.level = level;
			time = 0;
			Sprite background = new Sprite(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager());
			background.setAlpha(0.6f);
			this.attachChild(background);
			updateScene();
		} catch (Exception e) {
			System.out.println("error SceneWinlevel");
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
					time+=pSecondsElapsed;
					 //System.out.println("this is the time elapsed MAIN SCENE: "+time);
					 if(time>=2){
						 sceneManager.setCurrentScene(AllScenes.SELECT_LEVEL);
					 }
					// level.update(pSecondsElapsed, level);
					// game.update(pSecondsElapsed);
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
