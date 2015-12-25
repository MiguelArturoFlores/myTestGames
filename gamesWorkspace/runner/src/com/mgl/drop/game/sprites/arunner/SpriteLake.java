package com.mgl.drop.game.sprites.arunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.userinfo.PhysicSingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;

public class SpriteLake extends MySprite{

//	private SpriteGel gel; 
	
	public SpriteLake(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager, level);
		try {
			
		//	gel = (SpriteGel) MyFactory.createObstacle(SpriteTypeConstant.GEL, null);
		//	level.getScene().attachChild(gel);
		//	level.addSpriteToUpdate(gel);
		//	gel.setZIndex(ZIndexGame.FADE);
		//	setPosition(this.getX(),this.getY());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		return SpriteType.LAKE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setPosition(float pX, float pY) {
		// TODO Auto-generated method stub
		super.setPosition(pX, pY);
		try {
			
			//gel.setPosition(this.getX()+176,this.getY()+50);
			//gel.setZIndex(ZIndexGame.FADE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
		

}
