package com.mgl.drop.game.sprites.arunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpriteADN extends MyAnimateSprite {

	private boolean collected = false;
	private int quantity = 1;

	public SpriteADN(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,LevelController controller) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,controller);
		
		collected = false;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.ADN;
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			SpritePlayer player = level.getPlayer();
			
			if(player ==null ){
				return;
			}
			
			if(this.collidesWith(player) && !collected){
				UserInfoSingleton.getInstance().increaseMoneyInGame(quantity);
				level.increaseMoney();
				collected = true;
				changeAnimateState(State.DIYING, false);
			}
			
			if(collected){
				if(!isAnimationRunning()){
					this.detachSelf();
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initAnimationParams() {
		try {
		
			changeAnimateState(State.NORMAL, true);
			anime(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initHashMap() {
		try {
			
			fps = new long[] { 83, 83, 83, 83, 83, 83, 83, 83 };

			stateAnimate.put(State.NORMAL, new MyAnimateProperty(0, 2,
					new long[] { 83, 83}));
			stateAnimate.put(State.DIYING, new MyAnimateProperty(2, 6,
					new long[] { 83, 83, 83, 83, 83, 83 }));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
	
}
