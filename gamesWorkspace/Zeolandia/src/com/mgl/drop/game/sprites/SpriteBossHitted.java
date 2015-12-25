package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteBossHitted extends MySprite{

	private float cont = 0;
	private float timeDesapear = 0.2f;
	
	public SpriteBossHitted(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		this.setIgnoreUpdate(true);
		
	}

	@Override
	public SpriteType getSpriteType() {
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {

		try {
			cont+=dTime;
			if(cont>timeDesapear){
				level.removeEntity(this);
				this.detachSelf();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void resetConts() {
		try {
			cont = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
