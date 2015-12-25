package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpriteFireDeath extends MyAnimateSprite {

	private float timeToDesapear = 3.5f;
	private float contTime = 0;
	private float alpha = 1;

	public SpriteFireDeath(float pX, float pY, ITextureRegion pTextureRegion,
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
			changeAnimateState(State.FIRE_VAMPIRE, false);
			anime(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initHashMap() {
		fps = new long[] { 83, 83, 83, 83, 83, 83, 83, 83, 83 };

		stateAnimate.put(State.FIRE_VAMPIRE, new MyAnimateProperty(0, 9, fps));

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			contTime += dTime;
			if (contTime < timeToDesapear)
				return;

			this.setAlpha(alpha);

			alpha = alpha - 0.01f;
			if (alpha <= 0) {
				this.detachSelf();
				level.removeEntity(this);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
