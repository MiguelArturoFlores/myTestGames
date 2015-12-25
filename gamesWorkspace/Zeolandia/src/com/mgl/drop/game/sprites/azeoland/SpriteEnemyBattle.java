package com.mgl.drop.game.sprites.azeoland;

import java.util.Stack;

import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.controller.dijkstra.WorldNode;
import com.mgl.drop.game.entity.azeolandia.EntityCombatManager;
import com.mgl.drop.game.entity.azeolandia.EntityDamageNumber;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;
import com.startapp.android.publish.splash.SplashConfig.MaxAdDisplayTime;

public class SpriteEnemyBattle extends MyAnimateSprite {

	private Stack<Point> path;
	private Point pointToMove;
	private Point previusPoint;
	private Point centerPoint;
	private float normalSpeed = 20;

	private float speed = 20;
	private Float viewRange = 220f;

	//private float cont = 0.2f;
	//private float timeToMove = 0.5f;

	private int actionNumber = 2;
	private int contAction = 0;

	private boolean isActive = false;
	private boolean finishTurn = false;

	private float contTurn = 0;
	private float timeTurn = EntityCombatManager.TIME_TURN;
	
	// battle atts
	private float distanceToAttack = 35;
	private float totalHp = 10;
	private float currentHp = 10;
	private SpriteBackground totalLife;
	private SpriteBackground currentLife;
	private boolean isAlive = true;
	
	private float attack = 1f;
	private float defense = 1f;
	
	private int experience = 2;
	
	private SpritePlayerBattle playerTarget = null;

	public SpriteEnemyBattle(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// path = new Stack<Point>();
		// Point p = new Point(350, 200);
		// path.add(p);
		attachLife();
		centerPoint = new Point(this.getWidth()/2, this.getHeight()/2);
	}

	private void attachLife() {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			totalLife = new SpriteBackground(0, 0,
					texture.getTextureByName("emptyBar.png"),
					texture.getVertexBufferObjectManager());
			currentLife = new SpriteBackground(0, 0,
					texture.getTextureByName("fillLifeBar.png"),
					texture.getVertexBufferObjectManager());

			totalLife.setWidth(this.getWidth());

			currentLife.setWidth(currentHp * totalLife.getWidth() / totalHp);

			this.attachChild(totalLife);
			this.attachChild(currentLife);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void reloadLife() {
		try {

			totalLife.setWidth(this.getWidth());
			currentLife.setWidth(currentHp * totalLife.getWidth() / totalHp);

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

			changeAnimateState(State.IDLE, true);
			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83 };

			stateAnimate.put(State.IDLE, new MyAnimateProperty(0, 3, fps));
			stateAnimate.put(State.HITTED, new MyAnimateProperty(3, 2,
					new long[] { 83, 83 }));
			stateAnimate.put(State.ATTACK, new MyAnimateProperty(5, 4, new long[] { 83, 83, 83, 83 }));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			updateAnimationStates();

			contTurn += dTime;
			if (contTurn<timeTurn) {
				return;
			}
			contTurn = 0;

			updateAttacking(dTime,level);
			updateMoving(dTime, level);

			
			//if (contAction >= actionNumber) {
				
				contAction = 0;
				path = null;
				pointToMove = null;
				finishTurn = true;
			//}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateAttacking(float dTime, LevelController level) {
		try {
			
//			if(playerTarget == null || !playerTarget.isAlive()){
				playerTarget = searchForPlayerTarget(level);
			//}
			
			if(playerTarget == null){
				return;
			}
			
			Point playerPoint = new Point(this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2);
			Point enemyPoint = new Point(playerTarget.getX()+playerTarget.getWidth()/2, playerTarget.getY()+playerTarget.getHeight()/2);
			
			if(Point.distanceBetween(playerPoint, enemyPoint) <= distanceToAttack){
				//attack;
				changeAnimateState(State.ATTACK, false);
				playerTarget.recieveAttack(attack, this);
				playerTarget = null;
				pointToMove = null;
				path = null;
			}else{
				//moveToEnemy
				System.out.println("MUEVO HACIA EL ENEMIGO");
				generatePathToPoint(enemyPoint);
				
				/*int contTry = 0;
				while(path == null || path.isEmpty()){
					
					float x = MainDropActivity.getRandomMax(0, (int) playerTarget.getWidth()) + playerTarget.getX();
					float y = MainDropActivity.getRandomMax(0, (int) playerTarget.getHeight()) + playerTarget.getY();
					contTry ++;
					generatePathToPoint(new Point(x, y));
					
					if(contTry > 5){
						//print message imposible find a path
						return;
					}
				}*/
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SpritePlayerBattle searchForPlayerTarget(LevelController level) {
		try {
			SpritePlayerBattle player = null;
			float minDistance = Float.MAX_VALUE;
			for(MySpriteGeneral spr : level.getSpriteList()){
				if(spr instanceof SpritePlayerBattle){
					float distance = Point.distanceBetween(new Point(this.getX(), this.getY()), new Point(((SpritePlayerBattle) spr).getX(), ((SpritePlayerBattle) spr).getY()));
					if(distance<minDistance){
						player = (SpritePlayerBattle) spr;
						minDistance = distance;
					}
				}
			}
			
			return player;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void updateAnimationStates() {
		try {

			if (this.currentState.equals(State.HITTED) || this.currentState.equals(State.ATTACK)) {
				if (!isAnimationRunning()) {
					changeAnimateState(State.IDLE, true);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateMoving(float dTime, LevelController level) {
		try {

			if (path == null || (path.isEmpty() && pointToMove == null)) {
				return;
			}

			if (pointToMove == null) {
				pointToMove = path.pop();
			}

//			cont += dTime;
//			if (cont < timeToMove) {
//				return;
//			}
//			cont = 0;
			if(previusPoint!=null){
				level.getLevelBattleWorld().occupeDesocupePosition(previusPoint.getX(), previusPoint.getY(), WorldNode.EMPTY);
			}
			if(pointToMove!=null){
				level.getLevelBattleWorld().occupeDesocupePosition(pointToMove.getX(), pointToMove.getY(), WorldNode.OCCUPED);
			}
			this.setPosition(pointToMove.getX()-centerPoint.getX() , pointToMove.getY()-centerPoint.getY());
			
			previusPoint = new Point(pointToMove.getX(), pointToMove.getY());
			pointToMove = null;
			path = null;

			//contAction++;
			// updateMovingToPoint(dTime);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void searchPointToMove(LevelController level) {
		try {

			int x = (int) (MainDropActivity.getRandomMax(0,
					viewRange.intValue()) - viewRange / 2f);
			int y = (int) (MainDropActivity.getRandomMax(0,
					viewRange.intValue()) - viewRange / 2f);

			generatePathToPoint(new Point(this.getX() + x, this.getY() + y));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateMovingToPoint(float dTime) {
		try {

			Point currentPoint = new Point(this.getX(), this.getY());

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
				changeAnimateState(State.WALKIN_RIGHT, false);
				// down
			} else if (angle >= 45 && angle <= 135) {
				changeAnimateState(State.WALKIN_DOWN, false);
				// up
			} else if (angle >= -135 && angle <= -45) {
				changeAnimateState(State.WALKIN_UP, false);

				// left
			} else {
				changeAnimateState(State.WALKIN_LEFT, false);
			}

			if (distance < 1.5f) {
				pointToMove = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setInBattlePosition(Point position) {
		try {
			
			this.setPosition(position.getX()-centerPoint.getX() , position.getY()-centerPoint.getY());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generatePathToPoint(Point pointToMove) {
		try {

			path = level.getLevelBattleWorld().calculatePath(
					getMidPoint(), pointToMove, 1200);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Stack<Point> getPath() {
		return path;
	}

	public void setPath(Stack<Point> path) {
		this.path = path;
	}

	public Point getPointToMove() {
		return pointToMove;
	}

	public void setPointToMove(Point pointToMove) {
		this.pointToMove = pointToMove;
	}

	public float getNormalSpeed() {
		return normalSpeed;
	}

	public void setNormalSpeed(float normalSpeed) {
		this.normalSpeed = normalSpeed;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Float getViewRange() {
		return viewRange;
	}

	public void setViewRange(Float viewRange) {
		this.viewRange = viewRange;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isFinishTurn() {
		return finishTurn;
	}

	public void setFinishTurn(boolean finishTurn) {
		this.finishTurn = finishTurn;
	}

	public int getActionNumber() {
		return actionNumber;
	}

	public void setActionNumber(int actionNumber) {
		this.actionNumber = actionNumber;
	}

	public int getContAction() {
		return contAction;
	}

	public void setContAction(int contAction) {
		this.contAction = contAction;
	}

	public void recieveAttack(float damage,
			SpritePlayerBattle spritePlayerBattle) {

		try {
			this.currentHp = this.currentHp - damage;
			reloadLife();

			if (this.currentState.equals(State.IDLE) ) {
				changeAnimateState(State.HITTED, false);
			}
			
			
			EntityDamageNumber damageNumber = new EntityDamageNumber("-"+Float.valueOf(damage).intValue());
			this.attachChild(damageNumber.getNumberText());
			damageNumber.getNumberText().setPosition(this.getWidth()/2 - damageNumber.getNumberText().getWidth()/2, 0);
			level.addSpriteToUpdate(damageNumber);
			damageNumber.getNumberText().setColor(255/255f,0/255f,0/255f);

			if (currentHp <= 0) {
				died();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void died() {
		try {
			
			level.getLevelBattleWorld().occupeDesocupePosition(previusPoint.getX(), previusPoint.getY(), WorldNode.EMPTY);
			
			isAlive = false;
			this.detachSelf();
			level.removeEntity(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public float getDistanceToAttack() {
		return distanceToAttack;
	}

	public void setDistanceToAttack(float distanceToAttack) {
		this.distanceToAttack = distanceToAttack;
	}

	public float getTotalHp() {
		return totalHp;
	}

	public void setTotalHp(float totalHp) {
		this.totalHp = totalHp;
	}

	public float getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(float currentHp) {
		this.currentHp = currentHp;
	}

	public SpriteBackground getTotalLife() {
		return totalLife;
	}

	public void setTotalLife(SpriteBackground totalLife) {
		this.totalLife = totalLife;
	}

	public SpriteBackground getCurrentLife() {
		return currentLife;
	}

	public void setCurrentLife(SpriteBackground currentLife) {
		this.currentLife = currentLife;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public Point getPreviusPoint() {
		return previusPoint;
	}

	public void setPreviusPoint(Point previusPoint) {
		this.previusPoint = previusPoint;
	}

	public float getContTurn() {
		return contTurn;
	}

	public void setContTurn(float contTurn) {
		this.contTurn = contTurn;
	}

	public float getTimeTurn() {
		return timeTurn;
	}

	public void setTimeTurn(float timeTurn) {
		this.timeTurn = timeTurn;
	}

	public SpritePlayerBattle getPlayerTarget() {
		return playerTarget;
	}

	public void setPlayerTarget(SpritePlayerBattle playerTarget) {
		this.playerTarget = playerTarget;
	}

	public Point getMidPoint() {
		return new Point(this.getX()+centerPoint.getX(), this.getY() + centerPoint.getY());
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public void setAtts(SpriteEnemy enemyModel) {
		try {
			
			this.attack = enemyModel.getAttack();
			this.defense = enemyModel.getDefense();
			this.totalHp = enemyModel.getHP();
			this.experience = enemyModel.getExperience();
			this.currentHp = totalHp;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
