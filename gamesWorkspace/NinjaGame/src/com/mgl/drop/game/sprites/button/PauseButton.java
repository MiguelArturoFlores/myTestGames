package com.mgl.drop.game.sprites.button;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.LevelManager;
import com.mgl.drop.game.hud.PauseHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.texture.TextureSingleton;

public class PauseButton extends Sprite {

	private Scene scene;
	private TextureSingleton texture = TextureSingleton.getInstance();
	private LevelManager levelManager;

	public PauseButton(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Scene scene,LevelManager levelManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			this.scene = scene;
			this.levelManager = levelManager;
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
			//SceneManagerSingleton.getInstance().resetZoomInstant();
			scene.setIgnoreUpdate(true);
			SoundSingleton.getInstance().stopCurrentMusic();
			showPauseBar();

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

	private void showPauseBar() {
		try {
			
			PauseHUD hud = new PauseHUD(scene, levelManager);
			//camera.setHUD(hud);
			HUDManagerSingleton.getInstance().addHUD(hud,false);
			Log.d("Ägruegue el hud ", "Hud del pause");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
