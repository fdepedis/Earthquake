package it.fdepedis.earthquake.network;

import java.util.List;
import java.util.Map;

import it.fdepedis.earthquake.model.EarthquakeBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface GetDataService {

    @GET("/query?format=geojson")
    Call<List<EarthquakeBean>> getEarthquakes(
            @QueryMap Map<String, String> options
    );

}
