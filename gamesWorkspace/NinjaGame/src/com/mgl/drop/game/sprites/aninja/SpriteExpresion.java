package com.mgl.drop.game.sprites.aninja;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteExpresion extends MySprite{

	private float timeToDesapear;
	private float contTime;
	
	public SpriteExpresion(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, float timeToDesapear) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			
			this.timeToDesapear = timeToDesapear;
			contTime = 0;
			
		} catch (Exception e) {
			
		}
		
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			contTime += dTime;
			if(contTime>timeToDesapear){
				this.setAlpha(0);
				level.removeEntity(this);
				this.detachSelf();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
