package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpriteBigShit extends MyAnimateSprite {

	public SpriteBigShit(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);this.setIgnoreUpdate(true);
	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.DECORATIVE;
	}

	@Override
	public void initAnimationParams() {

		try {
			
			currentState = State.POOP_BEGIN;

			anime(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initHashMap() {
		try {

			
			stateAnimate.put(State.POOP_BEGIN, new MyAnimateProperty(0, 4,new long[] { 200, 200, 200, 200 }));
			changeAnimateState(State.POOP_BEGIN, true);
			anime(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

		} catch (Exception e) {

		}

	}

}
