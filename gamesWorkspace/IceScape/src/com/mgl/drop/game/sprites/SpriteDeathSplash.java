package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteDeathSplash extends MySprite {

	private float timeToDesapear = 1.5f;
	private float contTime = 0;
	private float alpha = 1;
	private boolean isExpanding;

	public SpriteDeathSplash(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		isExpanding = true;
		this.setIgnoreUpdate(true);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {

			if (isExpanding) {

				this.setHeight(this.getHeight() + 6);
				this.setY(this.getY() - 6);
				if(this.getHeight()>200){
					isExpanding = false;
				}
				
				return;
			}

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
