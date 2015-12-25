package com.mgl.drop.game.sprites.arunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteTurboBack extends MySprite {

	private float contTime = 0;
	private float timeToDesapear = 0.5f;

	public SpriteTurboBack(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);

	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {

			if (!mustUpdate) {
				return;
			}

			contTime = contTime + dTime;
			
			float alpha = 1 * contTime/timeToDesapear;
			alpha = 1 - alpha;
			
			if(alpha<0){
				this.detachSelf();
				level.removeEntity(this);
			}
			
			this.setAlpha(alpha);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
