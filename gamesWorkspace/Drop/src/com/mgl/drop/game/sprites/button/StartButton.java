package com.mgl.drop.game.sprites.button;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteInvisiblePointToFollow;

public class StartButton extends MySprite {

	private Scene scene;

	private boolean resize = true;

	private float maxTime = 0.4f;
	private float contTime = 0;
	
	private SpriteInvisiblePointToFollow sprite;

	public StartButton(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			LevelController level, Scene scene, SpriteInvisiblePointToFollow sprite) {
		super(pX, pY, pWidth, pHeight, pTextureRegion,
				vertexBufferObjectManager, level);
		this.scene = scene;
		//this.setAlpha(0.5f);
		this.sprite = sprite;

	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			contTime = contTime + dTime;

			if (contTime > maxTime) {
				contTime = 0;
				if (resize) {
					this.setSize(this.getWidth() + 5, this.getHeight() + 5);
				} else {
					this.setSize(this.getWidth() - 5, this.getHeight() - 5);

				}
				resize = !resize;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			SoundSingleton.getInstance().playSound("buttonPress.mp3");
			
			level.setUpdateAnimated(true);
			sprite.setMustUpdate(false);
			sprite.setVisible(false);
			level.removeEntity(sprite);
			

			for (MySpriteGeneral sprite : level.getSpriteList()) {

				if (sprite.getSpriteType().equals(SpriteType.PLAYER)) {

					Log.d("ABC", " CAMBIO EL LISTENER ");

					scene.setOnSceneTouchListener(null);

					level.getPlayer().setVisible(true);
					level.getPlayer().beginFlying();
					level.getBeginPoint().setVisible(true);
					level.getPigeonBegin().setVisible(false);

					Camera camera = SceneManagerSingleton.getInstance()
							.getCamera();
					camera.setChaseEntity(null);

					sprite.setStatus(StatusType.PLAYING);
					camera.getHUD().detachChild(this);

					SceneManagerSingleton.getInstance().resetZoom();
					level.detachZoomButton();

					// break;
				}
			}

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

}
