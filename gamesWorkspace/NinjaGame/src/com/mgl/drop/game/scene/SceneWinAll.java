package com.mgl.drop.game.scene;

import org.andengine.entity.scene.Scene;

import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;

public class SceneWinAll extends Scene{
	
	public SceneWinAll(){
		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			SpriteBackground background = new SpriteBackground(0, 0, texture.getTextureByName("winAll.png"), texture.getVertexBufferObjectManager());
			this.attachChild(background);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
