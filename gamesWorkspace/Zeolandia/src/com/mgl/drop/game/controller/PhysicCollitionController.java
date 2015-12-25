package com.mgl.drop.game.controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.sprites.arunner.SpriteGel;
import com.mgl.drop.game.sprites.arunner.SpritePlayer;
import com.mgl.drop.game.sprites.arunner.SpritePlayerShot;

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
			final Body bodyA = pContact.getFixtureA().getBody();
			final Body bodyB = pContact.getFixtureB().getBody();

			MySpriteGeneral sprA = (MySpriteGeneral) bodyA.getUserData();
			MySpriteGeneral sprB = (MySpriteGeneral) bodyB.getUserData();


			// collide with platform
			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.FLOOR))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.FLOOR))) {
			//	controller.getPlayer().contactWithFloor(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
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
			// Log.d("TOCADO LISTENER", "TOCADO LISTENER");
			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.HEDGEHOG))
					|| (sprA.getSpriteType().equals(SpriteType.HEDGEHOG) && sprB
							.getSpriteType().equals(SpriteType.PLAYER))) {

				controller.looseLevelReplay();
			}

			// collide with platform
			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.FLOOR))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.FLOOR))) {
			//	controller.getPlayer().canJump(true);
			}

			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.FLOOR))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.FLOOR))) {
					SpritePlayer player;
					MySprite floor;
					if(sprA.getSpriteType().equals(SpriteType.PLAYER)){
						player = (SpritePlayer) sprA;
						floor = (MySprite) sprB;
					}
					else{
						player = (SpritePlayer) sprB;
						floor = (MySprite) sprA;
					}
					
					/*if(floor.getY() < player.getY() + player.getHeight() -5){
						controller.looseLevelReplay();
					}*/
					
					player.validateCollition(floor);
					
					
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
