package mapping;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.breadcrumb.MainActivity;


public class MapView extends View {
	
	Path path;
	final int pM = 100; // how many pixels per meter
	float scale = 1f;
	RectF pathBounds;
	
	Canvas can;
	float pX, pY;
    static final float tol = 250;
    float offsetX = 0;
    float offsetY = 0;
    RectF basePathBounds = null;
    
    int val = 0;
    String debugText = "";
    ScaleGestureDetector sDetector;
    
    double totalSteps;
    double totalLength;
    double heading;
    
    float originX;
    float originY;
    
    float currX;
    float currY;
    
    float prevX;
    float prevY;
	
    float centerX;
    float centerY;
    
    public void debug(String txt){
    	this.debugText = txt;
    	this.invalidate();
    }
    
    public void offsetPath(float x, float y){
    	originX += x;
    	originY += y;
    	
    	currX += x;
    	currY += y;
    	
    	path.offset(x, y);
    }
    
    public void newPoint(double deg, double length, int detectedSteps){

    	double newX = Math.sin(Math.toRadians(deg)) * length * pM * scale;
    	double newY = Math.cos(Math.toRadians(deg)) * length * pM * scale;

    	newY *= -1;
    	
    	currX += newX;
    	currY += newY;
    	
    	path.rLineTo((float)( newX ), (float)( newY));
    	offsetPath((float)-newX, (float)-newY);
    	
    	
    	totalSteps += detectedSteps;
    	totalLength += length;
    	heading = deg;
    	
    	invalidate();
    }
    
    public MapView(Context context) {
         super(context);
         
         // get screen size
         Display display = ((MainActivity)context).getWindowManager().getDefaultDisplay();
         int width = display.getWidth();
         int height = display.getHeight();
         
         centerX = width/2;
         centerY = height/2;
         
         // initialize path
         originX = width * .5f;
         originY = height * .75f;
         
         currX = originX;
         currY = originY;
         
         path = new Path();
         path.moveTo(originX, originY);
         
         //debugText = width + " x " + height;
         
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
                    	offsetPath(currX - pX, currY - pY);
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
       paint.setTextSize(50);
       
       pathBounds = new RectF();
       path.computeBounds(pathBounds, true);
       if(this.basePathBounds == null){
    	   basePathBounds = new RectF();
    	   path.computeBounds(basePathBounds, true);
       }
       //canvas.drawText("Screen Size: " + getWidth() + " x " + getHeight() , 50, 50, paint);
       //canvas.drawText("Path Size: " + pathBounds.width() + " x " + pathBounds.height(), 50, 110, paint);
       canvas.drawText("Path Position: " + pathBounds.centerX() + " x " + pathBounds.centerY(), 50, 50, paint);
       canvas.drawText("Path Scale: " + (scale * 100) + "%", 50, 110, paint);
       canvas.drawText("Heading: " + this.heading, 50, 170, paint);
       canvas.drawText("Length: " + this.totalLength, 50, 240, paint);
       canvas.drawText("Steps: " + this.totalSteps, 50, 310, paint);
       canvas.drawText(debugText, 50, 390, paint);
       
       //canvas.rotate(val,centerX,centerY);
       
       // draw path
       paint.setStyle(Paint.Style.STROKE);
       paint.setStrokeWidth(5);
       canvas.drawPath(path, paint);
       
       paint.setStyle(Paint.Style.FILL);
       paint.setStrokeWidth(3);
       paint.setTextSize(30);
       
       paint.setColor(Color.rgb(64, 128, 192));
       canvas.drawCircle(originX, originY, 25, paint);
       paint.setColor(Color.rgb(64, 128, 32));
       
       int saveCount = -1;
       if(pathBounds.width() > 0){
    	   saveCount = canvas.save();
    	   canvas.rotate((float)heading,currX,currY);
    	   Path marker = new Path();
    	   marker.moveTo(currX, currY);
    	   marker.lineTo(currX-pM/3, currY+pM/3);
    	   marker.lineTo(currX, currY-pM/4);
    	   marker.lineTo(currX+pM/3, currY+pM/3);
    	   marker.lineTo(currX, currY);
    	   marker.close();

    	   canvas.drawPath(marker, paint);
    	   
    	   canvas.restore();
    	   
    	   paint.setColor(Color.BLACK);
       }
       canvas.drawText("debug: " + saveCount, 50, 460, paint);
       
       
       // reference lines
       
       canvas.drawText("1 meter" , 50, getHeight() - 140, paint);
       canvas.drawLine(50, getHeight() - 120, 50 + (1 * pM * scale), getHeight() - 120, paint);

       canvas.drawText("5 meters" , 50, getHeight() - 70, paint);
       canvas.drawLine(50, getHeight() - 50, 50 + (5 * pM * scale), getHeight() - 50, paint);
       
       can = canvas;
       //debugText = this.totalLength + " " + this.heading + " \n " + this.totalLength;
   }
    
   public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
	   @Override
	   public boolean onScale(ScaleGestureDetector detector){
		   //val+=5;
		   
		   return true;
		   /*
		   Matrix mat = new Matrix();
		   float prevScale = scale; 
		   
		   // update scale
		   scale *= detector.getScaleFactor();
		   scale = Math.max(.1f, Math.min(2f, scale));
		   
		   // move center of path
		   //path.computeBounds(pathBounds, true);
		   //offsetPath(Math.abs(pathBounds.width()) * (prevScale - scale), Math.abs(pathBounds.height()) * (prevScale - scale));
		   //offsetPath(Math.abs(pathBounds.width()) * 1/scale, Math.abs(pathBounds.height()) * 1/scale);
		   //offsetPath(Math.abs(pathBounds.width()) * (scale) * (), Math.abs(pathBounds.height()) * (scale));
		   
		   offsetPath(-offsetX, -offsetY);
		   offsetX += Math.abs(basePathBounds.centerX()) * -(prevScale - scale);
		   offsetY += Math.abs(basePathBounds.centerY()) * -(prevScale - scale);
		   
		   //debugText = "offset: " + offsetX + " x " + offsetY;
		   
		   //offsetPath(Math.abs(basePathBounds.centerX()) * -(prevScale - scale), Math.abs(basePathBounds.centerY()) * -(prevScale - scale));
		   
		   
		   // return to original
		   mat.setScale(1/prevScale, 1/prevScale);
		   path.transform(mat);
		   
		   // scale to new value
		   mat.setScale(scale, scale);
		   path.transform(mat);
		   offsetPath(offsetX, offsetY);

		   //debugText = "" + detector.getScaleFactor();
		   return true;
		   */
	   }
   }
}