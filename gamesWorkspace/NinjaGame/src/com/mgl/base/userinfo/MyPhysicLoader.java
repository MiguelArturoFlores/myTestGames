package com.mgl.base.userinfo;

import org.andlabs.andengine.extension.physicsloader.PhysicsEditorLoader;

import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.hud.InformativeHUD;

public class MyPhysicLoader extends PhysicsEditorLoader {

	protected void onAfterLoadLevel() {
		try {

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void onBeforeLoadLevel() {
		try {
			/*System.out.println("LOADING ON BEFORE");
			InformativeHUD loadingHUD = new InformativeHUD("Loading ... ",
					false);
			HUDManagerSingleton.getInstance().addHUD(loadingHUD, true);
		*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
