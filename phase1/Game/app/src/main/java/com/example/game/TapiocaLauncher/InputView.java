package com.example.game.TapiocaLauncher;

import android.view.MotionEvent;

import com.example.game.ScoreManager;

import static com.example.game.TapiocaLauncher.VisualView.launcher;

class InputView {

  private int screenX, screenY;
  private double startX = 0, startY = 0, endX = 0, endY = 0;
  private boolean ballClicked;

  InputView(int screenX, int screenY) {
    this.screenX = screenX;
    this.screenY = screenY;
  }

  void setDownAction(MotionEvent event) {
    if ((event.getX() >= launcher.getX()
            && event.getX() <= (launcher.getX() + launcher.getHeight()))
        && (event.getY() >= launcher.getY()
            && event.getY() <= (launcher.getY() + launcher.getWidth()))) {
      startX = event.getX();
      startY = event.getY();
      ballClicked = true;
      // Log.d("", "onTouchEvent: Motion Down  x-val: " + startX);
      // Log.d("", "onTouchEvent: Motion Down  y-val: " + startY);
    }
  }

  void setUpAction(MotionEvent event) {
    if (ballClicked) {
      endX = event.getX();
      endY = event.getY();
      launcher.moveBall(startX, startY, endX, endY);
      ballClicked = false;
    }
  }
}
