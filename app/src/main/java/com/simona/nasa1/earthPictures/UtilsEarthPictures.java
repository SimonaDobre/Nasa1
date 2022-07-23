package com.simona.nasa1.earthPictures;

import com.simona.nasa1.MainActivity;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UtilsEarthPictures {

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

    private static String dateOfPicture(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -20);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // format with slash, as in the Nasa link
        // https://api.nasa.gov/EPIC/archive/natural/2020/07/16/png/epic_1b_20200716000830.png?api_key=DEMO_KEY
        String dateOfPictures = dateFormat.format(cal.getTime());
        return dateOfPictures;
    }

    private static ArrayList<EarthPicture> getEarthPicturesFromJSON(String answer){
        ArrayList<EarthPicture> arrayPictures = new ArrayList<>();
        try {
            JSONArray root = new JSONArray(answer);
            for (int i=0; i<root.length(); i++){
                JSONObject currentObject = root.getJSONObject(i);
                String caption = currentObject.getString("caption");
                JSONObject coord = currentObject.getJSONObject("centroid_coordinates");
                double lati = coord.getDouble("lat");
                double longi = coord.getDouble("lon");
                String dateOfPicture = currentObject.getString("date");
                String pictureCODE = currentObject.getString("image");
                // URL of current picture, as per NASA API:
                // https://api.nasa.gov/EPIC/archive/natural/2019/05/30/png
                //  /epic_1b_20190530011359.png?api_key=DEMO_KEY
                String currentPictureURL = "https://api.nasa.gov/EPIC/archive/natural/"
                        + dateOfPicture()
                        + "/png/"
                        + pictureCODE
                        + ".png?api_key=" + MainActivity.API_KEY;
                arrayPictures.add(new EarthPicture(currentPictureURL, dateOfPicture, lati, longi, caption));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayPictures;
    }


    public static ArrayList<EarthPicture> getEarthPicturesArray(String x){
        URL myurl = createUrl(x);
        String answer = makeHttpRequestAndReadFromStream(myurl);
        ArrayList<EarthPicture> arrayPictures = getEarthPicturesFromJSON(answer);
        return arrayPictures;
    }

}
