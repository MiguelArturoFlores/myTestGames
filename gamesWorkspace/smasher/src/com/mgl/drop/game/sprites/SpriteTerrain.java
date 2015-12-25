package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteTerrain extends MySprite{

	
	
	public SpriteTerrain(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, LevelController level) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, vertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
		this.setIgnoreUpdate(true);
	}

	public SpriteTerrain(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX,pY,pNormalTextureRegion,pVertexBufferObjectManager);
	}
	
	@Override
	public void update(float dTime, LevelController level) {
		
		
	}

	@Override
	public SpriteType getSpriteType() {
		
		return SpriteType.TERRAIN;
	}	
	
}
