package com.example.game.DataBase;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UserRepository {
  private UserAccountDao userAccountDao;
  private UserScoresDao userScoresDao;
  private int userHighScore;
  private double userAverageScore;
  private int userMinTime;
  private int userAverageTime;
  private String userName;
  private String password;

  // Used this constructor for login/registration
  public UserRepository(Context context, String userName, String password) {
    UserDatabase db = UserDatabase.getInstance(context);
    userAccountDao = db.userAccountDao();
    this.userName = userName;
    this.password = password;
  }

  // Use this constructor for everything else
  public UserRepository(Context context, String userName) {
    UserDatabase db = UserDatabase.getInstance(context);
    userAccountDao = db.userAccountDao();
      userScoresDao = db.userScoresDao();
    this.userName = userName;
  }

  public boolean validateCredentials() {
    UserAccount ac = new UserAccount(userName, password);
    validateCredentialsAsyncTask v = new validateCredentialsAsyncTask(userAccountDao);
    try {
      v.execute(ac).get();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return v.isSuccess();
  }

  public boolean addUser() {
    UserAccount ac = new UserAccount(userName, password);
    ac.setAmount(0);
    addUserAsyncTask a = new addUserAsyncTask(userAccountDao);
    try {
      a.execute(ac).get();
    } catch (Exception e) {
      e.printStackTrace();
    }
    // try{a.get()}catch()
    return a.isSuccess();
  }

  /* Use this method whenever need to get User, can easily check user's
   *? amount once you retrieve the user by calling user.getAmount()
   */
  public UserAccount getUser(String userName) {
      getUserAsyncTask a = new getUserAsyncTask(userAccountDao);
      try {
          a.execute(userName).get();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return a.getUserAccount();
  }

  // Use this to update User's amount
  public void updateUserAmount(String userName, int costOfItem) {
      UserAccount ac = getUser(userName);
      ac.setAmount(ac.getAmount() - costOfItem);
      updateUserAmountAsyncTask a = new updateUserAmountAsyncTask(userAccountDao);
      try {
          a.execute(ac).get();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  // Call this when the game ends
  public void addUserScore(UserScores userScores) {
    addUserScoreAsyncTask a = new addUserScoreAsyncTask(userScoresDao);
      try {
      a.execute(userScores).get();
      Log.i("ADD_USER_SCORE", "user score successfully recorded");
      } catch (Exception e) {
      e.printStackTrace();
    }
  }

    public int getUserHighScore(String userName, String gameType) {
        StatsTaskParam s = new StatsTaskParam(userName, gameType);
        getUserHighScoreAsyncTask a = new getUserHighScoreAsyncTask(userScoresDao);
        try {
            a.execute(s).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a.getUserHighScore();
  }

    public double getUserAverageScore(String userName, String gameType) {
        StatsTaskParam s = new StatsTaskParam(userName, gameType);
        getUserAverageScoreAsyncTask a = new getUserAverageScoreAsyncTask(userScoresDao);
        try {
            a.execute(s).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a.getUserAverageScore();
  }

  public int getUserMinTime() {
    return userMinTime;
  }

    public int getUserAverageTime() {
    return userAverageTime;
  }

    public void resetUserStatistics(String userName) {
        removeUserStatisticsAsyncTask a = new removeUserStatisticsAsyncTask(userScoresDao);
        try {
            a.execute(userName).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  public static class addUserAsyncTask extends AsyncTask<UserAccount, Void, Boolean> {

    private UserAccountDao userAccountDao;
    private boolean success;

    addUserAsyncTask(UserAccountDao userAccountDao) {
      this.userAccountDao = userAccountDao;
      this.success = false;
    }

    boolean isSuccess() {
      return success;
    }

    @Override
    protected Boolean doInBackground(UserAccount... userAccounts) {
      System.out.println("l_1");
      if (userAccountDao.getUserAccountByUsername(userAccounts[0].getUserName()) == null) {
        userAccountDao.insert(userAccounts[0]);
        System.out.println("l_1_1");
        this.success = true;
        return true;
      }
      this.success = false;
      return false;
    }
  }

  public static class validateCredentialsAsyncTask extends AsyncTask<UserAccount, Void, Boolean> {

    private UserAccountDao userAccountDao;
    private boolean success;

    validateCredentialsAsyncTask(UserAccountDao userAccountDao) {
      this.userAccountDao = userAccountDao;
      this.success = false;
    }

      boolean isSuccess() {
      return success;
    }

    @Override
    protected Boolean doInBackground(UserAccount... userAccounts) {
      String userName = userAccounts[0].getUserName();
      String password = userAccounts[0].getPassword();
      this.success = userAccountDao.getUserCredentials(userName, password) != null;
      return this.success;
    }
  }

    public static class addUserScoreAsyncTask extends AsyncTask<UserScores, Void, Void> {
    private UserScoresDao userScoresDao;

    addUserScoreAsyncTask(UserScoresDao userScoresDao) {
      this.userScoresDao = userScoresDao;
    }

    @Override
    protected Void doInBackground(UserScores... userScores) {
      userScoresDao.insert(userScores[0]);
      return null;
    }
  }

    public static class getUserAsyncTask extends AsyncTask<String, Void, UserAccount> {
        private UserAccountDao userAccountDao;
        private UserAccount userAccount;

        getUserAsyncTask(UserAccountDao userAccountDao) {
            this.userAccountDao = userAccountDao;
        }

        UserAccount getUserAccount() {
            return userAccount;
        }

        @Override
        protected UserAccount doInBackground(String... strings) {
            userAccount = userAccountDao.getUserAccountByUsername(strings[0]);
            return userAccount;
        }
  }

    public static class updateUserAmountAsyncTask extends AsyncTask<UserAccount, Void, Void> {
        private UserAccountDao userAccountDao;

        updateUserAmountAsyncTask(UserAccountDao userAccountDao) {
            this.userAccountDao = userAccountDao;
        }

        @Override
        protected Void doInBackground(UserAccount... userAccounts) {
            userAccountDao.update(userAccounts[0]);
            return null;
        }
    }

    public static class getUserHighScoreAsyncTask extends AsyncTask<StatsTaskParam, Void, Integer> {
        private UserScoresDao userScoresDao;
        private int userHighScore;

        getUserHighScoreAsyncTask(UserScoresDao userScoresDao) {
            this.userScoresDao = userScoresDao;
        }

        int getUserHighScore() {
            return userHighScore;
        }

        @Override
        protected Integer doInBackground(StatsTaskParam... statsTaskParams) {
            String userName = statsTaskParams[0].getUserName();
            String gameType = statsTaskParams[0].getGameType();
            userHighScore = userScoresDao.getUserHighScore(userName, gameType);
            return userHighScore;
        }
    }

    public static class getUserAverageScoreAsyncTask extends AsyncTask<StatsTaskParam, Void, Double> {
        private UserScoresDao userScoresDao;
        private double userAverageScore;

        getUserAverageScoreAsyncTask(UserScoresDao userScoresDao) {
            this.userScoresDao = userScoresDao;
        }

        double getUserAverageScore() {
            return userAverageScore;
        }

        @Override
        protected Double doInBackground(StatsTaskParam... statsTaskParams) {
            String userName = statsTaskParams[0].getUserName();
            String gameType = statsTaskParams[0].getGameType();
            userAverageScore = userScoresDao.getUserAvgScore(userName, gameType);
            return userAverageScore;
        }
    }

    public static class removeUserStatisticsAsyncTask extends AsyncTask<String, Void, Void> {
        private UserScoresDao userScoresDao;

        public removeUserStatisticsAsyncTask(UserScoresDao userScoresDao) {
            this.userScoresDao = userScoresDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            userScoresDao.deleteUserScores(strings[0]);
            return null;
        }
    }

    private class StatsTaskParam {
        String userName;
        String gameType;

        StatsTaskParam(String userName, String gameType) {
            this.userName = userName;
            this.gameType = gameType;
        }

        public String getUserName() {
            return userName;
        }

        public String getGameType() {
            return gameType;
        }
    }
}
