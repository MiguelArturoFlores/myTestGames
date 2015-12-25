package com.mgl.drop.game.hud.zeolandia;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.entity.azeolandia.EntityCombatManager;
import com.mgl.drop.game.hud.MyHud;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.sprites.azeoland.SpritePlayerBattle;
import com.mgl.drop.game.sprites.azeoland.SpritePlayerBattle.AttackType;
import com.mgl.drop.game.sprites.button.azeolandia.technique.ButtonTechniqueSpecial1;

public class SpecialPowerHUD extends MyHud {

	private EntityCombatManager combatManager;
	private SpritePlayerBattle playerBattle;

	public SpecialPowerHUD(EntityCombatManager combatManager,
			SpritePlayerBattle playerBattle) {
		try {

			this.combatManager = combatManager;
			this.playerBattle = playerBattle;
			
			initHUD();
			
			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0, texture.getTextureByName("closeHud.png"), texture.getVertexBufferObjectManager(), this);
			remove.setPosition(MainDropActivity.CAMERA_WIDTH - remove.getWidth()-5, 5);
			this.attachChild(remove);
			this.registerTouchArea(remove);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initHUD() {
		try {

			ButtonTechniqueSpecial1 technique1 = new ButtonTechniqueSpecial1(0, 0, texture.getTextureByName("buttonAttack.png"), texture.getVertexBufferObjectManager(), playerBattle, combatManager, AttackType.ATTACK_SPECIAL1_ZEO);
			technique1.setPosition(MainDropActivity.CAMERA_WIDTH - technique1.getWidth() -5, 70);
			this.attachChild(technique1);
			this.registerTouchArea(technique1);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCloseAction() {
		
	}

}
