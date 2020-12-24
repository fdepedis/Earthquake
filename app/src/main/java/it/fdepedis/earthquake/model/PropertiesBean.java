package it.fdepedis.earthquake.model;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;

public class PropertiesBean {

    @SerializedName("mag")
    private BigDecimal mag;

    @SerializedName("place")
    private String place;

    public BigDecimal getMag() {
        return mag;
    }

    public String getPlace() {
        return place;
    }

    public void setMag(BigDecimal mag) {
        this.mag = mag;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}