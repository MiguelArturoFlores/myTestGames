package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpriteArmorCrash extends MyAnimateSprite {

	public SpriteArmorCrash(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		status = status.FALLING;
		this.setIgnoreUpdate(true);
	}

	private float speed = 1050f;
	private float xToMove;
	private float yToMove;
	private float angle;

	private float timeToDesapear = 2.2f;
	private float contTime = 0;
	private float alpha = 1;

	public void generatePositionToFall() {
		try {

			float val = (float) ((Math.random() * 100000000) % (MainDropActivity.CAMERA_WIDTH - 100));
			// this.setPosition(MainDropActivity.CAMERA_WIDTH, 0);
			this.setPosition(val, 0);
			xToMove = val;
			yToMove = MainDropActivity.CAMERA_HEIGHT / 2;

			angle = Point.angleBetween(new Point(xToMove, yToMove), new Point(
					this.getX(), this.getY()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
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
		fps = new long[] { 83, 83, 83 };
		stateAnimate.put(State.WALKIN_DOWN, new MyAnimateProperty(0, 3, fps));
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {

			try {

				contTime += dTime;
				if (contTime < timeToDesapear)
					return;

				this.setAlpha(alpha);

				alpha = alpha - 0.06f;
				if (alpha <= 0) {
					this.detachSelf();
					level.removeEntity(this);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
