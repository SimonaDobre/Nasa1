package com.simona.nasa1.asteroid;

public class Asteroid {
    private String nameAsteroid;
    private String idAsteroid;
    private int diameterMin;
    private int diameterMax;
    private String speed;

    private String closestApproachDate;
    private String closestApproachDistance;

    private boolean dangerous;
    private boolean sentryObject;

    public Asteroid(String nameAsteroid, String idAsteroid, int diameterMin, int diameterMax,
                    boolean dangerous, String closestApproachDate, String speed,
                    String closestApproachDistance, boolean sentryObject) {
        this.nameAsteroid = nameAsteroid;
        this.idAsteroid = idAsteroid;
        this.diameterMin = diameterMin;
        this.diameterMax = diameterMax;
        this.speed = speed;
        this.closestApproachDate = closestApproachDate;
        this.closestApproachDistance = closestApproachDistance;
        this.dangerous = dangerous;
        this.sentryObject = sentryObject;
    }

    public String getClosestApproachDate() {
        return closestApproachDate;
    }

    public boolean isDangerous() {
        return dangerous;
    }

    public boolean isSentryObject() {
        return sentryObject;
    }

    public String getNameAsteroid() {
        return nameAsteroid;
    }

    public String getIdAsteroid() {
        return idAsteroid;
    }

    public int getDiameterMin() {
        return diameterMin;
    }

    public int getDiameterMax() {
        return diameterMax;
    }

    public String getSpeed() {
        return speed;
    }

    public String getClosestApproachDistance() {
        return closestApproachDistance;
    }


}
