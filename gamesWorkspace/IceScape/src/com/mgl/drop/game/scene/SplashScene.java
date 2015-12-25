package com.mgl.drop.game.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;

public class SplashScene extends Scene {

	private Camera camera;
	private ObjectFactorySingleton objectFactorySingleton = ObjectFactorySingleton
			.getInstance();
	private TextureSingleton texture = TextureSingleton.getInstance();
	private SceneManagerSingleton sceneManager = SceneManagerSingleton
			.getInstance();

	// atts
	private float time = 0;
	MainDropActivity mainDropActivity;
	private boolean finish = false;

	private boolean isResized = false;
	private SpriteBackground imgLoad;
	private float contTime = 0;
	private float timeResizing = 0.6f;

	private int contPoint = 0;

	private Text loadingText;

	private float originalWidth ;
	private float originalHeight ;
	
	private boolean hasLoadMain = false;
	
	public SplashScene(Camera camera, MainDropActivity mainDropActivity) {
		this.mainDropActivity = mainDropActivity;
	}

	public Scene createScene() {
		try {

			time = 0;
			texture.loadLogo();
			texture.initGameLoadingFont();
			Thread.sleep(3000);

			/*
			 * Sprite background = new Sprite(0, 0,
			 * TextureSingleton.getInstance(
			 * ).getTextureByName("backgroundBegin.png"),
			 * TextureSingleton.getInstance().getVertexBufferObjectManager());
			 * background.setSize(800, 480); this.attachChild(background);
			 */

			imgLoad = new SpriteBackground(200, 200,
					texture.getLoadingTexture(),
					texture.getVertexBufferObjectManager());
			//imgLoad.setSize(300, 300);
			imgLoad.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - imgLoad.getWidth() / 2,
					MainDropActivity.CAMERA_HEIGHT / 2 - imgLoad.getHeight()
							/ 2);
			this.attachChild(imgLoad);

			originalWidth = imgLoad.getWidth();
			originalHeight = imgLoad.getHeight();
			
			loadingText = ObjectFactorySingleton.getInstance().createText(
					"Loading" , texture.getmFont2());
			loadingText.setPosition(MainDropActivity.CAMERA_WIDTH / 2
					- loadingText.getWidth() / 2, imgLoad.getY()+imgLoad.getHeight() +100);
			this.attachChild(loadingText);

			mainDropActivity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					try {

						//texture.loadTexturesMainScene();
						mainDropActivity.loadPublicitySound();
						
						texture.loadTextures();
						
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
					time += pSecondsElapsed;
				
					// System.out.println("this is the time elapsed MAIN SCENE: "+time);
					// if(time>=6){
					contTime = contTime + pSecondsElapsed;
					if (contTime > timeResizing) {

						if (isResized) {
							//imgLoad.setSize(originalWidth,originalHeight);
						} else {
							//imgLoad.setSize(originalWidth+25, originalHeight+25);
						}
						
						isResized = !isResized;
						
						contTime = 0;

						contPoint++;
						
						String numPoints = new String();
						for(int i = 0; i< contPoint; i++){
							numPoints = numPoints + ".";
						}
						
						loadingText = MainDropActivity.changeText("Loading " + numPoints,
								loadingText, texture.getmFont2());
						loadingText.setPosition(MainDropActivity.CAMERA_WIDTH
								/ 2 - loadingText.getWidth() / 2,
								loadingText.getY());
						getsScene().attachChild(loadingText);

						
						if (contPoint > 5) {
							contPoint = 0;
						}
					}
					
					imgLoad.setPosition(
							MainDropActivity.CAMERA_WIDTH / 2
									- imgLoad.getWidth() / 2,
							MainDropActivity.CAMERA_HEIGHT / 2
									- imgLoad.getHeight() / 2);

					if (finish) {
						//sceneManager.createMainScene();
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

	public Scene getsScene() {
		return this;
	}

}
