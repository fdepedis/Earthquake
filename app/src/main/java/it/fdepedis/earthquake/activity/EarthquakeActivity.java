package it.fdepedis.earthquake.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import it.fdepedis.earthquake.R;
import it.fdepedis.earthquake.adapter.EarthquakeAdapter;
import it.fdepedis.earthquake.model.EarthquakeBean;
import it.fdepedis.earthquake.model.FeatureBean;
import it.fdepedis.earthquake.network.GetDataService;
import it.fdepedis.earthquake.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import it.fdepedis.earthquake.settings.EarthquakePreferences;
import it.fdepedis.earthquake.settings.SettingsActivity;
import it.fdepedis.earthquake.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarthquakeActivity extends AppCompatActivity implements EarthquakeAdapter.OnEarthquakeClickListener {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();
    private Context context;
    private EarthquakeAdapter earthquakeAdapter;
    private List<FeatureBean> featureBeanList;
    private RecyclerView recyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private TextView mEmptyStateTextView;
    private ProgressDialog progressDialog;
    boolean doubleBackToExitPressedOnce = false;
    private SwipeRefreshLayout pullToRefresh;
    private Map<String, String> parameters = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        context = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        featureBeanList = new ArrayList<>();
        earthquakeAdapter = new EarthquakeAdapter(this, featureBeanList, this);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        pullToRefresh = findViewById(R.id.pullToRefresh);

        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EarthquakeActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(earthquakeAdapter);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();

                Log.e(LOG_TAG, "pullToRefresh.setOnRefreshListener");

                //Toast.makeText(context, "Pull to refresh", Toast.LENGTH_SHORT).show();
                pullToRefresh.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue, R.color.green);
                pullToRefresh.setRefreshing(false);
            }
        });

        init();

    }

    /** Method to init recycler view */
    public void init() {
        String minMagPrefs = EarthquakePreferences.getMinMagnitudePreferences(context);
        String orderByPrefs = EarthquakePreferences.getOrderByPreferences(context);
        String numItemPrefs = EarthquakePreferences.getNumItemsPreferences(context);

        Log.e(LOG_TAG, "minMagPrefs: " + minMagPrefs);
        Log.e(LOG_TAG, "orderByPrefs: " + orderByPrefs);
        Log.e(LOG_TAG, "numItemPrefs: " + numItemPrefs);

        parameters.put("format", "geojson");
        parameters.put("orderby", orderByPrefs);
        parameters.put("minmag", minMagPrefs);
        parameters.put("limit", numItemPrefs);

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

    /** Method to generate List of data using RecyclerView with custom adapter */
    private void generateDataList(EarthquakeBean earthquakeBean) {
        Log.e(LOG_TAG, "earthquakeBean: " + earthquakeBean);
        featureBeanList = earthquakeBean.getFeatures();

/*        Log.e(LOG_TAG, "JSON FeatureBean: " + featureBeanList.get(0).toString());
        Log.e(LOG_TAG, "JSON PropertiesBean: " + featureBeanList.get(0).getPropertiesBean().toString());
        Log.e(LOG_TAG, "JSON PropertiesBean Place: " + featureBeanList.get(0).getPropertiesBean().getPlace());
        Log.e(LOG_TAG, "JSON GeometryBean: " + Arrays.toString(featureBeanList.get(0).getGeometryBean().getCoordinates()));
        Log.e(LOG_TAG, "JSON GeometryBean type: " + featureBeanList.get(0).getGeometryBean().getType());

        String[] x =  featureBeanList.get(0).getGeometryBean().getCoordinates();
        *//*String coordX = x[0];
        String coordY = x[1];
        String deep = x[2];*//*
        Log.e(LOG_TAG, "x: " + x[0] + " - " + "y: " + x[1] + " - " + "d: " + x[2] );*/

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

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public void onEarthquakeClick(int position) {
        featureBeanList.get(position);
        /*Intent intent = new Intent(this, DetailEarthquakeActivity.class);
        intent.putExtra("position", featureBeanList.get(position));
        startActivity(intent);*/
        Toast.makeText(this, "position: " + /*featureBeanList.get(position)*/ position, Toast.LENGTH_SHORT).show();
    }
}