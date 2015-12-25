package com.mgl.drop.game.sprites.button.azeolandia;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.ButtonListener;
import com.mgl.base.userinfo.InventarySingleton;
import com.mgl.base.userinfo.PlayerSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.hud.zeolandia.ItemHUD;
import com.mgl.drop.game.model.Item;
import com.mgl.drop.game.model.PlayerModel;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.button.ButtonGeneral;

public class ButtonUseItem extends ButtonGeneral {

	private ButtonItem itemSelected;
	
	private ItemHUD hud;

	public ButtonUseItem(int pX, int pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			ButtonItem itemSelected, ItemHUD itemHud) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager, null);
		try {
			this.hud = itemHud;	
			this.itemSelected = itemSelected;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
				pTouchAreaLocalY);

		try {

			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

				PlayerModel player = PlayerSingleton.getInstance().getPlayer();
				if (itemSelected == null) {
					System.out.println("item select null");
					return true;
				}

				switch (itemSelected.getItem().getItem().getItemType()) {
				case Item.EAR:

					player.setEaring(itemSelected.getItem().getItem());

					break;
				case Item.HAND:

					player.setHand(itemSelected.getItem().getItem());

					break;
				case Item.SOCK:

					player.setSock(itemSelected.getItem().getItem());

					break;

				case Item.POTION:

					boolean used = player.usePotion(itemSelected.getItem().getItem());
					if(used){
					
						LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
								.getActivity(), DatabaseDrop.DB_NAME, null,
								MainDropActivity.DB_VERSION);
						
						InventarySingleton.getInstance().dropItem(itemSelected.getItem());
						itemSelected.setItem(null);
						hud.addItemsToButton();
						
					}
					
					
					
					break;

					
				default:
					break;
				}
				
				HUDManagerSingleton.getInstance().removeAndReplaceHud();

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

}
