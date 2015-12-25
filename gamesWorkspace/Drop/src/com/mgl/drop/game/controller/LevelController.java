package com.mgl.drop.game.controller;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.internal.he;
import com.mgl.base.InfoType;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.StatusType;
import com.mgl.base.analitycs.TrackerName;
import com.mgl.crappypigeon.R;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.LevelManager;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.database.model.SpriteDB;
import com.mgl.drop.game.entity.BackgroundEntity;
import com.mgl.drop.game.entity.EntityChangeLevel;
import com.mgl.drop.game.hud.InfoBeginHUD;
import com.mgl.drop.game.hud.InfoHowToHUD;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.LevelHUD;
import com.mgl.drop.game.hud.LooseHUD;
import com.mgl.drop.game.hud.UnlockWithFacebookHUD;
import com.mgl.drop.game.hud.WinHUD;
import com.mgl.drop.game.hud.WinHudSelect;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneMoveListener;
import com.mgl.drop.game.scene.SceneMoveListener2;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteBeginPoint;
import com.mgl.drop.game.sprites.SpriteButtonPoop;
import com.mgl.drop.game.sprites.SpriteEndPoint;
import com.mgl.drop.game.sprites.SpriteInvisiblePointToFollow;
import com.mgl.drop.game.sprites.SpriteInvisibleTouch;
import com.mgl.drop.game.sprites.SpriteMonsterBasic;
import com.mgl.drop.game.sprites.SpriteMonsterFat;
import com.mgl.drop.game.sprites.SpriteMonsterJustin;
import com.mgl.drop.game.sprites.SpriteMonsterKid;
import com.mgl.drop.game.sprites.SpriteMonsterMiley;
import com.mgl.drop.game.sprites.SpriteMonsterRunner;
import com.mgl.drop.game.sprites.SpriteOvni;
import com.mgl.drop.game.sprites.SpritePigeonBegin;
import com.mgl.drop.game.sprites.SpritePlayer;
import com.mgl.drop.game.sprites.SpritePoop;
import com.mgl.drop.game.sprites.SpritePoopBar;
import com.mgl.drop.game.sprites.SpritePoopBomb;
import com.mgl.drop.game.sprites.SpritePoopMultiple;
import com.mgl.drop.game.sprites.SpritePoopRocket;
import com.mgl.drop.game.sprites.SpriteRoof;
import com.mgl.drop.game.sprites.SpriteTerrain;
import com.mgl.drop.game.sprites.SpriteWayPoint;
import com.mgl.drop.game.sprites.SpriteZoomCamera;
import com.mgl.drop.game.sprites.button.ResetButton;
import com.mgl.drop.game.sprites.button.StartButton;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Path;
import com.mgl.drop.util.Point;

public class LevelController {

	private TextureSingleton texture = TextureSingleton.getInstance();
	private Scene scene;

	private static final float DISTANCE_BETWEEN_POINT = 12f;

	private LevelManager levelManager;

	private boolean isLoadFinish = false;
	private Path path;

	private float width;
	private float heigh;

	private Sprite background;
	private SpriteTerrain principalFloor;

	private ArrayList<SpriteWayPoint> spriteWayPointList;
	private SpritePlayer player;
	private BackgroundEntity backgroundEntity;
	
	private SpritePoopBar poopBar;
	private SpriteEndPoint endPoint;
	private SpriteBeginPoint beginPoint;
	private SpritePigeonBegin pigeonBegin;

	private int mustPooped = 7;

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

	public LevelController(Scene scene, LevelManager levelManager, Long idLevel) {
		try {

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
			poopList = new ArrayList<MyAnimateSprite>();
			mustPooped = 0;

			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), MainDropActivity.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			ArrayList<SpriteDB> spriteDBList = dao.loadSpriteDBList(idLevel);

			
			
			initSpriteList(spriteDBList);

			attachSrpiteList();
			
			initPoopBar();
			initWayPointList();
			player.setPoopBar(poopBar);
			player.setPath(path);
			player.setEndPoint(endPoint);

			// scene.registerTouchArea(player);
			
			backgroundEntity.setPlayer(player);
			initPigeonBegin();

			scene.attachChild(player);
			spriteList.add(player);
			
			scene.sortChildren();
			
			initCommandButtons();

			initZoomBar();

			initInvisibleFollow();
			
			initLevelInfo();
			// initStartButton();

			// attachplayer;

			/*
			 * initLevelData(); addSprites(); initPoopBar(); initPlayer();
			 * initMonster();
			 * 
			 * // initStartButton(); initInvisibleFollow();
			 */

			isLoadFinish = true;
			
			mustUpdate = true;
			
			setUpdateAnimated(false);
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	private void initLevelInfo() {
		try {
			
			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), MainDropActivity.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			
			levelCurrent =  dao.loadLevelById(idLevel);
			
			if(levelCurrent==null){
				System.out.println("null level");
				return;
			}
			System.out.println("level info "+ levelCurrent.getHelpInfo()+" id "+levelCurrent.getId()+" facebook "+levelCurrent.getFacebook());
			if(levelCurrent.getHelpInfo().equals(InfoType.NOTHING)){
				System.out.println("Nothing");
			//	return;
			}
			
			if(levelCurrent.getHelpInfo().equals(InfoType.BEGIN_GAME)){

				initBeginGameHud();
				//return;
			}
			if(levelCurrent.getHelpInfo().equals(InfoType.HOWTO_SPLIT)){
				
				initSplitHud();
				
			}
			if(levelCurrent.getHelpInfo().equals(InfoType.HOWTO_BOMB)){
				
				initBombHud();
				
			}
			if(levelCurrent.getHelpInfo().equals(InfoType.HOWTO_ROCKET)){
				
				initRocketHud();
				
			}
			if(levelCurrent.getHelpInfo().equals(InfoType.HOWTO_KID)){
				
				initKidHud();
				
			}
			if(levelCurrent.getFacebook()){
				initLevelBonusHud();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}

	private void initLevelBonusHud() {
		try {
			
			InformativeHUD hud = new InformativeHUD(SceneManagerSingleton.getInstance().getActivity().getResources().getString(R.string.bonusLevel));
			HUDManagerSingleton.getInstance().addHUD(hud);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void initKidHud() {
		try {
			
			ArrayList<String> stringList = new ArrayList<String>();
			stringList.add("howToKidA.png");
			stringList.add("howToKidB.png");
			
			InfoHowToHUD hud = new InfoHowToHUD(this,stringList);
			HUDManagerSingleton.getInstance().addHUD(hud);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void initRocketHud() {
		try {
			
			ArrayList<String> stringList = new ArrayList<String>();
			stringList.add("howToRocketA.png");
			stringList.add("howToRocketB.png");
			stringList.add("howToRocketC.png");
			stringList.add("howToRocketD.png");
			
			InfoHowToHUD hud = new InfoHowToHUD(this,stringList);
			HUDManagerSingleton.getInstance().addHUD(hud);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void initBombHud() {
		try {
			
			ArrayList<String> stringList = new ArrayList<String>();
			stringList.add("howToBombA.png");
			stringList.add("howToBombB.png");
			stringList.add("howToBombC.png");
			stringList.add("howToBombD.png");
			stringList.add("howToBombE.png");
			
			InfoHowToHUD hud = new InfoHowToHUD(this,stringList);
			HUDManagerSingleton.getInstance().addHUD(hud);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void initSplitHud() {
		try {
			
			ArrayList<String> stringList = new ArrayList<String>();
			stringList.add("howToSplitA.png");
			stringList.add("howToSplitB.png");
			stringList.add("howToSplitC.png");
			stringList.add("howToSplitD.png");
			
			InfoHowToHUD hud = new InfoHowToHUD(this,stringList);
			HUDManagerSingleton.getInstance().addHUD(hud);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void initBeginGameHud() {
		try {
			
			ArrayList<String> stringList = new ArrayList<String>();
			stringList.add("howToShitA.png");
			stringList.add("howToShitB.png");
			stringList.add("howToShitC.png");
			stringList.add("howToShitD.png");
			
			InfoHowToHUD hud = new InfoHowToHUD(this,stringList);
			HUDManagerSingleton.getInstance().addHUD(hud);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void setUpdateAnimated(boolean mustUpdate) {
		
		try {
			
			for(MySpriteGeneral spr : spriteList){
				if(spr instanceof MyAnimateSprite){
					MyAnimateSprite ms = (MyAnimateSprite) spr;
					ms.setMustUpdate(mustUpdate);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void changePoopEnd() {
		try {
			
			player.setVisible(false);
			player.endFlying();
			endPoint.setVisible(false);
			
			player.detachSelf();
			endPoint.detachSelf();
			
			//spriteListDelete.add(player);
			//spriteListDelete.add(endPoint);
			
			SpritePigeonBegin pigeonEnd = new SpritePigeonBegin(0, 0,
					texture.getTextureAnimateByName("pigeonBegin.png"),
					texture.getVertexBufferObjectManager(), this);
			pigeonEnd.setPosition(player);
			
			pigeonEnd.setSize(80, 80);
			scene.attachChild(pigeonEnd);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	private void initPigeonBegin() {
		try {

			player.setVisible(false);
			
			beginPoint = new SpriteBeginPoint(
					0, 0, 50, 80,
					texture.getTextureByName("nido.png"),
					texture.getVertexBufferObjectManager(),this);
			
			
			beginPoint.setSize(50, 40);

			beginPoint.setPosition(player.getX()+5,player.getY()+40);
			beginPoint.setVisible(false);
			scene.attachChild(beginPoint);
			
			pigeonBegin = new SpritePigeonBegin(0, 0,
					texture.getTextureAnimateByName("pigeonBegin.png"),
					texture.getVertexBufferObjectManager(), this);
			pigeonBegin.setPosition(player);
			pigeonBegin.setSize(80, 80);
			scene.attachChild(pigeonBegin);
			
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initZoomBar() {
		try {
			SmoothCamera cam = (SmoothCamera) SceneManagerSingleton
					.getInstance().getCamera();
			
			SpriteInvisibleTouch spr = new SpriteInvisibleTouch(0,0,150,150,texture.getTextureByName("point.png"),texture.getVertexBufferObjectManager(),null);
			
			spriteZoomIn = new SpriteZoomCamera(cam.getWidth() - 60*2-10, 0,
					texture.getTextureByName("zoomIn.png"),
					texture.getVertexBufferObjectManager(), true,this);
			spriteZoomOut = new SpriteZoomCamera(cam.getWidth() - 60-10, 0,
					texture.getTextureByName("zoomOut.png"),
					texture.getVertexBufferObjectManager(), false,this);

			spriteZoomIn.setSize(60, 60);
			spriteZoomOut.setSize(60, 60);
			
			spriteZoomIn.setPosition(spriteZoomOut.getX()-spriteZoomOut.getWidth()-20,spriteZoomOut.getY());
			
			
			cam.setBounds(0, 0, width, heigh);
			cam.setBoundsEnabled(true);

			cam.getHUD().attachChild(spriteZoomIn);
			cam.getHUD().registerTouchArea(spriteZoomIn);

			cam.getHUD().attachChild(spriteZoomOut);
			cam.getHUD().registerTouchArea(spriteZoomOut);

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
			LevelHUD hud = new LevelHUD(scene, levelManager);

			HUDManagerSingleton.getInstance().addHUD(hud);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initCommandButtons() {
		try {
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			SpriteButtonPoop buttonPoop = new SpriteButtonPoop(0,
					camera.getHeight() - 80,
					texture.getTextureAnimateByName("poopButton.png"),
					texture.getVertexBufferObjectManager(), this, player);
			buttonPoop.setSize(100, 100);
			buttonPoop.setPosition(0,
					camera.getHeight() - buttonPoop.getHeight());
			HUD hud = camera.getHUD();
			hud.attachChild(buttonPoop);
			hud.registerTouchArea(buttonPoop);

			HUDManagerSingleton.getInstance().addHUD(hud);

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
						//spriteList.add(backgroundEntity);
						
						width = 1024*2;
						heigh = 800;
						
						if(true){
							continue;
						}
						SpriteBackground background = new SpriteBackground(
								spr.getX(), spr.getY(),
								texture.getTextureByName(spr.getTextureName()),
								texture.getVertexBufferObjectManager());
						spriteList.add(background);
						
						background.setZIndex(0);
						
						background = new SpriteBackground(
								spr.getX(), spr.getY(),
								texture.getTextureByName(spr.getTextureName()),
								texture.getVertexBufferObjectManager());
						background.setX(background.getWidth()-1);
						spriteList.add(background);
						background.setZIndex(0);
						
						width = background.getWidth()*2;
						heigh = background.getHeight();
						break;

					case SpriteTypeConstant.TERRAIN:

						SpriteTerrain terrain = new SpriteTerrain(spr.getX(),
								spr.getY(), texture.getTextureByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager());
						spriteList.add(terrain);
						
						terrain.setZIndex(2);
						
						terrain = new SpriteTerrain(terrain.getWidth()-1,
								spr.getY(), texture.getTextureByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager());
						spriteList.add(terrain);
						
						break;

						
					case SpriteTypeConstant.OVNI:
						
						s = spr.getData().split("@");
						
						Float width = 120f;
						Float height = 80f;
						try {
							width = Float.valueOf(s[4]);
							height = Float.valueOf(s[5]);
						} catch (Exception e) {
							
						}
						
						SpriteOvni ovni = new SpriteOvni(spr.getX(),
								spr.getY(), texture.getTextureAnimateByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager(),Long.valueOf(s[0]), Long.valueOf(s[1]), Long.valueOf(s[2]),Long.valueOf(s[3]),this,width,height);
						
						
//						ovni.setSize(width, height);
						spriteList.add(ovni);
						ovni.setZIndex(4);
						
						break;
						
					case SpriteTypeConstant.BOX:
						
						s = spr.getData().split("@");
						SpriteTerrain box = new SpriteTerrain(spr.getX(),
								spr.getY(), texture.getTextureByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager());
						box.setSize(Long.valueOf(s[0]), Long.valueOf(s[1]));

						box.setZIndex(4);
						spriteList.add(box);
						
						break;
						
					case SpriteTypeConstant.MONSTER_BASIC:
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
						mustPooped++;
						break;
					case SpriteTypeConstant.MONSTER_RUNNER:
						s = spr.getData().split("@");

						SpriteMonsterRunner monsterRunner = new SpriteMonsterRunner(
								spr.getX(), spr.getY(),
								texture.getTextureAnimateByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager(), this,
								Long.valueOf(s[0]), Long.valueOf(s[1]));

						monsterRunner.setSize(50, 80);
						monsterRunner.setZIndex(4);
						monsterRunner.setSpeed(Long.valueOf(s[2]).floatValue());
						spriteList.add(monsterRunner);
						mustPooped++;
						break;
					case SpriteTypeConstant.MONSTER_MILEY:
						s = spr.getData().split("@");

						SpriteMonsterMiley monsterMiley = new SpriteMonsterMiley(
								spr.getX(), spr.getY(),
								texture.getTextureAnimateByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager(), this,
								Long.valueOf(s[0]), Long.valueOf(s[1]));

						monsterMiley.setSize(60, 80);
						monsterMiley.setZIndex(4);
						monsterMiley.setSpeed(Long.valueOf(s[2]).floatValue());
						spriteList.add(monsterMiley);
						mustPooped++;
						break;

					case SpriteTypeConstant.MONSTER_JUSTIN:
						s = spr.getData().split("@");

						SpriteMonsterJustin monsterJustin = new SpriteMonsterJustin(
								spr.getX(), spr.getY(),
								texture.getTextureAnimateByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager(), this,
								Long.valueOf(s[0]), Long.valueOf(s[1]));

						monsterJustin.setSize(60, 80);
						monsterJustin.setZIndex(4);
						monsterJustin.setSpeed(Long.valueOf(s[2]).floatValue());
						spriteList.add(monsterJustin);
						mustPooped++;
						break;

					case SpriteTypeConstant.MONSTER_FAT:
						s = spr.getData().split("@");

						SpriteMonsterFat monsterFat = new SpriteMonsterFat(
								spr.getX(), spr.getY(),
								texture.getTextureAnimateByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager(), this,
								Long.valueOf(s[0]), Long.valueOf(s[1]));

						monsterFat.setSize(60, 80);
						monsterFat.setZIndex(4);
						monsterFat.setSpeed(Long.valueOf(s[2]).floatValue());
						spriteList.add(monsterFat);
						mustPooped++;
						break;
					case SpriteTypeConstant.MONSTER_KID:
						s = spr.getData().split("@");

						SpriteMonsterKid monsterKid = new SpriteMonsterKid(
								spr.getX(), spr.getY(),
								texture.getTextureAnimateByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager(), this,
								Long.valueOf(s[0]), Long.valueOf(s[1]));
						monsterKid.setSize(50, 60);
						monsterKid.setPosition(monsterKid.getX(),
								monsterKid.getY() + 25);
						monsterKid.setSpeed(Long.valueOf(s[2]).floatValue());
						monsterKid.setDistanceToShoot(Long.valueOf(s[3])
								.floatValue());
						monsterKid.setZIndex(4);
						spriteList.add(monsterKid);
						mustPooped++;
						break;

					case SpriteTypeConstant.PLAYER:

						player = new SpritePlayer(spr.getX(), spr.getY(),
								texture.getTextureAnimateByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager(), this);

						player.setZIndex(6);
						player.setSize(80, 80);
						// spriteList.add(player);
						break;

					case SpriteTypeConstant.POOP_BASIC:
						for (int i = 0; i < Long.valueOf(spr.getData().split(
								"@")[0]); i++) {
							SpritePoop poop = new SpritePoop(spr.getX(),
									spr.getY(),
									texture.getTextureAnimateByName(spr
											.getTextureName()),
									texture.getVertexBufferObjectManager(),
									this);
							poopList.add(poop);
						}
						break;

					case SpriteTypeConstant.POINT_LIST:
						s = spr.getData().split("@");
						path = new Path();
						for (int i = 0; i < s.length; i++) {
							String[] sAux = s[i].split(",");
							Point p = new Point(Long.valueOf(sAux[0]),
									Long.valueOf(sAux[1]));
							path.push(p);
						}

						break;
					case SpriteTypeConstant.END_POINT:

						SpriteEndPoint endPoint = new SpriteEndPoint(
								spr.getX(), spr.getY(), 30, 50,
								texture.getTextureByName(spr.getTextureName()),
								texture.getVertexBufferObjectManager(), this);
						spriteList.add(endPoint);
						endPoint.setSize(50, 40);
						this.endPoint = endPoint;
						endPoint.setZIndex(3);
						break;
					case SpriteTypeConstant.POOP_BOMB:
						for (int i = 0; i < Long.valueOf(spr.getData().split(
								"@")[0]); i++) {
							SpritePoopBomb poop = new SpritePoopBomb(
									spr.getX(), spr.getY(),
									texture.getTextureAnimateByName(spr
											.getTextureName()),
									texture.getVertexBufferObjectManager(),
									this);
							poopList.add(poop);
						}

						break;
					case SpriteTypeConstant.POOP_ROCKET:
						for (int i = 0; i < Long.valueOf(spr.getData().split(
								"@")[0]); i++) {
							SpritePoopRocket poop = new SpritePoopRocket(
									spr.getX(), spr.getY(),
									texture.getTextureAnimateByName(spr
											.getTextureName()),
									texture.getVertexBufferObjectManager(),
									this);
							poopList.add(poop);
						}

						break;
					case SpriteTypeConstant.POOP_MULTIPLE:
						for (int i = 0; i < Long.valueOf(spr.getData().split(
								"@")[0]); i++) {
							SpritePoopMultiple poop = new SpritePoopMultiple(
									spr.getX(), spr.getY(),
									texture.getTextureAnimateByName(spr
											.getTextureName()),
									texture.getVertexBufferObjectManager(),
									this);
							poopList.add(poop);
						}

						break;
					case SpriteTypeConstant.ROOF:
						s = spr.getData().split("@");
						SpriteRoof roof = new SpriteRoof(spr.getX(),
								spr.getY(), Long.valueOf(s[0]),Long.valueOf(s[1]),texture.getTextureByName(spr
										.getTextureName()),
								texture.getVertexBufferObjectManager(), this);
						roof.setZIndex(1);

						spriteList.add(roof);
						break;
					default:
						break;
					}

				} catch (Exception e) {

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initInvisibleFollow() {
		try {

			
			SpriteInvisiblePointToFollow sprite = new SpriteInvisiblePointToFollow(
					250, 900, 60, 60, texture.getTextureByName("feather.png"),
					texture.getVertexBufferObjectManager(), this);

			sprite.setPath(path);
			

			// sprite.addPoint(new Point(0, heigh));
			sprite.setEndPoint(endPoint);
			sprite.setPosition(endPoint.getX(), endPoint.getY());

			spriteList.add(sprite);
			scene.attachChild(sprite);

			initStartButton(sprite);

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

	private void initPoopBar() {
		try {

			poopBar = new SpritePoopBar(100, 100, 300, 60,
					texture.getTextureByName("poopBar.png"),
					texture.getVertexBufferObjectManager(), this, poopList);

			poopBar.setPosition(140, 10);
			poopBar.setAlpha(0.8f);

			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			camera.getHUD().attachChild(poopBar);

			// scene.attachChild(poopBar);

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

	public void initStartButton(SpriteInvisiblePointToFollow sprite) {
		try {

			Camera camera = SceneManagerSingleton.getInstance().getCamera();

			StartButton start = new StartButton(camera.getCenterX(),
					camera.getCenterY(), 80, 80,
					texture.getTextureByName("startButton.png"),
					texture.getVertexBufferObjectManager(), this, scene, sprite);

			start.setPosition(camera.getWidth() / 2 - start.getWidth() / 2,
					camera.getHeight() / 2 - start.getHeight() / 2);

			
			
			//addSpriteToUpdate(start);
			
			camera.getHUD().registerTouchArea(start);
			camera.getHUD().attachChild(start);

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initMonster() {
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

	}

	public void reloadLevel() {
		scene.detachChildren();
		scene.clearTouchAreas();
		scene.clearEntityModifiers();
		scene.clearChildScene();

		loadLevel();

	}

	private void initPlayer() {
		try {

			player = new SpritePlayer(310, 20,
					texture.getTextureAnimateByName("paloma.png"),
					texture.getVertexBufferObjectManager(), this);
			player.setSize(75, 75);
			player.setRotation(90f);
			player.setPath(path);
			scene.attachChild(player);
			scene.registerTouchArea(player);

			spriteList.add(player);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initWayPointList() {
		try {

			spriteWayPointList = new ArrayList<SpriteWayPoint>();
			int cont = 1;
			Point lastPoint = null;
			for (Point p : path.getPath()) {
				if (cont >= 2) {
					float distanceB = Point.distanceBetween(lastPoint, p);

					if (distanceB >= DISTANCE_BETWEEN_POINT) {
						Double Quantity = Double.valueOf(distanceB
								/ DISTANCE_BETWEEN_POINT);

						Log.d("distance btw", "dis  " + distanceB);
						for (int i = 0; i <= Quantity.intValue(); i++) {

							float angle = Point.angleBetween2(lastPoint, p);

							Double y = lastPoint.getY() + Math.sin(angle)
									* (DISTANCE_BETWEEN_POINT * i);
							Double x = lastPoint.getX() + Math.cos(angle)
									* (DISTANCE_BETWEEN_POINT * i);

							SpriteWayPoint point = new SpriteWayPoint(
									x.floatValue(), y.floatValue(),
									texture.getTextureByName("point.png"),
									texture.getVertexBufferObjectManager(),
									scene);
							point.setSize(2, 2);
							scene.attachChild(point);
							spriteWayPointList.add(point);
							// spriteList.add(point);
						}

					}
				}
				lastPoint = p;

				cont++;
				SpriteWayPoint point = new SpriteWayPoint(p.getX(), p.getY(),
						texture.getTextureByName("feather.png"),
						texture.getVertexBufferObjectManager(), scene);
				point.setSize(25, 25);
				point.setPosition(p.getX() - point.getWidth() / 2, p.getY()
						- point.getHeight() / 2);
				scene.attachChild(point);
				spriteWayPointList.add(point);
				spriteList.add(point);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initLevelData() {
		try {

			background = new Sprite(0, 0,
					texture.getTextureByName("background1.png"),
					texture.getVertexBufferObjectManager());
			// background.setHeight(1200);
			// background.setWidth(700);

			scene.attachChild(background);
			width = background.getWidth();
			heigh = background.getHeight();

			principalFloor = new SpriteTerrain(0, 0,
					texture.getTextureByName("floorPrincipal.png"),
					texture.getVertexBufferObjectManager());
			scene.attachChild(principalFloor);
			spriteList.add(principalFloor);

			SpriteTerrain backgroundSprite = new SpriteTerrain(150, 380, 10,
					300, texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager(), this);
			scene.attachChild(backgroundSprite);
			spriteList.add(backgroundSprite);

			path = new Path();

			path.push(new Point(300, 30));
			path.push(new Point(300, 100));
			path.push(new Point(300, 200));
			path.push(new Point(300, 300));
			path.push(new Point(300, 350));
			path.push(new Point(420, 400));
			path.push(new Point(300, 900));

			SpriteBeginPoint beginPoint = new SpriteBeginPoint(315, 30, 30, 30,
					texture.getTextureByName("nido.png"),
					texture.getVertexBufferObjectManager(), this);
			scene.attachChild(beginPoint);
			SpriteEndPoint endPoint = new SpriteEndPoint(300, 900, 30, 30,
					texture.getTextureByName("nido.png"),
					texture.getVertexBufferObjectManager(), this);
			scene.attachChild(endPoint);
			spriteList.add(endPoint);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(float dTime) {
		try {

			if (!isLoadFinish || !mustUpdate) {

				return;
			}

			// for(MySprite sprite :spriteWayPointList){
			// sprite.update(dTime, this);
			// }
			// player.update(dTime, this);
			for (MySpriteGeneral sprite : spriteList) {
				sprite.update(dTime, this);
			}

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

	public void validateWin() {
		try {
			int contPooped = 0;
			for (MySpriteGeneral sprite : spriteList) {
				if (sprite.getSpriteType().equals(SpriteType.OBJETIVE)
						&& (sprite.getStatus().equals(StatusType.POOPED) || sprite
								.getStatus().equals(StatusType.FINISHED))) {
					contPooped++;
				}
			}
			if (contPooped >= mustPooped) {
				// then i win this level show score and options
				
				winLevelShowScore();
				

			} else {
				// you loose this level show reset level, play again

				looseLevelReplay();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void looseLevelReplay() {
		try {

			mustUpdate = false;
			
			SoundSingleton.getInstance().playSound("looseMusic.mp3");
			
			LooseHUD hud = new LooseHUD(scene,levelManager);
			HUDManagerSingleton.getInstance().addHUD(hud);
			

			Log.d("contRetry", "cont "
					+ SceneManagerSingleton.getInstance().getContRetry());
			if (SceneManagerSingleton.getInstance().getContRetry() >= MainDropActivity.HELP_LEVEL_RETRY) {
				showFacebookHelp();
				SceneManagerSingleton.getInstance().setContRetry(0);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showFacebookHelp() {
		try {

			
			if(!MainDropActivity.isConnectingToInternet()){		
				
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
					if(level.getFacebook()){
						continue;
					}
					break;
				}
			}

			if (level == null) {
				return;
			}
			UnlockWithFacebookHUD hud = new UnlockWithFacebookHUD(level);
			HUDManagerSingleton.getInstance().addHUD(hud);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void winLevelShowScore() {
		try {
			// mustUpdate = false;

			/*
			 * EntityChangeLevel entity = new EntityChangeLevel(3.3f);
			 * addSpriteToUpdate(entity);
			 */

			SoundSingleton.getInstance().playSound("winMusic.mp3");
			
			SceneManagerSingleton.getInstance().setContRetry(0);

			int stars = 0;
			
			if( poopBar.getRemainPoop() >=2 ){
				stars = 3;
			}
			if( poopBar.getRemainPoop() == 1 ){
				stars = 2;
			}
			if( poopBar.getRemainPoop() == 0 ){
				stars = 1;
			}
			
			
			Log.d("VALIDANDO GANAR", "GANEEEE");
			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), MainDropActivity.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			dao.setStars(idLevel, stars);
			
			
			Level level = null;
			boolean found = false;
			boolean foundBonus = false;
			for (Level l : levelManager.getLevelList()) {
				if (l.getId().equals(idLevel)) {
					found = true;
					continue;
				}
				if (found) {
					level = l;
					if(level.getFacebook()){
						foundBonus = true;
						//continue;
						break;
					}
					break;
				}
			}

			if (level == null) {
				// WON THIS LEVEL SERIES
				// TODO
				try {
					Tracker t = SceneManagerSingleton.getInstance().getActivity().getTracker(TrackerName.APP_TRACKER);
					t.setScreenName("Win All Levels 1");
					t.send(new HitBuilders.AppViewBuilder().build());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return;
			}

			dao.setNextAvalible(level.getId());
			
			/*if(foundBonus){
				WinHudSelect winHud = new WinHudSelect(scene, this, level, stars);
				HUDManagerSingleton.getInstance().addHUD(winHud);
			}else{

				WinHUD winHud = new WinHUD(scene, this, level, stars);
				HUDManagerSingleton.getInstance().addHUD(winHud);
			}*/
			WinHUD winHud = new WinHUD(scene, this, level, stars);
			HUDManagerSingleton.getInstance().addHUD(winHud);

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

	public ArrayList<SpriteWayPoint> getSpriteWayPointList() {
		return spriteWayPointList;
	}

	public void setSpriteWayPointList(
			ArrayList<SpriteWayPoint> spriteWayPointList) {
		this.spriteWayPointList = spriteWayPointList;
	}

	public SpritePlayer getPlayer() {
		return player;
	}

	public void setPlayer(SpritePlayer player) {
		this.player = player;
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

	public int getMustPooped() {
		return mustPooped;
	}

	public void setMustPooped(int mustPooped) {
		this.mustPooped = mustPooped;
	}

	public SpriteTerrain getPrincipalFloor() {
		return principalFloor;
	}

	public void setPrincipalFloor(SpriteTerrain principalFloor) {
		this.principalFloor = principalFloor;
	}

	public SpritePoopBar getPoopBar() {
		return poopBar;
	}

	public void setPoopBar(SpritePoopBar poopBar) {
		this.poopBar = poopBar;
	}

	public SpriteEndPoint getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(SpriteEndPoint endPoint) {
		this.endPoint = endPoint;
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

	public SpritePigeonBegin getPigeonBegin() {
		return pigeonBegin;
	}

	public void setPigeonBegin(SpritePigeonBegin pigeonBegin) {
		this.pigeonBegin = pigeonBegin;
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

	public SpriteBeginPoint getBeginPoint() {
		return beginPoint;
	}

	public void setBeginPoint(SpriteBeginPoint beginPoint) {
		this.beginPoint = beginPoint;
	}

	
	
}
