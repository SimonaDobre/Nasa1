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
import java.util.ArrayList;

public class UtilsFireball {

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

    private static ArrayList<Fireballs> obtineFireb(String m) {
        ArrayList<Fireballs> sirObtinut = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(m);
            JSONArray dateleJson = root.getJSONArray("data");
            for (int i = 0; i < dateleJson.length(); i++) {
                JSONArray obiectCurent = dateleJson.getJSONArray(i);
                String data = obiectCurent.getString(0);
                String energia = obiectCurent.getString(2);
                String latiNr = null;
                String latiNS = null;
                String longiNr = null;
                String longiEW = null;

                    latiNr = obiectCurent.getString(3);
                    Log.i("lati din utils=", latiNr + "");
                    latiNS = obiectCurent.getString(4);
                    longiNr = obiectCurent.getString(5);
                    longiEW = obiectCurent.getString(6);
                // am pus eqals("null")  cu null intre ghilimele,
                // pt ca, din JSONUL asta, ca si sin toate JSONURILE lui Nasa
                // null nu inseamna nimic. Null inseamna un string egal cu cuvantul "null"
                // deci  latiNr era initial null adica nimic
                // iar dupa initializarea de mai sus, devine egal cu "null"
                // adica contine stringul "null"
                if ( !latiNr.equals("null") && !longiEW.equals("null")) {
                    sirObtinut.add(new Fireballs(data, energia, latiNr, latiNS, longiNr, longiEW));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sirObtinut;
    }

    public static ArrayList<Fireballs> toateOdata(String x) {
//        URL url = createUrl(x);
//        String answer = readFromStream(url);
//        ArrayList<Fireballs> sir = ontineFireb(answer);
        return obtineFireb(readFromStream(createUrl(x)));

    }

}
