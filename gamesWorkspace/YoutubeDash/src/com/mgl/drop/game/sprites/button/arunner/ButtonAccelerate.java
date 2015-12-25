package com.mgl.drop.game.sprites.button.arunner;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.SpriteBackground;

public class ButtonAccelerate extends MySprite {

	private boolean accelerate = false;
	private float timeToReload = 1;
	private float contTime = timeToReload;
	//private SpriteBackground reload;
	private SpriteBackground reloadBar;
	private float angle = 0;
	private float maxAngle = 180;

	public ButtonAccelerate(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {

			timeToReload = UserInfoSingleton.getInstance().getPowerB()/2f;
			contTime = timeToReload;
			accelerate = false;

			reloadBar = new SpriteBackground(0, 0,
					texture.getTextureByName("reloadBar.png"),
					texture.getVertexBufferObjectManager());

			reloadBar.setSize(85, 85);
			reloadBar.setPosition(this.getWidth() / 2, 0);
			reloadBar.setZIndex(-1);

			this.attachChild(reloadBar);

			this.sortChildren();
			// this.getParent().attachChild(reloadBar);

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

				// if (contTime >= timeToReload) {
				accelerate = true;

				// contTime = timeToReload;
				// }

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				accelerate = false;
				level.getPlayer().desaccelerate();

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {

		try {

			if (!accelerate) {
				updateReloading(dTime, level);
			} else {
				updateAccelerating(dTime, level);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateAccelerating(float dTime, LevelController level) {
		try {

			// this.setAlpha(0.5f);
			contTime = contTime - dTime * 2;

			if (contTime < 0) {
				contTime = 0;
				accelerate = false;
				level.getPlayer().desaccelerate();
				return;
			}


			level.getPlayer().accelerate(contTime);
			
			angle =   maxAngle * contTime/timeToReload;
			
			angle = maxAngle - angle;
			
			reloadBar.setRotation(angle);			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateReloading(float dTime, LevelController level) {
		try {

			level.getPlayer().desaccelerate();
			contTime = contTime + dTime;
			if (contTime < timeToReload) {
				// this.setAlpha(0.15f);
				
//				level.getPlayer().accelerate(contTime);
				angle =   maxAngle * contTime/timeToReload;
				angle = maxAngle - angle;
				reloadBar.setRotation(angle);
				
			} else {
				contTime = timeToReload;
				this.setAlpha(1f);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void reloadBar() {
		try {

			

			reloadBar.setSize(this.getWidth()/2+5, this.getHeight()+10);
			reloadBar.setPosition(this.getWidth() / 2, this.getHeight()/2 - reloadBar.getHeight()/2);
			reloadBar.setZIndex(-1);

			reloadBar.setRotationCenter(0, reloadBar.getHeight()/2);
			this.sortChildren();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}