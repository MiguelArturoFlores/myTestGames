package com.mgl.drop.factory;

import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;

import com.mgl.drop.texture.TextureSingleton;

public class ObjectFactorySingleton {

	private static ObjectFactorySingleton instance = null;
	

	private ObjectFactorySingleton() {
		try {
			System.out.println("CREO EL OBJECT FACTORY SINGLEGTON");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ObjectFactorySingleton getInstance() {
		if (instance == null) {
			instance = new ObjectFactorySingleton();
		}
		return instance;
	}

	public Text createText(String text, Font font) {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			Text mText = new Text(0, 0, font, text,
					texture.getVertexBufferObjectManager());
			return mText;
		} catch (Exception e) {
			return null;
		}
	}
	
	

}
