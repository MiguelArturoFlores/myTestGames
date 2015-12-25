package com.mgl.drop.game.entity.ice;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import com.mgl.base.MyEntity;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.aicerunner.SpritePlayer;
import com.mgl.drop.game.sprites.aicerunner.SpriteSnowFloor;

public class EntityPenguinSnow extends MyEntity{
	
	private float contTime  = 1;
	private float timeToGenerate = 0.12f; 
	
	public EntityPenguinSnow(){
		try {
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			
			contTime+=dTime;
			
			if(contTime<timeToGenerate){
				return;
			}
			
			contTime = 0;
			
			SpritePlayer player = level.getPlayer();
			
			int val = MainDropActivity.getRandomMax(2, 3);
			
			for(int i = 0 ; i < val ; i++){
				
				SpriteSnowFloor snow = (SpriteSnowFloor) PoolObjectSingleton.getInstance().getSnowFloor();
				snow.reset();
				
				int val2 = MainDropActivity.getRandomMax(0, 100);
				snow.setZIndex(player.getZIndex() - 1 );
				if(val2 < 50){
					snow.setZIndex(player.getZIndex() -1 );
				}
				
				
				float x = MainDropActivity.getRandomMax(0, (int) player.getWidth());
				float y = MainDropActivity.getRandomMax(0, (int) (player.getHeight()*0.2f));
				
				snow.setPosition(player.getX()+ x, player.getY() +player.getHeight() - y);
				level.addSpriteToUpdate(snow);
				level.getScene().attachChild(snow);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
