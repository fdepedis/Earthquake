package it.fdepedis.earthquake.model;

import com.google.gson.annotations.SerializedName;

public class GeometryBean {

    @SerializedName("type")
    private String type;
    @SerializedName("coordinates")
    private String[] coordinates;

    public String[] getCoordinates() { return coordinates; }

    public void setCoordinates(String[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type){ this.type = type; }

}
