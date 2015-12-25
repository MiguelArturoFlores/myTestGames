package com.mgl.drop.game.objects.button;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.texture.TextureSingleton;

public class ButtonPlayNormal extends Sprite{

	TextureSingleton texture = TextureSingleton.getInstance();
	int i = 1;
	
	public ButtonPlayNormal(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		
		Text textMore = ObjectFactorySingleton.getInstance().createText("Normal",
				texture.getmFont2());
		this.setWidth(textMore.getWidth()+70);
		this.setHeight(textMore.getHeight()+40);
		
		textMore.setPosition(this.getWidth()/2 - textMore.getWidth()/2, this.getHeight()/2 - textMore.getHeight()/2);

		this.attachChild(textMore);
		
		Text textMoreAux = ObjectFactorySingleton.getInstance().createText("Mode",
				texture.getmFont2());
		
		
		textMoreAux.setPosition(this.getWidth()/2 - textMoreAux.getWidth()/2, this.getHeight()/2 - textMoreAux.getHeight()/2 + textMore.getHeight());

		this.attachChild(textMoreAux);
		
		this.setHeight(textMore.getHeight()+60+textMoreAux.getHeight());
		
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

			
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

				break;
			case TouchEvent.ACTION_MOVE:
				
				break;
			case TouchEvent.ACTION_UP:
				
				SoundSingleton.getInstance().playButtonSound();
				
				SceneManagerSingleton sceneManager =SceneManagerSingleton.getInstance();
				
				sceneManager.setCurrentScene(AllScenes.SELECT_LEVEL);
				
				SceneManagerSingleton.getInstance().sendGoogleTrack(
						"Playing normal");
				
								
				break;
			}
			return true;
	}

}
