package com.sande.soundload.DataLayer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;
import com.sande.soundload.Fragments.ShowTracksPresenterInterface;
import com.sande.soundload.Pojo.LikesPaginated;
import com.sande.soundload.Pojo.Track;
import com.sande.soundload.Pojo.User;
import com.sande.soundload.PrefsConstants;
import com.sande.soundload.SoundCloudApi;
import com.sande.soundload.loginActivity_MVP.LoginPresenterInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sandeep on 17-08-2016.
 */
public class DataLayer implements DataLayerInterface,PrefsConstants{

    private SoundCloudApi soundCloudApi;

    private void initNetwork() {
        Gson gson=new GsonBuilder().create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.soundcloud.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        soundCloudApi=retrofit.create(SoundCloudApi.class);
    }

    @Override
    public void getAppUserDetails(final LoginPresenterInterface loginPresenter, String accessToken) {
        if(soundCloudApi==null)
            initNetwork();
        Call<User> getUser=soundCloudApi.getUser(accessToken);
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                loginPresenter.gotUserDetails(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // TODO: 17-08-2016 Check something
            }
        });
    }

    @Override
    public void getTracks(final ShowTracksPresenterInterface tracksPresenter) {
        if(soundCloudApi==null)
            initNetwork();
        Call<LikesPaginated> getTracks=soundCloudApi.getTracks(Prefs.getString(ACCESSTOKEN,"0"));
        getTracks.enqueue(new Callback<LikesPaginated>() {
            @Override
            public void onResponse(Call<LikesPaginated> call, Response<LikesPaginated> response) {
                tracksPresenter.gotTracks(response.body().getCollection(),response.body().getNext_href());
            }

            @Override
            public void onFailure(Call<LikesPaginated> call, Throwable t) {

            }
        });
    }

    @Override
    public void getPaginatedTracks(final ShowTracksPresenterInterface tracksPresenter, String url) {
        if(soundCloudApi==null)
            initNetwork();
        Call<LikesPaginated> getPaginTracks=soundCloudApi.getTracksPagin(url);
        getPaginTracks.enqueue(new Callback<LikesPaginated>() {
            @Override
            public void onResponse(Call<LikesPaginated> call, Response<LikesPaginated> response) {
                tracksPresenter.gotTracks(response.body().getCollection(),response.body().getNext_href());
            }

            @Override
            public void onFailure(Call<LikesPaginated> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean checkForActiveNetworkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo=cm.getActiveNetworkInfo();

        //no active network connections returns false
        return ( mNetworkInfo != null && mNetworkInfo.isConnected() );

    }







    /*
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    */
}
