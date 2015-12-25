package com.mgl.drop.game.sprites.arunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpriteIceCircleAnimated extends MyAnimateSprite{
	
	private float speed = 400;
	
	
	public SpriteIceCircleAnimated(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,level);
		// TODO Auto-generated constructor stub
		speed = UserInfoSingleton.getInstance().getPowerC()*100 + 150;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}


	@Override
	public void initAnimationParams() {
		try {

			changeAnimateState(State.WALKIN_RIGHT, false);
			anime(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83, 83,83, 83, 83, 83,83, 83, 83, 83,83, 83, 83 };

			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0, 15,
					fps));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			SpritePlayer player = level.getPlayer();
			
			float distance = speed *dTime;
			
			this.setSize(this.getWidth() + distance, this.getHeight()+distance);
			
			this.setPosition(player.getX()+player.getWidth()/2 -this.getWidth()/2, player.getY()+player.getHeight()/2 - this.getHeight()/2);
			if(!this.isAnimationRunning()){
				this.detachSelf();
				level.removeEntity(this);
			}
			
			for(MySpriteGeneral spr : level.getSpriteList()){
				if(spr.getSpriteType().equals(SpriteType.ENEMY)){
					if(spr instanceof MyAnimateSprite){
						SpriteEnemy sprite = (SpriteEnemy) spr;
						if(Point.distanceBetween(new Point(this.getX()+this.getWidth()/2, this.getY() + this.getHeight()/2), new Point(sprite.getX()+sprite.getWidth()/2, sprite.getY()+sprite.getHeight()/2))< sprite.getWidth()/2 + this.getWidth()/2 - this.getWidth()*0.2f){
							if(!sprite.isFreeze()){
								sprite.setFreeze(true);
								
								SpriteIce ice = (SpriteIce) MyFactory.createObstacle(SpriteTypeConstant.ICE, level);
								ice.setSize(sprite.getWidth()*1.1f, sprite.getHeight()*1.1f);
								ice.setPosition(sprite.getX() + sprite.getWidth()/2 - ice.getWidth()/2,sprite.getY()+sprite.getHeight()/2 - ice.getWidth()/2);
								ice.setEnemy(sprite);
								level.addSpriteToUpdate(ice);
								level.getScene().attachChild(ice);
								
							}
							
						}
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
