package com.mgl.drop.game.sprites.arunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpriteStar extends MyAnimateSprite {

	private static final float MIN_DISTANCE_TO_UPDATE = 300;
	private boolean collected = false;

	public SpriteStar(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,LevelController controller) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,controller);
		collected = false;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.STAR;
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			SpritePlayer player = level.getPlayer();
			
			if(Point.distanceBetween(new Point(player.getX(), player.getY()), new Point(this.getX(), this.getY()))>MIN_DISTANCE_TO_UPDATE){
				return;
			}
			
			if (this.collidesWith(level.getPlayer()) && !collected) {
				collected = true;
				changeAnimateState(State.DIYING, false);
				SoundSingleton.getInstance().playStar();
				
			}
			if (collected == true) {
				if(!isAnimationRunning()){
					this.detachSelf();
				}
				return;
			}

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

}
