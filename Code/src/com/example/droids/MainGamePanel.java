package com.example.droids;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;

public abstract class MainGamePanel extends SurfaceView implements 
  SurfaceHolder.Callback {		
	
	//Tag for logging on Android's Log
	private static final String TAG = MainGamePanel.class.getSimpleName();
	
	//Main Thread of the Game
	private MainThread thread;
	protected Rect frameBox;
	protected String avgFps;	

	public MainGamePanel(Context context) {
		
		super(context);
		
		/*
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
		*/
	}

	@Override
	 public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	 }

	 @Override
	 public void surfaceCreated(SurfaceHolder holder) { 
		 
		 if ( thread != null ){
			 if( thread.getState() == Thread.State.TERMINATED ) {
				 Log.d(TAG, "Thread is TERMINATED");
					 
				//Create the game loop thread
				thread = new MainThread(getHolder(), this);
			} else {
				Log.d(TAG, "Thread is ALIVE");
			}
		} else {
			//Create the game loop thread
			thread = new MainThread(getHolder(), this);
		}
		 
		 thread.setRunning(true);
		 thread.start();
		 
		// Framing box for collisiong detection
		frameBox = new Rect(10, 10, getWidth()-10, getHeight()-10);
	 }

	 @Override
	 public void surfaceDestroyed(SurfaceHolder holder) {
		 //tell the thread to shut down and wait for it to finish
		 //this is a clean shutdown
		 boolean retry = true;
		 while ( retry ) {
			 try {
				 //Stopping the thread loop
				 thread.setRunning(false);
				 //Stopping the activity before joining the thread
				 //((Activity)getContext()).finish();
				 
				 //Joining the thread
				 thread.join();
				 retry = false;
				 
			 } catch ( InterruptedException e) {
				 //try again shutting down the thread
				 Log.d(TAG, "Excepction caught: "+ e.getMessage());
			 }
		 }
		 Log.d(TAG, "Thread was shut down cleanly");
	 }

	 @Override
	 abstract public boolean onTouchEvent(MotionEvent event);

	 public void setAvgFps( String avgFps ) {
		 this.avgFps = avgFps;
	 }
	 
	 protected void displayFps( Canvas canvas, String fps ){
		 if ( canvas != null && fps != null ) {
			 Paint paint = new Paint();
			 paint.setARGB(255, 255, 255, 255);
			 canvas.drawText(fps, this.getWidth() - 70, 30, paint);
		 }
	 }
	 
	 abstract public void render(Canvas canvas);
	 
	 abstract public void update();
}