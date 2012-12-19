package com.example.droids;

import com.example.droids.model.Droid;
import com.example.droids.model.ElaineAnimated;
import com.example.droids.model.components.Speed;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;

public class MainGamePanel extends SurfaceView implements 
  SurfaceHolder.Callback {		
	
	//Tag for logging on Android's Log
	private static final String TAG = MainGamePanel.class.getSimpleName();
	
	//Main Thread of the Game
	private MainThread thread;
	private Droid droid;
	private ElaineAnimated elaine;
	private String avgFps;

	public MainGamePanel(Context context) {
		
		super(context);
		
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		//Create droid and load bitmap
		droid = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), 50, 50);
		elaine = new ElaineAnimated(BitmapFactory.decodeResource( getResources(), R.drawable.walk_elaine), 
				10, 300, 
				5, 5);
		
		//Create the game loop thread
		thread = new MainThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	 public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	 }

	 @Override
	 public void surfaceCreated(SurfaceHolder holder) {
		 thread.setRunning(true);
		 thread.start();
	 }

	 @Override
	 public void surfaceDestroyed(SurfaceHolder holder) {
		 //tell the thread to shut down and wait for it to finish
		 //this is a clean shutdown
		 boolean retry = true;
		 while ( retry ) {
			 try {
				 thread.join();
				 retry = false;
			 } catch ( InterruptedException e) {
				 //try again shutting down the thread
			 }
		 }
		 Log.d(TAG, "Thread was shut down cleanly");
	 }

	 @Override
	 public boolean onTouchEvent(MotionEvent event) {
		 
		 if (event.getAction() == MotionEvent.ACTION_DOWN) {
			 
			 //delegating event handling to the droid
			 droid.handleActionDown((int)event.getX(), (int)event.getY());
			 
			 //Check if in the lower part of the screen we exit
			 if ( event.getY() > getHeight() - 50 ) {
				 thread.setRunning(false);
				 ((Activity)getContext()).finish();
			 } else {
				 Log.d(TAG, "Coords: x=" + event.getX() + ", y=" + event.getY());
			 }
		 } if (event.getAction() == MotionEvent.ACTION_MOVE ) {
			 //the gestures
			 if (droid.isTouched()) {
				 //the droid was picked up and is being dragged
				 droid.setX((int)event.getX());
				 droid.setY((int)event.getY());
			 }
		 } if (event.getAction() == MotionEvent.ACTION_UP ){
			 //touch was released
			 if (droid.isTouched()) {
				 droid.setTouched(false);
			 }
		 }
	  return true;
	 }

	 public void setAvgFps( String avgFps ) {
		 this.avgFps = avgFps;
	 }
	 
	 private void displayFps( Canvas canvas, String fps ){
		 if ( canvas != null && fps != null ) {
			 Paint paint = new Paint();
			 paint.setARGB(255, 255, 255, 255);
			 canvas.drawText(fps, this.getWidth() - 50, 20, paint);
		 }
	 }
	 
	 public void render(Canvas canvas) {
		//fills the canvas with black 
		canvas.drawColor(Color.BLACK);
		droid.draw(canvas);
		
		//Drawing Elaine
		elaine.draw(canvas);
		
		
		displayFps(canvas, avgFps);
	 }	 

	 public void update() {
		// check collision with right wall if heading right
		if (droid.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
				&& droid.getX() + droid.getBitmap().getWidth() / 2 >= getWidth()) {
			droid.getSpeed().togglexDirection();
		}
		// check collision with left wall if heading left
		if (droid.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
				&& droid.getX() - droid.getBitmap().getWidth() / 2 <= 0) {
			droid.getSpeed().togglexDirection();
		}
		// check collision with bottom wall if heading down
		if (droid.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
				&& droid.getY() + droid.getBitmap().getHeight() / 2 >= getHeight()) {
			droid.getSpeed().toggleyDirection();
		}
		// check collision with top wall if heading up
		if (droid.getSpeed().getyDirection() == Speed.DIRECTION_UP
				&& droid.getY() - droid.getBitmap().getHeight() / 2 <= 0) {
			droid.getSpeed().toggleyDirection();
		}
		// Update the lone droid
		droid.update();
		
		
		// check collision with right wall if heading right
		if (elaine.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
				&& elaine.getX() + elaine.getSpriteWidth() >= getWidth()) {
			elaine.getSpeed().togglexDirection();
		}
		// check collision with left wall if heading left
		if (elaine.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
				&& elaine.getX() - elaine.getSpriteWidth() / 2 <= 0) {
			elaine.getSpeed().togglexDirection();
		}
		
		//Updating Elaine
		elaine.update(System.currentTimeMillis());
	 }
}