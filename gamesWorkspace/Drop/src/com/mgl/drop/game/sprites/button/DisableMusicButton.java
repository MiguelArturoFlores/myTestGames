package com.mgl.drop.game.sprites.button;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.hud.PauseHUD;

public class DisableMusicButton  extends Sprite {

	private Scene scene;
	private PauseHUD pauseH;
	private EnableMusicButton button;
	

	public DisableMusicButton(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Scene scene, PauseHUD pauseHUD) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			this.scene = scene;
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

			SoundSingleton.getInstance().setHasSound(false);
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

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public PauseHUD getPauseH() {
		return pauseH;
	}

	public void setPauseH(PauseHUD pauseH) {
		this.pauseH = pauseH;
	}

	public EnableMusicButton getButton() {
		return button;
	}

	public void setButton(EnableMusicButton button) {
		this.button = button;
	}
	
	

}