package com.mgl.drop.game.scene;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.texture.TextureSingleton;

public class SceneMoveListener implements IOnSceneTouchListener {

	private SceneManagerSingleton sceneManage = SceneManagerSingleton
			.getInstance();

	private float xStart = 0, yStart = 0, xEnd = 0, yEnd = 0;
	int minDistance = 75;

	private Sprite spriteToFollow;
	private int CAMERA_MAX_WIDTH;
	private int CAMERA_MAX_HEIGHT;
	
	private float speedX;
	private float speedY;

	public SceneMoveListener(int CAMERA_MAX_WIDTH, int CAMERA_MAX_HEIGHT,int as) {
		try {
			this.CAMERA_MAX_HEIGHT = CAMERA_MAX_HEIGHT;
			this.CAMERA_MAX_WIDTH = CAMERA_MAX_WIDTH;

			spriteToFollow = new Sprite(0, 0, TextureSingleton.getInstance().getTextureByName("buttonPlay.jpg"), TextureSingleton.getInstance().getVertexBufferObjectManager());
			
			spriteToFollow.setPosition(sceneManage.getCamera().getCenterX(),
					sceneManage.getCamera().getCenterY());
			spriteToFollow.setSize(5, 5);
			
			SmoothCamera camera = (SmoothCamera) sceneManage.getCamera();
			speedX = camera.getMaxVelocityX() + camera.getMaxVelocityX()*0.25f;
			speedY = camera.getMaxVelocityX() + camera.getMaxVelocityX()*0.25f;

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene,
			final TouchEvent pSceneTouchEvent) {

		
		
		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			xStart = pSceneTouchEvent.getX();
			yStart = pSceneTouchEvent.getY();

			xEnd = pSceneTouchEvent.getX();
			yEnd = pSceneTouchEvent.getY();
			// smoothMove();
			smoothMovePlus();
			
			break;
		case TouchEvent.ACTION_MOVE:

			xStart = xEnd = pSceneTouchEvent.getX();
			yStart = yEnd = pSceneTouchEvent.getY();
			// smoothMove();
			smoothMovePlus();
			
			break;
		case TouchEvent.ACTION_UP:
			// xEnd = sceneManage.getCamera().getCenterX();
			// yEnd = sceneManage.getCamera().getCenterY();

			 xEnd = pSceneTouchEvent.getX();
			 yEnd = pSceneTouchEvent.getY();
			 //TODO
			 //que no pase de la mitad;
			// smoothMove();
			//smoothMovePlus();

			break;
		}

		return false;
	}

	private void smoothMovePlus() {
		try {
			// distance = (float)
			// Math.sqrt((xStart-xEnd)*(xStart-xEnd)+(yStart-yEnd)*(yStart-yEnd));
			
			//Limitss	Limitss	Limitss	Limitss	Limitss	Limitss	Limitss	Limitss
			if (xStart < MainDropActivity.CAMERA_WIDTH / 2) {
				xStart = MainDropActivity.CAMERA_WIDTH / 2;
			}
			if (xStart > CAMERA_MAX_WIDTH-MainDropActivity.CAMERA_WIDTH / 2) {
				xStart = CAMERA_MAX_WIDTH-MainDropActivity.CAMERA_WIDTH / 2;
			}
			if (yStart < MainDropActivity.CAMERA_HEIGHT / 2) {
				yStart = MainDropActivity.CAMERA_HEIGHT / 2;
			}
			if (yStart > CAMERA_MAX_HEIGHT-MainDropActivity.CAMERA_HEIGHT / 2) {
				yStart = CAMERA_MAX_HEIGHT-MainDropActivity.CAMERA_HEIGHT / 2;
			}
			
			spriteToFollow.setX(xStart);
			spriteToFollow.setY(yStart);
			
			sceneManage.getCamera().setChaseEntity(spriteToFollow);
			
			SmoothCamera camera = (SmoothCamera) sceneManage.getCamera();
			camera.setMaxVelocity(speedX, speedY);
			
			//camera.setMaxVelocity(pMaxVelocityX, pMaxVelocityY)
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void smoothMove() {
		try {

			sceneManage.getEngine().getScene().detachChild(spriteToFollow);
			sceneManage.getEngine().getScene().attachChild(spriteToFollow);

			// spriteToFollow.setPosition(xEnd, yEnd);
			sceneManage.getCamera().setChaseEntity(spriteToFollow);

			if (xStart < MainDropActivity.CAMERA_WIDTH / 2) {
				xStart = MainDropActivity.CAMERA_WIDTH / 2;
			}
			if (xStart > CAMERA_MAX_WIDTH) {
				xStart = CAMERA_MAX_WIDTH;
			}
			if (yStart < MainDropActivity.CAMERA_HEIGHT / 2) {
				yStart = MainDropActivity.CAMERA_HEIGHT / 2;
			}
			if (yStart > CAMERA_MAX_HEIGHT) {
				yStart = CAMERA_MAX_HEIGHT;
			}

			final MoveModifier modifier = new MoveModifier((float) 0.01,
					spriteToFollow.getX(), xStart, spriteToFollow.getY(),
					yStart);

			spriteToFollow.registerEntityModifier(modifier);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
