package com.simona.nasa1;

import android.util.Log;

import com.simona.nasa1.amdd.AMDD;
import com.simona.nasa1.asteroid.Asteroid;
import com.simona.nasa1.earthPictures.GeneralPicture;
import com.simona.nasa1.fireball.Fireball;
import com.simona.nasa1.marsWeather.MarsWeather;
import com.simona.nasa1.monitoredAsteroids.MonitoredAsteroid;
import com.simona.nasa1.nearestAsteroids.NearestAsteroid;

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
import java.util.Iterator;

public class UtilsGeneral {

    private static URL createURL(String x) {
        URL myURL = null;
        try {
            myURL = new URL(x);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        Log.i("TEST ", myURL.toString());
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
//        Log.i("TEST ", answer);
        return answer;
    }

    // general asteroids
    private static ArrayList<Asteroid> getGeneralAsteroidsList(String x) {
        ArrayList<Asteroid> arrayAsteroids = new ArrayList<>();
        JSONObject nearEarthObj;

        try {
            JSONObject root = new JSONObject(x);
            nearEarthObj = root.getJSONObject("near_earth_objects");
//            Log.i("TESTARE ", root.toString());
            for (int i = 0; i < 8; i++) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, +i);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dayNameJson = dateFormat.format(cal.getTime());
                JSONArray jsonOfTheDay = nearEarthObj.getJSONArray(dayNameJson);

                for (int j = 0; j < jsonOfTheDay.length(); j++) {
                    JSONObject currentAsteroid = jsonOfTheDay.getJSONObject(j);
                    String nameAsteroid = currentAsteroid.getString("name");
                    String idAsteroid = currentAsteroid.getString("id");

                    JSONObject diameter = currentAsteroid.getJSONObject("estimated_diameter");
                    JSONObject marime = diameter.getJSONObject("meters");

                    int diamterMin = marime.getInt("estimated_diameter_min");
                    int diameterMAx = marime.getInt("estimated_diameter_max");
                    boolean isDangerous = currentAsteroid.getBoolean("is_potentially_hazardous_asteroid");
                    boolean isSentry = currentAsteroid.getBoolean("is_sentry_object");

                    JSONArray closeness = currentAsteroid.getJSONArray("close_approach_data");
                    JSONObject theOnlyObject = closeness.getJSONObject(0);
                    String dateOfClosestPoz = "";

                    String dateFromObject = theOnlyObject.getString("close_approach_date_full");
                    if (dateFromObject.equals("null")) {
                        // in returned Json, close_approach_date_full contains the string "null".
                        dateFromObject = theOnlyObject.getString("close_approach_date");
                    }

                    dateOfClosestPoz = dateFromObject;

                    JSONObject jsonSpeed = theOnlyObject.getJSONObject("relative_velocity");
                    String speed = jsonSpeed.getString("kilometers_per_hour");
                    String finalSpeed = speed.substring(0, speed.indexOf("."));
                    JSONObject jsonMiss = theOnlyObject.getJSONObject("miss_distance");
                    String distance = jsonMiss.getString("kilometers");

                    arrayAsteroids.add(new Asteroid(nameAsteroid, idAsteroid,
                            diamterMin, diameterMAx, isDangerous, dateOfClosestPoz,
                            finalSpeed, distance, isSentry));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayAsteroids;
    }

    public static ArrayList<Asteroid> getAsteroidsListFromJson(String x) {
        URL myURL = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(myURL);
        ArrayList<Asteroid> sirAst = getGeneralAsteroidsList(answer);
        return sirAst;
    }

    // AsteroidApproachingDates
    private static  ArrayList<NearestAsteroid> getNearestAsteroidsFromJson(String a){
        ArrayList<NearestAsteroid> arrayClosenessdates = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(a);
            String name = root.getString("name");
            JSONArray closeAproachDatass = root.getJSONArray("close_approach_data");

            for (int i=0; i<closeAproachDatass.length(); i++){
                JSONObject currentJsonObject = closeAproachDatass.getJSONObject(i);
                String dateOfCloseness = currentJsonObject.getString("close_approach_date_full");
                if (dateOfCloseness.equals(null) || dateOfCloseness.equals("null")){
                    dateOfCloseness = currentJsonObject.getString("close_approach_date");
                }
                JSONObject relativeSpeed = currentJsonObject.getJSONObject("relative_velocity");
                String speedKM = relativeSpeed.getString("kilometers_per_hour");
                String finalSpeed = speedKM.substring(0, speedKM.indexOf("."));
                int speedFinal = Integer.parseInt(finalSpeed);

                JSONObject missDistance = currentJsonObject.getJSONObject("miss_distance");
                String distanceKM = missDistance.getString("kilometers");
                String distance = distanceKM.substring(0, distanceKM.indexOf("."));
                double distanceDbl = Double.parseDouble(distance);
                int distanceInt = (int) distanceDbl;

                arrayClosenessdates.add(new NearestAsteroid(name, dateOfCloseness, speedFinal, distanceInt));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayClosenessdates;
    }

    public static ArrayList<NearestAsteroid> getNearestAsteroidsArray(String x){
        URL urlx = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(urlx);
        ArrayList<NearestAsteroid> listOfCloseness = getNearestAsteroidsFromJson(answer);
        return listOfCloseness;
    }

    // nearest asteroids closeness approach
    private static ArrayList<NearestAsteroid> getNearestAsteroidsFromJSON(String x) {
        ArrayList<NearestAsteroid> arrayNearestAsteroids = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(x);
            JSONArray jsonData = root.getJSONArray("data");
            for (int i = 0; i < jsonData.length(); i++) {
                JSONArray currentObject = jsonData.getJSONArray(i);
                String name = currentObject.getString(0);
                String dateOfApproach = currentObject.getString(3);
                String distance = currentObject.getString(4);
                distance = distance.substring(0, 5);
                double distDbl = Double.parseDouble(distance);
                distDbl *= 150000000;
                int distInt = (int) distDbl;
                String speed = currentObject.getString(7);
//                String speedFinal = speed.substring(0, 4);

                double vit = Double.parseDouble(speed);
                vit = vit * 3600;
                int speedFin = (int) vit;

                arrayNearestAsteroids.add(new NearestAsteroid(name, dateOfApproach, speedFin, distInt));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayNearestAsteroids;
    }

    public static ArrayList<NearestAsteroid> getDatesOfClosnessApproach(String x) {
        URL myURL = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(myURL);
        ArrayList<NearestAsteroid> arrayClosenessDates = getNearestAsteroidsFromJSON(answer);
        return arrayClosenessDates;
    }

    // Earth pictures
    private static String dateOfPicture(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -20);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // format with slash, as in the Nasa link
        // https://api.nasa.gov/EPIC/archive/natural/2020/07/16/png/epic_1b_20200716000830.png?api_key=DEMO_KEY
        String dateOfPictures = dateFormat.format(cal.getTime());
        return dateOfPictures;
    }

    private static ArrayList<GeneralPicture> getEarthPicturesFromJSON(String answer){
        ArrayList<GeneralPicture> arrayPictures = new ArrayList<>();
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
                arrayPictures.add(new GeneralPicture(currentPictureURL, dateOfPicture, lati, longi, caption));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayPictures;
    }

    public static ArrayList<GeneralPicture> getEarthPicturesArray(String x){
        URL myurl = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(myurl);
        ArrayList<GeneralPicture> arrayPictures = getEarthPicturesFromJSON(answer);
        return arrayPictures;
    }

    // Mars pictures
    private static ArrayList<GeneralPicture> getMarsPicturesFromJson(String x) {
        ArrayList<GeneralPicture> arrayOfPictures = new ArrayList<>();
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
                arrayOfPictures.add(new GeneralPicture(urlForPicture, rover, camera, pictureID));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayOfPictures;
    }

    public static ArrayList<GeneralPicture> getMarsPicturesArray(String x) {
        URL myURL = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(myURL);
        ArrayList<GeneralPicture> marsPicturesArray = getMarsPicturesFromJson(answer);
        return marsPicturesArray;
    }

    // Picture of the day (first page)
    private static GeneralPicture getPictureFromJson (String x){
        GeneralPicture potd = null;
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
            potd = new GeneralPicture(explanation, urlOfThePicture, title, fileType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return potd;
    }

    public static GeneralPicture obtainPictureOfTheDay(String x){
        URL myURL = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(myURL);
        GeneralPicture picOfTheDay = getPictureFromJson(answer);
        return picOfTheDay;
    }

    // monitored asteroids
    private static ArrayList<MonitoredAsteroid> getMonitoredAsteroidsfromJson(String raspuns) {
        ArrayList<MonitoredAsteroid> arrayMonitorisedAster = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(raspuns);
            JSONArray datesJsonArray = root.getJSONArray("data");

            for (int i = 0; i < datesJsonArray.length(); i++) {
                JSONObject currentObject = datesJsonArray.getJSONObject(i);
                String name = currentObject.getString("fullname");
                String diam = currentObject.getString("diameter");
                diam = diam.substring(2);
                int diametr = Integer.parseInt(diam);
                String v1 = currentObject.getString("v_inf");
                String speed1 = "";
                double speedDbl = 0;
                int speedInteger = 0;
                if (v1.length() >= 5) {
                    speed1 = v1.substring(0, 4);
                } else if (v1.length() == 4) {
                    speed1 = v1.substring(0, 3);
                } else if (v1.length() == 3) {
                    speed1 = v1.substring(0, 2);
                } else if (v1.length() == 2) {
                    speed1 = v1.substring(0, 1);
                }

                try {
                    if (!(speed1.equals("null") || speed1.equals("nan"))) {
                        speedDbl = Double.parseDouble(speed1);
                    }
                    speedInteger = (int) speedDbl * 3600;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                String maxHazard = currentObject.getString("ip");
                double chancesOfImpactPercet = 0, missChancesPercent = 0, missChancesNumber = 0;
                String t1 = "", t2 = "";
                // there are some ip-s which contains a comma, not E-7 or E-2
                if (maxHazard.contains("E") || maxHazard.contains("e")) {
                    t1 = maxHazard.substring(0, 3);
                    t2 = maxHazard.substring(maxHazard.length() - 2, maxHazard.length());
                    double firstpart = Double.parseDouble(t1);
                    int powerOf10 = 0;
                    if (t2.charAt(0) == 0) {
                        powerOf10 = t2.charAt(1);
                    } else {
                        powerOf10 = Integer.parseInt(t2);
                    }
                    double a1 = firstpart / (Math.pow(10, powerOf10));
                    missChancesPercent = 100 - a1;
                    missChancesNumber = 1 / a1;
                    chancesOfImpactPercet = a1;
                    // DecimalFormat decimFormat = new DecimalFormat("0.0000000000");
                    //  sanseDeImpactProcent = Double.parseDouble(decimFormat.format(a1));
                    // sanseDeImpactProcent = a1*Math.pow(10, putereaLui10-1);
                } else {
                    double maxHazDOUBLE = Double.parseDouble(maxHazard);
                    chancesOfImpactPercet = maxHazDOUBLE * 100;
                    missChancesPercent = 100 - maxHazDOUBLE;
                    missChancesNumber = 1 / maxHazDOUBLE;
                }
                String observation = currentObject.getString("last_obs");

                ArrayList<String> nextDatesOfClosness = new ArrayList<>();
                arrayMonitorisedAster.add(new MonitoredAsteroid(name, diametr, speedInteger, observation,
                        nextDatesOfClosness, chancesOfImpactPercet,
                        missChancesPercent, missChancesNumber));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayMonitorisedAster;
    }

    public static ArrayList<MonitoredAsteroid> getMonitoredAsteroids(String x) {
        URL myurl = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(myurl);
        ArrayList<MonitoredAsteroid> arrayMonitAster = getMonitoredAsteroidsfromJson(answer);
        return arrayMonitAster;
    }

    // AMDD
    private static ArrayList<AMDD> getDetailsArrayFromJson(String raspuns) {
        ArrayList<AMDD> detailsArray = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(raspuns);
            JSONArray JSONdata = root.getJSONArray("data");
            for (int i = JSONdata.length() - 1; i >= 0; i--) {
                JSONObject currentObject = JSONdata.getJSONObject(i);

                String dateCloseness = currentObject.getString("date");
                String dateFinal = dateCloseness.substring(0, 10);
                String probability = currentObject.getString("ip");
                double impactChancesPercent = 0, missChancesPercent = 0;
                int aChanceOutOFHowMany = 0;
                String t1 = "", t2 = "";
                if (probability.contains("E") || probability.contains("e")) {
                    t1 = probability.substring(0, 3);
                    t2 = probability.substring(probability.length() - 2, probability.length());
                    double firstFactor = Double.parseDouble(t1);
                    int powerOf10 = 0;
                    if (t2.charAt(0) == 0) {
                        powerOf10 = t2.charAt(1);
                    } else {
                        powerOf10 = Integer.parseInt(t2);
                    }
                    double a1 = firstFactor / (Math.pow(10, powerOf10));
                    missChancesPercent = 100 - a1;
                    aChanceOutOFHowMany = (int) (1 / a1);
                    // sanseDeImpactProcent = a1*Math.pow(10, putereaLui10-1)/100;
                    impactChancesPercent = a1;
                } else {
                    double maxHazDOUBLE = Double.parseDouble(probability);
                    impactChancesPercent = maxHazDOUBLE * 100;
                    missChancesPercent = 100 - maxHazDOUBLE;
                    aChanceOutOFHowMany = (int) (1 / maxHazDOUBLE);
                }

                String energy = currentObject.getString("energy");
                double hitEnergyDbl = 0;
                if (energy.contains("E") || energy.contains("e")) {
                    t1 = energy.substring(0, 3);
                    t2 = energy.substring(energy.length() - 2);
                    double firstfactor = Double.parseDouble(t1);
                    int powerOf10 = 0;
                    if (t2.charAt(0) == 0) {
                        powerOf10 = t2.charAt(1);
                    } else {
                        powerOf10 = Integer.parseInt(t2);
                    }
                    hitEnergyDbl = firstfactor * (Math.pow(10, powerOf10));
                } else {
                    hitEnergyDbl = Double.parseDouble(energy);
                }
                int hitEnergyFinal = (int) hitEnergyDbl;

                Log.i("AICI utils dataFin = ", dateFinal);
                Log.i("AICI utils sanseImpa = ", impactChancesPercent + "");
                Log.i("AICI utils energyFIn = ", hitEnergyFinal + "");
                Log.i("AICI utils oSansaDi  = ", aChanceOutOFHowMany + "");

                detailsArray.add(new AMDD(dateFinal, 123456,
                        impactChancesPercent, aChanceOutOFHowMany, hitEnergyFinal, missChancesPercent));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return detailsArray;
    }

    public static ArrayList<AMDD> getMonitorizedAsteroidDetails(String x) {
        URL url = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(url);
        ArrayList<AMDD> detailsArray = getDetailsArrayFromJson(answer);
        return detailsArray;
    }

    // Fireball
    private static ArrayList<Fireball> getFireballFromJson(String m) {
        ArrayList<Fireball> fireballArray = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(m);
            JSONArray dateFromJson = root.getJSONArray("data");
            for (int i = 0; i < dateFromJson.length(); i++) {
                JSONArray currentObject = dateFromJson.getJSONArray(i);
                String date = currentObject.getString(0);
                String energy = currentObject.getString(2);
                String latiNr = null;
                String latiNS = null;
                String longiNr = null;
                String longiEW = null;

                latiNr = currentObject.getString(3);
                Log.i("lati din utils=", latiNr + "");
                latiNS = currentObject.getString(4);
                longiNr = currentObject.getString(5);
                longiEW = currentObject.getString(6);

                if (!latiNr.equals("null") && !longiEW.equals("null")) {
                    fireballArray.add(new Fireball(date, energy, latiNr, latiNS, longiNr, longiEW));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fireballArray;
    }

    public static ArrayList<Fireball> getFireballArray(String x) {
//        URL url = createUrl(x);
//        String answer = readFromStream(url);
//        ArrayList<Fireball> sir = ontineFireb(answer);
        return getFireballFromJson(makeHttpRequestAndReadFromStream(createURL(x)));

    }

    // Mars weather
    private static MarsWeather getMarsWeatherFromJSON(String z){
        MarsWeather v = null;
        String lastKey = "";
        int numberOfKeys = 0;
        try {
            JSONObject root = new JSONObject(z);
            Iterator<String> keys = root.keys();
            while (numberOfKeys < 6){
                lastKey = keys.next();
                //  Log.i("KEYA while=", lastKey);
                numberOfKeys ++;
            }
//            Log.i("KEYA=", lastKey);
            JSONObject lastObject = root.getJSONObject(lastKey);
            String season = lastObject.getString("Season");
            Log.i("KEYA anotimp =", season);
            JSONObject JSONtemp = lastObject.getJSONObject("AT");
            int tMin = JSONtemp.getInt("mn");
            int tMax = JSONtemp.getInt("mx");

            JSONObject JSONpres = null;
            int pressure = 0;
            double vitVantMin = 0, vitVantMax = 0;
            try {
                JSONpres = lastObject.getJSONObject("PRE");
                pressure = JSONpres.getInt("av");
                JSONObject JSONwind = lastObject.getJSONObject("HWS");
                vitVantMin = JSONwind.getDouble("mn");
                vitVantMax = JSONwind.getDouble("mx");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            v = new MarsWeather(season, tMin, tMax, vitVantMin, vitVantMax, pressure);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return v;
    }

    public static MarsWeather getMarsWeather(String x){
        URL myurl = createURL(x);
        String answer = makeHttpRequestAndReadFromStream(myurl);
        MarsWeather v = getMarsWeatherFromJSON(answer);
        return v;
    }




}


