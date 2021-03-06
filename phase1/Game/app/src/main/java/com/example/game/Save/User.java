package com.example.game.Save;

import java.io.Serializable;

/** Class representing a User. */
public class User implements Serializable {

  private String username;
  private String password;
  /** userData is the player's saved information about our games. */
  private UserData userData;

  public User(String username, String password, UserData userData) {
    this.username = username;
    this.password = password;
    this.userData = userData;
    userData.setUserName(username);
  }

  /** Getter for username. */
  public String getUsername() {
    return username;
  }

  /** Getter for userdata. */
  public UserData getUserData() {
    return userData;
  }

  /** Returns whether given password matches the User's password. */
  boolean checkPassword(String inputPassword) {
    return password.equals(inputPassword);
  }
}
