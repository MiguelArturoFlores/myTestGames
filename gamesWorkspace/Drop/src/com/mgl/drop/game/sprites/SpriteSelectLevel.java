package com.mgl.drop.game.sprites;

import java.util.ArrayList;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;

public class SpriteSelectLevel extends Sprite{
	
	private Level levelDB;
	private ArrayList<Level> levelList;
	
	public SpriteSelectLevel(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Level level, ArrayList<Level> levelList) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		
		this.levelDB =level;
		this.levelList =levelList;
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

			
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				
			

				
				SceneManagerSingleton sceneManager =SceneManagerSingleton.getInstance();
				sceneManager.createGameScene(levelDB,levelList);
				sceneManager.setCurrentScene(AllScenes.GAME_BEGIN);
				
				
				SoundSingleton.getInstance().playSound("buttonPress.mp3");
				
				
				
				break;
			case TouchEvent.ACTION_MOVE:
				
				
				break;
			case TouchEvent.ACTION_UP:
				
				
				break;
			}
			return true;
	}

	public Level getLevelDB() {
		return levelDB;
	}

	public void setLevelDB(Level levelDB) {
		this.levelDB = levelDB;
	}

	public ArrayList<Level> getLevelList() {
		return levelList;
	}

	public void setLevelList(ArrayList<Level> levelList) {
		this.levelList = levelList;
	}

	
	
}
