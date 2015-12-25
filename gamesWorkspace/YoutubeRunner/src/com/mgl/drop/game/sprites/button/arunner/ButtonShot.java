package com.mgl.drop.game.sprites.button.arunner;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.arunner.SpriteEnemy;
import com.mgl.drop.game.sprites.arunner.SpritePlayer;
import com.mgl.drop.util.Point;

public class ButtonShot extends MySprite {

	private boolean shoting = false;
	private float timeToReload = 0.1f;
	private float contTime = timeToReload;
	private SpriteBackground reload;

	public ButtonShot(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager, level);
		try {

			shoting = false;
			reload = new SpriteBackground(0, 0,
					texture.getTextureByName("blueBackground.jpg"),
					texture.getVertexBufferObjectManager());
			contTime = timeToReload;
			reload.setPosition(0, -5);
			reload.setHeight(3);
			reload.setWidth(this.getWidth());
			//this.attachChild(reload);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {

			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:
				if (contTime >= timeToReload) {
					shoting = true;
					contTime = 0;
				}

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {

		try {

			contTime = contTime + dTime;
			if (contTime < timeToReload) {
				this.setAlpha(0.15f);
				reload.setWidth(this.getWidth() * contTime / timeToReload);
			} else {
				this.setAlpha(1f);
				reload.setWidth(this.getWidth());
			}

			if (shoting) {

				SpritePlayer player = level.getPlayer();

				level.getPlayer().shotToEnemy(level);
				shoting = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
