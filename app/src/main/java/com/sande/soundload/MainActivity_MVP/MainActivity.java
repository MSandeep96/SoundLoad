package com.sande.soundload.mainActivity_MVP;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.sande.soundload.Fragments.FragmentNoInternet;
import com.sande.soundload.Fragments.FragmentNotLoggedIn;
import com.sande.soundload.Fragments.FragmentShowTracks;
import com.sande.soundload.ImpConstants;
import com.sande.soundload.R;
import com.sande.soundload.loginActivity_MVP.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView,ImpConstants{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MainPresenterInterface mainPresenterInterface;

    public static int NO_INTERNET=0;
    public static int NOT_LOGGED_IN=1;
    public static int SHOW_TRACKS=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create instance of Presenter
        mainPresenterInterface =new MainPresenter(this);

        //Is it user's first time?? Then make him login
        mainPresenterInterface.shouldMoveToLoginView();

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

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
}
