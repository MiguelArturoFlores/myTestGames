package com.mgl.drop.game.sprites.arunner;

import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.PhysicSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpriteObstacleFall extends MySprite {

	private float distanceToActivate = 200;

	public SpriteObstacleFall(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);

	}

	@Override
	public SpriteType getSpriteType() {
		return SpriteType.OBSTACLE;
	}

	@Override
	public void update(float dTime, LevelController level) {

		try {
			if (Point.distanceBetween(new Point(this.getX(), this.getY()),
					new Point(level.getPlayer().getX(), level.getPlayer()
							.getY())) > distanceToActivate) {
				return;
			}

			FixtureDef def = PhysicsFactory.createFixtureDef(8f, 0.05f, 0.5f);
			PhysicSingleton.getInstance().loadSpriteInWorld(this, def,
					BodyType.DynamicBody,false);

			level.removeEntity(this);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
