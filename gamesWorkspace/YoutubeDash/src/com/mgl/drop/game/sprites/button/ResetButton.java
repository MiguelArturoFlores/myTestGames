package com.mgl.drop.game.sprites.button;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.LevelManager;

public class ResetButton extends Sprite {

	private Scene scene;
	private LevelManager level;

	public ResetButton(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Scene scene,
			LevelManager level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {

			this.scene = scene;
			this.level = level;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			SoundSingleton.getInstance().playSound("buttonPress.mp3");
			PublicityManagerSingleton.getInstance().showIntersitial();
			level.reloadLevel();

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

}
