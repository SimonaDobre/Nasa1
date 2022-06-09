package com.simona.nasa1;

public class AMDD {

    private String dataApropierii;
    private int distantaApropierii;
    private double procentSanseLovire;
    private int oSansaDInCate;
    private int energiaLovirii;
    private double procentSanseRatare;

    public AMDD(String dataApropierii, int distantaApropierii, double procentSanseLovire, int oSansaDInCate, int energiaLovirii, double procentSanseRatare) {
        this.dataApropierii = dataApropierii;
        this.distantaApropierii = distantaApropierii;
        this.procentSanseLovire = procentSanseLovire;
        this.oSansaDInCate = oSansaDInCate;
        this.energiaLovirii = energiaLovirii;
        this.procentSanseRatare = procentSanseRatare;
    }

    public String getDataApropierii() {
        return dataApropierii;
    }

    public int getDistantaApropierii() {
        return distantaApropierii;
    }

    public double getProcentSanseLovire() {
        return procentSanseLovire;
    }

    public int getoSansaDInCate() {
        return oSansaDInCate;
    }

    public int getEnergiaLovirii() {
        return energiaLovirii;
    }

    public double getProcentSanseRatare() {
        return procentSanseRatare;
    }
}
