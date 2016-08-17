package com.sande.soundload.Pojo;

/**
 * Created by Sandeep on 17-08-2016.
 */
public class Track {
    long id;
    long duration;
    String title;
    String artwork_url;
    User user;


    public String getDuration() {
        long s = duration % 60;
        long m = (duration / 60) % 60;
        long h = (duration / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h,m,s);
    }

    @Override
    public String toString() {
        return title;
    }
}
