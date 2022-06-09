package com.simona.nasa1;

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

public class UtilsPtPrimaPagina  {

    private static URL createURL(String x){
        URL myURL = null;
        try {
            myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return myURL;
    }

    private static String makeHttpRequestAndReadFromStream(URL myURL){
        String answer = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) myURL.openConnection();
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String currentLine = "";
            StringBuffer stringBuffer = new StringBuffer();
            while ((currentLine = bufferedReader.readLine()) != null){
                stringBuffer.append(currentLine);
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

    private static PozaZilei rezultatObtinut(String x){
        PozaZilei pozaZilei = null;
        try {
            JSONObject root = new JSONObject(x);
           String titlu = root.getString("title");
           String explicatie = root.getString("explanation");

            String urlulPozei = null;
            try {
                urlulPozei = root.getString("hdurl");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                urlulPozei = root.getString("url");
            }
            Log.i("urlulpozei = ", urlulPozei);

           String tipFisier = root.getString("media_type");
           pozaZilei = new PozaZilei(explicatie, urlulPozei, titlu, tipFisier);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pozaZilei;
    }


    public static PozaZilei toateLaUnLoc(String x){
        URL myURL = createURL(x);
        String raspuns = makeHttpRequestAndReadFromStream(myURL);
        PozaZilei pozaZ = rezultatObtinut(raspuns);
        return pozaZ;
    }

}
