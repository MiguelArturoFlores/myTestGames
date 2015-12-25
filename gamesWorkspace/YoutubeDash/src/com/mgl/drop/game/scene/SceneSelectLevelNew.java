package com.mgl.drop.game.scene;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.google.example.games.basegameutils.GooglePlayButtonBar;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.controller.LevelListController;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteMoneyFree;
import com.mgl.drop.game.sprites.arunner.SpritePlayerModel;
import com.mgl.drop.game.sprites.arunner.SpritePowerSelect;
import com.mgl.drop.game.sprites.arunner.SpriteSelectLevelNew;
import com.mgl.drop.game.sprites.arunner.SpriteTitle;
import com.mgl.drop.game.sprites.button.DisableMusicButton;
import com.mgl.drop.game.sprites.button.EnableMusicButton;
import com.mgl.drop.game.sprites.button.arunner.ButtonFacebook;
import com.mgl.drop.game.sprites.button.arunner.ButtonInstagram;
import com.mgl.drop.game.sprites.button.arunner.ButtonTwitter;
import com.mgl.drop.game.sprites.button.arunner.ButtonUs;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class SceneSelectLevelNew extends Scene {

	private LevelListController beltListController;
	private SpritePlayerModel player;

	private float time = 0;
	private LevelController controller;
	private int levelPosition = 1;
	private ArrayList<Level> levelList;
	private ArrayList<MySpriteGeneral> levelSpriteList;
	private Sprite background;

	private TextureSingleton texture = TextureSingleton.getInstance();
	
	public SceneSelectLevelNew() {
		try {

			
			background = new Sprite(0, 0,
					texture.getTextureByName(MainDropActivity.BACKGROUND_TEXTURE_NAME),
					texture.getVertexBufferObjectManager());
			this.attachChild(background);

			this.setColor(130/255f,17/255f,217/255f);
			
			time = 0;
			loadLevelList();
			loadLevelSpriteList();
			levelPosition = 0;
			beltListController = new LevelListController(levelSpriteList,
					levelPosition, new Point(80, 100), this, ZIndexGame.FADE);


			int offset = 20;
			ButtonFacebook fb = new ButtonFacebook(0, 0,
					texture.getTextureByName("fb.png"),
					texture.getVertexBufferObjectManager());
			fb.setSize(60, 60);
			fb.setPosition(offset,
					MainDropActivity.CAMERA_HEIGHT - fb.getHeight() - 10);
			fb.setZIndex(ZIndexGame.FADE);
			//this.attachChild(fb);
			//this.registerTouchArea(fb);

			ButtonTwitter tw = new ButtonTwitter(0, 0,
					texture.getTextureByName("twttr.png"),
					texture.getVertexBufferObjectManager());
			tw.setPosition(fb.getWidth() + fb.getX() + 10,
					MainDropActivity.CAMERA_HEIGHT - fb.getHeight() - 10);
			tw.setZIndex(ZIndexGame.FADE);
			tw.setSize(60, 60);
			//this.attachChild(tw);
			//this.registerTouchArea(tw);

			ButtonInstagram ig = new ButtonInstagram(0, 0,
					texture.getTextureByName("igrm.png"),
					texture.getVertexBufferObjectManager());
			ig.setPosition(tw.getX() + tw.getWidth() + 10,
					MainDropActivity.CAMERA_HEIGHT - fb.getHeight() - 10);
			ig.setZIndex(ZIndexGame.FADE);
			ig.setSize(60, 60);
			//this.attachChild(ig);
			//this.registerTouchArea(ig);

			ButtonUs us = new ButtonUs(0, 0,
					texture.getTextureByName("blog.png"),
					texture.getVertexBufferObjectManager());
			us.setPosition(ig.getX() + ig.getWidth() + 10,
					MainDropActivity.CAMERA_HEIGHT - fb.getHeight() - 10);
			us.setZIndex(ZIndexGame.FADE);
			us.setSize(60, 60);
			//this.attachChild(us);
			//this.registerTouchArea(us);

			SpriteTitle title = new SpriteTitle(0, 0, texture.getTextureByName("title.png"),texture.getVertexBufferObjectManager(),null);
			title.setSize(150, 100);
			title.setPosition(5,MainDropActivity.CAMERA_HEIGHT - title.getHeight());
			title.setZIndex(ZIndexGame.FADE);
			this.attachChild(title);
			
			
			DisableMusicButton disable = new DisableMusicButton(0, 0,
					texture.getTextureByName("onBegin.png"),
					texture.getVertexBufferObjectManager(), this);
			disable.setSize(70, 80);
			disable.setPosition(fb.getX(), 30);
			
			
			EnableMusicButton enable = new EnableMusicButton(0, 0,
					texture.getTextureByName("offBegin.png"),
					texture.getVertexBufferObjectManager(), this);
			enable.setSize(70, 80);
			enable.setPosition(fb.getX(), 30);
			enable.setButton(disable);
			disable.setButton(enable);

			if (SoundSingleton.getInstance().isHasSound()) {
				this.attachChild(disable);
				this.registerTouchArea(disable);
			} else {
				this.attachChild(enable);
				this.registerTouchArea(enable);
			}

			enable.setZIndex(ZIndexGame.FADE);
			disable.setZIndex(ZIndexGame.FADE);

			SpriteMoneyFree moneyFree = new SpriteMoneyFree(0, 0,
					texture.getTextureByName("moreMoney.png"),
					texture.getVertexBufferObjectManager());
			moneyFree.setZIndex(ZIndexGame.FADE);
			moneyFree.setAutoUpdate();
			moneyFree.setPosition(MainDropActivity.CAMERA_WIDTH-moneyFree.getWidth(), MainDropActivity.CAMERA_HEIGHT-moneyFree.getHeight());

			this.attachChild(moneyFree);
			this.registerTouchArea(moneyFree);

			ButtonMoney money = new ButtonMoney(0, 5,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			money.setAlpha(0f);
			money.setPosition(MainDropActivity.CAMERA_WIDTH - 150, 25);

			this.attachChild(money);
			this.registerTouchArea(money);

			initGoogleButtons();
			
			//controller = new LevelController(this);
			//controller.loadLevelSelect();

			//addStars();

			//updateScene();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initGoogleButtons() {
		try {
			
			GooglePlayButtonBar buttonBar = new GooglePlayButtonBar(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager(), this);
			buttonBar.setPosition(MainDropActivity.CAMERA_WIDTH/2 - buttonBar.getWidth()/2, 15);
			buttonBar.setZIndex(ZIndexGame.FADE);
			this.attachChild(buttonBar);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadLevelSpriteList() {
		try {
			levelSpriteList = new ArrayList<MySpriteGeneral>();
			for (Level level : levelList) {

				//int val = Integer.valueOf(level.getName());
				SpriteSelectLevelNew selectSprite = new SpriteSelectLevelNew(0,
						0, TextureSingleton.getInstance().getTextureByName(
								"generalWindow.png"), TextureSingleton
								.getInstance().getVertexBufferObjectManager(),
						level, levelList);
				selectSprite.setSize(500, 300);
				selectSprite.init();
				//selectSprite.setAlpha(0.5f);
				levelSpriteList.add(selectSprite);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void loadLevelList() {
		try {

			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			levelList = dao.loadLevelList();
			int i = 0;
			for (Level l : levelList) {
				if (l.getAvalible()) {
					levelPosition = i;
				}
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void addStars() {
		try {

			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
		
			int stars = dao.loadStars();
			
			TextureSingleton texture = TextureSingleton.getInstance();
			
			Sprite star11 = new Sprite(0, 0,
					texture.getTextureByName("fillStar.png"),
					texture.getVertexBufferObjectManager());
			star11.setSize(50, 50);
			star11.setPosition(MainDropActivity.getCAMERA_WIDTH()-400,30);
			star11.setZIndex(ZIndexGame.FADE);
			this.attachChild(star11);
			
			Text textStar =  ObjectFactorySingleton.getInstance().createText(
					"x " + stars , texture.getmFont1());;
			textStar.setPosition(star11.getX()+star11.getWidth(), star11.getY()+10);
			textStar.setZIndex(ZIndexGame.FADE);
			this.attachChild(textStar);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addPowersToPowerBar(SpriteBackground powerBar) {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			int offset = 60;

			SpritePowerSelect powerShot = new SpritePowerSelect(0, 0, 60, 60,
					texture.getTextureByName("shotIcon.png"),
					texture.getVertexBufferObjectManager(), null,
					UserInfoSingleton.getInstance().getPowerA());
			powerShot
					.setPosition(powerBar.getX() + offset, powerBar.getY()
							+ powerBar.getHeight() / 2 - powerShot.getHeight()
							/ 2 + 20);
			powerShot.setZIndex(ZIndexGame.FADE);
			this.attachChild(powerShot);

			SpritePowerSelect powerAccelerate = new SpritePowerSelect(0, 0, 60,
					60, texture.getTextureByName("accelerateIcon.png"),
					texture.getVertexBufferObjectManager(), null,
					UserInfoSingleton.getInstance().getPowerB());
			powerAccelerate.setPosition(
					powerShot.getX() + 20 + powerShot.getWidth(),
					powerBar.getY() + powerBar.getHeight() / 2
							- powerAccelerate.getHeight() / 2 + 20);
			powerAccelerate.setZIndex(ZIndexGame.FADE);
			this.attachChild(powerAccelerate);

			SpritePowerSelect powerIce = new SpritePowerSelect(0, 0, 60, 60,
					texture.getTextureByName("iceIcon.png"),
					texture.getVertexBufferObjectManager(), null,
					UserInfoSingleton.getInstance().getPowerC());
			powerIce.setPosition(
					powerAccelerate.getX() + 20 + powerAccelerate.getWidth(),
					powerBar.getY() + powerBar.getHeight() / 2
							- powerIce.getHeight() / 2 + 20);
			powerIce.setZIndex(ZIndexGame.FADE);
			this.attachChild(powerIce);

		} catch (Exception e) {
			e.printStackTrace();
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
					if (time >= 0.03f) {
						controller.update(time);
						time = 0;
					}
					// level.update(pSecondsElapsed, level);
					// game.update(pSecondsElapsed);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Scene getScene() {
		return this;
	}

	public LevelListController getBeltListController() {
		return beltListController;
	}

	public void setBeltListController(LevelListController beltListController) {
		this.beltListController = beltListController;
	}

	public SpritePlayerModel getPlayer() {
		return player;
	}

	public void setPlayer(SpritePlayerModel player) {
		this.player = player;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public LevelController getController() {
		return controller;
	}

	public void setController(LevelController controller) {
		this.controller = controller;
	}

	public int getLevelPosition() {
		return levelPosition;
	}

	public void setLevelPosition(int levelPosition) {
		this.levelPosition = levelPosition;
	}

	public ArrayList<Level> getLevelList() {
		return levelList;
	}

	public void setLevelList(ArrayList<Level> levelList) {
		this.levelList = levelList;
	}

	public ArrayList<MySpriteGeneral> getLevelSpriteList() {
		return levelSpriteList;
	}

	public void setLevelSpriteList(ArrayList<MySpriteGeneral> levelSpriteList) {
		this.levelSpriteList = levelSpriteList;
	}

	public Sprite getBackgroundProper() {
		return background;
	}

	public void setBackground(Sprite background) {
		this.background = background;
	}

	public TextureSingleton getTexture() {
		return texture;
	}

	public void setTexture(TextureSingleton texture) {
		this.texture = texture;
	}

	
	
}
