package data;

import java.util.ArrayList;

public class BatchProcessingResults {

	private int detectedSteps;
	private double strideLength;
	
	public void setDetectedSteps(int detectedSteps){
		this.detectedSteps = detectedSteps;
	}
	
	public void setStrideLength(double strideLength){
		this.strideLength = strideLength;
	}
	
	public int getDetectedSteps(){
		return  detectedSteps;
	}
	
	public double getStrideLength(){
		return strideLength;
	}
	
}
