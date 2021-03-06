package it.fdepedis.earthquake.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ShareCompat;
import it.fdepedis.earthquake.R;
import it.fdepedis.earthquake.adapter.EarthquakeAdapter;
import it.fdepedis.earthquake.model.EarthquakeBean;
import it.fdepedis.earthquake.model.FeatureBean;
import it.fdepedis.earthquake.network.ApiService;
import it.fdepedis.earthquake.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import it.fdepedis.earthquake.settings.SettingsActivity;
import it.fdepedis.earthquake.sync.EarthquakeSyncUtils;
import it.fdepedis.earthquake.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class EarthquakeActivity extends AppCompatActivity implements EarthquakeAdapter.OnEarthquakeClickListener {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String EARTHQUAKE_SHARE_HASHTAG = " #EarthquakeApp";
    private Context context;
    private EarthquakeAdapter earthquakeAdapter;
    private List<FeatureBean> featureBeanList;
    private RecyclerView recyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private TextView mEmptyStateTextView;
    private AlertDialog loadingDialog;
    boolean doubleBackToExitPressedOnce = false;
    private SwipeRefreshLayout pullToRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        context = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.xml.layout_loading_dialog);
        loadingDialog = builder.create();
        loadingDialog.show();

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
                Timber.e("pullToRefresh.setOnRefreshListener");

                init();
                pullToRefresh.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue, R.color.green);
                pullToRefresh.setRefreshing(false);
            }
        });

        init();

        EarthquakeSyncUtils.initialize(context);
    }

    /** Method to init recycler view */
    public void init() {
        Map<String, String> parameters = Utils.getDefaultParamsQuery(context);

        /* Create handle for the RetrofitInstance interface */
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<EarthquakeBean> call = service.getEarthQuakes(parameters);
        call.enqueue(new Callback<EarthquakeBean>() {
            @Override
            public void onResponse(Call<EarthquakeBean> call, Response<EarthquakeBean> response) {
                String result;
                if (response != null) {
                    //Timber.e(LOG_TAG, "response: " + response);
                    try {
                        result = response.isSuccessful() ? response.body().toString() : null;

                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        //Timber.e(LOG_TAG, "result: " + result + " " + jsonObject.toString());

                        loadingDialog.dismiss();
                        generateDataList(response.body());
                    } catch (Exception e) {
                        Timber.e("Exception: %s", e.getLocalizedMessage());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EarthquakeBean> call, Throwable t) {
                loadingDialog.dismiss();
                Toast.makeText(EarthquakeActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /** Method to generate List of data using RecyclerView with custom adapter */
    private void generateDataList(EarthquakeBean earthquakeBean) {
        Timber.e("earthquakeBean: %s", earthquakeBean.toString());
        featureBeanList = earthquakeBean.getFeatures();

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
        if (id == R.id.action_tune) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        /* Share menu item clicked */
        if (id == R.id.action_share) {
            Intent shareIntent = createShareForecastIntent();
            startActivity(shareIntent);
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
        Timber.e("position: %s", featureBeanList.get(position));

        FeatureBean featureBean = featureBeanList.get(position);

        Intent intent = new Intent(this, DetailEarthquakeActivity.class);
        intent.putExtra("position", featureBean);
        startActivity(intent);

    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(/*mEarthquakeSummary + */EARTHQUAKE_SHARE_HASHTAG)
                .getIntent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        return shareIntent;
    }

    /*mEarthquakeSummary = String.format("%s - %s - %s/%s", dateText, description, highString, lowString);*/
}