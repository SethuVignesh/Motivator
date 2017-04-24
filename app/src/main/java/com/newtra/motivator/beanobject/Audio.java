package com.newtra.motivator.beanobject;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by sethugayu on 1/9/17.
 */

public class Audio implements Parcelable {
    public static final String AVAILABLE = "available";
    public static final String UNAVAILABLE = "unavailable";
    String downloadURL;
    String FileAvailabilityInLocalMmy;
    String audioName;
    String location;
    String audioId;

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

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(downloadURL);
        dest.writeString(FileAvailabilityInLocalMmy);
        dest.writeString(audioName);
        dest.writeString(location);
        dest.writeString(audioId);
    }

    public Audio() {

    }

    public Audio(@NonNull Parcel in) {

        downloadURL = in.readString(); // ANNOUNCEMENT | MESSAGE
        FileAvailabilityInLocalMmy = in.readString();// INBOX | SENT | SAVED
        audioName = in.readString(); // THIS ID.WAV IS THE AUDIO FILE IN DOWNLOAD URL
        location = in.readString(); //SENT TIME
        audioId = in.readString();

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @NonNull
        public Audio createFromParcel(@NonNull Parcel in) {
            return new Audio(in);
        }

        @NonNull
        public Audio[] newArray(int size) {
            return new Audio[size];
        }
    };

}
