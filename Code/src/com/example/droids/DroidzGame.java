package com.example.droids;

import java.util.ArrayList;

import com.example.droids.model.Droid;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class DroidzGame extends Game {
	
	private static final String TAG = Droidz.class.getSimpleName();
	private static final int MAX_NUMBER_DROIDS = 30;
	
	private DroidzPanel gamePanel;
	
	private Droid[] currentDroids;
	private int animationSpeedFactor;
	private int currentNumberDroids;
	private int index;
	private ArrayList<Rect>	currentObstacles;
	private boolean collisionDetection = true;	

	public DroidzGame(Context context) {
		super();
		
		//Create the game panel
		gamePanel = new DroidzPanel(context, this);
		
		//Droids specific
		animationSpeedFactor = 1;
		currentNumberDroids = 0;
		index = 0;
		currentDroids = new Droid[MAX_NUMBER_DROIDS];
		
		currentObstacles = new ArrayList<Rect>();
	}

	@Override
	public void start() {
		super.start();		
	}

	@Override
	public void end() {
		super.end();
	}
	/*
	public void addDroid(int x, int y) {
		
		if ( currentNumberDroids < MAX_NUMBER_DROIDS ) {
			if ( index < MAX_NUMBER_DROIDS ) {
				currentDroids[index] = new Droid(BitmapFactory.decodeResource(gamePanel.getResources(), R.drawable.droid_1),x, y );
				//droids[index] = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1),x, y, 3 );
				currentNumberDroids++;
				index++;
				
				//Update the floating display				
				if ( !floatingDisplay.updateParam("Droids", currentNumberDroids))
					makeToast("Param DROIDS couldn't be found");		
				
			} else {				
				droids[currentNumberDroids].setX(x);
				droids[currentNumberDroids].setY(y);
				currentNumberDroids++;
				
				//Update the floating display
				if ( !floatingDisplay.updateParam("Droids", currentNumberDroids))
					makeToast("Param DROIDS couldn't be found");
			}
		} else 
			makeToast("Max Number Of Droids Reached");
	}
	*/
	
	public int addObstacle(Rect obstacle) {
		currentObstacles.add(obstacle);
		return currentObstacles.indexOf(obstacle);
	}
	
	public void addObstacleAt(int index, Rect obstacle) {
		currentObstacles.add(index, obstacle);		
	}
	
	public int addObstacle(int x, int y, int width, int height) {
		 int left = (int) (x - (width / 2.0));
		 int right = left + width;
		 int top = (int) (y - (height / 2.0));
		 int bottom = top + height;
		 Rect obstacle = new Rect(left, top, right, bottom);
		currentObstacles.add(obstacle);
		return currentObstacles.indexOf(obstacle);
	}
	
	public void addObstacleAt(int index, int x, int y, int width, int height) {
		 int left = (int) (x - (width / 2.0));
		 int right = left + width;
		 int top = (int) (y - (height / 2.0));
		 int bottom = top + height;
		 Rect obstacle = new Rect(left, top, right, bottom);
		currentObstacles.add(index, obstacle);
	}
	
	public void removeObstacle(Rect obstacle) {
		currentObstacles.remove(obstacle);
	}
	
	
	
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////
	// Automatically generated setters and getters
	//////////////////////////////////////////////////////////////////////

	@Override
	public state getCurrentState() {
		return super.getCurrentState();
	}

	@Override
	public void setCurrentState(state currentState) {
		super.setCurrentState(currentState);
	}

	@Override
	public float getCurrentScore() {
		return super.getCurrentScore();
	}

	@Override
	public void setCurrentScore(float currentScore) {
		super.setCurrentScore(currentScore);
	}

	@Override
	public difficulty getCurrentDifficulty() {
		return super.getCurrentDifficulty();
	}

	@Override
	public void setCurrentDifficulty(difficulty currentDifficulty) {
		super.setCurrentDifficulty(currentDifficulty);
	}

	@Override
	public int getCurrentLevel() {
		return super.getCurrentLevel();
	}

	@Override
	public void setCurrentLevel(int currentLevel) {
		super.setCurrentLevel(currentLevel);
	}

	@Override
	public mode getCurrentMode() {
		return super.getCurrentMode();
	}

	@Override
	public void setCurrentMode(mode currentMode) {
		super.setCurrentMode(currentMode);
	}

	@Override
	public int getCurrentPlayerNumber() {
		return super.getCurrentPlayerNumber();
	}

	@Override
	public void setCurrentPlayerNumber(int currentPlayerNumber) {
		super.setCurrentPlayerNumber(currentPlayerNumber);
	}

	@Override
	public float getCurrentGoal() {
		return super.getCurrentGoal();
	}

	@Override
	public void setCurrentGoal(float currentGoal) {
		super.setCurrentGoal(currentGoal);
	}

	public DroidzPanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(DroidzPanel gamePanel) {
		this.gamePanel = gamePanel;
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
