package com.mgl.drop.game.scene;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import com.mgl.base.StatusType;
import com.mgl.drop.game.entity.EntityScore;

public class SceneSmashListener implements IOnSceneTouchListener{

	private EntityScore entityScore;
	
	public SceneSmashListener (EntityScore entityScore){
		
		this.entityScore = entityScore;
		
	}
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			
			entityScore.addTouch();

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		default:

			break;

		}
		
		
		
		return true;
	}

	
	
}
