package com.mgl.drop.game.hud;

import java.util.ArrayList;
import java.util.Stack;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.texture.TextureSingleton;

public class InfoHowToHUD extends HUD {

	

	public InfoHowToHUD (LevelController level, ArrayList<String> listToShow){
		
		try {
			
			TextureSingleton texture = TextureSingleton.getInstance();
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			
			Sprite background = new Sprite(0,0,
					texture.getTextureByName("background.jpg"),
					texture.getVertexBufferObjectManager());
			background.setSize(camera.getWidth(), camera.getHeight());
			this.attachChild(background);

			
			//SpriteBackground backAux = new SpriteBackground(0, 0, texture.getTextureByName("background.png"), texture.getVertexBufferObjectManager());
			//this.attachChild(backAux);

			
			SpriteImgHUD img = null;
			Stack<SpriteImgHUD> hudStack = new Stack<SpriteImgHUD>();
			
			for(String str : listToShow){
				
				img = new SpriteImgHUD(50, 0, texture.getTextureByName(str), texture.getVertexBufferObjectManager(), this, false,"background.jpg") ;
				img.setPosition(MainDropActivity.CAMERA_WIDTH/2-img.getWidth()/2,MainDropActivity.CAMERA_HEIGHT/2-img.getHeight()/2);
				this.registerTouchArea(img);
				hudStack.push(img);
				
			}
			if(img!=null){
				img.setMustRemove(true);
			}
			
			while(!hudStack.isEmpty()){
				
				this.attachChild(hudStack.pop());
				
			}
			
			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0,
					texture.getTextureByName("closeHud.png"),
					texture.getVertexBufferObjectManager(), this);
			remove.setSize(70, 70);
			remove.setPosition(MainDropActivity.CAMERA_WIDTH - remove.getWidth() - 20, 20);

			this.attachChild(remove);
			this.registerTouchArea(remove);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
