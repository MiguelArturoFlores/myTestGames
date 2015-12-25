package com.mgl.drop.game.sprites;

import org.andengine.audio.sound.Sound;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.google.android.gms.drive.events.ChangeEvent;
import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.StatusType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneTouchListenerRocket;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class SpritePoopRocket extends MyAnimateSprite {

	private float speed = 100;
	private float speedFlying = 500;
	private float explosionTime = 2;
	private float explosionCont = 0;
	private float totalDistance = 0;
	private float maxDistance = 400;

	private Sound flyingSound = null;

	private Point beginPoint;
	private Point endPoint;

	private SpriteInvisibleTouch collisionShape;

	public SpritePoopRocket(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		try {
			status = StatusType.FALLING;
			type = SpriteTypeConstant.POOP_ROCKET;

			collisionShape = new SpriteInvisibleTouch((0f), (0f), 200f, 200f,
					TextureSingleton.getInstance()
							.getTextureByName("black.jpg"),
					pVertexBufferObjectManager, this);
			this.attachChild((collisionShape));
			collisionShape.setWidth(20);
			collisionShape.setHeight(20);
			collisionShape.setPosition(-collisionShape.getWidth() / 2 +30,
					-collisionShape.getHeight() / 2+35);
			collisionShape.setAlpha(0.2f);
			// spr.setVisible(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initAnimationParams() {
		try {

			fps = new long[] { 100, 100, 100, 100, 100 };
			imageNumber = 1;
			currentState = State.FALLING_DOWN;

			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initHashMap() {
		try {
			fps = new long[] { 100, 100, 100 };
			stateAnimate.put(State.FALLING_DOWN, new MyAnimateProperty(0, 3,
					fps));
			stateAnimate.put(State.POOP_FLYING_UP, new MyAnimateProperty(3, 3,
					fps));
			stateAnimate.put(State.POOP_FLYING_UP_RIGHT, new MyAnimateProperty(
					6, 3, fps));

			stateAnimate.put(State.POOP_FLYING_RIGHT, new MyAnimateProperty(9,
					3, fps));

			stateAnimate.put(State.POOP_FLYING_DOWN_RIGHT,
					new MyAnimateProperty(12, 3, fps));

			stateAnimate.put(State.POOP_FLYING_DOWN, new MyAnimateProperty(15,
					3, fps));

			stateAnimate.put(State.POOP_FLYING_DOWN_LEFT,
					new MyAnimateProperty(18, 3, fps));

			stateAnimate.put(State.POOP_FLYING_LEFT, new MyAnimateProperty(21,
					3, fps));

			stateAnimate.put(State.POOP_FLYING_UP_LEFT, new MyAnimateProperty(
					24, 3, fps));

			stateAnimate.put(State.POOP_EXPLODING, new MyAnimateProperty(27, 3,
					fps));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			if (status.equals(StatusType.FALLING)) {

				updateFalling(dTime, level);

			} else if (status.equals(StatusType.POOP_FLYING)) {

				updateFlying(dTime, level);

			} else if (status.equals(StatusType.POOP_EXPLODING)) {

				if (!isAnimationRunning()) {
					status = StatusType.FINISHED;
					this.setZIndex(5);
					level.getScene().detachChild(this);
					level.removeEntity(this);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateFlying(float dTime, LevelController level) {
		try {

			float distance = dTime * speedFlying;

			totalDistance = totalDistance + distance;

			for (MySpriteGeneral sprite : level.getSpriteList()) {

				if (sprite.getSpriteType().equals(SpriteType.TERRAIN)) {

					if (sprite instanceof MySprite) {
						MySprite spr = (MySprite) sprite;
						if (this.collidesWith(spr)) {
							if (sprite.getSpriteType().equals(
									SpriteType.TERRAIN)) {
								/*
								 * status = StatusType.POOP_EXPLODING;
								 * changeAnimateState(State.POOP_EXPLODING,
								 * false);
								 */
								if (spr.getParent() instanceof SpriteOvni) {

									verifyCollisionOvni(spr);

									Sound s = SoundSingleton.getInstance()
											.getSound("explosion.mp3");
									if (s != null) {
										s.play();
									}

									this.setSize(this.getWidth() + 10,
											this.getHeight());
									this.setPosition(this.getX() - 10,
											(this.getY()));
									

								} else {
									boolean b = verifyCollision(spr);
									if (b) {
										status = StatusType.POOP_EXPLODING;
										changeAnimateState(
												State.POOP_EXPLODING, false);
										Sound s = SoundSingleton.getInstance()
												.getSound("explosion.mp3");
										if (s != null) {
											s.play();
										}
										this.setSize(this.getWidth() + 10,
												this.getHeight());
										this.setPosition(this.getX() - 10,
												(this.getY()));
										
									}
								}
							}
						} else if (sprite instanceof MyAnimateSprite) {
							MyAnimateSprite spr2 = (MyAnimateSprite) sprite;

							if (this.collidesWith(spr2)) {
								if (sprite.getSpriteType().equals(
										SpriteType.TERRAIN)) {
									/*
									 * status = StatusType.POOP_EXPLODING;
									 * changeAnimateState (State.POOP_EXPLODING,
									 * false);
									 */
									if (spr2.getParent() instanceof SpriteOvni) {
										verifyCollisionOvni(spr2);

										Sound s = SoundSingleton.getInstance()
												.getSound("explosion.mp3");
										if (s != null) {
											s.play();
										}
										this.setSize(this.getWidth() + 10,
												this.getHeight());
										this.setPosition(this.getX() - 10,
												(this.getY()));
										return;
									} else {
										boolean b = verifyCollision(spr2);
										if (b) {
											status = StatusType.POOP_EXPLODING;
											changeAnimateState(
													State.POOP_EXPLODING, false);
											Sound s = SoundSingleton
													.getInstance().getSound(
															"explosion.mp3");
											if (s != null) {
												s.play();
											}
											this.setSize(this.getWidth() + 10,
													this.getHeight());
											this.setPosition(this.getX() - 10,
													(this.getY()));
											
										}
									}

								}
							}
						}
					}

				}
				if (sprite.getSpriteType().equals(SpriteType.OBJETIVE)) {

					if (sprite instanceof MySprite) {
						MySprite spr = (MySprite) sprite;
						if (this.collidesWith(spr)) {
							spr.poop(this, level);
							status = StatusType.POOP_EXPLODING;
							changeAnimateState(State.POOP_EXPLODING, false);
							Sound s = SoundSingleton.getInstance().getSound(
									"explosion.mp3");
							if (s != null) {
								s.play();
							}
							
						}
					} else if (sprite instanceof MyAnimateSprite) {
						MyAnimateSprite spr = (MyAnimateSprite) sprite;
						if (this.collidesWith(spr)) {
							// spr.setStatus(StatusType.POOPED);
							spr.poop(this, level);
							status = StatusType.POOP_EXPLODING;
							changeAnimateState(State.POOP_EXPLODING, false);
							Sound s = SoundSingleton.getInstance().getSound(
									"explosion.mp3");
							if (s != null) {
								s.play();
							}
						
						}
					}
				}
			}
			// Log.d("rocjket",
			// "abc pos "+distance+" x"+this.getX()+" y"+this.getY());

			float x = this.getX();
			float y = this.getY();
			Point currentPoint = new Point(x + getWidth() / 2, y + getHeight()
					/ 2);

			if (totalDistance > maxDistance) {

				// this.setPosition(endPoint.getX()+getWidth()/2,
				// endPoint.getY()+getHeight()/2);

				status = StatusType.POOP_EXPLODING;
				changeAnimateState(State.POOP_EXPLODING, false);
				Sound s = SoundSingleton.getInstance()
						.getSound("explosion.mp3");
				if (s != null) {
					s.play();
				}

				return;
			}

			float angle = Point.angleBetween(currentPoint, endPoint);

			float dx = distance * (float) Math.cos(angle);
			float dy = distance * (float) Math.sin(angle);

			if (dx == 0f && dy == 0) {
				status = StatusType.POOP_EXPLODING;
				changeAnimateState(State.POOP_EXPLODING, false);
				Sound s = SoundSingleton.getInstance()
						.getSound("explosion.mp3");
				if (s != null) {
					s.play();
				}

				return;
			}
			this.setPosition(x + dx, y + dy);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean verifyCollision(MySpriteGeneral spr) {
		try {

			if (spr instanceof MySprite) {
				MySprite myspr = (MySprite) spr;
				return myspr.collidesWith(this.collisionShape);

			} else if (spr instanceof MyAnimateSprite) {

				MyAnimateSprite myspr = (MyAnimateSprite) spr;
				return (myspr.collidesWith(this.collisionShape));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void updateFalling(float dTime, LevelController level) {
		try {

			float distance = dTime * speed;

			if (this.getX() < 0) {
				level.getScene().detachChild(this);
				level.removeEntity(this);
			}

			for (MySpriteGeneral sprite : level.getSpriteList()) {
				if (sprite.getSpriteType().equals(SpriteType.TERRAIN)
						|| sprite.getSpriteType().equals(SpriteType.OBJETIVE)) {

					if (sprite instanceof MySprite) {
						MySprite spr = (MySprite) sprite;
						if (this.collidesWith(spr)) {
							if (sprite.getSpriteType().equals(
									SpriteType.TERRAIN)) {
								status = StatusType.POOP_EXPLODING;
								changeAnimateState(State.POOP_EXPLODING, false);

								verifyCollisionOvni(spr);

								Sound s = SoundSingleton.getInstance()
										.getSound("explosion.mp3");
								if (s != null) {
									s.play();
								}

								this.setSize(this.getWidth() + 10,
										this.getHeight());
								this.setPosition(this.getX() - 10,
										(this.getY()));
								return;
							} else {
								if (!spr.getStatus().equals(StatusType.POOPED)) {
									changeAnimateState(State.POOP_EXPLODING,
											false);
									Sound s = SoundSingleton.getInstance()
											.getSound("explosion.mp3");
									if (s != null) {
										s.play();
									}
									spr.poop(this, level);
								}
							}
						}
					} else if (sprite instanceof MyAnimateSprite) {
						MyAnimateSprite spr = (MyAnimateSprite) sprite;

						if (this.collidesWith(spr)) {
							if (sprite.getSpriteType().equals(
									SpriteType.TERRAIN)) {
								status = StatusType.POOP_EXPLODING;
								changeAnimateState(State.POOP_EXPLODING, false);
								verifyCollisionOvni(spr);
								Sound s = SoundSingleton.getInstance()
										.getSound("explosion.mp3");
								if (s != null) {
									s.play();
								}

								this.setSize(this.getWidth() + 10,
										this.getHeight());
								this.setPosition(this.getX() - 10,
										(this.getY()));
								return;
							} else {
								if (!spr.getStatus().equals(StatusType.POOPED)) {
									changeAnimateState(State.POOP_EXPLODING,
											false);
									Sound s = SoundSingleton.getInstance()
											.getSound("explosion.mp3");
									if (s != null) {
										s.play();
									}
									spr.poop(this, level);
								}
							}
						}
					}
				}
			}

			this.setPosition(this.getX(), (this.getY() + distance));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void verifyCollisionOvni(MySpriteGeneral spr) {
		try {

			if (spr instanceof MySprite) {
				MySprite myspr = (MySprite) spr;
				SpriteOvni ovni = (SpriteOvni) myspr.getParent();
				ovni.changeAnimateState(State.POOP_BEGIN, false);
				ovni.setStatus(StatusType.DESTROY_OVNI);
				Log.d("DESTROYING", "destroying ovni");
				SoundSingleton.getInstance().playSound("ovniExploding.mp3");

			} else if (spr instanceof MyAnimateSprite) {

				MyAnimateSprite myspr = (MyAnimateSprite) spr;
				SpriteOvni ovni = (SpriteOvni) myspr.getParent();
				ovni.changeAnimateState(State.POOP_BEGIN, false);
				ovni.setStatus(StatusType.DESTROY_OVNI);
				Log.d("DESTROYING", "destroying ovni");
				SoundSingleton.getInstance().playSound("ovniExploding.mp3");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.POOP;
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		if (true) {
			return true;
		}

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			beginPoint = new Point(this.getX() + this.getWidth() / 2,
					this.getY() + this.getWidth() / 2);
			addPointTOVIEW(beginPoint);

			break;
		case TouchEvent.ACTION_MOVE:

			if (status.equals(StatusType.POOP_FLYING)) {
				return true;
			}

			endPoint = new Point(this.getX() + pTouchAreaLocalX, this.getY()
					+ pTouchAreaLocalY);
			Log.d("BeginPointA",
					"X " + beginPoint.getX() + " y" + beginPoint.getY());
			Log.d("EndPointA", "X " + endPoint.getX() + " y" + endPoint.getY());

			if (Point.distanceBetween(beginPoint, endPoint) >= 100) {
				setStatus(StatusType.POOP_FLYING);
				changeAnimateState(State.POOP_FLYING, true);
				Sound s = SoundSingleton.getInstance().getSound(
						"flyingRocket.mp3");
				if (s != null) {
					s.play();
				}

				endPoint = Point.calculatePointPlusDistance(beginPoint,
						endPoint, maxDistance);
				Log.d("ENDP1", "X: " + endPoint.getX() + " y" + endPoint.getY());
				endPoint.setX(endPoint.getX() + this.getX());
				endPoint.setY(endPoint.getY() + this.getY());
				Log.d("ENDPPP",
						"X: " + endPoint.getX() + " y" + endPoint.getY());
				endPoint = new Point(this.getX() + pTouchAreaLocalX,
						this.getY() + pTouchAreaLocalY);
				addPointTOVIEW(endPoint);

			}
			break;
		case TouchEvent.ACTION_UP:

			break;
		default:

			break;

		}
		return false;
	}

	private void addPointTOVIEW(Point p) {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();
			SpriteWayPoint point = new SpriteWayPoint(p.getX(), p.getY(),
					texture.getTextureByName("feather.png"),
					texture.getVertexBufferObjectManager(), level.getScene());
			point.setSize(25, 25);
			point.setPosition(p.getX() - point.getWidth() / 2,
					p.getY() - point.getHeight() / 2);
			level.getScene().attachChild(point);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setSceneTouchListener() {
		try {

			if (level.getScene().getOnSceneTouchListener() instanceof SceneTouchListenerRocket) {
				SceneTouchListenerRocket listener = (SceneTouchListenerRocket) level
						.getScene().getOnSceneTouchListener();
				listener.addRocket(this);
			} else {
				level.getScene().setOnSceneTouchListener(
						new SceneTouchListenerRocket(this));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getSpeedFlying() {
		return speedFlying;
	}

	public void setSpeedFlying(float speedFlying) {
		this.speedFlying = speedFlying;
	}

	public float getExplosionTime() {
		return explosionTime;
	}

	public void setExplosionTime(float explosionTime) {
		this.explosionTime = explosionTime;
	}

	public float getExplosionCont() {
		return explosionCont;
	}

	public void setExplosionCont(float explosionCont) {
		this.explosionCont = explosionCont;
	}

	public float getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(float totalDistance) {
		this.totalDistance = totalDistance;
	}

	public float getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(float maxDistance) {
		this.maxDistance = maxDistance;
	}

	public Sound getFlyingSound() {
		return flyingSound;
	}

	public void setFlyingSound(Sound flyingSound) {
		this.flyingSound = flyingSound;
	}

	public Point getBeginPoint() {
		return beginPoint;
	}

	public void setBeginPoint(Point beginPoint) {
		this.beginPoint = beginPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public void activate() {
		try {

			// TODO change animate state from angle

			beginPoint = new Point(this.getX() + this.getWidth() / 2,
					this.getY() + this.getHeight() / 2);
			setStatus(StatusType.POOP_FLYING);
			changeAnimateState(State.POOP_FLYING, true);
			Sound s = SoundSingleton.getInstance().getSound("flyingRocket.mp3");

			Float angle = Point.angleBetween(beginPoint, endPoint);
			if (Point.distanceBetween(beginPoint, endPoint) < maxDistance) {

				// endPoint = Point.calculatePointPlusDistance(beginPoint,
				// endPoint, maxDistance);
				Log.d("BeginPOINT ", "x " + beginPoint.getX() + " y "
						+ beginPoint.getY());
				Log.d("ENDPOINT ",
						"x " + endPoint.getX() + " y " + endPoint.getY());
				maxDistance = Point.distanceBetween(beginPoint, endPoint);
				//

			}

			calculateDirection(beginPoint, endPoint);
			// asd TODO

			if (s != null) {
				s.play();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void calculateDirection(Point p1Aux, Point p2Aux) {
		try {

			Point p1 = new Point(0, 0);
			Point p2 = new Point(p2Aux.getX() - p1Aux.getX(), p2Aux.getY()
					- p1Aux.getY());

			Float grad45 = Double.valueOf(Math.PI).floatValue() / 4f;
			Float grad90 = Double.valueOf(Math.PI).floatValue() / 2f;
			Float grad135 = grad90 + grad45;

			Float angle = Point.angleBetween(p1, p2);

			Float anotherVal = angle * 180
					/ Double.valueOf(Math.PI).floatValue();

			System.out.println("este es el angulo " + angle + " angleGrad "
					+ anotherVal + " pi/4 " + grad135);

			if (p1.getX() <= p2.getX() && p1.getY() <= p2.getY()) {
				// cuarto cuadrante
				if (angle > grad45) {
					// posiblemente este en down o downRight
					Float valAux = grad45 + grad45 / 1.5f;
					if (angle > valAux) {
						// down
						System.out.println("Down1");
						changeAnimateState(State.POOP_FLYING_DOWN, true);
					} else {
						// downRight
						System.out.println("DownRight");
						changeAnimateState(State.POOP_FLYING_DOWN_RIGHT, true);
					}

				} else {
					// posiblemente este en right o downRight
					Float valAux = grad45 / 3f;
					if (angle > valAux) {
						// downRight
						System.out.println("DownRight");
						changeAnimateState(State.POOP_FLYING_DOWN_RIGHT, true);

					} else {
						// right
						System.out.println("Rigth");
						changeAnimateState(State.POOP_FLYING_RIGHT, true);
					}
				}
				return;
			}

			if (p1.getX() >= p2.getX() && p1.getY() <= p2.getY()) {
				// 3er cuadante
				if (angle >= grad135) {
					// posiblemente left o downLeft
					Float valAux = grad135 + grad45 / 1.5f;
					if (angle > valAux) {
						// left
						System.out.println("Left1");
						changeAnimateState(State.POOP_FLYING_LEFT, true);
					} else {
						// downLeft
						System.out.println("DownLeft1");
						changeAnimateState(State.POOP_FLYING_DOWN_LEFT, true);
					}

				} else {
					// posiblemente este en down o downLeft
					Float valAux = grad135 - grad45 / 3f;
					if (angle > valAux) {
						// downRight
						System.out.println("DownLeft2");
						changeAnimateState(State.POOP_FLYING_DOWN_LEFT, true);

					} else {
						// right
						System.out.println("down2");
						changeAnimateState(State.POOP_FLYING_DOWN, true);
					}
				}
				return;

			}

			if (p2.getX() <= p1.getX() && p2.getY() <= p1.getY()) {
				// segundo cuadrante
				angle = angle * -1;
				if (angle >= grad135) {
					// posiblemente left o upLeft
					Float valAux = grad135 + grad45 / 1.5f;
					if (angle > valAux) {
						// left
						System.out.println("Left2");
						changeAnimateState(State.POOP_FLYING_LEFT, true);
					} else {
						// upLeft
						System.out.println("UpLeft1");
						changeAnimateState(State.POOP_FLYING_UP_LEFT, true);
					}

				} else {

					// posiblemente este en up o upLeft
					Float valAux = grad135 - grad45 / 3f;
					if (angle > valAux) {
						// upRight
						System.out.println("UpLeft2");
						changeAnimateState(State.POOP_FLYING_UP_LEFT, true);

					} else {
						// left
						System.out.println("up2");
						changeAnimateState(State.POOP_FLYING_UP, true);
					}
				}
				return;

			}

			angle = angle * -1;
			if (angle > grad45) {
				// posiblemente este en up o upRight
				Float valAux = grad45 + grad45 / 1.5f;
				if (angle > valAux) {
					// up
					System.out.println("up1");
					changeAnimateState(State.POOP_FLYING_UP, true);
				} else {
					// upRight
					System.out.println("upRight");
					changeAnimateState(State.POOP_FLYING_UP_RIGHT, true);
				}

			} else {
				// posiblemente este en right o upRight
				Float valAux = grad45 / 3f;
				if (angle > valAux) {
					// upRight
					System.out.println("UpRight");
					changeAnimateState(State.POOP_FLYING_UP_RIGHT, true);

				} else {
					// right
					System.out.println("Rigth");
					changeAnimateState(State.POOP_FLYING_RIGHT, true);
				}
			}
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
