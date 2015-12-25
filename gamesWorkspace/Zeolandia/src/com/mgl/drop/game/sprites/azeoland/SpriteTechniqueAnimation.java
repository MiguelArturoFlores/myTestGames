package com.mgl.drop.game.sprites.azeoland;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpriteTechniqueAnimation extends MyAnimateSprite{

	private SpritePlayerBattle player;
	private Point centerPoint;
	
	
	public SpriteTechniqueAnimation(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level,long[] fps) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level,fps);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}
	
	public void init(){
		try {
			
			changeAnimateState(State.NORMAL, false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initAnimationParams() {
		try {

			changeAnimateState(State.NORMAL, false);
			anime(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		try {
			//fps = new long[] { 100, 100, 100};
			
			stateAnimate.put(State.NORMAL, new MyAnimateProperty(0, fps.length, fps));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			
			if(!isAnimationRunning()){
				this.detachSelf();
				level.removeEntity(this);
				player.setAlpha(1f);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public SpritePlayerBattle getPlayer() {
		return player;
	}

	public void setPlayer(SpritePlayerBattle player) {
		this.player = player;
	}

	public Point getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(Point centerPoint) {
		this.centerPoint = centerPoint;
	}

}
