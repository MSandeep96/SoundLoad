package com.sande.soundload.DownloadShareActivity;

import android.content.Intent;

import com.sande.soundload.Pojo.Track;

/**
 * Created by Sandeep on 20-08-2016.
 */
public interface DownloadSharePresenterInterface {
    void resolveUrl(Intent desc);

    void gotTrack(Track track);

    void notWritable();
}
