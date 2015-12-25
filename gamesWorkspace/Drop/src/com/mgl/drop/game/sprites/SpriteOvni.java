package com.mgl.drop.game.sprites;

import java.util.HashMap;

import org.andengine.audio.sound.Sound;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.StatusType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.thread.ThreadFatSound;
import com.mgl.drop.game.sprites.thread.ThreadOvniSound;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class SpriteOvni extends MyAnimateSprite {

	private float speed;
	private float maxX;
	private float minX;
	private float distanceVisibility;
	
	private SpriteTerrain spr; 

	public SpriteOvni(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Long minX,
			Long maxX, Long speed, Long distanceVisibility,
			LevelController level, float width, float height) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager, level);
		try {

			this.minX = minX;
			this.maxX = maxX;
			this.speed = speed;
			this.setVisible(false);
			this.distanceVisibility = distanceVisibility;

			status = StatusType.NORMAL;
			type = SpriteTypeConstant.OVNI;
			
			this.setWidth(width);
			this.setHeight(height);

			spr = new SpriteTerrain(+5, 0, TextureSingleton
					.getInstance().getTextureByName("black.jpg"),
					TextureSingleton.getInstance()
							.getVertexBufferObjectManager());
			// spr.setPosition(this.getX(),this.getY());

			spr.setWidth(this.getWidth() - 10);
			spr.setHeight((float) (this.getHeight() * 0.20) + 10);
			// spr.setAlpha(0.5f);
			spr.setVisible(false);

			// level.getScene().attachChild(spr);
			this.attachChild(spr);
			level.addSpriteToUpdate(spr);
			
			ThreadOvniSound thread = new ThreadOvniSound(this);
			thread.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	
	@Override
	public void updateAnimated(float dTime, LevelController level) {

		try {

			if (status.equals(StatusType.NORMAL)) {
				updateNormal(dTime, level);
			} else if (status.equals(StatusType.DESTROY_OVNI)) {
				if (!isAnimationRunning()) {
					status = StatusType.FINISHED;
					this.setZIndex(5);
					spr.detachSelf();
					level.removeEntity(spr);
					this.detachSelf();
					level.removeEntity(this);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateNormal(float dTime, LevelController level) {
		try {

			verifyVisibility(level);

			float distanceTo = dTime * speed;
			distanceTo = dTime * speed;
			this.setPosition(this.getX() + distanceTo, this.getY());

			if (this.getX() >= maxX) {
				speed = speed * -1;

				distanceTo = dTime * speed;
				this.setPosition(maxX, this.getY());

			} else if (this.getX() <= minX) {
				speed = speed * -1;
				distanceTo = dTime * speed;

				this.setPosition(minX, this.getY());
			}

			verifyOvniCollision(level);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void verifyVisibility(LevelController level) {
		try {

			if (!this.isVisible()) {

				float distaceB = Point.distanceBetween(
						new Point(getX(), getY()), new Point(level.getPlayer()
								.getX(), level.getPlayer().getY()));
				if (distaceB <= distanceVisibility) {
					this.setVisible(true);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void verifyOvniCollision(LevelController level) {
		try {

			for (MySpriteGeneral spr : level.getSpriteList()) {
				if (spr.getSpriteType().equals(SpriteType.POOP)) {

					if (spr instanceof MySprite) {

						MySprite sprAux = (MySprite) spr;

						if (!this.collidesWith(sprAux)) {
							return;
						}

						level.getScene().detachChild(sprAux);
						level.removeEntity(sprAux);

					} else if (spr instanceof MyAnimateSprite) {

						MyAnimateSprite sprAux = (MyAnimateSprite) spr;

						if (!this.collidesWith(sprAux)) {
							return;
						}

						level.getScene().detachChild(sprAux);
						level.removeEntity(sprAux);

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.DECORATIVE;
	}

	@Override
	public void initAnimationParams() {
		try {

			currentState = State.WAITING_RIGHT;
			//currentState = State.POOP_BEGIN;
			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 200, 200, 200 };
			stateAnimate.put(State.WAITING_RIGHT, new MyAnimateProperty(0, 6,
					new long[] { 200, 200, 200, 200, 200, 200 }));
			stateAnimate.put(State.POOP_BEGIN, new MyAnimateProperty(6, 2,
					new long[] { 200, 200 }));

			changeAnimateState(State.WAITING_RIGHT, true);
			//changeAnimateState(State.POOP_BEGIN, true);
			
			anime(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
