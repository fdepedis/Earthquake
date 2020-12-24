package it.fdepedis.earthquake.model;

import com.google.gson.annotations.SerializedName;

public class FeatureBean {

    @SerializedName("type")
    private String type;

    @SerializedName("properties")
    private PropertiesBean propertiesBean;

    public String getType() {
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public PropertiesBean getPropertiesBean() {
        return propertiesBean;
    }

    public void setPropertiesBean(PropertiesBean propertiesBean) {
        this.propertiesBean = propertiesBean;
    }
}

