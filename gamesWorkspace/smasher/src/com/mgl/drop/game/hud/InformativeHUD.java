package com.mgl.drop.game.hud;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;

public class InformativeHUD extends HUD {

	public InformativeHUD(String textToShow) {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();
			Camera camera = SceneManagerSingleton.getInstance().getCamera();

			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setSize(camera.getWidth(), camera.getHeight());

			this.attachChild(background);

			Text text = ObjectFactorySingleton.getInstance().createText(
					textToShow, TextureSingleton.getInstance().getmFont1());

			int split = 1;
			if (text.getWidth() > MainDropActivity.CAMERA_WIDTH) {
				split = Integer
						.valueOf((int) (text.getWidth() / MainDropActivity.CAMERA_WIDTH));
				split ++;
			}

			int quantity = textToShow.length()/split;
			ArrayList<String> textList = new ArrayList<String>();
			for(int i = 0; i< split; i++){
				String textAux = "";
				if((i*quantity)+ quantity>textToShow.length()){
					textAux = textToShow.substring(i*quantity, textToShow.length()+1);
				}else{
					textAux = textToShow.substring(i*quantity,(i*quantity)+ quantity);
				}
			
				textList.add(new String(textAux));
			}
			int i = 0;
			for(String textAux : textList){
				Text text1 = ObjectFactorySingleton.getInstance().createText(
						textAux, TextureSingleton.getInstance().getmFont1());
				
				text1.setPosition(camera.getWidth() / 2 - text1.getWidth() / 2,
					camera.getHeight() / 2 - text1.getHeight() / 2 );
				text1.setPosition(text1.getX(), text1.getY()+(i*100));
				i++;
				this.attachChild(text1);
			}
			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0,
					texture.getTextureByName("closeHud.png"),
					texture.getVertexBufferObjectManager(), this);
			remove.setSize(70, 70);
			remove.setPosition(camera.getWidth() - remove.getWidth() - 20, 20);

			this.attachChild(remove);
			

			this.registerTouchArea(remove);
			
			this.registerTouchArea(background);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
