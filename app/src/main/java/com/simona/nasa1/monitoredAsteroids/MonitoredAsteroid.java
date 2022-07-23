package com.simona.nasa1.monitoredAsteroids;

import java.util.ArrayList;

public class MonitoredAsteroid {

    private String name;
    private int diameter;
    private int speed;
    private String lastObservationDate;
    private ArrayList<String> nextObservationsList;
    private double hitProbabilityPercent;
    private double missProbabilityPercent;
    private double aChanceOutOfHowMany;


    public MonitoredAsteroid(String name, int diameter, int speed, String lastObservationDate,
                             ArrayList<String> nextObservationsList, double hitProbabilityPercent,
                             double missProbabilityPercent, double aChanceOutOfHowMany) {
        this.name = name;
        this.diameter = diameter;
        this.speed = speed;
        this.lastObservationDate = lastObservationDate;
        this.nextObservationsList = nextObservationsList;
        this.hitProbabilityPercent = hitProbabilityPercent;
        this.missProbabilityPercent = missProbabilityPercent;
        this.aChanceOutOfHowMany = aChanceOutOfHowMany;
    }

    public String getName() {
        return name;
    }

    public int getDiameter() {
        return diameter;
    }

    public int getSpeed() {
        return speed;
    }

    public String getLastObservationDate() {
        return lastObservationDate;
    }

    public ArrayList<String> getNextObservationsList() {
        return nextObservationsList;
    }

    public double getHitProbabilityPercent() {
        return hitProbabilityPercent;
    }

    public double getMissProbabilityPercent() {
        return missProbabilityPercent;
    }

    public double getaChanceOutOfHowMany() {
        return aChanceOutOfHowMany;
    }
}
