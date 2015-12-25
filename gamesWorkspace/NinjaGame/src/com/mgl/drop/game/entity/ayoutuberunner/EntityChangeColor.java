package com.mgl.drop.game.entity.ayoutuberunner;

import org.andengine.entity.IEntity;


import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.util.color.Color;

import com.mgl.base.MyEntity;
import com.mgl.drop.game.controller.LevelController;

public class EntityChangeColor extends MyEntity {

	private float plusR = 0;
	private float plusG = 0;
	private float plusB = 0;

	private Color colorToMove;
	private float speedR = 0;
	private float speedG = 0;
	private float speedB = 0;
	
	private float timeToChangeColor = 0.3f;
	private float contTime = timeToChangeColor;

	private float contChangeColor = 0;
	private float timeToChange = 1.5f;

	private boolean changeColor = false;

	public EntityChangeColor(){
		changeColor = false;
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

			if(!changeColor){
				return;
			}
			
			contTime = contTime + dTime;
			
			if(contTime<timeToChangeColor){
				return;
			}
			contTime = 0;
			
			plusR = plusR + speedR * dTime;
			plusG = plusG + speedG * dTime;
			plusB = plusB + speedB * dTime;
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
