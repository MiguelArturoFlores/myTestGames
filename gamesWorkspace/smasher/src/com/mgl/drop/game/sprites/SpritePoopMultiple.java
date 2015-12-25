package com.mgl.drop.game.sprites;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class SpritePoopMultiple extends MyAnimateSprite {

	
	private float speed = 200;
	private float distanceToMove = 100;

	public SpritePoopMultiple(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		try {
			status = StatusType.FALLING;
			SpriteInvisibleTouch spr = new SpriteInvisibleTouch((0f),(0f),200f,200f,TextureSingleton.getInstance().getTextureByName("black.jpg"),pVertexBufferObjectManager,this);
			
			
			this.attachChild((spr));
			
			spr.setWidth(100);
			spr.setHeight(100);
			spr.setPosition(-spr.getWidth()/2+10,-spr.getHeight()/2+10);
			//spr.setAlpha(0.2f);
			spr.setVisible(false);
			level.getScene().registerTouchArea(spr);

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
			stateAnimate.put(State.FALLING_DOWN, new MyAnimateProperty(0, 2,
					fps));
			stateAnimate.put(State.POOP_HIT_FLOOR, new MyAnimateProperty(2, 2,
					fps));
			stateAnimate.put(State.POOP_EXPLODING, new MyAnimateProperty(2, 2,
					fps));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			if (status.equals(StatusType.FALLING)) {
				
				updateFalling(dTime, level);
				
			}else if (status.equals(StatusType.POOP_EXPLODING)) {
				if(!isAnimationRunning()){
					status = StatusType.FINISHED;
					level.getScene().detachChild(this);
					level.removeEntity(this);
					
				}
				
			}
			else if (status.equals(StatusType.POOP_GROUND)) {

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
								
								addToTerrain(sprite);
								
								this.setZIndex(-1);
								this.setSize(this.getWidth() + 10,
										this.getHeight());
								this.setPosition(this.getX() ,
										(this.getY())+20);
								return;
							} else {
								if (!spr.getStatus().equals(StatusType.POOPED)) {
									changeAnimateState(State.POOP_EXPLODING, false);
									spr.poop(this,level);
									//spr.setStatus(StatusType.POOPED);
									level.getScene().detachChild(this);
									level.removeEntity(this);
								}
							}
						}
					} else if (sprite instanceof MyAnimateSprite) {
						MyAnimateSprite spr = (MyAnimateSprite) sprite;

						if (this.collidesWith(spr)) {
							if (sprite.getSpriteType().equals(
									SpriteType.TERRAIN)) {
								status = StatusType.POOP_GROUND;
								addToTerrain(sprite);
								changeAnimateState(State.POOP_EXPLODING, false);
								this.setZIndex(-1);
								this.setSize(this.getWidth() + 10,
										this.getHeight());
								this.setPosition(this.getX() ,
										(this.getY()+15));
								return;
							} else {
								if (!spr.getStatus().equals(StatusType.POOPED)) {
									changeAnimateState(State.POOP_EXPLODING, false);
									spr.poop(this,level);
									level.getScene().detachChild(this);
									level.removeEntity(this);
								}
							}
						}
					}
				}
			}

			this.setPosition(this.getX() , this.getY() + distance);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.POOP;
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			
			if(!status.equals( StatusType.FALLING)){
				return false;
			}
			
			status = (StatusType.POOP_EXPLODING);
			changeAnimateState(State.POOP_EXPLODING, false);
			
			level.getScene().unregisterTouchArea(this);
			SpritePoop poopBasic = new SpritePoop(0, 0, TextureSingleton.getInstance().getTextureAnimateByName("poopBasic.png"), TextureSingleton.getInstance().getVertexBufferObjectManager(), level);
			poopBasic.setSize(20, 20);
			poopBasic.setPosition(this);
			poopBasic.setDistanceToMove(distanceToMove);
			
			
			level.getScene().attachChild(poopBasic);
			level.getScene().registerTouchArea(poopBasic);
			level.addSpriteToUpdate(poopBasic);
			
			SoundSingleton.getInstance().playSound("split.mp3");
			
			poopBasic = new SpritePoop(0, 0, TextureSingleton.getInstance().getTextureAnimateByName("poopBasic.png"), TextureSingleton.getInstance().getVertexBufferObjectManager(), level);
			poopBasic.setSize(20, 20);
			poopBasic.setPosition(this);
			poopBasic.setDistanceToMove(distanceToMove*-1);
			
			level.getScene().attachChild(poopBasic);
			level.getScene().registerTouchArea(poopBasic);
			level.addSpriteToUpdate(poopBasic);
			
			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:
			
			
			
			break;
		default:
			
			break;
			
		}
		return false;
	}
	
}