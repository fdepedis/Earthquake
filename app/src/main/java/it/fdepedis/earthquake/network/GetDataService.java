package it.fdepedis.earthquake.network;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import it.fdepedis.earthquake.model.EarthquakeBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GetDataService {

    @GET("/query?format=geojson/")
    Call<List<EarthquakeBean>> getEarthquakes(
            @QueryMap Map<String, String> options
    );

    /*@GET("/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=1")
    Call<List<EarthquakeBean>> getEarthquakesTest();*/

    @GET("/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10")
    Call<JsonObject> getEarthquakesTest();

    @GET("/photos")
    Call<List<EarthquakeBean>> getAllPhotos();
}
