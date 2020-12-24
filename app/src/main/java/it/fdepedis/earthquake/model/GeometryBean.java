package it.fdepedis.earthquake.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GeometryBean {

    @SerializedName("coordinates")
    private String[] coordinates;

    public String[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String[] coordinates) {
        this.coordinates = coordinates;
    }
}
