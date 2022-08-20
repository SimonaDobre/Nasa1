package com.simona.nasa1.earthPictures;

public class GeneralPicture {
    private String pictureURL;
    private String date;
    private double latitude;
    private double longitude;
    private String caption;

    private String explanation;
    private String title;
    private String mediaType;

    private String rover;
    private String camera;
    private int pictureID;

    // Earth picture
    public GeneralPicture(String pictureURL, String date, double latitude, double longitude, String caption) {
        this.pictureURL = pictureURL;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.caption = caption;
    }

    // PictureOfTheDay
    public GeneralPicture(String explanation, String pictureURL, String title, String mediaType) {
        this.explanation = explanation;
        this.pictureURL = pictureURL;
        this.title = title;
        this.mediaType = mediaType;
    }

    // Mars picture
    public GeneralPicture(String pictureURL, String rover, String camera, int pictureID) {
        this.pictureURL = pictureURL;
        this.rover = rover;
        this.camera = camera;
        this.pictureID = pictureID;
    }

    // Earth picture
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


    // PictureOfTheDay
    public String getExplanation() {
        return explanation;
    }

    public String getTitle() {
        return title;
    }

    public String getMediaType() {
        return mediaType;
    }

    // Mars picture
    public String getRover() {
        return rover;
    }

    public String getCamera() {
        return camera;
    }

    public int getPictureID() {
        return pictureID;
    }


}


