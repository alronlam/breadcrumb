package com.example.breadcrumb;

import ins.INSController;

import java.util.ArrayList;

import mapping.Mapper;
import mapping.TextMapper;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import data.BatchProcessingResults;
import data.SensorEntry;

public class MainActivity extends Activity implements SensorEventListener {

	//Sensor variables
	private SensorManager senSensorManager;
	private Sensor senAccelerometer;
	
	//Time-related variables
	private long hz = 100;
	private long timeStampOfLastStepDetection = 0;
	private long timeStampOfLastSensorEntry = 0;
	private long sensorEntryTimeInterval = 1000/hz * 1000; //nano seconds
	private long stepDetectorTimeInterval = 1000000;
	
	//INS related
	private INSController insController;
	private Mapper mapper;
	private ArrayList<SensorEntry> sensorEntryBatch;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        
        sensorEntryBatch = new ArrayList<SensorEntry>();
        insController = new INSController();
        mapper = new TextMapper();
    }
    
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

	@Override
	public void onAccuracyChanged(Sensor sensor, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		// TODO Auto-generated method stub
		Sensor mySensor = sensorEvent.sensor;
		 
	    if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	    	
	    	//add to the sensor entry batch if time to add
	    	
	    	long currNanoTime = System.nanoTime();
	    	
	    	if(currNanoTime - this.timeStampOfLastSensorEntry >= this.sensorEntryTimeInterval){
	    		
	    		float x = sensorEvent.values[0];
		        float y = sensorEvent.values[1];
		        float z = sensorEvent.values[2];
		        
		        SensorEntry currEntry = new SensorEntry();
		        currEntry.setTimeRecorded(currNanoTime);
		        currEntry.setAcc_x(x);
		        currEntry.setAcc_y(y);
		        currEntry.setAcc_z(z);
		        
		        this.sensorEntryBatch.add(currEntry);

	    		this.timeStampOfLastSensorEntry = currNanoTime;
	    	}
	    	
	    	
	    	if(System.nanoTime() - this.timeStampOfLastStepDetection >= this.stepDetectorTimeInterval){
	    		/*
		    	 * For now, the sensor entries are being processed by the INS.
		    	 * In the future, insert the phone mode detection here and just switch to INS or V-INS accordingly.
		    	 */
	    		BatchProcessingResults results = this.insController.processSensorEntryBatch(this.sensorEntryBatch);
	    		
	    		//feed the results to the mapping module
	    		mapper.plot(this, results);
	    		
	    		//clear the processed entries for the new batch
	    		this.sensorEntryBatch.clear(); 
	    	}
	    }
	}
}
