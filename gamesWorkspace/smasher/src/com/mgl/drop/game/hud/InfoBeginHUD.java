package com.mgl.drop.game.hud;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;

import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.texture.TextureSingleton;

public class InfoBeginHUD extends HUD {


	public InfoBeginHUD (LevelController level){
		
		try {
			
			TextureSingleton texture = TextureSingleton.getInstance();
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			
			Sprite background = new Sprite(0,0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setAlpha(0.7f);
			background.setSize(camera.getWidth(), camera.getHeight());
			this.attachChild(background);

			SpriteImgHUD img2 = new SpriteImgHUD(50, 0, texture.getTextureByName("howToShitC.png"), texture.getVertexBufferObjectManager(),this,true);
			
			
			SpriteImgHUD img1 = new SpriteImgHUD(50, 0, texture.getTextureByName("howToShitB.png"), texture.getVertexBufferObjectManager(),this,false);
			
			
			
			SpriteImgHUD img = new SpriteImgHUD(50, 0, texture.getTextureByName("howToShitA.png"), texture.getVertexBufferObjectManager(),this,false);
			
			this.attachChild(img2);
			this.attachChild(img1);
			this.attachChild(img);
			//
			
			
			//
			
			this.registerTouchArea(img);
			this.registerTouchArea(img1);
			this.registerTouchArea(img2);
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
