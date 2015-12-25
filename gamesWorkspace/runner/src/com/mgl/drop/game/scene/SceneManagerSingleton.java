package com.mgl.drop.game.scene;

import java.util.ArrayList;
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
import com.mgl.base.userinfo.OffertGameSingleton;
import com.mgl.base.userinfo.PhysicSingleton;
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
	private SceneSelectMode sceneSelectMode;
	private SceneMoneyShop sceneMoneyShop;
	private ScenePowerUp scenePowerUp;
	private HelpScene sceneHelp;
	private SceneScore sceneScore;
	private SceneTrophy sceneTrophy;
	private SceneWinAll sceneWinAll;
	private SceneIntro sceneIntro;

	private Scene currentSceneTrue;
	private int contRetry = 0;
	private float zoomFactor;

	private int contZoom = 0;
	private Stack<AllScenes> sceneStack;

	public enum AllScenes {
		SPLASH, MAIN, SELECT_LEVEL, GAME, GAME_BEGIN, MODE_SELECT, MONEY_SHOP, HELP, SCORE, TROPHY, WIN_GAME, POWER_UP, INTRO
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

			this.currentScene = currentScene;

			sceneStack.push(currentScene);

			camera.setChaseEntity(null);
			camera.setCenterDirect(MainDropActivity.CAMERA_WIDTH / 2,
					MainDropActivity.CAMERA_HEIGHT / 2);

			resetZoom();

			switch (currentScene) {

			case SPLASH:
				break;
			case WIN_GAME:
				HUDManagerSingleton.getInstance().removeAllHUD();
				HUDManagerSingleton.getInstance().addHUD(new HUD(), false);

				SceneManagerSingleton.getInstance().sendGoogleTrack(
						"Win all tester");
				sceneWinAll = new SceneWinAll();
				engine.setScene(sceneWinAll);
				currentSceneTrue = sceneWinAll;

				break;

			case INTRO:
				SoundSingleton.getInstance().playMusic("music.ogg", false);
				SceneManagerSingleton.getInstance().sendGoogleTrack(
						"View INTRO SCENE");
				sceneIntro = new SceneIntro();
				engine.setScene(sceneIntro);
				currentSceneTrue = sceneIntro;

				getActivity().requestInventoryItems();

				break;
			case TROPHY:
				SceneManagerSingleton.getInstance().sendGoogleTrack(
						"View Trophy Scene");
				sceneTrophy = new SceneTrophy();
				engine.setScene(sceneTrophy);
				currentSceneTrue = sceneTrophy;

				getActivity().requestInventoryItems();

				break;
			case SCORE:
				SceneManagerSingleton.getInstance().sendGoogleTrack(
						"View Score Scene");
				sceneScore = new SceneScore();
				engine.setScene(sceneScore);
				currentSceneTrue = sceneScore;

				getActivity().requestInventoryItems();
				break;
			case POWER_UP:
				SceneManagerSingleton.getInstance().sendGoogleTrack(
						"View Power up");
				scenePowerUp = new ScenePowerUp();
				engine.setScene(scenePowerUp);
				currentSceneTrue = scenePowerUp;

				getActivity().requestInventoryItems();
				break;
			case HELP:

				SceneManagerSingleton.getInstance().sendGoogleTrack(
						"View Help Scene");
				sceneHelp = new HelpScene();
				engine.setScene(sceneHelp);

				currentSceneTrue = sceneHelp;
				getActivity().requestInventoryItems();
				break;
			case MONEY_SHOP:

				PublicityManagerSingleton.getInstance().destroyAdds();
				SceneManagerSingleton.getInstance().sendGoogleTrack(
						"View Money Shop Scene");
				HUDManagerSingleton.getInstance().removeAllHUD();
				SceneManagerSingleton.getInstance().getCamera()
						.setHUD(new HUD());
				sceneMoneyShop = new SceneMoneyShop();
				engine.setScene(sceneMoneyShop);
				currentSceneTrue = sceneMoneyShop;

				getActivity().requestInventoryItems();

				break;
			case MODE_SELECT:
				HUDManagerSingleton.getInstance().removeAllHUD();
				HUDManagerSingleton.getInstance().addHUD(new HUD(), false);
				;
				SoundSingleton.getInstance().playMusic("music.ogg", false);
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

				SoundSingleton.getInstance().playMusic("music.ogg", false);

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

				SoundSingleton.getInstance().playMusic("music.ogg", false);
				contRetry = 0;
				SceneSelectLevelNew selectLevel = new SceneSelectLevelNew();
				// engine.setScene(selectLevelScene); old
				engine.setScene(selectLevel);

				// PublicityManagerSingleton.getInstance().showIntersitial();
				// gameScene = null;
				currentSceneTrue = selectLevelScene;

				UserInfoSingleton.getInstance().showRateme();
				
				getActivity().requestInventoryItems();
				activity.requestEmailAccountToSend();
				WebServiceSingleton.getInstance().sendGeneralData();
				WebServiceSingleton.getInstance().sendDataTwitterYoutube();
				

				break;
			case GAME:
				// engine.setScene(gameScene);
				break;
			case GAME_BEGIN:
				SoundSingleton.getInstance().playMusic("backgroundSound1.ogg",
						true);
				if (gameScene != null) {

					PublicityManagerSingleton.getInstance().destroyAdds();
					engine.setScene(gameScene);

					SoundSingleton.getInstance().stopMusic("music.ogg");
					gameScene.loadLevel();
					currentSceneTrue = gameScene;
					OffertGameSingleton.getInstance().showOffert();

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
			if (sc.equals(AllScenes.MAIN)) {
				setCurrentScene(AllScenes.INTRO);
			} else if (sc.equals(AllScenes.HELP) || sc.equals(AllScenes.TROPHY)
					|| sc.equals(AllScenes.MODE_SELECT)
					|| sc.equals(AllScenes.WIN_GAME)
					|| sc.equals(AllScenes.SCORE)) {
				setCurrentScene(AllScenes.MAIN);
			} else if (sc.equals(AllScenes.MONEY_SHOP)) {
				if (!sceneStack.isEmpty()) {
					setCurrentScene(sceneStack.pop());
				}
			} else if (sc.equals(AllScenes.GAME_BEGIN)
					|| sc.equals(AllScenes.POWER_UP)) {
				gameScene = null;
				scenePowerUp = null;
				setCurrentScene(AllScenes.SELECT_LEVEL);
			} else if (sc.equals(AllScenes.SELECT_LEVEL)) {
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
			t.send(new HitBuilders.AppViewBuilder().build());

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

}