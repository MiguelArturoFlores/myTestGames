package com.mgl.drop.game.sprites.button;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.interfaz.SpritePowerBar;

public class StartButton extends MySprite {

	private Scene scene;

	private boolean resize = true;

	private float maxTime = 0.4f;
	private float contTime = 0;

	public StartButton(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			LevelController level, Scene scene) {
		super(pX, pY, pWidth, pHeight, pTextureRegion,
				vertexBufferObjectManager, level);
		this.scene = scene;
		// this.setAlpha(0.5f);

	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			contTime = contTime + dTime;

			if (contTime > maxTime) {
				contTime = 0;
				if (resize) {
					this.setSize(this.getWidth() + 5, this.getHeight() + 5);
				} else {
					this.setSize(this.getWidth() - 5, this.getHeight() - 5);

				}
				resize = !resize;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			SoundSingleton.getInstance().playButtonSound();

			level.setUpdateAnimated(true);

			HUDManagerSingleton.getInstance().getTop().unregisterTouchArea(this);
			HUDManagerSingleton.getInstance().removeAndReplaceHud();
			
			this.detachSelf();
			
			level.setMustUpdate(true);
			
			SpritePowerBar powerBar = new SpritePowerBar(80, 60,
					texture.getTextureByName("powerGameBarH.png"),
					texture.getVertexBufferObjectManager(),scene,level,false,true);
			powerBar.setSize(MainDropActivity.CAMERA_WIDTH, 150);
			//powerBar.setPosition(MainDropActivity.CAMERA_WIDTH - powerBar.getWidth(), 60);
			powerBar.setPosition(MainDropActivity.CAMERA_WIDTH/2 -powerBar.getWidth()/2, MainDropActivity.CAMERA_HEIGHT - powerBar.getHeight() -3);
			powerBar.setAlpha(0.8f);
			HUDManagerSingleton.getInstance().getTop().attachChild(powerBar);

			for (MySpriteGeneral sprite : level.getSpriteList()) {

				if (sprite.getSpriteType().equals(SpriteType.PLAYER)) {

					Log.d("ABC", " CAMBIO EL LISTENER ");

					scene.setOnSceneTouchListener(null);

					Camera camera = SceneManagerSingleton.getInstance()
							.getCamera();
					camera.setChaseEntity(null);

					sprite.setStatus(StatusType.PLAYING);
					camera.getHUD().detachChild(this);

					SceneManagerSingleton.getInstance().resetZoom();
					level.detachZoomButton();

					// break;
				}
			}

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

}
