package com.mgl.drop.game.sprites.arunner;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.google.android.gms.drive.internal.RemoveEventListenerRequest;
import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.util.Point;

public class SpriteStar extends MyAnimateSprite {

	private static final float MIN_DISTANCE_TO_UPDATE = 300;
	private boolean collected = false;
	private boolean checkedRemove = false;
	private Long id;

	public SpriteStar(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController controller) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,
				controller);
		collected = false;
		checkedRemove = false;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.STAR;
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			if (!checkedRemove) {
				LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
						.getActivity(), DatabaseDrop.DB_NAME, null,
						MainDropActivity.DB_VERSION);

				boolean collected = dao.isStarCollected(id);
				if (collected) {
					this.detachSelf();
					level.removeEntity(this);
				} else {
					checkedRemove = true;
				}

				return;
			}

			SpritePlayer player = level.getPlayer();

			if (Point.distanceBetween(new Point(player.getX(), player.getY()),
					new Point(this.getX(), this.getY())) > MIN_DISTANCE_TO_UPDATE) {
				return;
			}

			if (this.collidesWith(level.getPlayer()) && !collected) {
				collected = true;
				level.increaseStar();
				changeAnimateState(State.DIYING, false);
				SoundSingleton.getInstance().playStar();

			}
			if (collected == true) {
				if (!isAnimationRunning()) {
					this.detachSelf();
				}
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}

	@Override
	public void initAnimationParams() {
		try {

			changeAnimateState(State.NORMAL, true);
			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83, 83, 83, 83, 83, 83 };

			stateAnimate.put(State.NORMAL, new MyAnimateProperty(0, 2,
					new long[] { 83, 83 }));
			stateAnimate.put(State.DIYING, new MyAnimateProperty(2, 6,
					new long[] { 83, 83, 83, 83, 83, 83 }));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setXmlParameter(String parameter) {
		try {

			ArrayList<Long> parameterList = MyXmlParser
					.getParameterList(parameter);
			int i = 0;
			for (Long param : parameterList) {
				try {
					if (i == 0) {
						id = param;
					}

				} catch (Exception e) {

				}
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
