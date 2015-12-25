package com.mgl.drop.game.sprites.arunner;

import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.controller.LevelController;

public class SpriteStarCount extends MySprite{
	
	private int contStar = 0;
	
	private Text text;

	public SpriteStarCount(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		contStar = 0;
		reloadText();
	}

	public void reloadText() {
		try {
			
			if(text!= null){
				text.detachSelf();
			}
			
			text = ObjectFactorySingleton.getInstance().createText("x "+contStar, texture.getmFont1());
			text.setPosition(this.getWidth() +2, this.getHeight()/2 - text.getHeight()/2);
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
		try {
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void increaseStar(){
		try {
			contStar++;
			reloadText();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
}
