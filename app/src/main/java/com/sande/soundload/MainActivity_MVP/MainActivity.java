package com.sande.soundload.mainActivity_MVP;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    @OnClick(R.id.bsm_btn_cueDown)
    public void startDownload(){
        mainPresenterInterface.startDownload();
    }

    BottomSheetBehavior bottomSheet;

    private MainPresenterInterface mainPresenterInterface;


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
    public void itemClicked(Track track) {
        mainPresenterInterface.itemClicked(track.getDownloadLink());
        trackName.setText(track.getTitle());
        Glide.with(this).load(track.getArtwork_url()).into(trackCover);
        durationTV.setText(track.getDuration());
        artistName.setText(track.getArtist());
        bottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
