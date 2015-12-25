package com.mgl.drop.game.controller;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.util.color.Color;

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
import com.mgl.base.ButtonListener;
import com.mgl.base.InfoType;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.analitycs.TrackerName;
import com.mgl.base.userinfo.PhysicSingleton;
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
import com.mgl.drop.game.controller.dijkstra.WorldNode;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.database.model.SpriteDB;
import com.mgl.drop.game.entity.BackgroundEntity;
import com.mgl.drop.game.entity.EntityComboText;
import com.mgl.drop.game.entity.EntityLive;
import com.mgl.drop.game.entity.EntityOrderZindex;
import com.mgl.drop.game.entity.EntityScore;
import com.mgl.drop.game.entity.ayoutuberunner.EntityAddSpriteImage;
import com.mgl.drop.game.entity.ayoutuberunner.EntityChangeColor;
import com.mgl.drop.game.hud.InfoHowToHUD;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.InformativeSpriteHUD;
import com.mgl.drop.game.hud.LevelBeginHUD;
import com.mgl.drop.game.hud.LevelHUD;
import com.mgl.drop.game.hud.LooseHUD;
import com.mgl.drop.game.hud.UnlockWithFacebookHUD;
import com.mgl.drop.game.hud.WinHUD;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.hud.zeolandia.MenuHUD;
import com.mgl.drop.game.model.MyObjectXml;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneTouchListener;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteBlackBackground;
import com.mgl.drop.game.sprites.SpriteBlackLoose;
import com.mgl.drop.game.sprites.SpriteBloodInHud;
import com.mgl.drop.game.sprites.SpriteCollitionRpg;
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
import com.mgl.drop.game.sprites.arunner.SpriteEater;
import com.mgl.drop.game.sprites.arunner.SpriteFloor;
import com.mgl.drop.game.sprites.arunner.SpriteHedgehog;
import com.mgl.drop.game.sprites.arunner.SpriteInvisiblePointToFollow;
import com.mgl.drop.game.sprites.arunner.SpriteLake;
import com.mgl.drop.game.sprites.arunner.SpriteMeteorLevelSelect;
import com.mgl.drop.game.sprites.arunner.SpriteObstacleSmash;
import com.mgl.drop.game.sprites.arunner.SpritePlatform;
import com.mgl.drop.game.sprites.arunner.SpriteSmasher;
import com.mgl.drop.game.sprites.ayoutuberunner.SpritePlayerCollition;
import com.mgl.drop.game.sprites.azeoland.SpriteEnemy;
import com.mgl.drop.game.sprites.azeoland.SpritePlayerAdventure;
import com.mgl.drop.game.sprites.button.ButtonGeneral;
import com.mgl.drop.game.sprites.button.StartButton;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Path;
import com.mgl.drop.util.Point;
import com.mgl.ninja.R;
import com.mgl.twitter.TwitterSingleton;

public class LevelController {

	private TextureSingleton texture = TextureSingleton.getInstance();
	private Scene scene;

	private static final float DISTANCE_BETWEEN_POINT = 12f;
	private static final float DISTANCE_TO_ADD = 2000000;

	private LevelManager levelManager;
	private float contTime = 0;

	private ButtonMoney money;

	private boolean soundMusic = true;
	private boolean isLoadFinish = false;
	private boolean removeLoadingHud = false;

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

	private SpriteZoomCamera spriteZoomIn;
	private SpriteZoomCamera spriteZoomOut;

	private Level levelCurrent;
	private GameObjectsController gameObjectController;
	private EntityLive entityLive;
	private EntityScore entityScore;

	private EntityChangeColor entityChangeColor;

	private SpritePlayerAdventure player;
	private SpritePlayerCollition playerCollition;
	private SpriteInvisiblePointToFollow invisiblePointToFollow;
	private SpriteEater eater;

	private LevelHUD levelHud;
	private int gameType;

	private int contLikeCoin;

	private LevelWorld levelWorld;
	private BattleWorld levelBattleWorld;

	private boolean goBattleMode = false;
	private boolean isInBattleMode = false;

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

			loadLevel(idLevel);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadNextLevel(Long idLevel) {
		try {

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

	public void loadLevel(Long idLevel, Long x, Long y) {
		try {

			loadLevel(idLevel);
			player.setPosition(x, y);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadLevel(Long idLevel) {
		try {
			HUDManagerSingleton.getInstance().removeAllHUD();
			//initHud();

			detachAllChild();
			SmoothCamera camera = (SmoothCamera) SceneManagerSingleton
					.getInstance().getCamera();
			camera.setCenterDirect(MainDropActivity.CAMERA_WIDTH / 2,
					MainDropActivity.CAMERA_HEIGHT / 2);
			spriteListDelete = new ArrayList<MySpriteGeneral>();
			spriteListToAdd = new ArrayList<MySpriteGeneral>();
			// poopList = new ArrayList<MyAnimateSprite>();

			switch (gameType) {
			case GameConstants.PLAY_NORMAL:
				loadNormal(idLevel);
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

	private void loadNormal(Long idLevel) {
		try {

			goBattleMode = false;

			spriteList = new ArrayList<MySpriteGeneral>();

			loadLevelFromXml(idLevel);

			attachSrpiteList();

			initInvisibleFollow();

			scene.sortChildren();

			initSceneTouchListener();

			SmoothCamera camera = (SmoothCamera) SceneManagerSingleton
					.getInstance().getCamera();
			camera.setChaseEntity(null);

			setUpdateAnimated(true);

			initGameHUD();

			initPlayer();

			isLoadFinish = true;
			mustUpdate = true;
			setUpdateAnimated(true);

			initLevelInfo();

			initLevelWorld();

			initOrderZindex();

			initCameraBounds();

			SceneManagerSingleton.getInstance().sendGoogleTrack(
					"playLevel " + levelCurrent.getPosition() + " "
							+ levelCurrent.getName());
			
			SpriteBlackBackground black = new SpriteBlackBackground(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager(), this);
			black.setZIndex(ZIndexGame.FADE);
			scene.attachChild(black);
			black.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			black.setAutoUpdate();
			
			// SoundSingleton.getInstance().playMusic("level"+levelCurrent.getPosition()+".ogg",true);
			// SoundSingleton.getInstance().playMusic("level4"+".ogg",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initCameraBounds() {
		try {
			SmoothCamera camera = (SmoothCamera) SceneManagerSingleton
					.getInstance().getCamera();
			
			camera.setChaseEntity(player);

			camera.setBoundsEnabled(true);
			camera.setBounds(0, 0, levelWorld.getWidthInPixel(),
					levelWorld.getHeightInPixel());

			camera.setCenterDirect(player.getX(),player.getY());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initOrderZindex() {
		try {

			EntityOrderZindex entity = new EntityOrderZindex();
			spriteList.add(entity);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initLevelWorld() {
		try {

			float x = 0;
			float y = 0;

			for (MySpriteGeneral spr : spriteList) {
				if (spr instanceof MySprite) {
					MySprite sprite = (MySprite) spr;

					if (sprite.getY() + sprite.getHeight() > y) {
						y = sprite.getY() + sprite.getHeight();
					}
					if (sprite.getX() + sprite.getWidth() > x) {
						x = sprite.getX() + sprite.getWidth();
					}
				} else if (spr instanceof MyAnimateSprite) {
					MyAnimateSprite sprite = (MyAnimateSprite) spr;

					if (sprite.getY() + sprite.getHeight() > y) {
						y = sprite.getY() + sprite.getHeight();
					}
					if (sprite.getX() + sprite.getWidth() > x) {
						x = sprite.getX() + sprite.getWidth();
					}
				}
			}

			levelWorld = new LevelWorld(x, y, 12, 12);
			ArrayList<SpriteCollitionRpg> collitionList = new ArrayList<SpriteCollitionRpg>();
			for (MySpriteGeneral spr : spriteList) {
				try {

					if (spr.getSpriteType().equals(SpriteType.COLLITION_RPG)) {
						collitionList.add((SpriteCollitionRpg) spr);
						spriteListDelete.add(spr);
						((SpriteCollitionRpg) spr).detachSelf();
					}

				} catch (Exception e) {
				}
			}
			// drawLevelWorld();

			levelWorld.createCollitions(collitionList);

			// HUDManagerSingleton.getInstance().addHUD(new
			// InformativeHUD("Termine de cargar mundo"), true);
			// drawLevelWorld();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void drawLevelWorld() {
		try {

			for (int j = 0; j < levelWorld.getHeight(); j++) {
				for (int i = 0; i < levelWorld.getWidth(); i++) {

					WorldNode node = levelWorld.getNode(i, j);

					String textureName = "gray.jpg";

					SpriteBackground nodeB = new SpriteBackground(node.getX(),
							node.getY(), texture.getTextureByName(textureName),
							texture.getVertexBufferObjectManager());
					nodeB.setAlpha(0.5f);
					nodeB.setSize(levelWorld.getBoxWidth(),
							levelWorld.getBoxHeight());
					nodeB.setZIndex(ZIndexGame.FADE);

					if (node.getState().equals(WorldNode.OCCUPED)) {
						nodeB.setColor(0, 0, 1);
					}

					scene.attachChild(nodeB);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initPlayer() {
		try {

			player = MyFactory.createPlayer(this);
			player.setPosition(12 * 10, 12);
			scene.attachChild(player);
			spriteList.add(player);

			// camera.setBounds(0, 0, 2000,2000);

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

	private void initSceneTouchListenerBattle() {
		try {
			SceneTouchListener sceneSmashListener = new SceneTouchListener(this);
			sceneSmashListener.setBattle(true);
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

				/*
				 * SpriteMessage message2 =
				 * MyFactory.createMessage(SceneManagerSingleton
				 * .getInstance().getActivity
				 * ().getString(R.string.BEGIN_GAME_HELP),4f);
				 * HUDManagerSingleton
				 * .getInstance().getTop().attachChild(message2);
				 * message2.setAlpha(0.6f); message2.setAutoUpdate();
				 */
				return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.DONT_KILL)) {
				// initDontKillHud();
				// return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.KILL_BAT)) {
				// initKillGenericHUD("help6.png");
				// return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.KILL_HOLE)) {
				// initKillGenericHUD("help2.png");
				// return;
			}
			if (levelCurrent.getHelpInfo().equals(InfoType.KILL_ARMOR)) {
				// initKillGenericHUD("help5.png");
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

			// gameObjectController.setMustUpdate(mustUpdate);

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
					if (!s.hasParent())
						scene.attachChild(s);
				} else if (spr instanceof MyAnimateSprite) {
					MyAnimateSprite s = (MyAnimateSprite) spr;
					if (!s.hasParent())
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

		loadLevel(idLevel);

	}

	public void update(float dTime) {
		try {

			contTime = contTime + dTime;
			// if (!isLoadFinish || !mustUpdate) {
			if (!isLoadFinish) {
				return;
			}

			if (PhysicSingleton.getInstance().getPhysicsWorld().isLocked()) {
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

			// Log.d("SIZE ", "CALSS "+spriteList.size());

			// gameObjectController.setMustUpdate(mustUpdate);
			// gameObjectController.update(dTime, this);

			// for(MySpriteGeneral spr : spriteList){
			// Log.d("SIZE ", "CALSS "+spr.getClass());
			// }

			validateBattleMode();
			
			removeEntityToUpdate();

			addEntityToUpdate();

			scene.sortChildren();

			// System.out.println("size "+spriteList.size());

			if (soundMusic) {
				SoundSingleton.getInstance().playMusic("level1" + ".mp3", true);
				soundMusic = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void validateBattleMode() {
		try {

			if (!goBattleMode) {
				return;
			}
			if (isInBattleMode) {
				return;
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

			if (loadPercentage() < 25f) {

				SpriteBlackLoose loose = new SpriteBlackLoose(0, 0,
						texture.getTextureByName("black.jpg"),
						texture.getVertexBufferObjectManager());
				loose.setSize(MainDropActivity.CAMERA_WIDTH + 100,
						MainDropActivity.CAMERA_HEIGHT + 100);
				loose.setZIndex(ZIndexGame.FADE);
				HUDManagerSingleton.getInstance().removeAndReplaceHud();
				scene.attachChild(loose);
				spriteListToAdd.add(loose);

			} else {

				showLooseHUD();
				// TwitterSingleton.getInstance().showTwitterRewardLogin();
				spriteListDelete.add(invisiblePointToFollow);

			}

			float percentage = loadPercentage();
			if (levelCurrent.getPercentage() < percentage) {

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
			// this.setMustUpdate(false);

			SceneManagerSingleton.getInstance().sendGoogleTrack(
					"WIN Level " + levelCurrent.getPosition() + " "
							+ levelCurrent.getName());
			this.removeEntity(invisiblePointToFollow);
			SoundSingleton.getInstance().stopCurrentMusic();
			// SoundSingleton.getInstance().playSound("winMusic.mp3");

			ScoreSingleton.getInstance().addPlayerScore(contLikeCoin, true);
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
					dao.setPercentage(idLevel, 100);
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

				WinHUD winHud = new WinHUD(this, level);
				HUDManagerSingleton.getInstance().addHUD(winHud, false);

				// SpriteMessage message2 =
				// MyFactory.createMessage(SceneManagerSingleton.getInstance().getActivity().getString(R.string.EARN_MONEY)+100,3f);
				// HUDManagerSingleton.getInstance().getTop().attachChild(message2);
				// message2.setAutoUpdate();

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
			WinHUD winHud = new WinHUD(this, level);
			HUDManagerSingleton.getInstance().addHUD(winHud, false);

			// UserInfoSingleton.getInstance().increaseMoney(100);
			// SpriteMessage message2 =
			// MyFactory.createMessage(SceneManagerSingleton.getInstance().getActivity().getString(R.string.EARN_MONEY)+100,3f);
			// HUDManagerSingleton.getInstance().getTop().attachChild(message2);
			// message2.setAutoUpdate();

			initWinEvents();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initWinEvents() {
		try {

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

	public void manageSceneTouch(float x, float y) {
		try {

			player.generatePathToPoint(new Point(x, y));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void createFloor() {
		try {

			if (true) {
				return;
			}
			FixtureDef WALL_FIX = PhysicsFactory.createFixtureDef(0f, 0.0f, 0f);
			Rectangle floor = new Rectangle(0,
					MainDropActivity.CAMERA_HEIGHT - 15,
					MainDropActivity.CAMERA_WIDTH, 15,
					texture.getVertexBufferObjectManager());
			floor.setColor(new Color(15, 50, 0));

			floor.setWidth(MainDropActivity.CAMERA_WIDTH * 1000);
			floor.setZIndex(ZIndexGame.PLAYER);
			floor.setPosition(0, MainDropActivity.CAMERA_HEIGHT - 130);

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
			System.out.println("LOADING...level  " + idLevel);

			InputStream is = act.getAssets().open(
					"levels/level" + idLevel + ".xml");
			System.out.println(getStringFromInputStream(is));

			MyXmlParser parser = new MyXmlParser(act.getAssets().open(
					"levels/level" + idLevel + ".xml"));

			parser.parseDocument();

			for (MyObjectXml obj : parser.getObjectList()) {
				System.out.println("LOADING " + obj.getId());


				MySpriteGeneral spr = MyFactory.createObstacle(obj.getId(),
						this);
				spr.setXmlParameter(obj.getParameter());

				if (spr instanceof MySprite) {

					MySprite mySprite = (MySprite) spr;
					scene.attachChild(mySprite);
					// mySprite.setZIndex(zIndex);
					mySprite.setPosition(obj.getX(), obj.getY());
					mySprite.setMyAngle(obj.getAngle());

				} else if (spr instanceof MyAnimateSprite) {
					MyAnimateSprite mySprite = (MyAnimateSprite) spr;
					scene.attachChild(mySprite);
					mySprite.setPosition(obj.getX(), obj.getY());
					mySprite.setMyAngle(obj.getAngle());
					// mySprite.setZIndex(zIndex);

				}

				spriteList.add(spr);
				if (true) {
					continue;
				}

				int zIndex = 0;
				if (spr instanceof MySprite) {
					zIndex = ((MySprite) spr).getZIndex();

					spriteToLoadInWorld.add(spr);

				} else if (spr instanceof MyAnimateSprite) {
					zIndex = ((MyAnimateSprite) spr).getZIndex();
					if (Point.distanceBetween(
							new Point(player.getX(), player.getY()), new Point(
									obj.getX(), obj.getY())) < DISTANCE_TO_ADD) {
						PhysicSingleton.getInstance().loadSpriteInWorldXML(spr);
					} else {
						spriteToLoadInWorld.add(spr);
					}
				}

			}

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
			if (true) {
				return;
			}
			invisiblePointToFollow = new SpriteInvisiblePointToFollow(250, 900,
					60, 60, texture.getTextureByName("arrowRight.png"),
					texture.getVertexBufferObjectManager(), this);
			// invisiblePointToFollow.setSize(50, 50);
			invisiblePointToFollow.setAlpha(0.9f);
			invisiblePointToFollow.setZIndex(ZIndexGame.FADE);
			scene.attachChild(invisiblePointToFollow);
			spriteList.add(invisiblePointToFollow);
			if (true) {
				return;
			}

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

	public SpritePlayerAdventure getPlayerAdventure() {
		return player;
	}

	public void setPlayerAdventure(SpritePlayerAdventure player) {
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

			ButtonGeneral buttonMenu = new ButtonGeneral(0, 0, texture.getTextureByName("buttonMenu.png"), texture.getVertexBufferObjectManager(), new ButtonListener() {
				
				@Override
				public void onActionUp(float x, float y) {
					MenuHUD hud = new MenuHUD(getLevelManager().getLevel());
					HUDManagerSingleton.getInstance().addHUD(hud, false);
					setMustUpdate(false);
					
				}
				
				@Override
				public void onActionMove(float x, float y) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onActionDown(float x, float y) {
					// TODO Auto-generated method stub
					
				}
			});
			
			HUDManagerSingleton.getInstance().getTop().attachChild(buttonMenu);
			HUDManagerSingleton.getInstance().getTop().registerTouchArea(buttonMenu);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

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

			mustUpdate = true;
			isLoadFinish = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SpriteEater getEater() {
		return eater;
	}

	public void setEater(SpriteEater eater) {
		this.eater = eater;
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

			contLikeCoin++;

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
			if (spr instanceof MySprite) {
				MySprite sprite = (MySprite) spr;
				PhysicSingleton.getInstance().removeBodyRightNow(
						sprite.getBody());
				sprite.setBody(null);
				sprite.setXmlName(collitionName);
				PhysicSingleton.getInstance().loadSpriteInWorldXML(sprite);
			} else if (spr instanceof MyAnimateSprite) {
				MyAnimateSprite sprite = (MyAnimateSprite) spr;
				PhysicSingleton.getInstance().removeBodyRightNow(
						sprite.getBody());
				sprite.setBody(null);
				sprite.setXmlName(collitionName);
				PhysicSingleton.getInstance().loadSpriteInWorldXML(sprite);

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

			for (MySpriteGeneral spr : spriteList) {
				try {
					if (spr.getSpriteType().equals(SpriteType.BEGIN_POINT)) {
						if (spr instanceof MySprite) {
							p1 = new Point(((MySprite) spr).getX(),
									((MySprite) spr).getY());
						}
					}
					if (spr.getSpriteType().equals(SpriteType.WIN_PLAYER)) {
						if (spr instanceof MySprite) {
							p2 = new Point(((MySprite) spr).getX(),
									((MySprite) spr).getY());
						}
					}
				} catch (Exception e) {
				}
			}

			float total = Point.distanceBetween(p1, p2);

			float distancePlayer = Point.distanceBetween(
					new Point(player.getX(), player.getY()), p1);

			return distancePlayer * 100f / total;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public LevelWorld getLevelWorld() {
		return levelWorld;
	}

	public void setLevelWorld(LevelWorld levelWorld) {
		this.levelWorld = levelWorld;
	}



	private void initBattleWorld() {
		try {
			
			HUDManagerSingleton.getInstance().removeAllHUD();

			levelBattleWorld = new BattleWorld(400, 240, 24, 24);

			ArrayList<SpriteCollitionRpg> collitionList = new ArrayList<SpriteCollitionRpg>();
			for (MySpriteGeneral spr : spriteList) {
				try {

					if (spr.getSpriteType().equals(SpriteType.COLLITION_RPG)) {
						collitionList.add((SpriteCollitionRpg) spr);
						spriteListDelete.add(spr);
						((SpriteCollitionRpg) spr).detachSelf();
					}

				} catch (Exception e) {
				}
			}

			levelBattleWorld.createCollitions(collitionList);

			initOrderZindex();
			
			//drawBattleWorld();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void goToBattleMode() {
		try {

			goBattleMode = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void drawBattleWorld() {
		try {

			for (int j = 0; j < levelBattleWorld.getHeight(); j++) {
				for (int i = 0; i < levelBattleWorld.getWidth(); i++) {

					WorldNode node = levelBattleWorld.getNode(i, j);

					String textureName = "hexagon.png";

					SpriteBackground nodeB = new SpriteBackground(node.getX(),
							node.getY(), texture.getTextureByName(textureName),
							texture.getVertexBufferObjectManager());
					nodeB.setAlpha(0.4f);
					nodeB.setSize(levelBattleWorld.getBoxWidth(),
							levelBattleWorld.getBoxHeight());
					nodeB.setZIndex(ZIndexGame.BACKGROUND2);

					if (node.getState().equals(WorldNode.OCCUPED)) {

						nodeB.setAlpha(0f);
					} else {
						nodeB.setColor(174 / 255f, 174 / 255f, 174 / 255f);
					}

					scene.attachChild(nodeB);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BattleWorld getLevelBattleWorld() {
		return levelBattleWorld;
	}

	public void setLevelBattleWorld(BattleWorld levelBattleWorld) {
		this.levelBattleWorld = levelBattleWorld;
	}

	public void goBackToAdventure() {
		try {
			initCameraBounds();
			
			initGameHUD();
			
			isInBattleMode = false;
			goBattleMode = false;
			
			SpriteBlackBackground black = new SpriteBlackBackground(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager(), this);
			black.setZIndex(ZIndexGame.FADE);
			HUDManagerSingleton.getInstance().getTop().attachChild(black);
			black.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			black.setAutoUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ButtonMoney getMoney() {
		return money;
	}

	public void setMoney(ButtonMoney money) {
		this.money = money;
	}

	public boolean isSoundMusic() {
		return soundMusic;
	}

	public void setSoundMusic(boolean soundMusic) {
		this.soundMusic = soundMusic;
	}

	public boolean isRemoveLoadingHud() {
		return removeLoadingHud;
	}

	public void setRemoveLoadingHud(boolean removeLoadingHud) {
		this.removeLoadingHud = removeLoadingHud;
	}

	public SpritePlayerCollition getPlayerCollition() {
		return playerCollition;
	}

	public void setPlayerCollition(SpritePlayerCollition playerCollition) {
		this.playerCollition = playerCollition;
	}

	public boolean isGoBattleMode() {
		return goBattleMode;
	}

	public void setGoBattleMode(boolean goBattleMode) {
		this.goBattleMode = goBattleMode;
	}

	public boolean isInBattleMode() {
		return isInBattleMode;
	}

	public void setInBattleMode(boolean isInBattleMode) {
		this.isInBattleMode = isInBattleMode;
	}


	public void setPlayer(SpritePlayerAdventure player) {
		this.player = player;
	}

	
	
}
