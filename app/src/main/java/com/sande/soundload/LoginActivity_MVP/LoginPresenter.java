package com.sande.soundload.loginActivity_MVP;

import android.net.Uri;

import com.pixplicity.easyprefs.library.Prefs;
import com.sande.soundload.DataLayer.DataLayer;
import com.sande.soundload.DataLayer.DataLayerInterface;
import com.sande.soundload.Pojo.User;
import com.sande.soundload.PrefsConstants;

/**
 * Created by Sandeep on 14-08-2016.
 */
public class LoginPresenter implements LoginPresenterInterface,PrefsConstants {
    LoginView loginView;
    DataLayerInterface dataLayer;

    public LoginPresenter(LoginView mLogin){
        loginView=mLogin;
        dataLayer=new DataLayer();
    }

    @Override
    public void checkForBroadcast(Uri data){
        if(data!=null&& data.getScheme().equals("soundload")&&data.getFragment()!=null){
            loginView.showLoading();
            String accessToken=data.getFragment().replaceFirst("access_token=","");
            int pos=accessToken.indexOf("&");
            accessToken=accessToken.substring(0,pos);
            Prefs.putString(ACCESSTOKEN,accessToken);
            dataLayer.getAppUserDetails(this,accessToken);
        }
    }

    @Override
    public void gotUserDetails(User user) {
        Prefs.putLong(USER_ID,user.getId());
        Prefs.putString(USER_NAME,user.getUsername());
        Prefs.putBoolean(USER_LOGGED_IN,true);
        loginView.navigateToMainView();
    }


}
