package com.mgl.drop.game.hud.zeolandia;

import java.util.ArrayList;

import org.andengine.entity.text.Text;

import com.mgl.base.ButtonListener;
import com.mgl.base.MyFactory;
import com.mgl.base.TextFactory;
import com.mgl.base.userinfo.InventarySingleton;
import com.mgl.base.userinfo.PlayerSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.hud.MyHud;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.model.Item;
import com.mgl.drop.game.model.PlayerModel;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.arunner.SpritePlayerModel;
import com.mgl.drop.game.sprites.azeoland.SpriteCompareItem;
import com.mgl.drop.game.sprites.azeoland.SpriteItem;
import com.mgl.drop.game.sprites.button.ButtonGeneral;
import com.mgl.drop.game.sprites.button.azeolandia.ButtonItem;
import com.mgl.drop.game.sprites.button.azeolandia.ButtonUseItem;

public class ItemHUD extends MyHud {

	private ArrayList<SpriteItem> itemList;
	private ButtonItem itemSelected = null;

	private ButtonItem button1;
	private ButtonItem button2;
	private ButtonItem button3;
	private ButtonItem button4;
	private ButtonItem button5;
	private ButtonItem button6;
	private ButtonItem button7;
	private ButtonItem button8;

	private ArrayList<ButtonItem> buttonList;

	private ButtonGeneral navigateNext;
	private ButtonGeneral navigatePrev;

	private ButtonUseItem buttonUseItem;

	private ButtonGeneral characterSelected;

	private Text itemName;

	private int beginPos = 0;
	private int endPosition = 6;

	private SpriteCompareItem compareItem;

	private boolean comeFromMenu = false;

	public ItemHUD(SpriteItem itemSelect) {
		try {

			if (itemSelect == null) {
				comeFromMenu = true;
			} else {
				comeFromMenu = false;
			}

			Text items = TextFactory.getItemText(texture.getmFont());
			items.setPosition(10, 10);

			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("backgroundItem.png"),
					texture.getVertexBufferObjectManager());
			background.setSize(MainDropActivity.CAMERA_WIDTH,
					MainDropActivity.CAMERA_HEIGHT);
			// background.setAlpha(0.6f);
			this.attachChild(background);

			initItemButtons();

			itemList = InventarySingleton.getInstance().getItemList();

			addItemsToButton();

			initNavigateButtons();

			initViewBar(itemSelect);

			this.attachChild(items);
			
			initDropButton();

			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0,
					texture.getTextureByName("closeHud.png"),
					texture.getVertexBufferObjectManager(), this);
			remove.setPosition(10,
					MainDropActivity.CAMERA_HEIGHT - remove.getHeight() - 5);
			this.attachChild(remove);
			this.registerTouchArea(remove);

			this.registerTouchArea(background);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDropButton() {
		try {
			
			ButtonGeneral dropButton = new ButtonGeneral(0, 0, texture.getTextureByName("drop.png"), texture.getVertexBufferObjectManager(), null);
			dropButton.setPosition(100, MainDropActivity.CAMERA_HEIGHT - dropButton.getHeight() -5);
			
			this.attachChild(dropButton);
			this.registerTouchArea(dropButton);

			dropButton.setButtonListener(new ButtonListener() {
				
				@Override
				public void onActionUp(float x, float y) {
					
					try {
						
						if(itemSelected==null || itemSelected.getItem()==null){
							//no hay nada para borrar
							return;
						}
						
						InventarySingleton.getInstance().dropItem(itemSelected.getItem());
						PlayerSingleton.getInstance().getPlayer().dropItem(itemSelected.getItem());
						itemSelected.setItem(null);
						addItemsToButton();
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
				@Override
				public void onActionMove(float x, float y) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onActionDown(float x, float y) {
					// TODO Auto-generated method stub
					
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initViewBar(SpriteItem itemSelectAux) {
		try {

			SpriteBackground black = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			black.setSize(100, MainDropActivity.CAMERA_HEIGHT);
			black.setPosition(MainDropActivity.CAMERA_WIDTH - black.getWidth(),
					0);
			black.setAlpha(0.5f);
			this.attachChild(black);

			itemSelected = new ButtonItem(0, 0,
					texture.getTextureByName("buttonItem.png"),
					texture.getVertexBufferObjectManager());
			itemSelected.setItem(itemSelectAux);
			itemSelected.setPosition(black.getX() + black.getWidth() / 2
					- itemSelected.getWidth() / 2, 10);

			// this.attachChild(itemSelected);

			buttonUseItem = new ButtonUseItem(0, 0,
					texture.getTextureByName("use.png"),
					texture.getVertexBufferObjectManager(), itemSelected,this);
			buttonUseItem.setPosition(black.getX() + black.getWidth() / 2
					- buttonUseItem.getWidth() / 2,
					MainDropActivity.CAMERA_HEIGHT - buttonUseItem.getHeight()
							- 5);

			this.attachChild(buttonUseItem);
			this.registerTouchArea(buttonUseItem);

			characterSelected = new ButtonGeneral(0, 0,
					texture.getTextureByName("zeoFace.png"),
					texture.getVertexBufferObjectManager(), null);
			characterSelected.setPosition(black.getX() + black.getWidth() / 2
					- characterSelected.getWidth() / 2, 10);
			this.attachChild(characterSelected);

			showComparison();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showComparison() {
		try {

			if (compareItem != null) {
				compareItem.detachSelf();
				compareItem.detachChildren();
			}

			SpriteItem item2 = itemSelected.getItem();
			PlayerModel player = PlayerSingleton.getInstance().getPlayer();
			SpriteItem item1 = null; 
					
			switch (item2.getItem().getItemType()) {
			case Item.EAR:
				 item1 = MyFactory.createItem(player.getEaring());
				break;
			case Item.HAND:
				 item1 = MyFactory.createItem(player.getHand());
				break;
			case Item.SOCK:
				 item1 = MyFactory.createItem(player.getSock());
				break;

			default:
				break;
			}

			compareItem = new SpriteCompareItem(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager(), item1, item2);
			compareItem.setPosition(characterSelected.getX() - 20,
					itemName.getY() + itemName.getHeight() + 15);
			compareItem.setAlpha(0);

			this.attachChild(compareItem);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initNavigateButtons() {
		try {

			navigateNext = new ButtonGeneral(0, 0,
					texture.getTextureByName("arrowRight.png"),
					texture.getVertexBufferObjectManager(), null);

			navigatePrev = new ButtonGeneral(0, 0,
					texture.getTextureByName("arrowLeft.png"),
					texture.getVertexBufferObjectManager(), null);

			navigatePrev.setPosition(190, MainDropActivity.CAMERA_HEIGHT
					- navigatePrev.getHeight() - 5);
			navigateNext.setPosition(
					5 + navigatePrev.getX() + navigatePrev.getWidth(),
					MainDropActivity.CAMERA_HEIGHT - navigatePrev.getHeight()
							- 5);

			this.attachChild(navigateNext);
			this.attachChild(navigatePrev);

			this.registerTouchArea(navigateNext);
			this.registerTouchArea(navigatePrev);

			navigateNext.setButtonListener(new ButtonListener() {

				@Override
				public void onActionUp(float x, float y) {

					if (beginPos + 8 <= InventarySingleton.getInstance()
							.getInventarySize()) {
						beginPos = beginPos + 8;
						addItemsToButton();
					}

				}

				@Override
				public void onActionMove(float x, float y) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onActionDown(float x, float y) {
					// TODO Auto-generated method stub

				}
			});

			navigatePrev.setButtonListener(new ButtonListener() {

				@Override
				public void onActionUp(float x, float y) {
					if (beginPos - 8 >= 0) {
						beginPos = beginPos - 8;
						addItemsToButton();
					}
				}

				@Override
				public void onActionMove(float x, float y) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onActionDown(float x, float y) {
					// TODO Auto-generated method stub

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addItemsToButton() {
		try {
			int i = beginPos;

			for (ButtonItem item : buttonList) {
				try {

					item.setItem(itemList.get(i));

				} catch (Exception e) {
					item.setItem(null);
				}
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void changeItemSelected(SpriteItem item) {
		try {

			itemSelected.setItem(item);

			showComparison();
			
			if (itemName != null) {
				itemName.detachSelf();
			}
			itemName = TextFactory.createText(item.getItem().getName(),
					texture.getmFont1());
			itemName.setPosition(itemSelected.getX() + itemSelected.getWidth()
					/ 2 - itemName.getWidth() / 2, itemSelected.getY()
					+ itemSelected.getHeight() - 5);
			this.attachChild(itemName);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initItemButtons() {
		try {

			button1 = new ButtonItem(0, 0,
					texture.getTextureByName("buttonItem.png"),
					texture.getVertexBufferObjectManager());
			button2 = new ButtonItem(0, 0,
					texture.getTextureByName("buttonItem.png"),
					texture.getVertexBufferObjectManager());
			button3 = new ButtonItem(0, 0,
					texture.getTextureByName("buttonItem.png"),
					texture.getVertexBufferObjectManager());
			button4 = new ButtonItem(0, 0,
					texture.getTextureByName("buttonItem.png"),
					texture.getVertexBufferObjectManager());
			button5 = new ButtonItem(0, 0,
					texture.getTextureByName("buttonItem.png"),
					texture.getVertexBufferObjectManager());
			button6 = new ButtonItem(0, 0,
					texture.getTextureByName("buttonItem.png"),
					texture.getVertexBufferObjectManager());
			button7 = new ButtonItem(0, 0,
					texture.getTextureByName("buttonItem.png"),
					texture.getVertexBufferObjectManager());
			button8 = new ButtonItem(0, 0,
					texture.getTextureByName("buttonItem.png"),
					texture.getVertexBufferObjectManager());

			button1.setPosition(10, 40);
			button2.setPosition(button1.getX() + button1.getWidth(),
					button1.getY());
			button3.setPosition(button2.getX() + button2.getWidth(),
					button2.getY());
			button4.setPosition(button3.getX() + button3.getWidth(),
					button3.getY());

			button5.setPosition(button1.getX(),
					button1.getY() + button1.getHeight());
			button6.setPosition(button5.getX() + button5.getWidth(),
					button5.getY());
			button7.setPosition(button6.getX() + button6.getWidth(),
					button6.getY());
			button8.setPosition(button7.getX() + button7.getWidth(),
					button7.getY());

			this.attachChild(button1);
			this.attachChild(button2);
			this.attachChild(button3);
			this.attachChild(button4);
			this.attachChild(button5);
			this.attachChild(button6);
			this.attachChild(button7);
			this.attachChild(button8);

			this.registerTouchArea(button1);
			this.registerTouchArea(button2);
			this.registerTouchArea(button3);
			this.registerTouchArea(button4);
			this.registerTouchArea(button5);
			this.registerTouchArea(button6);
			this.registerTouchArea(button7);
			this.registerTouchArea(button8);

			buttonList = new ArrayList<ButtonItem>();
			buttonList.add(button1);
			buttonList.add(button2);
			buttonList.add(button3);
			buttonList.add(button4);
			buttonList.add(button5);
			buttonList.add(button6);
			buttonList.add(button7);
			buttonList.add(button8);

			for (final ButtonItem item : buttonList) {

				item.setButtonListener(new ButtonListener() {

					@Override
					public void onActionUp(float x, float y) {
						changeItemSelected(item.getItem());

					}

					@Override
					public void onActionMove(float x, float y) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onActionDown(float x, float y) {
						// TODO Auto-generated method stub

					}
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCloseAction() {
		try {

//			if(comeFromMenu){
//				return;
//			}
			HUDManagerSingleton.getInstance().addHUD(new EquipHUD(), true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
