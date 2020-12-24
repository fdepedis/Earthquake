package it.fdepedis.earthquake.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import it.fdepedis.earthquake.R;

import java.util.Date;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import it.fdepedis.earthquake.activity.EarthquakeActivity;
import it.fdepedis.earthquake.model.EarthquakeBean;
import it.fdepedis.earthquake.model.FeatureBean;
import it.fdepedis.earthquake.utils.Utils;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder> {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    private static final String LOCATION_SEPARATOR = " of ";
    private List<FeatureBean> featureList;
    private Context context;

    public EarthquakeAdapter(Context context, List<FeatureBean> featureList){
        this.context = context;
        this.featureList = featureList;
    }

    class EarthquakeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        TextView txtMagnitude;
        TextView txtPrimaryLocation;
        TextView txtLocationOffset;
        TextView txtDate;
        TextView txtTime;

        EarthquakeViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtMagnitude = mView.findViewById(R.id.magnitude);
            txtPrimaryLocation = mView.findViewById(R.id.primary_location);
            txtLocationOffset = mView.findViewById(R.id.location_offset);
            txtDate = mView.findViewById(R.id.date);
            txtTime = mView.findViewById(R.id.time);
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public EarthquakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.earthquake_row, parent, false);
        return new EarthquakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EarthquakeViewHolder holder, int position) {

        /** Format Magnitude */
        String formattedMagnitude = Utils.formatMagnitude(featureList.get(position).getPropertiesBean().getMag());
        holder.txtMagnitude.setText(formattedMagnitude);

        GradientDrawable magnitudeCircle = (GradientDrawable) holder.txtMagnitude.getBackground();
        int magnitudeColor = Utils.getMagnitudeColor(context, featureList.get(position).getPropertiesBean().getMag());
        magnitudeCircle.setColor(magnitudeColor);

        /** Format Location */
        String originalLocation = featureList.get(position).getPropertiesBean().getPlace();
        String primaryLocation;
        String locationOffset;

        // Check whether the originalLocation string contains the " of " text
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = context.getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        holder.txtPrimaryLocation.setText(primaryLocation);
        holder.txtLocationOffset.setText(locationOffset);

        /** Format Time */
        Date dateObject = new Date(featureList.get(position).getPropertiesBean().getTime());
        String formattedDate = Utils.formatDate(dateObject);
        String formattedTime = Utils.formatTime(dateObject);
        holder.txtDate.setText(formattedDate);
        holder.txtTime.setText(formattedTime);

    }

    @Override
    public int getItemCount() {
        if(featureList != null){
            return featureList.size();
        }
        return 0;
    }

    public void setFeatureList(List<FeatureBean> featureList) {
        this.featureList = featureList;
        notifyDataSetChanged();
    }
}
