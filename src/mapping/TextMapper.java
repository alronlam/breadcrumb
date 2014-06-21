package mapping;

import com.example.breadcrumb.R;

import android.app.Activity;
import android.widget.TextView;
import data.BatchProcessingResults;

public class TextMapper implements Mapper {

	@Override
	public void plot(Activity mainActivity, BatchProcessingResults results) {
		TextView tvLastStepsDetected = (TextView)mainActivity.findViewById(R.id.tvLastStepsDetected);
		TextView tvTotalStepsDetected= (TextView)mainActivity.findViewById(R.id.tvTotalStepsDetected);
		TextView tvLastStrideLength= (TextView)mainActivity.findViewById(R.id.tvLastStrideLength);
		TextView tvTotalStrideLength= (TextView)mainActivity.findViewById(R.id.tvTotalStrideLength);
		
		TextView tvLastHeadingAngle = (TextView)mainActivity.findViewById(R.id.tvLastHeadingAngle);
		
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
			tvLastStrideLength.setText(""+String.format("%.2f",results.getStrideLength()));
		if(results.getHeadingAngle() != 0)
			tvLastHeadingAngle.setText(""+String.format("%.2f",results.getHeadingAngle()));
		
		tvTotalStepsDetected.setText(""+totalStepsDetected);
		tvTotalStrideLength.setText(""+String.format("%.2f",totalStrideLength));

	}

}
