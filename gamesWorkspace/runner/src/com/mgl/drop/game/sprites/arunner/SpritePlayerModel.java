package com.mgl.drop.game.sprites.arunner;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.InformativeSpriteHUD;
import com.mgl.drop.game.hud.TwitterLoginHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.game.scene.ScenePowerUp;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.twitter.TwitterSingleton;

public class SpritePlayerModel extends MyAnimateSprite {

	private boolean isUpgrade = true;
	private int playerSelcet;
	
	public SpritePlayerModel(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
		isUpgrade = true;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
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

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		try {
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				
				SoundSingleton.getInstance().playButtonSound();
				
				if(isUpgrade){
					SceneManagerSingleton.getInstance().setCurrentScene(AllScenes.POWER_UP);
					return true;
				}
				
				//validate if is unlocked
				
				if(playerSelcet == GameConstants.PLAYER_C){
					
					if(!TwitterSingleton.getInstance().isLoggedIn()){

						TwitterLoginHUD hud = new TwitterLoginHUD();
						HUDManagerSingleton.getInstance().addHUD(hud, true);
						return false;
					}
				}
				
				
				UserInfoSingleton.getInstance().setPlayerSelect(playerSelcet);
				SceneManagerSingleton.getInstance().setCurrentScene(AllScenes.POWER_UP);
				
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

	public int getPlayerSelcet() {
		return playerSelcet;
	}

	public void setPlayerSelcet(int playerSelcet) {
		this.playerSelcet = playerSelcet;
	}

	
	
}
