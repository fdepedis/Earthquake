package it.fdepedis.earthquake.model;

import com.google.gson.annotations.SerializedName;

public class PropertiesBean {

    @SerializedName("mag")
    private String mag;
    @SerializedName("place")
    private String place;
    @SerializedName("time")
    private String time;
    @SerializedName("url")
    private String url;

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

}