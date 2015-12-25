package com.mgl.drop.game.scene;

import java.util.Date;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.graphics.Camera;
import android.util.Log;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class SceneMoveListener2 implements IOnSceneTouchListener {

	private Point beginPoint;
	private Point endPoint;

	private static final int NORMAL_MOVE = 0;
	private static final int PRESSED_MOVE = 1;

	private int state = NORMAL_MOVE;

	private SceneManagerSingleton sceneManage = SceneManagerSingleton
			.getInstance();

	private float xStart = 0, yStart = 0, xEnd = 0, yEnd = 0;
	int minDistance = 75;

	private Sprite spriteToFollow;
	private int CAMERA_MAX_WIDTH;
	private int CAMERA_MAX_HEIGHT;

	private float speedX;
	private float speedY;

	private Long dateBegin = null;
	private Long dateEnd = null;

	private boolean isMovingPressed = false;
	private MoveModifier modifier;

	public SceneMoveListener2(int CAMERA_MAX_WIDTH, int CAMERA_MAX_HEIGHT) {
		try {
			this.CAMERA_MAX_HEIGHT = CAMERA_MAX_HEIGHT;
			this.CAMERA_MAX_WIDTH = CAMERA_MAX_WIDTH;

			dateBegin = 0L;
			dateEnd = 0L;

			spriteToFollow = new Sprite(0, 0, TextureSingleton.getInstance()
					.getTextureByName("buttonPlay.jpg"), TextureSingleton
					.getInstance().getVertexBufferObjectManager());

			spriteToFollow.setPosition(sceneManage.getCamera().getCenterX(),
					sceneManage.getCamera().getCenterY());
			spriteToFollow.setSize(15, 15);
			spriteToFollow.setVisible(false);

			SmoothCamera camera = (SmoothCamera) sceneManage.getCamera();

			speedX = camera.getMaxVelocityX() + camera.getMaxVelocityX()
					* 0.75f;
			speedY = camera.getMaxVelocityY() + camera.getMaxVelocityY()
					* 0.75f;
			camera.setMaxVelocity(speedX, speedY);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene,
			final TouchEvent pSceneTouchEvent) {

		switch (pSceneTouchEvent.getAction()) {

		case TouchEvent.ACTION_DOWN:

			state = NORMAL_MOVE;

			beginPoint = new Point(pSceneTouchEvent.getX(),
					pSceneTouchEvent.getY());

			dateBegin = new Date().getTime();

			isMovingPressed = false;

			break;
		case TouchEvent.ACTION_MOVE:

			endPoint = new Point(pSceneTouchEvent.getX(),
					pSceneTouchEvent.getY());

			switch (state) {
			case NORMAL_MOVE:

				dateEnd = new Date().getTime();

				if (dateEnd - dateBegin > 500) {

					state = PRESSED_MOVE;
					smoothMovePressed();

					// dateBegin = dateEnd;
				}

				break;
			case PRESSED_MOVE:

				smoothMovePressed();

				break;
			default:

				break;

			}

			break;
		case TouchEvent.ACTION_UP:

			switch (state) {
			case NORMAL_MOVE:

				endPoint = new Point(pSceneTouchEvent.getX(),
						pSceneTouchEvent.getY());

				dateBegin = dateEnd = new Date().getTime();

				smoothMove();

				break;
			case PRESSED_MOVE:

				break;
			default:

				break;

			}

			break;
		}

		return false;
	}

	private void smoothMovePressed() {
		try {

			org.andengine.engine.camera.Camera cam = SceneManagerSingleton
					.getInstance().getCamera();
			Point center = new Point(cam.getCenterX(), cam.getCenterY());
			sceneManage.getCamera().setChaseEntity(null);

			int minDistanceX = 20;
			int minDistanceY = 20;

			float x = cam.getCenterX();
			float y = cam.getCenterY();

			if (endPoint.getX() < center.getX()) {
				// mover a la derecha
				x = (cam.getCenterX() + minDistanceX);
			} else {
				// mover a la izquierda
				x = (cam.getCenterX() - minDistanceX);
			}

			if (endPoint.getY() < center.getY()) {
				// mover arriba
				y = cam.getCenterY() + minDistanceY;
			} else {
				// mover abajo
				y = cam.getCenterY() - minDistanceY;
			}
			cam.setCenter(x, y);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void smoothMove() {
		try {

			sceneManage.getEngine().getScene().detachChild(spriteToFollow);
			sceneManage.getEngine().getScene().attachChild(spriteToFollow);

			org.andengine.engine.camera.Camera cam = SceneManagerSingleton
					.getInstance().getCamera();
			spriteToFollow.setPosition(cam.getCenterX(), cam.getCenterY());

			// spriteToFollow.setPosition(xEnd, yEnd);
			sceneManage.getCamera().setChaseEntity(spriteToFollow);

			if (beginPoint == null || endPoint == null) {
				return;
			}

			float distance = Point.distanceBetween(beginPoint, endPoint);
			if (distance > 350) {
				distance = 600;
			} else if (distance > 200) {
				distance = 450;
			} else if (distance > 100) {
				distance = 300;
			}
			float angle = Point.angleBetween(beginPoint, endPoint);
			float x = (float) (Math.cos(angle) * distance);
			float y = (float) (Math.sin(angle) * distance);
			if (x < 0) {
				x = x * -1;
			}
			if (y < 0) {
				y = y * -1;
			}

			if (beginPoint.getX() >= endPoint.getX()) {
				// mover camara derecha
				endPoint.setX(spriteToFollow.getX() + x);
			} else {
				// mover izqui
				endPoint.setX(spriteToFollow.getX() - x);
			}
			if (beginPoint.getY() >= endPoint.getY()) {
				// mover camara abajo
				endPoint.setY(spriteToFollow.getY() + y);
			} else {
				// mover arriba
				endPoint.setY(spriteToFollow.getY() - y);
			}

			Log.d("distance ", "distance" + distance);

			// beginPoint = calculateLimits(beginPoint);

			endPoint = calculateLimits(endPoint);

			modifier = new MoveModifier((float) 0.4, spriteToFollow.getX(),
					endPoint.getX(), spriteToFollow.getY(), endPoint.getY());

			spriteToFollow.registerEntityModifier(modifier);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Point calculateLimits(Point point) {
		try {

			xStart = point.getX();
			yStart = point.getY();

			if (xStart < MainDropActivity.CAMERA_WIDTH / 2) {
				xStart = MainDropActivity.CAMERA_WIDTH / 2;
			}
			if (xStart > CAMERA_MAX_WIDTH - MainDropActivity.CAMERA_WIDTH / 2) {
				xStart = CAMERA_MAX_WIDTH - MainDropActivity.CAMERA_WIDTH / 2;
				// spriteToFollow.setX(spriteToFollow.getX());
			}
			if (yStart < MainDropActivity.CAMERA_HEIGHT / 2) {
				yStart = MainDropActivity.CAMERA_HEIGHT / 2;
			}
			if (yStart > CAMERA_MAX_HEIGHT - MainDropActivity.CAMERA_HEIGHT / 2) {
				yStart = CAMERA_MAX_HEIGHT - MainDropActivity.CAMERA_HEIGHT / 2;
			}

			return point;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
