package com.mgl.drop.game.sprites.azeoland;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.NPCSingleton;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.DialogController;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.zeolandia.DialogHUD;
import com.mgl.drop.game.sprites.button.azeolandia.ButtonDialog;
import com.mgl.drop.util.Point;

public class SpriteNPC extends MyAnimateSprite{

	private String currentDialog = null;
	private float distanceToDialog = 70;
	private Long id;
	
	
	private ButtonDialog dialogButton;
	
	public SpriteNPC(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		try {
		
			dialogButton = MyFactory.createDialogButton(level);
			dialogButton.setNpc(this);
			dialogButton.setPosition(this.getWidth()/2- dialogButton.getWidth()/2, -dialogButton.getHeight());
			this.attachChild(dialogButton);
			
		} catch (Exception e) {

		}
		
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.NPC;
	}

	@Override
	public void initAnimationParams() {
		try {

			changeAnimateState(State.IDLE, false);
			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83};

			stateAnimate.put(State.WALKIN_UP, new MyAnimateProperty(0, 3,
					fps));
			stateAnimate.put(State.WALKIN_DOWN, new MyAnimateProperty(3, 3,
					fps));
			stateAnimate.put(State.WALKIN_LEFT, new MyAnimateProperty(6, 3,
					fps));
			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(9, 3,
					fps));
			
			stateAnimate.put(State.NORMAL, new MyAnimateProperty(9, 2,
					new long[] { 83, 83}));
			
			stateAnimate.put(State.IDLE, new MyAnimateProperty(3, 3,
					new long[] { 83, 83, 83}));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			
			if(Point.distanceBetween(new Point(this.getX(), this.getY()), new Point(level.getPlayerAdventure().getX(), level.getPlayerAdventure().getY()))<distanceToDialog){
				showDialogButton(true,level);
			}else{
				showDialogButton(false,level);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showDialogButton(boolean show, LevelController level) {
		try {
			
			
			if(show && dialogButton.getAlpha()<=0){
				dialogButton.setAlpha(1f);
				dialogButton.showAnimate();
				level.getScene().registerTouchArea(dialogButton);
			}
			
			if(!show && dialogButton.getAlpha()>=1){
				dialogButton.setAlpha(0f);
				level.getScene().unregisterTouchArea(dialogButton);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void showDialog() {
		try {
			
			//decide wich dialog to show
			level.setMustUpdate(false);
			DialogController dialogController = NPCSingleton.getInstance().createNpcDialog(id);
			
			DialogHUD hud = new DialogHUD(dialogController, this, level);
			HUDManagerSingleton.getInstance().addHUD(hud, false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DialogController createDialogTest1() {
		try {
			
			DialogController dialog1 = new  DialogController(null, null, "Hola estoy aca y no se que hago puedes ayudarme ?");
			DialogController dialog1Yes = new  DialogController(null, null, "Gracias, debes ir a la casa de al lado y volver, lo haras?");
			DialogController dialog1No = new  DialogController(null, null, "ok chao!");
			
			DialogController dialog2Yes = new  DialogController(null, null, "Gracias estare esperando! adios!");
			DialogController dialog2No = new  DialogController(null, null, "Tu te lo pierdes!");
			dialog1Yes.setDialogNo(dialog2No);
			dialog1Yes.setDialogYes(dialog2Yes);
			
			
			dialog1.setDialogNo(dialog1No);
			dialog1.setDialogYes(dialog1Yes);
			
			return dialog1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setXmlParameter(String parameter) {
		try {

			ArrayList<Long> parameterList = MyXmlParser
					.getParameterList(parameter);
			int i = 0;
			for (Long param : parameterList) {
				try {
					if (i == 0) {
						id = param;
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
