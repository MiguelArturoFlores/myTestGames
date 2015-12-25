package com.mgl.drop.game.sprites.ayoutuberunner;

import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.controller.LevelController;

public class SpriteLikeCount extends MySprite{

	private Text text;
	private Long quantity = 0l;
	
	public SpriteLikeCount(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			
			text = ObjectFactorySingleton.getInstance().createText("x "+quantity, texture.getmFont1());
			text.setPosition(this.getWidth(),this.getHeight()/2 - text.getHeight()/2);
			this.attachChild(text);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub
		
	}

	public void updateTextPosition() {
		try {
			
			text.setPosition(this.getWidth(),this.getHeight()/2 - text.getHeight()/2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void increment(int contLikeCoin) {
		try {
			
			this.quantity = (long) contLikeCoin;
			text.detachSelf();
			
			text = ObjectFactorySingleton.getInstance().createText("x"+quantity, texture.getmFont1());
			text.setPosition(this.getWidth(),this.getHeight()/2 - text.getHeight()/2);
			this.attachChild(text);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
