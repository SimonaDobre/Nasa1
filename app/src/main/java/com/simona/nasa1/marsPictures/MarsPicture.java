package com.simona.nasa1.marsPictures;

public class MarsPicture {

    private String pictureURL;
    private String rover;
    private String camera;
    private int pictureID;

    public MarsPicture(String pictureURL, String rover, String camera, int pictureID) {
        this.pictureURL = pictureURL;
        this.rover = rover;
        this.camera = camera;
        this.pictureID = pictureID;
    }

    public String getPictureURL() {
        return pictureURL;
    }

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
