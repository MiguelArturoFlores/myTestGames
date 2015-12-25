package com.mgl.drop.game.entity.azeolandia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import com.google.android.gms.internal.ex;
import com.mgl.base.MyEntity;
import com.mgl.base.MyFactory;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.userinfo.PlayerSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.controller.dijkstra.WorldNode;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.model.PlayerModel;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.game.sprites.azeoland.SpriteCharacterBattleFace;
import com.mgl.drop.game.sprites.azeoland.SpriteEnemyBattle;
import com.mgl.drop.game.sprites.azeoland.SpritePlayerBattle;
import com.mgl.drop.game.sprites.azeoland.SpriteTurnSelect;
import com.mgl.drop.game.sprites.azeoland.SpriteWinBattle;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class EntityCombatManager extends MyEntity {

	public static final float TIME_TURN = 1f;

	public static final float DISTANCE_TO_BATTLE = 200;

	private Queue<MySpriteGeneral> spriteQueue;

	private ArrayList<SpritePlayerBattle> playerBattleList;
	private ArrayList<SpriteEnemyBattle> enemyBattleList;
	private SpritePlayerBattle currentPlayer = null;
	
	
	private LevelController controller;
	private SpriteTurnSelect turnArrow;

	private EntityDrawWorld draw;
	
	private boolean hasShowLooseHud = false;
	private boolean hasShowWinHud = false;
	
	public EntityCombatManager(LevelController controller, ArrayList<MySpriteGeneral> spriteLists) {
		try {

			this.controller = controller;
			turnArrow = (SpriteTurnSelect) MyFactory.createObstacle(SpriteTypeConstant.TURN_ARROW, controller);
			
			hasShowLooseHud = false;
			hasShowWinHud = false;
			spriteQueue = new LinkedList<MySpriteGeneral>();
			Collections.shuffle(spriteLists);

			enemyBattleList = new ArrayList<SpriteEnemyBattle>();
			playerBattleList = new ArrayList<SpritePlayerBattle>();

			for (MySpriteGeneral spr : spriteLists) {
				spriteQueue.add(spr);
				if (spr.getSpriteType().equals(SpriteType.ENEMY)) {
					enemyBattleList.add((SpriteEnemyBattle) spr);
				} else if (spr.getSpriteType().equals(
						SpriteType.PLAYER)) {
					playerBattleList.add((SpritePlayerBattle) spr);
				}

			}

			addEnemies();
			
			addPlayer();
			
			drawWorld();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void drawWorld() {
		try {
			draw = new EntityDrawWorld(controller);
			controller.addSpriteToUpdate(draw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addPlayer() {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			try {
				
				int i = 0;
				int offset = 25;
				
				for(SpritePlayerBattle player : playerBattleList){
					
					Point position = null;
					while(position ==null ){
						
						int x = MainDropActivity.getRandomMax(0, MainDropActivity.CAMERA_WIDTH);
						int y = MainDropActivity.getRandomMax(MainDropActivity.CAMERA_HEIGHT/2, MainDropActivity.CAMERA_HEIGHT -50);
						
						position = controller.getLevelBattleWorld().placeInFreePosition(x, y);
						
					}
					
					player.setPreviusPoint(position);
					//player.setPosition(position.getX(), position.getY());
					player.setInBattlePosition(position);
					
					controller.getScene().attachChild(player);
					controller.addSpriteToUpdate(player);
					
					SpriteCharacterBattleFace face =  (SpriteCharacterBattleFace) MyFactory.createObstacle(SpriteTypeConstant.ZEO_FACE, controller);
					face.setPosition(5,((face.getHeight()+5)*i) + offset);
					face.setPlayerBattle(player);
					face.setCombatManager(this);
					i++;
					
				//	new SpriteCharacterBattleFace(0, 0, texture.getTextureByName("zeoFace.png"), texture.getVertexBufferObjectManager(), player);
					controller.getScene().attachChild(face);
					controller.getScene().registerTouchArea(face);
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addEnemies() {
		try {
			
			for(SpriteEnemyBattle enemy : enemyBattleList){
				
				Point position = null;
				while(position ==null ){
					
					int x = MainDropActivity.getRandomMax(0, MainDropActivity.CAMERA_WIDTH);
					int y = MainDropActivity.getRandomMax(0, MainDropActivity.CAMERA_HEIGHT/3);
					
					position = controller.getLevelBattleWorld().placeInFreePosition(x, y);
					
				}
				enemy.setContTurn(MainDropActivity.getRandomMax(0, (int) (TIME_TURN*100))/(TIME_TURN*100f));
				enemy.setPreviusPoint(position);
				//enemy.setPosition(position.getX(), position.getY());
				enemy.setInBattlePosition(position);
				controller.getScene().attachChild(enemy);
				controller.addSpriteToUpdate(enemy);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setMustUpdate(boolean mustUpdate) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IEntity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITouchArea getTouchArea() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateChild(float dTime, LevelController level) {
		try {

			
			validateFinishFight(dTime, level);

			//updateTurn(dTime, level);
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateTurn(float dTime, LevelController level) {
		try {

			MySpriteGeneral spr = spriteQueue.peek();

			if (spr.getSpriteType().equals(SpriteType.ENEMY)) {
				currentPlayer = null;
				SpriteEnemyBattle enemy = (SpriteEnemyBattle) spr;
				if(!enemy.isAlive()){
					spriteQueue.poll();
				}
				
				if (!enemy.isActive()) {
					enemy.setActive(true);
					turnArrow.detachSelf();
					turnArrow.setPosition(enemy.getWidth()/2 - turnArrow.getWidth()/2,- turnArrow.getHeight());
					enemy.attachChild(turnArrow);
				}
				if (enemy.isFinishTurn()) {
					enemy.setActive(false);
					enemy.setFinishTurn(false);
					MySpriteGeneral sprite = spriteQueue.poll();
					spriteQueue.add(sprite);
				}

			} else if (spr.getSpriteType().equals(
					SpriteType.PLAYER)) {
				SpritePlayerBattle player = (SpritePlayerBattle) spr;
				currentPlayer = player;

				if(!player.isAlive()){
					spriteQueue.poll();
				}
				
				if (!player.isActive()) {
					player.setActive(true);
					turnArrow.detachSelf();
					turnArrow.setPosition(player.getWidth()/2 - turnArrow.getWidth()/2,- turnArrow.getHeight());
					player.attachChild(turnArrow);
					
				}
				if (player.isFinishTurn()) {
					player.setActive(false);
					player.setFinishTurn(false);
					MySpriteGeneral sprite = spriteQueue.poll();
					spriteQueue.add(sprite);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void validateFinishFight(float dTime, LevelController level) {
		try {
			
			boolean isEnemyAlive = false;
			boolean isPlayerAlive = false;
			
			for(SpriteEnemyBattle ene : enemyBattleList){
				if(ene.isAlive()){
					isEnemyAlive = true;
					break;
				}
			}
			
			for(SpritePlayerBattle player : playerBattleList){
				if(player.isAlive()){
					isPlayerAlive = true;
				}
			}
			
			if(!isEnemyAlive){
				System.out.println("Player Win Battle");
				if(!hasShowWinHud){
				//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Win Battle"), true);
				
				int experience = 0;
				
				for(SpriteEnemyBattle ene : enemyBattleList){
					experience += ene.getExperience();
				}
				
				SpriteWinBattle win = new SpriteWinBattle(0, 0, TextureSingleton.getInstance().getTextureByName("black.jpg"), TextureSingleton.getInstance().getVertexBufferObjectManager(), level);
				win.setExperience(experience);
				HUDManagerSingleton.getInstance().getTop().attachChild(win);
				win.setAutoUpdate();
				
				boolean giveLifePotion = false;
				boolean giveManaPotion = false;
				
				for(SpritePlayerBattle pb : playerBattleList){
					
					if(PlayerSingleton.getInstance().getPlayer().getCurrentHP() <= PlayerSingleton.getInstance().getPlayer().getTotalHP()*0.25){
						giveLifePotion = true;
					}
					if(PlayerSingleton.getInstance().getPlayer().getCurrentMP() <= PlayerSingleton.getInstance().getPlayer().getTotalMP()*0.25){
						giveManaPotion = true;
					}
					
				}
								
				PlayerSingleton.getInstance().setGiveLifePotion(giveLifePotion);
				PlayerSingleton.getInstance().setGiveManaPotion(giveManaPotion);
				
				hasShowWinHud = true;
				}
				return;
			}
			if(!isPlayerAlive){
				if(!hasShowLooseHud){
					SceneManagerSingleton.getInstance().setCurrentScene(AllScenes.MAIN);
					PlayerSingleton.getInstance().died();
					HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Game Over"), true);
					System.out.println("Player Loose Battle");
					level.setMustUpdate(false);
					hasShowLooseHud = true;
					
				}
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void manageSceneTouch(float x, float y ){
		try {
			if(currentPlayer == null){
				return;
			}
			
			currentPlayer.manageSceneTouch(new Point(x, y));
			pause(false);
			
			draw.reset();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public Queue<MySpriteGeneral> getSpriteQueue() {
		return spriteQueue;
	}

	public void setSpriteQueue(Queue<MySpriteGeneral> spriteQueue) {
		this.spriteQueue = spriteQueue;
	}

	public ArrayList<SpritePlayerBattle> getPlayerBattleList() {
		return playerBattleList;
	}

	public void setPlayerBattleList(ArrayList<SpritePlayerBattle> playerBattleList) {
		this.playerBattleList = playerBattleList;
	}

	public ArrayList<SpriteEnemyBattle> getEnemyBattleList() {
		return enemyBattleList;
	}

	public void setEnemyBattleList(ArrayList<SpriteEnemyBattle> enemyBattleList) {
		this.enemyBattleList = enemyBattleList;
	}

	public SpritePlayerBattle getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(SpritePlayerBattle currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public LevelController getController() {
		return controller;
	}

	public void setController(LevelController controller) {
		this.controller = controller;
	}

	public SpriteTurnSelect getTurnArrow() {
		return turnArrow;
	}

	public void setTurnArrow(SpriteTurnSelect turnArrow) {
		this.turnArrow = turnArrow;
	}

	public boolean isHasShowLooseHud() {
		return hasShowLooseHud;
	}

	public void setHasShowLooseHud(boolean hasShowLooseHud) {
		this.hasShowLooseHud = hasShowLooseHud;
	}

	public EntityDrawWorld getDraw() {
		return draw;
	}

	public void setDraw(EntityDrawWorld draw) {
		this.draw = draw;
	}

	public static float getTimeTurn() {
		return TIME_TURN;
	}

	public static float getDistanceToBattle() {
		return DISTANCE_TO_BATTLE;
	}

	public void pause(boolean pause) {
		controller.setMustUpdate(!pause);
		
	}
	
	
}
