package com.sande.soundload.mainActivity_MVP;

/**
 * Created by Sandeep on 14-08-2016.
 */
public interface MainView {

    void navigateToLoginActivity();

    void showNotLoggedInScreen();

    void showTracks();

    void showNoInternet();

    void showAlertDialog();

    void showNotWritable();
}
