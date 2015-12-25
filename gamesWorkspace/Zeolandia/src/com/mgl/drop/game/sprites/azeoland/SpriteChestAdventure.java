package com.mgl.drop.game.sprites.azeoland;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.model.Item;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.button.azeolandia.ButtonPickUp;
import com.mgl.drop.util.Point;

public class SpriteChestAdventure extends MyAnimateSprite {

	private Item item;
	private ButtonPickUp buttonPickUp;
	private Long idItem; 
	
	private long distanceToPickUp = 25;

	public SpriteChestAdventure(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		try {

			buttonPickUp = MyFactory.createPickUpButton(level);
			buttonPickUp.setItem(this);
			buttonPickUp.setPosition(
					this.getWidth() / 2 - buttonPickUp.getWidth() / 2,
					-buttonPickUp.getHeight());
			buttonPickUp.setAlpha(0);
			this.attachChild(buttonPickUp);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void initAnimationParams() {
		changeAnimateState(State.NORMAL, false);
		anime(false);
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83 };

			stateAnimate.put(State.NORMAL, new MyAnimateProperty(0, 2, fps));

			stateAnimate.put(State.OPEN, new MyAnimateProperty(2, 4,
					new long[] { 83, 83, 83, 83 }));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			if(!buttonPickUp.hasParent()){
				return;
			}
			
			if(item == null){
				this.detachSelf();
			}
			
			if (Point.distanceBetween(new Point(this.getX(), this.getY()),
					new Point(level.getPlayerAdventure().getX(), level
							.getPlayerAdventure().getY())) < distanceToPickUp) {
				
				if(item == null){
					showDialogButton(false, level);
					return ;
				}
				
				showDialogButton(true, level);
			} else {
				showDialogButton(false, level);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showDialogButton(boolean show, LevelController level) {
		try {
			
			
			if(show && buttonPickUp.getAlpha()<=0){
				buttonPickUp.setAlpha(1f);
				buttonPickUp.showAnimate();
				level.getScene().registerTouchArea(buttonPickUp);
			}
			
			if(!show && buttonPickUp.getAlpha()>=1){
				buttonPickUp.setAlpha(0f);
				level.getScene().unregisterTouchArea(buttonPickUp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public void setXmlParameter(String parameter) {
		try {
			
			ArrayList<Long> parameterList = MyXmlParser.getParameterList(parameter);
			int i = 0;
			for(Long param : parameterList){
				try {
					if(i==0){
						idItem = param;
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
			}
			
			loadItemById();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadItemById() {
		try {
			
			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			
			ArrayList<Item> itemList = null;
			try {
				itemList = dao.loadItemByIdOwner(idItem);
				if(itemList == null || itemList.isEmpty()){
					reanimateState(State.OPEN, false);
					return;
				}
				item = itemList.get(0);	
			} catch (Exception e) {
				item = null;
				changeAnimateState(State.OPEN, true);
				anime(false);
				e.printStackTrace();
			}
			
			if(item == null || item.getId() == null || itemList == null || itemList.isEmpty()){
				changeAnimateState(State.OPEN, false);
				anime(false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	
	
}
