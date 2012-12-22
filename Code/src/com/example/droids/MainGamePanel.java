package com.example.droids;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
	protected Rect frameBox;
	protected double avgFps;
	protected String[] floatingInfoArray;
	protected String floatingInfo;

	public MainGamePanel(Context context) {
		
		super(context);
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
	 public boolean onTouchEvent(MotionEvent event){
		 return true;
	 }
	 
	public double getAvgFps() {
		return avgFps;
	}
	
	public void setAvgFps( double avgFps ) {
		 this.avgFps = avgFps;
	}
	
	protected void updateFloatingInfo( int infoId, String infoName, String info ) {
		
		floatingInfo = collapseStringArray(floatingInfoArray);
	}
	
	protected String collapseStringArray(String string[]) {
		String text = "";
		for (int i = 0; i < string.length; i++) {
			if ( string[i] != null )
				text += string[i] + " ";
		}
		
		return text;
	}
	
	protected void displayFloatingInfo( Canvas canvas ) {
		
		if ( canvas != null && floatingInfo != null ) {
			
			Paint paint = new Paint();
			paint.setColor(Color.WHITE);
			canvas.drawText(floatingInfo, 30, 30, paint);
		}
	}
	 
	 protected void displayFps( Canvas canvas, double fps ){
		 
		 if ( canvas != null && fps != -1 ) {	
			 
			 DecimalFormat df = new DecimalFormat("0.##");
			 
			 Paint paint = new Paint();
			 paint.setARGB(255, 255, 255, 255);			 
			 canvas.drawText("FPS: " + df.format(fps), this.getWidth() - 70, 30, paint);
		 }
	 }
	 
	 public void render(Canvas canvas){}
	 
	 public void update(){}
}