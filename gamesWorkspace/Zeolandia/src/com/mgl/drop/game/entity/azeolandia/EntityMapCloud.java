package com.mgl.drop.game.entity.azeolandia;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyEntity;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class EntityMapCloud extends MyEntity{

	private float contTime= 0;
	private float timeToGenerate = 4;
	
	@Override
	public void setMustUpdate(boolean mustUpdate) {
		
	}

	@Override
	public float getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IEntity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITouchArea getTouchArea() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateChild(float dTime, LevelController level) {
		try {
			
			contTime += dTime;
			
			if(contTime<timeToGenerate){
				return;
			}
			
			contTime = 0;
			
			generateCloud(level);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateCloud(LevelController level) {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			
			int val = MainDropActivity.getRandomMax(1, 2);
			for(int i=0;i<val;i++){
				
				int j = MainDropActivity.getRandomMax(1, 5);
				SpriteCloud cloud = new SpriteCloud(0, 0, texture.getTextureByName("cloud"+j+".png"), texture.getVertexBufferObjectManager());
				
				int x = MainDropActivity.getRandomMax(0, 25) -30;
				int y = MainDropActivity.getRandomMax(0, MainDropActivity.CAMERA_HEIGHT-50);
				
				cloud.setPosition(x,y);
				
				float scale = 2;
				cloud.setSize(cloud.getWidth()*scale, cloud.getHeight()*scale);
				cloud.setSpeed(MainDropActivity.getRandomMax(15, 25) );
				cloud.setAlpha(1f-(float)MainDropActivity.getRandomMax(20, 70)/100f);
				level.getScene().attachChild(cloud);
				level.addSpriteToUpdate(cloud);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public class SpriteCloud extends MySprite {

		private float speed = 20;
		
		public SpriteCloud(float pX, float pY,
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
				
				float distance = dTime * speed;
				this.setPosition(this.getX() + distance,this.getY());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public float getSpeed() {
			return speed;
		}

		public void setSpeed(float speed) {
			this.speed = speed;
		}
		
		
		
	}
	
	
}
