package com.example.droids;

import com.example.droids.model.Droid;
import com.example.droids.model.ElaineAnimated;
import com.example.droids.model.Explosion;
import com.example.droids.model.Particle;
import com.example.droids.model.components.Speed;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;

public class MainGamePanel extends SurfaceView implements 
  SurfaceHolder.Callback {		
	
	//Tag for logging on Android's Log
	private static final String TAG = MainGamePanel.class.getSimpleName();
	private static final int FIREWORKS_NUMBER = 30;
	private static final int EXPLOSIONS_NUMBER = 7;
	
	//Main Thread of the Game
	private MainThread thread;
	private Droid droid;
	private ElaineAnimated elaine;
	private Particle particle;
	private Explosion explosion;
	private Explosion[] fireworks;
	private int index;
	private Rect frameBox;
	private String avgFps;	

	public MainGamePanel(Context context) {
		
		super(context);
		
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		//Create droid and load bitmap
		//droid = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), 50, 50);
		//elaine = new ElaineAnimated(BitmapFactory.decodeResource( getResources(), R.drawable.walk_elaine), 10, 300, 5, 5);
		//particle = new Particle(160,240);
		//explosion = new Explosion(20, 170, 240);
		fireworks = new Explosion[FIREWORKS_NUMBER];
		index = 0;
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
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
		//frameBox = new Rect(40, 40, 280, 440);
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
				 ((Activity)getContext()).finish();
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
	 public boolean onTouchEvent(MotionEvent event) {
		 
		 if (event.getAction() == MotionEvent.ACTION_DOWN) {
			 
			 //delegating event handling to the droid
			 if ( droid != null ) {
				 droid.handleActionDown((int)event.getX(), (int)event.getY());
			 }
			 
			 // Populating the array and reusing the objects inside
			fireworks[index] = new Explosion(EXPLOSIONS_NUMBER, (int)(event.getX()), (int)(event.getY()));
			index++;
			if ( index == FIREWORKS_NUMBER)
				index = 0;
			 
		 } if (event.getAction() == MotionEvent.ACTION_MOVE ) {
			 //the gestures
			 
			 if( droid!= null) {
				 if (droid.isTouched()) {
					 //the droid was picked up and is being dragged
					 droid.setX((int)event.getX());
					 droid.setY((int)event.getY());
				 }
			 }
			 
			 fireworks[index] = new Explosion(40, (int)(event.getX()), (int)(event.getY()));
			 index++;
			 if ( index == FIREWORKS_NUMBER)
				index = 0;
			 
		 } if (event.getAction() == MotionEvent.ACTION_UP ){
			 
			 if( droid!= null) {
				 //touch was released
				 if (droid.isTouched()) {
					 droid.setTouched(false);
				 }
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
			 canvas.drawText(fps, this.getWidth() - 70, 30, paint);
		 }
	 }
	 
	 public void render(Canvas canvas) {
		//fills the canvas with black 
		canvas.drawColor(Color.BLACK);
		//droid.draw(canvas);
		
		//Drawing Elaine
		//elaine.draw(canvas);
		
		// Draw particle
		//particle.draw(canvas);
		
		// Draw explosion
		//explosion.draw(canvas);
		
		// Draw fireworks
		drawFireworks(canvas);
		
		displayFps(canvas, avgFps);
	 }
	 
	 public void drawFireworks(Canvas canvas) {
		 
		 Paint paint = new Paint();
		 paint.setARGB(255, 0, 255, 0);
		 paint.setStyle(Paint.Style.STROKE);
		 canvas.drawRect(frameBox, paint);
		 
		// Drawing all explosions in the array
		 for ( int i = 0; i < FIREWORKS_NUMBER; i++ ) {
			 if (fireworks[i] != null)
				 fireworks[i].draw(canvas);
		 }
	 }
	 
	 public void updateDroid(){
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
	 }
	 
	 public void updateElaine(){
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
	 
	 public void updateParticle(){
		
		// Framing box for collisiong detection
		Rect frameBox = new Rect(0, 0, getWidth(), getHeight());
		particle.update(frameBox);
	 }

	 public void updateExplosion(){		 
		 explosion.update(frameBox);
	 }
	 
	 public void updateFireworks() {
		 
		 // Updating all explosions in the array
		 for ( int i = 0; i < FIREWORKS_NUMBER; i++ ) {
			 if (fireworks[i] != null){
				 fireworks[i].update(frameBox);
			 }
		 }
	 }
	 
	 public void update() {
		
		//updateParticle();
		//updateExplosion();
		 updateFireworks();
	 }
}