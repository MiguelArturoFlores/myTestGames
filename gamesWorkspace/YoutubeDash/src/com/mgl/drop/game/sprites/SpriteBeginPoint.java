package com.mgl.drop.game.sprites;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class SpriteBeginPoint extends MySprite{
	
	private boolean isActivated = false;

	public SpriteBeginPoint(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, LevelController level) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager, level);
		this.setIgnoreUpdate(true);
	}

	@Override
	public SpriteType getSpriteType() {
		
		return SpriteType.BEGIN_POINT;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			if(!mustUpdate )
				return;
			
			if(isActivated){
				level.removeEntity(this);
				return;
			}
			LevelController.setBodyPixelPosition(this.getX(), this.getY()+50, 0, level.getPlayer().getWidth(), level.getPlayer().getHeight(), level.getPlayer().getBody());
			SmoothCamera camera = (SmoothCamera) SceneManagerSingleton.getInstance().getCamera();
			camera.setCenterDirect(this.getX()+400, this.getY());
			isActivated = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
