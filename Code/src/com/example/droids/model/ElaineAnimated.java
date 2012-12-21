package com.example.droids.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.droids.model.components.Speed;

public class ElaineAnimated {
	
	private static final String TAG = ElaineAnimated.class.getSimpleName();
	
	private Bitmap bitmap;		// the animation sequence
	private Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
	private int frameNr;		// number of frames in animation
	private int currentFrame;	// the current frame
	private long frameTicker;	// the time of the last frame update
	private int framePeriod;	// milliseconds between each frame 1000/fps
	
	private int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
	private int spriteHeight;	// the hight of the sprite
	
	private int x;				// the x coordinate of the object
	private int y;				// the y coordinate of the object
	
	private Speed speed;
	
	public Bitmap getBitmap() {
		return bitmap;
	}

	public Rect getSourceRect() {
		return sourceRect;
	}

	public int getFrameNr() {
		return frameNr;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public long getFrameTicker() {
		return frameTicker;
	}

	public int getFramePeriod() {
		return framePeriod;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public void setSourceRect(Rect sourceRect) {
		this.sourceRect = sourceRect;
	}

	public void setFrameNr(int frameNr) {
		this.frameNr = frameNr;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public void setFrameTicker(long frameTicker) {
		this.frameTicker = frameTicker;
	}

	public void setFramePeriod(int framePeriod) {
		this.framePeriod = framePeriod;
	}

	public void setSpriteWidth(int spriteWidth) {
		this.spriteWidth = spriteWidth;
	}

	public void setSpriteHeight(int spriteHeight) {
		this.spriteHeight = spriteHeight;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Speed getSpeed() {
		return speed;
	}

	public void setSpeed(Speed speed) {
		this.speed = speed;
	}

	public ElaineAnimated (Bitmap bitmap, int x, int y, int fps, int frameCount ) {
		
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		currentFrame = 0;
		frameNr = frameCount;
		spriteWidth = bitmap.getWidth() / frameCount;
		spriteHeight = bitmap.getHeight();
		sourceRect = new Rect(0,0, spriteWidth, spriteHeight);
		framePeriod = 1000 / fps;
		frameTicker = 0l;
		this.speed = new Speed(2,0);
	}
	
	public void update( long gameTime ){
		if (gameTime > frameTicker + framePeriod){
			frameTicker = gameTime;
			
			// increment the frame
			currentFrame++;
			if ( currentFrame >= frameNr ) {
				currentFrame = 0;
			}
		}
		// define the rectangle to cut out sprite
		this.sourceRect.left = currentFrame * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;
		
		x += (speed.getXv() * speed.getxDirection());
		y += (speed.getYv() * speed.getyDirection());
	}
	
	public void draw( Canvas canvas ){
		//Where to draw the sprite
		Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight);
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);
		
		canvas.drawBitmap(bitmap, 20, 150, null);
		Paint paint = new Paint();
		paint.setARGB(50, 0, 255, 0);
		canvas.drawRect(20 + (currentFrame * destRect.width()),150, 
						20 + (currentFrame * destRect.width()) + destRect.width(), 
						150 + destRect.height(), paint);
	}

}