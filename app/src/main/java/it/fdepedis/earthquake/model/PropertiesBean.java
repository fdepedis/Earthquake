package it.fdepedis.earthquake.model;

import com.google.gson.annotations.SerializedName;

public class PropertiesBean {

    @SerializedName("mag")
    private double mag;
    @SerializedName("place")
    private String place;
    @SerializedName("time")
    private long time;
    @SerializedName("url")
    private String url;

    public double getMag() {
        return mag;
    }

    public void setMag(double mag) {
        this.mag = mag;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public long getTime() { return time; }

    public void setTime(long time) { this.time = time; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

}