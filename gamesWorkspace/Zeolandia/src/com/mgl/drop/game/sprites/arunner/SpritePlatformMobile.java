package com.mgl.drop.game.sprites.arunner;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpritePlatformMobile extends MySprite{

	private static final float MIN_DISTANCE_TO_UPDATE = 1000;
	private float timeToChange = 0;
	private boolean horizontal = true;
	
	private float speed = 150; 
	private float contDistance = 0;
	private int direction=1;
	
	public SpritePlatformMobile(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			
		} catch (Exception e) {
			
		}
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.PLATFORM_MOBILE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			this.getBody().setType(BodyType.KinematicBody);
			SpritePlayer player = level.getPlayer();
			
			if(Point.distanceBetween(new Point(player.getX(), player.getY()), new Point(this.getX(), this.getY()))>MIN_DISTANCE_TO_UPDATE){
				return;
			}
			
			if(timeToChange<=0){
				this.getBody().setLinearVelocity(0, 0);
				return;
			}

			contDistance = contDistance + dTime;
			
			this.getBody().setLinearVelocity(speed/100f*direction, 0f);
			
			if(contDistance > timeToChange){
				contDistance = 0;
				direction = direction *-1;
			}
			
			
		} catch (Exception e) {
		}
	}


	public float getTimeToChange() {
		return timeToChange;
	}

	public void setTimeToChange(float timeToChange) {
		this.timeToChange = timeToChange;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getContDistance() {
		return contDistance;
	}

	public void setContDistance(float contDistance) {
		this.contDistance = contDistance;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setXmlParameter(String parameter) {
		try {
			
			ArrayList<Long> parameterList = MyXmlParser.getParameterList(parameter);
			int i = 0;
			for(Long param : parameterList){
				try {
					if(i==0){
						timeToChange = param.floatValue();
					}
					if(i==1){
						speed = param.floatValue();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}