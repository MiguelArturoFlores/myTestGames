package com.mgl.drop.game.entity;

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
import com.mgl.base.userinfo.PhysicSingleton;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.arunner.SpritePlayer;
import com.mgl.drop.util.Point;

public class EntityAddPhysics extends MyEntity {

	private float DISTANCE_TO_ADD = 1200;
	private ArrayList<MySpriteGeneral> spriteListToAdd;
	private boolean beginAdd;
	private Queue<OrderList> spriteQueueToAdd;
	private float contTime = 0;
	private float timeToAdd = 0.10f;
	public EntityAddPhysics(ArrayList<MySpriteGeneral> spriteToAdd) {
		try {

			this.spriteListToAdd = spriteToAdd;
			spriteQueueToAdd = new LinkedList();
			beginAdd = false;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setMustUpdate(boolean mustUpdate) {

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

			if (spriteQueueToAdd.isEmpty()) {
				level.removeEntity(this);
				return;
			}
			contTime = contTime +dTime;
			if(contTime<timeToAdd){
				return;
			}
			contTime = 0;
					
			int i = 0;
			while (!spriteQueueToAdd.isEmpty() && i < 1) {
				i++;
				
				OrderList order = spriteQueueToAdd.peek();
				SpritePlayer player = level.getPlayer();
				float distance = Point.distanceBetween(new Point(player.getX(), player.getY()), new Point(order.getX(), order.getY()));
				timeToAdd = 0.5f;
				if(distance>2000){
					System.out.println("reloading QUEUE "+distance +" x"+order.getX() +" y"+order.getY());
					//geneateQueue(level);
					timeToAdd = 1f;
					
				}
				 order = spriteQueueToAdd.poll();
				try {
					MySpriteGeneral spr = order.getSprite();

					if (spr instanceof MyAnimateSprite) {
						int zIndex = ((MyAnimateSprite) spr).getZIndex();
						PhysicSingleton.getInstance().loadSpriteInWorldXML(spr);
						MyAnimateSprite mySprite = (MyAnimateSprite) spr;
						mySprite.setZIndex(zIndex);
						level.setBodyPixelPosition(mySprite.getX(), mySprite.getY(),
								mySprite.getMyAngle(), mySprite.getWidth(),
								mySprite.getHeight(), mySprite.getBody());
						
					}else if(spr instanceof MySprite) {
						
					}
						int zIndex = ((MySprite) spr).getZIndex();
						PhysicSingleton.getInstance().loadSpriteInWorldXML(spr);
						MySprite mySprite = (MySprite) spr;
						mySprite.setZIndex(zIndex);
						level.setBodyPixelPosition(mySprite.getX(), mySprite.getY(),
								mySprite.getMyAngle(), mySprite.getWidth(),
								mySprite.getHeight(), mySprite.getBody());

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateQueue(LevelController level) {
		try {

			SpritePlayer player = level.getPlayer();
			spriteQueueToAdd = new LinkedList<OrderList>();

			ArrayList<OrderList> orderList = new ArrayList<EntityAddPhysics.OrderList>();

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

	private class OrderList implements Comparable<OrderList> {

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
		
		public float getX(){
			if(sprite instanceof MyAnimateSprite){
				return ((MyAnimateSprite) sprite).getX();
			}
			else if(sprite instanceof MySprite){
				return ((MySprite) sprite).getX();
			}
			return 0;
		}

		public float getY(){
			if(sprite instanceof MySprite){
				return ((MySprite) sprite).getY();
			}
			else if(sprite instanceof MyAnimateSprite){
				return ((MyAnimateSprite) sprite).getY();
			}
			return 0;
		}
	}

}
