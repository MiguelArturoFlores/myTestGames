package com.mgl.drop.game.sprites.arunner;

import java.util.Queue;









import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;









import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.util.Point;

public class SpriteCheckPoint extends MyAnimateSprite{
	
	private static final float MIN_DISTANCE_TO_UPDATE = 400;
	private Queue<Point> path;
	private Point cameraPosition;
	private boolean collected = false;

	public SpriteCheckPoint(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,LevelController controller) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,controller);

	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.CHECKPOINT;
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {

		try {
			
			SpritePlayer player = level.getPlayer();
			
			if(Point.distanceBetween(new Point(player.getX(), player.getY()), new Point(this.getX(), this.getY()))>MIN_DISTANCE_TO_UPDATE){
				return;
			}
			
			if(level.getPlayer().collidesWith(this) && !collected){
				this.path = level.getInvisiblePointToFollow().clonePath();
				Camera camera = SceneManagerSingleton.getInstance().getCamera();
				cameraPosition = new Point(camera.getCenterX(), camera.getCenterY());
				level.setCheckPoint(this);
				collected = true;
				changeAnimateState(State.DIYING, false);
				SoundSingleton.getInstance().playCheckpoint();
			}
			
			if(collected){
				if(!isAnimationRunning()){
					this.detachSelf();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Queue<Point> getPath() {
		return path;
	}

	public void setPath(Queue<Point> path) {
		this.path = path;
	}

	public Point getCameraPosition() {
		return cameraPosition;
	}

	public void setCameraPosition(Point cameraPosition) {
		this.cameraPosition = cameraPosition;
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
					new long[] { 83, 83}));
			stateAnimate.put(State.DIYING, new MyAnimateProperty(2, 6,
					new long[] { 83, 83, 83, 83, 83, 83 }));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
