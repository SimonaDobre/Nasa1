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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UtilsPtPozeCuPamantul {

    private static URL createUrl(String x){
        URL myURL = null;
        try {
            myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return myURL;
    }

    private static String makeHttpRequestAndReadFromStream(URL myUrl){
        String answer = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) myUrl.openConnection();
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

    private static String dataPozelor(){
        // Date dataAzi = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -6);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // am pus formatul cu slash in loc de minus, pentru ca asa e link-ul oferit de Nasa
        // catre adresa unde e stocata poza:
        // adica asta:
        // https://api.nasa.gov/EPIC/archive/natural/2020/07/16/png/epic_1b_20200716000830.png?api_key=kEfXDqiER0JSBhCQEt86TlQVC3FSkG5sF3X9y46N
        Date dataMinusCinciZile = cal.getTime();
        String azi = dateFormat.format(dataMinusCinciZile);
        return azi;
    }

    private static ArrayList<PozaPamant> obtinePoze(String answer){
        ArrayList<PozaPamant> sirPoze = new ArrayList<>();
        try {
            JSONArray root = new JSONArray(answer);
            for (int i=0; i<root.length(); i++){
                JSONObject obiectCurent = root.getJSONObject(i);
                String caption = obiectCurent.getString("caption");
                JSONObject coord = obiectCurent.getJSONObject("centroid_coordinates");
                double lati = coord.getDouble("lat");
                double longi = coord.getDouble("lon");
                String data = obiectCurent.getString("date");
                String codulPozei = obiectCurent.getString("image");

                // aici am format adresa unde se afla stocata poza,
                // conform cu linkul de mai sus
                String numelePozei = "https://api.nasa.gov/EPIC/archive/natural/"
                        + dataPozelor()
                        + "/png/"
                        + codulPozei
                        + ".png?api_key=kEfXDqiER0JSBhCQEt86TlQVC3FSkG5sF3X9y46N";
                Log.i("poza=", numelePozei);
                sirPoze.add(new PozaPamant(numelePozei, data, lati, longi, caption));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sirPoze;
    }


    public static ArrayList<PozaPamant> toateLaUnLoc(String x){
        URL myurl = createUrl(x);
        String raspundReadFromStream = makeHttpRequestAndReadFromStream(myurl);
        ArrayList<PozaPamant> sirPoze = obtinePoze(raspundReadFromStream);
        return sirPoze;
    }

}
