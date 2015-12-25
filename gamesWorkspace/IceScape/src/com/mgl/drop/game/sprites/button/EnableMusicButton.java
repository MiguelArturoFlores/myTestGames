package com.mgl.drop.game.sprites.button;

import org.andengine.entity.scene.Scene;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.drop.factory.SoundSingleton;

public class EnableMusicButton  extends Sprite {

	private Scene pauseH;
	private DisableMusicButton button;

	public EnableMusicButton(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Scene scene) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			this.pauseH = scene;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			//SoundSingleton.getInstance().setHasSound(true);
			
			SoundSingleton.getInstance().setSilenceMusic(false);
			
			pauseH.detachChild(this);
			pauseH.unregisterTouchArea(this);
			this.setParent(null);
			
			button.setParent(null);
			pauseH.attachChild(button);
			pauseH.registerTouchArea(button);
			

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

	
	public DisableMusicButton getButton() {
		return button;
	}

	public void setButton(DisableMusicButton button) {
		this.button = button;
	}

} 

