package com.mgl.drop.game.sprites;

import org.andengine.audio.sound.Sound;
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

public class SpritePoopRocket2 extends MyAnimateSprite {

	private float speed = 100;
	private float speedFlying = 500;
	private float explosionTime = 2;
	private float explosionCont = 0;
	private float totalDistance = 0;
	private float maxDistance = 400;
	
	private Sound flyingSound = null;

	public SpritePoopRocket2(float pX, float pY, ITextureRegion pTextureRegion,
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
			fps = new long[] { 100, 100, 100 };
			stateAnimate.put(State.FALLING_DOWN, new MyAnimateProperty(0, 3,
					fps));
			stateAnimate.put(State.POOP_FLYING, new MyAnimateProperty(3, 3,
					fps));
			stateAnimate.put(State.POOP_EXPLODING, new MyAnimateProperty(6, 3,
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
				
			} else if (status.equals(StatusType.POOP_FLYING)) {
				
				updateFlying(dTime,level);
				
			} else if(status.equals(StatusType.POOP_EXPLODING)) {
				
				if(!isAnimationRunning()){
					status = StatusType.FINISHED;
					
					level.getScene().detachChild(this);
					level.removeEntity(this);
					
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void updateFlying(float dTime, LevelController level) {
		try {
			
			float distance = dTime * speedFlying;
			
			for (MySpriteGeneral sprite : level.getSpriteList()) {
				if (sprite.getSpriteType().equals(SpriteType.OBJETIVE)) {

					if (sprite instanceof MySprite) {
						MySprite spr = (MySprite) sprite;
						if (this.collidesWith(spr)) {
							spr.setStatus(StatusType.POOPED);
							status = StatusType.POOP_EXPLODING;
							changeAnimateState(State.POOP_EXPLODING, false);
							Sound s = SoundSingleton.getInstance().getSound("explosion.mp3");
							if(s!=null){
								s.play();
							}
							return;
						}
					}
					else if (sprite instanceof MyAnimateSprite){
						MyAnimateSprite spr = (MyAnimateSprite) sprite;
						if(this.collidesWith(spr)){
							spr.setStatus(StatusType.POOPED);
							status = StatusType.POOP_EXPLODING;
							changeAnimateState(State.POOP_EXPLODING, false);
							Sound s = SoundSingleton.getInstance().getSound("explosion.mp3");
							if(s!=null){
								s.play();
							}
							return;
						}
					}
				}
			}
			//Log.d("rocjket", "abc pos "+distance+" x"+this.getX()+" y"+this.getY());
			
			this.setPosition(this.getX() + distance, (this.getY()));
			totalDistance = totalDistance+distance;
			if(totalDistance>=maxDistance){
				status = StatusType.POOP_EXPLODING;
				changeAnimateState(State.POOP_EXPLODING, false);
				Sound s = SoundSingleton.getInstance().getSound("explosion.mp3");
				if(s!=null){
					s.play();
				}
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
								status = StatusType.POOP_EXPLODING;
								changeAnimateState(State.POOP_EXPLODING, false);
								Sound s = SoundSingleton.getInstance().getSound("explosion.mp3");
								if(s!=null){
									s.play();
								}
								this.setZIndex(-1);
								this.setSize(this.getWidth() + 10,
										this.getHeight());
								this.setPosition(this.getX() - 10,
										(this.getY()));
								return;
							} else {
								if (!spr.getStatus().equals(StatusType.POOPED)) {
									changeAnimateState(State.POOP_EXPLODING, false);
									Sound s = SoundSingleton.getInstance().getSound("explosion.mp3");
									if(s!=null){
										s.play();
									}
									spr.setStatus(StatusType.POOPED);
								}
							}
						}
					} else if (sprite instanceof MyAnimateSprite) {
						MyAnimateSprite spr = (MyAnimateSprite) sprite;

						if (this.collidesWith(spr)) {
							if (sprite.getSpriteType().equals(
									SpriteType.TERRAIN)) {
								status = StatusType.POOP_EXPLODING;
								changeAnimateState(State.POOP_EXPLODING, false);
								Sound s = SoundSingleton.getInstance().getSound("explosion.mp3");
								if(s!=null){
									s.play();
								}
								this.setZIndex(-1);
								this.setSize(this.getWidth() + 10,
										this.getHeight());
								this.setPosition(this.getX() - 10,
										(this.getY()));
								return;
							} else {
								if (!spr.getStatus().equals(StatusType.POOPED)) {
									changeAnimateState(State.POOP_EXPLODING, false);
									Sound s = SoundSingleton.getInstance().getSound("explosion.mp3");
									if(s!=null){
										s.play();
									}
									spr.setStatus(StatusType.POOPED);
								}
							}
						}
					}
				}
			}

			this.setPosition(this.getX() , (this.getY() + distance));

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
			setStatus(StatusType.POOP_FLYING);
			changeAnimateState(State.POOP_FLYING, true);
			Sound s = SoundSingleton.getInstance().getSound("flyingRocket.mp3");
			if(s!=null){
				s.play();
			}
			
			
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



