package com.sande.soundload;

import com.sande.soundload.Pojo.Track;
import com.sande.soundload.Pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Sandeep on 17-08-2016.
 */
public interface SoundCloudApi {
    @GET("me/oauth_token={token}")
    Call<User> getUser(@Path("token") String token);

    @GET("me/{id}/favorites")
    Call<List<Track>> getTracks(@Path("id") long id);

}
