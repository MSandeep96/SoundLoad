package com.sande.soundload.mainActivity_MVP;

import android.content.Context;
import android.content.Intent;

import com.sande.soundload.Pojo.Track;

/**
 * Created by Sandeep on 14-08-2016.
 */
public interface MainPresenterInterface {

    void shouldMoveToLoginView();

    void chooseView(Context context);

    void itemClicked(Track downLink);

    void checkDownload(Context context);

    void startDownload(Context context);

    void notWritable();
}
