package com.mgl.drop.game.objects.button;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.texture.TextureSingleton;

public class ButtonPlaySurvival extends Sprite{

	TextureSingleton texture = TextureSingleton.getInstance();
	int i = 1;
	
	public ButtonPlaySurvival(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		
		Text textMore = ObjectFactorySingleton.getInstance().createText("Survival",
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
				
				if(!UserInfoSingleton.getInstance().isSurvivalActive()){
					InformativeHUD hud = new InformativeHUD("Unlock at level "+GameConstants.LEVEL_TO_UNLOCK_SURVIVAL);
					HUDManagerSingleton.getInstance().addHUD(hud, true);
					return true;
				}
				
				SoundSingleton.getInstance().playButtonSound();
				
				SceneManagerSingleton sceneManager =SceneManagerSingleton.getInstance();
				
				sceneManager.createGameScene(null, null,GameConstants.PLAY_SURVIVAL);
				sceneManager.setCurrentScene(AllScenes.GAME_BEGIN);
				
				SceneManagerSingleton.getInstance().sendGoogleTrack(
						"Playing survival");
				
								
				break;
			}
			return true;
	}

}
