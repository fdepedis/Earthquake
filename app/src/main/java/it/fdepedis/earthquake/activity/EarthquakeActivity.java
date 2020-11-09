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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import it.fdepedis.earthquake.R;
import it.fdepedis.earthquake.adapter.EarthquakeAdapter;
import it.fdepedis.earthquake.loader.EarthquakeLoader;
import it.fdepedis.earthquake.model.EarthquakeBean;
import it.fdepedis.earthquake.network.GetDataService;
import it.fdepedis.earthquake.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.fdepedis.earthquake.settings.SettingsActivity;
import it.fdepedis.earthquake.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarthquakeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<EarthquakeBean>> {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();
    private Context context;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private EarthquakeAdapter earthquakeAdapter;
    private RecyclerView recyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        context = this;

        recyclerView = findViewById(R.id.recycler_view);
        earthquakeAdapter = new EarthquakeAdapter(this, new ArrayList<EarthquakeBean>());
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EarthquakeActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(earthquakeAdapter);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            android.app.LoaderManager loaderManager = getLoaderManager();

            Log.d(LOG_TAG, "Log - in before initLoader() call");
            getSupportLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this).forceLoad();

            Log.d(LOG_TAG, "Log - in after initLoader() call");
        } /*else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }*/


        /* Create handle for the RetrofitInstance interface *//*
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<EarthquakeBean>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<EarthquakeBean>>() {
            @Override
            public void onResponse(Call<List<EarthquakeBean>> call, Response<List<EarthquakeBean>> response) {
                progressDialog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<EarthquakeBean>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EarthquakeActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    /* Method to generate List of data using RecyclerView with custom adapter */
    /*private void generateDataList(List<EarthquakeBean> earthquakeBeanList) {

        Log.e(LOG_TAG, "earthquakeBeanList: " + earthquakeBeanList);

    }*/

    @NonNull
    @Override
    public Loader<List<EarthquakeBean>> onCreateLoader(int id, @Nullable Bundle bundle) {

        //String dataToFetch = Utils.refreshData(context);
        Log.e(LOG_TAG, "onCreateLoader: " + id);

        return new EarthquakeLoader(context);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<EarthquakeBean>> loader, List<EarthquakeBean> data) {
        Log.e(LOG_TAG, "onLoadFinished: " + data);

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_earthquakes);

        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        recyclerView.smoothScrollToPosition(mPosition);
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            earthquakeAdapter.setData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<EarthquakeBean>> loader) {
        earthquakeAdapter.setData(new ArrayList<EarthquakeBean>());
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