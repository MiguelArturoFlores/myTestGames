package com.mgl.drop.game.entity;

import java.util.ArrayList;

import org.andengine.entity.scene.Scene;

import android.util.Log;

import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;

import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteRandomPoop;
import com.mgl.drop.texture.TextureSingleton;

public class BackgroudLevelSceneEntity implements MySpriteGeneral{

	
	private ArrayList<SpriteBackground> backgroundSky;
	private ArrayList<SpriteBackground> backgroundCity;
	
	private ArrayList<SpriteRandomPoop> randomList;
	
	private float contTime = 0;
	private float maxTime = 3;
	
	public BackgroudLevelSceneEntity(Scene scene){
		try {
			
			backgroundSky= new ArrayList<SpriteBackground>();
			backgroundCity= new ArrayList<SpriteBackground>();
			
			randomList = new ArrayList<SpriteRandomPoop>();
			
			TextureSingleton texture =  TextureSingleton.getInstance();
			
			SpriteBackground back = new SpriteBackground(0, -100, texture.getTextureByName("backgroundSky.png"), texture.getVertexBufferObjectManager());
			scene.attachChild(back);
			backgroundSky.add(back);
			
			back = new SpriteBackground(back.getWidth()*-1, -100, texture.getTextureByName("backgroundSky.png"), texture.getVertexBufferObjectManager());
			scene.attachChild(back);
			backgroundSky.add(back);
			
			back = new SpriteBackground(0, -100, texture.getTextureByName("backgroundBird.png"), texture.getVertexBufferObjectManager());
			scene.attachChild(back);
			backgroundCity.add(back);
			
			back = new SpriteBackground(back.getWidth()*-1, -400, texture.getTextureByName("backgroundBird.png"), texture.getVertexBufferObjectManager());
			scene.attachChild(back);
			backgroundCity.add(back);
			
			for(int i = 0 ; i < 4; i ++){
				SpriteRandomPoop random = new SpriteRandomPoop(0, -400, texture.getTextureAnimateByName("poopBasic.png"), texture.getVertexBufferObjectManager(), null);
				random.setSize(80, 80);
				scene.attachChild(random);
				randomList.add(random);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			//Log.d("updating", "updating "+dTime);
			
			contTime = contTime+dTime;
			if(contTime>maxTime){
				generateRandomShit();
				contTime=0;
			}
			
			for(MySpriteGeneral spr : randomList){
				spr.update(dTime, null);
			}
			
			float distance = 1;
			float distanceCity = 2;
			
			for(SpriteBackground spr : backgroundSky){
				
				spr.setX(spr.getX()+distance);
				
				if(spr.getX()>spr.getWidth()){
					spr.setX(spr.getWidth()*-1);
					spr.setX(spr.getX()+distance);
				}
			}
			
			for(SpriteBackground spr : backgroundCity){
				
				spr.setX(spr.getX()+distanceCity);
				
				if(spr.getX()>spr.getWidth()){
					spr.setX(spr.getWidth()*-1);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void generateRandomShit() {
		try {
			
			for(SpriteRandomPoop poop : randomList){
				
				if(poop.isMustUpdate()){
					continue;
				}
				
				float x = (float) ((Math.random()*124634654)%MainDropActivity.getCAMERA_WIDTH()-20);
				float y = (float) ((Math.random()*124634654)%MainDropActivity.getCAMERA_HEIGHT()-20)*-1;
				
				poop.setPosition(x,y);
				poop.setMustUpdate(true);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.DECORATIVE;
	}

	@Override
	public void setStatus(StatusType status) {

		
	}

	@Override
	public StatusType getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void poop(MySpriteGeneral poop, LevelController level) {

		
	}

	
	
}
