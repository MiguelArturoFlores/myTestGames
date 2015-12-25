package com.mgl.drop.game.hud.sprites;

import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.controller.LevelController;

public class SpriteScore extends MySprite{

	public SpriteScore(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,Long number, Long score) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);

		try {
			
			this.setWidth(MainDropActivity.CAMERA_WIDTH-100);
			this.setHeight(100);
			
			Text textNumber = ObjectFactorySingleton.getInstance().createText(number+".",
					texture.getmFont1());
			textNumber.setPosition(120,this.getHeight()/2 - textNumber.getHeight()/2);
			this.attachChild(textNumber);

			Text textScore = ObjectFactorySingleton.getInstance().createText(score+".",
					texture.getmFont1());
			textScore.setPosition(250,this.getHeight()/2 - textScore.getHeight()/2);
			this.attachChild(textScore);
			
			
			
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		
	}
	
	

}
