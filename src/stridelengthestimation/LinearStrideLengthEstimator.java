/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stridelengthestimation;

import java.util.ArrayList;

import data.DetectedEntry;

/**
 *
 * @author Chalsy
 */

/*
 * estimates step length based on a*stepRate + b.
 * where a and b are thresholds for a certain user?
 */
public class LinearStrideLengthEstimator implements StrideLengthEstimator{

    public final double a = -0.0273;
    public final double b = 0.7014;

    /*
     * stepRate : step rate per second
     */
    
    public double estimateLength(ArrayList<DetectedEntry> detectedSteps){
    	double stepRate = 1.0*detectedSteps.size()/ 1000; 
    	return estimateLength(stepRate);
    }
    
    public double estimateLength ( double stepRate ){
    	if(stepRate == 0)
    		return 0;
        return ( a * stepRate ) + b;
    }
    
    public double estimateLength ( double stepRate, double ta, double tb ){
    	if(stepRate == 0)
    		return 0;
        return ( ta * stepRate ) + tb;
    }
}
