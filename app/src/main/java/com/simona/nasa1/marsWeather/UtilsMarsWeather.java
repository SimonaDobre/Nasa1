package com.simona.nasa1.marsWeather;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class UtilsMarsWeather {

    private static URL createUrl(String x){
        URL myurl = null;
        try {
            myurl = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return myurl;
    }

    private static String makeHttpRequestAndReadFromStream(URL myurl){
        String answer = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection  = (HttpURLConnection) myurl.openConnection();
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String liniaCurenta = "";
            StringBuffer stringBuffer = new StringBuffer();
            while ((liniaCurenta = bufferedReader.readLine()) != null){
                stringBuffer.append(liniaCurenta);
            }
            answer = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (inputStream != null){
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

    private static MarsWeather getMarsWeatherFromJSON(String z){
        MarsWeather v = null;
        String lastKey = "";
        int numberOfKeys = 0;
        try {
            JSONObject root = new JSONObject(z);
            Iterator<String> keys = root.keys();
            while (numberOfKeys < 6){
                lastKey = keys.next();
              //  Log.i("KEYA while=", lastKey);
                numberOfKeys ++;
            }
//            Log.i("KEYA=", lastKey);
            JSONObject lastObject = root.getJSONObject(lastKey);
            String season = lastObject.getString("Season");
            Log.i("KEYA anotimp =", season);
            JSONObject JSONtemp = lastObject.getJSONObject("AT");
            int tMin = JSONtemp.getInt("mn");
            int tMax = JSONtemp.getInt("mx");

            JSONObject JSONpres = null;
            int pressure = 0;
            double vitVantMin = 0, vitVantMax = 0;
            try {
                JSONpres = lastObject.getJSONObject("PRE");
                pressure = JSONpres.getInt("av");
                JSONObject JSONwind = lastObject.getJSONObject("HWS");
                 vitVantMin = JSONwind.getDouble("mn");
                 vitVantMax = JSONwind.getDouble("mx");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            v = new MarsWeather(season, tMin, tMax, vitVantMin, vitVantMax, pressure);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return v;
    }

    public static MarsWeather getMarsWeather(String x){
        URL myurl = createUrl(x);
        String answer = makeHttpRequestAndReadFromStream(myurl);
        MarsWeather v = getMarsWeatherFromJSON(answer);
        return v;
    }


}
