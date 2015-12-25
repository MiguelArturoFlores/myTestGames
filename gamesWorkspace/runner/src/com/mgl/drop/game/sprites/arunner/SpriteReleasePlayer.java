package com.mgl.drop.game.sprites.arunner;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.google.android.gms.games.Player;
import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.util.Point;

public class SpriteReleasePlayer extends MySprite {

	private static final float MIN_DISTANCE_TO_UPDATE = 1000;
	private boolean isFinish = false;
	private int maxDistance = 450;
	private boolean hasPlayer = false;
	private float contBurble = 0;
	private float contBurbleBegin = 0;
	private boolean updateWithCheckpoint  = false;
	private boolean generateBurbles = true;
	
	private boolean hasSound = false;

	public SpriteReleasePlayer(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		hasPlayer = false;
		hasSound = false;
		updateWithCheckpoint  = false;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		if (isFinish) {
			return SpriteType.FINISH_POINT;
		}
		return SpriteType.BEGIN_POINT;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			if (!this.mustUpdate) {
				return;
			}
			if(!isFinish && generateBurbles){
				if(!hasSound){
					SoundSingleton.getInstance().playPlayerRelease();
					hasSound = true;
				}
				
				if (Point.distanceBetween(
						new Point(this.getX(), this.getY() + this.getHeight() / 2),
						new Point(level.getPlayer().getX(), level.getPlayer()
								.getY())) > 350) {
					generateBurbles = false;
				}
				contBurbleBegin = contBurbleBegin + dTime;
				if(contBurbleBegin>0.1f){
					generateBurblesBegin(dTime,level);
					contBurbleBegin = 0;
				}
				
			}
			if (!isFinish) {
				if(level.getCheckPoint()!=null){
					if(Point.distanceBetween(new Point(level.getCheckPoint().getX(), level.getCheckPoint().getY()), new Point(this.getX(), this.getY())) < 500 ){
						if(hasPlayer  == false){
							updateReleasePlayer(level);
						}
						return;
					}
				}
				updateReleasePlayer(level);
				return;
			}

			contBurble = contBurble  + dTime;
			if(contBurble>0.3f){
				generateBurbles(level);
				contBurble = 0;
			}

			if (Point.distanceBetween(
					new Point(this.getX(), this.getY() + this.getHeight() / 2),
					new Point(level.getPlayer().getX(), level.getPlayer()
							.getY())) > maxDistance) {
				return;
			}

			if (Point.distanceBetween(new Point(this.getX() + this.getWidth(),
					this.getY() + this.getHeight() / 2), new Point(level
					.getPlayer().getX() + level.getPlayer().getWidth(), level
					.getPlayer().getY())) < 150) {
				level.winLevelShowScore();
				System.out.println("WIIIIINNNNNNNNN");
				return;
			}

			level.getPlayer().setPositionToWin(
					new Point(this.getX() + this.getWidth(), this.getY()
							+ this.getHeight() / 2));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void generateBurblesBegin(float dTime, LevelController level) {
		try {
			
			SpritePlayer player = level.getPlayer();
			
			if(Point.distanceBetween(new Point(player.getX(), player.getY()), new Point(this.getX(), this.getY()))>MIN_DISTANCE_TO_UPDATE){
				return;
			}
			
			for (int i = 0; i < MainDropActivity.getRandomMax(18, 25); i++) {
				SpriteVulcanoBurble burble = (SpriteVulcanoBurble) MyFactory
						.createObstacle(SpriteTypeConstant.BURBLE_VULCANO,
								level);

				float x =  MainDropActivity.getRandomMax((int)(this.getX()+this.getWidth() -200) , (int)(this.getX()+this.getWidth() -150));
				float y = MainDropActivity
						.getRandomMax((int) (this.getY()+this.getHeight()/2 - 10),
								(int) (this.getY() + this.getHeight()/2+10));

				float w = MainDropActivity.getRandomMax(8, 25);
				// float h = MainDropActivity.getRandomMax(5,20);
				burble.setSize(w, w);

				burble.setPosition(x, y);
				burble.setDirection(SpriteVulcanoBurble.DRECTION_UP);
				x = MainDropActivity.getRandomMax( (int)(this.getX()+350), (int)(this.getX()+450));
				y = MainDropActivity.getRandomMax( (int)(this.getY() + this.getHeight()/2-100), (int)(this.getY() + this.getHeight()/2 +100));
				burble.setPointToMove(new Point(x,y));
				burble.setActive(false);
				burble.setZIndex(ZIndexGame.BURBLE);
				burble.setSpeed(100);
				//burble.setAlpha(0);
				level.getScene().attachChild(burble);
				level.addSpriteToUpdate(burble);

			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void generateBurbles(LevelController level) {
		try {
			SpritePlayer player = level.getPlayer();
			
			if(Point.distanceBetween(new Point(player.getX(), player.getY()), new Point(this.getX(), this.getY()))>MIN_DISTANCE_TO_UPDATE){
				return;
			}
			for (int i = 0; i < MainDropActivity.getRandomMax(15, 25); i++) {
				SpriteVulcanoBurble burble = (SpriteVulcanoBurble) MyFactory
						.createObstacle(SpriteTypeConstant.BURBLE_VULCANO,
								level);

				float x = MainDropActivity.getRandomMax(
						(int) (this.getX() - 250),
						(int) (this.getX() -120));
				float y = MainDropActivity
						.getRandomMax((int) (this.getY()),
								(int) (this.getY() + this.getHeight()));

				float w = MainDropActivity.getRandomMax(8, 25);
				// float h = MainDropActivity.getRandomMax(5,20);
				burble.setSize(w, w);

				burble.setPosition(x, y);
				burble.setDirection(SpriteVulcanoBurble.DRECTION_UP);
				burble.setPointToMove(new Point(this.getX()+200, this.getY()+this.getHeight()/2 - 20));
				burble.setActive(false);
				burble.setZIndex(ZIndexGame.BURBLE);
				burble.setDistance(50);
				burble.setAlpha(0);
				level.getScene().attachChild(burble);
				level.addSpriteToUpdate(burble);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateReleasePlayer(LevelController level) {
		try {
			
			if(level.getCheckPoint()!=null){
				if(Point.distanceBetween(new Point(level.getCheckPoint().getX(), level.getCheckPoint().getY()), new Point(this.getX(), this.getY())) >= 500 ){
					return;
				}
			}
			
			if (!hasPlayer ) {
				updateWithCheckpoint = true;
				hasPlayer = true;
				level.setBodyPixelPositionOld(this.getX() + this.getWidth()
						- 100, this.getY() + this.getHeight() / 2, 0, level
						.getPlayer().getWidth(), level.getPlayer().getHeight(),
						level.getPlayer().getBody());
				
				SpriteCheckPoint check = (SpriteCheckPoint) MyFactory.createObstacle(SpriteTypeConstant.CHECK_POINT, level);
				check.setPosition(level.getPlayer().getX(),level.getPlayer().getY());
				level.setCheckPoint(check);

			}

			
			
			if (Point.distanceBetween(
					new Point(this.getX(), this.getY() + this.getHeight() / 2),
					new Point(level.getPlayer().getX(), level.getPlayer()
							.getY())) > maxDistance) {
				return;
			}

			System.out.println("Setting velocity");

			level.getPlayer().getBody().setLinearVelocity(9, -3f);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateFinishPoint() {

		try {

			if (Point.distanceBetween(
					new Point(this.getX(), this.getY() + this.getHeight() / 2),
					new Point(level.getPlayer().getX(), level.getPlayer()
							.getY())) > maxDistance) {
				return;
			}

			if (Point.distanceBetween(new Point(this.getX() + this.getWidth(),
					this.getY() + this.getHeight() / 2), new Point(level
					.getPlayer().getX() + level.getPlayer().getWidth(), level
					.getPlayer().getY())) < 150) {
				level.winLevelShowScore();
				return;
			}

			level.getPlayer().setPositionToWin(
					new Point(this.getX() + this.getWidth(), this.getY()
							+ this.getHeight() / 2 -20));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

}
