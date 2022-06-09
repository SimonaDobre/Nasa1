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

public class UtilsPtUrmatoareleDateDeApropiere {

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

        private static ArrayList<String> obtineUrmatoareaData(String raspuns){
            ArrayList<String> urmatoareleDateDeApropiere = new ArrayList<>();
            try {
                JSONObject root = new JSONObject(raspuns);
                JSONArray JSONdata = root.getJSONArray("data");
                //  Log.i("testare SIZE=", JSONdata.length() + "");
                for (int i=JSONdata.length()-1; i>=0; i--) {
                    JSONObject obiectCurent = JSONdata.getJSONObject(i);
                    String dataApropierii = obiectCurent.getString("date");
                    Log.i("LINK din Utils ", dataApropierii);
                    String dataFinala = dataApropierii.substring(0, 10);
                    urmatoareleDateDeApropiere.add(dataFinala);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return  urmatoareleDateDeApropiere;
        }


        public static ArrayList<String> toateLaUnLoc(String x){
            URL url = createURL(x);
            String qq = makeHttpRequestAndReadFromStream(url);
            ArrayList<String> sirObtinut = obtineUrmatoareaData(qq);
            return sirObtinut;
        }


    }



