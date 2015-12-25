package com.mgl.drop.game.scene;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import android.util.Log;

import com.mgl.base.ButtonListener;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.BeltListController;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.controller.SelectLevelController;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteMoneyFree;
import com.mgl.drop.game.sprites.SpriteSelectLevel;
import com.mgl.drop.game.sprites.arunner.SpritePlayerModel;
import com.mgl.drop.game.sprites.arunner.SpritePowerSelect;
import com.mgl.drop.game.sprites.arunner.SpriteSelectLevelNew;
import com.mgl.drop.game.sprites.button.ButtonGeneral;
import com.mgl.drop.game.sprites.button.ButtonHelp;
import com.mgl.drop.game.sprites.button.ButtonRate;
import com.mgl.drop.game.sprites.button.DisableMusicButton;
import com.mgl.drop.game.sprites.button.EnableMusicButton;
import com.mgl.drop.game.sprites.button.arunner.ButtonFacebook;
import com.mgl.drop.game.sprites.button.arunner.ButtonInstagram;
import com.mgl.drop.game.sprites.button.arunner.ButtonUs;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class SceneSelectLevelNew extends Scene {

	private ArrayList<Level> levelList;
	private ArrayList<MySpriteGeneral> levelSpriteList;
	private BeltListController beltListController;
	private SpritePlayerModel player;

	private float time = 0;
	private LevelController controller;
	private int levelPosition = 0;
	private TextureSingleton texture = TextureSingleton.getInstance();

	public SceneSelectLevelNew() {
		try {

			Sprite background = new Sprite(0, 0,
					texture.getTextureByName("background.jpg"),
					texture.getVertexBufferObjectManager());
			this.attachChild(background);

			time = 0;
			loadLevelList();
			loadLevelSpriteList();
			beltListController = new BeltListController(levelSpriteList,
					levelPosition, new Point(25, 150), this, ZIndexGame.FADE);

			// TextureSingleton texture = TextureSingleton.getInstance();
			player = PoolObjectSingleton.getInstance().getPlayerModelSelected();
			player.setPosition(520, 100);
			player.setZIndex(ZIndexGame.FADE);
			this.registerTouchArea(player);
			
			SpriteBackground spot = new SpriteBackground(0, 0,
					texture.getTextureByName("spot.png"),
					texture.getVertexBufferObjectManager());
			spot.setPosition(520 - player.getWidth() * 0.25f,
					100 - player.getHeight() * 0.2f);
			spot.setSize(player.getWidth() * 1.4f, player.getHeight() * 1.4f);
			spot.setZIndex(ZIndexGame.FADE);

			this.attachChild(spot);
			this.attachChild(player);

			Text editText = ObjectFactorySingleton.getInstance().createText("Edit", texture.getmFont1());
			editText.setColor(Color.WHITE);
			editText.setPosition(player.getWidth()/2 - editText.getWidth()/2 , player.getHeight()*0.72f );
			editText.setZIndex(ZIndexGame.FADE);
			player.attachChild(editText);
			
			SpriteBackground powerBar = new SpriteBackground(0, 0,
					texture.getTextureByName("powerBar.png"),
					texture.getVertexBufferObjectManager());
			powerBar.setPosition(
					MainDropActivity.CAMERA_WIDTH - powerBar.getWidth() + 20,
					MainDropActivity.CAMERA_HEIGHT - powerBar.getHeight() + 10);
			powerBar.setZIndex(ZIndexGame.FADE);
			//this.attachChild(powerBar);
			this.registerTouchArea(powerBar);

			int offset = 100;

			DisableMusicButton disable = new DisableMusicButton(0, 0,
					texture.getTextureByName("onBegin.png"),
					texture.getVertexBufferObjectManager(), this);
			disable.setPosition(10, 10);
			
			disable.setSize(60, 60);

			EnableMusicButton enable = new EnableMusicButton(0, 0,
					texture.getTextureByName("offBegin.png"),
					texture.getVertexBufferObjectManager(), this);
			enable.setPosition(10, 10);
			enable.setSize(60, 60);

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
					texture.getTextureByName("adn2Free.png"),
					texture.getVertexBufferObjectManager());
			moneyFree.setZIndex(ZIndexGame.FADE);
			moneyFree.setSize(65, 70);
			moneyFree.setPosition(enable.getX() + enable.getWidth() + 50, enable.getY() -5);

			
			
			this.attachChild(moneyFree);
			this.registerTouchArea(moneyFree);

			ButtonHelp help = new ButtonHelp(0, 0,
					texture.getTextureByName("help.png"),
					texture.getVertexBufferObjectManager());
			help.setZIndex(ZIndexGame.FADE);
			help.setPosition(moneyFree.getX() + moneyFree.getWidth() + 50, 30);
			//this.attachChild(help);
			//this.registerTouchArea(help);

			ButtonMoney money = new ButtonMoney(0, 5,
					texture.getTextureByName("buttonTexture.png"),
					texture.getVertexBufferObjectManager());
			money.setPosition(MainDropActivity.CAMERA_WIDTH - 200, enable.getY());
			money.setAlpha(0);
			money.setSize(60, 60);
			this.attachChild(money);
			this.registerTouchArea(money);

			controller = new LevelController(this);
			controller.loadLevelSelect();

			addStars();

			//addPowersToPowerBar(powerBar);

			addButtonRate();
			
			updateScene();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addButtonRate() {
		try {
			
			if(!UserInfoSingleton.getInstance().canRate()){
				return;
			}
			
			ButtonGeneral general = new ButtonGeneral(0, 0, texture.getTextureByName("rate.png"), texture.getVertexBufferObjectManager(),new ButtonListener() {
				
				@Override
				public void onActionUp(float x, float y) {
					try {
						
						SoundSingleton.getInstance().playButtonSound();
						UserInfoSingleton.getInstance().showRateme();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
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
			
			general.setPosition(5, MainDropActivity.CAMERA_HEIGHT - general.getHeight()- 5);
			
			this.attachChild(general);
			this.registerTouchArea(general);
			
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
			star11.setPosition(MainDropActivity.getCAMERA_WIDTH()-400,10);
			star11.setZIndex(ZIndexGame.FADE);
			this.attachChild(star11);
			
			Text textStar =  ObjectFactorySingleton.getInstance().createText(
					"x " + stars , texture.getmFont1());;
			textStar.setPosition(star11.getX()+star11.getWidth()+10, star11.getY()+10);
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

	private void loadLevelSpriteList() {
		try {
			levelSpriteList = new ArrayList<MySpriteGeneral>();
			for (Level level : levelList) {

				int val = Integer.valueOf(level.getName());
				SpriteSelectLevelNew selectSprite = new SpriteSelectLevelNew(0,
						0, TextureSingleton.getInstance().getTextureByName(
								"level" + val + ".png"), TextureSingleton
								.getInstance().getVertexBufferObjectManager(),
						level, levelList);
				selectSprite.setSize(150, 150);
				selectSprite.init();
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
