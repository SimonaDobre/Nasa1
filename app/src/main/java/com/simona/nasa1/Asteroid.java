package com.simona.nasa1;

public class Asteroid {
    private String name;
    private String idul;
    private int diametruMin;
    private int diametruMax;
    private String viteza;

    private String closestApproachDate;
    private String distantaMinimaLaCareSeVaApropia;

    private boolean isDangerous;
    private boolean isSentryObject;

    public Asteroid(String name, String idul, int diametruMin, int diametruMax, boolean isDangerous, String closestApproaceDate, String viteza, String distantaMinimaLaCareSeVaApropia, boolean isSentryObject) {
        this.name = name;
        this.idul = idul;
        this.diametruMin = diametruMin;
        this.diametruMax = diametruMax;
        this.isDangerous = isDangerous;
        this.closestApproachDate = closestApproaceDate;
        this.viteza = viteza;
        this.distantaMinimaLaCareSeVaApropia = distantaMinimaLaCareSeVaApropia;
        this.isSentryObject = isSentryObject;
    }

    public String getIdul() {
        return idul;
    }

    public String getName() {
        return name;
    }

    public int getDiametruMin() {
        return diametruMin;
    }

    public int getDiametruMax() {
        return diametruMax;
    }

    public boolean isDangerous() {
        return isDangerous;
    }

    public String getClosestApproachDate() {
        return closestApproachDate;
    }

    public String getViteza() {
        return viteza;
    }

    public String getDistantaMinimaLaCareSeVaApropia() {
        return distantaMinimaLaCareSeVaApropia;
    }

    public boolean isSentryObject() {
        return isSentryObject;
    }
}
