package com.mgl.drop.game.scene;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.games.Games;
import com.mgl.base.analitycs.TrackerName;
import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.crappypigeon.R;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;

public class SceneManagerSingleton {

	private ObjectFactorySingleton objectFactorySingleton = ObjectFactorySingleton
			.getInstance();
	private static SceneManagerSingleton instance = null;

	private AllScenes currentScene;
	private MainDropActivity activity;
	private Engine engine;
	private SmoothCamera camera;
	private BitmapTextureAtlas splashTA;
	private ITextureRegion splashTR;
	// scenes scenes scenes scenes scenes scenes scenes
	private MainScene mainScene;
	private SelectLevelScene selectLevelScene;
	private SplashScene splashScene;
	private GameScene gameScene;
	private SceneWinLevel sceneWinLevel;

	private int contRetry = 0;
	private float zoomFactor;

	private int contZoom = 0;

	public enum AllScenes {
		SPLASH, MAIN, SELECT_LEVEL, GAME, GAME_BEGIN
	}

	public void destroy() {
		try {

			mainScene = null;
			instance = null;
			activity = null;
			gameScene = null;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private SceneManagerSingleton(MainDropActivity act, Engine eng,
			SmoothCamera cam) {
		// TODO Auto-generated constructor stub
		this.activity = act;
		this.engine = eng;
		this.camera = cam;
		zoomFactor = cam.getZoomFactor();
		System.out.println("Cargo Singleton ScenenManagers");

	}

	public AllScenes getCurrentScene() {
		return currentScene;
	}

	public void setCurrentScene(AllScenes currentScene) {
		this.currentScene = currentScene;

		engine.clearUpdateHandlers();
		engine.getScene().detachChildren();
		camera.setChaseEntity(null);
		resetZoom();
		
		if(SoundSingleton.getInstance().getMusic("backgroundSound.mp3").isPlaying()){
			SoundSingleton.getInstance().getMusic("backgroundSound.mp3").pause();
			SoundSingleton.getInstance().getMusic("backgroundSound.mp3").seekTo(0);
		}
		
		PublicityManagerSingleton.getInstance().showIntersitialHouseAdd();
		
		switch (currentScene) {

		case SPLASH:
			break;
		case MAIN:
			HUDManagerSingleton.getInstance().removeAllHUD();
			PublicityManagerSingleton.getInstance().destroyAdds();
			engine.setScene(mainScene);
			SoundSingleton.getInstance().getMusic("music.mp3").play();
			
			break;
		case SELECT_LEVEL:
			HUDManagerSingleton.getInstance().removeAllHUD();

			//if (!SoundSingleton.getInstance().getMusic("test.mp3").isPlaying()) {
				SoundSingleton.getInstance().getMusic("music.mp3").play();
				
			//}
			contRetry = 0;
			engine.setScene(selectLevelScene);
			// PublicityManagerSingleton.getInstance().showIntersitial();
			gameScene = null;
			break;
		case GAME:
			// engine.setScene(gameScene);
			break;
		case GAME_BEGIN:
			if (gameScene != null) {
				SoundSingleton.getInstance().getMusic("backgroundSound.mp3").play();
				PublicityManagerSingleton.getInstance().destroyAdds();
				engine.setScene(gameScene);
				SoundSingleton.getInstance().getMusic("music.mp3").pause();
				SoundSingleton.getInstance().getMusic("music.mp3").seekTo(0);
				
			}
			break;

		default:
			break;
		}
	}

	public static SceneManagerSingleton getInstance(MainDropActivity act,
			Engine eng, SmoothCamera cam) {

		instance = new SceneManagerSingleton(act, eng, cam);

		return instance;
	}

	public static SceneManagerSingleton getInstance() {

		return instance;
	}

	public Scene createGameScene(Level levelDB, ArrayList<Level> levelList) {

		try {

			gameScene = new GameScene();

			gameScene.createScene(levelDB, levelList);
			return gameScene;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Scene createMainScene() {
		try {

			mainScene = new MainScene(camera);
			mainScene.createScene();
			return mainScene;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public Scene createSplashScene(MainDropActivity mainDropActivity) {
		try {

			splashScene = new SplashScene(camera,mainDropActivity);
			splashScene.createScene();
			return splashScene;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public Scene createSelectLevelScene() {
		try {

			contRetry = 0;

			camera.setHUD(new HUD());

			camera.setCenterDirect(MainDropActivity.CAMERA_WIDTH / 2,
					MainDropActivity.CAMERA_HEIGHT / 2);

			selectLevelScene = new SelectLevelScene();
			selectLevelScene.createScene();

			return selectLevelScene;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public MainDropActivity getActivity() {
		return activity;
	}

	public void setActivity(MainDropActivity activity) {
		this.activity = activity;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(SmoothCamera camera) {
		this.camera = camera;
	}

	public ObjectFactorySingleton getObjectFactorySingleton() {
		return objectFactorySingleton;
	}

	public void setObjectFactorySingleton(
			ObjectFactorySingleton objectFactorySingleton) {
		this.objectFactorySingleton = objectFactorySingleton;
	}

	public BitmapTextureAtlas getSplashTA() {
		return splashTA;
	}

	public void setSplashTA(BitmapTextureAtlas splashTA) {
		this.splashTA = splashTA;
	}

	public ITextureRegion getSplashTR() {
		return splashTR;
	}

	public void setSplashTR(ITextureRegion splashTR) {
		this.splashTR = splashTR;
	}

	public MainScene getMainScene() {
		return mainScene;
	}

	public void setMainScene(MainScene mainScene) {
		this.mainScene = mainScene;
	}

	public SelectLevelScene getSelectLevelScene() {
		return selectLevelScene;
	}

	public void setSelectLevelScene(SelectLevelScene selectLevelScene) {
		this.selectLevelScene = selectLevelScene;
	}

	public SplashScene getSplashScene() {
		return splashScene;
	}

	public void setSplashScene(SplashScene splashScene) {
		this.splashScene = splashScene;
	}

	public GameScene getGameScene() {
		return gameScene;
	}

	public void setGameScene(GameScene gameScene) {
		this.gameScene = gameScene;
	}

	public SceneWinLevel getSceneWinLevel() {
		return sceneWinLevel;
	}

	public void setSceneWinLevel(SceneWinLevel sceneWinLevel) {
		this.sceneWinLevel = sceneWinLevel;
	}

	public static void setInstance(SceneManagerSingleton instance) {
		SceneManagerSingleton.instance = instance;
	}

	public void reloadLevel(Level levelDB, ArrayList<Level> levelList,
			AllScenes gameBegin) {
		try {
			contRetry++;

			Tracker t = activity.getTracker(TrackerName.APP_TRACKER);
			t.setScreenName("Level " + levelDB.getName() + " Retry ");
			t.send(new HitBuilders.AppViewBuilder().build());

			createGameScene(levelDB, levelList);
			setCurrentScene(AllScenes.GAME_BEGIN);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int getContRetry() {
		return contRetry;
	}

	public void setContRetry(int contRetry) {
		this.contRetry = contRetry;
	}

	public void goMainScene() {
		try {

			createMainScene();
			setCurrentScene(AllScenes.MAIN);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public float getZoomFactor() {
		return zoomFactor;
	}

	public void setZoomFactor(float zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	public void resetZoom() {
		try {

			camera.setZoomFactor(zoomFactor);
			contZoom = 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void resetZoomInstant() {
		try {

			// camera.setZoomFactor(zoomFactor);
			camera.setZoomFactorDirect(zoomFactor);
			contZoom = 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void zoomIn(LevelController level) {
		try {

			if (contZoom >= 1) {
				return;
			}
			camera.setZoomFactor(camera.getZoomFactor() * 1.2f);

			contZoom++;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void zoomOut(LevelController level) {
		try {
			if (contZoom <= -3) {
				return;
			}
			camera.setZoomFactor(camera.getZoomFactor() / 1.2f);
			contZoom--;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}