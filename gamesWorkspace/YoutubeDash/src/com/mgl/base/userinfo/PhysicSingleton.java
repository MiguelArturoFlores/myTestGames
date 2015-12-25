package com.mgl.base.userinfo;

import java.util.ArrayList;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andlabs.andengine.extension.physicsloader.PhysicsEditorLoader;
import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.physic.PausablePhysicWorld;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.arunner.SpritePlayer;

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
			if (sprite.getCollitionType() == CollitionType.COLLITION_NONE) {
				return;
			}
			if (sprite.getXmlName() == null || sprite.getXmlName().isEmpty()) {

				FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(2f, 0f,
						0f);
				System.out.println("loading here");
				loadSpriteInWorld(sprite, fixtureDef, BodyType.StaticBody,
						true);

				return;
			}

			// if(true){
			// loadFromMemoryCollitionType(sprite);
			// return;
			// }

			if (sprite instanceof MySprite) {

				MySprite spr = (MySprite) sprite;

				loader.load(SceneManagerSingleton.getInstance().getActivity(),
						PhysicSingleton.getInstance().getPhysicsWorld(), "xml/"
								+ spr.getXmlName(), spr, true, false);

				loader.getBodies().get(0).setFixedRotation(true);
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
		} finally {
			loader.reset();
		}

	}

	public  void loadStaticRectangle(MySprite sprite) {
		try {
			
			FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(2f, 0f,
					0f);
			loadSpriteInWorld(sprite, fixtureDef, BodyType.StaticBody,
					true);
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadSpriteInWorld(MySpriteGeneral sprite,
			FixtureDef fixtureDef, BodyType bodyType, boolean updateRotation) {
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
							spr, body, true, updateRotation));
					sprite.setBody(body);
					body.setUserData(sprite);

				} else if (sprite instanceof MySprite) {

					MySprite spr = (MySprite) sprite;
					Body body = null;

					if (sprite.getCollitionType() == CollitionType.COLLITION_CIRCULAR) {
						body = PhysicsFactory.createCircleBody(physicsWorld,
								spr, bodyType, fixtureDef);
					} else if (sprite.getCollitionType() == CollitionType.COLLITION_RECTANGLE) {
						body = PhysicsFactory.createBoxBody(physicsWorld, spr,
								bodyType, fixtureDef);
					}else if (sprite.getCollitionType() == CollitionType.COLLITION_TRIANGLE) {
						
						float x = spr.getWidth()/(2*32);
						float y = spr.getHeight()/(2*32);
						
						Vector2[] vetrices = {new Vector2(x*-1, y), new Vector2(0,0), new Vector2(x,y)};
						body = PhysicsFactory.createPolygonBody(physicsWorld, spr,vetrices ,
								bodyType, fixtureDef);
					
					}else if (sprite.getCollitionType() == CollitionType.COLLITION_RECTANGLE_GEOMETRY) {
						
						/*float x = spr.getWidth()/(2*32);
						float y = spr.getHeight()/(2*32);
						float less = 0;
						
						Vector2[] vetrices = {new Vector2(x*-1, y -less/32f), new Vector2(x*-1,y*-1), new Vector2(x,y*-1), new Vector2(x,y -less/32f)};
						body = PhysicsFactory.createPolygonBody(physicsWorld, spr,vetrices ,
								bodyType, fixtureDef);
						 */
						body = PhysicsFactory.createBoxBody(physicsWorld, spr,
								bodyType, fixtureDef);
					}else if (sprite.getCollitionType() == CollitionType.COLLITION_TRIANGLEPLUS) {
						
						
						float x = spr.getWidth()/(2*32);
						float y = spr.getHeight()/(2*32);
						
						Vector2[] vetrices = {new Vector2(x*-1, y), new Vector2((x*-1)+(25f/32f),0),new Vector2(x-(25f/32f),0),new Vector2(x,y)};
						body = PhysicsFactory.createPolygonBody(physicsWorld, spr,vetrices ,
								bodyType, fixtureDef);
					}
					else if (sprite.getCollitionType() == CollitionType.COLLITION_TRIANGLEPLUS3) {
						
						
						float x = spr.getWidth()/(2*32);
						float y = spr.getHeight()/(2*32);
						
						Vector2[] vetrices = {new Vector2(x*-1, y), new Vector2((x*-1)+(25f/32f),y*-1),new Vector2(x-(35f/32f),y*-1),new Vector2(x,y)};
						body = PhysicsFactory.createPolygonBody(physicsWorld, spr,vetrices ,
								bodyType, fixtureDef);
					}

					physicsWorld.registerPhysicsConnector(new PhysicsConnector(
							spr, body, true, updateRotation));
					sprite.setBody(body);
					body.setUserData(sprite);

				}

				// loader.reset();
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
			b = null;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadSpriteInWorldXML(SpritePlayer sprite, boolean b) {

		try {

			if (sprite.getCollitionType() == CollitionType.COLLITION_NONE) {
				return;
			}
			if (sprite.getXmlName() == null || sprite.getXmlName().isEmpty()) {

				FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(200f,
						0f, 0f);
				System.out.println("loading here");
				loadSpriteInWorld(sprite, fixtureDef, BodyType.DynamicBody,
						false);
				sprite.getBody().setFixedRotation(true);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addDistanceJoint(Body body, Body body2) {
		try {
			
			DistanceJointDef joint = new DistanceJointDef();
			joint.initialize(body, body2, new Vector2(body.getWorldCenter().x,body.getWorldCenter().y - 4/32f),
					new Vector2(body2.getWorldCenter().x,body.getWorldCenter().y - 5/32f  ));
			joint.collideConnected = true;
			joint.dampingRatio = 0;
			physicsWorld.createJoint(joint);
			
			
			DistanceJointDef joint2 = new DistanceJointDef();
			joint2.initialize(body, body2, new Vector2(body.getWorldCenter().x-15/32,body.getWorldCenter().y - 4/32f),
					new Vector2(body2.getWorldCenter().x-15/32,body.getWorldCenter().y - 5/32f  ));
			joint2.collideConnected = true;
			joint2.dampingRatio = 0;
			physicsWorld.createJoint(joint2);
			
			DistanceJointDef joint3 = new DistanceJointDef();
			joint3.initialize(body, body2, new Vector2(body.getWorldCenter().x+15/32,body.getWorldCenter().y - 4/32f),
					new Vector2(body2.getWorldCenter().x+15/32,body.getWorldCenter().y - 5/32f  ));
			joint3.collideConnected = true;
			joint3.dampingRatio = 0;
			physicsWorld.createJoint(joint3);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
