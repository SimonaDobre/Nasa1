package com.simona.nasa1.fireball;

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

public class FireballUTILS {

    private static URL createUrl(String x) {
        URL myurl = null;
        try {
            myurl = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return myurl;
    }

    private static String readFromStream(URL myurl) {
        String answer = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) myurl.openConnection();
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String currentLine = "";
            StringBuffer stringBuffer = new StringBuffer();
            while ((currentLine = bufferedReader.readLine()) != null) {
                stringBuffer.append(currentLine);
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

    private static ArrayList<Fireball> getFireballFromJson(String m) {
        ArrayList<Fireball> fireballArray = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(m);
            JSONArray dateFromJson = root.getJSONArray("data");
            for (int i = 0; i < dateFromJson.length(); i++) {
                JSONArray currentObject = dateFromJson.getJSONArray(i);
                String date = currentObject.getString(0);
                String energy = currentObject.getString(2);
                String latiNr = null;
                String latiNS = null;
                String longiNr = null;
                String longiEW = null;

                latiNr = currentObject.getString(3);
                Log.i("lati din utils=", latiNr + "");
                latiNS = currentObject.getString(4);
                longiNr = currentObject.getString(5);
                longiEW = currentObject.getString(6);

                if (!latiNr.equals("null") && !longiEW.equals("null")) {
                    fireballArray.add(new Fireball(date, energy, latiNr, latiNS, longiNr, longiEW));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fireballArray;
    }

    public static ArrayList<Fireball> getFireballArray(String x) {
//        URL url = createUrl(x);
//        String answer = readFromStream(url);
//        ArrayList<Fireball> sir = ontineFireb(answer);
        return getFireballFromJson(readFromStream(createUrl(x)));

    }

}
