package com.mgl.drop.game.sprites.button;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GamePurchaseConstant;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class ButtonRestartWave extends Sprite {

	private Scene scene;
	private LevelController level;

	public ButtonRestartWave(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level, String text) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {

			this.scene = scene;
			this.level = level;
			
			TextureSingleton texture = TextureSingleton.getInstance();
			
			Text textMore = ObjectFactorySingleton.getInstance().createText(text,
					texture.getmFont1());
			this.setWidth(textMore.getWidth() + 70);
			this.setHeight(textMore.getHeight() + 40);

			textMore.setPosition(this.getWidth() / 2 - textMore.getWidth() / 2,
					this.getHeight() / 2 - textMore.getHeight() / 2);

			this.attachChild(textMore);

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

				SoundSingleton.getInstance().playSound("buttonPress.mp3");
				UserInfoSingleton user = UserInfoSingleton.getInstance();
				if (user.getMoney() - GamePurchaseConstant.RESTART_WAVE_MONEY >= 0) {
					level.reloadWave();
					user.consumeMoney(user.getMoney()-GamePurchaseConstant.RESTART_WAVE_MONEY);
					HUDManagerSingleton.getInstance().removeAndReplaceHud();
				}

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
