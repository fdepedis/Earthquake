package it.fdepedis.earthquake.loader;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.loader.content.AsyncTaskLoader;
import it.fdepedis.earthquake.R;
import it.fdepedis.earthquake.activity.EarthquakeActivity;
import it.fdepedis.earthquake.model.EarthquakeBean;
import it.fdepedis.earthquake.network.GetDataService;
import it.fdepedis.earthquake.network.RetrofitClientInstance;
import it.fdepedis.earthquake.settings.EarthquakePreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthquakeBean>> {

    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String mUrl;
    private List<EarthquakeBean> earthquakes;
    Context context;
    /* Create handle for the RetrofitInstance interface */
    GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
    Map<String, String> data = new HashMap<>();

    /*public EarthquakeLoader(Context context, String url) {*/
    public EarthquakeLoader(Context context) {
        super(context);
        this.context = context;
        //mUrl = url;
    }

    @Override
    protected void onStartLoading() {

        Log.e(LOG_TAG, "Log - in onStartLoading() method");

        String minMagnitude = EarthquakePreferences.getMinMagnitudePreferences(context);
        String orderBy = EarthquakePreferences.getOrderByPreferences(context);
        String numItems = EarthquakePreferences.getNumItemsPreferences(context);


        //data.put("format", "geojson");
        data.put("limit", numItems);
        data.put("minmag", minMagnitude);
        data.put("orderby", orderBy);

        forceLoad();
    }

    @Override
    public List<EarthquakeBean> loadInBackground() {

        Log.e(LOG_TAG, "Log - in loadInBackground() method");

        //Call<List<EarthquakeBean>> call = service.getEarthquakes(data);
        //Call<List<EarthquakeBean>> call = service.getEarthquakesTest();
        Call<JsonObject> call = service.getEarthquakesTest();
        /*call.enqueue(new Callback<List<EarthquakeBean>>() {*/
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //progressDialog.dismiss();
                //generateDataList(response.body());
                //earthquakes = response.body();
                String s = String.valueOf(response.body());
                //JsonArray user_array= response.getAsJsonArray("user_array");
                Log.e(LOG_TAG, "onResponse - earthquakes: " + s);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //progressDialog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, "onFailure - t: " + t);
            }
        });

        // Perform the network request, parse the response, and extract a list of earthquakes.
        //List<EarthquakeBean> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        //List<EarthquakeBean> earthquakes = new ArrayList<EarthquakeBean>();
        return earthquakes;
    }

    private void generateDataList(List<EarthquakeBean> earthquakeBeanList) {

        Log.e(LOG_TAG, "generateDataList: " + earthquakeBeanList);

    }
}
