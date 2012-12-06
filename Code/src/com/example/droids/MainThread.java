package com.example.droids;

import android.util.Log;
import android.view.SurfaceHolder;
import android.graphics.Canvas;

public class MainThread extends Thread {
	
	//Tag for logging on Android's Log
	private static final String TAG = MainThread.class.getSimpleName();
	//To be able to lock the surface when we draw
	private SurfaceHolder surfaceHolder;
	//To be able to draw on to the surface
	private MainGamePanel gamePanel;
	//flag to hold game state
	private boolean running;
	
	//Constructor
	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public void run() {
		Canvas canvas;
		long tickCount = 0L;
		Log.d(TAG, "Starting game loop");
		
		while (running) {
			canvas = null;
			//try locking the canvas for exclusive pixel editing on the surface			
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					//Update game state
					//draws the canvas on the panel
					this.gamePanel.onDraw(canvas);
				}
			} finally {
				//in case of an exception the surface is not left in an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			} //end finally
			tickCount++;			
		}
		Log.d(TAG,"Game loop executed " + tickCount + " times");
	}
}
