package it.fdepedis.earthquake.network;

import java.util.List;

import it.fdepedis.earthquake.model.EarthquakeBean;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/photos")
    Call<List<EarthquakeBean>> getAllPhotos();

}
