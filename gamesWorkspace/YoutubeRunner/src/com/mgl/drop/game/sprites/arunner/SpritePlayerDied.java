package com.mgl.drop.game.sprites.arunner;

import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.util.Point;

public class SpritePlayerDied extends MyAnimateSprite {
	
	private float speed = 550;

	public SpritePlayerDied(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.PLAYER_DIED;
	}

	@Override
	public void initAnimationParams() {
		try {
			
			changeAnimateState(State.WALKIN_RIGHT, false);
			anime(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 42, 42, 42,42, 42, 42,42,42};

			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(8, 8,
					fps));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			
			//updateMovingToMid(dTime);
			
			if(!this.isAnimationRunning()){
				level.looseLevelReloadOnCheckpoint();
				level.removeEntity(this);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateMovingToMid(float dTime) {
		try {

			Camera cam = SceneManagerSingleton.getInstance().getCamera();
			float angle = Point.angleBetween(
					new Point(this.getX(), this.getY()), new Point(cam.getCenterX(), cam.getCenterY()-this.getWidth()/2));

			float distance = dTime * speed;
			float dx = distance * (float) Math.cos(angle);
			float dy = distance * (float) Math.sin(angle);

			//this.getBody().setLinearVelocity(dx, dy);
			this.setPosition(this.getX()+dx,this.getY()+dy);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
