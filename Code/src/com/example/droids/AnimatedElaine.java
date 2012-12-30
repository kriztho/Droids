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
	
	private static final int INVALID_POINTER_ID = -1;
		
	//Tag for logging on Android's Log
	private static final String TAG = AnimatedElaine.class.getSimpleName();
	private ElaineAnimated elaine;
	private FloatingDisplay floatingDisplay;
	private GestureDetector gestureDetector;
	private boolean isScrolling = false;
	private OnScreenController dPad;
	private OnScreenController actionButtons;
	private Background currentBackground;
	private int firstPointerId = INVALID_POINTER_ID;
	private int secondPointerId = INVALID_POINTER_ID;

	public AnimatedElaine(Context context) {
		super(context);
		
		getHolder().addCallback(this);
				
		//gestureDetector = new GestureDetector(context, new GestureListener());
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		super.surfaceCreated(holder);
		
		elaine = new ElaineAnimated(BitmapFactory.decodeResource( getResources(), R.drawable.walk_elaine), getWidth()/2, getHeight()/2, 5, 5);
		frameBox = new Rect(10,10,getWidth()-10, getHeight()-10);
		dPad = new OnScreenController(4, 1, frameBox, 'm', 150);
		actionButtons = new OnScreenController(2, 0, frameBox, 'l', 150);
		
		currentBackground = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.rpg_background), frameBox);
	}
	
	private class GestureListener extends GestureDetector.SimpleOnGestureListener {
		
		// Notified when a tap occurs with the down MotionEvent that triggered it.
		// NECESSARY TO RETURN TRUE TO LET OTHER METHODS FIRE AND CATCH TOUCHES!!
		@Override
		public boolean onDown(MotionEvent e) {						
			
			if ( e.getPointerCount() == 1 ) {
			
				//Calculate if a button was pressed
				int dPadButtonTouched = dPad.isTouching((int)(e.getX()), (int)(e.getY()));
				int actionButtonTouched = actionButtons.isTouching((int)(e.getX()), (int)(e.getY()));
				
				if ( dPadButtonTouched != -1 && actionButtonTouched == -1 )
				
					switch( dPadButtonTouched ) {
						case 0:
							elaine.move("left");
							break;
						case 1:
							elaine.move("up");
							break;
						case 2:
							elaine.move("down");
							break;
						case 3:
							elaine.move("right");
							break;
						default:
							elaine.stop();
							break;
				} else if ( dPadButtonTouched == -1 && actionButtonTouched != -1 ) {
					
				}
					
			} else if ( e.getPointerCount() == 2 ) {
				//Calculate if a button was pressed
				int dPadButtonTouched = dPad.isTouching((int)(e.getX()), (int)(e.getY()));
				int actionButtonTouched = actionButtons.isTouching((int)(e.getX()), (int)(e.getY()));
				
				// If the running button is punched plus the direction pad
				if ( actionButtonTouched == 0 ) {
					
					elaine.setRunning(true);
					
					switch( dPadButtonTouched ) {
						case 0:
							elaine.move("left");
							break;
						case 1:
							elaine.move("up");
							break;
						case 2:
							elaine.move("down");
							break;
						case 3:
							elaine.move("right");
							break;
						default:
							elaine.stop();
							break;
					}
				} else {
					switch( dPadButtonTouched ) {
					case 0:
						elaine.move("left");
						break;
					case 1:
						elaine.move("up");
						break;
					case 2:
						elaine.move("down");
						break;
					case 3:
						elaine.move("right");
						break;
					default:
						elaine.stop();
						break;
				}
				}
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
			
			int buttonTouched1 = dPad.isTouching((int)(e1.getX()), (int)(e1.getY()));
			int buttonTouched2 = dPad.isTouching((int)(e2.getX()), (int)(e2.getY()));
			switch( buttonTouched2 ) {
			case 0:
				elaine.move("left");
				if ( buttonTouched1 != buttonTouched2 && buttonTouched1 != -1 )
					dPad.release(buttonTouched1);
				break;
			case 1:
				elaine.move("up");
				if ( buttonTouched1 != buttonTouched2 && buttonTouched1 != -1 )
					dPad.release(buttonTouched1);
				break;
			case 2:
				elaine.move("down");
				if ( buttonTouched1 != buttonTouched2 && buttonTouched1 != -1 )
					dPad.release(buttonTouched1);
				break;
			case 3:
				elaine.move("right");
				if ( buttonTouched1 != buttonTouched2 && buttonTouched1 != -1 )
					dPad.release(buttonTouched1);
				break;
			default:
				elaine.stop();
				break;
		}
			
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
	 public boolean onTouchEvent(MotionEvent e) {
		 
		 //int count = e.getPointerCount();
		 //Log.d(TAG, "OnTouch Taps: " + count);
		 
		float x, y;		 		
		int dPadButtonTouched = -1;
		int actionButtonTouched = -1;
		
		final int action = e.getAction();		
		switch( action & MotionEvent.ACTION_MASK ) {
		 
			case  MotionEvent.ACTION_DOWN:{
				
				//Calculate if a button was pressed
				dPadButtonTouched = dPad.isTouching((int)(e.getX()), (int)(e.getY()));
				actionButtonTouched = actionButtons.isTouching((int)(e.getX()), (int)(e.getY()));
				
				firstPointerId = e.getPointerId(0);																		
				
				if ( dPadButtonTouched != -1 ) {
					switch( dPadButtonTouched ) {
						case 0:
							elaine.move("left");
							break;
						case 1:
							elaine.move("up");
							break;
						case 2:
							elaine.move("down");
							break;
						case 3:
							elaine.move("right");
							break;
						default:
							elaine.stop();
							break;
					}
				}
				
				if ( actionButtonTouched != -1 )
					elaine.setRunning(true);
				
				break;
			}
			
			case MotionEvent.ACTION_POINTER_DOWN: {
				
				// Find the pointer that triggered the action
				// Extract the index of the pointer that left the touch sensor
				x = e.getX(firstPointerId);
				y = e.getY(firstPointerId);
				dPadButtonTouched = dPad.isTouching((int)x, (int)y);
				actionButtonTouched = actionButtons.isTouching((int)x, (int)y);
												
				// Find if the new pointer pressed a button too
				final int pointerIndexNew = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) 
		                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		        secondPointerId = e.getPointerId(pointerIndexNew);
				x = e.getX(secondPointerId);
				y = e.getY(secondPointerId);
				if ( dPadButtonTouched == -1 )
					dPadButtonTouched = dPad.isTouching((int)x, (int)y);
				
				if ( actionButtonTouched == -1 )
					actionButtonTouched = actionButtons.isTouching((int)x, (int)y);					
				
					// If the running button is punched plus the direction pad
					if ( dPadButtonTouched != -1 && actionButtonTouched == 0 ) {
						
						elaine.setRunning(true);
						
						switch( dPadButtonTouched ) {
							case 0:
								elaine.move("left");
								break;
							case 1:
								elaine.move("up");
								break;
							case 2:
								elaine.move("down");
								break;
							case 3:
								elaine.move("right");
								break;
							default:
								elaine.stop();
								break;
						}
					} else if ( dPadButtonTouched != -1 ){
						
						switch( dPadButtonTouched ) {
						case 0:
							elaine.move("left");
							break;
						case 1:
							elaine.move("up");
							break;
						case 2:
							elaine.move("down");
							break;
						case 3:
							elaine.move("right");
							break;
						default:
							elaine.stop();
							break;
						}
					}
				break;
			}
			
			case MotionEvent.ACTION_UP: {
				
				x = e.getX();
				y = e.getY();				
				dPadButtonTouched = dPad.isTouching((int)x, (int)y);
				actionButtonTouched = actionButtons.isTouching((int)x, (int)y);
				 
				// Released walking button
				if ( dPadButtonTouched != -1 ) {
					dPad.release(dPadButtonTouched);
					elaine.stop();
				}
				
				// Running button is not being pressed
				if ( actionButtonTouched != -1 ) {
					actionButtons.release(actionButtonTouched);
					elaine.setRunning(false);	// Stop running
				}
				break;
			}
			
			case MotionEvent.ACTION_POINTER_UP: {
				
				// Find the pointer that left
				int pointerIndexNew = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) 
						>> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		        int pointerId = e.getPointerId(pointerIndexNew);
				x = e.getX(pointerId);
				y = e.getY(pointerId);
				dPadButtonTouched = dPad.isTouching((int)x, (int)y);
				actionButtonTouched = actionButtons.isTouching((int)x, (int)y);
				
				// The object that is pointed by the one that left is the one to be released
				if ( dPadButtonTouched != -1 ) {
					dPad.release(dPadButtonTouched);
					elaine.stop();
				}
				
				if ( actionButtonTouched != -1 ) {
					actionButtons.release(actionButtonTouched);
					elaine.setRunning(false);
				}							
				
				break;
			}
			
			case MotionEvent.ACTION_MOVE: {
					
				// Find the pointer that left
				int pointerIndexNew = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) 
						>> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		        int pointerId = e.getPointerId(pointerIndexNew);
		        
		        int historySize = e.getHistorySize();
				if ( historySize > 0 ) {
					float previousX = e.getHistoricalX(pointerId, historySize - 1);
					float previousY = e.getHistoricalY(pointerId, historySize - 1);
					int dPadButtonTouched1 = dPad.isTouching((int)previousX, (int)previousY);
					int actionButtonTouched1 = actionButtons.isTouching((int)previousX, (int)previousY);					
					
					x = e.getX(pointerId);
					y = e.getY(pointerId);									
					actionButtonTouched = actionButtons.isTouching((int)previousX, (int)previousY);
					dPadButtonTouched = dPad.isTouching((int)x, (int)y);
					
					// The object that is pointed by the one that left is the one to be released
					if ( dPadButtonTouched != -1 ) {
						switch( dPadButtonTouched ) {
						case 0:
							elaine.move("left");
							break;
						case 1:
							elaine.move("up");		
							break;
						case 2:
							elaine.move("down");
							break;
						case 3:
							elaine.move("right");
							break;
						default:
							elaine.stop();
							break;
						}
					} else if ( dPadButtonTouched1 != -1 && dPadButtonTouched == -1 ){
						elaine.stop();
					}
				
					if ( actionButtonTouched1 != -1 && actionButtonTouched == -1 )
						elaine.setRunning(false);					
				}
								
				break;
			}		
		}
		return true;
	 }
	 
	 public void render(Canvas canvas) {
		//fills the canvas with black 
		canvas.drawColor(Color.BLACK);
		currentBackground.render(canvas);
		
		// Draw the stage as a containing box
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(frameBox, paint);
		
		//Drawing Elaine
		elaine.draw(canvas);		
		dPad.render(canvas);
		actionButtons.render(canvas);
		
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
