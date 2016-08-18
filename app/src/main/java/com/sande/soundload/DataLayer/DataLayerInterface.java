package com.sande.soundload.DataLayer;

import android.content.Context;

import com.sande.soundload.Fragments.ShowTracks.ShowTracksPresenterInterface;
import com.sande.soundload.loginActivity_MVP.LoginPresenterInterface;

/**
 * Created by Sandeep on 17-08-2016.
 */
public interface DataLayerInterface {

    public void getAppUserDetails(LoginPresenterInterface loginPresenter, String accessToken);

    void getPaginatedTracks(ShowTracksPresenterInterface tracksPresenter, String url);

    public boolean checkForActiveNetworkConnection(Context context);

    public void getTracks(ShowTracksPresenterInterface tracksPresenter);
}
