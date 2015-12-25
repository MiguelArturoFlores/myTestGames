package com.mgl.drop.game.sprites.button.azeolandia.technique;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.entity.azeolandia.EntityCombatManager;
import com.mgl.drop.game.sprites.azeoland.SpritePlayerBattle;
import com.mgl.drop.game.sprites.azeoland.SpritePlayerBattle.AttackType;

public class ButtonTechniqueSpecial1 extends MySprite{

	private SpritePlayerBattle playerBattle;
	private EntityCombatManager combatManager;
	
	private AttackType attackType;
	
	public ButtonTechniqueSpecial1(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, SpritePlayerBattle player, EntityCombatManager combatManager, AttackType attackType) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		this.playerBattle = player;
		this.combatManager = combatManager;
		this.attackType = attackType;
		
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
					playerBattle.setAttackType(attackType);
					HUDManagerSingleton.getInstance().removeAndReplaceHud();
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
