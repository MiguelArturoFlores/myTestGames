package com.mgl.drop.game.hud;

import org.andengine.engine.camera.Camera;


import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import android.util.Log;

import com.google.example.games.basegameutils.ButtonLeaderboard;
import com.mgl.base.MyFactory;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ResetButton;
import com.mgl.drop.game.sprites.button.SelectLevelButton;
import com.mgl.drop.game.sprites.button.SelectNextLevelButton;
import com.mgl.drop.game.sprites.button.ayoutuberunner.ButtonLoginTwitter;
import com.mgl.drop.game.sprites.button.ayoutuberunner.ButtonShareTwitter;
import com.mgl.drop.game.sprites.button.ayoutuberunner.ButtonShareVolontary;
import com.mgl.drop.game.sprites.thread.ThreadSoundOnce;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.ninja.R;
import com.mgl.twitter.TwitterSingleton;

public class WinHUD extends HUD {

	private LevelController controller;
	
		public WinHUD(LevelController controller,Level level) {
			try {

				this.controller = controller;
				
				TextureSingleton texture = TextureSingleton.getInstance();

				createWinHUD();
				
				if(true){
					return;
				}
				
				SpriteBackground background = new SpriteBackground(0, 0,
						texture.getTextureByName("black.jpg"),
						texture.getVertexBufferObjectManager());

				background.setSize(MainDropActivity.CAMERA_WIDTH,
						MainDropActivity.CAMERA_HEIGHT);
				background.setAlpha(0.8f);

				SpriteBackground generalWindow = new SpriteBackground(0, 0,
						texture.getTextureByName("generalWindow.png"),
						texture.getVertexBufferObjectManager());
				// backgroundBlack.setSize(camera.getWidth()/2f,
				// camera.getHeight());
				generalWindow.setPosition(
						MainDropActivity.CAMERA_WIDTH / 2
								- generalWindow.getWidth() / 2,
						MainDropActivity.CAMERA_HEIGHT / 2
								- generalWindow.getHeight() / 2);
				generalWindow.setColor(controller.getLevelCurrent().getRed(),
						controller.getLevelCurrent().getGreen(), controller
								.getLevelCurrent().getBlue());

				this.attachChild(background);

				this.attachChild(generalWindow);

				
				SpriteBackground diamant = new SpriteBackground(0, 0, texture.getTextureByName("likeCoin.png"), texture.getVertexBufferObjectManager());
				diamant.setSize(100, 100);
				diamant.setPosition(MainDropActivity.CAMERA_WIDTH / 2 - diamant.getWidth() / 2 - 150,
						generalWindow.getY()+100);
				this.attachChild(diamant);
				
				Text coins = ObjectFactorySingleton.getInstance().createText(
						"x " + controller.getContLikeCoin(), texture.getmFont1());
				coins.setPosition(
						diamant.getX()+diamant.getWidth(),
						MainDropActivity.CAMERA_HEIGHT - 300);
				coins.setColor(Color.WHITE);
				this.attachChild(coins);

				
				Text text = ObjectFactorySingleton.getInstance().createText(
						SceneManagerSingleton.getInstance().getActivity()
								.getResources().getString(R.string.winLevel),
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
//
//				SelectNextLevelButton resetButton = new SelectNextLevelButton(120, 10,
//						texture.getTextureByName("buttonPlay.png"),
//						texture.getVertexBufferObjectManager(),
//						controller,level);
//				resetButton.setSize(60, 60);
//				resetButton.setPosition(
//						selectButton.getX() + selectButton.getWidth() + 15,
//						selectButton.getY());
//				this.registerTouchArea(resetButton);
//				this.attachChild(resetButton);

				ButtonLeaderboard leaderBoard = MyFactory.createButtonLeaderBoard();
				leaderBoard.setLeaderboarName(PoolObjectSingleton.getInstance().getLeaderboardName(UserInfoSingleton.getInstance().getPlayerSelected()));
				this.attachChild(leaderBoard);
				leaderBoard.setSize(80, 80);
				leaderBoard.setPosition(diamant.getX()+diamant.getWidth()+200,diamant.getY());
				this.registerTouchArea(leaderBoard);

				addTwitterShareButton(generalWindow,controller) ;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void createWinHUD() {
			
			try {
				
				TextureSingleton texture = TextureSingleton.getInstance();

				SpriteBackground background = new SpriteBackground(0, 0,
						texture.getTextureByName("black.jpg"),
						texture.getVertexBufferObjectManager());

				background.setSize(MainDropActivity.CAMERA_WIDTH,
						MainDropActivity.CAMERA_HEIGHT);
				background.setAlpha(0.98f);

				SelectLevelButton resetButtonAux = new SelectLevelButton(0, 0,
						texture.getTextureByName("selectLevel.png"),
						texture.getVertexBufferObjectManager(),
						controller.getScene());
				resetButtonAux.setSize(70, 80);
				resetButtonAux.setAlpha(1f);
				resetButtonAux.setPosition(MainDropActivity.CAMERA_WIDTH/2 - resetButtonAux.getWidth()/2, MainDropActivity.CAMERA_HEIGHT - 100 - resetButtonAux.getHeight());
				this.registerTouchArea(resetButtonAux);
				
				
				SpriteBackground generalWindow = new SpriteBackground(0, 0,
						texture.getTextureByName("blackBar.png"),
						texture.getVertexBufferObjectManager());
				generalWindow.setSize(800, 300);
				generalWindow.setPosition(0,
						MainDropActivity.CAMERA_HEIGHT / 4
								);
				
				
				UserInfoSingleton.getInstance().increaseMoney(100);
				Text message =  ObjectFactorySingleton.getInstance().createText(
						SceneManagerSingleton.getInstance().getActivity().getString(R.string.winLevel),
				texture.getmFont1());
				message.setPosition(MainDropActivity.CAMERA_WIDTH/2-message.getWidth()/2 , MainDropActivity.CAMERA_HEIGHT/2 -message.getHeight()/2);
				
				Text money =  ObjectFactorySingleton.getInstance().createText(
						SceneManagerSingleton.getInstance().getActivity().getString(R.string.EARN_MONEY)+" 100",
				texture.getmFont1());
				money.setPosition(MainDropActivity.CAMERA_WIDTH/2-money.getWidth()/2 , message.getY() + message.getHeight()+5);
				
				ButtonShareVolontary shareWhat = new ButtonShareVolontary(0, 0, texture.getTextureByName("whatsapp.png"), texture.getVertexBufferObjectManager(), ButtonShareVolontary.WHATSAPP);
				shareWhat.setSize(70, 70);
				shareWhat.setPosition(MainDropActivity.CAMERA_WIDTH - shareWhat.getWidth()-5, 150);
				
				ButtonShareVolontary shareFacebook = new ButtonShareVolontary(0, 0, texture.getTextureByName("fb.png"), texture.getVertexBufferObjectManager(), ButtonShareVolontary.FACEBOOK);
				shareFacebook.setSize(70, 70);
				shareFacebook.setPosition(MainDropActivity.CAMERA_WIDTH - shareFacebook.getWidth()-5, shareWhat.getY()+shareWhat.getHeight()+10);
				
				this.attachChild(background);
				this.attachChild(generalWindow);
				
				this.attachChild(shareWhat);
				this.attachChild(shareFacebook);
				this.registerTouchArea(shareFacebook);
				this.registerTouchArea(shareWhat);
				
				this.attachChild(message);
				this.attachChild(money);
				this.attachChild(resetButtonAux);
				
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
