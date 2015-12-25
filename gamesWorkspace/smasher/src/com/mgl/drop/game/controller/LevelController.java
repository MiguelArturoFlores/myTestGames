package com.mgl.drop.game.controller;

import java.util.ArrayList;
import java.util.Collections;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.example.games.basegameutils.GooglePlayGameSingleton;
import com.mgl.base.InfoType;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.StatusType;
import com.mgl.base.analitycs.TrackerName;
import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.base.userinfo.ScoreSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.LevelManager;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.database.model.SpriteDB;
import com.mgl.drop.game.entity.BackgroundEntity;
import com.mgl.drop.game.entity.EntityComboText;
import com.mgl.drop.game.entity.EntityLive;
import com.mgl.drop.game.entity.EntityScore;
import com.mgl.drop.game.hud.InfoHowToHUD;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.InformativeSpriteHUD;
import com.mgl.drop.game.hud.LevelBeginHUD;
import com.mgl.drop.game.hud.LevelHUD;
import com.mgl.drop.game.hud.LooseHUD;
import com.mgl.drop.game.hud.UnlockWithFacebookHUD;
import com.mgl.drop.game.hud.WinHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneMoveListener2;
import com.mgl.drop.game.scene.SceneSmashListener;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteBeginPoint;
import com.mgl.drop.game.sprites.SpriteBloodInHud;
import com.mgl.drop.game.sprites.SpriteDeadBody;
import com.mgl.drop.game.sprites.SpriteFire;
import com.mgl.drop.game.sprites.SpriteInvisibleTouch;
import com.mgl.drop.game.sprites.SpriteMonsterBasic;
import com.mgl.drop.game.sprites.SpritePoop;
import com.mgl.drop.game.sprites.SpritePoopBar;
import com.mgl.drop.game.sprites.SpritePoopMultiple;
import com.mgl.drop.game.sprites.SpriteRedBackground;
import com.mgl.drop.game.sprites.SpriteTerrain;
import com.mgl.drop.game.sprites.SpriteTrunk;
import com.mgl.drop.game.sprites.SpriteWall;
import com.mgl.drop.game.sprites.SpriteWayPoint;
import com.mgl.drop.game.sprites.SpriteZoomCamera;
import com.mgl.drop.game.sprites.button.StartButton;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Path;
import com.mgl.drop.util.Point;
import com.mgl.smasher.R;

public class LevelController {

	private TextureSingleton texture = TextureSingleton.getInstance();
	private Scene scene;

	private static final float DISTANCE_BETWEEN_POINT = 12f;

	private LevelManager levelManager;
	private float contTime = 0;

	private boolean isLoadFinish = false;
	private Path path;

	private BackgroundEntity backgroundEntity;

	private float width;
	private float heigh;

	private Sprite background;
	private SpriteTerrain floor;

	private Long idLevel;
	private ArrayList<MySpriteGeneral> spriteList;
	private ArrayList<MySpriteGeneral> spriteListDelete;
	private ArrayList<MySpriteGeneral> spriteListToAdd;

	private ArrayList<MyAnimateSprite> poopList;

	private boolean mustUpdate = false;

	private SceneMoveListener2 moveListener;
	private IOnSceneTouchListener prevListener;

	private SpriteZoomCamera spriteZoomIn;
	private SpriteZoomCamera spriteZoomOut;

	private Level levelCurrent;
	private WaveController waveController;
	private EntityLive entityLive;
	private EntityScore entityScore;

	private SpriteDeadBody deadBody;
	private SpriteWall wall;
	private SpriteFire fire;
	private SpriteTrunk trunk;

	private LevelHUD levelHud;
	private int gameType;

	public LevelController(Scene scene, LevelManager levelManager,
			Long idLevel, int gameType) {
		try {

			this.gameType = gameType;
			this.scene = scene;
			this.idLevel = idLevel;

			this.levelManager = levelManager;

			loadLevel();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reloadLevel(Long idLevel) {
		try {

			this.idLevel = idLevel;
			scene.detachChildren();
			scene.clearTouchAreas();
			scene.clearEntityModifiers();
			scene.clearUpdateHandlers();
			// loadLevel();
			reloadLevel();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadLevel() {
		try {
			HUDManagerSingleton.getInstance().removeAllHUD();
			initHud();

			detachAllChild();
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			camera.setCenter(camera.getWidth() / 2, camera.getHeight() / 2);
			spriteList = new ArrayList<MySpriteGeneral>();
			spriteListDelete = new ArrayList<MySpriteGeneral>();
			spriteListToAdd = new ArrayList<MySpriteGeneral>();
			// poopList = new ArrayList<MyAnimateSprite>();

			switch (gameType) {
			case GameConstants.PLAY_NORMAL:
				loadNormal();
				break;
			case GameConstants.PLAY_SURVIVAL:
				loadSurvival();
				break;

			default:
				break;
			}

			isLoadFinish = true;

			mustUpdate = false;

			setUpdateAnimated(false);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void loadSurvival() {
		try {

			initObjectToProtect();

			initWaveList();

			attachSrpiteList();

			scene.sortChildren();

			initStartButton();

			initLevelInfo();

			initEntityLiveScore();

			initSmashTouchListener();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadNormal() {
		try {

			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			ArrayList<SpriteDB> spriteDBList = dao.loadSpriteDBList(idLevel);

			initSpriteList(spriteDBList);

			initObjectToProtect();
			initWaveList();

			attachSrpiteList();

			scene.sortChildren();

			initStartButton();

			initLevelInfo();

			initEntityLiveScore();

			initSmashTouchListener();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initSmashTouchListener() {
		try {
			SceneSmashListener sceneSmashListener = new SceneSmashListener(
					entityScore);
			scene.setOnSceneTouchListener(sceneSmashListener);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initEntityLiveScore() {
		try {

			entityLive = new EntityLive(3L, levelHud);
			entityScore = new EntityScore(levelHud);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initObjectToProtect() {
		try {

			floor = new SpriteTerrain(0, MainDropActivity.CAMERA_HEIGHT + 100,
					texture.getTextureByName("buttonTexture.png"),
					texture.getVertexBufferObjectManager());

			floor.setWidth(MainDropActivity.CAMERA_WIDTH * 2);
			spriteList.add(floor);

			floor.setZIndex(2);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initWaveList() {
		try {
			waveController = new WaveController(idLevel, this, gameType);
			waveController.setMustUpdate(false);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initLevelInfo() {
		try {

			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);

			levelCurrent = dao.loadLevelById(idLevel);

			if (levelCurrent == null) {
				System.out.println("null level");
				return;
			}
			System.out.println("level info " + levelCurrent.getHelpInfo()
					+ " id " + levelCurrent.getId() + " facebook "
					+ levelCurrent.getFacebook());
			if (levelCurrent.getHelpInfo().equals(InfoType.NOTHING)) {
				System.out.println("Nothing");
				// return;
			}

			if (levelCurrent.getHelpInfo().equals(InfoType.BEGIN_GAME)) {
				initBeginGameHud();
				// return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.DONT_KILL)) {
				initDontKillHud();
				// return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.KILL_BAT)) {
				initKillGenericHUD("help6.png");
				// return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.KILL_HOLE)) {
				initKillGenericHUD("help2.png");
				// return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.KILL_ARMOR)) {
				initKillGenericHUD("help5.png");
				// return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.KILL_VAMPIRESA)) {
				initKillGenericHUD("help4.png");
				// return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.KILL_BOSS)) {
				initKillGenericHUD("help7.png");
				// return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initKillGenericHUD(String name) {
		try {

			ArrayList<String> stringList = new ArrayList<String>();
			stringList.add(name);

			InfoHowToHUD hud = new InfoHowToHUD(this, stringList);
			HUDManagerSingleton.getInstance().addHUD(hud, true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void initDontKillHud() {
		try {

			ArrayList<String> stringList = new ArrayList<String>();
			stringList.add("help3.png");

			InfoHowToHUD hud = new InfoHowToHUD(this, stringList);
			HUDManagerSingleton.getInstance().addHUD(hud, true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initBeginGameHud() {
		try {

			ArrayList<String> stringList = new ArrayList<String>();
			stringList.add("help1.png");

			InfoHowToHUD hud = new InfoHowToHUD(this, stringList);
			HUDManagerSingleton.getInstance().addHUD(hud, true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setUpdateAnimated(boolean mustUpdate) {

		try {

			for (MySpriteGeneral spr : spriteList) {
				if (spr instanceof MyAnimateSprite) {
					MyAnimateSprite ms = (MyAnimateSprite) spr;
					ms.setMustUpdate(mustUpdate);
				}
			}

			waveController.setMustUpdate(mustUpdate);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void detachZoomButton() {
		try {
			spriteZoomIn.detachSelf();
			spriteZoomOut.detachSelf();
			SmoothCamera cam = (SmoothCamera) SceneManagerSingleton
					.getInstance().getCamera();

			cam.getHUD().detachChild(spriteZoomIn);
			cam.getHUD().unregisterTouchArea(spriteZoomIn);

			cam.getHUD().detachChild(spriteZoomOut);
			cam.getHUD().unregisterTouchArea(spriteZoomOut);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initHud() {
		try {

			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			levelHud = new LevelHUD(scene, levelManager);

			HUDManagerSingleton.getInstance().addHUD(levelHud, false);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

	private void attachSrpiteList() {
		try {
			for (MySpriteGeneral spr : spriteList) {
				if (spr instanceof MySprite) {
					MySprite s = (MySprite) spr;
					scene.attachChild(s);
				} else if (spr instanceof MyAnimateSprite) {
					MyAnimateSprite s = (MyAnimateSprite) spr;
					scene.attachChild(s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initSpriteList(ArrayList<SpriteDB> spriteDBList) {
		try {

			for (SpriteDB spr : spriteDBList) {
				try {

					String[] s;
					switch (spr.getType().intValue()) {
					case SpriteTypeConstant.BACKGROUND:

						backgroundEntity = new BackgroundEntity(scene);
						spriteList.add(backgroundEntity);

						if (true) {
							continue;
						}
						SpriteBackground background = new SpriteBackground(
								spr.getX(), spr.getY(),
								texture.getTextureByName(spr.getTextureName()),
								texture.getVertexBufferObjectManager());
						spriteList.add(background);

						background.setZIndex(0);

						background = new SpriteBackground(spr.getX(),
								spr.getY(), texture.getTextureByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager());
						background.setX(background.getWidth() - 1);
						spriteList.add(background);
						background.setZIndex(0);

						width = background.getWidth() * 2;
						heigh = background.getHeight();

						width = 480;
						heigh = 800;

						break;

					case SpriteTypeConstant.TERRAIN:

						SpriteTerrain terrain = new SpriteTerrain(spr.getX(),
								spr.getY(), texture.getTextureByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager());
						spriteList.add(terrain);

						terrain.setZIndex(2);
						spr.setTextureName("floorPrincipal.png");
						terrain = new SpriteTerrain(terrain.getWidth() - 1,
								spr.getY(), texture.getTextureByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager());
						spriteList.add(terrain);

						break;

					/*case SpriteTypeConstant.MONSTER_BASIC:
						s = spr.getData().split("@");

						SpriteMonsterBasic monster = new SpriteMonsterBasic(
								spr.getX(), spr.getY(),
								texture.getTextureAnimateByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager(), this,
								Long.valueOf(s[0]), Long.valueOf(s[1]));

						monster.setSize(60, 80);
						monster.setZIndex(4);
						monster.setSpeed(Long.valueOf(s[2]).floatValue());
						spriteList.add(monster);

						break;*/
					}

				} catch (Exception e) {

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setSceneMoveListener() {
		try {

			Log.d("XXXXXXX", "X " + Float.valueOf(width).intValue() + " Y "
					+ Float.valueOf(heigh).intValue());

			moveListener = new SceneMoveListener2(Float.valueOf(width)
					.intValue(), Float.valueOf(heigh).intValue());

			prevListener = scene.getOnSceneTouchListener();
			scene.setOnSceneTouchListener(moveListener);

			addSpriteToUpdate(backgroundEntity);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void detachAllChild() {
		try {
			if (spriteList == null) {
				return;
			}
			for (MySpriteGeneral sprite : spriteList) {
				if (sprite instanceof MySprite) {
					MySprite spr = (MySprite) sprite;
					scene.detachChild(spr);
				} else if (sprite instanceof MyAnimateSprite) {
					MyAnimateSprite spr = (MyAnimateSprite) sprite;
					scene.detachChild(spr);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initStartButton() {
		try {

			LevelBeginHUD hud = new LevelBeginHUD(scene, this);
			HUDManagerSingleton.getInstance().addHUD(hud, false);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*private void initMonster() {
		try {

			SpriteMonsterBasic sprite = new SpriteMonsterBasic(250, 400,
					texture.getTextureAnimateByName("monster1.png"),
					texture.getVertexBufferObjectManager(), this, 200, 750);
			sprite.setPosition(98, 200);
			sprite.setSize(50, 50);
			sprite.setRotation(90);
			scene.attachChild(sprite);
			scene.registerTouchArea(sprite);
			spriteList.add(sprite);

			sprite = new SpriteMonsterBasic(250, 400,
					texture.getTextureAnimateByName("monster1.png"),
					texture.getVertexBufferObjectManager(), this, 400, 600);
			sprite.setPosition(150, 400);
			sprite.setSize(50, 50);
			sprite.setRotation(90);
			scene.attachChild(sprite);
			scene.registerTouchArea(sprite);
			spriteList.add(sprite);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/

	public void reloadLevel() {
		scene.detachChildren();
		scene.clearTouchAreas();
		scene.clearEntityModifiers();
		scene.clearChildScene();

		loadLevel();

	}

	public void update(float dTime) {
		try {

			contTime = contTime + dTime;
			// if (!isLoadFinish || !mustUpdate) {
			if (!isLoadFinish) {
				return;
			}

			// for(MySprite sprite :spriteWayPointList){
			// sprite.update(dTime, this);
			// }
			// player.update(dTime, this);
			
			fire = null;
			wall = null;
			trunk = null;
			deadBody = null;
			
			for (MySpriteGeneral sprite : spriteList) {
				
				if (sprite.getSpriteType().equals(SpriteType.FOOD)) {
					deadBody = (SpriteDeadBody) sprite;
				} else if (sprite.getSpriteType().equals(SpriteType.WALL)) {
					wall = (SpriteWall) sprite;
				} else if (sprite.getSpriteType().equals(SpriteType.TRUNK)) {
					trunk = (SpriteTrunk) sprite;
				} else if (sprite.getSpriteType().equals(SpriteType.FIRE)) {
					fire = (SpriteFire) sprite;
				}
			}
			
			for (MySpriteGeneral sprite : spriteList) {
				sprite.setMustUpdate(mustUpdate);
				sprite.update(dTime, this);
			}

			

			waveController.setMustUpdate(mustUpdate);
			waveController.update(dTime, this);

			// for(MySpriteGeneral spr : spriteList){
			// Log.d("SIZE ", "CALSS "+spr.getClass());
			// }

			addEntityToUpdate();

			removeEntityToUpdate();

			if (contTime > 0.1) {
				orderVisibilityVampire();
				contTime = 0;

			}

			scene.sortChildren();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void orderVisibilityVampire() {
		try {

			ArrayList<MyAnimateSprite> vampireList = new ArrayList<MyAnimateSprite>();
			for (MySpriteGeneral sprite : spriteList) {

				if (sprite.getSpriteType().equals(SpriteType.OBJETIVE)) {
					if (sprite instanceof MyAnimateSprite) {
						MyAnimateSprite animateSprite = (MyAnimateSprite) sprite;
						vampireList.add(animateSprite);
					}
				}

			}

			Collections.sort(vampireList);
			int i = 1;
			for (MyAnimateSprite spr : vampireList) {
				for (MyAnimateSprite sprAux : vampireList) {
					if (spr.collidesWith(sprAux)) {
						spr.setZIndex(ZIndexGame.VAMPIRE + i);
						i++;
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addEntityToUpdate() {
		try {

			spriteList.addAll(spriteListToAdd);
			spriteListToAdd = new ArrayList<MySpriteGeneral>();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void removeEntityToUpdate() {
		try {

			spriteList.removeAll(spriteListDelete);
			spriteListDelete = new ArrayList<MySpriteGeneral>();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addSpriteToUpdate(MySpriteGeneral sprite) {

		try {

			spriteListToAdd.add(sprite);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeEntity(MySpriteGeneral sprite) {
		try {

			spriteListDelete.add(sprite);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void looseLive() {
		try {

			entityLive.looseLive();

			SpriteRedBackground background = new SpriteRedBackground(0, 0,
					texture.getTextureByName("borderRed.png"),
					texture.getVertexBufferObjectManager());
			background.setZIndex(ZIndexGame.FADE);
			scene.attachChild(background);
			addSpriteToUpdate(background);

			if (entityLive.getLive() <= 0L) {
				looseLevelReplay();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void upScore(int score, float x, float y) {
		try {

			int combo = entityScore.updateScore(score);

			generateRandomBlood(x, y);

			if (combo != 0) {
				EntityComboText comboText = new EntityComboText(x, y, combo,
						this);
				this.addSpriteToUpdate(comboText);
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void generateRandomBlood(float x, float y) {
		try {

			for (int i = 0; i < 5; i++) {
				float xVal = (float) (((Math.random() * 1000000) % 400) + 50);
				float yVal = (float) (((Math.random() * 1000000) % 250) + y);

				SpriteBloodInHud blood  = PoolObjectSingleton.getInstance().getBloodInHud();
				blood.setPosition(xVal, yVal);
				blood.reset();
				blood.detachSelf();
				addSpriteToUpdate(blood);
				HUDManagerSingleton.getInstance().getTop().attachChild(blood);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void validateWin() {
		try {

			winLevelShowScore();
			UserInfoSingleton.getInstance().validateTrophyAllStar();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void looseLevelReplay() {
		try {

			mustUpdate = false;

			SoundSingleton.getInstance().playSound("looseMusic.mp3");

			ScoreSingleton.getInstance().newScore(entityScore.getScore());
			
			int score = entityScore.getScore();
			GooglePlayGameSingleton.getInstance().submitScore(score);
			
			LooseHUD hud = new LooseHUD(scene, levelManager, this);
			HUDManagerSingleton.getInstance().addHUD(hud, false);

			Log.d("contRetry", "cont "
					+ SceneManagerSingleton.getInstance().getContRetry());
			if (SceneManagerSingleton.getInstance().getContRetry() >= MainDropActivity.HELP_LEVEL_RETRY) {
				showFacebookHelp();
				SceneManagerSingleton.getInstance().setContRetry(0);

			}

			PublicityManagerSingleton.getInstance().showViewVideoToEarn();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showFacebookHelp() {
		try {

			if (!MainDropActivity.isConnectingToInternet()) {

				return;

			}

			Log.d("Si tengo conexion", "SIIII");
			boolean found = false;
			Level level = null;
			for (Level l : levelManager.getLevelList()) {
				if (l.getId().equals(idLevel)) {
					found = true;
					continue;
				}
				if (found) {
					level = l;
					if (level.getFacebook()) {
						continue;
					}
					break;
				}
			}

			if (level == null) {
				return;
			}
			UnlockWithFacebookHUD hud = new UnlockWithFacebookHUD(level);
			HUDManagerSingleton.getInstance().addHUD(hud, true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void winLevelShowScore() {
		try {
			// mustUpdate = false;

			/*
			 * EntityChangeLevel entity = new EntityChangeLevel(3.3f);
			 * addSpriteToUpdate(entity);
			 */

			SoundSingleton.getInstance().stopCurrentMusic();
			SoundSingleton.getInstance().playSound("winMusic.mp3");
			ScoreSingleton.getInstance().newScore(entityScore.getScore());

			SceneManagerSingleton.getInstance().setContRetry(0);

			int stars = entityLive.getLive().intValue();

			Log.d("VALIDANDO GANAR", "GANEEEE");
			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			dao.setStars(idLevel, stars);

			Level level = null;
			boolean found = false;
			boolean foundBonus = false;
			for (Level l : levelManager.getLevelList()) {

				if (l.getId().equals(idLevel)) {
					// TODO for Testing
					if (idLevel.intValue() == GameConstants.LEVEL_TO_WIN) {
						SceneManagerSingleton.getInstance().setCurrentScene(
								AllScenes.WIN_GAME);
						return;
					}
					found = true;
					continue;
				}
				if (found) {

					level = l;
					if (level.getFacebook()) {
						foundBonus = true;
						// continue;
						break;
					}
					break;
				}
			}

			if (level == null) {
				// WON THIS LEVEL SERIES
				// TODO
				try {
					Tracker t = SceneManagerSingleton.getInstance()
							.getActivity().getTracker(TrackerName.APP_TRACKER);
					t.setScreenName("Win All Levels 1");
					t.send(new HitBuilders.AppViewBuilder().build());
				} catch (Exception e) {
					e.printStackTrace();
				}

				return;
			}

			dao.setNextAvalible(level.getId());

			/*
			 * if(foundBonus){ WinHudSelect winHud = new WinHudSelect(scene,
			 * this, level, stars);
			 * HUDManagerSingleton.getInstance().addHUD(winHud); }else{
			 * 
			 * WinHUD winHud = new WinHUD(scene, this, level, stars);
			 * HUDManagerSingleton.getInstance().addHUD(winHud); }
			 */
			WinHUD winHud = new WinHUD(scene, this, level, stars);
			HUDManagerSingleton.getInstance().addHUD(winHud, false);
			
			int score = entityScore.getScore();
			GooglePlayGameSingleton.getInstance().submitScore(score);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public TextureSingleton getTexture() {
		return texture;
	}

	public void setTexture(TextureSingleton texture) {
		this.texture = texture;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public boolean isLoadFinish() {
		return isLoadFinish;
	}

	public void setLoadFinish(boolean isLoadFinish) {
		this.isLoadFinish = isLoadFinish;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Sprite getBackground() {
		return background;
	}

	public void setBackground(Sprite background) {
		this.background = background;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeigh() {
		return heigh;
	}

	public void setHeigh(float heigh) {
		this.heigh = heigh;
	}

	public ArrayList<MySpriteGeneral> getSpriteList() {
		return spriteList;
	}

	public void setSpriteList(ArrayList<MySpriteGeneral> spriteList) {
		this.spriteList = spriteList;
	}

	public ArrayList<MySpriteGeneral> getSpriteListDelete() {
		return spriteListDelete;
	}

	public void setSpriteListDelete(ArrayList<MySpriteGeneral> spriteListDelete) {
		this.spriteListDelete = spriteListDelete;
	}

	public LevelManager getLevelManager() {
		return levelManager;
	}

	public void setLevelManager(LevelManager levelManager) {
		this.levelManager = levelManager;
	}

	public BackgroundEntity getBackgroundEntity() {
		return backgroundEntity;
	}

	public void setBackgroundEntity(BackgroundEntity backgroundEntity) {
		this.backgroundEntity = backgroundEntity;
	}

	public SpriteTerrain getFloor() {
		return floor;
	}

	public void setFloor(SpriteTerrain floor) {
		this.floor = floor;
	}

	public SceneMoveListener2 getMoveListener() {
		return moveListener;
	}

	public void setMoveListener(SceneMoveListener2 moveListener) {
		this.moveListener = moveListener;
	}

	public Level getLevelCurrent() {
		return levelCurrent;
	}

	public void setLevelCurrent(Level levelCurrent) {
		this.levelCurrent = levelCurrent;
	}

	public WaveController getWaveController() {
		return waveController;
	}

	public void setWaveController(WaveController waveController) {
		this.waveController = waveController;
	}

	public Long getIdLevel() {
		return idLevel;
	}

	public void setIdLevel(Long idLevel) {
		this.idLevel = idLevel;
	}

	public ArrayList<MySpriteGeneral> getSpriteListToAdd() {
		return spriteListToAdd;
	}

	public void setSpriteListToAdd(ArrayList<MySpriteGeneral> spriteListToAdd) {
		this.spriteListToAdd = spriteListToAdd;
	}

	public ArrayList<MyAnimateSprite> getPoopList() {

		return poopList;
	}

	public void setPoopList(ArrayList<MyAnimateSprite> poopList) {
		this.poopList = poopList;
	}

	public boolean isMustUpdate() {
		return mustUpdate;
	}

	public void setMustUpdate(boolean mustUpdate) {
		this.mustUpdate = mustUpdate;
	}

	public static float getDistanceBetweenPoint() {
		return DISTANCE_BETWEEN_POINT;
	}

	public IOnSceneTouchListener getPrevListener() {
		return prevListener;
	}

	public void setPrevListener(IOnSceneTouchListener prevListener) {
		this.prevListener = prevListener;
	}

	public SpriteZoomCamera getSpriteZoomIn() {
		return spriteZoomIn;
	}

	public void setSpriteZoomIn(SpriteZoomCamera spriteZoomIn) {
		this.spriteZoomIn = spriteZoomIn;
	}

	public SpriteZoomCamera getSpriteZoomOut() {
		return spriteZoomOut;
	}

	public void setSpriteZoomOut(SpriteZoomCamera spriteZoomOut) {
		this.spriteZoomOut = spriteZoomOut;
	}

	public LevelHUD getLevelHud() {
		return levelHud;
	}

	public void setLevelHud(LevelHUD levelHud) {
		this.levelHud = levelHud;
	}

	public void reloadWave() {
		try {

			
			for (MySpriteGeneral spr : spriteList) {
				scene.detachChild(spr.getEntity());

				if (spr instanceof MyAnimateSprite) {
					MyAnimateSprite sprA = (MyAnimateSprite) spr;
					scene.unregisterTouchArea(sprA);
				}
				if (spr instanceof MySprite) {
					MySprite sprA = (MySprite) spr;
					scene.unregisterTouchArea(sprA);
				}
				
			}

			spriteList = new ArrayList<MySpriteGeneral>();
			spriteListDelete = new ArrayList<MySpriteGeneral>();
			spriteListToAdd = new ArrayList<MySpriteGeneral>();

			waveController.reloadWave(this, idLevel);
			mustUpdate = true;
			entityLive.resetLife();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public float getContTime() {
		return contTime;
	}

	public void setContTime(float contTime) {
		this.contTime = contTime;
	}

	public EntityLive getEntityLive() {
		return entityLive;
	}

	public void setEntityLive(EntityLive entityLive) {
		this.entityLive = entityLive;
	}

	public EntityScore getEntityScore() {
		return entityScore;
	}

	public void setEntityScore(EntityScore entityScore) {
		this.entityScore = entityScore;
	}

	public SpriteDeadBody getDeadBody() {
		return deadBody;
	}

	public void setDeadBody(SpriteDeadBody deadBody) {
		this.deadBody = deadBody;
	}

	public SpriteWall getWall() {
		return wall;
	}

	public void setWall(SpriteWall wall) {
		this.wall = wall;
	}

	public SpriteFire getFire() {
		return fire;
	}

	public void setFire(SpriteFire fire) {
		this.fire = fire;
	}

	public SpriteTrunk getTrunk() {
		return trunk;
	}

	public void setTrunk(SpriteTrunk trunk) {
		this.trunk = trunk;
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	
	
}
