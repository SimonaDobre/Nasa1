package com.simona.nasa1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Fireballs {

    private String data;
    private String energia;
    private String latNr;
    private String latNS;
    private String longNr;
    private String longEW;

    public Fireballs(String data, String energia, String latNr, String latNS, String longNr, String longEW) {
        this.data = data;
        this.energia = energia;
        this.latNr = latNr;
        this.latNS = latNS;
        this.longNr = longNr;
        this.longEW = longEW;
    }



    public String getData() {
        return data;
    }

    public String getEnergia() {
        return energia;
    }

    public String getLatNr() {
        return latNr;
    }

    public String getLatNS() {
        return latNS;
    }

    public String getLongNr() {
        return longNr;
    }

    public String getLongEW() {
        return longEW;
    }
}
