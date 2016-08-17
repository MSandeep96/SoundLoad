package com.sande.soundload.Fragments;

import com.sande.soundload.DataLayer.DataLayer;
import com.sande.soundload.DataLayer.DataLayerInterface;
import com.sande.soundload.Pojo.Track;

import java.util.List;

/**
 * Created by Sandeep on 17-08-2016.
 */
public class ShowTracksPresenter implements ShowTracksPresenterInterface {
    ShowTracksInterface showTracks;
    DataLayerInterface dataLayer;
    String next_pagin;

    public ShowTracksPresenter(ShowTracksInterface mShow){
        showTracks=mShow;
        dataLayer=new DataLayer();
    }


    @Override
    public void getTracks() {
        dataLayer.getTracks(this);
    }

    @Override
    public void gotTracks(List<Track> tracks, String href) {
        showTracks.gotTracks(tracks);
        if(href==null){
            showTracks.setScrollableFalse();
        }else {
            next_pagin = href;
        }
    }

    @Override
    public void hasPaginated() {
        dataLayer.getPaginatedTracks(this,next_pagin);
    }
}
