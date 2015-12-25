package com.mgl.drop.game.sprites.button;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class ButtonInHouse extends MySprite{

	public ButtonInHouse(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);

		try {
			
		} catch (Exception e) {

			e.printStackTrace();
			
		}
	}

	@Override
	public SpriteType getSpriteType() {
		
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			SoundSingleton.getInstance().playButtonSound();
			UserInfoSingleton.getInstance().setUserRate();
		
			
			 Intent intent = new Intent(Intent.ACTION_VIEW);
			    //Try Google play
			    intent.setData(Uri.parse("market://details?id=com.mgl.smasher"));
			    if (!MyStartActivity(intent)) {
			        //Market (Google play) app seems not installed, let's try to open a webbrowser
			        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.mgl.smasher"));
			        if (!MyStartActivity(intent)) {
			            //Well if this also fails, we have run out of options, inform the user.
			            return true;
			        	
			        }
			    }
			    
			    HUDManagerSingleton.getInstance().removeAndReplaceHud();
			    
			    try {
					Thread.sleep((long) 1500d);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   
			    this.detachSelf();
			    
			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

	private boolean MyStartActivity(Intent aIntent) {
	    try
	    {
	        SceneManagerSingleton.getInstance().getActivity().startActivity(aIntent);
	        return true;
	    }
	    catch (ActivityNotFoundException e)
	    {
	        return false;
	    }
	}

	
	
	
	
}
