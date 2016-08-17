package com.sande.soundload.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sande.soundload.Pojo.Track;
import com.sande.soundload.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sandeep on 17-08-2016.
 */
public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TrackViewHolder> {

    LayoutInflater layoutInflater;
    ArrayList<Track> trackList;

    public TracksAdapter(Context context){
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView=layoutInflater.inflate(R.layout.track_layout,parent,false);
        return new TrackViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(trackList.get(position).getArtwork_url());
        holder.artistName.setText(trackList.get(position).getArtist());
        holder.trackName.setText(trackList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        if(trackList==null)
            return 0;
        else
            return trackList.size();
    }

    public void addTracks(List<Track> mTrackList) {
        trackList.addAll(mTrackList);
        notifyDataSetChanged();
    }

    static class TrackViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tl_iv_trackCover)
        ImageView trackCover;
        @BindView(R.id.tl_tv_artistName)
        TextView artistName;
        @BindView(R.id.tl_tv_trackName)
        TextView trackName;

        public TrackViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
