package com.sande.soundload.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sande.soundload.Pojo.Track;
import com.sande.soundload.R;
import com.sande.soundload.TrackItemClicked;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sandeep on 17-08-2016.
 */
public class TracksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    LayoutInflater layoutInflater;
    ArrayList<Track> trackList;
    boolean paginated=true;
    public static final int LOADING_VIEW=0;
    public static final int TRACK_VIEW=1;
    TrackItemClicked trackItemDelegater;

    public TracksAdapter(Context context){
        layoutInflater=LayoutInflater.from(context);
        trackItemDelegater=(TrackItemClicked)context;
    }


    public void setPaginated(boolean val){
        paginated=val;
    }


    public void addTracks(List<Track> mTrackList) {
        if(trackList==null){
            trackList=new ArrayList<>();
        }
        int presentTrackCount=trackList.size();
        trackList.addAll(mTrackList);
        notifyItemRangeInserted(presentTrackCount,mTrackList.size());
    }

    @Override
    public int getItemCount() {
        //show loading
        if(trackList==null) {
            return 0;
        }
        else {
            if(paginated)
                return trackList.size() + 1;
            else
                return trackList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==trackList.size()){
            return LOADING_VIEW;
        }else{
            return TRACK_VIEW;
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if(holder instanceof TrackViewHolder){
            ((TrackViewHolder) holder).trackCover.setImageResource(0);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TRACK_VIEW) {
            View mView = layoutInflater.inflate(R.layout.track_layout, parent, false);
            return new TrackViewHolder(mView);
        }else{
            LinearLayout mLinearLayout = new LinearLayout(parent.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mLinearLayout.setLayoutParams(params);
            mLinearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            ProgressBar mProgre = new ProgressBar(parent.getContext());
            mLinearLayout.addView(mProgre);
            return new RecyclerView.ViewHolder(mLinearLayout) {};
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof TrackViewHolder) {
            Glide.with(holder.itemView.getContext())
                    .load(trackList.get(position).getArtwork_url())
                    .into(((TrackViewHolder) holder).trackCover);
            ((TrackViewHolder) holder).artistName.setText(trackList.get(position).getArtist());
            ((TrackViewHolder) holder).trackName.setText(trackList.get(position).getTitle());
            ((TrackViewHolder) holder).trackDuration.setText(trackList.get(position).getDuration());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    trackItemDelegater.itemClicked(trackList.get(holder.getAdapterPosition()));
                }
            });
        }
    }

    static class TrackViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tl_iv_trackCover)
        ImageView trackCover;
        @BindView(R.id.tl_tv_artistName)
        TextView artistName;
        @BindView(R.id.tl_tv_trackName)
        TextView trackName;
        @BindView(R.id.tl_tv_duration)
        TextView trackDuration;

        public TrackViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
