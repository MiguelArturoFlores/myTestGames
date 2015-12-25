package com.mgl.drop.game.sprites.arunner;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;

public class SpriteTunel extends MySprite{
	
	private Scene scene;
	private SpriteFloor bone;

	public SpriteTunel(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Scene scene) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		
		try {
			this.scene = scene;
			bone = (SpriteFloor) MyFactory.createObstacle(SpriteTypeConstant.TUNNEL,null);
			bone.setPosition(this.getX()-5,this.getY()-25);
			bone.setZIndex(ZIndexGame.FADE);
			scene.attachChild(bone);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		return SpriteType.TUNNEL;
	}

	@Override
	public void update(float dTime, LevelController level) {
		
	}

	@Override
	public void setPosition(float pX, float pY) {
		// TODO Auto-generated method stub
		super.setPosition(pX, pY);
		try {
			
			bone.setPosition(this.getX()-5,this.getY()-25);
			bone.setZIndex(ZIndexGame.FADE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	

}
