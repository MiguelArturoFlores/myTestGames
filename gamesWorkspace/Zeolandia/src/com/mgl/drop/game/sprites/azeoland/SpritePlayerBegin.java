package com.mgl.drop.game.sprites.azeoland;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;



public class SpritePlayerBegin extends MySprite{

	private boolean isActive = false;
	
	public SpritePlayerBegin(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
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
			
			if(isActive || !level.getPlayerAdventure().isMustRePosition() ){
				this.detachSelf();
				level.removeEntity(this);
				return;
			}
			
			
			level.getPlayerAdventure().setPosition(this.getX(),this.getY());
			isActive = true;
			
			this.detachSelf();
			level.removeEntity(this);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
