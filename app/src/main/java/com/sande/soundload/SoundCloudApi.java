package com.sande.soundload;

import com.sande.soundload.Pojo.LikesPaginated;
import com.sande.soundload.Pojo.Track;
import com.sande.soundload.Pojo.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Sandeep on 17-08-2016.
 */
public interface SoundCloudApi {
    @GET("me")
    Call<User> getUser(@Query("oauth_token") String token);

    @GET("/users/{user}/favorites?linked_partitioning=1&limit=35&client_id=7e02806c3fc8b37e671916b65262e6c5")
    Call<LikesPaginated> getTracks(@Path("user")long userID);

    @GET
    Call<LikesPaginated> getTracksPagin(@Url String url);

    @GET("/resolve?client_id=7e02806c3fc8b37e671916b65262e6c5")
    Call<Track> getTheSharedTrack(@Query("url") String url);

}
