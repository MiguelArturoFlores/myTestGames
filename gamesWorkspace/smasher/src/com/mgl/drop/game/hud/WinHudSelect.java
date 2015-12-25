package com.mgl.drop.game.hud;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.util.Log;

import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.button.ResetButton;
import com.mgl.drop.game.sprites.button.SelectLevelButton;
import com.mgl.drop.game.sprites.thread.ThreadSoundOnce;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.smasher.R;

public class WinHudSelect extends HUD {

	public WinHudSelect(Scene scene, LevelController levelController, Level level,int stars) {
		try {
	
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			TextureSingleton texture = TextureSingleton.getInstance();
			Sprite background = new Sprite(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());

			background.setSize(SceneManagerSingleton.getInstance().getCamera().getWidth(), SceneManagerSingleton.getInstance().getCamera().getHeight());
			background.setAlpha(0.5f);

			this.attachChild(background);
			
			Sprite backgroundBlack = new Sprite(0, 0, texture.getTextureByName("black.jpg"),texture.getVertexBufferObjectManager());
			backgroundBlack.setSize(camera.getWidth()/2f, camera.getHeight());
			backgroundBlack.setPosition(camera.getWidth()/4f,0);
			
			this.attachChild(backgroundBlack);
			
			//create paloma win
			Sprite winPigeon = new Sprite(0, 0, texture.getTextureByName("winPigeon.png"),texture.getVertexBufferObjectManager());
			
			winPigeon.setSize(160, 220);
			winPigeon.setPosition(camera.getWidth()/2 - winPigeon.getWidth() / 2,
					40);
			
			this.attachChild(winPigeon);
			int offset = 40;
			
			Text text = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getResources().getString(R.string.winLevel), texture.getmFont1());
			text.setPosition(camera.getWidth()/2 - text.getWidth() / 2,
					camera.getHeight()/2 - text.getHeight() / 2 + offset);
			
			this.attachChild(text);
			
			SelectLevelButton selectLevelButton = new SelectLevelButton(
					camera.getWidth() - 40, 220,
					texture.getTextureByName("menuOrange.png"),
					texture.getVertexBufferObjectManager(), scene);
			selectLevelButton.setSize(50, 50);
			selectLevelButton.setPosition(camera.getWidth()/2 - selectLevelButton.getWidth() / 2 -100, camera.getHeight()/2 - text.getHeight() / 2 + 50 + offset);
			
			this.attachChild(selectLevelButton);
			this.registerTouchArea(selectLevelButton);
			
			ResetButton resetButton = new ResetButton(camera.getWidth() - 40,
					160, texture.getTextureByName("tryAgainOrange.png"),
					texture.getVertexBufferObjectManager(), scene, levelController.getLevelManager());
			resetButton.setSize(50, 50);
			resetButton.setPosition(camera.getWidth()/2 - selectLevelButton.getWidth() / 2 , camera.getHeight()/2 - text.getHeight() / 2 + 50 + offset);
			
			this.attachChild(resetButton);
			this.registerTouchArea(resetButton);
			
			
			Sprite star1 = new Sprite(0, 0, texture.getTextureByName("emptyStar.png"), texture.getVertexBufferObjectManager());
			star1.setSize(50, 50);
			star1.setPosition(5+camera.getWidth()/2 -100,resetButton.getHeight()  +20 + resetButton.getY());
			this.attachChild(star1);
			
			
			Sprite star2 = new Sprite(0, 0, texture.getTextureByName("emptyStar.png"), texture.getVertexBufferObjectManager());
			star2.setSize(50, 50);
			star2.setPosition(star1.getX()+star1.getWidth()+10,resetButton.getHeight()  +20 + resetButton.getY()) ;
			this.attachChild(star2);
			
			Sprite star3 = new Sprite(0, 0, texture.getTextureByName("emptyStar.png"), texture.getVertexBufferObjectManager());
			star3.setSize(50, 50);
			star3.setPosition(star2.getX()+star2.getWidth()+10,resetButton.getHeight()  +20 + resetButton.getY()) ;
			this.attachChild(star3);
			Log.d("STARS ", "s"+stars);
			for(int i = 1; i<=stars; i++){
				Sprite star1F = new Sprite(0, 0, texture.getTextureByName("fillStar.png"), texture.getVertexBufferObjectManager());
				star1F.setSize(50, 50);
				if(i==1){
					
					star1F.registerEntityModifier(new MoveModifier(0.3f, 0, star1.getX(),0,star1.getY()));
					//star1F.registerEntityModifier(new MoveModifier(0.2f, 0, 0,
					//	500,300));
					this.attachChild(star1F);
					ThreadSoundOnce thread = new  ThreadSoundOnce("star1.mp3", 0.3f);
					thread.start();
				}else if(i==2){
					star1F.registerEntityModifier(new MoveModifier(0.8f, 0, star2.getX(),0,star2.getY()));
					//star1F.registerEntityModifier(new MoveModifier(0.2f, 0, 0,
					//		800,500));
					this.attachChild(star1F);
					
					ThreadSoundOnce thread = new  ThreadSoundOnce("star2.mp3", 0.8f);
					thread.start();
					
				}else if(i==3){
					star1F.registerEntityModifier(new MoveModifier(1.2f, 0, star3.getX(),0,star3.getY()));
					//star1F.registerEntityModifier(new MoveModifier(0.2f, 0, 0,
					//		1200,100));
						this.attachChild(star1F);
						ThreadSoundOnce thread = new  ThreadSoundOnce("star3.mp3", 1.2f);
						thread.start();
				}
				//star1F.setPosition(5+camera.getWidth()/2 -100,selectButton.getHeight()  +20 + selectNextLevelButton.getY());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
