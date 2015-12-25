package com.mgl.drop.game.sprites.arunner;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpriteMeteorLevelSelect extends MySprite{

	private float speed = 500;
	private Point pointToMove;
	
	public SpriteMeteorLevelSelect(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		pointToMove = null;
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
			if(this.getX()<-300){
				pointToMove = null;
			}
			
			if(pointToMove==null){
				this.speed = MainDropActivity.getRandomMax(300, 500);
				float x = -500;
				float y = MainDropActivity.getRandomMax(0,(int) (MainDropActivity.CAMERA_HEIGHT-this.getHeight()));
				pointToMove = new Point(x, y);
				x = MainDropActivity.getRandomMax(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_WIDTH+200);
				this.setPosition(x,y);
			}
			
			float distance = speed *dTime;
			float angle = Point.angleBetween(
					new Point(this.getX(), this.getY()), pointToMove);

			float dx = distance * (float) Math.cos(angle);
			float dy = distance * (float) Math.sin(angle);

			this.setPosition(this.getX()+dx,this.getY()+dy);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}