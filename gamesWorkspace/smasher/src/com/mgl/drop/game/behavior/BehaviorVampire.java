package com.mgl.drop.game.behavior;

import com.mgl.base.MyAnimateSprite;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.BehaviorTypeConstant;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.SpriteHumanBasic;

public class BehaviorVampire {

	public static void updateNormal(float dTime, LevelController level,
			MyAnimateSprite sprite) {
		try {
			// TODO change speed
			float distanceTo = dTime * sprite.getSpeed();
			distanceTo = dTime * sprite.getSpeed();
			sprite.setPosition(sprite.getX(), sprite.getY() + distanceTo);

			if (sprite.collidesWith(level.getFloor())) {
				level.removeEntity(sprite);
				level.getScene().detachChild(sprite);
				if (!(sprite instanceof SpriteHumanBasic)) {
					level.looseLive();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateZigZag(float dTime, LevelController level,
			MyAnimateSprite sprite) {
		try {

			float distanceTo = dTime * sprite.getSpeed();
			distanceTo = dTime * sprite.getSpeed();
			sprite.setPosition(sprite.getX(), sprite.getY() + distanceTo);

			float distanceToX = dTime * sprite.getSpeedX();
			sprite.setX(sprite.getX() + distanceToX);

			if (sprite.getX() > MainDropActivity.CAMERA_WIDTH
					- sprite.getWidth()
					|| sprite.getX() < 0) {
				sprite.setSpeedX(sprite.getSpeedX() * -1);
			}

			if (sprite.collidesWith(level.getFloor())) {
				level.removeEntity(sprite);
				level.getScene().detachChild(sprite);
				if (!(sprite instanceof SpriteHumanBasic)) {
					level.looseLive();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateAccelerate(float dTime, LevelController level,
			MyAnimateSprite sprite) {
		try {
			float speedAux = sprite.getSpeed();
			if (sprite.getY() + sprite.getWidth() / 2 > MainDropActivity.CAMERA_HEIGHT / 2 - 80) {

				sprite.setSpeed(450);

			}

			float distanceTo = dTime * sprite.getSpeed();
			distanceTo = dTime * sprite.getSpeed();
			sprite.setPosition(sprite.getX(), sprite.getY() + distanceTo);

			sprite.setSpeed(speedAux);

			if (sprite.collidesWith(level.getFloor())) {
				level.removeEntity(sprite);
				level.getScene().detachChild(sprite);
				if (!(sprite instanceof SpriteHumanBasic)) {
					level.looseLive();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateNormalZigZag(float dTime, LevelController level,
			MyAnimateSprite sprite) {
		try {

			if (sprite.getY() + sprite.getWidth() / 2 > MainDropActivity.CAMERA_HEIGHT / 2 - 80) {

				updateZigZag(dTime, level, sprite);
				return;
			}

			float distanceTo = dTime * sprite.getSpeed();
			distanceTo = dTime * sprite.getSpeed();
			sprite.setPosition(sprite.getX(), sprite.getY() + distanceTo);

			if (sprite.collidesWith(level.getFloor())) {
				level.removeEntity(sprite);
				level.getScene().detachChild(sprite);
				if (!(sprite instanceof SpriteHumanBasic)) {
					level.looseLive();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateZigZagNormal(float dTime, LevelController level,
			MyAnimateSprite sprite) {
		try {

			if (sprite.getY() + sprite.getWidth() / 2 > MainDropActivity.CAMERA_HEIGHT / 2 - 80) {

				updateNormal(dTime, level, sprite);
				return;
			}
			

			float distanceTo = dTime * sprite.getSpeed();
			distanceTo = dTime * sprite.getSpeed();
			sprite.setPosition(sprite.getX(), sprite.getY() + distanceTo);

			float distanceToX = dTime * sprite.getSpeedX();
			sprite.setX(sprite.getX() + distanceToX);

			if (sprite.getX() > MainDropActivity.CAMERA_WIDTH
					- sprite.getWidth()
					|| sprite.getX() < 0) {
				sprite.setSpeedX(sprite.getSpeedX() * -1);
			}

			if (sprite.collidesWith(level.getFloor())) {
				level.removeEntity(sprite);
				level.getScene().detachChild(sprite);
				if (!(sprite instanceof SpriteHumanBasic)) {
					level.looseLive();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateNormalBoss(float dTime, LevelController level,
			MyAnimateSprite sprite) {
		try {

			float distanceTo = dTime * sprite.getSpeed();
			distanceTo = dTime * sprite.getSpeed();
			if (sprite.getY() + sprite.getHeight() > MainDropActivity.CAMERA_HEIGHT / 2 + 200) {
				sprite.setBehavior(BehaviorTypeConstant.BEHAVIOR_BOSS_WAITING);
			} else {
				sprite.setPosition(sprite.getX(), sprite.getY() + distanceTo);
			}

			if (sprite.collidesWith(level.getFloor())) {
				level.removeEntity(sprite);
				level.getScene().detachChild(sprite);
				if (!(sprite instanceof SpriteHumanBasic)) {
					level.looseLive();
					level.looseLive();
					level.looseLive();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
