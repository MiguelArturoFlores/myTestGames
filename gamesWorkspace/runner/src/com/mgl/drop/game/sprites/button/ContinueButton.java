package com.mgl.drop.game.sprites.button;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;

public class ContinueButton extends Sprite {

	private Scene scene;
	private Sprite background;

	public ContinueButton(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Scene scene, Sprite spriteBackground) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			this.scene = scene;
			this.background = spriteBackground;
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
			
			scene.setIgnoreUpdate(false);
			
			//Camera camera = SceneManagerSingleton.getInstance().getCamera();
			HUDManagerSingleton.getInstance().removeAndReplaceHud();
			
			//hud.detachChild(background);
			//hud.unregisterTouchArea(this);
			//hud.detachChild(this);
			
			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

}
