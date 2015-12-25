package com.mgl.drop.game.entity;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;

import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;

public class EntityChangeLevel implements MySpriteGeneral{
	
	private float waitTime;
	private float currentTime;
	
	public EntityChangeLevel(float waitTime){
		try {
			
			currentTime=0;
			this.waitTime = waitTime;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(float dTime, LevelController level) {
		
		currentTime =currentTime+dTime;
		if(currentTime<waitTime){
			return;
		}
		level.removeEntity(this);
		SceneManagerSingleton manager = SceneManagerSingleton.getInstance();
		manager.createSelectLevelScene();
		manager.setCurrentScene(AllScenes.SELECT_LEVEL);
		
		Camera camera = SceneManagerSingleton.getInstance().getCamera();
		camera.getHUD().detachChildren();
		
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatus(StatusType status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StatusType getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void poop(MySpriteGeneral poop, LevelController level) {
		// TODO Auto-generated method stub
		
	}

	
}
