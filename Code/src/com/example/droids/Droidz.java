package com.example.droids;

import com.example.droids.model.Droid;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class Droidz extends MainGamePanel implements SurfaceHolder.Callback {
	
	//Tag for logging on Android's Log
	private static final String TAG = Droidz.class.getSimpleName();
	private static final int MAX_NUMBER_DROIDS = 30;
	
	private Droid[] droids;
	private int animationSpeedFactor;
	private int currentNumberDroids;
	private int index;
	private FloatingDisplay floatingDisplay;

	public Droidz(Context context) {
		super(context);
		
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		//Set context
		appContext = context;
		animationSpeedFactor = 1; 				//Normal speed
		currentNumberDroids = 0;				// Starts out with 0 droids
		index = 0;								// Checks if there is any more droids to create within the maximum allowed
		droids = new Droid[MAX_NUMBER_DROIDS];	//Create droid and load bitmap
		
		//Starts out 1X
		animationSpeedFactor = 1;
		
		setFocusable(true);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		super.surfaceCreated(holder);
		
		//Heads up display
		floatingDisplay = new FloatingDisplay(2, "bottomleft", Color.WHITE, getWidth(), getHeight());
		floatingDisplay.addParam("Speed", animationSpeedFactor);
		floatingDisplay.addParam("Droids", currentNumberDroids);
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
		//displayFps(canvas, avgFps);
		if ( !floatingFPS.display(canvas) )
			makeToast("Error. There was a problem displaying FPS");
		if ( !floatingDisplay.display(canvas) )
			makeToast("Error. There was a problem with floating display");
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
	 
	public int getAnimationSpeedFactor() {
		return animationSpeedFactor;
	}

	public void setAnimationSpeedFactor(int animationSpeedFactor) {
		this.animationSpeedFactor = animationSpeedFactor;
		if ( !floatingDisplay.updateParam("Speed", animationSpeedFactor))
			makeToast("Param SPEED couldn't be found");
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
				
				//Update the floating display				
				if ( !floatingDisplay.updateParam("Droids", currentNumberDroids))
					makeToast("Param DROIDS couldn't be found");		
				
			} else {				
				droids[currentNumberDroids].setX(x);
				droids[currentNumberDroids].setY(y);
				currentNumberDroids++;
				
				//Update the floating display
				if ( !floatingDisplay.updateParam("Droids", currentNumberDroids))
					makeToast("Param DROIDS couldn't be found");
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
				
				//Update the floating display
				if ( !floatingDisplay.updateParam("Droids", currentNumberDroids))
					makeToast("Param DROIDS couldn't be found");
				
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
