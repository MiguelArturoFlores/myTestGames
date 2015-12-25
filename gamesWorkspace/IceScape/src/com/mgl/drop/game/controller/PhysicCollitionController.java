package com.mgl.drop.game.controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.sprites.aicerunner.SpriteCoin;
import com.mgl.drop.game.sprites.aicerunner.SpriteObjectFalling;

public class PhysicCollitionController implements ContactListener {

	private LevelController controller;

	public PhysicCollitionController(LevelController controller) {
		try {

			this.controller = controller;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endContact(Contact pContact) {
		try {

		} catch (Exception e) {
			//e.printStackTrace();
		}

	}

	@Override
	public void beginContact(Contact pContact) {
		try {

			final Body bodyA = pContact.getFixtureA().getBody();
			final Body bodyB = pContact.getFixtureB().getBody();

			MySpriteGeneral sprA = (MySpriteGeneral) bodyA.getUserData();
			MySpriteGeneral sprB = (MySpriteGeneral) bodyB.getUserData();

			// player hedgehog collition
			if ((sprA.getSpriteType().equals(SpriteType.FLOOR) && sprB
					.getSpriteType().equals(SpriteType.DANGER))
					|| (sprA.getSpriteType().equals(SpriteType.DANGER) && sprB
							.getSpriteType().equals(SpriteType.FLOOR))) {
				
				if(sprA instanceof SpriteObjectFalling){
					
					((SpriteObjectFalling) sprA).collideWithFloor();
					
				}else if(sprB instanceof SpriteObjectFalling){
					((SpriteObjectFalling) sprB).collideWithFloor();	
				}
				
			}
			
			if ((sprA.getSpriteType().equals(SpriteType.COIN) && sprB
					.getSpriteType().equals(SpriteType.PLAYER))
					|| (sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
							.getSpriteType().equals(SpriteType.COIN))) {
				
				if(sprA instanceof SpriteCoin){
					((SpriteCoin) sprA).collectCoin();
					
				}else if(sprB instanceof SpriteCoin){
					((SpriteCoin) sprB).collectCoin();
				}
				
			}
			
			if ((sprA.getSpriteType().equals(SpriteType.FLOOR) && sprB
					.getSpriteType().equals(SpriteType.PLAYER))
					|| (sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
							.getSpriteType().equals(SpriteType.FLOOR))) {
				
				SoundSingleton.getInstance().playHit();
			}
			
			
			if ((sprA.getSpriteType().equals(SpriteType.FLOOR) && sprB
					.getSpriteType().equals(SpriteType.COIN))
					|| (sprA.getSpriteType().equals(SpriteType.COIN) && sprB
							.getSpriteType().equals(SpriteType.FLOOR))) {
				
				if(sprA instanceof SpriteCoin){
					
					((SpriteCoin) sprA).destroyCoin();
					
				}else if(sprB instanceof SpriteCoin){
					((SpriteCoin) sprB).destroyCoin();
				}
				
			}

			// collide with platform
			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.DANGER))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.DANGER))) {
				
				controller.looseLive();
				
			}

			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.FLOOR))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.FLOOR))) {
					
					
					
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

}
