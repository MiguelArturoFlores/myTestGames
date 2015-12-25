package com.mgl.drop.game.entity;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyEntity;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class EntityComboText extends MyEntity {

	private Text textCombo;

	private float timeToDesapear = 0.5f;
	private float contTime = 0;
	private float alpha = 1;

	public EntityComboText(float x, float y, int combo, LevelController level) {
		try {

			textCombo = ObjectFactorySingleton.getInstance().createText(
					"Combo x" + combo,
					TextureSingleton.getInstance().getmFont1());
			level.getScene().attachChild(textCombo);
			textCombo.setPosition(x - textCombo.getWidth() / 2, y);
			//textCombo.setZIndex(ZIndexGame.TRUNK);

			textCombo.registerEntityModifier(new MoveYModifier(
					timeToDesapear + 0.2f, textCombo.getY(),
					textCombo.getY() - 20));

		} catch (Exception e) {

		}
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {

			try {

				contTime += dTime;

				textCombo.setAlpha(alpha);

				alpha = alpha - 0.02f;
				if (alpha <= 0) {
					alpha = 0f;
				}

				if (contTime < timeToDesapear)
					return;

				textCombo.detachSelf();
				level.removeEntity(this);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public float getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IEntity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITouchArea getTouchArea() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateChild(float dTime, LevelController level) {

	}

	@Override
	public void setMustUpdate(boolean mustUpdate) {
		// TODO Auto-generated method stub

	}

}
