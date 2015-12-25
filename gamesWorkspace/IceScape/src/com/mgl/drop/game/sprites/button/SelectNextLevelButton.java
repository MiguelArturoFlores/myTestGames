package com.mgl.drop.game.sprites.button;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;

public class SelectNextLevelButton extends Sprite {

	
	private Level level;
	private LevelController controller; 
	
	public SelectNextLevelButton(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, LevelController controller, Level level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			
			this.level=level;
			this.controller =controller;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			int star = dao.loadStars();
			
			if(star<level.getMinStar()){
				SceneManagerSingleton sceneManager = SceneManagerSingleton
						.getInstance();
				sceneManager.setCurrentScene(AllScenes.SELECT_LEVEL);
				
				InformativeHUD hud = new InformativeHUD(
						"You need "+level.getMinStar()+" Stars to play this level get more stars");
				HUDManagerSingleton.getInstance().addHUD(hud, true);
				return true;
			}
			
			SoundSingleton.getInstance().playSound("buttonPress.mp3");
			PublicityManagerSingleton.getInstance().showIntersitial();
			
			SceneManagerSingleton sceneManager = SceneManagerSingleton
					.getInstance();
			
			sceneManager.setCurrentScene(AllScenes.GAME_BEGIN);
			
			controller = null;
			
			/*controller.loadNextLevel(level.getId());
			controller.getLevelManager().setLevelDB(level);
			controller.getLevelManager().reloadLevel();

			*/
			
			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}


}
