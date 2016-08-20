package com.sande.soundload.mainActivity_MVP;

import android.content.Context;
import android.content.Intent;

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
    Track toDownload;


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
    public void itemClicked(Track downLink) {
        toDownload=downLink;
    }

    @Override
    public void checkDownload(Context context) {
        if(toDownload==null)
            return;
        // TODO: 19-08-2016 cue download from data layer or here
        if(dataLayer.checkIfConnectionIsMetered(context)){
            mMainView.showAlertDialog();
        }else{
            startDownload(context);
        }
    }

    @Override
    public void startDownload(Context context) {
        dataLayer.startDownload(toDownload,context,this);
    }

    @Override
    public void notWritable() {
        mMainView.showNotWritable();
    }

}
