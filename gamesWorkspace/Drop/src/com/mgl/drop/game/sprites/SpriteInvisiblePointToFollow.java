package com.mgl.drop.game.sprites;

import java.util.Stack;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.util.Path;
import com.mgl.drop.util.Point;

public class SpriteInvisiblePointToFollow extends MySprite {

	private Stack<Point> path;
	private Point pointToMove = null;
	private float speed = 400;
	private SpriteEndPoint endPoint;
	private boolean mustUpdate = true;

	public SpriteInvisiblePointToFollow(float pX, float pY, float pWidth,
			float pHeight, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pWidth, pHeight, pTextureRegion,
				vertexBufferObjectManager, level);
		path = new Stack<Point>();
		try {
			//this.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addPoint(Point p) {
		path.push(p);
	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.INVISIBLE;
	}

	@Override
	public void update(float dTime, LevelController level) {

		try {
			if(!mustUpdate){
				return;
			}
			
			
			moveFollowingPath(dTime, level);

			float width = endPoint.getX() + endPoint.getWidth() * 3;
			float height = level.getHeigh();

			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			float xStart = this.getX();
			float yStart = this.getY() + 200;

			if (xStart < camera.getWidth() / 2) {
				xStart = camera.getWidth() / 2;

			}
			if (xStart > width - ((camera.getWidth() / 2))) {
				xStart = width - ((camera.getWidth() / 2));

			}
			if (camera.getWidth() - xStart > 0
					&& xStart + 100 < camera.getWidth()) {
				// xStart = camera.getWidth()/2;
			}

			// sad
			if (yStart + camera.getHeight() / 2 > height) {

				yStart = height - camera.getHeight() / 2;

			}

			if (camera.getHeight() > level.getHeigh()) {
				yStart = camera.getCenterY();
			}

			camera.setCenter(xStart, yStart);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void moveFollowingPath(float pSecondsElapsed, LevelController level) {

		float x = this.getX();
		float y = this.getY();
		float distance = pSecondsElapsed * speed;

		Point currentPoint = new Point(x + getWidth() / 2, y + getHeight() / 2);

		if (pointToMove == null && path.isEmpty()) {
			addStartButton(level);
			
			return;
		}

		if (!path.isEmpty()) {
			pointToMove = path.peek();
		}

		float disAux = Point.distanceBetween(currentPoint, pointToMove);

		if (distance > disAux) {
			if (!path.isEmpty()) {
				pointToMove = path.pop();
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

	}

	private void addStartButton(LevelController level) {
		try {
			
			
			
			level.removeEntity(this);
			
			level.getScene().detachChild(this);
			
			level.setSceneMoveListener();
			//level.initStartButton();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setPath(Path path2) {
		Stack<Point> stack = new Stack<Point>();
		for (Point p : path2.getPath()) {
			stack.push(p);
		}
		Point p = stack.peek();
		SmoothCamera camera = (SmoothCamera) SceneManagerSingleton
				.getInstance().getCamera();
		camera.setCenterDirect(p.getX(), p.getY());
		this.path = stack;
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

	public SpriteEndPoint getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(SpriteEndPoint endPoint) {
		this.endPoint = endPoint;
	}

	public Stack<Point> getPath() {
		return path;
	}

	public void setPath(Stack<Point> path) {
		this.path = path;
	}

	public boolean isMustUpdate() {
		return mustUpdate;
	}

	public void setMustUpdate(boolean mustUpdate) {
		this.mustUpdate = mustUpdate;
	}

}
