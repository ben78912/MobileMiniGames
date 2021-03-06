package com.example.game.TilesGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/** A dangerous tile. */
class DangerTile extends Tile {

  /**
   * Construct a dangerous tile at location (x, y).
   *
   * @param x: the x-coordinate of this tile.
   * @param y: the y-coordinate of this tile.
   */
  DangerTile(int x, int y) {
    super(x, y);
  }

  @Override
  void draw(Canvas canvas) {
    // Fill tile.
    paintRect.setStyle(Paint.Style.FILL);
    if (!touch) {
      paintRect.setColor(Color.WHITE);
    } else {
      paintRect.setColor(Color.RED);
    }
    canvas.drawRect(x, y, x + width, y + height, paintRect);

    super.draw(canvas); // Draw border of tile.
  }
}
