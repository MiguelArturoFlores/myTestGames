package com.mgl.drop.game.hud.sprites;

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
import com.mgl.drop.game.sprites.SpriteBackground;

public class SpriteMessage extends MySprite{

	private float contToDesapear = 1.2f;
	private String message;
	
	public SpriteMessage(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, String message, float timeToDessapear) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);

		try {
			time = 0;
			this.contToDesapear = timeToDessapear;
			this.message =message;
			
			Text textNumber = ObjectFactorySingleton.getInstance().createText(message,
					texture.getmFont1());

			this.setSize(MainDropActivity.CAMERA_WIDTH , textNumber.getHeight()*1.2f);
			this.setPosition(MainDropActivity.CAMERA_WIDTH/2 - this.getWidth()/2,MainDropActivity.CAMERA_HEIGHT/2 - this.getHeight()/2);
			
			textNumber.setPosition(this.getWidth()/2 - textNumber.getWidth()/2,this.getHeight()/2 - textNumber.getHeight()/2);
			this.attachChild(textNumber);
			
			//SpriteBackground image = new SpriteBackground(200, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager());
			//image.
			//image.setPosition(this.getWidth()/2-image.getWidth()/2, this.getHeight()/2 - image.getHeight()/2);
			
			//this.attachChild(image);
			
			
			
			//this.setWidth(MainDropActivity.CAMERA_WIDTH-100);
			//this.setHeight(100);
			
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
