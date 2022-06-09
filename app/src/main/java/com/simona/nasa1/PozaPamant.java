package com.simona.nasa1;

public class PozaPamant {
    private String numePoza;
    private String data;
    private double lati;
    private double longi;
    private String caption;


    public PozaPamant(String urlPoza, String data, double lati, double longi, String caption) {
        this.numePoza = urlPoza;
        this.data = data;
        this.lati = lati;
        this.longi = longi;
        this.caption = caption;
    }

    public String getNumePoza() {
        return numePoza;
    }

    public String getData() {
        return data;
    }

    public double getLati() {
        return lati;
    }

    public double getLongi() {
        return longi;
    }

    public String getCaption() {
        return caption;
    }
}
