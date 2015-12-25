package com.mgl.drop.game.sprites.ayoutuberunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteWake extends MySprite{

	private float contTime = 0;
	private float timeToDesapear = 1f;
	
	public SpriteWake(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		setIgnoreUpdate(true);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			if (!mustUpdate) {
				return;
			}

			contTime = contTime + dTime;
			
			float alpha = 1 * contTime/timeToDesapear;
			alpha = 1 - alpha;
			
			float w = 1 * contTime/timeToDesapear;
			w = 1 - w;
			
			this.setSize(this.getWidth()*w, this.getHeight()*w);
			
			if(alpha<0){
				this.detachSelf();
				level.removeEntity(this);
			}
			
			this.setAlpha(alpha);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	
}
