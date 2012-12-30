package com.example.droids;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Background {
	
	private Bitmap bitmap;
	private int width;
	private int height;
	private ArrayList<Rect> obstacles;
	private boolean collisionDetection = true;
	private Rect frameBox;

	public Background(Bitmap bitmap, Rect frameBox) {
		this.bitmap = bitmap;
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		this.frameBox = frameBox;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public Drawable getDrawable() {
		return new BitmapDrawable(bitmap);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ArrayList<Rect> getObstacles() {
		return obstacles;
	}

	public boolean isCollisionDetection() {
		return collisionDetection;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setObstacles(ArrayList<Rect> obstacles) {
		this.obstacles = obstacles;
	}

	public void setCollisionDetection(boolean collisionDetection) {
		this.collisionDetection = collisionDetection;
	}

	public void render(Canvas canvas) {
		//canvas.drawBitmap(bitmap, 0, 0, null);
		canvas.drawBitmap(bitmap, null, frameBox, null);
	}
}
