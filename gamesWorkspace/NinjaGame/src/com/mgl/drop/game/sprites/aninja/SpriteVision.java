package com.mgl.drop.game.sprites.aninja;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.controller.LevelWorld;
import com.mgl.drop.game.controller.dijkstra.WorldNode;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.ninja.SpritePlayer;
import com.mgl.drop.util.Point;

public class SpriteVision extends MySprite{
	
	private SpriteEnemy enemy;
	private ArrayList<SpriteBackground> visionList;

	private Queue<SpriteBackground> backgroundQueue;
	
	
	private SpriteBackground v1;
	private SpriteBackground v2;
	private SpriteBackground v3;
	private SpriteBackground v4;
	private SpriteBackground v5;
	private SpriteBackground v6;
	private SpriteBackground v7;
	private SpriteBackground v8;
	private SpriteBackground v9;
	
	
	public SpriteVision(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, SpriteEnemy enemy) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		this.enemy  = enemy;
		visionList = new ArrayList<SpriteBackground>();
		try {
			backgroundQueue = new LinkedList<SpriteBackground>();
			visionList = new ArrayList<SpriteBackground>();
			
			
				v1 = new SpriteBackground(0, 0, texture.getTextureByName("v1.png"), texture.getVertexBufferObjectManager());
				v1.setZIndex(ZIndexGame.BACKGROUND2);
				
				v2 = new SpriteBackground(0, 0, texture.getTextureByName("v2.png"), texture.getVertexBufferObjectManager());
				v2.setZIndex(ZIndexGame.BACKGROUND2);
				
				v3 = new SpriteBackground(0, 0, texture.getTextureByName("v3.png"), texture.getVertexBufferObjectManager());
				v3.setZIndex(ZIndexGame.BACKGROUND2);
				
				v4 = new SpriteBackground(0, 0, texture.getTextureByName("v4.png"), texture.getVertexBufferObjectManager());
				v4.setZIndex(ZIndexGame.BACKGROUND2);
				
				v5 = new SpriteBackground(0, 0, texture.getTextureByName("v5.png"), texture.getVertexBufferObjectManager());
				v5.setZIndex(ZIndexGame.BACKGROUND2);
				
				v6 = new SpriteBackground(0, 0, texture.getTextureByName("v6.png"), texture.getVertexBufferObjectManager());
				v6.setZIndex(ZIndexGame.BACKGROUND2);
				
				v7 = new SpriteBackground(0, 0, texture.getTextureByName("v7.png"), texture.getVertexBufferObjectManager());
				v7.setZIndex(ZIndexGame.BACKGROUND2);
				
				v8 = new SpriteBackground(0, 0, texture.getTextureByName("v8.png"), texture.getVertexBufferObjectManager());
				v8.setZIndex(ZIndexGame.BACKGROUND2);
			
				v9 = new SpriteBackground(0, 0, texture.getTextureByName("v9.png"), texture.getVertexBufferObjectManager());
				v9.setZIndex(ZIndexGame.BACKGROUND2);
				
				visionList.add(v1);
				visionList.add(v2);
				visionList.add(v3);
				visionList.add(v4);
				visionList.add(v5);
				visionList.add(v6);
				visionList.add(v7);
				visionList.add(v8);
				visionList.add(v9);
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub
		
	}

	public SpriteEnemy getEnemy() {
		return enemy;
	}

	public void setEnemy(SpriteEnemy enemy) {
		this.enemy = enemy;
	}

	public void updateVisionRight(LevelController controller) {
		try {
			
			WorldNode root = controller.getLevelWorld().getRight(enemy.getX() + enemy.getMidPoint().getX(),enemy.getY() + enemy.getMidPoint().getY());
			
			if(root == null){
				return;
			}

			LevelWorld world = controller.getLevelWorld();
			
			WorldNode nodeA1 = world.getUp(root.getX(), root.getY());
			WorldNode nodeA2 = root;
			WorldNode nodeA3 = world.getDown(root.getX(), root.getY());
			
			WorldNode nodeB1 = world.getRight(nodeA1.getX(), nodeA1.getY());
			WorldNode nodeB2 = world.getRight(nodeA2.getX(), nodeA2.getY());
			WorldNode nodeB3 = world.getRight(nodeA3.getX(), nodeA3.getY());
			
			WorldNode nodeC1 = world.getRight(nodeB1.getX(), nodeB1.getY());
			WorldNode nodeC2 = world.getRight(nodeB2.getX(), nodeB2.getY());
			WorldNode nodeC3 = world.getRight(nodeB3.getX(), nodeB3.getY());
			WorldNode nodeC4 = world.getDown(nodeC3.getX(), nodeC3.getY());
			WorldNode nodeC0 = world.getUp(nodeC1.getX(), nodeC1.getY());
			
			
			
			if(nodeA2.isOccupedForVision()){
				return;
			}
			
			float angle = 90*3;
			
			createRectangle(nodeA2, controller,1,angle);
			
			//b2
			if(!nodeB2.isOccupedForVision()){
				createRectangle(nodeB2, controller,3,angle);
			}
			
			//b1
			if(!nodeB1.isOccupedForVision() && !nodeA1.isOccupedForVision() ){
				createRectangle(nodeB1, controller,2,angle);
			}
			
			//b3
			if(!nodeB3.isOccupedForVision() && !nodeA3.isOccupedForVision() ){
				createRectangle(nodeB3, controller,4,angle);
			}
			
			//c0
			if(!nodeC0.isOccupedForVision() && !nodeB1.isOccupedForVision() && !nodeA1.isOccupedForVision()){
				createRectangle(nodeC0, controller,5,angle);
			}
			
			//c1
			if(!nodeC1.isOccupedForVision() && !nodeB1.isOccupedForVision()  && !nodeB2.isOccupedForVision()){
				createRectangle(nodeC1, controller,6,angle);
			}
			
			//c2
			if(!nodeC2.isOccupedForVision() && !nodeB2.isOccupedForVision()){
				createRectangle(nodeC2, controller,7,angle);
			}
			
			//c3
			if(!nodeC3.isOccupedForVision() && !nodeB3.isOccupedForVision() && !nodeB2.isOccupedForVision()){
				createRectangle(nodeC3, controller,8,angle);
			}
			
			//c4
			if(!nodeC4.isOccupedForVision() && !nodeB3.isOccupedForVision() && !nodeA3.isOccupedForVision()){
				createRectangle(nodeC4, controller,9,angle);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateVisionLeft(LevelController controller) {
		try {
			
			WorldNode root = controller.getLevelWorld().getLeft(enemy.getX() + enemy.getMidPoint().getX(),enemy.getY() + enemy.getMidPoint().getY());
			
			if(root == null){
				return;
			}

			LevelWorld world = controller.getLevelWorld();
			
			WorldNode nodeA1 = world.getUp(root.getX(), root.getY());
			WorldNode nodeA2 = root;
			WorldNode nodeA3 = world.getDown(root.getX(), root.getY());
			
			WorldNode nodeB1 = world.getLeft(nodeA1.getX(), nodeA1.getY());
			WorldNode nodeB2 = world.getLeft(nodeA2.getX(), nodeA2.getY());
			WorldNode nodeB3 = world.getLeft(nodeA3.getX(), nodeA3.getY());
			
			WorldNode nodeC1 = world.getLeft(nodeB1.getX(), nodeB1.getY());
			WorldNode nodeC2 = world.getLeft(nodeB2.getX(), nodeB2.getY());
			WorldNode nodeC3 = world.getLeft(nodeB3.getX(), nodeB3.getY());
			WorldNode nodeC4 = world.getDown(nodeC3.getX(), nodeC3.getY());
			WorldNode nodeC0 = world.getUp(nodeC1.getX(), nodeC1.getY());
			
			if(nodeA2.isOccupedForVision()){
				return;
			}
			float angle = 90;
			
			createRectangle(nodeA2, controller,1,angle);
			
			//b2
			if(!nodeB2.isOccupedForVision()){
				createRectangle(nodeB2, controller,3,angle);
			}
			
			//b1
			if(!nodeB1.isOccupedForVision() && !nodeA1.isOccupedForVision() ){
				createRectangle(nodeB1, controller,4,angle);
			}
			
			//b3
			if(!nodeB3.isOccupedForVision() && !nodeA3.isOccupedForVision() ){
				createRectangle(nodeB3, controller,2,angle);
			}
			
			//c0
			if(!nodeC0.isOccupedForVision() && !nodeB1.isOccupedForVision() && !nodeA1.isOccupedForVision()){
				createRectangle(nodeC0, controller,9,angle);
			}
			
			//c1
			if(!nodeC1.isOccupedForVision() && !nodeB1.isOccupedForVision()  && !nodeB2.isOccupedForVision()){
				createRectangle(nodeC1, controller,8,angle);
			}
			
			//c2
			if(!nodeC2.isOccupedForVision() && !nodeB2.isOccupedForVision() ){
				createRectangle(nodeC2, controller,7,angle);
			}
			
			//c3
			if(!nodeC3.isOccupedForVision() && !nodeB3.isOccupedForVision() && !nodeB2.isOccupedForVision()){
				createRectangle(nodeC3, controller,6,angle);
			}
			
			//c4
			if(!nodeC4.isOccupedForVision() && !nodeB3.isOccupedForVision() && !nodeA3.isOccupedForVision()){
				createRectangle(nodeC4, controller,5,angle);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateVisionUp(LevelController controller) {
		try {
			
			WorldNode root = controller.getLevelWorld().getUp(enemy.getX() + enemy.getMidPoint().getX(),enemy.getY() + enemy.getMidPoint().getY());
			
			if(root == null){
				return;
			}

			LevelWorld world = controller.getLevelWorld();
			
			WorldNode nodeA1 = world.getLeft(root.getX(), root.getY());
			WorldNode nodeA2 = root;
			WorldNode nodeA3 = world.getRight(root.getX(), root.getY());
			
			WorldNode nodeB1 = world.getUp(nodeA1.getX(), nodeA1.getY());
			WorldNode nodeB2 = world.getUp(nodeA2.getX(), nodeA2.getY());
			WorldNode nodeB3 = world.getUp(nodeA3.getX(), nodeA3.getY());
			
			WorldNode nodeC1 = world.getUp(nodeB1.getX(), nodeB1.getY());
			WorldNode nodeC2 = world.getUp(nodeB2.getX(), nodeB2.getY());
			WorldNode nodeC3 = world.getUp(nodeB3.getX(), nodeB3.getY());
			WorldNode nodeC4 = world.getRight(nodeC3.getX(), nodeC3.getY());
			WorldNode nodeC0 = world.getLeft(nodeC1.getX(), nodeC1.getY());
			
			
			if(nodeA2.isOccupedForVision()){
				return;
			}
			float angle = 180;
			
			createRectangle(nodeA2, controller,1,angle);
			
			//b2
			if(!nodeB2.isOccupedForVision()){
				createRectangle(nodeB2, controller,3,angle);
			}
			
			//b1
			if(!nodeB1.isOccupedForVision() && !nodeA1.isOccupedForVision() ){
				createRectangle(nodeB1, controller,2,angle);
			}
			
			//b3
			if(!nodeB3.isOccupedForVision() && !nodeA3.isOccupedForVision() ){
				createRectangle(nodeB3, controller,4,angle);
			}
			
			//c0
			if(!nodeC0.isOccupedForVision() && !nodeB1.isOccupedForVision() && !nodeA1.isOccupedForVision()){
				createRectangle(nodeC0, controller,5,angle);
			}
			
			//c1
			if(!nodeC1.isOccupedForVision() && !nodeB1.isOccupedForVision()  && !nodeB2.isOccupedForVision()){
				createRectangle(nodeC1, controller,6,angle);
			}
			
			//c2
			if(!nodeC2.isOccupedForVision() && !nodeB2.isOccupedForVision()){
				createRectangle(nodeC2, controller,7,angle);
			}
			
			//c3
			if(!nodeC3.isOccupedForVision() && !nodeB3.isOccupedForVision() && !nodeB2.isOccupedForVision()){
				createRectangle(nodeC3, controller,8,angle);
			}
			
			//c4
			if(!nodeC4.isOccupedForVision() && !nodeB3.isOccupedForVision() && !nodeA3.isOccupedForVision()){
				createRectangle(nodeC4, controller,9,angle);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateVisionDown(LevelController controller) {
		try {
			
			WorldNode root = controller.getLevelWorld().getDown(enemy.getX() + enemy.getMidPoint().getX(),enemy.getY() + enemy.getMidPoint().getY());
			
			if(root == null){
				return;
			}

			LevelWorld world = controller.getLevelWorld();
			
			WorldNode nodeA1 = world.getLeft(root.getX(), root.getY());
			WorldNode nodeA2 = root;
			WorldNode nodeA3 = world.getRight(root.getX(), root.getY());
			
			WorldNode nodeB1 = world.getDown(nodeA1.getX(), nodeA1.getY());
			WorldNode nodeB2 = world.getDown(nodeA2.getX(), nodeA2.getY());
			WorldNode nodeB3 = world.getDown(nodeA3.getX(), nodeA3.getY());
			
			WorldNode nodeC1 = world.getDown(nodeB1.getX(), nodeB1.getY());
			WorldNode nodeC2 = world.getDown(nodeB2.getX(), nodeB2.getY());
			WorldNode nodeC3 = world.getDown(nodeB3.getX(), nodeB3.getY());
			WorldNode nodeC4 = world.getRight(nodeC3.getX(), nodeC3.getY());
			WorldNode nodeC0 = world.getLeft(nodeC1.getX(), nodeC1.getY());
			
			
			
			if(nodeA2.isOccupedForVision()){
				return;
			}
			
			float angle = 0;
			
			createRectangle(nodeA2, controller,1,angle);
			
			//b2
			if(!nodeB2.isOccupedForVision()){
				createRectangle(nodeB2, controller,3,angle);
			}
			
			//b1
			if(!nodeB1.isOccupedForVision() && !nodeA1.isOccupedForVision() ){
				createRectangle(nodeB1, controller,4,angle);
			}
			
			//b3
			if(!nodeB3.isOccupedForVision() && !nodeA3.isOccupedForVision() ){
				createRectangle(nodeB3, controller,2,angle);
			}
			
			//c0
			if(!nodeC0.isOccupedForVision() && !nodeB1.isOccupedForVision() && !nodeA1.isOccupedForVision()){
				createRectangle(nodeC0, controller,9,angle);
			}
			
			//c1
			if(!nodeC1.isOccupedForVision() && !nodeB1.isOccupedForVision()  && !nodeB2.isOccupedForVision()){
				createRectangle(nodeC1, controller,8,angle);
			}
			
			//c2
			if(!nodeC2.isOccupedForVision() && !nodeB2.isOccupedForVision()){
				createRectangle(nodeC2, controller,7,angle);
			}
			
			//c3
			if(!nodeC3.isOccupedForVision() && !nodeB3.isOccupedForVision() && !nodeB2.isOccupedForVision()){
				createRectangle(nodeC3, controller,6,angle);
			}
			
			//c4
			if(!nodeC4.isOccupedForVision() && !nodeB3.isOccupedForVision() && !nodeA3.isOccupedForVision()){
				createRectangle(nodeC4, controller,5,angle);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createRectangle(WorldNode node, LevelController controller,int value,float angle) {
		try {
			
			//SpriteBackground occuped = new SpriteBackground(0, 0, texture.getTextureByName("gray.jpg"), texture.getVertexBufferObjectManager());
			
			//SpriteBackground occuped = getNextVision();
			
			if(node.isCorriged()){
				//return;
			}
			
			SpriteBackground occuped = getSprite(value);
			occuped.setZIndex(ZIndexGame.BACKGROUND2);
			occuped.setAlpha(1f);
						
			occuped.setPosition(node.getX(),node.getY());
			if(!occuped.hasParent()){
				controller.getScene().attachChild(occuped);
			}
			
			occuped.setRotationCenter(occuped.getWidth()/2, occuped.getHeight()/2);
			occuped.setRotation(angle);
			//occuped.setAlpha(0.3f);
			//occuped.setSize(controller.getLevelWorld().getBoxWidth(), controller.getLevelWorld().getBoxHeight());
			
			
			//validate player saw
			SpritePlayer player = controller.getPlayer();
			Point p = new Point(player.getX() + player.getMidPoint().getX(),player.getY() + player.getMidPoint().getY());
			
			if(p.getX() >= occuped.getX() && p.getX()<= occuped.getX()+ occuped.getWidth() ){
				if(p.getY() >= occuped.getY() && p.getY()<= occuped.getY()+ occuped.getHeight() ){
					controller.looseLevelReplay();
				}
			}
			
			//validate enemy dead saw
			for(MySpriteGeneral spr : controller.getSpriteList()){
				if(spr instanceof SpriteEnemy){
					SpriteEnemy enemy = (SpriteEnemy) spr;
					if(enemy.getX()+enemy.getMidPoint().getX() >= occuped.getX() && enemy.getX() +enemy.getMidPoint().getX() <= occuped.getX()+ occuped.getWidth() ){
						if(enemy.getY() +enemy.getMidPoint().getY() >= occuped.getY() && enemy.getY() +enemy.getMidPoint().getY() <= occuped.getY()+ occuped.getHeight() ){
							//enemy.wakeUp();
							this.enemy.wakeUpFriend(enemy);
						}
					}
				}
			}
			
			
			/*
			WorldNode myNode = controller.getLevelWorld().getDown(player.getX() + player.getMidPoint().getX(),player.getY() + player.getMidPoint().getY());
			
			if(node.equals(myNode)){
				controller.looseLevelReplay();
			}
			*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SpriteBackground getSprite(int value) {
		try {
			
			switch (value) {
			case 1:
				return v1;
			case 2:
				return v2;
			case 3:
				return v3;
			case 4:
				return v4;
			case 5:
				return v5;
			case 6:
				return v6;
			case 7:
				return v7;
			case 8:
				return v8;
			case 9:
				return v9;

			default:
				SpriteBackground occuped =  new SpriteBackground(0, 0, texture.getTextureByName("v1.png"), texture.getVertexBufferObjectManager());
				occuped.setZIndex(ZIndexGame.BACKGROUND2);
				return occuped;
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpriteBackground occuped =  new SpriteBackground(0, 0, texture.getTextureByName("v1.png"), texture.getVertexBufferObjectManager());
		occuped.setZIndex(ZIndexGame.BACKGROUND2);
		return occuped;
	}

	private SpriteBackground getNextVision() {
		try {
			
			SpriteBackground back = backgroundQueue.poll();
			backgroundQueue.add(back);
			
			return back;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void resetVision() {
		try {
			
			for(SpriteBackground spr : visionList){
			//	spr.detachSelf();
				spr.setAlpha(0f);
			}
			
			//visionList = new ArrayList<SpriteBackground>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearVision() {
		try {
			
			for(SpriteBackground spr : visionList){
					spr.detachSelf();
				}

			visionList = new ArrayList<SpriteBackground>();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
}
