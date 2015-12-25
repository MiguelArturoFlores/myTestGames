package com.mgl.drop.game.sprites.aninja;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.TextFactory;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.ninja.SpritePlayer;
import com.mgl.ninja.R;

public class SpriteVictory extends MySprite {

	public static final int ALL_VALID = 0;
	public static final int DONT_KILL = 1;
	public static final int KILL_ALL = 2;

	private Long idLevel;
	private int victoryCondition = ALL_VALID;

	public SpriteVictory(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.VICTORY;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {

			boolean hasKill = false;
			boolean killAll = true;
			
			for (MySpriteGeneral spr : level.getSpriteList()) {
				if (spr instanceof SpriteEnemy) {
					if (((SpriteEnemy) spr).isDead()) {
						hasKill = true;	
					}else{
						killAll = false;
					}
				}
			}
			
			SpritePlayer p = level.getPlayer();
			
			if(victoryCondition == ALL_VALID){
				
				if (p.getX() + p.getMidPoint().getX() >= this.getX()
						&& p.getX() + p.getMidPoint().getX() <= this.getX()
								+ this.getWidth()) {
					if (p.getY() + p.getMidPoint().getY() >= this.getY()
							&& p.getY() + p.getMidPoint().getY() <= this.getY()
									+ this.getHeight()) {
					level.winLevel(idLevel);
					}
				}
				
				return;
			}
			
			if(victoryCondition == DONT_KILL){
				if(hasKill){
					level.looseLevelReplay();
					return;
				}
				if (p.getX() + p.getMidPoint().getX() >= this.getX()
						&& p.getX() + p.getMidPoint().getX() <= this.getX()
								+ this.getWidth()) {
					if (p.getY() + p.getMidPoint().getY() >= this.getY()
							&& p.getY() + p.getMidPoint().getY() <= this.getY()
									+ this.getHeight()) {
					level.winLevel(idLevel);
					}
				}
			}
			
			if(p.getX()+p.getMidPoint().getX() >= this.getX() && p.getX() + p.getMidPoint().getX()<= this.getX()+ this.getWidth() ){
				if(p.getY()+ p.getMidPoint().getY() >= this.getY() && p.getY() + p.getMidPoint().getY()<= this.getY()+ this.getHeight() ){
				if(victoryCondition == KILL_ALL){
					if(!killAll){
						SpriteMessage message = MyFactory.createMessage(TextFactory.createText(R.string.KILL_ALL), 3);
						HUDManagerSingleton.getInstance().getTop().attachChild(message);
						message.setAutoUpdate();
						
					}else{
						level.winLevel(idLevel);
					}
				}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setXmlParameter(String parameter) {
		try {

			ArrayList<Long> parameterList = MyXmlParser
					.getParameterList(parameter);
			int i = 0;
			for (Long param : parameterList) {
				try {
					if (i == 0) {
						idLevel = param;
					}
					if (i == 1) {
						if (param.intValue() == 1) {
							victoryCondition = DONT_KILL;
							return;
						}
					}

					if (i == 2) {
						if (param.intValue() == 1) {
							victoryCondition = KILL_ALL;
							return;
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
