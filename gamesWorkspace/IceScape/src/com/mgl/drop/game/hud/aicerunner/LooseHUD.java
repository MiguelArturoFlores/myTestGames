package com.mgl.drop.game.hud.aicerunner;

import org.andengine.entity.text.Text;

import android.content.Intent;

import com.google.example.games.basegameutils.ButtonAchievements;
import com.google.example.games.basegameutils.ButtonLeaderboard;
import com.mgl.base.ButtonListener;
import com.mgl.base.userinfo.UserInfo;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.hud.MyHud;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.aicerunner.SpriteCoin;
import com.mgl.drop.game.sprites.button.ButtonGeneral;
import com.mgl.drop.game.sprites.button.DisableMusicButton;
import com.mgl.drop.game.sprites.button.EnableMusicButton;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.penguinsnow.R;

public class LooseHUD extends MyHud {

	private TextureSingleton texture = TextureSingleton.getInstance();
	private ButtonGeneral replay;
	private ButtonGeneral share;

	public LooseHUD(LevelController controller) {
		try {

			addReplayButton(controller);

			addShareButton(controller);

			addTopButton(controller);

			addSoundButtons();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addSoundButtons() {
		try {

			DisableMusicButton disable = new DisableMusicButton(0, 0,
					texture.getTextureByName("soundOff.png"),
					texture.getVertexBufferObjectManager(), this);
			disable.setPosition(15, 15);
			disable.setZIndex(ZIndexGame.FADE);
			disable.setSize(60, 60);
			
			EnableMusicButton enable = new EnableMusicButton(0, 0,
					texture.getTextureByName("soundOn.png"),
					texture.getVertexBufferObjectManager(), this);
			enable.setPosition(disable);
			enable.setZIndex(ZIndexGame.FADE);
			enable.setSize(60, 60);
			
			
			enable.setButton(disable);
			disable.setButton(enable);

			if (SoundSingleton.getInstance().isHasSound()) {
				this.attachChild(disable);
				this.registerTouchArea(disable);
			} else {

				this.attachChild(enable);
				this.registerTouchArea(enable);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addTopButton(LevelController controller) {
		try {
			
			ButtonGeneral background = new ButtonGeneral(0, 0, texture.getTextureByName("buttonTexture.png"), texture.getVertexBufferObjectManager(),null);
			background.setSize(350, 150);
			
			Text text = ObjectFactorySingleton.getInstance().createText(controller.getScoreEntity().getScore()+"", texture.getmFont());
			Text text1 = ObjectFactorySingleton.getInstance().createText(SceneManagerSingleton.getInstance().getActivity().getString(R.string.POINTS), texture.getmFont2());
			
			background.addText(text);
			background.attachChild(text1);
			
			text1.setPosition(background.getWidth()/2 - text1.getWidth()/2, background.getHeight() - text1.getHeight()-20);

			background.setPosition(MainDropActivity.CAMERA_WIDTH/2 - background.getWidth()/2, replay.getY() - replay.getHeight()-55);
			
			this.attachChild(background);
			
			
			int offset = 25;
			
			ButtonLeaderboard leaderboardButton = new ButtonLeaderboard(0, 0, texture.getTextureByName("googleLeaderboard.png"), texture.getVertexBufferObjectManager());
			leaderboardButton.setSize(80, 80);
			leaderboardButton.setPosition(background.getWidth() - offset - leaderboardButton.getWidth(), background.getHeight()/2 - leaderboardButton.getHeight()/2);
			leaderboardButton.setZIndex(ZIndexGame.FADE);
			
			this.registerTouchArea(leaderboardButton);
			background.attachChild(leaderboardButton);
			
			
			ButtonAchievements achievementButton = new ButtonAchievements(0, 0, texture.getTextureByName("googleAchievement.png"), texture.getVertexBufferObjectManager());
			achievementButton.setSize(80,80);
			achievementButton.setPosition( offset , background.getHeight()/2 - achievementButton.getHeight()/2);
			achievementButton.setZIndex(ZIndexGame.FADE);
			
			this.registerTouchArea(achievementButton);
			background.attachChild(achievementButton);
			
			addBestScore(controller);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addBestScore(LevelController controller) {
		try {
			
			ButtonGeneral background = new ButtonGeneral(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager(),null);
			background.setSize(MainDropActivity.CAMERA_WIDTH+150, 270);
			background.setAlpha(0.7f);
			
			background.setX(MainDropActivity.CAMERA_WIDTH/2 - background.getWidth()/2);
			background.setY(MainDropActivity.CAMERA_HEIGHT - background.getHeight());
			this.attachChild(background);
			
			SpriteCoin falling   = new SpriteCoin(0,0, TextureSingleton.getInstance().getTextureAnimateByName("fish.png"), TextureSingleton.getInstance().getVertexBufferObjectManager(),null);
			falling.setSize(80, 80);
			falling.setCollitionType(CollitionType.COLLITION_CIRCULAR);
			falling.setZIndex(ZIndexGame.PLAYER);
			falling.setPosition(background.getWidth()/2 - falling.getWidth()/2  - 20, 5);
			
			background.attachChild(falling);

			Text textFish = ObjectFactorySingleton.getInstance().createText(
					"x " + UserInfoSingleton.getInstance().getMoney(), texture.getmFont2());
			textFish.setPosition(falling.getX()+ falling.getWidth() + 5, +25);
			background.attachChild(textFish);
			
			Text text = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.YOUR_SCORE) +" " + controller.getScoreEntity().getScore(), texture.getmFont2());
			text.setPosition(background.getWidth()/2 - text.getWidth()/2, falling.getHeight() );
			background.attachChild(text);
			
			
			UserInfoSingleton.getInstance().setBestScore(controller.getScoreEntity().getScore());
			int bestScore = UserInfoSingleton.getInstance().getScore(); 
			
			Text text1 = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.BEST_SCORE) +" " + bestScore, texture.getmFont2());
			text1.setPosition(background.getWidth()/2 - text1.getWidth()/2, text.getY()+text.getHeight() + 15);
			background.attachChild(text1);
			
			this.attachChild(background);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addShareButton(LevelController controller) {
		try {

			share = new ButtonGeneral(0, 0,
					texture.getTextureByName("buttonTexture.png"),
					texture.getVertexBufferObjectManager(),
					new ButtonListener() {

						@Override
						public void onActionUp(float x, float y) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onActionMove(float x, float y) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onActionDown(float x, float y) {
							try {
								String message = SceneManagerSingleton
										.getInstance()
										.getActivity()
										.getString(
												R.string.WHATSAPP_SHARE_MESSAGE);
								Intent sendIntent = new Intent();
								sendIntent.setAction(Intent.ACTION_SEND);
								sendIntent.putExtra(Intent.EXTRA_TEXT, message);
								sendIntent.setType("text/plain");
								SceneManagerSingleton.getInstance()
										.getActivity()
										.startActivity(sendIntent);
								SceneManagerSingleton.getInstance()
										.sendGoogleTrack("Share Pressed");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});

			Text text = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.SHARE), texture.getmFont1());

			share.setSize(350, 100);
			share.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - share.getWidth() / 2,
					replay.getY() + replay.getHeight() + 5);

			share.addText(text);

			this.registerTouchArea(share);
			this.attachChild(share);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addReplayButton(LevelController controller) {
		try {

			replay = new ButtonGeneral(0, 0,
					texture.getTextureByName("buttonTexture.png"),
					texture.getVertexBufferObjectManager(),
					new ButtonListener() {

						@Override
						public void onActionUp(float x, float y) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onActionMove(float x, float y) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onActionDown(float x, float y) {
							try {
								SceneManagerSingleton.getInstance()
										.setCurrentScene(AllScenes.GAME_BEGIN);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});

			Text text = ObjectFactorySingleton.getInstance().createText(
					SceneManagerSingleton.getInstance().getActivity().getString(R.string.REPLAY), texture.getmFont1());

			replay.setSize(350, 100);
			replay.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - replay.getWidth() / 2,
					MainDropActivity.CAMERA_HEIGHT / 2 - replay.getHeight() / 2 -50);

			replay.addText(text);

			this.registerTouchArea(replay);
			this.attachChild(replay);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCloseAction() {
		// TODO Auto-generated method stub

	}

}
