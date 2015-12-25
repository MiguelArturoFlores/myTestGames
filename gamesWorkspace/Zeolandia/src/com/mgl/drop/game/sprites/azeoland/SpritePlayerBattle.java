package com.mgl.drop.game.sprites.azeoland;

import java.util.Stack;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.userinfo.PlayerSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.controller.dijkstra.WorldNode;
import com.mgl.drop.game.entity.azeolandia.EntityCombatManager;
import com.mgl.drop.game.entity.azeolandia.EntityDamageNumber;
import com.mgl.drop.game.hud.zeolandia.ActionHUD;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class SpritePlayerBattle extends MyAnimateSprite {

	public static final int ACTION_NONE = 0;
	public static final int ACTION_MOVE = 1;
	public static final int ACTION_ATTACK = 2;
	
	
	public enum AttackType {ATTACK_BASIC,ATTACK_SPECIAL1_ZEO,ATTACK_SPECIAL2_ZEO,ATTACK_SPECIAL3_ZEO}

	private AttackType attackType = AttackType.ATTACK_BASIC;
	
	private Long id = 1l;
	
	private Stack<Point> path;
	private Point pointToMove;
	private Point previusPoint;
	private Point centerPoint;
	private Point pointToMoveinBattle;
	private float speed = 120;

	private float contIdle = 0;
	private float timeToIdle = 25;

	private float contTurn = 0;
	private float timeTurn = EntityCombatManager.TIME_TURN;

	private String characterFace;

	// fight atts
	/*private float distanceToAttack = 30;
	private float totalHp = 30;
	private float currentHp = 30;
	private float totalMp = 10;
	private float currentMp = 10;
	private float damage = 2;
	*/
	
	private SpriteBackground totalLife;
	private SpriteBackground currentLife;
	private SpriteBackground totalMana;
	private SpriteBackground currentMana;
	
	
	private boolean isAlive = true;
	private boolean isActive = false;
	private boolean isFinishTurn = false;
	private boolean hasShowActionPanel = false;
	private int actionState = ACTION_NONE;

	private SpriteEnemyBattle enemyTarget = null;

	// Techniques
	private SpriteTechniqueAnimation animationBasic = (SpriteTechniqueAnimation) MyFactory
			.createTechniqueAnimation(SpriteTypeConstant.ZEO_BASIC_ATTACK,
					level);
/*
	
	private SpriteTechniqueAnimation animationSpecial1 = (SpriteTechniqueAnimation) MyFactory
			.createTechniqueAnimation(SpriteTypeConstant.ZEO_SPECIAL_ATTACK1,
					level);
	*/
	
	public SpritePlayerBattle(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		try {
			path = new Stack<Point>();
			attachLife();

			centerPoint = new Point(this.getWidth()/2, this.getHeight() - this.getHeight()/4);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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

			currentLife.setWidth(PlayerSingleton.getInstance().getPlayer().getCurrentHP() * totalLife.getWidth() / PlayerSingleton.getInstance().getPlayer().getTotalHP());

			this.attachChild(totalLife);
			this.attachChild(currentLife);
			
			totalMana = new SpriteBackground(0, 5,
					texture.getTextureByName("emptyBar.png"),
					texture.getVertexBufferObjectManager());
			currentMana = new SpriteBackground(0, 5,
					texture.getTextureByName("fillManaBar.png"),
					texture.getVertexBufferObjectManager());

			totalMana.setWidth(this.getWidth());

			currentMana.setWidth(PlayerSingleton.getInstance().getPlayer().getCurrentMP() * totalMana.getWidth() / PlayerSingleton.getInstance().getPlayer().getTotalMP());

			this.attachChild(totalMana);
			this.attachChild(currentMana);

			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void reloadLife() {
		try {

			totalLife.setWidth(this.getWidth());
			currentLife.setWidth(PlayerSingleton.getInstance().getPlayer().getCurrentHP() * totalLife.getWidth() / PlayerSingleton.getInstance().getPlayer().getTotalHP());
			
			totalMana.setWidth(this.getWidth());
			currentMana.setWidth(PlayerSingleton.getInstance().getPlayer().getCurrentMP() * totalMana.getWidth() / PlayerSingleton.getInstance().getPlayer().getTotalMP());

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

			fps = new long[] { 83, 83, 83 };

			stateAnimate.put(State.IDLE, new MyAnimateProperty(0, 3, fps));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			contTurn += dTime;
			if (contTurn < timeTurn) {
				return;
			}
			contTurn = 0;

			// updateByState(dTime,level);
			updateAttacking(dTime, level);

			updateMoving(dTime, level);

			finishTurn();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateAttacking(float dTime, LevelController level) {
		try {

			// enemyTarget = searchForNearestEnemy(level);

			if (enemyTarget == null) {
				return;
			}

			if (enemyTarget != null && !enemyTarget.isAlive()) {
				enemyTarget = null;
				return;
			}

			Point playerPoint = new Point(this.getX() + this.getWidth() / 2,
					this.getY() + this.getHeight() / 2);
			Point enemyPoint =  enemyTarget.getMidPoint();

			if (Point.distanceBetween(playerPoint, enemyPoint) <= PlayerSingleton.getInstance().getPlayer().getDistanceToAttack()){
				// attack;
				attackEnemy();
				

			} else {
				// moveToEnemy
				System.out.println("MUEVO HACIA EL ENEMIGO");
				pointToMoveinBattle = enemyPoint;
				generatePathToPoint(enemyPoint);

				/*
				 * int contTry = 0; while (path == null || path.isEmpty()) {
				 * 
				 * float x = MainDropActivity.getRandomMax(0, (int)
				 * enemyTarget.getWidth()) + enemyTarget.getX(); float y =
				 * MainDropActivity.getRandomMax(0, (int)
				 * enemyTarget.getHeight()) + enemyTarget.getY(); contTry++;
				 * generatePathToPoint(new Point(x, y)); pointToMoveinBattle =
				 * new Point(x, y);
				 * 
				 * if(contTry > 5){ //print message imposible find a path
				 * pointToMoveinBattle = null; return; } }
				 */

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void attackEnemy() {
		try {
			
			switch (attackType) {
			case ATTACK_BASIC:
				attackEnemyBasic();
				break;
			case ATTACK_SPECIAL1_ZEO:
				attackEnemySpecial1();
				//attackType = AttackType.ATTACK_BASIC;
				break;

			default:
				break;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void attackEnemySpecial1() {
		try {

			SpriteTechniqueAnimation animationBasic = (SpriteTechniqueAnimation) MyFactory
					.createTechniqueAnimation(SpriteTypeConstant.ZEO_SPECIAL_ATTACK1,
							level);

			int manaCost = 2;
			int damage = 6;
			
			if(PlayerSingleton.getInstance().getPlayer().getCurrentMP() - manaCost <0){
				return;
			}
			
			PlayerSingleton.getInstance().getPlayer().setCurrentMP(PlayerSingleton.getInstance().getPlayer().getCurrentMP()-manaCost);
			reloadLife();	
			
			this.setAlpha(0);
			
			enemyTarget.recieveAttack(damage, this);
			pointToMoveinBattle = null;
			
			animationBasic.setPosition(getMidPoint().getX()-animationBasic.getCenterPoint().getX(),getMidPoint().getY()-animationBasic.getCenterPoint().getY());
			animationBasic.setPlayer(this);
			animationBasic.detachSelf();
			level.getScene().attachChild(animationBasic);
			level.addSpriteToUpdate(animationBasic);
			animationBasic.init();
			
			//direction
			float angle = Point.angleBetween(getMidPoint(), enemyTarget.getMidPoint());
			angle = (float) (angle * 180/Math.PI);
			
			//right
			if(angle>= -90 && angle<=90){
				animationBasic.setPosition(getMidPoint().getX()-animationBasic.getCenterPoint().getX()+animationBasic.getWidth()/2,getMidPoint().getY()-animationBasic.getCenterPoint().getY());
				animationBasic.setFlippedHorizontal(true);
			//left
			}else{
				animationBasic.setFlippedHorizontal(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void attackEnemyBasic() {

		try {

			this.setAlpha(0);

			
			enemyTarget.recieveAttack(PlayerSingleton.getInstance().getPlayer().getAttack(), this);
			pointToMoveinBattle = null;
			
			animationBasic.setPosition(getMidPoint().getX()-animationBasic.getCenterPoint().getX(),getMidPoint().getY()-animationBasic.getCenterPoint().getY());
			animationBasic.setPlayer(this);
			animationBasic.detachSelf();
			level.getScene().attachChild(animationBasic);
			level.addSpriteToUpdate(animationBasic);
			animationBasic.init();
			
			//direction
			float angle = Point.angleBetween(getMidPoint(), enemyTarget.getMidPoint());
			angle = (float) (angle * 180/Math.PI);
			
			//right
			if(angle>= -90 && angle<=90){
				animationBasic.setPosition(getMidPoint().getX()-animationBasic.getCenterPoint().getX()+animationBasic.getWidth()/2,getMidPoint().getY()-animationBasic.getCenterPoint().getY());
				animationBasic.setFlippedHorizontal(true);
			//left
			}else{
				animationBasic.setFlippedHorizontal(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private SpriteEnemyBattle searchForNearestEnemy(LevelController level) {
		try {

			SpriteEnemyBattle enemy = null;
			float minDistance = Float.MAX_VALUE;
			for (MySpriteGeneral spr : level.getSpriteList()) {
				if (spr instanceof SpriteEnemyBattle) {
					float distance = Point.distanceBetween(
							new Point(this.getX(), this.getY()), new Point(
									((SpriteEnemyBattle) spr).getX(),
									((SpriteEnemyBattle) spr).getY()));
					if (distance < minDistance) {
						enemy = (SpriteEnemyBattle) spr;
						minDistance = distance;
					}
				}
			}

			return enemy;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void finishTurn() {
		try {

			// path = null;
			pointToMove = null;
			isFinishTurn = true;
			hasShowActionPanel = false;

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

			if (pointToMoveinBattle == null) {
				return;
			}

			generatePathToPoint(pointToMoveinBattle);

			if (path == null || (path.isEmpty() && pointToMove == null)) {
				pointToMoveinBattle = null;
				return;
			}

			if (pointToMove == null) {
				pointToMove = path.pop();
				if (pointToMove.getX() == pointToMoveinBattle.getX()
						&& pointToMove.getY() == pointToMoveinBattle.getY()) {
					pointToMoveinBattle = null;
					return;
				}
			}

			if (previusPoint != null) {
				level.getLevelBattleWorld().occupeDesocupePosition(
						previusPoint.getX(), previusPoint.getY(),
						WorldNode.EMPTY);
			}

			if (pointToMove != null) {
				level.getLevelBattleWorld().occupeDesocupePosition(
						pointToMove.getX(), pointToMove.getY(),
						WorldNode.OCCUPED);
			}

			// updateMovingToPoint(dTime);

			this.setPosition(pointToMove.getX()-centerPoint.getX() , pointToMove.getY()-centerPoint.getY());
			
			previusPoint = new Point(pointToMove.getX(), pointToMove.getY());
			pointToMove = null;

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

	public void generatePathToPoint(Point pointToMove) {
		try {
			if (pointToMove == null) {
				return;
			}

			path = level.getLevelBattleWorld().calculatePath(
					getMidPoint(), pointToMove, 1200);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void manageSceneTouch(Point point) {
		try {

			switch (actionState) {
			case ACTION_MOVE:
				pointToMoveinBattle = point;
				generatePathToPoint(point);
				enemyTarget = null;
				actionState = ACTION_NONE;

				break;
			case ACTION_ATTACK:

				enemyTarget = lookforEnemy(point);
				if (enemyTarget == null) {

				} else {
					actionState = ACTION_NONE;
				}

				break;

			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SpriteEnemyBattle lookforEnemy(Point point) {

		try {

			for (MySpriteGeneral spr : level.getSpriteList()) {
				if (spr instanceof SpriteEnemyBattle) {
					SpriteEnemyBattle sprite = (SpriteEnemyBattle) spr;
					if (point.getX() >= sprite.getX()
							&& point.getX() <= sprite.getX()
									+ sprite.getWidth()) {
						if (point.getY() >= sprite.getY()
								&& point.getY() <= sprite.getY()
										+ sprite.getHeight()) {
							return sprite;
						}
					}

				}

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public void setInBattlePosition(Point position) {
		try {
			
			this.setPosition(position.getX()-centerPoint.getX() , position.getY()-centerPoint.getY());
			
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

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getContIdle() {
		return contIdle;
	}

	public void setContIdle(float contIdle) {
		this.contIdle = contIdle;
	}

	public float getTimeToIdle() {
		return timeToIdle;
	}

	public void setTimeToIdle(float timeToIdle) {
		this.timeToIdle = timeToIdle;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isFinishTurn() {
		return isFinishTurn;
	}

	public void setFinishTurn(boolean isFinishTurn) {
		this.isFinishTurn = isFinishTurn;
	}

	public boolean isHasShowActionPanel() {
		return hasShowActionPanel;
	}

	public void setHasShowActionPanel(boolean hasShowActionPanel) {
		this.hasShowActionPanel = hasShowActionPanel;
	}

	public int getActionState() {
		return actionState;
	}

	public void setActionState(int actionState) {
		this.actionState = actionState;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public SpriteEnemyBattle getEnemyTarget() {
		return enemyTarget;
	}

	public void setEnemyTarget(SpriteEnemyBattle enemyTarget) {
		this.enemyTarget = enemyTarget;
	}

	public void recieveAttack(float damageRecieved,
			SpriteEnemyBattle spriteEnemyBattle) {

		try {
			PlayerSingleton.getInstance().getPlayer().setCurrentHP((int) (PlayerSingleton.getInstance().getPlayer().getCurrentHP()-damageRecieved));

			if ((enemyTarget == null || !enemyTarget.isAlive())
					&& pointToMoveinBattle == null) {
				enemyTarget = spriteEnemyBattle;
			}
			reloadLife();

			EntityDamageNumber damageNumber = new EntityDamageNumber("-"
					+ Float.valueOf(damageRecieved).intValue());
			this.attachChild(damageNumber.getNumberText());
			damageNumber.getNumberText().setPosition(
					this.getWidth() / 2
							- damageNumber.getNumberText().getWidth() / 2, 0);
			level.addSpriteToUpdate(damageNumber);

			damageNumber.getNumberText().setColor(192/255f,10/255f,174/255f);
			
			
			// changeAnimateState(State.HITTED, false);

			if (PlayerSingleton.getInstance().getPlayer().getCurrentHP() <= 0) {
				died();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void died() {
		try {

			level.getLevelBattleWorld().occupeDesocupePosition(
					previusPoint.getX(), previusPoint.getY(), WorldNode.EMPTY);

			isAlive = false;
			this.detachSelf();
			level.removeEntity(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public static int getActionNone() {
		return ACTION_NONE;
	}

	public static int getActionMove() {
		return ACTION_MOVE;
	}

	public static int getActionAttack() {
		return ACTION_ATTACK;
	}

	public String getCharacterFace() {
		return characterFace;
	}

	public void setCharacterFace(String characterFace) {
		this.characterFace = characterFace;
	}

	public Point getMidPoint() {
		return new Point(this.getX()+centerPoint.getX(), this.getY() + centerPoint.getY());
	}

	public Point getPointToMoveinBattle() {
		return pointToMoveinBattle;
	}

	public void setPointToMoveinBattle(Point pointToMoveinBattle) {
		this.pointToMoveinBattle = pointToMoveinBattle;
	}

	public AttackType getAttackType() {
		return attackType;
	}

	public void setAttackType(AttackType attackType) {
		this.attackType = attackType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

}
