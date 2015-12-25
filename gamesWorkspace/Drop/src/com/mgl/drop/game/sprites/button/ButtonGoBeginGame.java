package com.mgl.drop.game.sprites.button;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.drop.game.LevelManager;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class ButtonGoBeginGame extends Sprite {

	private Scene scene;
	private LevelManager level;
	
	public ButtonGoBeginGame(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
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

			SceneManagerSingleton.getInstance().goMainScene();
			

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

}
