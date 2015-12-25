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


			updateRemovingUnused(dTime, level);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateRemovingUnused(float dTime, LevelController level) {
		try {


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateQueue(LevelController level) {
		try {


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
