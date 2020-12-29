package it.fdepedis.earthquake.activity;

import androidx.appcompat.app.AppCompatActivity;
import it.fdepedis.earthquake.R;
import it.fdepedis.earthquake.databinding.ActivityDetailEarthquakeBinding;
import it.fdepedis.earthquake.model.FeatureBean;
import it.fdepedis.earthquake.utils.Utils;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
    private static final String PLACEHOLDER = " ";

    private String[] x;
    private String originalLocation;
    private String[] parts;
    private String textTitle = null;
    private ActivityDetailEarthquakeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding
        mBinding = ActivityDetailEarthquakeBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (getIntent().hasExtra("position")) {

            FeatureBean feature = (FeatureBean) getIntent().getSerializableExtra("position");

            /** Coordinates */
            x = feature.getGeometryBean().getCoordinates();
            Log.e(LOG_TAG, "x: " + x[0] + " - " + "y: " + x[1] + " - " + "d: " + x[2]);

            /** Format Title */
            originalLocation = feature.getPropertiesBean().getTitle();

            // Check whether the originalLocation string contains the " of " text
            if (originalLocation.contains(LOCATION_SEPARATOR_OF)) {
                parts = originalLocation.split(LOCATION_SEPARATOR_OF);
                textTitle = parts[1];
            } else {
                parts = originalLocation.split(LOCATION_SEPARATOR_MINUS);
                textTitle = parts[1];
            }
            setTitle(textTitle);

            /** Place */
            mBinding.dtPlace.setText(parts[0]);

            /** Format Magnitude */
            String formatMagnitude = Utils.formatMagnitude(feature.getPropertiesBean().getMag());
            mBinding.dtMagnitude.setText(formatMagnitude);

            GradientDrawable magnitudeCircle = (GradientDrawable) mBinding.dtMagnitude.getBackground();
            int magnitudeColor = Utils.getMagnitudeColor(this, feature.getPropertiesBean().getMag());
            magnitudeCircle.setColor(magnitudeColor);

            /** Time */
            Date dateObject = new Date(feature.getPropertiesBean().getTime());
            String formattedDate = Utils.formatDate(dateObject);
            String formattedTime = Utils.formatTime(dateObject);
            mBinding.dtTimeLabel.setText(R.string.dtTimeLabel);
            mBinding.dtTimeValue.setText(PLACEHOLDER + formattedDate + " - " + formattedTime);

            /** Type */
            mBinding.dtTypeLabel.setText(R.string.dtTypeLabel);
            mBinding.dtTypeValue.setText(PLACEHOLDER + feature.getPropertiesBean().getType());

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e(LOG_TAG, "INNER onMapReady");
        googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(x[1]), Double.parseDouble(x[0])))
                        .title(parts[0])
                        .snippet(textTitle)
                /*.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))*/);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(x[1]), Double.parseDouble(x[0])), 10));

    }
}