package com.mgl.drop.game.sprites.arunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpriteDecorativeShot extends MyAnimateSprite {

	public SpriteDecorativeShot(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void initAnimationParams() {
		try {
		
			changeAnimateState(State.NORMAL, true);
			anime(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initHashMap() {
		try {
			
			fps = new long[] { 83, 83, 83 , 83 };

			stateAnimate.put(State.NORMAL, new MyAnimateProperty(0,4,
					fps));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			if(!this.isAnimationRunning()){
				this.detachSelf();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {
		try {
			
			reanimateState(State.NORMAL, false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
