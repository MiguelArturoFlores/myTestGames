package com.mgl.drop.game;

import java.util.ArrayList;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.hud.LevelHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.texture.TextureSingleton;

public class LevelManager {

	//Texture mana
	private TextureSingleton texture = TextureSingleton.getInstance(); 
	
	//screen details
	
	private Scene scene;
	
		
	// general level
	private Level levelDB;
	private ArrayList<Level> levelList;
	

	//player
	private LevelController level;
	
	
	public LevelManager(Scene scene, Level levelDB, ArrayList<Level> levelList){
		try {
			
			this.levelDB = levelDB;
			this.scene = scene;
			this.levelList = levelList;
			
			
			
			level = new LevelController(scene, this, levelDB.getId());
			
			
			
			//initHashLevel();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void reloadLevel(){
		try {
			
			SceneManagerSingleton manager = SceneManagerSingleton.getInstance();
			
			manager.reloadLevel(levelDB,levelList, AllScenes.GAME_BEGIN);
			
			
			/*level.getCamera().getHUD().detachChildren();
			level = new Level(scene, camera,this);
			initHud();*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	

	public void update(float time){
		try {
			if(level!=null){
				level.update(time);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//getters setters;

	public TextureSingleton getTexture() {
		return texture;
	}

	public void setTexture(TextureSingleton texture) {
		this.texture = texture;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
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

	public LevelController getLevel() {
		return level;
	}

	public void setLevel(LevelController level) {
		this.level = level;
	}



	
	
}
