package com.mgl.drop.game.sprites.arunner;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.StatusType;
import com.mgl.base.userinfo.PhysicSingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpriteEnemyRombeBurble extends SpriteEnemy{

	private float distanceToActivate = 400;
	private float linearVelocity = 3;
	private boolean diying = false;
	private boolean relaseSon = false;

	public SpriteEnemyRombeBurble(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		diying = false;
		relaseSon = false;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.ENEMY;
	}

	@Override
	public void initAnimationParams() {
		try {

			changeAnimateState(State.WALKIN_RIGHT, true);
			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83, 83, 83, 83, 83, 83 };

			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0, 8,
					fps));
			stateAnimate.put(State.DIYING, new MyAnimateProperty(8, 4,
					new long[] { 83, 83, 83, 83 }));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			SpritePlayer player = level.getPlayer();
			if (hasParent() && this.getBody() != null
					&& !this.getBody().getType().equals(BodyType.StaticBody)) {
				this.getBody().setType(BodyType.StaticBody);
				
			}
			if (diying) {
				if (!isAnimationRunning()) {
					this.detachSelf();
					level.removeEntity(this);
					level.getScene().unregisterTouchArea(this);
				}
				if(!relaseSon){
					killEnemyIntern(SpriteType.FINGER);
					this.getBody().setLinearVelocity(new Vector2(0,0));
					relaseSon = true;
				}
				return;
			}
			
			if(isFreeze){
				this.getBody().setType(BodyType.StaticBody);
				this.getBody().setLinearVelocity(0,0);
				
				anime(false);
				return;
			}else{
				if (!isAnimationRunning()) {
					anime(true);
				}
			}
			
			if (Point.distanceBetween(new Point(this.getX(), this.getY()),
					new Point(player.getX(), player.getY())) > distanceToActivate) {
				return;
			}

			this.getBody().setType(BodyType.DynamicBody);
			float angle = Point.angleBetween(
					new Point(this.getX(), this.getY()),
					new Point(player.getX(), player.getY()));

			float dx = linearVelocity * (float) Math.cos(angle);
			float dy = linearVelocity * (float) Math.sin(angle);

			this.getBody().setLinearVelocity(dx, dy);

			float dxAux = dx;
			float dyAux = dy;

			if (dxAux < 0) {
				dxAux = dxAux * -1;
			}
			if (dyAux < 0) {
				dxAux = dyAux * -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {

			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

				if (!status.equals(StatusType.NORMAL)) {
					return false;
				}

				// killEnemy(SpriteType.FINGER);

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				break;
			default:

				break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private void killEnemyIntern(SpriteType finger) {
		try {

			if(killFreeze || isFreeze){
				return;
			}

			SpriteEnemyRombe sprSon = ( SpriteEnemyRombe) MyFactory.createObstacle(SpriteTypeConstant.ENEMY_ROMBE, level);
			PhysicSingleton.getInstance().loadSpriteInWorldXML(sprSon);
			LevelController.setBodyPixelPositionOld(
					this.getX()+this.getWidth()/2 -sprSon.getWidth()/2, this.getY()+this.getHeight()/2 - sprSon.getHeight()/2,0,
					sprSon.getWidth(), sprSon.getHeight(), sprSon.getBody());
			sprSon.setZIndex(this.getZIndex());
			level.getScene().attachChild(sprSon);
			level.getScene().registerTouchArea(sprSon);
			level.addSpriteToUpdate(sprSon);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void killEnemy(SpriteType finger) {
		try {

			SoundSingleton.getInstance().playEnemyDie();
			
			changeAnimateState(State.DIYING, false);
			diying = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
