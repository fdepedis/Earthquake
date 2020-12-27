package it.fdepedis.earthquake.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PropertiesBean implements Serializable {

    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;
    @SerializedName("mag")
    private double mag;
    @SerializedName("place")
    private String place;
    @SerializedName("time")
    private long time;
    @SerializedName("url")
    private String url;
    @SerializedName("alert")
    private String alert;
    @SerializedName("tsunami")
    private int tsunami;
    @SerializedName("status")
    private String status;


    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() { return type; }

    public void setType(String type) {
        this.type = type;
    }

    public double getMag() { return mag; }

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

    public String getAlert() { return alert; }

    public void setAlert(String alert) { this.alert = alert; }

    public int getTsunami() { return tsunami; }

    public void setTsunami(int tsunami) { this.tsunami = tsunami; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

}