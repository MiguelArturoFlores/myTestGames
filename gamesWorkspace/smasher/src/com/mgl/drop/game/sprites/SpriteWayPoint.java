package com.mgl.drop.game.sprites;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteWayPoint extends MySprite {
	
	private boolean statusA = false;

	public SpriteWayPoint(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Scene scene) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {

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

			break;
		}
		return true;
	}

	@Override
	public void update(float dTime, LevelController level) {
		
		currentTime = currentTime+dTime;
		
		if(currentTime<0.5){
			return;
		}
		currentTime = 0;
		if(statusA){
			this.setSize(this.getWidth()+1, this.getHeight()+1);
		}else{
			this.setSize(this.getWidth()-1, this.getHeight()-1);
		}
		statusA = !statusA;
		
	}
	

	@Override
	public SpriteType getSpriteType() {
		
		return SpriteType.DECORATIVE;
	}
	
}
