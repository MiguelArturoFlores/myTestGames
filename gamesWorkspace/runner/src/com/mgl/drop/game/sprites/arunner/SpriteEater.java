package com.mgl.drop.game.sprites.arunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class SpriteEater extends MySprite{

	
	
	public SpriteEater(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		
	}

	@Override
	public SpriteType getSpriteType() {
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {

		try {
			
			if (!mustUpdate) {
				return;
			}
			
			this.setPosition(SceneManagerSingleton.getInstance().getCamera().getCenterX()-MainDropActivity.CAMERA_WIDTH/2 -50, -MainDropActivity.CAMERA_HEIGHT);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
