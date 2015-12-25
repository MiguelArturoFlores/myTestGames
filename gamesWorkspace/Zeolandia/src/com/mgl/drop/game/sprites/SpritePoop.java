package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpritePoop extends MyAnimateSprite {

	private float speed = 200;
	private float speedH = 155;
	private float distanceToMove = 0;

	public SpritePoop(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		try {
			status = StatusType.FALLING;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			if (status.equals(StatusType.FALLING)) {
				updateFalling(dTime, level);
			} else if (status.equals(StatusType.POOP_GROUND)) {
				status = StatusType.FINISHED;
				this.setZIndex(3);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateFalling(float dTime, LevelController level) {
		try {

			float distance = dTime * speed;

			if (this.getX() < 0) {
				level.getScene().detachChild(this);
				level.removeEntity(this);
			}

			for (MySpriteGeneral sprite : level.getSpriteList()) {
				if (sprite.getSpriteType().equals(SpriteType.TERRAIN)
						|| sprite.getSpriteType().equals(SpriteType.OBJETIVE)) {

					if (sprite instanceof MySprite) {
						MySprite spr = (MySprite) sprite;
						if (this.collidesWith(spr)) {
							if (sprite.getSpriteType().equals(
									SpriteType.TERRAIN)) {
								status = StatusType.POOP_GROUND;
								changeAnimateState(State.POOP_HIT_FLOOR, false);
								
								level.getScene().sortChildren();
								this.setSize(this.getWidth() + 10,
										this.getHeight());
								this.setPosition(this.getX() ,
										(this.getY())+20);
								return;
							} else {
								if (!spr.getStatus().equals(StatusType.POOPED)) {
									level.getScene().detachChild(this);
									level.removeEntity(this);
									spr.poop(this, level);
									//spr.setStatus(StatusType.POOPED);
								}
							}
						}
					} else if (sprite instanceof MyAnimateSprite) {
						MyAnimateSprite spr = (MyAnimateSprite) sprite;

						if (this.collidesWith(spr)) {
							if (sprite.getSpriteType().equals(
									SpriteType.TERRAIN)) {
								status = StatusType.POOP_GROUND;
								changeAnimateState(State.POOP_HIT_FLOOR, false);
								
								
								this.setSize(this.getWidth() ,
										this.getHeight()+12);
								this.setPosition(this.getX() ,
										(this.getY())+20);
								return;
							} else {
								if (!spr.getStatus().equals(StatusType.POOPED)) {
									level.getScene().detachChild(this);
									level.removeEntity(this);
									spr.poop(this,level);
									//spr.setStatus(StatusType.POOPED);
								}
							}
						}
					}
				}
			}

			this.setPosition(this.getX() , (this.getY() + distance));
			if(distanceToMove == 0){
				return;
			}
			distance = dTime*speedH;
			if(distanceToMove > 0 ){
				//moving right
				this.setPosition(this.getX()+distance , (this.getY()));
				distanceToMove =distanceToMove -distance; 
				if(distanceToMove<=0){
					distanceToMove = 0;
				}
			}else if(distanceToMove < 0 ){
				//moving left
				this.setPosition(this.getX() -distance, (this.getY()));
				distanceToMove =distanceToMove +distance; 
				if(distanceToMove>=0){
					distanceToMove = 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initAnimationParams() {
		try {

			fps = new long[] { 100, 100, 100, 100, 100 };
			imageNumber = 1;
			currentState = State.FALLING_DOWN;

			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initHashMap() {
		try {
			fps = new long[] { 100, 100 };
			stateAnimate.put(State.FALLING_DOWN, new MyAnimateProperty(0, 4,
					new long[] { 100, 100,100,100 }));
			stateAnimate.put(State.POOP_HIT_FLOOR, new MyAnimateProperty(4, 2,
					fps));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.POOP;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getDistanceToMove() {
		return distanceToMove;
	}

	public void setDistanceToMove(float distanceToMove) {
		this.distanceToMove = distanceToMove;
	}

}
