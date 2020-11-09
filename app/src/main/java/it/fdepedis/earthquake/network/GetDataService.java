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

public interface GetDataService {

    @GET("/query?")
    @FormUrlEncoded
    Call<JsonObject> getEarthquakes(
            @QueryMap Map<String, String> options
    );

    @GET("/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10")
    Call<JsonObject> getEarthquakesTest();

    /*@GET("/query?format=geojson&orderby={orderby}&minmag={mag}")
            //&orderby=time&minmag=6&limit=10")
    @FormUrlEncoded
    Call<JsonObject> getEarthquakesTest(
            @Path("orderby") String orderby,
            @Path("migmag") String mag
    );*/

    @GET("/photos")
    Call<List<EarthquakeBean>> getAllPhotos();
}
