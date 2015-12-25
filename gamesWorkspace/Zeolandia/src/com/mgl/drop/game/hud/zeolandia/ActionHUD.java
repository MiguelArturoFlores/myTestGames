package com.mgl.drop.game.hud.zeolandia;

import java.util.ArrayList;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.PlayerTechnique;
import com.mgl.drop.game.entity.azeolandia.EntityCombatManager;
import com.mgl.drop.game.entity.azeolandia.EntityDrawWorld;
import com.mgl.drop.game.hud.MyHud;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.azeoland.SpritePlayerBattle;
import com.mgl.drop.game.sprites.button.azeolandia.ButtonAttack;
import com.mgl.drop.game.sprites.button.azeolandia.ButtonMove;
import com.mgl.drop.game.sprites.button.azeolandia.ButtonSpecial;
import com.mgl.drop.texture.TextureSingleton;

import org.andengine.engine.camera.hud.HUD;

public class ActionHUD extends MyHud{
	
	private TextureSingleton texture = TextureSingleton.getInstance();
	
	private SpritePlayerBattle playerBattle;
	
	private ButtonMove buttonMove;
	private ButtonAttack buttonAttack;
	private ButtonSpecial buttonSpecial;
	private EntityCombatManager combatManager;
	
	public ActionHUD (SpritePlayerBattle player, EntityCombatManager combatManager){
		
		try {
			
			this.combatManager = combatManager;
			this.playerBattle = player;
			
			initButtons();
			
			//buttonMove.setPosition(player.getX()-buttonMove.getWidth()/2,player.getY()-buttonMove.getHeight());
			
			buttonAttack.setPosition(MainDropActivity.CAMERA_WIDTH - buttonAttack.getWidth() -5, MainDropActivity.CAMERA_HEIGHT/2 - buttonAttack.getHeight()/2);
			buttonMove.setPosition(buttonAttack.getX(), buttonAttack.getY()-buttonMove.getHeight() - 5);
			buttonSpecial.setPosition(buttonAttack.getX(), buttonAttack.getY() + buttonAttack.getHeight()+ 5);
			
			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0, texture.getTextureByName("closeHud.png"), texture.getVertexBufferObjectManager(), this);
			remove.setPosition(MainDropActivity.CAMERA_WIDTH - remove.getWidth()-5, 5);
			this.attachChild(remove);
			this.registerTouchArea(remove);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void initButtons() {
		try {
			
			buttonMove = new ButtonMove(0, 0, texture.getTextureByName("buttonMove.png"), texture.getVertexBufferObjectManager(),playerBattle,combatManager);
			buttonMove.setSize(40, 40);
			this.attachChild(buttonMove);
			this.registerTouchArea(buttonMove);
			
			buttonAttack = new ButtonAttack(0, 0, texture.getTextureByName("buttonAttack.png"), texture.getVertexBufferObjectManager(),playerBattle,combatManager);
			buttonAttack.setSize(40, 40);
			this.attachChild(buttonAttack);
			this.registerTouchArea(buttonAttack);
			
			//validate special techniques for player selected
			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			
			ArrayList<PlayerTechnique> techniqueList = dao.loadSpecialTechniqueAvalible(playerBattle.getId());
			if(techniqueList.isEmpty()){
				return;
			}
			
			buttonSpecial = new ButtonSpecial(0, 0, texture.getTextureByName("buttonSpecial.png"), texture.getVertexBufferObjectManager(),playerBattle,combatManager);
			buttonSpecial.setSize(40, 40);
			this.attachChild(buttonSpecial);
			this.registerTouchArea(buttonSpecial);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeHUD() {
		try {
			
			HUDManagerSingleton.getInstance().removeAndReplaceHud(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCloseAction() {

			try {
				
				//combatManager.pause(false);
				
			} catch (Exception e) {

			}
	}
	
}
