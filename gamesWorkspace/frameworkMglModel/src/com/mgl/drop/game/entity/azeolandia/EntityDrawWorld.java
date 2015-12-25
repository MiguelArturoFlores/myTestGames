package com.mgl.drop.game.entity.azeolandia;

import java.util.ArrayList;


import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import com.mgl.base.MyEntity;
import com.mgl.base.MySpriteGeneral;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.BattleWorld;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.controller.dijkstra.WorldNode;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class EntityDrawWorld extends MyEntity {

	ArrayList<DrawNode> list;

	private boolean paintGreen = false;
	private boolean paintEnemy = false;

	public EntityDrawWorld(LevelController controller) {
		try {

			
			initWorlDraw(controller);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initWorlDraw(LevelController controller) {
		try {
			
			list = new ArrayList<DrawNode>();
			
			BattleWorld levelBattleWorld = controller.getLevelBattleWorld();
			
			for (int j = 0; j < levelBattleWorld.getHeight(); j++) {
				for (int i = 0; i < levelBattleWorld.getWidth(); i++) {

					WorldNode node = levelBattleWorld.getNode(i, j);
					DrawNode nodeD = new DrawNode(node, levelBattleWorld.getBoxWidth(), levelBattleWorld.getBoxHeight());
					list.add(nodeD);
					
				}
			}
			
			
			for(DrawNode node : list){
				controller.getScene().attachChild(node.getHexagon());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		try {

			paintGreen = false;
			paintEnemy = false;

		} catch (Exception e) {
			e.printStackTrace();
		}
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
			
			for(DrawNode nodeD : list){
				
					WorldNode node = nodeD.getNode();

					SpriteBackground nodeB = nodeD.getHexagon();
					
					if (node.getState().equals(WorldNode.OCCUPED)) {
						nodeB.setColor(1, 0, 0);
						//nodeB.setColor(174 / 255f, 174 / 255f, 174 / 255f);
						//nodeB.setAlpha(0f);
					} else {
						nodeB.setAlpha(0.5f);
						nodeB.setColor(174 / 255f, 174 / 255f, 174 / 255f);
						if (paintGreen) {
							nodeB.setColor(10 / 255f, 255 / 255f, 10 / 255f);
						}
					}
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private boolean validateCollitionWithPoint(float xBegin, float width,
			float yBegin, float height, Point point) {
		try {

			if (point.getX() >= xBegin && point.getX() <= xBegin + width) {
				if (point.getY() >= yBegin && point.getY() <= yBegin + height) {
					return true;
				}
			}

		} catch (Exception e) {
			return true;
		}
		return false;
	}

	public boolean isPaintGreen() {
		return paintGreen;
	}

	public void setPaintGreen(boolean paintGreen) {
		this.paintGreen = paintGreen;
	}


	public boolean isPaintEnemy() {
		return paintEnemy;
	}

	public void setPaintEnemy(boolean paintEnemy) {
		this.paintEnemy = paintEnemy;
	}

	public class DrawNode  {

		private WorldNode node;
		private SpriteBackground hexagon;
		
		public DrawNode(WorldNode node, float width, float height){
			this.node = node;
			
			drawHexagon(width,height);
			
		}

		private void drawHexagon(float width, float height) {
			
			try {
				
				TextureSingleton texture = TextureSingleton.getInstance();
				String textureName = "hexagon.png";

				hexagon = new SpriteBackground(node.getX(),
						node.getY(), texture.getTextureByName(textureName),
						texture.getVertexBufferObjectManager());
				hexagon.setAlpha(0.5f);
				hexagon.setSize(width,
						height);
				hexagon.setZIndex(ZIndexGame.BACKGROUND2);
				
				if (node.getState().equals(WorldNode.OCCUPED)) {
				//	hexagon.setColor(1, 0, 0);
				//	hexagon.setColor(174 / 255f, 174 / 255f, 174 / 255f);
					hexagon.setAlpha(0f);
				} else {
					hexagon.setColor(174 / 255f, 174 / 255f, 174 / 255f);
					if (paintGreen) {
						hexagon.setColor(10 / 255f, 255 / 255f, 10 / 255f);
					}
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		public WorldNode getNode() {
			return node;
		}

		public void setNode(WorldNode node) {
			this.node = node;
		}

		public SpriteBackground getHexagon() {
			return hexagon;
		}

		public void setHexagon(SpriteBackground hexagon) {
			this.hexagon = hexagon;
		}
		
		
	}
}
