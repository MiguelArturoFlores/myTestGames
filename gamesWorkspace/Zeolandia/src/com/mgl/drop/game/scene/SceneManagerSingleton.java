package com.mgl.drop.game.scene;

import java.util.ArrayList;

import java.util.Locale;
import java.util.Stack;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mgl.base.analitycs.TrackerName;
import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.base.userinfo.GoogleAPISingleton;
import com.mgl.base.userinfo.OffertGameSingleton;
import com.mgl.base.userinfo.PhysicSingleton;
import com.mgl.base.userinfo.TrophySingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.base.userinfo.WebServiceSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.RateMeHUD;
import com.mgl.drop.game.hud.WhatsappShareHUD;

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
	private SplashScene splashScene;
	private GameScene gameScene;
	private SceneWinLevel sceneWinLevel;
	private SceneSelectMode sceneSelectMode;
	private SceneWinAll sceneWinAll;
	private SceneIntro sceneIntro;
	private BattleScene battleScene;

	private Scene currentSceneTrue;
	private int contRetry = 0;
	private float zoomFactor;

	private int contZoom = 0;
	private Stack<AllScenes> sceneStack;

	public enum AllScenes {
		SPLASH, MAIN, SELECT_LEVEL, GAME, GAME_BEGIN, MODE_SELECT, MONEY_SHOP, HELP, SCORE, TROPHY, WIN_GAME, POWER_UP, INTRO, SELECT_PLAYER, BATTLE_SCENE
	}

	public void destroy() {
		try {

			mainScene = null;
			instance = null;
			activity = null;
			gameScene = null;
			sceneSelectMode = null;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private SceneManagerSingleton(MainDropActivity act, Engine eng,
			SmoothCamera cam) {
		// TODO Auto-generated constructor stub
		try {
			sceneStack = new Stack<SceneManagerSingleton.AllScenes>();
			this.activity = act;
			this.engine = eng;
			this.camera = cam;
			zoomFactor = cam.getZoomFactor();
			System.out.println("Cargo Singleton ScenenManagers");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public AllScenes getCurrentScene() {
		return currentScene;
	}

	public void setCurrentScene(AllScenes currentScene) {
		try {

			TrophySingleton.getInstance().validateTrophyAcomplish();
			
			this.currentScene = currentScene;

			sceneStack.push(currentScene);

			camera.setChaseEntity(null);
			camera.setCenterDirect(MainDropActivity.CAMERA_WIDTH / 2,
					MainDropActivity.CAMERA_HEIGHT / 2);
			camera.setBounds(0, 0, MainDropActivity.CAMERA_WIDTH,
					MainDropActivity.CAMERA_HEIGHT);

			resetZoom();

			switch (currentScene) {

			case SPLASH:
				break;
			case WIN_GAME:
				HUDManagerSingleton.getInstance().removeAllHUD();
				HUDManagerSingleton.getInstance().addHUD(new HUD(), false);

				SceneManagerSingleton.getInstance().sendGoogleTrack(
						"Win all");
				sceneWinAll = new SceneWinAll();
				engine.setScene(sceneWinAll);
				currentSceneTrue = sceneWinAll;

				break;
				
			case BATTLE_SCENE:
				
				
				engine.setScene(battleScene);
				currentSceneTrue = battleScene;

				//getActivity().requestInventoryItems();

				break;

			case INTRO:
				
				sceneIntro = new SceneIntro();
				engine.setScene(sceneIntro);
				currentSceneTrue = sceneIntro;

				getActivity().requestInventoryItems();

				break;
			case MODE_SELECT:
				HUDManagerSingleton.getInstance().removeAllHUD();
				HUDManagerSingleton.getInstance().addHUD(new HUD(), false);
				;
				SoundSingleton.getInstance().playMusic("music.mp3", false);
				PublicityManagerSingleton.getInstance().destroyAdds();
				UserInfoSingleton.getInstance().showRateme();
				MainDropActivity.verifyFreeGift();
				createSelectModeScene();
				engine.setScene(sceneSelectMode);

				currentSceneTrue = sceneSelectMode;

				getActivity().requestInventoryItems();

				break;
			case MAIN:
				PublicityManagerSingleton.getInstance().loadVideoCache();
				createMainScene();
				clearHUD();
				HUDManagerSingleton.getInstance().removeAllHUD();
				PublicityManagerSingleton.getInstance().destroyAdds();
				engine.setScene(mainScene);

				SoundSingleton.getInstance().playMusic("music.mp3", false);

				currentSceneTrue = mainScene;

				getActivity().requestInventoryItems();
				

				break;
			case SELECT_LEVEL:

				camera.setChaseEntity(null);
				camera.setCenterDirect(MainDropActivity.CAMERA_WIDTH / 2,
						MainDropActivity.CAMERA_HEIGHT / 2);

				HUDManagerSingleton.getInstance().removeAllHUD();
				HUDManagerSingleton.getInstance().addHUD(new HUD(), false);
				PublicityManagerSingleton.getInstance().destroyAdds();

				SoundSingleton.getInstance().playMusic("music.mp3", false);
				contRetry = 0;
				SceneSelectLevel selectLevel = new SceneSelectLevel();
				engine.setScene(selectLevel);
				currentSceneTrue = selectLevel;

				getActivity().requestInventoryItems();
				
				//UserInfoSingleton.getInstance().showRateme();
				//UserInfoSingleton.getInstance().shareWhatsapp();
				//UserInfoSingleton.getInstance().shareFacebook();
				//UserInfoSingleton.getInstance().showInternalPopUp();
				PublicityManagerSingleton.getInstance().showIntersitial();
				UserInfoSingleton.getInstance().showRateme();

				break;
			case GAME:
				// engine.setScene(gameScene);
				break;
			case GAME_BEGIN:
				//SoundSingleton.getInstance().playMusic("backgroundSound1.mp3",
				//		true);
				if (gameScene != null) {

					mainScene = null;
					selectLevel = null;
					
					PublicityManagerSingleton.getInstance().destroyAdds();
					engine.setScene(gameScene);

					SoundSingleton.getInstance().stopMusic("music.mp3");
					// SoundSingleton.getInstance().getMusic("music.mp3")
					// .seekTo(0);
					gameScene.loadLevel();
					currentSceneTrue = gameScene;
					OffertGameSingleton.getInstance().showOffert();
					PublicityManagerSingleton.getInstance().showIntersitial();
				}
				break;

			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearHUD() {
		try {

			camera.setHUD(new HUD());

		} catch (Exception e) {
			e.printStackTrace();
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

	public Scene createGameScene(Level levelDB, ArrayList<Level> levelList,
			int gameType) {

		try {

			gameScene = new GameScene();
			
			//Load level id from database
			
			//Level level  = new Level();
			//level.setId(1L);
			//level.setPosition(1);
			gameScene.createScene(levelDB, levelList, gameType);

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

			splashScene = new SplashScene(camera, mainDropActivity);
			splashScene.createScene();
			return splashScene;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public Scene createSelectModeScene() {
		try {

			sceneSelectMode = new SceneSelectMode();
			return sceneSelectMode;
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
			AllScenes gameBegin, int gameType) {
		try {
			contRetry++;
			createGameScene(levelDB, levelList, gameType);
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

	public int getContZoom() {
		return contZoom;
	}

	public void setContZoom(int contZoom) {
		this.contZoom = contZoom;
	}

	public Scene getCurrentSceneTrue() {
		return currentSceneTrue;
	}

	public void goBack() {
		try {

			if (sceneStack.isEmpty()) {
				setCurrentScene(AllScenes.MAIN);
				return;
			}

			AllScenes sc = sceneStack.pop();
			if (sc.equals(AllScenes.GAME_BEGIN) || sc.equals(AllScenes.BATTLE_SCENE) || sc.equals(AllScenes.SELECT_LEVEL) ) {
				setCurrentScene(AllScenes.MAIN);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendGoogleTrack(String token) {
		try {

			Tracker t = activity.getTracker(TrackerName.APP_TRACKER);
			t.setScreenName(token);
			// t.send(new HitBuilders.AppViewBuilder().build());

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public void goBattleScene(BattleScene battleScene) {
		try {
			
			this.battleScene = battleScene;
			setCurrentScene(AllScenes.BATTLE_SCENE);
			
			camera.setChaseEntity(null);
			camera.setBounds(0, 0, MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			camera.setCenterDirect(MainDropActivity.CAMERA_WIDTH/2, MainDropActivity.CAMERA_HEIGHT/2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void goBackToAdventureMode() {
		try {
			
			this.battleScene = null;
			engine.setScene(gameScene);
			gameScene.getLevel().getLevel().goBackToAdventure();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}