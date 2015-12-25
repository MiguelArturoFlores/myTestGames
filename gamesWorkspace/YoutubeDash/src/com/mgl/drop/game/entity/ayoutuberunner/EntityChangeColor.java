package com.mgl.drop.game.entity.ayoutuberunner;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.util.color.Color;

import com.mgl.base.MyEntity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.entity.arunner.EntityMoveBackground;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteColorChange;

public class EntityChangeColor extends MyEntity {

	private EntityMoveBackground entityMove;
	private EntityGenerateLevelInfinite levelInfinite;

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
	private SpriteColorChange spriteColor;

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
			
			spriteColor.setContTime(spriteColor.getContTime() + dTime);
			if( spriteColor.getContTime()>spriteColor.getTime() ){
				changeColor = false;
			}
			levelInfinite.updateChangeColor(plusR, plusG, plusB);
			entityMove.updateChangeColorNew(plusR, plusG, plusB);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EntityMoveBackground getEntityMove() {
		return entityMove;
	}

	public void setEntityMove(EntityMoveBackground entityMove) {
		this.entityMove = entityMove;
	}

	public EntityGenerateLevelInfinite getLevelInfinite() {
		return levelInfinite;
	}

	public void setLevelInfinite(EntityGenerateLevelInfinite levelInfinite) {
		this.levelInfinite = levelInfinite;
	}

	public void changeColorEntity(SpriteColorChange spriteColorChange) {
		try {

			changeColor = true;
			spriteColor = spriteColorChange;

			plusR = spriteColor.getrBegin();
			plusG = spriteColor.getgBegin();
			plusB = spriteColor.getbBegin();

			colorToMove = new Color(spriteColorChange.getrEnd(),
					spriteColorChange.getgEnd(), spriteColorChange.getbEnd());

			timeToChange = spriteColorChange.getTime();

			speedR = distanceBetween(plusR, colorToMove.getRed())
					/ timeToChange;
			speedG = distanceBetween(plusG, colorToMove.getGreen())
					/ timeToChange;
			speedB = distanceBetween(plusB, colorToMove.getBlue())
					/ timeToChange;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private float distanceBetween(float plus1, float plus2) {
		try {

			return (plus2 - plus1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

}
