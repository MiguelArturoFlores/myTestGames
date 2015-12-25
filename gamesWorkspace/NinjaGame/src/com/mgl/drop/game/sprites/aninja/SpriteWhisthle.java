package com.mgl.drop.game.sprites.aninja;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.ninja.SpritePlayer;
import com.mgl.drop.util.Point;

public class SpriteWhisthle extends MyAnimateSprite{

	private static final float DISTANCE_TO_CURIOUS = 200;
	private boolean isActive = false;


	public SpriteWhisthle(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void initAnimationParams() {

		try {

			changeAnimateState(State.NORMAL, false);
			anime(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83 };

			stateAnimate.put(State.NORMAL, new MyAnimateProperty(0, 12, fps));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			
			if(isActive ){
				if(!isAnimationRunning()){
					this.detachSelf();
					level.removeEntity(this);
				}
				return;
			}
			
			isActive = true;
			
			SpritePlayer player = level.getPlayer();
			Point playerP = new Point(player.getMidPoint().getX()+player.getX(),player.getMidPoint().getY()+player.getY());
			
			for(MySpriteGeneral spr : level.getSpriteList()){
				
				if(spr instanceof SpriteEnemy){
					
					SpriteEnemy enemy = (SpriteEnemy) spr;
					
					Point enemyP = new Point(enemy.getMidPoint().getX()+enemy.getX(),enemy.getMidPoint().getY()+enemy.getY());
					
					if(Point.distanceBetween(playerP, enemyP)<DISTANCE_TO_CURIOUS){
						enemy.lookForWhisthle(playerP);
					}
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
