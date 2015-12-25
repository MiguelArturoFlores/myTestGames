package com.mgl.drop.game.sprites;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;

public class SpriteDiamant extends MySprite {

	private int quantity = 1;

	private float contTime = 0;
	private float cont = 0;
	private boolean isUp = true;

	public SpriteDiamant(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, int quantity, LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,level);

		try {

			this.quantity = quantity;
			this.setZIndex(ZIndexGame.TRUNK);
			

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
	public void update(float dTime, LevelController level) {
		if (!mustUpdate) {
			
			return;
		}

		try {
			contTime += dTime;
			if (contTime > 0.05f) {
				cont++;
				if (cont > 10) {
					cont = 0;
					isUp = !isUp;
				}
				if (isUp) {
					this.setY(getY() + 1);
				} else {
					this.setY(getY() - 1);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			this.setAlpha(1);
			this.registerEntityModifier(new MoveModifier(0.5f, this.getX(),
					-100, this.getY(), -100));
			UserInfoSingleton.getInstance().increaseMoneyInGame(quantity);
			level.removeEntity(this);
			mustUpdate = false;
			SoundSingleton.getInstance().playDiamantSound();
			
			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		default:

			break;

		}
		return true;
	}

}
