package com.mgl.drop.game.sprites.button.aninja;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.aninja.SpriteLever;
import com.mgl.drop.game.sprites.aninja.SpriteWhisthle;
import com.mgl.drop.game.sprites.ninja.SpritePlayer;
import com.mgl.drop.util.Point;

public class ButtonWhisthle extends MySprite{

	private static final float DISTANCE_TO_WISTH = 150;

	public ButtonWhisthle(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
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
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {

			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				
				
				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				actionPress();
				
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private void actionPress() {
		try {
			
			SpritePlayer player = level.getPlayer();
			
			SpriteWhisthle whisthle = (SpriteWhisthle) MyFactory.createObstacle(SpriteTypeConstant.WHISTHLE, level);
			whisthle.setPosition(player.getX()+player.getWidth()/2 - whisthle.getWidth()/2, player.getY()+player.getHeight()/2 - whisthle.getHeight()/2);
			
			level.addSpriteToUpdate(whisthle);
			level.getScene().attachChild(whisthle);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
