package com.mgl.drop.game.sprites.ayoutuberunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpriteLikeCoinDead extends MyAnimateSprite {

	public SpriteLikeCoinDead(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController controller) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,
				controller);
		
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

			changeAnimateState(State.WALKIN_RIGHT, false);
			anime(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83, 83, 83, 83};

			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0, 6,
					fps));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {

		try {

			if (!this.isAnimationRunning()) {
				this.detachSelf();
				level.removeEntity(this);
				//level.increaseLikeCoin();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
