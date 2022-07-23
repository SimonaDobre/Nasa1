package com.simona.nasa1.amdd;

public class AMDD {

    private String date;
    private int distance;
    private double hitChancePercent;
    private int aChanceOutOfHowMany;
    private int hitEnergy;
    private double missChancePercent;

    public AMDD(String date, int distance,
                double hitChancePercent, int aChanceOutOfHowMany,
                int hitEnergy, double missChancePercent) {
        this.date = date;
        this.distance = distance;
        this.hitChancePercent = hitChancePercent;
        this.aChanceOutOfHowMany = aChanceOutOfHowMany;
        this.hitEnergy = hitEnergy;
        this.missChancePercent = missChancePercent;
    }

    public String getDate() {
        return date;
    }

    public int getDistance() {
        return distance;
    }

    public double getHitChancePercent() {
        return hitChancePercent;
    }

    public int getaChanceOutOfHowMany() {
        return aChanceOutOfHowMany;
    }

    public int getHitEnergy() {
        return hitEnergy;
    }

    public double getMissChancePercent() {
        return missChancePercent;
    }
}
