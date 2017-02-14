package com.newtra.motivator.beanobject;

/**
 * Created by sethugayu on 1/9/17.
 */

public class Audio {
    public static final String AVAILABLE = "available";
    public static final String UNAVAILABLE = "unavailable";
    String downloadURL;
    String FileAvailabilityInLocalMmy;
    String audioName;
    String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    String audioId;

    public String getFileAvailabilityInLocalMmy() {
        return FileAvailabilityInLocalMmy;
    }

    public void setFileAvailabilityInLocalMmy(String fileAvailabilityInLocalMmy) {
        FileAvailabilityInLocalMmy = fileAvailabilityInLocalMmy;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }
}
