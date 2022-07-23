package com.simona.nasa1.monitoredAsteroids;

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

public class MonitoredAsteroidsUTILS {

    private static URL createURL(String x) {
        URL myURL = null;
        try {
            myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return myURL;
    }

    private static String makeHttpRequestAndReadFromStream(URL myURL) {
        String answer = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) myURL.openConnection();
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

    private static ArrayList<MonitoredAsteroid> getMonitoredAsteroidsfromJson(String raspuns) {
        ArrayList<MonitoredAsteroid> arrayMonitorisedAster = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(raspuns);
            JSONArray datesJsonArray = root.getJSONArray("data");

            for (int i = 0; i < datesJsonArray.length(); i++) {
                JSONObject currentObject = datesJsonArray.getJSONObject(i);
                String name = currentObject.getString("fullname");
                String diam = currentObject.getString("diameter");
                diam = diam.substring(2);
                int diametr = Integer.parseInt(diam);
                String v1 = currentObject.getString("v_inf");
                String speed1 = "";
                double speedDbl = 0;
                int speedInteger = 0;
                if (v1.length() >= 5) {
                    speed1 = v1.substring(0, 4);
                } else if (v1.length() == 4) {
                    speed1 = v1.substring(0, 3);
                } else if (v1.length() == 3) {
                    speed1 = v1.substring(0, 2);
                } else if (v1.length() == 2) {
                    speed1 = v1.substring(0, 1);
                }

                try {
                    if (!(speed1.equals("null") || speed1.equals("nan"))) {
                        speedDbl = Double.parseDouble(speed1);
                    }
                    speedInteger = (int) speedDbl * 3600;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                String maxHazard = currentObject.getString("ip");
                double chancesOfImpactPercet = 0, missChancesPercent = 0, missChancesNumber = 0;
                String t1 = "", t2 = "";
                // there are some ip-s which contains a comma, not E-7 or E-2
                if (maxHazard.contains("E") || maxHazard.contains("e")) {
                    t1 = maxHazard.substring(0, 3);
                    t2 = maxHazard.substring(maxHazard.length() - 2, maxHazard.length());
                    double firstpart = Double.parseDouble(t1);
                    int powerOf10 = 0;
                    if (t2.charAt(0) == 0) {
                        powerOf10 = t2.charAt(1);
                    } else {
                        powerOf10 = Integer.parseInt(t2);
                    }
                    double a1 = firstpart / (Math.pow(10, powerOf10));
                    missChancesPercent = 100 - a1;
                    missChancesNumber = 1 / a1;
                    chancesOfImpactPercet = a1;
                    // DecimalFormat decimFormat = new DecimalFormat("0.0000000000");
                    //  sanseDeImpactProcent = Double.parseDouble(decimFormat.format(a1));
                    // sanseDeImpactProcent = a1*Math.pow(10, putereaLui10-1);
                } else {
                    double maxHazDOUBLE = Double.parseDouble(maxHazard);
                    chancesOfImpactPercet = maxHazDOUBLE * 100;
                    missChancesPercent = 100 - maxHazDOUBLE;
                    missChancesNumber = 1 / maxHazDOUBLE;
                }
                String observation = currentObject.getString("last_obs");

                ArrayList<String> nextDatesOfClosness = new ArrayList<>();
                arrayMonitorisedAster.add(new MonitoredAsteroid(name, diametr, speedInteger, observation,
                        nextDatesOfClosness, chancesOfImpactPercet,
                        missChancesPercent, missChancesNumber));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayMonitorisedAster;
    }

    public static ArrayList<MonitoredAsteroid> getMonitoredAsteroids(String x) {
        URL myurl = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(myurl);
        ArrayList<MonitoredAsteroid> arrayMonitAster = getMonitoredAsteroidsfromJson(answer);
        return arrayMonitAster;
    }

}
