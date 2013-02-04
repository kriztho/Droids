package com.example.droids;

import java.util.ArrayList;

import android.graphics.Rect;

import com.example.droids.model.Droid;

public class DroidzState {
	
	private static final String TAG = Droidz.class.getSimpleName();
	
	// Current State
	private static final int MAX_NUMBER_DROIDS = 30;	
	private Droid[] currentDroids;
	private int animationSpeedFactor;
	private int currentNumberDroids;
	private int index;
	private ArrayList<Rect>	currentObstacles;
	private boolean collisionDetection = true;
	
	// Current Game options
	private Game currentGame;

	public DroidzState() {
		
		// Droids specific
		animationSpeedFactor = 1;
		currentNumberDroids = 0;
		index = 0;
		currentDroids = new Droid[MAX_NUMBER_DROIDS];		
		currentObstacles = new ArrayList<Rect>();
		
		// Game specific
		currentGame = new Game();
	}
	
	public final int getMaxNumberDroids(){
		return MAX_NUMBER_DROIDS;
	}
	
	public Droid[] getCurrentDroids() {
		return currentDroids;
	}

	public void setCurrentDroids(Droid[] currentDroids) {
		this.currentDroids = currentDroids;
	}

	public int getAnimationSpeedFactor() {
		return animationSpeedFactor;
	}

	public void setAnimationSpeedFactor(int animationSpeedFactor) {
		this.animationSpeedFactor = animationSpeedFactor;
	}

	public int getCurrentNumberDroids() {
		return currentNumberDroids;
	}

	public void setCurrentNumberDroids(int currentNumberDroids) {
		this.currentNumberDroids = currentNumberDroids;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ArrayList<Rect> getCurrentObstacles() {
		return currentObstacles;
	}

	public void setCurrentObstacles(ArrayList<Rect> currentObstacles) {
		this.currentObstacles = currentObstacles;
	}

	public boolean isCollisionDetection() {
		return collisionDetection;
	}

	public void setCollisionDetection(boolean collisionDetection) {
		this.collisionDetection = collisionDetection;
	}
	
}

