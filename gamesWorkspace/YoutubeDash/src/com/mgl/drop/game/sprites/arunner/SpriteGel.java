package com.mgl.drop.game.sprites.arunner;

import org.andengine.entity.shape.IShape;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteGel extends MySprite{

	private boolean remove = false;
	
	
	public SpriteGel(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			/*TextureSingleton texture = TextureSingleton.getInstance();
			background = new SpriteBackground(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager());
			background.setAlpha(0.5f);
			background.setSize(this.getWidth()*0.9f, this.getHeight()*0.6f);
			background.setZIndex(ZIndexGame.FADE);
			background.setY(pY);
			this.attachChild(background);
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.GEL;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			
			if(remove){
				level.removeEntity(this);
				//PhysicSingleton.getInstance().removeBody(body);
				level.addSpriteToUpdate(this);
				remove = false;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean collidesWith(IShape pOtherShape) {
		return super.collidesWith(pOtherShape);
		//return background.collidesWith(pOtherShape);
	}

	public void removeBody() {
		try {
			remove = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
