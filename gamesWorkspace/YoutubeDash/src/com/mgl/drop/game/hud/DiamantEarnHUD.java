package com.mgl.drop.game.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.youtuberdash.R;

public class DiamantEarnHUD extends HUD{
	
	
	public DiamantEarnHUD(String quantity){
		try {
			
			TextureSingleton texture = TextureSingleton.getInstance();

			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setSize(MainDropActivity.CAMERA_WIDTH,
					MainDropActivity.CAMERA_HEIGHT);

			this.attachChild(background);

			Text text = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.EARN_FREE_MONEY), TextureSingleton.getInstance().getmFont1());
			text.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - text.getWidth() / 2,
					20);

			SpriteBackground diamant = new SpriteBackground(0, 0,
					texture.getTextureByName("moreMoney.png"),
					texture.getVertexBufferObjectManager());
			diamant.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - diamant.getWidth() / 2,
					MainDropActivity.CAMERA_HEIGHT/2- 5);

			Text text1 = ObjectFactorySingleton.getInstance().createText(
					quantity, TextureSingleton.getInstance().getmFont1());
			text1.setPosition(
					diamant.getX() + diamant.getWidth() - 20,
					diamant.getY() + diamant.getHeight() - 50
							- text1.getHeight());

		

			this.attachChild(diamant);
			this.attachChild(text1);
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

}
