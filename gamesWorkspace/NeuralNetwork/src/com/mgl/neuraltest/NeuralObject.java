package com.mgl.neuraltest;

import java.awt.Graphics;
import java.util.ArrayList;

import com.mgl.neural.NeuralEntry;
import com.mgl.neural.NeuralNetwork;
import com.mgl.neural.Neuron;
import com.mgl.neural.NeuronLayer;

public class NeuralObject {

	
	public static final String ACTION_MOVE_UP = "11";
	public static final String ACTION_MOVE_DOWN = "10";
	public static final String ACTION_MOVE_LEFT = "01";
	public static final String ACTION_MOVE_RIGHT = "00";
	
	private int x;
	private int y;
	
	private int action; 
	private int life = 20;
	
	private NeuralNetwork neuralNetwork;

	public NeuralObject(int x, int y){
		try {
			
			this.x = x;
			this.y = y;
			
			neuralNetwork = new NeuralNetwork(3+8*8, 100, 20, 2);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(float dTime , World world){
		try {
			
			//action = (int) World.getRandomMax(0, 3);
			//System.out.println("Object "+x +" y "+y);n
			
			ArrayList<NeuralEntry> entryList = generateEntryList(dTime, world);
			neuralNetwork.process(entryList);
			
			//System.out.println("OUT "+ neuralNetwork.getOut());
			
			
			life --;
			System.out.println("life "+life);
			if(life<0){
				die(dTime,world);
			}
			
			
			
			
			
			switch (neuralNetwork.getOut()) {
			case ACTION_MOVE_DOWN:
				
				if(world.canMoveDown(x,y)){
					world.moveDown(x,y,WorldConstants.NEURAL_OBJECT);
					y=y+1;
				}
				
				break;
				
			case ACTION_MOVE_UP:
				
				if(world.canMoveUp(x,y)){
					world.moveUp(x,y,WorldConstants.NEURAL_OBJECT);
					y=y-1;
				}
				
				break;
				
			case ACTION_MOVE_RIGHT:
				
				if(world.canMoveRight(x,y)){
					world.moveRight(x,y,WorldConstants.NEURAL_OBJECT);
					x=x+1;
				}
				
				break;
			case ACTION_MOVE_LEFT:
				
				if(world.canMoveLeft(x,y)){
					world.moveLeft(x,y,WorldConstants.NEURAL_OBJECT);
					x=x-1;
				}
				
				break;

			default:
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	private ArrayList<NeuralEntry> generateEntryList(float dTime, World world) {
		ArrayList<NeuralEntry> entryList = new ArrayList<>();
		try {
			
			NeuralEntry n = new NeuralEntry(x, Float.valueOf(""+x));
			NeuralEntry n1 = new NeuralEntry(y, Float.valueOf(""+y));
			NeuralEntry n2 = new NeuralEntry(life, Float.valueOf(""+life));
			
			entryList.add(n);
			entryList.add(n1);
			entryList.add(n2);
			
			for(int j = 0 ; j < world.getHeight(); j++ ){
				for(int i = 0 ; i < world.getWidth(); i++ ){
					
					NeuralEntry ne = new NeuralEntry(world.getWorld()[i][j], world.getWorld()[i][j].floatValue());
					entryList.add(ne);
				}
			}
			
			//System.out.println("GENERATE ENTRY LIST");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entryList;
	}

	private void die(float dTime, World world) {
		// TODO Auto-generated method stub
		
	}

	public void draw(Graphics g){
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public NeuralNetwork getNeuralNetwork() {
		return neuralNetwork;
	}

	public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
	}
	
	
	
}
