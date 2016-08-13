package com.sande.soundload;

import android.app.Application;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by Sandeep on 14-08-2016.
 */
public class Soundload extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName("Soundload")
                .setUseDefaultSharedPreference(true)
                .build();
    }
}
