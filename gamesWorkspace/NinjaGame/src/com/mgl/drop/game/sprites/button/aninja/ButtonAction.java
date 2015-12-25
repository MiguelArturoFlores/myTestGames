package com.mgl.drop.game.sprites.button.aninja;

import java.util.ArrayList;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.aninja.SpriteLever;
import com.mgl.drop.game.sprites.ninja.SpritePlayer;
import com.mgl.drop.util.Point;

public class ButtonAction extends MySprite{

	private static final float DISTANCE_TO_ACTION = 75;

	public ButtonAction(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return null;
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
			
			SpriteLever lever = null; 
			
			SpritePlayer player = level.getPlayer();
			Point playerPoint = new Point(player.getX()+player.getMidPoint().getX(), player.getY()+player.getMidPoint().getY());
			
			for(MySpriteGeneral spr : level.getSpriteList()){
				
				if( !(spr instanceof SpriteLever)){
					continue;
				}
				
				if(lever == null){
					lever = (SpriteLever) spr;
					continue;
				}

				Point p1 = ((SpriteLever) spr).getMidPoint();
				Point p2 = lever.getMidPoint();
				
				float distanceSpr = Point.distanceBetween(p1, playerPoint);
				float distanceLever = Point.distanceBetween(p2, playerPoint);
				
				if(distanceSpr < distanceLever){
					lever = (SpriteLever) spr;
				}
				
			}

			
			if(lever!=null){
				Point p1 = lever.getMidPoint();
				if(Point.distanceBetween(p1, playerPoint)<DISTANCE_TO_ACTION){
					lever.executeAction();
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
