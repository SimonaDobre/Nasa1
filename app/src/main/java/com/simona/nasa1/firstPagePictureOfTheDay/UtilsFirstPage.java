package com.simona.nasa1.firstPagePictureOfTheDay;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UtilsFirstPage {

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

    private static PictureOfTheDay getPictureFromJson (String x){
        PictureOfTheDay potd = null;
        try {
            JSONObject root = new JSONObject(x);
           String title = root.getString("title");
           String explanation = root.getString("explanation");

            String urlOfThePicture = null;
            try {
                urlOfThePicture = root.getString("hdurl");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                urlOfThePicture = root.getString("url");
            }
           String fileType = root.getString("media_type");
           potd = new PictureOfTheDay(explanation, urlOfThePicture, title, fileType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return potd;
    }


    public static PictureOfTheDay obtainPictureOfTheDay(String x){
        URL myURL = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(myURL);
        PictureOfTheDay picOfTheDay = getPictureFromJson(answer);
        return picOfTheDay;
    }

}
