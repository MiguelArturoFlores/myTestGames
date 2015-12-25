package com.mgl.drop.game.objects.button;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.texture.TextureSingleton;

public class ButtonPlay extends Sprite {

	TextureSingleton texture = TextureSingleton.getInstance();
	int i = 1;

	public ButtonPlay(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);

		Text textMore = ObjectFactorySingleton.getInstance().createText("Play",
				texture.getmFont1());
		this.setWidth(textMore.getWidth() + 70);
		this.setHeight(textMore.getHeight() + 40);

		textMore.setPosition(this.getWidth() / 2 - textMore.getWidth() / 2,
				this.getHeight() / 2 - textMore.getHeight() / 2);

		// this.attachChild(textMore);

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		try {

			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				SceneManagerSingleton sceneManager = SceneManagerSingleton
						.getInstance();
				// TODO uncomment to go direcctly toselect level scene
				//sceneManager.setCurrentScene(AllScenes.SELECT_LEVEL);

				 sceneManager.setCurrentScene(AllScenes.SELECT_LEVEL);

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
