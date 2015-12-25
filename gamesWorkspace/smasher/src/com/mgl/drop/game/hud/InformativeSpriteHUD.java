package com.mgl.drop.game.hud;

import org.andengine.entity.text.Text;

import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ButtonRate;
import com.mgl.drop.texture.TextureSingleton;

public class InformativeSpriteHUD extends MyHud {

	public InformativeSpriteHUD(MySprite sprite, String textToShow) {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();

			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setSize(MainDropActivity.CAMERA_WIDTH,
					MainDropActivity.CAMERA_HEIGHT);
			background.setAlpha(0.6f);
			this.attachChild(background);
			
			SpriteBackground backAux = new SpriteBackground(0, 0,
					texture.getTextureByName("verticalBackgroundGeneral.png"),
					texture.getVertexBufferObjectManager());
			backAux.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);

			this.attachChild(backAux);

			Text text = ObjectFactorySingleton.getInstance().createText(
					textToShow, TextureSingleton.getInstance().getmFont1());
			text.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - text.getWidth() / 2,
					250);

			sprite.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - sprite.getWidth() / 2,
					text.getY() + text.getHeight() + 20);
			this.registerTouchArea(sprite);
			this.attachChild(sprite);

			this.attachChild(text);

			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0,
					texture.getTextureByName("closeHud.png"),
					texture.getVertexBufferObjectManager(), this);
			remove.setSize(70, 70);
			remove.setPosition(
					MainDropActivity.CAMERA_WIDTH - remove.getWidth() - 20, 20);
			this.attachChild(remove);
			this.registerTouchArea(remove);

			this.registerTouchArea(background);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onCloseAction() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
