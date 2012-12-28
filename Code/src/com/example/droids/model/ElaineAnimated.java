package com.example.droids.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
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
	
	private boolean walking;
	private boolean showSource;

	private String direction;

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
		this.speed = new Speed(0,0);
		this.walking = false;
		this.showSource = false;
	}
	
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
	
	//////////////////////////////////////////////
	// Controlling Methods
	/////////////////////////////////////////////
	
	public void walk(String direction) {
		
		this.walking = true;
		this.direction = direction;
		
		if ( direction == "right") {
			this.speed.setxDirection(Speed.DIRECTION_RIGHT);
			this.speed.setXv(2);
			this.speed.setYv(0);
		}
		
		if ( direction == "left") {
			this.speed.setxDirection(Speed.DIRECTION_LEFT);
			this.speed.setXv(2);
			this.speed.setYv(0);
		}
		
		if ( direction == "up") {
			this.speed.setyDirection(Speed.DIRECTION_UP);
			this.speed.setXv(0);
			this.speed.setYv(2);
		}
		
		if ( direction == "down") {
			this.speed.setyDirection(Speed.DIRECTION_DOWN);
			this.speed.setXv(0);
			this.speed.setYv(2);
		}
		
	}
	
	public void stop() {
		this.walking = false;
		stopX();
		stopY();
	}
	
	public void stopX() {
		this.speed.setXv(0);
	}
	
	public void stopY() {
		this.speed.setYv(0);
	}
	
	public void detectCollisions(Rect box) {
		// check collision with right wall if heading right
		if (this.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
				&& this.getX() + this.getSpriteWidth() >= box.width()) {
			//elaine.getSpeed().togglexDirection();
			this.stopX();
		}
		// check collision with left wall if heading left
		if (this.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
				&& this.getX() - this.getSpriteWidth()/5 <= 0) {
			//elaine.getSpeed().togglexDirection();
			this.stopX();
		}
		
		// check collision with right wall if heading right
		if (this.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
				&& this.getY() + this.getSpriteHeight() >= box.height()) {
			//elaine.getSpeed().togglexDirection();
			this.stopY();
		}
		// check collision with left wall if heading left
		if (this.getSpeed().getyDirection() == Speed.DIRECTION_UP
				&& this.getY() - this.getSpriteHeight()/2 <= 0) {
			//elaine.getSpeed().togglexDirection();
			this.stopY();
		}
	}
	
	public void draw( Canvas canvas ){
		//Where to draw the sprite
		Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight);
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);
		
		if ( showSource ) {
			canvas.drawBitmap(bitmap, 20, 150, null);
			Paint paint = new Paint();
			paint.setARGB(50, 0, 255, 0);
			canvas.drawRect(20 + (currentFrame * destRect.width()),150, 
							20 + (currentFrame * destRect.width()) + destRect.width(), 
							150 + destRect.height(), paint);
		}
	}
	
	public void update( long gameTime, Rect frameBox ){
		
		if ( walking ) {
			
			detectCollisions(frameBox);
			
			if (gameTime > frameTicker + framePeriod){
				frameTicker = gameTime;
				
				// increment the frame
				currentFrame++;
				if ( currentFrame >= frameNr ) {
					currentFrame = 0;
				}
			}
		}
		// define the rectangle to cut out sprite
		this.sourceRect.left = currentFrame * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;
		
		
		x += (speed.getXv() * speed.getxDirection());
		y += (speed.getYv() * speed.getyDirection());			
	}

}