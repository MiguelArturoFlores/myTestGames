package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class SpriteRoof extends MySprite {
	
	
	
	public SpriteRoof(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pWidth, pHeight, pTextureRegion,
				vertexBufferObjectManager, level);

		try {
			
			SpriteTerrain spr = new SpriteTerrain(+5, 0, TextureSingleton.getInstance().getTextureByName("black.jpg"), TextureSingleton.getInstance().getVertexBufferObjectManager());
			//spr.setPosition(this.getX(),this.getY());
			
			spr.setWidth(this.getWidth()-10);
			spr.setHeight((float) (this.getHeight()*0.25));
			//spr.setAlpha(0.5f);
			spr.setVisible(false);
			
			//level.getScene().attachChild(spr);
			this.attachChild(spr);
			level.addSpriteToUpdate(spr);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(float dTime, LevelController level) {

	}

	
	
	@Override
	public SpriteType getSpriteType() {

		return SpriteType.DECORATIVE;
	}

}
