package com.mgl.base;

import com.badlogic.gdx.physics.box2d.Body;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.controller.LevelController;

public abstract class MyEntity implements MySpriteGeneral{
	
	
	protected boolean mustUpdate = true;
	protected String xmlName;
	
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
	
	public int getCollitionType(){
		return CollitionType.COLLITION_RECTANGLE;
	}
	
	public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}
	
	public Body getBody() {
		return null;
	}

	public void setBody(Body body) {
	}

	public void setXmlParameter(String parameter){
		
	}


	
}
