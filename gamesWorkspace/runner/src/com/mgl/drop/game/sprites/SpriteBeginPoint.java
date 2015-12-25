package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteBeginPoint extends MySprite{

	public SpriteBeginPoint(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, LevelController level) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, vertexBufferObjectManager, level);
		this.setIgnoreUpdate(true);
	}

	@Override
	public SpriteType getSpriteType() {
		
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		
		
	}

}
