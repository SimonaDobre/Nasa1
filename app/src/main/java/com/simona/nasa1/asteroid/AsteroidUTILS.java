package com.simona.nasa1.asteroid;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AsteroidUTILS {

    private static URL createURL(String x) {
        URL myURL = null;
        try {
            myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        Log.i("TEST ", myURL.toString());
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
//        Log.i("TEST ", answer);
        return answer;
    }

    private static ArrayList<Asteroid> getAsteroidsList(String x) {
        ArrayList<Asteroid> arrayAsteroids = new ArrayList<>();
        JSONObject nearEarthObj;

        try {
            JSONObject root = new JSONObject(x);
            nearEarthObj = root.getJSONObject("near_earth_objects");
//            Log.i("TESTARE ", root.toString());

            for (int i = 0; i < 8; i++) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, +i);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dayNameJson = dateFormat.format(cal.getTime());
                JSONArray jsonOfTheDay = nearEarthObj.getJSONArray(dayNameJson);

                for (int j = 0; j < jsonOfTheDay.length(); j++) {
                    JSONObject currentAsteroid = jsonOfTheDay.getJSONObject(j);
                    String nameAsteroid = currentAsteroid.getString("name");
                    String idAsteroid = currentAsteroid.getString("id");

                    JSONObject diameter = currentAsteroid.getJSONObject("estimated_diameter");
                    JSONObject marime = diameter.getJSONObject("meters");

                    int diamterMin = marime.getInt("estimated_diameter_min");
                    int diameterMAx = marime.getInt("estimated_diameter_max");
                    boolean isDangerous = currentAsteroid.getBoolean("is_potentially_hazardous_asteroid");
                    boolean isSentry = currentAsteroid.getBoolean("is_sentry_object");

                    JSONArray closeness = currentAsteroid.getJSONArray("close_approach_data");
                    JSONObject theOnlyObject = closeness.getJSONObject(0);
                    String dateOfClosestPoz = "";

                    String dateFromObject = theOnlyObject.getString("close_approach_date_full");
                    if (dateFromObject.equals("null")) {
                        // in returned Json, close_approach_date_full contains the string "null".
                        dateFromObject = theOnlyObject.getString("close_approach_date");
                    }

                    dateOfClosestPoz = dateFromObject;

                    JSONObject jsonSpeed = theOnlyObject.getJSONObject("relative_velocity");
                    String speed = jsonSpeed.getString("kilometers_per_hour");
                    String finalSpeed = speed.substring(0, speed.indexOf("."));
                    JSONObject jsonMiss = theOnlyObject.getJSONObject("miss_distance");
                    String distance = jsonMiss.getString("kilometers");

                    arrayAsteroids.add(new Asteroid(nameAsteroid, idAsteroid,
                            diamterMin, diameterMAx, isDangerous, dateOfClosestPoz,
                            finalSpeed, distance, isSentry));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayAsteroids;
    }

    public static ArrayList<Asteroid> getAsteroidsListFromJson(String x) {
        URL myURL = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(myURL);
        ArrayList<Asteroid> sirAst = getAsteroidsList(answer);
        return sirAst;
    }


}


