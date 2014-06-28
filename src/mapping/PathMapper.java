package mapping;

import android.app.Activity;

import com.example.breadcrumb.MainActivity;

import data.BatchProcessingResults;


public class PathMapper implements Mapper {

	private MainActivity mainActivity;
	private static MapView mapView;
	
	public PathMapper(MainActivity mainActivity){
		this.mainActivity = mainActivity;
		
		mapView = new MapView(mainActivity);
		
		this.mainActivity.setContentView(mapView);
	}
	
	public static void debug(String str){
		mapView.debug(str);
	}
	
	@Override
	public void plot(Activity mainActivity, BatchProcessingResults results) {
		
		if(results.getDetectedSteps() > 0){
			double deg = results.getHeadingAngle();
			double length = results.getStrideLength();
			int steps = results.getDetectedSteps();
		
			mapView.newPoint(deg, length, steps);
		}
		
//		TextView tvLastStepsDetected = (TextView)mainActivity.findViewById(R.id.tvLastStepsDetected);
//		TextView tvTotalStepsDetected= (TextView)mainActivity.findViewById(R.id.tvTotalStepsDetected);
//		TextView tvLastStrideLength= (TextView)mainActivity.findViewById(R.id.tvLastStrideLength);
//		TextView tvTotalStrideLength= (TextView)mainActivity.findViewById(R.id.tvTotalStrideLength);
//		
//		TextView tvLastHeadingAngle = (TextView)mainActivity.findViewById(R.id.tvLastHeadingAngle);
//		
//		long totalStepsDetected = 0;
//		try{
//			totalStepsDetected = Long.parseLong(tvTotalStepsDetected.getText().toString());
//		}catch(Exception e){}
//		
//		double totalStrideLength = 0;
//		try{
//			totalStrideLength = Double.parseDouble(tvTotalStrideLength.getText().toString());
//		}catch(Exception e){}
//		
//		
//		totalStepsDetected += results.getDetectedSteps();
//		totalStrideLength += results.getStrideLength();
//		
//		if(results.getDetectedSteps() > 0)
//			tvLastStepsDetected.setText(""+results.getDetectedSteps());
//		if(results.getStrideLength() > 0)
//			tvLastStrideLength.setText(""+String.format("%.2f",results.getStrideLength()));
//		if(results.getHeadingAngle() != 0)
//			tvLastHeadingAngle.setText(""+String.format("%.2f",results.getHeadingAngle()));
//		
//		tvTotalStepsDetected.setText(""+totalStepsDetected);
//		tvTotalStrideLength.setText(""+String.format("%.2f",totalStrideLength));
	}   
}