package com.simona.nasa1.nearestAsteroids;

public class NearestAsteroid {

    private String name;
    private String dateOfApproach;
    private int speed;
    private int distance;

    public NearestAsteroid(String name, String dateOfApproach, int speed, int distance) {
        this.name = name;
        this.dateOfApproach = dateOfApproach;
        this.speed = speed;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getDateOfApproach() {
        return dateOfApproach;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDistance() {
        return distance;
    }
}
