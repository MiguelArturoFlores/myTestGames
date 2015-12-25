package com.mgl.drop.game.sprites.arunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpritePlayerShot extends MyAnimateSprite{

	private MySpriteGeneral target;
	private float linearVelocity = 9;
	private boolean mustKillEnemy = false;
	private float distanceToMove = 2000;
	
	private float distance = 0;
	private float maxDistance = 800;
	private Point pointToMove;
	
	public SpritePlayerShot(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager, level);
		mustKillEnemy = false;
		target = null;
		distance = 0;
		maxDistance = UserInfoSingleton.getInstance().getPowerA()*50 + 150;
	}

	@Override
	public SpriteType getSpriteType() {
		return SpriteType.SHOT;
	}



	private void updateMovingToPoint(Point point) {
		try {

			float angle = Point.angleBetween(
					new Point(this.getX(), this.getY()), point);

			float dx = linearVelocity * (float) Math.cos(angle);
			float dy = linearVelocity * (float) Math.sin(angle);
			
			this.getBody().setLinearVelocity(dx, dy);

			if(dx<0){
				dx=dx*-1;
			}
			
			distance = distance + dx;


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public MySpriteGeneral getTarget() {
		return target;
	}

	public void setTarget(MySpriteGeneral target) {
		this.target = target;
	}

	public float getLinearVelocity() {
		return linearVelocity;
	}

	public void setLinearVelocity(float linearVelocity) {
		this.linearVelocity = linearVelocity;
	}

	private void killEnemy() {
		try {
			destroyShot();
			if (target != null) {

				if (target instanceof SpriteEnemyBasic) {
					SpriteEnemyBasic enemy = (SpriteEnemyBasic) target;
					enemy.killEnemy(SpriteType.FINGER);
				} else if (target instanceof SpriteEnemyTriangle) {
					SpriteEnemyTriangle enemy = (SpriteEnemyTriangle) target;
					enemy.killEnemy(SpriteType.FINGER);

				} else if (target instanceof SpriteEnemyRombe) {
					SpriteEnemyRombe enemy = (SpriteEnemyRombe) target;
					enemy.killEnemy(SpriteType.FINGER);

				} else if (target instanceof SpriteEnemyRombeBurble) {
					SpriteEnemyRombeBurble enemy = (SpriteEnemyRombeBurble) target;
					enemy.killEnemy(SpriteType.FINGER);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void destroyShot() {
		try {

			
			changeAnimateState(State.DIYING, false);
			
			//PhysicSingleton.getInstance().removeBody(body);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean isMustKillEnemy() {
		return mustKillEnemy;
	}

	public void setMustKillEnemy(boolean mustKillEnemy) {
		this.mustKillEnemy = mustKillEnemy;
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83};

			stateAnimate.put(State.NORMAL, new MyAnimateProperty(0, 2,
					fps));
			
			stateAnimate.put(State.DIYING, new MyAnimateProperty(2, 3,
					new long[] { 83, 83, 83}));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initAnimationParams() {
		try {
			
			changeAnimateState(State.NORMAL, true);
			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			if(this.currentState.equals(State.DIYING)){
				if(!isAnimationRunning()){
					this.detachSelf();
					level.removeEntity(this);
				}
				return;
			} 
			
			if (mustKillEnemy) {
				
				killEnemy();

			}
			if (distance>maxDistance) {
				destroyShot();
				return;
			}
			
				updateMovingToPoint(pointToMove);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public float getDistanceToMove() {
		return distanceToMove;
	}

	public void setDistanceToMove(float distanceToMove) {
		this.distanceToMove = distanceToMove;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(float maxDistance) {
		this.maxDistance = maxDistance;
	}

	public Point getPointToMove() {
		return pointToMove;
	}

	public void setPointToMove(Point pointToMove) {
		this.pointToMove = pointToMove;
	}
	
	
}
