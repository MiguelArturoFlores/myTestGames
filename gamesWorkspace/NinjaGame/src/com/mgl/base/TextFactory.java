package com.mgl.base;

import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;

import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.texture.TextureSingleton;

public class TextFactory {

	
	private static TextureSingleton texture = TextureSingleton.getInstance();
	
	public static Text getMenuText(Font font){
		return createText("Menu", font);
	}
	
	public static  Text getEquipText(Font font){
		return createText("Equip", font);
	}
	
	public static  Text getItemText(Font font){
		return createText("Items", font);
	}
	
	public static  Text getCardsText(Font font){
		return createText("Cards", font);
	}
	
	public static  Text getExitText(Font font){
		return createText("Exit", font);
	}
	
	public static  Text getSockText(Font font){
		return createText("Socks", font);
	}
	
	public static  Text getEaringText(Font font){
		return createText("Earings", font);
	}
	public static  Text getHandText(Font font){
		return createText("Hands", font);
	}
	
	public static String createText(int id) {
		try {
			
			return SceneManagerSingleton.getInstance().getActivity().getResources().getString(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static Text createText(String text, Font font) {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			Text mText = new Text(0, 0, font, text,
					texture.getVertexBufferObjectManager());
			return mText;
		} catch (Exception e) {
			return null;
		}
	}

	public static Text getText(String text, Font font) {
		return createText(text, font);
	}
	
}
