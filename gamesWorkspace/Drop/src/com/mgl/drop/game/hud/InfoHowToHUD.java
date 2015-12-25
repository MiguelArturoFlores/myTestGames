package com.mgl.drop.game.hud;

import java.util.ArrayList;
import java.util.Stack;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;

import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.texture.TextureSingleton;

public class InfoHowToHUD extends HUD {

	

	public InfoHowToHUD (LevelController level, ArrayList<String> listToShow){
		
		try {
			
			TextureSingleton texture = TextureSingleton.getInstance();
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			
			Sprite background = new Sprite(0,0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setAlpha(0.7f);
			background.setSize(camera.getWidth(), camera.getHeight());
			this.attachChild(background);

			SpriteImgHUD img = null;
			Stack<SpriteImgHUD> hudStack = new Stack<SpriteImgHUD>();
			
			for(String str : listToShow){
				
				img = new SpriteImgHUD(50, 0, texture.getTextureByName(str), texture.getVertexBufferObjectManager(), this, false) ;
				this.registerTouchArea(img);
				hudStack.push(img);
				
			}
			if(img!=null){
				img.setMustRemove(true);
			}
			
			while(!hudStack.isEmpty()){
				
				this.attachChild(hudStack.pop());
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
