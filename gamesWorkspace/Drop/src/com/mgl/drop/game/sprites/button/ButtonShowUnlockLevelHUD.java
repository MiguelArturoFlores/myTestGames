package com.mgl.drop.game.sprites.button;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.facebook.FacebookManager;
import com.mgl.drop.MainDropActivity;
import com.mgl.crappypigeon.R;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.LevelManager;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.UnlockWithFacebookHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class ButtonShowUnlockLevelHUD extends MySprite {

	private Level level;

	
	
	public ButtonShowUnlockLevelHUD(int pX, int pY, ITextureRegion textureByName,
			VertexBufferObjectManager vertexBufferObjectManager, Level level2) {
		super(pX, pY, textureByName, vertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		this.level =level2;
		
	}

	@Override
	public SpriteType getSpriteType() {

		return null;
	}

	@Override
	public void update(float dTime, LevelController level) {

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {

			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:
				
				//verify internert connection
				if(!MainDropActivity.isConnectingToInternet()){
					
					InformativeHUD hud = new InformativeHUD(SceneManagerSingleton.getInstance().getActivity().getResources().getString(R.string.noInternetConnection));
					HUDManagerSingleton.getInstance().addHUD(hud);
					
					return false;
					
				}

				Log.d("CONNECTED", "CONNECTED");
				  
				
				UnlockWithFacebookHUD HUD = new UnlockWithFacebookHUD(level);
				HUDManagerSingleton.getInstance().addHUD(HUD);
				
				break;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	
}