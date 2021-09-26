package com.game.mixed;

public class RoomSettingsHelperClass {
    private Integer pin;
    private Integer nrCards;

    public RoomSettingsHelperClass() {
    }

    public RoomSettingsHelperClass(Integer pin, Integer nrCards) {
        this.pin = pin;
        this.nrCards = nrCards;
    }


    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public Integer getNrCards() {
        return nrCards;
    }

    public void setNrCards(Integer nrCards) {
        this.nrCards = nrCards;
    }

   /* @Override
    public String toString(){
        return "Test{"+
                ", pin="+pin+
                "Nr. cards="+nrCards + "}";
    }*/
}

