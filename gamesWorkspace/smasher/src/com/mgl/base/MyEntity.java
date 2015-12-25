package com.mgl.base;

import com.mgl.drop.game.controller.LevelController;

public abstract class MyEntity implements MySpriteGeneral{
	
	
	protected boolean mustUpdate = true;
	
	public abstract void updateChild(float dTime, LevelController level);

	@Override
	public void update(float dTime, LevelController level) {
		if (!mustUpdate) {
			return;
		}
		updateChild(dTime, level);
		
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
