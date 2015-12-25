package com.mgl.drop.game.sprites.arunner;

import java.util.ArrayList;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;

public class SpriteSelectLevelNew extends MySprite {

	private Level levelDB;
	private ArrayList<Level> levelList;
	
	

	public SpriteSelectLevelNew(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Level level,
			ArrayList<Level> levelList) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);

		try {
		
			this.levelDB = level;
			this.levelList = levelList;
			this.setIgnoreUpdate(true);
			
			
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
			
			if(star<levelDB.getMinStar()){
				
				InformativeHUD hud = new InformativeHUD(
						"You need "+levelDB.getMinStar()+" to play this level get more stars");
				HUDManagerSingleton.getInstance().addHUD(hud, true);
				return true;
			}

			SceneManagerSingleton sceneManager = SceneManagerSingleton
					.getInstance();
			
			sceneManager.createGameScene(levelDB, levelList,
					GameConstants.PLAY_NORMAL);
			sceneManager.setCurrentScene(AllScenes.GAME_BEGIN);

			SoundSingleton.getInstance().playButtonSound();
			
			

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

	public void init(){
		try {
			
			this.detachChildren();
			
			Text textName = ObjectFactorySingleton.getInstance().createText(this
					.getLevelDB().getName(), texture.getmFont1());
			textName.setColor(Color.WHITE);
			textName.setPosition(
					this.getWidth() / 2 - textName.getWidth() / 2,
					this.getHeight() / 2 - textName.getHeight() / 2);
			this.attachChild(textName);
			
			int yStar =  -20;
			
			
			
			Sprite star1 = new Sprite(0, 0,
					texture.getTextureByName("fillStar.png"),
					texture.getVertexBufferObjectManager());
			star1.setSize(40, 40);
			star1.setPosition(this.getWidth()/2 - (star1.getWidth() * 3) + 30 + star1.getWidth() ,
					this.getHeight() + yStar);
			this.attachChild(star1);
			
			Text textStar =  ObjectFactorySingleton.getInstance().createText(
					"x " + levelDB.getMinStar() , texture.getmFont1());;
			textStar.setPosition(star1.getX()+star1.getWidth(), star1.getY()+10);
			textStar.setZIndex(ZIndexGame.FADE);
			this.attachChild(textStar);
			
			if(true){
				return;
			}
			
			
			
			for(int i = 0 ; i < 3; i ++){
				Sprite star = new Sprite(0, 0,
						texture.getTextureByName("emptyStar.png"),
						texture.getVertexBufferObjectManager());
				star.setSize(23, 27);
				star.setPosition(this.getWidth()/2 - (star.getWidth() * 3) + i*30 + star.getWidth() ,
						this.getHeight() + yStar);
				this.attachChild(star);
			}
			
			yStar =  -20;
			for (int i = 0; i < this.getLevelDB().getStars(); i++) {
				Sprite star = new Sprite(0, 0,
						texture.getTextureByName("fillStar.png"),
						texture.getVertexBufferObjectManager());
				star.setSize(23, 27);
				star.setPosition(this.getWidth()/2 - (star.getWidth() * 3) + i*30 + star.getWidth() ,
						this.getHeight() + yStar);
					
				this.attachChild(star);
			}
			
			if(this.getLevelDB().getAvalible()){
				isTouchable = true;
			}else{
				isTouchable = false;
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub

	}

}