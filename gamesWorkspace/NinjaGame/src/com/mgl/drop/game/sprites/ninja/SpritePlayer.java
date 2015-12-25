package com.mgl.drop.game.sprites.ninja;

import java.util.Stack;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.aninja.SpriteEnemy;
import com.mgl.drop.game.sprites.aninja.SpriteLever;
import com.mgl.drop.game.sprites.aninja.SpriteSmoke;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class SpritePlayer extends MyAnimateSprite {
	
	private static final float DISTANCE_TO_ACTION = 75;

	private Stack<Point> path;
	private Point pointToMove;
	private float speed = 200;

	private float contIdle = 0;
	private float timeToIdle = 2;

	private Animator animator;
	private SpriteEnemy enemyToAttack = null;

	private SpriteLever lever = null;
	
	public SpritePlayer(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		path = new Stack<Point>();
		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			animator = new Animator(0, 0,
					texture.getTextureAnimateByName("playerC2.png"),
					texture.getVertexBufferObjectManager(), level);
			animator.setPosition(this.getWidth() / 2 - animator.getWidth() / 2,
					this.getHeight() / 2 - animator.getHeight() / 2);
			animator.setAlpha(0);
			this.attachChild(animator);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.PLAYER;
	}

	@Override
	public void initAnimationParams() {
		try {

			changeAnimateState(State.IDLE, true);
			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83, 83, 83, 83, 83, 83, 83 };

			stateAnimate.put(State.WALKIN_LEFT, new MyAnimateProperty(12, 9,
					fps));
			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(21, 9,
					fps));

			stateAnimate
					.put(State.WALKIN_UP, new MyAnimateProperty(12, 9, fps));
			stateAnimate.put(State.WALKIN_DOWN, new MyAnimateProperty(21, 9,
					fps));

			stateAnimate.put(State.NORMAL,
					new MyAnimateProperty(0, 12, new long[] { 83, 83, 83, 83,
							83, 83, 83, 83, 83, 83, 83, 83 }));

			stateAnimate.put(State.IDLE,
					new MyAnimateProperty(0, 12, new long[] { 83, 83, 83, 83,
							83, 83, 83, 83, 83, 83, 83, 83 }));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			updateValidatingAnimation();

			if(lever!=null){
				updateOpenLever(dTime,level);
			}
			
			updateMoving(dTime, level);

			updateIdle(dTime, level);
			
			updateAttackingEnemy(dTime, level);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateOpenLever(float dTime, LevelController level) {
		try {
			
			generatePathToPoint(new Point(lever.getMidPoint().getX(), lever.getMidPoint().getY()));
			
			Point p1 = lever.getMidPoint();
			Point playerPoint = new Point(this.getX()+this.getMidPoint().getX(), this.getY()+this.getMidPoint().getY());
			
			float distanceSpr = Point.distanceBetween(p1, playerPoint);
			
			if(distanceSpr < DISTANCE_TO_ACTION){
				lever.executeAction();
				lever = null;
			}
			
			if(path == null || path.isEmpty()){
				lever = null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateAttackingEnemy(float dTime, LevelController level) {
		try {
			
			if(enemyToAttack == null){
				
				return;
			}
			
			generatePathToPoint(new Point(enemyToAttack.getX()+enemyToAttack.getMidPoint().getX(), enemyToAttack.getY()+enemyToAttack.getMidPoint().getY()));
			
			if(Point.distanceBetween(new Point(this.getX()+getMidPoint().getX(), this.getY()+getMidPoint().getY()), new Point(enemyToAttack.getX()+enemyToAttack.getMidPoint().getX(), enemyToAttack.getY()+enemyToAttack.getMidPoint().getY()))<50){
				enemyToAttack.recieveAttack();
				enemyToAttack.hidePointer();
				enemyToAttack = null;
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateValidatingAnimation() {
		try {

			if (this.currentState.equals(State.WALKIN_RIGHT)) {
				animator.setAlpha(1);
				this.setAlpha(0);
				if (animator.isFlippedHorizontal()) {
					animator.setFlippedHorizontal(false);
				}
			} else if (this.currentState.equals(State.WALKIN_LEFT)) {
				animator.setAlpha(1);
				this.setAlpha(0);
				if (!animator.isFlippedHorizontal()) {
					animator.setFlippedHorizontal(true);
				}

			} else {
				this.setAlpha(1);
				animator.setAlpha(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateIdle(float dTime, LevelController level) {
		try {

			if (!path.isEmpty() || pointToMove != null) {
				return;
			}

			/*
			 * if(currentState.equals(State.IDLE) || !isAnimationRunning()){
			 * changeAnimateState(State.NORMAL, false); }
			 */

			contIdle += dTime;
			if (contIdle < timeToIdle) {
				return;
			}
			contIdle = 0;
			changeAnimateState(State.IDLE, false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateMoving(float dTime, LevelController level) {
		try {

			if (path == null || (path.isEmpty() && pointToMove == null)) {
				changeAnimateState(State.IDLE, true);
				return;
			}

			if (pointToMove == null) {
				pointToMove = path.pop();
			}

			updateMovingToPoint(dTime);
			// this.setPosition(pointToMove.getX()+6 -
			// this.getWidth()/2,pointToMove.getY()+8 - this.getHeight()/2);
			// pointToMove = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	

	
	
	
	private void updateMovingToPoint(float dTime) {
		try {

			Point currentPoint = new Point(this.getX() + getMidPoint().getX(),
					this.getY() + getMidPoint().getY());

			float distance = Point.distanceBetween(currentPoint, pointToMove);
			float angle = Point.angleBetween(currentPoint, pointToMove);

			float speedX = (float) (Math.cos(angle) * speed);
			float speedY = (float) (Math.sin(angle) * speed);

			float distanceX = speedX * dTime;
			float distanceY = speedY * dTime;

			this.setPosition(this.getX() + distanceX, this.getY() + distanceY);

			angle = (float) (angle * 180 / Math.PI);
			int minDistanceTochange = 40;

			// right
			if (angle >= -45 && angle <= 45) {
				changeAnimateState(State.WALKIN_RIGHT, true);
				// down
			} else if (angle >= 45 && angle <= 135) {
				changeAnimateState(State.WALKIN_DOWN, true);
				// up
			} else if (angle >= -135 && angle <= -45) {
				changeAnimateState(State.WALKIN_UP, true);

				// left
			} else {
				changeAnimateState(State.WALKIN_LEFT, true);
			}

			if (distance < 5f) {
				pointToMove = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Point getMidPoint() {
		try {

			return new Point(this.getWidth() / 2, this.getHeight() * 0.75f);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void generatePathToPoint(Point pointToMove) {
		try {

			path = level.getLevelWorld().calculatePath(
					new Point(this.getX() + getMidPoint().getX(), this.getY()
							+ getMidPoint().getY()), pointToMove, 1200);
			//changeAnimateState(State.IDLE, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public class Animator extends MyAnimateSprite {

		public Animator(float pX, float pY, ITextureRegion pTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager,
				LevelController level) {
			super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
			// TODO Auto-generated constructor stub
		}

		@Override
		public SpriteType getSpriteType() {
			// TODO Auto-generated method stub
			return SpriteType.DECORATIVE;
		}

		@Override
		public void initAnimationParams() {

			changeAnimateState(State.WALKIN_RIGHT, true);
			anime(true);

		}

		@Override
		public void initHashMap() {

			try {

				stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0,
						12, new long[] { 83, 83, 83, 83, 83, 83, 83, 83, 83,
								83, 83, 83 }));

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void updateAnimated(float dTime, LevelController level) {
			// TODO Auto-generated method stub

		}

	}

	public void setEnemyToAttack(SpriteEnemy spriteEnemy) {
		try {
			
			if(enemyToAttack!=null && spriteEnemy == null){
				enemyToAttack.hidePointer();
			}
			this.enemyToAttack = spriteEnemy;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openLever(SpriteLever spriteLever) {
		try {
			
			this.lever = spriteLever;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
