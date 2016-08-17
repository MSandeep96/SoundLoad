package com.sande.soundload.loginActivity_MVP;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sande.soundload.ImpConstants;
import com.sande.soundload.mainActivity_MVP.MainActivity;
import com.sande.soundload.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView, ImpConstants {

    @BindView(R.id.al_iv_logo)
    ImageView logoImageView;


    LoginPresenterInterface loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginPresenter(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter.checkForBroadcast(this.getIntent().getData());
    }


    @OnClick(R.id.al_bt_login)
    void makeUserLogin() {
        Uri urlReq = Uri.parse(CALL_STRING);
        Intent inte = new Intent(Intent.ACTION_VIEW, urlReq);
        Intent browserChooser = Intent.createChooser(inte, "Choose a browser (Not Soundcloud):");
        startActivity(browserChooser);
    }

    @OnClick(R.id.al_ib_close_activity)
    void closeActivity() {
        navigateToMainView();
    }


    @Override
    public void navigateToMainView() {
        Intent mInte = new Intent(this, MainActivity.class);
        startActivity(mInte);
    }

    @Override
    public void showLoading() {
        findViewById(R.id.al_bt_login).setVisibility(View.GONE);
    }
}
