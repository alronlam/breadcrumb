/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Chalsy
 */
public class DetectedEntry {
    private long timeRecorded; // not seconds
    private double magnitude;
    
    public DetectedEntry(long timeRecorded, double magnitude){
    	this.timeRecorded = timeRecorded;
    	this.magnitude = magnitude;
    }
    
    public long getTimeRecorded() {
        return timeRecorded;
    }

    public void setTimeRecorded(long timeRecorded) {
        this.timeRecorded = timeRecorded;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
}
