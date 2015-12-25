package com.mgl.drop.game.sprites.azeoland;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneSelectLevel;

public class SpriteMapAnimation extends MyAnimateSprite{
	
	private SceneSelectLevel scene;

	public SpriteMapAnimation(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level, SceneSelectLevel scene) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		this.scene = scene;
	}

	@Override
	public void initAnimationParams() {

		try {

			changeAnimateState(State.NORMAL, false);
			anime(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83,83,83,83, 83, 83,83,83,83, 83, 83 };

			stateAnimate.put(State.NORMAL, new MyAnimateProperty(0, 13, fps));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			
			if(!isAnimationRunning()){
				//this.detachSelf();
				level.removeEntity(this);
				scene.createDefinitiveScene();

			}
			
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	
	
}
