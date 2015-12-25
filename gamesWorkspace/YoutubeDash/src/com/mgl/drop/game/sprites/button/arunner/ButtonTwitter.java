package com.mgl.drop.game.sprites.button.arunner;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class ButtonTwitter extends MySprite {

	public ButtonTwitter(float pX, float pY,
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
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		//

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

				SoundSingleton.getInstance().playButtonSound();

				Intent intent = new Intent(Intent.ACTION_VIEW);
				// Try Google play
				// Market (Google play) app seems not installed, let's try to
				// open a webbrowser
				intent.setData(Uri
						.parse("https://twitter.com/idrGames"));
				if (!MyStartActivity(intent)) {
					// Well if this also fails, we have run out of options,
					// inform the user.
					return true;

				}

				break;
			default:

				break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private boolean MyStartActivity(Intent aIntent) {
		try {
			SceneManagerSingleton.getInstance().getActivity()
					.startActivity(aIntent);
			return true;
		} catch (ActivityNotFoundException e) {
			return false;
		}
	}

}
