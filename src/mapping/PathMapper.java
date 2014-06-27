package mapping;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

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
		
			mapView.newPoint(deg, length, results.getDetectedSteps());
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
	
	public class MapView extends View {
    	
    	Path path;
    	final int pM = 100; // how many pixels per meter
    	float scale = 1f;
    	RectF pathBounds;
    	
    	float pX, pY;
        static final float tol = 250;
        float offsetX = 0;
        float offsetY = 0;
        RectF basePathBounds = null;
        
        String debugText;
        ScaleGestureDetector sDetector;
        
        double totalSteps;
        double totalLength;
        double heading;
        
    	
        public void debug(String txt){
        	this.debugText = txt;
        	this.invalidate();
        }
        
        public void newPoint(double deg, double length, int detectedSteps){

        	double newX = Math.sin(Math.toRadians(deg)) * length * pM * scale;
        	double newY = Math.cos(Math.toRadians(deg)) * length * pM * scale;
        	
        	newY *= -1;
        	
        	path.rLineTo((float)( newX ), (float)( newY));
        	path.offset((float)-newX, (float)-newY);
        	
        	totalSteps += detectedSteps;
        	totalLength += length;
        	heading = deg;
        	
        	invalidate();
        }
        
        public MapView(Context context) {
             super(context);
             
             // get screen size
             Display display = mainActivity.getWindowManager().getDefaultDisplay();
             int width = display.getWidth();
             int height = display.getHeight();
             
             // initialize path
             path = new Path();
             path.moveTo(width * .5f, height * .75f);
             debugText = width + " x " + height;
             
             // sample data
             /*
             path.rLineTo(1 * pM , -2 * pM);
             path.rLineTo(-2 * pM , -1 * pM);
             path.rLineTo(2.40f * pM , -1.55f * pM);
             path.rLineTo(-2.5f * pM , -1.75f * pM);
             path.rLineTo(.2f * pM , -.2f * pM);
             */
             /*path.rLineTo(1*pM, 0);
             path.rLineTo(0,-4*pM);
             path.rLineTo(-2*pM,0);
             path.rLineTo(0,4*pM);
             path.rLineTo(1*pM, 0);*/
             
             // path.rLineTo(Math.sin(deg) * length * pM * headX * scale, Math.cos(deg) * length * pM * headY * scale)\
             
             sDetector = new ScaleGestureDetector(context, new ScaleListener());
        }
        
        @Override
        public boolean onTouchEvent(MotionEvent event) {
        	super.onTouchEvent(event);
        	
        	float currX = event.getX();
            float currY = event.getY();
        	
        	switch(event.getAction()){
        		case MotionEvent.ACTION_DOWN:
        			 pX = currX;
        	         pY = currY;
        	         break;
        		
        		case MotionEvent.ACTION_MOVE:
        			float dx = Math.abs(currX - pX);
                    float dy = Math.abs(currY - pY);
                    if (dx < tol && dy < tol) {
                        if(event.getPointerCount() == 1)
                        	path.offset(currX - pX, currY - pY);
                        pX = currX;
                        pY = currY;
                    }
                    break;
                    
        		case MotionEvent.ACTION_UP:
                    break;
        	}
        	
        	sDetector.onTouchEvent(event);
        		
        	invalidate();
        	return true;
        }
        
        @Override
        protected void onDraw(Canvas canvas) {
           super.onDraw(canvas);
           
           Paint paint = new Paint();
           
           // Set backgroud color
           paint.setStyle(Paint.Style.FILL);
           paint.setColor(Color.WHITE);
           canvas.drawPaint(paint);
           
           
           // write information
           paint.setColor(Color.BLACK);
           paint.setTextAlign(Align.LEFT);
           paint.setTextSize(10);
           
           pathBounds = new RectF();
           path.computeBounds(pathBounds, true);
           if(this.basePathBounds == null){
        	   basePathBounds = new RectF();
        	   path.computeBounds(basePathBounds, true);
           }
           
           //canvas.drawText("Screen Size: " + getWidth() + " x " + getHeight() , 50, 50, paint);
           //canvas.drawText("Path Size: " + pathBounds.width() + " x " + pathBounds.height(), 50, 110, paint);
           canvas.drawText("Path Position: " + pathBounds.centerX() + " x " + pathBounds.centerY(), 10, 10, paint);
           canvas.drawText("Path Scale: " + (scale * 100) + "%", 10, 20, paint);
           canvas.drawText(debugText, 10, 30, paint);
           canvas.drawText("Heading: " + this.heading, 10, 40, paint);
           canvas.drawText("Length: " + this.totalLength, 10, 50, paint);
           canvas.drawText("Steps: " + this.totalSteps, 10, 60, paint);
           
           // draw path
           paint.setStyle(Paint.Style.STROKE);
           paint.setStrokeWidth(5);
           canvas.drawPath(path, paint);
           
           
           // reference lines
           paint.setStyle(Paint.Style.FILL);
           paint.setStrokeWidth(3);
           paint.setTextSize(20);
           
           canvas.drawText("1 meter" , 10, getHeight() - 140, paint);
           canvas.drawLine(50, getHeight() - 120, 50 + (1 * pM * scale), getHeight() - 120, paint);

           canvas.drawText("5 meters" , 10, getHeight() - 70, paint);
           canvas.drawLine(50, getHeight() - 50, 50 + (5 * pM * scale), getHeight() - 50, paint);
           
           //debugText = this.totalLength + " " + this.heading + " \n " + this.totalLength;
       }
        
       public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
    	   @Override
    	   public boolean onScale(ScaleGestureDetector detector){
	
    		   Matrix mat = new Matrix();
    		   float prevScale = scale; 
    		   
    		   // update scale
    		   scale *= detector.getScaleFactor();
    		   scale = Math.max(.1f, Math.min(2f, scale));
    		   
    		   // move center of path
    		   //path.computeBounds(pathBounds, true);
    		   //path.offset(Math.abs(pathBounds.width()) * (prevScale - scale), Math.abs(pathBounds.height()) * (prevScale - scale));
    		   //path.offset(Math.abs(pathBounds.width()) * 1/scale, Math.abs(pathBounds.height()) * 1/scale);
    		   //path.offset(Math.abs(pathBounds.width()) * (scale) * (), Math.abs(pathBounds.height()) * (scale));
    		   
    		   path.offset(offsetX, offsetY);
    		   offsetX += Math.abs(basePathBounds.centerX()) * -(prevScale - scale);
    		   offsetY += Math.abs(basePathBounds.centerY()) * -(prevScale - scale);
    		   
    		   //debugText = "offset: " + offsetX + " x " + offsetY;
    		   
    		   //path.offset(Math.abs(basePathBounds.centerX()) * -(prevScale - scale), Math.abs(basePathBounds.centerY()) * -(prevScale - scale));
    		   
    		   
    		   // return to original
    		   mat.setScale(1/prevScale, 1/prevScale);
    		   path.transform(mat);
    		   
    		   // scale to new value
    		   mat.setScale(scale, scale);
    		   path.transform(mat);
    		   path.offset(-offsetX, -offsetY);
  
    		   //debugText = "" + detector.getScaleFactor();
    		   return true;
    	   }
       }
    }
    
}
