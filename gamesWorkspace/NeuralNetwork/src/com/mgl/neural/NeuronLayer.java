package com.mgl.neural;

import java.util.ArrayList;

public class NeuronLayer {

	private ArrayList<Neuron> neuronList;

	public NeuronLayer() {
	}

	public void generateRandomLayer(int quantity) {

		try {

			neuronList = new ArrayList<>();
			for (int i = 0; i < quantity; i++) {
				Neuron n = new Neuron();
				n.initRandomize();
				neuronList.add(n);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void connectRandomLayers(ArrayList<Neuron> layer1,
			ArrayList<Neuron> layer2,float probability) {
		
		try {
			
			for(Neuron entryNeuron : layer1){
				for(Neuron outNeuron : layer2){
				
					if(Neuron.getRandomMax(0, 100)<=probability){
						entryNeuron.getNeuronOutList().add(outNeuron);
					}
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<Neuron> getNeuronList() {
		return neuronList;
	}

	public void setNeuronList(ArrayList<Neuron> neuronList) {
		this.neuronList = neuronList;
	}

	public void process() {
		try {
			
			for(Neuron n : neuronList){

				n.evaluateOut();
				n.propagate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void printResult() {
		try {
			
			int i=0;
			for(Neuron n : neuronList){
				System.out.println("Result Neuron "+i+" : "+n.getOut() +" weight "+n.getWeight()+" activation "+n.getActivationVal() );
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
