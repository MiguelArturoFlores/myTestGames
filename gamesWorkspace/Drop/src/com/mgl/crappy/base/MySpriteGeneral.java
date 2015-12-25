package com.mgl.crappy.base;

import org.andengine.entity.scene.Scene;

import com.mgl.drop.game.controller.LevelController;

public interface MySpriteGeneral {

	public void update(float dTime,LevelController level);
	public SpriteType getSpriteType();
	public void setStatus(StatusType status);
	public StatusType getStatus();
	public  void poop(MySpriteGeneral poop, LevelController level);
	
}
