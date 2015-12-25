package com.mgl.neural;

import java.util.ArrayList;

public class NeuralNetwork {

	private NeuronLayer entryLayer;
	private NeuronLayer outLayer;
	private ArrayList<NeuronLayer> internalLayerList;

	public NeuralNetwork(int entryQuantity, int internalQuantity,
			int internalQuantityDeepth, int outQuantity) {
		try {

			double time = System.currentTimeMillis();
			
			System.out.println("begin neural Network");
			
			System.out.println("init entry layer");
			entryLayer = new NeuronLayer();
			entryLayer.generateRandomLayer(entryQuantity);
			
			System.out.println("init out layer");
			outLayer = new NeuronLayer();
			outLayer.generateRandomLayer(outQuantity);

			System.out.println("init internal layer");
			internalLayerList = new ArrayList<>();
			
			for (int i = 0; i < internalQuantityDeepth; i++) {
				
				NeuronLayer layer = new NeuronLayer();
				layer.generateRandomLayer(internalQuantity);
				internalLayerList.add(layer);
				

			}
			
			//connecting layers
			System.out.println("begin connecting layers...");
			NeuronLayer layer1 = entryLayer;
			
			for(NeuronLayer layer : internalLayerList){
				
				NeuronLayer.connectRandomLayers(layer1.getNeuronList(), layer.getNeuronList(), 100f);
				layer1=layer;
				
			}

			NeuronLayer.connectRandomLayers(layer1.getNeuronList(), outLayer.getNeuronList(), 100f);
			
			double totalTime = System.currentTimeMillis()-time;
			System.out.println(" Neural Network Ready "+totalTime+"millis");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void process(ArrayList<NeuralEntry> neuralEntryList) {
		try {
			int i = 0;
			for(Neuron n: entryLayer.getNeuronList()){
				//System.out.println(" "+i);
				try {
					ArrayList<NeuralEntry> entryL = new ArrayList<>();
					entryL.add(neuralEntryList.get(i));
					n.setEntryList(entryL);
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
				i++;
				
			}
			i=0;
			//System.out.println("processing entry layer");
			entryLayer.process();
			//entryLayer.printResult();
			for(NeuronLayer layer : internalLayerList){
			//	System.out.println("processing internal layer "+i);
				layer.process();
				i++;
			//	layer.printResult();
			}
			
			//System.out.println("processing out layer");
			outLayer.process();
			//outLayer.printResult();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void printNeuralNetwork() {
		try {
			
			for(Neuron n : entryLayer.getNeuronList()){
				System.out.println("Neuron "+n.getWeight());
				n.printConnections();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String getOut (){
		
		String result = new String();
		try {
			
			for (Neuron n : outLayer.getNeuronList()){
				
				result = result + Float.valueOf(n.getOut()).intValue()+"";
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public NeuronLayer getEntryLayer() {
		return entryLayer;
	}

	public void setEntryLayer(NeuronLayer entryLayer) {
		this.entryLayer = entryLayer;
	}

	public NeuronLayer getOutLayer() {
		return outLayer;
	}

	public void setOutLayer(NeuronLayer outLayer) {
		this.outLayer = outLayer;
	}

	public ArrayList<NeuronLayer> getInternalLayerList() {
		return internalLayerList;
	}

	public void setInternalLayerList(ArrayList<NeuronLayer> internalLayerList) {
		this.internalLayerList = internalLayerList;
	}

}
