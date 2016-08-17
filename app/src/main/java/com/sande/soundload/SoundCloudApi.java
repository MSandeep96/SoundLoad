package com.sande.soundload;

import com.sande.soundload.Pojo.LikesPaginated;
import com.sande.soundload.Pojo.Track;
import com.sande.soundload.Pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Sandeep on 17-08-2016.
 */
public interface SoundCloudApi {
    @GET("me")
    Call<User> getUser(@Query("oauth_token") String token);

    @GET("me/favorites?linked_partitioning=1&limit=35")
    Call<LikesPaginated> getTracks(@Query("oauth_token") String token);

    @GET("/{url}")
    Call<LikesPaginated> getTracksPagin(@Path("url") String url);

}
