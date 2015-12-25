package com.mgl.drop.game.hud;

import org.andengine.engine.camera.Camera;


import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.ninja.R;

public class ShareHud extends HUD {

	public ShareHud(){
		try {
			
			TextureSingleton texture = TextureSingleton.getInstance();
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			
			SpriteBackground background = new SpriteBackground(0,0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			//background.setAlpha(0.8f);
			background.setSize(camera.getWidth(), camera.getHeight());

			this.attachChild(background);
			
			Sprite loosePigeon = new Sprite(0, 0, texture.getTextureByName("thanks.png"),texture.getVertexBufferObjectManager());
			
			loosePigeon.setSize(220, 250);
			loosePigeon.setPosition(camera.getWidth()/2 - loosePigeon.getWidth() / 2,
					40);
			this.attachChild(loosePigeon);
			
			Text text = ObjectFactorySingleton.getInstance().createText(SceneManagerSingleton.getInstance().getActivity().getResources().getString(R.string.thanksSharing), TextureSingleton.getInstance().getmFont1());
			
			
			text.setPosition(camera.getWidth()/2 -text.getWidth()/2, camera.getHeight()/2 - text.getHeight()/2+100);
			
			
			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0, texture.getTextureByName("closeHud.png"), texture.getVertexBufferObjectManager(), this);
			remove.setSize(50, 50);
			remove.setPosition(camera.getWidth()-remove.getWidth()-100, 20);
			
			background.attachChild(remove);
			
			//this.attachChild(remove);
			this.attachChild(text);
			
			
			this.registerTouchArea(remove);
			this.registerTouchArea(background);
			
			
			
			setTouchAreaBindingOnActionDownEnabled(false);
			setTouchAreaBindingOnActionMoveEnabled(false);

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
