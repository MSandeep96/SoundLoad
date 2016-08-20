package com.sande.soundload.DataLayer;

import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;
import com.sande.soundload.DownloadShareActivity.DownloadSharePresenterInterface;
import com.sande.soundload.Fragments.ShowTracks.ShowTracksPresenterInterface;
import com.sande.soundload.ImpConstants;
import com.sande.soundload.Pojo.LikesPaginated;
import com.sande.soundload.Pojo.Track;
import com.sande.soundload.Pojo.User;
import com.sande.soundload.PrefsConstants;
import com.sande.soundload.SoundCloudApi;
import com.sande.soundload.loginActivity_MVP.LoginPresenterInterface;
import com.sande.soundload.mainActivity_MVP.MainPresenterInterface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sandeep on 17-08-2016.
 */
public class DataLayer implements DataLayerInterface,PrefsConstants,ImpConstants{

    private SoundCloudApi soundCloudApi;
    HashMap<Long,Track> downloadRefs=new HashMap<>();

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
        Call<LikesPaginated> getTracks=soundCloudApi.getTracks(Prefs.getLong(USER_ID,0));
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

    @Override
    public boolean checkIfConnectionIsMetered(Context context){
        ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.isActiveNetworkMetered();
    }

    @Override
    public void startDownload(Track trackObject, Context context, MainPresenterInterface presenterInterface) {
        String downloadURL=trackObject.getDownloadLink()+"?client_id="+CLIENT_ID;
        if(!StorageManager.isWritable()){
            presenterInterface.notWritable();
            return;
        }
        Uri downUri=Uri.parse(downloadURL);
        DownloadManager.Request mReq=new DownloadManager.Request(downUri);
        mReq.setDestinationUri(StorageManager.getDestnationUri(trackObject));
        //Uri albumArt=Uri.parse(trackObject.getArtwork_url());
        DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadRefs.put(downloadManager.enqueue(mReq),trackObject);
    }

    @Override
    public void getSharedTrack(String url, final DownloadSharePresenterInterface downloadSharePresenterInterface) {
        if(soundCloudApi==null)
            initNetwork();
        Call<Track> getSharedTrack=soundCloudApi.getTheSharedTrack(url);
        getSharedTrack.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                downloadSharePresenterInterface.gotTrack(response.body());
            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {

            }
        });
    }

    @Override
    public void startSharedTrackDownload(Track track, Context context,DownloadSharePresenterInterface downloadSharePresenterInterface) {
        String downloadURL=track.getDownloadLink()+"?client_id="+CLIENT_ID;
        if(!StorageManager.isWritable()){
            downloadSharePresenterInterface.notWritable();
            return;
        }
        Uri downUri=Uri.parse(downloadURL);
        DownloadManager.Request mReq=new DownloadManager.Request(downUri);
        mReq.setDestinationUri(StorageManager.getDestnationUri(track));
        //Uri albumArt=Uri.parse(trackObject.getArtwork_url());
        DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadRefs.put(downloadManager.enqueue(mReq),track);
    }


    /*
    @Override
    public void downloaded(Context context, Intent intent) {
        long refID=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
        if(downloadRefs.containsKey(refID)){
            DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri musicFile=downloadManager.getUriForDownloadedFile(refID);
            File mFile=new File(musicFile.getPath());
            try {
                Mp3File songFile=new Mp3File(mFile.getAbsolutePath());
                ID3v24Tag tag=new ID3v24Tag();
                songFile.setId3v2Tag(tag);
                Track track=downloadRefs.get(refID);
                tag.setArtist(track.getTrackArtist());
                tag.setTitle(track.getTrackTitle());
                tag.setPublisher(track.getArtist());
                tag.setComment("Downloaded via Soundload");
                tag.setAlbum("SoundcloudSongs");
                songFile.save(track.getTrackTitle()+".mp3");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    */


}
