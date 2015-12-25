package com.mgl.drop.game.sprites.arunner;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.StatusType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class SpriteMoveControll extends MySprite{

	private SpriteBackground ball = null;
	
	public SpriteMoveControll(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, LevelController controller) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager, controller);
		try {
			
			ball = (SpriteBackground) MyFactory.createObstacle(SpriteTypeConstant.CONTROLL_BALL, null);
			ball.setSize(this.getWidth()/6, this.getHeight()/6);
			ball.setPosition(this.getWidth()/2 - ball.getWidth()/2, this.getHeight()/2 
					- ball.getHeight()/2);
			this.attachChild(ball);
			
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
	
	@Override
	public void setSize(float x, float y){
		try {
			
			this.setWidth(x);
			this.setHeight(y);
			ball.setSize(this.getWidth()/8, this.getHeight()/8);
			ball.setPosition(this.getWidth()/2 - ball.getWidth()/2, this.getHeight()/2 
					+ this.getHeight()/2- ball.getHeight()/2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {

			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

				
				
				break;
			case TouchEvent.ACTION_MOVE:
				
				
				ball.setPosition(pTouchAreaLocalX - ball.getWidth()/2, pTouchAreaLocalY - ball.getHeight()/2);
				
				Point midPoint = new Point(this.getWidth()/2 - ball.getWidth()/2, this.getHeight()/2 
						- ball.getHeight()/2);
				float distanceTotal = Point.distanceBetween(new Point(this.getX()+75, this.getY()+75 + this.getHeight()), midPoint);
				distanceTotal = 75;
				float distanceCurrent = Point.distanceBetween(midPoint, new Point(pTouchAreaLocalX - ball.getWidth()/2, pTouchAreaLocalY - ball.getHeight()/2));
				
				if(distanceCurrent>distanceTotal){
					distanceCurrent = distanceTotal;
				}
				
				float percentage = distanceCurrent*100/distanceTotal;
				
				if(distanceCurrent< distanceTotal/2){
					percentage = 50;
				}else{
					percentage=100;
				}
				//percentage=100;
				
				level.getPlayer().moveInDirection(new Point(this.getWidth()/2 , this.getHeight()/2 ),new Point(ball.getX()+ball.getWidth()/2, ball.getY()+ball.getHeight()/2),percentage);

				break;
			case TouchEvent.ACTION_UP:

				ball.setPosition(this.getWidth()/2 - ball.getWidth()/2, this.getHeight()/2 
						- ball.getHeight()/2);
				//level.getPlayer().moveInDirection(new Point(this.getWidth()/4 , this.getHeight()/2 + this.getHeight()/4),new Point(ball.getX()+ball.getWidth()/2, ball.getY()+ball.getHeight()/2));
				level.getPlayer().slowReleaseButton();
				break;
			default:

				break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean isRight() {
		try {
			
			if(ball.getX()>=this.getWidth()/2 - ball.getWidth()/2){
				return true;
			}
			
			return false;
		} catch (Exception e) {
		}
		return false;
	}

	public Point getPointCenter() {
		
		try {
			
			return new Point(this.getWidth()/2 , this.getHeight()/2 );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Point(0, 0);
	}

	public Point getPointBall() {
		
		try {
			
			return new Point(ball.getX()+ball.getWidth()/2, ball.getY()+ball.getHeight()/2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Point(0, 0);
	}
	
}
