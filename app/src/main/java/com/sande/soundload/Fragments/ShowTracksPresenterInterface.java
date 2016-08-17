package com.sande.soundload.Fragments;

import com.sande.soundload.Pojo.Track;

import java.util.List;

/**
 * Created by Sandeep on 17-08-2016.
 */
public interface ShowTracksPresenterInterface{

    public void getTracks();

    void gotTracks(List<Track> tracks, String href);

    void hasPaginated();
}
