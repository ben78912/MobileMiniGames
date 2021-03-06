package com.example.game.TapiocaLauncher;

import java.util.List;
import java.util.Observable;

// A GameFacade which represents the Model of the MVC
public class GameFacade extends Observable {

  private Launcher launcher; // The launcher ball the player launches
  private List<Ball> balls; // A list of balls that the user wants to destroy

  private int score; // the current score
  private int level = 1; // the current level
  private boolean gameOver = false; // if the game is over or not

  public GameFacade(Launcher launcher, List<Ball> balls) {

    this.launcher = launcher;
    this.balls = balls;
    this.score = 0;
  }

  public Launcher getLauncher() {
    return launcher;
  }

  public List<Ball> getBalls() {
    return balls;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }

  // Class is observed by VisualView and GameActivity which get notified if this class changes
  void update() {
    setChanged();
    notifyObservers(this);
  }
}
