package it.fdepedis.earthquake.activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import it.fdepedis.earthquake.R;
import it.fdepedis.earthquake.model.FeatureBean;
import it.fdepedis.earthquake.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DetailEarthquakeActivity extends AppCompatActivity implements Serializable {

    private static final String LOG_TAG = DetailEarthquakeActivity.class.getName();

    /*@BindView(R.id.tvCityMeteo)
    TextView tvCityMeteo;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_earthquake);
        ButterKnife.bind(this);

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
            //Log.e(LOG_TAG, "coordinates: " + feature.getGeometryBean().getCoordinates());
        }

    }
    
}