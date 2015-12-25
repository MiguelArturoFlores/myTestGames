package com.mgl.drop.game.sprites;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.ninja.SpritePlayer;

public class SpritePortal extends MySprite{
	
	private Long idLevel;
	private Long x;
	private Long y;

	public SpritePortal(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.PORTAL;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			SpritePlayer p = level.getPlayer();
			
			if(p.getX()+p.getMidPoint().getX() >= this.getX() && p.getX() + p.getMidPoint().getX()<= this.getX()+ this.getWidth() ){
				if(p.getY()+ p.getMidPoint().getY() >= this.getY() && p.getY() + p.getMidPoint().getY()<= this.getY()+ this.getHeight() ){
				 level.loadLevel(idLevel,x,y);
				 level.removeEntity(this);
				 
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
						x = param;
					}
					if (i == 2) {
						y = param;
					}

				} catch (Exception e) {

				}
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
