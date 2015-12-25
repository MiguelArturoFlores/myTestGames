package com.mgl.drop.game.sprites;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class SpriteRockBall extends MySprite{

	private SpritePlayer player;
	private float speed = 120;
	
	public SpriteRockBall(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, SpritePlayer player, LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,level);

		this.player = player;
		
		SpriteInvisibleTouch spr = new SpriteInvisibleTouch((0f),(0f),200f,200f,TextureSingleton.getInstance().getTextureByName("black.jpg"),pVertexBufferObjectManager,this);
		
		
		this.attachChild((spr));
		
		spr.setWidth(100);
		spr.setHeight(100);
		spr.setPosition(-spr.getWidth()/2,-spr.getHeight()/2);
		//spr.setAlpha(0.2f);
		spr.setVisible(false);
		level.getScene().registerTouchArea(spr);

	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.ROCK;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			float x = this.getX();
			float y = this.getY();
			float distance = dTime * speed;

			Point currentPoint = new Point(x, y);
			Point pointToMove = new Point(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2);
			if(player == null){
				return;
			}
			if(this.collidesWith(player)){
				if(!player.getStatus().equals(StatusType.PLAYING)){
					level.removeEntity(this);
					level.getScene().detachChild(this);
					return;
				}
				player.setStatus(StatusType.HITED_BY_ROCK);
				level.removeEntity(this);
				level.getScene().detachChild(this);
				return;
			}

			float disAux = Point.distanceBetween(currentPoint, pointToMove);

			if (distance > disAux) {
				this.setPosition(pointToMove.getX(), pointToMove.getY());
				pointToMove = null;
				if(player.getStatus().equals(StatusType.HITED_BY_ROCK)){
					return;
				}
				player.setStatus(StatusType.HITED_BY_ROCK);
				level.removeEntity(this);
				level.getScene().detachChild(this);
				return;
			}
			

			float angle = Point.angleBetween(currentPoint, pointToMove);

			float dx = distance * (float) Math.cos(angle);
			float dy = distance * (float) Math.sin(angle);

			this.setPosition(x + dx, y + dy);

			float dxAux = dx;
			float dyAux = dy;

			if (dxAux < 0) {
				dxAux = dxAux * -1;
			}
			if (dyAux < 0) {
				dxAux = dyAux * -1;
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			
			//player.setStatus(StatusType.HITED_BY_ROCK);
			
			level.removeEntity(this);
			level.getScene().detachChild(this);
			SoundSingleton.getInstance()
			.playSound("rockExplosion.mp3");

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:
			
			
			
			break;
		}
		
		return true;
	}
	

}
