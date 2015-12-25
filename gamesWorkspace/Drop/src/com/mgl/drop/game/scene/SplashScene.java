package com.mgl.drop.game.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.object.type.ObjectType;
import com.mgl.drop.texture.TextureSingleton;

public class SplashScene extends Scene {

	private Camera camera;
	private ObjectFactorySingleton objectFactorySingleton = ObjectFactorySingleton
			.getInstance();
	private TextureSingleton texture = TextureSingleton.getInstance();
	private SceneManagerSingleton sceneManager = SceneManagerSingleton.getInstance();

	//atts
	private float time = 0;
	MainDropActivity mainDropActivity;
	private boolean finish = false;
	
	public SplashScene(Camera camera, MainDropActivity mainDropActivity) {
		this.mainDropActivity = mainDropActivity;
	}

	public Scene createScene() {
		try {
			
			time = 0;
			
			Thread.sleep(2000);
			
			/*Sprite background = new Sprite(0, 0, TextureSingleton.getInstance().getTextureByName("backgroundBegin.png"), TextureSingleton.getInstance().getVertexBufferObjectManager());
			background.setSize(800, 480);
			this.attachChild(background);*/

			
		
			Text textPlay = objectFactorySingleton.createText("LOADING ...",
					texture.getmFont2());
			textPlay.setColor(Color.RED);
			textPlay.setPosition(200, 200);
		
			this.attachChild(textPlay);

			mainDropActivity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					try {
						texture.loadTextures();
						mainDropActivity.loadPublicitySound();
						finish = true;
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});
			
			
			updateScene();

			return this;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
					 //if(time>=6){
					if(finish){
						 sceneManager.createMainScene();
						 sceneManager.setCurrentScene(AllScenes.MAIN);
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
