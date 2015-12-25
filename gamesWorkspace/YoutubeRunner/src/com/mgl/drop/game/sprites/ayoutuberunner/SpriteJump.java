package com.mgl.drop.game.sprites.ayoutuberunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteJump extends MySprite{

	private boolean isActive = true;
	
	public SpriteJump(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			if(!isActive){
				return;
			}
			
			if(this.collidesWith(level.getPlayer())){
				level.getPlayer().jumpLong();
				isActive = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
