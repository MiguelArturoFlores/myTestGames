package com.mgl.drop.game.hud;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ButtonInHouse;
import com.mgl.drop.texture.TextureSingleton;

public class InHouseAddHUD extends HUD{
	
	public InHouseAddHUD(){
		try {
			
			TextureSingleton texture = TextureSingleton.getInstance();
			Camera camera = SceneManagerSingleton.getInstance().getCamera();

			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setSize(camera.getWidth(), camera.getHeight());

			this.attachChild(background);

			ButtonInHouse background2 = new ButtonInHouse(0, 0,
					texture.getTextureByName("inHouseIntersitial.png"),
					texture.getVertexBufferObjectManager());
			
			
			this.attachChild(background2);
			
			
			
			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0,
					texture.getTextureByName("closeHud.png"),
					texture.getVertexBufferObjectManager(), this);
			remove.setSize(70, 70);
			remove.setPosition(camera.getWidth() - remove.getWidth() - 20, 20);

			this.attachChild(remove);
			

			this.registerTouchArea(remove);
			
			this.registerTouchArea(background2);
			
			this.registerTouchArea(background);

		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}

}
