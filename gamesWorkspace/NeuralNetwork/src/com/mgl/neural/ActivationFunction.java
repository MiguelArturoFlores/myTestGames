package com.mgl.neural;

public class ActivationFunction {

	public ActivationFunction(){}
	
	
	public float executeFunction(float entry, float activationVal){
		try {
			
			if(entry>=activationVal){
				return 1;
			}
			
			return 0;
			
			
		} catch (Exception e) {
			
		}
		return 0;
	}
	
}
