package ins;

import headingdetermination.HeadingDeterminer;
import headingdetermination.SimpleHeadingDeterminer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import stepdetection.PeakThresholdStepDetector;
import stepdetection.PredictionModule;
import stepdetection.StepDetector;
import stepdetection.SummarizedEntry;
import stridelengthestimation.LinearStrideLengthEstimator;
import stridelengthestimation.StrideLengthEstimator;
import android.os.Environment;

import com.example.breadcrumb.MainActivity;

import data.BatchProcessingResults;
import data.DetectedEntry;
import data.EntryInstance;
import data.SensorEntry;

public class INSController {

	//Plan to make all these three interfaces so we can easily switch techniques. Just not sure yet what parameters are needed.
	private StepDetector stepDetector;
	private StrideLengthEstimator strideLengthEstimator;
	private HeadingDeterminer headingDeterminer;
	private PredictionModule predictionModule;
	private MainActivity mainActivity;

	//just for logging
	private String root = Environment.getExternalStorageDirectory().toString();
    private File fileSummarized = new File(root + "/accelerometer_record/se_logs.csv");

	
	public INSController(MainActivity mainActivity){
		this.mainActivity = mainActivity;
		predictionModule = new PredictionModule(mainActivity.getAssets());
		this.stepDetector = new PeakThresholdStepDetector();
		this.strideLengthEstimator = new LinearStrideLengthEstimator();
		this.headingDeterminer = new SimpleHeadingDeterminer();
		
	}
	
	public BatchProcessingResults processSensorEntryBatch(ArrayList<SensorEntry> batch){
		
		logEntries(batch);
		
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
		
	private void logEntries(ArrayList<SensorEntry> batch){
		try {
			
			FileOutputStream outputStream = new FileOutputStream(fileSummarized.getPath());	
			
			if(!fileSummarized.exists())
				outputStream.write("Acc_x,Acc_y,Acc_z,Gyro_x,Gyro_y,Gyro_z,Orient_x,Orient_y,Orient_z,Time\n".getBytes());
			
			for(SensorEntry e: batch)
				outputStream.write((e.toRawString()+","+e.getTimeRecorded()+"\n").getBytes());
			
			outputStream.close();
			
		} catch (Exception e) {
		  e.printStackTrace();
		}
	}
}
