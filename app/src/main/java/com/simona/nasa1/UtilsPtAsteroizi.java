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

public class UtilsPtAsteroizi {

    private static URL createURL(String x) {
        URL myURL = null;
        try {
            myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i("TESTARE ", myURL.toString());
        return myURL;
    }

    private static String makeHttpRequestAndReadFromStream(URL myURL) {
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
        Log.i("TESTARE ", answer);
        return answer;
    }

    private static ArrayList<Asteroid> obtineListaAsteroizi(String x) {
        ArrayList<Asteroid> sirAst = new ArrayList<>();
        JSONObject nearEarthObj = null;

        try {
            JSONObject root = new JSONObject(x);
            nearEarthObj = root.getJSONObject("near_earth_objects");
            Log.i("TESTARE ", root.toString());


            for (int i = 0; i < 8; i++) {
                // initializez calendarul aici, nu in afara for-ului
                // pentru ca, valoarea lui i o sa fie din 2 in 2
                // sare niste zile, pt ca tine minte valoarea anterioara
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, +i);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String ziuaNumeleJsonului = dateFormat.format(cal.getTime());
                //  Log.i("ASTER numeJSON=", ziuaNumeleJsonului);
                JSONArray jsonulZilei = nearEarthObj.getJSONArray(ziuaNumeleJsonului);
                // Log.i("ASTER size= ", jsonulZilei.length()+"");
                for (int j = 0; j < jsonulZilei.length(); j++) {
                    JSONObject asteroidCurent = jsonulZilei.getJSONObject(j);
                    String numele = asteroidCurent.getString("name");
                   // JSONObject jsonLink = asteroidCurent.getJSONObject("links");
                    String idulPtTransmisMaiDeparte = asteroidCurent.getString("id");
                    Log.i("PPPP id din UTILS= ", idulPtTransmisMaiDeparte);
                    // Log.i("ASTER numele=", numele);
                    JSONObject diametru = asteroidCurent.getJSONObject("estimated_diameter");
                    JSONObject marime = diametru.getJSONObject("meters");
                    int dMin = marime.getInt("estimated_diameter_min");
                    int dMAx = marime.getInt("estimated_diameter_max");
                    //  Log.i("ASTER dmin dmax=", dMin + dMAx + "");
                    boolean isDanger = asteroidCurent.getBoolean("is_potentially_hazardous_asteroid");
                    boolean isSentry = asteroidCurent.getBoolean("is_sentry_object");
                    JSONArray apropiere = asteroidCurent.getJSONArray("close_approach_data");
                    JSONObject singurulObiect = apropiere.getJSONObject(0);
                    String dataCelMaiAproape = "";

                    String ceDataContine = singurulObiect.getString("close_approach_date_full");
                    if (ceDataContine.equals("null")) {
                        // IN LINKUL RESPECTIV, IN JSON-UL RETURNAT
                        // LA close_approach_date_full SPUNE null
                        // dar de fapt este "null" adica ii aloca strigului ceDataContine
                        // valoarea "null" , care e confuz, pt ca prima data m-am gandit ca stringul e null
                        // dar de fapt stringul = "null";
                        ceDataContine = singurulObiect.getString("close_approach_date");
                    }
//
                    dataCelMaiAproape = ceDataContine;


                    JSONObject jsonViteza = singurulObiect.getJSONObject("relative_velocity");
                    String viteza = jsonViteza.getString("kilometers_per_hour");
                    String vitezaFinala = viteza.substring(0, viteza.indexOf("."));
                    JSONObject jsonMiss = singurulObiect.getJSONObject("miss_distance");
                    String distanta = jsonMiss.getString("kilometers");

                    sirAst.add(new Asteroid(numele, idulPtTransmisMaiDeparte, dMin, dMAx, isDanger, dataCelMaiAproape,
                            vitezaFinala, distanta, isSentry));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sirAst;
    }

    public static ArrayList<Asteroid> toateLaUnLoc(String x) {
        URL myURL = createURL(x);
        String raspuns = makeHttpRequestAndReadFromStream(myURL);
        ArrayList<Asteroid> sirAst = obtineListaAsteroizi(raspuns);
        return sirAst;
    }


}


