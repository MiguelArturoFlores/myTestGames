package com.mgl.drop.game.sprites;

import org.andengine.audio.sound.Sound;
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
import com.mgl.drop.game.sprites.thread.ThreadSoundOnce;
import com.mgl.drop.game.sprites.thread.ThreadSoundUntil;

public class SpritePoopBomb extends MyAnimateSprite {

	private float speed = 250;
	private float explosionTime = 2;
	private float explosionCont = 0;

	public SpritePoopBomb(float pX, float pY, ITextureRegion pTextureRegion,
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
			fps = new long[] { 100, 100 };
			stateAnimate.put(State.FALLING_DOWN, new MyAnimateProperty(0, 2,
					new long[] { 100, 100 }));
			stateAnimate.put(State.POOP_WAITING_EXPLODE, new MyAnimateProperty(5, 5,
					new long[] { 400, 400, 400, 400, 400 }));
			stateAnimate.put(State.POOP_EXPLODING, new MyAnimateProperty(10, 3,
					new long[] { 100, 100, 100 }));

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
				this.setZIndex(3);
				updateExploding(dTime,level);
				
				
			} else if(status.equals(StatusType.POOP_EXPLODING)) {
				this.setZIndex(5);
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

	private void updateExploding(float dTime, LevelController level) {
		try {
			
			explosionCont = explosionCont +dTime;
			if(explosionCont<explosionTime){
				
				return; 
			}
			
			Sound s = SoundSingleton.getInstance().getSound("explosion.mp3");
			if(s!=null){
				s.play();
			}
			explosionCont = 0;
			status = StatusType.POOP_EXPLODING;
			changeAnimateState(State.POOP_EXPLODING, false);
			this.setSize(this.getWidth()+50,this.getHeight()+50);
			this.setPosition(this.getX()-this.getWidth()/2,this.getY()-this.getHeight()/2);
			
			for (MySpriteGeneral sprite : level.getSpriteList()) {
				if (sprite.getSpriteType().equals(SpriteType.OBJETIVE)) {

					if (sprite instanceof MySprite) {
						MySprite spr = (MySprite) sprite;
						if (this.collidesWith(spr)) {
							spr.setStatus(StatusType.POOPED);
						}
					}
					else if (sprite instanceof MyAnimateSprite){
						MyAnimateSprite spr = (MyAnimateSprite) sprite;
						if(this.collidesWith(spr)){
							spr.setStatus(StatusType.POOPED);
						}
					}
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
						) {

					if (sprite instanceof MySprite) {
						MySprite spr = (MySprite) sprite;
						if (this.collidesWith(spr)) {
							if (sprite.getSpriteType().equals(
									SpriteType.TERRAIN)) {
								status = StatusType.POOP_GROUND;
								changeAnimateState(State.POOP_WAITING_EXPLODE, true);
								addToTerrain(sprite);
								ThreadSoundUntil thread = new ThreadSoundUntil("tictac.mp3", explosionTime);
								thread.start();
								
								this.setSize(this.getWidth() + 10,
										this.getHeight());
								this.setPosition(this.getX(),
										(this.getY()+10));
								return;
							} else {
								if (!spr.getStatus().equals(StatusType.POOPED)) {
									level.getScene().detachChild(this);
									level.removeEntity(this);
									spr.setStatus(StatusType.POOPED);
								}
							}
						}
					} else if (sprite instanceof MyAnimateSprite) {
						MyAnimateSprite spr = (MyAnimateSprite) sprite;

						if (this.collidesWith(spr)) {
							if (sprite.getSpriteType().equals(
									SpriteType.TERRAIN)) {
								status = StatusType.POOP_GROUND;
								changeAnimateState(State.POOP_WAITING_EXPLODE, true);
								addToTerrain(sprite);
								ThreadSoundUntil thread = new ThreadSoundUntil("tictac.mp3", explosionTime);
								thread.start();
								
								this.setSize(this.getWidth() + 10,
										this.getHeight());
								this.setPosition(this.getX(),
										(this.getY()+10));
								return;
							} else {
								if (!spr.getStatus().equals(StatusType.POOPED)) {
									level.getScene().detachChild(this);
									level.removeEntity(this);
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

		return SpriteType.POOP;
	}

}
