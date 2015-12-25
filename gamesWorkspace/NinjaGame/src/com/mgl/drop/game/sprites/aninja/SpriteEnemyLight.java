package com.mgl.drop.game.sprites.aninja;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpriteEnemyLight extends SpriteEnemy{

	public SpriteEnemyLight(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83 };

			stateAnimate.put(State.WAKE_UP,
					new MyAnimateProperty(48, 4, new long[] { 83, 83,83, 83}));
			
			stateAnimate.put(State.WALKIN_DOWN,
					new MyAnimateProperty(12, 12, fps));
			stateAnimate.put(State.WALKIN_UP, new MyAnimateProperty(0, 12, fps));
			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(24, 12,
					fps));
			stateAnimate.put(State.WALKIN_LEFT,
					new MyAnimateProperty(24, 12, fps));

			stateAnimate.put(State.DIYING, new MyAnimateProperty(40, 8, new long[] { 83, 83, 83, 83, 83, 83, 83, 83}));
			
			
			stateAnimate.put(State.WAITING_DOWN,
					new MyAnimateProperty(36, 2, new long[] { 83, 83}));
			stateAnimate.put(State.WAITING_UP, new MyAnimateProperty(38, 2, new long[] { 83, 83}));
			stateAnimate.put(State.WAITING_RIGHT, new MyAnimateProperty(58, 2,
					new long[] { 83, 83}));
			stateAnimate.put(State.WAITING_LEFT,
					new MyAnimateProperty(56, 2, new long[] { 83, 83}));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}