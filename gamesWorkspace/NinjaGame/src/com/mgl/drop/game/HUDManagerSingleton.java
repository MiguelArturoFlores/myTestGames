package com.mgl.drop.game;

import java.util.Stack;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;

import android.util.Log;

import com.mgl.drop.game.hud.MyHud;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class HUDManagerSingleton {

	private static HUDManagerSingleton instance = null;

	private Stack<RemovableHud> hudStack;

	private HUDManagerSingleton() {
		try {

			Log.d("INSTANCIA DEL HUD MANAGER", "INSTANCIA DEL HUD MANAGER");
			hudStack = new Stack<RemovableHud>();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addHUD(HUD hud, boolean removable) {
		try {

			RemovableHud remo = new RemovableHud(hud, removable);
			
			if (hudStack == null) {
				hudStack = new Stack<RemovableHud>();
				System.out.println("El hud estaba null");
			}
			hudStack.push(remo);
			Log.d("HUD manager singleton", "Tengo " + hudStack.size());
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

	public void removeAndReplaceHud() {
		try {
			if(hudStack == null){
				hudStack = new Stack<HUDManagerSingleton.RemovableHud>();
				return;
			}
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			RemovableHud close = hudStack.pop();
			if(close.getHud() instanceof MyHud){
				((MyHud) close.getHud()).onCloseAction();
			}
			
			Log.d("HUD", "Tengo Quitaondo" + hudStack.size());
			if (hudStack.isEmpty()) {
				camera.setHUD(new HUD());
				return;
			}
			RemovableHud hud = hudStack.peek();
			camera.setHUD(hud.getHud());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public HUD getTop() {
		try {

			return hudStack.peek().getHud();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void removeAllHUD() {
		try {

			hudStack = new Stack<RemovableHud>();
			HUDManagerSingleton.getInstance().addHUD(new HUD(), false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void removeHud() {
		try {
			hudStack.pop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private class RemovableHud {

		private boolean removable = false;
		private HUD hud;

		public RemovableHud(HUD hud, boolean b){
			this.hud = hud;
			this.removable = b;
		}
		
		public boolean isRemovable() {
			return removable;
		}

		public void setRemovable(boolean removable) {
			this.removable = removable;
		}

		public HUD getHud() {
			return hud;
		}

		public void setHud(HUD hud) {
			this.hud = hud;
		}
		
		

	}

	public boolean canGoBack() {

		try {
			
			if(hudStack.isEmpty()){
				return true;
			}
			
			if(hudStack.peek().isRemovable()){
				removeAndReplaceHud();
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
