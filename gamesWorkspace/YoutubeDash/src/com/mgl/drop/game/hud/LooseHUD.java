package com.mgl.drop.game.hud;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import com.google.example.games.basegameutils.ButtonLeaderboard;
import com.mgl.base.MyFactory;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.LevelManager;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteProgressBar;
import com.mgl.drop.game.sprites.SpriteRegresiveCount;
import com.mgl.drop.game.sprites.button.ResetButton;
import com.mgl.drop.game.sprites.button.SelectLevelButton;
import com.mgl.drop.game.sprites.button.ayoutuberunner.ButtonLoginTwitter;
import com.mgl.drop.game.sprites.button.ayoutuberunner.ButtonShareFacebook;
import com.mgl.drop.game.sprites.button.ayoutuberunner.ButtonShareTwitter;
import com.mgl.drop.game.sprites.button.ayoutuberunner.ButtonShareWhatsapp;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.twitter.TwitterSingleton;
import com.mgl.youtuberdash.R;

public class LooseHUD extends HUD {

	private LevelController controller;
	
	public LooseHUD(LevelController controller) {
		try {

			this.controller= controller;
			TextureSingleton texture = TextureSingleton.getInstance();

			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());

			background.setSize(MainDropActivity.CAMERA_WIDTH,
					MainDropActivity.CAMERA_HEIGHT);
			background.setAlpha(0.9f);

			ResetButton resetButtonAux = new ResetButton(0, 0,
					texture.getTextureByName("closeHud.png"),
					texture.getVertexBufferObjectManager(),
					controller.getScene(), controller.getLevelManager());
			resetButtonAux.setSize(80, 90);
			resetButtonAux.setAlpha(1f);
			resetButtonAux.setPosition(MainDropActivity.CAMERA_WIDTH - resetButtonAux.getWidth()-10, 10);
			this.registerTouchArea(resetButtonAux);
			
			
			SpriteBackground generalWindow = new SpriteBackground(0, 0,
					texture.getTextureByName("blackBar.png"),
					texture.getVertexBufferObjectManager());
			generalWindow.setSize(800, 300);
			generalWindow.setPosition(0,
					MainDropActivity.CAMERA_HEIGHT / 4
							);
			
			this.attachChild(background);
			this.attachChild(generalWindow);
			this.attachChild(resetButtonAux);
			
			//SpriteBackground diamant = new SpriteBackground(0, 0, texture.getTextureByName("likeCoin.png"), texture.getVertexBufferObjectManager());
			//diamant.setSize(100, 100);
			//diamant.setPosition(MainDropActivity.CAMERA_WIDTH / 2 - diamant.getWidth() / 2 - 150,
			//		generalWindow.getY()+100);
			//this.attachChild(diamant);
			
			addShareButton();
			
			if(true){
				return;
			}
			Text text = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity()
							.getResources().getString(R.string.looseRetry),
					texture.getmFont1());
			text.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - text.getWidth() / 2,
					MainDropActivity.CAMERA_HEIGHT - 200);
			text.setColor(Color.WHITE);
			this.attachChild(text);

			SelectLevelButton selectButton = new SelectLevelButton(0, 0,
					texture.getTextureByName("selectLevel.png"),
					texture.getVertexBufferObjectManager(),
					controller.getScene());
			selectButton.setSize(60, 60);
			selectButton.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - selectButton.getWidth()
							/ 2 - 10 - selectButton.getWidth() / 2, text.getY()
							+ text.getHeight() + 10);
			this.registerTouchArea(selectButton);
			this.attachChild(selectButton);

			ResetButton resetButton = new ResetButton(120, 10,
					texture.getTextureByName("retry.png"),
					texture.getVertexBufferObjectManager(),
					controller.getScene(), controller.getLevelManager());
			resetButton.setSize(60, 60);
			resetButton.setPosition(
					selectButton.getX() + selectButton.getWidth() + 15,
					selectButton.getY());
			this.registerTouchArea(resetButton);
			this.attachChild(resetButton);

			ButtonLeaderboard leaderBoard = MyFactory.createButtonLeaderBoard();
			leaderBoard.setLeaderboarName(PoolObjectSingleton.getInstance().getLeaderboardName(UserInfoSingleton.getInstance().getPlayerSelected()));
			this.attachChild(leaderBoard);
			leaderBoard.setSize(80, 80);
			leaderBoard.setPosition(resetButton.getX()+resetButton.getWidth()+15,resetButton.getY());
			this.registerTouchArea(leaderBoard);

			//addTwitterShareButton(generalWindow,controller) ;
			
			//addPercentageBar(generalWindow,controller);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetLevel(){
		try {
			controller.getLevelManager().reloadLevel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addShareButton() {
		try {
			
			if(UserInfoSingleton.getInstance().canShareWhatsapp()){
				addShareWhatsapp();
				return;
			}else if(UserInfoSingleton.getInstance().canShareFacebook()){
				addShareFacebook();
				return;
			}else if(!TwitterSingleton.getInstance().isLoggedIn()){
				addLoginTwitter();
				return;
			}else if(TwitterSingleton.getInstance().isLoggedIn()){
				addShareTwitter();
				return;
			}
			
			resetLevel();
			
			
			
		} catch (Exception e) {
			resetLevel();
			e.printStackTrace();
		}
		
	}

	private void addShareTwitter() {
		try {
			
			TextureSingleton texture = TextureSingleton.getInstance();
			
			ButtonShareTwitter shareWhatsapp =  new ButtonShareTwitter(0, 0, texture.getTextureByName("twttr.png"), texture.getVertexBufferObjectManager(),controller.getContLikeCoin());
			shareWhatsapp.setPosition(MainDropActivity.CAMERA_WIDTH/2 - shareWhatsapp.getWidth()/2,250);
			shareWhatsapp.setLooseHUD(this);
			shareWhatsapp.setSize(180, 180);
			shareWhatsapp.setPosition(40,MainDropActivity.CAMERA_HEIGHT/2 - shareWhatsapp.getHeight()/2 + 25);
			this.attachChild(shareWhatsapp);
			this.registerTouchArea(shareWhatsapp);
			
			ButtonShareTwitter shareWhatsapp2 =  new ButtonShareTwitter(0, 0, texture.getTextureByName("twttr.png"), texture.getVertexBufferObjectManager(), controller.getContLikeCoin());
			shareWhatsapp2.setAlpha(0);
			shareWhatsapp2.setLooseHUD(this);
			shareWhatsapp2.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			this.attachChild(shareWhatsapp2);
			
			Text pressToEarn = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.SHARE_AND_WIN)+" 50", texture.getmFont1());
			pressToEarn.setPosition(MainDropActivity.CAMERA_WIDTH/2 - pressToEarn.getWidth()/2,MainDropActivity.CAMERA_HEIGHT/2 - pressToEarn.getHeight()/2);
			this.attachChild(pressToEarn);
			
			Text freeMoney = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.FREE_MONEY), texture.getmFont1());
			freeMoney.setPosition(MainDropActivity.CAMERA_WIDTH/2 - freeMoney.getWidth()/2,pressToEarn.getHeight()+pressToEarn.getY());
			this.attachChild(freeMoney);
			
			String message = new String();
			message = TwitterSingleton.getTwitterShareMessage(controller.getContLikeCoin());
			
			
			Text messageText = ObjectFactorySingleton.getInstance().createText(
					message, texture.getmFont2());
			messageText.setPosition(MainDropActivity.CAMERA_WIDTH/2 - messageText.getWidth()/2,380);
			this.attachChild(messageText);
			
			this.registerTouchArea(shareWhatsapp2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addLoginTwitter() {
		try {
			
			TextureSingleton texture = TextureSingleton.getInstance();
			
			ButtonLoginTwitter shareWhatsapp =  new ButtonLoginTwitter(0, 0, texture.getTextureByName("twttr.png"), texture.getVertexBufferObjectManager());
			shareWhatsapp.setPosition(MainDropActivity.CAMERA_WIDTH/2 - shareWhatsapp.getWidth()/2,250);
			shareWhatsapp.setLooseHUD(this);
			shareWhatsapp.setSize(180, 180);
			shareWhatsapp.setPosition(40,MainDropActivity.CAMERA_HEIGHT/2 - shareWhatsapp.getHeight()/2 + 25);
			this.attachChild(shareWhatsapp);
			this.registerTouchArea(shareWhatsapp);
			
			ButtonLoginTwitter shareWhatsapp2 =  new ButtonLoginTwitter(0, 0, texture.getTextureByName("twttr.png"), texture.getVertexBufferObjectManager());
			shareWhatsapp2.setAlpha(0);
			shareWhatsapp2.setLooseHUD(this);
			shareWhatsapp2.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			this.attachChild(shareWhatsapp2);
			
			Text pressToEarn = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.LOGIN_TWITTER_AND_WIN)+" 5000 ", texture.getmFont1());
			pressToEarn.setPosition(MainDropActivity.CAMERA_WIDTH/2 - pressToEarn.getWidth()/2,MainDropActivity.CAMERA_HEIGHT/4 + pressToEarn.getHeight() + 5);
			this.attachChild(pressToEarn);
			
			Text freeMoney = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.FREE_MONEY), texture.getmFont1());
			freeMoney.setPosition(MainDropActivity.CAMERA_WIDTH/2 - freeMoney.getWidth()/2,pressToEarn.getHeight()+pressToEarn.getY());
			this.attachChild(freeMoney);
			
			this.registerTouchArea(shareWhatsapp2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addShareFacebook() {
		try {
			
			TextureSingleton texture = TextureSingleton.getInstance();
			
			ButtonShareFacebook shareWhatsapp =  new ButtonShareFacebook(0, 0, texture.getTextureByName("fb.png"), texture.getVertexBufferObjectManager());
			shareWhatsapp.setPosition(MainDropActivity.CAMERA_WIDTH/2 - shareWhatsapp.getWidth()/2,250);
			shareWhatsapp.setLooseHUD(this);
			shareWhatsapp.setSize(180, 180);
			shareWhatsapp.setPosition(40,MainDropActivity.CAMERA_HEIGHT/2 - shareWhatsapp.getHeight()/2 + 25);
			this.attachChild(shareWhatsapp);
			this.registerTouchArea(shareWhatsapp);
			
			ButtonShareFacebook shareWhatsapp2 =  new ButtonShareFacebook(0, 0, texture.getTextureByName("fb.png"), texture.getVertexBufferObjectManager());
			shareWhatsapp2.setAlpha(0);
			shareWhatsapp2.setLooseHUD(this);
			shareWhatsapp2.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			this.attachChild(shareWhatsapp2);
			
			Text pressToEarn = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.SHARE_AND_WIN)+" 100", texture.getmFont1());
			pressToEarn.setPosition(MainDropActivity.CAMERA_WIDTH/2 - pressToEarn.getWidth()/2,MainDropActivity.CAMERA_HEIGHT/2 - pressToEarn.getHeight()/2);
			this.attachChild(pressToEarn);
			
			Text freeMoney = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.FREE_MONEY), texture.getmFont1());
			freeMoney.setPosition(MainDropActivity.CAMERA_WIDTH/2 - freeMoney.getWidth()/2,pressToEarn.getHeight()+pressToEarn.getY());
			this.attachChild(freeMoney);
			
			this.registerTouchArea(shareWhatsapp2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addShareWhatsapp() {
		try {
			
			TextureSingleton texture = TextureSingleton.getInstance();
			
			ButtonShareWhatsapp shareWhatsapp =  new ButtonShareWhatsapp(0, 0, texture.getTextureByName("whatsapp.png"), texture.getVertexBufferObjectManager());
			shareWhatsapp.setPosition(MainDropActivity.CAMERA_WIDTH/2 - shareWhatsapp.getWidth()/2,250);
			shareWhatsapp.setLooseHUD(this);
			shareWhatsapp.setSize(180, 180);
			shareWhatsapp.setPosition(40,MainDropActivity.CAMERA_HEIGHT/2 - shareWhatsapp.getHeight()/2 + 25);
			this.attachChild(shareWhatsapp);
			this.registerTouchArea(shareWhatsapp);
			
			ButtonShareWhatsapp shareWhatsapp2 =  new ButtonShareWhatsapp(0, 0, texture.getTextureByName("whatsapp.png"), texture.getVertexBufferObjectManager());
			shareWhatsapp2.setAlpha(0);
			shareWhatsapp2.setLooseHUD(this);
			shareWhatsapp2.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			this.attachChild(shareWhatsapp2);
			
			Text pressToEarn = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.SHARE_AND_WIN)+ " 50", texture.getmFont1());
			pressToEarn.setPosition(MainDropActivity.CAMERA_WIDTH/2 - pressToEarn.getWidth()/2,MainDropActivity.CAMERA_HEIGHT/2 - pressToEarn.getHeight()/2);
			this.attachChild(pressToEarn);
			
			Text freeMoney = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.FREE_MONEY), texture.getmFont1());
			freeMoney.setPosition(MainDropActivity.CAMERA_WIDTH/2 - freeMoney.getWidth()/2,pressToEarn.getHeight()+pressToEarn.getY());
			this.attachChild(freeMoney);
			
			this.registerTouchArea(shareWhatsapp2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addPercentageBar(SpriteBackground generalWindow,
			LevelController controller) {
		try {
			
			float percentage = controller.loadPercentage();
			TextureSingleton texture = TextureSingleton.getInstance();
			
			Text text =  ObjectFactorySingleton.getInstance().createText(Float.valueOf(percentage).intValue()+"%" , TextureSingleton.getInstance().getmFont1());
			text.setPosition(generalWindow.getWidth()/2-text.getWidth()/2, 100);
			text.setZIndex(ZIndexGame.FADE);
			this.attachChild(text);
			
			SpriteProgressBar progressBar = new SpriteProgressBar(0, 0, 300, 50, texture.getTextureByName("emptyBar.png"), texture.getVertexBufferObjectManager(), "fillBar.png");
			progressBar.setPercentage(percentage);
			progressBar.setPosition(text.getX()+text.getWidth()/2 - progressBar.getWidth()/2, 50);
			this.attachChild(progressBar);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addTwitterShareButton(SpriteBackground generalWindow, LevelController controller) {
		try {
			
			if(!TwitterSingleton.getInstance().isLoggedIn()){
				ButtonLoginTwitter login = new ButtonLoginTwitter(0	, 0, TextureSingleton.getInstance().getTextureByName("twttr.png"), TextureSingleton.getInstance().getVertexBufferObjectManager());
				login.setSize(70, 70);
				login.setPosition(generalWindow.getX()+generalWindow.getWidth()-5 -login.getWidth(),generalWindow.getY()+5);
				
				this.attachChild(login);
				this.registerTouchArea(login);
				
			}else{
			
				ButtonShareTwitter share = new ButtonShareTwitter(0	, 0, TextureSingleton.getInstance().getTextureByName("twttr.png"), TextureSingleton.getInstance().getVertexBufferObjectManager(),controller.getContLikeCoin());
				share.setSize(70, 70);
				share.setPosition(generalWindow.getX()+generalWindow.getWidth()-5 -share.getWidth(),generalWindow.getY()+5);
				
				this.attachChild(share);
				this.registerTouchArea(share);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
