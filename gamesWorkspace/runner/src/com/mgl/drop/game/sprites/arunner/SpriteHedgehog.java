package com.mgl.drop.game.sprites.arunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteHedgehog extends MySprite{

	
	
	public SpriteHedgehog(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		this.setIgnoreUpdate(true);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub0
		return SpriteType.HEDGEHOG;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}