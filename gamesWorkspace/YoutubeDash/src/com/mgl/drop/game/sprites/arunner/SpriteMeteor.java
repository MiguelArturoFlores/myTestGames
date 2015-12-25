package com.mgl.drop.game.sprites.arunner;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.util.Point;

public class SpriteMeteor extends MySprite{

	private float distanceToActivate = 1200;
	private float speed = 80;
	private boolean isVertical = false;
	private float distanceToShake = 450;
	private boolean hasShake = false;
	private boolean hasSound = false;

	public SpriteMeteor(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		isVertical = false;
		hasShake = false;
	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.METEOR;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			if(!mustUpdate){
				return;
			}
			
			SpritePlayer player = level.getPlayer();
			
			if(Point.distanceBetween(new Point(this.getX(), this.getY()), new Point(player.getX(), player.getY())) > distanceToActivate  ){
				return;
			}
			if(!hasShake){
				Camera cam = SceneManagerSingleton.getInstance().getCamera();
				if((this.getX()+this.getWidth()/2 < cam.getCenterX()+MainDropActivity.CAMERA_WIDTH/2 && this.getX()+this.getWidth()/2 > cam.getCenterX()-MainDropActivity.CAMERA_WIDTH/2) &&
						(this.getY()+this.getHeight()/2 < cam.getCenterY()+MainDropActivity.CAMERA_HEIGHT/2 && this.getY()+this.getHeight()/2 > cam.getCenterY()-MainDropActivity.CAMERA_HEIGHT/2)){
					level.getInvisiblePointToFollow().shake(4,10);
					hasShake = true;
				}
			}
			
			if(Point.distanceBetween(new Point(this.getX(), this.getY()), new Point(player.getX(), player.getY()))< 480 && !hasSound){
				hasSound = true;
				SoundSingleton.getInstance().playMeteor();
			}
			
			//if(this.collidesWith(player)){
			//
			if(Point.distanceBetween(new Point(this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2), new Point(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2))< player.getWidth()/2+this.getWidth()/2){
				level.looseLevelReplay();
			}
			
			float distance = speed *dTime;
			if(isVertical){
				this.setY(this.getY() - distance);
				//this.setX(this.getX() - distance);
			}else{
				this.setX(this.getX() - distance);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void setXmlParameter(String parameter) {
		try {

			ArrayList<Long> parameterList = MyXmlParser
					.getParameterList(parameter);
			int i = 0;
			for (Long param : parameterList) {
				//System.out.println("PARAMETRO DEL METEORO " +param);
				try {
					if (i == 0) {
						speed = param;
					}else if(i == 1){
						
						//isVertical = param.equals(0L) ? false : true;
						if(param == null || param.equals(0L)){
							isVertical = false;
						}else{
							isVertical = true;
						}
					}

				} catch (Exception e) {

				}
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
}
