package com.sande.soundload.MainActivity_MVP;

import com.pixplicity.easyprefs.library.Prefs;
import com.sande.soundload.PrefsConstants;

/**
 * Created by Sandeep on 14-08-2016.
 */
public class MainPresenter implements MainPresenterInterface,PrefsConstants {

    MainView mMainView;

    public MainPresenter(MainView mainView){
        mMainView=mainView;
    }

    @Override
    public void shouldMoveToLoginView() {
        if(Prefs.getBoolean(ISFIRSTTIME,true)){
            mMainView.navigateToLoginActivity();
            Prefs.putBoolean(ISFIRSTTIME,false);
        }
    }
}
