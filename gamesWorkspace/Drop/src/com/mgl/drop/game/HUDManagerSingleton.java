package com.mgl.drop.game;

import java.util.Stack;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;

import com.mgl.drop.game.scene.SceneManagerSingleton;
import android.util.Log;

public class HUDManagerSingleton {

	private static HUDManagerSingleton instance = null;

	private Stack<HUD> hudStack;

	private HUDManagerSingleton() {
		try {

			Log.d("INSTANCIA DEL HUD MANAGER", "INSTANCIA DEL HUD MANAGER");
			hudStack = new Stack<HUD>();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addHUD(HUD hud) {
		try {
			if(hudStack==null){
				hudStack = new Stack<HUD>();
				System.out.println("El hud estaba null");
			}
			hudStack.push(hud);
			Log.d("HUD", "Tengo "+hudStack.size());
			SceneManagerSingleton.getInstance().getCamera().setHUD(hud);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public static HUDManagerSingleton getInstance() {
		if (instance == null) {
			instance = new HUDManagerSingleton();
		}
		return instance;
	}

	public static void setInstance(HUDManagerSingleton instance) {
		HUDManagerSingleton.instance = instance;
	}

	public void removeAndReplaceHud( ) {
		try {
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			if(!hudStack.isEmpty()){
				hudStack.pop();
			}
			Log.d("HUD", "Tengo Quitaondo"+hudStack.size());
			if(hudStack.isEmpty()){
				camera.setHUD(new HUD());
				return;
			}
			HUD hud = hudStack.peek();
			camera.setHUD(hud);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void removeAllHUD() {
		try {
			hudStack = new Stack<HUD>();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
