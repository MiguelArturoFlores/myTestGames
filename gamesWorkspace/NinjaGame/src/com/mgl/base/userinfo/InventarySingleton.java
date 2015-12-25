package com.mgl.base.userinfo;

import java.util.ArrayList;

import com.mgl.base.MyFactory;
import com.mgl.drop.game.model.Item;
import com.mgl.drop.game.model.Item.ItemType;
import com.mgl.drop.game.sprites.azeoland.SpriteItem;

public class InventarySingleton {

	private static InventarySingleton instance = null;

	private ArrayList<SpriteItem> itemList;
	
	private int inventarySize = 24;

	private InventarySingleton() {
		try {

			itemList = new ArrayList<SpriteItem>();
			fillItemList();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillItemList() {
		try {

			itemList = new ArrayList<SpriteItem>();

			Item item = new Item(1L,null, "Botas", "item1.png", 1, 2, 1, 2, 1, 1,
					2, 1, ItemType.FOOT);
			itemList.add(MyFactory.createItem(item));
			// HP MP LVL def atk dex str int
			item = new Item(2L,null, "Ears", "item2.png", 3, 0, 1, 0, 2, 1, 2, 3,ItemType.EAR);
			itemList.add(MyFactory.createItem(item));

			item = new Item(3L,null, "Hands", "item3.png", 0, 2, 1, 2, 0, 2, 0, 2,ItemType.HAND);
			itemList.add(MyFactory.createItem(item));

			item = new Item(4L,null, "Botas B", "item4.png", 0, 0, 0, 0, 0, 8, 1,
					0,ItemType.FOOT);
			itemList.add(MyFactory.createItem(item));

			item = new Item(5L,null, "Ears B", "item5.png", 0, 0, 0, 0, 0, 0, 5, 0,ItemType.EAR);
			itemList.add(MyFactory.createItem(item));

			item = new Item(6L,null, "Hand B", "item6.png", 0, 0, 0, 0, 0, 1, 0, 1,ItemType.HAND);
			itemList.add(MyFactory.createItem(item));
			
			item = new Item(7L,null, "Botas C", "item7.png", 0, 0, 0, 0, 3, 0, 1,
					4,ItemType.FOOT);
			itemList.add(MyFactory.createItem(item));

			item = new Item(8L,null, "Ears C", "item8.png", 0, 0, 0, 0, 0, 1, 6, 2,ItemType.EAR);
			itemList.add(MyFactory.createItem(item));

			item = new Item(9L,null, "Hand C", "item9.png", 0, 0, 0, 0, 0, 2, 6, -2,ItemType.HAND);
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
				}
			}
			
			itemList.remove(itemToDelete);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
