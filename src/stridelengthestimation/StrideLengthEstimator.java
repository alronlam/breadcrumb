package stridelengthestimation;

import java.util.ArrayList;

import data.DetectedEntry;

public interface StrideLengthEstimator {
	public double estimateLength(ArrayList<DetectedEntry> detectedSteps);
	
}
