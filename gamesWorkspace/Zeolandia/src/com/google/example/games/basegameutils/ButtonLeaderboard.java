package com.google.example.games.basegameutils;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class ButtonLeaderboard extends MySprite {

	boolean isLogin;
	private String leaderboarName;

	public ButtonLeaderboard(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		this.isLogin = isLogin;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {

			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				if (leaderboarName == null || leaderboarName.isEmpty()) {
					GooglePlayGameSingleton.getInstance().showLeaderboard();
				} else {
					GooglePlayGameSingleton.getInstance().showLeaderboard(
							leaderboarName);
				}

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public String getLeaderboarName() {
		return leaderboarName;
	}

	public void setLeaderboarName(String leaderboarName) {
		this.leaderboarName = leaderboarName;
	}

}