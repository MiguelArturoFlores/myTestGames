package com.mgl.drop.game.sprites;

import java.util.ArrayList;
import java.util.Stack;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class SpritePoopBar extends MySprite{

	
	private TextureSingleton texture = TextureSingleton.getInstance();
	private int poopNumber;
	private Stack<MySpriteGeneral> poopList;
	
	
	public SpritePoopBar(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, LevelController level, ArrayList<MyAnimateSprite> pooplist) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, vertexBufferObjectManager, level);

		this.poopNumber = pooplist.size();
		poopList = new Stack<MySpriteGeneral>();
		float width = 5;
		float height = 10;
		int i= 0;
		for(MyAnimateSprite poop : pooplist){
			
			poop.setSize(30, 30);
			poop.setPosition(20+poop.getWidth()*i, 10);
			this.attachChild(poop);
			poopList.add(poop);
			width =  width+ poop.getWidth();
			height = poop.getHeight();
			i++;
		}
		this.setWidth(width+20);
		this.setHeight(height+20);
	}

	public boolean canIPoop(){
		try {
			
			if(!poopList.isEmpty()){
				return true;
			}
			return false;
			
		} catch (Exception e) {
			return false;
		}
	}
	
	public MyAnimateSprite getPoop(){
		try {
			MyAnimateSprite poop = (MyAnimateSprite) poopList.pop();
			this.detachChild(poop);
			return poop;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public SpriteType getSpriteType() {

		return SpriteType.POOP_BAR;
	}

	@Override
	public void update(float dTime, LevelController level) {

		
	}

	public TextureSingleton getTexture() {
		return texture;
	}

	public void setTexture(TextureSingleton texture) {
		this.texture = texture;
	}

	public int getPoopNumber() {
		return poopNumber;
	}

	public void setPoopNumber(int poopNumber) {
		this.poopNumber = poopNumber;
	}

	public int getRemainPoop() {
		try {
			
			if(poopList.isEmpty()){
				return 0;
			}
			return poopList.size();
			
		
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	
	
	
}
