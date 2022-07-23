package com.simona.nasa1.nearestAsteroids;

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

public class NearestAsteroidsUTILS {

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
            inputStreamReader.close();
            bufferedReader.close();
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

    private static ArrayList<NearestAsteroid> getNearestAsteroidsFromJSON(String x) {
        ArrayList<NearestAsteroid> arrayNearestAsteroids = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(x);
            JSONArray jsonData = root.getJSONArray("data");
            for (int i = 0; i < jsonData.length(); i++) {
                JSONArray currentObject = jsonData.getJSONArray(i);
                String name = currentObject.getString(0);
                String dateOfApproach = currentObject.getString(3);
                String distance = currentObject.getString(4);
                distance = distance.substring(0, 5);
                double distDbl = Double.parseDouble(distance);
                distDbl *= 150000000;
                int distInt = (int) distDbl;
                String speed = currentObject.getString(7);
//                String speedFinal = speed.substring(0, 4);

                double vit = Double.parseDouble(speed);
                vit = vit * 3600;
                int speedFin = (int) vit;

                arrayNearestAsteroids.add(new NearestAsteroid(name, dateOfApproach, speedFin, distInt));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayNearestAsteroids;
    }


    public static ArrayList<NearestAsteroid> getDatesOfClosnessApproach(String x) {
        URL myURL = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(myURL);
        ArrayList<NearestAsteroid> arrayClosenessDates = getNearestAsteroidsFromJSON(answer);
        return arrayClosenessDates;
    }


}
