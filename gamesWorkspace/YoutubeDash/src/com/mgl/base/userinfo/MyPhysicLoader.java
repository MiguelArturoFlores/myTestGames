package com.mgl.base.userinfo;

import org.andlabs.andengine.extension.physicsloader.PhysicsEditorLoader;

public class MyPhysicLoader extends PhysicsEditorLoader {

	@Override
	protected void onAfterLoadLevel() {
		try {

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
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
