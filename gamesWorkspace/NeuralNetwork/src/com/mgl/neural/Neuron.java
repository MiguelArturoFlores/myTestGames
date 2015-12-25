package com.mgl.neural;

import java.util.ArrayList;

public class Neuron {

	private ArrayList<NeuralEntry> entryList;
	private ArrayList<Neuron> neuronOutList;
	private float weight = 10;
	private float out = 0f;
	private float activationVal = 3f;
	private ActivationFunction activationFunction;

	public Neuron(){
		this.entryList = new ArrayList<>();
		activationFunction = new ActivationFunction();
		neuronOutList = new ArrayList<>();
		activationVal = getRandomMax(0, 1);
	}
	
	public Neuron(float weight, float activationVal) {
		super();
		this.entryList = new ArrayList<>();
		activationFunction = new ActivationFunction();
		this.weight = weight;
		this.activationVal = activationVal;
		neuronOutList = new ArrayList<>();
		activationVal = getRandomMax(0, 1);
	}
	
	public void initRandomize(){
		try {
			
			this.entryList = new ArrayList<>();
			activationFunction = new ActivationFunction();
			neuronOutList = new ArrayList<>();
			
			weight = getRandomMax(0, 1)-0.5f;
			//activationVal = 0;//getRandomMax(0, 1000)/1000f;
			activationVal = getRandomMax(0, 1)-0.5f;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void evaluateOut(){
		try {
			
			float sum = 0;
			for(NeuralEntry e : getEntryList()){
				sum+=e.getEntryConverted();
			}
			//System.out.println("process val0 "+sum);
			//sum=sum*weight;
			//System.out.println("process val1 "+sum);
			
			out = activationFunction.executeFunction(sum,activationVal);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<NeuralEntry> getEntryList() {
		return entryList;
	}

	public void setEntryList(ArrayList<NeuralEntry> entryList) {
		this.entryList = entryList;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getOut() {
		return out;
	}

	public void setOut(float out) {
		this.out = out;
	}

	public float getActivationVal() {
		return activationVal;
	}

	public void setActivationVal(float activationVal) {
		this.activationVal = activationVal;
	}

	
	public static float getRandomMax(int min, int max) {

		try {

			Double val = min + ((Math.random() * 123456323) % ( max - min));
			return val.floatValue();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return 1;
	}

	public ArrayList<Neuron> getNeuronOutList() {
		return neuronOutList;
	}

	public void setNeuronOutList(ArrayList<Neuron> neuronOutList) {
		this.neuronOutList = neuronOutList;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public void setActivationFunction(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
	}

	public void propagate() {
		try {
			
			for(Neuron n : neuronOutList){
				n.clearEntryList();
				n.getEntryList().add(new NeuralEntry(out*weight, out*weight));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void clearEntryList() {
		entryList = new ArrayList<>();
		
	}

	public void printConnections() {
		try {
			for(Neuron n : neuronOutList){
				System.out.println("N "+n.getWeight());
				n.printConnections();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
