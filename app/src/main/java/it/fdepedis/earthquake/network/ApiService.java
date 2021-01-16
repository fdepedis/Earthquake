package it.fdepedis.earthquake.network;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import it.fdepedis.earthquake.model.EarthquakeBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("/fdsnws/event/1/query?")
    Call<EarthquakeBean> getEarthQuakes(
            @Query("format") String format,
            @Query("minmagnitude") String minMagnitude,
            @Query("maxmagnitude") String maxMagnitude);

    @GET("/fdsnws/event/1/query?")
    Call<EarthquakeBean> getEarthQuakes(@QueryMap Map<String,String> parameters);

}
