package com.mgl.drop.game.sprites.aninja;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.andengine.engine.camera.Camera;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.controller.ThreadSearchPath;
import com.mgl.drop.game.controller.dijkstra.WorldNode;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.ninja.SpritePlayer.Animator;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class SpriteEnemy extends MyAnimateSprite {

	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	public static final int LEFT = 4;

	public static final int BEHAVIOR_NORMAL = 1;
	public static final int BEHAVIOR_STOP = 2;
	public static final int BEHAVIOR_STOP_LOOK = 3;

	private Stack<Point> path;
	private Queue<Point> pathAux;
	private Point pointToMove;

	private int direction = DOWN;

	private float speed = 80;

	private int attack;
	private int defense;
	private int experience;
	private int HP;

	private SpriteVision vision;
	private SpriteBackground pointer;

	private ThreadSearchPath threadPath;

	private WorldNode prevNode = null;
	private boolean wakingUpFriend = false;

	private SpriteEnemy friend = null;

	private boolean surprised = false;
	private float timeSurprised = 2;
	private float contSurprised = 0;

	private int behavior = BEHAVIOR_STOP;
	
	private float contToLook = 0;
	private float timeToLook = 1;

	public SpriteEnemy(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
		path = new Stack<Point>();
		pathAux = new LinkedList<Point>();
		
		contToLook = 0;
		
		try {
			TextureSingleton texture = TextureSingleton.getInstance();

			wakingUpFriend = false;

			vision = new SpriteVision(0, 0,
					texture.getTextureByName("vision.png"),
					texture.getVertexBufferObjectManager(), this);
			vision.setPosition(this.getWidth() / 2 - vision.getWidth() / 2,
					this.getHeight() / 2);
			this.attachChild(vision);
			vision.setZIndex(this.getZIndex() - 1);
			vision.setEnemy(this);
			vision.setAlpha(0f);
			vision.setRotationCenter(vision.getWidth() / 2, 0);

			pointer = new SpriteBackground(0, 0,
					texture.getTextureByName("pointer.png"),
					texture.getVertexBufferObjectManager());
			this.attachChild(pointer);
			pointer.setAlpha(0);
			pointer.setPosition(this.getWidth() / 2 - pointer.getWidth() / 2,
					-pointer.getHeight());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void hidePointer() {
		try {

			pointer.setAlpha(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.ENEMY;
	}

	@Override
	public void initAnimationParams() {

		try {

			changeAnimateState(State.WALKIN_DOWN, false);
			anime(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83 };

			stateAnimate.put(State.WAKE_UP, new MyAnimateProperty(0, 2, fps));
			stateAnimate.put(State.WALKIN_DOWN,
					new MyAnimateProperty(0, 2, fps));
			stateAnimate.put(State.WALKIN_UP, new MyAnimateProperty(2, 2, fps));
			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(6, 2,
					fps));
			stateAnimate.put(State.WALKIN_LEFT,
					new MyAnimateProperty(6, 2, fps));

			stateAnimate.put(State.DIYING, new MyAnimateProperty(4, 2, fps));

			
			stateAnimate.put(State.WAITING_DOWN,
					new MyAnimateProperty(0, 2, fps));
			stateAnimate.put(State.WAITING_UP, new MyAnimateProperty(2, 2, fps));
			stateAnimate.put(State.WAITING_RIGHT, new MyAnimateProperty(6, 2,
					fps));
			stateAnimate.put(State.WAITING_LEFT,
					new MyAnimateProperty(6, 2, fps));

			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			if (currentState.equals(State.DIYING)) {
				this.setAlpha(1);
				return;
			}

			if (isWakeUp()) {
				return;
			}

			if (updateSurprised(dTime, level)) {
				return;
			}

			updateValidatingAnimation();

			updateVision(dTime, level);

			updateMoving(dTime, level);

			updateWakingUpFriend(dTime, level);

			prevNode = level.getLevelWorld().getBox(
					this.getX() + getMidPoint().getX(),
					this.getY() + getMidPoint().getY());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean updateSurprised(float dTime, LevelController level) {
		try {

			if (!surprised) {
				return false;
			}
			contSurprised += dTime;

			if (contSurprised > timeSurprised) {
				surprised = false;
			}

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void updateWakingUpFriend(float dTime, LevelController level) {
		try {

			if (friend == null
					|| !friend.getCurrentState().equals(State.DIYING)) {
				return;
			}

			Point myPoint = new Point(this.getX() + getMidPoint().getX(),
					this.getY() + getMidPoint().getY());
			Point friendPoint = new Point(friend.getX()
					+ friend.getMidPoint().getX(), friend.getY()
					+ friend.getMidPoint().getY());

			if (Point.distanceBetween(myPoint, friendPoint) < 50) {

				friend.wakeUp();
				wakingUpFriend = false;
				friend = null;
				// return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isWakeUp() {
		try {

			if (currentState.equals(State.WAKE_UP) && isAnimationRunning()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void updateVision(float dTime, LevelController level) {
		try {

			vision.setAlpha(0);

			Point myPoint = new Point(this.getX() + getMidPoint().getX(),
					this.getY() + getMidPoint().getY());
			Point playerPoint = new Point(level.getPlayer().getX()
					+ level.getPlayer().getMidPoint().getX(), level.getPlayer()
					.getY() + level.getPlayer().getMidPoint().getY());

			if (Point.distanceBetween(myPoint, playerPoint) > MainDropActivity.CAMERA_WIDTH + 250) {
				return;
			}

			WorldNode currentNode = level.getLevelWorld().getBox(
					this.getX() + getMidPoint().getX(),
					this.getY() + getMidPoint().getY());

			// if(prevNode!=null){
			// if(prevNode.getX() == currentNode.getX() && prevNode.getY() ==
			// currentNode.getY()){
			// //return;
			// }
			// }

			vision.resetVision();

			if (this.currentState.equals(State.WALKIN_RIGHT) || this.currentState.equals(State.WAITING_RIGHT) ) {
				vision.updateVisionRight(level);
			}

			if (this.currentState.equals(State.WALKIN_LEFT) || this.currentState.equals(State.WAITING_LEFT)) {
				vision.updateVisionLeft(level);
			}
			if (this.currentState.equals(State.WALKIN_UP)  || this.currentState.equals(State.WAITING_UP)) {
				vision.updateVisionUp(level);
			}
			if (this.currentState.equals(State.WALKIN_DOWN)  || this.currentState.equals(State.WAITING_DOWN)) {
				vision.updateVisionDown(level);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateVisionAux(float dTime, LevelController level) {
		try {

			vision.setZIndex(this.getZIndex() - 1);
			vision.setAlpha(0.3f);
			if (this.currentState.equals(State.WALKIN_RIGHT)) {

				vision.setFlippedHorizontal(false);
				vision.setFlippedVertical(false);
				vision.setPosition(this.getWidth() / 2 - vision.getWidth() / 2,
						this.getHeight() / 2);
				vision.setRotation(-90);

			} else if (this.currentState.equals(State.WALKIN_LEFT)) {

				vision.setFlippedHorizontal(false);
				vision.setFlippedVertical(false);
				vision.setPosition(this.getWidth() / 2 - vision.getWidth() / 2,
						this.getHeight() / 2);
				vision.setRotation(90);

			} else if (this.currentState.equals(State.WALKIN_DOWN)) {
				vision.setRotation(0);
				vision.setFlippedHorizontal(false);
				vision.setFlippedVertical(false);
				vision.setPosition(this.getWidth() / 2 - vision.getWidth() / 2,
						this.getHeight() / 2);
			} else if (this.currentState.equals(State.WALKIN_UP)) {
				vision.setRotation(0);
				vision.setFlippedHorizontal(false);
				if (!vision.isFlippedVertical()) {
					vision.setFlippedVertical(true);
					vision.setY(vision.getY() - vision.getHeight());
				}
			} else {
				vision.setAlpha(0);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateValidatingAnimation() {
		try {

			if (this.currentState.equals(State.WALKIN_RIGHT)) {
				this.setFlippedHorizontal(true);
			} else {
				this.setFlippedHorizontal(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateMoving(float dTime, LevelController level) {
		try {

			if (path == null || (path.isEmpty() && pointToMove == null)) {
				searchNewPointToMove();
				contToLook = 0;
				
				validateDirectionToLook(dTime,level);
				
				if (path == null || path.isEmpty()) {
					validateDirection();
				}
				// searchPointToMove(level);

				return;
			}

			if (pointToMove == null) {
				pointToMove = path.pop();
				WorldNode node = level.getLevelWorld().getBox(
						pointToMove.getX(), pointToMove.getY());
				if (node.isOccuped()) {
					path = null;
					pointToMove = null;
					return;
				}
				
			}
			
			if(mustUpdateMoving(dTime, level)){
				updateMovingToPoint(dTime);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void validateDirectionToLook(float dTime, LevelController level) {
		try {
			
			if(currentState.equals(State.WALKIN_DOWN)){
				changeAnimateState(State.WAITING_DOWN, true);
			}else if(currentState.equals(State.WALKIN_UP)){
				changeAnimateState(State.WAITING_UP, true);
			}else if(currentState.equals(State.WALKIN_RIGHT)){
				changeAnimateState(State.WAITING_RIGHT, true);
			}else if(currentState.equals(State.WALKIN_LEFT)){
				changeAnimateState(State.WAITING_LEFT, true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private boolean mustUpdateMoving(float dTime, LevelController level) {
		try {

			switch (behavior) {
			case BEHAVIOR_NORMAL:
				return true;

				
			case BEHAVIOR_STOP:

				contToLook += dTime;
				if(contToLook<timeToLook){
					return false;
				}
				
				return true;
				
			case BEHAVIOR_STOP_LOOK:

				break;

			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private void getNewPointToMove() {
		try {
			
			WorldNode node = level.getLevelWorld().getBox(
					pointToMove.getX(), pointToMove.getY());
			if (node.isOccuped()) {
				path = null;
				pointToMove = null;
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void searchNewPointToMove() {
		try {

			Point p = pathAux.poll();
			pathAux.add(p);
			generatePathToPoint(p);
			
			
			if(true){
				return;
			}
			
			
			if (pathAux == null || pathAux.isEmpty()) {
				return;
			}

			if (threadPath != null && threadPath.isLookingForPath()) {
				return;
			}

			if (threadPath != null && !threadPath.isLookingForPath()
					&& threadPath.isPathFound()) {
				path = threadPath.getPath();
				threadPath = null;
				return;
			}

			Point p2 = pathAux.poll();
			pathAux.add(p2);
			generatePathToPoint(p2);

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

			if (distance < 20f) {
				pointToMove = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void generatePathToPoint(Point pointToMove) {
		try {

			path = level.getLevelWorld().calculatePath(
					new Point(this.getX() + getMidPoint().getX(), this.getY()
							+ getMidPoint().getY()), pointToMove, 400);

			if (true)
				return;

			threadPath = new ThreadSearchPath(level,
					new Point(this.getX() + getMidPoint().getX(), this.getY()
							+ getMidPoint().getY()), pointToMove, 400);
			threadPath.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setXmlParameter(String parameter) {
		try {

			ArrayList<String> parameterList = MyXmlParser
					.getParameterListGeneric(parameter);
			int i = 0;

			Point p = new Point(this.getX() + getMidPoint().getX(), this.getY()
					+ getMidPoint().getY());
			pathAux.add(p);
			// pathAux.add(new Point(510, 210));

			for (String param : parameterList) {
				try {
					if (i == 0) {
						try {

							String[] paramArrayStr = param.split("&");
							for (int j = 0; j < paramArrayStr.length; j++) {
								String[] individualParam = paramArrayStr[j]
										.split(",");
								float x = Float.valueOf(individualParam[0]);
								float y = Float.valueOf(individualParam[1]);
								p = new Point(x, y);
								pathAux.add(p);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					// direction
					if (i == 1) {
						direction = Integer.parseInt(param);
					}

					if (i == 2) {
						behavior = Integer.parseInt(param);
					}
					if (i == 3) {
						timeToLook = Float.parseFloat(param);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
			}

			validateDirection();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void validateDirection() {
		try {

			switch (direction) {
			case UP:

				changeAnimateState(State.WAITING_UP, true);

				break;
			case DOWN:

				changeAnimateState(State.WAITING_DOWN, true);

				break;
			case RIGHT:

				changeAnimateState(State.WAITING_RIGHT, true);

				break;
			case LEFT:

				changeAnimateState(State.WAITING_LEFT, true);

				break;

			default:
				break;
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

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {

			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

				level.getPlayer().setEnemyToAttack(this);
				pointer.setAlpha(1);

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void recieveAttack() {
		try {

			vision.resetVision();
			changeAnimateState(State.DIYING, false);
			level.getScene().unregisterTouchArea(this);
			pointer.setAlpha(0);

			SpriteSmoke smoke = (SpriteSmoke) MyFactory.createObstacle(
					SpriteTypeConstant.SMOKE, level);
			smoke.setPosition(
					this.getX() + this.getWidth() / 2 - smoke.getWidth() / 2,
					this.getY() + this.getHeight() / 2 - smoke.getHeight() / 2);
			level.getScene().attachChild(smoke);
			level.addSpriteToUpdate(smoke);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void lookForWhisthle(Point playerP) {
		try {
			if (currentState.equals(State.DIYING)) {
				wakeUp();
			}

			surprise(0.5f, true);

			generatePathToPoint(playerP);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void wakeUp() {
		try {
			if (currentState.equals(State.DIYING)) {
				changeAnimateState(State.WAKE_UP, false);
				level.getScene().registerTouchArea(this);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isDead() {
		try {
			if (currentState.equals(State.DIYING)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void wakeUpFriend(SpriteEnemy enemy) {
		try {

			// pointToMove = null;
			if (enemy.getCurrentState().equals(State.DIYING) && !wakingUpFriend) {

				surprise(1.2f, false);
				path = null;

				path = level.getLevelWorld()
						.calculatePath(
								new Point(this.getX() + getMidPoint().getX(),
										this.getY() + getMidPoint().getY()),
								new Point(enemy.getX()
										+ enemy.getMidPoint().getX(), enemy
										.getY() + enemy.getMidPoint().getY()),
								400);
				threadPath = null;

				wakingUpFriend = true;
				friend = enemy;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void surprise(float timeSurprised, boolean exclamation) {

		try {

			surprised = true;
			this.timeSurprised = timeSurprised;
			contSurprised = 0;

			String textureName = "exclamation.png";

			if (!exclamation) {
				textureName = "question.png";
			}

			SpriteExpresion expresion = new SpriteExpresion(0, 0,
					TextureSingleton.getInstance()
							.getTextureByName(textureName), TextureSingleton
							.getInstance().getVertexBufferObjectManager(),
					timeSurprised);
			level.addSpriteToUpdate(expresion);
			expresion.setSize(50, 50);
			expresion.setPosition(this.getWidth() / 2 - expresion.getWidth()
					/ 2, expresion.getHeight() * -1);
			this.attachChild(expresion);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void clearVision() {
		try {

			// vision.resetVision();
			vision.clearVision();

			HUDManagerSingleton.getInstance().addHUD(
					new InformativeHUD("removing"), true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
