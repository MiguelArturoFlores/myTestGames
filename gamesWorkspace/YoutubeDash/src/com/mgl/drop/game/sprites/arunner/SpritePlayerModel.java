package com.mgl.drop.game.sprites.arunner;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.model.PlayerDB;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;

public class SpritePlayerModel extends MySprite {

	private boolean isUpgrade = true;
	private int playerSelect;
	private PlayerDB player;
	
	private String leaderboardName;
	
	public SpritePlayerModel(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
		isUpgrade = true;
		try {
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	/*@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83 };

			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0, 11,
					fps));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initAnimationParams() {
		try {

			changeAnimateState(State.WALKIN_RIGHT, true);
			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/

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
				
				SceneManagerSingleton.getInstance().sendGoogleTrack("Selected Player"+playerSelect);
				UserInfoSingleton.getInstance().setPlayerSelect(playerSelect);
				//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD(playerSelect+""), true);
				SceneManagerSingleton sceneManager =SceneManagerSingleton.getInstance();
				sceneManager.setCurrentScene(AllScenes.SELECT_LEVEL);
				/*
				sceneManager.createGameScene(null,null ,GameConstants.PLAY_NORMAL);
				sceneManager.setCurrentScene(AllScenes.GAME_BEGIN);
				*/
				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean isUpgrade() {
		return isUpgrade;
	}

	public void setUpgrade(boolean isUpgrade) {
		this.isUpgrade = isUpgrade;
	}

	public int getPlayerSelect() {
		return playerSelect;
	}

	public void setPlayerSelect(int playerSelect) {
		this.playerSelect = playerSelect;
		try {
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PlayerDB getPlayer() {
		return player;
	}

	public void setPlayer(PlayerDB player) {
		this.player = player;
		try {
			
			playerSelect = player.getId().intValue();
			
			Text text = ObjectFactorySingleton.getInstance().createText(
					"" + player.getName() , texture.getmFont1());
			text.setPosition(this.getWidth()/2 - text.getWidth()/2, -35);
			text.setColor(Color.BLACK);
			this.attachChild(text);
			
			
			Sprite star1 = new Sprite(0, 0,
					texture.getTextureByName("likeCoin.png"),
					texture.getVertexBufferObjectManager());
			star1.setSize(40, 40);
			star1.setPosition(15,
					this.getHeight()+5);
			this.attachChild(star1);
			
			Text textStar =  ObjectFactorySingleton.getInstance().createText(
					"x " + player.getLikesQuantity() , texture.getmFont1());;
			textStar.setPosition(star1.getX()+star1.getWidth(), star1.getY()+10);
			textStar.setZIndex(ZIndexGame.FADE);
			textStar.setColor(Color.BLACK);
			this.attachChild(textStar);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getLeaderboardName() {
		return leaderboardName;
	}

	public void setLeaderboardName(String leaderboardName) {
		this.leaderboardName = leaderboardName;
	}

	
	
}
