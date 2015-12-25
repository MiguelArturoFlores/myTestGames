package com.google.example.games.basegameutils;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;

public class GooglePlayButtonBar extends MySprite{

	private Scene scene;
	private ButtonLogInGoogle logInGoogle;
	private ButtonLogInGoogle logOutGoogle; 
	
	public GooglePlayButtonBar(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,Scene scene) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			
			this.scene = scene;
			this.setAlpha(0);
			
			addButtons();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addButtons() {
		try {
			
			float y = 0;
			float width = 60;
			float height = 60;
			int offset = 15;
			
			logInGoogle = new ButtonLogInGoogle(0, 0, texture.getTextureByName("googleLogin.png"), texture.getVertexBufferObjectManager(),true);
			logInGoogle.setSize(width, height);
			logInGoogle.setPosition(0,y);
			logInGoogle.setButtonBar(this);
			logInGoogle.setZIndex(ZIndexGame.FADE);
			
			logOutGoogle = new ButtonLogInGoogle(0, 0, texture.getTextureByName("googleLogout.png"), texture.getVertexBufferObjectManager(),false);
			logOutGoogle.setSize(width, height);
			logOutGoogle.setPosition(0,y);
			logOutGoogle.setButtonBar(this);
			logOutGoogle.setZIndex(ZIndexGame.FADE);
			
			ButtonAchievements achievementButton = new ButtonAchievements(0, 0, texture.getTextureByName("googleAchievement.png"), texture.getVertexBufferObjectManager());
			achievementButton.setSize(width, height);
			achievementButton.setPosition(logOutGoogle.getX()+logOutGoogle.getWidth()+offset,y);
			achievementButton.setZIndex(ZIndexGame.FADE);
			
			ButtonLeaderboard leaderboardButton = new ButtonLeaderboard(0, 0, texture.getTextureByName("googleLeaderboard.png"), texture.getVertexBufferObjectManager());
			leaderboardButton.setSize(width, height );
			leaderboardButton.setPosition(achievementButton.getX()+achievementButton.getWidth()+offset,y);
			leaderboardButton.setZIndex(ZIndexGame.FADE);
			
			this.attachChild(achievementButton);
			scene.registerTouchArea(achievementButton);
			
			this.attachChild(leaderboardButton);
			scene.registerTouchArea(leaderboardButton);
			
			boolean log = GooglePlayGameSingleton.getInstance().isLoggedIn();

			if(log){
				this.attachChild(logOutGoogle);
				scene.registerTouchArea(logOutGoogle);
			}else{
				this.attachChild(logInGoogle);
				scene.registerTouchArea(logInGoogle);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void reloadButtons(boolean isLoginClicked){
		try {
			
			if(isLoginClicked){
				logOutGoogle.detachSelf();
				scene.unregisterTouchArea(logOutGoogle);
				this.attachChild(logOutGoogle);
				scene.registerTouchArea(logOutGoogle);
			}else{
				logInGoogle.detachSelf();
				scene.unregisterTouchArea(logInGoogle);
				this.attachChild(logInGoogle);
				scene.registerTouchArea(logInGoogle);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
