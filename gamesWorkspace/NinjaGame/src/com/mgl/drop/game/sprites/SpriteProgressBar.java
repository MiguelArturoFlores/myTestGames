package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteProgressBar extends MySprite {

	private float percentage = 0;
	private SpriteBackground back;

	public SpriteProgressBar(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,String textureName) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager);
		this.setSize(pWidth, pHeight);
		try {

			back = new SpriteBackground(5, 5, texture.getTextureByName(textureName), texture.getVertexBufferObjectManager());
			back.setSize(1, pHeight-10);
			this.attachChild(back);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub

	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
		try {
			
			float newW = (this.getWidth()-10) *percentage/100f;
			back.setWidth(newW);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
