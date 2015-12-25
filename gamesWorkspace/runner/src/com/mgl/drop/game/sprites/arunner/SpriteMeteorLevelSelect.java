package com.mgl.drop.game.sprites.arunner;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpriteMeteorLevelSelect extends MyAnimateSprite{

	private float speed = 200;
	private Point pointToMove;
	
	public SpriteMeteorLevelSelect(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,null);
		// TODO Auto-generated constructor stub
		pointToMove = null;
	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.METEOR;
	}

	@Override
	public void initAnimationParams() {
		try {

			changeAnimateState(State.WALKIN_RIGHT, false);
			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initHashMap() {
		try {

			try {

				fps = new long[] { 83, 83, 83, 83 };

				stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0,
						3, fps));

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			
			try {
				if(!mustUpdate){
					return;
				}
				if(this.getY()<-100){
					pointToMove = null;
				}
				
				if(pointToMove==null){
					this.speed = MainDropActivity.getRandomMax(50, 120);
					float x = MainDropActivity.getRandomMax(0,(int) (MainDropActivity.CAMERA_WIDTH-this.getWidth()));
					float y = -100;
					pointToMove = new Point(x, y);
					float size = MainDropActivity.getRandomMax(20,50);
					this.setSize(size, size);
					this.setAlpha(0.5f);
					y = MainDropActivity.getRandomMax(MainDropActivity.CAMERA_HEIGHT +50, MainDropActivity.CAMERA_HEIGHT+450);
					this.setPosition(x,y);
				}
				
				float distance = speed *dTime;
				float angle = Point.angleBetween(
						new Point(this.getX(), this.getY()), pointToMove);

				float x = MainDropActivity.getRandomMax(0, 2) -1;
				
				float dx = distance * (float) Math.cos(angle);
				float dy = distance * (float) Math.sin(angle);

				this.setPosition(this.getX()+dx + x,this.getY()+dy);

				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			
		}
	}

	
	
}