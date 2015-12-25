package com.mgl.drop.game.controller;

import java.util.ArrayList;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.google.example.games.basegameutils.ButtonLeaderboard;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.arunner.SpritePlayerModel;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class BeltListController {

	private ArrayList<MySpriteGeneral> spriteList;
	private SpriteBackground side1;
	private SpriteBackground side2;
	private SpriteBackground sideMid;

	private int position = 0;
	private Point point;
	private Scene scene;
	private ButtonNavigate next;
	private ButtonNavigate prev;
	private int zIndex;

	public BeltListController(ArrayList<MySpriteGeneral> list, int position,
			Point point, Scene scene, int zindex) {
		try {

			zIndex = zindex;
			this.scene = scene;
			spriteList = list;
			this.position = position;
			this.point = point;
			if (position >= spriteList.size()) {
				position = spriteList.size();
			}

			TextureSingleton texture = TextureSingleton.getInstance();

			next = new ButtonNavigate(0, 0,
					texture.getTextureByName("arrowRight.png"),
					texture.getVertexBufferObjectManager(), true);
			next.setSize(70, 80);
			next.setZIndex(zindex);

			prev = new ButtonNavigate(0, 0,
					texture.getTextureByName("arrowLeft.png"),
					texture.getVertexBufferObjectManager(), false);
			prev.setSize(70, 80);
			prev.setZIndex(zindex);

			scene.registerTouchArea(next);
			scene.registerTouchArea(prev);

			scene.attachChild(next);
			scene.attachChild(prev);

			drawBelt();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void drawBelt() {
		try {
			boolean hasLeft = true;
			boolean hasRight = true;

			clearTouchareas();

			if (position >= spriteList.size()) {
				position = spriteList.size() - 1;
				hasRight = false;
			}
			if (position <= 0) {
				position = 0;
			}

			if (position - 1 < 0) {
				hasLeft = false;
			}

			prev.setPosition(point.getX() - 20, point.getY() + 80);

			if (side1 != null) {
				side1.detachSelf();
			}
			if (side2 != null) {
				side2.detachSelf();
			}
			if (sideMid != null) {
				sideMid.detachSelf();
			}

			side1 = null;
			side2 = null;
			sideMid = null;

			if (hasLeft) {
				System.out.println("getting left");
				addItemInPosition(position - 1, new Point(point.getX() + 60,
						point.getY()), false);
			}
			if (hasRight) {
				System.out.println("getting Right");
				addItemInPosition(position + 1, new Point(point.getX() + 455,
						point.getY()), false);
			}
			System.out.println("getting Mid");
			addItemInPosition(position,
					new Point(point.getX() + 255 , point.getY()), true);

			next.setPosition(point.getX() + 613, point.getY() + 80);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearTouchareas() {
		try {

			for (MySpriteGeneral spr : spriteList) {
				if (spr instanceof MySprite) {

					((MySprite) spr).setPosition(-1000, -1000);
					scene.unregisterTouchArea(((MySprite) spr));

				} else if (spr instanceof MyAnimateSprite) {
					((MyAnimateSprite) spr).setPosition(-1000, -1000);
					scene.unregisterTouchArea(((MyAnimateSprite) spr));

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addItemInPosition(int position2, Point point2, boolean isCenter) {
		try {

			MySpriteGeneral spr = spriteList.get(position2);
			SpritePlayerModel sprite = (SpritePlayerModel) spr;
			TextureSingleton texture = TextureSingleton.getInstance();

			sprite.detachSelf();
			sprite.setPosition(point2.getX(), point2.getY());
			sprite.setZIndex(zIndex);
			
			if (sprite.hasParent()) {
				sprite.detachSelf();
			}
			
			drawLeaderBoard(sprite);
			
			scene.unregisterTouchArea(sprite);
			
			if (!isCenter) {
				sprite.setSize(150, 150);
				sprite.setY((sprite).getY() + 25);
			} else {

			}

			scene.registerTouchArea(sprite);

			String textureName = "square.png";
			int offset = 8;
			if (!isCenter) {
				textureName = "square.png";
			}

			if (!isCenter) {
				if (side1 == null) {
					side1 = new SpriteBackground(0, 0,
							texture.getTextureByName(textureName),
							texture.getVertexBufferObjectManager());
					side1.setSize(sprite.getWidth() + offset,
							sprite.getHeight() + offset);
					side1.setPosition(sprite.getX() - offset / 2, sprite.getY()
							- offset / 2);
					side1.setZIndex(ZIndexGame.FADE);
					scene.attachChild(side1);
				} else if (side2 == null) {
					side2 = new SpriteBackground(0, 0,
							texture.getTextureByName(textureName),
							texture.getVertexBufferObjectManager());
					side2.setSize(sprite.getWidth() + offset,
							sprite.getHeight() + offset);
					side2.setPosition(sprite.getX() - offset / 2, sprite.getY()
							- offset / 2);
					side2.setZIndex(ZIndexGame.FADE);
					scene.attachChild(side2);
				}
			} else {
				sideMid = new SpriteBackground(0, 0,
						texture.getTextureByName(textureName),
						texture.getVertexBufferObjectManager());
				sideMid.setSize(sprite.getWidth() + offset, sprite.getHeight()
						+ offset);
				sideMid.setPosition(sprite.getX() - offset / 2, sprite.getY()
						- offset / 2);
				sideMid.setZIndex(ZIndexGame.FADE);
				scene.attachChild(sideMid);
			}

			scene.attachChild(sprite);
			
		} catch (Exception e) {
			System.out.println("problem on this position " + position2);
			e.printStackTrace();
		}

	}

	private void drawLeaderBoard(SpritePlayerModel sprite) {
		try {
			
			ButtonLeaderboard leaderBoard = MyFactory.createButtonLeaderBoard();
			leaderBoard.setLeaderboarName(sprite.getLeaderboardName());
			sprite.attachChild(leaderBoard);
			leaderBoard.setSize(40, 40);
			scene.registerTouchArea(leaderBoard);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class ButtonNavigate extends MySprite {

		private boolean isNext;

		public ButtonNavigate(float pX, float pY,
				ITextureRegion pNormalTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager,
				boolean isNext) {
			super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
			this.isNext = isNext;
		}

		@Override
		public SpriteType getSpriteType() {
			// TODO Auto-generated method stub
			return SpriteType.DECORATIVE;
		}

		@Override
		public void update(float dTime, LevelController level) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
				final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			try {

				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:

					if (isNext) {
						position++;
					} else {
						position--;
					}
					drawBelt();

					break;
				case TouchEvent.ACTION_MOVE:

					break;
				case TouchEvent.ACTION_UP:

					break;
				default:

					break;

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}

	}

}
