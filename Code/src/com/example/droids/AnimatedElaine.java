package com.example.droids;

import com.example.droids.model.ElaineAnimated;
import com.example.droids.model.components.Speed;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class AnimatedElaine extends MainGamePanel implements 
SurfaceHolder.Callback {
	
	//Tag for logging on Android's Log
	private static final String TAG = AnimatedElaine.class.getSimpleName();
	private ElaineAnimated elaine;

	public AnimatedElaine(Context context) {
		super(context);
		
		getHolder().addCallback(this);
		
		elaine = new ElaineAnimated(BitmapFactory.decodeResource( getResources(), R.drawable.walk_elaine), 10, 300, 5, 5);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	 @Override
	 public boolean onTouchEvent(MotionEvent event) {
		 
		 if (event.getAction() == MotionEvent.ACTION_DOWN) {
			 
			 
		 } if (event.getAction() == MotionEvent.ACTION_MOVE ) {
			 //the gestures
			 
		 } if (event.getAction() == MotionEvent.ACTION_UP ){
			 
			 
		 }
		 
		 return true;
	  
	 }
	 
	 public void render(Canvas canvas) {
		//fills the canvas with black 
		canvas.drawColor(Color.BLACK);
		
		//Drawing Elaine
		elaine.draw(canvas);
		
		displayFps(canvas, avgFps);
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
	 
	 public void update() {
		
		updateElaine();
	 }
}
