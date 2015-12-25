package com.mgl.drop.game.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.debugdraw.DebugRenderer;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.util.color.Color;

import android.app.Activity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.google.example.games.basegameutils.GooglePlayGameSingleton;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.userinfo.PhysicSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.entity.ice.EntityFallingDanger;
import com.mgl.drop.game.entity.ice.EntityPenguinBackground;
import com.mgl.drop.game.entity.ice.EntityPenguinSnow;
import com.mgl.drop.game.entity.ice.EntityShowScore;
import com.mgl.drop.game.hud.InfoHowToHUD;
import com.mgl.drop.game.hud.InformativeSpriteHUD;
import com.mgl.drop.game.hud.LevelBeginHUD;
import com.mgl.drop.game.hud.aicerunner.LooseHUD;
import com.mgl.drop.game.model.MyObjectXml;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneTouchListener;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpritePlayerBase;
import com.mgl.drop.game.sprites.aicerunner.SpriteFloor;
import com.mgl.drop.game.sprites.aicerunner.SpritePlayer;
import com.mgl.drop.game.sprites.button.ice.ButtonMove;
import com.mgl.drop.texture.TextureSingleton;

public class LevelController {

	private TextureSingleton texture = TextureSingleton.getInstance();
	private Scene scene;

	private boolean isLoadFinish = false;
	private boolean removeLoadingHud = false;

	private Long idLevel;
	private ArrayList<MySpriteGeneral> spriteList;
	private ArrayList<MySpriteGeneral> spriteListDelete;
	private ArrayList<MySpriteGeneral> spriteListToAdd;

	private boolean mustUpdate = false;

	private SpritePlayer player;
	private EntityShowScore scoreEntity;

	public LevelController(Scene scene) {
		this.scene = scene;
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

			spriteListDelete = new ArrayList<MySpriteGeneral>();
			spriteListToAdd = new ArrayList<MySpriteGeneral>();

			loadNormal();

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

			initCollitionDetectionBox2d();

			PhysicSingleton.getInstance().pause(true);


			/*  DebugRenderer debug = new DebugRenderer(PhysicSingleton
			  .getInstance().getPhysicsWorld(),
			  texture.getVertexBufferObjectManager());
			  debug.setZIndex(ZIndexGame.FADE);
			  
			  scene.attachChild(debug);
			 
			*/
			// loadLevelFromXml(idLevel);

			attachSrpiteList();

			scene.sortChildren();

			// initStartButton();

			initSceneTouchListener();

			SmoothCamera camera = (SmoothCamera) SceneManagerSingleton
					.getInstance().getCamera();
			camera.setChaseEntity(null);

			setUpdateAnimated(true);

			initFloorLine();

			initEntityDanger();
			// initLoadingHud();
			initPlayer();

			setMustUpdate(false);
			removeLoadingHud = false;
			SceneManagerSingleton.getInstance().getActivity()
					.runOnUiThread(new Runnable() {

						@Override
						public void run() {

							// loadLevelFromXml(idLevel);
							// removeLoadingHud = true;
							// HUDManagerSingleton.getInstance()
							// .removeAndReplaceHud();

						}
					});

			isLoadFinish = true;
			mustUpdate = true;
			setUpdateAnimated(true);
			PhysicSingleton.getInstance().pause(false);

			initBackground(); 

			initScoreEntity();
			
			initGameHUD();

			// SoundSingleton.getInstance().playMusic("level"+levelCurrent.getPosition()+".ogg",true);
			// SoundSingleton.getInstance().playMusic("level4"+".ogg",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initScoreEntity() {
		try {
			
			scoreEntity = new EntityShowScore(scene);
			spriteList.add(scoreEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initBackground() {
		try {
			
			EntityPenguinBackground entityPenguinBackground = new EntityPenguinBackground(this);
			spriteList.add(entityPenguinBackground);
			
			EntityPenguinSnow snowEntityFloor = new EntityPenguinSnow();
			spriteList.add(snowEntityFloor);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initEntityDanger() {
		try {
			
			EntityFallingDanger danger = new EntityFallingDanger();
			spriteList.add(danger);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initPlayer() {
		try {

			FixtureDef FIXTURE = PhysicsFactory
					.createFixtureDef(1000f, 0.0f, 0.2f);

			player = new SpritePlayer(0, 0,
					texture.getTextureAnimateByName("player.png"),
					texture.getVertexBufferObjectManager(), this);
			player.setSize(60, 90);
			player.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - player.getWidth() / 2,
					MainDropActivity.CAMERA_HEIGHT / 2 + 100);

			
			player.setCollitionType(CollitionType.COLLITION_PLAYER);
			player.setZIndex(ZIndexGame.PLAYER);
			
			PhysicSingleton.getInstance().loadSpriteInWorld(player, FIXTURE, BodyType.DynamicBody, false);

			scene.attachChild(player);
			spriteList.add(player);
			
			player.createReflex();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initFloorLine() {
		try {
			FixtureDef WALL_FIX = PhysicsFactory.createFixtureDef(0f, 0.0f, 0f);

			int offset = 230;
			
			SpriteFloor floor = new SpriteFloor(0, 0,
					texture.getTextureByName("floor1.png"),
					texture.getVertexBufferObjectManager());
			floor.setSize(800, 50);
			floor.setPosition(-100, MainDropActivity.CAMERA_HEIGHT - offset);

			Body body = PhysicsFactory.createBoxBody(PhysicSingleton
					.getInstance().getPhysicsWorld(), floor,
					BodyType.StaticBody, WALL_FIX);
			floor.setBody(body);
			body.setUserData(floor);
			scene.attachChild(floor);

			SpriteFloor left = new SpriteFloor(0, 0, texture.getTextureByName("floor1.png"), texture.getVertexBufferObjectManager());
			left.setSize(50, 200);
			left.setPosition(left.getWidth()*-1 - 1, MainDropActivity.CAMERA_HEIGHT - offset - left.getHeight());
			
			Body bodyLeft = PhysicsFactory.createBoxBody(PhysicSingleton
					.getInstance().getPhysicsWorld(), left,
					BodyType.StaticBody, WALL_FIX);
			left.setBody(bodyLeft);
			bodyLeft.setUserData(left);
			scene.attachChild(left);
			
			
			SpriteFloor right = new SpriteFloor(0, 0, texture.getTextureByName("floor1.png"), texture.getVertexBufferObjectManager());
			right.setSize(50, 200);
			right.setPosition(MainDropActivity.CAMERA_WIDTH+1, MainDropActivity.CAMERA_HEIGHT - offset - left.getHeight());
			
			Body bodyRight = PhysicsFactory.createBoxBody(PhysicSingleton
					.getInstance().getPhysicsWorld(), right,
					BodyType.StaticBody, WALL_FIX);
			right.setBody(bodyRight);
			bodyRight.setUserData(right);
			scene.attachChild(right);
			
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
			}
			removeEntityToUpdate();

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

	private void initSceneTouchListener() {
		try {
			SceneTouchListener sceneSmashListener = new SceneTouchListener(this);
			scene.setOnSceneTouchListener(sceneSmashListener);

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

	private void showLooseHUD() {
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

				MySpriteGeneral spr = MyFactory.createObstacle(obj.getId(),
						this);
				spr.setXmlParameter(obj.getParameter());

				int zIndex = 0;
				if (spr instanceof MySprite) {
					zIndex = ((MySprite) spr).getZIndex();

					PhysicSingleton.getInstance().loadSpriteInWorldXML(spr);
				} else if (spr instanceof MyAnimateSprite) {
					zIndex = ((MyAnimateSprite) spr).getZIndex();
					PhysicSingleton.getInstance().loadSpriteInWorldXML(spr);
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

	public void initGameHUD() {
		try {

			int  offset = 100;
			
			SmoothCamera camera = (SmoothCamera) SceneManagerSingleton
					.getInstance().getCamera();

			HUDManagerSingleton.getInstance().addHUD(new HUD(), false);
			
			ButtonMove moveRight = new ButtonMove(0, 0, 90, 70,
					texture.getTextureByName("arrowRight.png"),
					texture.getVertexBufferObjectManager(), this, true);
			moveRight.setPosition(
					MainDropActivity.CAMERA_WIDTH - moveRight.getWidth(),
					MainDropActivity.CAMERA_HEIGHT - moveRight.getHeight() - offset);
			HUDManagerSingleton.getInstance().getTop().attachChild(moveRight);
			HUDManagerSingleton.getInstance().getTop()
					.registerTouchArea(moveRight);

			ButtonMove moveLeft = new ButtonMove(0, 0, 90, 70,
					texture.getTextureByName("arrowLeft.png"),
					texture.getVertexBufferObjectManager(), this, false);
			moveLeft.setPosition(0,
					MainDropActivity.CAMERA_HEIGHT - moveLeft.getHeight() - offset);
			HUDManagerSingleton.getInstance().getTop().attachChild(moveLeft);
			HUDManagerSingleton.getInstance().getTop()
					.registerTouchArea(moveLeft);

		} catch (Exception e) {
			e.printStackTrace();
		}

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

	public SpritePlayer getPlayer() {
		return player;
	}

	public void setPlayer(SpritePlayer player) {
		this.player = player;
	}

	public void looseLive() {
		try {
			
			player.looseLive();
			
			//SceneManagerSingleton.getInstance().setCurrentScene(AllScenes.MAIN);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void collectCoin() {
		try {
			
			UserInfoSingleton.getInstance().increaseMoneyInGame(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void looseLevel() {
		try {
			
			LooseHUD looseHud = new LooseHUD(this);
			HUDManagerSingleton.getInstance().addHUD(looseHud, false);
			
			mustUpdate = false;
			PhysicSingleton.getInstance().pause(true);
			
			GooglePlayGameSingleton.getInstance().validateAchievement(this);
			GooglePlayGameSingleton.getInstance().submitScore(scoreEntity.getScore());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void increaseScore(int quantity) {
		try {
			
			scoreEntity.increaseScore(quantity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EntityShowScore getScoreEntity() {
		return scoreEntity;
	}

	public void setScoreEntity(EntityShowScore scoreEntity) {
		this.scoreEntity = scoreEntity;
	}

	public void loadMainScene() {
		try {
			
			spriteList = new ArrayList<MySpriteGeneral>();
			spriteListDelete = new ArrayList<MySpriteGeneral>();
			spriteListToAdd = new ArrayList<MySpriteGeneral>();
			
			EntityPenguinBackground entityPenguinBackground = new EntityPenguinBackground(this);
			spriteList.add(entityPenguinBackground);
			
			attachSrpiteList();
			isLoadFinish = true;
			mustUpdate = true;
			setUpdateAnimated(true);
			PhysicSingleton.getInstance().pause(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
