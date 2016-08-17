package com.sande.soundload.DataLayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;
import com.sande.soundload.Pojo.User;
import com.sande.soundload.PrefsConstants;
import com.sande.soundload.SoundCloudApi;
import com.sande.soundload.loginActivity_MVP.LoginPresenterInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sandeep on 17-08-2016.
 */
public class DataLayer implements DataLayerInterface,PrefsConstants{

    private final SoundCloudApi soundCloudApi;

    public DataLayer(){
        Gson gson=new GsonBuilder().create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://api.soundcloud.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        soundCloudApi=retrofit.create(SoundCloudApi.class);
    }

    @Override
    public void getAppUserDetails(final LoginPresenterInterface loginPresenter, String accessToken) {
        Call<User> getUser=soundCloudApi.getUser(accessToken);
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                loginPresenter.gotUserDetails(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // TODO: 17-08-2016 Check something
            }
        });
    }
}
