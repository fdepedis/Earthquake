package it.fdepedis.earthquake.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import butterknife.ButterKnife;
import it.fdepedis.earthquake.R;
import it.fdepedis.earthquake.model.FeatureBean;
import it.fdepedis.earthquake.utils.Utils;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.Serializable;


public class DetailEarthquakeActivity extends AppCompatActivity implements Serializable, OnMapReadyCallback {

    private static final String LOG_TAG = DetailEarthquakeActivity.class.getName();
    private static final String LOCATION_SEPARATOR_OF = " of ";
    private static final String LOCATION_SEPARATOR_MINUS = " - ";

    private String[] x;
    private String originalLocation;
    private String[] parts;
    private String textTitle = null;

//    @BindView(R.id.dt_place_primary)
    TextView txtPrimaryPlaceDetail;
    //    @BindView(R.id.dt_place_loc_offset)
    TextView txtOffsetPlaceDetail;
    //@BindView(R.id.dt_magnitude)
    TextView magnitudeDetail;
/*    private ActivityDetailBinding mDetailBinding;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_earthquake);*/
        setContentView(R.layout.activity_detail_earthquake);
        //ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtPrimaryPlaceDetail = findViewById(R.id.dt_place_primary);
        txtOffsetPlaceDetail = findViewById(R.id.dt_place_loc_offset);
        magnitudeDetail = findViewById(R.id.dt_magnitude);

        if (getIntent().hasExtra("position")) {

            FeatureBean feature = (FeatureBean) getIntent().getSerializableExtra("position");
           /* Log.e(LOG_TAG, "feature: " + feature);
            Log.e(LOG_TAG, "place: " + feature.getPropertiesBean().getPlace());
            Log.e(LOG_TAG, "mag: " + feature.getPropertiesBean().getMag());
            Log.e(LOG_TAG, "Date: " + Utils.formatDate(new Date(feature.getPropertiesBean().getTime())));
            Log.e(LOG_TAG, "Time: " + Utils.formatTime(new Date(feature.getPropertiesBean().getTime())));
            Log.e(LOG_TAG, "alert: " + feature.getPropertiesBean().getAlert());*/

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
            txtPrimaryPlaceDetail.setText(parts[0]);
            txtOffsetPlaceDetail.setText(parts[1]);

            /** Format Magnitude */
            String formatMagnitude = Utils.formatMagnitude(feature.getPropertiesBean().getMag());
            magnitudeDetail.setText(formatMagnitude);

            GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeDetail.getBackground();
            int magnitudeColor = Utils.getMagnitudeColor(this, feature.getPropertiesBean().getMag());
            magnitudeCircle.setColor(magnitudeColor);

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