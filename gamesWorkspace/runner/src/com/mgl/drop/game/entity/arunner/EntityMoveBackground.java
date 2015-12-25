package com.mgl.drop.game.entity.arunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import com.mgl.base.MyEntity;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;

public class EntityMoveBackground extends MyEntity{
	
	private SpriteBackground background1 ;
	private SpriteBackground background2 ;
	private SpriteBackground background3 ;
	private Queue<SpriteBackground> backgroundList;
	private SpriteBackground last;
	private SpriteBackground first;
	private Float previusXPlayer;
	
	//private SpriteBackground fade;
	private int distance = 0;
	
	
	private LevelController controller;

	public EntityMoveBackground(LevelController controller){
		try {
			
			this.controller = controller;
			
			TextureSingleton texture = TextureSingleton.getInstance();
			initBackground();
			previusXPlayer = null;
			//fade = new SpriteBackground(0, 0, texture.getTextureByName("fade.png"), texture.getVertexBufferObjectManager());
			//fade.setZIndex(ZIndexGame.FADE);
			

			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	private void initBackground() {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			Camera cam = SceneManagerSingleton.getInstance().getCamera();
			
			background1 = new SpriteBackground(0, 0, texture.getTextureByName("backgroundW1.png"), texture.getVertexBufferObjectManager());
			background2 = new SpriteBackground(0, 0, texture.getTextureByName("backgroundW2.png"), texture.getVertexBufferObjectManager());
			background3 = new SpriteBackground(0, 0, texture.getTextureByName("backgroundW3.png"), texture.getVertexBufferObjectManager());
			
			/*background1.setPosition(cam.getCenterX()-MainDropActivity.CAMERA_WIDTH/2,cam.getCenterY()-MainDropActivity.CAMERA_HEIGHT/2);
			background2.setPosition(background1.getX()+background1.getWidth(),0);
			background3.setPosition(background2.getWidth()+background2.getX(),0);
			*/
			
			background1.setWidth(800);
			background2.setWidth(800);
			background3.setWidth(800);
			
			background1.setPosition(-MainDropActivity.CAMERA_WIDTH/2,cam.getCenterY()-MainDropActivity.CAMERA_HEIGHT/2);
			background2.setPosition(background1.getX()+background1.getWidth() -2,0);
			background3.setPosition(background2.getWidth()+background2.getX() -4,0);
			
			controller.getScene().attachChild(background1);
			controller.getScene().attachChild(background2);
			controller.getScene().attachChild(background3);
			
			backgroundList = new LinkedList<SpriteBackground>();
			backgroundList.add(background1);
			backgroundList.add(background2);
			backgroundList.add(background3);
			
			distance = 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	
	@Override
	public void setMustUpdate(boolean mustUpdate) {

		this.mustUpdate = mustUpdate;
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
	
	//@Override
	public void updateChildAux(float dTime, LevelController level) {
		try {
			
			if(true )
				return;
			Camera cam = SceneManagerSingleton.getInstance().getCamera();
			int i = 0;
			for(SpriteBackground back : backgroundList){
				
				if(i==0){
					background1 = back;
					back.setZIndex(ZIndexGame.BACKGROUND1);
				}
				if(i==1){
					background2 = back;
					back.setZIndex(ZIndexGame.BACKGROUND2);
					if(previusXPlayer == null){
						previusXPlayer = level.getPlayer().getX();
					}
					
					int distanceB =(int) (level.getPlayer().getX() - previusXPlayer);
					distanceB =(int) (distanceB*0.4f);
					distance = distance + distanceB;
					previusXPlayer = level.getPlayer().getX();
					
					if(distance > back.getWidth()){
						distance = (int) back.getWidth();
					}
					if(distance < back.getWidth()*-1){
						distance = (int) back.getWidth()*-1;
					}
					back.setX((int)(cam.getCenterX()-MainDropActivity.CAMERA_WIDTH/2 - distance));
				}
				if(i==2){
					background3 = back;
					back.setZIndex(ZIndexGame.BACKGROUND3);
				}
				i++;
				
			}
			
			background1.setWidth(MainDropActivity.CAMERA_WIDTH);
			background2.setWidth(MainDropActivity.CAMERA_WIDTH);
			background3.setWidth(MainDropActivity.CAMERA_WIDTH);
			
			background2.setX((int)background2.getX());
			
			background1.setX((int)(background2.getX()-background2.getWidth()));
			background3.setX((int)(background2.getX()+background2.getWidth()));
			
			if(distance == background2.getWidth()){
				
				backgroundList = new LinkedList<SpriteBackground>();
				backgroundList.add(background2);
				backgroundList.add(background3);
				backgroundList.add(background1);
				distance = 0;
				
			}
			
			if(distance == background2.getWidth()*-1){
				
				backgroundList = new LinkedList<SpriteBackground>();
				backgroundList.add(background3);
				backgroundList.add(background1);
				backgroundList.add(background2);
				distance = 0;
				
			}
			
			updateOnY(dTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void updateChild(float dTime, LevelController level) {
		try {
			
			
			Camera cam = SceneManagerSingleton.getInstance().getCamera();
			int i = 0;
			for(SpriteBackground back : backgroundList){
				
				if(i==0){
					background1 = back;
					back.setZIndex(ZIndexGame.BACKGROUND1);
				}
				if(i==1){
					background2 = back;
					back.setZIndex(ZIndexGame.BACKGROUND2);
					if(previusXPlayer == null){
						previusXPlayer = level.getPlayer().getX();
					}
					
					int distanceB =(int) (level.getPlayer().getX() - previusXPlayer);
					distanceB =(int) (distanceB*0.4f);
					distance = distance + distanceB;
					previusXPlayer = level.getPlayer().getX();
					
					if(distance > back.getWidth()){
						distance = (int) back.getWidth();
					}
					if(distance < back.getWidth()*-1){
						distance = (int) back.getWidth()*-1;
					}
					back.setX((int)(cam.getCenterX()-MainDropActivity.CAMERA_WIDTH/2 - distance));
				}
				if(i==2){
					background3 = back;
					back.setZIndex(ZIndexGame.BACKGROUND3);
				}
				i++;
				
			}
			
			background1.setWidth(MainDropActivity.CAMERA_WIDTH);
			background2.setWidth(MainDropActivity.CAMERA_WIDTH);
			background3.setWidth(MainDropActivity.CAMERA_WIDTH);
			
			background2.setX((int)background2.getX());
			
			background1.setX((int)(background2.getX()-background2.getWidth()));
			background3.setX((int)(background2.getX()+background2.getWidth()));
			
			if(distance == background2.getWidth()){
				
				backgroundList = new LinkedList<SpriteBackground>();
				backgroundList.add(background2);
				backgroundList.add(background3);
				backgroundList.add(background1);
				distance = 0;
				
			}
			
			if(distance == background2.getWidth()*-1){
				
				backgroundList = new LinkedList<SpriteBackground>();
				backgroundList.add(background3);
				backgroundList.add(background1);
				backgroundList.add(background2);
				distance = 0;
				
			}
			
			updateOnY(dTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateOnY(float dTime) {
		try {
			
			Camera cam = SceneManagerSingleton.getInstance().getCamera();
			
			for(SpriteBackground b:backgroundList ){
				b.setY(cam.getCenterY()-MainDropActivity.CAMERA_HEIGHT/2);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
