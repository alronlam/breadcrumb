package ins;

import headingdetermination.HeadingDeterminer;
import headingdetermination.SimpleHeadingDeterminer;

import java.util.ArrayList;
import java.util.LinkedList;

import stepdetection.PeakThresholdStepDetector;
import stepdetection.PredictionModule;
import stepdetection.StepDetector;
import stridelengthestimation.LinearStrideLengthEstimator;
import stridelengthestimation.StrideLengthEstimator;
import android.util.Log;
import data.BatchProcessingResults;
import data.DetectedEntry;
import data.SensorEntry;

public class INSController {

	//Plan to make all these three interfaces so we can easily switch techniques. Just not sure yet what parameters are needed.
	private StepDetector stepDetector;
	private StrideLengthEstimator strideLengthEstimator;
	private HeadingDeterminer headingDeterminer;
	private PredictionModule predictionModule;
	
	public INSController(){
		predictionModule = new PredictionModule();
		this.stepDetector = new PeakThresholdStepDetector();
		this.strideLengthEstimator = new LinearStrideLengthEstimator();
		this.headingDeterminer = new SimpleHeadingDeterminer();
	}
	
	public BatchProcessingResults processSensorEntryBatch(ArrayList<SensorEntry> batch){
		
		BatchProcessingResults results = new BatchProcessingResults();
		
		//Negative Locomotion Classifier
		if(predictionModule.predict(predictionModule.summarizeEntries(new LinkedList<SensorEntry>(batch)))){
			//Step detection
			ArrayList<DetectedEntry> detectedSteps = stepDetector.detectSteps(batch);
			
			//Stride length estimation
			double strideLength = strideLengthEstimator.estimateLength(detectedSteps);
			
			//Heading determination
			double headingAngle = headingDeterminer.getHeading(batch);
			
			//return results
			
			results.setDetectedSteps(detectedSteps.size());
			results.setStrideLength(strideLength);
			results.setHeadingAngle(headingAngle);
			
			
		}
		
		
		//Log.d("detected steps", detectedSteps.size()+"");
//		Log.d("stride length", results.getStrideLength()+"");
//		Log.d("heading angle", results.getHeadingAngle()+"");
//		
		return results;
	}
		
}
