package com.mgl.drop.game.sprites;

import org.andengine.entity.IEntity;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpriteDeadBody extends MySprite {

	public SpriteDeadBody(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		status = status.FALLING;
		this.setIgnoreUpdate(true);
	}

	private float speed = 1050f;
	private float xToMove;
	private float yToMove;
	private float angle;

	private float timeToDesapear = 2.2f;
	private float contTime = 0;
	private float alpha= 1;

	public void generatePositionToFall() {
		try {

			float val = (float) ((Math.random() * 100000000) % (MainDropActivity.CAMERA_WIDTH - 100));
			// this.setPosition(MainDropActivity.CAMERA_WIDTH, 0);
			this.setPosition(val, 0);
			xToMove = val;
			yToMove = MainDropActivity.CAMERA_HEIGHT / 2;

			angle = Point.angleBetween(new Point(xToMove, yToMove), new Point(
					this.getX(), this.getY()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.FOOD;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {

			if (status.equals(StatusType.FALLING)) {

				float distanceTo = dTime * speed;
				distanceTo = dTime * speed;

				float sin = (float) (distanceTo * Math.sin(angle));
				if (sin < 0)
					sin = sin * -1;
				float y = (float) (this.getY() + sin);

				float cos = (float) (distanceTo * Math.cos(angle));
				// if(cos<0) cos =cos*-1;
				float x = (float) (this.getX() + cos);

				this.setPosition(this.getX(), this.getY() + distanceTo);
				// this.setPosition(x,y);

				if (this.getY() > MainDropActivity.getCAMERA_HEIGHT() / 2) {
					status = StatusType.NORMAL;
				}
				System.out.println("FAALLLLINNNGGG x" + this.getX() + " y"
						+ this.getY() + " angle " + angle);

			} else if (status.equals(StatusType.NORMAL)) {
				try {

					contTime += dTime;
					if (contTime > timeToDesapear) {
						status = StatusType.DESAPEARING;

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (status.equals(StatusType.DESAPEARING)) {

				this.setAlpha(alpha);
				
				alpha = alpha - 0.02f;
				if(alpha<=0){
					this.detachSelf();
					level.removeEntity(this);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
