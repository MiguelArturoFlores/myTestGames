package com.mgl.neural;

public class NeuralEntry {

	private Object entry;
	private float entryConverted;
	
	public NeuralEntry(Object entry, float entryConverted) {
		super();
		this.entry = entry;
		this.entryConverted = entryConverted;
	}
	
	public Object getEntry() {
		return entry;
	}
	public void setEntry(Object entry) {
		this.entry = entry;
	}
	public float getEntryConverted() {
		return entryConverted;
	}
	public void setEntryConverted(float entryConverted) {
		this.entryConverted = entryConverted;
	}
	
	
	
}
