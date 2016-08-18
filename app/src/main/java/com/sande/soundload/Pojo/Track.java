package com.sande.soundload.Pojo;

/**
 * Created by Sandeep on 17-08-2016.
 */
public class Track {
    long id;
    long duration;
    String title;
    String artwork_url;
    String stream_url;
    User user;


    public String getDuration() {
        long s = (duration/1000) % 60;
        long m = (duration /(1000* 60)) % 60;
        long h = (duration / (1000 * 60 * 60)) % 24;
        if(h!=0) {
            return String.format("%d:%02d:%02d", h, m, s);
        }else{
            return String.format("%02d:%02d",m,s);
        }
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public String getArtwork_url() {
        return artwork_url;
    }

    public String getArtist() {
        return user.getUsername();
    }

    public String getDownloadLink() {
        return stream_url;
    }
}
