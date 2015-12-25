package com.mgl.drop.game.entity;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import com.mgl.base.MyEntity;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class EntityFinalWave extends MyEntity {

	private Text textCombo;

	private float timeToDesapear = 1.8f;
	private float contTime = 0;
	private float alpha = 1;

	public EntityFinalWave(float x, float y, LevelController level, boolean isNormal, String textShow) {
		try {

			if(isNormal){
				
			}
			textCombo = ObjectFactorySingleton.getInstance().createText(
					textShow ,
					TextureSingleton.getInstance().getmFont2());
			level.getScene().attachChild(textCombo);
			textCombo.setPosition(x - textCombo.getWidth() / 2, y);
			//textCombo.setZIndex(ZIndexGame.TRUNK);
			textCombo.setColor(Color.RED);

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

