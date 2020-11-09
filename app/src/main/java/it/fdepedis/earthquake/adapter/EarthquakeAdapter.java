package it.fdepedis.earthquake.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import it.fdepedis.earthquake.R;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import it.fdepedis.earthquake.model.EarthquakeBean;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder> {

    private List<EarthquakeBean> dataList;
    private Context context;

    public EarthquakeAdapter(Context context, List<EarthquakeBean> dataList){
        this.context = context;
        this.dataList = dataList;
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

        holder.txtPlace.setText(dataList.get(position).getPlace());

        /*Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getThumbnailUrl())
                .placeholder((R.drawable.ic_stat_album))
                .error(R.drawable.ic_stat_album)
                .into(holder.coverImage);*/
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setData(List<EarthquakeBean> data) {
        this.dataList = data;
        notifyDataSetChanged();
    }
}
