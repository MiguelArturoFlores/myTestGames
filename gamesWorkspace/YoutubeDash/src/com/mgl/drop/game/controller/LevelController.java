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
import com.badlogic.gdx.physics.box2d.MassData;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.internal.md;
import com.google.example.games.basegameutils.GooglePlayGameSingleton;
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
import com.mgl.drop.game.entity.arunner.EntityBurbleGenerator;
import com.mgl.drop.game.entity.arunner.EntityMoveBackground;
import com.mgl.drop.game.entity.ayoutuberunner.EntityAddSpriteImage;
import com.mgl.drop.game.entity.ayoutuberunner.EntityChangeColor;
import com.mgl.drop.game.entity.ayoutuberunner.EntityGenerateLevelInfinite;
import com.mgl.drop.game.hud.InfoHowToHUD;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.InformativeSpriteHUD;
import com.mgl.drop.game.hud.LevelBeginHUD;
import com.mgl.drop.game.hud.LevelHUD;
import com.mgl.drop.game.hud.LooseHUD;
import com.mgl.drop.game.hud.WinHUD;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.model.MyObjectXml;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneTouchListener;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteBeginPoint;
import com.mgl.drop.game.sprites.SpriteBlackLoose;
import com.mgl.drop.game.sprites.SpriteInvisibleTouch;
import com.mgl.drop.game.sprites.SpriteRedBackground;
import com.mgl.drop.game.sprites.SpriteSmashGeneral;
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
import com.mgl.drop.game.sprites.arunner.SpriteTunel;
import com.mgl.drop.game.sprites.arunner.SpriteVulcano;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteColorChange;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteFloorLine;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteLikeCount;
import com.mgl.drop.game.sprites.ayoutuberunner.SpritePlayerCollition;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteStarGenerator;
import com.mgl.drop.game.sprites.button.StartButton;
import com.mgl.drop.game.sprites.button.arunner.ButtonAccelerate;
import com.mgl.drop.game.sprites.button.arunner.ButtonIce;
import com.mgl.drop.game.sprites.button.arunner.ButtonShot;
import com.mgl.drop.game.sprites.button.ayoutuberunner.ButtonGenerateCheckPoint;
import com.mgl.drop.game.sprites.button.ayoutuberunner.ButtonJump;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Path;
import com.mgl.drop.util.Point;
import com.mgl.twitter.TwitterSingleton;
import com.mgl.youtuberdash.R;;

public class LevelController {

	private TextureSingleton texture = TextureSingleton.getInstance();
	private Scene scene;

	private static final float DISTANCE_BETWEEN_POINT = 12f;
	private static final float DISTANCE_TO_ADD = 2000000;

	private ThreadLoadFromXml loadXml;
	private LevelManager levelManager;
	private float contTime = 0;

	private ButtonMoney money;

	private boolean soundMusic = true;
	private boolean isLoadFinish = false;
	private boolean removeLoadingHud = false;
	private Path path;

	private BackgroundEntity backgroundEntity;

	private float width;
	private float heigh;

	private Sprite background;

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

	private EntityChangeColor entityChangeColor;
	
	private SpritePlayer player;
	private SpritePlayerCollition playerCollition;
	private SpriteInvisiblePointToFollow invisiblePointToFollow;
	private SpriteEater eater;
	private SpriteLikeCount likeCounter;

	private LevelHUD levelHud;
	private int gameType;
	private SpriteCheckPoint checkPoint;

	private EntityMoveBackground entityMoveBackground;
	private int contLikeCoin;
	private EntityGenerateLevelInfinite infiniteLevel;

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

			loadLevel();

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

			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			levelCurrent = dao.loadLevelById(idLevel);
			
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

			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void loadSurvival() {
		try {


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

			spriteList = new ArrayList<MySpriteGeneral>();
			PhysicSingleton.getInstance().reset();

			scene.registerUpdateHandler(PhysicSingleton.getInstance()
					.getPhysicsWorld());

			contLikeCoin = 0;
			
			
			initCollitionDetectionBox2d();

			PhysicSingleton.getInstance().pause(true);
			
			
			/*DebugRenderer debug = new DebugRenderer(PhysicSingleton
			  .getInstance().getPhysicsWorld(),
			  texture.getVertexBufferObjectManager());
			  debug.setZIndex(ZIndexGame.FADE);
			  
			  scene.attachChild(debug);
			*/
			//idLevel = 4L;
			loadLevelFromXml(idLevel);

			initEntityBackground();

			attachSrpiteList();

			initInvisibleFollow();

			scene.sortChildren();

			//initStartButton();

			initEntityLiveScore();

			initSceneTouchListener();
			
			initEntityGenerateInfiniteLevel();

			initEntityChangeColor();
			
			SmoothCamera camera = (SmoothCamera) SceneManagerSingleton
					.getInstance().getCamera();
			camera.setChaseEntity(null);
			
			if(checkPoint!=null){
				camera.setCenterDirect(checkPoint.getX()+200, checkPoint.getY());
			}
			
			setUpdateAnimated(true);
			
			initFloorLine();
			initGameHUD();

			//initLoadingHud();
			initPlayer();
			
			validateCheckPoint();
			
			setMustUpdate(false);
			removeLoadingHud = false;
			SceneManagerSingleton.getInstance().getActivity()
					.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							
							
						//	loadLevelFromXml(idLevel);
							// removeLoadingHud = true;
							//HUDManagerSingleton.getInstance()
							//		.removeAndReplaceHud();
							
							
							

						}
					});
			
			removeStaticBodies();
			
			isLoadFinish = true;
			mustUpdate = true;
			setUpdateAnimated(true);
			PhysicSingleton.getInstance().pause(false);
			
			initLevelInfo();
			
			SceneManagerSingleton.getInstance().sendGoogleTrack("playLevel "+levelCurrent.getPosition()+" "+levelCurrent.getName());
			//SoundSingleton.getInstance().playMusic("level"+levelCurrent.getPosition()+".ogg",true);
			//SoundSingleton.getInstance().playMusic("level4"+".ogg",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initFloorLine() {
		try {
			
			SpriteFloorLine line = new SpriteFloorLine(0, 0, texture.getTextureByName("floorLine.png"), texture.getVertexBufferObjectManager());
			line.setWidth(600);
			spriteList.add(line);
			line.setZIndex(ZIndexGame.FADE);
			scene.attachChild(line);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removeStaticBodies() {
		try {
			
			ArrayList<MySpriteGeneral> sprList = new ArrayList<MySpriteGeneral>();
			for(MySpriteGeneral spr : spriteList){
				try {
					if(spr.getSpriteType().equals(SpriteType.HEDGEHOG) || spr.getSpriteType().equals(SpriteType.FLOOR) || spr.getSpriteType().equals(SpriteType.LIKE_COIN)){
						sprList.add(spr);
						if(spr instanceof MySprite){
							((MySprite) spr).detachSelf();
						}
						if(spr instanceof MyAnimateSprite){
							((MyAnimateSprite) spr).detachSelf();
						}
					}
				} catch (Exception e) {
					
				}
				
			}
			
			EntityAddSpriteImage sprImage = new EntityAddSpriteImage(sprList);
			spriteList.add(sprImage);
			
			spriteList.removeAll(sprList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initEntityChangeColor() {
		try {
			
			entityChangeColor = new EntityChangeColor();
			entityChangeColor.setEntityMove(entityMoveBackground);
			entityChangeColor.setLevelInfinite(infiniteLevel);
			spriteList.add(entityChangeColor);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initEntityGenerateInfiniteLevel() {
		try {
			
			infiniteLevel = new EntityGenerateLevelInfinite(this);
			spriteList.add(infiniteLevel);
			
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
			//player.setSize(48, 48);
			player.initData();
			
			PhysicSingleton.getInstance().loadSpriteInWorldXML(
					player);
			
			//PhysicSingleton.getInstance().addPlayerPhysic(player);
			//PhysicSingleton.getInstance().loadSpriteInWorldXML(player,true);
			if(player.hasParent()){
				player.detachSelf();
			}
			scene.attachChild(player);
			// scene.registerTouchArea(player);
			player.setZIndex(ZIndexGame.PLAYER);
			spriteList.add(player);
			player.transformOnShip();
			
			
		/*	playerCollition = MyFactory.createPlayerCollition();
			playerCollition.setPosition(100, 200);
			scene.attachChild(playerCollition);
			
			PhysicSingleton.getInstance().loadSpriteInWorldXML(
					playerCollition);
			
			playerCollition.setZIndex(ZIndexGame.PLAYER);
			
			PhysicSingleton.getInstance().addDistanceJoint(player.getBody(),playerCollition.getBody());
		*/
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
				
				SpriteMessage message2 = MyFactory.createMessage(SceneManagerSingleton.getInstance().getActivity().getString(R.string.BEGIN_GAME_HELP),4f);
				HUDManagerSingleton.getInstance().getTop().attachChild(message2);
				message2.setAlpha(0.6f);
				//message2.setY(message2.getY() + 120);
				message2.setAutoUpdate();
				return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.DONT_KILL)) {
			//	initDontKillHud();
				// return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.KILL_BAT)) {
			//	initKillGenericHUD("help6.png");
				// return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.KILL_HOLE)) {
		//		initKillGenericHUD("help2.png");
				// return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.KILL_ARMOR)) {
		//		initKillGenericHUD("help5.png");
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
			stringList.add("help3.png");
			stringList.add("help4.png");

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

			//gameObjectController.setMustUpdate(mustUpdate);

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
					if(!s.hasParent())
					scene.attachChild(s);
				} else if (spr instanceof MyAnimateSprite) {
					MyAnimateSprite s = (MyAnimateSprite) spr;
					if(!s.hasParent())
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

			if(PhysicSingleton.getInstance().getPhysicsWorld().isLocked()){
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

			//Log.d("SIZE ", "CALSS "+spriteList.size());
			
			// gameObjectController.setMustUpdate(mustUpdate);
			// gameObjectController.update(dTime, this);
			
			// for(MySpriteGeneral spr : spriteList){
			// Log.d("SIZE ", "CALSS "+spr.getClass());
			// }

			removeEntityToUpdate();

			addEntityToUpdate();

			scene.sortChildren();
			if(soundMusic){
				SoundSingleton.getInstance().playMusic("level"+levelCurrent.getPosition()+".ogg",true);
				soundMusic = false;
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

			for (MySpriteGeneral spr : spriteListDelete) {
				try {
					
					Body b = spr.getBody();
					if (b == null) {
						continue;
					}
					PhysicSingleton.getInstance().removeBodyRightNow(b);
					b = null;
					spr.setBody(null);
				} catch (Exception e) {
				}
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
			
			/*if(loadPercentage()<20f){
				winLevelShowScore();
				return ;
			}*/
			
			if(loadPercentage()<25f){
			
				SpriteBlackLoose loose = new SpriteBlackLoose(0, 0,
						texture.getTextureByName("black.jpg"),
						texture.getVertexBufferObjectManager());
				loose.setSize(MainDropActivity.CAMERA_WIDTH + 100,
						MainDropActivity.CAMERA_HEIGHT + 100);
				loose.setZIndex(ZIndexGame.FADE);
				HUDManagerSingleton.getInstance().removeAndReplaceHud();
				scene.attachChild(loose);
				spriteListToAdd.add(loose);
			
			}else{
				
				showLooseHUD();
				//TwitterSingleton.getInstance().showTwitterRewardLogin();
				spriteListDelete.add(invisiblePointToFollow);
				
			}
			
			float percentage = loadPercentage();
			if(levelCurrent.getPercentage()<percentage){
				
				LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
						.getActivity(), DatabaseDrop.DB_NAME, null,
						MainDropActivity.DB_VERSION);
				dao.setPercentage(levelCurrent.getId(), loadPercentage());
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showLooseHUD() {
		try {
			
			LooseHUD hud = new LooseHUD(this);
			HUDManagerSingleton.getInstance().addHUD(hud, false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void looseLevelReplay() {
		try {

			
			if (player.isLoose() || player.isWin()) {
				return;
			}
			SoundSingleton.getInstance().stopCurrentMusic();
			PublicityManagerSingleton.getInstance().showIntersitial();
					
			player.showLoosing(this);
			boolean submitScore = false;

			float distanceToSubmitScore = 800*2;
			if(player.getX()>distanceToSubmitScore){
				submitScore = true;
				ScoreSingleton.getInstance().addPlayerScore(contLikeCoin,submitScore);
			}else{
				ScoreSingleton.getInstance().addPlayerScore(contLikeCoin,submitScore);
			}
			// SoundSingleton.getInstance().playSound("looseMusic.mp3");
			ScoreSingleton.getInstance().newScore(entityScore.getScore());

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
			//this.setMustUpdate(false);
			
			SceneManagerSingleton.getInstance().sendGoogleTrack("WIN Level "+levelCurrent.getPosition()+" "+levelCurrent.getName());
			this.removeEntity(invisiblePointToFollow);
			SoundSingleton.getInstance().stopCurrentMusic();
			//SoundSingleton.getInstance().playSound("winMusic.mp3");
			
			ScoreSingleton.getInstance().addPlayerScore(contLikeCoin,true);
			ScoreSingleton.getInstance().newScore(entityScore.getScore());

			SceneManagerSingleton.getInstance().setContRetry(0);

			int stars = 0;
			int adnCount = 0;

			Log.d("VALIDANDO GANAR", "GANEEEE");
			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
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
					dao.setPercentage(idLevel,100);
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

				WinHUD winHud = new WinHUD(this,level);
				HUDManagerSingleton.getInstance().addHUD(winHud, false);
				
				//SpriteMessage message2 = MyFactory.createMessage(SceneManagerSingleton.getInstance().getActivity().getString(R.string.EARN_MONEY)+100,3f); 
				//HUDManagerSingleton.getInstance().getTop().attachChild(message2);
				//message2.setAutoUpdate();
				
				initWinEvents();
				
				UserInfoSingleton.getInstance().showRateme();
				
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
			WinHUD winHud = new WinHUD(this,level);
			HUDManagerSingleton.getInstance().addHUD(winHud, false);

			//UserInfoSingleton.getInstance().increaseMoney(100);
			//SpriteMessage message2 = MyFactory.createMessage(SceneManagerSingleton.getInstance().getActivity().getString(R.string.EARN_MONEY)+100,3f); 
			//HUDManagerSingleton.getInstance().getTop().attachChild(message2);
			//message2.setAutoUpdate();
			
			initWinEvents();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initWinEvents() {
		try {
			
			SpriteColorChange colorChange = (SpriteColorChange) MyFactory.createObstacle(SpriteTypeConstant.COLOR_CHANGE, this);
			colorChange.setrBegin(51f/255f);
			colorChange.setgBegin(7f/255f);
			colorChange.setbBegin(122f/255f);
			
			colorChange.setrBegin(0f/255f);
			colorChange.setgBegin(0/255f);
			colorChange.setbBegin(0f/255f);
			
			colorChange.setTime(3);
			colorChange.setPosition(player);
			scene.attachChild(colorChange);
			player.setWin(true);
			spriteListToAdd.add(colorChange);
			
			
			SpriteStarGenerator star = (SpriteStarGenerator) MyFactory.createObstacle(SpriteTypeConstant.STAR_GENERATOR, this); 
			star.setR(255);
			star.setG(255);
			star.setB(255);
			
			star.setTimeGenerate(10);
			star.setPosition(player);
			//scene.attachChild(star);
			//spriteListToAdd.add(star);
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
			
			
			if(true){
				return;
			}
			FixtureDef WALL_FIX = PhysicsFactory
					.createFixtureDef(0f, 0.0f, 0f);
			Rectangle floor = new Rectangle(0,
					MainDropActivity.CAMERA_HEIGHT - 15,
					MainDropActivity.CAMERA_WIDTH, 15,
					texture.getVertexBufferObjectManager());
			floor.setColor(new Color(15, 50, 0));

			floor.setWidth(MainDropActivity.CAMERA_WIDTH * 1000);
			floor.setZIndex(ZIndexGame.PLAYER);
			floor.setPosition(0, MainDropActivity.CAMERA_HEIGHT-130);

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
							PhysicSingleton.getInstance().loadSpriteInWorldXML(
									spr);
					} else {
						spriteToLoadInWorld.add(spr);
					}
				} else if (spr instanceof MyAnimateSprite) {
					zIndex = ((MyAnimateSprite) spr).getZIndex();
					if (Point.distanceBetween(
							new Point(player.getX(), player.getY()), new Point(
									obj.getX(), obj.getY())) < DISTANCE_TO_ADD) {
							PhysicSingleton.getInstance().loadSpriteInWorldXML(
									spr);
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

			initEntityAddPhisycs(spriteToLoadInWorld);

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

			SmoothCamera camera = (SmoothCamera) SceneManagerSingleton
					.getInstance().getCamera();
			camera.setChaseEntity(invisiblePointToFollow);
			
			money = new ButtonMoney(0, 5,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			money.setPosition(75, 0);
			money.setAlpha(0f);
			//HUDManagerSingleton.getInstance().getTop().attachChild(money);

			SpritePlayer p = PoolObjectSingleton.getInstance().getPlayer();
			p.detachChildren();
			p.setSize(100, 100);
			p.setPosition(75, -5);
			
			HUDManagerSingleton.getInstance().getTop().attachChild(p);
			
			ButtonGenerateCheckPoint checkButton = new ButtonGenerateCheckPoint(0, 0, texture.getTextureByName("changeColor.png"),texture.getVertexBufferObjectManager(), this);
			checkButton.setSize(150, 100);
			checkButton.setPosition(0,MainDropActivity.CAMERA_HEIGHT-checkButton.getHeight());
			//HUDManagerSingleton.getInstance().getTop().attachChild(checkButton);
			//HUDManagerSingleton.getInstance().getTop().registerTouchArea(checkButton);
			
			SpriteBackground fade = new SpriteBackground(0, 0, texture.getTextureByName("levelFade.png"), texture.getVertexBufferObjectManager());
			//fade.setSize(100, 480);
			//fade.setAlpha(0.5f);
			HUDManagerSingleton.getInstance().getTop().attachChild(fade);
			
			SpriteBackground fade2 = new SpriteBackground(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager());
			fade2.setSize(100, 480);
			fade2.setAlpha(0.5f);
			fade2.setRotationCenter(fade2.getWidth()/2f,fade2.getHeight()/2f);
			fade2.setRotation(180);
			
			//HUDManagerSingleton.getInstance().getTop().attachChild(fade2);
			
			likeCounter = new SpriteLikeCount(0, 0, texture.getTextureByName("money.png"), texture.getVertexBufferObjectManager());
			likeCounter.setSize(50, 50);
			likeCounter.updateTextPosition();
			likeCounter.setPosition(p.getX()+p.getWidth(),p.getY()+p.getHeight()/2 - likeCounter.getHeight()/2);
			HUDManagerSingleton.getInstance().getTop().attachChild(likeCounter);
			

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

			for (int i = 0; i < 3; i++) {
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

	public void increaseLikeCoin() {
		try {
			
			contLikeCoin ++;
			likeCounter.increment(contLikeCoin);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EntityChangeColor getEntityChangeColor() {
		return entityChangeColor;
	}

	public void setEntityChangeColor(EntityChangeColor entityChangeColor) {
		this.entityChangeColor = entityChangeColor;
	}

	public void reloadCollition(MySpriteGeneral spr, String collitionName) {
		try {
			if(spr instanceof MySprite){
				MySprite sprite = (MySprite) spr;
				PhysicSingleton.getInstance().removeBodyRightNow(sprite.getBody());
				sprite.setBody(null);
				sprite.setXmlName(collitionName);
				PhysicSingleton.getInstance().loadSpriteInWorldXML(
						sprite);
			}else if(spr instanceof MyAnimateSprite){
				MyAnimateSprite sprite = (MyAnimateSprite) spr;
				PhysicSingleton.getInstance().removeBodyRightNow(sprite.getBody());
				sprite.setBody(null);
				sprite.setXmlName(collitionName);
				PhysicSingleton.getInstance().loadSpriteInWorldXML(
						sprite);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getContLikeCoin() {
		return contLikeCoin;
	}

	public void setContLikeCoin(int contLikeCoin) {
		this.contLikeCoin = contLikeCoin;
	}

	public float loadPercentage() {
		try {
			
			Point p1 = new Point(0, 0);
			Point p2 = new Point(0, 0);
			
			for(MySpriteGeneral spr : spriteList ){
				try {
				if(spr.getSpriteType().equals(SpriteType.BEGIN_POINT)){
					if(spr instanceof MySprite){
						p1 = new Point(((MySprite) spr).getX(), ((MySprite) spr).getY());
					}
				}
				if(spr.getSpriteType().equals(SpriteType.WIN_PLAYER)){
					if(spr instanceof MySprite){
						p2 = new Point(((MySprite) spr).getX(), ((MySprite) spr).getY());
					}
				}
				} catch (Exception e) {
				}
			}
			
			float total = Point.distanceBetween(p1, p2);
			
			float distancePlayer = Point.distanceBetween(new Point(player.getX(), player.getY()), p1);

			return distancePlayer * 100f / total;
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	
}
