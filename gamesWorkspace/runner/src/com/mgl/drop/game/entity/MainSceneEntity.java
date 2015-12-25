package com.mgl.drop.game.entity;

import java.util.ArrayList;


import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;

import com.google.android.gms.internal.iz;
import com.mgl.base.MyEntity;
import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.SpriteBackgroundPersonage;
import com.mgl.drop.texture.TextureSingleton;

public class MainSceneEntity extends MyEntity {

	private Scene scene;

	private float contTime = 0;
	private float timeToGenerateHuman = 0.7f;
	private float timeBetween = 2f;
	private float offsetTime = 4;

	private TextureSingleton texture = TextureSingleton.getInstance();

	private ArrayList<MySpriteGeneral> spriteList;

	private boolean generateHuman = true;
	private boolean generateVampire = true;
	private float xToGenerate;

	public MainSceneEntity(Scene scene) {
		this.scene = scene;
		contTime = 0;
		spriteList = new ArrayList<MySpriteGeneral>();
		generateHuman = true;
		generateVampire = true;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			contTime = contTime + dTime;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.DECORATIVE;
	}

	@Override
	public void setStatus(StatusType status) {
		// TODO Auto-generated method stub

	}

	@Override
	public StatusType getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void poop(MySpriteGeneral poop, LevelController level) {
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
	public void setMustUpdate(boolean mustUpdate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateChild(float dTime, LevelController level) {
		// TODO Auto-generated method stub
		
	}

}
