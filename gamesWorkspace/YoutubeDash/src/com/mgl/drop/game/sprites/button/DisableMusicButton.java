package com.mgl.drop.game.sprites.button;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.drop.factory.SoundSingleton;

public class DisableMusicButton  extends Sprite {

	private Scene pauseH;
	private EnableMusicButton button;
	

	public DisableMusicButton(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,  Scene pauseHUD) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			this.pauseH = pauseHUD;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			//SoundSingleton.getInstance().setHasSound(false);
			SoundSingleton.getInstance().setSilenceMusic(true);
			
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

	
	public EnableMusicButton getButton() {
		return button;
	}

	public void setButton(EnableMusicButton button) {
		this.button = button;
	}
	
	

}