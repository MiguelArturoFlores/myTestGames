package com.mgl.drop.game.hud;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.mgl.crappypigeon.R;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.button.ButtonShareToUnlock;
import com.mgl.drop.texture.TextureSingleton;

public class InformativeHUD extends HUD {

	
	
	public InformativeHUD(String textToShow) {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			
			Sprite background = new Sprite(0,0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			//background.setAlpha(0.4f);
			background.setSize(camera.getWidth(), camera.getHeight());

			this.attachChild(background);
			
			Text text = ObjectFactorySingleton.getInstance().createText(textToShow, TextureSingleton.getInstance().getmFont1());
		
			
			
			text.setPosition(camera.getWidth()/2 -text.getWidth()/2, camera.getHeight()/2 - text.getHeight()/2);
		
			
			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0, texture.getTextureByName("closeHud.png"), texture.getVertexBufferObjectManager(), this);
			remove.setSize(70, 70);
			remove.setPosition(camera.getWidth()-remove.getWidth()-20, 20);
			
		
			this.attachChild(remove);
			this.attachChild(text);
		
			
			this.registerTouchArea(remove);



		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
}


