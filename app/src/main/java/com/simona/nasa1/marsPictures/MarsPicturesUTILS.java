package com.simona.nasa1.marsPictures;

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

public class MarsPicturesUTILS {

    private static URL createUrlPtPoze(String x) {
        URL myURL = null;
        try {
            myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return myURL;

    }

    private static String makeHttpRequestAndReadFromStreamMarsPictures(URL myUrl) {
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

    private static ArrayList<MarsPicture> getMarsPicturesFromJson(String x) {
        ArrayList<MarsPicture> arrayOfPictures = new ArrayList<>();
        JSONArray arrayPhotos = null;
        try {
            JSONObject root = new JSONObject(x);
            arrayPhotos = root.getJSONArray("photos");

            for (int i = 0; i < arrayPhotos.length(); i++) {
                JSONObject currentJsonObject = arrayPhotos.getJSONObject(i);
                String urlForPicture = currentJsonObject.getString("img_src");
                JSONObject roverObj = currentJsonObject.getJSONObject("rover");
                String rover = roverObj.getString("name");
                JSONObject cameraObj = currentJsonObject.getJSONObject("camera");
                String camera = cameraObj.getString("full_name");
                int pictureID = currentJsonObject.getInt("id");
                arrayOfPictures.add(new MarsPicture(urlForPicture, rover, camera, pictureID));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayOfPictures;
    }

    public static ArrayList<MarsPicture> getMarsPicturesArray(String x) {
        URL myURL = createUrlPtPoze(x);
        String answer = makeHttpRequestAndReadFromStreamMarsPictures(myURL);
        ArrayList<MarsPicture> marsPicturesArray = getMarsPicturesFromJson(answer);
        return marsPicturesArray;
    }


}
