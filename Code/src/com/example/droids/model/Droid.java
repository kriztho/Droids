package com.example.droids.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.droids.model.components.Speed;

public class Droid {
	
	private static final String TAG = Droid.class.getSimpleName();
	
	private Bitmap bitmap; 	// actual bitmap
	private int x; 			// x coordinate
	private int y;			// y coordinate
	private boolean touched;// if droid is touched
	private Speed speed; 	// Speed object
	
	public Droid( Bitmap bitmap, int x, int y ) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.speed = new Speed();
	}

	public Droid( Bitmap bitmap, int x, int y, Speed speed ) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		//this.speed = new Speed();
		this.speed = speed;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX( int x ) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY( int y ) {
		this.y = y;
	}
	
	public boolean isTouched() {
		return touched;
	}
	
	public void setTouched( boolean touched ) {
		this.touched = touched;
	}
	
	public Speed getSpeed() {
		return speed;
	}
	
	public void setSpeed( Speed speed ) {
		this.speed = speed;
	}
	
	public void draw( Canvas canvas ) {
		//Coordinates are in the center of the bitmap... so move to top left corner
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}
	
	public void handleActionDown(int eventX, int eventY) {
		//Is the touch within the bitmap area?
		if (eventX >= (x - (2*bitmap.getWidth() / 2)) && (eventX <= (x + (2*bitmap.getWidth()/2)))) {
			if (eventY >= (y - (2*bitmap.getHeight() / 2)) && (eventY <= (y + (2*bitmap.getHeight()/2) ))) {
				//droid touched
				setTouched(true);
			} else {
				setTouched(false);
			} 
		} else { 
			setTouched(false);
		}
	}
	
	public void update() {
		if (!touched){
			x += (speed.getXv() * speed.getxDirection());
			y += (speed.getYv() * speed.getyDirection());
		}
	}
}
