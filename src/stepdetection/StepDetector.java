package stepdetection;

import java.util.ArrayList;

import data.DetectedEntry;
import data.SensorEntry;

public interface StepDetector {
	 public ArrayList<DetectedEntry> detectSteps( ArrayList<SensorEntry> batch );
}
