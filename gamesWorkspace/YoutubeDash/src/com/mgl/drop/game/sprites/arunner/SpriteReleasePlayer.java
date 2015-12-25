package com.mgl.drop.game.sprites.arunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteReleasePlayer extends MySprite {

	private static final float MIN_DISTANCE_TO_UPDATE = 1000;
	private boolean isFinish = false;
	private int maxDistance = 450;
	private boolean hasPlayer = false;
	private float contBurble = 0;
	private float contBurbleBegin = 0;
	private boolean updateWithCheckpoint  = false;
	private boolean generateBurbles = true;

	public SpriteReleasePlayer(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		hasPlayer = false;
		updateWithCheckpoint  = false;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		if (isFinish) {
			return SpriteType.FINISH_POINT;
		}
		return SpriteType.BEGIN_POINT;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			if (!this.mustUpdate) {
				return;
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

}
