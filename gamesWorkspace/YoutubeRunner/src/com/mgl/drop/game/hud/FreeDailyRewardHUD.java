package com.mgl.drop.game.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.texture.TextureSingleton;

public class FreeDailyRewardHUD extends HUD{

	public FreeDailyRewardHUD(int freeDiamant){
		try {
			
			TextureSingleton texture = TextureSingleton.getInstance();
			Sprite background = new Sprite(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setAlpha(0.8f);
			background.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);

			this.attachChild(background);
			
			Sprite backgroundFreeDaily = new Sprite(0, 0,
					texture.getTextureByName("verticalBackgroundReward.png"),
					texture.getVertexBufferObjectManager());

			this.attachChild(backgroundFreeDaily);
			
			Sprite diamant = new Sprite(0, 0,
					texture.getTextureByName("moreMoney.png"),
					texture.getVertexBufferObjectManager());

			diamant.setPosition(MainDropActivity.CAMERA_WIDTH/2 - diamant.getWidth()/2, MainDropActivity.CAMERA_HEIGHT/2 - diamant.getHeight()/2-100);
			this.attachChild(diamant);
			
			Text text = ObjectFactorySingleton.getInstance().createText(""+freeDiamant, TextureSingleton.getInstance().getmFont2());
			text.setPosition(diamant.getX()+diamant.getWidth()-text.getWidth()-50, diamant.getY()+diamant.getHeight()-130);
			this.attachChild(text);
			

			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0,
					texture.getTextureByName("closeHud.png"),
					texture.getVertexBufferObjectManager(), this);
			remove.setSize(70, 70);
			remove.setPosition(MainDropActivity.CAMERA_WIDTH - remove.getWidth() - 20, 20);

			this.attachChild(remove);
			this.registerTouchArea(remove);
			
			this.registerTouchArea(background);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
