package com.simona.nasa1;

public class PozaZilei {

    private String explanation;
    private String pictureURL;
    private String title;
    private String mediaType;

    public PozaZilei(String explanation, String pictureURL, String title, String mediaType) {
        this.explanation = explanation;
        this.pictureURL = pictureURL;
        this.title = title;
        this.mediaType = mediaType;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public String getTitle() {
        return title;
    }

    public String getMediaType() {
        return mediaType;
    }
}
