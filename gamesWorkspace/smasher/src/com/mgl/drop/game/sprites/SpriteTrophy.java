package com.mgl.drop.game.sprites;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.model.Trophy;

public class SpriteTrophy extends MySprite{

	private float contToDesapear = 1.2f;
	
	public SpriteTrophy(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,Trophy trophy) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);

		try {
			
			time = 0;
			this.setWidth(MainDropActivity.CAMERA_WIDTH-100);
			this.setHeight(100);
			
			
			SpriteBackground trophyBack = new SpriteBackground(0, 0, texture.getTextureByName(trophy.isUnlock() ? "cadizT.png" : "trofeoGris.png"), texture.getVertexBufferObjectManager());
			trophyBack.setSize(140, 140);
			trophyBack.setPosition(0, this.getHeight()/2 - trophyBack.getHeight()/2);
			this.attachChild(trophyBack);
			
			
			Text textNumber = ObjectFactorySingleton.getInstance().createText(trophy.getTextName()+"",
					texture.getmFont1());
			textNumber.setPosition(this.getWidth()/2 - textNumber.getWidth()/2,this.getHeight()/2 - textNumber.getHeight()/2);
			this.attachChild(textNumber);

			
			SpriteBackground image = new SpriteBackground(200, 0, texture.getTextureByName(trophy.getTextureName()), texture.getVertexBufferObjectManager());
			image.setSize(100, 100);
			image.setPosition(this.getWidth()-image.getWidth(), this.getHeight()/2 - image.getHeight()/2);
			this.attachChild(image);
			
			
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

	public void setAutoUpdate() {
		try {
			
			this.registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() {
				}

				@Override
				public void onUpdate(float pSecondsElapsed) {
					// HERE IS THE GAME LOOP
					time += pSecondsElapsed;
					// System.out.println("this is the time elapsed MAIN SCENE: "+time);
					if(time >contToDesapear){
						if(!getSprite().hasParent()){
							return;
						}
						getSprite().detachSelf();
						
						//unregisterUpdateHandler(this);
					}
					
				}

				
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private Sprite getSprite() {
		return this;
		
	}

}