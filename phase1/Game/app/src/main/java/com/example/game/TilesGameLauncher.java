package com.example.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class TilesGameLauncher extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // Set the theme.
    SharedPreferences mSettings = this.getSharedPreferences("Settings", MODE_PRIVATE);
    ThemeManager.setTheme(TilesGameLauncher.this, mSettings.getInt("theme", -1));

    super.onCreate(savedInstanceState);
    setContentView(R.layout.tiles_game_launch);
  }

  /** Called when the user taps the 'PLAY' button */
  public void startTilesGame(View view) {
    Intent intent = new Intent(this, com.example.game.TilesGame.GameActivity.class);
    startActivity(intent);
  }

  /** Called when the user taps the 'EXIT' button */
  public void exitTilesGame(View view) {
    Intent intent = new Intent(this, com.example.game.MainActivity.class);
    startActivity(intent);
  }
}
