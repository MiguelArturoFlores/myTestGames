package com.mgl.drop.game.sprites.button.arunner;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GamePurchaseConstant;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;

public class ButtonIce extends MySprite {

	private boolean freeze = false;
	private float timeToReload = 3.5f;
	private float contTime = timeToReload;
	private SpriteBackground reloadBar;
	private float angle = 0;
	private float maxAngle = 180;

	public ButtonIce(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {

			contTime = timeToReload;
			freeze = false;
			
			reloadBar = new SpriteBackground(0, 0,
					texture.getTextureByName("reloadBar.png"),
					texture.getVertexBufferObjectManager());

			reloadBar.setSize(85, 85);
			reloadBar.setPosition(this.getWidth() / 2, 0);
			reloadBar.setZIndex(-1);

			this.attachChild(reloadBar);

			this.sortChildren();
			
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
				if (contTime >= timeToReload) {
					freeze = true;
					contTime = 0;
				}
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
			contTime = contTime + dTime;
			if (contTime < timeToReload) {
				this.setAlpha(0.15f);
				
				angle =   maxAngle * contTime/timeToReload;
				angle = maxAngle - angle;
				reloadBar.setRotation(angle);
				
			} else {
				this.setAlpha(1f);
			}
			if (freeze) {
				level.getPlayer().shotFreeze();
				freeze = false;
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