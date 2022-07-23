package com.simona.nasa1.asteroidApproachingDates;

import com.simona.nasa1.nearestAsteroids.NearestAsteroid;

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

public class AsteroidApproachingDatesUTILS {

    private static URL createURL(String x){
        URL myURL = null;
        try {
            myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return myURL;
    }

    private static String mAAKKttpRequestAndReadFromStream(URL myURL) {
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


    private static  ArrayList<NearestAsteroid> getArrayFromJson(String a){
        ArrayList<NearestAsteroid> arrayClosenessdates = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(a);
            String name = root.getString("name");
            JSONArray closeAproachDatass = root.getJSONArray("close_approach_data");

            for (int i=0; i<closeAproachDatass.length(); i++){
                JSONObject currentJsonObject = closeAproachDatass.getJSONObject(i);
                String dateOfCloseness = currentJsonObject.getString("close_approach_date_full");
                if (dateOfCloseness.equals(null) || dateOfCloseness.equals("null")){
                    dateOfCloseness = currentJsonObject.getString("close_approach_date");
                }
                JSONObject relativeSpeed = currentJsonObject.getJSONObject("relative_velocity");
                String speedKM = relativeSpeed.getString("kilometers_per_hour");
                String finalSpeed = speedKM.substring(0, speedKM.indexOf("."));
                int speedFinal = Integer.parseInt(finalSpeed);

                JSONObject missDistance = currentJsonObject.getJSONObject("miss_distance");
                String distanceKM = missDistance.getString("kilometers");
                String distance = distanceKM.substring(0, distanceKM.indexOf("."));
                double distanceDbl = Double.parseDouble(distance);
                int distanceInt = (int) distanceDbl;

                arrayClosenessdates.add(new NearestAsteroid(name, dateOfCloseness, speedFinal, distanceInt));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayClosenessdates;
    }

    public static ArrayList<NearestAsteroid> getArrayOfClosenessDates(String x){
        URL urlx = createURL(x);
        String answer = mAAKKttpRequestAndReadFromStream(urlx);
        ArrayList<NearestAsteroid> listOfCloseness = getArrayFromJson(answer);
        return listOfCloseness;
     }


}
