package com.sande.soundload.DownloadShareActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DownloadShareActivity extends AppCompatActivity implements DownloadShareView{

    DownloadSharePresenterInterface downloadSharePresenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent trackIntent=getIntent();
        String action=trackIntent.getAction();
        String type=trackIntent.getType();

        if(action.equals(Intent.ACTION_SEND)&&type.equals("text/plain")){
            downloadSharePresenterInterface=new DownloadSharePresenter(this);
            downloadSharePresenterInterface.resolveUrl(trackIntent);
        }else{
            curseTheUser();
        }
    }

    private void curseTheUser() {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
