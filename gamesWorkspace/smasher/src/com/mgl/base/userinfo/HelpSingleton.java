package com.mgl.base.userinfo;

import java.util.ArrayList;

import com.mgl.base.MySprite;
import com.mgl.drop.game.sprites.SpriteHelp;
import com.mgl.drop.texture.TextureSingleton;

public class HelpSingleton {

	private static HelpSingleton instance = null;

	private ArrayList<MySprite> spriteList;

	private HelpSingleton() {
		try {

			loadHelpList();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadHelpList() {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();
			
			spriteList = new ArrayList<MySprite>();
			
			SpriteHelp help = new SpriteHelp(0, 0, texture.getTextureByName("help1.png"), texture.getVertexBufferObjectManager());
			spriteList.add(help);
			
			help = new SpriteHelp(0, 0, texture.getTextureByName("help2.png"), texture.getVertexBufferObjectManager());
			spriteList.add(help);
			
			help = new SpriteHelp(0, 0, texture.getTextureByName("help3.png"), texture.getVertexBufferObjectManager());
			spriteList.add(help);
			
			help = new SpriteHelp(0, 0, texture.getTextureByName("help4.png"), texture.getVertexBufferObjectManager());
			spriteList.add(help);
			
			help = new SpriteHelp(0, 0, texture.getTextureByName("help5.png"), texture.getVertexBufferObjectManager());
			spriteList.add(help);
			
			help = new SpriteHelp(0, 0, texture.getTextureByName("help6.png"), texture.getVertexBufferObjectManager());
			spriteList.add(help);
			
			help = new SpriteHelp(0, 0, texture.getTextureByName("help7.png"), texture.getVertexBufferObjectManager());
			spriteList.add(help);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HelpSingleton getInstance() {
		try {
			
			if(instance == null){
				instance = new HelpSingleton();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}


	public ArrayList<MySprite> getSpriteList() {
		return spriteList;
	}

	public void setSpriteList(ArrayList<MySprite> spriteList) {
		this.spriteList = spriteList;
	}

	
	
}
