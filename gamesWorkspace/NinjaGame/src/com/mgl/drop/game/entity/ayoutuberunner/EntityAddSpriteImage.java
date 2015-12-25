package com.mgl.drop.game.entity.ayoutuberunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyEntity;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.PhysicSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.ninja.SpritePlayer;
import com.mgl.drop.util.Point;
import com.mgl.drop.util.Point;

public class EntityAddSpriteImage extends MyEntity {

	private ArrayList<MySpriteGeneral> spriteListToAdd;
	private boolean beginAdd;
	private Queue<OrderList> spriteQueueToAdd;
	private ArrayList<MySpriteGeneral> spriteQueueToRemove;
	private float contTime = 0;
	private float timeToAdd = 0.10f;

	public EntityAddSpriteImage(ArrayList<MySpriteGeneral> spriteToAdd) {

		this.spriteListToAdd = spriteToAdd;
		spriteQueueToAdd = new LinkedList();
		spriteQueueToRemove = new ArrayList<MySpriteGeneral>();
		beginAdd = false;

	}

	@Override
	public void setMustUpdate(boolean mustUpdate) {
		// TODO Auto-generated method stub

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
		try {
			if (!beginAdd) {
				beginAdd = true;
				generateQueue(level);
				return;
			}

			contTime = contTime + dTime;
			if (contTime < timeToAdd) {
				return;
			}
			contTime = 0;

			int i = 0;
			boolean stop = false;
			while (!spriteQueueToAdd.isEmpty() && i < 10 && !stop) {
				i++;

				OrderList order = spriteQueueToAdd.peek();
				SpritePlayer player = level.getPlayer();
				float distance = Point.distanceBetween(new Point(player.getX(),
						player.getY()), new Point(order.getX(), order.getY()));
				timeToAdd = 0.5f;
				if (distance > 2500) {
					// System.out.println("reloading QUEUE "+distance
					// +" x"+order.getX() +" y"+order.getY());
					// geneateQueue(level);
					timeToAdd = 1f;
					stop = true;

				}
				order = spriteQueueToAdd.poll();
				spriteQueueToRemove.add(order.getSprite());

				try {
					MySpriteGeneral spr = order.getSprite();

					if (spr instanceof MyAnimateSprite) {

						level.getScene().attachChild((MyAnimateSprite)spr);	
						
					} else if (spr instanceof MySprite) {
						if(spr.getSpriteType().equals(SpriteType.LIKE_COIN)){
							level.addSpriteToUpdate(spr);
						}
						level.getScene().attachChild((MySprite)spr);
						
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			updateRemovingUnused(dTime, level);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateRemovingUnused(float dTime, LevelController level) {
		try {
			ArrayList<MySpriteGeneral> listToRemove = new ArrayList<MySpriteGeneral>();
			for (MySpriteGeneral spr : spriteQueueToRemove) {
				if (spr instanceof MyAnimateSprite) {
					if (((MyAnimateSprite) spr).getX()
							+ ((MyAnimateSprite) spr).getWidth() < level
							.getPlayer().getX()-400) {
						level.removeEntity(spr);
						((MyAnimateSprite) spr).detachSelf();
						listToRemove.add(spr);
					}
				} else if (spr instanceof MySprite) {
					if (((MySprite) spr).getX() + ((MySprite) spr).getWidth() < level
							.getPlayer().getX()-400) {
						level.removeEntity(spr);
						((MySprite) spr).detachSelf();
						listToRemove.add(spr);
					}
				}
			}

			spriteQueueToRemove.removeAll(listToRemove);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateQueue(LevelController level) {
		try {

			SpritePlayer player = level.getPlayer();
			spriteQueueToAdd = new LinkedList<OrderList>();

			ArrayList<OrderList> orderList = new ArrayList<OrderList>();

			for (MySpriteGeneral spr : spriteListToAdd) {
				float distance = 0;
				if (spr instanceof MySprite) {
					distance = Point.distanceBetween(new Point(player.getX(),
							player.getY()), new Point(((MySprite) spr).getX(),
							((MySprite) spr).getY()));
				}
				if (spr instanceof MyAnimateSprite) {
					distance = Point.distanceBetween(new Point(player.getX(),
							player.getY()),
							new Point(((MyAnimateSprite) spr).getX(),
									((MyAnimateSprite) spr).getY()));
				}

				OrderList order = new OrderList(spr, distance);

				orderList.add(order);
			}

			Collections.sort(orderList);

			for (OrderList order : orderList) {

				spriteQueueToAdd.add(order);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public class OrderList implements Comparable<OrderList> {

		private MySpriteGeneral sprite;
		private float distance;

		public OrderList(MySpriteGeneral sprite, float distance) {
			this.distance = distance;
			this.sprite = sprite;
		}

		@Override
		public int compareTo(OrderList another) {

			if (this.distance < another.distance) {
				return -1;
			} else if (this.distance > another.distance) {
				return 1;
			}
			return 0;
		}

		public MySpriteGeneral getSprite() {
			return sprite;
		}

		public void setSprite(MySpriteGeneral sprite) {
			this.sprite = sprite;
		}

		public float getDistance() {
			return distance;
		}

		public void setDistance(float distance) {
			this.distance = distance;
		}

		public float getX() {
			if (sprite instanceof MyAnimateSprite) {
				return ((MyAnimateSprite) sprite).getX();
			} else if (sprite instanceof MySprite) {
				return ((MySprite) sprite).getX();
			}
			return 0;
		}

		public float getY() {
			if (sprite instanceof MySprite) {
				return ((MySprite) sprite).getY();
			} else if (sprite instanceof MyAnimateSprite) {
				return ((MyAnimateSprite) sprite).getY();
			}
			return 0;
		}
	}

}
