package ins;

import headingdetermination.HeadingDeterminer;
import headingdetermination.SimpleHeadingDeterminer;

import java.util.ArrayList;

import stepdetection.PeakThresholdStepDetector;
import stepdetection.StepDetector;
import stridelengthestimation.LinearStrideLengthEstimator;
import stridelengthestimation.StrideLengthEstimator;
import data.BatchProcessingResults;
import data.DetectedEntry;
import data.SensorEntry;

public class INSController {

	//Plan to make all these three interfaces so we can easily switch techniques. Just not sure yet what parameters are needed.
	private StepDetector stepDetector;
	private StrideLengthEstimator strideLengthEstimator;
	private HeadingDeterminer headingDeterminer;
	
	
	public INSController(){
		this.stepDetector = new PeakThresholdStepDetector();
		this.strideLengthEstimator = new LinearStrideLengthEstimator();
		this.headingDeterminer = new SimpleHeadingDeterminer();
	}
	
	public BatchProcessingResults processSensorEntryBatch(ArrayList<SensorEntry> batch){
		
		//Step detection
		ArrayList<DetectedEntry> detectedSteps = stepDetector.detectSteps(batch);
		
		//Stride length estimation
		double strideLength = strideLengthEstimator.estimateLength(detectedSteps);
		
		//Heading determination
		double headingAngle = headingDeterminer.getHeading(batch);
		
		//return results
		BatchProcessingResults results = new BatchProcessingResults();
		results.setDetectedSteps(detectedSteps.size());
		results.setStrideLength(strideLength);
		results.setHeadingAngle(headingAngle);
		
		return results;
	}
	
	
}
