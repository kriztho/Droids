package com.example.droids;

import com.example.droids.model.Droid;
import com.example.droids.model.components.Speed;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class Droidz extends MainGamePanel implements SurfaceHolder.Callback {
	
	//Tag for logging on Android's Log
	private static final String TAG = Droidz.class.getSimpleName();
	private static final int MAX_NUMBER_DROIDS = 10;
	private static final int FLOATING_INFO_PARAMS = 2;
	
	//private Droid droid;
	private Droid[] droids;
	private int animationSpeedFactor = 1;
	private int currentNumberDroids;
	private int index;
	private Context appContext;

	public Droidz(Context context) {
		super(context);
		
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		appContext = context;
		animationSpeedFactor = 1; 				//Normal speed
		currentNumberDroids = 0;				// Starts out with 0 droids
		index = 0;								// Checks if there is any more droids to create within the maximum allowed
		droids = new Droid[MAX_NUMBER_DROIDS];	//Create droid and load bitmap
		
		//Heads up display
		floatingInfoArray = new String[FLOATING_INFO_PARAMS];
		updateFloatingInfo(0, "Speed", ""+animationSpeedFactor);
		updateFloatingInfo(1, "Droids", ""+currentNumberDroids);
		
		//Starts out 1X
		animationSpeedFactor = 1;
		
		setFocusable(true);
	}
	
	@Override
	 public boolean onTouchEvent(MotionEvent event) {
		 
		 if (event.getAction() == MotionEvent.ACTION_DOWN) {
			 
			 addDroid((int)(event.getX()), (int)(event.getY()));
					 
			 /*
			 //delegating event handling to the droid
			 if ( droid != null ) {
				 droid.handleActionDown((int)event.getX(), (int)event.getY());
			 }
			 */
			 
		 } if (event.getAction() == MotionEvent.ACTION_MOVE ) {
			 //the gestures
			 /*
			 if( droid!= null) {
				 if (droid.isTouched()) {
					 //the droid was picked up and is being dragged
					 droid.setX((int)event.getX());
					 droid.setY((int)event.getY());
				 }
			 }
			 */
			 
		 } if (event.getAction() == MotionEvent.ACTION_UP ){
			 /*
			 if( droid!= null) {
				 //touch was released
				 if (droid.isTouched()) {
					 droid.setTouched(false);
				 }
			 }
			 */
		 }
	  return true;
	 }
	
	 public void render(Canvas canvas) {
		
		 //fills the canvas with black 
		canvas.drawColor(Color.BLACK);
		drawDroids(canvas);
		
		// Display Heads Up Information
		displayFps(canvas, avgFps);
		displayFloatingInfo(canvas);
	 }
	 
	 public void drawDroids(Canvas canvas) {
		 
		 Paint paint = new Paint();
		 paint.setARGB(255, 0, 255, 0);
		 paint.setStyle(Paint.Style.STROKE);
		 canvas.drawRect(frameBox, paint);
		 
		// Drawing all droids in the array
		 for ( int i = 0; i < currentNumberDroids; i++ ) {
			 if (droids[i] != null)
				 droids[i].draw(canvas);
		 }
	 }
	 
	 public void updateDroids(){
		 
		// Updating all droids
		 for ( int i = 0; i < currentNumberDroids; i++ ) {
			 if (droids[i] != null)
				 droids[i].update(frameBox, animationSpeedFactor);
		 }
		 
	 }
	 
	 public void update() {
		 
		 updateDroids();
	 }
	 
	 @Override
	protected void updateFloatingInfo(int infoId, String infoName, String info) {
		
		floatingInfoArray[infoId] = infoName +": "+ info;	
		
		super.updateFloatingInfo(infoId, infoName, info);
	}

	public void makeToast(CharSequence text) {
		Toast toast = Toast.makeText(appContext, text, Toast.LENGTH_SHORT);
		toast.show();
	}
	 
	public int getAnimationSpeedFactor() {
		return animationSpeedFactor;
	}

	public void setAnimationSpeedFactor(int animationSpeedFactor) {
		this.animationSpeedFactor = animationSpeedFactor;
		updateFloatingInfo(0, "Speed", ""+animationSpeedFactor);
	}

	public void multiplyAnimationSpeed() {
		 //Multiply by speed factor all droids speeds
		 for ( int i = 0; i < currentNumberDroids; i++ ) {
			 if (droids[i] != null){		
				 droids[i].multiplySpeed(animationSpeedFactor);
			 }
		 }
	 }
	
	public int getCurrentNumberDroids() {
		return currentNumberDroids;
	}
	
	public void addDroid(int x, int y) {
		
		if ( currentNumberDroids < MAX_NUMBER_DROIDS ) {
			if ( index < MAX_NUMBER_DROIDS ) {
				droids[index] = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1),x, y );
				currentNumberDroids++;
				index++;
				
				updateFloatingInfo(1, "Droids", ""+currentNumberDroids);
				
			} else {				
				droids[currentNumberDroids].setX(x);
				droids[currentNumberDroids].setY(y);
				currentNumberDroids++;
				
				updateFloatingInfo(1, "Droids", ""+currentNumberDroids);
			}
		} else 
			makeToast("Max Number Of Droids Reached");
	}
	
	public void setCurrentNumberDroids(int newNumberDroids) {
			
		// Add Droids
		if ( newNumberDroids > currentNumberDroids ) {
			// Add missing droids
			int missingDroids = newNumberDroids - currentNumberDroids;
			for ( int i = 0; i < missingDroids; i++ ) {
				addDroid(rndInt(50, 270), rndInt(50, 430)); 
			}
			
			//Delete droids
		} else if ( newNumberDroids < currentNumberDroids ) {
			
			// Possible to delete droids
			if ( newNumberDroids >= 0 ) {
				currentNumberDroids = newNumberDroids;
				
				updateFloatingInfo(1, "Droids", ""+currentNumberDroids);
				
			} else {
				//Impossible to delete droids
				makeToast("No More Droids To Delete");
			}
		}
	}
	
	// Return an integer that ranges from min inclusive to max inclusive.
	static int rndInt(int min, int max) {
		return (int) (min + Math.random() * (max - min + 1));
	}

	static double rndDbl(double min, double max) {
		return min + (max - min) * Math.random();
	}
}
