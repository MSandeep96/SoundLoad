package com.sande.soundload.mainActivity_MVP;

import android.content.Context;

import com.pixplicity.easyprefs.library.Prefs;
import com.sande.soundload.DataLayer.DataLayer;
import com.sande.soundload.DataLayer.DataLayerInterface;
import com.sande.soundload.Pojo.Track;
import com.sande.soundload.PrefsConstants;

/**
 * Created by Sandeep on 14-08-2016.
 */
public class MainPresenter implements MainPresenterInterface,PrefsConstants {

    MainView mMainView;
    DataLayerInterface dataLayer;
    String download_url;


    public MainPresenter(MainView mainView){
        mMainView=mainView;
        dataLayer=new DataLayer();

    }

    @Override
    public void shouldMoveToLoginView() {
        if(Prefs.getBoolean(ISFIRSTTIME,true)){
            mMainView.navigateToLoginActivity();
            Prefs.putBoolean(ISFIRSTTIME,false);
        }
    }

    @Override
    public void chooseView(Context context) {
        if(dataLayer.checkForActiveNetworkConnection(context)){
            //internet exists
            if(!Prefs.getBoolean(USER_LOGGED_IN,false)){
                mMainView.showNotLoggedInScreen();
            }else{
                mMainView.showTracks();
            }
        }else{
            //no internet
            mMainView.showNoInternet();
        }
    }

    @Override
    public void itemClicked(String downLink) {
        download_url=downLink;
    }

    @Override
    public void startDownload() {
        if(download_url==null)
            return;
        // TODO: 19-08-2016 cue download from data layer or here
    }
}
