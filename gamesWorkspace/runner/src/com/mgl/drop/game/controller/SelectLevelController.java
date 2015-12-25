package com.mgl.drop.game.controller;

import java.util.ArrayList;

import org.andengine.entity.scene.Scene;

import com.mgl.base.MyFactory;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.game.entity.BackgroudLevelSceneEntity;
import com.mgl.drop.game.entity.SelectLevelSceneEntity;
import com.mgl.drop.game.sprites.arunner.SpriteVulcano;

public class SelectLevelController {
	
	ArrayList<MySpriteGeneral> spriteList;
	
	public SelectLevelController(Scene scene){
		try {
			
			spriteList = new ArrayList<MySpriteGeneral>();
			
			//SelectLevelSceneEntity entity = new SelectLevelSceneEntity(scene); 
			//spriteList.add(entity);
			
			SpriteVulcano vulcano = (SpriteVulcano) MyFactory.createObstacle(SpriteTypeConstant.VULCANO, null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void update(float dTime, Scene scene){
		try {
			
			for(MySpriteGeneral spr: spriteList){
				spr.update(dTime, null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
