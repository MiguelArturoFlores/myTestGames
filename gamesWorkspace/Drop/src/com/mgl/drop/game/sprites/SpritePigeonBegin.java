package com.mgl.drop.game.sprites;

import org.andengine.audio.sound.Sound;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpritePigeonBegin extends MyAnimateSprite{

	
	
	public SpritePigeonBegin(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		
		
		status = StatusType.NORMAL;
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void initHashMap() {
		fps = new long[] { 200, 200, 300,200,300,200,300,200,300,100 };
		
		stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0, 10,fps));
	
	
	
	
//		stateAnimate.put(State.WALKIN_DOWN, 0);
//		stateAnimate.put(State.WALKIN_LEFT, 3);
//		stateAnimate.put(State.WALKIN_RIGHT, 6);
//		stateAnimate.put(State.WALKIN_UP, 9);

	}

	@Override
	public void initAnimationParams() {
		
		changeAnimateState(State.WALKIN_RIGHT,true);
		anime(true);
	}

	@Override
	public SpriteType getSpriteType() {
		
		return SpriteType.OBJETIVE;
	}

	
}
