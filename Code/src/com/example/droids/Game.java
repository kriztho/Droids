package com.example.droids;

/* Class that models the game state and options */

enum difficulty {
	easy, normal, hard
};

enum mode {
	single, versus
};

enum state {
	playing, over, paused
};

public class Game {
	
	state currentState;
	float currentScore;
	difficulty currentDifficulty;		// easy, normal, hard, etc...
	int currentLevel;			// it might represent levels of the game as in scenarios or stages
	mode currentMode;			// if different game modes for different ways of playing
	int currentPlayerNumber;	// the number of players
	float currentGoal;			// a goal that needs to be reached in order to advance throughout the game

	public Game() {
		currentState = state.over;
		currentScore = 0;
		currentDifficulty = difficulty.normal;
		currentLevel = 0;
		currentMode = mode.single;
		currentPlayerNumber = 1;
		currentGoal = 10; 
	}
	
	public void start(){
		currentState = state.playing;
	}
	
	public void end(){
		currentState = state.over;
	}

	public state getCurrentState() {
		return currentState;
	}

	public void setCurrentState(state currentState) {
		this.currentState = currentState;
	}

	public float getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(float currentScore) {
		this.currentScore = currentScore;
	}

	public difficulty getCurrentDifficulty() {
		return currentDifficulty;
	}

	public void setCurrentDifficulty(difficulty currentDifficulty) {
		this.currentDifficulty = currentDifficulty;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public mode getCurrentMode() {
		return currentMode;
	}

	public void setCurrentMode(mode currentMode) {
		this.currentMode = currentMode;
	}

	public int getCurrentPlayerNumber() {
		return currentPlayerNumber;
	}

	public void setCurrentPlayerNumber(int currentPlayerNumber) {
		this.currentPlayerNumber = currentPlayerNumber;
	}

	public float getCurrentGoal() {
		return currentGoal;
	}

	public void setCurrentGoal(float currentGoal) {
		this.currentGoal = currentGoal;
	}
	
	
}
