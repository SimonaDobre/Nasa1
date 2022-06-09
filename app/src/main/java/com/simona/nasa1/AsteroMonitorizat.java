package com.simona.nasa1;

import java.util.ArrayList;

public class AsteroMonitorizat {

    private String nume;
    private int diametru;
    private int viteza;
  //  private String maxHazard;
    private String ultimaObs;
    private ArrayList<String> urmatoareaObs;
    private double procentProbabilLovire;
    private double procentProbabilRATEU;
    private double oSansaDeLovireDinCate;

//    public AsteroMonitorizat(String nume, String diametru, String viteza, String maxHazard, String ultimaObs, String urmatoareaObs) {
//        this.nume = nume;
//        this.diametru = diametru;
//        this.viteza = viteza;
//       // this.maxHazard = maxHazard;
//        this.ultimaObs = ultimaObs;
//        this.urmatoareaObs = urmatoareaObs;
//    }


    public AsteroMonitorizat(String nume, int diametru, int viteza, String ultimaObs,
                             ArrayList<String> urmatoareaObs, double procentProbabilLovire,
                             double procentProbabilRATEU, double oSansaDeLovireDinCate) {
        this.nume = nume;
        this.diametru = diametru;
        this.viteza = viteza;
        this.ultimaObs = ultimaObs;
        this.urmatoareaObs = urmatoareaObs;
        this.procentProbabilLovire = procentProbabilLovire;
        this.procentProbabilRATEU = procentProbabilRATEU;
        this.oSansaDeLovireDinCate = oSansaDeLovireDinCate;
    }

    public String getNume() {
        return nume;
    }

    public int getDiametru() {
        return diametru;
    }

    public int getViteza() {
        return viteza;
    }

    //    public String getMaxHazard() {
//        return maxHazard;
//    }

    public String getUltimaObs() {
        return ultimaObs;
    }

    public ArrayList<String> getUrmatoareaObs() {
        return urmatoareaObs;
    }

    public void setUrmatoareaObs(ArrayList<String> urmatoareaObs) {
        this.urmatoareaObs = urmatoareaObs;
    }

    public double getProcentProbabilLovire() {
        return procentProbabilLovire;
    }

    public double getProcentProbabilRATEU() {
        return procentProbabilRATEU;
    }

    public double getoSansaDeLovireDinCate() {
        return oSansaDeLovireDinCate;
    }
}
