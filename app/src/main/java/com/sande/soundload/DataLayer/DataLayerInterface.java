package com.sande.soundload.DataLayer;

import android.content.Context;

import com.sande.soundload.DownloadShareActivity.DownloadSharePresenterInterface;
import com.sande.soundload.Fragments.ShowTracks.ShowTracksPresenterInterface;
import com.sande.soundload.Pojo.Track;
import com.sande.soundload.loginActivity_MVP.LoginPresenterInterface;
import com.sande.soundload.mainActivity_MVP.MainPresenterInterface;

/**
 * Created by Sandeep on 17-08-2016.
 */
public interface DataLayerInterface {

    void getAppUserDetails(LoginPresenterInterface loginPresenter, String accessToken);

    void getPaginatedTracks(ShowTracksPresenterInterface tracksPresenter, String url);

    boolean checkForActiveNetworkConnection(Context context);

    void getTracks(ShowTracksPresenterInterface tracksPresenter);

    boolean checkIfConnectionIsMetered(Context context);

    void startDownload(Track download_url, Context context, MainPresenterInterface presenterInterface);

    void getSharedTrack(String url, DownloadSharePresenterInterface downloadSharePresenterInterface);

    void startSharedTrackDownload(Track track,Context context,DownloadSharePresenterInterface downloadSharePresenterInterface);
}
