package com.example.game.TapiocaLauncher;

import java.util.List;
import java.util.Observable;

public class GameFacade extends Observable {

    private Launcher launcher;
    private List<Ball> balls;

    private int score;
    private int level = 2;
    private boolean gameOver = false;

    public GameFacade(Launcher launcher, List<Ball> balls){

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

    void update() {
        setChanged();
        notifyObservers(this);
    }

}