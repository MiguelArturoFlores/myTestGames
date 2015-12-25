package com.mgl.drop.game.sprites.arunner;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.os.Vibrator;
import android.util.Log;

import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.util.Path;
import com.mgl.drop.util.Point;

public class SpriteInvisiblePointToFollow extends MySprite {

	private float SHAKE_DISTANCE = 5;
	private Queue<Point> path;
	private Point pointToMove = null;
	private float speed = 80;
	private float normalSpeed = 80;
	private float accelerate = 300;
	private boolean mustUpdate = true;
	private boolean shake = false;
	private float shakeTime = 2;
	private float contTime = 0;

	public SpriteInvisiblePointToFollow(float pX, float pY, float pWidth,
			float pHeight, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pWidth, pHeight, pTextureRegion,
				vertexBufferObjectManager, level);
		path = new LinkedList<Point>();
		try {
			
			shake = false;
			contTime = 0;
			// this.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addPoint(Point p) {
		path.add(p);
	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.INVISIBLE;
	}

	@Override
	public void update(float dTime, LevelController level) {

		try {
			if (!mustUpdate) {
				return;
			}
			float playerX = level.getPlayer().getX()
					+ level.getPlayer().getWidth();
			float playerY = level.getPlayer().getY()
					+ level.getPlayer().getHeight()/2;
			
			

			moveFollowingPath(dTime, level);
			this.setX(playerX+100);
			this.setY(playerY-100);
			
			validateBorders(level);
			
			SmoothCamera camera =  (SmoothCamera) SceneManagerSingleton.getInstance().getCamera();
			//camera.setCenter(this.getX(), this.getY());
			//camera.setChaseEntity(this);
			
			this.setSpeed(normalSpeed);
			if (playerX > camera.getCenterX() && pointToMove.getX()>this.getX()) {

				// camera.setCenter(playerX, this.getY());
				// this.setX(playerX);
				
				//float distance  = Point.distanceBetween(new Point(this.getX(), this.getY()), new Point(playerX, playerY));
				float distance  = playerX - this.getX();
				accelerate  = distance / dTime; 
				this.setSpeed(accelerate + normalSpeed);
				//this.setSpeed(accelerate*0.9f);

			}

			if(shake){
				updateShaking(dTime,level);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateShaking(float dTime, LevelController level) {
		try {
			
			contTime = contTime + dTime;
			if(contTime>shakeTime){
				contTime = 0;
				shake = false;
				return;
			}
			
			float minX = this.getX() - SHAKE_DISTANCE;
			float maxX = this.getX() + SHAKE_DISTANCE;
			float minY = this.getY() - SHAKE_DISTANCE;
			float maxY = this.getY() + SHAKE_DISTANCE;

			float x = MainDropActivity.getRandomMax((int)minX, (int)maxX);
			float y = MainDropActivity.getRandomMax((int)minY, (int)maxY);
			
			this.setPosition(x, y);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void validateBorders(LevelController level) {
		try {
			
			float minX = 0;
			float maxX = Float.MAX_VALUE;
			float minY = -50;
			float maxY = Float.MAX_VALUE;
			
			
			if(this.getX() + MainDropActivity.CAMERA_WIDTH/2 > maxX ){
				this.setX(maxX-MainDropActivity.CAMERA_WIDTH/2);
			}
			if(this.getX() - MainDropActivity.CAMERA_WIDTH/2 < minX){
				this.setX(minX + MainDropActivity.CAMERA_WIDTH/2);
			}
			//en als y
			if(this.getY() + MainDropActivity.CAMERA_HEIGHT/2 > maxY ){
				this.setY(maxY-MainDropActivity.CAMERA_HEIGHT/2);
			}
			if(this.getY() - MainDropActivity.CAMERA_HEIGHT/2 < minY){
				this.setY(minY + MainDropActivity.CAMERA_HEIGHT/2);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void moveFollowingPath(float pSecondsElapsed, LevelController level) {

		try {

			float x = this.getX();
			float y = this.getY();
			float distance = pSecondsElapsed * speed;

			Point currentPoint = new Point(x + getWidth() / 2, y + getHeight()
					/ 2);

			if(path == null){
				return;
			}
			if (pointToMove == null && path.isEmpty()) {

				return;
			}

			if (!path.isEmpty()) {
				pointToMove = path.peek();
			}

			float disAux = Point.distanceBetween(currentPoint, pointToMove);

			if (distance > disAux) {
				if (!path.isEmpty()) {
					pointToMove = path.poll();
				} else {
					this.setPosition(pointToMove.getX() + getWidth() / 2,
							pointToMove.getY() + getHeight() / 2);
					pointToMove = null;
					return;
				}

			}

			float angle = Point.angleBetween(currentPoint, pointToMove);

			float dx = distance * (float) Math.cos(angle);
			float dy = distance * (float) Math.sin(angle);

			this.setPosition(x + dx, y + dy);

			float dxAux = dx;
			float dyAux = dy;

			if (dxAux < 0) {
				dxAux = dxAux * -1;
			}
			if (dyAux < 0) {
				dxAux = dyAux * -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Point getPointToMove() {
		return pointToMove;
	}

	public void setPointToMove(Point pointToMove) {
		this.pointToMove = pointToMove;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public boolean isMustUpdate() {
		return mustUpdate;
	}

	public void setMustUpdate(boolean mustUpdate) {
		this.mustUpdate = mustUpdate;
	}

	public boolean hasPoint() {
		if (path == null || path.isEmpty()) {
			return false;
		}
		return true;
	}

	public Point getLastPoint() {
		try {

			return path.peek();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Queue<Point> getPath() {
		return path;
	}

	public void setPath(Queue<Point> path) {
		this.path = path;
	}

	public Queue<Point> clonePath() {
		Queue<Point> pathAux = new LinkedList<Point>();
		
		try {
			
			for(Point p : path){
				pathAux.add(new Point(p.getX(), p.getY()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pathAux;
	}

	public void shake(int timeShake,float distanceToShake) {
		try {
			
			Vibrator v = (Vibrator) SceneManagerSingleton.getInstance().getActivity().getSystemService(MainDropActivity.VIBRATOR_SERVICE);
			v.vibrate(500);
			
			shake = true;
			contTime = 0;
			this.SHAKE_DISTANCE = distanceToShake;
			this.shakeTime = (float) timeShake; 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
