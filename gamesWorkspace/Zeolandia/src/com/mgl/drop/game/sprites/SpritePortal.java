package com.mgl.drop.game.sprites;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.scene.SceneManagerSingleton;

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
			
			if(this.collidesWith(level.getPlayerAdventure())){
				LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
						.getActivity(), DatabaseDrop.DB_NAME, null,
						MainDropActivity.DB_VERSION);
				dao.unlockLevel(idLevel.intValue());
				level.loadLevel(idLevel,x,y);
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
