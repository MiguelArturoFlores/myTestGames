package com.mgl.drop.game.sprites.azeoland;

import org.andengine.entity.text.Text;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.TextFactory;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.model.Item;

public class SpriteCompareItem extends MySprite{

	public SpriteCompareItem(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, SpriteItem item1, SpriteItem item2) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		
		compare(item1,item2);
		
	}

	private void compare(SpriteItem item1Spr, SpriteItem item2Spr) {
		try {
			
			if(item2Spr ==null || item2Spr.getItem()==null){
				return;
			}
			
			Text str = TextFactory.createText("ATK", texture.getmFont4());
			Text dex = TextFactory.createText("DEF", texture.getmFont4());
			Text inte = TextFactory.createText("MMP", texture.getmFont4());
			
			str.setPosition(0,0);
			dex.setPosition(0,str.getY()+str.getHeight()+2);
			inte.setPosition(0,dex.getY()+dex.getHeight()+2);
			
			Item item1 = null; 
			
			if(item1Spr==null || item1Spr.getItem() == null){
				item1 = new Item(-1L,null, "", "", 0, 0, 0, 0, 0, 0, Item.EAR);
			}else{
				item1 = item1Spr.getItem();
			}
			
			Item item2 = item2Spr.getItem();
			
			Text str1 = TextFactory.createText(""+item1.getAttack(), texture.getmFont4());
			Text dex1 = TextFactory.createText(""+item1.getDefense(), texture.getmFont4());
			Text int1 = TextFactory.createText(""+item1.getMagicPower(), texture.getmFont4());
			
			Text str2 = TextFactory.createText(""+item2.getAttack(), texture.getmFont4());
			Text dex2 = TextFactory.createText(""+item2.getDefense(), texture.getmFont4());
			Text int2 = TextFactory.createText(""+item2.getMagicPower(), texture.getmFont4());
			
			int beginPos = 30;
			int dis = 8;
			
			str1.setPosition(beginPos, str.getY());
			str2.setPosition(str1.getX()+str1.getWidth()+dis, str1.getY());
			
			dex1.setPosition(beginPos, dex.getY());
			dex2.setPosition(dex1.getX()+dex1.getWidth()+dis, dex1.getY());
			
			int1.setPosition(beginPos, inte.getY());
			int2.setPosition(int1.getX()+int1.getWidth()+dis, int1.getY());
			
			
			
			this.attachChild(str1);
			this.attachChild(str2);
			this.attachChild(dex1);
			this.attachChild(dex2);
			this.attachChild(int1);
			this.attachChild(int2);
			
			this.attachChild(str);
			this.attachChild(dex);
			this.attachChild(inte);
			
			
			//compare
			if(Integer.valueOf(str1.getText().toString()) < Integer.valueOf(str2.getText().toString())){
				str1.setColor(Color.RED);
				str2.setColor(Color.GREEN);
			}
			if(Integer.valueOf(dex1.getText().toString()) < Integer.valueOf(dex2.getText().toString())){
				dex1.setColor(Color.RED);
				dex2.setColor(Color.GREEN);
			}
			if(Integer.valueOf(int1.getText().toString()) < Integer.valueOf(int2.getText().toString())){
				int1.setColor(Color.RED);
				int2.setColor(Color.GREEN);
			}
			if(Integer.valueOf(str1.getText().toString()) > Integer.valueOf(str2.getText().toString())){
				str1.setColor(Color.GREEN);
				str2.setColor(Color.RED);
			}
			if(Integer.valueOf(dex1.getText().toString()) > Integer.valueOf(dex2.getText().toString())){
				dex1.setColor(Color.GREEN);
				dex2.setColor(Color.RED);
			}
			if(Integer.valueOf(int1.getText().toString()) > Integer.valueOf(int2.getText().toString())){
				int1.setColor(Color.GREEN);
				int2.setColor(Color.RED);
			}
			
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
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub
		
	}

	
	
}
