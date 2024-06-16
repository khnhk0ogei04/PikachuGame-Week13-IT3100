package model;

import javafx.scene.control.Button;

import java.util.Timer;
import java.util.TimerTask;

public abstract class PikachuGame {
    protected int rows;
    protected int cols;
    protected int timeLimit;
    protected Button[][] buttons;
    public Timer timer;
    protected int shuffleCount;
    protected int remainingTime;
    protected int maxShuffleCount;
    protected int[][] grid;
    protected int lives;
    protected int score;

    public PikachuGame(int rows, int cols, int timeLimit, int maxShuffleCount) {
        this.rows = rows;
        this.cols = cols;
        this.timeLimit = timeLimit;
        this.maxShuffleCount = maxShuffleCount;
        this.shuffleCount = 0;
        this.buttons = new Button[rows][cols];
        this.grid = new int[rows][cols];
        this.timer = new Timer();
        this.remainingTime = timeLimit;
        this.lives = 3;
        this.score = 0;
    }

    public abstract void shuffleButtons();
    public abstract void manualShuffle();
    public abstract boolean canMatch(Button btn1, Button btn2);
    public abstract boolean checkWin();
    public abstract void decreaseHeart();
    public abstract int[] findHint();
    public void startGame() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                remainingTime--;
                if (remainingTime <= 0) {
                    endGame();
                }
            }
        }, 1000, 1000);
    }
    public boolean shuffle() {
    	if (shuffleCount < this.maxShuffleCount) {
    		shuffleCount += 1;
    		shuffleButtons();
    		return true;
    	}
    	return false;
    }
    public boolean outOfLives() {
    	return lives <= 0;
    }
    public void endGame() {
    	timer.cancel();
    }
//    public void updateTimeDisplay() {
//    	  TODO generated methods 
//        int minutes = remainingTime / 60;
//        int seconds = remainingTime % 60;
//        timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
//    }

}
