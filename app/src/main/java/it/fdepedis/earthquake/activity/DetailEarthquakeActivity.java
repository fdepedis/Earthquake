package it.fdepedis.earthquake.activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import it.fdepedis.earthquake.R;
import it.fdepedis.earthquake.model.FeatureBean;
import it.fdepedis.earthquake.utils.Utils;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.Serializable;
import java.util.Date;

public class DetailEarthquakeActivity extends AppCompatActivity implements Serializable, OnMapReadyCallback {

    private static final String LOG_TAG = DetailEarthquakeActivity.class.getName();
    private static final String LOCATION_SEPARATOR_OF = " of ";
    private static final String LOCATION_SEPARATOR_MINUS = " - ";

    /*@BindView(R.id.tvCityMeteo)
    TextView tvCityMeteo;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_earthquake);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(getIntent().hasExtra("position")){

            FeatureBean feature = (FeatureBean) getIntent().getSerializableExtra("position");
            Log.e(LOG_TAG, "feature: " + feature);
            Log.e(LOG_TAG, "place: " + feature.getPropertiesBean().getPlace());
            Log.e(LOG_TAG, "mag: " + feature.getPropertiesBean().getMag());
            Log.e(LOG_TAG, "Date: " + Utils.formatDate(new Date(feature.getPropertiesBean().getTime())));
            Log.e(LOG_TAG, "Time: " + Utils.formatTime(new Date(feature.getPropertiesBean().getTime())));
            Log.e(LOG_TAG, "alert: " + feature.getPropertiesBean().getAlert());

            String[] x =  feature.getGeometryBean().getCoordinates();
            Log.e(LOG_TAG, "x: " + x[0] + " - " + "y: " + x[1] + " - " + "d: " + x[2] );

            /** Format Title */
            String originalLocation = feature.getPropertiesBean().getTitle();

            String textTitle = null;

            // Check whether the originalLocation string contains the " of " text
            if (originalLocation.contains(LOCATION_SEPARATOR_OF)) {
                String[] parts = originalLocation.split(LOCATION_SEPARATOR_OF);
                textTitle = parts[1];
            } else {
                String[] parts = originalLocation.split(LOCATION_SEPARATOR_MINUS);
                textTitle = parts[1];
            }
            setTitle(textTitle);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e(LOG_TAG, "INNER onMapReady");
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.4233438, -122.0728817))
                .title("LinkedIn")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.4629101,-122.2449094))
                .title("Facebook")
                .snippet("Facebook HQ: Menlo Park"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.3092293, -122.1136845))
                .title("Apple"));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.4233438, -122.0728817), 10));

/*        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));*/
    }
}