package com.sande.soundload.mainActivity_MVP;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sande.soundload.Fragments.FragmentNoInternet;
import com.sande.soundload.Fragments.FragmentNotLoggedIn;
import com.sande.soundload.Fragments.ShowTracks.FragmentShowTracks;
import com.sande.soundload.ImpConstants;
import com.sande.soundload.Pojo.Track;
import com.sande.soundload.R;
import com.sande.soundload.TrackItemClicked;
import com.sande.soundload.loginActivity_MVP.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView,ImpConstants,TrackItemClicked{

    private static final int PERMISSION_REQ = 90;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bsm_rl_root)
    RelativeLayout rlBottomSheet;

    //views of bottom sheet
    @BindView(R.id.bsm_iv_cover)
    ImageView trackCover;
    @BindView(R.id.bsm_tv_artist)
    TextView artistName;
    @BindView(R.id.bsm_tv_duration)
    TextView durationTV;
    @BindView(R.id.bsm_tv_trackName)
    TextView trackName;

    BottomSheetBehavior bottomSheet;

    private MainPresenterInterface mainPresenterInterface;
    private BroadcastReceiver downloadReceiver;

    @OnClick(R.id.bsm_btn_cueDown)
    public void startDownload(){
        bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            mainPresenterInterface.checkDownload(this);
        }else{
            askForPermission();
        }
    }

    private void askForPermission() {
        ActivityCompat.requestPermissions(this
                ,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                ,PERMISSION_REQ);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSION_REQ){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                mainPresenterInterface.checkDownload(this);
            }else{
                mainPresenterInterface.itemClicked(null);
                new AlertDialog.Builder(this)
                        .setTitle(R.string.permission_req)
                        .setMessage(R.string.permission_req_reason)
                        .show();
            }
        }
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create instance of Presenter
        mainPresenterInterface =new MainPresenter(this);

        //Is it user's first time?? Then make him login
        mainPresenterInterface.shouldMoveToLoginView();

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        bottomSheet=BottomSheetBehavior.from(rlBottomSheet);

        setSupportActionBar(toolbar);

        //choose fragment to display
        mainPresenterInterface.chooseView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void navigateToLoginActivity() {
        Intent mInte=new Intent(this,LoginActivity.class);
        startActivity(mInte);
    }

    @Override
    public void showNotLoggedInScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.cm_fl_holder,new FragmentNotLoggedIn())
                .commit();
    }

    @Override
    public void showTracks() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.cm_fl_holder,new FragmentShowTracks())
                .commit();
    }

    @Override
    public void showNoInternet() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.cm_fl_holder,new FragmentNoInternet())
                .commit();
    }

    @Override
    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.metered_connection)
                .setMessage(R.string.metered_conn_prompt)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       mainPresenterInterface.startDownload(MainActivity.this);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //dismiss
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void showNotWritable() {
        Toast.makeText(this, R.string.ext_stor_not_writable, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemClicked(Track track) {
        mainPresenterInterface.itemClicked(track);
        trackName.setText(track.getTitle());
        Glide.with(this).load(track.getArtwork_url()).into(trackCover);
        durationTV.setText(track.getDuration());
        artistName.setText(track.getArtist());
        bottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
