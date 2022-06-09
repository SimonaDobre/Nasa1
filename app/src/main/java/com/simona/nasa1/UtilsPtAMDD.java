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

public class UtilsPtAMDD {

    private static URL createURL(String x){
        URL myURL = null;
        try {
            myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return myURL;
    }

    private static String makeHttpRequestAndReadFromStream(URL myurl){
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

    private static ArrayList<AMDD> obtineSirDeDetalii(String raspuns){
        ArrayList<AMDD> sirDetalii = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(raspuns);
            JSONArray JSONdata = root.getJSONArray("data");
          //  Log.i("testare SIZE=", JSONdata.length() + "");
            for (int i=JSONdata.length()-1; i>=0; i--){
                JSONObject obiectCurent = JSONdata.getJSONObject(i);
                String dataApropierii = obiectCurent.getString("date");

                String dataFinala = dataApropierii.substring(0, 10);
//                Log.i("testare dataFinala=", dataFinala);
                String distantaString = obiectCurent.getString("dist");
               // String distFinalaString = distantaString.substring(, distantaString.length());
                double distDbl = Double.parseDouble(distantaString);
                Log.i("DIST=", distDbl + "");
                distDbl *= 150000000;
                int distFinal = (int) distDbl;
                // Log.i("testare distanta=", distDbl + "");

                String probabilitatea = obiectCurent.getString("ip");
                double sanseDeImpactProcent = 0, sanseRatareProcent=0;
                int oSansaDinCate=0;
                String t1 = "", t2 ="";
                if (probabilitatea.contains("E") || probabilitatea.contains("e")){
                    t1 = probabilitatea.substring(0,3);
                    t2 = probabilitatea.substring(probabilitatea.length()-2, probabilitatea.length());
                  //  Log.i("testare t1=", t1);
                  //  Log.i("testare t2=", t2);
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
                  //  Log.i("testare sanseRatare=", sanseRatareProcent + "");
                    oSansaDinCate = (int) (1/a1);
                   // Log.i("testare OsanseDinCate=", oSansaDinCate + "");
                   // sanseDeImpactProcent = a1*Math.pow(10, putereaLui10-1)/100;
                    sanseDeImpactProcent = a1;
                  //  Log.i("testare sanseLovire=", sanseDeImpactProcent + "");
                }
                else {
                    double maxHazDOUBLE = Double.parseDouble(probabilitatea);
                    sanseDeImpactProcent = maxHazDOUBLE*100;
                    sanseRatareProcent = 100 - maxHazDOUBLE;
                    oSansaDinCate = (int) (1/maxHazDOUBLE);
                }


                String energy = obiectCurent.getString("energy");
                double energiaLoviriiDbl = 0;
                if (energy.contains("E") || energy.contains("e")){
                    t1 = energy.substring(0,3);
                    t2 = energy.substring(energy.length()-2, energy.length());
                    double primulfactor = Double.parseDouble(t1);
                    int putereaLui10 = 0;
                    if (t2.charAt(0) == 0){
                        putereaLui10 = t2.charAt(1);
                    }
                    else {
                        putereaLui10 = Integer.parseInt(t2);
                    }
                    energiaLoviriiDbl = primulfactor*(Math.pow(10, putereaLui10));
                }
                else {
                    energiaLoviriiDbl = Double.parseDouble(energy);
                }
                int energiaFinal = (int) energiaLoviriiDbl;
             //   int oSansaDinCateFInal = (int) oSansaDinCate;
                sirDetalii.add(new AMDD(dataFinala, distFinal,
                        sanseDeImpactProcent, oSansaDinCate, energiaFinal, sanseRatareProcent ));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  sirDetalii;
    }


    public static ArrayList<AMDD> toateLaUnLoc(String x){
        URL url = createURL(x);
        String qq = makeHttpRequestAndReadFromStream(url);
        ArrayList<AMDD> sirObtinut = obtineSirDeDetalii(qq);
        return sirObtinut;
    }


}
