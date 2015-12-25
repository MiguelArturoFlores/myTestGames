package com.mgl.drop.game.hud.sprites;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class SpriteRemoveHud extends MySprite {

	private HUD hud;

	public SpriteRemoveHud(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, HUD hud) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		this.hud = hud;
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
			
			HUDManagerSingleton.getInstance().removeAndReplaceHud();
			SceneManagerSingleton.getInstance().setContRetry(0);
			
			break;
			
		}
		return true;
	}
	
	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub

	}

}
