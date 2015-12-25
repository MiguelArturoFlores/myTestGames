package com.mgl.drop.game.sprites.button.azeolandia;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.ButtonListener;
import com.mgl.base.userinfo.PlayerSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.model.PlayerModel;
import com.mgl.drop.game.model.Item.ItemType;
import com.mgl.drop.game.sprites.button.ButtonGeneral;

public class ButtonUseItem extends ButtonGeneral {

	private ButtonItem itemSelected;

	public ButtonUseItem(int pX, int pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			ButtonItem itemSelected) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager, null);
		try {

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
				case EAR:

					player.setEaring(itemSelected.getItem().getItem());

					break;
				case HAND:

					player.setHand(itemSelected.getItem().getItem());

					break;
				case FOOT:

					player.setSock(itemSelected.getItem().getItem());

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
