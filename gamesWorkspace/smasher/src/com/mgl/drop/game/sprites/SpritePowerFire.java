package com.mgl.drop.game.sprites;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class SpritePowerFire extends MySprite {

	private Text quantity;

	public SpritePowerFire(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {

			UserInfoSingleton userInfo = UserInfoSingleton.getInstance();

			quantity = ObjectFactorySingleton.getInstance().createText(
					"x" + userInfo.getPowerC(),
					TextureSingleton.getInstance().getmFont1());
			this.attachChild(quantity);
			this.setPosition(3, 35);
			this.level = level;
			
			this.setIgnoreUpdate(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.POWER;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			

			if (UserInfoSingleton.getInstance().getPowerC() <= 0) {

				// show store to buy more powers and pause the game
				SoundSingleton.getInstance().playWithoutPower();

				return true;
			}

			TextureSingleton texture = TextureSingleton.getInstance();
			SpriteFire fire = new SpriteFire(0, 0,
					texture.getTextureAnimateByName("fire.png"),
					texture.getVertexBufferObjectManager(), level);

			fire.setWidth(650);
			fire.setHeight(fire.getHeight()*1.2f);;
			fire.setPosition(-90, MainDropActivity.CAMERA_HEIGHT / 2 - 105);
			fire.addBackground();
			fire.setCollisionShape();
			
			fire.registerEntityModifier(new MoveModifier(1f, fire.getWidth()*-1, fire.getX(),
					fire.getY(), fire.getY())); 
			
			level.getScene().attachChild(fire);
			level.addSpriteToUpdate(fire);

			fire.setZIndex(ZIndexGame.FIRE);

			quantity = MainDropActivity.changeText((UserInfoSingleton
					.getInstance().getPowerC() - 1) + "", quantity,
					TextureSingleton.getInstance().getmFont1());
			this.attachChild(quantity);
			UserInfoSingleton.getInstance().consumePowerC();
			
			SoundSingleton.getInstance().playFireSound();

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

}
