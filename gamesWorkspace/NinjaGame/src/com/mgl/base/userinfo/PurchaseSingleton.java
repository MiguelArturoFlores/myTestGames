package com.mgl.base.userinfo;

import java.util.ArrayList;

import com.mgl.base.MySprite;
import com.mgl.drop.game.constant.GamePurchaseConstant;
import com.mgl.drop.game.database.model.MyPurchase;
import com.mgl.drop.game.sprites.button.ButtonBuyMoney;
import com.mgl.drop.texture.TextureSingleton;

public class PurchaseSingleton {

	private static PurchaseSingleton instance = null;

	private ArrayList<MySprite> spriteList;
	private ArrayList<MyPurchase> purchaseList;

	private ArrayList<MySprite> spriteOffertList;
	private ArrayList<MyPurchase> purchaseOffertList;

	private PurchaseSingleton() {
		try {

			loadPurchaseList();
			loadPurchaseOffertList();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadPurchaseOffertList() {

		try {

			TextureSingleton texture = TextureSingleton.getInstance();

			purchaseOffertList = new ArrayList<MyPurchase>();

			
			MyPurchase purchase = new MyPurchase(GamePurchaseConstant.BUY_D_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_D,
					GamePurchaseConstant.PRICE_BUY_D_OFFERT,
					GamePurchaseConstant.QUANTITY_BUY_D,
					GamePurchaseConstant.BUY_D_OFFERT,
					GamePurchaseConstant.BUY_D_OFFERT_STRING);
			purchaseOffertList.add(purchase);

			purchase = new MyPurchase(GamePurchaseConstant.BUY_E_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_E,
					GamePurchaseConstant.PRICE_BUY_E_OFFERT,
					GamePurchaseConstant.QUANTITY_BUY_E,
					GamePurchaseConstant.BUY_E_OFFERT,
					GamePurchaseConstant.BUY_E_OFFERT_STRING);
			purchaseOffertList.add(purchase);

			purchase = new MyPurchase(GamePurchaseConstant.BUY_F_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_F,
					GamePurchaseConstant.PRICE_BUY_F_OFFERT,
					GamePurchaseConstant.QUANTITY_BUY_F,
					GamePurchaseConstant.BUY_F_OFFERT,
					GamePurchaseConstant.BUY_F_OFFERT_STRING);
			purchaseOffertList.add(purchase);

			purchase = new MyPurchase(GamePurchaseConstant.BUY_G_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_G,
					GamePurchaseConstant.PRICE_BUY_G_OFFERT,
					GamePurchaseConstant.QUANTITY_BUY_G,
					GamePurchaseConstant.BUY_G_OFFERT,
					GamePurchaseConstant.BUY_G_OFFERT_STRING);
			purchaseOffertList.add(purchase);

			purchase = new MyPurchase(GamePurchaseConstant.BUY_H_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_H,
					GamePurchaseConstant.PRICE_BUY_H_OFFERT,
					GamePurchaseConstant.QUANTITY_BUY_H,
					GamePurchaseConstant.BUY_H_OFFERT,
					GamePurchaseConstant.BUY_H_OFFERT_STRING);
			purchaseOffertList.add(purchase);

			purchase = new MyPurchase(GamePurchaseConstant.BUY_I_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_I,
					GamePurchaseConstant.PRICE_BUY_I_OFFERT,
					GamePurchaseConstant.QUANTITY_BUY_I,
					GamePurchaseConstant.BUY_I_OFFERT,
					GamePurchaseConstant.BUY_I_OFFERT_STRING);
			purchaseOffertList.add(purchase);

			spriteOffertList = new ArrayList<MySprite>();

			for (MyPurchase p : purchaseOffertList) {

				ButtonBuyMoney buy = new ButtonBuyMoney(0, 0,
						texture.getTextureByName(p.getTextureName()),
						texture.getVertexBufferObjectManager(), p.getPrice(),
						p.getId(), p.getQuantity(),p.getNewPrice());
				spriteOffertList.add(buy);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public static PurchaseSingleton getInstance() {
		if (instance == null) {
			instance = new PurchaseSingleton();
		}
		return instance;
	}

	public void loadPurchaseList() {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();

			purchaseList = new ArrayList<MyPurchase>();

			MyPurchase purchase = new MyPurchase(
					GamePurchaseConstant.BUY_A_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_A,
					GamePurchaseConstant.QUANTITY_BUY_A,
					GamePurchaseConstant.BUY_A,
					GamePurchaseConstant.BUY_A_STRING);
			purchaseList.add(purchase);

			purchase = new MyPurchase(GamePurchaseConstant.BUY_B_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_B,
					GamePurchaseConstant.QUANTITY_BUY_B,
					GamePurchaseConstant.BUY_B,
					GamePurchaseConstant.BUY_B_STRING);
			purchaseList.add(purchase);

			purchase = new MyPurchase(GamePurchaseConstant.BUY_C_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_C,
					GamePurchaseConstant.QUANTITY_BUY_C,
					GamePurchaseConstant.BUY_C,
					GamePurchaseConstant.BUY_C_STRING);
			purchaseList.add(purchase);

			purchase = new MyPurchase(GamePurchaseConstant.BUY_D_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_D,
					GamePurchaseConstant.QUANTITY_BUY_D,
					GamePurchaseConstant.BUY_D,
					GamePurchaseConstant.BUY_D_STRING);
			purchaseList.add(purchase);

			purchase = new MyPurchase(GamePurchaseConstant.BUY_E_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_E,
					GamePurchaseConstant.QUANTITY_BUY_E,
					GamePurchaseConstant.BUY_E,
					GamePurchaseConstant.BUY_E_STRING);
			purchaseList.add(purchase);

			purchase = new MyPurchase(GamePurchaseConstant.BUY_F_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_F,
					GamePurchaseConstant.QUANTITY_BUY_F,
					GamePurchaseConstant.BUY_F,
					GamePurchaseConstant.BUY_F_STRING);
			purchaseList.add(purchase);

			purchase = new MyPurchase(GamePurchaseConstant.BUY_G_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_G,
					GamePurchaseConstant.QUANTITY_BUY_G,
					GamePurchaseConstant.BUY_G,
					GamePurchaseConstant.BUY_G_STRING);
			purchaseList.add(purchase);

			purchase = new MyPurchase(GamePurchaseConstant.BUY_H_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_H,
					GamePurchaseConstant.QUANTITY_BUY_H,
					GamePurchaseConstant.BUY_H,
					GamePurchaseConstant.BUY_H_STRING);
			purchaseList.add(purchase);

			purchase = new MyPurchase(GamePurchaseConstant.BUY_I_TEXTURE,
					GamePurchaseConstant.PRICE_BUY_I,
					GamePurchaseConstant.QUANTITY_BUY_I,
					GamePurchaseConstant.BUY_I,
					GamePurchaseConstant.BUY_I_STRING);
			purchaseList.add(purchase);

			spriteList = new ArrayList<MySprite>();

			for (MyPurchase p : purchaseList) {

				ButtonBuyMoney buy = new ButtonBuyMoney(0, 0,
						texture.getTextureByName(p.getTextureName()),
						texture.getVertexBufferObjectManager(), p.getPrice(),
						p.getId(), p.getQuantity());
				spriteList.add(buy);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public ArrayList<MySprite> getSpriteList() {
		return spriteList;
	}

	public void setSpriteList(ArrayList<MySprite> spriteList) {
		this.spriteList = spriteList;
	}

	public ArrayList<MyPurchase> getPurchaseList() {
		return purchaseList;
	}

	public void setPurchaseList(ArrayList<MyPurchase> purchaseList) {
		this.purchaseList = purchaseList;
	}

	public ArrayList<MySprite> getSpriteOffertList() {
		return spriteOffertList;
	}

	public void setSpriteOffertList(ArrayList<MySprite> spriteOffertList) {
		this.spriteOffertList = spriteOffertList;
	}

	public ArrayList<MyPurchase> getPurchaseOffertList() {
		return purchaseOffertList;
	}

	public void setPurchaseOffertList(ArrayList<MyPurchase> purchaseOffertList) {
		this.purchaseOffertList = purchaseOffertList;
	}

}
