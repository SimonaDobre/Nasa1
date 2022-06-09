package com.simona.nasa1;

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

public class UtilsPtPozeMarte {

    private static URL createUrlPtPoze(String x){
        URL myURL = null;
        try {
            myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return myURL;

    }

    private static String makeHttpRequestAndReadFromStreamPtPoze(URL myUrl) {
        String answer = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) myUrl.openConnection();
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String currentLine = "";
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


    private static ArrayList<PozaMarte> extractPicturesFromJsonPtPoze(String x){
        ArrayList<PozaMarte> arrayOfPictures = new ArrayList<>();
        JSONArray arrayPhotos = null;

        try {
            JSONObject root = new JSONObject(x);
            arrayPhotos = root.getJSONArray("photos");

            for (int i=0; i<arrayPhotos.length(); i++){
                JSONObject currentJsonObject = arrayPhotos.getJSONObject(i);
                String urlImagine = currentJsonObject.getString("img_src");
                JSONObject roverObj = currentJsonObject.getJSONObject("rover");
                String rover = roverObj.getString("name");
                JSONObject cameraObj = currentJsonObject.getJSONObject("camera");
                String camera = cameraObj.getString("full_name");
                int idul = currentJsonObject.getInt("id");
                arrayOfPictures.add(new PozaMarte(urlImagine, rover, camera, idul));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayOfPictures;
    }


    public static ArrayList<PozaMarte> toateLaUnLocPtPoze(String x){
        URL myURL = createUrlPtPoze(x);
        String raspuns = makeHttpRequestAndReadFromStreamPtPoze(myURL);
        ArrayList<PozaMarte> sirFinal = extractPicturesFromJsonPtPoze(raspuns);
        return sirFinal;
    }


    private static URL createUrlPtVremeaPeMarte(String x){
        URL myURL = null;
        try {
            myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return myURL;
    }

    private static String makeHttpRequestPtVremeaPeMarte(URL myurl){
        String answerVremea = "";
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
            answerVremea = stringBuffer.toString();
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
        return answerVremea;
    }

    private static VremeaPeMarte obtineVremeaPeMarte(String answerVremee){
        VremeaPeMarte v = null;
        try {
            JSONObject root = new JSONObject(answerVremee);
            // pe 21 era afisat 579 - 585,
            // la 585 era vremea pe 20
            // https://api.nasa.gov/insight_weather/?api_key=DEMO_KEY&feedtype=json&ver=1.0

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }



}
