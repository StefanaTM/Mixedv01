package com.game.mixed;

public class PlayerNameHelperClass {
    private String playerName;
    private Integer pin;

    public PlayerNameHelperClass() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public PlayerNameHelperClass(String playerName, Integer pin) {
        this.playerName = playerName;
        this.pin = pin;
    }
}
