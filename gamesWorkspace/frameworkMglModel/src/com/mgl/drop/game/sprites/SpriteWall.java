package com.mgl.drop.game.sprites;

import org.andengine.engine.handler.collision.CollisionHandler;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class SpriteWall extends MySprite {

	private float timeToDesapear = 3f;
	private float contTime = 0;
	
	private float xToMove;
	private float yToMove;
	
	private float totalHeight;
	private float currentHeight= 0;

	private SpriteBackground collideShape;
	
	public SpriteWall(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		
		
		
	}

	public void setCollisionShape(){
		try {
			
			float percentage = 0.4f;
			
			TextureSingleton texture = TextureSingleton.getInstance();
			collideShape = new SpriteBackground(0, 0, texture.getTextureByName("black.jpg"),texture.getVertexBufferObjectManager());
			collideShape.setWidth(this.getWidth());
			collideShape.setHeight(this.getHeight()*percentage);
			collideShape.setPosition(0,this.getHeight()-this.getHeight()*percentage);
			collideShape.setAlpha(0);
			this.attachChild(collideShape);
			
			xToMove = this.getX();
			yToMove = this.getY();
			totalHeight = this.getHeight();
			
			this.setY(yToMove +this.getHeight());
			this.setHeight(0);

			currentHeight = 0;
			
			status = StatusType.APPEARING;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean collidesWith(org.andengine.entity.shape.IShape pOtherShape) {
		try {
			
			return collideShape.collidesWith(pOtherShape);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public SpriteType getSpriteType() {

		return SpriteType.WALL;
	}

	
	@Override
	public void update(float dTime, LevelController level) {
		try {

			if(status.equals(StatusType.APPEARING)){
				
				currentHeight = currentHeight + 30f;
				this.setHeight(currentHeight);
				this.setY(this.getY() - 30);
				
				if(this.getHeight()>totalHeight){
					status = StatusType.NORMAL;
				}
				
			}else if (status.equals(StatusType.NORMAL)){
			
				contTime += dTime;
				if (contTime > timeToDesapear){
					status = StatusType.DESAPEARING;
				}
				
			}else if (status.equals(StatusType.DESAPEARING)){
				this.detachSelf();
				level.removeEntity(this);
			}

			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
