package com.mgl.drop.game.sprites.arunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpriteVulcanoBurble extends MyAnimateSprite {

	public static final Long DRECTION_UP = 0L;
	public static final Long DRECTION_DOWN = 1L;
	private Long direction = 0l;
	private boolean active = true;
	private boolean desapearing = false;
	private Point pointToMove = null;
	private float speed = 90;
	private float distance = 200;
	private float distanceAcc = 0;

	public SpriteVulcanoBurble(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
		init();
	}

	public void init(){
		try {
		
			direction = 0l;
			active = true;
			desapearing = false;
			pointToMove = null;
			speed = 90;
			distance = 200;
			distanceAcc = 0;
			this.setAlpha(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.VULCANO_BURBLE;
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
			fps = new long[] { 83, 83, 83, 83 };

			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0, 4,
					fps));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {

		try {
			if (pointToMove != null) {
				updateMoveToWin(dTime, level);
				return;
			}

			float distance = speed * dTime;
			distanceAcc = distanceAcc + distance;
			float x = MainDropActivity.getRandomMax(0, 4) -2;
			if (direction.equals(0L)) {
				this.setPosition(this.getX()+x, this.getY() - distance);
			} else {
				this.setPosition(this.getX()+x, this.getY() + distance);
			}

			if (distanceAcc >= this.distance) {

				desapearing = true;
				active = false;

			}
			if (desapearing) {
				this.setAlpha(this.getAlpha() - 0.05f);

				if (this.getAlpha() < 0) {
					this.detachSelf();
					level.removeEntity(this);
					return;
				}
			}

			if (!active) {
				return;
			}

			if (this.collidesWith(level.getPlayer())) {
				// SoundSingleton.getInstance().playSound("laught.mp3");
				Vector2 velocity = level.getPlayer().getBody()
						.getLinearVelocity();

				if (direction.equals(0L)) {
					if (velocity.y > -4) {
						velocity.y = -6;
					}
				} else {
					if (velocity.y > -4) {
						velocity.y = 6;
					}
				}
				level.getPlayer().setBurble(true);
				level.getPlayer().getBody().setLinearVelocity(velocity);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateMoveToWin(float dTime, LevelController level) {
		try {
			if(this.getAlpha()<1){
				this.setAlpha(this.getAlpha() + 0.1f);
			}
			
			if(Point.distanceBetween(new Point(this.getX(), this.getY()), pointToMove) < 10){
				this.detachSelf();
				level.removeEntity(this);
				return;
			}
			
			float angle = Point.angleBetween(
					new Point(this.getX(), this.getY()), pointToMove);

			float distance = speed*4 * dTime;
			
			float dx = distance * (float) Math.cos(angle);
			float dy = distance * (float) Math.sin(angle);

			//this.getBody().setLinearVelocity(dx, dy);
			this.setPosition(this.getX() + dx, this.getY() +dy);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Long getDirection() {
		return direction;
	}

	public void setDirection(Long direction) {
		this.direction = direction;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getDistanceAcc() {
		return distanceAcc;
	}

	public void setDistanceAcc(float distanceAcc) {
		this.distanceAcc = distanceAcc;
	}

	public Point getPointToMove() {
		return pointToMove;
	}

	public void setPointToMove(Point pointToMove) {
		this.pointToMove = pointToMove;
	}

}
