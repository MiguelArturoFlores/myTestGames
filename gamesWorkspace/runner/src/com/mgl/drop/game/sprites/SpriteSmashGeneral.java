package com.mgl.drop.game.sprites;

import java.util.ResourceBundle.Control;


import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpriteSmashGeneral extends MyAnimateSprite{

	private float timeToDesapear=1.5f;
	private float contTime=0;
	private float alpha = 1;
	
	public SpriteSmashGeneral(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
		this.setIgnoreUpdate(true);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.SMASH;
	}

	@Override
	public void initAnimationParams() {
		try {
			changeAnimateState(State.SMASH_VAMPIRE,false);
			anime(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initHashMap() {
		fps = new long[] { 83,83,83};

		stateAnimate.put(State.SMASH_VAMPIRE, new MyAnimateProperty(0, 3,
				fps));
		
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			contTime += dTime;
			if (contTime < timeToDesapear)
				return;

			this.setAlpha(alpha);
			
			alpha = alpha - 0.04f;
			if (alpha <= 0) {
				this.detachSelf();
				level.removeEntity(this);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	
}
