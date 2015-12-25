package com.mgl.base;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import com.badlogic.gdx.physics.box2d.Body;
import com.mgl.drop.game.controller.LevelController;

public interface MySpriteGeneral {

	public void update(float dTime,LevelController level);
	public SpriteType getSpriteType();
	public void setStatus(StatusType status);
	public StatusType getStatus();
	public  void poop(MySpriteGeneral poop, LevelController level);
	public void setMustUpdate(boolean mustUpdate);
	public float getTime();
	public IEntity getEntity();
	public ITouchArea getTouchArea();
	public void setBody(Body body);
	public Body getBody();
	public int getCollitionType();
	public String getXmlName();
	public void setXmlParameter(String parameter);
	
	
}
