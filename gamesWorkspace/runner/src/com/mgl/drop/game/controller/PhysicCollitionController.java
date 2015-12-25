package com.mgl.drop.game.controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.GameConstants;
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
					.getSpriteType().equals(SpriteType.PLATFORM))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.PLATFORM))) {
				controller.getPlayer().slowForContact(false);
			}

			// collide with floor
			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.FLOOR))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.FLOOR))) {
				controller.getPlayer().slowForContact(false);
			}

			// collide with tunnel
			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.TUNNEL))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.TUNNEL))) {
				controller.getPlayer().slowForContact(false);
			}

			// collide with lake
			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.LAKE))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.LAKE))) {
				controller.getPlayer().slowForContact(false);
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
				
				if( UserInfoSingleton.getInstance().getPlayerSelected() == GameConstants.PLAYER_C){
					SoundSingleton.getInstance().playMetallicCollition();
					return;
				}
				
				controller.looseLevelReplay();
			}

			// collide with platform
			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.PLATFORM))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.PLATFORM))) {
				controller.getPlayer().slowForContact(true);
				
			}

			// collide with floor
			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.FLOOR))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.FLOOR))) {
				controller.getPlayer().slowForContact(true);
			}

			// collide with tunnel
			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.TUNNEL))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.TUNNEL))) {
				controller.getPlayer().slowForContact(true);
			}

			// collide with lake
			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.LAKE))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.LAKE))) {
				controller.getPlayer().slowForContact(true);
			}

			// finish
			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.FINISH_POINT))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.FINISH_POINT))) {
				controller.winLevelShowScore();
			}
			// gel
			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.GEL))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.GEL))) {
				if (sprA instanceof SpriteGel) {
					SpriteGel shot = (SpriteGel) sprA;
					controller.getPlayer().setQuietInGel(shot);
					shot.removeBody();
				} else if (sprB instanceof SpriteGel) {
					SpriteGel shot = (SpriteGel) sprB;
					shot.removeBody();
					controller.getPlayer().setQuietInGel(shot);
				}

			}
			// shot touch enemy
			if ((sprA.getSpriteType().equals(SpriteType.SHOT) && sprB
					.getSpriteType().equals(SpriteType.ENEMY))
					|| (sprB.getSpriteType().equals(SpriteType.SHOT) && sprA
							.getSpriteType().equals(SpriteType.ENEMY))) {
				System.out.println("touching enemies");
				if (sprA instanceof SpritePlayerShot) {
					SpritePlayerShot shot = (SpritePlayerShot) sprA;
						shot.setTarget(sprB);
						shot.setMustKillEnemy(true);

				} else if (sprB instanceof SpritePlayerShot) {
					SpritePlayerShot shot = (SpritePlayerShot) sprB;
						shot.setTarget(sprA);
						shot.setMustKillEnemy(true);
				}
				return;
			}

			// enemyKillPlayer
			if ((sprA.getSpriteType().equals(SpriteType.PLAYER) && sprB
					.getSpriteType().equals(SpriteType.ENEMY))
					|| (sprB.getSpriteType().equals(SpriteType.PLAYER) && sprA
							.getSpriteType().equals(SpriteType.ENEMY))) {

				if (sprA instanceof SpritePlayer) {
					SpritePlayer player = (SpritePlayer) sprA;
					player.setCollitionWithEnemy(sprB.getBody().getUserData());

				} else if (sprB instanceof SpritePlayer) {
					SpritePlayer player = (SpritePlayer) sprB;
					player.setCollitionWithEnemy(sprA.getBody().getUserData());

				}
				
			}

			
			//SHOT COLLIDE
			if (sprA.getSpriteType().equals(SpriteType.SHOT)){
				SpritePlayerShot shot = (SpritePlayerShot) sprA;
				if(!sprB.getSpriteType().equals(SpriteType.PLAYER) && !sprB.getSpriteType().equals(SpriteType.SHOT))
					shot.destroyShot();
			}
			if (sprB.getSpriteType().equals(SpriteType.SHOT)){
				SpritePlayerShot shot = (SpritePlayerShot) sprB;
				if(!sprA.getSpriteType().equals(SpriteType.PLAYER) && !sprA.getSpriteType().equals(SpriteType.SHOT))
					shot.destroyShot();
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
