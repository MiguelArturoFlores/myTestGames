package com.mgl.drop.game.scene;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.google.example.games.basegameutils.GooglePlayButtonBar;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.BeltListController;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteMoneyFree;
import com.mgl.drop.game.sprites.arunner.SpritePlayerModel;
import com.mgl.drop.game.sprites.arunner.SpriteTitle;
import com.mgl.drop.game.sprites.button.DisableMusicButton;
import com.mgl.drop.game.sprites.button.EnableMusicButton;
import com.mgl.drop.game.sprites.button.arunner.ButtonFacebook;
import com.mgl.drop.game.sprites.button.arunner.ButtonInstagram;
import com.mgl.drop.game.sprites.button.arunner.ButtonTwitter;
import com.mgl.drop.game.sprites.button.arunner.ButtonUs;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class ScenePlayerSelect extends Scene {

	private BeltListController beltListController;
	private SpritePlayerModel player;

	private float time = 0;
	private LevelController controller;
	private int levelPosition = 1;

	private TextureSingleton texture = TextureSingleton.getInstance();
	
	public ScenePlayerSelect() {
		try {


			Sprite background = new Sprite(0, 0,
					texture.getTextureByName(MainDropActivity.BACKGROUND_TEXTURE_NAME),
					texture.getVertexBufferObjectManager());
			this.attachChild(background);

			background.setColor(15/255f,168/255f,248/255f);
			
			time = 0;

			beltListController = new BeltListController(PoolObjectSingleton.getInstance().getPlayerList(),
					levelPosition, new Point(80, 150), this, ZIndexGame.FADE);


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
			tw.setSize(60, 60);
			tw.setPosition(fb.getWidth() + fb.getX() + 10,
					MainDropActivity.CAMERA_HEIGHT - fb.getHeight() - 10);
			tw.setZIndex(ZIndexGame.FADE);
			//this.attachChild(tw);
			//this.registerTouchArea(tw);

			ButtonInstagram ig = new ButtonInstagram(0, 0,
					texture.getTextureByName("igrm.png"),
					texture.getVertexBufferObjectManager());
			ig.setSize(60, 60);
			ig.setPosition(tw.getX() + tw.getWidth() + 10,
					MainDropActivity.CAMERA_HEIGHT - fb.getHeight() - 10);
			ig.setZIndex(ZIndexGame.FADE);
			//this.attachChild(ig);
			//this.registerTouchArea(ig);

			ButtonUs us = new ButtonUs(0, 0,
					texture.getTextureByName("blog.png"),
					texture.getVertexBufferObjectManager());
			us.setSize(60, 60);
			us.setPosition(ig.getX() + ig.getWidth() + 10,
					MainDropActivity.CAMERA_HEIGHT - fb.getHeight() - 10);
			us.setZIndex(ZIndexGame.FADE);
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
			enable.setPosition(fb.getX(), 30);
			enable.setSize(70, 80);
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
			//moneyFree.setPosition(enable.getX() + enable.getWidth() + 50, 30);
			moneyFree.setPosition(MainDropActivity.CAMERA_WIDTH-moneyFree.getWidth(), MainDropActivity.CAMERA_HEIGHT-moneyFree.getHeight());

			this.attachChild(moneyFree);
			this.registerTouchArea(moneyFree);

			ButtonMoney money = new ButtonMoney(0, 5,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			money.setPosition(MainDropActivity.CAMERA_WIDTH - 150, 25);
			money.setAlpha(0f);
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

}
