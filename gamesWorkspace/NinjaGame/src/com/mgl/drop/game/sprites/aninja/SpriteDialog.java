package com.mgl.drop.game.sprites.aninja;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.ninja.DialogHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.ninja.SpritePlayer;
import com.mgl.ninja.R;

public class SpriteDialog extends MySprite{

	private Long idDialog;
	
	public SpriteDialog(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DIALOG;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			SpritePlayer p = level.getPlayer();
			
			if(p.getX()+p.getMidPoint().getX() >= this.getX() && p.getX() + p.getMidPoint().getX()<= this.getX()+ this.getWidth() ){
				if(p.getY()+ p.getMidPoint().getY() >= this.getY() && p.getY() + p.getMidPoint().getY()<= this.getY()+ this.getHeight() ){
					
					level.setMustUpdate(false);
					this.detachSelf();
					level.removeEntity(this);
					
					DialogHUD hud = new DialogHUD(createDialog(),level);
					HUDManagerSingleton.getInstance().addHUD(hud, true);
					
					return;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String createDialog() {
		try {
			
			switch (idDialog.intValue()) {
			case 1:
				
				return SceneManagerSingleton.getInstance().getActivity().getString(R.string.DIALOG1);
			
			case 2:
				return SceneManagerSingleton.getInstance().getActivity().getString(R.string.DIALOG2);

			case 3:
				return SceneManagerSingleton.getInstance().getActivity().getString(R.string.DIALOG3);
			
			case 4:
				return SceneManagerSingleton.getInstance().getActivity().getString(R.string.DIALOG4);
			
			case 5:
				return SceneManagerSingleton.getInstance().getActivity().getString(R.string.DIALOG5);
			
			case 6:
				return SceneManagerSingleton.getInstance().getActivity().getString(R.string.DIALOG6);
			case 7:
				return SceneManagerSingleton.getInstance().getActivity().getString(R.string.DIALOG7);
				
			default:
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public void setXmlParameter(String parameter) {
		try {
			
			ArrayList<Long> parameterList = MyXmlParser.getParameterList(parameter);
			int i = 0;
			for(Long param : parameterList){
				try {
					if(i==0){
						idDialog = param;
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
