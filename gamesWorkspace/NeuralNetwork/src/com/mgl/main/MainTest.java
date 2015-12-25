package com.mgl.main;

import java.util.ArrayList;

import com.mgl.neural.NeuralEntry;
import com.mgl.neural.NeuralNetwork;
import com.mgl.neuraltest.World;

public class MainTest {

	public static void main(String[] args) {
		try {
			
			NeuralNetwork n = new NeuralNetwork(1, 1, 1, 1);
			
			ArrayList<NeuralEntry> entryList = new ArrayList<>();
			for(int i=0; i< 1; i++){
				NeuralEntry ne = new NeuralEntry(1, 1);
				entryList.add(ne);
			}
			
			n.process(entryList);
			System.out.println("");
			entryList = new ArrayList<>();
			for(int i=0; i< 1; i++){
				NeuralEntry ne = new NeuralEntry(0, 0f);
				entryList.add(ne);
			}
			
			n.process(entryList);
			//n.printNeuralNetwork();
			
			World world = new World(8,8);
			//world.printWorld();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
