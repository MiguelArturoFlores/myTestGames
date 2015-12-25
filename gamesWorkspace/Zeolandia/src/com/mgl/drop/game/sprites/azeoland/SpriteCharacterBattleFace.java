package com.mgl.drop.game.sprites.azeoland;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.entity.azeolandia.EntityCombatManager;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class SpriteCharacterBattleFace extends MySprite{

	private SpritePlayerBattle playerBattle;
	private EntityCombatManager combatManager;
	
	public SpriteCharacterBattleFace(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager,LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,level);

	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {

			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
					
				if(!level.isMustUpdate()){
					return true;
				}
						level.showCombatHud(playerBattle,combatManager);
						combatManager.pause(true);
						//level.getScene().setIgnoreUpdate(true);
				
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

	public SpritePlayerBattle getPlayerBattle() {
		return playerBattle;
	}

	public void setPlayerBattle(SpritePlayerBattle playerBattle) {
		this.playerBattle = playerBattle;
	}

	public EntityCombatManager getCombatManager() {
		return combatManager;
	}

	public void setCombatManager(EntityCombatManager combatManager) {
		this.combatManager = combatManager;
	}

	
	
}
