package com.example.droids;

import java.util.ArrayList;

//import com.example.droids.Droidz.GestureListener;
import com.example.droids.model.Droid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class DroidzPanel extends MainGamePanel implements SurfaceHolder.Callback {
	
	//Tag for logging on Android's Log
		private static final String TAG = Droidz.class.getSimpleName();
		private FloatingDisplay floatingDisplay; 
		private int finger;
		private GestureDetector gestureDetector;
		private boolean isScrolling = false;
		private DroidzGame currentGame;

	public DroidzPanel(Context context, DroidzGame game) {
		super(context);
		
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		//Set context
		appContext = context;
		currentGame = game;
		finger = -1;				
		
		gestureDetector = new GestureDetector(context, new GestureListener());				
		
		setFocusable(true);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		super.surfaceCreated(holder);
		
		setup();
		
		/*
		// Framing box for collision detection
		frameBox = new Rect(10, 10, getWidth()-10, getHeight()+1);		
		
		// Add framebox at the very first position
		if ( obstacles.size() == 0 )
			addObstacleAt(0,frameBox);					// Adding the framebox		
		
		//Heads up display
		floatingDisplay = new FloatingDisplay(2, "topleft", Color.WHITE, getWidth(), getHeight());
		floatingDisplay.addParam("Speed", animationSpeedFactor);
		floatingDisplay.addParam("Droids", currentNumberDroids);
		*/
	}
	
	public void setup(){
		
		// Framing box for collision detection
		frameBox = new Rect(10, 10, getWidth()-10, getHeight()+1);
		
		// Add framebox at the very first position
		if ( currentGame.getCurrentObstacles().size() == 0 )
			currentGame.addObstacleAt(0,frameBox);		// Adding the framebox

		//Heads up display
		floatingDisplay = new FloatingDisplay(2, "topleft", Color.WHITE, getWidth(), getHeight());
		floatingDisplay.addParam("Speed", currentGame.getAnimationSpeedFactor());
		floatingDisplay.addParam("Droids", currentGame.getCurrentNumberDroids());
		
		showSplashScreen();
		
	}
	
	public void showSplashScreen() {
		
		//Setting up splashscreen
		makeToast("Splash Screen");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if ( gestureDetector.onTouchEvent(event) )
			return true;
		
		if ( event.getAction() == MotionEvent.ACTION_UP ) {
			if ( isScrolling ) {
				
				/*
				// Handle when scroll finished
				isScrolling = false;							
				obstacles.get(finger).setEmpty();
				*/
			}
		}
		return false;
	 }
	
	public void render(Canvas canvas) {
		
		 //fills the canvas with black 
		canvas.drawColor(Color.BLACK);
		drawObstacles(canvas);
		//drawDroids(canvas);		
		
		// Display Heads Up Information
		//displayFps(canvas, avgFps);
		if ( !floatingFPS.display(canvas) )
			makeToast("Error. There was a problem displaying FPS");
		if ( !floatingDisplay.display(canvas) )
			makeToast("Error. There was a problem with floating display");
	}
	
	public void drawObstacles(Canvas canvas) {
		
		// Draw the stage as a containing box
		 Paint paint = new Paint();
		 paint.setColor(Color.GREEN);
		 paint.setStyle(Paint.Style.STROKE);
		 canvas.drawRect(currentGame.getCurrentObstacles().get(0), paint);
		 
		 int start = 1;
		 
		 if ( finger == 1 ) {
			 // Draw the finger with a different color
			 paint.setColor(Color.YELLOW);
			 canvas.drawRect(currentGame.getCurrentObstacles().get(1), paint);
			 
			 start = 2;
		 }
		
		 // Draw the rest of the obstacles
		 paint.setColor(Color.RED);
		 
		// Drawing all droids in the array
		 for ( int i = start; i < currentGame.getCurrentObstacles().size(); i++ ) {
			 canvas.drawRect(currentGame.getCurrentObstacles().get(i), paint);
		 }
	}
	 
	public void drawDroids(Canvas canvas) {
		 
		// Drawing all droids in the array
		 for ( int i = 0; i < currentGame.getCurrentNumberDroids(); i++ ) {
			 if (currentGame.getCurrentDroids()[i] != null)
				 currentGame.getCurrentDroids()[i].draw(canvas);
		 }
	 }
	 
	public void updateDroids(){
		
		if ( currentGame.isCollisionDetection() == true ) {
			
			// Updating all droids
			 for ( int i = 0; i < currentGame.getCurrentNumberDroids(); i++ ) {
				 if (currentGame.getCurrentDroids()[i] != null)
					 currentGame.getCurrentDroids()[i].update(currentGame.getCurrentObstacles(), currentGame.getAnimationSpeedFactor());
			 }
			
		} else {		 
			// Updating all droids
			 for ( int i = 0; i < currentGame.getCurrentNumberDroids(); i++ ) {
				 if (currentGame.getCurrentDroids()[i] != null)
					 currentGame.getCurrentDroids()[i].update(frameBox, currentGame.getAnimationSpeedFactor());
			 }
		}
		 
	 }
	 
	 
	public void update() {
		 
		 updateDroids();
	 }
	
	//////////////////////////////////////////////////////////////////////////
	private class GestureListener extends GestureDetector.SimpleOnGestureListener {				
		
		// Notified when a tap occurs with the down MotionEvent that triggered it.
		// NECESSARY TO RETURN TRUE TO LET OTHER METHODS FIRE AND CATCH TOUCHES!!
		@Override
		public boolean onDown(MotionEvent e) {			
			return true;
		}
		
		// Notified when a double-tap occurs.
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			//currentGame.addDroid((int)(e.getX()), (int)(e.getY()));
			//makeToast("DoubleTap");
			return false;
		}

		// Notified when an event within a double-tap gesture occurs, 
		// including the down, move, and up events.
		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			//makeToast("DoubleTapEvent");
			return false;
		}
		
		//Notified when a single-tap occurs.
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			
			//addObstacle((int)(e.getX()), (int)(e.getY()), 100, 100);			 
			
			return false;
		}

		// Notified of a fling event when it occurs with the initial on down 
		// MotionEvent and the matching up MotionEvent.
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			return false;
		}

		// Notified when a long press occurs with the initial on down 
		// MotionEvent that trigged it.
		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		// Notified when a scroll occurs with the initial on down 
		// MotionEvent and the current move MotionEvent.
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			/*
			isScrolling = true;
			
			if ( finger == -1 ) {
				 finger = 1;
				 addObstacleAt(finger, (int)(e2.getX()), (int)(e1.getY()), 100, 100);
			} else {
				 obstacles.get(finger).left = (int) (e2.getX() - 50); 
				 obstacles.get(finger).right = obstacles.get(finger).left + 100;
				 obstacles.get(finger).top = (int) (e2.getY() - 50);
				 obstacles.get(finger).bottom = obstacles.get(finger).top + 100;
			 }				
			*/
			 return false;
		}

		// The user has performed a down MotionEvent and not performed 
		// a move or up yet.
		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		// Notified when a tap occurs with the up MotionEvent that triggered it.
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}
		
	}

}
