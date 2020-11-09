package it.fdepedis.earthquake.loader;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.loader.content.AsyncTaskLoader;
import it.fdepedis.earthquake.activity.EarthquakeActivity;
import it.fdepedis.earthquake.model.EarthquakeBean;
import it.fdepedis.earthquake.network.GetDataService;
import it.fdepedis.earthquake.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthquakeBean>> {

    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String mUrl;
    private List<EarthquakeBean> earthquakes;

    /*public EarthquakeLoader(Context context, String url) {*/
    public EarthquakeLoader(Context context) {
        super(context);
        //mUrl = url;
    }

    @Override
    protected void onStartLoading() {

        Log.e(LOG_TAG, "Log - in onStartLoading() method");

        forceLoad();
    }

    @Override
    public List<EarthquakeBean> loadInBackground() {

        Log.e(LOG_TAG, "Log - in loadInBackground() method");

        if (mUrl == null) {
            return null;
        }

        /* Create handle for the RetrofitInstance interface */
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<EarthquakeBean>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<EarthquakeBean>>() {
            @Override
            public void onResponse(Call<List<EarthquakeBean>> call, Response<List<EarthquakeBean>> response) {
                //progressDialog.dismiss();
                generateDataList(response.body());
                earthquakes = response.body();
                Log.e(LOG_TAG, "onResponse - earthquakes: " + earthquakes);
            }

            @Override
            public void onFailure(Call<List<EarthquakeBean>> call, Throwable t) {
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
