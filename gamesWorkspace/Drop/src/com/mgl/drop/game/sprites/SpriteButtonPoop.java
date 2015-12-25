package com.mgl.drop.game.sprites;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpriteButtonPoop extends MyAnimateSprite {

	private SpritePlayer player;

	public SpriteButtonPoop(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level, SpritePlayer player) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);

		this.player = player;
	}

	@Override
	public SpriteType getSpriteType() {

		return null;
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			player.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
					pTouchAreaLocalY);
			changeAnimateState(State.POOPING,false);
			anime(false);
			
			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			player.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
					pTouchAreaLocalY);
			changeAnimateState(State.POOPING,false);
			anime(false);

			break;
		}

		return true;
	}

	@Override
	public void initHashMap() {
		
		
		stateAnimate.put(State.POOPING, new MyAnimateProperty(0, 6,new long[] { 100, 100 , 100,100,100,100}));
		
	}

	@Override
	public void initAnimationParams() {

		fps = new long[] { 100, 100, 100 };
		imageNumber = 3;
		//currentState = State.WALKIN_UP;
		changeAnimateState(State.POOPING,false);
		
	}


}
