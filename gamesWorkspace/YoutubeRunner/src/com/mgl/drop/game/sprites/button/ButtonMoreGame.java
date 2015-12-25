package com.mgl.drop.game.sprites.button;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.controller.LevelController;

public class ButtonMoreGame extends MySprite {

	private Scene scene;

	public ButtonMoreGame(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager);
		
		// this.setAlpha(0.5f);
		
		Text textMore = ObjectFactorySingleton.getInstance().createText("More",
				texture.getmFont1());
		
		Text textGames = ObjectFactorySingleton.getInstance().createText("Games",
				texture.getmFont1());
		
		this.setWidth(textGames.getWidth()+70);
		this.setHeight(textMore.getHeight()*2+40);

		textMore.setPosition(this.getWidth()/2 - textMore.getWidth()/2, 10);
		textGames.setPosition(this.getWidth()/2 - textGames.getWidth()/2, textMore.getY()+textMore.getHeight() +5);

		
		this.attachChild(textMore);
		this.attachChild(textGames);
		
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			PublicityManagerSingleton.getInstance().showMoreGames();
			
			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

}