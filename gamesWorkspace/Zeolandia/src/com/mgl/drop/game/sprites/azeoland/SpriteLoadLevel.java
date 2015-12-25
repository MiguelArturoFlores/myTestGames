package com.mgl.drop.game.sprites.azeoland;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.ButtonListener;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneSelectLevel;
import com.mgl.drop.game.sprites.SpriteBackground;

public class SpriteLoadLevel extends MySprite {

	private float contTime = 0f;
	private float timeToAction = 0.5f;
	private boolean isPaused = false;

	private SceneSelectLevel scene;
	
	public SpriteLoadLevel(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController controller) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,
				controller);
		
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setAutoUpdate() {
		try {

			try {

				this.registerUpdateHandler(new IUpdateHandler() {
					@Override
					public void reset() {
					}

					@Override
					public void onUpdate(float pSecondsElapsed) {
						// HERE IS THE GAME LOOP
						time += pSecondsElapsed;

						try {

							contTime += pSecondsElapsed;

							if (contTime > timeToAction) {
								getSprite().detachSelf();
								scene.play();

							}

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				});

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SpriteLoadLevel getSprite() {
		return this;
	}

	public float getContTime() {
		return contTime;
	}

	public void setContTime(float contTime) {
		this.contTime = contTime;
	}

	public float getTimeToAction() {
		return timeToAction;
	}

	public void setTimeToAction(float timeToAction) {
		this.timeToAction = timeToAction;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public void setScene(SceneSelectLevel scene) {
		this.scene = scene;
	}

	public void init(String iconName) {
		try {
			
			iconName = "placeLogo.png";
			
			this.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			this.setAlpha(0.95f);
			HUDManagerSingleton.getInstance().getTop().attachChild(this);
			HUDManagerSingleton.getInstance().getTop().registerTouchArea(this);
			
			
			SpriteBackground back = new SpriteBackground(0, 0, texture.getTextureByName(iconName), texture.getVertexBufferObjectManager());
			back.setPosition(MainDropActivity.CAMERA_WIDTH/2 - back.getWidth()/2, MainDropActivity.CAMERA_HEIGHT/2 - back.getHeight()/2 );
			this.attachChild(back);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
