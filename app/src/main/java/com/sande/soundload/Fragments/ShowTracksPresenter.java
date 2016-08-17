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

    public ShowTracksPresenter(ShowTracksInterface mShow){
        showTracks=mShow;
        dataLayer=new DataLayer();
    }


    @Override
    public void getTracks() {
        dataLayer.getTracks(this);
    }

    @Override
    public void gotTracks(List<Track> tracks) {
        showTracks.gotTracks(tracks);
    }
}
