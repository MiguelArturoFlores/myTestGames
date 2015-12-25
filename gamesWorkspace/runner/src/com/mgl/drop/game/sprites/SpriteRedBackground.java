package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteRedBackground extends MySprite{
	
	private float duration = 1.5f;
	private float contTime = 0;

	public SpriteRedBackground(float pX, float pY,
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
			
			contTime +=dTime;
			
			this.setAlpha(this.getAlpha()-0.01f);
			
			if(contTime>duration){
				this.detachSelf();
				level.removeEntity(this);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	}

	
	
}
