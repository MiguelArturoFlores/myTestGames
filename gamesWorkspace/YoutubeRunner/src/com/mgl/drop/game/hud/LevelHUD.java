package com.mgl.drop.game.hud;

import org.andengine.engine.camera.Camera;


import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.util.Log;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.LevelManager;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.PauseButton;
import com.mgl.drop.game.sprites.button.ResetButton;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.youtuberunner.R;

public class LevelHUD extends HUD {

	private Text waveText;
	private Text liveText;
	private Text scoreText;
	private int currentWave= 0;
	private int totalWave = 0;
	
	public LevelHUD(Scene scene, LevelManager levelManager) {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();
			Camera camera = SceneManagerSingleton.getInstance().getCamera();

			
			PauseButton pasuseButton = new PauseButton(15,
					0 , texture.getTextureByName("pause.png"),
					texture.getVertexBufferObjectManager(), scene,levelManager);
			pasuseButton.setAlpha(0.8f);
			pasuseButton.setSize(60, 60);

			Sprite background = new Sprite(0,0,
					texture.getTextureByName("gray.jpg"),
					texture.getVertexBufferObjectManager());
			background.setAlpha(0f);
			background.setSize(camera.getWidth(), 70);
			
			waveText = ObjectFactorySingleton.getInstance().createText( currentWave+"/"+totalWave, texture.getmFont1());
			waveText.setPosition(camera.getWidth()-waveText.getWidth()-15,0);
			
			liveText = ObjectFactorySingleton.getInstance().createText( "" , texture.getmFont1());
			liveText.setPosition(camera.getWidth()-200,0);
			
			
			scoreText = ObjectFactorySingleton.getInstance().createText( "" , texture.getmFont1());
			scoreText.setPosition(camera.getWidth()-350,0);

			//this.attachChild(waveText);
			//this.attachChild(liveText);
			//this.attachChild(scoreText);
			//this.attachChild(waveImg);
			
			//this.attachChild(heart);
			
			this.attachChild(background);
			this.attachChild(pasuseButton);
			


			this.registerTouchArea(pasuseButton);




		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeWaveText(String text){
		try {
			if(true){
				return;
			}
			TextureSingleton texture = TextureSingleton.getInstance();
			float x = waveText.getX();
			float y = waveText.getY();
			
			this.detachChild(waveText);
			waveText = ObjectFactorySingleton.getInstance().createText( text, texture.getmFont1());
			waveText.setPosition(x,y);
			
			
			
			this.attachChild(waveText);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeLiveText(String text) {
		try {
			if(true){
				return;
			}
			TextureSingleton texture = TextureSingleton.getInstance();
			float x = liveText.getX();
			float y = liveText.getY();
			
			this.detachChild(liveText);
			liveText = ObjectFactorySingleton.getInstance().createText( "x"+text, texture.getmFont1());
			liveText.setPosition(x,y);
			
			this.attachChild(liveText);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void changeScoreText(int score) {
		try {
			if(true){
				return;
			}
			TextureSingleton texture = TextureSingleton.getInstance();
			scoreText = MainDropActivity.changeText(score+"", scoreText, texture.getmFont1());
			this.attachChild(scoreText);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
