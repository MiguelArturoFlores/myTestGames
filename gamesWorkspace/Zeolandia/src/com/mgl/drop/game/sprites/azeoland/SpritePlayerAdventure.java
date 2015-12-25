package com.mgl.drop.game.sprites.azeoland;

import java.util.Stack;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.controller.dijkstra.WorldNode;
import com.mgl.drop.util.Point;

public class SpritePlayerAdventure extends MyAnimateSprite {
	
	private Stack<Point> path;
	private Point pointToMove;
	private float speed = 80;
	
	private float contIdle = 0;
	private float timeToIdle = 25;
	private boolean mustRePosition = true;
	
	public SpritePlayerAdventure(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		path = new Stack<Point>();
		mustRePosition = true;
		
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.PLAYER;
	}

	@Override
	public void initAnimationParams() {
		try {

			changeAnimateState(State.IDLE, false);
			anime(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83};

			stateAnimate.put(State.WALKIN_UP, new MyAnimateProperty(0, 3,
					fps));
			stateAnimate.put(State.WALKIN_DOWN, new MyAnimateProperty(3, 3,
					fps));
			stateAnimate.put(State.WALKIN_LEFT, new MyAnimateProperty(6, 3,
					fps));
			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(9, 3,
					fps));
			
			stateAnimate.put(State.NORMAL, new MyAnimateProperty(9, 2,
					new long[] { 83, 83}));
			
			stateAnimate.put(State.IDLE, new MyAnimateProperty(13, 2,
					new long[] { 83, 83}));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			updateMoving(dTime,level);
			
			updateIdle(dTime,level);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateIdle(float dTime, LevelController level) {
		try {
			
			if(!path.isEmpty() || pointToMove != null){
				return;
			}
			
			/*if(currentState.equals(State.IDLE) || !isAnimationRunning()){
				changeAnimateState(State.NORMAL, false);
			}*/
			
			contIdle +=dTime;
			if(contIdle<timeToIdle){
				return;
			}
			contIdle = 0;
			changeAnimateState(State.IDLE, false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateMoving(float dTime, LevelController level) {
		try {
			
			if(path == null ||( path.isEmpty() && pointToMove == null)){
				return;
			}
			
			if(pointToMove == null){
				pointToMove = path.pop();
			}
			
			
			
			updateMovingToPoint(dTime);
			//this.setPosition(pointToMove.getX()+6 - this.getWidth()/2,pointToMove.getY()+8 - this.getHeight()/2);
			//pointToMove = null;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateMovingToPoint(float dTime) {
		try {
			
			Point currentPoint = new Point(this.getX(), this.getY());
			float distanceA = Point.distanceBetween(currentPoint, pointToMove);
			
			float angle = Point.angleBetween(currentPoint, pointToMove);
			float speedX = (float) (Math.cos(angle)*speed);
			float speedY = (float) (Math.sin(angle)*speed);
			float distanceX = speedX * dTime;
			float distanceY = speedY * dTime;
			
			//this.setPosition(this.getX()+distanceX,this.getY()+distanceY);
			
			angle = (float) (angle * 180/Math.PI);

			float distance = speed * dTime;
			
			while(distance>0){

				//right
				if(angle>= -45 && angle<=45){

					if( currentPoint.getX()+distance >= pointToMove.getX()){
						//need another point
						distance = currentPoint.getX()+distance -pointToMove.getX();
						this.setPosition(pointToMove.getX(),this.getY());
						//validating next point
						if( path != null && !path.isEmpty()){
							pointToMove = path.pop();
						}
						else{
							pointToMove = null;
						}
					}else{
						this.setPosition(this.getX()+distance,this.getY());
						distance=0;
					}
					
					currentPoint = new Point(this.getX(), this.getY());
					angle = Point.angleBetween(currentPoint, pointToMove);
					angle = (float) (angle * 180/Math.PI);

				}
				//down
				else if(angle>= 45 && angle<=135){
					
					if( currentPoint.getY()+distance >= pointToMove.getY()){
						//need another point
						distance = currentPoint.getY()+distance -pointToMove.getY();
						this.setPosition(this.getX(),pointToMove.getY());
						//validating next point
						
						if( path != null && !path.isEmpty()){
							pointToMove = path.pop();
						}
						else{
							pointToMove = null;
						}
						
					}else{
						this.setPosition(this.getX(),this.getY()+distance);
						distance=0;
					}
					
					currentPoint = new Point(this.getX(), this.getY());
					angle = Point.angleBetween(currentPoint, pointToMove);
					angle = (float) (angle * 180/Math.PI);
				
				}
				//up
				else if(angle>= -135 && angle<=-45){
					
					if( currentPoint.getY()-distance <= pointToMove.getY()){
						//need another point
						distance = pointToMove.getY() - (currentPoint.getY() - distance);
						this.setPosition(this.getX(),pointToMove.getY());
						//validating next point
						
						if( path != null && !path.isEmpty()){
							pointToMove = path.pop();
						}
						else{
							pointToMove = null;
						}
						
					}else{
						this.setPosition(this.getX(),this.getY()-distance);
						distance=0;
					}
					
					currentPoint = new Point(this.getX(), this.getY());
					angle = Point.angleBetween(currentPoint, pointToMove);
					angle = (float) (angle * 180/Math.PI);
					
				}
				else {
					//left
					if( currentPoint.getX()-distance <= pointToMove.getX()){
						//need another point
						distance = pointToMove.getX() - (currentPoint.getX()- distance);
						this.setPosition(pointToMove.getX(),this.getY());
						//validating next point
						
						if( path != null && !path.isEmpty()){
							pointToMove = path.pop();
						}
						else{
							pointToMove = null;
						}
						
					}else{
						this.setPosition(this.getX()-distance,this.getY());
						distance=0;
					}
					
					currentPoint = new Point(this.getX(), this.getY());
					angle = Point.angleBetween(currentPoint, pointToMove);
					angle = (float) (angle * 180/Math.PI);
					
				}
				
			}
			
				//right
				if(angle>= -45 && angle<=45){
					changeAnimateState(State.WALKIN_RIGHT, false);
				//down
				}else if(angle>= 45 && angle<=135){
					changeAnimateState(State.WALKIN_DOWN, false);
				//up
				}else if(angle>= -135 && angle<=-45){
					changeAnimateState(State.WALKIN_UP, false);
				//left	
				}else{
					changeAnimateState(State.WALKIN_LEFT, false);
				}
				
				if(distanceA <1.5f){
					//pointToMove = null;
					return;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void generatePathToPoint(Point pointToMove) {
		try {
			
			path = level.getLevelWorld().calculatePath(new Point(this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2),pointToMove,900);
			pointToMove = null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public boolean isMustRePosition() {
		return mustRePosition;
	}

	public void setMustRePosition(boolean mustRePosition) {
		this.mustRePosition = mustRePosition;
	}

	
}
