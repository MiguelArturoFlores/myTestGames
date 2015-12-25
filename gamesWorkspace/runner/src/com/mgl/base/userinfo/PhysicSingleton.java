package com.mgl.base.userinfo;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.level.IEntityLoader;
import org.andlabs.andengine.extension.physicsloader.PhysicsEditorLoader;
import org.xml.sax.Attributes;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.physic.PausablePhysicWorld;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.model.MyPurchase;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class PhysicSingleton {

	private static PhysicSingleton instance = null;
	private PausablePhysicWorld physicsWorld;
	private PhysicsEditorLoader loader;
	private ArrayList<Body> bodyToRemove;

	private PhysicSingleton() {

		try {

			physicsWorld = new PausablePhysicWorld(new Vector2(0,
					SensorManager.GRAVITY_EARTH), true);
			physicsWorld.setPaused(true);
			loader = new PhysicsEditorLoader();
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static PhysicSingleton getInstance() {
		try {
			if (instance == null) {
				instance = new PhysicSingleton();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	public PhysicsWorld getPhysicsWorld() {
		return physicsWorld;
	}

	public void pause(boolean pause) {
		try {

			physicsWorld.setPaused(pause);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addPlayerPhysic(MyAnimateSprite sprite) {
		try {

			final FixtureDef PLAYER_FIX = PhysicsFactory.createFixtureDef(4f,
					0f, 1f);
			Body body = PhysicsFactory.createBoxBody(physicsWorld, sprite,
					BodyType.DynamicBody, PLAYER_FIX);

			physicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite,
					body, true, false));
			// body.setAngularDamping(1000);
			// body.setFixedRotation(true);
			// sprite.setBody(body);
			body.setUserData(sprite);
			sprite.setBody(body);
			

			/*
			 * RevoluteJointDef joint = new RevoluteJointDef();
			 * joint.initialize(body, body2, body.getWorldCenter());
			 * joint.enableMotor = false; joint.motorSpeed = 0;
			 * joint.maxMotorTorque = 0;
			 */

			// DistanceJointDef joint = new DistanceJointDef();
			// joint.initialize(body, body2, body.getWorldCenter(),
			// body2.getWorldCenter());
			// physicsWorld.createJoint(joint);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addDynamicBody(MySpriteGeneral sprite, FixtureDef fixtureDef) {
		try {

			if (sprite instanceof MySprite) {
				MySprite spr = (MySprite) sprite;
				// Body body = PhysicsFactory.createCircleBody(physicsWorld,
				// spr,
				// BodyType.DynamicBody, fixtureDef);

				// Vector2 [] vecArray = {new Vector2(0f,0f),new
				// Vector2(0.5f,0.5f), new Vector2(0.5f,1f)};
				Vector2[] vecArray = { new Vector2(-1f, 0f),
						new Vector2(0f, -1f), new Vector2(1f, 0f),
						new Vector2(0f, 1f) };
				Body body = PhysicsFactory.createPolygonBody(physicsWorld, spr,
						vecArray, BodyType.DynamicBody, fixtureDef);

				physicsWorld.registerPhysicsConnector(new PhysicsConnector(spr,
						body, true, true));
				sprite.setBody(body);
			} else if (sprite instanceof MyAnimateSprite) {
				MyAnimateSprite spr = (MyAnimateSprite) sprite;
				Body body = PhysicsFactory.createCircleBody(physicsWorld, spr,
						BodyType.DynamicBody, fixtureDef);

				physicsWorld.registerPhysicsConnector(new PhysicsConnector(spr,
						body, true, true));
				sprite.setBody(body);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void reset() {
		try {
			physicsWorld = new PausablePhysicWorld(new Vector2(0,
					SensorManager.GRAVITY_EARTH), true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadSpriteInWorldXML(MySpriteGeneral sprite) {
		try {
			if(sprite.getCollitionType() == CollitionType.COLLITION_NONE){
				return;
			}
			if (sprite.getXmlName() == null || sprite.getXmlName().isEmpty()) {
				
				FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(2f, 0f,
						0.5f);
				System.out.println("loading here");
				loadSpriteInWorld(sprite, fixtureDef, BodyType.DynamicBody);

				return;
			}
			
//			if(true){
//				loadFromMemoryCollitionType(sprite);
//				return;
//			}
			
			if (sprite instanceof MySprite) {

				MySprite spr = (MySprite) sprite;
				
				loader.load(SceneManagerSingleton.getInstance().getActivity(),
						PhysicSingleton.getInstance().getPhysicsWorld(), "xml/"
								+ spr.getXmlName(), spr, true, true);

				spr.setBody(loader.getBodies().get(0));
				loader.getBodies().get(0).setUserData(spr);

			} else if (sprite instanceof MyAnimateSprite) {
				MyAnimateSprite spr = (MyAnimateSprite) sprite;
				loader.load(SceneManagerSingleton.getInstance().getActivity(),
						PhysicSingleton.getInstance().getPhysicsWorld(), "xml/"
								+ spr.getXmlName(), spr, true, false);
				loader.getBodies().get(0).setFixedRotation(true);
				spr.setBody(loader.getBodies().get(0));
				loader.getBodies().get(0).setUserData(spr);

			}

			loader.reset();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			loader.reset();
		}

	}

	private void loadFromMemoryCollitionType(MySpriteGeneral sprite) {
		try {
		
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void loadSpriteInWorld(MySpriteGeneral sprite,
			FixtureDef fixtureDef, BodyType bodyType) {
		try {

			if (sprite.getXmlName() == null || sprite.getXmlName().isEmpty()) {

				if (sprite instanceof MyAnimateSprite) {

					MyAnimateSprite spr = (MyAnimateSprite) sprite;
					Body body = null;

					if (sprite.getCollitionType() == CollitionType.COLLITION_CIRCULAR) {
						body = PhysicsFactory.createCircleBody(physicsWorld,
								spr, bodyType, fixtureDef);
					} else if (sprite.getCollitionType() == CollitionType.COLLITION_RECTANGLE) {
						body = PhysicsFactory.createBoxBody(physicsWorld, spr,
								bodyType, fixtureDef);
					}

					physicsWorld.registerPhysicsConnector(new PhysicsConnector(
							spr, body, true, false));
					sprite.setBody(body);
					body.setUserData(spr);

				} else if (sprite instanceof MySprite) {

					MySprite spr = (MySprite) sprite;
					Body body = null;

					if (sprite.getCollitionType() == CollitionType.COLLITION_CIRCULAR) {
						body = PhysicsFactory.createCircleBody(physicsWorld,
								spr, bodyType, fixtureDef);
					} else if (sprite.getCollitionType() == CollitionType.COLLITION_RECTANGLE) {
						body = PhysicsFactory.createBoxBody(physicsWorld, spr,
								bodyType, fixtureDef);
						
					}

					physicsWorld.registerPhysicsConnector(new PhysicsConnector(
							spr, body, true, false));
					sprite.setBody(body);
					body.setUserData(spr);

				}

				loader.reset();
				return;
			}

			if (sprite instanceof MySprite) {

				MySprite spr = (MySprite) sprite;
				loader.load(SceneManagerSingleton.getInstance().getActivity(),
						PhysicSingleton.getInstance().getPhysicsWorld(), "xml/"
								+ spr.getXmlName(), spr, true, false);

			} else if (sprite instanceof MyAnimateSprite) {
				MyAnimateSprite spr = (MyAnimateSprite) sprite;
				loader.load(SceneManagerSingleton.getInstance().getActivity(),
						PhysicSingleton.getInstance().getPhysicsWorld(), "xml/"
								+ spr.getXmlName(), spr, true, false);
			}

			loader.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeBodyRightNow(Body b) {
		try {
			
			physicsWorld.destroyBody(b);
			b=null;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
