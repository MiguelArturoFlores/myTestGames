package com.mgl.drop.game.sprites.button;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;

public class SelectLevelButton extends Sprite {

	private Scene scene;

	public SelectLevelButton(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Scene scene) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			this.scene = scene;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:


			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			SoundSingleton.getInstance().playButtonSound();
			SceneManagerSingleton manager = SceneManagerSingleton.getInstance();
			manager.setCurrentScene(AllScenes.SELECT_LEVEL);
			
			break;
		}
		return true;
	}

}
