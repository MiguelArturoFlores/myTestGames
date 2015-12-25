package com.mgl.drop.game.scene;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;

import com.google.android.gms.internal.iz;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.LevelManager;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.texture.TextureSingleton;

public class GameScene extends Scene {

	private ObjectFactorySingleton objectFactorySingleton = ObjectFactorySingleton
			.getInstance();
	private TextureSingleton texture = TextureSingleton.getInstance();

	private int CAMERA_MAX_WIDTH = 2000;
	private int CAMERA_MAX_HEIGHT = 2000;

	private float time = 0;

	private LevelManager level;

	public GameScene() {
		super();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createScene(Level levelDB, ArrayList<Level> levelList,
			int gameType) {
		try {

			// load level from db or txt or some other place

			level = new LevelManager(this, levelDB, levelList, gameType);

			String backName = "";
			String fadeName = "";
			if ((Math.random() * 1000000) % 100 > 50) {
				backName = "background.png";
				fadeName = "fade.png";
			} else {
				backName = "background2.png";
				fadeName = "fade2.png";
			}

			Sprite background = new Sprite(0, 0,
					texture.getTextureByName(backName),
					texture.getVertexBufferObjectManager());
			background.setZIndex(ZIndexGame.BACKGROUND);
			this.attachChild(background);

			Sprite fade = new Sprite(0, 0,
					texture.getTextureByName(fadeName),
					texture.getVertexBufferObjectManager());
			fade.setZIndex(ZIndexGame.FADE);
			this.attachChild(fade);

			updateScene();

			// PublicityManagerSingleton.getInstance().showBanner();
			// PublicityManagerSingleton.getInstance().showIntersitial();

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
					if (time >= 0.02) {
						//Log.d("Seconds", pSecondsElapsed+"");
						level.update(pSecondsElapsed);
						time=time-0.02f;
					}
					// level.update(pSecondsElapsed, level);
					// game.update(pSecondsElapsed);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ObjectFactorySingleton getObjectFactorySingleton() {
		return objectFactorySingleton;
	}

	public void setObjectFactorySingleton(
			ObjectFactorySingleton objectFactorySingleton) {
		this.objectFactorySingleton = objectFactorySingleton;
	}

	public TextureSingleton getTexture() {
		return texture;
	}

	public void setTexture(TextureSingleton texture) {
		this.texture = texture;
	}

	public int getCAMERA_MAX_WIDTH() {
		return CAMERA_MAX_WIDTH;
	}

	public void setCAMERA_MAX_WIDTH(int cAMERA_MAX_WIDTH) {
		CAMERA_MAX_WIDTH = cAMERA_MAX_WIDTH;
	}

	public int getCAMERA_MAX_HEIGHT() {
		return CAMERA_MAX_HEIGHT;
	}

	public void setCAMERA_MAX_HEIGHT(int cAMERA_MAX_HEIGHT) {
		CAMERA_MAX_HEIGHT = cAMERA_MAX_HEIGHT;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public LevelManager getLevel() {
		return level;
	}

	public void setLevel(LevelManager level) {
		this.level = level;
	}

}
