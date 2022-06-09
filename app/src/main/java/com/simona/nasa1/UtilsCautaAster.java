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

public class UtilsCautaAster {

    private static  URL createURL(String x){
        URL myURL = null;
        try {
             myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return myURL;
    }

    private static String readFromStream(URL myurl){
        String raspuns = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) myurl.openConnection();
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String liniaCurenta = "";
            StringBuffer stringBuffer = new StringBuffer();
            while ((liniaCurenta = bufferedReader.readLine()) != null){
                stringBuffer.append(liniaCurenta);
            }
            raspuns = stringBuffer.toString();
            inputStreamReader.close();
            bufferedReader.close();
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
        return raspuns;
    }

    private static ArrayList<AsterPericulosApropieri> extrageSir(String x){
        ArrayList<AsterPericulosApropieri> sir = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(x);
            JSONArray jsonData = root.getJSONArray("data");
            for (int i=0; i<jsonData.length(); i++){
                JSONArray obiectCurent = jsonData.getJSONArray(i);
                String numele = obiectCurent.getString(0);
                String dataAprop = obiectCurent.getString(3);
                String distanta = obiectCurent.getString(4);
                distanta = distanta.substring(0, 5);
                double distDbl = Double.parseDouble(distanta);
                distDbl *= 150000000;
                int distFinala = (int) distDbl;
                String viteza = obiectCurent.getString(7);
                String vFinala = viteza.substring(0, 4);
                //Log.i("VITEZA string=", vFinala + "");
                double vit = Double.parseDouble(vFinala);
               // Log.i("VITEZA 1=", vit + "");
                vit = vit*3600;
                int vitezaFin = (int) vit;
              //  Log.i("VITEZA final=", vit + "");

                sir.add(new AsterPericulosApropieri(numele, dataAprop, vitezaFin, distFinala));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sir;
    }


    public static ArrayList<AsterPericulosApropieri> toateLaUnLoc(String x){
        URL myURL = createURL(x);
        String raspuns = readFromStream(myURL);
        ArrayList<AsterPericulosApropieri> sir = extrageSir(raspuns);
        return sir;
    }



}
