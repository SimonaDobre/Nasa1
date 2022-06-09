package com.simona.nasa1;

import android.util.Log;
import android.view.DragAndDropPermissions;

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

public class UtilsPtAsteroPERICULOSI {

    private static URL createURL(String x){
        URL myURL = null;
        try {
            myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
      //  Log.i("PPPP din createURL", myURL.toString());
        return myURL;
    }



//    private static String makeHttpRequestAndReadFromStream(URL myURL){
//        String answer = "";
//        HttpURLConnection httpURLConnection = null;
//        InputStream inputStream = null;
//        try {
//            httpURLConnection = (HttpURLConnection) myURL.openConnection();
//            //Log.i("HTTPCONN = ", httpURLConnection.toString());
//            inputStream = httpURLConnection.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String currentLine = "";
//            StringBuffer stringBuffer = new StringBuffer();
//            while ((currentLine = bufferedReader.readLine()) != null){
//                stringBuffer.append(currentLine);
//            }
//            answer = stringBuffer.toString();
//            bufferedReader.close();
//            inputStreamReader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//            if (inputStream != null){
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            httpURLConnection.disconnect();
//        }
//        Log.i("PPPP ", answer);
//        return answer;
//    }

//    private static String readStream(URL myurl){
//        String raspuns = "";
//        HttpURLConnection httpURLConnection = null;
//        InputStream inputStream = null;
//        try {
//            httpURLConnection = (HttpURLConnection) myurl.openConnection();
//            inputStream = httpURLConnection.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String liniaCurenta = "";
//            StringBuffer stringBuffer = new StringBuffer();
//            while ((liniaCurenta = bufferedReader.readLine()) != null){
//                stringBuffer.append(liniaCurenta);
//            }
//            raspuns = stringBuffer.toString();
//            bufferedReader.close();
//            inputStreamReader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//            if (inputStream != null){
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            httpURLConnection.disconnect();
//        }
//        Log.i("PPPP raspunsul=", raspuns);
//        return raspuns;
//    }


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
      //  Log.i("TESTARE ", answer);
        return answer;
    }


    private static  ArrayList<AsterPericulosApropieri> obtineSirApropieri(String a){
        ArrayList<AsterPericulosApropieri> sirApropieri = new ArrayList<>();
     //   Log.i("PPPP ", "am inceput ");
        try {
           // Log.i("PPPP ", "din tryyyy");
            JSONObject radacina = new JSONObject(a);

            String numele = radacina.getString("name");

            String linkTEST = radacina.getString("id");
          //  Log.i("PPPP id ", linkTEST);
          //  Log.i("PPPP ", radacina.toString());
            JSONArray closeAproachDatass = radacina.getJSONArray("close_approach_data");

           // Log.i("PPPP SIZE=", closeAproachDatass.length()+"");
            for (int i=0; i<closeAproachDatass.length(); i++){
                JSONObject obiectCurent = closeAproachDatass.getJSONObject(i);
                String dataAprop = obiectCurent.getString("close_approach_date_full");
                if (dataAprop.equals(null) || dataAprop.equals("null")){
                    dataAprop = obiectCurent.getString("close_approach_date");
                }
              //  Log.i("PPPP DATA ", dataAprop);
                JSONObject jsonViteza = obiectCurent.getJSONObject("relative_velocity");
                String viteza = jsonViteza.getString("kilometers_per_hour");
                String vitezaFinal = viteza.substring(0, viteza.indexOf("."));
                int vitFinal = Integer.parseInt(vitezaFinal);
              //  Log.i("PPPP VITEZA ", vitezaFinal);

                JSONObject jsonDistanta = obiectCurent.getJSONObject("miss_distance");
                String distanta = jsonDistanta.getString("kilometers");
                String disntantaFInal = distanta.substring(0, distanta.indexOf("."));
                double distDbl = Double.parseDouble(distanta);
                int distFinala = (int) distDbl;
               // Log.i("PPPP DIST ", disntantaFInal);

                sirApropieri.add(new AsterPericulosApropieri(numele, dataAprop, vitFinal, distFinala));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sirApropieri;
    }

    public static ArrayList<AsterPericulosApropieri> toateLaUnLoc(String x){
        URL urlx = createURL(x);
        String answer = mAAKKttpRequestAndReadFromStream(urlx);
        ArrayList<AsterPericulosApropieri> sirAp = obtineSirApropieri(answer);
        return sirAp;
     }


}
