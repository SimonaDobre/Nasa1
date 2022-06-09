package com.simona.nasa1;

public class AsterPericulosApropieri {

    private String nume;
    private String dataApropierii;
    private int viteza;
    private int distanta;

    public AsterPericulosApropieri(String nume, String dataApropierii, int viteza, int distanta) {
        this.nume = nume;
        this.dataApropierii = dataApropierii;
        this.viteza = viteza;
        this.distanta = distanta;
    }

//    public AsterPericulosApropieri(String dataApropierii, int viteza, String distanta) {
//        this.dataApropierii = dataApropierii;
//        this.viteza = viteza;
//        this.distanta = distanta;
//    }

    public String getNume() {
        return nume;
    }

    public String getDataApropierii() {
        return dataApropierii;
    }

    public int getViteza() {
        return viteza;
    }

    public int getDistanta() {
        return distanta;
    }
}
