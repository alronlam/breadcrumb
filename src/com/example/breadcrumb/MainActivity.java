package com.example.breadcrumb;

import ins.INSController;

import java.util.ArrayList;

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
	    	
	    	// run the batch through the INS
	    	if(System.nanoTime() - this.timeStampOfLastStepDetection >= this.stepDetectorTimeInterval){
	    		//change this in the future so the results will come from v-ins controller when the system is in hand mode
	    		BatchProcessingResults results = this.insController.processSensorEntryBatch(this.sensorEntryBatch);
	    		
	    		//feed the results to the mapping module
	    		
	    		//temporary implementation
	    		TextView tvTotalStepsDetected= (TextView)findViewById(R.id.tvTotalStepsDetected);
	    		TextView tvLastStepsDetected = (TextView)findViewById(R.id.tvLastStepsDetected);
	    		TextView tvLastStrideLength= (TextView)findViewById(R.id.tvLastStrideLength);
	    		TextView tvTotalStrideLength= (TextView)findViewById(R.id.tvTotalStrideLength);
	    		
	    		long totalStepsDetected = 0;
	    		try{
	    			totalStepsDetected = Long.parseLong(tvTotalStepsDetected.getText().toString());
	    		}catch(Exception e){}
	    		
	    		double totalStrideLength = 0;
	    		try{
	    			totalStrideLength = Double.parseDouble(tvTotalStrideLength.getText().toString());
	    		}catch(Exception e){}
	    		
	    		
	    		totalStepsDetected += results.getDetectedSteps();
	    		totalStrideLength += results.getStrideLength();
	    		
	    		if(results.getDetectedSteps() > 0)
	    			tvLastStepsDetected.setText(""+results.getDetectedSteps());
	    		if(results.getStrideLength() > 0)
	    			tvLastStrideLength.setText(""+results.getStrideLength());
	    		
	    		tvTotalStepsDetected.setText(""+totalStepsDetected);
	    		tvTotalStrideLength.setText(""+totalStrideLength);
	    		
//	    		Log.d("Total Steps", ""+totalStepsDetected);
//	    		Log.d("Total Stride Length", ""+totalStrideLength);
//	    		
	    		
	    		this.sensorEntryBatch.clear(); //clear the processed entries for the new batch
	    	}
	    }
	}
}
