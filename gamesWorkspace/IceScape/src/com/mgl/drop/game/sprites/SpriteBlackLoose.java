package com.mgl.drop.game.sprites;

import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class SpriteBlackLoose extends MySprite{

	public SpriteBlackLoose(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		this.setAlpha(0);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			this.setPosition(camera.getCenterX()-MainDropActivity.CAMERA_WIDTH/2,camera.getCenterY()-MainDropActivity.CAMERA_HEIGHT/2);
			this.setAlpha(this.getAlpha() + 0.1f);
			if(this.getAlpha()>=1){
				this.setAlpha(1);
				level.reloadLevel();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
