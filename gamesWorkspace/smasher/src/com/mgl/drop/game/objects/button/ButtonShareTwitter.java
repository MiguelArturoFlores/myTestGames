package com.mgl.drop.game.objects.button;

import org.andengine.input.touch.TouchEvent;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.LooseHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.twitter.TwitterSingleton;

public class ButtonShareTwitter extends MySprite{

	private float score;
	private boolean activated = false;
	
	private LooseHUD looseHUD;
	private int message;
	
	public ButtonShareTwitter(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, float score) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		this.score = score;
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

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {

			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:
				if(looseHUD!=null && !activated){
					
					TwitterSingleton.getInstance().postYoutuberTweet(score);
					UserInfoSingleton.getInstance().increaseTwitterShare();
					UserInfoSingleton.getInstance().increaseMoney(50);
					
					activated = true;
					return true;
				}
				
				if(!activated ){
					TwitterSingleton.getInstance().postYoutuberTweet(score);
					UserInfoSingleton.getInstance().increaseTwitterShare();
					this.detachSelf();
					activated = true;
					HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("You have share your score thanks!"), true);
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public LooseHUD getLooseHUD() {
		return looseHUD;
	}

	public void setLooseHUD(LooseHUD looseHUD) {
		this.looseHUD = looseHUD;
	}

	public int getMessage() {
		return message;
	}

	public void setMessage(int message) {
		this.message = message;
	}

	
	
}
