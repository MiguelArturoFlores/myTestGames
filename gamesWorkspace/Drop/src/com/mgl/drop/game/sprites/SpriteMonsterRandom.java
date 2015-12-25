package com.mgl.drop.game.sprites;

import org.andengine.audio.sound.Sound;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpriteMonsterRandom extends MyAnimateSprite{
	
	private static final Double WAITING = 0D;
	private static final Double WALKING_TO_POINT = 1D;
	
	private float maxDistance = 200;
	private float distance = 0;
	private float speed = 20f;
	private float minY;
	private float maxY;
	private boolean botherSound = false;
	
	private boolean actionFinished = true;
	private Double updType = WALKING_TO_POINT;
	
	private Point pointToMove = null;
	private float waitingTimeMax = 3;
	
	public SpriteMonsterRandom(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, LevelController level,float minY,float maxY) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		this.maxY=maxY;
		this.minY=minY;
		status = StatusType.NORMAL;
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			
			if(status.equals(StatusType.NORMAL)){
				
				if(actionFinished ){
					Double updType = ((Math.random()* 12542)%2);
					if(updType.intValue() == 0){
						this.updType  = WAITING;
						pointToMove = null;
					}else if(updType.intValue() == 1){
						pointToMove = null;
						this.updType = WALKING_TO_POINT;
					}
				}
				
				if(updType == WAITING){
					
					updateWaiting(dTime,level);
					
					
				}else if(updType== WALKING_TO_POINT){
					
					updateMoving(dTime,level);
				}
			
				
			}else if (status.equals(StatusType.POOPED)){
				changeAnimateState(State.POOP_BEGIN,false);
				if(!botherSound){
					botherSound = true;
					Sound s = SoundSingleton.getInstance().getSound("poopReactOld.mp3");
					if(s!=null){
						s.play();
					}
				}
				if(!isAnimationRunning()){
					changeAnimateState(State.POOP_END, true);
					status=StatusType.FINISHED;
				}
			}else if (status.equals(StatusType.POOPED_END)){
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void updateWaiting(float dTime, LevelController level) {
		try {
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void updateMoving(float dTime, LevelController level) {
		try {
			if(pointToMove == null){
				
				Double dis = (Math.random()*12324523)%maxDistance;
				
					
				Double val = (Math.random()*12324523)%2;
				if(val.equals(0D)){
					if(this.getY()+dis>maxY){
						dis = maxY+0D;
						changeAnimateState(State.WALKIN_RIGHT,true);
						if(speed<0)
							speed = speed*-1;
					}
				}
				if(val.equals(1D)){
					dis = dis*-1;
					if(this.getY()+dis<minY){
						dis = minY+0D;
						changeAnimateState(State.WALKIN_LEFT,true);
						if(speed>0)
							speed = speed*-1;
					}
					
				}
				
				
				
				pointToMove = new Point(this.getX(), dis.floatValue());
			}
			
			
			float distanceTo  = dTime * speed;
			distanceTo  = dTime * speed;
			
			this.setPosition(this.getX(),this.getY()+distanceTo);
			if(status.equals(State.WALKIN_LEFT)){
				if(this.getY()<=pointToMove.getY()){
					this.setY(pointToMove.getY());
					actionFinished = true;
				}
			}
			if(status.equals(State.WALKIN_RIGHT)){
				if(this.getY()>=pointToMove.getY()){
					this.setY(pointToMove.getY());
					actionFinished = true;
				}
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initHashMap() {
		fps = new long[] { 200, 200, 200 };
		stateAnimate.put(State.WALKIN_DOWN, new MyAnimateProperty(0, 3,fps ));
		stateAnimate.put(State.WALKIN_LEFT, new MyAnimateProperty(3, 3,fps ));
		stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0, 3,fps ));
		stateAnimate.put(State.WALKIN_UP, new MyAnimateProperty(0, 3,fps));
		stateAnimate.put(State.POOP_BEGIN, new MyAnimateProperty(6, 3,new long[] { 200, 300, 200 }));
		stateAnimate.put(State.POOP_END, new MyAnimateProperty(9, 2,new long[] { 200, 200 }));
//		stateAnimate.put(State.WALKIN_DOWN, 0);
//		stateAnimate.put(State.WALKIN_LEFT, 3);
//		stateAnimate.put(State.WALKIN_RIGHT, 6);
//		stateAnimate.put(State.WALKIN_UP, 9);

	}

	@Override
	public void initAnimationParams() {

		fps = new long[] { 100, 100, 100 };
		imageNumber = 3;
		//currentState = State.WALKIN_UP;
		changeAnimateState(State.WALKIN_RIGHT,true);
		anime(true);
	}

	@Override
	public SpriteType getSpriteType() {
		
		return SpriteType.OBJETIVE;
	}

	public float getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(float maxDistance) {
		this.maxDistance = maxDistance;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getMinY() {
		return minY;
	}

	public void setMinY(float minY) {
		this.minY = minY;
	}

	public float getMaxY() {
		return maxY;
	}

	public void setMaxY(float maxY) {
		this.maxY = maxY;
	}

	public boolean isBotherSound() {
		return botherSound;
	}

	public void setBotherSound(boolean botherSound) {
		this.botherSound = botherSound;
	}


	

}
