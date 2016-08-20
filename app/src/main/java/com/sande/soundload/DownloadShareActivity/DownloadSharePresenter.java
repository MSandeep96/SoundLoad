package com.sande.soundload.DownloadShareActivity;

import android.content.Intent;
import android.util.Log;

import com.sande.soundload.DataLayer.DataLayer;
import com.sande.soundload.Pojo.Track;

/**
 * Created by Sandeep on 20-08-2016.
 */
public class DownloadSharePresenter implements DownloadSharePresenterInterface {

    private final DataLayer dataLayer;
    DownloadShareView downloadShareView;

    DownloadSharePresenter(DownloadShareView shareView){
        downloadShareView=shareView;
        dataLayer=new DataLayer();
    }

    @Override
    public void resolveUrl(Intent inte) {
        String desc=inte.getStringExtra(Intent.EXTRA_TEXT);
        if(desc!=null){
            int pos=desc.lastIndexOf("https://");
            if(pos==-1)
                pos=desc.lastIndexOf("http://");
            String url=desc.substring(pos);
            Log.i("TrackUrl",url);
            dataLayer.getSharedTrack(url,this);
        }
    }

    @Override
    public void gotTrack(Track track) {
        dataLayer.startSharedTrackDownload(track,downloadShareView.getContext(),this);
    }

    @Override
    public void notWritable() {

    }
}
