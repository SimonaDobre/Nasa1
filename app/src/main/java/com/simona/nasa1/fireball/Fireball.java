package com.simona.nasa1.fireball;

public class Fireball {

    private String date;
    private String energy;
    private String latNr;
    private String latNS;
    private String longNr;
    private String longEW;

    public Fireball(String date, String energy, String latNr, String latNS, String longNr, String longEW) {
        this.date = date;
        this.energy = energy;
        this.latNr = latNr;
        this.latNS = latNS;
        this.longNr = longNr;
        this.longEW = longEW;
    }



    public String getDate() {
        return date;
    }

    public String getEnergy() {
        return energy;
    }

    public String getLatNr() {
        return latNr;
    }

    public String getLatNS() {
        return latNS;
    }

    public String getLongNr() {
        return longNr;
    }

    public String getLongEW() {
        return longEW;
    }
}
