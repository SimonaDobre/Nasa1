package com.simona.nasa1;

import android.icu.text.Edits;
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

public class UtilsPtVremeMarte {

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

    private static VremeaPeMarte obtineVremea(String z){
        VremeaPeMarte v = null;
        String ultimaKeye = "";
        int laACateKeyeMaOpresc = 0;
        try {
            JSONObject root = new JSONObject(z);
            Iterator<String> keile = root.keys();
            while (laACateKeyeMaOpresc < 6){
                ultimaKeye = keile.next();
                Log.i("KEYA din while=", ultimaKeye);
                laACateKeyeMaOpresc ++;
            }
            Log.i("KEYA=", ultimaKeye);
            JSONObject ultimulObiect = root.getJSONObject(ultimaKeye);
            String anotimp = ultimulObiect.getString("Season");
            Log.i("KEYA anotimp =", anotimp);
            JSONObject JSONtemp = ultimulObiect.getJSONObject("AT");
            int tMin = JSONtemp.getInt("mn");
            int tMax = JSONtemp.getInt("mx");

            JSONObject JSONpres = null;
            int presiunea = 0;
            double vitVantMin = 0, vitVantMax = 0;
            try {
                JSONpres = ultimulObiect.getJSONObject("PRE");
                 presiunea = JSONpres.getInt("av");
                JSONObject JSONvant = ultimulObiect.getJSONObject("HWS");
                 vitVantMin = JSONvant.getDouble("mn");
                 vitVantMax = JSONvant.getDouble("mx");
            } catch (JSONException e) {
                e.printStackTrace();

            }

            v = new VremeaPeMarte(anotimp, tMin, tMax, vitVantMin, vitVantMax, presiunea);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }

    public static VremeaPeMarte obtineVremeaMarte(String x){
        URL myurl = createUrl(x);
        String answer = makeHttpRequestAndReadFromStream(myurl);
        VremeaPeMarte v = obtineVremea(answer);
        return v;
    }


}
