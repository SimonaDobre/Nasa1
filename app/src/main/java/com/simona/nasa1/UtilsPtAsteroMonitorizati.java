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
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UtilsPtAsteroMonitorizati {

    private static URL createURL(String x){
        URL myURL = null;
        try {
            myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return myURL ;
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

    private static ArrayList<AsteroMonitorizat> obtineSirMonitorizati(String raspuns){
        ArrayList<AsteroMonitorizat> sirMonit = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(raspuns);
            JSONArray jsonulDeDate = root.getJSONArray("data");
            for (int i=0; i<jsonulDeDate.length(); i++){
                JSONObject obiectCurent = jsonulDeDate.getJSONObject(i);
                String nume = obiectCurent.getString("fullname");
                String diametru = obiectCurent.getString("diameter");
                diametru = diametru.substring(2);
                int diametr = Integer.parseInt(diametru);

                String v1 = obiectCurent.getString("v_inf");
                String vitez = "";
                double vitDbl = 0;
                int vitInteger = 0;
                if (v1.length()>=5){
                    vitez = v1.substring(0,4);
                }
                else {
                    vitez = v1.substring(0,3);
                }

                if (!(vitez.equals("null") || vitez.equals("nan"))){
                    vitDbl = Double.parseDouble(vitez);
                }
                vitInteger = (int) vitDbl * 3600;
               // Log.i("VITEZA=", v1 +"; " + vitez + "; " + vitDbl);
                //viteza = viteza.substring(0,4);


                String maxHaz = obiectCurent.getString("ip");
                double sanseDeImpactProcent = 0, sanseRatareProcent=0, cateSanseDeRateu=0;
               // int oSansaDinCate=0;

                String t1 = "", t2 ="";
                // mai sunt si ip-uri exprimate direct cu virgula
                // nu cu E-7 sau E-2

                if (maxHaz.contains("E") || maxHaz.contains("e")){
                     t1 = maxHaz.substring(0,3);
                     t2 = maxHaz.substring(maxHaz.length()-2, maxHaz.length());
                    double primulfactor = Double.parseDouble(t1);
                    int putereaLui10 = 0;
                    if (t2.charAt(0) == 0){
                        putereaLui10 = t2.charAt(1);
                    }
                    else {
                        putereaLui10 = Integer.parseInt(t2);
                    }
                    double a1 = primulfactor/(Math.pow(10, putereaLui10));
                     sanseRatareProcent = 100-a1;
                     cateSanseDeRateu = 1/a1;
                     sanseDeImpactProcent = a1;
                   // DecimalFormat decimFormat = new DecimalFormat("0.0000000000");
                  //  sanseDeImpactProcent = Double.parseDouble(decimFormat.format(a1));
                   // sanseDeImpactProcent = a1*Math.pow(10, putereaLui10-1);
                }
                else {
                    double maxHazDOUBLE = Double.parseDouble(maxHaz);
                    sanseDeImpactProcent = maxHazDOUBLE*100;
                    sanseRatareProcent = 100 - maxHazDOUBLE;
                    cateSanseDeRateu = 1/maxHazDOUBLE;
                }

               // Log.i("t1, t2 = ", t1 + "; " + t2);
                String ultima = obiectCurent.getString("last_obs");
                String ultimaFinal = ultima.substring(0, ultima.indexOf("."));
                ArrayList<String> urmatoareleDateDeApropiere = new ArrayList<>();
                sirMonit.add(new AsteroMonitorizat(nume, diametr, vitInteger, ultimaFinal, urmatoareleDateDeApropiere, sanseDeImpactProcent, sanseRatareProcent, cateSanseDeRateu));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sirMonit;
    }

    public static ArrayList<AsteroMonitorizat> toateLaUnLoc(String x){
        URL myurl = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(myurl);
        ArrayList<AsteroMonitorizat> sirObtinut = obtineSirMonitorizati(answer);
        return sirObtinut;
    }

}
