package com.mgl.drop.game.sprites.button.azeolandia;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.entity.azeolandia.EntityCombatManager;
import com.mgl.drop.game.entity.azeolandia.EntityDrawWorld;
import com.mgl.drop.game.sprites.azeoland.SpritePlayerBattle;
import com.mgl.drop.game.sprites.azeoland.SpritePlayerBattle.AttackType;

public class ButtonAttack extends MySprite{

	private SpritePlayerBattle playerBattle;

	private EntityCombatManager combatManager;
	
	public ButtonAttack(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, SpritePlayerBattle player, EntityCombatManager combatManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		this.playerBattle = player;
		this.combatManager = combatManager;
		
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {

			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

					playerBattle.setActionState(SpritePlayerBattle.ACTION_ATTACK);
					playerBattle.setAttackType(AttackType.ATTACK_BASIC);
					HUDManagerSingleton.getInstance().removeAndReplaceHud();
					combatManager.getDraw().setPaintEnemy(true);
					
				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				
				
				break;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
}