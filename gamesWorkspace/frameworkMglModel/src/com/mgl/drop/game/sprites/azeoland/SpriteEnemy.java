package com.mgl.drop.game.sprites.azeoland;

import java.util.ArrayList;
import java.util.Stack;

import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.util.Point;

public class SpriteEnemy extends MyAnimateSprite{

	private Stack<Point> path;
	private Point pointToMove;
	private float normalSpeed = 20;
	
	private float speed = 20;
	private float distanceToBattle = 24;
	
	boolean lookingPlayer = false;
	private Float viewRange = 80f;
	
	private float contToLook = 0;
	private float timeToLook = 0.4f;
	
	public SpriteEnemy(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.ENEMY;
	}

	@Override
	public void initAnimationParams() {
		
		try {

			changeAnimateState(State.WALKIN_DOWN, false);
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
			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(6, 3,
					fps));
			stateAnimate.put(State.WALKIN_LEFT, new MyAnimateProperty(9, 3,
					fps));
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			float minX = camera.getCenterX()-MainDropActivity.CAMERA_WIDTH/2;
			float maxX = camera.getCenterX()+MainDropActivity.CAMERA_WIDTH/2;
			
			float minY = camera.getCenterY()-MainDropActivity.CAMERA_HEIGHT/2;
			float maxY = camera.getCenterY()+MainDropActivity.CAMERA_HEIGHT/2;
			
			if(this.getX()<minX-50 || this.getX()>maxX+50 || this.getY()<minY-50 || this.getY()> maxY +50){
				return;
			}
			
			updateMoving(dTime,level);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateMoving(float dTime, LevelController level) {
		try {
			
			if( path == null || (path.isEmpty() && pointToMove == null) ){
				lookingPlayer = false;
				searchPointToMove(level);
				
				return;
			}
			
			if(pointToMove == null){
				pointToMove = path.pop();
			}
			
			if(!lookingPlayer){
				lookingPlayer = searchForPlayer(level);
				contToLook = 0;
			}else{
				//is looking for player
				Point playerP = new Point(level.getPlayerAdventure().getX(), level.getPlayerAdventure().getY());
				
				contToLook +=dTime;
				if(contToLook>=timeToLook){
					generatePathToPoint(playerP);
					contToLook = 0;
				}
				
				if(Point.distanceBetween(new Point(this.getX(), this.getY()), playerP)<distanceToBattle){
					
					level.goToBattleMode();
					
					//this.detachSelf();
					//level.removeEntity(this);
				}
				
			}
			
			updateMovingToPoint(dTime);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean searchForPlayer(LevelController level) {
		try {
			
			Point playerP = new Point(level.getPlayerAdventure().getX(), level.getPlayerAdventure().getY());
			
			if(Point.distanceBetween(new Point(this.getX(), this.getY()), playerP)<viewRange){
				generatePathToPoint(playerP);
				speed = normalSpeed *2.5f;
				return true;
			}
			speed = normalSpeed;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void searchPointToMove(LevelController level) {
		try {
			
			
			if(searchForPlayer(level)){
				return;
			}
			
			int x = (int) (MainDropActivity.getRandomMax(0, viewRange.intValue()) - viewRange/2f);
			int y = (int) (MainDropActivity.getRandomMax(0, viewRange.intValue()) - viewRange/2f);
			
			generatePathToPoint(new Point(this.getX()+x, this.getY()+y));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateMovingToPoint(float dTime) {
		try {
			
			Point currentPoint = new Point(this.getX(), this.getY());

			float distance = Point.distanceBetween(currentPoint, pointToMove);
			float angle = Point.angleBetween(currentPoint, pointToMove);
			
			float speedX = (float) (Math.cos(angle)*speed);
			float speedY = (float) (Math.sin(angle)*speed);
			
			float distanceX = speedX * dTime;
			float distanceY = speedY * dTime;
			
			this.setPosition(this.getX()+distanceX,this.getY()+distanceY);
			
			angle = (float) (angle * 180/Math.PI);
			
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
			
			if(distance<1.5f){
				pointToMove = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void generatePathToPoint(Point pointToMove) {
		try {
			
			path = level.getLevelWorld().calculatePath(new Point(this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2),pointToMove,400);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
