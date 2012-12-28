package com.example.droids;

import com.example.droids.model.ElaineAnimated;
import com.example.droids.model.components.Speed;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class AnimatedElaine extends MainGamePanel implements 
SurfaceHolder.Callback {
	
	//Tag for logging on Android's Log
	private static final String TAG = AnimatedElaine.class.getSimpleName();
	private ElaineAnimated elaine;
	private FloatingDisplay floatingDisplay;
	private GestureDetector gestureDetector;
	private boolean isScrolling = false;
	private OnScreenController dPad;

	public AnimatedElaine(Context context) {
		super(context);
		
		getHolder().addCallback(this);
		
		elaine = new ElaineAnimated(BitmapFactory.decodeResource( getResources(), R.drawable.walk_elaine), 10, 240, 5, 5);
		gestureDetector = new GestureDetector(context, new GestureListener());
		//dPad = new OnScreenController(2, 0, 3, 400, 'l', 100);
		//dPad = new OnScreenController(4, 0, 3, 400, 'l', 100);
		dPad = new OnScreenController(4, 1, 3, 227, 'm', 100);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		super.surfaceCreated(holder);
		
		frameBox = new Rect(10,10,getWidth() - 10, getHeight() - 166);
		
	}
	
	private class GestureListener extends GestureDetector.SimpleOnGestureListener {				
		
		// Notified when a tap occurs with the down MotionEvent that triggered it.
		// NECESSARY TO RETURN TRUE TO LET OTHER METHODS FIRE AND CATCH TOUCHES!!
		@Override
		public boolean onDown(MotionEvent e) {	
			
			//Calculate if a button was pressed
			int buttonTouched = dPad.isTouching((int)(e.getX()), (int)(e.getY()));
			switch( buttonTouched ) {
				case 0:
					elaine.walk("left");
					break;
				case 1:
					elaine.walk("up");
					break;
				case 2:
					elaine.walk("down");
					break;
				case 3:
					elaine.walk("right");
					break;
				default:
					break;
			}						
			
			return true;
		}
		
		// Notified when a double-tap occurs.
		@Override
		public boolean onDoubleTap(MotionEvent e) {			
			return false;
		}

		// Notified when an event within a double-tap gesture occurs, 
		// including the down, move, and up events.
		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			return false;
		}
		
		//Notified when a single-tap occurs.
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {	
			return false;
		}

		// Notified of a fling event when it occurs with the initial on down 
		// MotionEvent and the matching up MotionEvent.
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return false;
		}

		// Notified when a long press occurs with the initial on down 
		// MotionEvent that trigged it.
		@Override
		public void onLongPress(MotionEvent e) {			
		}

		// Notified when a scroll occurs with the initial on down 
		// MotionEvent and the current move MotionEvent.
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			
			isScrolling = true;					
			
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

	 @Override
	 public boolean onTouchEvent(MotionEvent event) {
		 
		 if ( gestureDetector.onTouchEvent(event) )
				return true;
			
			if ( event.getAction() == MotionEvent.ACTION_UP ) {
				if ( isScrolling ) {
					
					// Handle when scroll finished
					isScrolling = false;					
				}
				
				int buttonTouched = dPad.isTouching((int)(event.getX()), (int)(event.getY()));
				if ( buttonTouched != -1) {
					dPad.release(buttonTouched);
					elaine.stop();
				}
			}
		return false;
	 }
	 
	 public void render(Canvas canvas) {
		//fills the canvas with black 
		canvas.drawColor(Color.BLACK);
		
		// Draw the stage as a containing box
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(frameBox, paint);
		
		//Drawing Elaine
		elaine.draw(canvas);		
		dPad.render(canvas);
		
		//displayFps(canvas, avgFps);
		if ( !floatingFPS.display(canvas) )
			makeToast("Error. There was a problem displaying FPS");
		
	 }
	 
	 public void updateElaine(){
		
		//Updating Elaine
		elaine.update(System.currentTimeMillis(), frameBox);
	 }
	 
	 public void update() {
		
		updateElaine();
	 }
}
