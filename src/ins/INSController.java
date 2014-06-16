package ins;

import java.util.ArrayList;

import android.util.Log;
import stepdetection.StepDetector;
import stridelengthestimation.StrideLengthEstimator;
import data.BatchProcessingResults;
import data.DetectedEntry;
import data.SensorEntry;

public class INSController {

	
	private StepDetector stepDetector;
	private StrideLengthEstimator strideLengthEstimator;
	
	
	public INSController(){
		this.stepDetector = new StepDetector();
		this.strideLengthEstimator = new StrideLengthEstimator();
	}
	
	public BatchProcessingResults processSensorEntryBatch(ArrayList<SensorEntry> batch){
		
		//run through step detection
		ArrayList<DetectedEntry> detectedSteps = stepDetector.detectSteps(batch);
		//if true, then estimate stride length
		
		double stepRate = 1.0*detectedSteps.size()/ 1000; //ms 
		double strideLength = strideLengthEstimator.estimateLength(stepRate);
		
		//determine heading
		
		
		//return results
		BatchProcessingResults results = new BatchProcessingResults();
		results.setDetectedSteps(detectedSteps.size());
		results.setStrideLength(strideLength);
//		Log.d("Detected Steps", ""+results.getDetectedSteps());
//		Log.d("Stride Length", ""+results.getStrideLength());
		return results;
	}
	
	
}
