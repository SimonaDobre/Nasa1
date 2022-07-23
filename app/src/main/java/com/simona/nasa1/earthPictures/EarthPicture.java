package com.simona.nasa1.earthPictures;

public class EarthPicture {
    private String pictureURL;
    private String date;
    private double latitude;
    private double longitude;
    private String caption;

    public EarthPicture(String pictureURL, String date, double latitude, double longitude, String caption) {
        this.pictureURL = pictureURL;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.caption = caption;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public String getDate() {
        return date;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCaption() {
        return caption;
    }

}
