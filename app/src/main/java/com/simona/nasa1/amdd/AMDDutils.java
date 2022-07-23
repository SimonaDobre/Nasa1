package com.simona.nasa1.amdd;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AMDDutils {

    private static URL createURL(String x) {
        URL myURL = null;
        try {
            myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return myURL;
    }

    private static String makeHttpRequestAndReadFromStream(URL myurl) {
        String answer = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) myurl.openConnection();
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String liniaCurenta = "";
            StringBuffer stringBuffer = new StringBuffer();
            while ((liniaCurenta = bufferedReader.readLine()) != null) {
                stringBuffer.append(liniaCurenta);
            }
            answer = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpURLConnection.disconnect();
        }
        return answer;
    }

    private static ArrayList<AMDD> getDetailsArrayFromJson(String raspuns) {
        ArrayList<AMDD> detailsArray = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(raspuns);
            JSONArray JSONdata = root.getJSONArray("data");
            for (int i = JSONdata.length() - 1; i >= 0; i--) {
                JSONObject currentObject = JSONdata.getJSONObject(i);

                String dateCloseness = currentObject.getString("date");
                String dateFinal = dateCloseness.substring(0, 10);
                String probability = currentObject.getString("ip");
                double impactChancesPercent = 0, missChancesPercent = 0;
                int aChanceOutOFHowMany = 0;
                String t1 = "", t2 = "";
                if (probability.contains("E") || probability.contains("e")) {
                    t1 = probability.substring(0, 3);
                    t2 = probability.substring(probability.length() - 2, probability.length());
                    double firstFactor = Double.parseDouble(t1);
                    int powerOf10 = 0;
                    if (t2.charAt(0) == 0) {
                        powerOf10 = t2.charAt(1);
                    } else {
                        powerOf10 = Integer.parseInt(t2);
                    }
                    double a1 = firstFactor / (Math.pow(10, powerOf10));
                    missChancesPercent = 100 - a1;
                    aChanceOutOFHowMany = (int) (1 / a1);
                    // sanseDeImpactProcent = a1*Math.pow(10, putereaLui10-1)/100;
                    impactChancesPercent = a1;
                } else {
                    double maxHazDOUBLE = Double.parseDouble(probability);
                    impactChancesPercent = maxHazDOUBLE * 100;
                    missChancesPercent = 100 - maxHazDOUBLE;
                    aChanceOutOFHowMany = (int) (1 / maxHazDOUBLE);
                }

                String energy = currentObject.getString("energy");
                double hitEnergyDbl = 0;
                if (energy.contains("E") || energy.contains("e")) {
                    t1 = energy.substring(0, 3);
                    t2 = energy.substring(energy.length() - 2);
                    double firstfactor = Double.parseDouble(t1);
                    int powerOf10 = 0;
                    if (t2.charAt(0) == 0) {
                        powerOf10 = t2.charAt(1);
                    } else {
                        powerOf10 = Integer.parseInt(t2);
                    }
                    hitEnergyDbl = firstfactor * (Math.pow(10, powerOf10));
                } else {
                    hitEnergyDbl = Double.parseDouble(energy);
                }
                int hitEnergyFinal = (int) hitEnergyDbl;

                Log.i("AICI utils dataFin = ", dateFinal);
                Log.i("AICI utils sanseImpa = ", impactChancesPercent + "");
                Log.i("AICI utils energyFIn = ", hitEnergyFinal + "");
                Log.i("AICI utils oSansaDi  = ", aChanceOutOFHowMany + "");

                detailsArray.add(new AMDD(dateFinal, 123456,
                        impactChancesPercent, aChanceOutOFHowMany, hitEnergyFinal, missChancesPercent));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return detailsArray;
    }

    public static ArrayList<AMDD> getMonitorizedAsteroidDetails(String x) {
        URL url = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(url);
        ArrayList<AMDD> detailsArray = getDetailsArrayFromJson(answer);
        return detailsArray;
    }


}
