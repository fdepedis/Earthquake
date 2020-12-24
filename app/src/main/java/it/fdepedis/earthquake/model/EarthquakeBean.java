package it.fdepedis.earthquake.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EarthquakeBean {

/*    @SerializedName("mag")
    private String mag;
    @SerializedName("place")
    private String place;
    @SerializedName("time")
    private String time;
    @SerializedName("url")
    private String url;

    public EarthquakeBean(String mag, String place, String time, String url) {
        this.mag = mag;
        this.place = place;
        this.time = time;
        this.url = url;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }*/

    @SerializedName("features")
    private List<FeatureBean> features;

    public List<FeatureBean> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureBean> features) {
        this.features = features;
    }
}


