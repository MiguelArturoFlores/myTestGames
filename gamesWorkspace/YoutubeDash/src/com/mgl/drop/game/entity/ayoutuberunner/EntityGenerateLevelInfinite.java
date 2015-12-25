package com.mgl.drop.game.entity.ayoutuberunner;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import com.mgl.base.MyEntity;
import com.mgl.base.MyFactory;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.userinfo.PhysicSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.arunner.SpriteFloor;
import com.mgl.drop.game.sprites.arunner.SpritePlayer;
import com.mgl.drop.util.Point;

public class EntityGenerateLevelInfinite extends MyEntity{
	
	private float DISTANCE_TO_GENERATE = 1600;
	
	private ArrayList<MySpriteGeneral> spriteList;
	private ArrayList<MySpriteGeneral> spriteListToRemove;
	private ArrayList<MySpriteGeneral> spriteListToAdd;

	private float contTimeElapsed = 0;

	private Point lastItemPoint;

	private float r = 20;
	private float g = 20;
	private float b = 20;
	
	
	public EntityGenerateLevelInfinite(LevelController level){
		try {
			
			
			
			spriteList = new ArrayList<MySpriteGeneral>();
			spriteListToAdd = new ArrayList<MySpriteGeneral>();
			spriteListToRemove = new ArrayList<MySpriteGeneral>();
			
			lastItemPoint = new Point(0, 500);
			
			generateFloorOnPlayer(level);
			
			//for(int i=0;i<100;i++){
			//	generateMoreFloor(0, level);
			//}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void generateFloorOnPlayer(LevelController level) {
		try {
			
			lastItemPoint = new Point(level.getPlayer().getX()-MainDropActivity.CAMERA_WIDTH,500);
			//for(int i=0; i<2 ; i ++)
				generateMoreFloor(0, level);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
			contTimeElapsed = contTimeElapsed + dTime;
			//removeSprites
			
			SpritePlayer player = level.getPlayer();
			if(lastItemPoint != null && Point.distanceBetween(new Point(player.getX(), player.getY()), lastItemPoint)<DISTANCE_TO_GENERATE){
			 
				generateMoreFloor(dTime,level);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void generateMoreFloor(float dTime, LevelController level) {
		try {
			
			for(int i=0; i<2; i ++){
			
				SpriteFloor floor = (SpriteFloor) MyFactory.createObstacle(SpriteTypeConstant.LEVEL_GENERAL_FLOOR, level);
				floor.setPosition(lastItemPoint.getX(),lastItemPoint.getY());
				PhysicSingleton.getInstance().loadStaticRectangle(floor);
				floor.setZIndex(ZIndexGame.ROOF);
				level.getScene().attachChild(floor);
				//level.addSpriteToUpdate(floor);
				
				floor.setColor(r,g,b);
				spriteList.add(floor);
				
				lastItemPoint = new Point(floor.getX()+floor.getWidth(), floor.getY());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateChangeColor(float r, float g, float b){
		try {
			
			this.r = r;
			this.g = g;
			this.b = b;
			
			for(MySpriteGeneral spr : spriteList ){
				
				if(spr instanceof SpriteFloor){
					
					((SpriteFloor) spr).setColor(r, g, b);
					
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			}
	}
	
}
