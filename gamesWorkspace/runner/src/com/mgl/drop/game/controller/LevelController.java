package com.mgl.drop.game.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.debugdraw.DebugRenderer;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.util.color.Color;
import org.andlabs.andengine.extension.physicsloader.*;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.util.Log;
import android.util.Xml;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mgl.base.InfoType;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.StatusType;
import com.mgl.base.analitycs.TrackerName;
import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.base.userinfo.PhysicSingleton;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.base.userinfo.ScoreSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.LevelManager;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.database.model.SpriteDB;
import com.mgl.drop.game.entity.BackgroundEntity;
import com.mgl.drop.game.entity.EntityAddPhysics;
import com.mgl.drop.game.entity.EntityComboText;
import com.mgl.drop.game.entity.EntityLive;
import com.mgl.drop.game.entity.EntityScore;
import com.mgl.drop.game.entity.arunner.EntityAddSpriteImage;
import com.mgl.drop.game.entity.arunner.EntityBurbleGenerator;
import com.mgl.drop.game.entity.arunner.EntityMoveBackground;
import com.mgl.drop.game.hud.InfoHowToHUD;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.InformativeSpriteHUD;
import com.mgl.drop.game.hud.LevelBeginHUD;
import com.mgl.drop.game.hud.LevelHUD;
import com.mgl.drop.game.hud.LooseHUD;
import com.mgl.drop.game.hud.UnlockWithFacebookHUD;
import com.mgl.drop.game.hud.WinHUD;
import com.mgl.drop.game.model.MyObjectXml;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneTouchListener;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteBeginPoint;
import com.mgl.drop.game.sprites.SpriteBlackLoose;
import com.mgl.drop.game.sprites.SpriteBloodInHud;
import com.mgl.drop.game.sprites.SpriteDeadBody;
import com.mgl.drop.game.sprites.SpriteInvisibleTouch;
import com.mgl.drop.game.sprites.SpritePoop;
import com.mgl.drop.game.sprites.SpritePoopBar;
import com.mgl.drop.game.sprites.SpritePoopMultiple;
import com.mgl.drop.game.sprites.SpriteRedBackground;
import com.mgl.drop.game.sprites.SpriteSmashGeneral;
import com.mgl.drop.game.sprites.SpriteTerrain;
import com.mgl.drop.game.sprites.SpriteTrunk;
import com.mgl.drop.game.sprites.SpriteWall;
import com.mgl.drop.game.sprites.SpriteWayPoint;
import com.mgl.drop.game.sprites.SpriteZoomCamera;
import com.mgl.drop.game.sprites.arunner.SpriteADN;
import com.mgl.drop.game.sprites.arunner.SpriteCheckPoint;
import com.mgl.drop.game.sprites.arunner.SpriteEater;
import com.mgl.drop.game.sprites.arunner.SpriteFloor;
import com.mgl.drop.game.sprites.arunner.SpriteHedgehog;
import com.mgl.drop.game.sprites.arunner.SpriteInvisiblePointToFollow;
import com.mgl.drop.game.sprites.arunner.SpriteLake;
import com.mgl.drop.game.sprites.arunner.SpriteMeteorLevelSelect;
import com.mgl.drop.game.sprites.arunner.SpriteMoveControll;
import com.mgl.drop.game.sprites.arunner.SpriteObstacleFall;
import com.mgl.drop.game.sprites.arunner.SpriteObstacleSmash;
import com.mgl.drop.game.sprites.arunner.SpritePlatform;
import com.mgl.drop.game.sprites.arunner.SpritePlayer;
import com.mgl.drop.game.sprites.arunner.SpritePlayerShot;
import com.mgl.drop.game.sprites.arunner.SpriteSmasher;
import com.mgl.drop.game.sprites.arunner.SpriteStar;
import com.mgl.drop.game.sprites.arunner.SpriteStarCount;
import com.mgl.drop.game.sprites.arunner.SpriteTunel;
import com.mgl.drop.game.sprites.arunner.SpriteVulcano;
import com.mgl.drop.game.sprites.button.StartButton;
import com.mgl.drop.game.sprites.button.arunner.ButtonAccelerate;
import com.mgl.drop.game.sprites.button.arunner.ButtonIce;
import com.mgl.drop.game.sprites.button.arunner.ButtonShot;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Path;
import com.mgl.drop.util.Point;
import com.mgl.runner.R;

public class LevelController {

	private TextureSingleton texture = TextureSingleton.getInstance();
	private Scene scene;

	private static final float DISTANCE_BETWEEN_POINT = 12f;
	private static final float DISTANCE_TO_ADD = 10000000;

	private ThreadLoadFromXml loadXml;
	private LevelManager levelManager;
	private float contTime = 0;

	private ButtonMoney money;
	private SpriteStarCount starCount;

	private boolean isLoadFinish = false;
	private boolean removeLoadingHud = false;
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
	private ArrayList<Point> cameraPointList;

	private ArrayList<MyAnimateSprite> poopList;

	private boolean mustUpdate = false;

	private IOnSceneTouchListener prevListener;
	private SpriteMoveControll controll;

	private SpriteZoomCamera spriteZoomIn;
	private SpriteZoomCamera spriteZoomOut;

	private Level levelCurrent;
	private GameObjectsController gameObjectController;
	private EntityLive entityLive;
	private EntityScore entityScore;

	private SpritePlayer player;
	private SpriteInvisiblePointToFollow invisiblePointToFollow;
	private SpriteEater eater;

	private LevelHUD levelHud;
	private int gameType;
	private SpriteCheckPoint checkPoint;

	private EntityMoveBackground entityMoveBackground;

	public LevelController(Scene scene) {
		this.scene = scene;
	}

	public LevelController(Scene scene, LevelManager levelManager,
			Long idLevel, int gameType) {
		try {

			this.gameType = gameType;
			this.scene = scene;
			this.idLevel = idLevel;

			this.levelManager = levelManager;

			// loadLevel();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadNextLevel(Long idLevel) {
		try {

			checkPoint = null;
			reloadLevel(idLevel);

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

	public void loadLevel() {
		try {
			HUDManagerSingleton.getInstance().removeAllHUD();
			initHud();

			detachAllChild();
			SmoothCamera camera = (SmoothCamera) SceneManagerSingleton
					.getInstance().getCamera();
			camera.setCenterDirect(camera.getWidth() / 2,
					camera.getHeight() / 2);
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

			initGameObjectController();

			attachSrpiteList();

			scene.sortChildren();

			initStartButton();

			// initLevelInfo();

			initEntityLiveScore();

			initSceneTouchListener();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadNormal() {
		try {

			PoolObjectSingleton.getInstance();

			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			// ArrayList<SpriteDB> spriteDBList = dao.loadSpriteDBList(idLevel);

			// initSpriteList(spriteDBList);

			// initSceneTouchListener();

			if (checkPoint == null) {
				spriteList = new ArrayList<MySpriteGeneral>();
				PhysicSingleton.getInstance().reset();

				scene.registerUpdateHandler(PhysicSingleton.getInstance()
						.getPhysicsWorld());

				initCollitionDetectionBox2d();
			} else {

				removeNotMustUpdate();

			}

			PhysicSingleton.getInstance().pause(true);
			/*
			 * DebugRenderer debug = new DebugRenderer(PhysicSingleton
			 * .getInstance().getPhysicsWorld(),
			 * texture.getVertexBufferObjectManager());
			 * debug.setZIndex(ZIndexGame.FADE);
			 * 
			 * scene.attachChild(debug);
			 */
			initGameObjectController();

			initEntityBackground();

			attachSrpiteList();
			
			

			initPlayer();

			validateCheckPoint();

			// loadXml = new ThreadLoadFromXml(this, idLevel);
			// loadXml.run();
			removeLoadingHud = false;
			SceneManagerSingleton.getInstance().getActivity()
					.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							loadLevelFromXml(idLevel);
							// removeLoadingHud = true;
							
							HUDManagerSingleton.getInstance()
									.removeAndReplaceHud();
						}
					});

			initInvisibleFollow();

			scene.sortChildren();

			initStartButton();

			initLevelInfo();

			initEntityLiveScore();

			//initEntityBackground();

			initBurbleEntity();

			SmoothCamera camera = (SmoothCamera) SceneManagerSingleton
					.getInstance().getCamera();
			camera.setChaseEntity(null);

			

			initLoadingHud();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initEntityAddImagesToObstacle() {
		try {

			try {

				ArrayList<MySpriteGeneral> sprList = new ArrayList<MySpriteGeneral>();
				for (MySpriteGeneral spr : spriteList) {
					try {
						if (spr.getSpriteType().equals(SpriteType.HEDGEHOG)
								|| spr.getSpriteType().equals(SpriteType.FLOOR)
								|| spr.getSpriteType().equals(
										SpriteType.DECORATIVE)) {
							sprList.add(spr);
							if (spr instanceof MySprite) {
								((MySprite) spr).detachSelf();
							}
							if (spr instanceof MyAnimateSprite) {
								((MyAnimateSprite) spr).detachSelf();
							}
						}
					} catch (Exception e) {

					}

				}

				EntityAddSpriteImage sprImage = new EntityAddSpriteImage(
						sprList);
				spriteList.add(sprImage);

				spriteList.removeAll(sprList);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initLoadingHud() {
		try {

			SpriteBackground back = new SpriteBackground(0, 0,
					texture.getTextureByName("loading1.png"),
					texture.getVertexBufferObjectManager());
			InformativeSpriteHUD loadingHUD = new InformativeSpriteHUD(back,
					"", false);
			HUDManagerSingleton.getInstance().addHUD(loadingHUD, true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initBurbleEntity() {
		try {
			EntityBurbleGenerator burbleGenerator = new EntityBurbleGenerator();
			spriteList.add(burbleGenerator);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void removeNotMustUpdate() {

		try {

			for (MySpriteGeneral spr : spriteList) {
				if (spr instanceof MySprite) {
					if (((MySprite) spr).isMustReload()) {
						spriteListDelete.add(spr);
					}
				}
				if (spr instanceof MyAnimateSprite) {
					if (((MyAnimateSprite) spr).isMustReload()) {
						spriteListDelete.add(spr);
					}
				}
				if (spr instanceof EntityBurbleGenerator) {
					spriteListDelete.add(spr);
				}
			}
			removeEntityToUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void validateCheckPoint() {
		try {

			if (checkPoint == null) {
				return;
			}

			player.setX(checkPoint.getX());
			player.setY(checkPoint.getY());
			setBodyPixelPosition(checkPoint.getX(), checkPoint.getY(), 0,
					player.getWidth(), player.getHeight(), player.getBody());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initCollitionDetectionBox2d() {
		try {
			// TODO composite que cada quien sepa como reaccionar a la colision

			PhysicCollitionController controller = new PhysicCollitionController(
					this);

			PhysicSingleton.getInstance().getPhysicsWorld()
					.setContactListener(controller);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initEntityBackground() {
		try {

			entityMoveBackground = new EntityMoveBackground(this);
			// entityMoveBackground.update(0.03f, this);
			spriteList.add(entityMoveBackground);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initPlayer() {
		try {

			player = MyFactory.createPlayer(this);
			player.setPosition(100, 200);
			player.setSize(80, 80);
			// PhysicSingleton.getInstance().addPlayerPhysic(player);
			PhysicSingleton.getInstance().loadSpriteInWorldXML(player);

			scene.attachChild(player);
			// scene.registerTouchArea(player);
			player.setZIndex(ZIndexGame.PLAYER);
			spriteList.add(player);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initSceneTouchListener() {
		try {
			SceneTouchListener sceneSmashListener = new SceneTouchListener(this);
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

	private void initGameObjectController() {
		try {
			gameObjectController = new GameObjectsController(this, idLevel,
					gameType);
			gameObjectController.setMustUpdate(false);

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

			// TODO remove this
			if (levelCurrent == null) {
				System.out.println("null level");
				return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.NOTHING)) {
				System.out.println("Nothing");
				// return;
			}

			if (levelCurrent.getHelpInfo().equals(InfoType.BEGIN_GAME)) {
				initBeginGameHud();
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
			stringList.add("help2.png");
			stringList.add("help3.png");
			stringList.add("help4.png");
			stringList.add("help5.png");

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

			gameObjectController.setMustUpdate(mustUpdate);

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

					}

				} catch (Exception e) {

				}
			}

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

	public void reloadLevel() {
		scene.detachChildren();
		scene.clearTouchAreas();
		scene.clearEntityModifiers();
		scene.clearChildScene();

		spriteListDelete = new ArrayList<MySpriteGeneral>();
		spriteListToAdd = new ArrayList<MySpriteGeneral>();

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

			for (MySpriteGeneral sprite : spriteList) {
				sprite.setMustUpdate(mustUpdate);
				sprite.update(dTime, this);
			}

			// gameObjectController.setMustUpdate(mustUpdate);
			// gameObjectController.update(dTime, this);

			// for(MySpriteGeneral spr : spriteList){
			// Log.d("SIZE ", "CALSS "+spr.getClass());
			// }

			removeEntityToUpdate();

			addEntityToUpdate();

			scene.sortChildren();

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

			for (MySpriteGeneral spr : spriteListDelete) {
				Body b = spr.getBody();
				if (b == null) {
					continue;
				}
				PhysicSingleton.getInstance().removeBodyRightNow(b);
				b = null;
				spr.setBody(null);
			}

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

			if (combo != 0) {
				EntityComboText comboText = new EntityComboText(x, y, combo,
						this);
				this.addSpriteToUpdate(comboText);
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

	public void looseLevelReloadOnCheckpoint() {
		try {

			SpriteBlackLoose loose = new SpriteBlackLoose(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			loose.setSize(MainDropActivity.CAMERA_WIDTH + 100,
					MainDropActivity.CAMERA_HEIGHT + 100);
			loose.setZIndex(ZIndexGame.FADE);
			HUDManagerSingleton.getInstance().removeAndReplaceHud();
			scene.attachChild(loose);
			spriteListToAdd.add(loose);

			if (true) {
				return;
			}

			LooseHUD hud = new LooseHUD(scene, levelManager, this);
			HUDManagerSingleton.getInstance().addHUD(hud, false);

			Log.d("contRetry", "cont "
					+ SceneManagerSingleton.getInstance().getContRetry());
			if (SceneManagerSingleton.getInstance().getContRetry() >= MainDropActivity.HELP_LEVEL_RETRY) {
				showFacebookHelp();
				SceneManagerSingleton.getInstance().setContRetry(0);

			}
			mustUpdate = false;

			PublicityManagerSingleton.getInstance().showViewVideoToEarn();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void looseLevelReplay() {
		try {

			if (player.isLoose()) {
				return;
			}

			player.showLoosing(this);
			// SoundSingleton.getInstance().playSound("looseMusic.mp3");
			ScoreSingleton.getInstance().newScore(entityScore.getScore());

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
			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			
			this.setMustUpdate(false);
			SoundSingleton.getInstance().stopCurrentMusic();
			SoundSingleton.getInstance().playSound("winMusic.mp3");
			ScoreSingleton.getInstance().newScore(entityScore.getScore());

			SceneManagerSingleton.getInstance().setContRetry(0);

			int stars = 0;
			int adnCount = 0;

			for (MySpriteGeneral spr : spriteList) {
				try {

					if (spr.getSpriteType().equals(SpriteType.STAR)) {
						SpriteStar star = (SpriteStar) spr;
						if (star.isCollected()) {
							//updating star collected in db
							dao.collectStar(star.getId());
							stars++;
							System.out.println("starts collected " + stars);
						}
					}
					if (spr.getSpriteType().equals(SpriteType.ADN)) {
						SpriteADN adn = (SpriteADN) spr;
						if (adn.isCollected()) {
							adnCount++;
							System.out.println("adn collected " + adnCount);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			// if (stars > 3) {
			// stars = 3;
			// }

			Log.d("VALIDANDO GANAR", "GANEEEE");
			
			// dao.setStars(idLevel, stars);

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

					dao.setStars(idLevel, stars + l.getStars());
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
			WinHUD winHud = new WinHUD(scene, this, level, stars, adnCount);
			HUDManagerSingleton.getInstance().addHUD(winHud, false);

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

	public Level getLevelCurrent() {
		return levelCurrent;
	}

	public void setLevelCurrent(Level levelCurrent) {
		this.levelCurrent = levelCurrent;
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
		try {

			PhysicSingleton.getInstance().pause(!mustUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		}

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

			// waveController.reloadWave(this, idLevel);
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

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public GameObjectsController getGameObjectController() {
		return gameObjectController;
	}

	public void setGameObjectController(
			GameObjectsController gameObjectController) {
		this.gameObjectController = gameObjectController;
	}

	public void manageSceneTouchAccelerate(int direcction, boolean isHorizontal) {
		try {
			player.manageSceneTouchAccelerate(direcction, isHorizontal);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void manageSceneTouch() {
		try {

			player.manageSceneTouch();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void createFloor() {
		try {

			FixtureDef WALL_FIX = PhysicsFactory
					.createFixtureDef(0f, 0.0f, 10f);
			// SpriteBackground floor = new SpriteBackground(0,
			// MainDropActivity.CAMERA_HEIGHT - 50,
			// texture.getTextureByName("black.jpg"),
			// texture.getVertexBufferObjectManager());
			Rectangle floor = new Rectangle(0,
					MainDropActivity.CAMERA_HEIGHT - 15,
					MainDropActivity.CAMERA_WIDTH, 15,
					texture.getVertexBufferObjectManager());
			floor.setColor(new Color(15, 50, 0));

			floor.setWidth(MainDropActivity.CAMERA_WIDTH * 2);
			floor.setZIndex(ZIndexGame.PLAYER);

			Body body = PhysicsFactory.createBoxBody(PhysicSingleton
					.getInstance().getPhysicsWorld(), floor,
					BodyType.StaticBody, WALL_FIX);

			this.scene.attachChild(floor);

			// http://www.box2d.org/manual.html#_Toc258082972 manual
			// createObstacle();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadLevelFromXml(Long idLevel) {
		try {

			ArrayList<MySpriteGeneral> spriteToLoadInWorld = new ArrayList<MySpriteGeneral>();

			Activity act = SceneManagerSingleton.getInstance().getActivity();
			// DefaultHandler root = new DefaultHandler();
			// Xml.parse(act.getResources().openRawResource(R.raw.level1),Xml.Encoding.UTF_8,
			// root);
			System.out.println("LOADING...level  " + idLevel);
			InputStream is = act.getAssets().open(
					"levels/level" + idLevel + ".xml");
			System.out.println(getStringFromInputStream(is));

			MyXmlParser parser = new MyXmlParser(act.getAssets().open(
					"levels/level" + idLevel + ".xml"));

			parser.parseDocument();

			for (MyObjectXml obj : parser.getObjectList()) {
				System.out.println("LOADING " + obj.getId());

				// Loading camera
				if (SpriteTypeConstant.CAMERA_POINT == obj.getId()) {

					addCameraPoint(obj);
					continue;
				}

				MySpriteGeneral spr = MyFactory.createObstacle(obj.getId(),
						this);
				spr.setXmlParameter(obj.getParameter());

				int zIndex = 0;
				if (spr instanceof MySprite) {
					zIndex = ((MySprite) spr).getZIndex();

					if (Point.distanceBetween(
							new Point(player.getX(), player.getY()), new Point(
									obj.getX(), obj.getY())) < DISTANCE_TO_ADD) {
						if (((MySprite) spr).isMustReload()
								|| checkPoint == null) {
							PhysicSingleton.getInstance().loadSpriteInWorldXML(
									spr);
						} else {
							continue;
						}
					} else {
						// spriteToLoadInWorld.add(spr);
					}
				} else if (spr instanceof MyAnimateSprite) {
					zIndex = ((MyAnimateSprite) spr).getZIndex();
					if (Point.distanceBetween(
							new Point(player.getX(), player.getY()), new Point(
									obj.getX(), obj.getY())) < DISTANCE_TO_ADD) {
						if (((MyAnimateSprite) spr).isMustReload()
								|| checkPoint == null) {
							PhysicSingleton.getInstance().loadSpriteInWorldXML(
									spr);
						} else {
							continue;
						}
					} else {
						spriteToLoadInWorld.add(spr);
					}
				}

				if (spr instanceof MySprite) {

					MySprite mySprite = (MySprite) spr;
					scene.attachChild(mySprite);
					mySprite.setZIndex(zIndex);
					mySprite.setPosition(obj.getX(), obj.getY());
					mySprite.setMyAngle(obj.getAngle());
					setBodyPixelPosition(obj.getX(), obj.getY(),
							obj.getAngle(), mySprite.getWidth(),
							mySprite.getHeight(), mySprite.getBody());
				} else if (spr instanceof MyAnimateSprite) {
					MyAnimateSprite mySprite = (MyAnimateSprite) spr;
					scene.attachChild(mySprite);
					mySprite.setPosition(obj.getX(), obj.getY());
					mySprite.setMyAngle(obj.getAngle());
					mySprite.setZIndex(zIndex);
					setBodyPixelPosition(obj.getX(), obj.getY(),
							obj.getAngle(), mySprite.getWidth(),
							mySprite.getHeight(), mySprite.getBody());
				}

				spriteList.add(spr);
			}

			// initEntityAddPhisycs(spriteToLoadInWorld);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initEntityAddPhisycs(
			ArrayList<MySpriteGeneral> spriteToLoadInWorld) {
		try {

			EntityAddPhysics entity = new EntityAddPhysics(spriteToLoadInWorld);
			spriteList.add(entity);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addCameraPoint(MyObjectXml obj) {

		try {

			if (cameraPointList == null) {
				cameraPointList = new ArrayList<Point>();
			}

			cameraPointList.add(new Point(obj.getX(), obj.getY()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initInvisibleFollow() {
		try {

			invisiblePointToFollow = new SpriteInvisiblePointToFollow(250, 900,
					60, 60, texture.getTextureByName("arrowRight.png"),
					texture.getVertexBufferObjectManager(), this);
			// invisiblePointToFollow.setSize(50, 50);
			invisiblePointToFollow.setAlpha(0);
			invisiblePointToFollow.setZIndex(ZIndexGame.FADE);
			scene.attachChild(invisiblePointToFollow);
			spriteList.add(invisiblePointToFollow);

			if (checkPoint != null) {
				// checkPoint.getPath().add(new
				// Point(checkPoint.getCameraPosition().getX(),
				// checkPoint.getCameraPosition().getY()));
				invisiblePointToFollow.setPath(checkPoint.getPath());
				invisiblePointToFollow.setPosition(checkPoint
						.getCameraPosition().getX(), checkPoint
						.getCameraPosition().getY());
				SmoothCamera camera = (SmoothCamera) SceneManagerSingleton
						.getInstance().getCamera();
				camera.setCenterDirect(checkPoint.getCameraPosition().getX(),
						checkPoint.getCameraPosition().getY());

			} else {

				Point pointA = null;
				pointA = new Point(MainDropActivity.CAMERA_WIDTH / 2,
						MainDropActivity.CAMERA_HEIGHT / 2);
				invisiblePointToFollow
						.setPosition(pointA.getX(), pointA.getY());
				invisiblePointToFollow.addPoint(pointA);
				int i = 0;

				while (cameraPointList != null && !cameraPointList.isEmpty()) {
					i++;
					System.out.println("iteration number " + i);
					Point pointB = null;
					for (Point p : cameraPointList) {

						if (pointB == null) {
							pointB = p;
						}

						float distanceA = Point.distanceBetween(pointA, pointB);
						float distanceB = Point.distanceBetween(pointA, p);

						if (distanceB < distanceA) {
							pointB = p;
						}

					}

					invisiblePointToFollow.addPoint(pointB);

					cameraPointList.remove(pointB);

					pointA = new Point(pointB.getX(), pointB.getY());

				}
			}

			// camera.setCenterDirect(invisiblePointToFollow.getX(),invisiblePointToFollow.getY());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void setBodyPixelPosition(float x, float y, float angle,
			float width, float height, Body body) {
		try {
			if (body == null) {
				return;
			}

			final float PIXEL_TO_METER_RATIO_DEFAULT = 32;
			float widthD2 = width / 2;
			float heightD2 = height / 2;

			double sin = Math.abs(Math.sin(Math.toRadians(angle))), cos = Math
					.abs(Math.cos(Math.toRadians(angle)));

			int neww = (int) Math.floor(width * cos + height * sin), newh = (int) Math
					.floor(height * cos + width * sin);

			widthD2 = (neww) / 2;
			heightD2 = (newh) / 2;

			final Vector2 v2 = Vector2Pool.obtain((x + widthD2)
					/ PIXEL_TO_METER_RATIO_DEFAULT, (y + heightD2)
					/ PIXEL_TO_METER_RATIO_DEFAULT);
			body.setTransform(v2, (float) Math.toRadians(angle));
			Vector2Pool.recycle(v2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setBodyPixelPositionOld(float x, float y, float angle,
			float width, float height, Body body) {
		try {

			final float PIXEL_TO_METER_RATIO_DEFAULT = 32;
			float widthD2 = width / 2;
			float heightD2 = height / 2;

			double sin = Math.abs(Math.sin(Math.toRadians(angle))), cos = Math
					.abs(Math.cos(Math.toRadians(angle)));

			int neww = (int) Math.floor(width * cos + height * sin), newh = (int) Math
					.floor(height * cos + width * sin);

			final Vector2 v2 = Vector2Pool.obtain((x + widthD2)
					/ PIXEL_TO_METER_RATIO_DEFAULT, (y + heightD2)
					/ PIXEL_TO_METER_RATIO_DEFAULT);
			body.setTransform(v2, (float) Math.toRadians(angle));
			Vector2Pool.recycle(v2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SpritePlayer getPlayer() {
		return player;
	}

	public void setPlayer(SpritePlayer player) {
		this.player = player;
	}

	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	public ArrayList<Point> getCameraPointList() {
		return cameraPointList;
	}

	public void setCameraPointList(ArrayList<Point> cameraPointList) {
		this.cameraPointList = cameraPointList;
	}

	public void initGameHUD() {
		try {

			ButtonIce ice = new ButtonIce(MainDropActivity.CAMERA_WIDTH - 80,
					MainDropActivity.CAMERA_HEIGHT - 80,
					this.texture.getTextureByName("iceIcon.png"),
					texture.getVertexBufferObjectManager(), this);
			ice.setSize(80, 80);
			ice.reloadBar();
			addSpriteToUpdate(ice);

			HUDManagerSingleton.getInstance().getTop().attachChild(ice);
			HUDManagerSingleton.getInstance().getTop().registerTouchArea(ice);

			ButtonAccelerate acc = new ButtonAccelerate(0,
					MainDropActivity.CAMERA_HEIGHT - 80,
					this.texture.getTextureByName("accelerateIcon.png"),
					texture.getVertexBufferObjectManager(), this);
			acc.setSize(80, 80);
			acc.setX(ice.getX() - acc.getWidth() - 20);
			acc.reloadBar();
			addSpriteToUpdate(acc);

			HUDManagerSingleton.getInstance().getTop().attachChild(acc);
			HUDManagerSingleton.getInstance().getTop().registerTouchArea(acc);

			ButtonShot shot = new ButtonShot(0,
					MainDropActivity.CAMERA_HEIGHT - 80,
					this.texture.getTextureByName("shotIcon.png"),
					texture.getVertexBufferObjectManager(), this);
			shot.setSize(80, 80);
			shot.setX(acc.getX() - shot.getWidth() - 20);
			addSpriteToUpdate(shot);

			HUDManagerSingleton.getInstance().getTop().attachChild(shot);
			HUDManagerSingleton.getInstance().getTop().registerTouchArea(shot);

			controll = (SpriteMoveControll) MyFactory.createObstacle(
					SpriteTypeConstant.CONTROLL, this);
			addSpriteToUpdate(controll);
			controll.setPosition(-25,
					MainDropActivity.CAMERA_HEIGHT - controll.getHeight() + 50);
			HUDManagerSingleton.getInstance().getTop().attachChild(controll);
			HUDManagerSingleton.getInstance().getTop()
					.registerTouchArea(controll);

			money = new ButtonMoney(0, 5,
					texture.getTextureByName("buttonTexture.png"),
					texture.getVertexBufferObjectManager());
			money.setPosition(100, 0);
			money.setAlpha(0f);
			HUDManagerSingleton.getInstance().getTop().attachChild(money);

			starCount = new SpriteStarCount(0, 0,
					texture.getTextureByName("fillStar.png"),
					texture.getVertexBufferObjectManager());
			starCount.setPosition(250, 5);
			starCount.setAlpha(0.5f);
			starCount.setSize(50, 50);
			starCount.reloadText();

			HUDManagerSingleton.getInstance().getTop().attachChild(starCount);

			SmoothCamera camera = (SmoothCamera) SceneManagerSingleton
					.getInstance().getCamera();
			camera.setChaseEntity(invisiblePointToFollow);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SpriteCheckPoint getCheckPoint() {
		return checkPoint;
	}

	public void setCheckPoint(SpriteCheckPoint checkPoint) {
		this.checkPoint = checkPoint;
	}

	public SpriteInvisiblePointToFollow getInvisiblePointToFollow() {
		return invisiblePointToFollow;
	}

	public void setInvisiblePointToFollow(
			SpriteInvisiblePointToFollow invisiblePointToFollow) {
		this.invisiblePointToFollow = invisiblePointToFollow;
	}

	public void loadLevelSelect() {
		try {

			spriteList = new ArrayList<MySpriteGeneral>();
			spriteListDelete = new ArrayList<MySpriteGeneral>();
			spriteListToAdd = new ArrayList<MySpriteGeneral>();

			for (int i = 0; i < 14; i++) {
				SpriteMeteorLevelSelect meteor = (SpriteMeteorLevelSelect) MyFactory
						.createObstacle(SpriteTypeConstant.METEOR_LEVEL, this);
				meteor.setPosition(800, 400);
				meteor.setZIndex(ZIndexGame.METEOR);
				scene.attachChild(meteor);
				spriteList.add(meteor);
			}

			mustUpdate = true;
			isLoadFinish = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SpriteMoveControll getControll() {
		return controll;
	}

	public void setControll(SpriteMoveControll controll) {
		this.controll = controll;
	}

	public SpriteEater getEater() {
		return eater;
	}

	public void setEater(SpriteEater eater) {
		this.eater = eater;
	}

	public EntityMoveBackground getEntityMoveBackground() {
		return entityMoveBackground;
	}

	public void setEntityMoveBackground(
			EntityMoveBackground entityMoveBackground) {
		this.entityMoveBackground = entityMoveBackground;
	}

	public static float getDistanceToAdd() {
		return DISTANCE_TO_ADD;
	}

	public void increaseMoney() {
		try {

			money.setQuantityPlus(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void increaseStar() {
		try {

			starCount.increaseStar();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
