package com.mgl.drop.game.hud;

import org.andengine.engine.camera.hud.HUD;

import com.mgl.drop.texture.TextureSingleton;

public abstract class MyHud extends HUD{
	
	protected TextureSingleton texture = TextureSingleton.getInstance();
	
	public abstract void onCloseAction();

}
