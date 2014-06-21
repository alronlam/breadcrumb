package headingdetermination;

import java.util.ArrayList;

import data.SensorEntry;

public class SimpleHeadingDeterminer implements HeadingDeterminer{

	public double getHeading(ArrayList<SensorEntry> batch){
		double avg = calculateAvg(batch);
		return getNearestDiscreteHeading(avg);
	}
	
	//Might consider improving this by increasing the number of possible discrete angle values
	private double getNearestDiscreteHeading(double average){
		if( average > 22.5 && average <= 67.5 ){
	        return 45;
	    }
	    else if ( average > 67.5 && average <= 112.5 ){
	        return 90;
	    }
	    else if ( average > 112.5 && average <= 157.5 ){
	        return 135;
	    }
	    else if ( average > 157.5 && average <= 202.5 ){
	        return 180;
	    }
	    else if ( average > 202.5 && average <= 247.5 ){
	        return 225;
	    }
	    else if ( average > 247.5 && average <= 292.5 ){
	        return 270;
	    }
	    else if ( average > 292.5 && average <= 337.5 ){
	        return 315;
	    }
	    
	    return 0;
	}
	
	private double calculateAvg(ArrayList<SensorEntry> batch){
		if(batch.size() == 0)
			return 0;
		
		double avg = 0;
		for(SensorEntry entry: batch){
			avg += entry.getOrient_y() + 180;
		}
		
		return avg / batch.size();
	}
	
	
	
}
