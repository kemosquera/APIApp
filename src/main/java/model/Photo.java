package model;

import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("id")
    private int id;

    @SerializedName("img_src")
    private String imageUrl;

    @SerializedName("earth_date")
    private String earthDate;

    @SerializedName("camera")
    private Camera camera;

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getEarthDate() {
        return earthDate;
    }

    public Camera getCamera() {
        return camera;
    }

    @Override
    public String toString() {
        return "Photo ID: " + id + ", Earth Date: " + earthDate + "\nImage: " + imageUrl;
    }
}