package com.mgl.base.userinfo;


import java.util.ArrayList;

import com.mgl.base.MyFactory;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.model.Item;
import com.mgl.drop.game.model.PlayerModel;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.azeoland.SpriteItem;

public class InventarySingleton {

	public static final Long ID_HEALTH_POTION_FREE = -1000L;
	public static final Long ID_MANA_POTION_FREE = -2000L;

	private static InventarySingleton instance = null;

	private ArrayList<SpriteItem> itemList;
	
	private int inventarySize = 24;

	private InventarySingleton() {
		try {

			itemList = new ArrayList<SpriteItem>();
			//fillItemList();
			loadItemListFromDB();
			
			verifyEquipedItem();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void verifyEquipedItem() {
		try {

			
			for(SpriteItem item : itemList){
				
				PlayerModel player = PlayerSingleton.getInstance().getPlayer();
				
				switch (item.getItem().getItemType()) {
				
				case Item.EAR:

					player.setEaring(item.getItem());

					break;
				case Item.HAND:

					player.setHand(item.getItem());

					break;
				case Item.SOCK:

					player.setSock(item.getItem());

					break;

				default:
					break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadItemListFromDB() {
		try {
			
			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			
			itemList = new ArrayList<SpriteItem>();
			
			ArrayList<Item> itemListAux = dao.loadItemByIdOwner(GameConstants.ID_PLAYER_OWNER);
			for(Item item : itemListAux){
				itemList.add(MyFactory.createItem(item));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillItemList() {
		try {

			itemList = new ArrayList<SpriteItem>();

			Item item = new Item(1L,null, "Botas", "item1.png", 1, 2, 1, 2, 2, 1, Item.SOCK);
			itemList.add(MyFactory.createItem(item));
			// HP MP LVL def atk dex str int
			item = new Item(2L,null, "Ears", "item2.png", 3, 0, 1, 0, 2, 3,Item.EAR);
			itemList.add(MyFactory.createItem(item));

			item = new Item(3L,null, "Hands", "item3.png", 0, 2, 1, 2, 0, 2,Item.HAND);
			itemList.add(MyFactory.createItem(item));


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static InventarySingleton getInstance() {
		if (instance == null) {
			instance = new InventarySingleton();
		}
		return instance;
	}

	
	public ArrayList<SpriteItem> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<SpriteItem> itemList) {
		this.itemList = itemList;
	}

	public static void setInstance(InventarySingleton instance) {
		InventarySingleton.instance = instance;
	}

	private ArrayList<SpriteItem> getItems(int begin, int end) {

		try {
			ArrayList<SpriteItem> itemListRet = new ArrayList<SpriteItem>();
			int i = 0;
			for (SpriteItem item : itemList) {
				if (i < 6) {
					itemListRet.add(item);
				} else {
					break;
				}
				i++;
			}

			for (int j = i; j < 6; j++) {
				itemListRet.add(null);
			}

			return itemListRet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getInventarySize() {
		return inventarySize;
	}

	public void setInventarySize(int inventarySize) {
		this.inventarySize = inventarySize;
	}

	public void dropItem(SpriteItem item) {
		try {
			
			SpriteItem itemToDelete = null;
			for(SpriteItem itemS : itemList){
				if(item.getItem().getId().equals(itemS.getItem().getId())){
					itemToDelete = itemS;
					break;
				}
			}
			
			itemList.remove(itemToDelete);
			
			//remove from relation in db
			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			
			dao.dropItem(item.getItem());

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public boolean pickUpItem(Item item) {
		try {
			if(itemList.size()>=inventarySize){
				return false;
			}
			
			itemList.add(MyFactory.createItem(item));
			
			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			dao.pickUpItem(item);

			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	
}
