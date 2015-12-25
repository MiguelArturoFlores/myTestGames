package com.mgl.drop.game.sprites;

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

public class SpritePowerTrunk  extends MySprite {

	private Text quantity;

	public SpritePowerTrunk(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {

			UserInfoSingleton userInfo = UserInfoSingleton.getInstance();

			quantity = ObjectFactorySingleton.getInstance().createText(
					"x" + userInfo.getPowerD(),
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

			
			if (UserInfoSingleton.getInstance().getPowerD() <= 0) {

				// show store to buy more powers and pause the game
				SoundSingleton.getInstance().playWithoutPower();

				return true;
			}

			TextureSingleton texture = TextureSingleton.getInstance();
			SpriteTrunk trunk = new SpriteTrunk(0, 0,
					texture.getTextureAnimateByName("trunk.png"),
					texture.getVertexBufferObjectManager(), level);

			trunk.setWidth(650);
			trunk.setPosition(-90, MainDropActivity.CAMERA_HEIGHT );
			trunk.setCollisionShape();
			level.getScene().attachChild(trunk);
			level.addSpriteToUpdate(trunk);

			
			trunk.setZIndex(ZIndexGame.TRUNK);

			quantity = MainDropActivity.changeText((UserInfoSingleton
					.getInstance().getPowerD() - 1) + "", quantity,
					TextureSingleton.getInstance().getmFont1());
			this.attachChild(quantity);
			UserInfoSingleton.getInstance().consumePowerD();
			
			SoundSingleton.getInstance().playTrunkSound();

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

}
