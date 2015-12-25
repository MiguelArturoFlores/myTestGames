package com.mgl.drop.game.sprites.button;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.model.Level;

public class SelectNextLevelButton extends Sprite {

	
	private Level level;
	private LevelController controller; 
	
	public SelectNextLevelButton(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, LevelController controller, Level level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			
			this.level=level;
			this.controller =controller;
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
			controller.reloadLevel(level.getId());
			controller.getLevelManager().setLevelDB(level);
			controller.getLevelManager().reloadLevel();

			
			
			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}


}
