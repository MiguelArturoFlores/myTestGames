package com.mgl.drop.game.sprites;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpriteBossHit extends MyAnimateSprite {

	private float damage;
	private MyAnimateSprite sprite;
	private SpriteLife life;
	private float contTime = 0;
	private float timeToChange = 0.2f;
	private boolean isGreat = true;

	public SpriteBossHit(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level, float damage, MyAnimateSprite sprite,
			SpriteLife life) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		try {

			this.damage = damage;
			this.sprite = sprite;
			this.life = life;

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

			changeAnimateState(State.ROTATING, true);
			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83 };
			stateAnimate.put(State.ROTATING, new MyAnimateProperty(0, 2, fps));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			contTime = contTime + dTime;
			if (contTime < timeToChange) {
				return;
			}

			contTime =0;
			
			if (isGreat) {
				this.setSize(65	, 65);
			} else {
				this.setSize(70,70);
			}
			isGreat = !isGreat;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void generateRandomPositionToHit() {
		try {
			
			float x = (float) ((Math.random()*10000000)%(sprite.getWidth()-150))+50f;
			float y = (float) ((Math.random()*10000000)%(sprite.getHeight()-125))+75f;
			
			this.setPosition(x,y);
			this.blink();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				if (life == null) {
					return true;
				}

				life.recieveHit(damage);
				generateRandomPositionToHit();
				sprite.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);

				break;
			default:

				break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void blink() {
		try {

			setSize(100, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
