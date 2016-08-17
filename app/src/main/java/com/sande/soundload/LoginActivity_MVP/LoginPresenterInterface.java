package com.sande.soundload.loginActivity_MVP;

import android.net.Uri;

import com.sande.soundload.Pojo.User;

/**
 * Created by Sandeep on 14-08-2016.
 */
public interface LoginPresenterInterface {
    void checkForBroadcast(Uri data);

    void gotUserDetails(User user);
}
