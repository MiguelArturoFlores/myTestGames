package com.mgl.drop.game.entity;

import java.util.ArrayList;
import java.util.Collections;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyEntity;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class EntityOrderZindex extends MyEntity{

	@Override
	public void setMustUpdate(boolean mustUpdate) {
		// TODO Auto-generated method stub
		
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
			
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			float minX = camera.getCenterX()-MainDropActivity.CAMERA_WIDTH/2 -100;
			float maxX = camera.getCenterX()+MainDropActivity.CAMERA_WIDTH/2 +100;
			
			float minY = camera.getCenterY()-MainDropActivity.CAMERA_HEIGHT/2 -100;
			float maxY = camera.getCenterY()+MainDropActivity.CAMERA_HEIGHT/2 +100;
			
			ArrayList<ComparableElemet> elementList = new ArrayList<EntityOrderZindex.ComparableElemet>();
			
			for(MySpriteGeneral spr : level.getSpriteList()){
				try {
					if(spr.getSpriteType().equals(SpriteType.FLOOR)){
						continue;
					}
				} catch (Exception e) {
				
				}
				
				
				if(spr instanceof MySprite){
					MySprite sprite = (MySprite) spr;
					
					if(sprite.getX()>=minX && sprite.getX()<=maxX){
						if(sprite.getY()>=minY && sprite.getY()<=maxY){
							
							ComparableElemet element = new ComparableElemet(sprite, sprite.getY()+sprite.getHeight());
							elementList.add(element);
							
						}
					}
				}else if(spr instanceof MyAnimateSprite){
					MyAnimateSprite sprite = (MyAnimateSprite) spr;
					
					if(sprite.getX()>=minX && sprite.getX()<=maxX){
						if(sprite.getY()>=minY && sprite.getY()<=maxY){
							
							ComparableElemet element = new ComparableElemet(sprite, sprite.getY()+sprite.getHeight());
							elementList.add(element);
							
						}
					}
				}
			}
			
			Collections.sort(elementList);
			int zindex = ZIndexGame.ORDER_OBJECT;
			
			for(ComparableElemet element : elementList){
				
				if(element.getSpr() instanceof MySprite){
						MySprite spr = (MySprite) element.getSpr();
						spr.setZIndex(zindex);
				}else if(element.getSpr() instanceof MyAnimateSprite){
					MyAnimateSprite spr = (MyAnimateSprite) element.getSpr();
					spr.setZIndex(zindex);
				} 
				
				zindex++;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public class ComparableElemet implements Comparable<ComparableElemet>{
		
		private MySpriteGeneral spr;
		private Float y;
		
		public ComparableElemet(MySpriteGeneral spr, float y) {
			super();
			this.spr = spr;
			this.y = y;
		}

		public MySpriteGeneral getSpr() {
			return spr;
		}

		public void setSpr(MySpriteGeneral spr) {
			this.spr = spr;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}

		@Override
		public int compareTo(ComparableElemet another) {
			
			return this.y.compareTo(another.y);
		}
		
		
		
	}
	

}
