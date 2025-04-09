package model;

import com.google.gson.annotations.SerializedName;

public class Camera {
    @SerializedName("full_name")
    private String fullName;

    public String getFullName() {
        return fullName;
    }
}