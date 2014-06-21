package data;

import java.util.ArrayList;

/*
 * This class is used to represent the results of an INS(or V-INS) after running a batch of SensorEntry objects through it.
 */
public class BatchProcessingResults {

	private int detectedSteps;
	private double strideLength; //meters
	private double headingAngle; //degrees
	
	public void setDetectedSteps(int detectedSteps){
		this.detectedSteps = detectedSteps;
	}
	
	public void setStrideLength(double strideLength){
		this.strideLength = strideLength;
	}
	
	public void setHeadingAngle(double headingAngle) {
		this.headingAngle = headingAngle;
	}
	
	public int getDetectedSteps(){
		return  detectedSteps;
	}
	
	public double getStrideLength(){
		return strideLength;
	}
	
	public double getHeadingAngle() {
		return headingAngle;
	}


	
}
