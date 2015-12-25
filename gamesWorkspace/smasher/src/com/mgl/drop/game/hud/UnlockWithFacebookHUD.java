package com.mgl.drop.game.hud;

import org.andengine.engine.camera.Camera;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.smasher.R;

public class UnlockWithFacebookHUD extends HUD {

	
	
	public UnlockWithFacebookHUD(Level level) {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			
			Sprite background = new Sprite(0,0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			//background.setAlpha(0.4f);
			background.setSize(camera.getWidth(), camera.getHeight());

			this.attachChild(background);
			
			Sprite loosePigeon = new Sprite(0, 0, texture.getTextureByName("loosePigeon.png"),texture.getVertexBufferObjectManager());
			
			loosePigeon.setSize(160, 220);
			loosePigeon.setPosition(camera.getWidth()/2 - loosePigeon.getWidth() / 2,
					25);
			
			this.attachChild(loosePigeon);
			
			Text text = ObjectFactorySingleton.getInstance().createText(SceneManagerSingleton.getInstance().getActivity().getResources().getString(R.string.unlockFacebook), TextureSingleton.getInstance().getmFont1());
			Text text2 = ObjectFactorySingleton.getInstance()
					.createText(
							SceneManagerSingleton.getInstance().getActivity()
									.getResources()
									.getString(R.string.unlockFacebook2), TextureSingleton.getInstance().getmFont1());
			
			
			text.setPosition(camera.getWidth()/2 -text.getWidth()/2, camera.getHeight()/2 - text.getHeight()/2+20);
			text2.setPosition(camera.getWidth()/2 -text2.getWidth()/2, camera.getHeight()/2 - text2.getHeight()/2+ text.getHeight() +20);
			
			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0, texture.getTextureByName("closeHud.png"), texture.getVertexBufferObjectManager(), this);
			remove.setSize(70, 70);
			remove.setPosition(camera.getWidth()-remove.getWidth()-20, 20);
			
			this.attachChild(remove);
			this.attachChild(text);
			this.attachChild(text2);
			
			this.registerTouchArea(remove);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
}

