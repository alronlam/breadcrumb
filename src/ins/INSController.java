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

import com.example.breadcrumb.MainActivity;

import data.BatchProcessingResults;
import data.DetectedEntry;
import data.SensorEntry;

public class INSController {

	//Plan to make all these three interfaces so we can easily switch techniques. Just not sure yet what parameters are needed.
	private StepDetector stepDetector;
	private StrideLengthEstimator strideLengthEstimator;
	private HeadingDeterminer headingDeterminer;
	private PredictionModule predictionModule;
	private MainActivity mainActivity;
	
	public INSController(MainActivity mainActivity){
		this.mainActivity = mainActivity;
		predictionModule = new PredictionModule(mainActivity.getAssets());
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

		return results;
	}
		
}
