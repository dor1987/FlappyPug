package com.dorashush.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.dorashush.game.FlappyPug;

/**
 * Created by Dor on 06/10/18.
 */

public class UserProfile {
    private static UserProfile INSTANCE = null;

    private String name;
    private int money;
    private int spins;
    private int diamonds;
    private int invinciblityLevel;
    private int speedReduceLevel;
    private int timeBonusLevel;
    private int startingTimeLevel;
    private float localHighScore;
    private String localHighScoreName;
    private float volume;
    private boolean vibration;

    private Preferences flappyDogPreferences;

    private UserProfile() {};

    public static UserProfile getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserProfile();
        }
        return(INSTANCE);
    }

    public void userProfileInit() {
        this.flappyDogPreferences = Gdx.app.getPreferences("flappyDogPreferences");
        this.name = flappyDogPreferences.getString("name","No name stored");
        this.money = flappyDogPreferences.getInteger("money",00000);
        this.spins = flappyDogPreferences.getInteger("spins",00000);
        this.diamonds = flappyDogPreferences.getInteger("diamonds",00000);
        this.invinciblityLevel = flappyDogPreferences.getInteger("invinclevel",0);
        this.speedReduceLevel = flappyDogPreferences.getInteger("speedreducelevel",0);
        this.timeBonusLevel = flappyDogPreferences.getInteger("timebonuslevel",0);
        this.startingTimeLevel = flappyDogPreferences.getInteger("startingtime",0);
        this.localHighScore = flappyDogPreferences.getFloat("highScore",0f);
        this.localHighScoreName = flappyDogPreferences.getString("highScoreName","");
        this.volume = flappyDogPreferences.getFloat("volume",0.5f);
        this.vibration = flappyDogPreferences.getBoolean("vibration",true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        flappyDogPreferences.putString("name",name);
        flappyDogPreferences.flush();

    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;

        flappyDogPreferences.putInteger("money",money);
        flappyDogPreferences.flush();
    }

    public int getSpins() {
        return spins;
    }

    public void setSpins(int spins) {
        this.spins = spins;

        flappyDogPreferences.putInteger("spins",spins);
        flappyDogPreferences.flush();
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
        flappyDogPreferences.putInteger("diamonds",diamonds);
        flappyDogPreferences.flush();
    }

    public int getInvinciblityLevel() {
        return invinciblityLevel;
    }

    public void setInvinciblityLevel(int invinciblityLevel) {
        this.invinciblityLevel = invinciblityLevel;
        flappyDogPreferences.putInteger("invinclevel",invinciblityLevel);
        flappyDogPreferences.flush();
    }

    public int getSpeedReduceLevel() {
        return speedReduceLevel;
    }

    public void setSpeedReduceLevel(int speedReduceLevel) {
        this.speedReduceLevel = speedReduceLevel;
        flappyDogPreferences.putInteger("speedreducelevel",speedReduceLevel);
        flappyDogPreferences.flush();
    }

    public int getTimeBonusLevel() {
        return timeBonusLevel;
    }

    public void setTimeBonusLevel(int timeBonusLevel) {
        this.timeBonusLevel = timeBonusLevel;
        flappyDogPreferences.putInteger("timebonuslevel",timeBonusLevel);
        flappyDogPreferences.flush();
    }

    public int getStartingTimeLevel() {
        return startingTimeLevel;
    }

    public void setStartingTimeLevel(int startingTimeLevel) {
        this.startingTimeLevel = startingTimeLevel;
        flappyDogPreferences.putInteger("startingtime",startingTimeLevel);
        flappyDogPreferences.flush();
    }

    public float getLocalHighScore() {
        return localHighScore;
    }

    public void setLocalHighScore(float localHighScore) {
        this.localHighScore = localHighScore;

        flappyDogPreferences.putFloat("highScore",localHighScore);
        flappyDogPreferences.flush();
    }

    public String getLocalHighScoreName() {
        return localHighScoreName;
    }

    public void setLocalHighScoreName(String localHighScoreName) {
        this.localHighScoreName = localHighScoreName;

        flappyDogPreferences.putString("highScoreName",localHighScoreName);
        flappyDogPreferences.flush();
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;

        flappyDogPreferences.putFloat("volume",volume);
        flappyDogPreferences.flush();

    }

    public boolean isVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
        flappyDogPreferences.putBoolean("vibration",vibration);
    }
}
