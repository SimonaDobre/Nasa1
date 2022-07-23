package com.simona.nasa1.marsWeather;

public class MarsWeather {
    private String season;
    private int temperatureMin;
    private int temperatureMax;
    private double windSpeedMin;
    private double windSpeedMax;
    private int pressure;

    public MarsWeather(String season, int temperatureMin, int temperaturaMax, double windSpeedMin, double windSpeedMax, int pressure) {
        this.season = season;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperaturaMax;
        this.windSpeedMin = windSpeedMin;
        this.windSpeedMax = windSpeedMax;
        this.pressure = pressure;
    }

    public String getSeason() {
        return season;
    }

    public int getTemperatureMin() {
        return temperatureMin;
    }

    public int getTemperatureMax() {
        return temperatureMax;
    }

    public double getWindSpeedMin() {
        return windSpeedMin;
    }

    public double getWindSpeedMax() {
        return windSpeedMax;
    }

    public int getPressure() {
        return pressure;
    }
}
