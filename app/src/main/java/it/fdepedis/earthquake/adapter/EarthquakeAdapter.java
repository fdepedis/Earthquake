package it.fdepedis.earthquake.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import it.fdepedis.earthquake.R;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import it.fdepedis.earthquake.activity.EarthquakeActivity;
import it.fdepedis.earthquake.model.EarthquakeBean;
import it.fdepedis.earthquake.model.FeatureBean;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder> {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    private List<FeatureBean> featureList;
    private Context context;

    public EarthquakeAdapter(Context context, List<FeatureBean> featureList){
        this.context = context;
        this.featureList = featureList;
    }

    class EarthquakeViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        TextView txtPlace;
        //private ImageView coverImage;

        EarthquakeViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtPlace = mView.findViewById(R.id.place);
            //coverImage = mView.findViewById(R.id.coverImage);
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

        holder.txtPlace.setText(featureList.get(position).getPropertiesBean().getPlace());

        /*Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getThumbnailUrl())
                .placeholder((R.drawable.ic_stat_album))
                .error(R.drawable.ic_stat_album)
                .into(holder.coverImage);*/
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
