package com.simona.nasa1;

public class VremeaPeMarte {
    private String anotimp;
    private int temperaturaMin;
    private int temperaturaMax;
    private double vitezavantMin;
    private double vitezavantMax;
    private int presiunea;

    public VremeaPeMarte(String anotimp, int temperaturaMin, int temperaturaMax, double vitezavantMin, double vitezavantMax, int presiunea) {
        this.anotimp = anotimp;
        this.temperaturaMin = temperaturaMin;
        this.temperaturaMax = temperaturaMax;
        this.vitezavantMin = vitezavantMin;
        this.vitezavantMax = vitezavantMax;
        this.presiunea = presiunea;
    }

    public String getAnotimp() {
        return anotimp;
    }

    public int getTemperaturaMin() {
        return temperaturaMin;
    }

    public int getTemperaturaMax() {
        return temperaturaMax;
    }

    public double getVitezavantMin() {
        return vitezavantMin;
    }

    public double getVitezavantMax() {
        return vitezavantMax;
    }

    public int getPresiunea() {
        return presiunea;
    }
}
