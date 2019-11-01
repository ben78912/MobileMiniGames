package com.example.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game.Save.User;

public class MazeGameLauncher extends AppCompatActivity {

  User usr;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // Set the theme.
    SharedPreferences mSettings = this.getSharedPreferences("Settings", MODE_PRIVATE);
    ThemeManager.setTheme(MazeGameLauncher.this, mSettings.getInt("theme", -1));

    super.onCreate(savedInstanceState);
    setContentView(R.layout.maze_game_launch);
    usr = (User) getIntent().getSerializableExtra("UserObject");
  }

  /** Called when the user taps the 'PLAY' button */
  public void startMazeGame(View view) {
    Intent intent = new Intent(this, com.example.game.MazeGame.MazeGameActivity.class);
    intent.putExtra("MazeGameLauncher", usr);
    startActivity(intent);
  }

  /** Called when the user taps the 'EXIT' button */
  public void exitMazeGame(View view) {
    Intent intent = new Intent(this, com.example.game.MainActivity.class);
    startActivity(intent);
  }
}
