package it.fdepedis.earthquake.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import it.fdepedis.earthquake.R;
import it.fdepedis.earthquake.adapter.EarthquakeAdapter;
import it.fdepedis.earthquake.model.EarthquakeBean;
import it.fdepedis.earthquake.network.GetDataService;
import it.fdepedis.earthquake.network.RetrofitClientInstance;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.fdepedis.earthquake.settings.SettingsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    private EarthquakeAdapter earthquakeAdapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        progressDialog = new ProgressDialog(EarthquakeActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        /* Create handle for the RetrofitInstance interface */
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
        });
    }

    /* Method to generate List of data using RecyclerView with custom adapter */
    private void generateDataList(List<EarthquakeBean> earthquakeBeanList) {

        Log.e(LOG_TAG, "earthquakeBeanList: " + earthquakeBeanList);

        recyclerView = findViewById(R.id.recycler_view);
        earthquakeAdapter = new EarthquakeAdapter(this, earthquakeBeanList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EarthquakeActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(earthquakeAdapter);
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