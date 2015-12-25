package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpriteBloodInHud extends MyAnimateSprite {

	private float contTime = 0;
	private float duration = 0.5f;

	public SpriteBloodInHud(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager,null);
		speed = 0;
		
	}

	@Override
	public SpriteType getSpriteType() {
		return SpriteType.DECORATIVE;
	}

	@Override
	public void initAnimationParams() {
		try {

			fps = new long[] { 100, 100, 100, 100 };
			imageNumber = 4;
			Double val = ((Math.random()*10000000)%100);
			if(val<33){
				changeAnimateState(State.WALKIN_DOWN, false);
			}else if(val <66){
				changeAnimateState(State.WALKIN_UP, false);
			}else{
				changeAnimateState(State.WALKIN_RIGHT, false);
			}
			anime(false);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {

		try {

			fps = new long[] { 83, 83, 83, 83 };

			stateAnimate.put(State.WALKIN_DOWN,
					new MyAnimateProperty(0, 4, fps));
			stateAnimate.put(State.WALKIN_UP,
					new MyAnimateProperty(4, 4, fps));
			stateAnimate.put(State.WALKIN_RIGHT,
					new MyAnimateProperty(8, 4, fps));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			contTime += dTime;

			this.setAlpha(this.getAlpha() - 0.005f);
			this.setY(this.getY() + speed * dTime);
			if (this.collidesWith(level.getFloor()) || this.getAlpha()<=0) {
				this.detachSelf();
				level.removeEntity(this);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void reset(){
		try {
			
			contTime = 0;
			this.setAlpha(1);
			
		} catch (Exception e) {

			e.printStackTrace();
		} 
		
	}
	
}
