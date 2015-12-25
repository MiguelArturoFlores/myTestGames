package com.mgl.drop.game.entity;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;

import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpritePlayer;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class BackgroundEntity implements MySpriteGeneral{
	
	
	private Scene scene;
	
	private ArrayList<SpriteBackground> backgroundSky;
	private ArrayList<SpriteBackground> backgroundTree;
	private ArrayList<SpriteBackground> backgroundCity;
	private ArrayList<SpriteBackground> backgroundMountain;
	private ArrayList<SpriteBackground> backgroundBird;
	
	private Point camPoint;
	private SpritePlayer player;
	
	private float contTime= 0;
	private float maxTime= 2;
	
	public BackgroundEntity (Scene scene){
		try {
			Camera cam =  SceneManagerSingleton.getInstance().getCamera();
			camPoint = new Point(cam.getCenterX(), cam.getCenterY()); 
			
			
			this.scene = scene;
			
			backgroundSky = new ArrayList<SpriteBackground>();
			backgroundTree = new ArrayList<SpriteBackground>();
			backgroundCity = new ArrayList<SpriteBackground>();
			backgroundMountain = new ArrayList<SpriteBackground>();
			backgroundBird = new ArrayList<SpriteBackground>();
			
			
			TextureSingleton texture =  TextureSingleton.getInstance();
			
			//adding sky
			SpriteBackground back = new SpriteBackground(0, 0, texture.getTextureByName("backgroundSky.png"), texture.getVertexBufferObjectManager());
			scene.attachChild(back);
			backgroundSky.add(back);
			
			back = new SpriteBackground(back.getWidth()-1, 0, texture.getTextureByName("backgroundSky.png"), texture.getVertexBufferObjectManager());
			scene.attachChild(back);
			backgroundSky.add(back);
			
			//adding city
			back = new SpriteBackground(0, 0, texture.getTextureByName("backgroundCity.png"), texture.getVertexBufferObjectManager());
			scene.attachChild(back);
			backgroundCity.add(back);
			
			back = new SpriteBackground(back.getWidth()-1, 0, texture.getTextureByName("backgroundCity.png"), texture.getVertexBufferObjectManager());
			scene.attachChild(back);
			backgroundCity.add(back);
			
			//adding mountains
			back = new SpriteBackground(0, 800-177-75, texture.getTextureByName("backgroundMountain.png"), texture.getVertexBufferObjectManager());
			scene.attachChild(back);
			backgroundMountain.add(back);
			
			back = new SpriteBackground(back.getWidth()-1, 800-177-75, texture.getTextureByName("backgroundMountain.png"), texture.getVertexBufferObjectManager());
			scene.attachChild(back);
			backgroundMountain.add(back);
			
			//adding trees
			
			back = new SpriteBackground(0, 800-99-80, texture.getTextureByName("backgroundTree.png"), texture.getVertexBufferObjectManager());
			scene.attachChild(back);
			backgroundTree.add(back);
			
			back = new SpriteBackground(back.getWidth()-1, 800-99-80, texture.getTextureByName("backgroundTree.png"), texture.getVertexBufferObjectManager());
			scene.attachChild(back);
			backgroundTree.add(back);
			
			//	adding pigeons
			back = new SpriteBackground(-100, 200, texture.getTextureByName("backgroundBird.png"), texture.getVertexBufferObjectManager());
			scene.attachChild(back);
			backgroundBird.add(back);
			
			back = new SpriteBackground(back.getWidth()-1+100, 200, texture.getTextureByName("backgroundBird.png"), texture.getVertexBufferObjectManager());
			scene.attachChild(back);
			backgroundBird.add(back);
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			Camera cam =  SceneManagerSingleton.getInstance().getCamera();
			float centerX = cam.getCenterX();
			
			float distanceMountain = 5;
			float distanceTree = 3;
			float distanceCity = 3;
			float distanceBird = 4;
			
			if(!player.getStatus().equals(StatusType.WAITING)){
				contTime = contTime+dTime;
				if(contTime < 0.05){
					
					
					return;
				}
				
				distanceMountain = 0.7f;
				distanceTree = 1;
				distanceCity = -0.5f;
				distanceBird = 1;
				
				contTime = 0;
				
			}
			
			if(camPoint.getX()<centerX){
				for(SpriteBackground spr : backgroundMountain){
				
					spr.setX(spr.getX()+distanceMountain);
					camPoint.setX(centerX);
					
				}
				for(SpriteBackground spr : backgroundTree){
					
					spr.setX(spr.getX()-distanceTree);
					camPoint.setX(centerX);
					
				}
				for(SpriteBackground spr : backgroundCity){
					
					spr.setX(spr.getX()+distanceCity);
					camPoint.setX(centerX);
					
				}
				for(SpriteBackground spr : backgroundBird){
					
					spr.setX(spr.getX()-distanceBird);
					camPoint.setX(centerX);
					
				}
			}else if(camPoint.getX()>centerX){
				
				for(SpriteBackground spr : backgroundMountain){
					
					spr.setX(spr.getX()-distanceMountain);
					camPoint.setX(centerX);
					
				}
				for(SpriteBackground spr : backgroundTree){
					
					spr.setX(spr.getX()+distanceTree);
					camPoint.setX(centerX);
					
				}
				for(SpriteBackground spr : backgroundCity){
					
					spr.setX(spr.getX()-distanceCity);
					camPoint.setX(centerX);
					
				}
				for(SpriteBackground spr : backgroundBird){
					
					spr.setX(spr.getX()+distanceBird);
					camPoint.setX(centerX);
					
				}
			}
			
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

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public ArrayList<SpriteBackground> getBackgroundSky() {
		return backgroundSky;
	}

	public void setBackgroundSky(ArrayList<SpriteBackground> backgroundSky) {
		this.backgroundSky = backgroundSky;
	}

	public ArrayList<SpriteBackground> getBackgroundTree() {
		return backgroundTree;
	}

	public void setBackgroundTree(ArrayList<SpriteBackground> backgroundTree) {
		this.backgroundTree = backgroundTree;
	}

	public ArrayList<SpriteBackground> getBackgroundCity() {
		return backgroundCity;
	}

	public void setBackgroundCity(ArrayList<SpriteBackground> backgroundCity) {
		this.backgroundCity = backgroundCity;
	}

	public ArrayList<SpriteBackground> getBackgroundMountain() {
		return backgroundMountain;
	}

	public void setBackgroundMountain(ArrayList<SpriteBackground> backgroundMountain) {
		this.backgroundMountain = backgroundMountain;
	}

	public ArrayList<SpriteBackground> getBackgroundBird() {
		return backgroundBird;
	}

	public void setBackgroundBird(ArrayList<SpriteBackground> backgroundBird) {
		this.backgroundBird = backgroundBird;
	}

	public Point getCamPoint() {
		return camPoint;
	}

	public void setCamPoint(Point camPoint) {
		this.camPoint = camPoint;
	}

	public SpritePlayer getPlayer() {
		return player;
	}

	public void setPlayer(SpritePlayer player) {
		this.player = player;
	}

}
