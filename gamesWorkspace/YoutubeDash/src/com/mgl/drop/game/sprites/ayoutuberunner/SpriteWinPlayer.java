package com.mgl.drop.game.sprites.ayoutuberunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteWinPlayer extends MySprite{

	public SpriteWinPlayer(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		setIgnoreUpdate(true);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.WIN_PLAYER;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			if(this.collidesWith(level.getPlayer())){
				level.winLevelShowScore();
				level.removeEntity(this);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
