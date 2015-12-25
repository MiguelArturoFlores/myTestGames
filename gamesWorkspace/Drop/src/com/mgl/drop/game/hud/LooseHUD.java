package com.mgl.drop.game.hud;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import com.mgl.drop.MainDropActivity;
import com.mgl.crappypigeon.R;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.LevelManager;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.button.ResetButton;
import com.mgl.drop.texture.TextureSingleton;

public class LooseHUD extends HUD{
	
	public LooseHUD(Scene scene, LevelManager levelManager){
		try {
			
			TextureSingleton texture =TextureSingleton.getInstance();
			
			
			Sprite background = new Sprite(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());

			background.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			background.setAlpha(0.5f);
			
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			
			Sprite backgroundBlack = new Sprite(0, 0, texture.getTextureByName("black.jpg"),texture.getVertexBufferObjectManager());
			backgroundBlack.setSize(camera.getWidth()/2f, camera.getHeight());
			backgroundBlack.setPosition(camera.getWidth()/4f,0);
			
			this.attachChild(background);

			this.attachChild(backgroundBlack);

			Sprite loosePigeon = new Sprite(0, 0, texture.getTextureByName("loosePigeon.png"),texture.getVertexBufferObjectManager());
			
			loosePigeon.setSize(160, 220);
			loosePigeon.setPosition(camera.getWidth()/2 - loosePigeon.getWidth() / 2,
					40);
			
			this.attachChild(loosePigeon);
			
			Text text = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getResources().getString(R.string.looseRetry), texture.getmFont1());
			text.setPosition(camera.getWidth()/2 - text.getWidth() / 2,
				 	75+ camera.getHeight()/2 - text.getHeight() / 2);
			text.setColor(Color.WHITE);
			this.attachChild(text);

			ResetButton resetButton = new ResetButton(120, 10,
					texture.getTextureByName("tryAgainOrange.png"),
					texture.getVertexBufferObjectManager(), scene, levelManager);
			resetButton.setSize(60, 60);
			resetButton.setPosition(camera.getWidth()/2 - resetButton.getWidth() / 2,camera.getHeight()/2 - text.getHeight() / 2 + 50 +75);
			
			this.registerTouchArea(resetButton);
			this.attachChild(resetButton);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
