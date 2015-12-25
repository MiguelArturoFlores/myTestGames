package com.mgl.drop.game.sprites.ayoutuberunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.game.controller.LevelController;

public class SpriteLikeCoin extends MySprite{

	public SpriteLikeCoin(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		setIgnoreUpdate(true);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.LIKE_COIN;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			if(this.collidesWith(level.getPlayer())){
				this.detachSelf();
				level.removeEntity(this);
				level.increaseLikeCoin();
				SpriteLikeCoinDead lkd = (SpriteLikeCoinDead) MyFactory.createObstacle(SpriteTypeConstant.LIKE_COIN_DEAD, level);
				lkd.setPosition(this);
				level.addSpriteToUpdate(lkd);
				level.getScene().attachChild(lkd);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
