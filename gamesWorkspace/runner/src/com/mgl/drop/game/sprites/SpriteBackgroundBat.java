package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpriteBackgroundBat extends MyAnimateSprite {

	private float speed = 150;
	
	public SpriteBackgroundBat(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initAnimationParams() {
		try {
			fps = new long[] { 100, 100, 100 };
			imageNumber = 3;
			// currentState = State.WALKIN_UP;
			changeAnimateState(State.WALKIN_DOWN, true);
			anime(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		fps = new long[] { 83, 83, 83, 83 };
		stateAnimate.put(State.WALKIN_DOWN, new MyAnimateProperty(0, 4, fps));

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			
		this.setY(this.getY() + dTime*speed);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}