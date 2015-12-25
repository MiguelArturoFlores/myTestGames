package com.mgl.drop.game.objects.button;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.PowerShopHud;
import com.mgl.drop.texture.TextureSingleton;

public class ButtonPower extends Sprite {

	private TextureSingleton texture = TextureSingleton.getInstance();
	private Scene scene;
	private LevelController level;

	public ButtonPower(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Scene scene, LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);

		this.scene = scene;
		this.level = level;

		Text textMore = ObjectFactorySingleton.getInstance().createText(
				"Buy Powers" , texture.getmFont1());
		this.setWidth(textMore.getWidth() + 150);
		this.setHeight(textMore.getHeight() + 30);

		textMore.setPosition(this.getWidth() / 2 - textMore.getWidth() / 2,
				this.getHeight() / 2 - textMore.getHeight() / 2+25);

		this.attachChild(textMore);

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			SoundSingleton.getInstance().playSound("buttonPress.mp3");
			
			HUDManagerSingleton.getInstance().removeHud();
			HUDManagerSingleton.getInstance().addHUD(new PowerShopHud(level,scene),false);
			
			break;
		}
		return true;
	}

}
