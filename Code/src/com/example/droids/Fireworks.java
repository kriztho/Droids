package com.example.droids;

import com.example.droids.model.Explosion;
import com.example.droids.model.Particle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class Fireworks extends MainGamePanel implements 
SurfaceHolder.Callback {
	
	//Tag for logging on Android's Log
	private static final String TAG = Fireworks.class.getSimpleName();
	private static final int FIREWORKS_MAX_NUMBER = 50;
	private static final int EXPLOSIONS_MAX_SIZE = 30;
	
	private int fireworksSize;
	private Explosion[] fireworks;
	private Explosion explosion;
	private int index;	
	private int explosionSize;
	private Particle particle;
	
	private FloatingDisplay floatingDisplay;

	public Fireworks(Context context) {
		super(context);
		
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		//Create droid and load bitmap
		fireworksSize = 50;
		fireworks = new Explosion[FIREWORKS_MAX_NUMBER];
		index = 0;
		
		explosionSize = 10;
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		super.surfaceCreated(holder);
		
		//Heads up display
		floatingDisplay = new FloatingDisplay(2, "bottomleft", Color.WHITE, getWidth(), getHeight());
		floatingDisplay.addParam("Explosions", fireworksSize);
		floatingDisplay.addParam("Particles", explosionSize);
	}

	 @Override
	 public boolean onTouchEvent(MotionEvent event) {
		 
		 if (event.getAction() == MotionEvent.ACTION_DOWN) {
			 
			 // Populating the array and reusing the objects inside
			fireworks[index] = new Explosion(explosionSize, (int)(event.getX()), (int)(event.getY()));
			index++;
			if ( index == fireworksSize)
				index = 0;
			 
		 } if (event.getAction() == MotionEvent.ACTION_MOVE ) {
			 //the gestures
			 
			 fireworks[index] = new Explosion(explosionSize, (int)(event.getX()), (int)(event.getY()));
			 index++;
			 if ( index == fireworksSize)
				index = 0;
			 
		 } if (event.getAction() == MotionEvent.ACTION_UP ){
			 
		 }
		 
	  return true;
	  
	 }
	 
	 public void render(Canvas canvas) {
		
		 //fills the canvas with black 
		canvas.drawColor(Color.BLACK);
		
		// Draw fireworks
		drawFireworks(canvas);
		
		//displayFps(canvas, avgFps);
		if ( !floatingFPS.display(canvas) )
			makeToast("Error. There was a problem displaying FPS");
		if ( !floatingDisplay.display(canvas) )
			makeToast("Error. There was a problem with floating display");
	 }
	 
	 public void drawFireworks(Canvas canvas) {
		 
		 Paint paint = new Paint();
		 paint.setARGB(255, 0, 255, 0);
		 paint.setStyle(Paint.Style.STROKE);
		 canvas.drawRect(frameBox, paint);
		 
		// Drawing all explosions in the array
		 for ( int i = 0; i < fireworksSize; i++ ) {
			 if (fireworks[i] != null)
				 fireworks[i].draw(canvas);
		 }
	 }
	 
	 public void updateParticle(){
		
		// Framing box for collision detection
		Rect frameBox = new Rect(0, 0, getWidth(), getHeight());
		particle.update(frameBox);
	 }

	 public void updateExplosion(){		 
		 explosion.update(frameBox);
	 }
	 
	 public void updateFireworks() {
		 
		 // Updating all explosions in the array
		 for ( int i = 0; i < fireworksSize; i++ ) {
			 if (fireworks[i] != null){
				 fireworks[i].update(frameBox);
			 }
		 }
	 }
	 
	 public void update() {

		 updateFireworks();
	 }
	 
	 public void setFireworksSize(int newFireworksSize) {
		 fireworksSize = newFireworksSize;
	 }
	 
	 public int getFireworksSize() {
		 return fireworksSize;
	 }
	 
}
