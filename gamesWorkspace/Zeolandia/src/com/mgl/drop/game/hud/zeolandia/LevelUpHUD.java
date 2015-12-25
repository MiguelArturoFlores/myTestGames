package com.mgl.drop.game.hud.zeolandia;

import org.andengine.entity.text.Text;

import com.mgl.base.TextFactory;
import com.mgl.base.userinfo.InventarySingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.hud.MyHud;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.sprites.SpriteBackground;

public class LevelUpHUD extends MyHud{

	
	public LevelUpHUD(int level){
		try {
			


			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("levelUpBackground.png"),
					texture.getVertexBufferObjectManager());
			background.setSize(MainDropActivity.CAMERA_WIDTH,
					MainDropActivity.CAMERA_HEIGHT);
			// background.setAlpha(0.6f);
			this.attachChild(background);

			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0,
					texture.getTextureByName("closeHud.png"),
					texture.getVertexBufferObjectManager(), this);
			remove.setPosition(10,
					MainDropActivity.CAMERA_HEIGHT - remove.getHeight() - 5);
			this.attachChild(remove);
			this.registerTouchArea(remove);

			this.registerTouchArea(background);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCloseAction() {
		
	}

}
