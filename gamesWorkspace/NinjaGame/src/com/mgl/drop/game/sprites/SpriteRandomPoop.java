package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpriteRandomPoop extends MyAnimateSprite{

	
	public SpriteRandomPoop(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		try {
			status = StatusType.FALLING;
			
			mustUpdate = false;
			changeAnimateState(State.FALLING_DOWN, true);

			this.setIgnoreUpdate(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public SpriteType getSpriteType() {
		// 
		return SpriteType.DECORATIVE;
	}

	@Override
	public void initAnimationParams() {
		
		
	}

	@Override
	public void initHashMap() {
		try {
			fps = new long[] { 100, 100 };
			stateAnimate.put(State.FALLING_DOWN, new MyAnimateProperty(0, 4,
					new long[] { 100, 100,100,100 }));
			stateAnimate.put(State.POOP_HIT_FLOOR, new MyAnimateProperty(4, 2,
					fps));
			changeAnimateState(State.FALLING_DOWN, true);
			anime(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
			try {
				
				
				
				this.setY(this.getY()+3);
				if(this.getY()>MainDropActivity.getCAMERA_HEIGHT()){
					this.setPosition(0,-200);
					changeAnimateState(State.FALLING_DOWN, true);
					mustUpdate = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	

}
