package com.mgl.drop.game.sprites.ayoutuberunner;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class SpriteStarGenerator extends MySprite {

	private boolean active = false;

	private float r = 90;
	private float g = 90;
	private float b = 90;

	private float timeGenerate = 2;

	private float distance = 1600;
	private float quantity = 5;

	private float contTime = 0;

	private float timeGenerateSmall = 0.2f;

	private float contTimeGenerate = 0;

	public SpriteStarGenerator(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		setIgnoreUpdate(true);

	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {

			if (this.collidesWith(level.getPlayer())) {
				active = true;
			}
			if (active) {

				contTime = contTime + dTime;

				if (contTime > timeGenerate) {
					level.removeEntity(this);
					this.detachSelf();
				}

				contTimeGenerate = contTimeGenerate + dTime;
				if (contTimeGenerate > timeGenerateSmall) {
					generateStar(quantity, level);
					contTimeGenerate = 0;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setAutoUpdate() {
		try {
			setIgnoreUpdate(false);
			time = 1;
			this.registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() {
				}

				
				@Override
				public void onUpdate(float pSecondsElapsed) {
					time += pSecondsElapsed;
					if(time<0.5f){
						return;
					}
					time = 0;
					for (int i = 0; i < 40; i++) {

						float x = MainDropActivity.getRandomMax(0, MainDropActivity.CAMERA_WIDTH);
						float y = MainDropActivity.getRandomMax(0, MainDropActivity.CAMERA_HEIGHT);

						float siz = MainDropActivity.getRandomMax(20, 50);

						
						SpriteStarGeometry star = (SpriteStarGeometry) MyFactory
								.createObstacle(SpriteTypeConstant.STAR_GEOMETRY, level);
						star.setPosition( x,  y);
						star.setTimeToDesapear(MainDropActivity.getRandomMax(0, 100)/100f);
						star.setColor(r, g, b);
						star.setZIndex(ZIndexGame.PLAYER - 1);
						star.setSize(siz, siz);
						HUDManagerSingleton.getInstance().getTop().attachChild(star);
						level.addSpriteToUpdate(star);

					}
					
					
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void generateStar(float starNum, LevelController level) {
		try {

			for (int i = 0; i < starNum; i++) {

				float x = MainDropActivity.getRandomMax(0, (int) distance);
				float y = MainDropActivity.getRandomMax(0, 480);

				float siz = MainDropActivity.getRandomMax(20, 50);

				float xP = level.getPlayer().getX() - 200;

				SpriteStarGeometry star = (SpriteStarGeometry) MyFactory
						.createObstacle(SpriteTypeConstant.STAR_GEOMETRY, level);
				star.setPosition(xP + x, this.getY() + y);
				star.setColor(r, g, b);
				star.setZIndex(ZIndexGame.PLAYER - 1);
				star.setSize(siz, siz);
				level.getScene().attachChild(star);
				level.addSpriteToUpdate(star);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setXmlParameter(String parameter) {
		try {

			ArrayList<Long> parameterList = MyXmlParser
					.getParameterList(parameter);
			int i = 0;
			float f = 255;
			for (Long param : parameterList) {
				try {
					if (i == 0) {
						r = param.floatValue() / f;
					}
					if (i == 1) {
						g = param.floatValue() / f;
					}
					if (i == 2) {
						b = param.floatValue() / f;
					}
					if (i == 3) {
						timeGenerate = param.floatValue();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getB() {
		return b;
	}

	public void setB(float b) {
		this.b = b;
	}

	public float getTimeGenerate() {
		return timeGenerate;
	}

	public void setTimeGenerate(float timeGenerate) {
		this.timeGenerate = timeGenerate;
	}

}
