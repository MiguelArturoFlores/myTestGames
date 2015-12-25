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
import com.mgl.base.userinfo.UserInfoSingleton;
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
import com.mgl.drop.game.hud.InformativeSpriteHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteProgressBar;
import com.mgl.drop.game.sprites.button.ayoutuberunner.ButtonLoginTwitter;
import com.mgl.ninja.R;
import com.mgl.twitter.TwitterSingleton;

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
			
			if(levelDB.isLast()){
				InformativeHUD hud = new InformativeHUD(
						SceneManagerSingleton.getInstance().getActivity().getString(R.string.MORE_LEVEL_MESSAGE));
				
				HUDManagerSingleton.getInstance().addHUD(hud, true);
				return true;
			}
			
			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			int star = UserInfoSingleton.getInstance().getMoney();
			
			if(star<levelDB.getMinStar()){
				
				SpriteBackground diamant = new SpriteBackground(0, 0, texture.getTextureByName("money.png"), texture.getVertexBufferObjectManager());
				
				InformativeSpriteHUD hud = new InformativeSpriteHUD(diamant,
						SceneManagerSingleton.getInstance().getActivity().getString(R.string.UNLOCK_LEVEL_MESSAGE)+" x "+levelDB.getMinStar());
				
				HUDManagerSingleton.getInstance().addHUD(hud, true);
				return true;
			}
			if(levelDB.getFacebook() && !TwitterSingleton.getInstance().isLoggedIn()){
				
				ButtonLoginTwitter diamant = new ButtonLoginTwitter(0, 0, texture.getTextureByName("twttr.png"), texture.getVertexBufferObjectManager());
				InformativeSpriteHUD hud = new InformativeSpriteHUD(diamant,
						SceneManagerSingleton.getInstance().getActivity().getString(R.string.UNLOCK_WITH_TWITTER)+" x "+levelDB.getMinStar());
				HUDManagerSingleton.getInstance().addHUD(hud, true);
				return true;
				
			}
			

			UserInfoSingleton.getInstance().increaseMoney(levelDB.getMinStar() *-1);
			dao.setLevelStar(levelDB.getId(),0);
			
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
			
			//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("r"+levelDB.getRed()+"g"+levelDB.getGreen()+"b"+levelDB.getBlue()), true);
			
			//this.setColor(levelDB.getRed()/255f, levelDB.getGreen()/255f, levelDB.getBlue()/255f); 
			//this.setColor(Color.BLUE);
			
			Text textName = ObjectFactorySingleton.getInstance().createText(levelDB.getName(), texture.getmFont1());
			textName.setColor(Color.WHITE);
			textName.setPosition(
					this.getWidth() / 2 - textName.getWidth() / 2,
					this.getHeight() / 2 - textName.getHeight() / 2);
			this.attachChild(textName);
			
			int yStar =  -20;
			
			if(levelDB.getMinStar()>0){
				
			Sprite star1 = new Sprite(0, 0,
					texture.getTextureByName("money.png"),
					texture.getVertexBufferObjectManager());
			star1.setSize(60, 60);
			star1.setPosition(this.getWidth()/2 - (star1.getWidth() * 3) + 30 + star1.getWidth() ,
					this.getHeight() -75);
			this.attachChild(star1);
			
			Text textStar =  ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.UNLOCK_LEVEL)+" x" + levelDB.getMinStar(), texture.getmFont1());;
			textStar.setPosition(this.getWidth()/2 - textStar.getWidth()/2, star1.getY()-20);
			textStar.setZIndex(ZIndexGame.FADE);
			this.attachChild(textStar);
		
			star1.setPosition(textStar.getX() +textStar.getWidth()+5, textStar.getY()+ textStar.getHeight()/2 -star1.getHeight()/2);
			
			}
			
			//percentagfee
			Text percentage =  ObjectFactorySingleton.getInstance().createText(Float.valueOf(levelDB.getPercentage()).intValue()+"%" , texture.getmFont1());
			percentage.setPosition(this.getWidth()/2-percentage.getWidth()/2, 100);
			percentage.setZIndex(ZIndexGame.FADE);
			this.attachChild(percentage);
			
			SpriteProgressBar progressBar = new SpriteProgressBar(0, 0, 300, 50, texture.getTextureByName("emptyBar.png"), texture.getVertexBufferObjectManager(), "fillBar.png");
			progressBar.setPercentage(levelDB.getPercentage());
			progressBar.setPosition(percentage.getX()+percentage.getWidth()/2 - progressBar.getWidth()/2, 50);
			this.attachChild(progressBar);
			
			
			
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