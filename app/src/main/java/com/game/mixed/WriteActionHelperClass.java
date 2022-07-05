package com.game.mixed;

public class WriteActionHelperClass {
    private String action;
    private Integer idAction;
    private Integer pin;

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getIdAction() {
        return idAction;
    }

    public void setIdAction(Integer idAction) {
        this.idAction = idAction;
    }

    public WriteActionHelperClass(String action, Integer idAction, Integer pin) {
        this.action = action;
        this.idAction = idAction;
        this.pin = pin;
    }

    public WriteActionHelperClass() {
    }
}
