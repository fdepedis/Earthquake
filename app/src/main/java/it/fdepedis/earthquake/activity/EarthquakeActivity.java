package it.fdepedis.earthquake.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import it.fdepedis.earthquake.R;
import it.fdepedis.earthquake.adapter.EarthquakeAdapter;
import it.fdepedis.earthquake.model.EarthquakeBean;
import it.fdepedis.earthquake.model.FeatureBean;
import it.fdepedis.earthquake.network.GetDataService;
import it.fdepedis.earthquake.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.fdepedis.earthquake.settings.SettingsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();
    private Context context;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private EarthquakeAdapter earthquakeAdapter;
    private List<FeatureBean> featureBeanList;
    private RecyclerView recyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private TextView mEmptyStateTextView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        context = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        featureBeanList = new ArrayList<>();
        earthquakeAdapter = new EarthquakeAdapter(this, featureBeanList);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EarthquakeActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(earthquakeAdapter);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            android.app.LoaderManager loaderManager = getLoaderManager();

            Log.d(LOG_TAG, "Log - in before initLoader() call");


            Log.d(LOG_TAG, "Log - in after initLoader() call");
        } /*else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }*/

        Map<String, String> parameters = new HashMap<>();
        parameters.put("format", "geojson");
        parameters.put("orderby", "time");
        parameters.put("minmag", "6");
        //parameters.put("maxmagnitude", "6");
        parameters.put("limit", "10");


        /* Create handle for the RetrofitInstance interface */
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<EarthquakeBean> call = service.getEarthQuakes(parameters);
        call.enqueue(new Callback<EarthquakeBean>() {
            @Override
            public void onResponse(Call<EarthquakeBean> call, Response<EarthquakeBean> response) {
                String result;
                if (response != null) {
                    try {
                        result = response.isSuccessful() ? response.body().toString() : null;
                        Log.e(LOG_TAG, "result: " + result);
                        progressDialog.dismiss();
                        generateDataList(response.body());
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "Exception: " + e.getLocalizedMessage());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EarthquakeBean> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EarthquakeActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* Method to generate List of data using RecyclerView with custom adapter */
    private void generateDataList(EarthquakeBean earthquakeBean) {
        Log.e(LOG_TAG, "earthquakeBean: " + earthquakeBean);
        featureBeanList = earthquakeBean.getFeatures();

        Log.e(LOG_TAG, "JSON FeatureBean: " + featureBeanList.get(0).toString());
        Log.e(LOG_TAG, "JSON PropertiesBean: " + featureBeanList.get(0).getPropertiesBean().toString());
        Log.e(LOG_TAG, "JSON PropertiesBean Place: " + featureBeanList.get(0).getPropertiesBean().getPlace());
        Log.e(LOG_TAG, "JSON GeometryBean: " + Arrays.toString(featureBeanList.get(0).getGeometryBean().getCoordinates()));
        Log.e(LOG_TAG, "JSON GeometryBean type: " + featureBeanList.get(0).getGeometryBean().getType());

        String[] x =  featureBeanList.get(0).getGeometryBean().getCoordinates();
        /*String coordX = x[0];
        String coordY = x[1];
        String deep = x[2];*/
        Log.e(LOG_TAG, "x: " + x[0] + " - " + "y: " + x[1] + " - " + "d: " + x[2] );


        earthquakeAdapter.setFeatureList(featureBeanList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}