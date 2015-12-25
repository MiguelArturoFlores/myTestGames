package com.mgl.drop.game.sprites.arunner;

import java.util.ArrayList;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;

public class SpriteTitle extends MySprite {

	public SpriteTitle(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub

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
				// sceneManager.setCurrentScene(AllScenes.SELECT_LEVEL);

				LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
						.getActivity(), DatabaseDrop.DB_NAME, null,
						MainDropActivity.DB_VERSION);
				
				sceneManager.createGameScene(null, null,
						GameConstants.PLAY_NORMAL);
				sceneManager.setCurrentScene(AllScenes.GAME_BEGIN);

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}